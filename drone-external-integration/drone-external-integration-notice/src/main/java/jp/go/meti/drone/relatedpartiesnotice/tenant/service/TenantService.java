package jp.go.meti.drone.relatedpartiesnotice.tenant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.relatedpartiesnotice.tenant.model.DroneRouteResponseOperatorInfo;
import jp.go.meti.drone.relatedpartiesnotice.tenant.model.DroneRouteResponseOperatorList;
import jp.go.meti.drone.relatedpartiesnotice.tenant.repository.entity.AirwayTenantAssociationEntity;
import jp.go.meti.drone.relatedpartiesnotice.tenant.repository.entity.NotificationTargetInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.tenant.repository.entity.TenantEntity;
import jp.go.meti.drone.relatedpartiesnotice.tenant.repository.entity.TenantRoleEntity;
import jp.go.meti.drone.relatedpartiesnotice.tenant.repository.mapper.AirwayTenantAssociationRepository;
import jp.go.meti.drone.relatedpartiesnotice.tenant.repository.mapper.NotificationTargetInfoRepository;
import jp.go.meti.drone.relatedpartiesnotice.tenant.repository.mapper.TenantRepository;
import jp.go.meti.drone.relatedpartiesnotice.tenant.repository.mapper.TenantRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 事業者情報取得 サービス実装
 * <p>
 * DBより事業者関連の周知先、航路ID情報を取得し、事業者IDで紐づく情報を処理して返却
 * </p>
 * 
 * @version 1.0 2024/11/14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TenantService {

	/** 事業者情報 リポジトリ */
	@Autowired
	private TenantRepository tenantRepository;

	/** 周知先情報 リポジトリ */
	@Autowired
	private NotificationTargetInfoRepository notificationTargetInfoRepository;

	/** 航路事業者関連情報 リポジトリ */
	@Autowired
	private AirwayTenantAssociationRepository airwayTenantAssociationRepository;

	/** 事業者権限情報 リポジトリ */
	@Autowired
	private TenantRoleRepository tenantRoleRepository;

	/**
	 * 事業者情報を取得
	 * <p>
	 * 事業者IDが設定した場合、事業者IDより事業者情報を取得<br>
	 * 事業者IDが設定しない場合、事業者情報一覧を取得
	 * </p>
	 * 
	 * @param operatorId 事業者ID
	 * @return 事業者情報
	 */
	public DroneRouteResponseOperatorList getOperatorByOperatorId(String operatorId) {
		// 返却モジュール
		DroneRouteResponseOperatorList droneRouteResponseTenantList = new DroneRouteResponseOperatorList();
		List<DroneRouteResponseOperatorInfo> droneRouteResponseTenantInfoList = new ArrayList<>();
		DroneRouteResponseOperatorInfo droneRouteResponseTenantInfo;

		try {
		    // 事業者情報取得
		    List<TenantEntity> tenantEntities = tenantRepository.selectByPrimaryKey(operatorId);
		    // 取得した事業者情報件数分を繰り返し、周知先情報と航路事業者関連情報取得
		    for (TenantEntity tenantEntity : tenantEntities) {

		        //異常なデータ場ある場合は除外する。
		        if (ObjectUtils.isEmpty(tenantEntity.getOperatorId()) || ObjectUtils.isEmpty(tenantEntity.getOperatorName())
		            || ObjectUtils.isEmpty(tenantEntity.getNotificationType())) {
		            continue;
		        }
		        droneRouteResponseTenantInfo = new DroneRouteResponseOperatorInfo();
		        // 周知先情報取得
		        List<NotificationTargetInfoEntity> notificationTargetInfoEntities = notificationTargetInfoRepository
		            .selectByPrimaryKey(tenantEntity.getOperatorId());
		        // 航路事業者関連情報取得
		        List<AirwayTenantAssociationEntity> airwayTenantAssociationEntities = airwayTenantAssociationRepository
		            .selectByPrimaryKey(tenantEntity.getOperatorId());
		        // 事業者権限情報取得
		        List<TenantRoleEntity> tenantRoleEntities = tenantRoleRepository
		            .selectByPrimaryKey(tenantEntity.getOperatorId());

		        // 取得した事業者情報をモジュールに設定
		        droneRouteResponseTenantInfo.setOperatorId(tenantEntity.getOperatorId());
		        droneRouteResponseTenantInfo.setOperatorName(tenantEntity.getOperatorName());
		        droneRouteResponseTenantInfo.setNotificationType(tenantEntity.getNotificationType());

		        if (!getNotifacationTargetList(notificationTargetInfoEntities).isEmpty()
		            && !getTenantRoleList(tenantRoleEntities).isEmpty()) {

		            // 事業者権限情報設定
		            droneRouteResponseTenantInfo.setRoleList(getTenantRoleList(tenantRoleEntities));

		            // 連絡先情報毎に取得し、リストに追加
		            droneRouteResponseTenantInfo
		            .setNotificationTargetList(getNotifacationTargetList(notificationTargetInfoEntities));

		            // 紐づき航路情報ごとに取得し、リストに追加
		            droneRouteResponseTenantInfo.setLinkAirwayList(getLinkAirwayList(airwayTenantAssociationEntities));

		            // 事業者を事業者情報リストに設定
		            droneRouteResponseTenantInfoList.add(droneRouteResponseTenantInfo);
		        }
		    }
		    droneRouteResponseTenantList.setOperatorList(droneRouteResponseTenantInfoList);
		    log.info("事業者情報サービス処理層：" + droneRouteResponseTenantList);
		    return droneRouteResponseTenantList;
		}catch (DataAccessException e) {
		    // ＤＢアクセスエラーが発生しました。メッセージとして出力される
		    String message = MessageUtils.getMessage("DR000E001");
		    log.error(message,e);
		    throw new CommonResponseInternalServerError(500,message);
		} catch (Exception e) {
		    // その他予期せぬエラー発生時
		    String message = MessageUtils.getMessage("DRC01E001");
		    log.error(message,e);
		    throw new CommonResponseInternalServerError(500,message);
		}
	}

	/**
	 * 事業者情報取得
	 * <p>
	 * 航路IDが設定した場合、航路IDより事業者情報を取得<br>
	 * </p>
	 * 
	 * @param airwayId 航路ID
	 * @return 事業者情報
	 */
	public DroneRouteResponseOperatorList getOperatorByAirwayId(String airwayId) {
		// 返却モジュール
		DroneRouteResponseOperatorList droneRouteResponseTenantList = new DroneRouteResponseOperatorList();
		List<DroneRouteResponseOperatorInfo> droneRouteResponseTenantInfoList = new ArrayList<>();
		DroneRouteResponseOperatorInfo droneRouteResponseTenantInfo;

		try {
		    // 航路事業者関連情報取得
		    List<AirwayTenantAssociationEntity> airwayTenantAssociationEntities = airwayTenantAssociationRepository
		        .selectByAirwayId(airwayId);

		    for (AirwayTenantAssociationEntity airwayTenantAssociationEntity : airwayTenantAssociationEntities) {

		        droneRouteResponseTenantInfo = new DroneRouteResponseOperatorInfo();
		        // 事業者情報取得
		        List<TenantEntity> tenantEntities = tenantRepository
		            .selectByPrimaryKey(airwayTenantAssociationEntity.getOperatorId());

		        if (ObjectUtils.isEmpty(tenantEntities) || ObjectUtils.isEmpty(tenantEntities.get(0).getOperatorId())
		            || ObjectUtils.isEmpty(tenantEntities.get(0).getOperatorName())
		            || ObjectUtils.isEmpty(tenantEntities.get(0).getNotificationType())) {
		            continue;
		        }
		        // 取得した事業者情報をモジュールに設定
		        droneRouteResponseTenantInfo.setOperatorId(tenantEntities.get(0).getOperatorId());
		        droneRouteResponseTenantInfo.setOperatorName(tenantEntities.get(0).getOperatorName());
		        droneRouteResponseTenantInfo.setNotificationType(tenantEntities.get(0).getNotificationType());

		        // 事業者権限情報取得
		        List<TenantRoleEntity> tenantRoleEntities = tenantRoleRepository
		            .selectByPrimaryKey(airwayTenantAssociationEntity.getOperatorId());

		        // 周知先情報取得
		        List<NotificationTargetInfoEntity> notificationTargetInfoEntities = notificationTargetInfoRepository
		            .selectByPrimaryKey(airwayTenantAssociationEntity.getOperatorId());
		        if (!getNotifacationTargetList(notificationTargetInfoEntities).isEmpty()
		            && !getTenantRoleList(tenantRoleEntities).isEmpty()) {

		            // 事業者権限情報設定
		            droneRouteResponseTenantInfo.setRoleList(getTenantRoleList(tenantRoleEntities));

		            // 連絡先情報毎に取得し、リストに追加
		            droneRouteResponseTenantInfo
		            .setNotificationTargetList(getNotifacationTargetList(notificationTargetInfoEntities));

		            // 紐づき航路情報ごとに取得し、リストに追加
		            droneRouteResponseTenantInfo.setLinkAirwayList(getLinkAirwayList(airwayTenantAssociationEntities));

		            // 事業者を事業者情報リストに設定
		            droneRouteResponseTenantInfoList.add(droneRouteResponseTenantInfo);
		        }
		    }
		    droneRouteResponseTenantList.setOperatorList(droneRouteResponseTenantInfoList);
		    log.info("事業者情報サービス処理層：" + droneRouteResponseTenantList);
		    return droneRouteResponseTenantList;
		}catch (DataAccessException e) {
		    // ＤＢアクセスエラーが発生しました。メッセージとして出力される
		    String message = MessageUtils.getMessage("DR000E001");
		    log.error(message,e);
		    throw new CommonResponseInternalServerError(500,message);
		} catch (Exception e) {
		    // その他予期せぬエラー発生時
		    String message = MessageUtils.getMessage("DRC01E001");
		    log.error(message,e);
		    throw new CommonResponseInternalServerError(500,message);
		}

	}

	/**
	 * 周知先情報より、連絡先のみをリストとして抽出する。
	 * 
	 * @param notificationTargetInfoEntities 周知先情報
	 * @return 連絡先リスト
	 */
	private List<String> getNotifacationTargetList(List<NotificationTargetInfoEntity> notificationTargetInfoEntities) {

		// 連絡先情報ごとに取得し、リストに追加
		List<String> notifacationTargetList = new ArrayList<>();
		if (!notificationTargetInfoEntities.isEmpty()) {
			notificationTargetInfoEntities.forEach(notificationTargetInfo -> notifacationTargetList
					.add(notificationTargetInfo.getNotificationTarget()));
		}
		return notifacationTargetList;
	}

	/**
	 * 紐づき航路情報より航路IDのみをリストとして抽出する。
	 * 
	 * @param airwayTenantAssociationEntities 航路事業者関連情報
	 * @return 航路IDリスト
	 */
	private List<String> getLinkAirwayList(List<AirwayTenantAssociationEntity> airwayTenantAssociationEntities) {

		// 紐づき航路情報ごとに取得し、リストに追加
		List<String> linkAirwayList = new ArrayList<>();
		if (!airwayTenantAssociationEntities.isEmpty()) {
			airwayTenantAssociationEntities.forEach(
					airwayTenantAssociationInfo -> linkAirwayList.add(airwayTenantAssociationInfo.getAirwayId()));
		}
		return linkAirwayList.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * 事業者権限情報より権限のみをリストとして抽出する。
	 * 
	 * @param tenantRoleEntities 事業者権限情報
	 * @return 権限リスト
	 */
	private List<String> getTenantRoleList(List<TenantRoleEntity> tenantRoleEntities) {

		// 事業者情報ごとに取得し、リストに追加
		List<String> tenantRoleList = new ArrayList<>();
		if (!tenantRoleEntities.isEmpty()) {
			tenantRoleEntities.forEach(tenantRoleEntity -> tenantRoleList.add(tenantRoleEntity.getRole()));
		}
		return tenantRoleList;
	}
}
