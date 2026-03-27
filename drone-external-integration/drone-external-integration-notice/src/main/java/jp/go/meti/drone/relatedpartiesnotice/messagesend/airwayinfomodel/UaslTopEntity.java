package jp.go.meti.drone.relatedpartiesnotice.messagesend.airwayinfomodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航路
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class UaslTopEntity {
    private List<UaslInfoEntity> uasl;
}
