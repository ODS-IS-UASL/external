package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper;

import org.apache.ibatis.annotations.Mapper;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReserveRelatedEntity;

/**
 * 航路予約関係者にデータを挿入、更新
 */
@Mapper
public interface AirwayReserveRelatedMapper {
    /**
     * @param airwayReserveRelatedEntity
     */
    void insertAirwayReserveRelated(AirwayReserveRelatedEntity airwayReserveRelatedEntity);
}
