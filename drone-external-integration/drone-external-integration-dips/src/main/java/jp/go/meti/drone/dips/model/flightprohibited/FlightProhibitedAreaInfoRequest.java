package jp.go.meti.drone.dips.model.flightprohibited;

import java.util.Objects;

import javax.annotation.Generated;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import io.swagger.v3.oas.annotations.media.Schema;
import jp.go.meti.drone.dips.model.commonmodel.Features;

/**
 * flightProhibitedAreaInfoRequest APIリクエストパラメータ
 */
@JsonTypeName("  flightProhibitedAreaInfoRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-17T10:28:08.455766100+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class FlightProhibitedAreaInfoRequest {

    private Features features;

    private String startTime;

    private String finishTime;

    public FlightProhibitedAreaInfoRequest() {
        super();
    }

    /**
     * Constructor with only required parameters
     */
    public FlightProhibitedAreaInfoRequest(Features features) {
        this.features = features;
    }

    public FlightProhibitedAreaInfoRequest features(Features features) {
        this.features = features;
        return this;
    }

    /**
     * Get features
     * 
     * @return features
     */
    @Valid
    @Schema(name = "features", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("features")
    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    public FlightProhibitedAreaInfoRequest startTime(String startTime) {
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

    public FlightProhibitedAreaInfoRequest finishTime(String finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    /**
     * 検索期間(TO)
     * 
     * @return finishTime
     */

    @Schema(name = "finishTime", description = "検索期間(TO)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
        FlightProhibitedAreaInfoRequest flightProhibitedAreaInfoRequest = (FlightProhibitedAreaInfoRequest) o;
        return Objects.equals(this.features, flightProhibitedAreaInfoRequest.features) && Objects.equals(
            this.startTime,
            flightProhibitedAreaInfoRequest.startTime) && Objects.equals(
                this.finishTime,
                flightProhibitedAreaInfoRequest.finishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(features, startTime, finishTime);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FlightProhibitedAreaInfoRequest {\n");
        sb.append("    features: ").append(toIndentedString(features)).append("\n");
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
