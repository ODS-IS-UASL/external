package jp.go.meti.drone.relatedpartiesnotice.reservenotification.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.com.BadRequestException;
import jp.go.meti.drone.relatedpartiesnotice.reservenotification.model.DroneRouteResponseNotificationInfo;
import jp.go.meti.drone.relatedpartiesnotice.reservenotification.repository.entity.ReserveNotificationEntity;
import jp.go.meti.drone.relatedpartiesnotice.reservenotification.repository.mapper.ReserveNotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReserveNotificationServiceImpl implements ReserveNotificationService{ 
    
    @Autowired
    private ReserveNotificationMapper reserveNotificationMapper;
    
    private static final String AIRWAY_RESERVEID = "航路予約ID";
    
    private static final String AIRWAY_RESERVEID_LENGTH = "36";
    
    /**
     * 航路予約関係者情報取得
     * @param airwayReserveId 航路予約ID
     * @return 航路予約関係者情報リスト
     */
    public List<DroneRouteResponseNotificationInfo> getReserveNotificationInfo(String airwayReserveId) throws Exception {
        
        // リクエストパラメータチェック(航路予約ID)
        try {
            resquestCheck(airwayReserveId);
        }catch(BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
        
        // 返却モジュール
        
        List<DroneRouteResponseNotificationInfo> responseNotificationInfoList = new ArrayList<>();
        List<ReserveNotificationEntity> reserveNotificationEntityList = new ArrayList<>();
        try {
            // 航路予約関係者情報取得
        	reserveNotificationEntityList = reserveNotificationMapper.findReserveNotificationByIds(
                airwayReserveId);
        } catch (DataAccessException e) {
            // ＤＢアクセスエラーが発生した
            String message = MessageUtils.getMessage("DR000E001");
            log.error(message,e);
            throw new Exception(message);
        } catch (Exception e) {
            // システムエラーが発生した
            String message = MessageUtils.getMessage("DRC01E001");
            log.error(message,e);
            throw new Exception(message);
        }
        // 情報取得してない場合
        if (reserveNotificationEntityList.isEmpty()) {
            String message = MessageUtils.getMessage("DR000E014", AIRWAY_RESERVEID, airwayReserveId);
            log.error(message);
            throw new NotFoundException(message);
        }
        for(ReserveNotificationEntity reserventity:reserveNotificationEntityList) {
        	if(reserventity != null) {
        		DroneRouteResponseNotificationInfo responseNotificationInfo = new DroneRouteResponseNotificationInfo();
                // 取得した航路予約関係者情報をモジュールに設定
                responseNotificationInfo.setRelatedCategory(reserventity.getRelatedCategory());
                responseNotificationInfo.setOperatorId(reserventity.getOperatorId());
                responseNotificationInfo.setOperatorName(reserventity.getOperatorName());
                responseNotificationInfo.setNotificationEmail(reserventity.getNotificationEmail());
                responseNotificationInfo.setNotificationPhone(reserventity.getNotificationPhone());
                responseNotificationInfoList.add(responseNotificationInfo);
        	}
        }
        log.info("航路予約関係者情報サービス処理層：" + responseNotificationInfoList);
        return responseNotificationInfoList;

    }
    
    /**
     * リクエストパラメータ航路予約IDチェック
     * @param airwayReserveId
     * @return
     */
    private void resquestCheck(String airwayReserveId) throws BadRequestException {
        if (StringUtils.isEmpty(airwayReserveId)) {
            String message = MessageUtils.getMessage("DRC01E003", AIRWAY_RESERVEID);
            log.error(message);
            throw new BadRequestException(message);
        }
        if (airwayReserveId.length() != 36) {
            String message = MessageUtils.getMessage("DR000E012", AIRWAY_RESERVEID, AIRWAY_RESERVEID_LENGTH);
            log.error(message);
            throw new BadRequestException(message);
        }
        if (!airwayReserveId.matches("^[0-9A-Za-z_-]+$")) {
            String message = MessageUtils.getMessage("DR000E013", AIRWAY_RESERVEID);
            log.error(message);
            throw new BadRequestException(message);
        }
    }
}
