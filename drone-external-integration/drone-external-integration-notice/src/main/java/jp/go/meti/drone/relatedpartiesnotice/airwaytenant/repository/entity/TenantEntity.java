package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 事業者情報
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantEntity {

    private String operatorId;

    private String operatorName;

    private String userType;

    private String notificationType;

    private Integer creationId;

    private LocalDateTime creationDatetime;

    private Integer updateId;

    private LocalDateTime updateDatetime;

}