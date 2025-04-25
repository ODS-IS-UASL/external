package jp.go.meti.drone.com.common.exception;

/**
 * 次期ドローン情報基盤システム実行時例外クラス。
 * <p>
 * 実行時エラーを表す例外クラスです。
 * </p>
 *
 * @version $Revision$
 */
public class DroneRuntimeException extends RuntimeException {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 1L;

    /**
     * 次期ドローン情報基盤システム実行時例外クラスのコンストラクタ。
     *
     * @param message エラーメッセージ。
     */
    public DroneRuntimeException(String message) {
        super(message);
    }

    /**
     * 次期ドローン情報基盤システム実行時例外クラスのコンストラクタ。
     *
     * @param message エラーメッセージ。
     * @param cause 原因となったエラーオブジェクト
     */
    public DroneRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
