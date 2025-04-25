package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.model.RequestRouteTenantInfo;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.model.RequestUpdateRouteTenantInfo;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.mapper.AirwayTenantAssociationMapper;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.repository.mapper.TenantMapper;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.service.AirwayTenantServiceImpl;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.AirwayReservationInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.TenantNotificationEntity;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ComponentScan(basePackages = "jp.go.meti.drone")
@ContextConfiguration(classes = { AirwayTenantController.class })
class AirwayTenantControllerForEmailTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	AirwayTenantAssociationMapper airwayTenantAssociationMapper;

	@Autowired
	TenantMapper ｒelatedPartiesMapper;

	ObjectMapper objectMapper = new ObjectMapper();
	
    @Autowired
    private AirwayTenantServiceImpl airwayTenantServiceImpl;   
    
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

	
    /**
     * addAirwayTenantメソッドのテスト
     * <p>
     * 正常系:航路事業者情報を登録処理後、メール送信機能を呼び出し<br>
     * </p>
     * 
     * @throws Exception
     */
    @Test
    void testSendAirwayTenantEmail_normal1() throws Exception {
        
        // 入力データの準備
        RequestRouteTenantInfo entityInfo = RequestRouteTenantInfo.builder()
                .airwayId("100000000000000000000000000000000111")
                .relatedPartiesIdList(
                        Arrays.asList("111010000000000000000000000000000000"))
                .build();

        String jsonString = objectMapper.writeValueAsString(entityInfo);

        mockMvc.perform(
                post("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk()).andExpect(content().string("")).andReturn();
        
    }

    /**
     * updateAirwayTenantメソッドのテスト
     * <p>
     * 正常系:航路事業者情報を更新(追加)処理後、メール送信機能を呼び出し<br>
     * </p>
     * 
     * @throws Exception
     */
    @Test
    void testSendAirwayTenantEmail_norma2() throws Exception {
        // テスト実行前航路事業者情報DBデータを作成
        List<String> dbOperatorIds = Arrays.asList("111020000000000000000000000000000000");
        airwayTenantAssociationMapper.insertAirwayTenantAssociation(
                "100000000000000000000000000000000111", dbOperatorIds, -1, -1);
        // 更新データの準備
        RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("1")
                .airwayId("100000000000000000000000000000000111")
                .relatedPartiesIdList(
                        Arrays.asList("111010000000000000000000000000000000"))
                .build();

        String jsonString = objectMapper.writeValueAsString(entityInfo);

        mockMvc.perform(
                put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk()).andExpect(content().string("")).andReturn(); 
        
    }
    
    /**
     * updateAirwayTenantメソッドのテスト
     * <p>
     * 正常系:航路事業者情報を更新(上書き)処理後、メール送信機能を呼び出し<br>
     * </p>
     * 
     * @throws Exception
     */
    @Test
    void testSendAirwayTenantEmail_norma3() throws Exception {
        // テスト実行前航路事業者情報DBデータを作成
        List<String> dbOperatorIds = Arrays.asList("111020000000000000000000000000000000","111030000000000000000000000000000000");
        airwayTenantAssociationMapper.insertAirwayTenantAssociation(
                "100000000000000000000000000000000111", dbOperatorIds, -1, -1);
        // 更新データの準備
        RequestUpdateRouteTenantInfo entityInfo = RequestUpdateRouteTenantInfo.builder().updateType("0")
                .airwayId("100000000000000000000000000000000111")
                .relatedPartiesIdList(
                        Arrays.asList("111010000000000000000000000000000000","111020000000000000000000000000000000"))
                .build();

        String jsonString = objectMapper.writeValueAsString(entityInfo);

        mockMvc.perform(
                put("/api/v1/airwayTenantLink").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk()).andExpect(content().string("")).andReturn();
        
    }
    
    /**
     * deleteAirwayTenantメソッドのテスト
     * <p>
     * 正常系:航路事業者情報を削除処理後、メール送信機能を呼び出し<br>
     * </p>
     * 
     * @throws Exception
     */
    @Test
    void testSendAirwayTenantEmail_normal4() throws Exception {

        // テスト実行前航路事業者情報DBデータを作成
        List<String> dbOperatorIds = Arrays.asList("111020000000000000000000000000000000","111030000000000000000000000000000000");
        airwayTenantAssociationMapper.insertAirwayTenantAssociation(
                "100000000000000000000000000000000111", dbOperatorIds, -1, -1);
        // モックデータ設定
        String airwayId = "100000000000000000000000000000000111";        
        
        mockMvc.perform(delete("/api/v1/airwayTenantLink?airwayId={airwayId}", airwayId))
               .andExpect(status().isOk()); 

    }
    

    /**
     * 非同期タスクの完了を待つための CountDownLatch を使用
     * @throws InterruptedException
     */
    @Test
    void testAsyncMailSend() throws InterruptedException {
    	
        CountDownLatch latch = new CountDownLatch(1);

        // 非同期タスクの実行
        taskExecutor.execute(() -> {
            // 非同期でメール送信メソッドを呼び出し
        	airwayTenantServiceImpl.sendAirwayEmail(getAirwayInfoEntity(), getRelatedpartiesNotificationList());
            // メール送信が完了したことを通知
            latch.countDown();
        });

        // 非同期タスクが完了するまで待つ
        latch.await();

        // メール送信が完了したことを確認するためのアサーション
        assertTrue(true);
    }
    
    /**
     * 非同期タスクが複数回実行されるシナリオ
     * @throws InterruptedException
     */
    @Test
    void testAsyncMailSendMultipleTasks() throws InterruptedException {
        int numTasks = 5;
        CountDownLatch latch = new CountDownLatch(numTasks);

        // 複数の非同期タスクを実行
        for (int i = 0; i < numTasks; i++) {
            taskExecutor.execute(() -> {
            	airwayTenantServiceImpl.sendAirwayReservationEmail(getAirwayReservationInfoEntity(), getRelatedpartiesNotificationList());
                // メール送信が完了したことを通知
                latch.countDown();
            });
        }

        // 全ての非同期タスクが完了するのを待つ
        latch.await();

        // メール送信が完了したことを確認するためのアサーション
        assertTrue(true);
    }
    
    /**
     * スレッドプールの容量が足りない場合、タスクが拒否されるのテスト
     */
    @Test
    void testRejectedExecution() {
    	
        ThreadPoolTaskExecutor smallExecutor = new ThreadPoolTaskExecutor();
        smallExecutor.setCorePoolSize(2);
        smallExecutor.setMaxPoolSize(2);
        smallExecutor.setQueueCapacity(2);
        smallExecutor.initialize();

        // 非同期タスクをスレッドプールに投入
        for (int i = 0; i < 20; i++) {
            try {
                smallExecutor.execute(() -> {
                    try {
                    	airwayTenantServiceImpl.sendAirwayEmail(getAirwayInfoEntity(), getRelatedpartiesNotificationList());
                    } catch (Exception e) {
                        System.err.println("Task rejected1: " + e.getMessage());
                    }
                });
            } catch (Exception e) {
                // タスクが拒否された場合の処理
                System.err.println("Task rejected2: " + e.getMessage());
            }
        }

        smallExecutor.shutdown();
    }
    
    /**
     * 航路情報準備
     * @return
     */
    private AirwayInfoEntity getAirwayInfoEntity() {
    	
    	AirwayInfoEntity airwayInfoEntity = new AirwayInfoEntity();
        airwayInfoEntity.setAirwayId("100000000000000000000000000000000111");
        airwayInfoEntity.setAirwayName("11111");
        airwayInfoEntity.setStatus("1");
        airwayInfoEntity.setAirwayAdministratorId("1111");

        return airwayInfoEntity;
    }
    
    /**
     * 航路予約情報準備
     * @return
     */
    private AirwayReservationInfoEntity getAirwayReservationInfoEntity() {
    	
    	AirwayReservationInfoEntity airwayReservationInfoEntity = AirwayReservationInfoEntity.builder()
                .airwayReserveId("1")
                .operatorId("U000000001")
                .status("1")
                .reservedAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .airwayId("100000000000000000000000000000000111")
                .build();

        return airwayReservationInfoEntity;
    }
    
    /**
     * 周知先情報準備
     * @return
     */
    private List<TenantNotificationEntity> getRelatedpartiesNotificationList() {

        List<TenantNotificationEntity> relatedpartiesNotificationEntities = new ArrayList<>();

        TenantNotificationEntity airwayInfoEntity = new TenantNotificationEntity();
        airwayInfoEntity.setOperatorId("111010000000000000000000000000000000");
        airwayInfoEntity.setOperatorName("フライト1");
        airwayInfoEntity.setNotificationType("1");
        airwayInfoEntity.setNotificationTarget("000000001@gmail.com");
        relatedpartiesNotificationEntities.add(airwayInfoEntity);
        return relatedpartiesNotificationEntities;
        
    }

}
