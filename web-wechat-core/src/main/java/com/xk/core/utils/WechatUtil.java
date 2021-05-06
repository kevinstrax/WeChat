package com.xk.core.utils;

import com.xk.common.utils.HTTPUtil;
import com.xk.common.utils.JSONUtil;
import com.xk.core.bean.ContactsStruct;
import com.xk.core.constant.Constant;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kivenstrax
 */
public class WechatUtil {
    /**
     * 用途：抓取群组
     * @date 2016年12月14日
     * @param groups
     */
    public static void loadGroups(List<String> groups) {
        if(null == groups || groups.size() == 0) {
            return ;
        }

        List<Map<String, String>> gs = new ArrayList<>();
        for(String name : groups) {
            Map<String, String> map = new HashMap<>();
            map.put("UserName", name);
            map.put("ChatRoomId", "");
            gs.add(map);
        }

        HTTPUtil hu = HTTPUtil.getInstance();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        Map<String,String> bodyInner = new HashMap<String,String>();
        bodyInner.put("Uin", Constant.sign.wxuin);
        bodyInner.put("Sid", Constant.sign.wxsid);
        bodyInner.put("Skey", Constant.sign.skey);
        bodyInner.put("DeviceID", Constant.sign.deviceid);
        bodyMap.put("BaseRequest", bodyInner);
        bodyMap.put("Count", gs.size());
        bodyMap.put("List", gs);

        try {
            String url = String.format(Constant.GET_GROUPS, Constant.HOST).replace("{TIME}", System.currentTimeMillis() + "").replace("{TICKET}", Constant.sign.pass_ticket);
            String result = hu.postBody(url, JSONUtil.toJson(bodyMap));
            Map<String, Object> rstMap = JSONUtil.fromJson(result);
            Map<String, Object> baseResponse = (Map<String, Object>) rstMap.get("BaseResponse");
            if(null != baseResponse && new Integer(0).equals(baseResponse.get("Ret"))) {
                List<Map<String, Object>> contactList = (List<Map<String, Object>>) rstMap.get("ContactList");
                if(null != contactList) {
                    for(Map<String, Object> cmap : contactList) {
                        ContactsStruct convs = ContactsStruct.fromMap(cmap);
                        if(Constant.contacts.containsKey(convs.UserName)) {
                            ContactsStruct old = Constant.contacts.get(convs.UserName);
                            System.out.println(convs.UserName + " " + convs.NickName + " " + convs.RemarkName);
                            old.fixMissProps(convs);
                        }else {
                            Constant.contacts.put(convs.UserName, convs);
                        }
                        //						String headUrl = Constant.BASE_URL + convs.HeadImgUrl;
                        //						convs.head = ImageCache.getUserHeadCache(convs.UserName, headUrl, null, 50, 50);
                    }
                    System.out.println("load Group over!!");
                }
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
