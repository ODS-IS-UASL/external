package jp.go.meti.drone.dips.apimodel.dips;

import java.util.Objects;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import io.swagger.v3.oas.annotations.media.Schema;
import jp.go.meti.drone.dips.model.commonmodel.FeaturesDips;
import jp.go.meti.drone.dips.model.commonmodel.FlightProhibitedAreaInfoDips;

/**
 * FlightProhibitedAreaInfoDipsRequest リクエストパラメータDIPS
 */
@JsonTypeName("  flightProhibitedAreaInfoDipsRequest")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-17T10:48:25.264675700+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class FlightProhibitedAreaInfoDipsRequest {

    private FeaturesDips features;

    private FlightProhibitedAreaInfoDips flightProhibitedAreaInfo;

    public FlightProhibitedAreaInfoDipsRequest() {
        super();
    }

    /**
     * Constructor with only required parameters
     */
    public FlightProhibitedAreaInfoDipsRequest(FeaturesDips features,
        FlightProhibitedAreaInfoDips flightProhibitedAreaInfo) {
        this.features = features;
        this.flightProhibitedAreaInfo = flightProhibitedAreaInfo;
    }

    public FlightProhibitedAreaInfoDipsRequest features(FeaturesDips features) {
        this.features = features;
        return this;
    }

    /**
     * Get features
     * 
     * @return features
     */
    @NotNull
    @Valid
    @Schema(name = "features", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("features")
    public FeaturesDips getFeatures() {
        return features;
    }

    public void setFeatures(FeaturesDips features) {
        this.features = features;
    }

    public FlightProhibitedAreaInfoDipsRequest flightProhibitedAreaInfo(
        FlightProhibitedAreaInfoDips flightProhibitedAreaInfo) {
        this.flightProhibitedAreaInfo = flightProhibitedAreaInfo;
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
    public FlightProhibitedAreaInfoDips getFlightProhibitedAreaInfo() {
        return flightProhibitedAreaInfo;
    }

    public void setFlightProhibitedAreaInfo(FlightProhibitedAreaInfoDips flightProhibitedAreaInfo) {
        this.flightProhibitedAreaInfo = flightProhibitedAreaInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FlightProhibitedAreaInfoDipsRequest flightProhibitedAreaInfoDipsRequest = (FlightProhibitedAreaInfoDipsRequest) o;
        return Objects.equals(this.features, flightProhibitedAreaInfoDipsRequest.features) && Objects.equals(
            this.flightProhibitedAreaInfo,
            flightProhibitedAreaInfoDipsRequest.flightProhibitedAreaInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(features, flightProhibitedAreaInfo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FlightProhibitedAreaInfoDipsRequest {\n");
        sb.append("    features: ").append(toIndentedString(features)).append("\n");
        sb.append("    flightProhibitedAreaInfo: ").append(toIndentedString(flightProhibitedAreaInfo)).append("\n");
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
