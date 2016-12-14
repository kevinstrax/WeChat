package com.xk.utils;

import java.util.Arrays;
import java.util.List;

public class Constant {
	
	public static final String STATUS_NOTIFY = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxstatusnotify";
	public static final String GET_STATUS = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsync";
	public static final String SYNC_CHECK = "https://webpush.wx.qq.com/cgi-bin/mmwebwx-bin/synccheck";
	public static final String GET_CONTACT = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact";
	public static final String GET_GROUPS = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxbatchgetcontact?type=ex&r={TIME}&pass_ticket={TICKET}";
	public static final String GET_CONV_ID = "https://login.weixin.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_={TIME}";
	public static final String GET_QR_IMG = "https://login.weixin.qq.com/qrcode/{UUID}?t=webwx";
	public static final String GET_STATUE = "https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login?uuid={UUID}&tip=1&_={TIME}";
	public static final String GET_INIT = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r={TIME}";
	public static final String BASE_URL = "https://wx.qq.com/";
	
	// 特殊用户 须过滤
	public static final List<String> FILTER_USERS = Arrays.asList("newsapp", "fmessage", "filehelper", "weibo", "qqmail", 
			"fmessage", "tmessage", "qmessage", "qqsync", "floatbottle", "lbsapp", "shakeapp", "medianote", "qqfriend", 
			"readerapp", "blogapp", "facebookapp", "masssendapp", "meishiapp", "feedsapp", "voip", "blogappweixin", 
			"weixin", "brandsessionholder", "weixinreminder", "wxid_novlwrv3lqwv11", "gh_22b87fa7cb3c", "officialaccounts",
			"notification_messages", "wxid_novlwrv3lqwv11", "gh_22b87fa7cb3c", "wxitil", "userexperience_alarm", 
			"notification_messages");
}