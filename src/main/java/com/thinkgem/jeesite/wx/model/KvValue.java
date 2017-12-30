package com.thinkgem.jeesite.wx.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存数据
 * @author a
 *
 */
public class KvValue {

	private static Map<String,Object> wxUsers = new HashMap<String,Object>();
	
	
	
	public static Map<String, Object> getWxUsers() {
		return wxUsers;
	}

	public static boolean isExist(String key){
		return wxUsers.containsKey(key);
	}
	
	public static Object getUserInfo(String key){
		return wxUsers.get(key);
	}
	
	public static void wxUsersPutData(String key,Object value){
		wxUsers.put(key, value);
	}
	
	public static void wxUserRemoveData(String key){
		wxUsers.remove(key);
	}
	
}
