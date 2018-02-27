package com.thinkgem.jeesite.modules.news.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
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
import com.thinkgem.jeesite.modules.news.entity.News;
import com.thinkgem.jeesite.modules.news.service.NewsService;

/**
 * newsController
 * @author wzy
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/news/news")
public class NewsController extends BaseController {

	@Autowired
	private NewsService newsService;
	
	@ModelAttribute
	public News get(@RequestParam(required=false) String id) {
		News entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = newsService.get(id);
		}
		if (entity == null){
			entity = new News();
		}
		return entity;
	}
	
	@RequiresPermissions("news:news:view")
	@RequestMapping(value = {"list", ""})
	public String list(News news, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<News> page = newsService.findPage(new Page<News>(request, response), news); 
		model.addAttribute("page", page);
		return "modules/news/newsList";
	}

	@RequiresPermissions("news:news:view")
	@RequestMapping(value = "form")
	public String form(News news, Model model) {
		model.addAttribute("news", news);
		return "modules/news/newsForm";
	}

	@RequiresPermissions("news:news:edit")
	@RequestMapping(value = "save")
	public String save(News news, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, news)){
			return form(news, model);
		}
		String content = news.getConstants();
		if(null!=content) {
			news.setConstants(StringEscapeUtils.unescapeHtml4(content.trim()));
		}
		newsService.save(news);
		addMessage(redirectAttributes, "保存新闻信息成功");
		return "redirect:"+Global.getAdminPath()+"/news/news/list";
	}
	
	@RequiresPermissions("news:news:edit")
	@RequestMapping(value = "delete")
	public String delete(News news, RedirectAttributes redirectAttributes) {
		newsService.delete(news);
		addMessage(redirectAttributes, "删除新闻信息成功");
		return "redirect:"+Global.getAdminPath()+"/news/news/list";
	}

}