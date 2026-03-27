package jp.go.meti.drone.swim.service.convert;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import jp.go.meti.drone.swim.repository.entity.SwimMaxFallRangeEntity;
import jp.go.meti.drone.swim.repository.entity.SwimMaxFallRangeEntityExample;
import jp.go.meti.drone.swim.repository.mapper.SwimMaxFallRangeRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * 航路ID変換処理
 */
@Slf4j
@Service
public class DesignatorConverter {

    /** 最大落下範囲のリポジトリ */
    private SwimMaxFallRangeRepository swimMaxFallRangeRepository;

    /**
     * コンストラクタ
     * 
     * @param swimMaxFallRangeRepository SWIM向け最大落下範囲テーブル
     */
    public DesignatorConverter(SwimMaxFallRangeRepository swimMaxFallRangeRepository) {
        this.swimMaxFallRangeRepository = swimMaxFallRangeRepository;
    }

    /**
     * DB中でのユニーク性を担保するためdesignator(8桁)を作成
     * 
     * @param swimOperatorId SWIM向け事業者ID(3桁)
     * @param internalIdentifier 事業者内ID(３桁)
     * @param typeId 系統ID
     * @param regionId 地域ID
     * @return 生成された航路ID
     * @throws DuplicateKeyException キー重複
     */
    public String convertId(String swimOperatorId, String internalIdentifier, String typeId, String regionId)
        throws DuplicateKeyException {

        String designator = swimOperatorId + internalIdentifier + regionId + typeId;

        // 生成した designator がすでに存在していた場合はエラー
        SwimMaxFallRangeEntityExample example = new SwimMaxFallRangeEntityExample();
        example.createCriteria().andSwimIdEqualTo(designator);
        List<SwimMaxFallRangeEntity> ent = this.swimMaxFallRangeRepository.selectByExample(example);
        if (!ent.isEmpty()) {
            log.error("designator重複");
            throw new DuplicateKeyException("designator重複: " + designator);
        }

        return designator;
    }
}
