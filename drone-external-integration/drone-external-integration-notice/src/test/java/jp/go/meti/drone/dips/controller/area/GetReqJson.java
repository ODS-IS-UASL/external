package jp.go.meti.drone.dips.controller.area;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.go.meti.drone.dips.model.flightprohibited.FlightProhibitedAreaInfoRequest;

public class GetReqJson {

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
     * @return
     */
    public static FlightProhibitedAreaInfoRequest getJson() {

        String res = "api004Request.json";

        File file = new File(res);
        FlightProhibitedAreaInfoRequest getDipsResult = new FlightProhibitedAreaInfoRequest();

        try {
            ObjectMapper mapper = new ObjectMapper();
            getDipsResult = mapper.readValue(readerMethod(file), FlightProhibitedAreaInfoRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return getDipsResult;

    }

}
