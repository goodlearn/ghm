/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ce.web;

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
import com.thinkgem.jeesite.modules.ce.entity.Communityemail;
import com.thinkgem.jeesite.modules.ce.service.CommunityemailService;

/**
 * ceController
 * @author wzy
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/ce/communityemail")
public class CommunityemailController extends BaseController {

	@Autowired
	private CommunityemailService communityemailService;
	
	@ModelAttribute
	public Communityemail get(@RequestParam(required=false) String id) {
		Communityemail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = communityemailService.get(id);
		}
		if (entity == null){
			entity = new Communityemail();
		}
		return entity;
	}
	
	@RequiresPermissions("ce:communityemail:view")
	@RequestMapping(value = {"list", ""})
	public String list(Communityemail communityemail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Communityemail> page = communityemailService.findPage(new Page<Communityemail>(request, response), communityemail); 
		model.addAttribute("page", page);
		return "modules/ce/communityemailList";
	}

	@RequiresPermissions("ce:communityemail:view")
	@RequestMapping(value = "form")
	public String form(Communityemail communityemail, Model model) {
		model.addAttribute("communityemail", communityemail);
		return "modules/ce/communityemailForm";
	}
	
	@RequiresPermissions("ce:communityemail:view")
	@RequestMapping(value = "formShow")
	public String formShow(Communityemail communityemail, Model model) {
		model.addAttribute("communityemail", communityemail);
		return "modules/ce/communityemailFormShow";
	}


	@RequiresPermissions("ce:communityemail:edit")
	@RequestMapping(value = "save")
	public String save(Communityemail communityemail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, communityemail)){
			return form(communityemail, model);
		}
		communityemailService.save(communityemail);
		addMessage(redirectAttributes, "保存社区邮箱成功");
		return "redirect:"+Global.getAdminPath()+"/ce/communityemail/?repage";
	}
	
	@RequiresPermissions("ce:communityemail:edit")
	@RequestMapping(value = "delete")
	public String delete(Communityemail communityemail, RedirectAttributes redirectAttributes) {
		communityemailService.delete(communityemail);
		addMessage(redirectAttributes, "删除社区邮箱成功");
		return "redirect:"+Global.getAdminPath()+"/ce/communityemail/?repage";
	}

}