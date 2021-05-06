package com.xk.core.exception;

/**
 * @description
 * @author kevinstrax
 * @createDate 2021/5/6
 */
public class IMException extends RuntimeException {

    public IMException(int code, String message) {
        super(code + ": " + message);
    }
}
