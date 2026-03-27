package jp.go.meti.drone.dips.service.flightplan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.go.meti.drone.api.auth.exception.DipsAccessTokenException;
import jp.go.meti.drone.api.auth.service.DipsAccessService;
import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.dips.com.RequestFlightPlanValidator;
import jp.go.meti.drone.dips.com.ResponseFlightPlanValidator;
import jp.go.meti.drone.dips.com.ResponseValidatorException;
import jp.go.meti.drone.dips.model.flightplan.AircraftInfo;
import jp.go.meti.drone.dips.model.flightplan.Contact;
import jp.go.meti.drone.dips.model.flightplan.DroneRouteFlightPlanInfoRequest;
import jp.go.meti.drone.dips.model.flightplan.DroneRouteFlightPlanInfoResponse;
import jp.go.meti.drone.dips.model.flightplan.FlightPermitApplicationInfo;
import jp.go.meti.drone.dips.model.flightplan.FlightPlan;
import jp.go.meti.drone.dips.model.flightplan.FlyRoute;
import jp.go.meti.drone.dips.model.flightplan.Geometry;
import jp.go.meti.drone.dips.model.flightplan.PilotInfo;
import jp.go.meti.drone.dips.model.flightplan.Reporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DIPS飛行プラン取得サービス
 * 
 * @version 1.0 2025/9/16
 */
@Service("FlightPlanService")
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("javadoc")
public class FlightPlanServiceImpl implements FlightPlanService{
    
    /** DIPSアクセスサービスクラス */
    private final DipsAccessService dipsAccessService;

    private final ObjectMapper objectMapper;
    
    @Value("${rest.api.dips.flightPlan.url.get}")
    private String dipsFlightPlanUrl;
    
    
    /** パラメータ値(検索範囲)設定 */
    private static final String FEATURE = "検索範囲";
    
    /** パラメータ値(ジオメトリタイプ)設定 */
    private static final String TYPE = "ジオメトリタイプ";
    
    /** パラメータ値(ジオメトリ（中心点）)設定 */
    private static final String CNETER = "ジオメトリ（中心点）";
    
    /** パラメータ値(半径)設定 */
    private static final String RADIUS = "半径";
    
    /** パラメータ値(ジオメトリ（構成点） )設定 */
    private static final String COORDINATES = "ジオメトリ（構成点）";

    /** パラメータ値(検索期間(FROM))設定 */
    private static final String STARTTIME = "検索期間(FROM)";

    /** パラメータ値(検索期間(TO))設定 */
    private static final String FINISHTIME = "検索期間(TO)";

    /** パラメータ値(更新時刻)設定 */
    private static final String UPDATETIME = "更新時刻";
    
    /** リトライ回数取得 */
    @Value("${retryCntMax}")
    private int retryCntMax;
    
    public DroneRouteFlightPlanInfoResponse flightPlanInfoReceiver(
        DroneRouteFlightPlanInfoRequest droneRouteFlightPlanInfoRequest) throws Exception {
        log.info("DIPS飛行プラン取得サービス開始します。");
          
        //入力値チェック
        try {
            requestCheck(droneRouteFlightPlanInfoRequest);
            log.info("DroneRouteFlightPlanInfoRequestチェック後:" + HtmlUtils.htmlEscape(droneRouteFlightPlanInfoRequest.toString()));
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
        
        DroneRouteFlightPlanInfoResponse flightPlanInfoResponse = new DroneRouteFlightPlanInfoResponse();
        String messageTmp = "";
        int sendCnt = 0;

        do {
            sendCnt++;
            try {
                ResponseEntity<Object> responseEntity = dipsAccessService.dipsApiExecutePost(
                    dipsFlightPlanUrl,
                    droneRouteFlightPlanInfoRequest);
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    flightPlanInfoResponse = objectMapper.convertValue(
                        responseEntity.getBody(),
                        DroneRouteFlightPlanInfoResponse.class);                    
                    log.info("DroneRouteFlightPlanInfoResponseチェック前:"+ flightPlanInfoResponse);
                    // レスポンス戻り値チェック
                    flightPlanInfoResponse = responseCheck(flightPlanInfoResponse);
                    log.info("DroneRouteFlightPlanInfoResponseチェック後:"+ flightPlanInfoResponse);
                    log.info("DIPS飛行プラン取得サービス終了します。");
                    return flightPlanInfoResponse;
                } else {
                    log.error("HTTP Request Failure. STATUS_CODE: ", responseEntity.getStatusCode());
                }
            } catch (DipsAccessTokenException e) {
                // DIPSアクセストークン例外
                messageTmp = MessageUtils.getMessage("DR005E010");
                log.error(messageTmp);
                Thread.sleep(5000);
            }
            
        } while (retryCntMax >= sendCnt);
        
        Object[] args = {messageTmp};
        throw new DipsAccessTokenException("DR005E010" ,args);

    }
    

    /**
     * 入力値チェック
     * @param droneRouteFlightPlanInfoRequest　リクエスト
     * @throws Exception
     */
    public void requestCheck(DroneRouteFlightPlanInfoRequest droneRouteFlightPlanInfoRequest) throws BadRequestException {
        Geometry getFeatures = droneRouteFlightPlanInfoRequest.getFeatures();
        
        // 検索範囲必須チェック
        RequestFlightPlanValidator.validateRequired(getFeatures, FEATURE);
        
        // ジオメトリタイプチェック
        RequestFlightPlanValidator.validateRequired(getFeatures.getType(), TYPE);
        RequestFlightPlanValidator.validateGeometryType(getFeatures.getType());
        
        // ジオメトリ（中心点） 相関チェック
        RequestFlightPlanValidator.validateCircleGeometry(
            getFeatures.getType(),
            getFeatures.getCenter(),
            getFeatures.getRadius(),
            CNETER,
            RADIUS);
        
        // ジオメトリ（構成点） 相関チェック
        RequestFlightPlanValidator.validatePolygonGeometry(
            getFeatures.getType(),
            getFeatures.getCoordinates(),
            COORDINATES);
        
        // 検索対象利用者チェック
        RequestFlightPlanValidator.validateUserTarget(droneRouteFlightPlanInfoRequest.getAllFlightPlan());
        
        // 検索期間(FROM)フォーマットチェック
        RequestFlightPlanValidator.validateDateTimeFormat(droneRouteFlightPlanInfoRequest.getStartTime(),STARTTIME);
        // 検索期間(FROM)は昨日よりも後の日時チェック
        RequestFlightPlanValidator.validateMinusDays(droneRouteFlightPlanInfoRequest.getStartTime(),STARTTIME);
        
        // 検索期間(TO)フォーマットチェック
        RequestFlightPlanValidator.validateDateTimeFormat(droneRouteFlightPlanInfoRequest.getFinishTime(),FINISHTIME);
                
        String start = droneRouteFlightPlanInfoRequest.getStartTime();
        String end = droneRouteFlightPlanInfoRequest.getFinishTime();
        // 検索期間(FROM,TO)関連チェック
        if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)) {
            RequestFlightPlanValidator.validateStartBeforeEnd(start,end);
        }else if (StringUtils.isNotEmpty(start)) { //開始時刻指定して、終了時刻指定してない
            droneRouteFlightPlanInfoRequest.setFinishTime(getStartEnd(start,end));
        }else if(StringUtils.isNotEmpty(end)) {  // 終了時刻指定して、開始時刻指定してない
            droneRouteFlightPlanInfoRequest.setStartTime(getStartEnd(start,end));
        }
              
        // 更新時刻チェック
        RequestFlightPlanValidator.validateUpdateTime(droneRouteFlightPlanInfoRequest.getUpdateTime(),UPDATETIME);
        
    }
    
    /**
     * 検索開始時刻と検索終了時刻設定
     * @param start
     * @param end
     * @return
     */
    public String getStartEnd(String start, String end){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmm");
            LocalDateTime startTime = null;
            LocalDateTime endTime = null;
            if(StringUtils.isEmpty(start)) {
                endTime = LocalDateTime.parse(end, formatter);
                startTime = endTime.minusHours(23).minusMinutes(59);
                return startTime.format(formatter);
            }else if(StringUtils.isEmpty(end)) {
                startTime = LocalDateTime.parse(start, formatter);
                endTime = startTime.plusHours(23).plusMinutes(59);
                return endTime.format(formatter);
            }else {
                return null;
            }
        } catch (DateTimeParseException e) {
            throw e;
        }
    }
    
    /**
     * 戻り値チェック
     * @param droneRouteFlightPlanInforesponse　　レスポンス
     * @throws Exception
     */
    public DroneRouteFlightPlanInfoResponse responseCheck(DroneRouteFlightPlanInfoResponse droneRouteFlightPlanInforesponse) throws Exception {
        
        List<FlightPlan> newFlightPlanInfos = new ArrayList<>();
        List<FlightPlan> flightPlanInfos = droneRouteFlightPlanInforesponse.getFlightPlanInfo();
        if (flightPlanInfos != null) {
            for (FlightPlan flightPlan : flightPlanInfos) {
                if (flightPlan != null) {
                    try {               
                        //flightPlanId 必須チェック
                        ResponseFlightPlanValidator.validateRequired(flightPlan.getFlightPlanId(),"flightPlanId");
                        
                        //flightPurpose 数値(1~16)の範囲チェック
                        ResponseFlightPlanValidator.validateIntegerListValueRange(flightPlan.getFlightPurpose(),1,16,"flightPurpose");
                        
                        //othergyomutext チェック
                        ResponseFlightPlanValidator.validatePurpose(flightPlan.getFlightPurpose(),13,flightPlan.getOthergyomutext(),"flightPurpose","othergyomutext");
                
                        //othergyomugaitext チェック
                        ResponseFlightPlanValidator.validatePurpose(flightPlan.getFlightPurpose(),16,flightPlan.getOthergyomugaitext(),"flightPurpose","othergyomugaitext");
                        
                        //flightAirspace 数値(1~3)の範囲チェック 
                        ResponseFlightPlanValidator.validateIntegerListValueRange(flightPlan.getFlightAirspace(),1,3,"flightAirspace");
                        
                        //flightType 数値(1~6)の範囲チェック 
                        ResponseFlightPlanValidator.validateIntegerListValueRange(flightPlan.getFlightType(),1,6,"flightType");
                        
                        //startTime チェック
                        ResponseFlightPlanValidator.validateRequired(flightPlan.getStartTime(),"startTime");
                        ResponseFlightPlanValidator.validateDateTimeFormat(flightPlan.getStartTime(),"startTime");
                        
                        //finishTime  チェック
                        ResponseFlightPlanValidator.validateRequired(flightPlan.getFinishTime(),"finishTime");
                        ResponseFlightPlanValidator.validateDateTimeFormat(flightPlan.getFinishTime(),"finishTime");
                        
                        //plannedMaxTime チェック
                        ResponseFlightPlanValidator.validateRequired(flightPlan.getPlannedMaxTime(),"plannedMaxTime");
                        ResponseFlightPlanValidator.validateNumberWithStep(flightPlan.getPlannedMaxTime(), 5, 1440, 5, "plannedMaxTime");
                        
                        //plannedFlightTime チェック
                        ResponseFlightPlanValidator.validateRequired(flightPlan.getPlannedFlightTime(),"plannedMaxTime");
                        ResponseFlightPlanValidator.validateNumberWithStep(flightPlan.getPlannedFlightTime(), 5, 1440, 5, "plannedFlightTime");
                        
                        //flightSpeed チェック
                        ResponseFlightPlanValidator.validateRequired(flightPlan.getFlightSpeed(),"flightSpeed");
                        
                        //flightAltitude チェック
                        ResponseFlightPlanValidator.validateRequired(flightPlan.getFlightAltitude(),"flightAltitude");
                        
                        FlyRoute flyRoute = flightPlan.getFlyRoute();
                        //flyRoute チェック
                        ResponseFlightPlanValidator.validateRequired(flyRoute,"flyRoute");
                        
                        //flyRoute.type チェック
                        ResponseFlightPlanValidator.validateRequired(flyRoute.getType(),"flyRoute.type");
                        ResponseFlightPlanValidator.validateAllowedValues(flyRoute.getType(), Set.of("Circle", "Polygon"), "flyRoute.type");
                        
                        // ジオメトリ（中心点） 相関チェック
                        ResponseFlightPlanValidator.validateCircleGeometry(flyRoute.getType(),flyRoute.getCenter(),flyRoute.getRadius(),"flyRoute.center","flyRoute.radius");
                        
                        // ジオメトリ（構成点） 相関チェック
                        ResponseFlightPlanValidator.validatePolygonGeometry(flyRoute.getType(), flyRoute.getCoordinates(),"flyRoute.coordinates");
                        
                        //riskMitigationOnsiteControl チェック
                        ResponseFlightPlanValidator.validateAllowedValues(flightPlan.getRiskMitigationOnsiteControl(), Set.of("0", "1"), "riskMitigationOnsiteControl");

                        //riskMitigationOnsiteControlL3 チェック
                        ResponseFlightPlanValidator.validateAllowedValues(flightPlan.getRiskMitigationOnsiteControlL3(), Set.of("0", "1"), "riskMitigationOnsiteControlL3");

                        //riskMitigationOnsiteControl2 チェック
                        ResponseFlightPlanValidator.validateAllowedValues(flightPlan.getRiskMitigationOnsiteControl2(), Set.of("0", "1"), "riskMitigationOnsiteControl2");

                        //exceptionalConditionsMooring チェック
                        ResponseFlightPlanValidator.validateAllowedValues(flightPlan.getExceptionalConditionsMooring(), Set.of("0", "1"), "exceptionalConditionsMooring");
                        
                        //insuranceInformation.insuranceAbility チェック
                        if(flightPlan.getInsuranceInformation() != null) {
                            ResponseFlightPlanValidator.validateAllowedValues(flightPlan.getInsuranceInformation().getInsuranceAbility(), Set.of("0", "1"), "insuranceInformation.insuranceAbility");
                        }
                        
                        Reporter reporter = flightPlan.getReporter();
                        Set<String> extraAllowed = new HashSet<>(Arrays.asList("99"));
                        if (reporter != null) {
                            //reporter.contactReporterFlag チェック         
                            ResponseFlightPlanValidator.validateAllowedValues(reporter.getContactReporterFlag(), Set.of("0", "1"), "reporter.contactReporterFlag");

                            Contact contactReporter = reporter.getContactReporter();
                            if (contactReporter != null) {
                                //reporter.contactReporter.country チェック
                                ResponseFlightPlanValidator.validateCodeInRangeWithExtras(contactReporter.getCountry(), 1, 199, null, "reporter.contactReporter.country","%03d～%03d");
                                
                                //reporter.contactReporter.prefectures チェック
                                ResponseFlightPlanValidator.validateCodeInRangeWithExtras(contactReporter.getPrefectures(), 1, 49, extraAllowed, "reporter.contactReporter.prefectures","%02d～%02d");
                                
                                //reporter.contactReporter.telephoneCountry チェック
                                ResponseFlightPlanValidator.validateCodeInRangeWithExtras(contactReporter.getTelephoneCountry(), 1, 199, null, "reporter.contactReporter.telephoneCountry","%03d～%03d");     
                            }
                        }
                       
                        List<PilotInfo> pilotInfos = flightPlan.getPilotInfo();
                        if (pilotInfos != null) {
                            for (PilotInfo pilotInfo : pilotInfos) {
                                if (pilotInfo != null) {
                                    //pilotInfo.contactPilotFlag チェック
                                    ResponseFlightPlanValidator.validateAllowedValues(pilotInfo.getContactPilotFlag(), Set.of("0", "1"), "pilotInfo.contactPilotFlag");
                                    
                                    Contact contactPilot = pilotInfo.getContactPilot();
                                    if (contactPilot != null) {
                                        //pilotInfo.contactPilot.country チェック
                                        ResponseFlightPlanValidator.validateCodeInRangeWithExtras(contactPilot.getCountry(), 1, 199, null, "pilotInfo.contactPilot.country","%03d～%03d");
                                        
                                        //pilotInfo.contactPilot.prefectures チェック
                                        ResponseFlightPlanValidator.validateCodeInRangeWithExtras(contactPilot.getPrefectures(), 1, 49, extraAllowed, "pilotInfo.contactPilot.prefectures","%02d～%02d");
                                        
                                        //pilotInfo.contactPilot.telephoneCountry チェック
                                        ResponseFlightPlanValidator.validateCodeInRangeWithExtras(contactPilot.getTelephoneCountry(), 1, 199, null, "pilotInfo.contactPilot.telephoneCountry","%03d～%03d");   
                                    }

                                    //pilotInfo.firstClass チェック
                                    ResponseFlightPlanValidator.validateAllowedValues(pilotInfo.getFirstClass(), Set.of("0", "1"), "pilotInfo.firstClass");
                                    
                                    //pilotInfo.secondClass チェック
                                    ResponseFlightPlanValidator.validateAllowedValues(pilotInfo.getSecondClass(), Set.of("0", "1"), "pilotInfo.secondClass");
                                    
                                    //pilotInfo.privateLicense チェック
                                    ResponseFlightPlanValidator.validateAllowedValues(pilotInfo.getPrivateLicense(), Set.of("0", "1"), "pilotInfo.privateLicense");
                                }                                    
                            }  
                        }

                        List<AircraftInfo> aircraftInfos = flightPlan.getAircraftInfo();
                        if (aircraftInfos != null) {
                            for (AircraftInfo aircraftInfo : aircraftInfos) {
                                if (aircraftInfo != null) {
                                    //aircraftInfo.type チェック
                                    ResponseFlightPlanValidator.validateAllowedValues(aircraftInfo.getType(), Set.of("1","2","3","4","5","6"), "aircraftInfo.type");
                                    
                                    //aircraftInfo.certification1 チェック
                                    ResponseFlightPlanValidator.validateAllowedValues(aircraftInfo.getCertification1(), Set.of("0", "1"), "aircraftInfo.certification1");
                                    
                                    //aircraftInfo.certification2 チェック
                                    ResponseFlightPlanValidator.validateAllowedValues(aircraftInfo.getCertification2(), Set.of("0", "1"), "aircraftInfo.certification2");
                                }                                      
                            }     
                        }

                        FlightPermitApplicationInfo flightPermitApplicationInfo = flightPlan.getFlightPermitApplicationInfo();
                        if (flightPermitApplicationInfo != null) {
                            //flightPermitApplicationInfo.contactPermitFlag チェック
                            ResponseFlightPlanValidator.validateAllowedValues(flightPermitApplicationInfo.getContactPermitFlag(), Set.of("0", "1"), "pilotInfo.contactPilotFlag");
                            
                            Contact contactPermit = flightPermitApplicationInfo.getContactPermit();
                            if (contactPermit != null) {
                                //flightPermitApplicationInfo.contactPermit.country チェック
                                ResponseFlightPlanValidator.validateCodeInRangeWithExtras(contactPermit.getCountry(), 1, 199, null, "flightPermitApplicationInfo.contactPermit.country","%03d～%03d");
                                
                                //flightPermitApplicationInfo.contactPermit.prefectures チェック
                                ResponseFlightPlanValidator.validateCodeInRangeWithExtras(contactPermit.getPrefectures(), 1, 49, extraAllowed, "flightPermitApplicationInfo.contactPermit.prefectures","%02d～%02d");
                                
                                //flightPermitApplicationInfo.contactPermit.telephoneCountry チェック
                                ResponseFlightPlanValidator.validateCodeInRangeWithExtras(contactPermit.getTelephoneCountry(), 1, 199, null, "flightPermitApplicationInfo.contactPermit.telephoneCountry","%03d～%03d");
                            }
                        }
                        
                        // いずれか一つの「連絡先フラグ」が”１”設定される必要がある連絡先フラグ チェック
                        if (reporter != null && flightPermitApplicationInfo != null && pilotInfos != null) {
                            ResponseFlightPlanValidator.checkContactFlags(
                                reporter.getContactReporterFlag(),
                                flightPermitApplicationInfo.getContactPermitFlag(),
                                pilotInfos);
                        }

                        newFlightPlanInfos.add(flightPlan);
                        
                    } catch (ResponseValidatorException e) {
                        log.error(e.getMessage());
                    }     
                }
 
            }  
        }

        //飛行計画情報設定
        DroneRouteFlightPlanInfoResponse newFlightPlanInfoResponse = new DroneRouteFlightPlanInfoResponse();
        newFlightPlanInfoResponse.setFlightPlanInfo(newFlightPlanInfos);
        
        try {
            //totalCount チェック
            ResponseFlightPlanValidator.validateRequired(droneRouteFlightPlanInforesponse.getTotalCount(), "totalCount");
            newFlightPlanInfoResponse.setTotalCount(newFlightPlanInfos.size());
        } catch (ResponseValidatorException e) {
            log.error(e.getMessage());
        } 
        
        return newFlightPlanInfoResponse;
        
    }

}
