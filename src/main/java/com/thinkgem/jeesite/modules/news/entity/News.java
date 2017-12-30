/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.news.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * newsEntity
 * @author wzy
 * @version 2017-09-20
 */
public class News extends DataEntity<News> {
	
	private static final long serialVersionUID = 1L;
	private String author;		// author
	private String constants;		// constants
	private String imagePath;		// imagepath
	private String newsDate;		// newsdate
	private Boolean staticFlag;		// staticflag
	private String title;		// title
	private Integer type;		// type
	private Integer version;		// version
	
	public News() {
		super();
	}

	public News(String id){
		super(id);
	}

	@Length(min=0, max=255, message="author长度必须介于 0 和 255 之间")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@NotBlank
	public String getConstants() {
		return constants;
	}

	public void setConstants(String constants) {
		this.constants = constants;
	}
	
	@Length(min=0, max=255, message="imagepath长度必须介于 0 和 255 之间")
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@Length(min=0, max=255, message="newsDate长度必须介于 0 和 255 之间")
	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsdate) {
		this.newsDate = newsdate;
	}
	
	public Boolean getStaticFlag() {
		return staticFlag;
	}

	public void setStaticFlag(Boolean staticFlag) {
		this.staticFlag = staticFlag;
	}
	
	@Length(min=0, max=255, message="title长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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