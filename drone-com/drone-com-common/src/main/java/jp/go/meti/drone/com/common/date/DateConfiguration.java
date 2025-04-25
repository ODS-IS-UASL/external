package jp.go.meti.drone.com.common.date;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * 日時設定クラス。
 * 
 * @version $Revision$
 */
@Configuration
@Slf4j
public class DateConfiguration {

    /**
     * 現在日時の基準とする{@link Clock}オブジェクトを生成します。
     * 
     * <pre>
     * {@code offsetLocalDateTime}が指定されていた場合は、
     * システム日付から指定された日時へオフセットした{@link Clock}オブジェクトを生成します。
     * </pre>
     * 
     * @param offsetLocalDateTime 現在日時の基準日をずらすためのオフセット日付
     * @return 現在日時の基準とする{@link Clock}オブジェクト
     */
    @Bean
    Clock clock(@Value("${drone.com.system-date.offset:#{null}}") Optional<String> offsetLocalDateTime) {
        var now = Clock.systemDefaultZone();
        return offsetLocalDateTime.map(localDateTime -> {
            log.info("drone.com.system-date.offset: {}", localDateTime);
            var offsetDuration = Duration.between(
                LocalDateTime.now(now),
                LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return Clock.offset(now, offsetDuration);
        }).orElse(now);
    }
}
