package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.DestinationReservationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.DestinationReservationNotification;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.DestinationUaslSectionEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.UaslReservation;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.UaslSectionEntity;
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
     * 乗り入れ元予約情報登録・更新処理
     * 
     * @param uaslReservation 乗り入れ元予約情報
     * @throws Exception 例外
     */
    @Transactional
    public void uaslReservationCreateAndUpdate(UaslReservation uaslReservation) throws Exception {

        try {
            AirwayReservationEntity airwayReservation = new AirwayReservationEntity();
            // ステータスがRESERVEDの場合、登録処理
            if (uaslReservation.getStatus().toString().equals("RESERVED")) {
                // 乗り入れ元予約情報を登録する
                // 親予約ID
                airwayReservation.setRequestId(uaslReservation.getRequestId());
                // 航路予約ID
                airwayReservation.setAirwayReserveId(uaslReservation.getOriginReservation().getReservationId());
                // 運航事業者（予約者）ID
                airwayReservation.setOperatorId(uaslReservation.getOperatorId());
                // PublishイベントID
                airwayReservation.setEventId(uaslReservation.getEventId());
                // 飛行目的
                airwayReservation.setFlightPurpose(uaslReservation.getFlightPurpose());
                // 処理区分
                airwayReservation.setStatus("1");
                // 予約完了日時
                airwayReservation.setReservedAt(convertOffsetDateTime(uaslReservation.getReservedAt(),"ReservedAt"));
                // 当該予約が属する航路のID
                airwayReservation.setUaslId(uaslReservation.getOriginReservation().getUaslId());
                // 予約状態更新日時
                airwayReservation.setUpdatedAt(convertOffsetDateTime(uaslReservation.getUpdatedAt(),"UpdatedAt"));

                airwayReservation.setCreationId(userId);
                airwayReservation.setUpdateId(userId);
                airwayReservationMapper.insertAirwayReservation(airwayReservation);

                // 乗り入れ元の予約詳細情報を登録する。
                List<UaslSectionEntity> uaslSections = uaslReservation.getOriginReservation().getUaslSections();
                for (UaslSectionEntity uaslSection : uaslSections) {
                    ReservationAirwayAssociationEntity reservationAirwayAssociationEntity = new ReservationAirwayAssociationEntity();
                    // 航路予約ID
                    reservationAirwayAssociationEntity.setAirwayReserveId(uaslReservation.getOriginReservation().getReservationId());
                    // 航路区画ID
                    reservationAirwayAssociationEntity.setAirwaySectionId(uaslSection.getUaslSectionId());
                    // 通過順
                    reservationAirwayAssociationEntity.setSequence(uaslSection.getSequence());
                    // 予約開始日時
                    reservationAirwayAssociationEntity.setStartAt(convertOffsetDateTime(uaslSection.getStartAt(),"StartAt"));
                    // 予約終了日時
                    reservationAirwayAssociationEntity.setEndAt(convertOffsetDateTime(uaslSection.getEndAt(),"EndAt"));

                    reservationAirwayAssociationEntity.setCreationId(userId);
                    reservationAirwayAssociationEntity.setUpdateId(userId);
                    reservationAirwayAssociationMapper.insertReservationAirwayAssociation(reservationAirwayAssociationEntity);
                }

                // 乗り入れ先の予約情報を登録する。
                List<DestinationReservationEntity> destinationReservations = uaslReservation.getDestinationReservations();
                if (!ObjectUtils.isEmpty(destinationReservations)) {
                    for (DestinationReservationEntity destinationReservation : destinationReservations) {
                        // 乗り入れ先予約情報を登録する
                        AirwayReservationEntity drInairwayReservation = new AirwayReservationEntity();
                        // 親予約ID
                        drInairwayReservation.setRequestId(uaslReservation.getRequestId());
                        // 航路予約ID
                        drInairwayReservation.setAirwayReserveId(destinationReservation.getReservationId());
                        // 運航事業者（予約者）ID
                        drInairwayReservation.setOperatorId(uaslReservation.getOperatorId());
                        // PublishイベントID
                        drInairwayReservation.setEventId(uaslReservation.getEventId());
                        // 飛行目的
                        drInairwayReservation.setFlightPurpose(uaslReservation.getFlightPurpose());
                        // 処理区分
                        drInairwayReservation.setStatus("1");
                        // 予約完了日時
                        drInairwayReservation.setReservedAt(convertOffsetDateTime(uaslReservation.getReservedAt(),"ReservedAt"));
                        // 当該予約が属する航路のID
                        drInairwayReservation.setUaslId(destinationReservation.getUaslId());
                        // 予約状態更新日時
                        drInairwayReservation.setUpdatedAt(convertOffsetDateTime(uaslReservation.getUpdatedAt(),"UpdatedAt"));

                        drInairwayReservation.setCreationId(userId);
                        drInairwayReservation.setUpdateId(userId);
                        airwayReservationMapper.insertAirwayReservation(drInairwayReservation);

                        // 乗り入れ先の予約詳細情報を登録する。
                        for (DestinationUaslSectionEntity destinationUaslSection : destinationReservation.getUaslSections()) {
                            ReservationAirwayAssociationEntity reservationAirwayAssociation = new ReservationAirwayAssociationEntity();
                            // 航路予約ID
                            reservationAirwayAssociation.setAirwayReserveId(destinationReservation.getReservationId());
                            // 航路区画ID
                            reservationAirwayAssociation.setAirwaySectionId(destinationUaslSection.getUaslSectionId());
                            // 通過順
                            reservationAirwayAssociation.setSequence(destinationUaslSection.getSequence());
                            // 予約開始日時
                            reservationAirwayAssociation.setStartAt(convertOffsetDateTime(destinationUaslSection.getStartAt(),"StartAt"));
                            // 予約終了日時
                            reservationAirwayAssociation.setEndAt(convertOffsetDateTime(destinationUaslSection.getEndAt(),"EndAt"));

                            reservationAirwayAssociation.setCreationId(userId);
                            reservationAirwayAssociation.setUpdateId(userId);
                            reservationAirwayAssociationMapper.insertReservationAirwayAssociation(reservationAirwayAssociation);
                        }
                    }
                }
            } else {
                // 予約情報を更新する
            	airwayReservation.setRequestId(uaslReservation.getRequestId());
                airwayReservation.setStatus(uaslReservation.getStatus().toString().equals("CANCELED") ? "2" : "3");
                airwayReservation.setUpdatedAt(convertOffsetDateTime(uaslReservation.getUpdatedAt(),"UpdatedAt"));
                airwayReservation.setUpdateId(userId);
                airwayReservationMapper.updateByAirwayReservation(airwayReservation);
            }
        } catch (DataAccessException e) {
            // ＤＢアクセスエラーが発生しました。メッセージとして出力される
            String message = MessageUtils.getMessage("DR000E001");
            log.error(message, e);
            throw e;
        } catch (Exception e) {
            // 挿入失敗時
            String message = MessageUtils.getMessage("DRC01E001");
            log.error(message, e);
            throw e;
        }
    }

    /**
     * 乗り入れ先予約情報登録・更新処理
     * 
     * @param destinationReservationNotification 乗り入れ先予約情報
     * @throws Exception 例外
     */
    @Transactional
    public void destinationReservationNotificationCreateAndUpdate(DestinationReservationNotification destinationReservationNotification) throws Exception {

        try {
            AirwayReservationEntity airwayReservation = new AirwayReservationEntity();
            // ステータスがRESERVEDの場合、登録処理
            if (destinationReservationNotification.getStatus().toString().equals("RESERVED")) {
                // 乗り入れ先予約情報を登録する
                // 親予約ID
                airwayReservation.setRequestId(destinationReservationNotification.getRequestId());
                // 航路予約ID
                airwayReservation.setAirwayReserveId(destinationReservationNotification.getReservationId());
                // 運航事業者（予約者）ID
                airwayReservation.setOperatorId(destinationReservationNotification.getOperatorId());
                // PublishイベントID
                airwayReservation.setEventId(destinationReservationNotification.getEventId());
                // 飛行目的
                airwayReservation.setFlightPurpose(destinationReservationNotification.getFlightPurpose());
                // 処理区分
                airwayReservation.setStatus("1");
                // 予約完了日時
                airwayReservation.setReservedAt(convertOffsetDateTime(destinationReservationNotification.getReservedAt(),"ReservedAt"));
                // 当該予約が属する航路のID
                airwayReservation.setUaslId(destinationReservationNotification.getUaslId());
                // 予約状態更新日時
                airwayReservation.setUpdatedAt(convertOffsetDateTime(destinationReservationNotification.getUpdatedAt(),"UpdatedAt"));

                airwayReservation.setCreationId(userId);
                airwayReservation.setUpdateId(userId);
                airwayReservationMapper.insertAirwayReservation(airwayReservation);

                // 乗り入れ先の予約詳細情報を登録する
                List<DestinationUaslSectionEntity> destinationUaslSections = destinationReservationNotification.getUaslSections();
                for (DestinationUaslSectionEntity destinationUaslSection : destinationUaslSections) {
                    ReservationAirwayAssociationEntity reservationAirwayAssociationEntity = new ReservationAirwayAssociationEntity();
                    // 航路予約ID
                    reservationAirwayAssociationEntity.setAirwayReserveId(destinationReservationNotification.getReservationId());
                    // 航路区画ID
                    reservationAirwayAssociationEntity.setAirwaySectionId(destinationUaslSection.getUaslSectionId());
                    // 通過順
                    reservationAirwayAssociationEntity.setSequence(destinationUaslSection.getSequence());
                    // 予約開始日時
                    reservationAirwayAssociationEntity.setStartAt(convertOffsetDateTime(destinationUaslSection.getStartAt(),"StartAt"));
                    // 予約終了日時
                    reservationAirwayAssociationEntity.setEndAt(convertOffsetDateTime(destinationUaslSection.getEndAt(),"EndAt"));

                    reservationAirwayAssociationEntity.setCreationId(userId);
                    reservationAirwayAssociationEntity.setUpdateId(userId);
                    reservationAirwayAssociationMapper.insertReservationAirwayAssociation(reservationAirwayAssociationEntity);
                }
            } else {
                // 予約情報を更新する
            	airwayReservation.setRequestId(destinationReservationNotification.getRequestId());
                airwayReservation.setStatus(destinationReservationNotification.getStatus().toString().equals("CANCELED") ? "2" : "3");
                airwayReservation.setUpdatedAt(convertOffsetDateTime(destinationReservationNotification.getUpdatedAt(),"UpdatedAt"));
                airwayReservation.setUpdateId(userId);
                airwayReservationMapper.updateByAirwayReservation(airwayReservation);
            }
        } catch (DataAccessException e) {
            // ＤＢアクセスエラーが発生しました。メッセージとして出力される
            String message = MessageUtils.getMessage("DR000E001");
            log.error(message, e);
            throw e;
        } catch (Exception e) {
            // 挿入失敗時
            String message = MessageUtils.getMessage("DRC01E001");
            log.error(message, e);
            throw e;
        }
    }
	
	/**
	 * OffsetDateTime形式をLocalDateTimeに変換する
	 * 
     * @param timestampStr
     * @param fieldName
     * @return 変換後の日時オブジェクト
     */
    private LocalDateTime convertOffsetDateTime(String timestampStr, String fieldName) {
    	
        try {
        	Instant instant = Instant.parse(timestampStr);
            return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        } catch (DateTimeParseException e) {
            // エラー メッセージ：{0}の値が正しくありません。出力される。
            String errorMessage = MessageUtils.getMessage("DR000E003", (Object) fieldName);
            log.error(errorMessage,e);
            throw e;
        }
    }

}
