package jp.go.meti.drone.swim.apimodel.export.pip;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザー属性レスポンス
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAttrResponse {

    /** ユーザーID */
    @JsonProperty("user_id")
    private String userId;

    /** ユーザーログインID */
    @JsonProperty("user_login_id")
    private String loginId;

    /** 事業者名 */
    @JsonProperty("operator_name")
    private String operatorName;

    /** 属性情報 */
    @JsonProperty("attribute")
    private Attribute attribute;

    /**
     * 属性情報
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Attribute {

        /** 事業者ID */
        @JsonProperty("operatorId")
        private String operatorId;

        /** ロール */
        @JsonProperty("roles")
        private List<Role> roles;

        /** DIPSアカウントID */
        @JsonProperty("dipsAccountId")
        private String dipsAccountId;

        /** DIPSアカウント名 */
        @JsonProperty("dipsAccountName")
        private String dipsAccountName;

        /** 電話番号 */
        @JsonProperty("phone")
        private String phone;

        /** 更新日時 */
        @JsonProperty("updateDatetime")
        private String updateDatetime;

        /** SWIM連携用事業者ID(3桁) */
        @JsonProperty("swimOperatorId")
        private String swimOperatorId;
    }

    /**
     * ロール情報
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Role {

        /** ロールID */
        @JsonProperty("roleId")
        private String roleId;

        /** ロール名 */
        @JsonProperty("roleName")
        private String roleName;
    }
}
