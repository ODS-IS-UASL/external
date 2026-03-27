package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.UaslReservation;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.UaslSectionEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.UaslSections;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.DestinationReservationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.DestinationReservationNotification;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.DestinationUaslSectionEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.UaslInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * MQTT情報必須な項目をチェック 日時フォーマットを変更 MQTT情報Json文字列からJavaクラスへ変更サービス
 */
@Slf4j
@Service
public class CheckAndChangeService {
	// キャンセル
    private static final String STATUS_CANCELED = "CANCELED";
    //予約
    private static final String STATUS_RESERVED = "RESERVED";
    //撤回
    private static final String STATUS_RESCINDED = "RESCINDED";
    
    //日時フォーマット1
    private static final DateTimeFormatter FORMATTER1 =
    		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
  //日時フォーマット2
    private static final DateTimeFormatter FORMATTER2 =
    		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

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
	 * @param checkTarget
	 * @return result
	 */
	private boolean checktimeStampFormat(String checkTarget) {
		try {
    		FORMATTER1.parse(checkTarget);
    		return true;
    	}catch(DateTimeParseException e){
    		// 次のチェックへ進む
    	}
		try {
    		FORMATTER2.parse(checkTarget);
    		return true;
    	}catch(DateTimeParseException e){
    		return false;
    	}
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
	
	/**
	 * 乗り入れ元航路（主航路）の予約通知ペイロードの項目をチェック
	 * 
	 * @param uaslReservationInfo
	 */
	public boolean uaslReservationCheck(UaslReservation uaslReservationInfo) {
		//チェック結果
	    boolean result = true;
	    
	    if(uaslReservationInfo.getEventId() == null || uaslReservationInfo.getEventId().isEmpty()) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "EventId");
            log.error(errorMessage);
            result = false;
	    }
	    
	    if(uaslReservationInfo.getRequestId() == null || uaslReservationInfo.getRequestId().isEmpty()) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "RequestId");
            log.error(errorMessage);
            result = false;
	    }
	    
	    if(uaslReservationInfo.getOperatorId() == null || uaslReservationInfo.getOperatorId().isEmpty()) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "OperatorId");
            log.error(errorMessage);
            result = false;
	    }
	    
	    if(uaslReservationInfo.getFlightPurpose() == null || uaslReservationInfo.getFlightPurpose().isEmpty()) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "FlightPurpose");
            log.error(errorMessage);
            result = false;
	    }
	    
	    if(uaslReservationInfo.getStatus() == null) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "Status");
            log.error(errorMessage);
            result = false;
	    }else if(uaslReservationInfo.getStatus().getValue() != STATUS_RESERVED
	    		&& uaslReservationInfo.getStatus().getValue() != STATUS_CANCELED
	    		&& uaslReservationInfo.getStatus().getValue() != STATUS_RESCINDED) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "Status");
            log.error(errorMessage);
            result = false;
	    }
	    
	    if(uaslReservationInfo.getReservedAt() == null || uaslReservationInfo.getReservedAt().isEmpty()) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "ReservedAt");
            log.error(errorMessage);
            result = false;
	    }else{
	    	if(!checktimeStampFormat(uaslReservationInfo.getReservedAt())) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "ReservedAt");
	            log.error(errorMessage);
	            result = false;
    		}
	    }
	    
	    if(uaslReservationInfo.getUpdatedAt() == null || uaslReservationInfo.getUpdatedAt().isEmpty()) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UpdatedAt");
            log.error(errorMessage);
            result = false;
	    }else{
	    	if(!checktimeStampFormat(uaslReservationInfo.getUpdatedAt())) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UpdatedAt");
	            log.error(errorMessage);
	            result = false;
    		}
	    }
	    
	    if(uaslReservationInfo.getOriginReservation() == null) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "OriginReservation");
            log.error(errorMessage);
            result = false;
	    }else {
	    	if(uaslReservationInfo.getOriginReservation().getReservationId() == null
	    			|| uaslReservationInfo.getOriginReservation().getReservationId().isEmpty()) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "ReservationId");
	            log.error(errorMessage);
	            result = false;
	    	}
	    	
	    	if(uaslReservationInfo.getOriginReservation().getUaslId() == null
	    			|| uaslReservationInfo.getOriginReservation().getUaslId().isEmpty()) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslId");
	            log.error(errorMessage);
	            result = false;
	    	}
	    	
	    	if(uaslReservationInfo.getOriginReservation().getAdministratorId() == null
	    			|| uaslReservationInfo.getOriginReservation().getAdministratorId().isEmpty()) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "AdministratorId()");
	            log.error(errorMessage);
	            result = false;
	    	}
	    	
	    	if(uaslReservationInfo.getOriginReservation().getUaslSections() == null
	    			|| uaslReservationInfo.getOriginReservation().getUaslSections().isEmpty()) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslSections");
	            log.error(errorMessage);
	            result = false;
	    	}else {
	    		for(UaslSectionEntity uaslSectonEntity : uaslReservationInfo.getOriginReservation().getUaslSections()) {
	    			if(uaslSectonEntity.getUaslSectionId() == null || uaslSectonEntity.getUaslSectionId().isEmpty()) {
	    				String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslSectionId");
	    	            log.error(errorMessage);
	    	            result = false;
	    			}
	    			
	    			if(uaslSectonEntity.getStartAt() == null || uaslSectonEntity.getStartAt().isEmpty()) {
	    				String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "StartAt");
	    	            log.error(errorMessage);
	    	            result = false;
	    			}else{
	    				if(!checktimeStampFormat(uaslSectonEntity.getStartAt())) {
	    					String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "StartAt");
	    		            log.error(errorMessage);
	    		            result = false;
	    	    		}
	    		    }
	    			
	    			if(uaslSectonEntity.getEndAt() == null || uaslSectonEntity.getEndAt().isEmpty()) {
	    				String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "EndAt");
	    	            log.error(errorMessage);
	    	            result = false;
	    			}else{
	    				if(!checktimeStampFormat(uaslSectonEntity.getEndAt())) {
	    					String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "EndAt");
	    		            log.error(errorMessage);
	    		            result = false;
	    	    		}
	    		    }
	    			
	    			if(uaslSectonEntity.getSequence() == null) {
	    				String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "Sequence");
	    	            log.error(errorMessage);
	    	            result = false;
	    			}
	    		}
	    	}
	    }
	    
	    if(uaslReservationInfo.getDestinationReservations() != null
    			&& !uaslReservationInfo.getDestinationReservations().isEmpty()) {
    		for(DestinationReservationEntity destinationReservationEntity : uaslReservationInfo.getDestinationReservations()) {
    			if(destinationReservationEntity.getReservationId() == null || destinationReservationEntity.getReservationId().isEmpty())  {
    				String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "ReservationId");
    	            log.error(errorMessage);
    	            result = false;
    			}
    			
    			if(destinationReservationEntity.getUaslId() == null || destinationReservationEntity.getUaslId().isEmpty())  {
    				String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslId");
    	            log.error(errorMessage);
    	            result = false;
    			}
    			
    			if(destinationReservationEntity.getAdministratorId() == null || destinationReservationEntity.getAdministratorId().isEmpty())  {
    				String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "AdministratorId");
    	            log.error(errorMessage);
    	            result = false;
    			}
    			
    			if(destinationReservationEntity.getUaslSections() == null
    					|| destinationReservationEntity.getUaslSections().isEmpty()) {
    				String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslSections");
    	            log.error(errorMessage);
    	            result = false;
    			}else {
    				for(DestinationUaslSectionEntity destinationUaslSectionEntity : destinationReservationEntity.getUaslSections()) {
    					if(destinationUaslSectionEntity.getUaslSectionId() == null
    							|| destinationUaslSectionEntity.getUaslSectionId().isEmpty() ) {
    						String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslSectionId");
    	    	            log.error(errorMessage);
    	    	            result = false;
    					}
    					
    					if(destinationUaslSectionEntity.getStartAt() == null || destinationUaslSectionEntity.getStartAt().isEmpty()) {
    						String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "StartAt");
    	    	            log.error(errorMessage);
    	    	            result = false;
    					}else{
    						if(!checktimeStampFormat(destinationUaslSectionEntity.getStartAt())) {
    				    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "StartAt");
    				            log.error(errorMessage);
    				            result = false;
    			    		}
    	    		    }
    					
    					if(destinationUaslSectionEntity.getEndAt() == null || destinationUaslSectionEntity.getEndAt().isEmpty()) {
    						String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "EndAt");
    	    	            log.error(errorMessage);
    	    	            result = false;
    					}else{
    						if(!checktimeStampFormat(destinationUaslSectionEntity.getEndAt())) {
    				    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "EndAt");
    				            log.error(errorMessage);
    				            result = false;
    			    		}
    	    		    }
    					
    					if(destinationUaslSectionEntity.getSequence() == null) {
    						String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "Sequence");
    	    	            log.error(errorMessage);
    	    	            result = false;
    					}
    				}
    			}
    		}
    	}
	    
		//チェック結果を返却
	  	return result;
	}
	
	/**
	 * 乗り入れ先航路の予約通知ペイロードの項目をチェック
	 * 
	 * @param destinationReservationNotification
	 */
	public boolean destinationReservationNotificationCheck(DestinationReservationNotification destinationReservationNotificationInfo) {
		//チェック結果
	    boolean result = true;
	    
	    if(destinationReservationNotificationInfo.getEventId() == null || destinationReservationNotificationInfo.getEventId().isEmpty()) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "EventId");
            log.error(errorMessage);
            result = false;
	    }
	    
	    if(destinationReservationNotificationInfo.getRequestId() == null || destinationReservationNotificationInfo.getRequestId().isEmpty()) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "RequestId");
            log.error(errorMessage);
            result = false;
	    }
	    
	    if(destinationReservationNotificationInfo.getReservationId() == null || destinationReservationNotificationInfo.getReservationId().isEmpty()) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "ReservationId");
            log.error(errorMessage);
            result = false;
	    }
	    
	    if(destinationReservationNotificationInfo.getOperatorId() == null || destinationReservationNotificationInfo.getOperatorId().isEmpty()) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "OperatorId");
            log.error(errorMessage);
            result = false;
	    }
	    
	    if(destinationReservationNotificationInfo.getFlightPurpose() == null || destinationReservationNotificationInfo.getFlightPurpose().isEmpty()) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "FlightPurpose");
            log.error(errorMessage);
            result = false;
	    }
	    
	    if(destinationReservationNotificationInfo.getStatus() == null) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "Status");
            log.error(errorMessage);
            result = false;
	    }else if(destinationReservationNotificationInfo.getStatus().getValue() != "RESERVED"
	    		&& destinationReservationNotificationInfo.getStatus().getValue() != "CANCELED"
	    		&& destinationReservationNotificationInfo.getStatus().getValue() != "RESCINDED") {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "Status");
            log.error(errorMessage);
            result = false;
	    }
	    
	    if(destinationReservationNotificationInfo.getReservedAt() == null || destinationReservationNotificationInfo.getReservedAt().isEmpty()) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "ReservedAt");
            log.error(errorMessage);
            result = false;
	    }else{
	    	if(!checktimeStampFormat(destinationReservationNotificationInfo.getReservedAt())) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "ReservedAt");
	            log.error(errorMessage);
	            result = false;
    		}
	    }
	    
	    if(destinationReservationNotificationInfo.getUpdatedAt() == null || destinationReservationNotificationInfo.getUpdatedAt().isEmpty()) {
	    	String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UpdatedAt");
            log.error(errorMessage);
            result = false;
	    }else{
	    	if(!checktimeStampFormat(destinationReservationNotificationInfo.getUpdatedAt())) {
	    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UpdatedAt");
	            log.error(errorMessage);
	            result = false;
    		}
	    }
	    
	    if(destinationReservationNotificationInfo.getUaslSections() == null
    			|| destinationReservationNotificationInfo.getUaslSections().isEmpty()) {
    		String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslSections");
            log.error(errorMessage);
            result = false;
    	}else {
    		for(DestinationUaslSectionEntity destinationUaslSectionEntity : destinationReservationNotificationInfo.getUaslSections()) {
    			if(destinationUaslSectionEntity.getUaslSectionId() == null || destinationUaslSectionEntity.getUaslSectionId().isEmpty()) {
    				String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslSectionId");
    	            log.error(errorMessage);
    	            result = false;
    			}
    			
    			if(destinationUaslSectionEntity.getStartAt() == null || destinationUaslSectionEntity.getStartAt().isEmpty()) {
    				String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "StartAt");
    	            log.error(errorMessage);
    	            result = false;
    			}else{
    				if(!checktimeStampFormat(destinationUaslSectionEntity.getStartAt())) {
    					String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "StartAt");
    		            log.error(errorMessage);
    		            result = false;
    	    		}
    		    }
    			
    			if(destinationUaslSectionEntity.getEndAt() == null || destinationUaslSectionEntity.getEndAt().isEmpty()) {
    				String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "EndAt");
    	            log.error(errorMessage);
    	            result = false;
    			}else{
    				if(!checktimeStampFormat(destinationUaslSectionEntity.getEndAt())) {
    					String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "EndAt");
    		            log.error(errorMessage);
    		            result = false;
    	    		}
    		    }
    			
    			if(destinationUaslSectionEntity.getSequence() == null) {
    				String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "Sequence");
    	            log.error(errorMessage);
    	            result = false;
    			}
    		}
    	}
		
		//チェック結果を返却
	  	return result;
	}
	
	/**
     * uaslInfoの必須な項目をチェック
     * 
     * @param uaslInfo
     */
    public boolean notNullCheckAirwayInfo(UaslInfo uaslInfo) {
        //チェック結果
        boolean result = true;
        
        if (uaslInfo.getRegisteredAt() == null || uaslInfo.getRegisteredAt().isEmpty()) {
            String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "RegisteredAt");
            log.error(errorMessage);
            result = false;
        }
        if (uaslInfo.getUaslAdministratorId() == null || uaslInfo.getUaslAdministratorId().isEmpty()) {
            String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslAdministratorId");
            log.error(errorMessage);
            result = false;
        }
        if(uaslInfo.getUasl() != null) {
            if (uaslInfo.getUasl().getUaslId() == null || uaslInfo.getUasl().getUaslId().isEmpty()) {
                String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslId");
                log.error(errorMessage);
                result = false;
            }
            if (uaslInfo.getUasl().getUaslName() == null || uaslInfo.getUasl().getUaslName().isEmpty()) {
                String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslName");
                log.error(errorMessage);
                result = false;
            }
            if (uaslInfo.getUasl().getFlightPurpose() == null || uaslInfo.getUasl().getFlightPurpose().isEmpty()) {
                String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "FlightPurpose");
                log.error(errorMessage);
                result = false;
            }
            if (uaslInfo.getUasl().getCreatedAt() == null || uaslInfo.getUasl().getCreatedAt().isEmpty()) {
                String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "CreatedAt");
                log.error(errorMessage);
                result = false;
            }
            if (uaslInfo.getUasl().getUpdatedAt() == null || uaslInfo.getUasl().getUpdatedAt().isEmpty()) {
                String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UpdatedAt");
                log.error(errorMessage);
                result = false;
            }
            //航路区画情報そのものが存在しない場合、後続チェックを行わずチェックNGとする。
            if (uaslInfo.getUasl().getUaslSections() == null || uaslInfo.getUasl().getUaslSections().isEmpty()) {
                String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslSectionsEntity");
                log.error(errorMessage);
                return false;
            }
            //セクションの件数分繰り返しチェックを行う。
            for (UaslSections uaslSections : uaslInfo.getUasl().getUaslSections()) {
                if (uaslSections.getUaslSectionId() == null || uaslSections.getUaslSectionId().isEmpty()) {
                    String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslSectionId");
                    log.error(errorMessage);
                    result = false;
                }
                if (uaslSections.getUaslSectionName() == null || uaslSections.getUaslSectionName().isEmpty()) {
                    String errorMessage = MessageUtils.getMessage("DR000E003", (Object) "UaslSectionName");
                    log.error(errorMessage);
                    result = false;
                }
            }
        }
        //チェック結果を返却
        return result;
    } 
}
