package jp.go.meti.drone.relatedpartiesnotice.tenant.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *　事業者情報
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantInfoEntity {

	// 事業者ID
	private String operatorId;

	// 事業者名称
	private String operatorName;

	// 権限
	private String role;

}
