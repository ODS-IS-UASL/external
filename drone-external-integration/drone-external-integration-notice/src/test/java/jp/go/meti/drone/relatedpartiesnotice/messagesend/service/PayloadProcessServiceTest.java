package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.Airway;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwayInfo;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwaySection;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.Geometry;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwayReserveInfo;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.ReserveSection;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwaySectionEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.MailSentInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.ReservationAirwayAssociationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayReservationMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwaySectionMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.MailSentInfoMapper;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.ReservationAirwayAssociationMapper;


/**
 * 航路情報、航路予約情報と予約情報航路関連情報へ挿入を確認
 */
@ExtendWith({ MockitoExtension.class, SpringExtension.class })
@SpringBootTest
public class PayloadProcessServiceTest {
	
	@Autowired
	AirwayReservationMessageSendService airwayReservationMessageSendService;
	
	@Autowired
	AirwayMessageSendService airwayMessageSendService;
	
	@Autowired
	private AirwayMapper airwayMapper;
	
	@Autowired
	private MailSentInfoMapper mailSentInfoMapper;
	
	@Autowired	
	AirwaySectionMapper airwaySectionMapper;

	@Autowired
	private AirwayService airwayService;
	
	@Autowired
	private CheckAndChangeService checkAndChangeService;	
	
	@Autowired
	private AirwayReservationMapper airwayReservationMapper;

	@Autowired
	private ReservationAirwayAssociationMapper reservationAirwayAssociationMapper;

	@Autowired
	private AirwayReserveService airwayReserveService;

	
	/**
	 * airwayServiceで航路情報と航路区画情報にデータ登録のテスト
	 * 
	 */
	@Test
	public void testInsertAirway1() throws Exception {

		Geometry geometry = new Geometry();
		geometry.setType("Type");

		AirwaySection section1 = new AirwaySection();
		section1.setAirwaySectionId("setAirwaySectionId11101");
		section1.setAirwaySectionName("setAirwaySectionName11101");

        Airway airway = new Airway();
        airway.setAirwayId("airwayid20250123");
        airway.setAirwayName("airwayname20250123");
        airway.setFlightPurpose("FlightPurpose20250123");
        airway.setCreatedAt("2025-01-23T12:56:00Z");
        airway.setUpdatedAt("2025-01-23T12:56:00Z");

        airway.setAirwaySections(Arrays.asList(section1));

        AirwayInfo airwayInfo = new AirwayInfo();
        airwayInfo.setAirwayAdministratorId("AdministratorId20250123");
		airwayInfo.setRegisteredAt("2025-01-23T12:56:00Z");
		airwayInfo.setUpdatedAt("2025-01-23T12:56:00Z");
        airwayInfo.setAirway(airway); 
        
        boolean result = checkAndChangeService.notNullCheckAirwayInfo(airwayInfo);

		airwayService.saveAirway(airwayInfo,"airwayid20250123");
		
		List<AirwayEntity> AirwayEntityList = airwayMapper.getAirwayById("airwayid20250123");
		List<AirwaySectionEntity>  AirwaySectionEntityList1 = airwaySectionMapper.getAirwaySectionById(airwayInfo.getAirway().getAirwayId(),"setAirwaySectionId11101");

		//挿入したデータが存在することを確認
		assertNotNull(AirwayEntityList);
		assertNotNull(AirwaySectionEntityList1);

		assertTrue(result);
	}
	
	
	/**
	 * airwayServiceで航路情報にデータ削除、更新のテスト
	 * 
	 */
	@Test
	public void testInsertAirway2() throws Exception {
		
		Geometry geometry = new Geometry();
		geometry.setType("Type");

		AirwaySection section1 = new AirwaySection();
		section1.setAirwaySectionId("setAirwaySectionId11101更新");
		section1.setAirwaySectionName("setAirwaySectionName11101更新");

        Airway airway = new Airway();
        airway.setAirwayId("airwayid20250123");
        airway.setAirwayName("airwayname20250123更新");
        airway.setFlightPurpose("FlightPurpose20250123");
        airway.setCreatedAt("2025-01-23T12:56:00Z");
        airway.setUpdatedAt("2025-01-23T12:56:00Z");

        airway.setAirwaySections(Arrays.asList(section1));

        AirwayInfo airwayInfo = new AirwayInfo();
        airwayInfo.setAirwayAdministratorId("AdministratorId20250123更新");
		airwayInfo.setRegisteredAt("2025-01-23T12:56:00Z");
		airwayInfo.setUpdatedAt("2025-01-23T12:56:00Z");
        airwayInfo.setAirway(airway); 
        
        boolean result = checkAndChangeService.notNullCheckAirwayInfo(airwayInfo);

		airwayService.saveAirway(airwayInfo,"airwayid20250123");
		
		List<AirwayEntity> AirwayEntityList = airwayMapper.getAirwayById("airwayid20250123");
		List<AirwaySectionEntity>  AirwaySectionEntityList1 = airwaySectionMapper.getAirwaySectionById(airwayInfo.getAirway().getAirwayId(),"setAirwaySectionId11101");
		List<AirwaySectionEntity>  AirwaySectionEntityList2 = airwaySectionMapper.getAirwaySectionById(airwayInfo.getAirway().getAirwayId(),"setAirwaySectionId11101更新");

		//挿入したデータが存在することを確認
		assertNotNull(AirwayEntityList);
		assertNotNull(AirwaySectionEntityList2);
		assertTrue(AirwaySectionEntityList1.isEmpty());

		assertTrue(result);

	}
	
	
	
	/**
	 * airwayServiceで航路情報の項目statusを2に更新
	 * 
	 */
	@Test
	public void testInsertAirway3() throws Exception {

        AirwayInfo airwayInfo = new AirwayInfo();
		airwayInfo.setRegisteredAt("2025-01-23T12:56:00Z");
		airwayInfo.setUpdatedAt("2025-01-23T12:56:00Z");
		airwayInfo.setAirwayAdministratorId("AirwayAdministratorIdTest");

        boolean result = checkAndChangeService.notNullCheckAirwayInfo(airwayInfo);

		airwayService.saveAirway(airwayInfo,"airwayid20250123");
		
		List<AirwayEntity> AirwayEntityList = airwayMapper.getAirwayById("airwayid20250123");

		//挿入したデータが存在することを確認
		assertTrue(AirwayEntityList.getFirst().getStatus().endsWith("2"));

		assertTrue(result);

	}

	/**
	 * AirwayReserveServiceで航路予約情報と予約情報航路関連情報にデータ登録のテスト
	 * 
	 */
	@Test
	public void testInsertAirwayReservation1() throws Exception {
		
		ReserveSection reserveSection1 = new ReserveSection("airwaysectionid202501","2024-11-18T18:18:18Z","2025-11-18T18:18:18Z");
		ReserveSection reserveSection2 = new ReserveSection("airwaysectionid202502","2024-11-18T18:18:18Z","2025-11-18T18:18:18Z");
		ReserveSection reserveSection3 = new ReserveSection("airwaysectionid202503","2024-11-18T18:18:18Z","2025-11-18T18:18:18Z");
		AirwayReserveInfo airwayReserveInfo = new AirwayReserveInfo();
		airwayReserveInfo.setAirwayReservationId("airwayreservationid202501挿入");
		airwayReserveInfo.setOperatorId("operatorid202501");
		airwayReserveInfo.setEventId("eventid202501");
		airwayReserveInfo.setStatus("RESERVED");
		airwayReserveInfo.setAirwaySections(Arrays.asList(reserveSection1,reserveSection2,reserveSection3));
		airwayReserveInfo.setReservedAt("2025-01-05T18:18:18Z");
		airwayReserveInfo.setReservedAt("2025-01-05T18:20:18Z");

		boolean result1 = checkAndChangeService.notNullCheckAirwayReserveInfo(airwayReserveInfo);
		airwayReserveService.saveAirwayReservation(airwayReserveInfo);
				
		AirwayReservationEntity airwayReservationEntityList = airwayReservationMapper.getAirwayReservationById("airwayreservationid202501挿入");
		ReservationAirwayAssociationEntity reservationAirwayAssociation1 = reservationAirwayAssociationMapper.getReservationAirwayAssociationById("airwayreservationid202501挿入", "airwaysectionid202501");
		ReservationAirwayAssociationEntity reservationAirwayAssociation2 = reservationAirwayAssociationMapper.getReservationAirwayAssociationById("airwayreservationid202501挿入", "airwaysectionid202501");
		ReservationAirwayAssociationEntity reservationAirwayAssociation3 = reservationAirwayAssociationMapper.getReservationAirwayAssociationById("airwayreservationid202501挿入", "airwaysectionid202501");
		//挿入したデータが存在することを確認
		assertNotNull(airwayReservationEntityList);
		assertNotNull(reservationAirwayAssociation1);
		assertNotNull(reservationAirwayAssociation2);
		assertNotNull(reservationAirwayAssociation3);
		assertTrue(result1);
	}
	

	/**
	 * AirwayReserveServiceで航路予約情報と予約情報航路関連情報にデータ削除、更新のテスト
	 * 
	 */
	@Test
	public void testInsertAirwayReservation2() throws Exception {
		
		ReserveSection reserveSection1 = new ReserveSection("airwaysectionid202501","2024-11-18T18:18:18Z","2025-11-18T18:18:18Z");
		ReserveSection reserveSection2 = new ReserveSection("airwaysectionid202502","2024-11-18T18:18:18Z","2025-11-18T18:18:18Z");
		ReserveSection reserveSection3 = new ReserveSection("airwaysectionid202503","2024-11-18T18:18:18Z","2025-11-18T18:18:18Z");
		AirwayReserveInfo airwayReserveInfo1 = new AirwayReserveInfo();
		airwayReserveInfo1.setAirwayReservationId("airwayreservationid202506更新");
		airwayReserveInfo1.setOperatorId("operatorid202501");
		airwayReserveInfo1.setEventId("eventid202501");
		airwayReserveInfo1.setStatus("RESERVED");
		airwayReserveInfo1.setAirwaySections(Arrays.asList(reserveSection1,reserveSection2,reserveSection3));
		airwayReserveInfo1.setReservedAt("2025-01-05T18:18:18Z");
		airwayReserveInfo1.setReservedAt("2025-01-05T18:20:18Z");

		ReserveSection reserveSection4 = new ReserveSection("airwaysectionid202504","2024-11-19T18:18:18Z","2025-11-19T18:18:18Z");
		ReserveSection reserveSection5 = new ReserveSection("airwaysectionid202505","2024-11-19T18:18:18Z","2025-11-19T18:18:18Z");
		ReserveSection reserveSection6 = new ReserveSection("airwaysectionid202506","2024-11-19T18:18:18Z","2025-11-19T18:18:18Z");
		AirwayReserveInfo airwayReserveInfo2 = new AirwayReserveInfo();
		airwayReserveInfo2.setAirwayReservationId("airwayreservationid202506更新");
		airwayReserveInfo2.setOperatorId("operatorid202506");
		airwayReserveInfo2.setEventId("eventid202506");
		airwayReserveInfo2.setStatus("RESERVED");
		airwayReserveInfo2.setAirwaySections(Arrays.asList(reserveSection4,reserveSection5,reserveSection6));
		airwayReserveInfo2.setReservedAt("2025-01-06T18:18:18Z");
		airwayReserveInfo2.setReservedAt("2025-01-06T18:20:18Z");
		boolean result1 = checkAndChangeService.notNullCheckAirwayReserveInfo(airwayReserveInfo1);
		boolean result2 = checkAndChangeService.notNullCheckAirwayReserveInfo(airwayReserveInfo2);
		
		airwayReserveService.saveAirwayReservation(airwayReserveInfo1);
		airwayReserveService.saveAirwayReservation(airwayReserveInfo2);
				
		AirwayReservationEntity airwayReservationEntityList = airwayReservationMapper.getAirwayReservationById("airwayreservationid202506更新");
		ReservationAirwayAssociationEntity reservationAirwayAssociation1 = reservationAirwayAssociationMapper.getReservationAirwayAssociationById("airwayreservationid202506更新", "airwaysectionid202501");
		ReservationAirwayAssociationEntity reservationAirwayAssociation2 = reservationAirwayAssociationMapper.getReservationAirwayAssociationById("airwayreservationid202506更新", "airwaysectionid202502");
		ReservationAirwayAssociationEntity reservationAirwayAssociation3 = reservationAirwayAssociationMapper.getReservationAirwayAssociationById("airwayreservationid202506更新", "airwaysectionid202503");
		ReservationAirwayAssociationEntity reservationAirwayAssociation4 = reservationAirwayAssociationMapper.getReservationAirwayAssociationById("airwayreservationid202506更新", "airwaysectionid202504");
		ReservationAirwayAssociationEntity reservationAirwayAssociation5 = reservationAirwayAssociationMapper.getReservationAirwayAssociationById("airwayreservationid202506更新", "airwaysectionid202505");
		ReservationAirwayAssociationEntity reservationAirwayAssociation6 = reservationAirwayAssociationMapper.getReservationAirwayAssociationById("airwayreservationid202506更新", "airwaysectionid202506");
		//挿入したデータが存在することを確認
		assertNotNull(airwayReservationEntityList);
		assertNull(reservationAirwayAssociation1);
		assertNull(reservationAirwayAssociation2);
		assertNull(reservationAirwayAssociation3);
		assertNotNull(reservationAirwayAssociation4);
		assertNotNull(reservationAirwayAssociation5);
		assertNotNull(reservationAirwayAssociation6);
		assertTrue(result1);
		assertTrue(result2);
	}

	
	/**
	 * 	航路情報の更新成功後の処理
 	 * メール送信サービス処理の呼び出しのテスト
	 * 
	 */
	@Test
	public void testAirwayMessageSendServiceMessageSend() throws Exception {
		
        AirwayInfo airwayInfo = new AirwayInfo();
		airwayInfo.setRegisteredAt("2025-01-23T12:56:00Z");
		airwayInfo.setUpdatedAt("2025-01-23T12:56:00Z");
        checkAndChangeService.notNullCheckAirwayInfo(airwayInfo);
		airwayService.saveAirway(airwayInfo,"airwayid20250123");
		
		//メール送信サービス処理の呼び出しを確認
		airwayMessageSendService.messageSend("airwayid20250123");		
				
	}	


	/**
	 * 航路予約情報を保存する後、
	 * メール送信サービス処理の呼び出しのテスト
	 * 送信後メール周知履歴更新のテスト
	 * 
	 */
	@Test
	public void testAirwayReservationMessageSendServiceMessageSend() throws Exception {
		
		ReserveSection reserveSection1 = new ReserveSection("airwaysectionid202501","2024-11-18T18:18:18Z","2025-11-18T18:18:18Z");
		ReserveSection reserveSection2 = new ReserveSection("airwaysectionid202502","2024-11-18T18:18:18Z","2025-11-18T18:18:18Z");
		ReserveSection reserveSection3 = new ReserveSection("airwaysectionid202503","2024-11-18T18:18:18Z","2025-11-18T18:18:18Z");
		AirwayReserveInfo airwayReserveInfo = new AirwayReserveInfo();
		airwayReserveInfo.setAirwayReservationId("70782784-568c-62df-4ff3-fb3e9003456");
		airwayReserveInfo.setOperatorId("11101");
		airwayReserveInfo.setEventId("eventid202501");
		airwayReserveInfo.setStatus("RESERVED");
		airwayReserveInfo.setAirwaySections(Arrays.asList(reserveSection1,reserveSection2,reserveSection3));
		airwayReserveInfo.setReservedAt("2025-01-05T18:18:18Z");
		airwayReserveInfo.setReservedAt("2025-01-05T18:20:18Z");

		checkAndChangeService.notNullCheckAirwayReserveInfo(airwayReserveInfo);
		airwayReserveService.saveAirwayReservation(airwayReserveInfo);				
		airwayReservationMessageSendService.messageSend(airwayReserveInfo.getAirwayReservationId(),airwayReserveInfo.getStatus(), airwayReserveInfo.getOperatorId());


	}
	
	
}
