package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import org.junit.jupiter.api.Order;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.Airway;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwayInfo;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwayReserveInfo;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwaySection;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.ReserveSection;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayMapper;

/**
 * メール送信サービステスト
 */
@ExtendWith({ MockitoExtension.class, SpringExtension.class })
@SpringBootTest
public class CheckAndChangeServiceTest {

    @Autowired
    MailSendService mailSendService;

    @Autowired
    private CheckAndChangeService checkAndChangeService;

	@Autowired
	private AirwayMapper airwayMapper;
	
    @Value("${systemuser}")
    private int userId;

    
	/**
	 *  CheckAndChangeServiceのconvertTimestamp方法のテスト
	 */  
    @Test  
	@Order(1)
    public void convertTimestampTest() {
    	String convertTimestamp =  "2024/01/24 18:30:30";
    	DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    	LocalDateTime expectedConvertTimestamp = LocalDateTime.parse(convertTimestamp, inputFormat);
    	
    	LocalDateTime convertedTimestamp = checkAndChangeService.convertTimestamp(convertTimestamp, "reservedAt");
    	
    	assertEquals(expectedConvertTimestamp, convertedTimestamp);
    }

	/**
	 *  CheckAndChangeServiceのnotNullCheckAirwayInfo方法のテスト
	 *  AirwayInfoをチェックして、Trueを返却
	 */  
    @Test  
	@Order(2)
    public void notNullCheckAirwayInfoTest1() {
		AirwayInfo airwayInfo = new AirwayInfo();
		airwayInfo.setAirwayAdministratorId("TestAdministratorId");
		airwayInfo.setRegisteredAt("2025-01-23T12:56:00Z");
		airwayInfo.setUpdatedAt("2025-01-23T12:56:00Z");
		Airway airway = new Airway();
		airway.setAirwayId("TestAirwayId");
		airway.setAirwayName("TestAirwayName");
		airway.setFlightPurpose("FlightPurposeTEST");
		airway.setCreatedAt("2025-01-23T12:56:00Z");
		airway.setUpdatedAt("2025-01-23T12:56:00Z");
		AirwaySection AirwaySectionTest1 = AirwaySection.builder().airwaySectionId("airwaySectionId4").airwaySectionName("airwaySectionName7").build();
		AirwaySection AirwaySectionTest2 = AirwaySection.builder().airwaySectionId("airwaySectionId5").airwaySectionName("airwaySectionName8").build();
		AirwaySection AirwaySectionTest3 = AirwaySection.builder().airwaySectionId("airwaySectionId6").airwaySectionName("airwaySectionName9").build();
		List<AirwaySection> airwaySections = new ArrayList<>();
		airwaySections.add(AirwaySectionTest1);
		airwaySections.add(AirwaySectionTest2);
		airwaySections.add(AirwaySectionTest3);
		airway.setAirwaySections(airwaySections);
		airwayInfo.setAirway(airway);
		assertTrue(checkAndChangeService.notNullCheckAirwayInfo(airwayInfo));
    }
    
	/**
	 *  CheckAndChangeServiceのnotNullCheckAirwayInfo方法のテスト
	 *  Airwayが存在しない場合、Trueを返却
	 */  
    @Test  
	@Order(3)
    public void notNullCheckAirwayInfoTest2() {
		AirwayInfo airwayInfo = new AirwayInfo();
		airwayInfo.setAirwayAdministratorId("TestAdministratorId");
		airwayInfo.setRegisteredAt("2025-01-23T12:56:00Z");
		airwayInfo.setUpdatedAt("2025-01-23T12:56:00Z");
		assertTrue(checkAndChangeService.notNullCheckAirwayInfo(airwayInfo));
    }
    
    
	/**
	 *  CheckAndChangeServiceのnotNullCheckAirwayInfo方法のテスト
	 *  airwayのAirwayIdがない場合、Falseを返却
	 */  
    @Test 
	@Order(4)
    public void notNullCheckAirwayInfoTest3() {
		AirwayInfo airwayInfo = new AirwayInfo();
		airwayInfo.setAirwayAdministratorId("TestAdministratorId");
		airwayInfo.setRegisteredAt("2025-01-23T12:56:00Z");
		airwayInfo.setUpdatedAt("2025-01-23T12:56:00Z");
		Airway airway = new Airway();
		airway.setAirwayName("TestAirwayName");
		airway.setFlightPurpose("FlightPurposeTEST");
		airway.setCreatedAt("2025-01-23T12:56:00Z");
		airway.setUpdatedAt("2025-01-23T12:56:00Z");
		AirwaySection AirwaySectionTest1 = AirwaySection.builder().airwaySectionId("airwaySectionId4").airwaySectionName("airwaySectionName7").build();
		AirwaySection AirwaySectionTest2 = AirwaySection.builder().airwaySectionId("airwaySectionId5").airwaySectionName("airwaySectionName8").build();
		AirwaySection AirwaySectionTest3 = AirwaySection.builder().airwaySectionId("airwaySectionId6").airwaySectionName("airwaySectionName9").build();
		List<AirwaySection> airwaySections = new ArrayList<>();
		airwaySections.add(AirwaySectionTest1);
		airwaySections.add(AirwaySectionTest2);
		airwaySections.add(AirwaySectionTest3);
		airway.setAirwaySections(airwaySections);
		airwayInfo.setAirway(airway);
		assertTrue(!checkAndChangeService.notNullCheckAirwayInfo(airwayInfo));	
    }
    

	/**
	 *  CheckAndChangeServiceのnotNullCheckAirwayInfo方法のテスト
	 *  airwayのAirwayNameがない場合、Falseを返却
	 */  
    @Test  
	@Order(5)
    public void notNullCheckAirwayInfoTest4() {
		AirwayInfo airwayInfo = new AirwayInfo();
		airwayInfo.setAirwayAdministratorId("TestAdministratorId");
		airwayInfo.setRegisteredAt("2025-01-23T12:56:00Z");
		airwayInfo.setUpdatedAt("2025-01-23T12:56:00Z");
		Airway airway = new Airway();
		airway.setAirwayId("TestAirwayId");
		airway.setFlightPurpose("FlightPurposeTEST");
		airway.setCreatedAt("2025-01-23T12:56:00Z");
		airway.setUpdatedAt("2025-01-23T12:56:00Z");
		AirwaySection AirwaySectionTest1 = AirwaySection.builder().airwaySectionId("airwaySectionId4").airwaySectionName("airwaySectionName7").build();
		AirwaySection AirwaySectionTest2 = AirwaySection.builder().airwaySectionId("airwaySectionId5").airwaySectionName("airwaySectionName8").build();
		AirwaySection AirwaySectionTest3 = AirwaySection.builder().airwaySectionId("airwaySectionId6").airwaySectionName("airwaySectionName9").build();
		List<AirwaySection> airwaySections = new ArrayList<>();
		airwaySections.add(AirwaySectionTest1);
		airwaySections.add(AirwaySectionTest2);
		airwaySections.add(AirwaySectionTest3);
		airway.setAirwaySections(airwaySections);
		airwayInfo.setAirway(airway);
		assertTrue(!checkAndChangeService.notNullCheckAirwayInfo(airwayInfo));	
    }
    

	/**
	 *  CheckAndChangeServiceのnotNullCheckAirwayInfo方法のテスト
	 *  airwayのFlightPurposeがない場合、Falseを返却
	 */  
    @Test 
	@Order(13)
    public void notNullCheckAirwayInfoTest5() {
		AirwayInfo airwayInfo = new AirwayInfo();
		airwayInfo.setAirwayAdministratorId("TestAdministratorId");
		airwayInfo.setRegisteredAt("2025-01-23T12:56:00Z");
		airwayInfo.setUpdatedAt("2025-01-23T12:56:00Z");
		Airway airway = new Airway();
		airway.setAirwayId("TestAirwayId");
		airway.setAirwayName("TestAirwayName");
		airway.setCreatedAt("2025-01-23T12:56:00Z");
		airway.setUpdatedAt("2025-01-23T12:56:00Z");
		AirwaySection AirwaySectionTest1 = AirwaySection.builder().airwaySectionId("airwaySectionId4").airwaySectionName("airwaySectionName7").build();
		AirwaySection AirwaySectionTest2 = AirwaySection.builder().airwaySectionId("airwaySectionId5").airwaySectionName("airwaySectionName8").build();
		AirwaySection AirwaySectionTest3 = AirwaySection.builder().airwaySectionId("airwaySectionId6").airwaySectionName("airwaySectionName9").build();
		List<AirwaySection> airwaySections = new ArrayList<>();
		airwaySections.add(AirwaySectionTest1);
		airwaySections.add(AirwaySectionTest2);
		airwaySections.add(AirwaySectionTest3);
		airway.setAirwaySections(airwaySections);
		airwayInfo.setAirway(airway);
		assertTrue(!checkAndChangeService.notNullCheckAirwayInfo(airwayInfo));
    }

    

	/**
	 *  CheckAndChangeServiceのnotNullCheckAirwayInfo方法のテスト
	 *  airwayのCreateAtがない場合、Falseを返却
	 */  
    @Test 
	@Order(14)
    public void notNullCheckAirwayInfoTest6() {
		AirwayInfo airwayInfo = new AirwayInfo();
		airwayInfo.setAirwayAdministratorId("TestAdministratorId");
		airwayInfo.setRegisteredAt("2025-01-23T12:56:00Z");
		airwayInfo.setUpdatedAt("2025-01-23T12:56:00Z");
		Airway airway = new Airway();
		airway.setAirwayId("TestAirwayId");
		airway.setAirwayName("TestAirwayName");
		airway.setFlightPurpose("FlightPurposeTest");
		airway.setUpdatedAt("2025-01-23T12:56:00Z");
		AirwaySection AirwaySectionTest1 = AirwaySection.builder().airwaySectionId("airwaySectionId4").airwaySectionName("airwaySectionName7").build();
		AirwaySection AirwaySectionTest2 = AirwaySection.builder().airwaySectionId("airwaySectionId5").airwaySectionName("airwaySectionName8").build();
		AirwaySection AirwaySectionTest3 = AirwaySection.builder().airwaySectionId("airwaySectionId6").airwaySectionName("airwaySectionName9").build();
		List<AirwaySection> airwaySections = new ArrayList<>();
		airwaySections.add(AirwaySectionTest1);
		airwaySections.add(AirwaySectionTest2);
		airwaySections.add(AirwaySectionTest3);
		airway.setAirwaySections(airwaySections);
		airwayInfo.setAirway(airway);
		assertTrue(!checkAndChangeService.notNullCheckAirwayInfo(airwayInfo));
    }
    

	/**
	 *  CheckAndChangeServiceのnotNullCheckAirwayInfo方法のテスト
	 *  airwayのUpdatedAtがない場合、Falseを返却
	 */  
    @Test 
	@Order(6)
    public void notNullCheckAirwayInfoTest7() {
		AirwayInfo airwayInfo = new AirwayInfo();
		airwayInfo.setAirwayAdministratorId("TestAdministratorId");
		airwayInfo.setRegisteredAt("2025-01-23T12:56:00Z");
		airwayInfo.setUpdatedAt("2025-01-23T12:56:00Z");
		Airway airway = new Airway();
		airway.setAirwayId("TestAirwayId");
		airway.setAirwayName("TestAirwayName");
		airway.setFlightPurpose("2025-01-23T12:56:00Z");
		airway.setCreatedAt("2025-01-23T12:56:00Z");
		AirwaySection AirwaySectionTest1 = AirwaySection.builder().airwaySectionId("airwaySectionId4").airwaySectionName("airwaySectionName7").build();
		AirwaySection AirwaySectionTest2 = AirwaySection.builder().airwaySectionId("airwaySectionId5").airwaySectionName("airwaySectionName8").build();
		AirwaySection AirwaySectionTest3 = AirwaySection.builder().airwaySectionId("airwaySectionId6").airwaySectionName("airwaySectionName9").build();
		List<AirwaySection> airwaySections = new ArrayList<>();
		airwaySections.add(AirwaySectionTest1);
		airwaySections.add(AirwaySectionTest2);
		airwaySections.add(AirwaySectionTest3);
		airway.setAirwaySections(airwaySections);
		airwayInfo.setAirway(airway);
		assertTrue(!checkAndChangeService.notNullCheckAirwayInfo(airwayInfo));
    }
    
    
	/**
	 *  CheckAndChangeServiceのnotNullCheckAirwayInfo方法のテスト
	 *  airwayのAirwaySectionsがない場合、Falseを返却
	 */  
    @Test  
	@Order(7)
    public void notNullCheckAirwayInfoTest8() {
		AirwayInfo airwayInfo = new AirwayInfo();
		airwayInfo.setAirwayAdministratorId("TestAdministratorId");
		airwayInfo.setRegisteredAt("2025-01-23T12:56:00Z");
		airwayInfo.setUpdatedAt("2025-01-23T12:56:00Z");
		Airway airway = new Airway();
		airway.setAirwayId("TestAirwayId");
		airway.setAirwayName("TestAirwayName");
		airway.setFlightPurpose("FlightPurposeTest");
		airway.setCreatedAt("2025-01-23T12:56:00Z");
		airway.setUpdatedAt("2025-01-23T12:56:00Z");
		airwayInfo.setAirway(airway);
		assertTrue(!checkAndChangeService.notNullCheckAirwayInfo(airwayInfo));
    }

	/**
	 *  CheckAndChangeServiceのnotNullCheckAirwayInfo方法のテスト
	 *  AirwaySectionのSectionIdとか、SectionNameが設定しない場合、Falseを返却
	 */  
    @Test  
	@Order(8)
    public void notNullCheckAirwayInfoTest9() {
		AirwayInfo airwayInfo = new AirwayInfo();
		airwayInfo.setAirwayAdministratorId("TestAdministratorId");
		airwayInfo.setRegisteredAt("2025-01-23T12:56:00Z");
		airwayInfo.setUpdatedAt("2025-01-23T12:56:00Z");
		Airway airway = new Airway();
		airway.setAirwayId("TestAirwayId");
		airway.setAirwayName("TestAirwayName");
		airway.setFlightPurpose("FlightPurposeTest");
		airway.setCreatedAt("2025-01-23T12:56:00Z");
		airway.setUpdatedAt("2025-01-23T12:56:00Z");
		AirwaySection AirwaySectionTest1 = AirwaySection.builder().airwaySectionId("airwaySectionId4").build();
		AirwaySection AirwaySectionTest2 = AirwaySection.builder().airwaySectionId("airwaySectionId5").airwaySectionName("airwaySectionName8").build();
		AirwaySection AirwaySectionTest3 = AirwaySection.builder().airwaySectionName("airwaySectionName9").build();
		List<AirwaySection> airwaySections = new ArrayList<>();
		airwaySections.add(AirwaySectionTest1);
		airwaySections.add(AirwaySectionTest2);
		airwaySections.add(AirwaySectionTest3);
		airway.setAirwaySections(airwaySections);
		airwayInfo.setAirway(airway);
		assertTrue(!checkAndChangeService.notNullCheckAirwayInfo(airwayInfo));
    }
    
    

	/**
	 *  CheckAndChangeServiceのnotNullCheckAirwayReserveInfo方法のテスト
	 *  MQTTからメッセージのStatusが"RESERVED"場合、"1"に変更
	 */  
    @Test  
	@Order(9)
    public void notNullCheckAirwayReserveInfoTest1() {
		AirwayReserveInfo airwayReserveInfo = new AirwayReserveInfo();
		ReserveSection ReserveSection1 = ReserveSection.builder().airwaySectionId("airwaySectionId4").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		ReserveSection ReserveSection2 = ReserveSection.builder().airwaySectionId("airwaySectionId5").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		ReserveSection ReserveSection3 = ReserveSection.builder().airwaySectionId("airwaySectionId6").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		List<ReserveSection> reserveSections = new ArrayList<>();
		reserveSections.add(ReserveSection1);
		reserveSections.add(ReserveSection2);
		reserveSections.add(ReserveSection3);
		airwayReserveInfo.setAirwayReservationId("2024123120241231202412311123");
		airwayReserveInfo.setOperatorId("2024123120241231202412311117name");
		airwayReserveInfo.setEventId("2024123120241231202412311117AdministratorId");
		airwayReserveInfo.setStatus("RESERVED");
		//airwayReserveInfo.setReserveSections(reserveSections);
		airwayReserveInfo.setReservedAt("2026-01-18T18:18:18.123");
		airwayReserveInfo.setUpdatedAt("2026-01-18T18:18:18.123");

		checkAndChangeService.notNullCheckAirwayReserveInfo(airwayReserveInfo);

		String expectedStatus = "1";
		assertEquals(expectedStatus, airwayReserveInfo.getStatus());	
    }
    

	/**
	 *  CheckAndChangeServiceのnotNullCheckAirwayReserveInfo方法のテスト
	 *  MQTTからメッセージのStatusが"CANCELED"場合、"2"に変更
	 */  
    @Test 
	@Order(10)
    public void notNullCheckAirwayReserveInfoTest2() {
		AirwayReserveInfo airwayReserveInfo = new AirwayReserveInfo();
		ReserveSection ReserveSection1 = ReserveSection.builder().airwaySectionId("airwaySectionId4").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		ReserveSection ReserveSection2 = ReserveSection.builder().airwaySectionId("airwaySectionId5").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		ReserveSection ReserveSection3 = ReserveSection.builder().airwaySectionId("airwaySectionId6").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		List<ReserveSection> reserveSections = new ArrayList<>();
		reserveSections.add(ReserveSection1);
		reserveSections.add(ReserveSection2);
		reserveSections.add(ReserveSection3);
		airwayReserveInfo.setAirwayReservationId("2024123120241231202412311123");
		airwayReserveInfo.setOperatorId("2024123120241231202412311117name");
		airwayReserveInfo.setEventId("2024123120241231202412311117AdministratorId");
		airwayReserveInfo.setStatus("CANCELED");
		//airwayReserveInfo.setReserveSections(reserveSections);
		airwayReserveInfo.setReservedAt("2026-01-18T18:18:18.123");
		airwayReserveInfo.setUpdatedAt("2026-01-18T18:18:18.123");

		checkAndChangeService.notNullCheckAirwayReserveInfo(airwayReserveInfo);

		String expectedStatus = "2";
		assertEquals(expectedStatus, airwayReserveInfo.getStatus());	
    }

	/**
	 *  CheckAndChangeServiceのnotNullCheckAirwayReserveInfo方法のテスト
	 *  MQTTからメッセージのStatusが"RESCINDED"場合、"3"に変更
	 */  
    @Test  
	@Order(11)
    public void notNullCheckAirwayReserveInfoTest3() {
		AirwayReserveInfo airwayReserveInfo = new AirwayReserveInfo();
		ReserveSection ReserveSection1 = ReserveSection.builder().airwaySectionId("airwaySectionId4").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		ReserveSection ReserveSection2 = ReserveSection.builder().airwaySectionId("airwaySectionId5").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		ReserveSection ReserveSection3 = ReserveSection.builder().airwaySectionId("airwaySectionId6").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		List<ReserveSection> reserveSections = new ArrayList<>();
		reserveSections.add(ReserveSection1);
		reserveSections.add(ReserveSection2);
		reserveSections.add(ReserveSection3);
		airwayReserveInfo.setAirwayReservationId("2024123120241231202412311123");
		airwayReserveInfo.setOperatorId("2024123120241231202412311117name");
		airwayReserveInfo.setEventId("2024123120241231202412311117AdministratorId");
		airwayReserveInfo.setStatus("RESCINDED");
		//airwayReserveInfo.setReserveSections(reserveSections);
		airwayReserveInfo.setReservedAt("2026-01-18T18:18:18.123");
		airwayReserveInfo.setUpdatedAt("2026-01-18T18:18:18.123");

		checkAndChangeService.notNullCheckAirwayReserveInfo(airwayReserveInfo);

		String expectedStatus = "3";
		assertEquals(expectedStatus, airwayReserveInfo.getStatus());
    }

	/**
	 *  CheckAndChangeServiceのnotNullCheckAirwayReserveInfo方法のテスト
	 *  必須項目がNULL場合、ログで(Object)の値が正しくありません。を出力
	 */  
    @Test  
	@Order(12)
    public void notNullCheckAirwayReserveInfoTest4() {
		AirwayReserveInfo airwayReserveInfo = new AirwayReserveInfo();
		ReserveSection ReserveSection1 = ReserveSection.builder().airwaySectionId("").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		ReserveSection ReserveSection2 = ReserveSection.builder().airwaySectionId("").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		ReserveSection ReserveSection3 = ReserveSection.builder().airwaySectionId("").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		List<ReserveSection> reserveSections = new ArrayList<>();
		reserveSections.add(ReserveSection1);
		reserveSections.add(ReserveSection2);
		reserveSections.add(ReserveSection3);
		airwayReserveInfo.setAirwayReservationId("");
		airwayReserveInfo.setOperatorId("");
		airwayReserveInfo.setEventId("");
		airwayReserveInfo.setStatus("RESCINDED");
		//airwayReserveInfo.setReserveSections(reserveSections);
		airwayReserveInfo.setReservedAt("");
		airwayReserveInfo.setUpdatedAt("");

		checkAndChangeService.notNullCheckAirwayReserveInfo(airwayReserveInfo);

		String expectedStatus = "3";
		assertEquals(expectedStatus, airwayReserveInfo.getStatus());
    }
}
