package jp.go.meti.drone.api.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jp.go.meti.drone.com.common.web.WebClientTemplate;
import jp.go.meti.drone.com.common.web.WebClientTemplateFactory;
import jp.go.meti.drone.com.common.web.WebClientTemplateProperties;
import lombok.RequiredArgsConstructor;

/**
 * WebClientTemplate設定.
 */
@Configuration
@EnableConfigurationProperties(WebClientTemplateProperties.class)
@RequiredArgsConstructor
public class WebClientTemplateConfig {

    /** WebClientTemplateプロパティ */
    private final WebClientTemplateProperties clientProps;

    /**
     * DIPS外部API Bean生成。
     * 
     * @return 生成したBean
     */
    @Bean
    WebClientTemplate dipsWebClientTemplate() {
        return new WebClientTemplateFactory()   //
            .createWebClientTemplate(clientProps, "dipsWebClientTemplate");
    }

    /**
     * DIPSトークン Bean生成。
     * <p>
     * リフレッシュ実行時のテンプレート
     * </p>
     * 
     * @return 生成したBean
     */
    @Bean
    WebClientTemplate dipsTokenTemplate() {
        return new WebClientTemplateFactory()   //
            .createWebClientTemplate(clientProps, "dipsTokenTemplate");
    }
}
