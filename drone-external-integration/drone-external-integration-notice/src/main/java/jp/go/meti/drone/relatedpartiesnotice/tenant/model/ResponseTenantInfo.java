package jp.go.meti.drone.relatedpartiesnotice.tenant.model;


import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * 
 */

@Data
@Builder
public class ResponseTenantInfo  implements Serializable {
  
    // SerialVersion
    private static final long serialVersionUID = 1L;
    
    // 事業者ID
    private String operatorId;
    
    // 事業者名称    
    private String operatorName;

    // 権限情報リスト
    private List<String> roleList;  

    /**
     * @param operatorId
     * @param operatorName
     * @param roleList
     */
    public ResponseTenantInfo(String operatorId, String operatorName, List<String> roleList) {
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "TenantInfoResponse{" +
                "operatorId='" + operatorId + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", roleList=" + roleList.toString() +
                '}';
    }
}

