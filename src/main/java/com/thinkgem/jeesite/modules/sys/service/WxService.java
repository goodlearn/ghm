package com.thinkgem.jeesite.modules.sys.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.entity.wx.WechatMsg;
import com.thinkgem.jeesite.modules.sys.entity.wx.WechatTextMsg;
import com.thoughtworks.xstream.XStream;

/**
 * 微信信息处理
 * @author wzy
 *
 */
@Service
public class WxService extends BaseService implements InitializingBean {
	
	private static String WX_TOKEN = "KDTEST";

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	//处理微信消息
	public String WxPostMsgProcess(HttpServletRequest request) throws Exception{
		
		String respMessage = null;
		
		//xml解析
		Map<String, String> requestMap = xmlToMap(request); 
		
		//解析公共消息部分
		// 发送方帐号（open_id）
        String fromUserName = requestMap.get("FromUserName");
        // 公众帐号
        String toUserName = requestMap.get("ToUserName");
        // 消息类型
        String msgType = requestMap.get("MsgType");
        // 消息内容
        String content = requestMap.get("Content");
        
        logger.info("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType);
		
        //文本消息
        if (msgType.equals(Global.WX_REQ_MESSAGE_TYPE_TEXT)) {
        	WechatTextMsg wechatMsg = new WechatTextMsg();
        	wechatMsg.setContent("任哲是" + content);
        	wechatMsg.setToUserName(fromUserName);
        	wechatMsg.setFromUserName(toUserName);
        	wechatMsg.setCreateTime(new Date().getTime() + "");
        	wechatMsg.setMsgType(msgType);
        	respMessage = messageToXml(wechatMsg);
        	logger.info(respMessage);
        } 
        // 事件推送
        else if (msgType.equals(Global.WX_REQ_MESSAGE_TYPE_EVENT)) {
        	String eventType = requestMap.get("Event");// 事件类型
        	// 订阅
            if (eventType.equals(Global.WX_EVENT_TYPE_SUBSCRIBE)) {
            	WechatTextMsg wechatMsg = new WechatTextMsg();
            	wechatMsg.setContent("欢迎关注，xxx");
            	wechatMsg.setToUserName(fromUserName);
            	wechatMsg.setFromUserName(toUserName);
            	wechatMsg.setCreateTime(new Date().getTime() + "");
            	wechatMsg.setMsgType(Global.WX_RESP_MESSAGE_TYPE_TEXT);
                
                respMessage = messageToXml(wechatMsg);
            } 
            //取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
            else if (eventType.equals(Global.WX_EVENT_TYPE_UNSUBSCRIBE)) {
            	// 取消订阅
                
            } 
            // 自定义菜单点击事件
            else if (eventType.equals(Global.WX_EVENT_TYPE_CLICK)) {
                String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                if (eventKey.equals("customer_telephone")) {
                	WechatTextMsg wechatMsg = new WechatTextMsg();
                	wechatMsg.setContent("0755-86671980");
                	wechatMsg.setToUserName(fromUserName);
                	wechatMsg.setFromUserName(toUserName);
                	wechatMsg.setCreateTime(new Date().getTime() + "");
                	wechatMsg.setMsgType(Global.WX_RESP_MESSAGE_TYPE_TEXT);
                    
                    respMessage = messageToXml(wechatMsg);
                }
            }
        }
        return respMessage;
	} 
	
	//排序
	public String sort(String timestamp, String nonce) {
		 String[] strArray = { WX_TOKEN, timestamp, nonce };
		 Arrays.sort(strArray);
		 StringBuilder sbuilder = new StringBuilder();
		    for (String str : strArray) {
		        sbuilder.append(str);
		    }
		 return sbuilder.toString();
	}
	
	//sha1加密
	public String sha1(String decript) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.update(decript.getBytes());
			byte messageDigests[] = messageDigest.digest();
			// Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigests.length; i++) {
                String shaHex = Integer.toHexString(messageDigests[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
	
	/** 
     * 文本消息对象转换成xml 
     *  
     * @param textMessage 文本消息对象 
     * @return xml 
     */ 
    public static String messageToXml(WechatTextMsg wechatMsg){
        XStream xstream = new XStream();
        xstream.alias("xml", wechatMsg.getClass());
        return xstream.toXML(wechatMsg);
    }
	
	 /**
	  * 解析微信发来的请求(XML)
	  * @param request
	  * @return
	  * @throws IOException
	  * @throws DocumentException
	  */
    public Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
         
    	// 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();  
        // 从request中取得输入流    
        InputStream inputStream = request.getInputStream();  
        // 读取输入流   
        SAXReader reader = new SAXReader();   
        Document document = reader.read(inputStream);    
        String requestXml = document.asXML();  
        String subXml = requestXml.split(">")[0] + ">";  
        requestXml = requestXml.substring(subXml.length());  
        // 得到xml根元素  
        Element root = document.getRootElement();  
        // 得到根元素的全部子节点  
        List<Element> elementList = root.elements();  
        // 遍历全部子节点  
        for (Element e : elementList) {  
            map.put(e.getName(), e.getText());  
            }  
        map.put("requestXml", requestXml);    
        // 释放资源    
        inputStream.close();    
        inputStream = null;    
        return map;  
    }

}
