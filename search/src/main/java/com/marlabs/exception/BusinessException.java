package com.marlabs.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends Exception {

    private static final long serialVersionUID = -3255537269611230349L;
    
    private String errorCode;
    private String errorMsg;
    private Throwable cause;
    

    public BusinessException(Throwable cause) {
        this.cause = cause;
        this.errorMsg = cause.getMessage();
    }

    public BusinessException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    
    public BusinessException(String errorCode, String errorMsg, Throwable cause) {
        this.cause = cause;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
