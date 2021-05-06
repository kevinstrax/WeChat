package com.xk.core.exception;

/**
 * @description
 * @author kevinstrax
 * @createDate 2021/5/6
 */
public enum IMExceptionCode {

    CURRENT_NO_QR_CODE(30001, "当前未获取到验证码")

    ;

    private int code;
    private String msg;

    IMExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void throwException() {
        throw new IMException(code, msg);
    }

    public void throwException(String message) {
        throw new IMException(code, message);
    }

    public <T> void throwExceptionWhenNull(T t, String message) {
        if (t == null) {
            throw new IMException(code, message);
        }
    }

    public void throwExceptionWhenTrue(Boolean bool, String message) {
        if (bool) {
            throw new IMException(code, message);
        }
    }

    public <T> void throwExceptionWhenNull(T t) {
        if (t == null) {
            throw new IMException(code, msg);
        }
    }

    public void throwExceptionWhenTrue(Boolean bool) {
        if (bool) {
            throw new IMException(code, msg);
        }
    }
}
