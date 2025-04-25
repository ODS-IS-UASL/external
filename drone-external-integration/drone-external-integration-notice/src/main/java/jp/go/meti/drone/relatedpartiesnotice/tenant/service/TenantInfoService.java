package jp.go.meti.drone.relatedpartiesnotice.tenant.service;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseNotFoundError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import jp.go.meti.drone.relatedpartiesnotice.tenant.model.ResponseTenantInfo;
import jp.go.meti.drone.relatedpartiesnotice.tenant.repository.entity.TenantInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.tenant.repository.mapper.TenantInfoMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 権限情報取得処理の実装クラス。
 * @version 1.0 2024/12/12
 */
@Slf4j
@Service
public class TenantInfoService {

	@Autowired
	private TenantInfoMapper tenantInfoMapper;

	/**
	 * 事業者IDをもとに紐づく権限情報を取得する。
	 * @param operatorId 事業者ID
	 * @return ResponseTenantInfo 
	 */
	public ResponseTenantInfo getTenantEntityInfo(String operatorId) {

		List<TenantInfoEntity> tenantInfoList = new ArrayList<>();
		try {
			tenantInfoList = tenantInfoMapper.getTenantInfoByOperatorId(operatorId);
		} catch (DataAccessException e) {
			// ＤＢアクセスエラーが発生しました。メッセージとして出力される
			String message = MessageUtils.getMessage("DR000E001");
			log.error(message,e);
			throw new CommonResponseInternalServerError(500,message);
		} catch (Exception e) {
			// 検索失敗時
			String message = MessageUtils.getMessage("DRC01E001");
			log.error(message,e);
			throw new CommonResponseInternalServerError(500,message);
		}

		if (tenantInfoList == null || tenantInfoList.isEmpty()) {       	
			Object[] args = { "operatorId", operatorId };
			log.error("getTenantEntityInfo:" + MessageUtils.getMessage("DR000E007", args));
			throw new CommonResponseNotFoundError(404, MessageUtils.getMessage("DR000E007", args));
		}

		Map<String, List<String>> operatorRolesMap = tenantInfoList.stream()
				.collect(Collectors.groupingBy(
						TenantInfoEntity::getOperatorId,
						Collectors.mapping(
								TenantInfoEntity::getRole,
								Collectors.toList()  
								)
						));

		String operatorName = tenantInfoList.getFirst().getOperatorName();
		List<String> roleList = operatorRolesMap.get(operatorId);

		return  new ResponseTenantInfo(operatorId, operatorName, roleList);

	}
}