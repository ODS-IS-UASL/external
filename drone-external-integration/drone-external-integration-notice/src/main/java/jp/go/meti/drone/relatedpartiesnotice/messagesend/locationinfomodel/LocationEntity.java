package jp.go.meti.drone.relatedpartiesnotice.messagesend.locationinfomodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * JSON マッピング用クラス
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationEntity {
    private String lat;
    private String lon;
    private String type;
    private String addresstype;
    private String name;
    @JsonProperty("display_name")
    private String displayName;
    private Address address;

    /**
     * JSON マッピング用クラス
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        // 県
        private String province;
        // 市
        private String city;
        // 郡
        private String county;
        // 町
        private String town;
        // 村
        private String village;
        // 区
        private String suburb;
    }
}
