package com.thinkgem.jeesite.wx.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.modules.assist.dao.AssistDao;
import com.thinkgem.jeesite.modules.assist.entity.Assist;
import com.thinkgem.jeesite.modules.famliyship.dao.FamliyrelationshipDao;
import com.thinkgem.jeesite.modules.famliyship.entity.Famliyrelationship;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;
import com.thinkgem.jeesite.modules.userinfo.service.BaseUserinfoService;
import com.thinkgem.jeesite.wx.view.ViewAssist;
import com.thinkgem.jeesite.wx.view.ViewFamliyRelationship;
import com.thinkgem.jeesite.wx.view.ViewUserInfo;

@Service
@Transactional(readOnly = true)
public class UserInfoManager extends BaseUserinfoService {

	@Autowired
	private FamliyrelationshipDao famliyrelationshipDao;

	@Autowired
	private AssistDao assistDao;
	
	public Userinfo get(String id) {
		Userinfo userinfo = super.get(id);
		return userinfo;
	}
	
	public Userinfo queryByIdCard(String idCard) {
		Userinfo userinfo = dao.getByIdCard(idCard);
		return userinfo;
	}
	
	/**
	 * 依据身份证号查询用户信息
	 * @param idcard
	 * @return
	 */
	public ViewUserInfo getByIdCard(String idcard) {
		
		ViewUserInfo ret = null;
		Userinfo userInfo = dao.getByIdCard(idcard);
		if(null != userInfo){
			ret = new ViewUserInfo();
			ret.convertToVoModel(userInfo);
		}
		return ret;
		
	}
	
	//补充字典数据 比如政治面貌是 党员 群众
	protected void dictData(ViewUserInfo userinfo) {
		
		String community = DictUtils.getDictLabel(String.valueOf(userinfo.getCommunityKey()), "ce_serial", "");
		userinfo.setCommunity(community);
		
		String incomeSource = DictUtils.getDictLabel(String.valueOf(userinfo.getIncomeSourceKey()), "incomeSourceKey", "");
		userinfo.setIncomeSource(incomeSource);
		
		String insurance = DictUtils.getDictLabel(String.valueOf(userinfo.getInsuranceKey()), "insuranceKey", "");
		userinfo.setInsurance(insurance);

		String  nation= DictUtils.getDictLabel(String.valueOf(userinfo.getNationKey()), "nationKey", "");
		userinfo.setNation(nation);;
	}
	
	//添加信息时候的默认值   因为之前表格设计过程的字段用途已经取消 但是不能删除字段 只能现将部分字段给予默认值 保证系统的稳定运行
	protected void setAddDefault(ViewUserInfo userinfo) {
		userinfo.setFamliyNum(1);
		userinfo.setStaticFlag(true);
		userinfo.setVersion(1);
		userinfo.setMembershipTime(CasUtils.convertDate2DefaultString(new Date()));
	}
	
	/**
	 * 完善个人信息  参数判断
	 * 0 数据正常
	 * 1 身份证为空
	 * 2 该身份没有数据
	 */
	private int supplementDataCheck(String idCard){
		//如果身份证没有的话，无法补充数据
		if(StringUtils.isEmpty(idCard)){
			return 1;
		}
		Userinfo userInfo = this.dao.getByIdCard(idCard);
		//该身份证没有数据
		if(null == userInfo){
			return 2;
		}
		return 0;
	}
	
	
	/**
	 * 申请帮扶
	 */
	@Transactional(readOnly = false)
	public Assist applyAssist(String idCard,ViewAssist viewAssist) {
		
		//数据验证
		int result = supplementDataCheck(idCard);
		
		if(result!=0){
			return null;
		}
		
		Userinfo userInfo = this.dao.getByIdCard(idCard);
		Assist assist = null;
		assist = viewAssist.convertToDb(userInfo);
		assist.setStaticFlag(true);
		assist.setVersion(1);
		assist.wxPreInsert();
		assistDao.insert(assist);
		return assist;
	}
	
	/**
	 * 完善个人信息
	 * 补充家庭成员和工资待遇等数据
	 * 要求：因为是补充数据，所以之前的数据是拥有的，需要有用户的身份证ID 
	 * 
	 */
	@Transactional(readOnly = false)
	public void supplementPersonData(String idCard,ViewUserInfo viewUserInfo) {
		//数据验证
		int result = supplementDataCheck(idCard);
		
		if(result!=0){
			return;
		}
		
		/**
		 * 根据键值生成对应的信息
		 */
		dictData(viewUserInfo);
		
		setAddDefault(viewUserInfo);
		
		Userinfo userInfo = this.dao.getByIdCard(idCard);
		
		//补充数据
		viewUserInfo.convertToSupplyDbModel(userInfo);
		userInfo.wxPreInsert();
		super.save(userInfo);
	}
	
	//依据帮扶信息查询家庭成员
	public List<Famliyrelationship> findFr(Userinfo userInfo){
		List<Famliyrelationship> frss = null;
		Long userinfoId = Long.valueOf(userInfo.getId());
		if(null!=userinfoId) {
			frss = famliyrelationshipDao.findByUserId(Long.valueOf(userinfoId));
		}
		return frss;
	}
	
	/**
	 * 清空家庭成员
	 */
	@Transactional(readOnly = false)
	public void clearFamliyRelationData(String idCard){
		//数据验证
		int result = supplementDataCheck(idCard);
		
		if(result!=0){
			return;
		}
		
		Userinfo userInfo = this.dao.getByIdCard(idCard);
		
		
		//清空家庭成员
		List<Famliyrelationship> frs = findFr(userInfo);
		if(null == frs){
			return;
		}
		
		for (int i = frs.size()-1; i >=0; i--){
			Famliyrelationship fr = frs.get(i);
			fr.setUserInfo(null);
			fr.wxPreInsert();
			famliyrelationshipDao.delete(fr);
		}
	}
	
	/**
	 * 创建家庭关系
	 */
	@Transactional(readOnly = false)
	public void createFamliyRelationData(String idCard, List<ViewFamliyRelationship> viewFamliyRelationships,String postCode,String famliyAddress) {
		
		//数据验证
		int result = supplementDataCheck(idCard);
		
		if(result!=0){
			return;
		}
		
		Userinfo userInfo = this.dao.getByIdCard(idCard);
		
		if(null==viewFamliyRelationships){
			return;
		}
		
		int famliyNum = viewFamliyRelationships.size();
		userInfo.setFamliyNum(++famliyNum);
		userInfo.setPostCode(postCode);
		userInfo.setFamliyAddress(famliyAddress);
		
		List<Famliyrelationship> frs = new ArrayList<Famliyrelationship>();
		
		//生成新的家庭成员数据
		for(ViewFamliyRelationship viewFamliyRelationship:viewFamliyRelationships){
			Famliyrelationship entity = viewFamliyRelationship.convertToDbModel(userInfo);
			entity.wxPreInsert();
			frs.add(entity);
		}
		
		for(Famliyrelationship fr:frs){
			famliyrelationshipDao.insert(fr);
		}
		
		dao.update(userInfo);//更新家庭成员信息
		
	}
	
	/**
	 * 更新家庭关系
	 */
	@Transactional(readOnly = false)
	public void updateFamliyRelationData(String idCard,List<ViewFamliyRelationship> viewFamliyRelationships,String postCode,String famliyAddress){
		//数据验证
		int result = supplementDataCheck(idCard);
		
		if(result!=0){
			return;
		}
		
		Userinfo userInfo = this.dao.getByIdCard(idCard);
		
		//数据为空
		if(null==viewFamliyRelationships){
			return;
		}
		
		//Id是否是空
		for(ViewFamliyRelationship viewFamliyRelationship:viewFamliyRelationships){
			String famliyId = viewFamliyRelationship.getFamliyId();
			if(StringUtils.isEmpty(famliyId)){
				return;
			}
		}
		
		//更新家庭成员数据
		for(ViewFamliyRelationship viewFamliyRelationship:viewFamliyRelationships){
			String famliyId = viewFamliyRelationship.getFamliyId();
			Famliyrelationship existFrs = famliyrelationshipDao.get(famliyId);
			if(null!=existFrs){
				Famliyrelationship entity = viewFamliyRelationship.convertToDbModel(userInfo,existFrs);
				entity.wxPreInsert();
				famliyrelationshipDao.update(entity);
			}
		}
		
		int famliyNum = viewFamliyRelationships.size();
		userInfo.setFamliyNum(++famliyNum);
		userInfo.setPostCode(postCode);
		userInfo.setFamliyAddress(famliyAddress);
		dao.update(userInfo);//更新家庭成员信息
		
	}
	
	/**
	 * 依据身份证信息查询数据(返回Json) 如果在Control用Json转换 会出先could not initialize proxy - no Session at  问题  
	 */
	public String queryFrByIdCardForJson(String idCard){
		String ret = null;
		//如果参数没有问题 查询数据
		List<ViewFamliyRelationship>  rets = queryFrByIdCard(idCard);
		if(null==rets || rets.size()<=0){
			return ret;
		}
		ret = JSONObject.toJSONString(rets);
        return ret;
	}
	
	/**
	 * 依据身份证信息查询数据
	 */
	public List<ViewFamliyRelationship> queryFrByIdCard(String idCard){
		List<ViewFamliyRelationship> rets = null;
		Userinfo userInfo = dao.getByIdCard(idCard);
		if(null != userInfo){
			rets = new ArrayList<ViewFamliyRelationship>();
			List<Famliyrelationship> frs = findFr(userInfo);
			if(null != frs && frs.size()>0){
				for(int i=0;i<frs.size();i++){
					Famliyrelationship fr = frs.get(i);
					if(null!=fr){
						ViewFamliyRelationship ret = new ViewFamliyRelationship();
						ret.convertToModel(fr);
						rets.add(ret);
					}
				}
			}else{
				//补充上个人数据
				ViewFamliyRelationship vfr = new ViewFamliyRelationship();
				vfr.createCurrentUserInfo(userInfo);
				rets.add(vfr);
			}
		}
		return rets;
	}
	
}
