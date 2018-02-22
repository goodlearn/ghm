package com.thinkgem.jeesite.modules.news.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.news.entity.News;
import com.thinkgem.jeesite.modules.news.dao.NewsDao;

/**
 * newsService
 * @author wzy
 * @version 2017-09-20
 */
@Service
@Transactional(readOnly = true)
public class NewsService extends CrudService<NewsDao, News> {

	public News get(String id) {
		return super.get(id);
	}
	
	public List<News> findList(News news) {
		return super.findList(news);
	}
	
	public Page<News> findPage(Page<News> page, News news) {
		page.setOrderBy("id desc");
		return super.findPage(page, news);
	}
	
	
	//添加信息时候的默认值   因为之前表格设计过程的字段用途已经取消 但是不能删除字段 只能现将部分字段给予默认值 保证系统的稳定运行
	private void setAddDefault(News news) {
		news.setStaticFlag(true);
		news.setVersion(1);
	}
	
	@Transactional(readOnly = false)
	public void save(News news) {
		
		//默认数据
		setAddDefault(news);
		
		super.save(news);
	}
	
	@Transactional(readOnly = false)
	public void delete(News news) {
		super.delete(news);
	}
	
}