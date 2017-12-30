/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resourceType.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resourceType.entity.ResourceType;

/**
 * 资源配置DAO接口
 * @author wzy
 * @version 2017-10-06
 */
@MyBatisDao
public interface ResourceTypeDao extends CrudDao<ResourceType> {
	
}