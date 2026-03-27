package jp.go.meti.drone.swim.apimodel.export.pip;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * クライアントシステム認証API レスポンス
 */
@Data
public class TokenClientResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("title")
    private String title;

    @JsonProperty("status")
    private String status;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("data")
    private TokenClientResponseData data;
}
