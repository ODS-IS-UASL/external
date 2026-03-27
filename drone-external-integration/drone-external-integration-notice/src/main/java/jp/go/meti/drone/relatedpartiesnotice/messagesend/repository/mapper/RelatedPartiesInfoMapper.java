package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.RelatedMunicipalityInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.RelatedPartiesInfoEntity;

/**
 * 関係者情報を取得
 */
@Mapper
public interface RelatedPartiesInfoMapper {

    /**
     * @param flightPrefecture
     * @return RelatedPartiesInfoEntity
     */
    @Select("SELECT * FROM RELATED_PARTIES_INFO \r\n"
        + "WHERE flight_prefecture LIKE CONCAT('%', #{flightPrefecture}, '%')")
    List<RelatedPartiesInfoEntity> getRelatedPartiesInfoByPrefecture(String flightPrefecture);
    
    /**
     * @param operatorId
     * @param flightMunicipality
     * @return RelatedMunicipalityInfoEntity
     */
    @Select("SELECT * FROM RELATED_MUNICIPALITY_INFO " +
        "WHERE operator_id = #{operatorId} " +
        "AND flight_municipality LIKE CONCAT('%', #{flightMunicipality}, '%')")
    List<RelatedMunicipalityInfoEntity> getRelatedMunicipalityInfoEntity(String operatorId, String flightMunicipality);
}
