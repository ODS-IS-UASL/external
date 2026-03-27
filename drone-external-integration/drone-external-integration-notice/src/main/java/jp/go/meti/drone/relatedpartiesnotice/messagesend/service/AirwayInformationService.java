package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

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
import org.springframework.web.util.UriComponentsBuilder;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.airwayinfomodel.UaslTopEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.airwayinfomodel.dto.AirwayGetResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 航路画定APIから航路情報取得するクラス
 * 
 * @version 1.0 2025/12/11
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class AirwayInformationService {

    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${rest.api.airway.url.get}")
    private String url;

    /**
     * 航路画定機能APIを呼び出して、航路情報を取得して、返却する
     * @param uaslId 航路ID
     * @return 取得結果オブジェクト
     * 
     */
    public AirwayGetResult getAirwayInformation(String uaslId) {
        
        AirwayGetResult result = new AirwayGetResult();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        // クエリパラメータ
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        String uri = builder.queryParam("uaslId", uaslId).toUriString();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        log.info("AirwayInformationService RequestQueryParam is. uaslId: " + uaslId + ".");

        ResponseEntity<UaslTopEntity> response;
        try {
            // 航路情報取得(GET リクエスト)を送信
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, UaslTopEntity.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("HTTP Request Success.");
                log.info("Response : " + response.getBody().toString());
                result.setStatusCode(200);
                result.setUaslTopEntity(response.getBody());
                return result;
            } else {
                log.error("HTTP Request Failure. STATUS_CODE: ", response.getStatusCode());
                result.setStatusCode(response.getStatusCode().value());
                return result;
            }
        }catch(HttpClientErrorException e) {
            log.error("HTTP Request Failure. 400系エラー", e);
            // エラーレスポンス受信のエラーを返却する
            result.setStatusCode(400);
            return result;
        }catch(HttpServerErrorException e) {
            log.error("HTTP Request Failure. 500系エラー", e);
            // エラーレスポンス受信のエラーを返却する
            result.setStatusCode(500);
            return result;
        }catch(Exception e) {
            log.error("HTTP Request Failure. ", e);
            // エラーレスポンス受信のエラーを返却する
            result.setStatusCode(500);
            return result;
        }
    }
}
