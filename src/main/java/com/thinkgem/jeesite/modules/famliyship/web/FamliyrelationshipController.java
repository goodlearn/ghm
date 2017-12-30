/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.famliyship.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.famliyship.entity.Famliyrelationship;
import com.thinkgem.jeesite.modules.famliyship.service.FamliyrelationshipService;
import com.thinkgem.jeesite.modules.views.ViewFrs;

/**
 * famliyshipController
 * @author wzy
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/famliyship/famliyrelationship")
public class FamliyrelationshipController extends BaseController {

	@Autowired
	private FamliyrelationshipService famliyrelationshipService;
	
	@ModelAttribute
	public Famliyrelationship get(@RequestParam(required=false) String id,@RequestParam(required=false) String userInfoIdCard) {
		Famliyrelationship entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = famliyrelationshipService.get(id);
		}
		if (entity == null){
			entity = new Famliyrelationship();
		}
		
		if (StringUtils.isNotBlank(userInfoIdCard)){
			entity.setUserinfoId(Long.valueOf(userInfoIdCard));
		}
		
		return entity;
	}
	
	@RequiresPermissions("famliyship:famliyrelationship:view")
	@RequestMapping(value = {"list", ""})
	public String list(Famliyrelationship famliyrelationship, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Famliyrelationship> page = famliyrelationshipService.findPage(new Page<Famliyrelationship>(request, response), famliyrelationship); 
		model.addAttribute("page", page);
		return "modules/famliyship/famliyrelationshipList";
	}

	@RequiresPermissions("famliyship:famliyrelationship:view")
	@RequestMapping(value = "form")
	public String form(Famliyrelationship famliyrelationship, Model model) {
		
		//补充会员信息
		famliyrelationshipService.setUserInfo(famliyrelationship);
		
		//页面传值
		model.addAttribute("famliyrelationship", famliyrelationship);
		return "modules/famliyship/famliyrelationshipForm";
	}

	@RequiresPermissions("famliyship:famliyrelationship:edit")
	@RequestMapping(value = "save")
	public String save(Famliyrelationship famliyrelationship, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, famliyrelationship)){
			return form(famliyrelationship, model);
		}
		famliyrelationshipService.supplyAddData(famliyrelationship);
		famliyrelationshipService.save(famliyrelationship);
		addMessage(redirectAttributes, "保存家庭信息成功");
		return "redirect:"+Global.getAdminPath()+"/famliyship/famliyrelationship/?repage";
	}
	
	@RequiresPermissions("famliyship:famliyrelationship:edit")
	@RequestMapping(value = "delete")
	public String delete(Famliyrelationship famliyrelationship, RedirectAttributes redirectAttributes) {
		famliyrelationshipService.delete(famliyrelationship);
		addMessage(redirectAttributes, "删除家庭信息成功");
		return "redirect:"+Global.getAdminPath()+"/famliyship/famliyrelationship/?repage";
	}

}