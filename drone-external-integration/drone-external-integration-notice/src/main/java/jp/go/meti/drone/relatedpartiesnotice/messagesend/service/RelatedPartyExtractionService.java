package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.airwayinfomodel.UaslPointEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.airwayinfomodel.UaslSectionsEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.airwayinfomodel.UaslTopEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.airwayinfomodel.dto.AirwayGetResult;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.locationinfomodel.LocationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.locationinfomodel.dto.LocationGetResult;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwayReserveInfo;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReserveRelatedEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.RelatedMunicipalityInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.RelatedPartiesInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayReserveRelatedMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.RelatedPartiesInfoMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.userinfomodel.NotificationTargetInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.userinfomodel.UserInfoEntity.AttributeItem;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.userinfomodel.dto.UserManagementGetResult;
import lombok.extern.slf4j.Slf4j;

/**
 * 関係者抽出サービスクラス
 */
@Slf4j
@Service
public class RelatedPartyExtractionService {

    @Autowired
    private AirwayInformationService airwayInformationService;
    
    @Autowired
    private LocationInformationService locationInformationService;
    
    @Autowired
    private UserManagementService userManagementService;
    
    @Autowired
    private RelatedPartiesInfoMapper relatedPartiesInfoMapper;
    
    @Autowired
    private AirwayReserveRelatedMapper airwayReserveRelatedMapper;
    
    /** ユーザID値取得 */
    @Value("${systemuser}")
    private int userId;
    
    /**
     * 航路予約情報に関する関係者抽出処理
     * @param airwayReserveInfoList 航路予約情報
     * @throws Exception 例外
     */
    public void getRelatedUsers(List<AirwayReserveInfo> airwayReserveInfoList) throws Exception {

        List<NotificationTargetInfoEntity> notificationAddList = new ArrayList<>();
        
        // 航路IDをもとに航路情報取得APIを実施する。
        for (AirwayReserveInfo airwayReserveInfo : airwayReserveInfoList) {
            AirwayGetResult airwayGetResult = airwayInformationService.getAirwayInformation(airwayReserveInfo.getUaslId());
            if (airwayGetResult.getStatusCode()!=200 || airwayGetResult.getUaslTopEntity().getUasl().isEmpty()) {
                continue;
            }
            UaslTopEntity uaslTopEntity = airwayGetResult.getUaslTopEntity();
       
            // 取得したレスポンスより、予約にて受信した航路区画IDと一致する「航路区画ID情報」（uaslSections）を抽出する。
            List<UaslSectionsEntity> uaslSections = new ArrayList<>();
            uaslSections = uaslTopEntity.getUasl().get(0).getUasl().getUaslSections().stream()
                .filter(uasl -> airwayReserveInfo.getUaslSectionIds().stream().anyMatch(reserve -> Objects.equals(uasl.getUaslSectionId(), reserve)))
                .collect(Collectors.toList());
            
           // 抽出した情報の航路点ID（uaslPointIds）をもとに、航路点情報（uaslPoints）を抽出。
            List<UaslPointEntity> uaslPoints = new ArrayList<>();
            for (UaslSectionsEntity uaslSectionsEntity : uaslSections) {
                List<UaslPointEntity> filteredPoints = uaslTopEntity.getUasl().get(0).getUasl().getUaslPoints().stream()
                    .filter(p -> uaslSectionsEntity.getUaslPointIds().contains(p.getUaslPointId()))
                    .collect(Collectors.toList());
                uaslPoints.addAll(filteredPoints);  
            }
            List<UaslPointEntity> distinctPoints = uaslPoints.stream().collect(Collectors.collectingAndThen(
                    Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(UaslPointEntity::getUaslPointId))),
                    ArrayList::new));
           
           // 緯度経度を算出する。
            // 座標配列内は経度,緯度,高度順で記述
            List<LocationEntity> locationList = new ArrayList<>();
            for (UaslPointEntity uaslPoint : distinctPoints) {
                // top_1都道府県情報を取得
                List<Double> top1= uaslPoint.getGeometry().getCoordinates().get(0).get(0);
                // 経度設定
                Double lon1 = top1.get(0);
                // 緯度設定
                Double lat1 = top1.get(1);
                LocationGetResult locationGetResult1 = locationInformationService.getAddressByLatLon(lon1, lat1);
                Thread.sleep(1000);

                if (locationGetResult1.getStatusCode()!=200 || locationGetResult1.getLocationEntity() == null) {
                    continue;
                }
                locationList.add(locationGetResult1.getLocationEntity());
                
                // top_2都道府県情報を取得
                List<Double> top2= uaslPoint.getGeometry().getCoordinates().get(0).get(1);
                // 経度設定
                Double lon2 = top2.get(0);
                // 緯度設定
                Double lat2 = top2.get(1);
                LocationGetResult locationGetResult2 = locationInformationService.getAddressByLatLon(lon2, lat2);
                Thread.sleep(1000);
                
                if (locationGetResult2.getStatusCode()!=200 || locationGetResult2.getLocationEntity() == null) {
                    continue;
                }
                locationList.add(locationGetResult2.getLocationEntity());   
            }
            // 都道府県情報にて関係者取得
            List<NotificationTargetInfoEntity> notificationList = getRelatedInfo(locationList, airwayReserveInfo);
            notificationAddList.addAll(notificationList);              
        }
        
        if (notificationAddList.isEmpty()) {
            log.info("都道府県情報にて関係者取得失敗。");
            return;
        }
        
        // ユーザ情報取得処理
        List<NotificationTargetInfoEntity> notificationInsertList = notificationAddList.stream().collect(Collectors.collectingAndThen(
            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(NotificationTargetInfoEntity::getOperatorId))),
            ArrayList::new));
        
        List<String> userIdList = notificationInsertList.stream().map(NotificationTargetInfoEntity::getOperatorId).collect(Collectors.toList());
        UserManagementGetResult userManagementGetResult = userManagementService.getUserAttr(userIdList);  
        for (NotificationTargetInfoEntity notificationInsert : notificationInsertList) {
            AirwayReserveRelatedEntity airwayReserveRelatedEntity = new AirwayReserveRelatedEntity(); 
            AttributeItem targetUserAttr = userManagementGetResult.getUserInfoEntity().getAttributeList().stream()
                .filter(u -> u.getUserId().equals(notificationInsert.getOperatorId())).findFirst()
                .orElse(null);     
            airwayReserveRelatedEntity.setAirwayReserveId(notificationInsert.getAirwayReserveId());
            airwayReserveRelatedEntity.setRequestId(notificationInsert.getRequestId());
            airwayReserveRelatedEntity.setOperatorId(notificationInsert.getOperatorId());
            airwayReserveRelatedEntity.setRelatedCategory(notificationInsert.getRelatedCategory()); 
            airwayReserveRelatedEntity.setNotificationRemark(notificationInsert.getNotificationRemark());
            airwayReserveRelatedEntity.setCreationId(userId);
            airwayReserveRelatedEntity.setUpdateId(userId);
            if (targetUserAttr != null) {
                airwayReserveRelatedEntity.setOperatorName(targetUserAttr.getOperatorName());
                airwayReserveRelatedEntity.setNotificationEmail(targetUserAttr.getUserLoginId());
                airwayReserveRelatedEntity.setNotificationPhone(targetUserAttr.getAttribute().getPhone());
            } else {
                airwayReserveRelatedEntity.setOperatorName("無し");
                airwayReserveRelatedEntity.setNotificationEmail("無し");
                airwayReserveRelatedEntity.setNotificationPhone("無し");
            }
                        
            // 航路予約関係者登録
            airwayReserveRelatedMapper.insertAirwayReserveRelated(airwayReserveRelatedEntity);
        }   
    }
    
    /**
     * @param locationEntity
     * @return 都道府県情報にて関係者
     */
    private List<NotificationTargetInfoEntity> getRelatedInfo(List<LocationEntity> locationEntityList, 
        AirwayReserveInfo airwayReserveInfo) {
        List<NotificationTargetInfoEntity> notificationInfoList = new ArrayList<>();
        for (LocationEntity locationEntity : locationEntityList) {
            List<String> addressList = Arrays.stream(locationEntity.getDisplayName().split(","))
                .map(String::trim)
                .toList();
            if (addressList.contains("東京都")) {
                List<RelatedPartiesInfoEntity> relatedPartiesInfoList = new ArrayList<>();
                relatedPartiesInfoList = relatedPartiesInfoMapper.getRelatedPartiesInfoByPrefecture("東京都");
                for (RelatedPartiesInfoEntity relatedPartiesInfoEntity : relatedPartiesInfoList) {
                    if (relatedPartiesInfoEntity.getExtractionConditions().equals("1")) {
                        NotificationTargetInfoEntity notificationInfo = new NotificationTargetInfoEntity();
                        notificationInfo.setAirwayReserveId(airwayReserveInfo.getReservationId());
                        notificationInfo.setRequestId(airwayReserveInfo.getRequestId());
                        notificationInfo.setOperatorId(relatedPartiesInfoEntity.getOperatorId());
                        notificationInfo.setRelatedCategory(relatedPartiesInfoEntity.getRelatedCategory());
                        notificationInfo.setFlightPrefecture(relatedPartiesInfoEntity.getFlightPrefecture());
                        notificationInfo.setNotificationRemark(relatedPartiesInfoEntity.getNotificationRemark());
                        notificationInfoList.add(notificationInfo);
                    } else if (relatedPartiesInfoEntity.getExtractionConditions().equals("2")){
                        List<String> municipalityList = new ArrayList<>();
                        // 区
                        if (locationEntity.getAddress().getSuburb() != null) {
                        municipalityList.add(locationEntity.getAddress().getSuburb());
                        }
                        // 村
                        if (locationEntity.getAddress().getVillage() != null) {
                        municipalityList.add(locationEntity.getAddress().getVillage());
                        }
                        // 町
                        if (locationEntity.getAddress().getTown() != null) {
                        municipalityList.add(locationEntity.getAddress().getTown());
                        }
                        // 郡
                        if (locationEntity.getAddress().getCounty() != null) {
                        municipalityList.add(locationEntity.getAddress().getCounty());
                        }
                        // 市 
                        if (locationEntity.getAddress().getCity() != null) {
                        municipalityList.add(locationEntity.getAddress().getCity());
                        }
                        boolean municipalityFlag = false;
                        for (String municipality : municipalityList) {
                            List<RelatedMunicipalityInfoEntity> relatedMunicipalityInfoList = new ArrayList<>();
                            relatedMunicipalityInfoList = 
                                relatedPartiesInfoMapper.getRelatedMunicipalityInfoEntity(relatedPartiesInfoEntity.getOperatorId(), 
                                    municipality);
                            if (!relatedMunicipalityInfoList.isEmpty()) {
                                municipalityFlag = true;
                                break;
                            }
                        }
                                               
                        // 取得結果1件以上の場合
                        if(municipalityFlag) {
                            NotificationTargetInfoEntity notificationInfo = new NotificationTargetInfoEntity();
                            notificationInfo.setAirwayReserveId(airwayReserveInfo.getReservationId());
                            notificationInfo.setRequestId(airwayReserveInfo.getRequestId());
                            notificationInfo.setOperatorId(relatedPartiesInfoEntity.getOperatorId());
                            notificationInfo.setRelatedCategory(relatedPartiesInfoEntity.getRelatedCategory());
                            notificationInfo.setFlightPrefecture(relatedPartiesInfoEntity.getFlightPrefecture());
                            notificationInfo.setFlightMunicipality(locationEntity.getAddress().getCity());
                            notificationInfo.setNotificationRemark(relatedPartiesInfoEntity.getNotificationRemark());
                            notificationInfoList.add(notificationInfo);
                        } else {
                            // 取得結果0件の場合
                            List<RelatedPartiesInfoEntity> emptyResult = relatedPartiesInfoList.stream()
                                .filter(x -> x.getRelatedCategory().equals(relatedPartiesInfoEntity.getRelatedCategory()) 
                                    && x.getExtractionConditions().equals("3")).collect(Collectors.toList());
                            for (RelatedPartiesInfoEntity relatedPartiesInfo : emptyResult) {
                                NotificationTargetInfoEntity notificationInfo = new NotificationTargetInfoEntity();
                                notificationInfo.setAirwayReserveId(airwayReserveInfo.getReservationId());
                                notificationInfo.setRequestId(airwayReserveInfo.getRequestId());
                                notificationInfo.setOperatorId(relatedPartiesInfo.getOperatorId());
                                notificationInfo.setRelatedCategory(relatedPartiesInfo.getRelatedCategory());
                                notificationInfo.setFlightPrefecture(relatedPartiesInfo.getFlightPrefecture());
                                notificationInfo.setFlightMunicipality(locationEntity.getAddress().getCity());
                                notificationInfo.setNotificationRemark(relatedPartiesInfo.getNotificationRemark());
                                notificationInfoList.add(notificationInfo);
                            }
                        }
                    } 
                }
            } else {
                List<RelatedPartiesInfoEntity> relatedPartiesInfoList = new ArrayList<>();
                relatedPartiesInfoList = relatedPartiesInfoMapper.getRelatedPartiesInfoByPrefecture(locationEntity.getAddress().getProvince());
                for (RelatedPartiesInfoEntity relatedPartiesInfoEntity : relatedPartiesInfoList) {
                    if (relatedPartiesInfoEntity.getExtractionConditions().equals("1")) {
                        NotificationTargetInfoEntity notificationInfo = new NotificationTargetInfoEntity();
                        notificationInfo.setAirwayReserveId(airwayReserveInfo.getReservationId());
                        notificationInfo.setRequestId(airwayReserveInfo.getRequestId());
                        notificationInfo.setOperatorId(relatedPartiesInfoEntity.getOperatorId());
                        notificationInfo.setRelatedCategory(relatedPartiesInfoEntity.getRelatedCategory());
                        notificationInfo.setFlightPrefecture(relatedPartiesInfoEntity.getFlightPrefecture());
                        notificationInfo.setNotificationRemark(relatedPartiesInfoEntity.getNotificationRemark());
                        notificationInfoList.add(notificationInfo);
                    } else if (relatedPartiesInfoEntity.getExtractionConditions().equals("2")){
                        List<String> municipalityList = new ArrayList<>();
                        // 区
                        if (locationEntity.getAddress().getSuburb() != null) {
                        municipalityList.add(locationEntity.getAddress().getSuburb());
                        }
                        // 村
                        if (locationEntity.getAddress().getVillage() != null) {
                        municipalityList.add(locationEntity.getAddress().getVillage());
                        }
                        // 町
                        if (locationEntity.getAddress().getTown() != null) {
                        municipalityList.add(locationEntity.getAddress().getTown());
                        }
                        // 郡
                        if (locationEntity.getAddress().getCounty() != null) {
                        municipalityList.add(locationEntity.getAddress().getCounty());
                        }
                        // 市 
                        if (locationEntity.getAddress().getCity() != null) {
                        municipalityList.add(locationEntity.getAddress().getCity());
                        }
                        boolean municipalityFlag = false;
                        for (String municipality : municipalityList) {
                            List<RelatedMunicipalityInfoEntity> relatedMunicipalityInfoList = new ArrayList<>();
                            relatedMunicipalityInfoList = 
                                relatedPartiesInfoMapper.getRelatedMunicipalityInfoEntity(relatedPartiesInfoEntity.getOperatorId(), 
                                    municipality);
                            if (!relatedMunicipalityInfoList.isEmpty()) {
                                municipalityFlag = true;
                                break;
                            }
                        }
                        // 取得結果1件以上の場合
                        if(municipalityFlag) {
                            NotificationTargetInfoEntity notificationInfo = new NotificationTargetInfoEntity();
                            notificationInfo.setAirwayReserveId(airwayReserveInfo.getReservationId());
                            notificationInfo.setRequestId(airwayReserveInfo.getRequestId());
                            notificationInfo.setOperatorId(relatedPartiesInfoEntity.getOperatorId());
                            notificationInfo.setRelatedCategory(relatedPartiesInfoEntity.getRelatedCategory());
                            notificationInfo.setFlightPrefecture(relatedPartiesInfoEntity.getFlightPrefecture());
                            notificationInfo.setFlightMunicipality(locationEntity.getAddress().getCity());
                            notificationInfo.setNotificationRemark(relatedPartiesInfoEntity.getNotificationRemark());
                            notificationInfoList.add(notificationInfo);
                        } else {
                            // 取得結果0件の場合
                            List<RelatedPartiesInfoEntity> emptyResult = relatedPartiesInfoList.stream()
                                .filter(x -> x.getRelatedCategory().equals(relatedPartiesInfoEntity.getRelatedCategory()) 
                                    && x.getExtractionConditions().equals("3")).collect(Collectors.toList());
                            for (RelatedPartiesInfoEntity relatedPartiesInfo : emptyResult) {
                                NotificationTargetInfoEntity notificationInfo = new NotificationTargetInfoEntity();
                                notificationInfo.setAirwayReserveId(airwayReserveInfo.getReservationId());
                                notificationInfo.setRequestId(airwayReserveInfo.getRequestId());
                                notificationInfo.setOperatorId(relatedPartiesInfo.getOperatorId());
                                notificationInfo.setRelatedCategory(relatedPartiesInfo.getRelatedCategory());
                                notificationInfo.setFlightPrefecture(relatedPartiesInfo.getFlightPrefecture());
                                notificationInfo.setFlightMunicipality(locationEntity.getAddress().getCity());
                                notificationInfo.setNotificationRemark(relatedPartiesInfo.getNotificationRemark());
                                notificationInfoList.add(notificationInfo);
                            }
                        }
                    } 
                }
            }    
        }
        List<NotificationTargetInfoEntity> notificationList = notificationInfoList.stream().collect(Collectors.collectingAndThen(
                    Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(NotificationTargetInfoEntity::getOperatorId))),
                    ArrayList::new));
        return notificationList;
    }
}
