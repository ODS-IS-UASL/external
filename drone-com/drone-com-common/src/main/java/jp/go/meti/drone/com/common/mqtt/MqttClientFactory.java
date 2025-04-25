package jp.go.meti.drone.com.common.mqtt;

import java.util.Arrays;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import jp.go.meti.drone.com.common.util.DateUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * MQTT クライアント作成クラス
 */
@Slf4j
public class MqttClientFactory {

    /**
     * MqttClient生成
     * 
     * @param brokerUrl 接続先
     * @param clientId クライアントID
     * @return MqttClient MQTTクライアント
     * @throws MqttException MQTT例外
     */
    public static MqttClient getMqttClient(String brokerUrl, String clientId) throws MqttException {

        String brokerClientId = clientId + "-" + DateUtils.currentDateTimeToStr("yyyyMMddHHmmss");
        log.info("Broker Client Id: " + brokerClientId);
        MqttClient mqttClient = new MqttClient(brokerUrl, brokerClientId);
        setCallback(mqttClient);
        return mqttClient;

    }

    /**
     * MQTTコールバック
     * 
     * @param mqttClient MQTTクライアント
     */
    private static void setCallback(MqttClient mqttClient) {
        mqttClient.setCallback(new MqttCallback() {

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                // subscribeしていないトピックの受信
                log.warn("Message Arrived Topic: " + topic + "Message: " + message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                log.info("Delivery Complete Token Topics: " + Arrays.toString(token.getTopics()));
            }

            @Override
            public void connectionLost(Throwable cause) {
                log.error("MQTT Connect Lost Please Restart And Connect");
            }
        });
    }
}
