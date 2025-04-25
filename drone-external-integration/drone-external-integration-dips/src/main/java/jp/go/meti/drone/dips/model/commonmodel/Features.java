package jp.go.meti.drone.dips.model.commonmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Generated;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 検索範囲 ジオメトリ（構成点）
 */
@Schema(name = "features", description = "検索範囲 ")
@JsonTypeName("features")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-10T16:19:13.697153400+09:00[Asia/Tokyo]", comments = "Generator version: 7.8.0")
public class Features {

    @Valid
    private List<List<Double>> coordinates = new ArrayList<>();

    public Features() {
        super();
    }

    /**
     * Constructor with only required parameters
     */
    public Features(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

    public Features coordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public Features addCoordinatesItem(List<Double> coordinatesItem) {
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
    @Schema(name = "coordinates", description = "ジオメトリ（構成点） ", requiredMode = Schema.RequiredMode.REQUIRED)
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
        Features searchModelApiFeatures = (Features) o;
        return Objects.equals(this.coordinates, searchModelApiFeatures.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Features {\n");
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
