/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ce.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * ceEntity
 * @author wzy
 * @version 2017-09-20
 */
public class Communityemail extends DataEntity<Communityemail> {
	
	private static final long serialVersionUID = 1L;
	private String communityName;		// communityname
	private String email;		// email
	private String serial;		// serial
	/**
	 * 模板对象标识。
	 */
	private Boolean staticFlag;
	
	private Integer version;
	
	public Communityemail() {
		super();
	}

	public Communityemail(String id){
		super(id);
	}

	@Length(min=0, max=255, message="communityname长度必须介于 0 和 255 之间")
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityname) {
		this.communityName = communityname;
	}
	
	@Length(min=0, max=255, message="email长度必须介于 0 和 255 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=255, message="serial长度必须介于 0 和 255 之间")
	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Boolean getStaticFlag() {
		return staticFlag;
	}

	public void setStaticFlag(Boolean staticFlag) {
		this.staticFlag = staticFlag;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
	
}