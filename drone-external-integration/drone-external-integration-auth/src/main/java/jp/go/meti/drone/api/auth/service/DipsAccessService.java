package jp.go.meti.drone.api.auth.service;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.micrometer.common.util.StringUtils;
import jp.go.meti.drone.api.auth.exception.DipsAccessTokenException;
import jp.go.meti.drone.api.auth.repository.DipsAccessRepository;
import jp.go.meti.drone.api.auth.repository.entity.DipsAccessEntity;
import jp.go.meti.drone.api.auth.util.DipsAccessUtils;
import jp.go.meti.drone.com.common.date.SystemDate;
import jp.go.meti.drone.com.common.logging.ApplicationLogger;
import jp.go.meti.drone.com.common.logging.LoggerFactory;
import jp.go.meti.drone.com.common.web.WebClientTemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DIPSアクセスサービスクラス.
 * 
 * @version $Revision$
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DipsAccessService {

    /** ロガー */
    private final ApplicationLogger logger = LoggerFactory.getApplicationLogger(log);

    /** DIPSアクセスリポジトリ */
    private final DipsAccessRepository dipsAccessRepository;

    /** DIPS APIテンプレート */
    private final WebClientTemplate dipsWebClientTemplate;

    /** DIPS Tokenテンプレート */
    private final WebClientTemplate dipsTokenTemplate;

    /** アクセストークン取得(リフレッシュ)パス */
    private static final String REFRESH_PATH = "/token";

    /** grant_type */
    private static final String GRANT_TYPE = "grant_type";

    /** client_id */
    private static final String CLIENT_ID = "client_id";

    /** client_secret */
    private static final String CLIENT_SECRET = "client_secret";

    /** refresh_token */
    private static final String REFRESH_TOKEN = "refresh_token";

    /** scope */
    private static final String SCOPE = "scope";

    /** openid */
    private static final String OPENID = "openid";

    /** システム日時 */
    private final SystemDate systemDate;

    /** DIPSのクライアントID */
    @Value("${dips.token.refresh.request.client-id}")
    private String dipsClientId;

    /** DIPSのクライアントシークレット */
    @Value("${dips.refresh.request.client-secret}")
    private String dipsClientSecret;

    /** ドローン航路システムがDIPS APIを実行時に利用するクライアントID */
    @Value("${drone.client-id}")
    private String defaultClientId;

    /** DIPSトークンの有効期限チェックに使用する猶予時間（s） */
    @Value("${dips.token.expiration.buffer}")
    private long dipsTokenExpirationBuffer;

    /** DIPSリフレッシュトークンの有効期限（s） */
    @Value("${dips.refresh.token.expiration}")
    private Integer dipsRefreshTokenExpiration;

    /**
     * DIPS API実行(GET)
     * <p>
     * DIPS APIの実行URLを指定し、GETで実行する<br>
     * 引数:URLの指定は以下の通り呼び出すAPI固有のパスを指定する<br>
     * 例)機体情報一覧取得API<br>
     * ・/v1/aircrafts
     * </p>
     * 
     * @param url DIPS APIのURL
     * @return 応答
     */
    @Transactional
    public ResponseEntity<Object> dipsApiExecuteGet(String url) {
        return dipsApiExecuteGet(defaultClientId, url);
    }

    /**
     * DIPS API実行(GET)
     * <p>
     * 取得したDIPSアクセストークンを利用し、DIPS APIをGETで実行する<br>
     * 引数:URLの指定は以下の通り呼び出すAPI固有のパスを指定する<br>
     * 例)機体情報一覧取得API<br>
     * ・/v1/aircrafts
     * </p>
     * 
     * @param clientId クライアントID
     * @param url DIPS APIのURL
     * @return 応答
     */
    @Transactional
    public ResponseEntity<Object> dipsApiExecuteGet(String clientId, String url) {

        // トークン検証
        tokenVerify(clientId);

        // DIPSアクセストークン取得
        String accessToken = getDipsAccessToken(clientId);

        return dipsWebClientTemplate.get( //
            url, //
            httpHeaders -> httpHeaders.setBearerAuth(accessToken), //
            Object.class //
        );
    }

    /**
     * DIPS API実行(POST)
     * <p>
     * DIPS APIの実行URLとリクエストパラメータを指定し、POSTで実行する<br>
     * 引数:URLの指定は以下の通り呼び出すAPI固有のパスを指定する<br>
     * 例)飛行禁止エリア情報取得API<br>
     * ・/flight-prohibited-area/search
     * </p>
     * 
     * @param url DIPS APIのURL
     * @param request 要求
     * @return 応答
     */
    @Transactional
    public ResponseEntity<Object> dipsApiExecutePost(String url, Object request) {
        return dipsApiExecutePost(defaultClientId, url, request);
    }

    /**
     * DIPS API実行(POST)
     * <p>
     * 取得したDIPSアクセストークンを利用し、DIPS APIをPOSTで実行する<br>
     * 引数:URLの指定は以下の通り呼び出すAPI固有のパスを指定する<br>
     * 例)飛行禁止エリア情報取得API<br>
     * ・/flight-prohibited-area/search
     * </p>
     * 
     * @param clientId クライアントID
     * @param url DIPS APIのURL
     * @param request 要求
     * @return 応答
     */
    @Transactional
    public ResponseEntity<Object> dipsApiExecutePost(String clientId, String url, Object request) {

        // トークン検証
        tokenVerify(clientId);

        // DIPSアクセストークン取得
        String accessToken = getDipsAccessToken(clientId);

        return dipsWebClientTemplate.post( //
            url, //
            httpHeaders -> {
                httpHeaders.setBearerAuth(accessToken);
                httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            },
            request,
            Object.class);
    }

    /**
     * DIPSアクセストークンのDB存在確認
     * <p>
     * クライアントIDに紐づくトークン情報が存在するか確認する<br>
     * </p>
     * 
     * @param clientId クライアントID
     * @return true:存在する false:存在しない
     */
    private boolean existsByClientId(String clientId) {
        DipsAccessEntity tokenInfo = dipsAccessRepository.selectByPrimaryKey(clientId);
        return tokenInfo != null;
    }

    /**
     * DIPSアクセストークンをDBから取得する
     * 
     * @param clientId クライアントID
     * @return DIPSアクセストークン
     */
    private String getDipsAccessToken(String clientId) {

        DipsAccessEntity tokenInfo = dipsAccessRepository.selectByPrimaryKey(clientId);
        if (tokenInfo == null) {
            log.error("該当するトークン情報が取得できません。 クライアントID :" + clientId);
            throw new DipsAccessTokenException("e.auth.0001");
        }
        return tokenInfo.getAccessToken();

    }

    /**
     * DIPSアクセストークン保存
     * <p>
     * DIPSアクセスのための登録情報をDBに保存する<br>
     * </p>
     * 
     * @param dipsAccessEntity DIPSアクセスのための登録情報
     * @throws DipsAccessTokenException 例外
     */
    public void save(DipsAccessEntity dipsAccessEntity) throws DipsAccessTokenException {

        String clientId = dipsAccessEntity.getClientId();

        if (StringUtils.isEmpty(clientId)) {
            log.error("クライアントIDが取得できません。 クライアントID :" + clientId);
            throw new DipsAccessTokenException("e.auth.0002");
        }

        // トークン情報が登録されている場合には削除
        if (existsByClientId(clientId)) {
            delete(clientId);
        }

        try {
            int count = dipsAccessRepository.insert(dipsAccessEntity);
            log.debug("保存件数 :" + count + "件");
        } catch (Exception e) {
            log.error("DB登録時にエラー発生 クライアントID :" + clientId);
            throw new DipsAccessTokenException("e.auth.0003");
        }

    }

    /**
     * DIPSアクセストークン削除(物理)
     * 
     * @param clientId クライアントID
     * @throws DipsAccessTokenException DIPSアクセストークン例外
     */
    public void delete(String clientId) throws DipsAccessTokenException {

        try {
            int count = dipsAccessRepository.deleteByPrimaryKey(clientId);
            log.debug("削除件数 :" + count + "件");
        } catch (Exception e) {
            log.error("DB削除時にエラー発生 クライアントID :" + clientId);
            throw new DipsAccessTokenException("e.auth.0004");
        }

    }

    /**
     * アクセストークン検証
     * <p>
     * クライアントIDに紐づくトークン情報を取得し、有効かどうか検証する
     * </p>
     * 
     * @param clientId クライアントID
     */
    public void tokenVerify(String clientId) {

        // トークン情報取得
        DipsAccessEntity tokenInfo = dipsAccessRepository.selectByPrimaryKey(clientId);
        if (tokenInfo == null) {
            log.error("トークン情報が取得できません。 クライアントID :" + clientId);
            throw new DipsAccessTokenException("e.auth.0005");
        }

        // アクセストークン有効期限チェック
        Long longAcsessTokenIsuueAt;
        try {
            longAcsessTokenIsuueAt = DipsAccessUtils.getClaimFromJWT(tokenInfo.getAccessToken(), "iat");
        } catch (JWTDecodeException e) {
            throw new DipsAccessTokenException("e.auth.0008", null, e);
        }
        LocalDateTime dateTimeAcsessTokenIsuueAt = LocalDateTime.ofInstant(
            Instant.ofEpochSecond(longAcsessTokenIsuueAt),
            ZoneId.systemDefault());
        if (!isTokenValid(dateTimeAcsessTokenIsuueAt, tokenInfo.getExpiresIn())) {
            // リフレッシュトークン有効期限チェック
            Long longRefreshTokenIsuueAt;
            try {
                longRefreshTokenIsuueAt = DipsAccessUtils.getClaimFromJWT(tokenInfo.getRefreshToken(), "iat");
            } catch (JWTDecodeException e) {
                throw new DipsAccessTokenException("e.auth.0009", null, e);
            }
            LocalDateTime dateTimeRefreshTokenIsuueAt = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(longRefreshTokenIsuueAt),
                ZoneId.systemDefault());
            // DIPSへのアクセストークン取得リクエスト時、scopeの値がoffline_accessの場合、レスポンスパラメータrefresh_expires_inに0が設定され、リフレッシュトークンの有効期間が取得できない場合がある。
            // そのため、DIPS側にリフレッシュトークンの有効期間を確認し、環境変数に設定を可能とする。
            if (!isTokenValid(dateTimeRefreshTokenIsuueAt, dipsRefreshTokenExpiration)) {
                log.error("リフレッシュトークンの有効期限切れです。 クライアントID :" + clientId);
                throw new DipsAccessTokenException("e.auth.0006");
            } else {
                // リフレッシュトークンを使用してアクセストークンを再取得する
                RefreshTokenRequest request = RefreshTokenRequest.builder() //
                    .grantType(REFRESH_TOKEN)
                    .clientId(dipsClientId)
                    .clientSecret(dipsClientSecret)
                    .refreshToken(tokenInfo.getRefreshToken())
                    .scope(OPENID)
                    .build();
                MultiValueMap<String, String> requestMap = refreshTokenRequestConvertMap(request);

                // トークンリフレッシュ実行
                RefreshTokenResponse refreshTokenResponse = tokenRefresh(requestMap).getBody();

                if (refreshTokenResponse == null) {
                    log.error("更新されたトークン情報が取得できません。 クライアントID :" + dipsClientId);
                    throw new DipsAccessTokenException("e.auth.0007");
                }

                DipsAccessEntity updateTokenInfo = DipsAccessEntity.builder() //
                    .clientId(clientId) //
                    .accessToken(refreshTokenResponse.getAccessToken()) //
                    .expiresIn(refreshTokenResponse.getExpiresIn()) //
                    .refreshExpiresIn(refreshTokenResponse.getRefreshExpiresIn()) //
                    .refreshToken(refreshTokenResponse.getRefreshToken()) //
                    .tokenType(refreshTokenResponse.getTokenType()) //
                    .idToken(refreshTokenResponse.getIdToken()) //
                    .notBeforePolicy(refreshTokenResponse.getNotBeforePolicy()) //
                    .sessionState(refreshTokenResponse.getSessionState()) //
                    .scope(refreshTokenResponse.getScope()) //
                    .build();

                // DBに登録されているアクセストークン削除
                delete(updateTokenInfo.getClientId());

                // 再取得したアクセストークンをDBに保存
                save(updateTokenInfo);
            }
        }

    }

    /**
     * トークンのリクエスト情報をMultiValueMapに変換する
     * 
     * @param request アクセストークン取得(リフレッシュ)リクエストオブジェクト
     * @return MultiValueMapのアクセストークン取得(リフレッシュ)リクエストオブジェクト
     */
    private MultiValueMap<String, String> refreshTokenRequestConvertMap(RefreshTokenRequest request) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(GRANT_TYPE, request.getGrantType());
        map.add(CLIENT_ID, request.getClientId());
        map.add(CLIENT_SECRET, request.getClientSecret());
        map.add(REFRESH_TOKEN, request.getRefreshToken());
        map.add(SCOPE, request.getScope());
        return map;
    }

    /**
     * アクセストークン取得(リフレッシュ)
     * 
     * @param requestMap アクセストークン取得(リフレッシュ)リクエスト
     * @return 応答
     */
    private ResponseEntity<RefreshTokenResponse> tokenRefresh(MultiValueMap<String, String> requestMap) {
        return dipsTokenTemplate.post( //
            REFRESH_PATH, //
            httpHeaders -> httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE),
            requestMap, // リフレッシュ用リクエストオブジェクト
            RefreshTokenResponse.class);
    }

    /**
     * アクセストークンの有効期限チェック
     * 
     * @param startTime 開始時刻
     * @param expiresIn 有効時間(秒)
     * @return true:有効期限内 false:有効期限切れ
     */
    private boolean isTokenValid(LocalDateTime startTime, Integer expiresIn) {
        // 現在時刻
        LocalDateTime currentTime = systemDate.now();
        // 有効期限計算
        LocalDateTime expirationTime = startTime.plusSeconds(expiresIn);
        // 有効期限との比較用時刻
        LocalDateTime comparisonTime = currentTime.plusSeconds(dipsTokenExpirationBuffer);

        return comparisonTime.isBefore(expirationTime);
    }

    /**
     * アクセストークン取得(リフレッシュ)リクエストオブジェクト
     */
    @Getter
    @Builder
    private static class RefreshTokenRequest implements Serializable {

        /** SerialVersion */
        private static final long serialVersionUID = 1L;

        /** grant_type */
        private String grantType;

        /** client_id */
        private String clientId;

        /** client_secret */
        private String clientSecret;

        /** refresh_token */
        private String refreshToken;

        /** scope */
        private String scope;
    }

    /**
     * アクセストークン取得(リフレッシュ)レスポンスオブジェクト
     */
    @Getter
    private static class RefreshTokenResponse implements Serializable {

        /** SerialVersion */
        private static final long serialVersionUID = 1L;

        /** access_token */
        @JsonProperty("access_token")
        private String accessToken;

        /** expires_in */
        @JsonProperty("expires_in")
        private Integer expiresIn;

        /** refresh_expires_in */
        @JsonProperty("refresh_expires_in")
        private Integer refreshExpiresIn;

        /** refresh_token */
        @JsonProperty("refresh_token")
        private String refreshToken;

        /** token_type */
        @JsonProperty("token_type")
        private String tokenType;

        /** id_token */
        @JsonProperty("id_token")
        private String idToken;

        /** not-before-policy */
        @JsonProperty("not-before-policy")
        private Integer notBeforePolicy;

        /** session_state */
        @JsonProperty("session_state")
        private String sessionState;

        /** scope */
        @JsonProperty("scope")
        private String scope;
    }

}
