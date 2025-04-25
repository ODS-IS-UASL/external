package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationInfoEntity;

/**
 * 航路予約情報を取得
 */
@Mapper
public interface AirwayReservationInfoMapper {
	/**
	 * @param airwayReserveId 予約ID
	 * @return List<AirwayReservationInfoEntity>
	 */
	List<AirwayReservationInfoEntity> searchAirwayReservationInfo(@Param("airwayReserveId") String airwayReserveId);
	
    /**
     * 航路IDに基づいて航路予約情報取得
     * 
     * @param airwayId
     * @return　航路予約情報
     */
	List<AirwayReservationInfoEntity> findAirwayReservationInfo(@Param("airwayId") String airwayId);

}
