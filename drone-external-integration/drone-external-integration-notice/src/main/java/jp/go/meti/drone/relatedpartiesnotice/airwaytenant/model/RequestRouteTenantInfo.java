package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.model;

import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登録対象の航路事業者情報
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestRouteTenantInfo implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /** 登録対象の航路ID */
    @NotEmpty(message = "DRC01E003")
    private String airwayId;

    /** 登録対象の事業者IDのリスト */
    @NotNull(message = "DRC01E003")
    @Size(min = 1, message = "DRC01E003")
    private List<@NotBlank(message = "DRC01E003") @Size(min = 1, max = 36, message = "DR000E006") String> relatedPartiesIdList;

}
