package com.yimei.hs.boot.exception;

/**
 * Created by xiangyang on 2017/7/2.
 */
public class NoJwtTokenException extends RuntimeException {

    public NoJwtTokenException(String msg) {
        super(msg);
    }
    public NoJwtTokenException(String msg, Throwable e) {
        super(msg, e);
    }
}
