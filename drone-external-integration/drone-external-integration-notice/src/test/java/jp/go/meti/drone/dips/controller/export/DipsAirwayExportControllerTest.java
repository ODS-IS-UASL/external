package jp.go.meti.drone.dips.controller.export;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import jp.go.meti.drone.dips.model.export.RequestAirwayIdList;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ComponentScan(basePackages = "jp.go.meti.drone")
@ContextConfiguration(classes = { DipsAirwayExportController.class })
class DipsAirwayExportControllerTest {

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * dipsAirwayExportメソッド
	 * <p>
	 * バリデーションairwayIdListの@NotNullの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testDipsAirwayExport_error1() throws Exception {

		// 入力データの準備
		RequestAirwayIdList requestAirwayIdList = new RequestAirwayIdList();

		String jsonString = objectMapper.writeValueAsString(requestAirwayIdList);

		mockMvc.perform(post("/api/v1/dipsAirwayExport").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest());

	}

	/**
	 * dipsAirwayExportメソッド
	 * <p>
	 * バリデーションairwayIdの@NotBlankの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testDipsAirwayExport_error2() throws Exception {

		// 入力データの準備
		RequestAirwayIdList requestAirwayIdList = new RequestAirwayIdList();
		requestAirwayIdList.setAirwayIdList(Arrays.asList());

		String jsonString = objectMapper.writeValueAsString(requestAirwayIdList);

		mockMvc.perform(post("/api/v1/dipsAirwayExport").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest());

	}

	/**
	 * dipsAirwayExportメソッド
	 * <p>
	 * バリデーションairwayIdの@Sizeの確認用<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testDipsAirwayExport_error3() throws Exception {

		// 入力データの準備
		RequestAirwayIdList requestAirwayIdList = new RequestAirwayIdList();
		requestAirwayIdList.setAirwayIdList(Arrays.asList("000000000000000000000000000000000000000000-1"));

		String jsonString = objectMapper.writeValueAsString(requestAirwayIdList);

		mockMvc.perform(post("/api/v1/dipsAirwayExport").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isBadRequest());

	}

	/**
	 * dipsAirwayExportメソッドのテスト
	 * <p>
	 * 異常系:航路情報がない場合<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testDipsAirwayExport_error4() throws Exception {

		// 入力データの準備
		RequestAirwayIdList requestAirwayIdList = new RequestAirwayIdList();
		requestAirwayIdList.setAirwayIdList(Arrays.asList("0000000000000000000000000000000000-5"));

		String jsonString = objectMapper.writeValueAsString(requestAirwayIdList);

		mockMvc.perform(post("/api/v1/dipsAirwayExport").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isNotFound());
	}

	/**
	 * dipsAirwayExportメソッドのテスト
	 * <p>
	 * 異常系:(zipストリームのチェック)の場合<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testDipsAirwayExport_error5() throws Exception {

		// 入力データの準備
		RequestAirwayIdList requestAirwayIdList = new RequestAirwayIdList();
		requestAirwayIdList.setAirwayIdList(Arrays.asList("0000000000000000000000000000000000-1"));

		String jsonString = objectMapper.writeValueAsString(requestAirwayIdList);

		mockMvc.perform(post("/api/v1/dipsAirwayExport").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isNotFound());
	}

	/**
	 * dipsAirwayExportメソッドのテスト
	 * <p>
	 * 正常系:件航路情報をダウンロード検証<br>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	void testDipsAirwayExport_normal() throws Exception {

		// 入力データの準備
		RequestAirwayIdList requestAirwayIdList = new RequestAirwayIdList();
		requestAirwayIdList.setAirwayIdList(
				Arrays.asList("0000000000000000000000000000000000-1", "0000000000000000000000000000000000-2"));

		String jsonString = objectMapper.writeValueAsString(requestAirwayIdList);

		mockMvc.perform(post("/api/v1/dipsAirwayExport").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM)).andExpect(result -> {
					// 実際のレスポンスボディ（ZIPファイル）が期待通りかチェック
					Resource resource = new ByteArrayResource(result.getResponse().getContentAsByteArray());

					// ボディの検証
					assertNotNull(resource);

					// ボディの内容（バイナリデータ）の検証
					assertTrue(resource instanceof ByteArrayResource);

					byte[] actualZipData = result.getResponse().getContentAsByteArray();
					assertTrue(actualZipData.length > 0);

				});
	}
}