package jp.go.meti.drone.relatedpartiesnotice.messagesend.airwayinfomodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class UaslEntity {
    private String uaslId;
    private String uaslName;
    private String flightPurpose;
    private String createdAt;
    private String updatedAt;
    private List<Double> droneList;
    private List<UaslPointEntity> uaslPoints;
    private List<UaslSectionsEntity> uaslSections;
}
