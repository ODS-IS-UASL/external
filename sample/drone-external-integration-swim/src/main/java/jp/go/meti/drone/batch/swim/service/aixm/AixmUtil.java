package jp.go.meti.drone.batch.swim.service.aixm;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import com.cfar.swim.aixm.bind.AixmMarshaller;

import aero.aixm.AbstractAIXMFeatureType;
import aero.aixm.AirspaceActivationPropertyType;
import aero.aixm.AirspaceActivationType;
import aero.aixm.AirspaceBorderCrossingPropertyType;
import aero.aixm.AirspaceBorderCrossingTimeSlicePropertyType;
import aero.aixm.AirspaceBorderCrossingTimeSliceType;
import aero.aixm.AirspaceBorderCrossingTimeSliceType.Extension;
import aero.aixm.AirspaceBorderCrossingType;
import aero.aixm.AirspaceGeometryComponentPropertyType;
import aero.aixm.AirspaceGeometryComponentType;
import aero.aixm.AirspaceLayerClassPropertyType;
import aero.aixm.AirspaceLayerClassType;
import aero.aixm.AirspaceLayerPropertyType;
import aero.aixm.AirspaceLayerType;
import aero.aixm.AirspacePropertyType;
import aero.aixm.AirspaceTimeSlicePropertyType;
import aero.aixm.AirspaceTimeSliceType;
import aero.aixm.AirspaceType;
import aero.aixm.AirspaceVolumeDependencyPropertyType;
import aero.aixm.AirspaceVolumeDependencyType;
import aero.aixm.AirspaceVolumePropertyType;
import aero.aixm.AirspaceVolumeType;
import aero.aixm.CodeAirspaceActivityType;
import aero.aixm.CodeAirspaceAggregationType;
import aero.aixm.CodeAirspaceClassificationType;
import aero.aixm.CodeAirspaceDependencyType;
import aero.aixm.CodeAirspaceDesignatorType;
import aero.aixm.CodeAirspaceType;
import aero.aixm.CodeAltitudeUseType;
import aero.aixm.CodeMilitaryOperationsType;
import aero.aixm.CodeStatusAirspaceType;
import aero.aixm.CodeVerticalReferenceType;
import aero.aixm.CodeYesNoType;
import aero.aixm.CurvePropertyType;
import aero.aixm.NoSequenceType;
import aero.aixm.RoutePropertyType;
import aero.aixm.StandardLevelColumnPropertyType;
import aero.aixm.SurfacePropertyType;
import aero.aixm.TextNameType;
import aero.aixm.ValDistanceType;
import aero.aixm.ValDistanceVerticalType;
import aero.aixm.ValFLType;
import aero.aixm.message.AIXMBasicMessageType;
import aero.aixm.message.BasicMessageMemberAIXMPropertyType;

/**
 * AIXM-JAXBライブラリを扱うためのユーティリティクラス
 *
 * @version 1.0 2024/9/4
 */
public class AixmUtil {
    /** AIXMエレメントファクトリー */
    aero.aixm.ObjectFactory elementFactory;

    /** Open GIS関連クラスのファクトリー */
    net.opengis.gml.ObjectFactory gmlFactory;

    /** AIXMメッセージファクトリー */
    aero.aixm.message.ObjectFactory messageFactory;

    /**
     * コンストラクター
     */
    public AixmUtil() {
        super();
        this.messageFactory = new aero.aixm.message.ObjectFactory();
        this.elementFactory = new aero.aixm.ObjectFactory();
        this.gmlFactory = new net.opengis.gml.ObjectFactory();
    }

    /**
     * @param value AirspaceTypeクラスのインスタンス
     * @return AirspaceTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspace(aero.aixm.AirspaceType)
     */
    public JAXBElement<AirspaceType> createAirspace(AirspaceType value) {
        return elementFactory.createAirspace(value);
    }

    /**
     * @param value AirspaceActivationTypeクラスのインスタンス
     * @return AirspaceActivationTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceActivation(aero.aixm.AirspaceActivationType)
     */
    public JAXBElement<AirspaceActivationType> createAirspaceActivation(AirspaceActivationType value) {
        return elementFactory.createAirspaceActivation(value);
    }

    /**
     * @return AirspaceActivationPropertyTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceActivationPropertyType()
     */
    public AirspaceActivationPropertyType createAirspaceActivationPropertyType() {
        return elementFactory.createAirspaceActivationPropertyType();
    }

    /**
     * @return AirspaceActivationTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceActivationType()
     */
    public AirspaceActivationType createAirspaceActivationType() {
        return elementFactory.createAirspaceActivationType();
    }

    /**
     * @param value CodeAirspaceActivityTypeクラスのインスタンス
     * @return CodeAirspaceActivityTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceActivationTypeActivity(aero.aixm.CodeAirspaceActivityType)
     */
    public JAXBElement<CodeAirspaceActivityType> createAirspaceActivationTypeActivity(CodeAirspaceActivityType value) {
        return elementFactory.createAirspaceActivationTypeActivity(value);
    }

    /**
     * @return AirspaceActivationType.Extensionクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceActivationTypeExtension()
     */
    public aero.aixm.AirspaceActivationType.Extension createAirspaceActivationTypeExtension() {
        return elementFactory.createAirspaceActivationTypeExtension();
    }

    /**
     * @param value CodeStatusAirspaceTypeクラスのインスタンス
     * @return CodeStatusAirspaceTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceActivationTypeStatus(aero.aixm.CodeStatusAirspaceType)
     */
    public JAXBElement<CodeStatusAirspaceType> createAirspaceActivationTypeStatus(CodeStatusAirspaceType value) {
        return elementFactory.createAirspaceActivationTypeStatus(value);
    }

    /**
     * @param value AirspaceBorderCrossingTypeクラスのインスタンス
     * @return AirspaceBorderCrossingTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceBorderCrossing(aero.aixm.AirspaceBorderCrossingType)
     */
    public JAXBElement<AirspaceBorderCrossingType> createAirspaceBorderCrossing(AirspaceBorderCrossingType value) {
        return elementFactory.createAirspaceBorderCrossing(value);
    }

    /**
     * @return AirspaceBorderCrossingPropertyTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceBorderCrossingPropertyType()
     */
    public AirspaceBorderCrossingPropertyType createAirspaceBorderCrossingPropertyType() {
        return elementFactory.createAirspaceBorderCrossingPropertyType();
    }

    /**
     * @param value AirspaceBorderCrossingTimeSliceTypeクラスのインスタンス
     * @return AirspaceBorderCrossingTimeSliceTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceBorderCrossingTimeSlice(aero.aixm.AirspaceBorderCrossingTimeSliceType)
     */
    public JAXBElement<AirspaceBorderCrossingTimeSliceType> createAirspaceBorderCrossingTimeSlice(
        AirspaceBorderCrossingTimeSliceType value) {
        return elementFactory.createAirspaceBorderCrossingTimeSlice(value);
    }

    /**
     * @return AirspaceBorderCrossingTimeSlicePropertyTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceBorderCrossingTimeSlicePropertyType()
     */
    public AirspaceBorderCrossingTimeSlicePropertyType createAirspaceBorderCrossingTimeSlicePropertyType() {
        return elementFactory.createAirspaceBorderCrossingTimeSlicePropertyType();
    }

    /**
     * @return AirspaceBorderCrossingTimeSliceTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceBorderCrossingTimeSliceType()
     */
    public AirspaceBorderCrossingTimeSliceType createAirspaceBorderCrossingTimeSliceType() {
        return elementFactory.createAirspaceBorderCrossingTimeSliceType();
    }

    /**
     * @param value AirspacePropertyTypeクラスのインスタンス
     * @return AirspacePropertyTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceBorderCrossingTimeSliceTypeEnteredAirspace(aero.aixm.AirspacePropertyType)
     */
    public JAXBElement<AirspacePropertyType> createAirspaceBorderCrossingTimeSliceTypeEnteredAirspace(
        AirspacePropertyType value) {
        return elementFactory.createAirspaceBorderCrossingTimeSliceTypeEnteredAirspace(value);
    }

    /**
     * @param value AirspacePropertyTypeクラスのインスタンス
     * @return AirspacePropertyTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceBorderCrossingTimeSliceTypeExitedAirspace(aero.aixm.AirspacePropertyType)
     */
    public JAXBElement<AirspacePropertyType> createAirspaceBorderCrossingTimeSliceTypeExitedAirspace(
        AirspacePropertyType value) {
        return elementFactory.createAirspaceBorderCrossingTimeSliceTypeExitedAirspace(value);
    }

    /**
     * @return Extensionクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceBorderCrossingTimeSliceTypeExtension()
     */
    public Extension createAirspaceBorderCrossingTimeSliceTypeExtension() {
        return elementFactory.createAirspaceBorderCrossingTimeSliceTypeExtension();
    }

    /**
     * @return AirspaceBorderCrossingTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceBorderCrossingType()
     */
    public AirspaceBorderCrossingType createAirspaceBorderCrossingType() {
        return elementFactory.createAirspaceBorderCrossingType();
    }

    /**
     * @param value AirspaceGeometryComponentTypeクラスのインスタンス
     * @return AirspaceGeometryComponentTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceGeometryComponent(aero.aixm.AirspaceGeometryComponentType)
     */
    public JAXBElement<AirspaceGeometryComponentType> createAirspaceGeometryComponent(
        AirspaceGeometryComponentType value) {
        return elementFactory.createAirspaceGeometryComponent(value);
    }

    /**
     * @return AirspaceGeometryComponentPropertyTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceGeometryComponentPropertyType()
     */
    public AirspaceGeometryComponentPropertyType createAirspaceGeometryComponentPropertyType() {
        return elementFactory.createAirspaceGeometryComponentPropertyType();
    }

    /**
     * @return AirspaceGeometryComponentTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceGeometryComponentType()
     */
    public AirspaceGeometryComponentType createAirspaceGeometryComponentType() {
        return elementFactory.createAirspaceGeometryComponentType();
    }

    /**
     * @return AirspaceGeometryComponentType.Extensionクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceGeometryComponentTypeExtension()
     */
    public aero.aixm.AirspaceGeometryComponentType.Extension createAirspaceGeometryComponentTypeExtension() {
        return elementFactory.createAirspaceGeometryComponentTypeExtension();
    }

    /**
     * @param value CodeAirspaceAggregationTypeクラスのインスタンス
     * @return CodeAirspaceAggregationTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceGeometryComponentTypeOperation(aero.aixm.CodeAirspaceAggregationType)
     */
    public JAXBElement<CodeAirspaceAggregationType> createAirspaceGeometryComponentTypeOperation(
        CodeAirspaceAggregationType value) {
        return elementFactory.createAirspaceGeometryComponentTypeOperation(value);
    }

    /**
     * @param value NoSequenceTypeクラスのインスタンス
     * @return NoSequenceTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceGeometryComponentTypeOperationSequence(aero.aixm.NoSequenceType)
     */
    public JAXBElement<NoSequenceType> createAirspaceGeometryComponentTypeOperationSequence(NoSequenceType value) {
        return elementFactory.createAirspaceGeometryComponentTypeOperationSequence(value);
    }

    /**
     * @param value AirspaceLayerTypeクラスのインスタンス
     * @return AirspaceLayerTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceLayer(aero.aixm.AirspaceLayerType)
     */
    public JAXBElement<AirspaceLayerType> createAirspaceLayer(AirspaceLayerType value) {
        return elementFactory.createAirspaceLayer(value);
    }

    /**
     * @param value AirspaceLayerClassTypeクラスのインスタンス
     * @return AirspaceLayerClassTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceLayerClass(aero.aixm.AirspaceLayerClassType)
     */
    public JAXBElement<AirspaceLayerClassType> createAirspaceLayerClass(AirspaceLayerClassType value) {
        return elementFactory.createAirspaceLayerClass(value);
    }

    /**
     * @return AirspaceLayerClassPropertyTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceLayerClassPropertyType()
     */
    public AirspaceLayerClassPropertyType createAirspaceLayerClassPropertyType() {
        return elementFactory.createAirspaceLayerClassPropertyType();
    }

    /**
     * @return AirspaceLayerClassTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceLayerClassType()
     */
    public AirspaceLayerClassType createAirspaceLayerClassType() {
        return elementFactory.createAirspaceLayerClassType();
    }

    /**
     * @param value CodeAirspaceClassificationTypeクラスのインスタンス
     * @return CodeAirspaceClassificationTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceLayerClassTypeClassification(aero.aixm.CodeAirspaceClassificationType)
     */
    public JAXBElement<CodeAirspaceClassificationType> createAirspaceLayerClassTypeClassification(
        CodeAirspaceClassificationType value) {
        return elementFactory.createAirspaceLayerClassTypeClassification(value);
    }

    /**
     * @return AirspaceLayerClassType.Extensionクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceLayerClassTypeExtension()
     */
    public aero.aixm.AirspaceLayerClassType.Extension createAirspaceLayerClassTypeExtension() {
        return elementFactory.createAirspaceLayerClassTypeExtension();
    }

    /**
     * @return AirspaceLayerPropertyTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceLayerPropertyType()
     */
    public AirspaceLayerPropertyType createAirspaceLayerPropertyType() {
        return elementFactory.createAirspaceLayerPropertyType();
    }

    /**
     * @return AirspaceLayerTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceLayerType()
     */
    public AirspaceLayerType createAirspaceLayerType() {
        return elementFactory.createAirspaceLayerType();
    }

    /**
     * @param value CodeAltitudeUseTypeクラスのインスタンス
     * @return CodeAltitudeUseTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceLayerTypeAltitudeInterpretation(aero.aixm.CodeAltitudeUseType)
     */
    public JAXBElement<CodeAltitudeUseType> createAirspaceLayerTypeAltitudeInterpretation(CodeAltitudeUseType value) {
        return elementFactory.createAirspaceLayerTypeAltitudeInterpretation(value);
    }

    /**
     * @param value StandardLevelColumnPropertyTypeクラスのインスタンス
     * @return StandardLevelColumnPropertyTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceLayerTypeDiscreteLevelSeries(aero.aixm.StandardLevelColumnPropertyType)
     */
    public JAXBElement<StandardLevelColumnPropertyType> createAirspaceLayerTypeDiscreteLevelSeries(
        StandardLevelColumnPropertyType value) {
        return elementFactory.createAirspaceLayerTypeDiscreteLevelSeries(value);
    }

    /**
     * @return AirspaceLayerType.Extensionクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceLayerTypeExtension()
     */
    public aero.aixm.AirspaceLayerType.Extension createAirspaceLayerTypeExtension() {
        return elementFactory.createAirspaceLayerTypeExtension();
    }

    /**
     * @param value ValDistanceVerticalTypeクラスのインスタンス
     * @return ValDistanceVerticalTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceLayerTypeLowerLimit(aero.aixm.ValDistanceVerticalType)
     */
    public JAXBElement<ValDistanceVerticalType> createAirspaceLayerTypeLowerLimit(ValDistanceVerticalType value) {
        return elementFactory.createAirspaceLayerTypeLowerLimit(value);
    }

    /**
     * @param value CodeVerticalReferenceTypeクラスのインスタンス
     * @return CodeVerticalReferenceTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceLayerTypeLowerLimitReference(aero.aixm.CodeVerticalReferenceType)
     */
    public JAXBElement<CodeVerticalReferenceType> createAirspaceLayerTypeLowerLimitReference(
        CodeVerticalReferenceType value) {
        return elementFactory.createAirspaceLayerTypeLowerLimitReference(value);
    }

    /**
     * @param value ValDistanceVerticalTypeクラスのインスタンス
     * @return ValDistanceVerticalTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceLayerTypeUpperLimit(aero.aixm.ValDistanceVerticalType)
     */
    public JAXBElement<ValDistanceVerticalType> createAirspaceLayerTypeUpperLimit(ValDistanceVerticalType value) {
        return elementFactory.createAirspaceLayerTypeUpperLimit(value);
    }

    /**
     * @param value CodeVerticalReferenceTypeクラスのインスタンス
     * @return CodeVerticalReferenceTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceLayerTypeUpperLimitReference(aero.aixm.CodeVerticalReferenceType)
     */
    public JAXBElement<CodeVerticalReferenceType> createAirspaceLayerTypeUpperLimitReference(
        CodeVerticalReferenceType value) {
        return elementFactory.createAirspaceLayerTypeUpperLimitReference(value);
    }

    /**
     * @return AirspacePropertyTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspacePropertyType()
     */
    public AirspacePropertyType createAirspacePropertyType() {
        return elementFactory.createAirspacePropertyType();
    }

    /**
     * @param value AirspaceTimeSliceTypeクラスのインスタンス
     * @return AirspaceTimeSliceTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceTimeSlice(aero.aixm.AirspaceTimeSliceType)
     */
    public JAXBElement<AirspaceTimeSliceType> createAirspaceTimeSlice(AirspaceTimeSliceType value) {
        return elementFactory.createAirspaceTimeSlice(value);
    }

    /**
     * @return AirspaceTimeSlicePropertyTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceTimeSlicePropertyType()
     */
    public AirspaceTimeSlicePropertyType createAirspaceTimeSlicePropertyType() {
        return elementFactory.createAirspaceTimeSlicePropertyType();
    }

    /**
     * @return AirspaceTimeSliceTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceTimeSliceType()
     */
    public AirspaceTimeSliceType createAirspaceTimeSliceType() {
        return elementFactory.createAirspaceTimeSliceType();
    }

    /**
     * @param value CodeMilitaryOperationsTypeクラスのインスタンス
     * @return CodeMilitaryOperationsTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceTimeSliceTypeControlType(aero.aixm.CodeMilitaryOperationsType)
     */
    public JAXBElement<CodeMilitaryOperationsType> createAirspaceTimeSliceTypeControlType(
        CodeMilitaryOperationsType value) {
        return elementFactory.createAirspaceTimeSliceTypeControlType(value);
    }

    /**
     * @param value CodeAirspaceDesignatorTypeクラスのインスタンス
     * @return CodeAirspaceDesignatorTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceTimeSliceTypeDesignator(aero.aixm.CodeAirspaceDesignatorType)
     */
    public JAXBElement<CodeAirspaceDesignatorType> createAirspaceTimeSliceTypeDesignator(
        CodeAirspaceDesignatorType value) {
        return elementFactory.createAirspaceTimeSliceTypeDesignator(value);
    }

    /**
     * @param value CodeYesNoTypeクラスのインスタンス
     * @return CodeYesNoTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceTimeSliceTypeDesignatorICAO(aero.aixm.CodeYesNoType)
     */
    public JAXBElement<CodeYesNoType> createAirspaceTimeSliceTypeDesignatorICAO(CodeYesNoType value) {
        return elementFactory.createAirspaceTimeSliceTypeDesignatorICAO(value);
    }

    /**
     * @return AirspaceTimeSliceType.Extensionクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceTimeSliceTypeExtension()
     */
    public aero.aixm.AirspaceTimeSliceType.Extension createAirspaceTimeSliceTypeExtension() {
        return elementFactory.createAirspaceTimeSliceTypeExtension();
    }

    /**
     * @param value TextNameTypeクラスのインスタンス
     * @return TextNameTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceTimeSliceTypeLocalType(aero.aixm.TextNameType)
     */
    public JAXBElement<TextNameType> createAirspaceTimeSliceTypeLocalType(TextNameType value) {
        return elementFactory.createAirspaceTimeSliceTypeLocalType(value);
    }

    /**
     * @param value TextNameTypeクラスのインスタンス
     * @return TextNameTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceTimeSliceTypeName(aero.aixm.TextNameType)
     */
    public JAXBElement<TextNameType> createAirspaceTimeSliceTypeName(TextNameType value) {
        return elementFactory.createAirspaceTimeSliceTypeName(value);
    }

    /**
     * @param value RoutePropertyTypeクラスのインスタンス
     * @return RoutePropertyTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceTimeSliceTypeProtectedRoute(aero.aixm.RoutePropertyType)
     */
    public JAXBElement<RoutePropertyType> createAirspaceTimeSliceTypeProtectedRoute(RoutePropertyType value) {
        return elementFactory.createAirspaceTimeSliceTypeProtectedRoute(value);
    }

    /**
     * @param value CodeAirspaceTypeクラスのインスタンス
     * @return CodeAirspaceTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceTimeSliceTypeType(aero.aixm.CodeAirspaceType)
     */
    public JAXBElement<CodeAirspaceType> createAirspaceTimeSliceTypeType(CodeAirspaceType value) {
        return elementFactory.createAirspaceTimeSliceTypeType(value);
    }

    /**
     * @param value ValFLTypeクラスのインスタンス
     * @return ValFLTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceTimeSliceTypeUpperLowerSeparation(aero.aixm.ValFLType)
     */
    public JAXBElement<ValFLType> createAirspaceTimeSliceTypeUpperLowerSeparation(ValFLType value) {
        return elementFactory.createAirspaceTimeSliceTypeUpperLowerSeparation(value);
    }

    /**
     * @return AirspaceTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceType()
     */
    public AirspaceType createAirspaceType() {
        return elementFactory.createAirspaceType();
    }

    /**
     * @param value AirspaceVolumeTypeクラスのインスタンス
     * @return AirspaceVolumeTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolume(aero.aixm.AirspaceVolumeType)
     */
    public JAXBElement<AirspaceVolumeType> createAirspaceVolume(AirspaceVolumeType value) {
        return elementFactory.createAirspaceVolume(value);
    }

    /**
     * @param value AirspaceVolumeDependencyTypeクラスのインスタンス
     * @return AirspaceVolumeDependencyTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeDependency(aero.aixm.AirspaceVolumeDependencyType)
     */
    public JAXBElement<AirspaceVolumeDependencyType> createAirspaceVolumeDependency(
        AirspaceVolumeDependencyType value) {
        return elementFactory.createAirspaceVolumeDependency(value);
    }

    /**
     * @return AirspaceVolumeDependencyPropertyTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeDependencyPropertyType()
     */
    public AirspaceVolumeDependencyPropertyType createAirspaceVolumeDependencyPropertyType() {
        return elementFactory.createAirspaceVolumeDependencyPropertyType();
    }

    /**
     * @return AirspaceVolumeDependencyTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeDependencyType()
     */
    public AirspaceVolumeDependencyType createAirspaceVolumeDependencyType() {
        return elementFactory.createAirspaceVolumeDependencyType();
    }

    /**
     * @param value CodeAirspaceDependencyTypeクラスのインスタンス
     * @return CodeAirspaceDependencyTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeDependencyTypeDependency(aero.aixm.CodeAirspaceDependencyType)
     */
    public JAXBElement<CodeAirspaceDependencyType> createAirspaceVolumeDependencyTypeDependency(
        CodeAirspaceDependencyType value) {
        return elementFactory.createAirspaceVolumeDependencyTypeDependency(value);
    }

    /**
     * @return AirspaceVolumeDependencyType.Extensionクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeDependencyTypeExtension()
     */
    public aero.aixm.AirspaceVolumeDependencyType.Extension createAirspaceVolumeDependencyTypeExtension() {
        return elementFactory.createAirspaceVolumeDependencyTypeExtension();
    }

    /**
     * @return AirspaceVolumePropertyTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceVolumePropertyType()
     */
    public AirspaceVolumePropertyType createAirspaceVolumePropertyType() {
        return elementFactory.createAirspaceVolumePropertyType();
    }

    /**
     * @return AirspaceVolumeTypeクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeType()
     */
    public AirspaceVolumeType createAirspaceVolumeType() {
        return elementFactory.createAirspaceVolumeType();
    }

    /**
     * @param value CurvePropertyTypeクラスのインスタンス
     * @return CurvePropertyTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeTypeCentreline(aero.aixm.CurvePropertyType)
     */
    public JAXBElement<CurvePropertyType> createAirspaceVolumeTypeCentreline(CurvePropertyType value) {
        return elementFactory.createAirspaceVolumeTypeCentreline(value);
    }

    /**
     * @param value AirspaceVolumeDependencyPropertyTypeクラスのインスタンス
     * @return AirspaceVolumeDependencyPropertyTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeTypeContributorAirspace(aero.aixm.AirspaceVolumeDependencyPropertyType)
     */
    public JAXBElement<AirspaceVolumeDependencyPropertyType> createAirspaceVolumeTypeContributorAirspace(
        AirspaceVolumeDependencyPropertyType value) {
        return elementFactory.createAirspaceVolumeTypeContributorAirspace(value);
    }

    /**
     * @return AirspaceVolumeType.Extensionクラスのインスタンス
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeTypeExtension()
     */
    public aero.aixm.AirspaceVolumeType.Extension createAirspaceVolumeTypeExtension() {
        return elementFactory.createAirspaceVolumeTypeExtension();
    }

    /**
     * @param value SurfacePropertyTypeクラスのインスタンス
     * @return SurfacePropertyTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeTypeHorizontalProjection(aero.aixm.SurfacePropertyType)
     */
    public JAXBElement<SurfacePropertyType> createAirspaceVolumeTypeHorizontalProjection(SurfacePropertyType value) {
        return elementFactory.createAirspaceVolumeTypeHorizontalProjection(value);
    }

    /**
     * @param value ValDistanceVerticalTypeクラスのインスタンス
     * @return ValDistanceVerticalTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeTypeLowerLimit(aero.aixm.ValDistanceVerticalType)
     */
    public JAXBElement<ValDistanceVerticalType> createAirspaceVolumeTypeLowerLimit(ValDistanceVerticalType value) {
        return elementFactory.createAirspaceVolumeTypeLowerLimit(value);
    }

    /**
     * @param value CodeVerticalReferenceTypeクラスのインスタンス
     * @return CodeVerticalReferenceTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeTypeLowerLimitReference(aero.aixm.CodeVerticalReferenceType)
     */
    public JAXBElement<CodeVerticalReferenceType> createAirspaceVolumeTypeLowerLimitReference(
        CodeVerticalReferenceType value) {
        return elementFactory.createAirspaceVolumeTypeLowerLimitReference(value);
    }

    /**
     * @param value ValDistanceVerticalTypeクラスのインスタンス
     * @return ValDistanceVerticalTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeTypeMaximumLimit(aero.aixm.ValDistanceVerticalType)
     */
    public JAXBElement<ValDistanceVerticalType> createAirspaceVolumeTypeMaximumLimit(ValDistanceVerticalType value) {
        return elementFactory.createAirspaceVolumeTypeMaximumLimit(value);
    }

    /**
     * @param value CodeVerticalReferenceTypeクラスのインスタンス
     * @return CodeVerticalReferenceTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeTypeMaximumLimitReference(aero.aixm.CodeVerticalReferenceType)
     */
    public JAXBElement<CodeVerticalReferenceType> createAirspaceVolumeTypeMaximumLimitReference(
        CodeVerticalReferenceType value) {
        return elementFactory.createAirspaceVolumeTypeMaximumLimitReference(value);
    }

    /**
     * @param value ValDistanceVerticalTypeクラスのインスタンス
     * @return ValDistanceVerticalTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeTypeMinimumLimit(aero.aixm.ValDistanceVerticalType)
     */
    public JAXBElement<ValDistanceVerticalType> createAirspaceVolumeTypeMinimumLimit(ValDistanceVerticalType value) {
        return elementFactory.createAirspaceVolumeTypeMinimumLimit(value);
    }

    /**
     * @param value CodeVerticalReferenceTypeクラスのインスタンス
     * @return CodeVerticalReferenceTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeTypeMinimumLimitReference(aero.aixm.CodeVerticalReferenceType)
     */
    public JAXBElement<CodeVerticalReferenceType> createAirspaceVolumeTypeMinimumLimitReference(
        CodeVerticalReferenceType value) {
        return elementFactory.createAirspaceVolumeTypeMinimumLimitReference(value);
    }

    /**
     * @param value ValDistanceVerticalTypeクラスのインスタンス
     * @return ValDistanceVerticalTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeTypeUpperLimit(aero.aixm.ValDistanceVerticalType)
     */
    public JAXBElement<ValDistanceVerticalType> createAirspaceVolumeTypeUpperLimit(ValDistanceVerticalType value) {
        return elementFactory.createAirspaceVolumeTypeUpperLimit(value);
    }

    /**
     * @param value CodeVerticalReferenceTypeクラスのインスタンス
     * @return CodeVerticalReferenceTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeTypeUpperLimitReference(aero.aixm.CodeVerticalReferenceType)
     */
    public JAXBElement<CodeVerticalReferenceType> createAirspaceVolumeTypeUpperLimitReference(
        CodeVerticalReferenceType value) {
        return elementFactory.createAirspaceVolumeTypeUpperLimitReference(value);
    }

    /**
     * @param value ValDistanceTypeクラスのインスタンス
     * @return ValDistanceTypeクラスのJAXBElementエレメント
     * @see aero.aixm.ObjectFactory#createAirspaceVolumeTypeWidth(aero.aixm.ValDistanceType)
     */
    public JAXBElement<ValDistanceType> createAirspaceVolumeTypeWidth(ValDistanceType value) {
        return elementFactory.createAirspaceVolumeTypeWidth(value);
    }

    /**
     * @param value AIXMBasicMessageTypeクラスのインスタンス
     * @return AIXMBasicMessageTypeクラスのJAXBElementエレメント
     * @see aero.aixm.message.ObjectFactory#createAIXMBasicMessage(aero.aixm.message.AIXMBasicMessageType)
     */
    public JAXBElement<AIXMBasicMessageType> createAIXMBasicMessage(AIXMBasicMessageType value) {
        return messageFactory.createAIXMBasicMessage(value);
    }

    /**
     * @return AIXMBasicMessageTypeクラスのインスタンス
     * @see aero.aixm.message.ObjectFactory#createAIXMBasicMessageType()
     */
    public AIXMBasicMessageType createAIXMBasicMessageType() {
        return messageFactory.createAIXMBasicMessageType();
    }

    /**
     * AIXM形式のXMLを生成してテキストとして返す。
     *
     * @param valueList AIXMに含めるJAXBElementエレメントのリスト
     * @return 生成されたAIXMのXMLの内容
     * @throws JAXBException AIXMのXMLを出力エラー
     * @see javax.xml.bind.JAXBElement
     * @see aero.aixm.AbstractAIXMFeatureType
     */
    public String createAixmData(List<JAXBElement<? extends AbstractAIXMFeatureType>> valueList) throws JAXBException {
        // AIXMのXML生成用マーシャルクラス
        AixmMarshaller marshaller = new AixmMarshaller();

        // XMLのルートエレメントの要素を生成
        AIXMBasicMessageType xmlValue = this.messageFactory.createAIXMBasicMessageType();
        UUID xmlId = UUID.randomUUID();
        xmlValue.setId("uuid." + xmlId.toString());

        valueList.forEach(element -> {
            // XML内のデータ部分を生成
            BasicMessageMemberAIXMPropertyType property = this.messageFactory
                .createBasicMessageMemberAIXMPropertyType();
            property.setAbstractAIXMFeature(element);

            // XMLのデータ部分を設定
            xmlValue.getHasMember().add(property);
        });

        // AIXM出力用バッファーを用意
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

        // AIXMのXMLを出力
        marshaller.marshal(messageFactory.createAIXMBasicMessage(xmlValue), byteOutputStream);

        return byteOutputStream.toString();
    }

    /**
     * @return BasicMessageMemberAIXMPropertyTypeクラスのインスタンス
     * @see aero.aixm.message.ObjectFactory#createBasicMessageMemberAIXMPropertyType()
     */
    public BasicMessageMemberAIXMPropertyType createBasicMessageMemberAIXMPropertyType() {
        return messageFactory.createBasicMessageMemberAIXMPropertyType();
    }

    /**
     * aero.aixm パッケージ用のObjectFactoryクラスを返す。
     *
     * @return aero.aixm パッケージ用のObjectFactoryクラス
     * @see aero.aixm.ObjectFactory
     */
    public aero.aixm.ObjectFactory getElementFactory() {
        return elementFactory;
    }

    /**
     * @return gmlFactory
     */
    public net.opengis.gml.ObjectFactory getGmlFactory() {
        return gmlFactory;
    }

    /**
     * aero.aixm.message パッケージ用のObjectFactoryクラスを返す。
     *
     * @return messageFactory aero.aixm.message パッケージ用のObjectFactoryクラス
     * @see aero.aixm.message.ObjectFactory
     */
    public aero.aixm.message.ObjectFactory getMessageFactory() {
        return messageFactory;
    }

    /**
     * @param gmlFactory セットする gmlFactory
     */
    public void setGmlFactory(net.opengis.gml.ObjectFactory gmlFactory) {
        this.gmlFactory = gmlFactory;
    }

}
