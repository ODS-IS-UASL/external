package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * UaslEntityInfo 航路情報
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class UaslInfo {
	
	// 航路運営者ID
	private String uaslAdministratorId;
	
	// 登録日時
	private String registeredAt;
	
	// 更新日時
	private String updatedAt;
	
	//航路情報
	private Uasl uasl;
    
}
