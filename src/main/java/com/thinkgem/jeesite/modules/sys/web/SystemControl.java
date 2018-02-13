package com.thinkgem.jeesite.modules.sys.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.assist.entity.Assist;
import com.thinkgem.jeesite.modules.assist.service.AssistService;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;
import com.thinkgem.jeesite.modules.userinfo.service.UserinfoService;
import com.thinkgem.jeesite.modules.views.ViewUserinfoAssistCount;

/**
 * 控系统处理
 *
 */
@Controller
@RequestMapping(value = "/system")
public class SystemControl extends BaseController {

	@Autowired
	private AssistService assistService;
	@Autowired
	private UserinfoService userinfoService;
	
	private String MMMdyyy = "MMMddyyyy";
	private String yyyymmdd = "yyyy-MMM-dd";
	private String EEEMMMDDD = "EEE MMM dd HH:mm:ss z yyyy";
	
	@RequestMapping(value = "findAucp")
	public String findAucp() {
		return "modules/assist/assistUserinfo";
	}
	
	@RequestMapping(value = "findAuc")
	@ResponseBody
	public String findAuc() {
		List<ViewUserinfoAssistCount> list = new ArrayList<ViewUserinfoAssistCount>();
		ViewUserinfoAssistCount vugc1 = new ViewUserinfoAssistCount();
		vugc1.setType("总帮扶数量");
		vugc1.setNum(assistService.findCount());
		ViewUserinfoAssistCount vugc2 = new ViewUserinfoAssistCount();
		vugc2.setType("困难职工数量");
		vugc2.setNum(assistService.findDistinctCount());
		ViewUserinfoAssistCount vugc3 = new ViewUserinfoAssistCount();
		vugc3.setType("总会员数量");
		vugc3.setNum(userinfoService.findCount());
		list.add(vugc1);
		list.add(vugc2);
		list.add(vugc3);
		String jsonResult = JSONObject.toJSONString(list);//将map对象转换成json类型数据
		return jsonResult;
	}
	
	@RequestMapping(value = "findGenderCountPage")
	public String findGenderCountPage() {
		return "modules/userinfo/userinfoGender";
	}
	
	/**
	 * 依据性别查询会员数
	 */
	@RequestMapping(value = "findGenderCount")
	@ResponseBody
	public String findGenderCount() {
		Userinfo userinfo = new Userinfo();
		logger.info("总人数:"+userinfoService.findGenderCount(userinfo));
		userinfo.setGender(Boolean.TRUE);
		logger.info("男人数:"+userinfoService.findGenderCount(userinfo));
		userinfo.setGender(Boolean.FALSE);
		logger.info("女人数:"+userinfoService.findGenderCount(userinfo));
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("totalP", userinfoService.findCount());
		userinfo.setGender(Boolean.TRUE);
		map.put("manP", userinfoService.findGenderCount(userinfo));
		userinfo.setGender(Boolean.FALSE);
		map.put("womanP", userinfoService.findGenderCount(userinfo));
		userinfo.setGender(null);
		map.put("undefinedP", userinfoService.findGenderCount(userinfo));
		String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
		return jsonResult;
	}
	/**
	 * 数据库中Assist的applyDate中数据存放格式为Apr 1, 2017,改变为2017-12-05 09:13:49格式
	 */
	@RequestMapping(value = "convertAssistApplyDate")
	public void convertAssistApplyDate() {
		try {
			Assist entity = new Assist();
			entity.setAssistState(null);
			List<Assist> assists = assistService.findList(entity);
			for(Assist assist : assists) {
				String applyDateString = assist.getApplyDate().toString();
				logger.info("时间数据转换前:"+applyDateString);
				applyDateString = getSpiltString(applyDateString);
				logger.info("时间数据转换后:"+applyDateString);
				SimpleDateFormat sdf = null;
				sdf = new SimpleDateFormat(yyyymmdd,Locale.ENGLISH);
				Date date = sdf.parse(applyDateString);
				assist.setApplyDate(date);
				assistService.save(assist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("转换完成");
	}
	
	//看是不是有逗号，如果有逗号就劈分以下比如：Feb 24, 2017
	private String getSpiltString(String date) {
		String ret = null;
		String[] results = date.split(",");
		if(results.length > 1) {
			String[] temps = results[0].split(" ");
			ret = results[1].trim() +"-"+ temps[0] +"-"+temps[1];
		}else {
			ret = date;
		}
		return ret;
	}
	
	// 检查日期格式 MM D,yyyy
	public boolean checkDateMMDYYYY(String sourceDate) {
		if (sourceDate == null || sourceDate.trim().length() == 0) {
			return false;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(MMMdyyy);
		try {
			dateFormat.setLenient(false);
			dateFormat.parse(sourceDate);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// 检查日期格式 YYYY-MM-DD
	public boolean checkDateYYYYMMDD(String sourceDate) {
		if (sourceDate == null || sourceDate.trim().length() == 0) {
			return false;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(yyyymmdd);
		try {
			dateFormat.setLenient(false);
			dateFormat.parse(sourceDate);
			return true;
		} catch (Exception e) {
			return false;
		}
	}  
	
	// 检查日期格式 EEE MMM dd HH:mm:ss z yyyy
	public boolean checkDateEEEMMMDDHHMMSSZYYYY(String sourceDate) {
		if (sourceDate == null || sourceDate.trim().length() == 0) {
			return false;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(EEEMMMDDD);
		try {
			dateFormat.setLenient(false);
			dateFormat.parse(sourceDate);
			return true;
		} catch (Exception e) {
			return false;
		}
	} 
	
}
