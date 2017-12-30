/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.userinfo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;
import com.thinkgem.jeesite.modules.assist.dao.AssistDao;
import com.thinkgem.jeesite.modules.assist.entity.Assist;
import com.thinkgem.jeesite.modules.famliyship.dao.FamliyrelationshipDao;
import com.thinkgem.jeesite.modules.famliyship.entity.Famliyrelationship;
import com.thinkgem.jeesite.modules.famliyship.service.FamliyrelationshipService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.userinfo.dao.UserinfoDao;

/**
 * userInfoService
 * @author wzy
 * @version 2017-09-20
 */
@Service
@Transactional(readOnly = true)
public class UserinfoService extends BaseUserinfoService {

	@Autowired
	private FamliyrelationshipDao frDao;
	
	@Autowired
	private AssistDao assistDao;
	
	public Userinfo get(String id) {
		Userinfo userinfo = super.get(id);
		return userinfo;
	}
	
	public Userinfo getByIdCard(String idcard) {
		return dao.getByIdCard(idcard);
	}
	
	
	public List<String> findAllIdCards(){
		return dao.findListIdCards();
	}
	
	public List<Userinfo> findList(Userinfo userinfo) {
		return super.findList(userinfo);
	}
	
	public Page<Userinfo> findPage(Page<Userinfo> page, Userinfo userinfo) {
		page.setOrderBy("update_date desc");
		return super.findPage(page, userinfo);
	}
	
	//查询家庭成员
	public List<Famliyrelationship> findFr(Userinfo userinfo){
		List<Famliyrelationship> frss = null;
		Long userinfoId = Long.valueOf(userinfo.getId());
		if(null!=userinfoId) {
			frss = frDao.findByUserId(Long.valueOf(userinfoId));
		}
		return frss;
	}
	
	@Transactional(readOnly = false)
	public void save(Userinfo userinfo) {
		
		setAddDefault(userinfo);//补充默认数据
		dictData(userinfo);//补充字典数据
		
		super.save(userinfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(Userinfo userinfo) {
		
		//删除之前需要删除关联对象 依次顺序是 家庭成员  帮扶状态 会员
		
		
		String userinfoId = userinfo.getId();
		if(null == userinfoId) {
			return;
		}
		
		Long assistId = userinfo.getAssistId();
		if(null == assistId) {
			return;
		}
		
		//查找成员和帮扶
		List<Famliyrelationship> frss = frDao.findByUserId(Long.valueOf(userinfoId));
		List<Assist> assists = assistDao.getByUserInfoId(Long.valueOf(userinfoId));
		
		//删除成员关系
		if(null!=frss) {
			for(Famliyrelationship frs:frss) {
				frDao.delete(frs);
			}
		}
		
		//先设置外键为空
		if(null!=assists) {
			for(Assist assist:assists) {
				assist.setUserinfoId(null);
				assistDao.update(assist);

			}
		}
		//先设置外键为空
		userinfo.setAssistId(null);
		super.save(userinfo);
		
		//删除帮扶状态
		if(null!=assists) {
			for(Assist assist:assists) {
				assistDao.delete(assist);

			}
		}
		

		//删除会员
		super.delete(userinfo);
	}
	
}