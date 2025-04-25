package jp.go.meti.drone.api.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;

import lombok.extern.slf4j.Slf4j;

/**
 * Dipsアクセスのユーティリティクラス
 * 
 * @version $Revision$
 */
@Slf4j
public class DipsAccessUtils {

    private DipsAccessUtils() {
        // ユーティリティクラスのため、インスタンス化禁止
    }

    /**
     * JWTから対象のクレームを取得する
     * 
     * @param dipsToken DIPSのトークン(アクセストークンおよびリフレッシュトークン)
     * @param claim クレーム
     * @return デコードした対象クレームの値
     */
    public static Long getClaimFromJWT(String dipsToken, String claim) {
        try {
            return JWT.decode(dipsToken).getClaim(claim).asLong();
        } catch (JWTDecodeException e) {
            log.error("DIPSのトークンからクレーム値の取得に失敗しました。");
            throw e;
        }
    }
}
