package jp.go.meti.drone.swim.apimodel.export.pip;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * クライアントシステム認証API リクエスト
 */
@Data
public class TokenClientRequest {

    /** クライアントID */
    @JsonProperty("client_id")
    private String clientId;

    /** クライアントシークレット */
    @JsonProperty("client_secret")
    private String clientSecret;
}
