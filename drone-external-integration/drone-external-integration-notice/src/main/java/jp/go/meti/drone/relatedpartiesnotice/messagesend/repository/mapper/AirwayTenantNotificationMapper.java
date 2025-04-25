package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.TenantNotificationEntity;

/**
 * 航路関係者送信情報を取得
 */
@Mapper
public interface AirwayTenantNotificationMapper {
	/**
	 * @param airwayId
	 * @return List<RelatedpartiesNotification>
	 */
	List<TenantNotificationEntity> searchAirwayTenantNotification(String airwayId);
	
    /**
     * 事業者IDリストに基づいて航路事業者送信情報取得
     * 
     * @param operatorIdList
     * @return 航路事業者送信情報
     */
    List<TenantNotificationEntity> findAirwayTenantNotification(
        @Param("operatorIdList") List<String> operatorIdList);


}
