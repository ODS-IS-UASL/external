package jp.go.meti.drone.com.common.web;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.util.UriComponentsBuilder;

import io.netty.channel.ChannelOption;
import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.TimeoutException;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;
import reactor.util.retry.Retry;

/**
 * WebClientTemplate の FactoryBean。
 */
@Slf4j
public class WebClientTemplateFactory {

    /**
     * BASE-URL用送信前オペレータ生成。
     * 
     * @param baseUrl BASE-URL
     * @param addrSupplier アドレスサプライヤ
     * @return 生成した送信前オペレータ
     */
    public static UnaryOperator<WebClient> createBeforeSendOperatorForBaseUrl(String baseUrl,
        Supplier<String> addrSupplier) {
        var uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        return client -> {
            // ホスト名とポート番号取得
            var addr = addrSupplier.get();
            var hostPort = addr.split(":");
            var host = hostPort[0];
            var port = (hostPort.length > 1) ? hostPort[1] : null;

            // 新しいBaseURL構築
            var builder = uriBuilder.cloneBuilder().host(host);
            if (port != null) {
                builder.port(port);
            }
            var newBaseUrl = builder.toUriString();

            // BaseURL更新
            return client.mutate().baseUrl(newBaseUrl).build();
        };
    }

    /**
     * WebClientTemplate生成。
     * 
     * @param props クライアントプロパティ
     * @param clientId クライアントID
     * @param beforeSendOperator 送信前オペレータ
     * @return 生成した WebClientTemplate
     */
    public WebClientTemplate createWebClientTemplate(WebClientTemplateProperties props, String clientId,
        UnaryOperator<WebClient> beforeSendOperator) {
        return builder() //
            .props(props)
            .clientId(clientId)
            .beforeSendOperator(beforeSendOperator)
            .build();
    }

    /**
     * WebClientTemplate生成。
     * 
     * @param props クライアントプロパティ
     * @param clientId クライアントID
     * @param hostSupplier ホスト名サプライヤ
     * @return 生成した WebClientTemplate
     */
    public WebClientTemplate createWebClientTemplate(WebClientTemplateProperties props, String clientId,
        Supplier<String> hostSupplier) {
        return builder() //
            .props(props)
            .clientId(clientId)
            .hostSupplier(hostSupplier)
            .build();
    }

    /**
     * WebClientTemplate生成。
     * 
     * @param client クライアント情報
     * @param beforeSendOperator 送信前オペレータ
     * @return 生成した WebClientTemplate
     */
    public WebClientTemplate createWebClientTemplate(WebClientTemplateProperties.Client client,
        UnaryOperator<WebClient> beforeSendOperator) {
        return new WebClientTemplate(
            createWebClient(client), client.getContentTypeForm(), createRetrySpec(client), beforeSendOperator);
    }

    /**
     * WebClientTemplate生成。
     * 
     * @param props クライアントプロパティ
     * @param clientId クライアントID
     * @return 生成した WebClientTemplate
     */
    public WebClientTemplate createWebClientTemplate(WebClientTemplateProperties props, String clientId) {
        return createWebClientTemplate(props, clientId, UnaryOperator.identity());
    }

    /**
     * WebClientTemplate生成。
     * 
     * @param client クライアント情報
     * @return 生成した WebClientTemplate
     */
    public WebClientTemplate createWebClientTemplate(WebClientTemplateProperties.Client client) {
        return createWebClientTemplate(client, UnaryOperator.identity());
    }

    /**
     * WebClient生成。
     * 
     * @param client クライアント情報
     * @return 生成した WebClient
     */
    private static WebClient createWebClient(WebClientTemplateProperties.Client client) {
        return WebClient.builder()
            .baseUrl(client.getBaseUrl())
            .clientConnector(new ReactorClientHttpConnector(createHttpClient(client)))
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize((int) client.getMaxInMemorySize().toBytes()))
            .defaultHeaders(headers -> {
                // ContentType（必須）
                headers.setContentType(client.getContentType());

                // BasicAuth（任意）
                if (StringUtils.hasLength(client.getUsername())) {
                    headers.setBasicAuth(client.getUsername(), client.getPassword());
                }

                // Accept（任意）
                if (!client.getAccept().isEmpty()) {
                    headers.setAccept(client.getAccept());
                }
            })
            .build();
    }

    /**
     * HttpClient生成。
     * 
     * @param client クライアント情報
     * @return 生成した HttpClient
     */
    private static HttpClient createHttpClient(WebClientTemplateProperties.Client client) {
        var timeout = client.getTimeout();
        var httpClient = HttpClient.newConnection()
            .resolver(DefaultAddressResolverGroup.INSTANCE)
            .wiretap("reactor.netty.http.client.HttpClient", LogLevel.INFO, AdvancedByteBufFormat.SIMPLE)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) timeout.toMillis())
            .doOnConnected(
                connection -> connection.addHandlerLast(
                    new ReadTimeoutHandler(timeout.toMillis(), TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(timeout.toMillis(), TimeUnit.MILLISECONDS)))
            .responseTimeout(timeout);

        // プロキシ設定
        if (StringUtils.hasLength(client.getProxyHost())) {
            httpClient = httpClient.proxy(spec -> {
                // ホスト名、ポート番号設定
                var builder = spec.type(ProxyProvider.Proxy.HTTP)
                    .host(client.getProxyHost())
                    .port(client.getProxyPort())
                    .nonProxyHosts(client.getNonProxyHosts());

                // プロキシ認証設定
                var username = client.getProxyUsername();
                if (StringUtils.hasLength(username)) {
                    builder.username(username) //
                        .password(u -> client.getProxyPassword());
                }
            });
        }

        return httpClient;
    }

    /**
     * Retry生成。
     * 
     * @param client クライアント情報
     * @return 生成した Retry
     */
    private static Retry createRetrySpec(WebClientTemplateProperties.Client client) {
        return Retry.fixedDelay(client.getRetryMaxAttempts(), client.getRetryFixedDelay()).filter(throwable -> {
            if (throwable instanceof WebClientRequestException) {
                var cause = throwable.getCause();
                // 接続監視タイムアウトや応答監視タイムアウトなどタイムアウト時の例外でリトライ対象
                return cause instanceof ConnectTimeoutException || cause instanceof TimeoutException;
            }
            return false;
        });
    }

    /**
     * ビルダー生成。
     * 
     * @return 生成したビルダー
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * ビルダー。
     */
    @Data
    @Accessors(fluent = true)
    public static class Builder {
        /** プロパティ */
        private WebClientTemplateProperties props;

        /** クライアントID */
        private String clientId;

        /** clientプロパティ */
        private WebClientTemplateProperties.Client clientProps;

        /** 送信前オペレータ */
        private UnaryOperator<WebClient> beforeSendOperator = UnaryOperator.identity();

        /** ホスト名サプライヤ */
        private Supplier<String> hostSupplier;

        /** WebClient生成後処理 */
        private UnaryOperator<WebClient> postProcessor;

        /**
         * 構築。
         * 
         * @return 生成した WebClientTemplate
         */
        public WebClientTemplate build() {
            if (clientProps == null) {
                clientProps = props.getClient().get(clientId);
                if (clientProps == null) {
                    log.warn("No clientId. id={}", clientId);
                    return null;
                }
            }

            if (hostSupplier != null) {
                beforeSendOperator = createBeforeSendOperatorForBaseUrl(clientProps.getBaseUrl(), hostSupplier);
            }

            var webClient = createWebClient(clientProps);
            var retrySpec = createRetrySpec(clientProps);

            if (postProcessor != null) {
                webClient = postProcessor.apply(webClient);
            }

            return new WebClientTemplate(webClient, clientProps.getContentTypeForm(), retrySpec, beforeSendOperator);
        }
    }
}
