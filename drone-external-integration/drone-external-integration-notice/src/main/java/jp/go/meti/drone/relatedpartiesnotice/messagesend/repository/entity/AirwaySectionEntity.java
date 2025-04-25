package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * テーブル：航路区画情報 AIRWAY_SECTION
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirwaySectionEntity {

	// 航路ID
	private String airwayId;

	// 航路区画ID
	private String airwaySectionId;

	// 航路区画名
	private String airwaySectionName;

	// 作成者ID
	private Integer creationId;

	// 作成日時
	private LocalDateTime creationDatetime;

	// 更新者ID
	private Integer updateId;

	// 更新日時
	private LocalDateTime updateDatetime;

}
