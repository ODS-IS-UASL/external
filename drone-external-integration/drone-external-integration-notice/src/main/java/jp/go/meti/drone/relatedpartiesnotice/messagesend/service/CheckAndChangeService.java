package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwayInfo;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwayReserveInfo;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwaySection;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.ReserveSection;
import lombok.extern.slf4j.Slf4j;

/**
 * MQTT情報必須な項目をチェック 日時フォーマットを変更 MQTT情報Json文字列からJavaクラスへ変更サービス
 */
@Slf4j
@Service
public class CheckAndChangeService {
	
    private static final String STATUS_CANCELED = "CANCELED";
    //予約
    private static final String STATUS_RESERVED = "RESERVED";
    //撤回
    private static final String STATUS_RESCINDED = "RESCINDED";
    //予約可能
    private static final String STATUS_AVAILABLE = "AVAILABLE";
	/**
	 * @param timestampStr
	 * @param fieldName
	 * @return null
	 */
	public LocalDateTime convertTimestamp(String timestampStr, String fieldName) {
		// yyyy/MM/dd HH:mm:ss
		DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		try {
			return LocalDateTime.parse(timestampStr, inputFormat);
		} catch (DateTimeParseException e) {
			// エラー メッセージ：{0}の値が正しくありません。出力される。
			String errorMessage = MessageUtils.getMessage("DR000E003", (Object) fieldName);

			log.error(errorMessage,e);
			return null;

		}
	}

	/**
	 * airwayInfoの必須な項目をチェック
	 * 
	 * @param airWayInfo
	 */
	public boolean notNullCheckAirwayInfo(AirwayInfo airwayInfo) {
	    //チェック結果
	    boolean result = true;
	    
	    if (airwayInfo.getRegisteredAt() == null || airwayInfo.getRegisteredAt().isEmpty()) {
            String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "RegisteredAt");
            log.error(errorMessage);
            result = false;
        }
	    if (airwayInfo.getAirwayAdministratorId() == null || airwayInfo.getAirwayAdministratorId().isEmpty()) {
            String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "AirwayAdministratorId");
            log.error(errorMessage);
            result = false;
        }
	    if(airwayInfo.getAirway() != null) {
	    	if (airwayInfo.getAirway().getAirwayId() == null || airwayInfo.getAirway().getAirwayId().isEmpty()) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "AirwayId");
	    		log.error(errorMessage);
	    		result = false;
	    	}
	    	if (airwayInfo.getAirway().getAirwayName() == null || airwayInfo.getAirway().getAirwayName().isEmpty()) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "AirwayName");
	    		log.error(errorMessage);
	    		result = false;
	    	}
	    	if (airwayInfo.getAirway().getFlightPurpose() == null || airwayInfo.getAirway().getFlightPurpose().isEmpty()) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "FlightPurpose");
	    		log.error(errorMessage);
	    		result = false;
	    	}
	    	if (airwayInfo.getAirway().getCreatedAt() == null || airwayInfo.getAirway().getCreatedAt().isEmpty()) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "CreatedAt");
	    		log.error(errorMessage);
	    		result = false;
	    	}
	    	if (airwayInfo.getAirway().getUpdatedAt() == null || airwayInfo.getAirway().getUpdatedAt().isEmpty()) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UpdatedAt");
	    		log.error(errorMessage);
	    		result = false;
	    	}
	    	//航路区画情報そのものが存在しない場合、後続チェックを行わずチェックNGとする。
	    	if (airwayInfo.getAirway().getAirwaySections() == null || airwayInfo.getAirway().getAirwaySections().isEmpty()) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "AirwaySections");
	    		log.error(errorMessage);
	    		return false;
	    	}
	    	//セクションの件数分繰り返しチェックを行う。
	    	for (AirwaySection airwaySection : airwayInfo.getAirway().getAirwaySections()) {
	    		if (airwaySection.getAirwaySectionId() == null || airwaySection.getAirwaySectionId().isEmpty()) {
	    			String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "AirwaySectionId");
	    			log.error(errorMessage);
	    			result = false;
	    		}
	    		if (airwaySection.getAirwaySectionName() == null || airwaySection.getAirwaySectionName().isEmpty()) {
	    			String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "AirwaySectionName");
	    			log.error(errorMessage);
	    			result = false;
	    		}
	    	}
        }
        //チェック結果を返却
		return result;

	}

    
	/**
	 * airwayReserveInfoの必須な項目をチェック
	 * 
	 * @param airwayReserveInfo 航路予約メッセージ
	 * @return チェック結果
	 */
	public boolean notNullCheckAirwayReserveInfo(AirwayReserveInfo airwayReserveInfo) {
	    //チェック結果
	    boolean result = true;
	    //受信メッセージ自体がnullの場合、後続処理を行わずにチェックNGとする。
	    if(airwayReserveInfo == null) {
	        String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "AirwayReserveInfo");
	        log.error(errorMessage);
	        return false;
	    }

	    if (airwayReserveInfo.getAirwayReservationId() == null || airwayReserveInfo.getAirwayReservationId().isEmpty()) {
	        // エラー メッセージ：{0}は必須項目です。出力される。
	        String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "AirwayReservationId");
	        log.error(errorMessage);
	        result = false;
	    }
	    if (airwayReserveInfo.getOperatorId() == null || airwayReserveInfo.getOperatorId().isEmpty()) {
	        String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "OperatorId");
	        log.error(errorMessage);
	        result = false;
	    }
	    if (airwayReserveInfo.getEventId() == null || airwayReserveInfo.getEventId().isEmpty()) {
	        String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "EventId");
	        log.error(errorMessage);
	        result = false;
	    }
	    if (airwayReserveInfo.getStatus() == null || airwayReserveInfo.getStatus().isEmpty()) {
	        String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "Status");
	        log.error(errorMessage);
	        result = false;
	    }
	    else {
	        if (airwayReserveInfo.getStatus().equals(STATUS_RESERVED)) {
	            airwayReserveInfo.setStatus("1");
	        }
	        else if (airwayReserveInfo.getStatus().equals(STATUS_CANCELED)) {
	            airwayReserveInfo.setStatus("2");
	        }
	        else if (airwayReserveInfo.getStatus().equals(STATUS_RESCINDED)) {
	            airwayReserveInfo.setStatus("3");
	        }
	        else if (airwayReserveInfo.getStatus().equals(STATUS_AVAILABLE)) {
	            airwayReserveInfo.setStatus("4");
	        }
	        //上記以外の場合、エラー値のためチェックNGとする。
	        else {
	            String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "Status");
	            log.error(errorMessage);
	            result = false;
	        }
	    }
	    //予約航路区画情報そのものが存在しない場合、後続チェックを行わずチェックNGとする。
	    if (airwayReserveInfo.getAirwaySections() == null || airwayReserveInfo.getAirwaySections().isEmpty()) {
	        String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "AirwaySections");
	        log.error(errorMessage);
	        return false;
	    }
	    for (ReserveSection reserveSection : airwayReserveInfo.getAirwaySections()) {
	        if (reserveSection.getAirwaySectionId() == null || reserveSection.getAirwaySectionId().isEmpty()) {
	            String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "AirwaySectionId");
	            log.error(errorMessage);
	            result = false;
	        }		
	    }
	    if (airwayReserveInfo.getReservedAt() == null || airwayReserveInfo.getReservedAt().isEmpty()) {
	        String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "ReservedAt");
	        log.error(errorMessage);
	        result = false;
	    }
	    //チェック結果を返却
	    return result;
	}

	/**
	 * Json型MTQQメッセージをobjectに変更
	 * 
	 * @param payload
	 * @param clazz
	 * @return AirwayReserveInfo
	 */
	public <T> T converJsontoObject(String payload, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(payload, clazz);
		} catch (IOException e) {
			// エラーメッセージ：Jsonデータの形式が正しくありません。出力される。
			String errorMessage = MessageUtils.getMessage("DRC01E002");
			log.error(errorMessage,e);

			return null;
		}
	}
    
}
