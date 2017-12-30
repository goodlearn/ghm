package com.thinkgem.jeesite.wx.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.CommonConstants;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.news.entity.News;
import com.thinkgem.jeesite.modules.news.service.NewsService;
import com.thinkgem.jeesite.modules.resourceType.entity.ResourceType;
import com.thinkgem.jeesite.modules.resourceType.service.ResourceTypeService;
import com.thinkgem.jeesite.wx.manager.NewsManager;
import com.thinkgem.jeesite.wx.view.ViewNews;

@Controller
@RequestMapping(value = "wx/news")
public class WxNewsController extends BaseController {

	@Autowired
	private NewsManager newsManager;
	
	@Autowired
	private ResourceTypeService resourceTypeService;
	
	/**
	 * 每次请求滚动图片的个数
	 */
	private final short topImageNum = 3;
	
	/**
	 *帮扶状态图片起始个数
	 */
	private final short startAssistImage = 6;
	

	/**
	 *帮扶状态图片结束个数
	 */
	private final short endAssistImage = 17;


	public short getTopImageNum() {
		return topImageNum;
	}


	public short getStartAssistImage() {
		return startAssistImage;
	}


	public short getEndAssistImage() {
		return endAssistImage;
	}
	
	/**
	 * 返回成功数据 携带检验码
	 */
	private String backSuccessWithCode(int code,String retMsg){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		 map.put("retMsg", retMsg);
		String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
		return jsonResult;
	}
	
	/**
	 * 请求背景图片
	 * @return
	 */
	@RequestMapping(value = "getBackImages",method=RequestMethod.GET)
	@ResponseBody
	public String getBackImages(HttpServletRequest request,HttpServletResponse response){
		String backImageType = request.getParameter("backImageType"); 
		//参数为空
		if(CasUtils.isEmpty(backImageType)){
			return backSuccessWithCode(2,"参数为空");
		}
		
		ResourceType rt = new ResourceType();
		rt.setType(Integer.valueOf(backImageType));
		List<ResourceType> vrts = resourceTypeService.findList(rt);
		if(null == vrts || vrts.size()<=0){
			//不存在
			return backSuccessWithCode(4,"不存在");
		}else{
			ResourceType vrt = vrts.get(0);//第一个
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("code", 1);
			map.put("imgUrl", vrt.getSourcePath());
			String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
			return jsonResult;
		}
	}
	
	/**
	 * 查询图文
	 */
	@RequestMapping(value = "queryNewsById",method=RequestMethod.GET)
	@ResponseBody
	public String queryNewsById(HttpServletRequest request,HttpServletResponse response){
		String newsId = request.getParameter("newsId"); 
		//参数为空
		if(CasUtils.isEmpty(newsId)){
			return backSuccessWithCode(2,"参数为空");
		}
		
		
		News vnEntity = newsManager.get(newsId);
		//数据成功
		if(null == vnEntity){
			//不存在
			return backSuccessWithCode(4,"不存在");
		}else{
			ViewNews vn = new ViewNews();
			vn.convertToVoModel(vnEntity);
			vn.setCode(1);
			String jsonResult = JSONObject.toJSONString(vn);//将对象转换成json类型数据
			return jsonResult;
		}
	}

	/**
	 * 查询图文
	 */
	@RequestMapping(value = "queryNews")
	@ResponseBody
	public String queryNews(News news, HttpServletRequest request, HttpServletResponse response, Model model){
		
		String firstResult = request.getParameter("pageNo"); //第几页
		String maxResult = request.getParameter("pageSize"); //每页几个
		
		
		//参数为空
		if(CasUtils.isEmpty(firstResult)){
			
			return backSuccessWithCode(2,"参数为空");
		}
		//参数为空
		if(CasUtils.isEmpty(maxResult)){
			return backSuccessWithCode(2,"参数为空");
		}
		
		news.setType(CommonConstants.firstNews);
		
		Page<News> page = newsManager.findPage(new Page<News>(request, response), news); 
		
		page = isLastPageData(page,Integer.valueOf(firstResult));
		
		//数据成功
		if(null == page||null == page.getList()){
			//不存在
			return backSuccessWithCode(4,"不存在");
		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("code", 1);
			List<News> pageNews = page.getList();
			List<ViewNews> viewNewss = new ArrayList<ViewNews>();
			for(News forNews:pageNews) {
				ViewNews viewNews = new ViewNews();
				viewNews.convertToVoModel(forNews);
				viewNewss.add(viewNews);
			}
			map.put("viewNews", viewNewss);
			String jsonResult = JSONObject.toJSONString(map,SerializerFeature.WriteMapNullValue);
			return jsonResult;
		}
	}
	
	private Page<News> isLastPageData(Page<News> page,int pageNo) {
		//最后一页判断
		/**
		 * 因为分页中如果pageNo超过最后一页，机制是默认返回第一页的数据，针对这种情况
		 * 如果是最后一页，看页码是否超过最后一页，如果超过那么返回空值
		 */
		if(page.isLastPage()) {
			int lastPage = page.getLast();
			if(pageNo>=lastPage) {
				return null;
			}
		}
		
		return page;
		
	}
	
	/**
	 * 查询顶端图片
	 * @return
	 */
	@RequestMapping(value = "queryTopImages",method=RequestMethod.GET)
	@ResponseBody
	public String queryTopImages(News news,HttpServletRequest request,HttpServletResponse response){
		news.setType(CommonConstants.scrollNews);
		List<News> pageNews = newsManager.findList(news);		//数据成功
		if(null == pageNews){
			//不存在
			return backSuccessWithCode(4,"不存在");
		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("code", 1);
			List<ViewNews> viewNewss = new ArrayList<ViewNews>();
			for(News forNews:pageNews) {
				ViewNews viewNews = new ViewNews();
				viewNews.convertToVoModel(forNews);
				viewNewss.add(viewNews);
			}
			map.put("viewNews", viewNewss);
			String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
			return jsonResult;
		}
	}
	
	/**
	 * 请求帮扶状态图片
	 */
	@RequestMapping(value = "getAssitsImages",method=RequestMethod.GET)
	@ResponseBody
	public String getAssitsImages(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		for(int i=startAssistImage;i<endAssistImage;i++){
			ResourceType rt = new ResourceType();
			rt.setType(i);
			List<ResourceType> vrts = resourceTypeService.findList(rt);
			if(null != vrts && vrts.size()>0){
				ResourceType vrt = vrts.get(0);//第一个
				map.put("imgUrl"+i, vrt.getSourcePath());
			}
		}
		map.put("code", 1);
		String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
		return jsonResult;
		
	}
	
}
