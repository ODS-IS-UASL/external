package jp.go.meti.drone.swim.service.export;

import java.util.List;
import java.util.Objects;

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
import org.springframework.web.util.UriComponentsBuilder;

import jp.go.meti.drone.swim.apimodel.export.MaxFallRange;
import jp.go.meti.drone.swim.apimodel.export.uasl.ResponseUasl;
import jp.go.meti.drone.swim.apimodel.export.uasl.ResponseUaslInfo;
import jp.go.meti.drone.swim.apimodel.export.uasl.Uasl;
import jp.go.meti.drone.swim.apimodel.export.uasl.UaslPoint;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseNotFoundError;
import lombok.extern.slf4j.Slf4j;

/**
 * 最大高度取得サービス（最大落下範囲に含まれる全航路から抽出）
 * 
 * @version 1.0
 */
@Slf4j
@Service
public class UaslMaxAltitudeService {

    private final RestTemplate restTemplate;

    @Value("${external.api.uasl.url.get}")
    private String url;

    @Value("${external.api.uasl.flightPurpose}")
    private String flightPurpose;

    /**
     * コンストラクタ
     * 
     * @param restTemplate @Qualifier("restTemplateSwim")
     */
    public UaslMaxAltitudeService(@Qualifier("restTemplateSwim") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 指定された最大落下範囲IDに紐づく全航路情報を航路画定から取得し、 航路情報取得レスポンスから内包する全航路の逸脱範囲（緯度,経度,高度）ジオメトリから最大高度(標高[M])を返却
     * 
     * @param maxFallRangeId 最大落下範囲ID
     * @return 最大落下範囲に含まれる全航路の最大高度：標高[M]
     * @throws CommonResponseNotFoundError リソースが見つからない
     * @throws CommonResponseBadRequestError リクエスト不正
     * @throws CommonResponseInternalServerError 接続先APIのエラー
     */
    public double getMaxHeight(String maxFallRangeId) throws CommonResponseNotFoundError, CommonResponseBadRequestError,
        CommonResponseInternalServerError {
        log.debug("baseURL:" + url);
        ResponseEntity<ResponseUasl> response;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

            String uri = builder.queryParam("maxFallRangeId", maxFallRangeId)
                .queryParam("all", false)
                .queryParam("flightPurpose", flightPurpose)
                .build()
                .toUriString();

            HttpEntity<MaxFallRange> entity = new HttpEntity<>(headers);
            log.debug(uri);
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, ResponseUasl.class);
            log.debug(response.toString());

            double max = this.getMaxHeight(response.getBody());

            log.info("getMaxHeight:" + max);
            return max;

        } catch (HttpClientErrorException e) {
            // 400 Bad Request, 404 Not Found
            log.error("Client error.", e.getStatusCode(), e.getResponseBodyAsString());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CommonResponseNotFoundError(HttpStatus.NOT_FOUND.value(), "指定された情報は取得できませんでした。");
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new CommonResponseBadRequestError(HttpStatus.BAD_REQUEST.value(), "パラメーターに誤りがあります。");
            }
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
     * 航路情報取得レスポンスから内包する全航路の逸脱範囲（緯度,経度,高度）ジオメトリから最大高度(標高[M])を抽出 {@link UaslPoint#getDeviationGeometry()}
     * 
     * @param top 航路情報取得レスポンス
     * @return 最大高度(標高[M])
     */
    private double getMaxHeight(ResponseUasl top) {
        double max = 0.0;
        for (ResponseUaslInfo uasls : top.getUasl()) {
            Uasl uasl = uasls.getUasl();
            Double pointsMax = uasl.getUaslPoints()
                .stream()
                .flatMap(o -> o.getDeviationGeometry().getCoordinates().stream())
                .flatMap(List::stream)
                .map(l -> l.size() > 2 ? l.get(2) : null)
                .filter(Objects::nonNull)
                .max(Double::compareTo)
                .orElse(null);

            log.debug("uasl:" + uasl.getUaslName() + " max:" + pointsMax);
            if (pointsMax != null && max < pointsMax) {
                max = pointsMax;
            }
        }
        return max;
    }
}
