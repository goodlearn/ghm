package com.thinkgem.jeesite.wx.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.thinkgem.jeesite.common.utils.CasUtils;


/**
 * 微信小程序返回数据
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxServerBackCode implements Serializable {
	
	private static final long serialVersionUID = -7658944012975580243L;

	
	/**
	 * 用户唯一标识
	 */
	private String openid;

	
	/**
	 * 会话密钥
	 */
	private String session_key;
	
	/**
	 * 过期时间
	 * @return
	 */
	private String expires_in = "1000000";
	
	/**
	 * 身份证ID
	 * @return
	 */
	private String idCard;
	
	/**
	 * 生成时间
	 * @return
	 */
	private Date recordDate;
	
	/**
	 * 保存的Storage
	 */
	private String storage;
	
	/**
	 * 名字
	 */
	private String name;
	
	private String req_id;
	
	
	/**
	 * 对象是否包含唯一的openid
	 */
	public WxServerBackCode isSameOpenId(Map<String,Object> wxUsers){
		if(null == openid||null == wxUsers){
			return null;//没有包含
		}
		Set<String> keies = wxUsers.keySet();
		Iterator<String> its =  keies.iterator();
		while(its.hasNext()){
			String it = its.next();
			Object obj = wxUsers.get(it);
			if(obj instanceof WxServerBackCode){
				WxServerBackCode wxServerBackCode = (WxServerBackCode)obj;
				String wxOpenId = wxServerBackCode.getOpenid();
				if(this.getOpenid().equals(wxOpenId)){
					//过期判断
					if(wxServerBackCode.isOverTime()){
						//过期了
						return null;//没有包含
					}
					return wxServerBackCode;//包含
				}
			}
		}
		return null;//没有包含
	}
	
	/**
	 * 时间是否过期
	 * @return
	 */
	public boolean isOverTime(){
		//数据为空
		if(null == recordDate||null == expires_in){
			return true;//过期了
		}
		int result = 0;
		long recordDateMils = recordDate.getTime();
		recordDateMils = recordDateMils / 1000;//转换成秒
		long expiresInMils = Long.valueOf(expires_in);//加上过期时间
		expiresInMils = expiresInMils + recordDateMils;
		Date expireDate = new Date(expiresInMils);//得到时间
		Date now = new Date();
		try {
			result = CasUtils.compareTime(now,expireDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result<0){
			return true;//过期了
		}
		return false;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public Date getRecordDate() {
		return recordDate;
	}


	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}


	public String getIdCard() {
		return idCard;
	}


	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}


	public String getExpires_in() {
		return expires_in;
	}


	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}


	public String getOpenid() {
		return openid;
	}


	public void setOpenid(String openid) {
		this.openid = openid;
	}


	public String getSession_key() {
		return session_key;
	}


	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}


	@Override
	public String toString() {
		return "WxServerBackCode [openid=" + openid + ", session_key=" + session_key + ", expires_in=" + expires_in
				+ ", idCard=" + idCard + "]";
	}

	public String getReq_id() {
		return req_id;
	}

	public void setReq_id(String req_id) {
		this.req_id = req_id;
	}


	

	
	
	
}
