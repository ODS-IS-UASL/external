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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * メール送信 サービス
 * <p>
 * 受信した航路予約情報を周知先に送信
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
	 * 航路予約情報メール送信
	 * <p>
	 * 航路予約情報を基にメール送信メッセージを編集する<br>
	 * 関係者へメール送信を行う。
	 * <p>
	 * 
	 * @param targetList 航路予約情報
	 * @param requestId 親予約ID
	 * @return 返答
	 */
	public boolean sendAirwayReservationMailMessage(List<AirwayReservationInfoEntity> targetList, String requestId) {
		String mailMessage = "";
		boolean isSent = true;
		for (AirwayReservationInfoEntity airwayReservationInfo : targetList) {
			String messageType = "";
			try {
				// メッセージテンプレート
				String templateFileName = "emailTemplateB.ftl";
				// メール名
				String subject = MessageUtils.getContextMessage("DR000I007");
				// 送信内容編集
				Map<String, Object> model = new HashMap<>();
				model.put("relatedParties", airwayReservationInfo.getOperatorName());
				model.put("airwayReserveUser", airwayReservationInfo.getReservationOperatorId());
				model.put("requestId", requestId);
				model.put("airwayReserveIds", airwayReservationInfo.getAirwayReserveId());
				model.put("airwayNames", airwayReservationInfo.getAirwayName());
				model.put("flightPurpose", airwayReservationInfo.getFlightPurpose());
				model.put("updatedAt", getFormattedJST(airwayReservationInfo.getUpdatedAt()));
				
				if ("1".equals(airwayReservationInfo.getStatus())) {
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
				mailUtils.sendMail(subject, mailMessage, airwayReservationInfo.getNotificationEmail());
				log.info("sendAirWayReservationMailMessage:"
						+ MessageUtils.getMessage("DR000I009", airwayReservationInfo.getNotificationEmail()));

				// 送信履歴更新
				saveMailSentInfo(airwayReservationInfo, mailMessage, "00", "", messageType);
			} catch (Exception ex) {
				isSent = false;
				log.error("sendAirwayMailMessage:" + MessageUtils.getMessage("DR000E002"), ex);
				// 送信履歴更新
				saveMailSentInfo(airwayReservationInfo, mailMessage, "01", ex.getMessage(), messageType);
			}
		}
		return isSent;
	}

	/**
	 * 送信履歴更新を行う。<br>
	 * 
	 * @param airwayReservationInfo      関係者情報
	 * @param mailMessage                送信内容
	 * @param sentResult                 送信結果
	 * @param failReason                 失敗理由
	 * @param messageType                メッセージタイプ
	 */
	private void saveMailSentInfo(AirwayReservationInfoEntity airwayReservationInfo, String mailMessage,
			String sentResult, String failedReason, String messageType) {
		try {
			// 送信履歴更新
			MailSentInfoEntity mailSendInfo = new MailSentInfoEntity();
			mailSendInfo.setOperatorId(airwayReservationInfo.getRelatedOperatorId());
			mailSendInfo.setNotificationTarget(airwayReservationInfo.getNotificationEmail());
			mailSendInfo.setNotificationType("1");
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
