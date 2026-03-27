package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AirwayReserveInfo 航路予約情報
 */
@Data
@NoArgsConstructor
public class AirwayReserveInfo {

    // 親予約ID
    private String requestId;
    
	// 航路予約ID
	private String reservationId;

	// 航路ID
	private String uaslId;

    // 航路区画IDリスト
	private List<String> uaslSectionIds = new ArrayList<>();

}
