package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.TenantNotificationEntity;

/**
 * 航路予約関係者送信情報を取得
 */
@Mapper
public interface AirwayReservationTenantNotificationMapper {
	/**
	 * @return List<RelatedpartiesNotification>
	 */
	List<TenantNotificationEntity> searchAirwayReservationRelatedpartiesNotification();

}
