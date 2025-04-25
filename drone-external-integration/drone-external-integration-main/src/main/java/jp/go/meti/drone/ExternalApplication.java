package jp.go.meti.drone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import jp.go.meti.drone.com.common.ComCommonConfiguration;

/**
 * SpringBootアプリケーション起動メインクラス。
 */
@SpringBootApplication
@EnableScheduling
@Import(ComCommonConfiguration.class)
public class ExternalApplication {

    /**
     * メイン。
     *
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(ExternalApplication.class, args);
    }
}
