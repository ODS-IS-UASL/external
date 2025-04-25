package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.model;

import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更新対象の航路事業者情報
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateRouteTenantInfo implements Serializable {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/** 更新の種類（例: 0：上書き更新、1：追加更新） */
	@NotBlank(message = "DRC01E003")
	@Pattern(regexp = "^[01]$", message = "DR000E009")
	private String updateType;

	/** 更新対象の航路ID */
	@NotBlank(message = "DRC01E003")
	private String airwayId;

	/** 更新対象の事業者のIDのリスト */
	private List<String> relatedPartiesIdList;

}
