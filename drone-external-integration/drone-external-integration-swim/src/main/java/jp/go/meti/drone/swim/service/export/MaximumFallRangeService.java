package jp.go.meti.drone.swim.service.export;

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
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.model.commonmodel.CommonResponseNotFoundError;
import lombok.extern.slf4j.Slf4j;

/**
 * 航路画定から最大落下範囲を取得するサービス
 * 
 * @version 1.0
 */
@Slf4j
@Service
public class MaximumFallRangeService {

    // 外部API呼び出しに使用する RestTemplate
    private final RestTemplate restTemplate;

    // 外部APIのURL
    @Value("${external.api.max-fall-range.url.get}")
    private String url;

    /**
     * restTemplateの初期化
     * 
     * @param restTemplate HTTPリクエスト送信時に使用
     */
    public MaximumFallRangeService(@Qualifier("restTemplateSwim") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 航路画定API（最大落下範囲情報の取得）から1つの最大落下範囲を取得
     * 
     * @param maxFallRangeId 最大落下範囲のID
     * @param businessNumber 事業者ID
     * @return 1つの最大落下範囲
     * @throws CommonResponseNotFoundError リソースが見つからない
     * @throws CommonResponseBadRequestError リクエスト不正
     * @throws CommonResponseInternalServerError 接続先APIのエラー
     */
    public MaxFallRange getMaximumFallRange(String maxFallRangeId, String businessNumber)
        throws CommonResponseNotFoundError, CommonResponseBadRequestError, CommonResponseInternalServerError {
        try {
            log.debug(url);
            ResponseEntity<MaxFallRange> response;

            // HTTPヘッダ作成(JSON形式でやりとりする）
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // URLを組み立てる
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

            // 上記のURLの後ろにmaxFallRangeId, businessNumberをつける
            String uri = builder.pathSegment(maxFallRangeId, businessNumber).toUriString();

            // HTTPリクエストを作成
            HttpEntity<MaxFallRange> entity = new HttpEntity<>(headers);
            log.debug(uri);

            // 外部APIにGETリクエストを送信する
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, MaxFallRange.class);
            log.debug(response.toString());

            // 取得したデータを返す
            return response.getBody();

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
            log.error("通信に失敗しました。", e);
            throw new CommonResponseInternalServerError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "通信に失敗しました。");
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new CommonResponseBadRequestError(HttpStatus.BAD_REQUEST.value(), "パラメーターに誤りがあります。");
        }
    }
}
