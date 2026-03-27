package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * テーブル：航路予約情報 AIRWAY_RESERVATION
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirwayReservationEntity {

	// 親予約ID
	private String requestId;
	
	// 航路予約ID
	private String airwayReserveId;

	// 運航事業者(予約者)ID
	private String operatorId;

	// PublishイベントID
	private String eventId;
	
	// 飛行目的
	private String flightPurpose;

	// 処理区分
	private String status;

	// 予約完了日時
	private LocalDateTime reservedAt;
	
	// 航路ID
	private String uaslId;

	// 予約状態更新日時
	private LocalDateTime updatedAt;

	// 作成者ID
	private Integer creationId;

	// 作成日時
	private LocalDateTime creationDatetime;

	// 更新者ID
	private Integer updateId;

	// 更新日時
	private LocalDateTime updateDatetime;

}
