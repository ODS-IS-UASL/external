package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ジャンクション内の航路逸脱領域
 */
@Data
@NoArgsConstructor
public class JunctionDeviation {

    //航路逸脱領域のジオメトリ
    private Geometry geometry;
}
