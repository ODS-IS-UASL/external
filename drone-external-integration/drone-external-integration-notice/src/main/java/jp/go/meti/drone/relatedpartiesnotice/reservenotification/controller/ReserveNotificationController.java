package jp.go.meti.drone.relatedpartiesnotice.reservenotification.controller;

import org.springframework.http.ResponseEntity;


/**
 * 航路予約関係者情報取得コントローラーインターフェース
 * 
 * @version 1.0 2025/12/05
 */
public interface ReserveNotificationController {

    /**
     * 航路予約関係者情報取得する
     * 
     * @param airwayReserveId 航路予約ID
     * @return　航路予約関係者情報リスト
     */
    public ResponseEntity<?> getReserveNotificationInfo(String airwayReserveId);

}
