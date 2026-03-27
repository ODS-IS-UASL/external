package jp.go.meti.drone.relatedpartiesnotice.messagesend.airwayinfomodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class UaslInfoEntity {
    private String uaslAdministratorId;
    private String businessNumber;
    private UaslEntity uasl;
}
