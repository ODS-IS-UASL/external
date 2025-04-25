package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.service;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.com.BadRequestException;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.entity.AirwayTenantAssociationEntity;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.entity.TenantEntity;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.mapper.AirwayTenantAssociationMapper;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.mapper.TenantMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.TenantNotificationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayInfoMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayReservationInfoMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayTenantNotificationMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.service.MailSendService;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * 航路事業者情報サービスクラス
 * 
 * @version 1.0 2024/11/08
 */
@Slf4j
@Service("AirwayTenantService")
public class AirwayTenantServiceImpl implements AirwayTenantService {

	/** 事業者情報マッパー */
	@Autowired
	private TenantMapper tenantMapper;

	/** 航路事業者情報マッパー */
	@Autowired
	private AirwayTenantAssociationMapper airwayTenantMapper;

	/** 航路情報マッパー */
	@Autowired
	private AirwayInfoMapper airwayInfoMapper;

	/** 航路予約情報マッパー */
	@Autowired
	private AirwayReservationInfoMapper airwayReservationInfoMapper;

	/** 航路事業者送信情報マッパー */
	@Autowired
	private AirwayTenantNotificationMapper airwayTenantNotificationMapper;

	/** メール送信サービス */
	@Autowired
	private MailSendService mailSendService;
	
	/** スレッドプールを使って非同期処理を管理 */
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

	/** パラメータ値(航路ID)設定 */
	private static final String KOURO_ID = "航路ID";

	/** パラメータ値(事業者ID)設定 */
	private static final String KANKEISYA_ID = "事業者ID";

	/** ユーザID値取得 */
	@Value("${systemuser}")
	private int userId;

	/**
     * 航路事業者情報を登録する処理
     * 
     * @param airwayId 航路ID
     * @param operatorIdList 事業者IDリスト
     * @throws Exception 登録処理に失敗した場合の例外
     */
    @Override
    @Transactional
    public void addTenantAssociation(String airwayId, List<String> operatorIdList) throws Exception {
        log.info("航路事業者情報を登録サービス開始します。");
        // 1.事業者情報テーブルから事業者IDリストが全部存在するか確認
        List<String> notFoundIds = tenantCheck(operatorIdList);
        if (!notFoundIds.isEmpty()) {
            String notFoundIdsStr = String.join(", ", notFoundIds);
            // 指定された事業者情報の取得結果が0件
            String message = MessageFormat.format(MessageUtils.getMessage("DR000E007"), KANKEISYA_ID, notFoundIdsStr);
            log.error(message);
            throw new NotFoundException(message);
        }

        // 2. 航路事業者情報テーブルにデータが存在するか確認
        if (airwayTenantMapper.countByAirwayId(airwayId) > 0) {
            String message = MessageFormat.format(MessageUtils.getMessage("DR000E010"), airwayId);
            log.error(message);
            throw new BadRequestException(message);
        }
        // 3. 新しい関連情報を登録する
        try {
            //情報を登録する
            airwayTenantMapper.insertAirwayTenantAssociation(airwayId, operatorIdList, userId, userId);
            //メール送信機能を呼び出し
            sendEmail(airwayId, operatorIdList);
            log.info("航路事業者情報を登録サービス終了します。");
        } catch (DataAccessException e) {
            // ＤＢアクセスエラーが発生した
            String message = MessageUtils.getMessage("DR000E001");
            log.error(message,e);
            throw new Exception(message);
        } catch (Exception e) {
            // 登録失敗時
            String message = MessageUtils.getMessage("DRC01E001");
            log.error(message,e);
            throw new Exception(message);
        }

    }

	/**
	 * 事業者情報存在チェックの共通めメソッド
	 * 
	 * @param operatorIdList 事業者IDリスト
	 * @return 存在チェック結果
	 */
	public List<String> tenantCheck(List<String> operatorIdList) {
		// 1. 事業者情報テーブルから関連パーティを複数IDで検索
		List<TenantEntity> foundTenant = tenantMapper.findOperatorByIds(operatorIdList);

		// 2. 検索結果に存在しないIDをチェック
		return operatorIdList.stream()
				.filter(id -> foundTenant.stream().noneMatch(p -> Objects.equals(p.getOperatorId(), id))).toList();
	}

	/**
	 * 航路事業者情報を更新する処理
	 * 
	 * @param updateType     更新タイプ (0: 上書き更新, 1: 追加)
	 * @param airwayId       航路ID
	 * @param operatorIdList 事業者IDリスト
	 * @throws Exception     更新処理に失敗した場合の例外
	 */
	@Override
	@Transactional
	public void updateAirwayTenant(String updateType, String airwayId, List<String> operatorIdList) throws Exception {
		log.info("航路事業者情報を更新サービス開始します。");
		// 1. 事業者情報存在チェック
		List<String> notFoundoperatorIds = tenantCheck(operatorIdList);
		if (!notFoundoperatorIds.isEmpty()) {
			String notFoundIdsStr = String.join(", ", notFoundoperatorIds);
			String message = MessageFormat.format(MessageUtils.getMessage("DR000E007"), KANKEISYA_ID, notFoundIdsStr);
			log.error(message);
			throw new NotFoundException(message);
		}

		// 2. 航路事業者情報を登録
		if (airwayTenantMapper.countByAirwayId(airwayId) == 0) {
			// 航路事業者情報テーブルにデータが存在しない場合、追加処理を行う
			tsuikaAirway(airwayId, operatorIdList);
		} else {
			// その以外、更新処理
			updateAirway(updateType, airwayId, operatorIdList);
		}
		log.info("航路事業者情報を更新サービス終了します。");
	}

	/**
	 * 紐づき情報存在チェックの共通めメソッド
	 * 
	 * @param airwayId       航路ID
	 * @param operatorIdList 事業者IDリスト
	 * @throws Exception 検索処理に失敗した場合の例外
	 * @return 事業者IDリスト
	 */
	public List<String> airwayTenantCheck(String airwayId, List<String> operatorIdList) {
		// 航路事業者情報テーブルから関連パーティを複数IDで検索
		List<AirwayTenantAssociationEntity> foundAirwayTenant = airwayTenantMapper.foundAirwayIdAndOperatorIds(airwayId,
				operatorIdList);
		List<String> operatorIds = new ArrayList<>();
		if (!foundAirwayTenant.isEmpty()) {
			operatorIds = foundAirwayTenant.stream().map(AirwayTenantAssociationEntity::getOperatorId).toList();
		}
		return operatorIds;
	}

	/**
	 * 更新処理
	 * 
	 * @param updateType     更新タイプ (0: 上書き更新, 1: 追加)
	 * @param airwayId       航路ID
	 * @param operatorIdList 事業者IDリスト
	 * @throws Exception
	 */
	private void updateAirway(String updateType, String airwayId, List<String> operatorIdList) throws Exception {
		if (updateType.equals("0")) {
			// 上書き更新の場合
			uwagakiAirway(airwayId, operatorIdList);
		} else if (updateType.equals("1")) {
			// 追加の場合
			tsuikaAirway(airwayId, operatorIdList);
		}
	}

	/**
	 * 追加
	 * 
	 * @param airwayId       航路ID
	 * @param operatorIdList 事業者IDリスト
	 * @throws Exception
	 */
	private void tsuikaAirway(String airwayId, List<String> operatorIdList) throws Exception {
		// 重複チェック
		List<String> operatorIds = airwayTenantCheck(airwayId, operatorIdList);
		if (operatorIds != null && !operatorIds.isEmpty()) {
			String notFoundIdsStr = String.join(", ", operatorIds);
			String message = MessageFormat.format(MessageUtils.getMessage("DR000E011"), notFoundIdsStr, airwayId);
			log.error(message);
			throw new BadRequestException(message);
		}
		try {
			// 新しい関連情報を追加登録する
			airwayTenantMapper.insertAirwayTenantAssociation(airwayId, operatorIdList, userId, userId);
			// メール送信機能を呼び出し
			sendEmail(airwayId, operatorIdList);
		} catch (DataAccessException e) {
			// ＤＢアクセスエラーが発生した
			String message = MessageUtils.getMessage("DR000E001");
			log.error(message);
			throw new Exception(message);
		} catch (Exception e) {
			// 更新失敗時
			String message = MessageUtils.getMessage("DRC01E001");
			log.error(message);
			throw new Exception(message);
		}
	}

	/**
	 * 上書き更新
	 * 
	 * @param airwayId       航路ID
	 * @param operatorIdList 事業者IDリスト
	 * @throws Exception
	 */
	private void uwagakiAirway(String airwayId, List<String> operatorIdList) throws Exception {
		// 航路事業者情報を削除して再登録
		try {

			// もともと紐付いた事業者情報を取得する
			List<String> operatorIds = airwayTenantMapper.findByAirwayId(airwayId);
			List<String> nonCommonElements = new ArrayList<>();
			
			// メール送信事業者対象整理
			if (operatorIds != null && !operatorIds.isEmpty()) {
				nonCommonElements.addAll(operatorIds.stream().filter(e -> !operatorIdList.contains(e)).toList());
				nonCommonElements.addAll(operatorIdList.stream().filter(e -> !operatorIds.contains(e)).toList());
			}

			// 航路事業者情報を削除する
			airwayTenantMapper.deleteByAirwayId(airwayId);
			// 新しい関連情報を登録する
			airwayTenantMapper.insertAirwayTenantAssociation(airwayId, operatorIdList, userId, userId);

			// メール送信事業者ある場合、メール送信機能を呼び出し
			if(!nonCommonElements.isEmpty()) {
				sendEmail(airwayId, nonCommonElements);
			}
			
		} catch (DataAccessException e) {
			// ＤＢアクセスエラーが発生した
			String message = MessageUtils.getMessage("DR000E001");
			log.error(message,e);
			throw new Exception(message);
		} catch (Exception e) {
			// 更新失敗時
			String message = MessageUtils.getMessage("DRC01E001");
			log.error(message,e);
			throw new Exception(message);
		}
	}

	/**
	 * 航路事業者情報を削除する処理
	 * 
	 * @param airwayId 航路ID
	 * @throws Exception 削除処理が失敗した場合にスロー
	 */
	@Override
	@Transactional
	public void deleteAirwayTenant(String airwayId) throws Exception {

		log.info("航路事業者情報を削除サービス開始します。");

		// 1. 航路IDチェック
		if (airwayId == null || airwayId.isEmpty()) {
			String message = MessageFormat.format(MessageUtils.getMessage("DRC01E003"), KOURO_ID, airwayId);
			log.error(message);
			throw new BadRequestException(message);
		}

		// 2. 航路IDで航路事業者情報を検索
		int count = airwayTenantMapper.countByAirwayId(airwayId);
		if (count == 0) {
			// データが存在しない場合、エラーログ
			Object[] args = { KOURO_ID, airwayId };
			String message = MessageUtils.getMessage("DR000E007", args);
			log.error(message);
			throw new BadRequestException(message);
		}
		// 3. 航路事業者情報が存在する場合、削除処理を実行
		try {
			// 航路事業者情報削除前、情報を事業者IDリストに置く
			List<String> operatorIdList = airwayTenantMapper.findByAirwayId(airwayId);
			// 削除処理を実行
			airwayTenantMapper.deleteByAirwayId(airwayId);
			// メール送信機能を呼び出し
			sendEmail(airwayId, operatorIdList);
			log.info("航路事業者情報を削除サービス終了します。");
		} catch (DataAccessException e) {
			// ＤＢアクセスエラーが発生した
			String message = MessageUtils.getMessage("DR000E001");
			log.error(message,e);
			throw new Exception(message);
		} catch (Exception e) {
			// 削除失敗時
			String message = MessageUtils.getMessage("DRC01E001");
			log.error(message,e);
			throw new Exception(message);
		}
	}

	/**
	 * メール送信の航路情報・予約情報取得する
	 * 
	 * @param airwayId
	 * @param operatorIdList
	 */
	private void sendEmail(String airwayId, List<String> operatorIdList) {
		// 航路情報取得
		AirwayInfoEntity airwayInfo = airwayInfoMapper.findAirwayById(airwayId);
		
		// 送信先情報取得
		List<TenantNotificationEntity> tenantNotificationList = airwayTenantNotificationMapper
				.findAirwayTenantNotification(operatorIdList);

		if (airwayInfo != null && !tenantNotificationList.isEmpty()) {
			// 航路登録情報メール送信
			sendAirwayEmail(airwayInfo, tenantNotificationList);
		}

		// 航路予約情報取得
		List<AirwayReservationInfoEntity> airwayReservationInfoList = airwayReservationInfoMapper
				.findAirwayReservationInfo(airwayId);
		
		for (AirwayReservationInfoEntity airwayReservation : airwayReservationInfoList) {
		    // 航路予約情報メール送信
		    sendAirwayReservationEmail(airwayReservation, tenantNotificationList);
		}
	}

	/**
	 * 航路登録情報メール送信機能を呼び出す
	 * 
	 * @param airwayInfo             航路情報
	 * @param tenantNotificationList 事業者情報
	 */
	public void sendAirwayEmail(AirwayInfoEntity airwayInfo,
			List<TenantNotificationEntity> tenantNotificationList) {
		taskExecutor.execute(() -> {
			try {
				// メール送信処理を呼び出す
				mailSendService.sendAirwayMailMessage(airwayInfo, tenantNotificationList);
				log.info("航路登録情報メール送信完了 (スレッド名: " + Thread.currentThread().getName() + ")");
			} catch (Exception e) {
				log.error("非同期タスクでエラーが発生しました: {}", e.getMessage(), e);
			}
		});
	}
	
	
	/**
	 * 航路予約情報メール送信機能を呼び出す
	 * 
	 * @param airwayReservationInfo  航路予約情報
	 * @param tenantNotificationList 事業者情報
	 */
	public void sendAirwayReservationEmail(AirwayReservationInfoEntity airwayReservationInfo,
			List<TenantNotificationEntity> tenantNotificationList) {
		taskExecutor.execute(() -> {
			try {
				// メール送信処理を呼び出す
				List<String> aiywayNames = new ArrayList<>();
				aiywayNames.add(airwayReservationInfo.getAirwayName());
				mailSendService.sendAirwayReservationMailMessage(airwayReservationInfo,
						tenantNotificationList, aiywayNames);
				log.info("航路予約情報メール送信完了 (スレッド名: " + Thread.currentThread().getName() + ")");
			} catch (Exception e) {
				log.error("非同期タスクでエラーが発生しました: {}", e.getMessage(), e);
			}
		});
	}
}
