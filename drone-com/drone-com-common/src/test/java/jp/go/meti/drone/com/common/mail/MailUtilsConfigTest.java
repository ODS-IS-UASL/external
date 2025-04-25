package jp.go.meti.drone.com.common.mail;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import freemarker.template.Configuration;
import jp.go.meti.drone.com.common.date.SystemDate;
import jp.go.meti.drone.com.common.mail.control.delivery.MailDeliveryControl;

/**
 * {@link MailUtilsConfig}のテストクラス。
 * <p>
 * MailUtilsConfigの各メソッドをテストします。
 * </p>
 */
@DisplayName("MailUtilsConfigのテストクラス")
@SpringBootTest(classes = { MailUtilsConfig.class })
class MailUtilsConfigTest {

    /**
     * テスト対象
     */

    @Autowired
    MailUtilsConfig target;

    /**
     * FreeMarker用の設定情報のMock
     */
    @MockBean
    Configuration freemarkerConfiguration;

    /**
     * システム日時のMock
     */
    @MockBean
    SystemDate systemDate;
    
    /**
     * メール送信制御用クラスのMock
     */
    @MockBean
    MailDeliveryControl deliveryControl;
    
    /**
     * mailUtilsImplメソッドのテスト
     * <p>
     * 正常系1：LoggingMailUtilsImplのBeanが生成されることを確認する。<br>
     * 設定条件1：JavaMailSenderがnull
     * </p>
     * <li>モックオブジェクト1：freemarkerConfiguration
     * <li>モックオブジェクト2:systemDate
     * <li>モックオブジェクト3:deliveryControl
     * <li>検証：LoggingMailUtilsImplのBeanが生成されること
     * </ul>
     */
    @Test
    @DisplayName("正常系1：LoggingMailUtilsImplのBeanが生成されることを確認する。")
    void testMailUtilsImpl01() throws Exception {
        // 入力値の準備
        JavaMailSender mailSender = null;

        // メソッドの呼び出し
        MailUtils result = target.mailUtilsImpl(mailSender, freemarkerConfiguration, systemDate, deliveryControl);
        
        // 結果の検証
        assertTrue(result instanceof LoggingMailUtilsImpl);
    }
}
