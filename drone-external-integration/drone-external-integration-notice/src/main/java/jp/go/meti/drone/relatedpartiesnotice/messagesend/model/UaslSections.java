package jp.go.meti.drone.relatedpartiesnotice.messagesend.model;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UaslSectionsEntity 航路区画
 */
@Builder
@Data
@NoArgsConstructor
public class UaslSections {

	// 航路区画ID
	private String uaslSectionId;

	// 航路区画名
	private String uaslSectionName;
	
    //航路区画に接している航路点ID
	private List<String> uaslPointIds;
	
    //航路区画に所属しているドローンポートID
	private List<String> droneportIds;
	/**
     * @param uaslSectionId
     * @param uaslSectionName
     * @param uaslPointIds
     * @param droneportIds
     */
    public UaslSections(String uaslSectionId, String uaslSectionName, List<String> uaslPointIds, List<String> droneportIds) {
        super();
        this.uaslSectionId = uaslSectionId;
        this.uaslSectionName = uaslSectionName;
        this.uaslPointIds = uaslPointIds;
        this.droneportIds = droneportIds;
    }

}
