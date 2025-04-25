package jp.go.meti.drone.dips.model.export;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 標準ライブラリのバリデーションによる単項目チェッククラス
 * 
 * @version 1.0 2024/11/01
 */
@Data
public class RequestAirwayIdList {

	/** 航路IDリスト */
	@NotNull(message = "DRC01E003")
	@Size(min = 1, message = "DRC01E003")
	private List<@NotBlank(message = "DRC01E003") @Size(min = 1, max = 300, message = "DR002E002") String> airwayIdList;

}
