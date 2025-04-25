package jp.go.meti.drone.com.common.web;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.util.unit.DataSize;

import lombok.Data;

/**
 * WebClientTemplateプロパティ。
 */
@ConfigurationProperties("drone.common.web-client")
@Data
public class WebClientTemplateProperties {
    /** クライアント情報 */
    private final Map<String, Client> client = new HashMap<>();

    /**
     * クライアント定義
     */
    @Data
    public static class Client {
        /** BaseURL */
        private String baseUrl = "http://localhost:18080";

        /** Content-Type */
        private MediaType contentType = MediaType.APPLICATION_JSON;

        /** Form用 Content-Type */
        private MediaType contentTypeForm = MediaType.APPLICATION_FORM_URLENCODED;

        /** Acceptヘッダ情報 */
        private List<MediaType> accept = new ArrayList<>();

        /** 最大インメモリサイズ */
        private DataSize maxInMemorySize = DataSize.ofMegabytes(3);

        /** タイムアウト値 */
        private Duration timeout = Duration.ofSeconds(5);

        /** リトライ回数 */
        private long retryMaxAttempts = 0;

        /** リトライ間隔 */
        private Duration retryFixedDelay = Duration.ofSeconds(1);

        /** ユーザ名 */
        private String username;

        /** パスワード */
        private String password;

        /** プロキシホスト */
        private String proxyHost;

        /** プロキシポート */
        private int proxyPort = 8080;

        /** プロキシ対象外ホスト一覧 */
        private String nonProxyHosts = "localhost";

        /** プロキシユーザ名 */
        private String proxyUsername;

        /** プロキシパスワード */
        private String proxyPassword;
    }

}
