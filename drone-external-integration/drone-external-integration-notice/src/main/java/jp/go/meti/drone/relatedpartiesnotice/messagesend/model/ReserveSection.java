package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AirwaySectionAllocateTime 航路区画毎の確保時間
 */
@Builder
@Data
@NoArgsConstructor
public class ReserveSection {

	// 航路区画ID
	private String airwaySectionId;

	// 予約開始日時
	private String startAt;
	
	// 予約終了日時
	private String endAt;

	/**
	 * @param airwaySectionId
	 * @param startAt
	 * @param endAt
	 */
	public ReserveSection(String airwaySectionId, String startAt, String endAt) {
		super();
		this.airwaySectionId = airwaySectionId;
		this.startAt = startAt;
		this.endAt = endAt;
	}

}
