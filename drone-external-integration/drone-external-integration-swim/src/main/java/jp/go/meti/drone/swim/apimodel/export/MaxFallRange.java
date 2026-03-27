package jp.go.meti.drone.swim.apimodel.export;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 航路の画定APIからのレスポンスに含まれる最大落下範囲情報
 */
@Data
public class MaxFallRange {

    /** 最大落下範囲ID */
    @JsonProperty("maxFallRangeId")
    private String maxFallRangeId;

    /** 事業者番号 */
    @JsonProperty("businessNumber")
    private String businessNumber;

    /** 航路運営者ID */
    @JsonProperty("uaslOperatorId")
    private String uaslOperatorId;

    /** 名称 */
    @JsonProperty("name")
    private String name;

    /** エリア名称 */
    @JsonProperty("areaName")
    private String areaName;

    /** 系統ID */
    @JsonProperty("typeId")
    private String typeId;

    /** 地域ID */
    @JsonProperty("regionId")
    private String regionId;

    /** 最大標高・地形 */
    @JsonProperty("elevationTerrain")
    private String elevationTerrain;

    /** ジオメトリ */
    @JsonProperty("geometry")
    private Geometry geometry;

    /** 登録日時 */
    @JsonProperty("createdAt")
    private String createdAt;

    /** 更新日時 */
    @JsonProperty("updatedAt")
    private String updatedAt;
}
