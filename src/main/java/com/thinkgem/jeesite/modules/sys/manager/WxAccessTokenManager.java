package com.thinkgem.jeesite.modules.sys.manager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.WxUrlUtils;

import net.sf.json.JSONObject;

/**
 * 微信调用接口凭证保存
 * @author wzy
 *
 */
public class WxAccessTokenManager {
	
	 private static final String appId = "wx766175b073e94a7d";
	 
	 private static final String appSecret = "c47438ccab70251b579f4a579a223193";
	 
	 private static String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	 private Map<String, String> map = new HashMap<>();
	 
	 private static WxAccessTokenManager single = null;
	 
	 public WxAccessTokenManager() {}
	 
	 //单例
	 public static synchronized WxAccessTokenManager getInstance() {
		 if(null == single) {
			 single = new WxAccessTokenManager();
		 }
		 return single;
	 }
	
	 //获取access_token
	 public String getAccessToken() {
		 String retAccessToken = null;
		 String time = map.get("time");
         String accessToken = map.get("access_token");
         Long nowDate = new Date().getTime();
         if (accessToken != null && time != null && nowDate - Long.parseLong(time) < 7000 * 1000) {   
              System.out.println("accessToken 存在，且没有超时 ， 返回单例" + accessToken);  
              retAccessToken = accessToken;
         }else {
        	  System.out.println("start access_token");
        	  System.out.println("accessToken 超时 ， 或者不存在 ， 重新获取");
        	  String url = String.format(requestUrl,appId,appSecret);
        	  System.out.println("格式化Url:" + url); 
        	  JSONObject jsonObject = WxUrlUtils.httpRequest(url,Global.GET_METHOD,null); 
        	  if(null != jsonObject) {
        		  retAccessToken = jsonObject.getString("access_token");
            	  map.put("time", nowDate + "");
            	  map.put("access_token", retAccessToken);
            	  System.out.println("access_token" + retAccessToken);  
        	  }else {
        		  System.out.println("jsonObject is null");  
        	  }
        	  System.out.println("end access_token");  
        	
         }
		 return retAccessToken;
	 }
	 
}
