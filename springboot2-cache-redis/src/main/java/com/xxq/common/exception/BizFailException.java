package com.xxq.common.exception;

import lombok.Getter;

@Getter
public class BizFailException extends RuntimeException {

    private String errorCode;

    public BizFailException(String errorCode,String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public BizFailException(String errorCode,String errorMessage,Throwable cause){
        super(errorMessage,cause);
        this.errorCode = errorCode;
    }

}
