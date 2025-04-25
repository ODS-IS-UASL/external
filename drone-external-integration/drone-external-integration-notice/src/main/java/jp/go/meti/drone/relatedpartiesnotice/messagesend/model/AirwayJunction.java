package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航路情報 航路点情報
 */
@Data
@NoArgsConstructor
public class AirwayJunction {

    //航路点ID
    private String airwayJunctionId;
    
    //航路点名
    private String airwayJunctionName;
    
	// ジャンクション内の落下空間に描かれる航路/航路逸脱領域の位置座標
	private List<AirwayJunctionAirwaysInfo> airways;

}
