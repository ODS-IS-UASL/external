package jp.go.meti.drone.relatedpartiesnotice.tenant.repository.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TenantRoleEntity {
	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column droneairway2.tenant_role.operator_id
	 *
	 * @mbg.generated Wed Dec 04 10:58:04 JST 2024
	 */
	private String operatorId;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column droneairway2.tenant_role.role
	 *
	 * @mbg.generated Wed Dec 04 10:58:04 JST 2024
	 */
	private String role;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column droneairway2.tenant_role.creation_id
	 *
	 * @mbg.generated Wed Dec 04 10:58:04 JST 2024
	 */
	private Integer creationId;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column droneairway2.tenant_role.creation_datetime
	 *
	 * @mbg.generated Wed Dec 04 10:58:04 JST 2024
	 */
	private LocalDateTime creationDatetime;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column droneairway2.tenant_role.update_id
	 *
	 * @mbg.generated Wed Dec 04 10:58:04 JST 2024
	 */
	private Integer updateId;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database column droneairway2.tenant_role.update_datetime
	 *
	 * @mbg.generated Wed Dec 04 10:58:04 JST 2024
	 */
	private LocalDateTime updateDatetime;
}