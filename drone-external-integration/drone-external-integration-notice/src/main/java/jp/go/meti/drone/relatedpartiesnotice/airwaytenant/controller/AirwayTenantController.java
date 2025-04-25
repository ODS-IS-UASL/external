package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.model.RequestRouteTenantInfo;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.model.RequestUpdateRouteTenantInfo;

/**
 * 航路事業者情報コントローラーインターフェース
 * 
 * @version 1.0 2024/11/08
 */
public interface AirwayTenantController {

    /**
     * 航路事業者情報を登録する
     * 
     * @param request
     * @return
     */
    public ResponseEntity<?> addAirwayTenant(@RequestBody @Validated RequestRouteTenantInfo request);

    /**
     * 航路事業者情報を更新する
     * 
     * @param request
     * @return
     */
    public ResponseEntity<?> updateAirwayTenant(
        @RequestBody @Validated RequestUpdateRouteTenantInfo request);

    /**
     * 航路事業者情報を削除する
     * 
     * @param airwayId
     * @return
     */
    public ResponseEntity<?> deleteAirwayTenant(@RequestParam String airwayId);
}
