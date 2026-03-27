package jp.go.meti.drone.swim.model.export;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * レスポンスオブジェクト
 */
@Slf4j
@Data
public class ResponseSuccessSwim {

    /** リソース */
    private Resource resource;

    /** ジップファイル名 */
    private String zipFileName;

    /**
     * フィールドに値をセット
     * 
     * @param zipName ZIPファイル名
     * @param resource リソース
     */
    public ResponseSuccessSwim(String zipName, Resource resource) {
        this.zipFileName = zipName;
        this.resource = resource;
    }

    /**
     * ZIPファイルのダウンロード成功のレスポンス
     * 
     * @return ResponseEntity<Resource>
     */
    public ResponseEntity<?> successResponse() {
        try {
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFileName + "\"")
                .body(resource);
        } catch (IOException e) {
            log.error(e.getMessage());
            CommonResponseInternalServerError internalServerError = new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return internalServerError.errorResponse();
        }
    }
}
