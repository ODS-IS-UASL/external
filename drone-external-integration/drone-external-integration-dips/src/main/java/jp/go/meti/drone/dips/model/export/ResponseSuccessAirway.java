package jp.go.meti.drone.dips.model.export;

import java.io.IOException;
import java.io.Serializable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseInternalServerError;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * レスポンスオブジェクト
 * 
 * @version 1.0 2024/11/01
 */
@Data
@Slf4j
public class ResponseSuccessAirway implements Serializable {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/** リソース */
	private Resource resource;

	/** ジップファイル名 */
	private String zipFileName;

	public ResponseSuccessAirway() {
	}

	public ResponseSuccessAirway(String zipName, Resource resource) {
		this.zipFileName = zipName;
		this.resource = resource;
	}

	/**
	 * ZIPファイルのダウンロードが成功のレスポンス
	 * 
	 * @return ResponseEntity<Resource>
	 */
	public ResponseEntity<?> successResponse() {
		try {
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
					.contentLength(resource.contentLength())
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFileName + "\"")
					.body(resource);
		} catch (IOException e) {
			String errorMessage = MessageUtils.getMessage("DRC01E001");
			log.error(errorMessage);
			CommonResponseInternalServerError internalServerError = new CommonResponseInternalServerError(
					HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);
			return internalServerError.errorResponse();
		}
	}

}
