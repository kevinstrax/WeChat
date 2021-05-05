package com.xk.core;

import java.util.Optional;

/**
 * @description
 * @author kivenstrax
 * @createDate 2021/5/6
 */
public class IMEventSupport {

    public static <T> void onActionEvent(IMEvent<T> event, IMEventListener<T> listener) {
        Optional.ofNullable(listener).ifPresent(l -> listener.onActionEvent(event));
    }

}
