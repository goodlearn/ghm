package com.thinkgem.jeesite.wx.manager;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.assist.entity.Assist;
import com.thinkgem.jeesite.modules.assist.service.AssistBaseService;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;
import com.thinkgem.jeesite.wx.model.AssistStateValue;
import com.thinkgem.jeesite.wx.view.ViewAssist;

@Service
@Transactional(readOnly = true)
public class AssistManager extends AssistBaseService {
	

	public Assist get(String id) {
		return super.get(id);
	}
	
	public List<Assist> getByUserInfoId(Long userInfoId) {
		return dao.getByUserInfoId(userInfoId);
	}
	
	/**
	 * 查询帮扶
	 */
	public ViewAssist queryAssistForZero(String idCard){
		ViewAssist ret = null;
		Userinfo userInfo = userinfoDao.getByIdCard(idCard);
		if(null != userInfo){
			List<Assist> assists = dao.getByUserInfoId(Long.valueOf(userInfo.getId()));
			if(null == assists){
				return ret;
			}
			
			for(Assist assist:assists) {
				ret = new ViewAssist();
				ret.convertToModel(assist);
				return ret;
			}
		}
		return ret;
	}
	
	
	/**
	 * 查询帮扶
	 */
	public ViewAssist queryAssist(String idCard){
		ViewAssist ret = null;
		Userinfo userInfo = userinfoDao.getByIdCard(idCard);
		if(null != userInfo){
			List<Assist> assists = dao.getByUserInfoId(Long.valueOf(userInfo.getId()));
			if(null == assists){
				return ret;
			}
			for(Assist assist:assists) {
				int stateValue = assist.getAssistState();
				stateValue++;
				if(stateValue!=AssistStateValue.end){
					ret = new ViewAssist();
					ret.convertToModel(assist);
					break;
				}
			}
		}
		return ret;
	}
	
}
