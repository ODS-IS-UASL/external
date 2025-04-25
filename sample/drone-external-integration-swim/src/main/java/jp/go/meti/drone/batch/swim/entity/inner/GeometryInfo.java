package jp.go.meti.drone.batch.swim.entity.inner;

import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GeometryComponentを生成するためのポリゴンを保持
 *
 * @version 1.0 2024/11/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeometryInfo {

    /**
     * AirSpaceタグのID
     */
    String airSpaceId;

    /**
     * AirSpaceの短縮識別名
     */
    String designator;

    /**
     * AirSpaceの下限
     */
    String lowerLimit;

    /**
     * AirSpaceの上限
     */
    String upperLimit;

    /**
     * AirSpaceを構成する座標の件数(NOTE: 未使用)
     */
    BigInteger posCount;

    /**
     * AirSpaceを構成する座標のリスト（緯度、経度の順番）
     */
    List<Double> posList;

}
