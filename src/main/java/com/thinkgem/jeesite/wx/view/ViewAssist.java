package com.thinkgem.jeesite.wx.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.assist.entity.Assist;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;


/**
 * 帮扶
 * @author wzy
 *
 */
public class ViewAssist{
	
	private static final long serialVersionUID = -8033915728409510247L;

	
	
	/**申请人**/
	private String applyName;
	
	/**申请理由**/
	private String applyReason;
	
	/**申请时间**/
	private String applyDate;	
	
	/**
	 * 所属会员
	 */
	private String idCard;
	
	/**
	 * 上传图片
	 */
	private List<File> files;
	
	/**
	 * 帮扶状态
	 */
	private int assistStateValue;
	
	/**
	 * 模板对象标识。
	 */
	private Boolean staticFlag;
	
	/**
	 * 标示值
	 */
	private Integer code;
	
	/**
	 * 数据转换
	 * @param userInfo
	 */
	public Assist convertToDb(Userinfo userInfo){
		Assist assist = new Assist();
		assist.setId(String.valueOf(IdGen.randomLong()));
		assist.setStaticFlag(true);
		assist.setApplyName(applyName);
		assist.setApplyReason(applyReason);
		assist.setApplyDate(applyDate);
		assist.setAssistState(assistStateValue);
		assist.setUserinfoId(Long.valueOf(userInfo.getId()));
		assist.setUserInfo(userInfo);
		return assist;
	}
	
	/**
	 * 数据转换
	 * @param userInfo
	 */
	public void convertToModel(Assist assist){
		applyName = assist.getApplyName();
		applyReason = assist.getApplyReason();
		applyDate = assist.getApplyDate();
		assistStateValue = assist.getAssistState();
	}

	public String getApplyName() {
		return applyName;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	

	public int getAssistStateValue() {
		return assistStateValue;
	}

	public void setAssistStateValue(int assistStateValue) {
		this.assistStateValue = assistStateValue;
	}

	public Boolean getStaticFlag() {
		return staticFlag;
	}

	public void setStaticFlag(Boolean staticFlag) {
		this.staticFlag = staticFlag;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public void addFile(File file){
		if(null == files){
			files = new ArrayList<File>();
		}
		files.add(file);
	}
	
}