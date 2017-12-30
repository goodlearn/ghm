package com.thinkgem.jeesite.modules.assist.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.assist.entity.Assist;

@Service
@Transactional(readOnly = true)
public class AssistActivityService extends AssistBaseService {
	
	public Page<Assist> findPage(Page<Assist> page, Assist assist) {
		page.setOrderBy("applyDate desc");
		Page<Assist> assists =  findAllStateListPage(page, assist);
		setUserInfoForAssist(assists.getList());//补充会员信息
		return assists ;
	}

	
}
