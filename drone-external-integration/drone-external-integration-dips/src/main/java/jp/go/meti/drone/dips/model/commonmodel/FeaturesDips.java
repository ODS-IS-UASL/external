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
 * 飛行禁止範囲
 */
@Schema(name = "featuresDips", description = "飛行禁止範囲")
@JsonTypeName("featuresDips")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-17T10:48:25.264675700+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class FeaturesDips {

    private String type;

    @Valid
    private List<Double> center = new ArrayList<>();

    private Double radius;

    @Valid
    private List<List<Double>> coordinates = new ArrayList<>();

    public FeaturesDips() {
        super();
    }

    /**
     * Constructor with only required parameters
     */
    public FeaturesDips(String type) {
        this.type = type;
    }

    public FeaturesDips type(String type) {
        this.type = type;
        return this;
    }

    /**
     * ジオメトリタイプ
     * 
     * @return type
     */
    @NotNull
    @Schema(name = "type", description = "ジオメトリタイプ", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FeaturesDips center(List<Double> center) {
        this.center = center;
        return this;
    }

    public FeaturesDips addCenterItem(Double centerItem) {
        if (this.center == null) {
            this.center = new ArrayList<>();
        }
        this.center.add(centerItem);
        return this;
    }

    /**
     * ジオメトリ（中心点）
     * 
     * @return center
     */
    @Valid
    @Schema(name = "center", description = "ジオメトリ（中心点）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("center")
    public List<Double> getCenter() {
        return center;
    }

    public void setCenter(List<Double> center) {
        this.center = center;
    }

    public FeaturesDips radius(Double radius) {
        this.radius = radius;
        return this;
    }

    /**
     * 半径
     * 
     * @return radius
     */
    @Valid
    @Schema(name = "radius", description = "半径", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("radius")
    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public FeaturesDips coordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public FeaturesDips addCoordinatesItem(List<Double> coordinatesItem) {
        if (this.coordinates == null) {
            this.coordinates = new ArrayList<>();
        }
        this.coordinates.add(coordinatesItem);
        return this;
    }

    /**
     * ジオメトリ（構成点）
     * 
     * @return coordinates
     */
    @Valid
    @Schema(name = "coordinates", description = "ジオメトリ（構成点） ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("coordinates")
    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FeaturesDips featuresDips = (FeaturesDips) o;
        return Objects.equals(this.type, featuresDips.type) && Objects.equals(this.center, featuresDips.center)
            && Objects.equals(this.radius, featuresDips.radius) && Objects.equals(
                this.coordinates,
                featuresDips.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, center, radius, coordinates);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FeaturesDips {\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    center: ").append(toIndentedString(center)).append("\n");
        sb.append("    radius: ").append(toIndentedString(radius)).append("\n");
        sb.append("    coordinates: ").append(toIndentedString(coordinates)).append("\n");
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
