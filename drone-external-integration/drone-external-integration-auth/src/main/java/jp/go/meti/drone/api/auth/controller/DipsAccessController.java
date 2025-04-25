package jp.go.meti.drone.api.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.go.meti.drone.api.auth.controller.model.DipsAccessResponse;
import jp.go.meti.drone.api.auth.controller.model.DipsTokenRequest;
import jp.go.meti.drone.api.auth.exception.DipsAccessTokenException;
import jp.go.meti.drone.api.auth.repository.entity.DipsAccessEntity;
import jp.go.meti.drone.api.auth.service.DipsAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DIPSアクセスコントローラクラス.
 * 
 * @version $Revision$
 */
@CrossOrigin
@RestController
@RequestMapping("${drone-route-base-path}")
@RequiredArgsConstructor
@Slf4j
public class DipsAccessController {

    /** DIPSアクセスサービス */
    private final DipsAccessService dipsAccessService;

    /** ドローン航路システムがDIPS APIを実行時に利用するクライアントID */
    @Value("${drone.client-id}")
    private String defaultClientId;

    /**
     * アクセストークン検証
     * <p>
     * 共通UIからログイン時およびDIPS API実行時におけるアクセストークンの検証を行う<br>
     * <code>
     * curl -v http://localhost:8283/external/api/v1/dipsTokenVerification -X POST
     * </code>
     * </p>
     * 
     * @return 応答(アクセストークン検証結果情報)
     */
    @PostMapping("/dipsTokenVerification")
    public ResponseEntity<DipsAccessResponse> dipsAccessVerify() {

        // アクセストークン検証実行
        try {
            dipsAccessService.tokenVerify(defaultClientId);
            DipsAccessResponse response = new DipsAccessResponse();
            response.setMessage("Token Verify Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        // アクセストークン検証失敗
        catch (DipsAccessTokenException e) {
            DipsAccessResponse response = new DipsAccessResponse();
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        // 失敗(システムエラー)
        catch (Exception e) {
            DipsAccessResponse response = new DipsAccessResponse();
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * アクセストークン保存
     * <p>
     * <code>
     * curl -v http://localhost:8283/external/api/v1/dipsToken -X PUT -H "Content-Type: application/json" -d '{"clientId": "2aa899fb", "accessToken": "xxx", "expiresIn": 10, "refreshExpiresIn": 600, "refreshToken": "yyy", "tokenType": "bearer", "idToken": "zzz", "notBeforePolicy": 0, "sessionState": "sss", "scope": "openid profile offline_access"}'
     * </code>
     * </p>
     * 
     * @param request 要求(クライアントIDに紐づくDIPSのアクセストークン取得レスポンス)
     * @return 応答(アクセストークン保存結果情報)
     */
    @PutMapping("/dipsToken")
    public ResponseEntity<DipsAccessResponse> dipsTokenSave(@RequestBody @Validated DipsTokenRequest request) {

        DipsAccessEntity insertTokenInfo = DipsAccessEntity.builder() //
            .clientId(request.getClientId()) //
            .accessToken(request.getAccessToken()) //
            .expiresIn(request.getExpiresIn()) //
            .refreshExpiresIn(request.getRefreshExpiresIn()) //
            .refreshToken(request.getRefreshToken()) //
            .tokenType(request.getTokenType()) //
            .idToken(request.getIdToken()) //
            .notBeforePolicy(request.getNotBeforePolicy()) //
            .sessionState(request.getSessionState()) //
            .scope(request.getScope()) //
            .build();

        try {
            dipsAccessService.save(insertTokenInfo);
            DipsAccessResponse response = new DipsAccessResponse();
            response.setMessage("Token Save Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        // アクセストークン保存失敗
        catch (DipsAccessTokenException e) {
            DipsAccessResponse response = new DipsAccessResponse();
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        // 失敗(システムエラー)
        catch (Exception e) {
            DipsAccessResponse response = new DipsAccessResponse();
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
