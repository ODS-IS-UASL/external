package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.MailSentInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.TenantNotificationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.MailSentInfoMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * メール送信サービステスト
 */
@ExtendWith({ MockitoExtension.class, SpringExtension.class })
@SpringBootTest
public class MailSendServiceTest {

    @Autowired
    MailSendService mailSendService;

    @Autowired
    private MailSentInfoService mailSentInfoService;

    @Autowired
    private MailSentInfoMapper mailSentInfoMapper;

    @Value("${systemuser}")
    private int userId;

    /**
     * mailSentInfoMapper test
     */
    @Test
    public void mailSentInfoServiceTest() throws Exception{

    	TenantNotificationEntity relatedpartiesNotification = getRelatedpartiesNotificationList().get(0);
        System.out.println("relatedpartiesNotification" + relatedpartiesNotification);
        MailSentInfoEntity mailSendInfo = new MailSentInfoEntity();
        mailSendInfo.setOperatorId(relatedpartiesNotification.getOperatorId());
        mailSendInfo.setNotificationTarget(relatedpartiesNotification.getNotificationTarget());
        mailSendInfo.setNotificationType(relatedpartiesNotification.getNotificationType());
        mailSendInfo.setMessageType("20");
        mailSendInfo.setMailDetail("mailDetail");
        mailSendInfo.setSentResult("00");
        mailSendInfo.setFailedReason("FailedReason");
        mailSendInfo.setSentDatetime(LocalDateTime.parse("2024-11-21T18:18:18.123"));
        mailSendInfo.setCreationId(userId);
        mailSendInfo.setCreationDatetime(LocalDateTime.parse("2024-11-21T18:18:18.123"));
        mailSendInfo.setUpdateId(userId);
        mailSendInfo.setCreationDatetime(LocalDateTime.parse("2024-11-21T18:18:18.123"));
        mailSentInfoService.saveMailSentInfo(mailSendInfo);
    }

    /**
     * 正常系<br>
     * 処理区分=1 更新<br>
     * 航路登録情報メール送信
     */
    @Test
    public void test1() {

        AirwayInfoEntity airwayInfoEntity = new AirwayInfoEntity();
        airwayInfoEntity.setAirwayId("U000000001");
        airwayInfoEntity.setAirwayName("東京国際空港 空港の区域");
        airwayInfoEntity.setStatus("1");
        airwayInfoEntity.setAirwayAdministratorId("U000000001");
        airwayInfoEntity.setFlightPurpose("FlightPurpose123TEST");
        airwayInfoEntity.setUpdatedAt("2025/12/12");

        boolean res = mailSendService.sendAirwayMailMessage(airwayInfoEntity, getRelatedpartiesNotificationList());
        assertTrue(res);
    }

    /**
     * 正常系<br>
     * 処理区分=2 削除<br>
     * 航路登録情報メール送信
     */
    @Test
    public void test2() {

        AirwayInfoEntity airwayInfoEntity = new AirwayInfoEntity();
        airwayInfoEntity.setAirwayId("U000000001");
        airwayInfoEntity.setAirwayName("東京国際空港 空港の区域");
        airwayInfoEntity.setStatus("2");
        airwayInfoEntity.setAirwayAdministratorId("U000000002");
        airwayInfoEntity.setFlightPurpose("FlightPurpose123TEST");
        airwayInfoEntity.setUpdatedAt("2025/12/12");

        boolean res = mailSendService.sendAirwayMailMessage(airwayInfoEntity, getRelatedpartiesNotificationList());
        assertTrue(res);
    }


    /**
     * 処理区分パターン外の場合<br>
     * 航路登録情報メール送信
     */
    @Test
    public void test3() {
        AirwayInfoEntity airwayInfoEntity = new AirwayInfoEntity();
        airwayInfoEntity.setAirwayId("U000000001");
        airwayInfoEntity.setAirwayName("東京国際空港 空港の区域");
        airwayInfoEntity.setStatus("3");
        airwayInfoEntity.setAirwayAdministratorId("U000000001");
        airwayInfoEntity.setFlightPurpose("FlightPurpose123TEST");
        airwayInfoEntity.setUpdatedAt("2025/12/12");

        boolean res = mailSendService.sendAirwayMailMessage(airwayInfoEntity, getRelatedpartiesNotificationList());
        assertFalse(res);
    }

    /**
     * 異常系<br>
     * 航路登録情報メール送信
     */
    @Test
    public void test4() {
        AirwayInfoEntity airwayInfoEntity = new AirwayInfoEntity();
        airwayInfoEntity.setAirwayId("U000000001");
        airwayInfoEntity.setAirwayName("東京国際空港 空港の区域");
        airwayInfoEntity.setStatus("1");
        airwayInfoEntity.setAirwayAdministratorId("U000000001");
        airwayInfoEntity.setFlightPurpose("FlightPurpose123TEST");
        airwayInfoEntity.setUpdatedAt("2025/12/12");

        boolean res = mailSendService.sendAirwayMailMessage(airwayInfoEntity, getRelatedpartiesNotificationList());
        assertFalse(res);
    }

    /**
     * 正常系<br>
     * 処理区分=1 更新<br>
     * 航路予約情報メール送信
     */
    @Test
    public void test5() {
        AirwayReservationInfoEntity airwayReservationInfoEntity = AirwayReservationInfoEntity.builder()
            .airwayReserveId("70782784-568c-62df-4ff3-fb3e9003456")
            .operatorId("11101")
            .status("1")
            .reservedAt(LocalDateTime.now().toString())
            .updatedAt(LocalDateTime.now().toString())
            .airwayId("10111")
            .airwayName("test101")
            .build();
		List<String> aiywayNames = new ArrayList<>();
		aiywayNames.add(airwayReservationInfoEntity.getAirwayName());
        boolean res = mailSendService.sendAirwayReservationMailMessage(
        		airwayReservationInfoEntity,
            getRelatedpartiesNotificationList(),aiywayNames);
        assertTrue(res);
    }


    /**
     * 正常系<br>
     * 処理区分=2 キャンセル<br>
     * 航路予約情報メール送信
     */
    @Test
    public void test6() {
        AirwayReservationInfoEntity airwayReservationInfoEntity = AirwayReservationInfoEntity.builder()
            .airwayReserveId("70782784-568c-62df-4ff3-fb3e9003457")
            .operatorId("11101")
            .status("2")
            .reservedAt(LocalDateTime.now().toString())
            .updatedAt(LocalDateTime.now().toString())
            .airwayId("10111")
            .airwayName("test102")
            .build();
		List<String> aiywayNames = new ArrayList<>();
		aiywayNames.add(airwayReservationInfoEntity.getAirwayName());
        boolean res = mailSendService.sendAirwayReservationMailMessage(
        		airwayReservationInfoEntity,
            getRelatedpartiesNotificationList(),aiywayNames);
        assertTrue(res);
    }

    
    /**
     * 正常系<br>
     * 処理区分が1と2以外 キャンセル<br>
     * 航路予約情報メール送信
     */
    @Test
    public void test8() {
        AirwayReservationInfoEntity airwayReservationInfoEntity = AirwayReservationInfoEntity.builder()
            .airwayReserveId("70782784-568c-62df-4ff3-fb3e9003458")
            .operatorId("11101")
            .status("6")
            .reservedAt(LocalDateTime.now().toString())
            .updatedAt(LocalDateTime.now().toString())
            .airwayId("10111")
            .airwayName("test102")
            .build();
		List<String> aiywayNames = new ArrayList<>();
		aiywayNames.add(airwayReservationInfoEntity.getAirwayName());
        boolean res = mailSendService.sendAirwayReservationMailMessage(
        		airwayReservationInfoEntity,
            getRelatedpartiesNotificationList(),aiywayNames);
        assertTrue(res);
    }
    
    
    /**
     * 異常系<br>
     * 航路予約情報メール送信
     */
    @Test
    public void test9() {
        AirwayReservationInfoEntity airwayReservationInfoEntity = AirwayReservationInfoEntity.builder()
            .airwayReserveId("1")
            .operatorId("U000000001")
            .status("1")
            .reservedAt(LocalDateTime.now().toString())
            .updatedAt(LocalDateTime.now().toString())
            .airwayId("T000000001")
            .airwayName("test102")
            .build();
		List<String> aiywayNames = new ArrayList<>();
		aiywayNames.add(airwayReservationInfoEntity.getAirwayName());
        boolean res = mailSendService.sendAirwayReservationMailMessage(
        		airwayReservationInfoEntity,
            getRelatedpartiesNotificationList(),aiywayNames);
        assertFalse(res);
    }

    /**
     * 航路関係者送信情報
     * 
     * @return
     */
    private List<TenantNotificationEntity> getRelatedpartiesNotificationList() {

        List<TenantNotificationEntity> relatedpartiesNotificationEntities = new ArrayList<>();

        TenantNotificationEntity tenantNotificationEntity = new TenantNotificationEntity();
        tenantNotificationEntity.setOperatorId("11101");
        tenantNotificationEntity.setOperatorName("user1");
        tenantNotificationEntity.setNotificationType("1");
        tenantNotificationEntity.setNotificationTarget("test1@dips.co.jp");
        TenantNotificationEntity tenantNotificationEntity1 = new TenantNotificationEntity();
        tenantNotificationEntity1.setOperatorId("70782784-568c-62df-4ff3-fb3e90051c01");
        tenantNotificationEntity1.setOperatorName("フライト2");
        tenantNotificationEntity1.setNotificationType("2");
        tenantNotificationEntity1.setNotificationTarget("000000004@gmail.com");
        TenantNotificationEntity tenantNotificationEntity2 = new TenantNotificationEntity();
        tenantNotificationEntity2.setOperatorId("70782784-568c-62df-4ff3-fb3e90051c02");
        tenantNotificationEntity2.setOperatorName("フライト3");
        tenantNotificationEntity2.setNotificationType("3");
        tenantNotificationEntity2.setNotificationTarget("000000005@gmail.com");

        relatedpartiesNotificationEntities.add(tenantNotificationEntity);
        relatedpartiesNotificationEntities.add(tenantNotificationEntity1);
        relatedpartiesNotificationEntities.add(tenantNotificationEntity2);
        return relatedpartiesNotificationEntities;
    }

}
