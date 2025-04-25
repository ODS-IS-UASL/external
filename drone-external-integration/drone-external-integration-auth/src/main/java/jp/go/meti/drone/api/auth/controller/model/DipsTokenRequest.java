package jp.go.meti.drone.api.auth.controller.model;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DipsTokenRequestクラス.
 * 
 * @version $Revision$
 */
@Data
public class DipsTokenRequest implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /** クライアントID */
    @NotBlank
    private String clientId;

    /** DIPSアクセストークン */
    @NotBlank
    private String accessToken;

    /** DIPSアクセストークンの有効時間(秒) */
    @NotNull
    private Integer expiresIn;

    /** リフレッシュトークンの有効時間(秒) */
    @NotNull
    private Integer refreshExpiresIn;

    /** DIPSリフレッシュトークン */
    @NotBlank
    private String refreshToken;

    /** token_type */
    @NotBlank
    private String tokenType;

    /** IDトークン */
    @NotBlank
    private String idToken;

    /** アクセストークンの有効性確認のための値 */
    @NotNull
    private Integer notBeforePolicy;

    /** セッション状態 */
    @NotBlank
    private String sessionState;

    /** スコープ */
    @NotBlank
    private String scope;

}
