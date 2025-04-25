package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * テーブル：予約情報航路関連情報 RESERVATION_AIRWAY_ASSOCIATION
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationAirwayAssociationEntity {

	// 航路ID
	private String airwayReserveId;

	// 航路区画ID
	private String airwaySectionId;
	
	// 作成日時
	private LocalDateTime startAt;
	
	// 作成日時
	private LocalDateTime endAt;

	// 作成者ID
	private Integer creationId;

	// 作成日時
	private LocalDateTime creationDatetime;

	// 更新者ID
	private Integer updateId;

	// 更新日時
	private LocalDateTime updateDatetime;

}
