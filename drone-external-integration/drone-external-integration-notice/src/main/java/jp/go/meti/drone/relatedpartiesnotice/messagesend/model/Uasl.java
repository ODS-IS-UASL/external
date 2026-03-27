package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航路。削除時にはNULLが指定される。
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Uasl {

	// 航路ID
	private String uaslId;

	// 航路名称
	private String uaslName;
	
	// 飛行目的
	private String flightPurpose;
	
	// 作成日時
	private String createdAt;
	
	// 更新日時
	private String updatedAt;
	
	//航路を利用可能なドローンの機体種別IDのリスト
	private List<Integer> droneList;
	
	// 航路点情報
	private List<UaslPoint> uaslPoint;

    // 航路区画情報
    private List<UaslSections> uaslSections;

}
