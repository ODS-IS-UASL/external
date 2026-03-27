package jp.go.meti.drone.relatedpartiesnotice.reservenotification.service;

import java.util.List;

import jp.go.meti.drone.relatedpartiesnotice.reservenotification.model.DroneRouteResponseNotificationInfo;

public interface ReserveNotificationService {
    
    /**
     * 航路予約関係者情報取得
     * 
     * @param airwayReserveId 航路予約ID
     * @return 航路予約関係者情報リスト
     */
    public List<DroneRouteResponseNotificationInfo> getReserveNotificationInfo(String airwayReserveId) throws Exception;

}
