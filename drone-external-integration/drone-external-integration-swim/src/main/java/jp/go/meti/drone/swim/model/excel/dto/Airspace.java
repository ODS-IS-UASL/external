package jp.go.meti.drone.swim.model.excel.dto;

import java.util.List;

import lombok.Data;

/**
 * シートごとのPOJOモデルを保持
 */
@Data
public class Airspace {

    /** FeatureシートのPOJOリスト */
    private List<Feature> features;

    /** object10シートのPOJOリスト */
    private List<Object10> object10;

    /** object13シートのPOJOリスト */
    private List<Object13> object13;

    /** object24シートのPOJOリスト */
    private List<Object24> object24;

    /** object29シートのPOJOリスト */
    private List<Object29> object29;

    /** object38シートのPOJOリスト */
    private List<Object38> object38;
}
