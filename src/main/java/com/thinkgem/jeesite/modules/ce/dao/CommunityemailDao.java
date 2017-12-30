/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ce.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ce.entity.Communityemail;

/**
 * ceDAO接口
 * @author wzy
 * @version 2017-09-20
 */
@MyBatisDao
public interface CommunityemailDao extends CrudDao<Communityemail> {
	
	public Communityemail getCommunityEmailByIdSerial(String serial);
	
}