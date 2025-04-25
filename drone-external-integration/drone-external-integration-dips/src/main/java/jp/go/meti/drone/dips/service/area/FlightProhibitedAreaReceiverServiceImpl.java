package jp.go.meti.drone.dips.service.area;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.go.meti.drone.api.auth.service.DipsAccessService;
import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.dips.apimodel.dips.DroneRouteFlightProhibitedAreaResponse;
import jp.go.meti.drone.dips.apimodel.dips.FlightProhibitedAreaInfoDipsRequest;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.dips.model.commonmodel.FeaturesDips;
import jp.go.meti.drone.dips.model.commonmodel.FlightProhibitedAreaInfoDips;
import jp.go.meti.drone.dips.model.flightprohibited.FlightProhibitedAreaInfoRequest;
import jp.go.meti.drone.dips.service.common.CoordinateCheck;
import jp.go.meti.drone.dips.service.common.FlightProhibtedConst;
import jp.go.meti.drone.dips.service.common.StartTimeAndFinishTimeCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 飛行禁止エリア取得のサービス実装
 *
 * @version 1.0 2024/10/30
 */
@Slf4j
@Service("AreaReceiverService")
@RequiredArgsConstructor
public class FlightProhibitedAreaReceiverServiceImpl implements FlightProhibitedAreaReceiverService {

    /** DIPSアクセスサービスクラス */
    private final DipsAccessService dipsAccessService;

    private final ObjectMapper objectMapper;
    
    @Value("${rest.api.dips.area.url.get}")
    private String dipsFlightPrphibitedAreaUrl;
    
    /**
     * 飛行禁止エリア取得の業務処理
     * <p>
     * リクエストパラメータを加工し、DIPSより取得した結果チェックする。
     * </p>
     * 
     * @param searchModelApi 飛行禁止エリアの検索範囲
     * @return ステータス メッセージ データ詳細
     */
    @Override
    public DroneRouteFlightProhibitedAreaResponse flightProhibitedAreaReceiverInfo(
        FlightProhibitedAreaInfoRequest searchModelApi) {

        String messageTmp = "";
        if (ObjectUtils.isEmpty(searchModelApi.getFeatures()) || ObjectUtils.isEmpty(
            searchModelApi.getFeatures().getCoordinates())) {
            Object[] args = { "検索範囲 ジオメトリ（構成点）" };
            messageTmp = MessageUtils.getMessage("DRC01E003", args);
            throw new CommonResponseBadRequestError(400, messageTmp);
        }
        DroneRouteFlightProhibitedAreaResponse response = new DroneRouteFlightProhibitedAreaResponse();
        List<List<Double>> coordinates = searchModelApi.getFeatures().getCoordinates();
        int size = coordinates.size();
        if (size < FlightProhibtedConst.THREE_INTEGER) {
            // ・エラーメッセージ：ジオメトリ（構成点）が3点以上の構成点を指定してください。
            messageTmp = MessageUtils.getMessage("DR001E001");
            throw new CommonResponseBadRequestError(400, messageTmp);
        }
        Double longitudeTmp = null;
        Double latitudeTmp = null;
        List<Double> coordinate = null;
        // 経度、緯度は範囲値チェック
        for (int i = 0; i < size; i++) {
            coordinate = coordinates.get(i);
            //緯度、経度両方設定されていなければNG
            if(coordinate.size() !=2) {
                messageTmp = MessageUtils.getMessage("DR001E004");
                throw new CommonResponseBadRequestError(400, messageTmp);
            }
            longitudeTmp = coordinate.get(FlightProhibtedConst.ZERO_INTEGER);
            latitudeTmp = coordinate.get(FlightProhibtedConst.ONE_INTEGER);

            if (longitudeTmp == null || latitudeTmp == null) {
                messageTmp = MessageUtils.getMessage("DR001E004");
                throw new CommonResponseBadRequestError(400, messageTmp);
            }
            if (!CoordinateCheck.longitudeCheck(longitudeTmp)) {
                // 経度は｛｝～｛｝以内の値を設定してください。
                Object[] args = { FlightProhibtedConst.MIN_LONGITUDE, FlightProhibtedConst.MAX_LONGITUDE };
                messageTmp = MessageUtils.getMessage("DR001E002", args);
                log.error("Bad Request:" + messageTmp);
                throw new CommonResponseBadRequestError(400, messageTmp);
            } else if (!CoordinateCheck.latitudeCheck(latitudeTmp)) {
                // "緯度は%d～%d以内の値を設定してください。"
                Object[] args = { FlightProhibtedConst.MIN_LATITUDE, FlightProhibtedConst.MAX_LATITUDE };
                messageTmp = MessageUtils.getMessage("DR001E003", args);
                log.error("Bad Request:" + messageTmp);
                throw new CommonResponseBadRequestError(400, messageTmp);
            }
        }

        // 飛行禁止エリア情報取得 Dips
        FlightProhibitedAreaInfoDipsRequest searchModelDips = new FlightProhibitedAreaInfoDipsRequest();

        // ジオメトリタイプ、検索範囲の経度、緯度リスト設定
        FeaturesDips featuresDipsReq = new FeaturesDips();

        List<List<Double>> searchCoordinates = searchModelApi.getFeatures().getCoordinates();

        featuresDipsReq.setCoordinates(searchCoordinates);
        featuresDipsReq.setType(FlightProhibtedConst.TYPE);
        searchModelDips.setFeatures(featuresDipsReq);

        // 飛行禁止エリア情報設定
        FlightProhibitedAreaInfoDips flightProhibitedAreaInfo = new FlightProhibitedAreaInfoDips();
        List<Integer> flightProhibitedAreaTypeId = Arrays.asList(1, 2, 5, 6, 7, 8, 9, 10, 11);
        flightProhibitedAreaInfo.setFlightProhibitedAreaTypeId(flightProhibitedAreaTypeId);
        flightProhibitedAreaInfo.setStartTime(
            StartTimeAndFinishTimeCheck.startTimeCheck(searchModelApi.getStartTime()));
        flightProhibitedAreaInfo.setFinishTime(
            StartTimeAndFinishTimeCheck.finishTimeCheck(searchModelApi.getFinishTime()));
        searchModelDips.setFlightProhibitedAreaInfo(flightProhibitedAreaInfo);

        log.debug("searchModelDips:" + searchModelDips);

        // 飛行禁止エリア情報取得Dips_APIを呼び出し
        try {
        	ResponseEntity<Object> responseEntity = dipsAccessService.dipsApiExecutePost(
        	    dipsFlightPrphibitedAreaUrl,
        			searchModelDips);
        	if (responseEntity.getStatusCode() == HttpStatus.OK) {
        		log.info("HTTP Request Success.");
        		DroneRouteFlightProhibitedAreaResponse getDipsResult = objectMapper.convertValue(
        		    responseEntity.getBody(), DroneRouteFlightProhibitedAreaResponse.class);
        		response = DipsResponseCheck.check(getDipsResult);
        	} else {
        		log.error("HTTP Request Failure. STATUS_CODE: "+ responseEntity.getStatusCode());
        		log.error("Error Response Info. " +  responseEntity.getBody());
        		// エラーレスポンス受信のエラーを返却する
        		messageTmp = MessageUtils.getMessage("DRC01E001");
        		throw new CommonResponseInternalServerError(responseEntity.getStatusCode().value(), messageTmp);
        	}
        }catch(HttpClientErrorException e) {
    		log.error("HTTP Request Failure. 400系エラー", e);
    		// エラーレスポンス受信のエラーを返却する
    		messageTmp = MessageUtils.getMessage("DRC01E001");
        	throw new CommonResponseInternalServerError(e.getStatusCode().value(), messageTmp);
    	}catch(HttpServerErrorException e) {
    		log.error("HTTP Request Failure. 500系エラー", e);
    		// エラーレスポンス受信のエラーを返却する
    		messageTmp = MessageUtils.getMessage("DRC01E001");
        	throw new CommonResponseInternalServerError(e.getStatusCode().value(), messageTmp);
    	}catch(CommonResponseInternalServerError e) {
        	throw e;
    	}catch (Exception e) {
        	log.error("HTTP Request Failure. ", e);
        	// エラーレスポンス受信のエラーを返却する
        	messageTmp = MessageUtils.getMessage("DRC01E001");
        	throw new CommonResponseInternalServerError(500, messageTmp);
        }

        log.debug("DroneRouteFlightProhibitedAreaResponse:", response);
        return response;
    }

}
