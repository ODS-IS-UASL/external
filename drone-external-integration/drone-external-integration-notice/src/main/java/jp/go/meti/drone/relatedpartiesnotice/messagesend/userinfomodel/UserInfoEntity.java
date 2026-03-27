package jp.go.meti.drone.relatedpartiesnotice.messagesend.userinfomodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * JSON マッピング用クラス
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoEntity {
    private List<AttributeItem> attributeList;

    @Data
    public static class AttributeItem {
        @JsonProperty("user_id")
        private String userId;

        @JsonProperty("user_login_id")
        private String userLoginId;

        @JsonProperty("operator_name")
        private String operatorName;

        private Attribute attribute;
    }

    @Data
    public static class Attribute {
        @JsonProperty("operatorId")
        private String operatorId;

        @JsonProperty("roles")
        private List<Role> roles;

        @JsonProperty("dipsAccountId")
        private String dipsAccountId;

        @JsonProperty("dipsAccountName")
        private String dipsAccountName;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("swimOperatorId")
        private String swimOperatorId;

        @JsonProperty("updateDatetime")
        private String updateDatetime;
    }

    @Data
    public static class Role {
        @JsonProperty("roleId")
        private String roleId;

        @JsonProperty("roleName")
        private String roleName;
    }
}
