package com.thinkgem.jeesite.wx.control;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.CommonConstants;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.wx.manager.AssistManager;
import com.thinkgem.jeesite.wx.view.ViewAssist;

@Controller
@RequestMapping(value = "wx/assist")
public class WxAssistController extends BaseController {
	
	@Autowired
	private AssistManager assistManager;

	private List<File> image; // 上传的文件
	
	private List<String> imageFileName; // 文件名称
	
    private List<String> imageContentType; // 文件类型

	public List<File> getImage() {
		return image;
	}

	public void setImage(List<File> image) {
		this.image = image;
	}

	public List<String> getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(List<String> imageFileName) {
		this.imageFileName = imageFileName;
	}

	public List<String> getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(List<String> imageContentType) {
		this.imageContentType = imageContentType;
	}
    
	/**
	 * 查询帮扶
	 */
 	@RequestMapping(value = "queryAssistInfo")
	@ResponseBody
  	public String queryAssistInfo(HttpServletRequest request,HttpServletResponse response){
		String idCard = request.getParameter("idCard"); 
		
		//参数为空
		if(CasUtils.isEmpty(idCard)){
			return backSuccessWithCode(2);
		}
		
		//身份证号码错误
		if(!CasUtils.idCardNumber(idCard)){
			return backSuccessWithCode(3);
		}
		
		ViewAssist viewAssist  = assistManager.queryAssistForZero(idCard);
		
		//不存在
		if(null==viewAssist){
			return backSuccessWithCode(4);
		}

		viewAssist.setCode(1);
        String jsonResult = JSONObject.toJSONString(viewAssist);
        System.out.println(jsonResult);;
		return jsonResult;
  }
 	
	
	/**
  	 * 查询帮扶状态
  	 */
 	@RequestMapping(value = "queryAssistState")
	@ResponseBody
    public String queryAssistState(HttpServletRequest request,HttpServletResponse response){
		String idCard = request.getParameter("idCard"); 
		
		//参数为空
		if(CasUtils.isEmpty(idCard)){
			return backSuccessWithCode(2);
		}
		
		//身份证号码错误
		if(!CasUtils.idCardNumber(idCard)){
			return backSuccessWithCode(3);
		}
		
		ViewAssist viewAssist  = assistManager.queryAssistForZero(idCard);
		
		//不存在
		if(null==viewAssist){
			return backSuccessWithCode(4);
		}


		Map<String,String> map = new HashMap<String,String>();
		map.put("code", "1");
		map.put("assistValue", String.valueOf(viewAssist.getAssistStateValue()));
		String jsonResult = JSONObject.toJSONString(map);
		return jsonResult;
    }
 	
 	/**
	 * 删除帮扶附件照片
	 */
 	@RequestMapping(value = "removeAssistExtraFile")
	@ResponseBody
	public String removeAssistExtraFile(HttpServletRequest request,HttpServletResponse response){
		int returnCode = 1;
		try {
			request.setCharacterEncoding("UTF-8");
			String idCard = request.getParameter("idCard"); 
			//身份证为空
			if(CasUtils.isEmpty(idCard)){
				returnCode = 2;
				return backSuccessWithCode(returnCode);
			}
			String dirParam = headPhotoPath(idCard);
			if(null == dirParam){
				returnCode = 3;
				return backSuccessWithCode(returnCode);
			}
			File dir = new File(dirParam);
			if(dir.exists()){
				CasUtils.deleteDir(dir);//如果存在删除头像目录
			}
			CasUtils.judeDirExists(dir);//创建目录
		} catch (UnsupportedEncodingException e) {
			returnCode = 4;
			e.printStackTrace();
		}
		return backSuccessWithCode(returnCode);
	}
 	
 	
 	
 	/**
  	 * 上传帮扶附件
  	 */
 	@RequestMapping(value = "uploadManyImages")
	@ResponseBody
	public String uploadManyImages(HttpServletRequest request,HttpServletResponse response){
		int returnCode = 1;
		try {
				request.setCharacterEncoding("UTF-8");
				//获取用户的身份证ID
				String idCard = request.getParameter("idCard"); 
				if(null == idCard){
					returnCode = 2;
					return backSuccessWithCode(returnCode);
				}
				String dirParam = headPhotoPath(idCard);
				if(null == dirParam){
					returnCode = 3;
					return backSuccessWithCode(returnCode);
				}
				File dir = new File(dirParam);
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
			System.out.println("上传多个图片出错");
			returnCode = 4;
			e.printStackTrace();
		}
		return backSuccessWithCode(returnCode);
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
	
}
