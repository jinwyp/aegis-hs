package com.yimei.hs.boot.exception;

/**
 * Created by hary on 2017/9/26.
 */
public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String message) {
        super(message);
    }
}
