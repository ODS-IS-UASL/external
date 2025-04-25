package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.TenantNotificationEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.AirwayInfoMapper;

/**
 * 航路送信のテスト
 */
@ExtendWith({ MockitoExtension.class, SpringExtension.class })
@SpringBootTest
public class AirwayMessageSendServiceTest {
	@Autowired
	private AirwayInfoMapper airwayInfoMapper;

	@Autowired
	private AirwayTenantNotificationService airwayTenantNotificationService;

	
    /**
     * airwayInfoMapperとairwayTenantNotificationMapperのテスト
     */
    @Test
    public void AirwayMessageSendTest() throws Exception{


    	AirwayInfoEntity airwayInfo = airwayInfoMapper.searchAirwayInfo("10111");
    	// 航路更新情報取得のテスト
    	assertNotNull(airwayInfo);
    	
    	List<TenantNotificationEntity> relatedpartiesNotificationList = airwayTenantNotificationService
				.getTenantNotification(airwayInfo);
    	// 送信先情報検索のテスト
    	assertNotNull(relatedpartiesNotificationList);
    	
    }
}
