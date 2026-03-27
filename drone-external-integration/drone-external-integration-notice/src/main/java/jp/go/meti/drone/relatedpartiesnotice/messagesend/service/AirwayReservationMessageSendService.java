package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayReservationInfoMapper;
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
	private MailSendService mailSendService;
	
	/**
	 * 航路予約情報に対するメール送信処理
	 * @param requestId 親予約ID
	 * @throws Exception 例外
	 */
	public void messageSend(String requestId) throws Exception {
		// 親予約IDをもとにメール周知対象の情報を取得する。
	    List<AirwayReservationInfoEntity> airwayReservationInfoList = new ArrayList<>();
	    airwayReservationInfoList = airwayReservationInfoMapper.selectReservationDetail(requestId);
	    
		//メール周知対象が取得できない場合は処理終了する。
		if (airwayReservationInfoList.isEmpty()) {
			log.info("messageSend:" + MessageUtils.getMessage("DR000I011"));
			return;
		}
		
		List<AirwayReservationInfoEntity> mailSendList = new ArrayList<>();
		for (AirwayReservationInfoEntity airwayReservationInfo : airwayReservationInfoList) {
		    if ("無し".equals(airwayReservationInfo.getNotificationEmail())) {
		        log.info("messageSend:" + MessageUtils.getMessage("DR000I015"));
                continue;
		    }
			mailSendList.add(airwayReservationInfo);
		}
		
		if(mailSendList.isEmpty()) {
		    log.error("予約情報に紐づく連絡先情報が取得できませんでした。");
		    return;
		}

		//連絡先情報リストの重複を削除する。
		List<AirwayReservationInfoEntity> targetList = distinctTarget(mailSendList);

		// 予約情報に紐づいた航路登録関係者へメール送信
        mailSendService.sendAirwayReservationMailMessage(targetList, requestId);
	}
	
	/**
	 * 連絡先情報の重複を削除する。
	 */
	private List<AirwayReservationInfoEntity> distinctTarget(List<AirwayReservationInfoEntity> targetList) {
        Map<String,AirwayReservationInfoEntity> map = new HashMap<>();
        for(AirwayReservationInfoEntity target : targetList) {
            map.put(target.getNotificationEmail(), target);
        }
	    return new ArrayList<>(map.values());
	}

}
