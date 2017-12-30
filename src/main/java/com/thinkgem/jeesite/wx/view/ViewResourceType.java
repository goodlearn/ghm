package com.thinkgem.jeesite.wx.view;

import com.thinkgem.jeesite.modules.resourceType.entity.ResourceType;

public class ViewResourceType {
	
	private int code;

	/**资源类型**/
	private Integer type ;
	
	
	/**
	 * 图片路径
	 */
	private String sourcePath;
	
	
	
	public void convertToVoModel(ResourceType rt){
		this.type = rt.getType();
		this.sourcePath = rt.getSourcePath();
	}
	
	/**
	 * 数据转换
	 * @param userInfo
	 */
	public void convertToDb(ResourceType rt){
		rt.setSourcePath(sourcePath);
		rt.setType(type);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	
	
}
