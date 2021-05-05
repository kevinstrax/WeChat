package com.xk.bean;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xk.utils.JSONUtil;

/**
 * 用途：联系人数据
 *
 * @author xiaokui
 * @date 2016年12月30日
 */
public class ContactsStruct {
	public Long Uin;
	public String UserName;
	public String NickName;
	public String HeadImgUrl;
	public Integer ContactFlag;
	public Integer MemberCount;
	public List<MemberStruct> MemberList;
	public String RemarkName;
	public Integer HideInputBarFlag;
	public Integer Sex;
	public String Signature;
	public Integer VerifyFlag;
	public Long OwnerUin;
	public String PYInitial;
	public String PYQuanPin;
	public String RemarkPYInitial;
	public String RemarkPYQuanPin;
	public Integer StarFriend;
	public Integer AppAccountFlag;
	public Integer Statues;
	public Long AttrStatus;
	public String Province;
	public String City;
	public String Alias;
	public Integer SnsFlag;
	public Integer UniFriend;
	public String DisplayName;
	public Integer ChatRoomId;
	public String KeyWord;
	public String EncryChatRoomId;
	public String IsOwner;
	
	@JsonIgnore
	public Image head;
	
	public ContactsStruct(){
		
	}
	
	
	public ContactsStruct(Long uin, String userName, String nickName, String headImgUrl, Integer contactFlag,
			Integer memberCount, List<MemberStruct> memberList, String remarkName, Integer hideInputBarFlag,
			Integer sex, String signature, Integer verifyFlag, Long ownerUin, String pYInitial, String pYQuanPin,
			String remarkPYInitial, String remarkPYQuanPin, Integer starFriend, Integer appAccountFlag, Integer statues,
			Long attrStatus, String province, String city, String alias, Integer snsFlag, Integer uniFriend,
			String displayName, Integer chatRoomId, String keyWord, String encryChatRoomId) {
		super();
		Uin = uin;
		UserName = userName;
		NickName = nickName;
		HeadImgUrl = headImgUrl;
		ContactFlag = contactFlag;
		MemberCount = memberCount;
		MemberList = memberList;
		RemarkName = remarkName;
		HideInputBarFlag = hideInputBarFlag;
		Sex = sex;
		Signature = signature;
		VerifyFlag = verifyFlag;
		OwnerUin = ownerUin;
		PYInitial = pYInitial;
		PYQuanPin = pYQuanPin;
		RemarkPYInitial = remarkPYInitial;
		RemarkPYQuanPin = remarkPYQuanPin;
		StarFriend = starFriend;
		AppAccountFlag = appAccountFlag;
		Statues = statues;
		AttrStatus = attrStatus;
		Province = province;
		City = city;
		Alias = alias;
		SnsFlag = snsFlag;
		UniFriend = uniFriend;
		DisplayName = displayName;
		ChatRoomId = chatRoomId;
		KeyWord = keyWord;
		EncryChatRoomId = encryChatRoomId;
	}
	
	/**
	 * 补全缺失的部分属性，从服务器拉下来的数据有时候会缺少，需要再次填充
	 * 作者 ：肖逵
	 * 时间 ：2018年8月30日 上午9:28:50
	 * @param newInstance
	 */
	public void fixMissProps(ContactsStruct newInstance) {
		if(MemberCount == null || MemberCount == 0) {
			MemberCount = newInstance.MemberCount;
		}
		if(MemberList == null || MemberList.isEmpty()) {
			MemberList = newInstance.MemberList;
		}
		if(NickName == null || NickName.trim().isEmpty()) {
			NickName = newInstance.NickName;
		}
		if(RemarkName == null || NickName.trim().isEmpty()) {
			NickName = newInstance.NickName;
		}
	}
	
	/**
	 * 用途：微信服务器获取的数据
	 * @date 2016年12月30日
	 * @param map
	 * @return
	 */
	public static ContactsStruct fromMap(Map<String, Object> map) {
		String json = JSONUtil.toJson(map);
		ContactsStruct conv = JSONUtil.toBean(json, ContactsStruct.class);
		return conv;
	}
	
	
	/**
	 * 用途：获取指定群成员显示名称
	 * @date 2017年1月3日
	 * @param user
	 * @param con
	 * @return
	 */
	public static String getGroupMember(String user, ContactsStruct con) {
		String name = "匿名";
		if(null != con && con.UserName.startsWith("@@")) {
			for(MemberStruct member : con.MemberList) {
				if(user.equals(member.UserName)) {
					if(null != member.DisplayName && !member.DisplayName.isEmpty()) {
						name = member.DisplayName.trim();
					}else {
						name = member.NickName;
					}
					break;
				}
			}
		}
		return name;
	}
	
	/**
	 * 没有群名的群自己造名字
	 * 作者 ：肖逵
	 * 时间 ：2018年8月30日 上午9:30:46
	 * @param cs
	 * @return
	 */
	public static String getContactsStructName(ContactsStruct cs) {
		String nick = cs.NickName;
		String remark = cs.RemarkName;
		String name = (null == remark || remark.trim().isEmpty()) ? nick : remark; 
		if((name == null || name.trim().isEmpty()) && cs.MemberCount > 0) {
			StringBuffer sb = new StringBuffer();
			int count = 3;
			for(int i = 0; i < cs.MemberCount; i++) {
				MemberStruct memb =cs.MemberList.get(i);
				sb.append(memb.DisplayName.isEmpty() ? memb.NickName : memb.DisplayName);
				if(count > 0 && i < cs.MemberCount - 1) {
					count--;
					sb.append("、");
				}else {
					break;
				}
			}
			name = sb.toString();
		}
		return name;
	}
	
	/**
	 * 用途：获取显示名
	 * @date 2017年1月3日
	 * @param con
	 * @return
	 */
	public static String getContactName(ContactsStruct con) {
		String name = "匿名";
		if(null == con) {
			return name;
		}
		if (null != con.RemarkName && !con.RemarkName.trim().isEmpty()) {
			name = con.RemarkName;
		} else {
			name = con.NickName;
		}
		return name;
	}
}
