package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.model.RequestRouteTenantInfo;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.model.RequestUpdateRouteTenantInfo;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.entity.AirwayTenantAssociationEntity;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.mapper.AirwayTenantAssociationMapper;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.mapper.TenantMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ComponentScan(basePackages = "jp.go.meti.drone")
@ContextConfiguration(classes = { AirwayTenantController.class })
class AirwayTenantControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	AirwayTenantAssociationMapper airwayTenantAssociationMapper;

	@Autowired
	TenantMapper ｒelatedPartiesMapper;

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {

		// テスト実行前航路事業者情報DBデータを作成
		List<String> dbOperatorIds = Arrays.asList("0000000000000000000000000000000000-3",
				"0000000000000000000000000000000000-4");
		airwayTenantAssociationMapper.insertAirwayTenantAssociation(
				"airway_id_0000000000000000000002", dbOperatorIds, -1, -1);

	}

	/**
	 * addAirwayTenantメソッド
	 * <p>
	 * バリデーションairwayIdの@NotEmptyの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testAddAirwayTenant_error1() throws Exception {

		// 入力データの準備
		RequestRouteTenantInfo requestRouteTenantInfo = RequestRouteTenantInfo.builder()
				.airwayId("")
				.relatedPartiesIdList(
						Arrays.asList("0000000000000000000000000000000000-1", "0000000000000000000000000000000000-2"))
				.build();

		String jsonString = objectMapper.writeValueAsString(requestRouteTenantInfo);

		mockMvc.perform(
				post("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest()).andExpect(content().string(containsString("航路IDは必須項目です。")));

	}

	/**
	 * addAirwayTenantメソッド
	 * <p>
	 * バリデーションoperatorIdListの@NotNullの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testAddAirwayTenant_error2() throws Exception {

		// 入力データの準備
		RequestRouteTenantInfo requestRouteTenantInfo = RequestRouteTenantInfo.builder()
				.airwayId("airway_id_0000000000000000000001").build();

		String jsonString = objectMapper.writeValueAsString(requestRouteTenantInfo);

		mockMvc.perform(
				post("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest()).andExpect(content().string(containsString("事業者IDリストは必須項目です。")));

	}

	/**
	 * addAirwayTenantメソッド
	 * <p>
	 * バリデーションoperatorIdの@NotBlankの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testAddAirwayTenant_error3() throws Exception {

		// 入力データの準備
		RequestRouteTenantInfo requestRouteTenantInfo = RequestRouteTenantInfo.builder()
				.airwayId("airway_id_0000000000000000000001").relatedPartiesIdList(Arrays.asList("")).build();

		String jsonString = objectMapper.writeValueAsString(requestRouteTenantInfo);

		mockMvc.perform(
				post("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest()).andExpect(content().string(containsString("事業者IDは必須項目です。")));

	}

	/**
	 * addAirwayTenantメソッド
	 * <p>
	 * バリデーションoperatorIdの@Sizeの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testAddAirwayTenant_error4() throws Exception {

		// 入力データの準備
		RequestRouteTenantInfo requestRouteTenantInfo = RequestRouteTenantInfo.builder()
				.airwayId("airway_id_0000000000000000000001")
				.relatedPartiesIdList(Arrays.asList("0000000000000000000000000000000000000-01")).build();

		String jsonString = objectMapper.writeValueAsString(requestRouteTenantInfo);

		mockMvc.perform(
				post("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("事業者IDの型が正しくありません。36桁の整数を設定してください。")));

	}

	/**
	 * addAirwayTenantメソッドのテスト
	 * <p>
	 * 異常系:(事業者情報存在チェック)指定された事業者情報の取得結果が0件<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testAddAirwayTenant_error5() throws Exception {

		// 入力データの準備
		RequestRouteTenantInfo requestRouteTenantInfo = RequestRouteTenantInfo.builder()
				.airwayId("airway_id_0000000000000000000002")
				.relatedPartiesIdList(Arrays.asList("0000000000000000000000000000000000-8")).build();

		String jsonString = objectMapper.writeValueAsString(requestRouteTenantInfo);

		mockMvc.perform(
				post("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isNotFound()).andExpect(
						content().string(containsString("指定された事業者ID(0000000000000000000000000000000000-8)は存在しません。")));

	}

	/**
	 * addAirwayTenantメソッドのテスト
	 * <p>
	 * 異常系:(紐づき情報存在チェック)航路事業者情報テーブルにデータが存在する<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testAddAirwayTenant_error6() throws Exception {

		// 入力データの準備
		RequestRouteTenantInfo requestRouteTenantInfo = RequestRouteTenantInfo.builder()
				.airwayId("airway_id_0000000000000000000002")
				.relatedPartiesIdList(Arrays.asList("0000000000000000000000000000000000-1")).build();

		String jsonString = objectMapper.writeValueAsString(requestRouteTenantInfo);

		mockMvc.perform(
				post("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest()).andExpect(content()
						.string(containsString("航路ID(airway_id_0000000000000000000002)に紐づく事業者情報はすでに存在しています。")));

	}

	/**
	 * addAirwayTenantメソッドのテスト
	 * <p>
	 * 異常系:サーバーエラー<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testAddAirwayTenant_error7() throws Exception {

		// テーブル削除を実行
		jdbcTemplate.execute("DROP TABLE IF EXISTS AIRWAY_TENANT_ASSOCIATION");

		// 入力データの準備
		RequestRouteTenantInfo entityInfo = RequestRouteTenantInfo.builder()
				.airwayId("airway_id_0000000000000000000001")
				.relatedPartiesIdList(
						Arrays.asList("0000000000000000000000000000000000-1", "0000000000000000000000000000000000-2"))
				.build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				post("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isInternalServerError());
	}

	/**
	 * addAirwayTenantメソッドのテスト
	 * <p>
	 * 正常系:航路事業者情報を登録検証<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testAddAirwayTenant_normal() throws Exception {

		// 入力データの準備
		RequestRouteTenantInfo entityInfo = RequestRouteTenantInfo.builder()
				.airwayId("airway_id_0000000000000000000001")
				.relatedPartiesIdList(
						Arrays.asList("0000000000000000000000000000000000-1", "0000000000000000000000000000000000-2"))
				.build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				post("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk()).andExpect(content().string("")).andReturn();

		// データベースに保存されたデータを確認
		List<AirwayTenantAssociationEntity> foundAirwayIdAndOperatorIds = airwayTenantAssociationMapper
				.foundAirwayIdAndOperatorIds(entityInfo.getAirwayId(),
						entityInfo.getRelatedPartiesIdList());
		for (int i = 0; i < foundAirwayIdAndOperatorIds.size(); i++) {
			assertTrue(
					foundAirwayIdAndOperatorIds.get(i).getAirwayId().contains(entityInfo.getAirwayId()));
			assertTrue(foundAirwayIdAndOperatorIds.get(i).getOperatorId()
					.contains(entityInfo.getRelatedPartiesIdList().get(i)));
		}

	}

	/**
	 * updateAirwayTenantメソッド
	 * <p>
	 * バリデーションupdateTypesの@NotBlankの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateAirwayTenant_error1() throws Exception {

		// 入力データの準備
		RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("")
				.airwayId("airway_id_0000000000000000000001")
				.relatedPartiesIdList(
						Arrays.asList("0000000000000000000000000000000000-1", "0000000000000000000000000000000000-2"))
				.build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest()).andExpect(content().string(containsString("更新種別は必須項目です。")));

	}

	/**
	 * updateAirwayTenantメソッド
	 * <p>
	 * バリデーションupdateTypesの@Patternの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateAirwayTenant_error2() throws Exception {

		// 入力データの準備
		RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("6")
				.airwayId("airway_id_0000000000000000000001")
				.relatedPartiesIdList(
						Arrays.asList("0000000000000000000000000000000000-1", "0000000000000000000000000000000000-2"))
				.build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("更新種別は0(上書き更新)または、1(追加更新)を指定してください。")));

	}

	/**
	 * updateAirwayTenantメソッド
	 * <p>
	 * バリデーションairwayIdの@NotEmptyの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateAirwayTenant_error3() throws Exception {

		// 入力データの準備
		RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("0")
				.airwayId("")
				.relatedPartiesIdList(
						Arrays.asList("0000000000000000000000000000000000-1", "0000000000000000000000000000000000-2"))
				.build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest()).andExpect(content().string(containsString("航路IDは必須項目です。")));

	}

	/**
	 * updateAirwayTenantメソッド
	 * <p>
	 * バリデーションoperatorIdListの@NotNullの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateAirwayTenant_error4() throws Exception {

		// 入力データの準備
		RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("0")
				.airwayId("airway_id_0000000000000000000001").build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest()).andExpect(content().string(containsString("事業者IDリストは必須項目です。")));

	}

	/**
	 * updateAirwayTenantメソッド
	 * <p>
	 * バリデーションoperatorIdの@NotBlankの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateAirwayTenant_error5() throws Exception {

		// 入力データの準備
		RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("0")
				.airwayId("airway_id_0000000000000000000001").relatedPartiesIdList(Arrays.asList("")).build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest()).andExpect(content().string(containsString("事業者IDは必須項目です。")));

	}

	/**
	 * updateAirwayTenantメソッド
	 * <p>
	 * バリデーションoperatorIdの@Sizeの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateAirwayTenant_error6() throws Exception {

		// 入力データの準備
		RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("0")
				.airwayId("airway_id_0000000000000000000001")
				.relatedPartiesIdList(Arrays.asList("000000000000000000000000000000000000-1")).build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("事業者IDの型が正しくありません。36桁の整数を設定してください。")));

	}

	/**
	 * updateAirwayTenantメソッドのテスト
	 * <p>
	 * 異常系:(事業者情報存在チェック)指定された事業者情報の取得結果が0件<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateAirwayTenant_error7() throws Exception {

		RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("0")
				.airwayId("airway_id_0000000000000000000002")
				.relatedPartiesIdList(
						Arrays.asList("0000000000000000000000000000000000-8", "0000000000000000000000000000000000-3"))
				.build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isNotFound()).andExpect(
						content().string(containsString("指定された事業者ID(0000000000000000000000000000000000-8)は存在しません。")));

	}

	/**
	 * updateAirwayTenantメソッドのテスト
	 * <p>
	 * 異常系:(紐づき情報存在チェック)航路事業者情報テーブルにデータが存在しない<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateAirwayTenant_error8() throws Exception {

		RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("0")
				.airwayId("airway_id_0000000000000000000008")
				.relatedPartiesIdList(Arrays.asList("0000000000000000000000000000000000-3")).build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isNotFound())
				.andExpect(content().string(containsString("指定された航路ID(airway_id_0000000000000000000008)は存在しません。")));

	}

	/**
	 * updateAirwayTenantメソッドのテスト
	 * <p>
	 * 異常系:(重複チェック)航路事業者情報テーブルにデータが存在する<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateAirwayTenant_error9() throws Exception {

		RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("1")
				.airwayId("airway_id_0000000000000000000002")
				.relatedPartiesIdList(Arrays.asList("0000000000000000000000000000000000-3")).build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest()).andExpect(content().string(containsString(
						"事業者ID(0000000000000000000000000000000000-3)はすでに航路ID(airway_id_0000000000000000000002)に紐づいています。")));

	}

	/**
	 * updateAirwayTenantメソッドのテスト
	 * <p>
	 * 異常系:サーバーエラー<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateAirwayTenant_error10() throws Exception {

		// テーブル削除を実行
		jdbcTemplate.execute("DROP TABLE IF EXISTS AIRWAY_TENANT_ASSOCIATION");

		// 更新データの準備
		RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("0")
				.airwayId("airway_id_0000000000000000000002")
				.relatedPartiesIdList(Arrays.asList("0000000000000000000000000000000000-1")).build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isInternalServerError());

	}

	/**
	 * updateAirwayTenantメソッドのテスト
	 * <p>
	 * 正常系:航路事業者情報を更新(上書き)検証<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateAirwayTenant_normal1() throws Exception {

		// 更新データの準備
		RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("0")
				.airwayId("airway_id_0000000000000000000002")
				.relatedPartiesIdList(
						Arrays.asList("0000000000000000000000000000000000-1", "0000000000000000000000000000000000-2"))
				.build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk()).andExpect(content().string("")).andReturn();

		// データベースに更新されたデータを確認
		List<AirwayTenantAssociationEntity> foundAirwayIdAndOperatorIds = airwayTenantAssociationMapper
				.foundAirwayIdAndOperatorIds(entityInfo.getAirwayId(),
						entityInfo.getRelatedPartiesIdList());
		for (int i = 0; i < foundAirwayIdAndOperatorIds.size(); i++) {
			assertTrue(foundAirwayIdAndOperatorIds.get(i).getAirwayId()
					.contains("airway_id_0000000000000000000002"));
			assertTrue(foundAirwayIdAndOperatorIds.get(i).getOperatorId()
					.contains(entityInfo.getRelatedPartiesIdList().get(i)));
		}

	}

	/**
	 * updateAirwayTenantメソッドのテスト
	 * <p>
	 * 正常系:航路事業者情報を更新(追加)検証<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateAirwayTenant_normal2() throws Exception {

		// 更新データの準備
		RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("0")
				.airwayId("airway_id_0000000000000000000002")
				.relatedPartiesIdList(Arrays.asList("0000000000000000000000000000000000-1")).build();

		String jsonString = objectMapper.writeValueAsString(entityInfo);

		mockMvc.perform(
				put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk()).andExpect(content().string("")).andReturn();

		// データベースに更新されたデータを確認
		List<AirwayTenantAssociationEntity> foundAirwayIdAndOperatorIds = airwayTenantAssociationMapper
				.foundAirwayIdAndOperatorIds(entityInfo.getAirwayId(),
						entityInfo.getRelatedPartiesIdList());
		for (int i = 0; i < foundAirwayIdAndOperatorIds.size(); i++) {
			assertTrue(
					foundAirwayIdAndOperatorIds.get(i).getAirwayId().contains(entityInfo.getAirwayId()));
			assertTrue(foundAirwayIdAndOperatorIds.get(i).getOperatorId()
					.contains(entityInfo.getRelatedPartiesIdList().get(i)));
		}

	}

	/**
	 * deleteAirwayTenantメソッド
	 * <p>
	 * バリデーションairwayIdの@NotEmptyの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testDeleteAirwayTenant_error1() throws Exception {

		mockMvc.perform(delete("/api/v1/airwayTenantLink?airwayId=")).andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("航路IDは必須項目です。")));

	}

	/**
	 * deleteAirwayTenantメソッドのテスト
	 * <p>
	 * 異常系:航路事業者情報テーブルに情報存在しない場合<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testDeleteAirwayTenant_error2() throws Exception {

		// 入力データの準備
		String airwayId = "airway_id_0000000000000000000005";

		mockMvc.perform(delete("/api/v1/airwayTenantLink?airwayId={airwayId}", airwayId))
				.andExpect(status().isBadRequest()).andExpect(content().string(containsString("指定された航路ID(airway_id_0000000000000000000005)は存在しません。")));

	}

	/**
	 * deleteAirwayTenantメソッドのテスト
	 * <p>
	 * 異常系:サーバーエラー<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testDeleteAirwayTenant_error3() throws Exception {

		// テーブル削除を実行
		jdbcTemplate.execute("DROP TABLE IF EXISTS AIRWAY_TENANT_ASSOCIATION");

		// 入力データの準備
		String airwayId = "airway_id_0000000000000000000002";

		mockMvc.perform(delete("/api/v1/airwayTenantLink?airwayId={airwayId}", airwayId))
				.andExpect(status().isInternalServerError());

	}

	/**
	 * deleteAirwayTenantメソッドのテスト
	 * <p>
	 * 正常系:航路事業者情報を削除検証<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testDeleteAirwayTenant_normal() throws Exception {

		// 入力データの準備
		String airwayId = "airway_id_0000000000000000000002";

		mockMvc.perform(delete("/api/v1/airwayTenantLink?airwayId={airwayId}", airwayId))
				.andExpect(status().isOk()).andExpect(content().string("")).andReturn();

		// データベースに削除されたデータを確認
		int count = airwayTenantAssociationMapper.countByAirwayId(airwayId);
		assertEquals(0, count);

	}
}
