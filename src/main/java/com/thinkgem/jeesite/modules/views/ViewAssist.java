package com.thinkgem.jeesite.modules.views;

import com.thinkgem.jeesite.modules.assist.entity.Assist;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;

public class ViewAssist {

	private String applydate;		// applydate
	private String applyname;		// applyname
	private String applyreason;		// applyreason
	private String assiststate;		// assiststate
	private Integer staticflag;		// staticflag
	private Integer version;		// version
	private Long userinfoId;		// userinfo_id
	private Userinfo userInfo;//会员信息
	
	
	//转换到视图数据
	public void convertToViewData(Assist assist) {
		this.applydate = assist.getApplyDate();
		this.applyname = assist.getApplyName();
		this.applyreason = assist.getApplyReason();
		this.assiststate = String.valueOf(assist.getAssistState());
		this.userInfo = assist.getUserInfo();
	}
	
	
	public Userinfo getUserInfo() {
		return userInfo;
	}



	public void setUserInfo(Userinfo userInfo) {
		this.userInfo = userInfo;
	}



	public String getApplydate() {
		return applydate;
	}
	public void setApplydate(String applydate) {
		this.applydate = applydate;
	}
	public String getApplyname() {
		return applyname;
	}
	public void setApplyname(String applyname) {
		this.applyname = applyname;
	}
	public String getApplyreason() {
		return applyreason;
	}
	public void setApplyreason(String applyreason) {
		this.applyreason = applyreason;
	}
	public String getAssiststate() {
		return assiststate;
	}
	public void setAssiststate(String assiststate) {
		this.assiststate = assiststate;
	}
	public Integer getStaticflag() {
		return staticflag;
	}
	public void setStaticflag(Integer staticflag) {
		this.staticflag = staticflag;
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
	
}
