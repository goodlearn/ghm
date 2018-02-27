package com.thinkgem.jeesite.modules.assist.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.BasePathUtils;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.CommonConstants;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.assist.entity.Assist;
import com.thinkgem.jeesite.modules.assist.service.AssistService;
import com.thinkgem.jeesite.modules.famliyship.entity.Famliyrelationship;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;
import com.thinkgem.jeesite.modules.userinfo.service.UserinfoService;
import com.thinkgem.jeesite.modules.views.ViewUserinfoAssistCount;

/**
 * assistController
 * @author wzy
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/assist/assist")
public class AssistController extends BaseController {

	@Autowired
	private AssistService assistService;
	@Autowired
	private UserinfoService userinfoService;
	
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public Assist get(@RequestParam(required=false) String id,@RequestParam(required=false) String userInfoIdCard) {
		Assist entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = assistService.get(id);
		}
		if (entity == null){
			entity = new Assist();
		}
		return entity;
	}
	
	@RequiresPermissions("assist:assist:view")
	@RequestMapping(value = {"list", ""})
	public String list(Assist assist, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		String communityKey = user.getCommunityKey();
		if(StringUtils.isNotEmpty(communityKey)) {
			assist.setQueryCnk(communityKey);
		}
		Page<Assist> page = systemService.findPage(new Page<Assist>(request, response), assist); 
		model.addAttribute("page", page);
		return "modules/assist/assistList";
	}

	@RequiresPermissions("assist:assist:view")
	@RequestMapping(value = "form")
	public String form(Assist assist, Model model) {
		model.addAttribute("assist", assist);
		return "modules/assist/assistForm";
	}
	
	//验证数据
	private boolean isSendMail(Assist assist, Model model) {
		Userinfo userinfo = assistService.findUserinfo(assist);
		if(null == userinfo) {
			addMessage(model, CommonConstants.noUserInfo);
			return false;
		}
		
		Integer communityKey = userinfo.getCommunityKey();//社区的键值 用来查询邮箱地址
		if(null == communityKey) {
			addMessage(model, CommonConstants.noUserInfoCommunity);
			return false;
		}
		
		
		return true;
	}
	
	@RequiresPermissions("assist:assist:view")
	@RequestMapping(value = "sendMail")
	public String sendMail(Assist assist, Model model) {
		
		//数据验证
		if(isSendMail(assist,model)) {
			assistService.sendAssistMail(assist);
		}
		return "redirect:"+Global.getAdminPath()+"/assist/assist/?repage";
	}
	
	private String headPhotoPath(String idCard){
    	StringBuilder sb;
		try {
			sb = new StringBuilder(getSavePath(CommonConstants.fileDir));
			sb.append(File.separator);
			sb.append(idCard);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	 }
	
	 /**
     * 返回上传文件保存的位置
     * 
     * @return
     * @throws Exception
     */
    private String getSavePath(String savePath) throws Exception {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(savePath);
    }
	
	/**
  	 * 上传帮扶附件
  	 */
    @RequiresPermissions("assist:assist:view")
 	@RequestMapping(value = "uploadManyImages")
	public String uploadManyImages(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		int returnCode = 1;
		try {
				request.setCharacterEncoding("UTF-8");
				//获取用户的身份证ID
				String idCard = request.getParameter("idCard"); 
				if(CasUtils.isEmpty(idCard)){
					returnCode = 2;
					addMessage(redirectAttributes, "空身份证信息！");
					return "redirect:" + adminPath + "/assist/assist/list?repage";
				}
				String dirParam = headPhotoPath(idCard);
				if(null == dirParam){
					returnCode = 3;
					addMessage(redirectAttributes, "空目录！");
					return "redirect:" + adminPath + "/assist/assist/list?repage";
				}
				File dir = new File(dirParam);
				
				CasUtils.deleteDir(dir);
				
				if(!dir.exists()){
					CasUtils.judeDirExists(dir);//创建目录
				}
		         CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
				 if(multipartResolver.isMultipart(request)){
					  //将request变成多部分request
	                 MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
	                 //获取multiRequest 中所有的文件名
	                 Iterator<String> iter=multiRequest.getFileNames();
	                 while(iter.hasNext()){
	                	 MultipartFile file=multiRequest.getFile(iter.next().toString());
	                     if(file!=null)
	                     {
	                    	   //上传
	                    	  String fileName = UUID.randomUUID().toString();
	                          file.transferTo(new File(dirParam,fileName + ".jpeg"));
	                     }
	                 }
				 }
		} catch (Exception e) {
			addMessage(redirectAttributes, "上传多个图片出错！");
			System.out.println("上传多个图片出错");
			returnCode = 4;
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/assist/assist/list?repage";
	}
 	
	
	@RequiresPermissions("assist:assist:view")
	@RequestMapping(value = "downloadPhoto")
	public String downloadPhoto(Assist assist, Model model,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		try {
			File filename = assistService.downloadPhoto(assist);
			if(null == filename) {
				addMessage(redirectAttributes, "无附件");
				return null;
			}
			response.reset();
	        response.setContentType("application/octet-stream; charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(filename.getName()));
	        //打开本地文件流
	        InputStream inputStream = new FileInputStream(filename);
	        //激活下载操作
	        OutputStream os = response.getOutputStream(); 
	        //循环写入输出流
	        byte[] b = new byte[2048];
	        int length;
	        while ((length = inputStream.read(b)) > 0) {
	            os.write(b, 0, length);
	        }

	        // 这里主要关闭。
	        os.close();
	        inputStream.close();
	        return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/assist/assist/formDetails?repage";
	}
	

	@RequiresPermissions("assist:assist:view")
	@RequestMapping(value = "downloadWord")
	public String downloadWord(Assist assist, Model model,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		try {
			String filename = assistService.downloadWord(assist);
			response.reset();
	        response.setContentType("application/octet-stream; charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(filename));
	        //打开本地文件流
	        InputStream inputStream = new FileInputStream(filename);
	        //激活下载操作
	        OutputStream os = response.getOutputStream(); 
	        //循环写入输出流
	        byte[] b = new byte[2048];
	        int length;
	        while ((length = inputStream.read(b)) > 0) {
	            os.write(b, 0, length);
	        }

	        // 这里主要关闭。
	        os.close();
	        inputStream.close();
	        return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/assist/assist/formDetails?repage";
	}
	
	@RequiresPermissions("assist:assist:view")
	@RequestMapping(value = "formDetails")
	public String formDetails(Assist assist, Model model) {
		Role role = UserUtils.getUser().getMaxRole();
		String roleType = role.getRoleType();
		Userinfo userinfo = assistService.findUserinfo(assist);
		List<Famliyrelationship> frs = assistService.findFr(assist);
		int frCount = 0;
		double salayTotal = 0;
		if(null != frs) {
			frCount = frs.size();
			model.addAttribute("famliyrelationships", frs);
			salayTotal = userinfo.getSalaryPerson();
			for(int i=0;i<frCount;i++){
				Famliyrelationship fr = frs.get(i);
				salayTotal +=fr.getSalaryPerson();
	    	}	
			Double salayFamliy = salayTotal/(frCount+1);
			model.addAttribute("salaryFamliy", salayFamliy);
		}
		String showButtolWord = showButton(roleType,assist);
		if(null != showButtolWord) {
			model.addAttribute("showButtolWord", showButtolWord);
		}
		model.addAttribute("frCount", frCount);
		model.addAttribute("assist", assist);
		model.addAttribute("userinfo", userinfo);
		
		
		return "modules/assist/assistFormDetails";
	}
	
	//根据帮扶管理人员的权限 展示的按钮文字不同
	private String showButton(String roleType,Assist assist) {
		Integer assistState = assist.getAssistState();
		String ret = null;
		switch(roleType) {
			case "1":
				//社区
			case "2":
				ret = "上报";
				//街道
				break;
			case "3":
				//工会
				if(assistState.intValue() == 2) {
					//已报送
					ret = "通过";
				}else if(assistState.intValue() == 3) {
					//通过
					ret = "发放";
				}
				break;
			default:
				break;
		}
		return ret;
	}
	
	@RequiresPermissions("assist:assist:editState")
	@RequestMapping(value = "editState")
	public String editState(Assist assist, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, assist)){
			return form(assist, model);
		}
		Role role = UserUtils.getUser().getMaxRole();
		String roleType = role.getRoleType();
		Integer assistState = assist.getAssistState();
		boolean isPass = false;
		switch(roleType) {
			case "1":
				//社区
				if(assistState.intValue() != 0) {
					addMessage(redirectAttributes, "上报状态不符合");
				}else {
					isPass = true;
				}
				break;
			case "2":
				//街道
				if(assistState.intValue() != 1) {
					addMessage(redirectAttributes, "上报状态不符合");
				}else {
					isPass = true;
				}
				break;
			case "3":
				//工会
				if(assistState.intValue() == 2
						||assistState.intValue() == 3) {
					isPass = true;
				}else {
					addMessage(redirectAttributes, "帮扶状态不符合");
				}
				break;
			default:
				break;
		}
		if(isPass) {
			assist.setAssistState(++assistState);
			assistService.save(assist);
			addMessage(redirectAttributes, "操作成功");
		}
		return "redirect:"+Global.getAdminPath()+"/assist/assist/?repage";
	}

	@RequiresPermissions("assist:assist:edit")
	@RequestMapping(value = "save")
	public String save(Assist assist, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, assist)){
			return form(assist, model);
		}
		assistService.save(assist);
		addMessage(redirectAttributes, "保存帮扶状态成功");
		return "redirect:"+Global.getAdminPath()+"/assist/assist/?repage";
	}
	
	@RequiresPermissions("assist:assist:edit")
	@RequestMapping(value = "delete")
	public String delete(Assist assist, RedirectAttributes redirectAttributes) {
		assistService.delete(assist);
		addMessage(redirectAttributes, "删除帮扶状态成功");
		return "redirect:"+Global.getAdminPath()+"/assist/assist/?repage";
	}
	
	//获取帮扶会员数量图
	@RequiresPermissions("assist:assist:view")
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
	
	/**
	 * 预览附件
	 * @param assist
	 * @param model
	 * @return
	 */
	@RequiresPermissions("assist:assist:view")
	@RequestMapping(value = "preview")
	public String preview(Assist assist, Model model,HttpServletRequest request, HttpServletResponse response) {
	    String httpProtocol = DictUtils.getDictValue("httpProtocol", "systemControl", "http");
	    String url = BasePathUtils.getBasePath(request);
	    if("https".equals(httpProtocol)) {
	    	url = url.replace("http", "https");
	    }
	    
	    Long userinfoId = assist.getUserinfoId();
	    Userinfo userinfo = userinfoService.get(userinfoId.toString());
	    String idCard = userinfo.getIdCard();
	    String dirParam = headPhotoPath(idCard);
	    File dir = new File(dirParam);
	    if(dir.isDirectory()) {
	    	File[] listFiles = dir.listFiles();
	    	List<String> urlList = null;
	    	if(listFiles.length > 0) {
	    		urlList = new ArrayList<String>();
	    		for(File file : listFiles) {
		    		String tempUrl = url +"images/" + idCard + "/" + file.getName();
		    		urlList.add(tempUrl);
		    	}
	    		model.addAttribute("urlList",urlList);
	    	}else {
	    		assist.setNoSlashShow("无帮扶附件");
	    	}
	    }else {
	    	assist.setNoSlashShow("无帮扶附件");
	    }
		model.addAttribute("assist", assist);
		return "modules/assist/assistSlash";
	}
}