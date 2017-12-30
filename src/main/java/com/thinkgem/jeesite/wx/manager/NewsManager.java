package com.thinkgem.jeesite.wx.manager;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.news.dao.NewsDao;
import com.thinkgem.jeesite.modules.news.entity.News;

@Service
@Transactional(readOnly = true)
public class NewsManager extends CrudService<NewsDao, News> {

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
	
}
