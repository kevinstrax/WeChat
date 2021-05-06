package com.xk.core.bean;

/**
 * 用途：群成员结构
 *
 * @author xiaokui
 * @date 2017年1月3日
 */
public class MemberStruct {

	public Long Uin;
	public String UserName;
	public String NickName;
	public Long AttrStatus;
	public String PYInitial;
	public String PYQuanPin;
	public String RemarkPYInitial;
	public String RemarkPYQuanPin;
	public Integer MemberStatus;
	public String DisplayName;
	public String KeyWord;
	
	public MemberStruct() {
		
	}
	
	public MemberStruct(Long uin, String userName, String nickName, Long attrStatus, String pYInitial,
			String pYQuanPin, String remarkPYInitial, String remarkPYQuanPin, Integer memberStatus, String displayName,
			String keyWord) {
		super();
		Uin = uin;
		UserName = userName;
		NickName = nickName;
		AttrStatus = attrStatus;
		PYInitial = pYInitial;
		PYQuanPin = pYQuanPin;
		RemarkPYInitial = remarkPYInitial;
		RemarkPYQuanPin = remarkPYQuanPin;
		MemberStatus = memberStatus;
		DisplayName = displayName;
		KeyWord = keyWord;
	}
	
}
