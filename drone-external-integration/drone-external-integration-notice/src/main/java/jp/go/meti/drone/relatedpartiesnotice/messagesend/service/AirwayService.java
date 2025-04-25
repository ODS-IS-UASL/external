package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwayInfo;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwaySection;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwaySectionEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwaySectionMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 航路情報をテーブルAirWayに保存するサービス
 */
@Slf4j
@Service
public class AirwayService {
    @Autowired
    private AirwayMapper airwayMapper;

    @Autowired
    private AirwaySectionMapper airwaySectionMapper;

    /** ユーザID値取得 */
    @Value("${systemuser}")
    private int userId;

    /**
     * 受信した航路情報、航路区画情報を保存する。
     * 
     * @param airwayInfo 航路情報
     * 
     */
    @Transactional
    public void saveAirway(AirwayInfo airwayInfo, String airwayId) {

    	AirwayEntity airway = new AirwayEntity();
    	//日時フォーマットが正しいかチェックする。
    	LocalDateTime registeredAt = convertTimestamp(airwayInfo.getRegisteredAt(), "RegisteredAt");
    	//更新日時に値がある場合は変換する。
    	LocalDateTime updatedAt = null;
    	if(airwayInfo.getUpdatedAt() != null && !airwayInfo.getUpdatedAt().isEmpty() ) {
    		updatedAt = convertTimestamp(airwayInfo.getUpdatedAt(), "UpdatedAt");
    	}
    	if(airwayInfo.getAirway() != null) {
    		LocalDateTime airwayCreatedAt = convertTimestamp(airwayInfo.getAirway().getCreatedAt(), "AirwayCreatedAt");
    		LocalDateTime airwayUpdatedAt = convertTimestamp(airwayInfo.getAirway().getUpdatedAt(), "AirwayUpdatedAt");
    		airway.setAirwayId(airwayInfo.getAirway().getAirwayId());
    		airway.setAirwayName(airwayInfo.getAirway().getAirwayName());
    		airway.setAirwayAdministratorId(airwayInfo.getAirwayAdministratorId());
    		airway.setStatus("1");
    		airway.setRegisteredAt(registeredAt);
    		airway.setUpdatedAt(updatedAt == null? registeredAt : updatedAt);
    		airway.setAirwayCreatedAt(airwayCreatedAt);
    		airway.setAirwayUpdatedAt(airwayUpdatedAt);
    		airway.setFlightPurpose(airwayInfo.getAirway().getFlightPurpose());
    		airway.setCreationId(userId);
    		airway.setUpdateId(userId);
    		//航路IDをもとに航路情報を取得する
    		AirwayEntity selectAirway = airwayMapper.getAirwayByAirwayId(airway.getAirwayId());

    		//航路IDと一致する航路情報がテーブルに存在しない場合、航路情報を登録する。
    		if(selectAirway == null) {
    			airwayMapper.insertAirway(airway);
    		}
    		else {
    			//航路情報が既に存在する場合は、更新する。
    			airwayMapper.updateByAirwayId(airway);
    		}

    		//すでに存在する航路区画情報は削除する。
    		airwaySectionMapper.deleteAirwaySection(airway.getAirwayId());
    		//航路区画の件数分、繰り返し航路区画情報を登録する。
    		for(AirwaySection airwaySection : airwayInfo.getAirway().getAirwaySections()) {
    			AirwaySectionEntity airwaySectionEntity = new AirwaySectionEntity();
    			airwaySectionEntity.setAirwayId(airwayInfo.getAirway().getAirwayId());
    			airwaySectionEntity.setAirwaySectionId(airwaySection.getAirwaySectionId());
    			airwaySectionEntity.setAirwaySectionName(airwaySection.getAirwaySectionName());
    			airwaySectionEntity.setCreationId(userId);
    			airwaySectionEntity.setUpdateId(userId);
    			airwaySectionMapper.insertAirwaySection(airwaySectionEntity);
    		}
    	}else {
    		airwayMapper.updateStatusByAirwayId(airwayId);
    		airwaySectionMapper.deleteAirwaySection(airwayId);
    	}

    }	

    /**
     * テーブルAirwayからデータ削除
     * 
     * @param airway 航路情報
     */
    @Transactional
    public void deleteAirway(AirwayEntity airway) {
        airwayMapper.deleteAirway(airway.getAirwayId());
    }	

    /**
     * 航路IDをもとにAirwayテーブルから航路情報を1件取得する
     * 
     * @param airwayId 航路ID
     * @return 航路情報
     */
    public AirwayEntity selectAirway(String airwayId){
        return airwayMapper.getAirwayByAirwayId(airwayId);

    }
    
	/**
     * @param timestampStr
     * @param fieldName
     * @return 変換後の日時オブジェクト
     */
    private LocalDateTime convertTimestamp(String timestampStr, String fieldName) {
        // インプット：yyyy-MM-dd'T'HH:mm:ssZ
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
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
