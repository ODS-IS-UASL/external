package jp.go.meti.drone.api.auth.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import jp.go.meti.drone.api.auth.controller.model.DipsAccessResponse;
import jp.go.meti.drone.api.auth.controller.model.DipsTokenRequest;
import jp.go.meti.drone.api.auth.repository.entity.DipsAccessEntity;
import jp.go.meti.drone.api.auth.service.DipsAccessService;

/**
 * DIPSアクセスコントローラーテストクラス.
 * 
 * @version $Revision$
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DipsAccessControllerTest {

    /** テスト対象クラス */
    @InjectMocks
    private DipsAccessController target;

    /** DipsAccessServiceモック */
    @Mock
    private DipsAccessService dipsAccessService;

    /** ドローン航路のアクセストークンサンプル */
    private static final String DIPS_TOKEN_SAMPLE = "eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0.eyJvcGVyYXRvcl9pZCI6ImIzOWU2MjQ4LWM4ODgtNTZjYS1kOWQwLTg5ZGUxYjFhZGM4ZSIsImVtYWlsIjoib2VtX2FAZXhhbXBsZS5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImF1dGhfdGltZSI6MTcxODE1NDQyOSwidXNlcl9pZCI6IjJhYTg5OWZiLTkyMGEtNDc5Zi05Yzk4LTZkZmY5NDNkYmYzMSIsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsib2VtX2FAZXhhbXBsZS5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9LCJpYXQiOjE3MTgxNTQ0MjksImV4cCI6MTcxODE1ODAyOSwiYXVkIjoibG9jYWwiLCJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbG9jYWwiLCJzdWIiOiIyYWE4OTlmYi05MjBhLTQ3OWYtOWM5OC02ZGZmOTQzZGJmMzEifQ.";

    /**
     * 前処理
     * <p>
     * 環境変数の値を設定する<br>
     * </p>
     */
    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(target, "defaultClientId", "2aa899fb");
    }

    /**
     * dipsAccessVerifyメソッドのテスト
     * <p>
     * 正常系1:アクセストークン検証<br>
     * 設定条件1:リクエストボディ送信なし<br>
     * </p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessService - tokenVerifyメソッドが正常処理を実行
     * <li>検証1：呼び出したAPIが200 OKステータスかつ成功メッセージを持つボディを返すこと
     * <li>検証2：事前設定した環境変数の事業者IDでトークン検証メソッドが実行されること
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("アクセストークン検証成功_デフォルトの事業者IDを取得して検証")
    void testDipsAccessVerify_normal1() throws Exception {
        ArgumentCaptor<String> clientId = ArgumentCaptor.forClass(String.class);

        // モックの作成
        doNothing().when(dipsAccessService).tokenVerify(clientId.capture());

        DipsAccessResponse expectedResponse = new DipsAccessResponse();
        expectedResponse.setMessage("Token Verify Successfully");

        // テスト対象呼び出し
        // 結果の検証
        ResponseEntity<DipsAccessResponse> actual = target.dipsAccessVerify();

        ResponseEntity<DipsAccessResponse> expected = new ResponseEntity<DipsAccessResponse>(
            expectedResponse, HttpStatus.OK);

        assertEquals(expected, actual);
        assertEquals("2aa899fb", clientId.getValue());
    }

    /**
     * dipsTokenSaveメソッドのテスト
     * <p>
     * 正常系2:アクセストークン保存<br>
     * 設定条件1:なし<br>
     * </p>
     * <ul>
     * <li>モックオブジェクト1：DipsAccessService - saveメソッドが正常処理を実行
     * <li>検証1：呼び出したAPIが200 OKステータスかつ成功メッセージを持つボディを返すこと
     * <li>検証2：アクセストークン保存情報(DipsTokenRequest)がトークン保存の実メソッドの保存情報と同じであること
     * </ul>
     * 
     * @throws Exception 例外
     */
    @Test
    @DisplayName("アクセストークン保存成功")
    void testDipsTokenSave() throws Exception {
        ArgumentCaptor<DipsAccessEntity> insertTokenInfo = ArgumentCaptor.forClass(DipsAccessEntity.class);

        DipsTokenRequest request = new DipsTokenRequest();
        request.setClientId("2aa899fb");
        request.setAccessToken(DIPS_TOKEN_SAMPLE);
        request.setExpiresIn(10);
        request.setRefreshExpiresIn(600);
        request.setRefreshToken("refreshToken");
        request.setTokenType("bearer");
        request.setIdToken("idToken");
        request.setNotBeforePolicy(0);
        request.setSessionState("sessionState");
        request.setScope("openid profile offline");

        // モックの作成
        doNothing().when(dipsAccessService).save(insertTokenInfo.capture());

        DipsAccessResponse expectedResponse = new DipsAccessResponse();
        expectedResponse.setMessage("Token Save Successfully");

        // テスト対象呼び出し
        // 結果の検証
        ResponseEntity<DipsAccessResponse> actual = target.dipsTokenSave(request);

        ResponseEntity<DipsAccessResponse> expected = new ResponseEntity<DipsAccessResponse>(
            expectedResponse, HttpStatus.OK);

        assertEquals(expected, actual);
        assertEquals(request.getClientId(), insertTokenInfo.getValue().getClientId());
        assertEquals(request.getAccessToken(), insertTokenInfo.getValue().getAccessToken());
        assertEquals(request.getExpiresIn(), insertTokenInfo.getValue().getExpiresIn());
        assertEquals(request.getRefreshExpiresIn(), insertTokenInfo.getValue().getRefreshExpiresIn());
        assertEquals(request.getRefreshToken(), insertTokenInfo.getValue().getRefreshToken());
        assertEquals(request.getTokenType(), insertTokenInfo.getValue().getTokenType());
        assertEquals(request.getRefreshToken(), insertTokenInfo.getValue().getRefreshToken());
        assertEquals(request.getIdToken(), insertTokenInfo.getValue().getIdToken());
        assertEquals(request.getNotBeforePolicy(), insertTokenInfo.getValue().getNotBeforePolicy());
        assertEquals(request.getSessionState(), insertTokenInfo.getValue().getSessionState());
        assertEquals(request.getScope(), insertTokenInfo.getValue().getScope());

    }
}
