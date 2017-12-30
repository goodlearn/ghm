/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.resourceType.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.resourceType.entity.ResourceType;
import com.thinkgem.jeesite.modules.resourceType.dao.ResourceTypeDao;

/**
 * 资源配置Service
 * @author wzy
 * @version 2017-10-06
 */
@Service
@Transactional(readOnly = true)
public class ResourceTypeService extends CrudService<ResourceTypeDao, ResourceType> {

	public ResourceType get(String id) {
		return super.get(id);
	}
	
	public List<ResourceType> findList(ResourceType resourceType) {
		return super.findList(resourceType);
	}
	
	public Page<ResourceType> findPage(Page<ResourceType> page, ResourceType resourceType) {
		return super.findPage(page, resourceType);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourceType resourceType) {
		resourceType.setStaticFlag(true);
		resourceType.setVersion(1);
		super.save(resourceType);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourceType resourceType) {
		super.delete(resourceType);
	}
	
}