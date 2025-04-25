package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.TenantNotificationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayReservationInfoMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * 航路情報更新メール送信サービスクラス
 *
 * @version 1.0 2024/11/11
 */
@Slf4j
@Service
public class AirwayReservationMessageSendService {
	@Autowired
	private AirwayReservationInfoMapper airwayReservationInfoMapper;

	@Autowired
	private AirwayReservationTenantNotificationService airwayReservationTenantNotificationService;

	@Autowired
	private MailSendService mailSendService;
	
	//予約ステータス：撤回
	private final static String rescinded = "3";


	/**
	 * 航路予約情報に対するメール送信処理
	 * @param airwayReserveId 予約ID
	 * @param revervationStatus 予約更新状態
	 * @param operatorId 予約者ID
	 * @throws Exception 例外
	 */
	public void messageSend(String airwayReserveId, String revervationStatus, String operatorId) throws Exception {
		// 予約IDをもとにメール周知対象の情報を取得する。
		List<AirwayReservationInfoEntity> airwayReservationInfoList = airwayReservationInfoMapper
				.searchAirwayReservationInfo(airwayReserveId);
		//メール周知対象が取得できない場合は処理終了する。
		if (airwayReservationInfoList.isEmpty()) {
			log.info("messageSend:" + MessageUtils.getMessage("DR000I010"));
			return;
		}
		
		List<TenantNotificationEntity> mailSendList = new ArrayList<>();

		for (AirwayReservationInfoEntity airwayReservationInfo : airwayReservationInfoList) {
			// 関係者情報取得
			List<TenantNotificationEntity> relatedpartiesNotificationList = airwayReservationTenantNotificationService
					.getTenantNotification(airwayReservationInfo);
			if (relatedpartiesNotificationList.isEmpty()) {
				log.info("messageSend:" + MessageUtils.getMessage("DR000I011"));
				continue;
			}
			mailSendList.addAll(relatedpartiesNotificationList);
		}
		//予約撤回の場合、予約者情報を取得する。
		if(revervationStatus.equals(rescinded)) {
		    AirwayReservationEntity rescindedOperator = new AirwayReservationEntity();
		    rescindedOperator.setOperatorId(operatorId);
		    List<TenantNotificationEntity> rescindedNotificationList = airwayReservationTenantNotificationService
                .findRescindedNotification(rescindedOperator);
		    if(rescindedNotificationList.isEmpty()) {
		        log.error("予約者の連絡先情報が取得できませんでした。");
		    }
		    else {
		        //メール周知先リストに予約者を追加する。
		        mailSendList.addAll(rescindedNotificationList);
		    }
		}
		
		if(mailSendList.isEmpty()) {
		    log.error("予約情報に紐づく連絡先情報が取得できませんでした。");
		    return;
		}
		//連絡先情報リストの重複を削除する。
		List<TenantNotificationEntity> targetList = distinctTarget(mailSendList);
		//予約区画の航路名を取得
		List<String> airwayNames = airwayReservationInfoList.stream()
				.map(AirwayReservationInfoEntity :: getAirwayName)
				.distinct()
				.collect(Collectors.toList());
		// 予約情報に紐づいた航路登録関係者へメール送信
        mailSendService.sendAirwayReservationMailMessage(airwayReservationInfoList.get(0), targetList, airwayNames);
	}
	
	/**
	 * 連絡先情報の重複を削除する。
	 */
	private List<TenantNotificationEntity> distinctTarget(List<TenantNotificationEntity> targetList) {
        Map<String,TenantNotificationEntity> map = new HashMap<>();
        for(TenantNotificationEntity target : targetList) {
            map.put(target.getNotificationTarget(), target);
        }
	    return new ArrayList<>(map.values());
	}

}
