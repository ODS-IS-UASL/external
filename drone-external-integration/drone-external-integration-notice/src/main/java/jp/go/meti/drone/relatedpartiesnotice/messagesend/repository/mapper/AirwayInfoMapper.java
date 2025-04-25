package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayInfoEntity;

/**
 * 航路情報を取得
 */
@Mapper
public interface AirwayInfoMapper {
	/**
	 * @param airwayId 航路ID
	 * @return AirwayInfoEntity 航路情報
	 */
	AirwayInfoEntity searchAirwayInfo(@Param("airwayId") String airwayId);
	
    /**
     * 航路IDに基づいて航路情報取得
     * 
     * @param airwayId
     * @return　航路情報
     */
    AirwayInfoEntity findAirwayById(@Param("airwayId") String airwayId);

}
