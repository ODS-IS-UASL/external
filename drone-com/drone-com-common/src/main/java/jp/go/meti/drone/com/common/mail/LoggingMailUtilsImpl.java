package jp.go.meti.drone.com.common.mail;

import java.util.Arrays;

import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import jp.go.meti.drone.com.common.date.SystemDate;
import lombok.extern.slf4j.Slf4j;

/**
 * ログ出力メールユーティリティ実装。 <br>
 * メールサーバのない環境等で、メール送信を行わず、デバッグ用のログ出力のみ行いたい場合に使用する。 <br>
 * 実装クラスの切り替えをMailUtilsConfigクラスにて行っている。
 */
@Slf4j
public class LoggingMailUtilsImpl extends MailUtilsImpl {
    /**
     * コンストラクタ。
     * 
     * @param freemarkerConfiguration FreeMarker設定
     * @param systemDate システム日時取得機能
     */
    public LoggingMailUtilsImpl(Configuration freemarkerConfiguration, SystemDate systemDate) {
        super(null, freemarkerConfiguration, systemDate, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void sendMail(MimeMessage mimeMessage) {
        log.info("sendMail: mimeMessage={}", mimeMessage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMail(String subject, String text, String... to) {
        log.info("sendMail: subject=[{}] text=[{}] to=[{}]", subject, text, Arrays.asList(to));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMailWithFrom(String fromAddress, String fromName, String subject, String text, String... to) {
        log.info(
            "sendMailWithFrom: fromAddress=[{}] fromName=[{}] subject=[{}] text=[{}] to=[{}]",
            fromAddress,
            fromName,
            subject,
            text,
            Arrays.asList(to));
    }
}
