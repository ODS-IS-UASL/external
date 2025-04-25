package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.ReservationAirwayAssociationEntity;

/**
 * RESERVATION_AIRWAY_ASSOCIATIONにデータを挿入
 */
@Mapper
public interface ReservationAirwayAssociationMapper {
	/**
	 * @param reservationAirwayAssociation
	 */
	void insertReservationAirwayAssociation(ReservationAirwayAssociationEntity reservationAirwayAssociation);

	/**
	 * @param airwayReserveId
	 * @param airwaySectionId
	 * @return ReservationAirwayAssociation
	 */
	@Select("SELECT * FROM reservation_airway_association WHERE airway_reserve_id = #{airwayReserveId} AND airway_section_id = #{airwaySectionId} ")
	ReservationAirwayAssociationEntity getReservationAirwayAssociationById(String airwayReserveId, String airwaySectionId);
	
	/**
     * 予約IDをもとに紐づいている航路情報を削除する
     * 
     * @param airwayReserveId 予約ID
     */
    void deleteByAirwayReserveId(@Param("airwayReserveId") String airwayReserveId);

}
