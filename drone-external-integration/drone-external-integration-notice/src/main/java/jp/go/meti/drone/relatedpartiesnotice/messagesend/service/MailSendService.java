package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.com.common.mail.MailUtils;
import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.MailSentInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.TenantNotificationEntity;
import jp.go.meti.drone.relatedpartiesnotice.tenant.repository.entity.TenantEntity;
import jp.go.meti.drone.relatedpartiesnotice.tenant.repository.mapper.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * メール送信 サービス
 * <p>
 * 受信した航路登録情報、航路予約情報を周知先に送信
 * </p>
 * 
 * @version 1.0 2024/11/28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailSendService {

	/** メール送信用のユーティリティ **/
	private final MailUtils mailUtils;

	@Autowired
	private MailSentInfoService mailSentInfoService;

	@Autowired
	private TenantRepository tenantRepository;

	// ユーザID
	@Value("${systemuser}")
	private int userId;
	
	//ログイン画面URL
	@Value("${notice.mail.login.link}")
	private String loginLink;
	
	//問い合わせ先担当者
	@Value("${notice.mail.contanct.person}")
	private String contactPerson;
	
	///問い合わせ先所属
	@Value("${notice.mail.contanct.affiliation}")
	private String contactAffiliation;
	
	//問い合わせ先電話番号
	@Value("${notice.mail.contanct.phonenumber}")
	private String contactPhoneNumber;
	
	//問い合わせ先メールアドレス
	@Value("${notice.mail.contanct.mailaddress}")
	private String contactMailAddress;

	/**
	 * 航路登録情報メール送信
	 * <p>
	 * 航路登録情報を基にメール送信メッセージを編集する<br>
	 * 関係者へメール送信を行う。
	 * <p>
	 * 
	 * @param airwayInfo                     航路情報
	 * @param tenantNotificationList         関係者情報
	 * @return 返答
	 */
	public boolean sendAirwayMailMessage(AirwayInfoEntity airwayInfo,
			List<TenantNotificationEntity> tenantNotificationList) {
		String mailMessage = "";
		boolean isSent = true;
		for (TenantNotificationEntity tenantNotification : tenantNotificationList) {
			String messageType = "";
			try {
				// メッセージテンプレート
				String templateFileName = "emailTemplateA.ftl";
				// メール名
				String subject = "";
				String div = "";
				// 送信内容編集
				Map<String, Object> model = new HashMap<>();
				if ("1".equals(airwayInfo.getStatus())) {
					subject = MessageUtils.getContextMessage("DR000I001");
					messageType = "10";
					div = "更新";
				} else if ("2".equals(airwayInfo.getStatus())) {
					subject = MessageUtils.getContextMessage("DR000I002");
					messageType = "11";
					div = "削除";
				} else {
					log.error("処理区分パターン外。");
					return false;
				}
				model.put("div", div);
				model.put("relatedParties", tenantNotification.getOperatorName());
				model.put("airwayId", airwayInfo.getAirwayId());
				model.put("airwayName", airwayInfo.getAirwayName());
				model.put("flightPurpose", airwayInfo.getFlightPurpose());
				model.put("updatedAt", getFormattedJST(airwayInfo.getUpdatedAt()));
				//共通情報を設定
				setCommonMailInfo(model);

				// メール送信メソッドの呼び出し
				mailMessage = mailUtils.getTextFromTemplate(templateFileName, model);
				mailUtils.sendMail(subject, mailMessage, tenantNotification.getNotificationTarget());
				log.info("sendAirwayMailMessage:"
						+ MessageUtils.getMessage("DR000I008", tenantNotification.getNotificationTarget()));

				// 送信履歴更新
				saveMailSentInfo(tenantNotification, mailMessage, "00", "", messageType);
			} catch (Exception ex) {
				isSent = false;
				log.error("sendAirwayMailMessage:" + MessageUtils.getMessage("DR000E002"), ex);
				// 送信履歴更新
				saveMailSentInfo(tenantNotification, mailMessage, "01", ex.getMessage(), messageType);
			}
		}
		return isSent;
	}

	/**
	 * 航路予約情報メール送信
	 * <p>
	 * 航路予約情報を基にメール送信メッセージを編集する<br>
	 * 関係者へメール送信を行う。
	 * <p>
	 * 
	 * @param airwayReservationInfo          航路予約情報
	 * @param tenantNotificationList         関係者情報
	 * @return 返答
	 */
	public boolean sendAirwayReservationMailMessage(AirwayReservationInfoEntity airwayReservationInfo,
			List<TenantNotificationEntity> tenantNotificationList,List<String> airwayNames) {
		String mailMessage = "";
		boolean isSent = true;
		for (TenantNotificationEntity tenantNotification : tenantNotificationList) {
			String messageType = "";
			try {
				// メッセージテンプレート
				String templateFileName = "emailTemplateB.ftl";
				// メール名
				String subject = MessageUtils.getContextMessage("DR000I007");
				// 送信内容編集
				Map<String, Object> model = new HashMap<>();
				model.put("relatedParties", tenantNotification.getOperatorName());
				model.put("airwayReserveUser", getOperatorName(airwayReservationInfo.getOperatorId()));
				model.put("airwayReserveId", airwayReservationInfo.getAirwayReserveId());
				model.put("airwayNames", airwayNames.stream().collect(Collectors.joining(",")));
				model.put("updatedAt", getFormattedJST(airwayReservationInfo.getUpdatedAt()));
				if ("1".equals(airwayReservationInfo.getStatus()) || "4".equals(airwayReservationInfo.getStatus())) {
					model.put("div", "更新");
					messageType = "20";
				} else if ("2".equals(airwayReservationInfo.getStatus())) {
					model.put("div", "キャンセル");
					messageType = "21";
				} else {
					model.put("div", "キャンセル");
					messageType = "22";
				}
				//共通情報を設定
				setCommonMailInfo(model);
				
				// メール送信メソッドの呼び出し
				mailMessage = mailUtils.getTextFromTemplate(templateFileName, model);
				mailUtils.sendMail(subject, mailMessage, tenantNotification.getNotificationTarget());
				log.info("sendAirWayReservationMailMessage:"
						+ MessageUtils.getMessage("DR000I009", tenantNotification.getNotificationTarget()));

				// 送信履歴更新
				saveMailSentInfo(tenantNotification, mailMessage, "00", "", messageType);
			} catch (Exception ex) {
				isSent = false;
				log.error("sendAirwayMailMessage:" + MessageUtils.getMessage("DR000E002"), ex);
				// 送信履歴更新
				saveMailSentInfo(tenantNotification, mailMessage, "01", ex.getMessage(), messageType);
			}
		}
		return isSent;
	}

	/**
	 * 送信履歴更新を行う。<br>
	 * 
	 * @param tenantNotification         関係者情報
	 * @param mailMessage                送信内容
	 * @param sentResult                 送信結果
	 * @param failReason                 失敗理由
	 * @param messageType                メッセージタイプ
	 */
	private void saveMailSentInfo(TenantNotificationEntity tenantNotification, String mailMessage,
			String sentResult, String failedReason, String messageType) {
		try {
			// 送信履歴更新
			MailSentInfoEntity mailSendInfo = new MailSentInfoEntity();
			mailSendInfo.setOperatorId(tenantNotification.getOperatorId());
			mailSendInfo.setNotificationTarget(tenantNotification.getNotificationTarget());
			mailSendInfo.setNotificationType(tenantNotification.getNotificationType());
			mailSendInfo.setMessageType(messageType);
			mailSendInfo.setMailDetail(mailMessage);
			mailSendInfo.setSentResult(sentResult);
			mailSendInfo.setFailedReason(failedReason);
			mailSendInfo.setCreationId(userId);
			mailSendInfo.setUpdateId(userId);
			mailSentInfoService.saveMailSentInfo(mailSendInfo);
		} catch (Exception ex) {
			log.error("sendAirwayMailMessage:" + MessageUtils.getMessage("DR000E002"));
		}
	}

	/**
	 * 事業者名を取得
	 * <p>
	 * 事業者IDより事業者名を取得
	 * </p>
	 * 
	 * @param operatorId 事業者ID
	 * @return 事業者名
	 */
	private String getOperatorName(String operatorId) {
		String operatorName = "-";
		try {
		    // 事業者情報取得
		    List<TenantEntity> tenantEntities = tenantRepository.selectByPrimaryKey(operatorId);
		    if (!tenantEntities.isEmpty()) {
		    	operatorName = tenantEntities.get(0).getOperatorName();
		    }
		} catch (Exception e) {
			// ＤＢアクセスエラーが発生しました。
		    String message = MessageUtils.getMessage("DR000E001");
		    log.error(message,e);
		}
		return operatorName;
	}
	
	/**
	 * メールの共通情報に対する埋め込み文字列を設定する。
	 * @param model マップ
	 */
	private void setCommonMailInfo(Map<String, Object> model) {
		model.put("loginLink", loginLink);
		model.put("contactPerson", contactPerson);
		model.put("contactAffiliation", contactAffiliation);
		model.put("contactPhoneNumber", contactPhoneNumber);
		model.put("contactMailAddress", contactMailAddress);
	}

	private String getFormattedJST(String updatedAt) {
        // UTCの日時を指定
        String utcDateTime = updatedAt;
        
        // フォーマッタを使用して文字列をパース
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime utcDateTimeLocal = LocalDateTime.parse(utcDateTime, formatter);
        
        // UTCのローカル日時をZonedDateTimeに変換
        ZonedDateTime utcZonedDateTime = ZonedDateTime.of(utcDateTimeLocal, ZoneOffset.UTC);
        
        // UTCからJST（日本標準時）に変換
        ZonedDateTime jstDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
        
        // 日時（yyyy/MM/dd HH:mm:ss）で表示
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String formattedJST = jstDateTime.format(outputFormatter);
        
		return formattedJST;
	}
}
