package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航路情報 ジャンクション内の航路情報
 */
@Data
@NoArgsConstructor
public class AirwayJunctionAirwaysInfo {

    //ジャンクション内の落下空間に描かれる航路の位置座標
    private JunctionAirway airway;
    
    //ジャンクション内の落下空間に描かれる航路逸脱領域の位置座標
    private JunctionDeviation deviation;
}
