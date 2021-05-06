package com.xk.core.event;

/**
 * @description
 * @author kivenstrax
 * @createDate 2021/5/6
 */
public interface IMEventListener<T> {
    void onActionEvent(IMEvent<T> event);
}
