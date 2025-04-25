package jp.go.meti.drone.com.common.mqtt;

import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MQTT 送信実装クラス
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MqttPublishServiceImpl implements MqttPublishService {

    /** mqttClient */
    private final @Nullable Map<String, MqttClient> mqttClients;

    /** 送信用クライアントID */
    @Value("${mqtt.send-client-id:null}")
    private String sendClientId;
    
    /** QOS */
    @Value("${mqtt.qos:2}")
    private int qos;

    /** 接続待ち時間 */
    @Value("${mqtt.time-to-wait:0}")
    private long timeToWait;

    /** 最大リトライ回数 */
    @Value("${mqtt.retry-cnt-max:0}")
    private int retryCntMax;

    @Override
    public void publishMessage(String topic, String message) throws MqttException {

        // MQTTクライアント
        MqttClient mqttClient = mqttClients.get(sendClientId);
        
        // MQTTクライアントの接続ができていない場合
        if (mqttClient == null) {
            log.error("Send Publish Error MQTT Broker Not Started Please Restart And Connect");
            throw new MqttException(MqttException.REASON_CODE_CLIENT_NOT_CONNECTED);
        }

        // 送信回数
        int sendCnt = 0;
        do {
            sendCnt++;
            try {
                MqttMessage mqttMessage = new MqttMessage(message.getBytes());
                mqttMessage.setQos(qos);
                mqttClient.setTimeToWait(timeToWait);
                mqttClient.publish(topic, mqttMessage);
                return;
            } catch (MqttException mqttException) {
                log.error("Send Publish Error. Number of Transmissions:" + sendCnt, mqttException);
            }

        } while (retryCntMax >= sendCnt);

        // 再送回数上限まで送信を実施しても成功しなかった場合、MqttExceptionを発生
        throw new MqttException(MqttException.REASON_CODE_SERVER_CONNECT_ERROR);

    }

}
