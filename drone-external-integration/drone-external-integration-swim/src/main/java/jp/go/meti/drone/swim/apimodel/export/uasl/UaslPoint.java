package jp.go.meti.drone.swim.apimodel.export.uasl;

import jp.go.meti.drone.swim.apimodel.export.Geometry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航路点のレスポンスDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UaslPoint {

    /** 航路点ID */
    private String uaslPointId;

    /** 航路点名 */
    private String uaslPointName;

    /** 航路点ジオメトリ（緯度,経度,高度4点で表現された矩形） */
    private Geometry geometry;

    /** 逸脱範囲ジオメトリ（航路点を内包する数m外側の矩形） */
    private Geometry deviationGeometry;

    /** 外部保証フラグ */
    private boolean externalGuarantee;

    /** 外部システム情報 */
    private ExternalSystemInfo externalSystemInfo;
}
