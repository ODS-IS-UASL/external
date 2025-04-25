package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航路関係者送信情報
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantNotificationEntity {

	// 関係者ID
	private String operatorId;

	// 関係者名称
	private String operatorName;

	// 周知方法
	private String notificationType;

	// 周知先
	private String notificationTarget;

}
