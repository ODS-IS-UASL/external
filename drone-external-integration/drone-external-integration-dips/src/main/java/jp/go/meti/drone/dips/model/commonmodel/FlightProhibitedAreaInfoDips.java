package jp.go.meti.drone.dips.model.commonmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * flightProhibitedAreaInfoDips 飛行禁止エリア情報 Dips
 */
@JsonTypeName("flightProhibitedAreaInfoDips")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-17T10:48:25.264675700+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class FlightProhibitedAreaInfoDips {

    @Valid
    private List<Integer> flightProhibitedAreaTypeId = new ArrayList<>();

    private String startTime;

    private String finishTime;

    private String updateTime;

    public FlightProhibitedAreaInfoDips() {
        super();
    }

    /**
     * Constructor with only required parameters
     */
    public FlightProhibitedAreaInfoDips(List<Integer> flightProhibitedAreaTypeId) {
        this.flightProhibitedAreaTypeId = flightProhibitedAreaTypeId;
    }

    public FlightProhibitedAreaInfoDips flightProhibitedAreaTypeId(List<Integer> flightProhibitedAreaTypeId) {
        this.flightProhibitedAreaTypeId = flightProhibitedAreaTypeId;
        return this;
    }

    public FlightProhibitedAreaInfoDips addFlightProhibitedAreaTypeIdItem(Integer flightProhibitedAreaTypeIdItem) {
        if (this.flightProhibitedAreaTypeId == null) {
            this.flightProhibitedAreaTypeId = new ArrayList<>();
        }
        this.flightProhibitedAreaTypeId.add(flightProhibitedAreaTypeIdItem);
        return this;
    }

    /**
     * 飛行禁止エリア種別
     * 
     * @return flightProhibitedAreaTypeId
     */
    @NotNull
    @Schema(name = "flightProhibitedAreaTypeId", description = "飛行禁止エリア種別", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("flightProhibitedAreaTypeId")
    public List<Integer> getFlightProhibitedAreaTypeId() {
        return flightProhibitedAreaTypeId;
    }

    public void setFlightProhibitedAreaTypeId(List<Integer> flightProhibitedAreaTypeId) {
        this.flightProhibitedAreaTypeId = flightProhibitedAreaTypeId;
    }

    public FlightProhibitedAreaInfoDips startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * 検索期間(FROM)
     * 
     * @return startTime
     */

    @Schema(name = "startTime", description = "検索期間(FROM)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("startTime")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public FlightProhibitedAreaInfoDips finishTime(String finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    /**
     * 検索期間(TO)
     * 
     * @return finishTime
     */

    @Schema(name = "finishTime", description = "検索期間(TO) ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("finishTime")
    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public FlightProhibitedAreaInfoDips updateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    /**
     * 更新時刻
     * 
     * @return updateTime
     */

    @Schema(name = "updateTime", description = "更新時刻 ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("updateTime")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FlightProhibitedAreaInfoDips flightProhibitedAreaInfoDips = (FlightProhibitedAreaInfoDips) o;
        return Objects.equals(this.flightProhibitedAreaTypeId, flightProhibitedAreaInfoDips.flightProhibitedAreaTypeId)
            && Objects.equals(this.startTime, flightProhibitedAreaInfoDips.startTime) && Objects.equals(
                this.finishTime,
                flightProhibitedAreaInfoDips.finishTime) && Objects.equals(
                    this.updateTime,
                    flightProhibitedAreaInfoDips.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightProhibitedAreaTypeId, startTime, finishTime, updateTime);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FlightProhibitedAreaInfoDips {\n");
        sb.append("    flightProhibitedAreaTypeId: ").append(toIndentedString(flightProhibitedAreaTypeId)).append("\n");
        sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
        sb.append("    finishTime: ").append(toIndentedString(finishTime)).append("\n");
        sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
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
