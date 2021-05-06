package com.xk.core.event;

/**
 * @description
 * @author kivenstrax
 * @createDate 2021/5/6
 */
public class IMEvent<T> {
    private Type type;
    private T data;

    public IMEvent(Type type, T data) {
        this.type = type;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        EVT_OK,
        EVT_ERROR,

        EVT_WAITING_LOGIN,
        EVT_WAITING_SCAN,
        EVT_WAITING_CONFIRM,
        EVT_LOGIN_SUCCESS
    }

    @Override
    public String toString() {
        return "IMEvent{" + "data=" + data + ", type=" + type + '}';
    }
}
