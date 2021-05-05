package com.xk;

import com.xk.bean.WeChatSign;
import com.xk.core.IMEvent;
import com.xk.core.IMEventListener;
import com.xk.core.IMEventSupport;
import com.xk.exception.IMExceptionCode;
import com.xk.utils.AsyncHandleUtil;
import com.xk.utils.Constant;
import com.xk.utils.HTTPUtil;
import com.xk.utils.XMLUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description 基于 web 协议的 wechat IM 实现
 * @author kivenstrax
 * @createDate 2021/5/6
 */
public class WebWechatClient implements IMClient {

    private static final Log LOG = LogFactory.getLog(WebWechatClient.class);

    private boolean genQrCode = false;

    private String uuid = "";

    public static WeChatSign sign = new WeChatSign();

    @Override
    public Future<InputStream> getQrCode(IMEventListener<InputStream> listener) {
        return AsyncHandleUtil.handle(() -> {
            String url = Constant.GET_CONV_ID.replace("{TIME}", System.currentTimeMillis() + "");
            HTTPUtil cl = HTTPUtil.getInstance();
            try {
                String result = cl.readJsonfromURL2(url, null);
                String sign = "window.QRLogin.code = 200; window.QRLogin.uuid = ";
                if (null != result && result.contains(sign)) {
                    uuid = result.replace(sign, "").replace("\"", "").replace(";", "");
                    String qrUrl = Constant.GET_QR_IMG.replace("{UUID}", uuid);
                    InputStream in = cl.getInput(qrUrl);
                    genQrCode = true;
                    IMEventSupport.onActionEvent(new IMEvent<>(IMEvent.Type.EVT_OK, in), listener);
                    return in;
                }
            } catch (IOException e) {
                LOG.error("[Error] getQrCode error, ", e);
            }
            IMEventSupport.onActionEvent(new IMEvent<>(IMEvent.Type.EVT_ERROR, null), listener);
            return null;
        });
    }

    @Override
    public void checkQrCode(IMEventListener<String> listener) {
        if (!genQrCode) {
            IMExceptionCode.CURRENT_NO_QR_CODE.throwException();
        }

        final String url = Constant.GET_STATUE.replace("{TIME}", System.currentTimeMillis() + "")
                .replace("{UUID}", uuid);
        ScheduledExecutorService timer = new ScheduledThreadPoolExecutor(1);
        final HTTPUtil hu = HTTPUtil.getInstance();
        timer.scheduleWithFixedDelay(() -> {
            try {
                String result = hu.readJsonfromURL2(url, null);
                LOG.info("获取状态: " + result);
                if (null != result) {
                    if (result.contains("window.code=408;")) {
                        IMEventSupport.onActionEvent(new IMEvent<>(IMEvent.Type.EVT_WAITING_SCAN, result), listener);
                    } else if (result.contains("window.code=201;")) {
                        IMEventSupport.onActionEvent(new IMEvent<>(IMEvent.Type.EVT_WAITING_CONFIRM, result), listener);
                    } else if (result.contains("window.code=200;window.redirect_uri=")) {
                        timer.shutdownNow();
                        result = result.replace("window.code=200;window.redirect_uri=", "").replace("\"", "");
                        IMEventSupport.onActionEvent(new IMEvent<>(IMEvent.Type.EVT_WAITING_LOGIN, result), listener);
                        for (String host : Constant.HOSTS) {
                            if (result.contains(host)) {
                                Constant.HOST = host;
                                break;
                            }
                        }
                        String winAndsid = hu.readJsonfromURL2(result, null);
                        LOG.info("获取wxsid和wxuin: " + winAndsid);
                        if (null != winAndsid) {
                            Document doc = XMLUtils.fromText(winAndsid);
                            if (null != doc) {
                                Element root = doc.getRootElement();
                                sign.pass_ticket = root.elementTextTrim("pass_ticket");
                                sign.skey = root.elementTextTrim("skey");
                                sign.wxsid = root.elementTextTrim("wxsid");
                                sign.wxuin = root.elementTextTrim("wxuin");
                                Constant.sign = sign;
                                IMEventSupport
                                        .onActionEvent(new IMEvent<>(IMEvent.Type.EVT_LOGIN_SUCCESS, result), listener);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                LOG.error("[Error] checkQrCode failed, ", e);
            }
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }


    @Override
    public void init() {
        System.out.println("init...");
    }

    @Override
    public void destroy() {
        System.out.println("destroy...");
        System.exit(-1);
    }
}
