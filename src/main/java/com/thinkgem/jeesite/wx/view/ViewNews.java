package com.thinkgem.jeesite.wx.view;

import com.thinkgem.jeesite.common.utils.CommonConstants;
import com.thinkgem.jeesite.modules.news.entity.News;

public class ViewNews {

	private static final long serialVersionUID = -8033925728409510247L;
	
	private String id;
	
	/**标题**/
	private String title;
	
	/**内容**/
	private String constants;
	
	/**
	 * 图片路径
	 */
	private String imagePath;
	
	/**时间**/
	private String newsDate;
	
	/**
	 * 新闻类型
	 */
	private Integer type;
	
	/**
	 * 新闻类型
	 */
	private String typeName;
	
	/**
	 * 作者
	 */
	private String author;
	
	/**
	 * 校验码
	 */
	private Integer code;
	
	/**
	 * 模板对象标识。
	 */
	private Boolean staticFlag = true;
	
	
	/**
	 * 转换类型名称 将类型值得到类型名称
	 * @param news
	 */
	public void convertTypeValue(){
		if(type == CommonConstants.firstNews){
			typeName = "首页图片";
		}else if(type == CommonConstants.scrollNews){
			typeName = "滚动图片";
		}
	}
	
	/**
	 * 转换类型名称 将类型名称得到类型值
	 * @param news
	 */
	public void convertTypeConstants(){
		if(type == CommonConstants.firstNews){
			typeName = CommonConstants.firstName;
		}else if(type == CommonConstants.scrollNews){
			typeName = CommonConstants.scrollName;
		}
	}
	
	public void convertToVoModel(News news){
		this.constants = news.getConstants();
		this.title = news.getTitle();
		this.newsDate = news.getNewsDate();
		this.imagePath = news.getImagePath();
		this.author = news.getAuthor();
		this.type = news.getType();
		this.id = news.getId();
		this.title = news.getTitle();
	}
	
	
	
	/**
	 * 数据转换
	 * @param userInfo
	 */
	public void convertToDb(News news){
		news.setId(id.toString());
		news.setAuthor(author);
		news.setType(type);
		news.setConstants(constants);
		news.setImagePath(imagePath);
		news.setNewsDate(newsDate);
		news.setStaticFlag(true);
		news.setTitle(title);
	}
	

	public String getTypeName() {
		return typeName;
	}



	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}



	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getConstants() {
		return constants;
	}

	public void setConstants(String constants) {
		this.constants = constants;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}

	public Boolean getStaticFlag() {
		return staticFlag;
	}

	public void setStaticFlag(Boolean staticFlag) {
		this.staticFlag = staticFlag;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ViewNews [id=" + id + ", title=" + title + ", constants=" + constants + ", imagePath=" + imagePath
				+ ", newsDate=" + newsDate + ", type=" + type + ", typeName=" + typeName + ", author=" + author
				+ ", code=" + code + ", staticFlag=" + staticFlag + "]";
	}
	
	
}
