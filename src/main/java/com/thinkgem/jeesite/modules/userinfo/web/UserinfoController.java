package com.thinkgem.jeesite.modules.userinfo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.CommonConstants;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.famliyship.entity.Famliyrelationship;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;
import com.thinkgem.jeesite.modules.userinfo.service.UserinfoService;

/**
 * userInfoController
 * @author wzy
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/userinfo/userinfo")
public class UserinfoController extends BaseController {

	@Autowired
	private UserinfoService userinfoService;
	
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public Userinfo get(@RequestParam(required=false) String id) {
		Userinfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userinfoService.get(id);
		}
		if (entity == null){
			entity = new Userinfo();
		}
		return entity;
	}
	
	@RequiresPermissions("userinfo:userinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(Userinfo userinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Userinfo> page = systemService.findPage(new Page<Userinfo>(request, response), userinfo); 
		model.addAttribute("page", page);
		return "modules/userinfo/userinfoList";
	}
	
	@RequiresPermissions("userinfo:userinfo:view")
	@RequestMapping(value = "formDetails")
	public String formDetails(Userinfo userinfo, Model model) {
		
		List<Famliyrelationship> frs = userinfoService.findFr(userinfo);
		int frCount = 0;
		Double salayTotal = 0D;
		if(null != frs) {
			frCount = frs.size();
			model.addAttribute("famliyrelationships", frs);
			salayTotal = userinfo.getSalaryPerson();
			if(null == salayTotal) {
				salayTotal = 0.0D;
			}
			for(int i=0;i<frCount;i++){
				Famliyrelationship fr = frs.get(i);
				salayTotal +=fr.getSalaryPerson();
	    	}	
			Double salayFamliy = salayTotal/(frCount+1);
			model.addAttribute("salaryFamliy", salayFamliy);
		}
		model.addAttribute("frCount", frCount);
		model.addAttribute("userinfo", userinfo);
		return "modules/userinfo/userinfoFormDetails";
	}

	@RequiresPermissions("userinfo:userinfo:view")
	@RequestMapping(value = "form")
	public String form(Userinfo userinfo, Model model) {
		
		model.addAttribute("userinfo", userinfo);
		return "modules/userinfo/userinfoForm";
	}
	
	//保存和更新信息 验证用户信息
	private Boolean isModifyUserInfo(Model model, Userinfo userinfo) {
		
		//身份证号必须符合验证
		String idCard = userinfo.getIdCard();
		if(!CasUtils.idCardNumber(idCard)) {
			addMessage(model, CommonConstants.regexIdCard);
			return false;
		}
		
		//电话号码 符合验证
		String phoneNumber = userinfo.getPhoneNumber();
		if(null!=phoneNumber){
			if(!CasUtils.phoneNumber(phoneNumber)) {
				addMessage(model, CommonConstants.regexPhoneNumber);
				return false;
			}
		}
		
		return true;
	}

	@RequiresPermissions("userinfo:userinfo:edit")
	@RequestMapping(value = "save")
	public String save(Userinfo userinfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userinfo)){
			return form(userinfo, model);
		}
		
		if(!isModifyUserInfo(model, userinfo)) {
			return form(userinfo, model);
		}
		
		userinfoService.save(userinfo);
		addMessage(redirectAttributes, "保存会员信息成功");
		return "redirect:"+Global.getAdminPath()+"/userinfo/userinfo/?repage";
	}
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("userinfo:userinfo:batchedit")
    @RequestMapping(value = "importNew", method=RequestMethod.POST)
    public String importNew(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			String error = userinfoService.processExcel(userinfoService.excel(file));
			if(null == error) {
				addMessage(redirectAttributes, "成功导入 ");
			}else {
				addMessage(redirectAttributes, error);
			}
		}catch(Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/userinfo/userinfo/list?repage";
	}
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("userinfo:userinfo:batchedit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Userinfo> list = ei.getDataList(Userinfo.class);
			for (Userinfo userinfo : list){
				try{
					
					//身份证号必须符合验证
					String idCard = userinfo.getIdCard();
				
					if(null!=idCard) {
						if(!CasUtils.idCardNumber(idCard)) {
							failureMsg.append("<br/>身份证号 "+idCard+" 格式错误; ");
							failureNum++;
						}else if(null!=userinfoService.getByIdCard(idCard)){
							failureMsg.append("<br/>身份证号 "+idCard+" 重复; ");
							failureNum++;
						}else {
							BeanValidators.validateWithException(validator, userinfo);
							userinfoService.save(userinfo);
							successNum++;
						}
					}else {
						failureMsg.append("<br/>有空身份证号,请检查!");
						failureNum++;
					}
				
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>身份证号 "+userinfo.getIdCard()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>身份证号 "+userinfo.getIdCard()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/userinfo/userinfo/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("userinfo:userinfo:batchedit")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = CommonConstants.importFileTemplateXls;
    		List<Userinfo> list = Lists.newArrayList(); list.add(new Userinfo());
    		new ExportExcel(CommonConstants.importFileTemplateXlsTitle, Userinfo.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/userinfo/userinfo/list?repage";
    }
	
	@RequiresPermissions("userinfo:userinfo:edit")
	@RequestMapping(value = "delete")
	public String delete(Userinfo userinfo, RedirectAttributes redirectAttributes) {
		userinfoService.delete(userinfo);
		addMessage(redirectAttributes, "删除会员信息成功");
		return "redirect:"+Global.getAdminPath()+"/userinfo/userinfo/?repage";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response){
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<String> list = userinfoService.findAllIdCards();
		for (int i=0; i<list.size(); i++){
			Map<String, Object> map = Maps.newHashMap();
			map.put("name", list.get(i));
			map.put("isParent", true);
			mapList.add(map);
		}
		return mapList;
	}
	
	/**
	 * 查询性别人数
	 * @param userinfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("userinfo:userinfo:view")
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

}