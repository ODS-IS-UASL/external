package jp.go.meti.drone.relatedpartiesnotice.messagesend.userinfomodel.dto;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.userinfomodel.TokenClientOutputModel;
import lombok.Data;

/**
 * アクセストークン情報取得結果DTO。
 */
@Data
public class UserAccessTokenGetResult {
    /**
     * HTTPステータスコード
     */
    private int statusCode;
    
    /**
     * アクセストークン情報
     */
    private TokenClientOutputModel tokenClientOutputModel;

}
