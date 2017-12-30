/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.news.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.news.entity.News;

/**
 * newsDAO接口
 * @author wzy
 * @version 2017-09-20
 */
@MyBatisDao
public interface NewsDao extends CrudDao<News> {
	
}