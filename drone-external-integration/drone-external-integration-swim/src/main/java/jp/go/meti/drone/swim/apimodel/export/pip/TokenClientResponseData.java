package jp.go.meti.drone.swim.apimodel.export.pip;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TokenClientResponseData {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("not_before_policy")
    private int notBeforePolicy;

    @JsonProperty("scope")
    private String scope;

}
