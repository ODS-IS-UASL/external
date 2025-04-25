package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwayReserveInfo;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.ReserveSection;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.ReservationAirwayAssociationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayReservationMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.ReservationAirwayAssociationMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 航路予約情報をテーブルAirwayReservation、ReservationAirwayAssociationに保存するサービス
 */

@Slf4j
@Service
public class AirwayReserveService {
	@Autowired
	private AirwayReservationMapper airwayReservationMapper;

	@Autowired
	private ReservationAirwayAssociationMapper reservationAirwayAssociationMapper;
	
	/** ユーザID値取得 */
    @Value("${systemuser}")
    private int userId;

	/**
	 * テーブルAirwayReservationに保存
	 * 
	 * @param airwayReserveInfo 予約情報
	 * @throws Exception 例外
	 */
    @Transactional
	public void saveAirwayReservation(AirwayReserveInfo airwayReserveInfo) throws Exception {

        try {
            //日時フォーマットが正しいかチェックする。
            LocalDateTime reservedAt = convertTimestamp(airwayReserveInfo.getReservedAt(), "ReservedAt");
            //予約状態更新日時に値がある場合は変換する。
            LocalDateTime updatedAt = null;
            if(airwayReserveInfo.getUpdatedAt() != null && !airwayReserveInfo.getUpdatedAt().isEmpty() ) {
                updatedAt = convertTimestamp(airwayReserveInfo.getUpdatedAt(), "UpdatedAt");
            }

            // テーブル:AirwayReservationに保存
            AirwayReservationEntity airwayReservation = new AirwayReservationEntity();
            airwayReservation.setAirwayReserveId(airwayReserveInfo.getAirwayReservationId());
            airwayReservation.setOperatorId(airwayReserveInfo.getOperatorId());
            airwayReservation.setEventId(airwayReserveInfo.getEventId());
            airwayReservation.setStatus(airwayReserveInfo.getStatus());
            airwayReservation.setReservedAt(reservedAt);
            airwayReservation.setUpdatedAt(updatedAt);
            airwayReservation.setCreationId(userId);
            airwayReservation.setUpdateId(userId);

            //予約IDをもとに航路予約テーブルから予約情報を取得する。
            AirwayReservationEntity selectReservation = airwayReservationMapper.getAirwayReservationById(airwayReserveInfo.getAirwayReservationId());
            //予約IDと一致する予約情報がテーブルに存在しない場合、予約情報を登録する。
            if(selectReservation == null) {
                airwayReservationMapper.insertAirwayReservation(airwayReservation);
            }
            else {
                //予約情報が既に存在する場合は、更新する。
                airwayReservationMapper.updateByAirwayReservation(airwayReservation);
            }
            //すでに存在する予約情報航路関連情報は削除する。
            reservationAirwayAssociationMapper.deleteByAirwayReserveId(airwayReservation.getAirwayReserveId());

            //予約情報に紐づく航路区画情報の件数分、予約情報航路関連情報を登録する。
            for(ReserveSection reserveSection : airwayReserveInfo.getAirwaySections()) {
                ReservationAirwayAssociationEntity reservationAirwayAssociationEntity = new ReservationAirwayAssociationEntity();
                reservationAirwayAssociationEntity.setAirwayReserveId(airwayReservation.getAirwayReserveId());
                reservationAirwayAssociationEntity.setAirwaySectionId(reserveSection.getAirwaySectionId());
                reservationAirwayAssociationEntity.setStartAt(convertTimestamp(reserveSection.getStartAt(), "StartAt"));
                reservationAirwayAssociationEntity.setEndAt(convertTimestamp(reserveSection.getEndAt(), "EndAt"));
                reservationAirwayAssociationEntity.setCreationId(userId);
                reservationAirwayAssociationEntity.setUpdateId(userId);
                reservationAirwayAssociationMapper.insertReservationAirwayAssociation(reservationAirwayAssociationEntity);
            }

        } catch (DataAccessException e) {
            // ＤＢアクセスエラーが発生しました。メッセージとして出力される
            String message = MessageUtils.getMessage("DR000E001");
            log.error(message,e);
            throw e;
        } catch (Exception e) {
            // 挿入失敗時
            String message = MessageUtils.getMessage("DRC01E001");
            log.error(message,e);
            throw e;
        }
	}
	
	/**
     * @param timestampStr
     * @param fieldName
     * @return 変換後の日時オブジェクト
     */
    private LocalDateTime convertTimestamp(String timestampStr, String fieldName) {
        // インプット：yyyy-MM-dd'T'HH:mm:ssZ
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        // 出力:yyyy-MM-dd HH:mm:ss
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            LocalDateTime input = LocalDateTime.parse(timestampStr, inputFormat);
            String outStr = input.format(outputFormat);
            return LocalDateTime.parse(outStr, outputFormat);

        } catch (DateTimeParseException e) {
            // エラー メッセージ：{0}の値が正しくありません。出力される。
            String errorMessage = MessageUtils.getMessage("DR000E003", (Object) fieldName);
            log.error(errorMessage,e);
            throw e;
        }
    }

}
