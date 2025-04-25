package jp.go.meti.drone.dips.service.export;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.WktImportFlags;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.dips.apimodel.export.AirwayEntity;
import jp.go.meti.drone.dips.apimodel.export.AirwayJunctionsEntity;
import jp.go.meti.drone.dips.apimodel.export.AirwaySectionsEntity;
import jp.go.meti.drone.dips.apimodel.export.AirwaysEntity;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseNotFoundError;
import jp.go.meti.drone.dips.model.export.RequestAirwayIdList;
import jp.go.meti.drone.dips.model.export.ResponseSuccessAirway;
import jp.go.meti.drone.dips.model.export.dto.AirwayGetResult;
import jp.go.meti.drone.dips.model.export.dto.AirwayIdAndInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DIPS登録用航路情報エクスポートのサービスクラス
 *
 * @version 1.0 2024/11/05
 */
@Service("AirwayExportService")
@RequiredArgsConstructor
@Slf4j
public class DipsAirwayExportServiceImpl implements DipsAirwayExportService {

	/** GeoJSONファイルで利用するFEATURE_COLLECTIONの定数 */
	private static final String FEATURE_COLLECTION = "FeatureCollection";

	/** GeoJSONファイルで利用するFEATUREの定数 */
	private static final String FEATURE = "Feature";

	/** ZIPヘッダ情報のサイズ */
	private static final int SIZE = 22;
	
	@Autowired
	private RoutePlanningService routePlanningService;

    /**
     * DIPS登録用航路情報エクスポートする
     * <p>
     * 指定された航路IDで航路情報を取得し、DIPS2.0へ登録できるGeoJSON形式に加工して、ZIP形式のファイルを返却する。<br>
     * 複数の航路を指定された場合、航路単位でGeoJSONファイルへ加工し、1つにまとめたZIP形式のファイルを返却する。
     *
	 */
	public ResponseEntity<?> dipsAirwayExport(RequestAirwayIdList requestAirwayIdList) {

		log.info("DIPS登録用航路情報エクスポートのサービス開始します。");

		String errorMessage = "";

		// zipFilenameを取得する
		String zipName = getZipFileName();

		try {
			// 航路情報を取得
			List<AirwayIdAndInfoDto> routePlanningInfoList = getRoutePlanningInfo(requestAirwayIdList);

			//エクスポート用OutputStream作成
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream, StandardCharsets.UTF_8)) {
				// ZIPファイルにエントリを追加
			    for (AirwayIdAndInfoDto dto : routePlanningInfoList) {
			        // GeoJSONファイル形式に加工して、取得する
			        String[] routePlanningInfoGeoJson = getGeoJson(dto.getAirwayEntity(), dto.getAirwayId());
			        if (routePlanningInfoGeoJson.length > 0) {
			            ZipEntry entry = new ZipEntry(routePlanningInfoGeoJson[0]);
			            zipOutputStream.putNextEntry(entry);
			            zipOutputStream.write(routePlanningInfoGeoJson[1].getBytes());
			        }
			        zipOutputStream.closeEntry();
			    }
			}
			// zipストリームのチェック
			if (byteArrayOutputStream.size() <= SIZE) {
				errorMessage = MessageUtils.getMessage("DR002E003");
				log.error(errorMessage);
				// Not Found 指定した航路情報が取得できない場合
				CommonResponseNotFoundError internalServerError = new CommonResponseNotFoundError(
						HttpStatus.NOT_FOUND.value(), errorMessage);
				return internalServerError.errorResponse();
			} else {
				ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
				ResponseSuccessAirway response = new ResponseSuccessAirway(zipName, resource);
				log.info("DIPS登録用航路情報エクスポートのサービス終了します。");
				return response.successResponse();
			}
		} catch (Exception e) {
			errorMessage = MessageUtils.getMessage("DRC01E001");
			log.error(errorMessage,e);
			// Internal Server Error サーバーエラー
			CommonResponseInternalServerError internalServerError = new CommonResponseInternalServerError(
					HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);
			return internalServerError.errorResponse();
		}
	}

	/**
	 * 航路画定API呼び出して、指定された航路IDの航路情報を取得する
	 * 
	 * @param requestAirwayIdList
	 * @return 取得した航路情報リストDTO
	 */
	private List<AirwayIdAndInfoDto> getRoutePlanningInfo(RequestAirwayIdList requestAirwayIdList) {

		List<AirwayIdAndInfoDto> routePlanningInfoList = new ArrayList<>();

		List<String> airwayIdList = requestAirwayIdList.getAirwayIdList();
		AirwayGetResult getResult;
		for (String airwayId : airwayIdList) {
			// 航路情報を取得する
		    getResult = routePlanningService.getRoutePlanningInfo(airwayId);

			if (getResult.getStatusCode() == 200) {
			    AirwayIdAndInfoDto dto = new AirwayIdAndInfoDto();
			    dto.setAirwayId(airwayId);
			    dto.setAirwayEntity(getResult.getAirwayEntity());
				routePlanningInfoList.add(dto);
			} else {
				// 航路情報がない場合
				String errorMessage = MessageUtils.getMessage("DR002E004", airwayId);
				log.error(errorMessage);
			}
		}
		return routePlanningInfoList;
	}

	/**
	 * 航路情報をGeoJSON形式に加工して、航路名とGeoJSON形式情報を返却する
	 * 
	 * @param planningInfoJson 航路情報
	 * @return 航路GeoJSON情報
	 */
	private String[] getGeoJson(AirwayEntity getAirwayInfo, String airwayId){
		try {
			// 取得した航路情報がない場合
			if (getAirwayInfo.getAirway() == null || getAirwayInfo.getAirway().getAirways().isEmpty()) {
				// 航路情報がない場合
				String errorMessage = MessageUtils.getMessage("DR002E005", airwayId);
				log.error(errorMessage);
				String[] rst = {};
				return rst;
			} else {
		        String topAirwayName = getAirwayInfo.getAirway().getAirways().get(0).getAirwayName();
		        
				// 結果を格納するためのJSONオブジェクト
				JSONObject result = new JSONObject(new LinkedHashMap<>());
				result.put("type", FEATURE_COLLECTION);
				JSONArray features = new JSONArray();
				// 新しいFeatureを作成
				JSONObject feature = new JSONObject(new LinkedHashMap<>());
				feature.put("type", FEATURE);
				// 新しいポリゴンの座標を格納するリスト
				String geom = createGeoJson(getAirwayInfo.getAirway().getAirways());
                if(geom.isEmpty()) {
                    String[] rst = {};
                    return rst;
                }
				feature.put("geometry", new JSONObject(geom));
				features.put(feature);
				result.put("features", features);
				// 結果を返却
				String[] rst = { topAirwayName + "_" + airwayId + ".geojson", result.toString(2) };
				return rst;
			}
		} catch (Exception e) {
		    log.error("エクスポート処理中にエラーが発生しました。",e);
			throw e;
		}
	}
	
	/**
     * セクションのポリゴンを結合して1つのポリゴン情報として返却する
     * 
     * @param airways
     * @return 結合した航路のポリゴン情報
     */
	private String createGeoJson(List<AirwaysEntity> airways) {
	    String geoJson = "";
	    List<Geometry> polygons = new ArrayList<>();
        //1つの航路に紐づく航路情報件数分繰り返す。項目：airways
        for(AirwaysEntity airwayDetail: airways) {
            //セクション件数分、繰り返し以下の処理を実行する。項目：airwaySections
            for(AirwaySectionsEntity airwaySection : airwayDetail.getAirwaySections()) {
                
                //セクション情報がない場合は、異常なため破棄する。
                if(airwaySection.getAirwayJunctionIds().size() < 2) {
                    log.info("セクション情報がないため、スキップします。");
                    continue;
                }
                //セクションの1つ目の航路点IDを取得する。
                String airwayPoint1 = airwaySection.getAirwayJunctionIds().get(0);
                //ジャンクション情報から1つ目の航路点IDと一致するジャンクション情報を取得する。
                AirwayJunctionsEntity point1Info = airwayDetail.getAirwayJunctions().stream()
                    .filter(junction -> airwayPoint1.equals(junction.getAirwayJunctionId())).findFirst().orElse(null);

                //セクションの2つ目の航路点IDを取得する。
                String airwayPoint2 = airwaySection.getAirwayJunctionIds().get(1);
                //ジャンクション情報から2つ目の航路点IDと一致するジャンクション情報を取得する。
                AirwayJunctionsEntity point2Info = airwayDetail.getAirwayJunctions().stream()
                    .filter(junction -> airwayPoint2.equals(junction.getAirwayJunctionId())).findFirst().orElse(null);

                //ジャンクション情報が取得できない場合、異常なため破棄する。
                if(point1Info != null && point2Info != null) {
                    //ジャンクション1,2より、ポリゴンを形成する緯度経度情報を取得する。
                    List<List<BigDecimal>> p1LatLon = new ArrayList<>(point1Info.getAirways().get(0).getAirway().getGeometry().getCoordinates());
                    List<List<BigDecimal>> p2LatLon = new ArrayList<>(point2Info.getAirways().get(0).getAirway().getGeometry().getCoordinates());

                    //終点を除いて高度の高い2地点を算出
                    p1LatLon.removeLast();
                    List<List<BigDecimal>> p1HighLatLon= p1LatLon.stream().sorted(Comparator.comparing((List<BigDecimal> point) -> point.get(2)).reversed()).toList();
                    p2LatLon.removeLast();
                    List<List<BigDecimal>> p2HighLatLon= p2LatLon.stream().sorted(Comparator.comparing((List<BigDecimal> point) -> point.get(2)).reversed()).toList();
                    
                    //地点1と地点2の点を結んだ線分を作成。
                    StringBuilder lineString1St = new StringBuilder();
                    lineString1St.append("LINESTRING(");
                    //ジャンクション1の地点1を設定する。
                    lineString1St.append(p1HighLatLon.get(0).get(0)).append(" ").append(p1HighLatLon.get(0).get(1)).append(", ");
                    //ジャンクション2の地点2を設定する。
                    lineString1St.append(p2HighLatLon.get(1).get(0)).append(" ").append(p2HighLatLon.get(1).get(1)).append(")");
                    Geometry polyLine1 = GeometryEngine.geometryFromWkt(lineString1St.toString(), WktImportFlags.wktImportDefaults, Geometry.Type.Polyline);
                    
                    StringBuilder lineString2St = new StringBuilder();
                    lineString2St.append("LINESTRING(");
                    //ジャンクション1の地点2を設定する。
                    lineString2St.append(p1HighLatLon.get(1).get(0)).append(" ").append(p1HighLatLon.get(1).get(1)).append(", ");
                    //ジャンクション2の地点2を設定する。
                    lineString2St.append(p2HighLatLon.get(0).get(0)).append(" ").append(p2HighLatLon.get(0).get(1)).append(")");
                    Geometry polyLine2 = GeometryEngine.geometryFromWkt(lineString2St.toString(), WktImportFlags.wktImportDefaults, Geometry.Type.Polyline);
                    StringBuilder wktPolygonSt = new StringBuilder();
                    if(GeometryEngine.crosses(polyLine1, polyLine2, SpatialReference.create(4326))) {
                        wktPolygonSt.append("POLYGON((");
                        //ジャンクション1の地点1を設定する。
                        wktPolygonSt.append(p1HighLatLon.get(0).get(0)).append(" ").append(p1HighLatLon.get(0).get(1)).append(", ");
                        //ジャンクション1の地点2を設定する。
                        wktPolygonSt.append(p1HighLatLon.get(1).get(0)).append(" ").append(p1HighLatLon.get(1).get(1)).append(", ");
                        //ジャンクション2の地点2を設定する。
                        wktPolygonSt.append(p2HighLatLon.get(1).get(0)).append(" ").append(p2HighLatLon.get(1).get(1)).append(", ");
                        //ジャンクション2の地点1を設定する。
                        wktPolygonSt.append(p2HighLatLon.get(0).get(0)).append(" ").append(p2HighLatLon.get(0).get(1)).append(", ");
                        //終点(ジャンクション1の地点1)を設定する。
                        wktPolygonSt.append(p1HighLatLon.get(0).get(0)).append(" ").append(p1HighLatLon.get(0).get(1)).append("))");

                    }
                    else {
                        wktPolygonSt.append("POLYGON((");
                        //ジャンクション1の地点1を設定する。
                        wktPolygonSt.append(p1HighLatLon.get(0).get(0)).append(" ").append(p1HighLatLon.get(0).get(1)).append(", ");
                        //ジャンクション1の地点2を設定する。
                        wktPolygonSt.append(p1HighLatLon.get(1).get(0)).append(" ").append(p1HighLatLon.get(1).get(1)).append(", ");
                        //ジャンクション2の地点2を設定する。
                        wktPolygonSt.append(p2HighLatLon.get(0).get(0)).append(" ").append(p2HighLatLon.get(0).get(1)).append(", ");
                        //ジャンクション2の地点1を設定する。
                        wktPolygonSt.append(p2HighLatLon.get(1).get(0)).append(" ").append(p2HighLatLon.get(1).get(1)).append(", ");
                        //終点(ジャンクション1の地点1)を設定する。
                        wktPolygonSt.append(p1HighLatLon.get(0).get(0)).append(" ").append(p1HighLatLon.get(0).get(1)).append("))");
                    }
                    
                    Geometry polygon = GeometryEngine.geometryFromWkt(wktPolygonSt.toString(), WktImportFlags.wktImportDefaults, Geometry.Type.Polygon);
                    //ポリゴンリストに追加する。
                    polygons.add(polygon);
                }
            }
        }
        //ポリゴン情報が1件も存在しない場合、からのgeojsonを返却する。
        if(polygons.isEmpty()) return geoJson;
        //List→Arrayへ変換
        Geometry[] polygonsArray = polygons.toArray(new Geometry[polygons.size()]);
        //ポリゴンを結合して1つのポリゴンへ変換する。
        Geometry geom = GeometryEngine.union(polygonsArray, SpatialReference.create(4326));
        //変換後のポリゴンをGeoJSONへ変換
        geoJson = GeometryEngine.geometryToGeoJson(geom);
        return geoJson;
	    
	}

	/**
	 * zipFilenameを取得する
	 * 
	 * @return　zipファイル名
	 */
	private String getZipFileName() {
		Calendar instance = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(instance.getTime()) + "_FlightProhibitedArea.zip";
	}
}