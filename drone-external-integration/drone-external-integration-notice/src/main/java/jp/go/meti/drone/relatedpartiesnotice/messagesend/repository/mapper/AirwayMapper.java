package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayEntity;

/**
 * AIRWAYにデータを挿入、更新、検索
 */
@Mapper
public interface AirwayMapper {
	/**
	 * @param airWay
	 */
	void insertAirway(AirwayEntity airWay);
	
	/**
	 * @param airway
	 * @return 更新件数
	 */
	int updateByAirwayId(AirwayEntity airway);

	/**
	 * @param airwayId
	 */
	void updateStatusByAirwayId(@Param("airwayId") String airwayId);
	
	/**
	 * @param airwayId
	 */
	void deleteAirway(@Param("airwayId") String airwayId);
	
	/**
	 * @param airwayId
	 * @return AirWay
	 */
    @Select("SELECT * FROM airway WHERE airway_id = #{airwayId}")
	List<AirwayEntity> getAirwayById(String airwayId);
    
	/**
	 * @param airwayId
	 * @return AirWay
	 */
    @Select("SELECT airway_id FROM airway")
	List<String> getAirwayId();
    
    /**
     * @param airwayId
     * @return 航路情報
     */
    AirwayEntity getAirwayByAirwayId(String airwayId);
}
