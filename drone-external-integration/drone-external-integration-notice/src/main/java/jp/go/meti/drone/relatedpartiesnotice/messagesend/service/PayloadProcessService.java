package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.UaslInfo;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.UaslReservation;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.UaslSectionEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwayReserveInfo;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.DestinationReservationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.DestinationReservationNotification;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.DestinationUaslSectionEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * MQTT情報を受け取って、テーブルに保存するサービス
 */
@Slf4j
@Service
public class PayloadProcessService {
    
	@Autowired
	CheckAndChangeService checkAndChangeService;
	
	@Autowired
	AirwayService airwayService;

	@Autowired
	AirwayReserveService airwayReserveService;
	
	@Autowired
	RelatedPartyExtractionService relatedPartyExtractionService;
	
	@Autowired
	AirwayReservationMessageSendService airwayReservationMessageSendService;
	
	/** スレッドプールを使って非同期処理を管理 */
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
	
	/** ユーザID値取得 */
    @Value("${systemuser}")
    private int userId;

	/**
	 * 航路情報を受け取り 受け取った情報をclass AirWayInfoに変更 必須項目をチェック 情報をテーブルAirWayに保存
	 * 
	 * @param topic   トピック
	 * @param message メッセージ
	 * @throws Exception 例外
	 */
	public void subscribeFirst(String topic, String message) throws Exception {
		//topicからuaslIdを取得
		String uaslId = topic.substring(topic.lastIndexOf("/") + 1);
		
		// MQTT航路情報メッセージとして出力される
		log.info("MQTT Subscribe First Service Topic:" + topic + " Message:" + message);
		
		// 情報をUaslEntityInfoに変更
		UaslInfo uaslInfo = checkAndChangeService.converJsontoObject(message, UaslInfo.class);

		// 必須チェック
		boolean result = checkAndChangeService.notNullCheckAirwayInfo(uaslInfo);
		//チェック結果がNGの場合、エラー処理とし受信処理を終了する。
		if(!result) {
		    log.info("MQTT Subscribe airwayCheck is NG.");
		    return;
		}
		try {
		    //航路情報・航路区画情報を保存する。
		    airwayService.saveAirway(uaslInfo, uaslId);
		} catch (DataAccessException e) {
		    // ＤＢアクセスエラーが発生しました。メッセージとして出力される
		    String erm = MessageUtils.getMessage("DR000E001");
		    log.error(erm,e);
		    throw e;
		} catch (Exception e) {
		    // 挿入失敗時
		    String erm = MessageUtils.getMessage("DRC01E001");
		    log.error(erm,e);
		    throw e;
		}
	}

	/**
	 * 航路予約情報を受け取り 受け取った情報をclass AirwayReserveInfoに変更 必須項目をチェック Timestampのチェックと変更
	 * 情報をテーブルAirwayReservation、ReservationAirwayAssociationに保存
	 * 
	 * @param topic   トピック
	 * @param message メッセージ
	 * @throws Exception 例外
	 */
	public void subscribeSecond(String topic, String message) throws Exception {
		// MQTT航路予約情報メッセージとして出力される
		log.info("MQTT Subscribe Second Service Topic:" + topic + " Message:" + message);
		
		// 乗り入れ判定処理
		final ObjectMapper objectMapper = new ObjectMapper();
        String[] parts = topic.split("/");
        String topicId = parts[4];
        JsonNode root = objectMapper.readTree(message);
        String requestId = root.get("requestId").asText();

        if (topicId.equals(requestId)) {
            // 乗り入れ元情報をUaslReservationに変更
            UaslReservation uaslReservation = checkAndChangeService.converJsontoObject(message,
                UaslReservation.class);
            if (uaslReservation == null) {
                log.info("Jsonデータの形式が正しくありません。");
                return;
            }
            
            // 必須な項目チェック
            boolean result = checkAndChangeService.uaslReservationCheck(uaslReservation);
            //チェック結果がNGの場合、エラー処理とし受信処理を終了する。
            if(!result) {
                log.info("MQTT Subscribe uaslReservationCheck is NG.");
                return;
            }
            try {
                //航路予約情報を保存する。
                airwayReserveService.uaslReservationCreateAndUpdate(uaslReservation);
            } catch (DataAccessException e) {
                // ＤＢアクセスエラーが発生しました。メッセージとして出力される
                String erm = MessageUtils.getMessage("DR000E001");
                log.error(erm,e);
                throw e;
            } catch (Exception e) {
                // 挿入失敗時
                String erm = MessageUtils.getMessage("DRC01E001");
                log.error(erm,e);
                throw e;
            }
            
            // status判定
            if(uaslReservation.getStatus().toString().equals("RESERVED")) {
                // 必要情報抽出処理
                List<AirwayReserveInfo> airwayReserveInfoList = new ArrayList<>();
                // originReservationの情報をリストに追加する。
                AirwayReserveInfo originReservationInfo = new AirwayReserveInfo();
                originReservationInfo.setRequestId(uaslReservation.getRequestId());
                originReservationInfo.setReservationId(uaslReservation.getOriginReservation().getReservationId());
                originReservationInfo.setUaslId(uaslReservation.getOriginReservation().getUaslId());
                for (UaslSectionEntity uaslSectionEntity : uaslReservation.getOriginReservation().getUaslSections()) {
                    originReservationInfo.getUaslSectionIds().add(uaslSectionEntity.getUaslSectionId());
                }
                airwayReserveInfoList.add(originReservationInfo);
                
                //destinationReservationsの情報をリストに追加する。 ※空リストではない場合実施
                if (uaslReservation.getDestinationReservations() != null && !uaslReservation.getDestinationReservations().isEmpty()) {
                    for(DestinationReservationEntity destinationReservation : uaslReservation.getDestinationReservations()) {
                        AirwayReserveInfo destinationReservationsInfo = new AirwayReserveInfo();
                        destinationReservationsInfo.setRequestId(uaslReservation.getRequestId());
                        destinationReservationsInfo.setReservationId(destinationReservation.getReservationId());
                        destinationReservationsInfo.setUaslId(destinationReservation.getUaslId());
                        for (DestinationUaslSectionEntity destinationUaslSection : destinationReservation.getUaslSections()) {
                            destinationReservationsInfo.getUaslSectionIds().add(destinationUaslSection.getUaslSectionId());
                        }
                        airwayReserveInfoList.add(destinationReservationsInfo);
                    }
                }

                taskExecutor.execute(() -> {
                    try {
                        // 関係者抽出処理の呼び出し
                        relatedPartyExtractionService.getRelatedUsers(airwayReserveInfoList);
                        log.info("航路予約情報に関する関係者抽出処理完了 (スレッド名: " + Thread.currentThread().getName() + ")");
                        // メール送信サービス処理の呼び出し
                        airwayReservationMessageSendService.messageSend(uaslReservation.getRequestId());
                        log.info("航路予約情報メール送信完了 (スレッド名: " + Thread.currentThread().getName() + ")");
                    } catch (Exception e) {
                        log.error("非同期タスクでエラーが発生しました: {}", e.getMessage(), e);
                    }
                });
                
            } else {
                // CANCELED(キャンセル),RESCINDED(撤回)
                taskExecutor.execute(() -> {
                    try {
                        // メール送信サービス処理の呼び出し
                        airwayReservationMessageSendService.messageSend(uaslReservation.getRequestId());
                        log.info("航路予約情報メール送信完了 (スレッド名: " + Thread.currentThread().getName() + ")");
                    } catch (Exception e) {
                        log.error("非同期タスクでエラーが発生しました: {}", e.getMessage(), e);
                    }
                });
            }

        } else {
            
            // 乗り入れ先情報をDestinationReservationNotificationに変更
            DestinationReservationNotification destinationReservationNotification = checkAndChangeService.converJsontoObject(message,
                DestinationReservationNotification.class);
            if (destinationReservationNotification == null) {
                log.info("Jsonデータの形式が正しくありません。");
                return;
            }
            
            // 必須な項目チェック
            boolean result = checkAndChangeService.destinationReservationNotificationCheck(destinationReservationNotification);
            //チェック結果がNGの場合、エラー処理とし受信処理を終了する。
            if(!result) {
                log.info("MQTT Subscribe destinationReservationNotificationCheck is NG.");
                return;
            }
            try {
                //航路予約情報を保存する。
                airwayReserveService.destinationReservationNotificationCreateAndUpdate(destinationReservationNotification);
            } catch (DataAccessException e) {
                // ＤＢアクセスエラーが発生しました。メッセージとして出力される
                String erm = MessageUtils.getMessage("DR000E001");
                log.error(erm,e);
                throw e;
            } catch (Exception e) {
                // 挿入失敗時
                String erm = MessageUtils.getMessage("DRC01E001");
                log.error(erm,e);
                throw e;
            }
            
            // status判定
            if(destinationReservationNotification.getStatus().toString().equals("RESERVED")) {
             // 必要情報抽出処理
                List<AirwayReserveInfo> airwayReserveInfoList = new ArrayList<>();
                // DestinationReservationNotificationの情報をリストに追加する。
                AirwayReserveInfo destinationReservationInfo = new AirwayReserveInfo();
                destinationReservationInfo.setRequestId(destinationReservationNotification.getRequestId());
                destinationReservationInfo.setReservationId(destinationReservationNotification.getReservationId());
                destinationReservationInfo.setUaslId(destinationReservationNotification.getUaslId());
                for (DestinationUaslSectionEntity destinationUaslSection : destinationReservationNotification.getUaslSections()) {
                    destinationReservationInfo.getUaslSectionIds().add(destinationUaslSection.getUaslSectionId());
                }
                airwayReserveInfoList.add(destinationReservationInfo);
                
                taskExecutor.execute(() -> {
                    try {
                        // 関係者抽出処理の呼び出し
                        relatedPartyExtractionService.getRelatedUsers(airwayReserveInfoList);
                        log.info("航路予約情報に関する関係者抽出処理完了 (スレッド名: " + Thread.currentThread().getName() + ")");
                        // メール送信サービス処理の呼び出し
                        airwayReservationMessageSendService.messageSend(destinationReservationNotification.getRequestId());
                        log.info("航路予約情報メール送信完了 (スレッド名: " + Thread.currentThread().getName() + ")");
                    } catch (Exception e) {
                        log.error("非同期タスクでエラーが発生しました: {}", e.getMessage(), e);
                    }
                });
            } else {
                // CANCELED(キャンセル),RESCINDED(撤回)
                taskExecutor.execute(() -> {
                    try {
                        // メール送信サービス処理の呼び出し
                        airwayReservationMessageSendService.messageSend(destinationReservationNotification.getRequestId());
                        log.info("航路予約情報メール送信完了 (スレッド名: " + Thread.currentThread().getName() + ")");
                    } catch (Exception e) {
                        log.error("非同期タスクでエラーが発生しました: {}", e.getMessage(), e);
                    }
                });
            }
        }	
	}
}
