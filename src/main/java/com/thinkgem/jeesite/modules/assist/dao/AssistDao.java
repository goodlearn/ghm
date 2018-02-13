package com.thinkgem.jeesite.modules.assist.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.assist.entity.Assist;

/**
 * assistDAO接口
 * @author wzy
 * @version 2017-09-20
 */
@MyBatisDao
public interface AssistDao extends CrudDao<Assist> {

	public List<Assist> getByUserInfoId(Long userInfoId);
	
	
	public List<Assist> findAllStateList(Assist entity);
	
	//查询数量
	public Integer findCount();
	
	//去除重复会员
	public Integer findDistinctCount();
}