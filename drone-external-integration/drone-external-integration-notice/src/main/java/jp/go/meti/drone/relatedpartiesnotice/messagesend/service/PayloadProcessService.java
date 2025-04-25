package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwayInfo;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwayReserveInfo;
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
	AirwayMessageSendService airwayMessageSendService;
	
	@Autowired
	AirwayReservationMessageSendService airwayReservationMessageSendService;

	@Autowired
	AirwayService airwayService;

	@Autowired
	AirwayReserveService airwayReserveService;
	
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
		//topicからairwayIdを取得
		String airwayId = topic.substring(topic.lastIndexOf("/") + 1);
		
		// MQTT航路情報メッセージとして出力される
		log.info("MQTT Subscribe First Service Topic:" + topic + " Message:" + message);
		
		// 情報をAirWayInfoに変更
		AirwayInfo airwayInfo = checkAndChangeService.converJsontoObject(message, AirwayInfo.class);

		// 必須チェック
		boolean result = checkAndChangeService.notNullCheckAirwayInfo(airwayInfo);
		//チェック結果がNGの場合、エラー処理とし受信処理を終了する。
		if(!result) {
		    log.info("MQTT Subscribe airwayCheck is NG.");
		    return;
		}
		try {
		    //航路情報・航路区画情報を保存する。
		    airwayService.saveAirway(airwayInfo, airwayId);
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
		
		//航路情報の更新成功後の処理
		// メール送信サービス処理の呼び出し
		taskExecutor.execute(() -> {
			try {
				airwayMessageSendService.messageSend(airwayInfo.getAirway() == null ? airwayId : airwayInfo.getAirway().getAirwayId());
				log.info("航路登録情報メール送信完了 (スレッド名: " + Thread.currentThread().getName() + ")");
			} catch (Exception e) {
				log.error("非同期タスクでエラーが発生しました: {}", e.getMessage(), e);
			}
		});

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

		// 情報をAirwayReserveInfoに変更
		AirwayReserveInfo airwayReserveInfo = checkAndChangeService.converJsontoObject(message,
				AirwayReserveInfo.class);
		
		// 必須な項目チェック
		boolean result = checkAndChangeService.notNullCheckAirwayReserveInfo(airwayReserveInfo);
		//チェック結果がNGの場合、エラー処理とし受信処理を終了する。
        if(!result) {
            log.info("MQTT Subscribe airwayReserveInfoCheck is NG.");
            return;
        }
        try {
            //航路予約情報を保存する。
            airwayReserveService.saveAirwayReservation(airwayReserveInfo);
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
		// メール送信サービス処理の呼び出し
		taskExecutor.execute(() -> {
			try {
				airwayReservationMessageSendService.messageSend(airwayReserveInfo.getAirwayReservationId(),airwayReserveInfo.getStatus(), airwayReserveInfo.getOperatorId());
				log.info("航路予約情報メール送信完了 (スレッド名: " + Thread.currentThread().getName() + ")");
			} catch (Exception e) {
				log.error("非同期タスクでエラーが発生しました: {}", e.getMessage(), e);
			}
		});

	}

}
