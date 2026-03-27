package jp.go.meti.drone.swim.apimodel.export.pip;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ユーザー属性取得リクエスト
 */
@Data
public class GetUserAttrRequest {

    /** from */
    @JsonProperty("from")
    private String from;

    /** 航路IDリスト */
    @JsonProperty("userIdList")
    private List<String> userIdList;

    /** ログインIDリスト */
    @JsonProperty("loginIdList")
    private List<String> loginIdList;
}
