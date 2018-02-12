package com.thinkgem.jeesite.modules.sys.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.assist.entity.Assist;
import com.thinkgem.jeesite.modules.assist.service.AssistService;

/**
 * 控系统处理
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/system")
public class SystemControl extends BaseController {

	@Autowired
	private AssistService assistService;
	
	private String MMMdyyy = "MMMddyyyy";
	private String yyyymmdd = "yyyy-MMM-dd";
	private String EEEMMMDDD = "EEE MMM dd HH:mm:ss z yyyy";
	
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
