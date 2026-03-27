package jp.go.meti.drone.swim.apimodel.export.pip;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザー属性リスト
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAttrResponseList {

    /** 属性リスト */
    @JsonProperty("attributeList")
    private List<UserAttrResponse> attributeList;
}
