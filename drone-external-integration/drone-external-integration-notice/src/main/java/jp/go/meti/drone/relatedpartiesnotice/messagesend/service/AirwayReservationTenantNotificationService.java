package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.TenantNotificationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayTenantNotificationMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 航路予約時、送信情報を取得するサービス
 */
@Slf4j
@Service
public class AirwayReservationTenantNotificationService {
	@Autowired
	private AirwayTenantNotificationMapper airwayTenantNotificationMapper;

	/**
	 * 送信先情報検索
	 * 
	 * @param airwayReservationInfo 予約情報
	 * @return List<TenantNotificationEntity>
	 * @throws Exception 例外
	 */
	public List<TenantNotificationEntity> getTenantNotification(AirwayReservationInfoEntity airwayReservationInfo)
			throws Exception {
		try {
		    return airwayTenantNotificationMapper.searchAirwayTenantNotification(airwayReservationInfo.getAirwayId());
		} catch (DataAccessException e) {
			// ＤＢアクセスエラーが発生しました。メッセージとして出力される
			String message = MessageUtils.getMessage("DR000E001");
			log.error(message,e);
			throw e;
		} catch (Exception e) {
			// データ取得失敗時
			String message = MessageUtils.getMessage("DRC01E001");
			log.error(message,e);
			throw e;
		}
	}

	
	/**
	 * 撤回送信先情報検索
	 * 
	 * @param airwayReservationEntity
	 * @return List<TenantNotificationEntity>
	 * @throws Exception
	 */
	public List<TenantNotificationEntity> findRescindedNotification(AirwayReservationEntity airwayReservationEntity)
			throws Exception {
		List<String> operatorIds = new ArrayList<>();
		operatorIds.add(airwayReservationEntity.getOperatorId());
		List<TenantNotificationEntity> tenantNotificationList = new ArrayList<>();
		try {
			tenantNotificationList = airwayTenantNotificationMapper
					.findAirwayTenantNotification(operatorIds);
		} catch (DataAccessException e) {
			// ＤＢアクセスエラーが発生しました。メッセージとして出力される
			String message = MessageUtils.getMessage("DR000E001");
			log.error(message);
			throw new Exception(message);
		} catch (Exception e) {
			// データ取得失敗時
			String message = MessageUtils.getMessage("DRC01E001");
			log.error(message);
			throw new Exception(message);
		}
		return tenantNotificationList;
	}

}
