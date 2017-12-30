package com.thinkgem.jeesite.wx.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.wx.manager.UserInfoManager;

@Controller
@RequestMapping(value = "wx/fr")
public class WxFamliyRelationShipController extends BaseController {

	@Autowired
	private UserInfoManager userInfoManager;
	
	/**
	 * 身份查询
	 */
	@RequestMapping(value = "queryFamliyRelationIdCard")
	@ResponseBody
	public String queryFamliyRelationIdCard(HttpServletRequest request,HttpServletResponse response){
		
		String idCard = request.getParameter("idCard"); 
		//参数为空
		if(CasUtils.isEmpty(idCard)){
			return backSuccessWithCode(2);
		}
		
		//身份证号码错误
		if(!CasUtils.idCardNumber(idCard)){
			return backSuccessWithCode(3);
		}
		//如果参数没有问题 查询数据
		String  jsonResult = userInfoManager.queryFrByIdCardForJson(idCard);	
		
		if(null==jsonResult){
			//该会员不存在
			return backSuccessWithCode(4);
		}

        System.out.println(jsonResult);;
        return jsonResult;
	}
	
}
