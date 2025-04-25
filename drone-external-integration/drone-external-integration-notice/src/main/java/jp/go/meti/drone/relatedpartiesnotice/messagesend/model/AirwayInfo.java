package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * AirwayInfo 航路情報
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class AirwayInfo {
	
	// 航路運営者ID
	private String airwayAdministratorId;
	
	// 登録日時
	private String registeredAt;
	
	// 更新日時
	private String updatedAt;
	
	//航路情報
	private Airway airway;
    
}
