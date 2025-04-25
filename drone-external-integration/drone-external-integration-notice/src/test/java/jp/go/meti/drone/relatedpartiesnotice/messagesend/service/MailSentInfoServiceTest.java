package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import org.junit.jupiter.api.Order;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.MailSentInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.MailSentInfoMapper;

/**
 * mail_sent_infoメール周知履歴へ挿入のテスト
 */
@ExtendWith({ MockitoExtension.class, SpringExtension.class })
@SpringBootTest
public class MailSentInfoServiceTest {
	@Autowired
	private MailSentInfoMapper mailSentInfoMapper;

	@Autowired
	private MailSentInfoService mailSentInfoService;

	/**
	 * メール周知履歴インサートのテスト
	 * 
	 */
	@Test
	@Order(1)
	public void testInsertMailSentInfo() throws Exception {
		mailSentInfoMapper.deleteMailSentInfoById("102102102102102102102102102102109");
		MailSentInfoEntity mailSentInfoEntity = MailSentInfoEntity.builder().operatorId("102102102102102102102102102102109")
				.messageType("2")
				.notificationTarget("102@gmail.com").notificationType("1").mailDetail("testmailDetai101").sentResult("1")
				.failedReason("testfailedReason3").sentDatetime(LocalDateTime.parse("2024-11-20T18:18:18.123"))
				.creationId(1).creationDatetime(LocalDateTime.parse("2024-11-21T18:18:18.123")).updateId(1)
				.updateDatetime(LocalDateTime.parse("2024-11-22T18:18:18.123")).build();
		mailSentInfoService.saveMailSentInfo(mailSentInfoEntity);
		
		MailSentInfoEntity searchedmailSentInfoEntity = mailSentInfoMapper.getMailSentInfoById(103,"102102102102102102102102102102109");
		//挿入したデータが存在することを確認
		assertNotNull(searchedmailSentInfoEntity);


	}
	

}
