package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.entity.AirwayTenantAssociationEntity;

/**
 * 航路事業者情報マッパー
 */
@Mapper
public interface AirwayTenantAssociationMapper {

    /**
     * 航路事業者情報テーブルから航路IDに基づいて件数を検索
     * 
     * @param airwayId 航路ID
     * @return 件数
     */
    int countByAirwayId(@Param("airwayId") String airwayId);
    
    /**
     * 航路事業者情報テーブルから航路IDに基づいて事業者IDリストを検索
     * 
     * @param airwayId 航路ID
     * @return List<String>
     */
    List<String> findByAirwayId(@Param("airwayId") String airwayId);

    /**
     * 航路事業者情報テーブルから航路IDと事業者IDリストに基づいて関連パーティを検索
     * 
     * @param airwayId 航路ID
     * @param operatorIdList 事業者IDリスト
     * @return 関連パーティのリスト
     */
    List<AirwayTenantAssociationEntity> foundAirwayIdAndOperatorIds(@Param("airwayId") String airwayId,
        @Param("operatorIdList") List<String> operatorIdList);

    /**
     * 航路事業者情報テーブルに登録する
     * 
     * @param airwayId 航路ID
     * @param operatorIdList 事業者IDリスト
     * @param creationId 作成者ID
     * @param updateId 更新者ID
     */
    void insertAirwayTenantAssociation(@Param("airwayId") String airwayId,
        @Param("operatorIdList") List<String> operatorIdList, @Param("creationId") int creationId,
        @Param("updateId") int updateId);

    /**
     * 航路IDと事業者IDリスト に基づいて航路事業者情報を削除する
     * 
     * @param airwayId 航路ID
     * @param operatorIdList 事業者IDリスト
     */
    void deleteByAirwayIdAndOperatorIds(@Param("airwayId") String airwayId,
        @Param("operatorIdList") List<String> operatorIdList);

    /**
     * 航路ID に基づいて航路事業者情報を削除する
     * 
     * @param airwayId 航路ID
     */
    void deleteByAirwayId(@Param("airwayId") String airwayId);
}
