package jp.go.meti.drone.relatedpartiesnotice.reservenotification.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import jp.go.meti.drone.relatedpartiesnotice.reservenotification.repository.entity.ReserveNotificationEntity;


/**
 * 航路予約関係者情報マッパー
 */
@Mapper
public interface ReserveNotificationMapper {

    /**
     * 航路予約関係者テーブルから航路予約IDに基づいて関連パーティを検索
     * 
     * @param airwayReserveId 航路予約ID
     * @return 航路予約関係者情報リスト
     */
	List<ReserveNotificationEntity> findReserveNotificationByIds(String airwayReserveId);

}
