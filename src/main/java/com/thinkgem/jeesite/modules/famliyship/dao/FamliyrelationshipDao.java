/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.famliyship.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.famliyship.entity.Famliyrelationship;

/**
 * famliyshipDAO接口
 * @author wzy
 * @version 2017-09-20
 */
@MyBatisDao
public interface FamliyrelationshipDao extends CrudDao<Famliyrelationship> {
	
	public List<Famliyrelationship> findByUserId(Long userinfoId);
	
}