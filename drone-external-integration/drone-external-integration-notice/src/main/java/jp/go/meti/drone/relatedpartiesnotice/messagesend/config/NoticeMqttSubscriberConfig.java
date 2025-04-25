package jp.go.meti.drone.relatedpartiesnotice.messagesend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.go.meti.drone.com.common.mqtt.MqttSubscriberHandler;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.handler.NoticeMqttSubscriberHandler;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.service.PayloadProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MQTT 受信Configクラス
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class NoticeMqttSubscriberConfig {

    @Bean
    MqttSubscriberHandler noticeMqttClient(PayloadProcessService payloadProcessService) {
        return new NoticeMqttSubscriberHandler(payloadProcessService);
    }

}
