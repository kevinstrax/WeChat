package com.xk.ui.swt.chatlogs;

import java.util.HashMap;
import java.util.Map;

import com.xk.ui.swt.chatlogs.interfaces.ChatLogChain;
import com.xk.ui.swt.chatlogs.interfaces.IChatLogChain;
import com.xk.core.constant.Constant;
import com.xk.ui.swt.core.utils.ImageCache;

/**
 * 处理添加好友
 * @author Administrator
 *
 */
public class AddFriendChain extends ChatLogChain {

	private IChatLogChain nextChain;
	
	@Override
	public ChatLog fromMap(ChatLog log, Map<String, Object> map) {
		if(log.msgType == 37) {
			log.recommendInfo = (Map<String, Object>) map.get("RecommendInfo");
			log.content = "请求添加好友";
			String userName = log.recommendInfo.get("UserName").toString();
			Map<String, String> params = new HashMap<String, String>();
			params.put("seq", "0");
			params.put("username", userName);
			params.put("msgid", log.msgid);
			params.put("skey", Constant.sign.skey);
			log.img = ImageCache
                    .getUserHeadCache(userName, String.format(Constant.GET_MEMBER_ICON, Constant.HOST), params);
		}
		return processReturn(log, map);
	}

	@Override
	public IChatLogChain getNext() {
		return nextChain;
	}

}
