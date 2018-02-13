package com.thinkgem.jeesite.modules.userinfo.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.userinfo.dao.UserinfoDao;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;

@Service
@Transactional(readOnly = true)
public class BaseUserinfoService extends CrudService<UserinfoDao, Userinfo> {
	
	//添加信息时候的默认值   因为之前表格设计过程的字段用途已经取消 但是不能删除字段 只能现将部分字段给予默认值 保证系统的稳定运行
	protected void setAddDefault(Userinfo userinfo) {
		userinfo.setFamliyNum(1);
		userinfo.setStaticFlag(true);
		userinfo.setVersion(1);
		userinfo.setMembershipTime(CasUtils.convertDate2HMSString(new Date()));
	}

	//补充字典数据 比如政治面貌是 党员 群众
	protected void dictData(Userinfo userinfo) {
		
		String community = DictUtils.getDictLabel(String.valueOf(userinfo.getCommunityKey()), "ce_serial", "");
		userinfo.setCommunity(community);
		
		String incomeSource = DictUtils.getDictLabel(String.valueOf(userinfo.getIncomeSourceKey()), "incomeSourceKey", "");
		userinfo.setIncomeSource(incomeSource);
		
		String insurance = DictUtils.getDictLabel(String.valueOf(userinfo.getInsuranceKey()), "insuranceKey", "");
		userinfo.setInsurance(insurance);

		String  nation= DictUtils.getDictLabel(String.valueOf(userinfo.getNationKey()), "nationKey", "");
		userinfo.setNation(nation);;
	}
	
}
