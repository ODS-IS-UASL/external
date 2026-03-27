package jp.go.meti.drone.dips.com;


/**
 * 戻り値チェック異常系のクラス
 * 
 * @version 1.0 2025/09/22
 */

@SuppressWarnings("javadoc")
public class ResponseValidatorException extends RuntimeException {
	/**
	 * シリアルバージョンUID。
	 */
	private static final long serialVersionUID = 1L;
	
    
    public ResponseValidatorException(String message) {
        super(message);
    }

    public ResponseValidatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
