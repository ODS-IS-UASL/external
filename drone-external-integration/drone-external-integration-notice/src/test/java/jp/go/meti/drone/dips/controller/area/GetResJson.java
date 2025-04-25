package jp.go.meti.drone.dips.controller.area;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Repository;

/**
 * 
 */
@Repository
public class GetResJson {

	private static String readerMethod(File file) {

		FileReader fileReader;
		Reader reader;
		int ch = 0;
		StringBuffer sb = null;
		String jsonStr = null;
		try {
			fileReader = new FileReader(file);
			reader = new InputStreamReader(new FileInputStream(file), "utf-8");
			sb = new StringBuffer();
			while ((ch = reader.read()) != -1) {
				sb.append((char) ch);

			}
			fileReader.close();
			reader.close();
			jsonStr = sb.toString();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonStr;
	}

	/**
	 * 結果json取得
	 * 
	 * @return
	 */
	public static String getJson(String flg) {
		String res = "";

		switch (flg) {
		case "1": {
			res = "api004ResponseOne.json";
			break;
		}
		case "oneExpect": {
			res = "api004ResponseOneExpect.json";
			break;
		}
		case "all": {
			res = "api004ResponseAll.json";
			break;
		}
		case "allExpect": {
			res = "api004ResponseAllExpect.json";
			break;
		}
		case "zero": {
			res = "api004ResponseZero.json";
			break;
		}
		case "zeroExpect": {
			res = "api004ResponseZeroExpect.json";
			break;
		}
		case "No22": {
			res = "api004ResponseAllNo22.json";
			break;
		}
		case "No22Expect": {
			res = "api004ResponseAllNo22Expect.json";
			break;
		}

		case "No30": {
			res = "api004ResponseOneNo30.json";
			break;
		}
		case "No32": {
			res = "api004ResponseOneNo32.json";
			break;
		}
		case "No33": {
			res = "api004ResponseOneNo33.json";
			break;
		}
		case "No34": {
			res = "api004ResponseOneNo34.json";
			break;
		}
		case "No35": {
			res = "api004ResponseOneNo35.json";
			break;
		}
		case "No36": {
			res = "api004ResponseOneNo36.json";
			break;
		}
		case "No37": {
			res = "api004ResponseOneNo37.json";
			break;
		}
		case "No38": {
			res = "api004ResponseOneNo38.json";
			break;
		}
		case "No40": {
			res = "api004ResponseOneNo40.json";
			break;
		}
		case "No41": {
			res = "api004ResponseOneNo41.json";
			break;
		}
		case "No42": {
			res = "api004ResponseOneNo42.json";
			break;
		}
		case "No44": {
			res = "api004ResponseOneNo44.json";
			break;
		}
		case "No45": {
			res = "api004ResponseOneCircleNo45.json";
			break;
		}
		case "No46": {
			res = "api004ResponseOneCircleNo46.json";
			break;
		}
		case "No47": {
			res = "api004ResponseOneCircle.json";
			break;
		}
		case "No47Expect": {
			res = "api004ResponseOneCircleExpect.json";
			break;
		}
		case "No48": {
			res = "api004ResponseOneCircleNo48.json";
			break;
		}
		case "No49": {
			res = "api004ResponseOneCircleNo49.json";
			break;
		}
		case "No50": {
			res = "api004ResponseOneCircleNo50.json";
			break;
		}
		case "No52": {
			res = "api004ResponseOneCircleNo52.json";
			break;
		}
		case "No55": {
			res = "api004ResponseAllNo55.json";
			break;
		}
		case "No55Expect": {
			res = "api004ResponseAllNo55Expect.json";
			break;
		}
		case "No57": {
			res = "api004ResponseAllNo57.json";
			break;
		}
		case "No58": {
			res = "api004ResponseAllNo58.json";
			break;
		}
		case "No60": {
			res = "api004ResponseAllNo60.json";
			break;
		}
		case "No61": {
			res = "api004ResponseAllNo61.json";
			break;
		}
		case "No61Expect": {
			res = "api004ResponseAllNo61Expect.json";
			break;
		}

		}

		File file = new File(res);
		res = readerMethod(file);

		return res;

	}

	/**
	 * 結果json取得
	 * 
	 * @return
	 */
	public static String getTenantJson(String flg) {
		String res = "";

		switch (flg) {
		case "all":
			res = "tenantResponseAll.json";
			break;

		}

		File file = new File(res);
		res = readerMethod(file);

		return res;

	}

}
