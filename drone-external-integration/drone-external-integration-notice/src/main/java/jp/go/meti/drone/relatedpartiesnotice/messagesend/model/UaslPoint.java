package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航路情報 航路点情報
 */
@Data
@NoArgsConstructor
public class UaslPoint {

    //航路点ID
    private String uaslPointId;
    
    //航路点名
    private String uaslPointName;
    
    //航路点ジオメトリー
    private UaslPointGeometry geometry;
    
    //航路逸脱領域ジオメトリー
    private UaslPointGeometry deviationGeometry;

}
