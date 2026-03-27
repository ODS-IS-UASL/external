package jp.go.meti.drone.relatedpartiesnotice.messagesend.userinfomodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * JSON マッピング用クラス
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenClientOutputModel {
    // 実行結果の種類を識別するURI
    private String type;
    
    // 実行結果メッセージ
    private String title;
    
    // HTTPステータスコード
    private String status;
    
    // 基盤運営事業者向け調査情報
    private String detail;
    
    // APIの実行結果のデータオブジェクト
    private TokenData data;
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TokenData {
        // アクセストークン
        @JsonProperty("access_token")
        private String accessToken;
        
        // アクセストークンの有効期限（秒単位）
        @JsonProperty("expires_in")
        private Integer expiresIn;
        
        // アクセストークンのタイプ
        @JsonProperty("token_type")
        private String tokenType;
        
        // アクセストークンの適用開始時刻（秒単位）
        @JsonProperty("not_before_policy")
        private Integer notBeforePolicy;
        
        // クライアントスコープ
        @JsonProperty("scope")
        private String scope;
    }
}
