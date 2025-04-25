package jp.go.meti.drone.com.common.date;

import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * システム日時取得機能の実装クラス。
 * 
 * @version $Revision$
 */
@Component
@RequiredArgsConstructor
public class SystemDate {

    /**
     * 現在日時の基準とする{@link Clock}オブジェクト。
     */
    private final Clock clock;

    /**
     * 現在日時を示す{@link LocalDateTime}を取得する。
     * 
     * @return 現在日時を示す{@link LocalDateTime}
     */
    public LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

}
