package jp.go.meti.drone.swim.service.convert;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.swim.repository.entity.SwimMaxFallRangeEntity;
import jp.go.meti.drone.swim.repository.entity.SwimMaxFallRangeEntityExample;
import jp.go.meti.drone.swim.repository.mapper.SwimMaxFallRangeRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * 事業者内ID生成処理
 */
@Slf4j
@Service
public class DesignatorAllocator {

    /** SwimMaxfallRangeテーブル内での登録や検索時の条件を記載したインターフェース */
    private SwimMaxFallRangeRepository swimMaxFallRangeRepository;

    // 事業者内IDの桁数
    private int digits = 3;
 
    // 事業者内IDの上限
    private String internalIdLimit = "999";

    private int baseNumber = 10; // 事業者内IDの基数

    /**
     * コンストラクタ
     * 
     * @param swimMaxFallRangeRepository SwimMaxfallRangeテーブル内での登録や検索時の条件を記載したインターフェース
     */
    public DesignatorAllocator(SwimMaxFallRangeRepository swimMaxFallRangeRepository) {
        this.swimMaxFallRangeRepository = swimMaxFallRangeRepository;
    }

    /**
     * 事業者番号をもとに事業者内IDを生成し返却
     * 
     * @param businessNumber 事業者番号
     * @return 事業者番号に紐づいた番号の一番大きい数字に＋1した数を返却
     */
    public String generateInternalIdentifier(String businessNumber) {

        SwimMaxFallRangeEntityExample example = new SwimMaxFallRangeEntityExample();
        example.createCriteria().andBusinessNumberEqualTo(businessNumber);
        example.setOrderByClause("internal_identifier DESC");
        example.setLimit(1);

        List<SwimMaxFallRangeEntity> list = swimMaxFallRangeRepository.selectByExample(example);

        String currentMax = list.isEmpty() ? null : list.get(0).getInternalIdentifier();

        String nextId = getNextId(currentMax);
        if (nextId == null) {
            throw new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), businessNumber + " : ID上限(" + internalIdLimit + ")を超過しました");
        }
        return nextId;
    }

    private String getNextId(String maxId) {

        if (maxId == null) {
            maxId = "0";
        }

        // 数値化してインクリメント
        int nextVal = Integer.parseInt(maxId, baseNumber) + 1;

        // 上限
        if (nextVal > Integer.parseInt(internalIdLimit, baseNumber)) {
            return null;
        }
        String base = Integer.toString(nextVal).toUpperCase();
        return "0".repeat(Math.max(0, digits - base.length())) + base;
    }

}
