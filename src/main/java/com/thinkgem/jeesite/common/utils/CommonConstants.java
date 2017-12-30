package com.thinkgem.jeesite.common.utils;

public class CommonConstants {
	
	/**
	 * 当日首次登陆时间差距限制。
	 * 目前默认：30秒
	 */
	public static final long FIRST_LOGIN_TIME_DIFFER = 30000;
	
	/**
	 * 微信Url
	 */
    public static final String WX_URL = "https://api.weixin.qq.com/sns/jscode2session";  

    
    /**
	 * 微信AppId
	 */
    public static final String WX_App_Id = "wxe044762b224cd73c"; 
    
    /**
     * 小程序的 AppSecret
     */
    public static final String WX_App_Secret = "7a730659bd70ba7530c3baa94eacd9a9"; 
    
    /**
	 * 升序
	 */
	public static final String ASC = "asc";

	/**
	 * 降序
	 */
	public static final String DESC = "desc";
    
    /**
	 * MD5
	 */
	public static final String MD5 = "md5";

	/**
	 * utf8
	 */
	public static final String UTF8 = "utf8";
	/**
	 * GBK
	 */
	public static final String GBK = "GBK";

	/**
	 * BIG5
	 */
	public static final String BIG5 = "BIG5";
    
	
	public static final String fileDir = "images";
	
	public static final String tempDir = "tmp";
	
	public final static String noUserInfo = "帮扶会员不存在";
	
	public final static String noUserInfoCommunity = "帮扶会员没有社区信息";
	
	public final static String regexIdCard = "请输入正确的身份证号";
	
	public final static String regexPhoneNumber = "请输入正确的电话号码";
	
	public final static String importFileTemplateXls = "用户数据导入模板.xlsx";

	public final static String importFileTemplateXlsTitle = "用户数据";

	public final static int ASSIST_NUM = 5;
	
	public static final String WORD_SAVE_DIR = "wordTemplate";
	
	public static final String WORDS = "words";
	
    public static final String EMAIL_HOST = "smtp.163.com";//邮箱主机
    
    public static final String EMAIL_USER = "15904793789@163.com";//邮箱账号
    
    public static final String EMAIL_PWD = "bqwzy@158wy";//邮箱密码
    
    public static final String EMAIL_FROM = "15904793789@163.com";//发送地址
    
    public static final String EMAIL_DEFAULT = "463718998@qq.com";//默认地址
	
	/**滚动图片**/
	public static int scrollNews = 0;
	
	/**首页图片**/
	public static int firstNews = 1;
	

	/**滚动图片**/
	public static String scrollName = "滚动图片";
	
	/**首页图片**/
	public static String firstName = "首页图片";

}
