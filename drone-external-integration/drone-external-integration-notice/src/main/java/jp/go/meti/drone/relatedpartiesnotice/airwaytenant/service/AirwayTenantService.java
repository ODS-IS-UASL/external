package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.service;

import java.util.List;

/**
 * 航路事業者情報サービスインターフェース
 * 
 * @version 1.0 2024/11/08
 */
public interface AirwayTenantService {

    /**
     * 航路事業者情報を登録する処理
     * 
     * @param airwayId 航路ID
     * @param operatorIdList 事業者IDリスト
     * @throws Exception 登録処理に失敗した場合の例外
     */
    public void addTenantAssociation(String airwayId, List<String> operatorIdList) throws Exception;

    /**
     * 航路事業者情報を更新する処理
     * 
     * @param updateType 更新タイプ (0: 上書き更新, 1: 追加)
     * @param airwayId 航路ID
     * @param operatorIdList 事業者IDリスト
     * @throws Exception 更新処理に失敗した場合の例外
     */
    public void updateAirwayTenant(String updateType, String airwayId, List<String> operatorIdList)
        throws Exception;

    /**
     * 航路事業者情報を削除する処理
     * 
     * @param airwayId 航路ID
     * @throws Exception 削除処理が失敗した場合にスロー
     */
    public void deleteAirwayTenant(String airwayId) throws Exception;
}
