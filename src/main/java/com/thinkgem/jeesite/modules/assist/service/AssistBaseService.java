package com.thinkgem.jeesite.modules.assist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.assist.dao.AssistDao;
import com.thinkgem.jeesite.modules.assist.entity.Assist;
import com.thinkgem.jeesite.modules.userinfo.dao.UserinfoDao;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;
import com.thinkgem.jeesite.modules.userinfo.service.UserinfoService;

@Transactional(readOnly = true)
public class AssistBaseService extends CrudService<AssistDao, Assist>{
	
	//会员
	@Autowired
	protected UserinfoDao userinfoDao;
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<Assist> findAllStateListPage(Page<Assist> page, Assist entity) {
		entity.setPage(page);
		page.setList(dao.findAllStateList(entity));
		return page;
	}

	
	//将帮扶状态中的会员信息补充 没有利用MyBatits查询 进行分开查询 就是用MyBatits查询出家庭成员 然后其中关联的会员信息是在这个部分用代码查出 不是用MyBatits查出
	protected void setUserInfoForAssist(List<Assist> assists){
		if(null == assists){
			return;
		}
		
		//循环将帮扶的会员信息关联
		for(Assist assist:assists) {
			setUserInfo(assist);//补充会员信息
		}
	}
	
	//补充会员信息
	public void setUserInfo(Assist assist) {
		if(null == assist){
			return;
		}
		
		Long userInfoId = assist.getUserinfoId();
		if(null!=userInfoId){
			Userinfo userInfo = userinfoDao.get(userInfoId.toString());
			assist.setUserInfo(userInfo);
		}
	}
	
}
