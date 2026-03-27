package jp.go.meti.drone.swim.service.export;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jp.go.meti.drone.swim.apimodel.export.pip.GetUserAttrRequest;
import jp.go.meti.drone.swim.apimodel.export.pip.TokenClientRequest;
import jp.go.meti.drone.swim.apimodel.export.pip.TokenClientResponse;
import jp.go.meti.drone.swim.apimodel.export.pip.UserAttrResponse;
import jp.go.meti.drone.swim.apimodel.export.pip.UserAttrResponse.Attribute;
import jp.go.meti.drone.swim.apimodel.export.pip.UserAttrResponseList;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import lombok.extern.slf4j.Slf4j;

/**
 * ユーザー情報取得サービス
 */
@Slf4j
@Service
public class PipUserAttributeService {

    private final RestTemplate restTemplate;

    @Value("${external.api.token-client.url.post}")
    private String tokenClientUrl;

    @Value("${external.api.token-client.api_key}")
    private String tokenApiKey;

    @Value("${external.api.token-client.client_id}")
    private String tokenClientId;

    @Value("${external.api.token-client.client_secret}")
    private String tokenClientSecret;

    @Value("${external.api.pip.url.post}")
    private String pipPostUrl;

    /**
     * コンストラクタ
     * 
     * @param restTemplate @Qualifier("restTemplateSwim")
     */
    public PipUserAttributeService(@Qualifier("restTemplateSwim") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * PIPからユーザ属性を取得、指定したoperatorIdのSWIM連携用事業者IDを返却
     * 
     * @param operatorId PIPに問い合わせる事業者ID（L3で事業者登録時に発行されたUUID）
     * @return SWIM連携用事業者ID, 未設定時はnull(null時は呼び出し側でハンドリング)
     * @throws CommonResponseInternalServerError バックエンド通信失敗時
     */
    public String getSwimId(String operatorId) throws CommonResponseInternalServerError {

        try {
            String accessToken = this.getAccessToken();
            log.debug("Using accessToken: " + accessToken);
            ResponseEntity<UserAttrResponseList> response;
            // header
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "bearer " + accessToken);
            String uri = pipPostUrl;

            // body
            GetUserAttrRequest body = new GetUserAttrRequest();
            body.setUserIdList(Arrays.asList(operatorId));
            HttpEntity<GetUserAttrRequest> entity = new HttpEntity<>(body, headers);

            log.debug(uri);
            response = restTemplate.exchange(uri, HttpMethod.POST, entity, UserAttrResponseList.class);
            log.debug(response.toString());

            Attribute target = null;
            for (UserAttrResponse res : response.getBody().getAttributeList()) {
                String id = res.getUserId();
                if (operatorId.equals(id)) {
                    if (res.getAttribute() != null) {
                        target = res.getAttribute();
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }

            if (target != null) {
                return target.getSwimOperatorId();
            } else {
                log.error("対象レコードなし（空返却） 事業者ID[" + operatorId + "]");
                return null;
            }

        } catch (HttpClientErrorException e) {
            // 400 Bad Request, 404 Not Found
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                // 対象レコードなし
                log.error("対象レコードなし（404返却） 事業者ID[" + operatorId + "]");
                return null;
            }

            log.error("通信に失敗しました。", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "通信に失敗しました。" + e.getStatusCode() + e
                    .getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            // 500 Internal Server Error
            log.error("接続先サーバーでエラーが発生しました", e.getStatusCode(), e.getResponseBodyAsString());
            throw new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "接続先サーバーでエラーが発生しました。" + e.getStatusCode() + e
                    .getResponseBodyAsString());
        } catch (RestClientException e) {
            log.error("通信に失敗しました。", e.getMessage());
            throw new CommonResponseInternalServerError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "通信に失敗しました。");
        }
    }

    /**
     * PIPからアクセストークンを取得し返却
     * 
     * @return
     * @throws CommonResponseInternalServerError 500系のHTTPレスポンス
     */
    private String getAccessToken() throws CommonResponseInternalServerError {
        log.debug("baseURL:" + tokenClientUrl);
        ResponseEntity<TokenClientResponse> response;
        try {
            // header
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("API-Key", tokenApiKey);
            String uri = tokenClientUrl;

            // body
            TokenClientRequest body = new TokenClientRequest();
            body.setClientId(tokenClientId);
            body.setClientSecret(tokenClientSecret);
            HttpEntity<TokenClientRequest> entity = new HttpEntity<>(body, headers);

            log.debug(uri);
            response = restTemplate.exchange(uri, HttpMethod.POST, entity, TokenClientResponse.class);
            log.debug(response.toString());

            return response.getBody().getData().getAccessToken();

        } catch (Exception e) {
            log.error("アクセストークン取得通信に失敗しました。", e);
            throw new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "アクセストークン取得通信に失敗しました。");
        }
    }
}
