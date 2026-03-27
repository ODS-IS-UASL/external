package jp.go.meti.drone.swim.apimodel.export;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * ジオメトリ
 */
@Data
public class Geometry {

    /** type */
    @JsonProperty("type")
    private String type;

    /** coordinates */
    @JsonProperty("coordinates")
    private List<List<List<Double>>> coordinates;
}
