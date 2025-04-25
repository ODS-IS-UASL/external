package jp.go.meti.drone.com.common.mail;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jp.go.meti.drone.com.common.date.SystemDate;
import jp.go.meti.drone.com.common.exception.DroneRuntimeException;
import jp.go.meti.drone.com.common.logging.ApplicationLogger;
import jp.go.meti.drone.com.common.logging.LoggerFactory;
import jp.go.meti.drone.com.common.mail.control.delivery.MailDeliveryControl;
import jp.go.meti.drone.com.common.util.DateUtils;
import jp.go.meti.drone.com.common.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * メール送信用のユーティリティクラス。
 * <p>
 * メール送信やメール本文を作成するための機能を提供します。
 * </p>
 *
 * @version $Revision$
 */
@Slf4j
@RequiredArgsConstructor
public class MailUtilsImpl implements MailUtils {
    /** ロガー */
    private final ApplicationLogger appLogger = LoggerFactory.getApplicationLogger(log);

    /** メール送信で使用するJavaMail用のSender */
    private final JavaMailSender mailSender;

    /** FreeMarker用の設定情報 */
    private final Configuration freemarkerConfiguration;

    /** システム日時 */
    private final SystemDate systemDate;

    /** メール送信制御部品 */
    private final MailDeliveryControl deliveryControl;

    /** 文字コード */
    private static final String CHARSET = StandardCharsets.UTF_8.name();

    /** 送信元メールアドレス */
    @Value("${mail.template.fromAddress:}")
    private String fromAddress;

    /** 送信元表示名 */
    @Value("${mail.template.fromName:}")
    private String fromName;

    /**
     * 指定されたMessage情報を非同期でメール送信します。
     *
     * @param mimeMessage 送信対象のMessage情報
     */
    void sendMail(MimeMessage mimeMessage) {
        mailSender.send(mimeMessage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMail(String subject, String text, String... to) {
        execute(this.fromAddress, this.fromName, subject, text, to);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMail(String subjectId, String templateFileName, Map<String, Object> model, List<String> toList) {
        try {
            // 入力チェック
            checkSubjectId(subjectId);
            checkTemplateFileName(templateFileName);
            checkModel(model);
            checkToList(toList);

            String subject = MessageUtils.getContextMessage(subjectId);
            String text = getTextFromTemplate(templateFileName, model);
            String[] to = toList.toArray(new String[toList.size()]);

            // メール送信
            execute(this.fromAddress, this.fromName, subject, text, to);
        } catch (IllegalArgumentException e) {
            throw new DroneRuntimeException(
                String.format(
                    "subjectId: [%s], templateFileName: [%s], model: %s, toList: %s",
                    subjectId,
                    templateFileName,
                    model,
                    toList), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTextFromTemplate(String templateFileName, Map<String, Object> model) {

        try {
            // 入力チェック
            checkTemplateFileName(templateFileName);
            checkModel(model);

            var template = freemarkerConfiguration.getTemplate(templateFileName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException e) {
            throw new UncheckedIOException(
                String.format("templateFileName: [%s], model: [%s]", templateFileName, model), e);
        } catch (IllegalArgumentException | TemplateException e) {
            throw new DroneRuntimeException(
                String.format("templateFileName: [%s], model: [%s]", templateFileName, model), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void sendMailWithFrom(String fromAddress, String fromName, String subject, String text, String... to) {
        try {
            // 入力チェック
            checkFromAddress(fromAddress);
            checkFromName(fromName);

            // メール送信
            execute(fromAddress, fromName, subject, text, to);
        } catch (IllegalArgumentException e) {
            throw new DroneRuntimeException(
                String.format(
                    "fromAddress: [%s], fromName: [%s], subject: [%s], text: [%s], to: %s",
                    fromAddress,
                    fromName,
                    subject,
                    text,
                    Arrays.toString(to)), e);
        }
    }

    /**
     * メール送信の共通処理。
     *
     * @param fromAddress 送信元アドレス
     * @param fromName 送信元として表示する名前
     * @param subject メールの件名
     * @param text メール本文
     * @param to メールの宛先リスト
     */
    private void execute(String fromAddress, String fromName, String subject, String text, String... to) {
        try {
            // 入力チェック
            checkSubject(subject);
            checkText(text);
            checkTo(to);

            if (appLogger.isDebugEnabled()) {
                appLogger.debug("MailUtils mail.template.fromAddress = {}", fromAddress);
                appLogger.debug("MailUtils mail.template.fromName = {}", fromName);

                appLogger.debug("MailUtils Arguments= {} , {} , {}", subject, text, Arrays.asList(to).toString());
            }

            // メール送信抑止対象になっているメールアドレスを除外する
            String[] actualToAddresses = deliveryControl(subject, to);
            if (actualToAddresses.length == 0) {
                // 除外した結果送信対象がなくなった場合は何もせず終了する
                return;
            }

            MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(), false, CHARSET);
            helper.setFrom(fromAddress, fromName);
            helper.setSubject(subject);
            helper.setTo(actualToAddresses);
            helper.setSentDate(DateUtils.localDateTimeToDate(systemDate.now()));

            MimeMessage message = helper.getMimeMessage();
            message.setContent(text, "text/plain; charset=" + CHARSET);

            if (appLogger.isDebugEnabled()) {
                appLogger.debug("MailUtils MimeMessage.getFrom = {}", Arrays.asList(message.getFrom()).toString());
                appLogger.debug(
                    "MailUtils MimeMessage.getTo = {}",
                    Arrays.asList(message.getRecipients(Message.RecipientType.TO)));
            }
            sendMail(message);
        } catch (IllegalArgumentException | UnsupportedEncodingException | MessagingException e) {
            throw new DroneRuntimeException(
                String.format("subject: [%s], text: [%s], to: %s", subject, text, Arrays.toString(to)), e);
        }
    }

    /**
     * メール送信抑止対象になっているメールアドレスを除外します。
     *
     * @param subject メールの件名
     * @param to メールの宛先リスト
     * @return メール送信抑止対象になっているメールアドレスを除外したメールアドレスの配列
     */
    private String[] deliveryControl(String subject, String... to) {
        String[] actualToAddresses = new String[0];
        actualToAddresses = deliveryControl.createActualToAddressArray(subject, to);
        if (appLogger.isDebugEnabled()) {
            appLogger.debug(
                "MailUtils some addresses are suppressed {} -> {}",
                Arrays.asList(to).toString(),
                Arrays.asList(actualToAddresses).toString());
        }
        if (actualToAddresses.length == 0) {
            if (appLogger.isDebugEnabled()) {
                appLogger.debug("MailUtils all addresses are suppressed");
            }
        }
        return actualToAddresses;
    }

    /**
     * メール件名のチェック
     *
     * @param subject メール件名
     * @throws IllegalArgumentException subjectがnullの場合
     */
    private void checkSubject(String subject) {
        if (subject == null) {
            throw new IllegalArgumentException("'subject' cannot be null.");
        }
    }

    /**
     * メール本文のチェック
     *
     * @param text メール本文
     * @throws IllegalArgumentException textがnullの場合
     */
    private void checkText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("'text' cannot be null.");
        }
    }

    /**
     * メールの宛先リストのチェック
     *
     * @param toArray メールの宛先リスト
     * @throws IllegalArgumentException toArrayの値にnull、空文字が含まれる場合
     */
    private void checkTo(String... toArray) {
        for (String to : toArray) {
            if (StringUtils.isEmpty(to)) {
                throw new IllegalArgumentException("'to' cannot be null or empty.");
            }
        }
    }

    /**
     * メール件名のメッセージIDのチェック
     *
     * @param subjectId メール件名のメッセージID
     * @throws IllegalArgumentException subjectIdがnullまたは空文字の場合
     */
    private void checkSubjectId(String subjectId) {
        if (StringUtils.isEmpty(subjectId)) {
            throw new IllegalArgumentException("'subjectId' cannot be null or empty.");
        }
    }

    /**
     * テンプレートファイル名のチェック
     *
     * @param templateFileName テンプレートファイル名
     * @throws IllegalArgumentException templateFileNameがnullまたは空文字の場合
     */
    private void checkTemplateFileName(String templateFileName) {
        if (StringUtils.isEmpty(templateFileName)) {
            throw new IllegalArgumentException("'templateFileName' cannot be null or empty.");
        }
    }

    /**
     * モデル情報のチェック
     *
     * @param model テンプレートに埋め込むモデル情報
     * @throws IllegalArgumentException modelがnullまたは空の場合、modelのkeyにnullまたは空文字が含まれる場合、modelのvalueにnullが含まれる場合
     */
    private void checkModel(Map<String, Object> model) {
        if (MapUtils.isEmpty(model)) {
            throw new IllegalArgumentException("'model' cannot be null or empty.");
        }

        for (Map.Entry<String, Object> entry : model.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            if (StringUtils.isEmpty(key)) {
                throw new IllegalArgumentException("'model' cannot be null or empty.");
            }
            if (value == null) {
                throw new IllegalArgumentException("'model' cannot be null.");
            }
        }
    }

    /**
     * メールの宛先リストのチェック<br>
     * 配列に変換後にcheckToメソッドでここでリスト内の値のチェック処理を行うため、本メソッドでは実施しない。
     *
     * @param toList メールの宛先リスト
     * @throws IllegalArgumentException toListがnullの場合
     */
    private void checkToList(List<String> toList) {
        if (toList == null) {
            throw new IllegalArgumentException("'toList' cannot be null.");
        }
    }

    /**
     * 送信元アドレスのチェック
     *
     * @param fromAddress 送信元アドレス
     * @throws IllegalArgumentException fromAddressがnullまたは空文字の場合
     */
    private void checkFromAddress(String fromAddress) {
        if (StringUtils.isEmpty(fromAddress)) {
            throw new IllegalArgumentException("'fromAddress' cannot be null or empty.");
        }
    }

    /**
     * 送信元として表示する名前のチェック
     *
     * @param fromName 送信元として表示する名前
     * @throws IllegalArgumentException fromNameがnullの場合
     */
    private void checkFromName(String fromName) {
        if (fromName == null) {
            throw new IllegalArgumentException("'fromName' cannot be null.");
        }
    }
}
