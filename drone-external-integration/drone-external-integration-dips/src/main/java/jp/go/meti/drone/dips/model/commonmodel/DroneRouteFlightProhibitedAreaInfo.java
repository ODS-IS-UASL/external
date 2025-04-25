package jp.go.meti.drone.dips.model.commonmodel;

import java.util.Objects;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DroneRouteFlightProhibitedAreaInfo
 */

@JsonTypeName("droneRoute.FlightProhibitedAreaInfo")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-18T17:13:11.936428400+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class DroneRouteFlightProhibitedAreaInfo {

    private String flightProhibitedAreaId;

    private String name;

    private DroneRouteRange range;

    private String detail;

    private String url;

    private Integer flightProhibitedAreaTypeId;

    private String startTime;

    private String finishTime;

    public DroneRouteFlightProhibitedAreaInfo() {
        super();
    }

    /**
     * Constructor with only required parameters
     */
    public DroneRouteFlightProhibitedAreaInfo(String flightProhibitedAreaId, DroneRouteRange range,
        Integer flightProhibitedAreaTypeId) {
        this.flightProhibitedAreaId = flightProhibitedAreaId;
        this.range = range;
        this.flightProhibitedAreaTypeId = flightProhibitedAreaTypeId;
    }

    public DroneRouteFlightProhibitedAreaInfo flightProhibitedAreaId(String flightProhibitedAreaId) {
        this.flightProhibitedAreaId = flightProhibitedAreaId;
        return this;
    }

    /**
     * 飛行禁止エリアID
     * 
     * @return flightProhibitedAreaId
     */
    @NotNull
    @Size(max = 64)
    @Schema(name = "flightProhibitedAreaId", example = "20221105_FISSikou0015", description = "飛行禁止エリアID", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("flightProhibitedAreaId")
    public String getFlightProhibitedAreaId() {
        return flightProhibitedAreaId;
    }

    public void setFlightProhibitedAreaId(String flightProhibitedAreaId) {
        this.flightProhibitedAreaId = flightProhibitedAreaId;
    }

    public DroneRouteFlightProhibitedAreaInfo name(String name) {
        this.name = name;
        return this;
    }

    /**
     * 飛行禁止エリア名
     * 
     * @return name
     */
    @Size(max = 128)
    @Schema(name = "name", example = "東京国際空港 空港の区域", description = "飛行禁止エリア名", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DroneRouteFlightProhibitedAreaInfo range(DroneRouteRange range) {
        this.range = range;
        return this;
    }

    /**
     * Get range
     * 
     * @return range
     */
    @NotNull
    @Valid
    @Schema(name = "range", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("range")
    public DroneRouteRange getRange() {
        return range;
    }

    public void setRange(DroneRouteRange range) {
        this.range = range;
    }

    public DroneRouteFlightProhibitedAreaInfo detail(String detail) {
        this.detail = detail;
        return this;
    }

    /**
     * 説明詳細
     * 
     * @return detail
     */

    @Schema(name = "detail", example = "小型無人機等飛行禁止法に基づく飛行禁止空域", description = "説明詳細", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("detail")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public DroneRouteFlightProhibitedAreaInfo url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 説明URL
     * 
     * @return url
     */
    @Size(max = 255)
    @Schema(name = "url", example = "https://www.mlit.go.jp/koku/koku_tk2_000023.html", description = "説明URL", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DroneRouteFlightProhibitedAreaInfo flightProhibitedAreaTypeId(Integer flightProhibitedAreaTypeId) {
        this.flightProhibitedAreaTypeId = flightProhibitedAreaTypeId;
        return this;
    }

    /**
     * 飛行禁止エリア種別
     * 
     * @return flightProhibitedAreaTypeId
     */
    @NotNull
    @Schema(name = "flightProhibitedAreaTypeId", example = "5", description = "飛行禁止エリア種別", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("flightProhibitedAreaTypeId")
    public Integer getFlightProhibitedAreaTypeId() {
        return flightProhibitedAreaTypeId;
    }

    public void setFlightProhibitedAreaTypeId(Integer flightProhibitedAreaTypeId) {
        this.flightProhibitedAreaTypeId = flightProhibitedAreaTypeId;
    }

    public DroneRouteFlightProhibitedAreaInfo startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * 有効期限(FROM) <yyyyMMdd HHmm形式>
     * 
     * @return startTime
     */
    @Size(max = 13)
    @Schema(name = "startTime", example = "20210101 0000", description = "有効期限(FROM) <yyyyMMdd HHmm形式>", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("startTime")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public DroneRouteFlightProhibitedAreaInfo finishTime(String finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    /**
     * 有効期限(TO) <yyyyMMdd HHmm形式>
     * 
     * @return finishTime
     */
    @Size(max = 13)
    @Schema(name = "finishTime", example = "20210101 2359", description = "有効期限(TO) <yyyyMMdd HHmm形式>", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("finishTime")
    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DroneRouteFlightProhibitedAreaInfo droneRouteFlightProhibitedAreaInfo = (DroneRouteFlightProhibitedAreaInfo) o;
        return Objects.equals(this.flightProhibitedAreaId, droneRouteFlightProhibitedAreaInfo.flightProhibitedAreaId)
            && Objects.equals(this.name, droneRouteFlightProhibitedAreaInfo.name) && Objects.equals(
                this.range,
                droneRouteFlightProhibitedAreaInfo.range) && Objects.equals(
                    this.detail,
                    droneRouteFlightProhibitedAreaInfo.detail) && Objects.equals(
                        this.url,
                        droneRouteFlightProhibitedAreaInfo.url) && Objects.equals(
                            this.flightProhibitedAreaTypeId,
                            droneRouteFlightProhibitedAreaInfo.flightProhibitedAreaTypeId) && Objects.equals(
                                this.startTime,
                                droneRouteFlightProhibitedAreaInfo.startTime) && Objects.equals(
                                    this.finishTime,
                                    droneRouteFlightProhibitedAreaInfo.finishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            flightProhibitedAreaId,
            name,
            range,
            detail,
            url,
            flightProhibitedAreaTypeId,
            startTime,
            finishTime);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DroneRouteFlightProhibitedAreaInfo {\n");
        sb.append("    flightProhibitedAreaId: ").append(toIndentedString(flightProhibitedAreaId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    range: ").append(toIndentedString(range)).append("\n");
        sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
        sb.append("    url: ").append(toIndentedString(url)).append("\n");
        sb.append("    flightProhibitedAreaTypeId: ").append(toIndentedString(flightProhibitedAreaTypeId)).append("\n");
        sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
        sb.append("    finishTime: ").append(toIndentedString(finishTime)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
