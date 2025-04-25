package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AirwaySection 航路区画
 */
@Builder
@Data
@NoArgsConstructor
public class AirwaySection {

	// 航路区画ID
	private String airwaySectionId;

	// 航路区画名
	private String airwaySectionName;
	
    //航路区画に接している航路点ID
	private List<String> airwayJunctionIds;
	
    //航路区画に所属しているドローンポートID
	private List<String> droneportIds;
	/**
     * @param airwaySectionId
     * @param airwaySectionName
     * @param airwayJunctionIds
     * @param droneportIds
     */
    public AirwaySection(String airwaySectionId, String airwaySectionName, List<String> airwayJunctionIds, List<String> droneportIds) {
        super();
        this.airwaySectionId = airwaySectionId;
        this.airwaySectionName = airwaySectionName;
        this.airwayJunctionIds = airwayJunctionIds;
        this.droneportIds = droneportIds;
    }

}
