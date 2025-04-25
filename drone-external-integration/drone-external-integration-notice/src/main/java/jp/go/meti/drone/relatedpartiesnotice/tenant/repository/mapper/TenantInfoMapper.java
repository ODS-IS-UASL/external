package jp.go.meti.drone.relatedpartiesnotice.tenant.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.go.meti.drone.relatedpartiesnotice.tenant.repository.entity.TenantInfoEntity;

/**
 * 事業者情報 Mapper
 */
@Mapper
public interface TenantInfoMapper {

    /**
     * 事業者IDより事業者情報を返却
     * 
     * @param  operatorId
     * @return List<TenantInfoEntity>
     */
    List<TenantInfoEntity> getTenantInfoByOperatorId(String operatorId);

}
