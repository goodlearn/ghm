package com.thinkgem.jeesite.modules.assist.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	private Date applyDate;	
	
	/**帮扶状态**/
	private Integer assistState = 0;
	private Boolean staticFlag;		// staticflag
	private Integer version;		// version
	private Long userinfoId;		// userinfo_id
	private Userinfo userInfo;//帮扶对象
	private String queryCnk;//社区查询
	private Date beginApplydate;
	private Date endApplydate;
	private String noSlashShow;//无帮扶附件
	
	
	public String getQueryCnk() {
		return queryCnk;
	}


	public void setQueryCnk(String queryCnk) {
		this.queryCnk = queryCnk;
	}


	public Assist() {
		super();
	}
	

	public String getNoSlashShow() {
		return noSlashShow;
	}



	public void setNoSlashShow(String noSlashShow) {
		this.noSlashShow = noSlashShow;
	}



	public Assist(String id){
		super(id);
	}

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
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

	public Date getBeginApplydate() {
		return beginApplydate;
	}

	public void setBeginApplydate(Date beginApplydate) {
		this.beginApplydate = beginApplydate;
	}

	public Date getEndApplydate() {
		return endApplydate;
	}

	public void setEndApplydate(Date endApplydate) {
		this.endApplydate = endApplydate;
	}

	
}