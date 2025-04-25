package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * テーブル：航路情報 AIRWAY
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirwayEntity {

	// 航路ID
	private String airwayId;

	// 航路名称
	private String airwayName;
	
	// 航路運営者ID
	private String airwayAdministratorId;
	
	// 処理区分
	private String status;
	
	// 登録日時
	private LocalDateTime registeredAt;

	// 更新日時
	private LocalDateTime updatedAt;
	
	// airway作成日時
	private LocalDateTime airwayCreatedAt;

	// airway更新日時
	private LocalDateTime airwayUpdatedAt;
	
	// 処理区分
	private String flightPurpose;

	// 作成者ID
	private Integer creationId;

	// 作成日時
	private LocalDateTime creationDatetime;

	// 更新者ID
	private Integer updateId;

	// 更新日時
	private LocalDateTime updateDatetime;

}
