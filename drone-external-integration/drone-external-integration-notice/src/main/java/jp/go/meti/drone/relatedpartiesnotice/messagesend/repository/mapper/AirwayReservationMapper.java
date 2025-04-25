package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationEntity;

/**
 * AIRWAY_RESERVATIONにデータを挿入、更新
 */
@Mapper
public interface AirwayReservationMapper {
	/**
	 * @param airwayReservation
	 */
	void insertAirwayReservation(AirwayReservationEntity airwayReservation);


	/**
	 * @param airwayReserveId
	 * @return airwayReservation
	 */
	AirwayReservationEntity getAirwayReservationById(String airwayReserveId);
	
	/**
	 * @param airwayReservation
	 * @return 更新件数
	 */
	int updateByAirwayReservation(AirwayReservationEntity airwayReservation);
}
