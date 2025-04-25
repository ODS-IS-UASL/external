package jp.go.meti.drone.com.common.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import jp.go.meti.drone.com.common.date.SystemDate;
import jp.go.meti.drone.com.common.mail.control.delivery.MailDeliveryControl;

/**
 * メールユーティリティ設定。
 */
@Configuration(proxyBeanMethods = false)
public class MailUtilsConfig {
    /**
     * メールユーティリティ Bean生成。 <br>
     * Spring BootのJavaMailSenderを利用する場合、 プロパティ「spring.mail.host」が設定されている必要がある。<br>
     * 設定されていない場合、JavaMailSenderのBeanが生成されないため、 <br>
     * LoggingMailUtilsImplにてBeanを生成する
     *
     * @param mailSender JavaMailSender
     * @param freemarkerConfiguration FreeMarker設定
     * @param systemDate システム日時取得機能
     * @return 生成したBean
     */
    @Bean
    public MailUtils mailUtilsImpl(@Autowired(required = false) JavaMailSender mailSender,
        freemarker.template.Configuration freemarkerConfiguration, SystemDate systemDate,
        MailDeliveryControl deliveryControl) {
        // JavaMailSender の Beanの有無でメールユーティリティの実装クラスを切り替える
        return (mailSender != null) ? new MailUtilsImpl(
            mailSender, freemarkerConfiguration, systemDate, deliveryControl) : new LoggingMailUtilsImpl(
                freemarkerConfiguration, systemDate);
    }
}
