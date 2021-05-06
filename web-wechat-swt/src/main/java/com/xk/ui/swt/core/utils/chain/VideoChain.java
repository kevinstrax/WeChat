package com.xk.ui.swt.core.utils.chain;

import java.util.Map;

import com.xk.ui.swt.chatlogs.ChatLog;
import com.xk.ui.swt.chatlogs.interfaces.ChatLogChain;
import com.xk.ui.swt.chatlogs.interfaces.IChatLogChain;
import com.xk.core.constant.Constant;
import com.xk.core.utils.WechatUtil;
import com.xk.ui.swt.core.utils.WeChatUtil;

public class VideoChain extends ChatLogChain {

	
	private IChatLogChain next = new VoiceChain();
	@Override
	public ChatLog fromMap(ChatLog log, Map<String, Object> msg) {
		Integer MsgType = (Integer) msg.get("MsgType");
		if( 43 == MsgType) {
			String FromUserName = (String) msg.get("FromUserName");
			WeChatUtil.statusNotify(Constant.user.UserName, FromUserName);
		}
		return processReturn(log, msg);
	}

	@Override
	public IChatLogChain getNext() {
		// TODO Auto-generated method stub
		return next;
	}

}
