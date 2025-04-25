package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 航路事業者情報 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirwayTenantAssociationEntity {

    private String airwayId;

    private String operatorId;

    private Integer creationId;

    private LocalDateTime creationDatetime;

    private Integer updateId;

    private LocalDateTime updateDatetime;

}