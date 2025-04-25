package jp.go.meti.drone.com.common.mail.control.delivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import jakarta.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import jp.go.meti.drone.com.common.logging.ApplicationLogger;
import jp.go.meti.drone.com.common.logging.LoggerFactory;
import jp.go.meti.drone.com.common.util.MessageUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * メール送信制御用のクラス。
 * <p>
 * メール送信時に送信抑止対象に設定されたメールアドレスを除隊したアドレスリストを作成する機能を提供します。
 * </p>
 *
 * @version $Revision$
 */
@Component
@EnableConfigurationProperties(MailDeliveryControlProperties.class)
@RequiredArgsConstructor
@Slf4j
public class MailDeliveryControl {

    /**
     * メッセージ未定義通知用の文字列識別用。
     */
    private static final String UNDEFINED_MESSAGE_PREFIX = "UNDEFINED-MESSAGE:";

    /**
     * メッセージ定義の可変部分を表す正規表現パターン。
     */
    private static final Pattern HOLDER_PATTERN = Pattern.compile("\\{\\d+\\}");

    /**
     * メッセージ定義の可変部分を置き換える正規表現文字列。
     */
    private static final String ANYSTRING_PATTERN = ".*";

    /**
     * 正規表現化する際にエスケープが必要となる文字群。
     */
    private static final Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");

    /**
     * 空配列
     */
    private static final String[] EMPTY_ARRAY = new String[0];

    /**
     * ロガー。
     */
    private final ApplicationLogger appLogger = LoggerFactory.getApplicationLogger(log);

    /**
     * メール送信制御プロパティ
     */
    private final MailDeliveryControlProperties mailDeliveryProps;

    /**
     * 送信抑止対象のメールアドレスリスト
     */
    @Getter
    private List<String> excludeAddresses;

    /**
     * 送信抑止を行わないメールのタイトルリスト
     */
    @Getter
    private List<Pattern> deliveryAllSubjectPatterns;

    /**
     * 引数として渡された宛先メールアドレスリストから送信抑止対象のメールアドレスを除外して返却します。<br/>
     * ただし、メールのタイトルが除外処理対象外に設定されたタイトルの場合は何もせずそのまま返却します。
     *
     * @param subject メールの題名
     * @param toAddresses 送信先アドレスの配列
     * @return 送信抑止対象を除いた送信先アドレスの配列。除外した結果対象がいなくなった場合は空配列。
     */
    public String[] createActualToAddressArray(String subject, String... toAddresses) {
        if (toAddresses == null) {
            // 万が一引数のアドレスリストがnullだったら空配列を返却する
            return EMPTY_ARRAY;
        }
        for (Pattern subjectPattern : deliveryAllSubjectPatterns) {
            if (subjectPattern.matcher(subject).matches()) {
                // タイトルが抑止対象外のメールのタイトルと一致する場合は引数をそのまま返却する
                return toAddresses;
            }
        }
        List<String> actualSendAddresList = new ArrayList<>(toAddresses.length);
        for (String address : toAddresses) {
            if (!excludeAddresses.contains(address)) {
                actualSendAddresList.add(address);
            }
        }
        // 送信抑止対象以外のメールアドレスのみを実際に送信するアドレスとして返却する
        return actualSendAddresList.toArray(new String[actualSendAddresList.size()]);
    }

    /**
     * 初期化処理を行う。
     */
    @PostConstruct
    void initialize() {

        if (appLogger.isDebugEnabled()) {
            appLogger.debug("MailDeliveryControl:initialize props = {}", mailDeliveryProps);
        }

        this.excludeAddresses = mailDeliveryProps.getExcludeAddresses();
        if (mailDeliveryProps.getExcludeAddresses() == null) {
            this.excludeAddresses = Collections.emptyList();
        } else {
            this.excludeAddresses = Collections.unmodifiableList(mailDeliveryProps.getExcludeAddresses());
        }

        if (mailDeliveryProps.getDeliveryAllSubjectKeys() != null) {
            List<Pattern> tempList = new ArrayList<>(mailDeliveryProps.getDeliveryAllSubjectKeys().size());
            for (String key : mailDeliveryProps.getDeliveryAllSubjectKeys()) {
                String subject = MessageUtils.getMessage(key);
                if (StringUtils.startsWith(subject, UNDEFINED_MESSAGE_PREFIX)) {
                    if (appLogger.isWarnEnabled()) {
                        appLogger.warn("MailDeliveryControl:initialize undefined title key = {}", key);
                    }
                } else {
                    tempList.add(convertTitleToPattern(subject));
                }
            }
            this.deliveryAllSubjectPatterns = Collections.unmodifiableList(tempList);
        } else {
            this.deliveryAllSubjectPatterns = Collections.emptyList();
        }

        if (appLogger.isDebugEnabled()) {
            appLogger.debug(
                "MailDeliveryControl:initialize addresses = {}, titles = {}",
                this.excludeAddresses,
                this.deliveryAllSubjectPatterns);
        }
    }

    /**
     * メッセージ定義された文字列から可変部分（{0}、{1}…の部分）を任意の文字列を示す表現に置き換えた形で正規表現化する。
     *
     * @param subject 対象文字列
     * @return 変換した正規表現オブジェクト
     */
    private static Pattern convertTitleToPattern(String subject) {
        var matcher = HOLDER_PATTERN.matcher(subject);
        var sb = new StringBuilder();
        var start = 0;
        var end = 0;
        while (matcher.find()) {
            end = matcher.start();
            var subSeq = subject.substring(start, end);
            sb.append(escapeSpecialRegexWithSingleEscape(subSeq));
            sb.append(ANYSTRING_PATTERN);
            start = matcher.end();
        }
        if (start < subject.length()) {
            sb.append(escapeSpecialRegexWithSingleEscape(subject.substring(start)));
        }
        return Pattern.compile(sb.toString());
    }

    /**
     * 文字列を正規表現として扱えるように特殊文字をエスケープする
     *
     * @param str エスケープ対象文字列
     * @return 正規表現用にエスケープを行った文字列
     */
    private static String escapeSpecialRegexWithSingleEscape(String str) {
        return SPECIAL_REGEX_CHARS.matcher(str).replaceAll("\\\\$0");
    }
}
