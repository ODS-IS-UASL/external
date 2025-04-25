package jp.go.meti.drone.api.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.auth0.jwt.exceptions.JWTDecodeException;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import jp.go.meti.drone.api.auth.exception.DipsAccessTokenException;
import jp.go.meti.drone.api.auth.repository.DipsAccessRepository;
import jp.go.meti.drone.api.auth.repository.entity.DipsAccessEntity;
import jp.go.meti.drone.api.auth.util.DipsAccessUtils;
import jp.go.meti.drone.com.common.date.SystemDate;
import jp.go.meti.drone.com.common.web.WebClientTemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DIPSアクセスサービステストクラス.
 * 
 * @version $Revision$
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DipsAccessServiceTest {

    /** テスト対象クラス */
    @Spy
    @InjectMocks
    private DipsAccessService target;

    /** DipsAccessRepositoryモック */
    @Mock
    private DipsAccessRepository dipsAccessRepository;

    /** WebClientTemplateモック */
    @Mock
    private WebClientTemplate webClientTemplate;

    /** システム日時モック */
    @Mock
    private SystemDate systemDate;

    /** Appenderモック */
    @Mock
    private Appender<ILoggingEvent> mockAppender;

    /** ログイベントキャプチャ */
    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    /**
     * 前処理
     * <p>
     * 環境変数の値を設定する<br>
     * </p>
     */
    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(target, "defaultClientId", "2aa899fb");
        ReflectionTestUtils.setField(target, "dipsTokenExpirationBuffer", 100);
        ReflectionTestUtils.setField(target, "dipsRefreshTokenExpiration", 3600);
    }

    /**
     * dipsApiExecuteGetメソッドのテスト
     * <p>
     * 正常系1：DIPS API 呼び出し(GET)<br>
     * 設定条件1：なし<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessService - defaultClientId(環境変数) を前処理で定義
     * <li>モックオブジェクト2：DipsAccessService - dipsApiExecuteGet(clientId, url)メソッドが正常レスポンスを返却
     * <li>検証1：モック化した環境変数でテスト対象メソッドが呼び出されていること
     * <li>検証2：dipsApiExecuteGet(clientId)メソッド呼び出し結果が200 OKステータスを返すこと
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("正常系1：DIPS API 呼び出し(GET)_環境変数で定義した事業者IDを利用")
    void testDipsApiExecuteGet_normal1() throws Exception {

        ArgumentCaptor<String> clientId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> url = ArgumentCaptor.forClass(String.class);

        // モックの作成
        DipsAccessServiceTest.TestResponse testResponse = new DipsAccessServiceTest.TestResponse(0, "sample");
        ResponseEntity<Object> dipsApiResponse = new ResponseEntity<Object>(testResponse, HttpStatus.OK);
        doReturn(dipsApiResponse).when(target).dipsApiExecuteGet(clientId.capture(), url.capture());

        // テスト対象呼び出し
        ResponseEntity<Object> actual = target.dipsApiExecuteGet("/hogehoge");

        // 結果の検証
        assertEquals("2aa899fb", clientId.getValue());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    /**
     * dipsApiExecuteGetメソッドのテスト
     * <p>
     * 正常系2：DIPS API 呼び出し(GET)<br>
     * 設定条件1：なし<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessRepository - selectByPrimaryKeyメソッドが正常処理を実行(対象データあり)
     * <li>モックオブジェクト2：DipsAccessService - tokenVerifyメソッドが正常
     * <li>モックオブジェクト3：WebClientTemplate - getメソッドが正常なレスポンスを返却
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：dipsApiExecuteGetメソッド呼び出し結果が200 OKステータスを返すこと
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("正常系2：DIPS API 呼び出し(GET)")
    void testDipsApiExecuteGet_normal2() throws Exception {

        ArgumentCaptor<String> clientId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Consumer<HttpHeaders>> httpHedders = ArgumentCaptor.forClass(Consumer.class);
        ArgumentCaptor<String> url = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Class<Object>> resObj = ArgumentCaptor.forClass(Class.class);

        // モックの作成
        DipsAccessEntity entity = DipsAccessEntity.builder() //
            .clientId("2aa899fb") //
            .accessToken("aaa") //
            .expiresIn(10) //
            .refreshExpiresIn(600) //
            .refreshToken("bbb") //
            .tokenType("bearer") //
            .idToken("ccc") //
            .notBeforePolicy(0) //
            .sessionState("ddd") //
            .scope("openid profile offline_access") //
            .createDatetime(null) //
            .updateDatetime(null) //
            .logicDeleteFlag(false) //
            .build();
        when(dipsAccessRepository.selectByPrimaryKey(clientId.capture())).thenReturn(entity);

        doNothing().when(target).tokenVerify(clientId.capture());

        DipsAccessServiceTest.TestResponse testResponse = new DipsAccessServiceTest.TestResponse(0, "sample");
        ResponseEntity<Object> dipsApiResponse = new ResponseEntity<Object>(testResponse, HttpStatus.OK);
        when(webClientTemplate.get(url.capture(), httpHedders.capture(), resObj.capture())).thenReturn(dipsApiResponse);

        // テスト対象呼び出し
        ResponseEntity<Object> actual = target.dipsApiExecuteGet("2aa899fb", "/hogehoge");

        // 結果の検証
        verify(dipsAccessRepository, times(1)).selectByPrimaryKey(clientId.capture());
        verify(target, times(1)).tokenVerify(clientId.capture());
        verify(systemDate, times(0)).now();
        verify(webClientTemplate, times(1)).get(url.capture(), httpHedders.capture(), resObj.capture());

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    /**
     * dipsApiExecutePostメソッドのテスト
     * <p>
     * 正常系1：DIPS API 呼び出し(POST)<br>
     * 設定条件1：なし<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessService - defaultClientId(環境変数) を前処理で定義
     * <li>モックオブジェクト2：DipsAccessService - dipsApiExecutePost(clientId, url, request)メソッドが正常レスポンスを返却
     * <li>検証1：モック化した環境変数でテスト対象メソッドが呼び出されていること
     * <li>検証2：dipsApiExecutePost(url, request)メソッド呼び出し結果が200 OKステータスを返すこと
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("正常系1：DIPS API 呼び出し(POST)_成功_環境変数で定義した事業者IDを利用")
    void testDipsApiExecutePost_normal1() throws Exception {

        ArgumentCaptor<String> clientId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> url = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> reqObj = ArgumentCaptor.forClass(Object.class);

        // モックの作成
        DipsAccessServiceTest.TestResponse testResponse = new DipsAccessServiceTest.TestResponse(0, "sample");
        ResponseEntity<Object> dipsApiResponse = new ResponseEntity<Object>(testResponse, HttpStatus.OK);
        doReturn(dipsApiResponse).when(target).dipsApiExecutePost(clientId.capture(), url.capture(), reqObj.capture());

        // テスト対象呼び出し
        DipsAccessServiceTest.TestRequest testRequest = new DipsAccessServiceTest.TestRequest(1, "test");
        ResponseEntity<Object> actual = target.dipsApiExecutePost("/fugafuga", testRequest);

        // 結果の検証
        assertEquals("2aa899fb", clientId.getValue());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    /**
     * dipsApiExecutePostメソッドのテスト
     * <p>
     * 正常系2：DIPS API 呼び出し(POST)_成功<br>
     * 設定条件1：なし<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessRepository - selectByPrimaryKeyメソッドが正常処理を実行(対象データあり)
     * <li>モックオブジェクト2：DipsAccessService - tokenVerifyメソッドが正常
     * <li>モックオブジェクト3：WebClientTemplate - postメソッドが正常なレスポンスを返却
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：dipsApiExecutePostメソッド呼び出し結果が200 OKステータスを返すこと
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("正常系2：DIPS API 呼び出し(POST)_成功")
    void testDipsApiExecutePost_normal2() throws Exception {
        ArgumentCaptor<String> clientId = ArgumentCaptor.forClass(String.class);

        ArgumentCaptor<Consumer<HttpHeaders>> httpHedders = ArgumentCaptor.forClass(Consumer.class);
        ArgumentCaptor<String> url = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> reqObj = ArgumentCaptor.forClass(Object.class);
        ArgumentCaptor<Class<Object>> resObj = ArgumentCaptor.forClass(Class.class);

        // モックの作成
        DipsAccessEntity entity = DipsAccessEntity.builder() //
            .clientId("2aa899fb") //
            .accessToken("aaa") //
            .expiresIn(10) //
            .refreshExpiresIn(600) //
            .refreshToken("bbb") //
            .tokenType("bearer") //
            .idToken("ccc") //
            .notBeforePolicy(0) //
            .sessionState("ddd") //
            .scope("openid profile offline_access") //
            .createDatetime(null) //
            .updateDatetime(null) //
            .logicDeleteFlag(false) //
            .build();
        when(dipsAccessRepository.selectByPrimaryKey(clientId.capture())).thenReturn(entity);

        doNothing().when(target).tokenVerify(clientId.capture());

        DipsAccessServiceTest.TestResponse testResponse = new DipsAccessServiceTest.TestResponse(0, "sample");
        ResponseEntity<Object> dipsApiResponse = new ResponseEntity<Object>(testResponse, HttpStatus.OK);

        when(webClientTemplate.post(url.capture(), httpHedders.capture(), reqObj.capture(), resObj.capture()))
            .thenReturn(dipsApiResponse);

        // テスト対象呼び出し
        DipsAccessServiceTest.TestRequest testRequest = new DipsAccessServiceTest.TestRequest(1, "test");
        ResponseEntity<Object> actual = target.dipsApiExecutePost("2aa899fb", "/fugafuga", testRequest);

        // 結果の検証
        verify(dipsAccessRepository, times(1)).selectByPrimaryKey(clientId.capture());
        verify(target, times(1)).tokenVerify(clientId.capture());
        verify(systemDate, times(0)).now();
        verify(webClientTemplate, times(1)).post(
            url.capture(),
            httpHedders.capture(),
            reqObj.capture(),
            resObj.capture());
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * tokenVerifyメソッドのテスト
     * <p>
     * 正常系1：アクセストークン検証_成功(アクセストークンが有効)<br>
     * 設定条件1：アクセストークン有効期限内<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessUtils - getClaimFromJWTメソッドがデコードした対象クレームの値を取得(アクセストークン)
     * <li>モックオブジェクト2：DipsAccessRepository - selectByPrimaryKeyメソッドが正常処理を実行(対象データあり)
     * <li>モックオブジェクト3：systemDate - nowメソッドが正常に日時取得
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：例外が発生しないこと
     * <li>検証3：リフレッシュトークンの有効期限チェックおよびトークンリフレッシュが実行されないこと
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("正常系1：アクセストークン検証_成功(アクセストークンが有効)")
    void testTokenVerify_normal1() throws Exception {

        ArgumentCaptor<String> clientId = ArgumentCaptor.forClass(String.class);

        // モックの作成
        // Dipsアクセスのユーティリティクラスのモック
        try (MockedStatic<DipsAccessUtils> dipsAccessUtilsMock = mockStatic(DipsAccessUtils.class)) {

            dipsAccessUtilsMock.when(() -> DipsAccessUtils.getClaimFromJWT(anyString(), anyString()))
                .thenReturn(System.currentTimeMillis());

            // トークンDB取得のモック
            DipsAccessEntity tokenInfo = DipsAccessEntity.builder() //
                .clientId("2aa899fb-920a-479f-9c98-6dff943dbf31") //
                .accessToken("bbb") //
                .expiresIn(60) //
                .refreshExpiresIn(300) //
                .refreshToken("ccc") //
                .tokenType("bearer") //
                .idToken("ddd") //
                .notBeforePolicy(0) //
                .sessionState("eee") //
                .scope("openid profile offline_access") //
                .build();
            when(dipsAccessRepository.selectByPrimaryKey(clientId.capture())).thenReturn(tokenInfo);

            // システム日時取得のモック
            when(systemDate.now()).thenReturn(LocalDateTime.now());

            // テスト対象呼び出し
            target.tokenVerify("2aa899fb-920a-479f-9c98-6dff943dbf31");

            // 結果の検証
            dipsAccessUtilsMock.verify(() -> DipsAccessUtils.getClaimFromJWT(anyString(), anyString()), times(1));
            verify(dipsAccessRepository, times(1)).selectByPrimaryKey(clientId.capture());
            verify(systemDate, times(1)).now();
        }

    }

    /**
     * tokenVerifyメソッドのテスト
     * <p>
     * 正常系2：アクセストークン検証_成功(リフレッシュトークンが有効)<br>
     * 設定条件1：アクセストークン有効期限外かつリフレッシュトークン有効期限内<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessUtils - getClaimFromJWTメソッドがデコードした対象クレームの値を取得(1回目：アクセストークン、2回目：リフレッシュトークン)
     * <li>モックオブジェクト2：DipsAccessRepository - selectByPrimaryKeyメソッドが正常処理を実行(対象データあり)
     * <li>モックオブジェクト3：systemDate - nowメソッドが正常に日時取得
     * <li>モックオブジェクト4：WebClientTemplate - postメソッドでトークンのリフレッシュ処理正常
     * <li>モックオブジェクト5：DipsAccessRepository - deleteByPrimaryKeyメソッドが正常処理を実行（対象データ削除）
     * <li>モックオブジェクト5：DipsAccessRepository - insertメソッドが正常処理を実行（対象データ登録）
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：例外が発生しないこと
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("正常系2：アクセストークン検証_成功(リフレッシュトークンが有効)")
    void testTokenVerify_normal2() throws Exception {

        ArgumentCaptor<String> clientId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<DipsAccessEntity> insertParam = ArgumentCaptor.forClass(DipsAccessEntity.class);

        ArgumentCaptor<String> refreshPath = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Consumer<HttpHeaders>> httpHedders = ArgumentCaptor.forClass(Consumer.class);

        // モックの作成
        // Dipsアクセスのユーティリティクラスのモック
        try (MockedStatic<DipsAccessUtils> dipsAccessUtilsMock = mockStatic(DipsAccessUtils.class)) {

            dipsAccessUtilsMock.when(() -> DipsAccessUtils.getClaimFromJWT(anyString(), anyString()))
                .thenReturn(1700000000L)
                .thenReturn(2000000000L);

            // トークンDB取得のモック
            DipsAccessEntity tokenInfo = DipsAccessEntity.builder() //
                .clientId("2aa899fb-920a-479f-9c98-6dff943dbf31") //
                .accessToken("bbb") //
                .expiresIn(60) //
                .refreshExpiresIn(300) //
                .refreshToken("ccc") //
                .tokenType("bearer") //
                .idToken("ddd") //
                .notBeforePolicy(0) //
                .sessionState("eee") //
                .scope("openid profile offline_access") //
                .build();
            when(dipsAccessRepository.selectByPrimaryKey(clientId.capture())).thenReturn(tokenInfo);

            // システム日時取得のモック
            when(systemDate.now()).thenReturn(LocalDateTime.now());

            // アクセストークン取得リクエスト(リフレッシュ)のモック
            Class innerClazz = Class.forName(
                "jp.go.meti.drone.api.auth.service.DipsAccessService$RefreshTokenResponse");
            Constructor constructor = innerClazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object refreshTokenResponse = constructor.newInstance();

            Field accessTokenField = refreshTokenResponse.getClass().getDeclaredField("accessToken");
            accessTokenField.setAccessible(true);
            accessTokenField.set(refreshTokenResponse, "abc");

            ResponseEntity<Object> refreshTokenInfoRespose = new ResponseEntity<>(refreshTokenResponse, HttpStatus.OK);
            when(
                webClientTemplate.post(
                    refreshPath.capture(),
                    httpHedders.capture(),
                    any(Object.class),
                    any(Class.class))).thenReturn(refreshTokenInfoRespose);

            // トークンDB削除のモック
            when(dipsAccessRepository.deleteByPrimaryKey(clientId.capture())).thenReturn(1);

            // トークンDB登録のモック
            when(dipsAccessRepository.insert(insertParam.capture())).thenReturn(1);

            // テスト対象呼び出し
            target.tokenVerify("2aa899fb-920a-479f-9c98-6dff943dbf31");

            // 結果の検証
            dipsAccessUtilsMock.verify(() -> DipsAccessUtils.getClaimFromJWT(anyString(), anyString()), times(2));
            verify(dipsAccessRepository, times(2)).selectByPrimaryKey(clientId.capture());
            verify(systemDate, times(2)).now();
            verify(webClientTemplate, times(1)).post(
                refreshPath.capture(),
                httpHedders.capture(),
                any(Object.class),
                any(Class.class));
            verify(dipsAccessRepository, times(2)).deleteByPrimaryKey(clientId.capture());
            verify(dipsAccessRepository, times(1)).insert(insertParam.capture());
        }

    }

    /**
     * tokenVerifyメソッドのテスト
     * <p>
     * 異常系1：アクセストークン検証_例外発生<br>
     * 設定条件1：アクセストークン有効期限外かつリフレッシュトークン有効期限外<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessUtils - getClaimFromJWTメソッドがデコードした対象クレームの値を取得(1回目：アクセストークン、2回目：リフレッシュトークン)
     * <li>モックオブジェクト2：DipsAccessRepository - selectByPrimaryKeyメソッドが正常処理を実行(対象データあり)
     * <li>モックオブジェクト3：systemDate - nowメソッドが正常に日時取得
     * <li>モックオブジェクト4：WebClientTemplate - postメソッドでトークンのリフレッシュ処理正常
     * <li>モックオブジェクト5：DipsAccessRepository - deleteByPrimaryKeyメソッドが正常処理を実行（対象データ削除）
     * <li>モックオブジェクト5：DipsAccessRepository - insertメソッドが正常処理を実行（対象データ登録）
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：DipsAccessTokenExceptionが発生すること
     * <li>検証3：ERRORレベルのログメッセージが出力されること
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("異常系1：アクセストークン検証_リフレッシュトークンの有効期限切れにより例外発生")
    void testTokenVerify_error1() throws Exception {

        ArgumentCaptor<String> clientId = ArgumentCaptor.forClass(String.class);
        Logger logger = (Logger) LoggerFactory.getLogger(target.getClass());
        logger.addAppender(mockAppender);

        // モックの作成
        // Dipsアクセスのユーティリティクラスのモック
        try (MockedStatic<DipsAccessUtils> dipsAccessUtilsMock = mockStatic(DipsAccessUtils.class)) {

            dipsAccessUtilsMock.when(() -> DipsAccessUtils.getClaimFromJWT(anyString(), anyString()))
                .thenReturn(1700000000L)
                .thenReturn(1700000001L);

            // トークンDB取得のモック
            DipsAccessEntity tokenInfo = DipsAccessEntity.builder() //
                .clientId("2aa899fb-920a-479f-9c98-6dff943dbf31") //
                .accessToken("bbb") //
                .expiresIn(60) //
                .refreshExpiresIn(300) //
                .refreshToken("ccc") //
                .tokenType("bearer") //
                .idToken("ddd") //
                .notBeforePolicy(0) //
                .sessionState("eee") //
                .scope("openid profile offline_access") //
                .build();
            when(dipsAccessRepository.selectByPrimaryKey(clientId.capture())).thenReturn(tokenInfo);

            // システム日時取得のモック
            when(systemDate.now()).thenReturn(LocalDateTime.now());

            // テスト対象呼び出し
            assertThrows(
                DipsAccessTokenException.class,
                () -> target.tokenVerify("2aa899fb-920a-479f-9c98-6dff943dbf31"));

            // 結果の検証
            dipsAccessUtilsMock.verify(() -> DipsAccessUtils.getClaimFromJWT(anyString(), anyString()), times(2));
            verify(dipsAccessRepository, times(1)).selectByPrimaryKey(clientId.capture());
            verify(systemDate, times(2)).now();
            verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
            assertThat(captorLoggingEvent.getValue().getLevel().toString()).hasToString("ERROR");
            assertThat(captorLoggingEvent.getValue().getMessage()).hasToString(
                "リフレッシュトークンの有効期限切れです。 クライアントID :" + clientId.getValue());
        }

    }

    /**
     * tokenVerifyメソッドのテスト
     * <p>
     * 異常系2：アクセストークン検証_例外発生<br>
     * 設定条件1：アクセストークンがDBに存在しない<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessRepository - selectByPrimaryKeyメソッドが正常処理を実行(対象データなし)
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：例外が発生すること
     * </ul>
     */
    @Test
    @DisplayName("異常系2：アクセストークン検証_トークン情報が取得失敗による例外発生")
    void testTokenVerify_error2() throws Exception {

        ArgumentCaptor<String> clientId = ArgumentCaptor.forClass(String.class);

        // モックの作成
        // DBからnullを返す。
        when(dipsAccessRepository.selectByPrimaryKey(clientId.capture())).thenReturn(null);

        // テスト対象呼び出し
        DipsAccessTokenException e = assertThrows(DipsAccessTokenException.class, () -> {
            target.tokenVerify("2aa899fb-920a-479f-9c98-6dff943dbf31");
        });

        // 結果の検証
        verify(dipsAccessRepository, times(1)).selectByPrimaryKey(clientId.capture());
        assertEquals("TokenInfo Not Found", e.getMessage());

    }

    /**
     * tokenVerifyメソッドのテスト
     * <p>
     * 異常系3：アクセストークン検証_例外発生<br>
     * 設定条件1：更新されたトークン情報が取得できない<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessUtils - getClaimFromJWTメソッドがデコードした対象クレームの値を取得(1回目：アクセストークン、2回目：リフレッシュトークン)
     * <li>モックオブジェクト2：DipsAccessRepository - selectByPrimaryKeyメソッドが正常処理を実行(対象データあり)
     * <li>モックオブジェクト3：systemDate - nowメソッドが正常に日時取得
     * <li>モックオブジェクト4：WebClientTemplate - postメソッドでトークンのリフレッシュ処理正常
     * <li>モックオブジェクト5：DipsAccessRepository - deleteByPrimaryKeyメソッドが正常処理を実行（対象データ削除）
     * <li>モックオブジェクト5：DipsAccessRepository - insertメソッドが正常処理を実行（対象データ登録）
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：例外が発生すること
     * </ul>
     */
    @Test
    @DisplayName("異常系3：アクセストークン検証_更新されたトークン情報が取得できないことによる例外発生")
    void testTokenVerify_error3() throws Exception {

        ArgumentCaptor<String> clientId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<DipsAccessEntity> insertParam = ArgumentCaptor.forClass(DipsAccessEntity.class);

        ArgumentCaptor<String> refreshPath = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Consumer<HttpHeaders>> httpHedders = ArgumentCaptor.forClass(Consumer.class);

        // モックの作成
        // Dipsアクセスのユーティリティクラスのモック
        try (MockedStatic<DipsAccessUtils> dipsAccessUtilsMock = mockStatic(DipsAccessUtils.class);) {

            dipsAccessUtilsMock.when(() -> DipsAccessUtils.getClaimFromJWT(anyString(), anyString()))
                .thenReturn(1700000000L)
                .thenReturn(2000000000L);

            // トークンDB取得のモック
            DipsAccessEntity tokenInfo = DipsAccessEntity.builder() //
                .clientId("2aa899fb-920a-479f-9c98-6dff943dbf31") //
                .accessToken("bbb") //
                .expiresIn(60) //
                .refreshExpiresIn(300) //
                .refreshToken("ccc") //
                .tokenType("bearer") //
                .idToken("ddd") //
                .notBeforePolicy(0) //
                .sessionState("eee") //
                .scope("openid profile offline_access") //
                .build();
            when(dipsAccessRepository.selectByPrimaryKey(clientId.capture())).thenReturn(tokenInfo);

            // システム日時取得のモック
            when(systemDate.now()).thenReturn(LocalDateTime.now());

            // アクセストークン取得リクエスト(リフレッシュ)のレスポンスボディのモック
            Object refreshTokenResponse = null;

            ResponseEntity<Object> refreshTokenInfoRespose = new ResponseEntity<>(
                refreshTokenResponse, HttpStatus.BAD_REQUEST);
            when(
                webClientTemplate.post(
                    refreshPath.capture(),
                    httpHedders.capture(),
                    any(Object.class),
                    any(Class.class))).thenReturn(refreshTokenInfoRespose);

            // テスト対象呼び出し
            DipsAccessTokenException e = assertThrows(DipsAccessTokenException.class, () -> {
                target.tokenVerify("2aa899fb-920a-479f-9c98-6dff943dbf31");
            });

            // 結果の検証
            dipsAccessUtilsMock.verify(() -> DipsAccessUtils.getClaimFromJWT(anyString(), anyString()), times(2));
            verify(dipsAccessRepository, times(1)).selectByPrimaryKey(clientId.capture());
            verify(systemDate, times(2)).now();
            verify(webClientTemplate, times(1)).post(
                refreshPath.capture(),
                httpHedders.capture(),
                any(Object.class),
                any(Class.class));
            assertEquals("Updated TokenInfo Not Found", e.getMessage());

        }
    }

    /**
     * tokenVerifyメソッドのテスト
     * <p>
     * 異常系5：アクセストークン検証_例外発生<br>
     * 設定条件1：アクセストークンのデコードに失敗<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessUtils - getClaimFromJWTメソッドがデコードした対象クレームの値を取得(1回目：デコード失敗による例外、2回目：呼び出しなし)
     * <li>モックオブジェクト2：DipsAccessRepository - selectByPrimaryKeyメソッドが正常処理を実行(対象データあり)
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：例外が発生しないこと
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("異常系4：アクセストークン検証_アクセストークンのデコードに失敗することによる例外発生")
    void testTokenVerify_error4() throws Exception {

        ArgumentCaptor<String> clientId = ArgumentCaptor.forClass(String.class);

        // モックの作成
        // Dipsアクセスのユーティリティクラスのモック
        try (MockedStatic<DipsAccessUtils> dipsAccessUtilsMock = mockStatic(DipsAccessUtils.class)) {

            dipsAccessUtilsMock.when(() -> DipsAccessUtils.getClaimFromJWT(anyString(), anyString()))
                .thenThrow(new JWTDecodeException("Access Refresh Token Test"));

            // トークンDB取得のモック
            DipsAccessEntity tokenInfo = DipsAccessEntity.builder() //
                .clientId("2aa899fb-920a-479f-9c98-6dff943dbf31") //
                .accessToken("bbb") //
                .expiresIn(60) //
                .refreshExpiresIn(300) //
                .refreshToken("ccc") //
                .tokenType("bearer") //
                .idToken("ddd") //
                .notBeforePolicy(0) //
                .sessionState("eee") //
                .scope("openid profile offline_access") //
                .build();
            when(dipsAccessRepository.selectByPrimaryKey(clientId.capture())).thenReturn(tokenInfo);

            // テスト対象呼び出し
            DipsAccessTokenException e = assertThrows(DipsAccessTokenException.class, () -> {
                target.tokenVerify("2aa899fb-920a-479f-9c98-6dff943dbf31");
            });

            // 結果の検証
            dipsAccessUtilsMock.verify(() -> DipsAccessUtils.getClaimFromJWT(anyString(), anyString()), times(1));
            verify(dipsAccessRepository, times(1)).selectByPrimaryKey(clientId.capture());
            assertEquals("Access Token is Invalid", e.getMessage());
        }
    }

    /**
     * tokenVerifyメソッドのテスト
     * <p>
     * 異常系5：アクセストークン検証_例外発生<br>
     * 設定条件1：リフレッシュトークンのデコードに失敗<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessUtils - getClaimFromJWTメソッドがデコードした対象クレームの値を取得(1回目：アクセストークン、2回目：デコード失敗による例外)
     * <li>モックオブジェクト2：DipsAccessRepository - selectByPrimaryKeyメソッドが正常処理を実行(対象データあり)
     * <li>モックオブジェクト3：systemDate - nowメソッドが正常に日時取得
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：例外が発生しないこと
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("異常系5：アクセストークン検証_リフレッシュトークンのデコードに失敗することによる例外発生")
    void testTokenVerify_error5() throws Exception {

        ArgumentCaptor<String> clientId = ArgumentCaptor.forClass(String.class);

        // モックの作成
        // Dipsアクセスのユーティリティクラスのモック
        try (MockedStatic<DipsAccessUtils> dipsAccessUtilsMock = mockStatic(DipsAccessUtils.class)) {

            dipsAccessUtilsMock.when(() -> DipsAccessUtils.getClaimFromJWT(anyString(), anyString()))
                .thenReturn(1700000000L)
                .thenThrow(new JWTDecodeException("Invalid Refresh Token Test"));

            // トークンDB取得のモック
            DipsAccessEntity tokenInfo = DipsAccessEntity.builder() //
                .clientId("2aa899fb-920a-479f-9c98-6dff943dbf31") //
                .accessToken("bbb") //
                .expiresIn(60) //
                .refreshExpiresIn(300) //
                .refreshToken("ccc") //
                .tokenType("bearer") //
                .idToken("ddd") //
                .notBeforePolicy(0) //
                .sessionState("eee") //
                .scope("openid profile offline_access") //
                .build();
            when(dipsAccessRepository.selectByPrimaryKey(clientId.capture())).thenReturn(tokenInfo);

            // システム日時取得のモック
            when(systemDate.now()).thenReturn(LocalDateTime.now());

            // テスト対象呼び出し
            DipsAccessTokenException e = assertThrows(DipsAccessTokenException.class, () -> {
                target.tokenVerify("2aa899fb-920a-479f-9c98-6dff943dbf31");
            });

            // 結果の検証
            dipsAccessUtilsMock.verify(() -> DipsAccessUtils.getClaimFromJWT(anyString(), anyString()), times(2));
            verify(dipsAccessRepository, times(1)).selectByPrimaryKey(clientId.capture());
            verify(systemDate, times(1)).now();
            assertEquals("Refresh Token is Invalid", e.getMessage());
        }
    }

    /**
     * getDipsAccessTokenメソッドのテスト
     * <p>
     * "正常系1：アクセストークンをDBより取得_成功"<br>
     * 設定条件1：DBからアクセストークンの取得が正常に実行される<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessRepository - selectByPrimaryKeyメソッドが正常処理を実行(対象データなし)
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：例外が発生しないこと
     * </ul>
     */
    @Test
    @DisplayName("正常系1：アクセストークンをDBより取得_成功")
    void testGetDipsAccessToken_normal1() throws Exception {
        // プライベートメソッドの取得
        Method method = DipsAccessService.class.getDeclaredMethod("getDipsAccessToken", String.class);
        method.setAccessible(true);

        // モックの作成
        DipsAccessEntity entity = DipsAccessEntity.builder() //
            .clientId("2aa899fb") //
            .accessToken("aaa") //
            .expiresIn(10) //
            .refreshExpiresIn(600) //
            .refreshToken("bbb") //
            .tokenType("bearer") //
            .idToken("ccc") //
            .notBeforePolicy(0) //
            .sessionState("ddd") //
            .scope("openid profile offline_access") //
            .createDatetime(null) //
            .updateDatetime(null) //
            .logicDeleteFlag(false) //
            .build();

        when(dipsAccessRepository.selectByPrimaryKey(any(String.class))).thenReturn(entity);

        // テスト対象呼び出し
        String result = (String) method.invoke(target, "2aa899fb");

        // 結果の検証
        verify(dipsAccessRepository, times(1)).selectByPrimaryKey("2aa899fb");
        assertEquals("aaa", result);
    }

    /**
     * getDipsAccessTokenメソッドのテスト
     * <p>
     * "異常系1：アクセストークンをDBより取得_事業者IDに対応するトークンがないことによる例外発生"<br>
     * 設定条件1：事業者IDに対応するトークンがないことで例外が発生<br>
     * <p>
     * <ul>
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：例外が発生すること
     * </ul>
     */
    @Test
    @DisplayName("異常系1：アクセストークンをDBより取得_事業者IDに対応するトークンがないことによる例外発生")
    void testGetDipsAccessToken_error1() throws Exception {
        // プライベートメソッドの取得
        Method method = DipsAccessService.class.getDeclaredMethod("getDipsAccessToken", String.class);
        method.setAccessible(true);

        when(dipsAccessRepository.selectByPrimaryKey(any(String.class))).thenReturn(null);

        // テスト対象呼び出し
        InvocationTargetException e = assertThrows(InvocationTargetException.class, () -> {
            method.invoke(target, "2aa899fb");
        });

        Throwable cause = e.getCause();

        // 結果の検証
        verify(dipsAccessRepository, times(1)).selectByPrimaryKey("2aa899fb");
        assertEquals("Access Token Not Found", cause.getMessage());
    }

    /**
     * saveメソッドのテスト
     * <p>
     * "正常系1：アクセストークン保存_成功"<br>
     * 設定条件1：DBへアクセストークンの保存が正常に実行される<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessRepository - selectByPrimaryKeyメソッドが正常処理を実行(対象データなし)
     * <li>モックオブジェクト2：DipsAccessRepository - insertメソッドが正常処理を実行（対象データ登録）
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：例外が発生しないこと
     * </ul>
     */
    @Test
    @DisplayName("正常系1：アクセストークン保存_成功")
    void testSave_normal1() throws Exception {
        // モックの作成
        DipsAccessEntity entity = DipsAccessEntity.builder() //
            .clientId("2aa899fb") //
            .accessToken("aaa") //
            .expiresIn(10) //
            .refreshExpiresIn(600) //
            .refreshToken("bbb") //
            .tokenType("bearer") //
            .idToken("ccc") //
            .notBeforePolicy(0) //
            .sessionState("ddd") //
            .scope("openid profile offline_access") //
            .createDatetime(null) //
            .updateDatetime(null) //
            .logicDeleteFlag(false) //
            .build();

        when(dipsAccessRepository.selectByPrimaryKey(any(String.class))).thenReturn(null);
        when(dipsAccessRepository.insert(any(DipsAccessEntity.class))).thenReturn(1);

        // テスト対象呼び出しと結果の検証
        assertDoesNotThrow(() -> target.save(entity));
        verify(dipsAccessRepository, times(1)).selectByPrimaryKey("2aa899fb");
        verify(dipsAccessRepository, times(1)).insert(entity);
    }

    /**
     * saveメソッドのテスト
     * <p>
     * "異常系1：アクセストークン保存_事業者IDが取得できないことによる例外発生"<br>
     * 設定条件1：事業者IDが取得できず、例外が発生する<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessRepository - selectByPrimaryKeyメソッドが正常処理を実行(対象データなし)
     * <li>モックオブジェクト2：DipsAccessRepository - insertメソッドが正常処理を実行（対象データ登録）
     * <li>検証1：例外「e.auth.0002」が発生すること
     * </ul>
     */
    @Test
    @DisplayName("異常系1：アクセストークン保存_事業者IDが取得できないことによる例外発生")
    void testSave_error1() throws Exception {
        // モックの作成
        DipsAccessEntity entity = DipsAccessEntity.builder() //
            .clientId(null) //
            .accessToken("aaa") //
            .expiresIn(10) //
            .refreshExpiresIn(600) //
            .refreshToken("bbb") //
            .tokenType("bearer") //
            .idToken("ccc") //
            .notBeforePolicy(0) //
            .sessionState("ddd") //
            .scope("openid profile offline_access") //
            .createDatetime(null) //
            .updateDatetime(null) //
            .logicDeleteFlag(false) //
            .build();

        when(dipsAccessRepository.selectByPrimaryKey(any(String.class))).thenReturn(null);
        when(dipsAccessRepository.insert(any(DipsAccessEntity.class))).thenReturn(1);

        // テスト対象呼び出し
        DipsAccessTokenException e = assertThrows(DipsAccessTokenException.class, () -> {
            target.save(entity);
        });

        // 結果の検証
        assertEquals("ClientID Not Found", e.getMessage());
    }

    /**
     * saveメソッドのテスト
     * <p>
     * "異常系2：アクセストークン保存_DB登録時に例外発生"<br>
     * 設定条件1：DB登録時に例外が発生する<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessRepository - selectByPrimaryKeyメソッドが正常処理を実行(対象データなし)
     * <li>モックオブジェクト2：DipsAccessRepository - insertメソッドが正常処理を実行（対象データ登録）
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：例外「e.auth.0002」が発生すること
     * </ul>
     */
    @Test
    @DisplayName("異常系2：アクセストークン保存_DB登録時に例外発生")
    void testSave_error2() throws Exception {
        // モックの作成
        DipsAccessEntity entity = DipsAccessEntity.builder() //
            .clientId("2aa899fb") //
            .accessToken("aaa") //
            .expiresIn(10) //
            .refreshExpiresIn(600) //
            .refreshToken("bbb") //
            .tokenType("bearer") //
            .idToken("ccc") //
            .notBeforePolicy(0) //
            .sessionState("ddd") //
            .scope("openid profile offline_access") //
            .createDatetime(null) //
            .updateDatetime(null) //
            .logicDeleteFlag(false) //
            .build();

        when(dipsAccessRepository.selectByPrimaryKey(any(String.class))).thenReturn(null);
        when(dipsAccessRepository.insert(any(DipsAccessEntity.class))).thenThrow(new RuntimeException());

        // テスト対象呼び出し
        DipsAccessTokenException e = assertThrows(DipsAccessTokenException.class, () -> {
            target.save(entity);
        });

        // 結果の検証
        assertEquals("Token Save Failed", e.getMessage());
        verify(dipsAccessRepository, times(1)).selectByPrimaryKey("2aa899fb");
        verify(dipsAccessRepository, times(1)).insert(entity);
    }

    /**
     * deleteメソッドのテスト
     * <p>
     * "正常系1：アクセストークン削除_成功"<br>
     * 設定条件1：DBからアクセストークンが正常に削除される<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessRepository - deleteByPrimaryKeyメソッドが正常処理を実行(対象データなし)
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：例外が発生しないこと
     * </ul>
     */
    @Test
    @DisplayName("正常系1：アクセストークン削除_成功")
    void testDelete_normal1() throws Exception {
        // モックの作成
        String clientId = "2aa899fb";

        when(dipsAccessRepository.deleteByPrimaryKey(any(String.class))).thenReturn(1);

        // テスト対象呼び出しと結果の検証
        assertDoesNotThrow(() -> target.delete(clientId));
        verify(dipsAccessRepository, times(1)).deleteByPrimaryKey("2aa899fb");
    }

    /**
     * deleteメソッドのテスト
     * <p>
     * "異常系1：アクセストークン削除_DB削除時に例外発生"<br>
     * 設定条件1：DB削除時に例外が発生する<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessRepository - deleteByPrimaryKeyメソッドが正常処理を実行(対象データなし)
     * <li>検証1：モック化したメソッドが指定回数呼び出されること
     * <li>検証2：例外が発生しないこと
     * </ul>
     */
    @Test
    @DisplayName("異常系1：アクセストークン削除_DB削除時に例外発生")
    void testDelete_error1() throws Exception {
        // モックの作成
        String clientId = "2aa899fb";

        when(dipsAccessRepository.deleteByPrimaryKey(any(String.class))).thenThrow(new RuntimeException());

        // テスト対象呼び出し
        DipsAccessTokenException e = assertThrows(DipsAccessTokenException.class, () -> {
            target.delete(clientId);
        });

        // 結果の検証
        assertEquals("Token Delete Failed", e.getMessage());
        verify(dipsAccessRepository, times(1)).deleteByPrimaryKey("2aa899fb");
    }

    /**
     * isTokenValidメソッドのテスト
     * <p>
     * "正常系1：アクセストークン有効期限チェック_成功"<br>
     * 設定条件1：startTimeにexpiresInを足した時刻が、現在時刻から101s後の時刻<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：systemDate - 現在時刻をLocalDateTime型で返却
     * <li>検証1：targetからtrueが返却されること
     * </ul>
     */
    @Test
    @DisplayName("正常系1：アクセストークン有効期限チェック__成功")
    void testIsTokenValid_normal1() throws Exception {
        // モックの作成
        LocalDateTime startTime = LocalDateTime.of(2024, 12, 23, 10, 10, 30);
        Integer expiresIn = 300;
        // 現在時刻のモックとして時刻を設定
        when(systemDate.now()).thenReturn(LocalDateTime.of(2024, 12, 23, 10, 13, 49));

        Method method = DipsAccessService.class.getDeclaredMethod("isTokenValid", LocalDateTime.class, Integer.class);
        method.setAccessible(true);

        // テスト対象呼び出し
        boolean result = (boolean) method.invoke(target, startTime, expiresIn);

        // テスト対象呼び出しと結果の検証
        assertEquals(true, result);
    }

    /**
     * isTokenValidメソッドのテスト
     * <p>
     * "異常系1：アクセストークン有効期限チェック_失敗"<br>
     * 設定条件1：startTimeにexpiresInを足した時刻が、現在時刻から100s後の時刻<br>
     * <p>
     * <ul>
     * <li>モックオブジェクト1：systemDate - 現在時刻をLocalDateTime型で返却
     * <li>検証1：targetからfalseが返却されること
     * </ul>
     */
    @Test
    @DisplayName("異常系1：アクセストークン有効期限チェック__失敗")
    void testIsTokenValid_error1() throws Exception {
        // モックの作成
        LocalDateTime startTime = LocalDateTime.of(2024, 12, 23, 10, 10, 30);
        Integer expiresIn = 300;
        // 現在時刻のモックとして時刻を設定
        when(systemDate.now()).thenReturn(LocalDateTime.of(2024, 12, 23, 10, 13, 50));

        Method method = DipsAccessService.class.getDeclaredMethod("isTokenValid", LocalDateTime.class, Integer.class);
        method.setAccessible(true);

        // テスト対象呼び出し
        boolean result = (boolean) method.invoke(target, startTime, expiresIn);

        // テスト対象呼び出しと結果の検証
        assertEquals(false, result);
    }

    /**
     * DIPS API 実行時のテスト用リクエストオブジェクト
     */
    @Getter
    @Setter
    @Builder
    private static class TestRequest implements Serializable {

        /** SerialVersion */
        private static final long serialVersionUID = 1L;

        /** testRequestValue1 */
        private int testRequestValue1;

        /** testRequestValue2 */
        private String testRequestValue2;
    }

    /**
     * DIPS API 実行時のテスト用レスポンスオブジェクト
     */
    @Getter
    @Setter
    @Builder
    private static class TestResponse implements Serializable {

        /** SerialVersion */
        private static final long serialVersionUID = 1L;

        /** testResponseValue1 */
        private int testResponseValue1;

        /** testResponseValue2 */
        private String testResponseValue2;
    }

}
