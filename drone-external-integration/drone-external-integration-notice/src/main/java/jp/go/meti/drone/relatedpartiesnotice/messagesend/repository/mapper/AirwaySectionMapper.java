package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwaySectionEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.ReservationAirwayAssociationEntity;

/**
 * 航路区画情報AIRWAY_SECTIONにデータを挿入、削除
 */
@Mapper
public interface AirwaySectionMapper {
	
	/**
	 * @param airWay
	 */
	void insertAirwaySection(AirwaySectionEntity airwaySection);
	
	/**
	 * @param airwayId
	 * @param airwaySectionId
	 */
	void deleteAirwaySection(@Param("airwayId") String airwayId);

	@Select("SELECT * FROM airway_section WHERE airway_id = #{airwayId} AND airway_section_id = #{airwaySectionId} ")
	List<AirwaySectionEntity> getAirwaySectionById(@Param("airwayId") String airwayId, @Param("airwaySectionId") String airwaySectionId);
	
}
