package jp.go.meti.drone.api.auth.controller.model;

import java.io.Serializable;

import lombok.Data;

/**
 * DipsAccessResponseクラス.
 * 
 * @version $Revision$
 */
@Data
public class DipsAccessResponse implements Serializable {

    /** SerialVersion */
    private static final long serialVersionUID = 1L;

    /** メッセージ */
    private String message;

}
