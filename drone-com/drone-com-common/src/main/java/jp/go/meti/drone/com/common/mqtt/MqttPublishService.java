package jp.go.meti.drone.com.common.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * MQTT 送信インタフェース。
 * 
 * @version $Revision$
 */
public interface MqttPublishService {

    /**
     * メッセージを送信する
     * 
     * @param topic トピック
     * @param message メッセージ
     * @throws MqttException MQTT例外
     */
    void publishMessage(String topic, String message) throws MqttException;

}
