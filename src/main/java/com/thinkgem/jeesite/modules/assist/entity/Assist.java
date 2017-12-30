/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assist.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;

/**
 * assistEntity
 * @author wzy
 * @version 2017-09-20
 */
public class Assist extends DataEntity<Assist>{
	
	private static final long serialVersionUID = 1L;
	/**申请人**/
	private String applyName;
	
	/**申请理由**/
	private String applyReason;
	
	/**申请时间**/
	private String applyDate;	
	
	/**帮扶状态**/
	private Integer assistState = 0;
	private Boolean staticFlag;		// staticflag
	private Integer version;		// version
	private Long userinfoId;		// userinfo_id
	private Userinfo userInfo;//帮扶对象
	
	public Assist() {
		super();
	}

	public Assist(String id){
		super(id);
	}

	@Length(min=0, max=255, message="applydate长度必须介于 0 和 255 之间")
	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applydate) {
		this.applyDate = applydate;
	}
	
	@Length(min=0, max=255, message="applyname长度必须介于 0 和 255 之间")
	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyname) {
		this.applyName = applyname;
	}
	
	
	
	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	@Length(min=0, max=255, message="applyreason长度必须介于 0 和 255 之间")
	public String getApplyReason() {
		return applyReason;
	}

	public Integer getAssistState() {
		return assistState;
	}

	public void setAssistState(Integer assistState) {
		this.assistState = assistState;
	}

	public Boolean getStaticFlag() {
		return staticFlag;
	}

	public void setStaticFlag(Boolean staticflag) {
		this.staticFlag = staticflag;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Long getUserinfoId() {
		return userinfoId;
	}

	public void setUserinfoId(Long userinfoId) {
		this.userinfoId = userinfoId;
	}

	public Userinfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Userinfo userInfo) {
		this.userInfo = userInfo;
	}

}