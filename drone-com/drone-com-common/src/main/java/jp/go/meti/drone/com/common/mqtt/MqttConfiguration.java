package jp.go.meti.drone.com.common.mqtt;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.go.meti.drone.com.common.mqtt.MqttClientTemplateProperties.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MQTT共通設定クラス MqttConfiguration
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MqttClientTemplateProperties.class)
@Slf4j
public class MqttConfiguration {

    /** MqttClientTemplateプロパティ */
    private final MqttClientTemplateProperties mqttProps;

    /** ApplicationContext */
    private final ApplicationContext applicationContext;

    /** SSL設定 */
    @Value("${mqtt.ssl:true}")
    private boolean sslFlg;

    /** CleanSession設定 */
    @Value("${mqtt.clean-session:false}")
    private boolean cleanSessionFlg;

    /** 再接続設定 */
    @Value("${mqtt.reconnect:true}")
    private boolean reconnectFlg;

    /** 接続タイムアウト */
    @Value("${mqtt.connect-timeout:10}")
    private int connectTimeout;

    /** トラストストアパスワード */
    @Value("${mqtt.truststore.password:}") // NOSONAR 空文字設定による警告を回避
    private String tsPassWord;

    /** トラストストアファイルパス */
    @Value("${mqtt.truststore.filepath:}") // NOSONAR 空文字設定による警告を回避
    private String tsPath;

    /**
     * MQTTクライアント作成
     * 
     * @return mqttClient MQTTクライアント
     * @throws MqttException MQTT例外
     */
    @Bean
    @ConditionalOnProperty(name = "mqtt.send-client-id", matchIfMissing = false)
    Map<String, MqttClient> mqttClient() throws MqttException {

        // プロパティからクライアント情報取得
        Map<String, Client> propClientMap = mqttProps.getClient();

        // Bean登録用
        Map<String, MqttClient> mqttClientMap = new HashMap<>();

        for (Map.Entry<String, Client> entry : propClientMap.entrySet()) {

            // オブジェクト生成
            MqttClient mqttClient = MqttClientFactory.getMqttClient(entry.getValue().getBaseUrl(), entry.getKey());

            // オプション
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(entry.getValue().getUsername());
            options.setPassword(entry.getValue().getPassword().toCharArray());
            options.setCleanSession(cleanSessionFlg);
            options.setAutomaticReconnect(reconnectFlg);
            options.setConnectionTimeout(connectTimeout);

            try {
                // SSL設定
                if (sslFlg) {
                    options.setSocketFactory(MqttSSLSocketFactory.getSSLSocketFactory(tsPassWord, tsPath));
                }

                // MQTT 接続
                mqttClient.connect(options);
                log.info(
                    "MQTT Connect .broker-url: {} .client-id: {} .username: {} ",
                    entry.getValue().getBaseUrl(),
                    entry.getKey(),
                    entry.getValue().getUsername());
            } catch (MqttException mqttException) {
                // ブローカーに接続できなくてもアプリケーションは起動する
                log.error(
                    "MQTT Connect Error .broker-url: {} .client-id: {} .username: {} ",
                    entry.getValue().getBaseUrl(),
                    entry.getKey(),
                    entry.getValue().getUsername());
                log.error("MQTT Connect Error Exception", mqttException);
                continue;
            }

            // subscribe 設定
            try {
                MqttSubscriberHandler handler = applicationContext.getBean(entry.getKey(), MqttSubscriberHandler.class);
                handler.subscribe(mqttClient);
                log.info("MQTT Subscriber Set Completion .client-id: {}", entry.getKey());
            } catch (BeansException beanException) {
                // subscribe設定がない場合
                log.info("MQTT Subscriber No Set .client-id: {}", entry.getKey());
            }

            mqttClientMap.put(entry.getKey(), mqttClient);
        }

        return mqttClientMap;
    }

}
