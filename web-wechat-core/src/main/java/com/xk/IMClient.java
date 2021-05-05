package com.xk;

import com.xk.core.IMEventListener;

import java.io.InputStream;
import java.util.concurrent.Future;

/**
 * @description 一个 IM 客户端 interface
 * @author kevinstrax
 * @createDate 2021/5/6
 */
public interface IMClient extends Lifecycle {

    /**
     * 获取登录二维码
     * @return
     */
    Future<InputStream> getQrCode(IMEventListener<InputStream> listener);

    /**
     * 检查二维码状态
     * @return
     */
    void checkQrCode(IMEventListener<String> listener);


}
