package jp.go.meti.drone.relatedpartiesnotice.messagesend.userinfomodel.dto;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.userinfomodel.UserInfoEntity;
import lombok.Data;

/**
 * ユーザ情報取得結果DTO。
 */
@Data
public class UserManagementGetResult {
    /**
     * HTTPステータスコード
     */
    private int statusCode;
    
    /**
     * ユーザ情報
     */
    private UserInfoEntity userInfoEntity;
}
