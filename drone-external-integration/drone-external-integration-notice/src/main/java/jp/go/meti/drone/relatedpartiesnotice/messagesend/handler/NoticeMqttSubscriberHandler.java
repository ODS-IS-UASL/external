package jp.go.meti.drone.relatedpartiesnotice.messagesend.handler;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jp.go.meti.drone.com.common.mqtt.MqttSubscriberHandler;
import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.service.PayloadProcessService;
import lombok.extern.slf4j.Slf4j;

/**
 * MQTT 受信実装クラス
 */
@Component
@Slf4j
public class NoticeMqttSubscriberHandler implements MqttSubscriberHandler {

	private final PayloadProcessService payloadProcessService;
	
	//航路情報のMQTTトピック
	@Value("${mqtt.topic.airway}")
	private String airwayTopic;
	//予約情報のMQTTトピック
    @Value("${mqtt.topic.airway-reservation}")
    private String reservationTopic;

	public NoticeMqttSubscriberHandler(PayloadProcessService payloadProcessService) {
        this.payloadProcessService = payloadProcessService;
    }
	
	@Override
	public void subscribe(MqttClient mqttClient) throws MqttException {
	 // 航路情報subscribe設定(QOS1)
        mqttClient.subscribe(airwayTopic, 1, (topic, message) -> {
            try {
                payloadProcessService.subscribeFirst(topic, new String(message.getPayload()));
                
            } catch (Exception ex) {
                String errorMessage = MessageUtils.getMessage("DRC01E004");
                log.error("airway:" + errorMessage, ex);
            }
        });

        // 予約情報subscribe設定(QOS1)
        mqttClient.subscribe(reservationTopic, 1, (topic, message) -> {
            try {
                payloadProcessService.subscribeSecond(topic, new String(message.getPayload()));
            } catch (Exception ex) {
                String errorMessage = MessageUtils.getMessage("DRC01E004");
                log.error("airwayReservation:" + errorMessage, ex);
            }
        });
	}
}
