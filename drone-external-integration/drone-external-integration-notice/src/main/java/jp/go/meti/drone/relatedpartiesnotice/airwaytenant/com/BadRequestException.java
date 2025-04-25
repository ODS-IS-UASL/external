package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.com;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * パラメータ不正用のクラス
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;
	
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
