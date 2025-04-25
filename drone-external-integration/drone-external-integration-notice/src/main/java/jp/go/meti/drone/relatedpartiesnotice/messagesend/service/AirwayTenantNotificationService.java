package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.TenantNotificationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayTenantNotificationMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 航路登録時、送信情報を取得するサービス
 */
@Slf4j
@Service
public class AirwayTenantNotificationService {
	@Autowired
	private AirwayTenantNotificationMapper airwayTenantNotificationMapper;

	@Autowired
	private AirwayMapper airwayMapper;

	/**
	 * 送信先情報検索
	 * 
	 * @param airwayinfo
	 * @return List<TenantNotificationEntity>
	 * @throws Exception
	 */
	public List<TenantNotificationEntity> getTenantNotification(AirwayInfoEntity airwayinfo) throws Exception {
		try {
		    return airwayTenantNotificationMapper.searchAirwayTenantNotification(airwayinfo.getAirwayId());
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
	}


}
