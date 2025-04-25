package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AirwayReserveInfo 航路予約情報
 */
@Builder
@Data
@NoArgsConstructor
public class AirwayReserveInfo {

	// 航路予約ID
	private String airwayReservationId;

	// 運航事業者(予約者)ID
	private String operatorId;

	// PublishイベントID
	private String eventId;

	// 予約状態
	private String status;

    // 航路区画毎の確保時間
    private List<ReserveSection> airwaySections;
    
	// 予約完了日時
	private String reservedAt;

	// 予約状態更新日時
	private String updatedAt;

	/**
	 * @param airwayReservationId
	 * @param operatorId
	 * @param eventId
	 * @param status
	 * @param airwaySections
	 * @param reservedAt
	 * @param updatedAt
	 */
	public AirwayReserveInfo(String airwayReservationId, String operatorId, String eventId,
			String status, List<ReserveSection> airwaySections, String reservedAt,
			String updatedAt) {
		super();
		this.airwayReservationId = airwayReservationId;
		this.operatorId = operatorId;
		this.eventId = eventId;
		this.status = status;
		this.airwaySections = airwaySections;
		this.reservedAt = reservedAt;
		this.updatedAt = updatedAt;
	}

}
