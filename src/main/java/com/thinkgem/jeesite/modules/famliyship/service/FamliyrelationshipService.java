/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.famliyship.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.famliyship.entity.Famliyrelationship;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.userinfo.dao.UserinfoDao;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;
import com.thinkgem.jeesite.modules.userinfo.service.UserinfoService;
import com.thinkgem.jeesite.modules.famliyship.dao.FamliyrelationshipDao;

/**
 * famliyshipService
 * @author wzy
 * @version 2017-09-20
 */
@Service
@Transactional(readOnly = true)
public class FamliyrelationshipService extends CrudService<FamliyrelationshipDao, Famliyrelationship> {

	//会员
	@Autowired
	private UserinfoDao userinfoDao;
	

	public Famliyrelationship get(String id) {
		return super.get(id);
	}
	
	//依据会员信息查找家庭成员信息
	public List<Famliyrelationship> findByUserId(Long userinfoId){
		return dao.findByUserId(userinfoId);
	}
	
	//补充添加数据
	public void supplyAddData(Famliyrelationship fr) {
		dictData(fr);//字典数据
		setAddDefault(fr);//默认值
	}
	
	//补充字典数据 比如政治面貌是 党员 群众
	private void dictData(Famliyrelationship fr) {
		String healthy = DictUtils.getDictLabel(String.valueOf(fr.getHealthyKey()), "healthy", "");
		String insurance = DictUtils.getDictLabel(String.valueOf(fr.getInsuranceKey()), "insuranceKey", "");
		String politicallandscape = DictUtils.getDictLabel(String.valueOf(fr.getPoliticalLandscapeKey()), "politicalLandscape", "");
		String relationship = DictUtils.getDictLabel(String.valueOf(fr.getRelationshipKey()), "relationship", "");
		fr.setHealthy(healthy);
		fr.setInsurance(insurance);
		fr.setPoliticalLandscape(politicallandscape);
		fr.setRelationship(relationship);
	}
	
	//添加信息时候的默认值   因为之前表格设计过程的字段用途已经取消 但是不能删除字段 只能现将部分字段给予默认值 保证系统的稳定运行
	private void setAddDefault(Famliyrelationship fr) {
		fr.setFamliyNum(1);
		fr.setStaticFlag(true);
		fr.setVersion(1);
	}
	
	//补充会员信息
	public void setUserInfo(Famliyrelationship fr) {
		if(null == fr){
			return;
		}
		
		Long userInfoId = fr.getUserinfoId();
		if(null!=userInfoId){
			Userinfo userInfo = userinfoDao.get(userInfoId.toString());
			fr.setUserInfo(userInfo);
		}
	}
	
	public List<Famliyrelationship> findList(Famliyrelationship famliyrelationship) {
		
		//查询家庭成员关系数据后 将会员信息放入
		List<Famliyrelationship> frss = super.findList(famliyrelationship);
		setUserInfoForRfs(frss);
		
		return frss;
	}
	
	public Page<Famliyrelationship> findPage(Page<Famliyrelationship> page, Famliyrelationship famliyrelationship) {
		
		//查询家庭成员关系数据后 将会员信息放入
		Page<Famliyrelationship> pageFrs = super.findPage(page, famliyrelationship);
		setUserInfoForRfs(pageFrs.getList());
		return pageFrs;
	}
	
	
	//将家庭成员中的会员信息补充 没有利用MyBatits查询 进行分开查询 就是用MyBatits查询出家庭成员 然后其中关联的会员信息是在这个部分用代码查出 不是用MyBatits查出
	private void setUserInfoForRfs(List<Famliyrelationship> frss){
		if(null == frss){
			return;
		}
		
		//循环将家庭成员的会员信息关联
		for(Famliyrelationship fr:frss) {
			setUserInfo(fr);//补充会员信息
		}
	}
	
	@Transactional(readOnly = false)
	public void save(Famliyrelationship famliyrelationship) {
		super.save(famliyrelationship);
	}
	
	@Transactional(readOnly = false)
	public void delete(Famliyrelationship famliyrelationship) {
		super.delete(famliyrelationship);
	}
	
}