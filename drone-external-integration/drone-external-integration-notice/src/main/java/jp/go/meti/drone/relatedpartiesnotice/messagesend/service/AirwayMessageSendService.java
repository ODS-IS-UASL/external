package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.TenantNotificationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayInfoMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 航路情報更新メール送信サービスクラス
 *
 * @version 1.0 2024/11/11
 */
@Slf4j
@Service
public class AirwayMessageSendService {
	@Autowired
	private AirwayInfoMapper airwayInfoMapper;

	@Autowired
	private AirwayTenantNotificationService airwayTenantNotificationService;

	@Autowired
	private MailSendService mailSendService;

	/**
	 * 航路情報のメール送信処理
	 * @param airwayId 航路ID
	 * @throws Exception
	 */
	public void messageSend(String airwayId) throws Exception {
	    // 航路更新情報取得
	    AirwayInfoEntity airwayInfo = airwayInfoMapper.searchAirwayInfo(airwayId);
	    if (airwayInfo == null) {
	        log.info("messageSend:" + MessageUtils.getMessage("DR000I004"));
	        return;
	    }
	    // 関係者情報取得
	    List<TenantNotificationEntity> relatedpartiesNotificationList = airwayTenantNotificationService
	        .getTenantNotification(airwayInfo);
	    if (relatedpartiesNotificationList.isEmpty()) {
	        log.info("messageSend:" + MessageUtils.getMessage("DR000I005"));
	        return;
	    }

	    // 航路登録関係者へメール送信
	    mailSendService.sendAirwayMailMessage(airwayInfo, relatedpartiesNotificationList);

	}
}
