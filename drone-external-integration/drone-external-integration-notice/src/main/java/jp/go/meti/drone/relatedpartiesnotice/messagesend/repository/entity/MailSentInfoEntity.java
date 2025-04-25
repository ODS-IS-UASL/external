package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * テーブル：メール周知履歴 MAIL_SENT_INFO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailSentInfoEntity {

	// 関係者ID
	private String operatorId;
	
	// 周知種別
	private String messageType;

	// 周知先
	private String notificationTarget;

	// 周知方法
	private String notificationType;

	// 通信内容
	private String mailDetail;

	// 通信結果
	private String sentResult;

	// 失敗理由
	private String failedReason;

	// 送信日時
	private LocalDateTime sentDatetime;

	// 作成者ID
	private Integer creationId;

	// 作成日時
	private LocalDateTime creationDatetime;

	// 更新者ID
	private Integer updateId;

	// 更新日時
	private LocalDateTime updateDatetime;

}
