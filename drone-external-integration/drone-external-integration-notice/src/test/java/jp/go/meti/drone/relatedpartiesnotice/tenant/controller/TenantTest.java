package jp.go.meti.drone.relatedpartiesnotice.tenant.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import jp.go.meti.drone.dips.controller.area.GetResJson;
import jp.go.meti.drone.relatedpartiesnotice.tenant.model.DroneRouteResponseOperatorList;
import jp.go.meti.drone.relatedpartiesnotice.tenant.service.TenantService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ContextConfiguration(classes = { MockServletContext.class })
@WebAppConfiguration
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
public class TenantTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	TenantController relatedPartiesController;

	@Autowired
	private TenantService tenantService;

	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(relatedPartiesController).build();
	}

	/**
	 * 事業者ID＝null<br>
	 * 航路ID=nullの場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {

		// リクエストパラメータ
		String operatorId = null;
		String airwayId = null;

		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);

		// 期待結果取得
		DroneRouteResponseOperatorList responseOperatorList = mapper.readValue(GetResJson.getTenantJson("all"),
				DroneRouteResponseOperatorList.class);
		String expect = mapper.writeValueAsString(responseOperatorList);
		assertEquals(expect, mvcResult.getResponse().getContentAsString());
	}

	/**
	 * 事業者ID＝値<br>
	 * 航路ID=値の場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test2() throws Exception {

		// リクエストパラメータ
		String operatorId = "70782784-568c-62df-4ff3-fb3e90051c00";
		String airwayId = "70782784-568c-62dfd-fb3e90051c00-U00";

		// 結果取得
		MvcResult mvcResult = getResponse400(operatorId, airwayId);

		// 期待結果取得
		String expect = "{\n" + "    \"code\": 400,\n" + "    \"errorMessage\": \"指定されたパラメータが不正です。\"\n" + "}";
		assertEquals(expect, mvcResult.getResponse().getContentAsString());
	}

	/**
	 * 事業者ID＝値, 航路ID=nullの場合<br>
	 * 事業者ID＞36桁
	 * 
	 * @throws Exception
	 */
	@Test
	public void test3() throws Exception {

		// リクエストパラメータ
		String operatorId = "70782784-568c-62df-4ff3-fb3e90051c002";
		String airwayId = null;
		// 結果取得
		MvcResult mvcResult = getResponse400(operatorId, airwayId);
		// 期待結果
		String expect = "{\n" + "    \"code\": 400,\n" + "    \"errorMessage\": \"指定されたパラメータが不正です。\"\n" + "}";

		assertEquals(expect, mvcResult.getResponse().getContentAsString());
		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());
	}

	/**
	 * 事業者ID＝値, 航路ID=nullの場合<br>
	 * 事業者ID=36桁
	 * 
	 * @throws Exception
	 */
	@Test
	public void test4() throws Exception {

		// リクエストパラメータ
		String operatorId = "70782784-568c-62df-4ff3-fb3e90051c00";
		String airwayId = null;

		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);

		// 期待結果
		String expect = "{\"operatorList\":[{\"operatorId\":\"70782784-568c-62df-4ff3-fb3e90051c00\",\"operatorName\":\"dips1\",\"notificationType\":\"1\",\"roleList\":[\"1\",\"2\",\"3\"],\"notificationTargetList\":[\"000000000@gmail.com\",\"000000001@gmail.com\",\"000000002@gmail.com\"],\"linkAirwayList\":[\"70782784-568c-62dfd-fb3e90051c00-U00\",\"70782784-568c-62dfd-fb3e90051c00-U01\"]}]}";
		assertEquals(expect, mvcResult.getResponse().getContentAsString());
	}

	/**
	 * 事業者ID=null, 航路ID=値の場合<br>
	 * 航路ID＞36桁
	 * 
	 * @throws Exception
	 */
	@Test
	public void test5() throws Exception {

		// リクエストパラメータ
		String operatorId = null;
		String airwayId = "70782784-568c-62dfd-fb3e90051c00-U002";

		// 結果取得
		MvcResult mvcResult = getResponse400(operatorId, airwayId);
		// 期待結果
		String expect = "{\n" + "    \"code\": 400,\n" + "    \"errorMessage\": \"指定されたパラメータが不正です。\"\n" + "}";

		assertEquals(expect, mvcResult.getResponse().getContentAsString());
		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());
	}

	/**
	 * 事業者ID=null, 航路ID=値の場合<br>
	 * 航路ID=36桁
	 * 
	 * @throws Exception
	 */
	@Test
	public void test6() throws Exception {

		// リクエストパラメータ
		String operatorId = null;
		String airwayId = "70782784-568c-62dfd-fb3e90051c00-U03";

		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);
		// 期待結果
		String expect = "{\"operatorList\":[{\"operatorId\":\"70782784-568c-62df-4ff3-fb3e90051c01\",\"operatorName\":\"dips2\",\"notificationType\":\"1\",\"roleList\":[\"1\"],\"notificationTargetList\":[\"000000003@gmail.com\",\"000000004@gmail.com\"],\"linkAirwayList\":[\"70782784-568c-62dfd-fb3e90051c00-U03\"]}]}";
		assertEquals(expect, mvcResult.getResponse().getContentAsString());

		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());
	}

	/**
	 * 事業者情報取得結果チェック,取得件数０件の場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test7() throws Exception {

		// リクエストパラメータ
		String operatorId = "70782784-568c-62df-4ff3-fb3e90051c20";
		String airwayId = "";
		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);
		// 期待結果取得
		String expect = "{\"operatorList\":[]}";
		assertEquals(expect, mvcResult.getResponse().getContentAsString());
		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());
	}

	/**
	 * 事業者情報取得結果チェック,取得件数=１件の場合,事業者IDが空の場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test8() throws Exception {

		// リクエストパラメータ
		String operatorId = "70782784-568c-62df-4ff3-fb3e90051c11";
		String airwayId = "";

		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);

		// 期待結果取得
		String expect = "{\"operatorList\":[]}";
		assertEquals(expect, mvcResult.getResponse().getContentAsString());
		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

	}

	/**
	 * 事業者情報取得結果チェック,取得件数=１件の場合,事業者名称が空の場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test9() throws Exception {

		// リクエストパラメータ
		String operatorId = "70782784-568c-62df-4ff3-fb3e90051c13";
		String airwayId = null;

		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);
		// 期待結果取得
		String expect = "{\"operatorList\":[]}";

		assertEquals(expect, mvcResult.getResponse().getContentAsString());
		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

	}

	/**
	 * 事業者情報取得結果チェック,取得件数=１件の場合,周知方法が空の場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test10() throws Exception {

		// リクエストパラメータ
		String operatorId = "70782784-568c-62df-4ff3-fb3e90051c12";
		String airwayId = null;

		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);
		// 期待結果取得
		String expect = "{\"operatorList\":[]}";
		assertEquals(expect, mvcResult.getResponse().getContentAsString());
		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

	}

	/**
	 * 事業者情報取得結果チェック,取得件数=１件の場合,事業者情報取得が空の場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test11() throws Exception {

		// リクエストパラメータ
		String operatorId = "70782784-568c-62df-4ff3-fb3e90051c14";
		String airwayId = null;

		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);

		// 期待結果取得
		String expect = "{\"operatorList\":[]}";
		assertEquals(expect, mvcResult.getResponse().getContentAsString());
		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

	}

	/**
	 * 事業者情報取得結果チェック,取得件数=１件の場合,権限情報取得が空の場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test12() throws Exception {

		// リクエストパラメータ
		String operatorId = "70782784-568c-62df-4ff3-fb3e90051c15";
		String airwayId = null;

		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);
		// 期待結果取得
		String expect = "{\"operatorList\":[]}";

		assertEquals(expect, mvcResult.getResponse().getContentAsString());
		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

	}

	/**
	 * 異常の場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test20() throws Exception {

		// リクエストパラメータ
		String operatorId = "70782784-568c-62df-4ff3-fb3e90051c00";
		String airwayId = null;

		// 結果取得
		MvcResult mvcResult = getResponse500(operatorId, airwayId);

		// 期待結果取得
		String expect = "{\n" + "    \"code\": 500,\n" + "    \"errorMessage\": \"予期しないシステムエラーが発生しました。\"\n" + "}";
		assertEquals(expect, mvcResult.getResponse().getContentAsString());
		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

	}

	/**
	 * 航路IDより取得レスポンスチェック,事業者IDが空の場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test21() throws Exception {

		// リクエストパラメータ
		String operatorId = null;
		String airwayId = "70782784-568c-62dfd-fb3e90051c00-U11";

		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);

		// 期待結果取得
		String expect = "{\"operatorList\":[]}";
		assertEquals(expect, mvcResult.getResponse().getContentAsString());
		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

	}

	/**
	 * 航路IDより取得レスポンスチェック,事業者名称が空の場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test22() throws Exception {

		// リクエストパラメータ
		String operatorId = null;
		String airwayId = "70782784-568c-62dfd-fb3e90051c00-U13";

		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);
		// 期待結果取得
		String expect = "{\"operatorList\":[]}";

		assertEquals(expect, mvcResult.getResponse().getContentAsString());
		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

	}

	/**
	 * 航路IDより取得レスポンスチェック,周知方法が空の場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test23() throws Exception {

		// リクエストパラメータ
		String operatorId = null;
		String airwayId = "70782784-568c-62dfd-fb3e90051c00-U12";

		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);
		// 期待結果取得
		String expect = "{\"operatorList\":[]}";
		assertEquals(expect, mvcResult.getResponse().getContentAsString());
		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

	}

	/**
	 * 航路IDより取得レスポンスチェック,事業者情報取得が空の場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test24() throws Exception {

		// リクエストパラメータ
		String operatorId = null;
		String airwayId = "70782784-568c-62dfd-fb3e90051c00-U14";

		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);

		// 期待結果取得
		String expect = "{\"operatorList\":[]}";
		assertEquals(expect, mvcResult.getResponse().getContentAsString());
		log.info("test 結果：" + mvcResult.getResponse().getContentAsString());

	}

	/**
	 * 航路IDより取得レスポンスチェック,権限情報取得が空の場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void test25() throws Exception {

		// リクエストパラメータ
		String operatorId = null;
		String airwayId = "70782784-568c-62dfd-fb3e90051c00-U15";

		// 結果取得
		MvcResult mvcResult = getResponse(operatorId, airwayId);
		// 期待結果取得
		String expect = "{\"operatorList\":[]}";
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
	public MvcResult getResponse(String operatorId, String airwayId) throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/operator").param("operatorId", operatorId)
						.param("airwayId", airwayId).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
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
	public MvcResult getResponse400(String operatorId, String airwayId) throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/operator").param("operatorId", operatorId)
						.param("airwayId", airwayId).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
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
	public MvcResult getResponse404(String operatorId, String airwayId) throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/operator").param("operatorId", operatorId)
						.param("airwayId", airwayId).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
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
	public MvcResult getResponse500(String operatorId, String airwayId) throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/operator").param("operatorId", operatorId)
						.param("airwayId", airwayId).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError())
				.andReturn();

		return mvcResult;
	}

}
