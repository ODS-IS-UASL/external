package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.entity.TenantEntity;

/**
 * 事業者情報マッパー
 */
@Mapper
public interface TenantMapper {

	/**
	 * 事業者情報テーブルから事業者IDリストに基づいて関連パーティを検索
	 * 
	 * @param operatorIdList 事業者IDリスト
	 * @return 関連パーティのリスト
	 */
	List<TenantEntity> findOperatorByIds(@Param("operatorIdList") List<String> operatorIdList);

}