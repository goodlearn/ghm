package com.thinkgem.jeesite.wx.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.encrypt.HttpClientUtil;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.CommonConstants;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.JsonUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.assist.entity.Assist;
import com.thinkgem.jeesite.modules.assist.service.AssistService;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;
import com.thinkgem.jeesite.wx.manager.UserInfoManager;
import com.thinkgem.jeesite.wx.model.AssistStateValue;
import com.thinkgem.jeesite.wx.model.KvValue;
import com.thinkgem.jeesite.wx.model.WxServerBackCode;
import com.thinkgem.jeesite.wx.view.ViewAssist;
import com.thinkgem.jeesite.wx.view.ViewFamliyRelationship;
import com.thinkgem.jeesite.wx.view.ViewUserInfo;

@Controller
@RequestMapping(value = "wx/userinfo")
public class WxUserInfoController extends BaseController {

	@Autowired
	private UserInfoManager userInfoManager;
	
	@Autowired
	private AssistService assistService;
	
	@ModelAttribute
	public Userinfo get(@RequestParam(required=false) String id,@RequestParam(required=false) String idCard) {
		Userinfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userInfoManager.get(id);
		}
		
		if (null == entity && StringUtils.isNotBlank(idCard)){
			entity = userInfoManager.queryByIdCard(idCard);
		}
		
		if (entity == null){
			entity = new Userinfo();
		}
		return entity;
	}
	
	/**
	 * 依据身份证获取个人信息
	 * 1 成功
	 * 2 参数为空
	 * 3 身份证号码错误
	 * 4 该会员不存在
	 */
	@RequestMapping(value = "getInfoByIdCard")
	@ResponseBody
	public String getInfoByIdCard(HttpServletRequest request,HttpServletResponse response){
		
		ViewUserInfo viewUserInfo = null;
		String idCard = request.getParameter("idCard"); 
		
		//参数为空
		if(StringUtils.isEmpty(idCard)){
			return backSuccessWithCode(2);
		}
		
		//身份证号码错误
		if(!CasUtils.idCardNumber(idCard)){
			return backSuccessWithCode(3);
		}
		
		//如果参数没有问题 查询数据
		viewUserInfo = userInfoManager.getByIdCard(idCard);	
		
		if(null==viewUserInfo){
			//该会员不存在
			return backSuccessWithCode(4);
		}
		
		//数据成功
		viewUserInfo.setCode(1);
		String jsonResult = JSONObject.toJSONString(viewUserInfo);
		return jsonResult;
	}
	
	/**
	 * 依据身份证获取个人信息
	 * 1  成功
	 * 2  参数为空
	 * 3  身份证号码错误
	 * 4  该会员不存在
	 * 5  会员storage不存在
	 * 6 storage过期了
	 * 7  未知错误
	 */
	@RequestMapping(value = "queryInfoByIdCard")
	@ResponseBody
	public String queryInfoByIdCard(HttpServletRequest request,HttpServletResponse response){
		
		try{
			ViewUserInfo viewUserInfo = null;
			String idCard = request.getParameter("idCard"); 
			String name = request.getParameter("name");
			String storage = request.getParameter("storage");
			
			//参数为空
			if(StringUtils.isEmpty(idCard)||
					StringUtils.isEmpty(name)||
						StringUtils.isEmpty(storage)){
				return backSuccessWithCode(2);
			}
			
			//身份证号码错误
			if(!CasUtils.idCardNumber(idCard)){
				return backSuccessWithCode(3);
			}
			
			/**
			 * 登录测试
			 */
			Object obj = KvValue.getUserInfo(storage);
			if(null == obj){
	        	return backSuccessWithCode(5);
	        }
			
			if(!(obj instanceof WxServerBackCode)){
				return backSuccessWithCode(5);
			}
			
	        WxServerBackCode wxServerBackCode = (WxServerBackCode)obj;
			//过期检验
			if(wxServerBackCode.isOverTime()){
				return backSuccessWithCode(6);
			}
			//如果参数没有问题 查询数据
			viewUserInfo = userInfoManager.getByIdCard(idCard);	
			
			if(null==viewUserInfo){
				//该会员不存在
				return backSuccessWithCode(4);
			}
			
			/**
			 * 保存到session
			 */
			wxServerBackCode.setIdCard(idCard);
			wxServerBackCode.setName(name);
			
			String dbName = viewUserInfo.getName();
			if(!name.equals(dbName)){
				//该会员不存在
				return backSuccessWithCode(4);
			}
		}catch(Exception e){
			e.printStackTrace();
			return backSuccessWithCode(7);
		}
		
		
		//数据成功
		return backSuccessWithCode(1);
	}
	
	 /**
     * 获取微信小程序登录信息
     */
    public WxServerBackCode wxLoginInfo(String code){
    	WxServerBackCode ret = null;
        try {
        	HttpClientUtil httpClientUtil = new HttpClientUtil();
        	String httpOrgCreateUrl = CommonConstants.WX_URL;  
            Map<String,String> createMap = new HashMap<String,String>();  
            createMap.put("appid",CommonConstants.WX_App_Id);  
            createMap.put("secret",CommonConstants.WX_App_Secret);  
            createMap.put("js_code",code);  
            createMap.put("grant_type","authorization_code");  
            String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateUrl,createMap,CommonConstants.UTF8);  
            System.out.println("result:"+httpOrgCreateTestRtn);
        	ret = JsonUtil.generatorObject(httpOrgCreateTestRtn,WxServerBackCode.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return ret;
    }
	
	  /**
     * 查询code
     * 错误返回码
     * 0 未知错误
     * 1 成功
     * 2 参数为空
     * 3 参数为空
     * @return
     */
	@RequestMapping(value = "queryCodeKey")
	@ResponseBody
    public Map<String,String> queryCodeKey(HttpServletRequest request,HttpServletResponse response){
    	int backCode = 0;
    	String rdSession = null;
    	String idCard = null;
    	String name = null;
		String code = request.getParameter("wxCode"); 
		//code为空
		if(StringUtils.isEmpty(code)){
			backCode = 2;
			return backSuccessWithCodeByMap(backCode);
		}
		try{
	        WxServerBackCode wxServerBackCode = wxLoginInfo(code);
	        if(null == wxServerBackCode){
	        	backCode = 3;
	        }else{
	        	//是否已经包含
	        	WxServerBackCode constainWxSb = wxServerBackCode.isSameOpenId(KvValue.getWxUsers());
	        	if(null!=constainWxSb){
	        		rdSession = constainWxSb.getStorage();
	        		idCard = constainWxSb.getIdCard();
	        		name = constainWxSb.getName();    
	        	}else{
	        		//不包含
	        	    rdSession = UUID.randomUUID().toString();
	  		        KvValue.wxUsersPutData(rdSession, wxServerBackCode);
	  		        wxServerBackCode.setRecordDate(new Date());
	  		        wxServerBackCode.setStorage(rdSession);
	        	}
	        	backCode = 1;	
	        }
		}catch(Exception e){
			backCode = 0;
			e.printStackTrace();
		}
		
		if(backCode == 1){
			Map<String,String> map = new HashMap<String,String>();
			map.put("code", "1");
			map.put("storage", rdSession);
			if(null!=rdSession){
				map.put("storage", rdSession);
			}
			if(null!=name){
				map.put("name", name);
			}
			if(null!=idCard){
				map.put("idCard", idCard);
			}		
			return map;
		}else{
			return backSuccessWithCodeByMap(1);
		}		
    }
	
	/**
     * 注销
     */
	@RequestMapping(value = "unLoadCodeKey")
	@ResponseBody
    public String unLoadCodeKey(HttpServletRequest request,HttpServletResponse response){
    	int backCode = 0;
		String storage = request.getParameter("storage"); 
		if(StringUtils.isEmpty(storage)){
			backCode = 2;
			return backSuccessWithCode(backCode);
		}
		KvValue.wxUserRemoveData(storage);
		return backSuccessWithCode(1);
    }
	
	/**
	 * 编辑个人信息
	 * 1 成功
	 * 2 身份证参数为空
	 * 3 身份证号码格式错误
	 * 4 会员不存在
	 * 5 未知错误
	 */
	@RequestMapping(value = "editPersonData",method=RequestMethod.POST)
	@ResponseBody
	public String editPersonData(ViewUserInfo viewUserinfo,HttpServletRequest request,HttpServletResponse response){
		try{
			String idCard = request.getParameter("idCard"); 
			
			//参数为空
			if(StringUtils.isEmpty(idCard)){
				return backSuccessWithCode(2);
			}
			
			//身份证号码错误
			if(!CasUtils.idCardNumber(idCard)){
				return backSuccessWithCode(3);
			}
			
			ViewUserInfo viewDbUserInfo = userInfoManager.getByIdCard(idCard);	
			
			//该会员不存在
			if(null==viewDbUserInfo){
				return backSuccessWithCode(4);
			}
			
			viewUserinfo.setId(viewDbUserInfo.getId());
			
			//更新数据
			userInfoManager.supplementPersonData(idCard, viewUserinfo);
			
		}catch(Exception e){
			e.printStackTrace();
			return backSuccessWithCode(5);
		}
				
		return backSuccessWithCode(1);
	}
	
	/**
	 * 编辑家庭成员数据
	 * 1 成功
	 * 2 身份证为空
	 * 3 身份证号码格式错误
	 * 4 会员不存在
	 * 5 家庭成员数量为空
	 * 6 家庭成员数量错误
	 * 7 未知错误
	 */
	@RequestMapping(value = "editFamliyData")
	@ResponseBody
	public String editFamliyData(HttpServletRequest request,HttpServletResponse response){
		try{
			String idCard = request.getParameter("idCard"); 
			String numberString = request.getParameter("number"); 

			//家庭成员数量为空
			if(StringUtils.isEmpty(numberString)){
				return backSuccessWithCode(5);
			}
			
			//身份证为空
			if(StringUtils.isEmpty(idCard)){
				return backSuccessWithCode(2);
			}
			
			//身份证号码错误
			if(!CasUtils.idCardNumber(idCard)){
				return backSuccessWithCode(3);
			}
			
			ViewUserInfo viewUserInfo = userInfoManager.getByIdCard(idCard);	
			
			//该会员不存在
			if(null==viewUserInfo){
				return backSuccessWithCode(4);
			}
			
			int number = Integer.valueOf(numberString);
			
			//家庭成员数量错误
			if(number<0||number>CommonConstants.ASSIST_NUM){
				return backSuccessWithCode(6);
			}
			//编辑家庭成员
			operateFamliyData(request,number,idCard);
		}catch(Exception e){
			e.printStackTrace();
			return backSuccessWithCode(7);
		}
		
		return backSuccessWithCode(1);
		
	}
	
	/**
	 * 编辑家庭成员
	 */
	private void operateFamliyData(HttpServletRequest request,int number,String idCard){
		String address = request.getParameter("famliyAddress"); 
		String postCode = request.getParameter("postCode"); 
		String famliyIdOne = request.getParameter("famliyIdOne");//第一个家庭成员
		String famliyIdTwo = request.getParameter("famliyIdTwo");//第二个家庭成员
		String famliyIdThree = request.getParameter("famliyIdThree");//第三个家庭成员
		String famliyIdFour = request.getParameter("famliyIdFour");//第四个家庭成员
		String famliyIdFive = request.getParameter("famliyIdFive");//第五个家庭成员
		
		List<ViewFamliyRelationship> vss = new ArrayList<ViewFamliyRelationship>();
		for(int i=0;i<number;i++){
			vss.add(createVsData(i,request,number,idCard,postCode,address));
		}
		
		//如果5个家庭成员ID均为空 说明是保存操作 否则是更新操作
		if(StringUtils.isEmpty(famliyIdOne)&&
				StringUtils.isEmpty(famliyIdTwo)&&
				StringUtils.isEmpty(famliyIdThree)&&
				StringUtils.isEmpty(famliyIdFour)&&
				StringUtils.isEmpty(famliyIdFive)){
			
			//清空数据
			userInfoManager.clearFamliyRelationData(idCard);
			//更新数据
			userInfoManager.createFamliyRelationData(idCard, vss,postCode,address);
		}else{
			
			userInfoManager.updateFamliyRelationData(idCard, vss,postCode,address);
		}
	}
	
	/**
	 * 生成家庭成员数据
	 */
	private ViewFamliyRelationship createVsData(int i,HttpServletRequest request,
			int number,String idCard,
			String postCode,String address){
		i++;//参数序号增加一个
		ViewFamliyRelationship vs = new ViewFamliyRelationship();
		String famliyId = request.getParameter("famliyId"+i);
		String idCardFamliy = request.getParameter("idCard"+i);
		String name = request.getParameter("name"+i); 
		String relationship = request.getParameter("relationship"+i); 
		String relationshipKey = request.getParameter("relationshipKey"+i); 
		String gender = request.getParameter("gender"+i); 
		String birth = request.getParameter("birth"+i); 
		String politicalLandscape = request.getParameter("politicalLandscape"+i); 
		String politicalLandscapeKey = request.getParameter("politicalLandscapeKey"+i); 
		String age = request.getParameter("age"+i); 
		String healthy = request.getParameter("healthy"+i); 
		String insurance = request.getParameter("insurance"+i); 
		String insuranceKey = request.getParameter("insuranceKey"+i); 
		String jobAddress = request.getParameter("jobAddress"+i); 
		String salaryPersonString = request.getParameter("salaryPerson"+i); 

		Double salaryPerson = 0d;
		Integer relationshipKeyInt = 0;
		Integer politicalLandscapeKeyInt = 0;
		Integer insuranceKeyInt = 0;
		
		if(null!=relationshipKey){
			relationshipKeyInt = Integer.valueOf(relationshipKey);
		}
		
		if(null!=politicalLandscapeKey){
			politicalLandscapeKeyInt = Integer.valueOf(politicalLandscapeKey);
		}
		
		if(null!=insuranceKey){
			insuranceKeyInt = Integer.valueOf(insuranceKey);
		}
		
		if(null!=salaryPersonString){
			salaryPerson = Double.valueOf(salaryPersonString);
		}
		
	/*	System.out.println("famliyId"+i+"-----------------"+famliyId);	
		System.out.println("idCard"+i+"-----------------"+idCardFamliy);	
		System.out.println("name"+i+"-----------------"+name);
		System.out.println("birth"+i+"-----------------"+birth);
		System.out.println("gender"+i+"-----------------"+gender);
		System.out.println("politicalLandscape"+i+"-----------------"+politicalLandscape);
		System.out.println("age"+i+"-----------------"+age);
		System.out.println("healthy"+i+"-----------------"+healthy);
		System.out.println("insurance"+i+"-----------------"+insurance);
		System.out.println("jobAddress"+i+"-----------------"+jobAddress);*/
		
		vs.setFamliyId(famliyId);
		vs.setName(name);
		vs.setRelationship(relationship);
		if(!StringUtils.isEmpty(gender)){
			vs.setGender(convertGender(gender));
		}
		vs.setBirth(birth);
		vs.setPoliticalLandscape(politicalLandscape);
		vs.setIdCard(idCardFamliy);
		vs.setJobAddress(jobAddress);
		vs.setHealthy(healthy);
		vs.setFamliyAddress(address);
		vs.setFamliyNum(++number);
		vs.setPostCode(postCode);
		vs.setInsurance(insurance);
		vs.setInsuranceKey(insuranceKeyInt);
		vs.setPoliticalLandscapeKey(politicalLandscapeKeyInt);
		vs.setRelationshipKey(relationshipKeyInt);
		if(!StringUtils.isEmpty(age)){
			vs.setAge(Integer.valueOf(age));
		}
		vs.setSalaryPerson(salaryPerson);
		return vs;
	}
	
	   /**
		 * 申请帮扶
		 * 1、成功
		 * 2、身份证为空
		 * 3、身份证号码错误
		 * 4、该会员不存在
		 * 5、未知错误
		 */
		@RequestMapping(value = "applyAssist")
		@ResponseBody
		public String applyAssist(HttpServletRequest request,HttpServletResponse response){
			
			try{
				String idCard = request.getParameter("idCard"); 
				String numberString = request.getParameter("applyReason"); 
						
				//身份证为空
				if(StringUtils.isEmpty(idCard)){
					return backSuccessWithCode(2);
				}
				
				//身份证号码错误
				if(!CasUtils.idCardNumber(idCard)){
					return backSuccessWithCode(3);
				}
				
				ViewUserInfo viewUserInfo = userInfoManager.getByIdCard(idCard);	
				
				//该会员不存在
				if(null==viewUserInfo){
					return backSuccessWithCode(4);
				}
				
				ViewAssist viewAssist = new ViewAssist();
				viewAssist.setApplyName(viewUserInfo.getName());
				viewAssist.setApplyReason(numberString);
				viewAssist.setApplyDate(new Date());
				viewAssist.setIdCard(idCard);
				viewAssist.setAssistStateValue(AssistStateValue.apply);
				viewAssist.setStaticFlag(true);
				
				Assist assist = userInfoManager.applyAssist(idCard, viewAssist);
				//发送邮件
				if(null!=assist) {
					assistService.sendAssistMail(assist);
				}
			}catch(Exception e){
				e.printStackTrace();
				return backSuccessWithCode(5);
			}
		
			
			return backSuccessWithCode(1);
		}
	
}
