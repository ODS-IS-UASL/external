package jp.go.meti.drone.com.common.mail.control.delivery;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * メール送信制御用のプロパティクラス。
 * <p>
 * メール送信制御のための設定値を保持する機能を提供します。
 * </p>
 *
 * @version $Revision$
 */
@ConfigurationProperties(prefix = "mail.delivery-control")
@Data
public class MailDeliveryControlProperties {
    /**
     * メール送信対象から除外するメールアドレス群の定義
     */
    private List<String> excludeAddresses = new ArrayList<>();

    /**
     * メール送信の制御を行わず全送信先にメールを送信するメールタイトルの取得キー軍の定義
     */
    private List<String> deliveryAllSubjectKeys = new ArrayList<>();
}
