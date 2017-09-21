package com.yimei.hs.boot.exception;

/**
 * Created by hary on 2017/9/21.
 */
public class BusinessException extends RuntimeException {

    private String message;

    public BusinessException(String message) {
        this.message = message;
    }

}
