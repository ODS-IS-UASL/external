package jp.go.meti.drone.relatedpartiesnotice.tenant.controller;

import org.springframework.http.ResponseEntity;


/**
 * 権限報取得コントローラーインターフェース
 * 
 * @version 1.0 2024/12/12
 */
public interface TenantInfoController {

    /**
     * 権限情報取得する
     * 
     * @param operatorId 事業者ID
     * @return　権限情報
     */
    public ResponseEntity<?> getTenantInfo(String operatorId);

}
