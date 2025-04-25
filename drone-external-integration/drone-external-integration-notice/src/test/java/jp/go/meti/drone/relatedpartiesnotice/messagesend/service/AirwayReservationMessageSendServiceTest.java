package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import org.junit.jupiter.api.Order;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.AirwayReserveInfo;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.model.ReserveSection;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.TenantNotificationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayReservationInfoMapper;

/**
 * 航路予約送信のテスト
 */
@ExtendWith({ MockitoExtension.class, SpringExtension.class })
@SpringBootTest
public class AirwayReservationMessageSendServiceTest {
	@Autowired
	private AirwayReservationTenantNotificationService airwayReservationTenantNotificationService;

	@Autowired
	private AirwayReservationInfoMapper airwayReservationInfoMapper;

	@Autowired
	private CheckAndChangeService checkAndChangeService;

	@Autowired
	AirwayReservationMessageSendService airwayReservationMessageSendService;
    /**
     * airwayReservationInfoMapperとairwayTenantNotificationMapperのテスト
     */
    @Test
    public void AirwayReservationSendTest() throws Exception{

		List<AirwayReservationInfoEntity> airwayReservationInfoList = airwayReservationInfoMapper
				.searchAirwayReservationInfo("70782784-568c-62df-4ff3-fb3e9003456");
    	// 航路予約情報取得のテスト
    	assertNotNull(airwayReservationInfoList);
    	
		List<TenantNotificationEntity> relatedpartiesNotificationList = airwayReservationTenantNotificationService
				.getTenantNotification(airwayReservationInfoList.getFirst());
    	// 送信先情報検索のテスト
    	assertNotNull(relatedpartiesNotificationList);
    	
    }
    
	/**
	 *  rescindedMessageSend方法のテスト
	 */
	@Test
	public void testRescindedMessageSend() throws Exception {
		AirwayReserveInfo airwayReserveInfo = new AirwayReserveInfo();
		ReserveSection ReserveSection1 = ReserveSection.builder().airwaySectionId("airwaySectionId4").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		ReserveSection ReserveSection2 = ReserveSection.builder().airwaySectionId("airwaySectionId5").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		ReserveSection ReserveSection3 = ReserveSection.builder().airwaySectionId("airwaySectionId6").startAt("2024/01/24 18:30:30").endAt("2024/01/24 18:30:30").build();
		List<ReserveSection> reserveSections = new ArrayList<>();
		reserveSections.add(ReserveSection1);
		reserveSections.add(ReserveSection2);
		reserveSections.add(ReserveSection3);
		airwayReserveInfo.setAirwayReservationId("2024123120241231202412311123");
		airwayReserveInfo.setOperatorId("11101");
		airwayReserveInfo.setEventId("2024123120241231202412311117AdministratorId");
		airwayReserveInfo.setStatus("RESCINDED");
		//airwayReserveInfo.setReserveSections(reserveSections);
		airwayReserveInfo.setReservedAt("2026/01/18 18:18:18");
		airwayReserveInfo.setUpdatedAt("2026/01/18 18:18:18");

		checkAndChangeService.notNullCheckAirwayReserveInfo(airwayReserveInfo);
		LocalDateTime reservedAt = checkAndChangeService.convertTimestamp(airwayReserveInfo.getReservedAt(), "reservedAt");
		
		AirwayReservationEntity airwayReservation = new AirwayReservationEntity();
		airwayReservation.setAirwayReserveId(airwayReserveInfo.getAirwayReservationId());
		airwayReservation.setOperatorId(airwayReserveInfo.getOperatorId());
		airwayReservation.setEventId(airwayReserveInfo.getEventId());
		airwayReservation.setStatus(airwayReserveInfo.getStatus());		
		airwayReservation.setReservedAt(reservedAt);
		airwayReservation.setUpdatedAt(airwayReserveInfo.getUpdatedAt() == null ? null : checkAndChangeService.convertTimestamp(airwayReserveInfo.getUpdatedAt(), "updatedAt"));
		airwayReservation.setCreationId(1);
		airwayReservation.setCreationDatetime(LocalDateTime.now());
		airwayReservation.setUpdateId(1);
		airwayReservation.setUpdateDatetime(LocalDateTime.now());
		
		//airwayReservationMessageSendService.rescindedMessageSend(airwayReservation);
	}
}
