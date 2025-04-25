package jp.go.meti.drone.dips.controller.area;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.go.meti.drone.dips.apimodel.dips.DroneRouteFlightProhibitedAreaResponse;
import jp.go.meti.drone.dips.model.flightprohibited.FlightProhibitedAreaInfoRequest;
import jp.go.meti.drone.dips.service.area.FlightProhibitedAreaReceiverServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Slf4j
@ContextConfiguration(classes = { MockServletContext.class })
@WebAppConfiguration
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
public class FlightProhibitedAreaReceiverControllerTest {

    @Autowired
    FlightProhibitedAreaReceiverController flightProhibitedAreaReceiverController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightProhibitedAreaReceiverServiceImpl flightProhibitedAreaReceiverService;

    ObjectMapper mapper = new ObjectMapper();

    /**
     * 
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(flightProhibitedAreaReceiverController).build();
    }

    /**
     * 必須項目テスト 検索範囲がnullの場合
     * 
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        // 期待結果
        String expectStr = "{\"status\":400,\"errorMessage\":\"検索範囲 ジオメトリ（構成点）は必須項目です。\",\"data\":null}";

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.setFeatures(null);

        MvcResult mvcResult = getResponse400(request);

        assertEquals(expectStr, mvcResult.getResponse().getContentAsString());

        log.info("Testing 結果:" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * 必須項目テスト ジオメトリ（構成点） がnull,空の場合
     */
    @Test
    public void test2() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.getFeatures().setCoordinates(null);

        MvcResult mvcResult = getResponse400(request);

        // 期待結果
        String expectStr = "{\"status\":400,\"errorMessage\":\"検索範囲 ジオメトリ（構成点）は必須項目です。\",\"data\":null}";
        assertEquals(expectStr, mvcResult.getResponse().getContentAsString());

        log.info("Testing 結果:" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * 必須項目テスト ジオメトリ（構成点） が空の場合
     */
    @Test
    public void test3() throws Exception {

        List<List<Double>> coordinateList = new ArrayList<>();

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.getFeatures().setCoordinates(coordinateList);

        MvcResult mvcResult = getResponse400(request);

        // 期待結果
        String expectStr = "{\"status\":400,\"errorMessage\":\"検索範囲 ジオメトリ（構成点）は必須項目です。\",\"data\":null}";
        assertEquals(expectStr, mvcResult.getResponse().getContentAsString());

        log.info("Testing 結果:" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ジオメトリ（構成点）のサイズ<3の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes4() throws Exception {

        // 期待結果取得
        String expect = "{\"status\":400,\"errorMessage\":\"ジオメトリタイプで「Polygon：多角形」を指定した場合3点以上の構成点を設定してください。\",\"data\":null}";

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.getFeatures().getCoordinates().remove(0);
        request.getFeatures().getCoordinates().remove(1);

        // 結果取得
        MvcResult mvcResult = getResponse400(request);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test用結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ジオメトリ（構成点）のサイズ=3の場合、1件取得
     * 
     * @throws Exception
     */
    @Test
    public void tes5() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.getFeatures().getCoordinates().remove(0);

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("oneExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test用結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ジオメトリ（構成点）のサイズ=3の場合、正常値以外の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes6() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.getFeatures().getCoordinates().remove(0);
        request.getFeatures().getCoordinates().get(1).set(0, 189.42);

        // 結果取得
        MvcResult mvcResult = getResponse400(request);

        // 期待結果取得
        String expect = "{\"status\":400,\"errorMessage\":\"経度は-180～180以内の値を設定してください。\",\"data\":null}";

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test用結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ジオメトリ（構成点）のサイズ>3の場合、経度がNULLの場合
     * 
     * @throws Exception
     */
    @Test
    public void tes7() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.getFeatures().getCoordinates().get(1).set(0, null);

        // 結果取得
        MvcResult mvcResult = getResponse400(request);

        // 期待結果取得
        String expect = "{\"status\":400,\"errorMessage\":\"検索条件をご確認ください。\",\"data\":null}";

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test用結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ジオメトリ（構成点）のサイズ>3の場合、経度の値は<-180の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes9() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.getFeatures().getCoordinates().get(0).set(0, -190.0);

        // 結果取得
        MvcResult mvcResult = getResponse400(request);

        // 期待結果取得
        String expect = "{\"status\":400,\"errorMessage\":\"経度は-180～180以内の値を設定してください。\",\"data\":null}";

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test用結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ジオメトリ（構成点）のサイズ>3の場合、経度の値は>180の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes10() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.getFeatures().getCoordinates().get(0).set(0, 190.0);

        // 結果取得
        MvcResult mvcResult = getResponse400(request);

        // 期待結果取得
        String expect = "{\"status\":400,\"errorMessage\":\"経度は-180～180以内の値を設定してください。\",\"data\":null}";

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test用結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ジオメトリ（構成点）のサイズ>3の場合、-180<経度の値は<180の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes11() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("allExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

    }

    /**
     * ジオメトリ（構成点）のサイズ>3の場合、経度がNULLの場合
     * 
     * @throws Exception
     */
    @Test
    public void tes12() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.getFeatures().getCoordinates().get(1).set(1, null);

        // 結果取得
        MvcResult mvcResult = getResponse400(request);

        // 期待結果取得
        String expect = "{\"status\":400,\"errorMessage\":\"検索条件をご確認ください。\",\"data\":null}";

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test用結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ジオメトリ（構成点）のサイズ>3の場合、緯度の値は<-90の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes14() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.getFeatures().getCoordinates().get(0).set(1, -100.0);

        log.info("緯度の値は<-90の場合:" + request);
        // 結果取得
        MvcResult mvcResult = getResponse400(request);

        // 期待結果取得
        String expect = "{\"status\":400,\"errorMessage\":\"緯度は-90～90以内の値を設定してください。\",\"data\":null}";

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test用結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ジオメトリ（構成点）のサイズ>3の場合、緯度の値は>90の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes15() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.getFeatures().getCoordinates().get(0).set(1, 100.0);

        log.info("緯度の値は<-90の場合:" + request);
        // 結果取得
        MvcResult mvcResult = getResponse400(request);

        // 期待結果取得
        String expect = "{\"status\":400,\"errorMessage\":\"緯度は-90～90以内の値を設定してください。\",\"data\":null}";

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test用結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * 検索期間(FROM)入力チェック、検索期間(FROM)がNULLの場合
     * 
     * @throws Exception
     */
    @Test
    public void tes17() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.setStartTime(null);

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("allExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test用結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * 検索期間(FROM)入力チェック、検索期間(FROM)が空の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes18() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.setStartTime("");

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("allExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

    }

    /**
     * 検索期間(FROM)入力チェック、yyyyMMdd HHmm以外の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes20() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.setStartTime("2022/10/01 09:00");

        // 結果取得
        MvcResult mvcResult = getResponse400(request);

        // 期待結果取得
        String expect = "{\"status\":400,\"errorMessage\":\"検索期間日付をyyyyMMdd HHmmのように設定してください。\",\"data\":null}";

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * 検索期間(TO)入力チェック、検索期間(TO)がNULLの場合
     * 
     * @throws Exception
     */
    @Test
    public void tes21() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.setFinishTime(null);

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("allExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

    }

    /**
     * 検索期間(TO)入力チェック、検索期間(TO)が空の場合 <br>
     * ケース： 55,57,58,60,61,63,64,66 <br>
     * 経度がぬnull 移動がnull 飛行禁止エリア種別がnull
     * 
     * @throws Exception
     */
    @Test
    public void tes22() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.setFinishTime("");

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得

        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("No22Expect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test用結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * 検索期間(FROM)入力チェック、yyyyMMdd HHmm以外の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes24() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.setFinishTime("2022-10-01 09:00");

        // 結果取得
        MvcResult mvcResult = getResponse400(request);

        // 期待結果取得
        String expect = "{\"status\":400,\"errorMessage\":\"検索期間日付をyyyyMMdd HHmmのように設定してください。\",\"data\":null}";

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、レスポンス件数が0の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes28() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、（FROM）の値が"2022/10/01 09:00:00"の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes30() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、（TO）の値が"9999/12/31 23:59:00"の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes32() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、飛行禁止エリアIDが空の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes33() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、飛行禁止範囲（range）が空の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes34() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプが空の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes35() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、飛行禁止エリア種別が空の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes36() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプがPolygon,ジオメトリ (構成点）の経度が空の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes37() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプがPolygon,ジオメトリ (構成点）の経度値<-180.0
     * 
     * @throws Exception
     */
    @Test
    public void tes38() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプがPolygon,ジオメトリ (構成点）の経度値>180.0
     * 
     * @throws Exception
     */
    @Test
    public void tes40() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプがPolygon,ジオメトリ (構成点）の経度が空の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes41() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプがPolygon,ジオメトリ (構成点）の経度値<-90
     * 
     * @throws Exception
     */
    @Test
    public void tes42() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプがPolygon,ジオメトリ (構成点）の経度値>90.0
     * 
     * @throws Exception
     */
    @Test
    public void tes44() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプがCircle,経度が空の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes45() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプがCircle,経度<-180
     * 
     * @throws Exception
     */
    @Test
    public void tes46() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプがCircle,-180<経度値<180
     * 
     * @throws Exception
     */
    @Test
    public void tes47() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("No47Expect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプがCircle,経度>180.0
     * 
     * @throws Exception
     */
    @Test
    public void tes48() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプがCircle,緯度が空の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes49() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプがCircle,緯度<-90.0
     * 
     * @throws Exception
     */
    @Test
    public void tes50() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが200の場合、1件取得、ジオメトリタイプがCircle,緯度>90.0
     * 
     * @throws Exception
     */
    @Test
    public void tes52() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("zeroExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

    }

    /**
     * ステータスが500の場合、予期しないシステムエラーが発生しました。
     * 
     * @throws Exception
     */
    @Test
    public void tes67() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        // 結果取得
        MvcResult mvcResult = getResponse500(request);

        // 期待結果取得
        String expect = "{\"status\":500,\"errorMessage\":\"予期しないシステムエラーが発生しました。\",\"data\":null}";
        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());
    }

    /**
     * 追加 リクエストパラメータ： ジオメトリ（構成点） 始点と終点が異なる場合
     * 
     * @throws Exception
     */
    @Test
    public void tes68() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("allExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());
    }

    /**
     * 追加 リクエストパラメータ： ジオメトリ（構成点） 始点と終点が一致の場合
     * 
     * @throws Exception
     */
    @Test
    public void tes69() throws Exception {

        // リクエストパラメータ取得、設定
        FlightProhibitedAreaInfoRequest request = GetReqJson.getJson();
        request.getFeatures().getCoordinates().get(3).set(1, 35.68);

        // 結果取得
        MvcResult mvcResult = getResponse(request);

        // 期待結果取得
        DroneRouteFlightProhibitedAreaResponse responseFlightProhibitedArea = mapper.readValue(
            GetResJson.getJson("allExpect"),
            DroneRouteFlightProhibitedAreaResponse.class);
        String expect = mapper.writeValueAsString(responseFlightProhibitedArea);

        assertEquals(expect, mvcResult.getResponse().getContentAsString());

        log.info("test 結果：" + mvcResult.getResponse().getContentAsString());
    }

    /**
     * controller 呼び出す 結果取得 共通
     * 
     * @param fAreaInfoRequest
     * @return
     * @throws Exception
     */
    public MvcResult getResponse(FlightProhibitedAreaInfoRequest fAreaInfoRequest) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(fAreaInfoRequest);

        MvcResult mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/ｆlightProhibitedAreaReceiver")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        return mvcResult;
    }

    /**
     * controller 呼び出す 結果取得 共通
     * 
     * @param fAreaInfoRequest
     * @return
     * @throws Exception
     */
    public MvcResult getResponse400(FlightProhibitedAreaInfoRequest fAreaInfoRequest) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(fAreaInfoRequest);

        MvcResult mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/ｆlightProhibitedAreaReceiver")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andReturn();

        return mvcResult;
    }

    /**
     * controller 呼び出す 結果取得 共通
     * 
     * @param fAreaInfoRequest
     * @return
     * @throws Exception
     */
    public MvcResult getResponse500(FlightProhibitedAreaInfoRequest fAreaInfoRequest) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(fAreaInfoRequest);

        MvcResult mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/ｆlightProhibitedAreaReceiver")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andReturn();

        return mvcResult;
    }

}
