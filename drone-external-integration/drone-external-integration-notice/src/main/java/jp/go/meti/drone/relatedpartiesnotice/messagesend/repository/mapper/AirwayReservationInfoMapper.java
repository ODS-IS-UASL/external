package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationInfoEntity;

/**
 * 航路予約情報を取得
 */
@Mapper
public interface AirwayReservationInfoMapper {
	/**
	 * @param requestId 親予約ID
	 * @return List<AirwayReservationInfoEntity>
	 */
    List<AirwayReservationInfoEntity> selectReservationDetail(String requestId);
}
