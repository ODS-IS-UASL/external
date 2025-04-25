package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航路予約情報取得
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirwayReservationInfoEntity {

	// 航路予約ID
	private String airwayReserveId;

	// 航路予約者
	private String operatorId;

	// 処理区分
	private String status;

	// 予約開始日時
	private String reservedAt;

	// 予約終了日時
	private String updatedAt;

	// 航路ID
	private String airwayId;

	// 航路名
	private String airwayName;
}
