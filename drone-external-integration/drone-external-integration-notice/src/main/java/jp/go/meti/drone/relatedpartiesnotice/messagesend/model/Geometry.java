package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GeoJsonにおけるgeometryオブジェクト
 */
@Data
@NoArgsConstructor
public class Geometry {

    //タイプ
    private String type;
    
    //位置情報
    private List<List<Double>> coordinates;
}
