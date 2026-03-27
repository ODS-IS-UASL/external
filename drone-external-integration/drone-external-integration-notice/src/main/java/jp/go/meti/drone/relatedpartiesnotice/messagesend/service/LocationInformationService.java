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

import jp.go.meti.drone.relatedpartiesnotice.messagesend.locationinfomodel.LocationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.locationinfomodel.dto.LocationGetResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 都道府県APIから都道府県情報取得するクラス
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class LocationInformationService {

    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${rest.api.location.url.get}")
    private String url;

    /**
     * 都道府県APIを呼び出して、都道府県情報を取得して、返却する
     * @param lat 緯度
     * @param lon 経度
     * @return 取得結果オブジェクト
     * 
     */
    public LocationGetResult getAddressByLatLon(double lon, double lat) {
        
        LocationGetResult result = new LocationGetResult();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "DroneExternalIntegration/1.0");
        // クエリパラメータ
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
            .queryParam("lat", lat)
            .queryParam("lon", lon)
            .queryParam("format", "json")
            .queryParam("zoom", "14");
        String uri = builder.toUriString();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        log.info("LocationInformationService RequestQueryParam is. lat: " + lat + " ,lon: " + lon + " ,format: json ,zoom: 14.");

        ResponseEntity<LocationEntity> response;
        try {
            // 都道府県情報取得(GET リクエスト)を送信
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, LocationEntity.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("HTTP Request Success.");
                log.info("Response : " + response.getBody().toString());
                result.setStatusCode(response.getStatusCode().value());
                result.setLocationEntity(response.getBody());
                return result;
            } else {
                log.error("HTTP Request Failure. STATUS_CODE: ", response.getStatusCode());
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
