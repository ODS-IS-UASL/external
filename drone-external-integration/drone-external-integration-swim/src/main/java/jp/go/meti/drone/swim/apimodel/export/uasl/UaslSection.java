package jp.go.meti.drone.swim.apimodel.export.uasl;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航路区画情報のレスポンスDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UaslSection {

    /** 航路区画ID */
    private String uaslSectionId;

    /** 航路区画名 */
    private String uaslSectionName;

    /** 航路区画に接している航路点ID */
    private List<String> uaslPointIds;

    /** 航路区画に紐づいているドローンポートID */
    private List<String> droneportIds;
}
