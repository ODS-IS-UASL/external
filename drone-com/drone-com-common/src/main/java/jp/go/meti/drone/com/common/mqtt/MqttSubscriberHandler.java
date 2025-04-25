package jp.go.meti.drone.com.common.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * MQTT SubscriberHandlerインターフェース
 */
public interface MqttSubscriberHandler {

    /**
     * Subscriber受信ハンドラー
     * 
     * @param mqttClient MQTTクライアント
     * @throws MqttException MQTT例外
     */
    void subscribe(MqttClient mqttClient) throws MqttException;

}
