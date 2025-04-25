package jp.go.meti.drone.com.common.mqtt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * MqttClientTemplateプロパティ。
 */
@ConfigurationProperties("mqtt")
@Data
public class MqttClientTemplateProperties {
    
    /** クライアント情報 */
    private final Map<String, Client> client = new HashMap<>();

    /**
     * クライアント定義
     */
    @Data
    public static class Client {
        /** BaseURL */
        private String baseUrl;

        /** ユーザ名 */
        private String username;

        /** パスワード */
        private String password;

    }

}
