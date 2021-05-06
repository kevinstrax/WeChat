package com.xk.ui.swt.core.utils.chain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.xk.core.bean.ContactsStruct;
import com.xk.ui.swt.chatlogs.ChatLog;
import com.xk.ui.swt.chatlogs.ChatLogCache;
import com.xk.ui.swt.chatlogs.interfaces.ChatLogChain;
import com.xk.ui.swt.chatlogs.interfaces.IChatLogChain;
import com.xk.core.constant.Constant;
import com.xk.core.utils.WechatUtil;
import com.xk.ui.swt.core.utils.WeChatUtil;
import com.xk.ui.swt.main.MainWindow;

public class MessageChain extends ChatLogChain {
	public IChatLogChain next = new VideoChain();
	public static List<Integer> normalMsg = Arrays.asList(new Integer[]{1,3,47,49,37,43,34});

	@Override
	public ChatLog fromMap(ChatLog log, Map<String, Object> msg) {
		Integer MsgType = (Integer) msg.get("MsgType");
		if(normalMsg.contains(MsgType)) {
			String FromUserName = (String) msg.get("FromUserName");
			String ToUserName = (String) msg.get("ToUserName");
			String Content = (String) msg.get("Content");
			MainWindow main = MainWindow.getInstance();
			if(Constant.FILTER_USERS.contains(FromUserName)) {
				System.out.println("忽略特殊用户信息！！" + Content);
			}else if(FromUserName.equals(Constant.user.UserName)){
				if(null != log) {
					ChatLogCache.saveLogs(ToUserName, log);
					main.flushChatView(ToUserName, false );
					System.out.println("来自手机端自己的消息：" + Content);
				}
				
			}else if(FromUserName.startsWith("@@")) {
				if(null != log) {
					ChatLogCache.saveLogs(FromUserName, log);
					int start = Content.indexOf(":<br/>");
					if(start > 0) {
						String name = Content.substring(0, start);
						String ctt = Content.substring(start + ":<br/>".length(), Content.length());
						String sender = ContactsStruct.getGroupMember(name, Constant.getContact(FromUserName));
						if(!Constant.noReply.contains(FromUserName) && !Constant.globalSilence) {
							if(ctt.contains("@" + Constant.user.NickName)) {
								String detail = ctt.replace("@" + Constant.user.NickName, "");
								String reply = "什么情况?";
								if(!detail.trim().isEmpty()) {
									WeChatUtil.addRandomReply(reply, FromUserName, name);
								}
							}
						}
					}
					main.flushChatView(FromUserName, true);
				}
				
				
			}else {
				if(null != log) {
					ChatLogCache.saveLogs(FromUserName, log);
					String sender = ContactsStruct.getContactName(Constant.getContact(FromUserName));
					String ctt = Content.replace("<br/>", "\n");
					System.out.println(sender + " 说：" + ctt);
					if(log.msgType == 1 && !Constant.noReply.contains(FromUserName) && !Constant.globalSilence) {
                        WeChatUtil.addRandomReply(ctt, FromUserName, FromUserName);
					}
					main.flushChatView(FromUserName, true);
				}
				
			}
		}
		return processReturn(log, msg);
	}

	@Override
	public IChatLogChain getNext() {
		return next;
	}

}
