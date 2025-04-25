package jp.go.meti.drone.dips;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

/**
 * WebClientTemplate設定.
 */
@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    /**
     * restTemplateのBean
     * @return restTemplate
     */
    @Bean
    RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        return restTemplateBuilder.build();
    }
}
