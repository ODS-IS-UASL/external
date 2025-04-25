package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航路情報
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirwayInfoEntity {

	// 航路ID
	private String airwayId;

	// 航路名称
	private String airwayName;

	// 処理区分
	private String status;

	// 航路登録者氏名
	private String airwayAdministratorId;
	
	// 飛行目的
	private String flightPurpose;
	
	// 更新日
	private String updatedAt;

}
