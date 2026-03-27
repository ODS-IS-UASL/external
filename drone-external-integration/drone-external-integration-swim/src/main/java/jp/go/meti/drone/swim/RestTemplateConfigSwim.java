package jp.go.meti.drone.swim;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateの設定
 */
@Configuration
public class RestTemplateConfigSwim {

    /** 接続時間タイムアウト */
    @Value("${external.api.connect_timeout}")
    private Long connectTimeout;

    /** 読取時間タイムアウト */
    @Value("${external.api.read_timeout}")
    private Long readTimeout;

    /**
     * プロジェクト全体で使用可能なRestTemplateを作成
     * 
     * @return RestTemplate
     */
    @Bean(name = "restTemplateSwim")
    RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(connectTimeout)) // 接続が確立するまでの期限
            .setReadTimeout(Duration.ofSeconds(readTimeout)) // データの応答が返ってくるまでの期限
            .build();
    }
}
