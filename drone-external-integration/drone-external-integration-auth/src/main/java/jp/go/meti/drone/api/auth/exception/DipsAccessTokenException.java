package jp.go.meti.drone.api.auth.exception;

import java.io.Serializable;

import jp.go.meti.drone.com.common.util.MessageUtils;
import lombok.Getter;

/**
 * DIPSアクセストークン例外クラス。
 * 
 * @version $Revision$
 */
public class DipsAccessTokenException extends RuntimeException {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 1L;

    /**
     * エラーコード(メッセージID)。
     */
    @Getter
    private final String code;

    /**
     * 埋め込み文字列。
     */
    @Getter
    private final transient Object[] args;

    /**
     * DIPSアクセストークン例外クラスのコンストラクタ。
     * 
     * @param code エラーコード(メッセージID)
     */
    public DipsAccessTokenException(String code) {
        this(code, new Serializable[0]);
    }

    /**
     * DIPSアクセストークン例外クラスのコンストラクタ。
     * 
     * @param code エラーコード(メッセージID)
     * @param args 埋め込み文字列
     */
    public DipsAccessTokenException(String code, Object[] args) {
        this(code, args, null);
    }

    /**
     * DIPSアクセストークン例外クラスのコンストラクタ。
     * 
     * @param code エラーコード(メッセージID)
     * @param args 埋め込み文字列
     * @param cause 原因となったエラーオブジェクト
     */
    public DipsAccessTokenException(String code, Object[] args, Throwable cause) {
        super(cause);
        this.code = code;
        this.args = args;
    }

    /**
     * メッセージ取得メソッド。
     * 
     * @return エラーコード(メッセージID)に紐づくメッセージ文字列。
     */
    @Override
    public String getMessage() {
        return MessageUtils.getMessage(code, args);
    }
}
