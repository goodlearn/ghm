package com.thinkgem.jeesite.modules.resourceType.entity;


import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 资源配置Entity
 * @author wzy
 * @version 2017-10-06
 */
public class ResourceType extends DataEntity<ResourceType> {
	
	private static final long serialVersionUID = 1L;
	
	/**资源类型**/
	private Integer type ;
	
	
	/**
	 * 图片路径
	 */
	private String sourcePath;
	
	
	/**
	 * 模板对象标识。
	 */
	private Boolean staticFlag;
	
	private Integer version;
	
	public ResourceType() {
		super();
	}

	public ResourceType(String id){
		super(id);
	}

	@Length(min=0, max=255, message="sourcepath长度必须介于 0 和 255 之间")
	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcepath) {
		this.sourcePath = sourcepath;
	}
	
	public Boolean getStaticFlag() {
		return staticFlag;
	}

	public void setStaticFlag(Boolean staticflag) {
		this.staticFlag = staticflag;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
}