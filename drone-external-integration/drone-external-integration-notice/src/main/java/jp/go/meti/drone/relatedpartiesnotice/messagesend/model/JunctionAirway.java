package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ジャンクション内の航路情報
 */
@Data
@NoArgsConstructor
public class JunctionAirway {

    //ジャンクション内航路のジオメトリ
    private Geometry geometry;
}
