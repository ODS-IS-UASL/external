package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.userinfomodel.UserInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.userinfomodel.dto.UserAccessTokenGetResult;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.userinfomodel.dto.UserManagementGetResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ユーザ管理機能APIからユーザ情報取得するクラス
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class UserManagementService {
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private UserAccessTokenService userAccessTokenService;
    
    @Value("${rest.api.usermanagement.url.post}")
    private String url;

    /**
     * ユーザ管理機能APIを呼び出して、ユーザ情報を取得して、返却する
     * @param userIdList リクエストボディ
     * @return 取得結果オブジェクト
     * 
     */
    public UserManagementGetResult getUserAttr(List<String> userIdList) {
        
        UserManagementGetResult result = new UserManagementGetResult();
        
        //クライアントIDとクライアントシークレットを使用して、アクセストークン情報を取得する。
        UserAccessTokenGetResult userAccessTokenGetResult = userAccessTokenService.getAccessToken();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + userAccessTokenGetResult.getTokenClientOutputModel().getData().getAccessToken());
        
        // リクエストボディを作成する
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userIdList", userIdList);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<UserInfoEntity> response;
        
        try {
            // ユーザー情報取得(POST リクエスト)を送信
            response = restTemplate.exchange(url, HttpMethod.POST, entity, UserInfoEntity.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("HTTP Request Success.");
                result.setStatusCode(response.getStatusCode().value());
                result.setUserInfoEntity(response.getBody());
                return result;
            } else {
                log.error("HTTP Request Failure. STATUS_CODE: {}", response.getStatusCode());
                result.setStatusCode(response.getStatusCode().value());
                return result;
            }
        }catch(HttpClientErrorException e) {
            log.error("HTTP Request Failure. 400系エラー", e);
            // エラーレスポンス受信のエラーを返却する
            result.setStatusCode(e.getStatusCode().value());
            return result;
        }catch(HttpServerErrorException e) {
            log.error("HTTP Request Failure. 500系エラー", e);
            // エラーレスポンス受信のエラーを返却する
            result.setStatusCode(e.getStatusCode().value());
            return result;
        }catch(Exception e) {
            log.error("HTTP Request Failure. ", e);
            // エラーレスポンス受信のエラーを返却する
            result.setStatusCode(500);
            return result;
        }
    }
}
