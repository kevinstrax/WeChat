package com.xk.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @description
 * @author kivenstrax
 * @createDate 2021/5/6
 */
public class AsyncHandleUtil {

    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    public static <V> Future<V> handle(Callable<V> task) {
        return EXECUTOR.submit(task);
    }

}
