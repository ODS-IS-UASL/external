package jp.go.meti.drone.dips.apimodel.dips;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import io.swagger.v3.oas.annotations.media.Schema;
import jp.go.meti.drone.dips.model.commonmodel.DroneRouteFlightProhibitedAreaInfo;

/**
 * DroneRouteFlightProhibitedAreaResponse
 */

@JsonTypeName("droneRoute.FlightProhibitedAreaResponse")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-18T17:13:11.936428400+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class DroneRouteFlightProhibitedAreaResponse {

    @Valid
    private List<DroneRouteFlightProhibitedAreaInfo> flightProhibitedAreaInfo = new ArrayList<>();

    private Integer totalCount;

    public DroneRouteFlightProhibitedAreaResponse() {
        super();
    }

    /**
     * Constructor with only required parameters
     */
    public DroneRouteFlightProhibitedAreaResponse(List<DroneRouteFlightProhibitedAreaInfo> flightProhibitedAreaInfo,
        Integer totalCount) {
        this.flightProhibitedAreaInfo = flightProhibitedAreaInfo;
        this.totalCount = totalCount;
    }

    public DroneRouteFlightProhibitedAreaResponse flightProhibitedAreaInfo(
        List<DroneRouteFlightProhibitedAreaInfo> flightProhibitedAreaInfo) {
        this.flightProhibitedAreaInfo = flightProhibitedAreaInfo;
        return this;
    }

    public DroneRouteFlightProhibitedAreaResponse addFlightProhibitedAreaInfoItem(
        DroneRouteFlightProhibitedAreaInfo flightProhibitedAreaInfoItem) {
        if (this.flightProhibitedAreaInfo == null) {
            this.flightProhibitedAreaInfo = new ArrayList<>();
        }
        this.flightProhibitedAreaInfo.add(flightProhibitedAreaInfoItem);
        return this;
    }

    /**
     * Get flightProhibitedAreaInfo
     * 
     * @return flightProhibitedAreaInfo
     */
    @NotNull
    @Valid
    @Schema(name = "flightProhibitedAreaInfo", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("flightProhibitedAreaInfo")
    public List<DroneRouteFlightProhibitedAreaInfo> getFlightProhibitedAreaInfo() {
        return flightProhibitedAreaInfo;
    }

    public void setFlightProhibitedAreaInfo(List<DroneRouteFlightProhibitedAreaInfo> flightProhibitedAreaInfo) {
        this.flightProhibitedAreaInfo = flightProhibitedAreaInfo;
    }

    public DroneRouteFlightProhibitedAreaResponse totalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    /**
     * 取得した総件数
     * 
     * @return totalCount
     */
    @NotNull
    @Schema(name = "totalCount", example = "2", description = "取得した総件数", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("totalCount")
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DroneRouteFlightProhibitedAreaResponse droneRouteFlightProhibitedAreaResponse = (DroneRouteFlightProhibitedAreaResponse) o;
        return Objects.equals(
            this.flightProhibitedAreaInfo,
            droneRouteFlightProhibitedAreaResponse.flightProhibitedAreaInfo) && Objects.equals(
                this.totalCount,
                droneRouteFlightProhibitedAreaResponse.totalCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightProhibitedAreaInfo, totalCount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DroneRouteFlightProhibitedAreaResponse {\n");
        sb.append("    flightProhibitedAreaInfo: ").append(toIndentedString(flightProhibitedAreaInfo)).append("\n");
        sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
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
