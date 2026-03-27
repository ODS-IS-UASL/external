package jp.go.meti.drone.relatedpartiesnotice.messagesend.airwayinfomodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class GeometryEntity {
    private String type;
    private List<List<List<Double>>> coordinates;
}
