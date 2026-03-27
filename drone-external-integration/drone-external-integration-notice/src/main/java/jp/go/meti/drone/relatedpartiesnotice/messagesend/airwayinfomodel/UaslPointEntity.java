package jp.go.meti.drone.relatedpartiesnotice.messagesend.airwayinfomodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class UaslPointEntity {
    private String uaslPointId;
    private String uaslPointName;
    private GeometryEntity geometry;
    private GeometryEntity deviationGeometry;
}
