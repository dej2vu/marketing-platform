package io.github.dej2vu.constant.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServerException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5317680961212299217L;

    /** 异常码 */
    private String code;

    /** 异常信息 */
    private String info;

    public ServerException(String code) {
        this.code = code;
    }

    public ServerException(String code, Throwable cause) {
        this.code = code;
        super.initCause(cause);
    }

    public ServerException(String code, String message) {
        this.code = code;
        this.info = message;
    }

    public ServerException(String code, String message, Throwable cause) {
        this.code = code;
        this.info = message;
        super.initCause(cause);
    }


    public void throwIf(){

    }

}
