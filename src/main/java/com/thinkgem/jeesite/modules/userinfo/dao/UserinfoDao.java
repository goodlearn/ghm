package com.thinkgem.jeesite.modules.userinfo.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;

/**
 * userInfoDAO接口
 * @author wzy
 * @version 2017-09-20
 */
@MyBatisDao
public interface UserinfoDao extends CrudDao<Userinfo> {
	
	public List<String> findListIdCards();
	
	public Userinfo getByIdCard(String idcard);
	
	public Integer findGenderCount(Userinfo userinfo);
	
	public Integer findCount();
}