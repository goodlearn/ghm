/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ce.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ce.entity.Communityemail;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.ce.dao.CommunityemailDao;

/**
 * ceService
 * @author wzy
 * @version 2017-09-20
 */
@Service
@Transactional(readOnly = true)
public class CommunityemailService extends CrudService<CommunityemailDao, Communityemail> {

	public Communityemail get(String id) {
		return super.get(id);
	}
	
	public List<Communityemail> findList(Communityemail communityemail) {
		return super.findList(communityemail);
	}
	
	public Page<Communityemail> findPage(Page<Communityemail> page, Communityemail communityemail) {
		return super.findPage(page, communityemail);
	}
	
	//补充字典数据
	private void dictData(Communityemail communityemail) {
		String communityName = DictUtils.getDictLabel(String.valueOf(communityemail.getSerial()), "ce_serial", "");
		communityemail.setCommunityName(communityName);
	}
	
	//添加信息时候的默认值   因为之前表格设计过程的字段用途已经取消 但是不能删除字段 只能现将部分字段给予默认值 保证系统的稳定运行
	private void setAddDefault(Communityemail communityemail) {
		communityemail.setStaticFlag(true);
		communityemail.setVersion(1);
	}
	
	@Transactional(readOnly = false)
	public void save(Communityemail communityemail) {
		
		setAddDefault(communityemail);
		dictData(communityemail);
		
		super.save(communityemail);
	}
	
	@Transactional(readOnly = false)
	public void delete(Communityemail communityemail) {
		super.delete(communityemail);
	}
	
}