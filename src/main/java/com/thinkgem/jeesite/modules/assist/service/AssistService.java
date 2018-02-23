package com.thinkgem.jeesite.modules.assist.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.CommonConstants;
import com.thinkgem.jeesite.common.utils.EmailHelper;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.XwpfTUtils;
import com.thinkgem.jeesite.modules.assist.entity.Assist;
import com.thinkgem.jeesite.modules.ce.dao.CommunityemailDao;
import com.thinkgem.jeesite.modules.ce.entity.Communityemail;
import com.thinkgem.jeesite.modules.famliyship.dao.FamliyrelationshipDao;
import com.thinkgem.jeesite.modules.famliyship.entity.Famliyrelationship;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;

/**
 * assistService
 * @author wzy
 * @version 2017-09-20
 */
@Service
@Transactional(readOnly = true)
public class AssistService extends AssistBaseService {

	@Autowired
	private FamliyrelationshipDao famliyrelationshipDao;

	//会员
	@Autowired
	protected CommunityemailDao communityemailDao;
	
	@Autowired
	protected UserDao userDao;
	
	public Assist get(String id) {
		return super.get(id);
	}
	
	public List<Assist> getByUserInfoId(Long userInfoId) {
		return dao.getByUserInfoId(userInfoId);
	}
	
	public List<Assist> findList(Assist assist) {
		List<Assist> assists =  super.findList(assist);
		//setUserInfoForAssist(assists);//补充会员信息
		return assists;
	}
	
	public Page<Assist> findPage(Page<Assist> page, Assist assist) {
		page.setOrderBy("applyDate desc");
		Page<Assist> assists =  super.findPage(page, assist);
		/*List<Assist> queryAssistStates = findAssistByRoleType(assists.getList());
		assists.setList(queryAssistStates);
		setUserInfoForAssist(assists.getList());//补充会员信息*/
		return assists ;
	}
	
	//根据用户类型获得帮扶列表
	private List<Assist> findAssistByRoleType(List<Assist> pages){
		List<Assist> rets = null;
		Role role = UserUtils.getUser().getMaxRole();
		String roleType = role.getRoleType();
			switch(roleType) {
				case "0":
					rets = new ArrayList<Assist>();
					break;
				case "1":
					rets = findAssistForState(0,pages);
					//社区
					break;
				case "2":
					rets = findAssistForState(1,pages);
					//街道
					break;
				case "3":
					//工会
					rets = findAssistForState(2,3,pages);
					break;
				default:
					rets = pages;
					break;
			}
			return rets;
		}
	
	//把对应帮扶状态筛选出来
	private List<Assist> findAssistForState(int assistQueryValue,List<Assist> pages){
		List<Assist> rets = new ArrayList<Assist>();
		for(Assist assist:pages) {
			int assistState = assist.getAssistState();
			if(assistQueryValue == assistState) {
				rets.add(assist);
			}
		}
		return rets;
	}
	
	//把对应帮扶状态筛选出来
	private List<Assist> findAssistForState(int assistQueryValue1,int assistQueryValue2,List<Assist> pages){
		List<Assist> rets = new ArrayList<Assist>();
		for(Assist assist:pages) {
			int assistState = assist.getAssistState();
			if(assistQueryValue1 == assistState
					||assistQueryValue2 == assistState) {
				rets.add(assist);
			}
		}
		return rets;
	}

	
	//补充添加数据
	public void supplyAddData(Assist assist) {
		setAddDefault(assist);//默认值
	}
	
	//添加信息时候的默认值   因为之前表格设计过程的字段用途已经取消 但是不能删除字段 只能现将部分字段给予默认值 保证系统的稳定运行
	private void setAddDefault(Assist assist) {
		assist.setStaticFlag(true);
		assist.setVersion(1);
	}
	
	@Transactional(readOnly = false)
	public void save(Assist assist) {
		supplyAddData(assist);//先补充数据
		super.save(assist);
	}
	
	//根据帮扶信息查询会员信息
	public Userinfo findUserinfo(Assist assist) {
		Long userinfoId = assist.getUserinfoId();
		Userinfo userinfo = null;
		if(null!=userinfoId) {
			userinfo = userinfoDao.get(userinfoId.toString());
		}
		return userinfo;
	}

	//依据帮扶信息查询家庭成员
	public List<Famliyrelationship> findFr(Assist assist){
		List<Famliyrelationship> frss = null;
		Long userinfoId = assist.getUserinfoId();
		if(null!=userinfoId) {
			frss = famliyrelationshipDao.findByUserId(Long.valueOf(userinfoId));
		}
		return frss;
	}
	
	@Transactional(readOnly = false)
	public void delete(Assist assist) {
		
		String asssitId = assist.getId();
		
		//因为帮扶信息和会员是一对一的情况 但是新增帮扶 旧帮扶信息会出现滞留 针对这种情况 删除帮扶信息的时候需要核对该帮扶信息的会员是否对应的帮扶是一样的
		Long userinfoId = assist.getUserinfoId();
		Userinfo userinfo = null;
		Long userInfoAssistId = null;
		
		if(null!=userinfoId) {
			userinfo = userinfoDao.get(userinfoId.toString());
			userInfoAssistId = userinfo.getAssistId();
		}
		
		//如果会员中的帮扶和要删除的一样 
		if(null != userInfoAssistId) {
			if(asssitId.equals(userInfoAssistId.toString())) {
				//先清空帮扶信息
				userinfo.setAssistId(null);
				userinfoDao.update(userinfo);
			}
		}
		
		super.delete(assist);
	}
	
	
	//下载表格
	public String downloadWord(Assist assist) {
		Map<String, Object> params = createTempSheet(assist);//生成模板数据
		String fileName = String.valueOf(IdGen.randomLong());
        fileName = fileName + ".docx";
        XwpfTUtils xwpfUtils = new XwpfTUtils();
        String path = xwpfUtils.importWordData(params,fileName);
        String file = path + File.separator + fileName;
        return file;
	}
	
	//发送邮件
	public void sendAssistMail(Assist assist) {
		
		try {
			Map<String, Object> params = createTempSheet(assist);//生成模板数据
			String toAddress = findToAddress(assist);//邮箱地址
	        EmailHelper eh = new EmailHelper(CommonConstants.EMAIL_HOST,CommonConstants.EMAIL_USER,CommonConstants.EMAIL_PWD,CommonConstants.EMAIL_FROM,toAddress);
	        sendLastMail(params,eh,assist);//发送邮件
		}catch(Exception e){
			System.out.println("Email send fail");
			e.printStackTrace();
		}
	
	}
	
	public static void zipFile(List<File> files, ZipOutputStream outputStream) throws Exception{
        try {
            int size = files.size();
            // 压缩列表中的文件
            for (int i = 0; i < size; i++) {
                File file = (File) files.get(i);
                zipFile(file, outputStream);
            }
        } catch (IOException e) {
            throw e;
        }
    }
	public static void zipFile(File inputFile, ZipOutputStream outputstream) throws Exception{
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    FileInputStream inStream = new FileInputStream(inputFile);
                    BufferedInputStream bInStream = new BufferedInputStream(inStream);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    outputstream.putNextEntry(entry);

                    final int MAX_BYTE = 10 * 1024 * 1024; // 最大的流为10M
                    long streamTotal = 0; // 接受流的容量
                    int streamNum = 0; // 流需要分开的数量
                    int leaveByte = 0; // 文件剩下的字符数
                    byte[] inOutbyte; // byte数组接受文件的数据

                    streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
                    streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
                    leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量

                    if (streamNum > 0) {
                        for (int j = 0; j < streamNum; ++j) {
                            inOutbyte = new byte[MAX_BYTE];
                            // 读入流,保存在byte数组
                            bInStream.read(inOutbyte, 0, MAX_BYTE);
                            outputstream.write(inOutbyte, 0, MAX_BYTE); // 写出流
                        }
                    }
                    // 写出剩下的流数据
                    inOutbyte = new byte[leaveByte];
                    bInStream.read(inOutbyte, 0, leaveByte);
                    outputstream.write(inOutbyte);
                    outputstream.closeEntry(); // Closes the current ZIP entry
                    // and positions the stream for
                    // writing the next entry
                    bInStream.close(); // 关闭
                    inStream.close();
                }
            } else {
                throw new Exception("文件不存在！");
            }
        } catch (IOException e) {
            throw e;
        }
	}
	
	//下载附件
	public File downloadPhoto(Assist assist) throws Exception{
		Userinfo userInfo = findUserinfo(assist);
		String dirParam = getImagePath(userInfo.getIdCard());
		File[] sendFiles = CasUtils.traverseFolder2(dirParam);
		if(null == sendFiles || sendFiles.length == 0) {
			return null;
		}
		List<File> imageName = new ArrayList<File>();
		for(int i=0;i<sendFiles.length;i++) {
			File tempFile = sendFiles[i];
			imageName.add(tempFile);
		}
		String fileName = UUID.randomUUID().toString() + ".zip";
		String outFilePath = getSavePath(CommonConstants.tempDir);
    	File dir = new File(dirParam);
    	if(!dir.exists()) {
    		dir.mkdir();
    	}
		File fileZip = new File(outFilePath + fileName);
		  // 文件输出流
        FileOutputStream outStream = new FileOutputStream(fileZip);
        // 压缩流
        ZipOutputStream toClient = new ZipOutputStream(outStream);
        zipFile(imageName, toClient);
        toClient.close();
        outStream.close();
		return fileZip;
	}
	
	//发送邮件
	private void sendLastMail(Map<String, Object> params,EmailHelper eh,Assist assist) throws Exception{
		Userinfo userInfo = findUserinfo(assist);
		String dirParam = getImagePath(userInfo.getIdCard());
		String fileName = String.valueOf(IdGen.randomLong());
        fileName = fileName + ".docx";
        XwpfTUtils xwpfUtils = new XwpfTUtils();
        String path = xwpfUtils.importWordData(params,fileName);
        String file = path + File.separator + fileName;
        if(null == dirParam){
			eh.sendMail(file,fileName);
		}else{
			File[] sendFiles = CasUtils.traverseFolder2(dirParam);
			
			if(null != sendFiles){
				List<String> imageNames = new ArrayList<String>();
				List<String> imagePaths = new ArrayList<String>();
				for(int i=0;i<sendFiles.length;i++){
					File tempFile = sendFiles[i];
					String name = tempFile.getName();
					imageNames.add(name);
					imagePaths.add(dirParam + File.separator + name);
					System.out.println("-----------文件路径------------"+dirParam);;
					System.out.println("-----------文件名------------"+name);;
				}
				if(null !=imageNames && imageNames.size()>0){
					eh.sendMails(file,fileName,imagePaths,imageNames);
				}else{
					eh.sendMail(file,fileName);
				}
			}else{
				eh.sendMail(file,fileName);
			}
		}
	}
		
	
	//根据社区键值查询社区邮箱 如果没有邮箱 默认采用0序号的邮箱地址
	private String findToAddress(Assist assist) {
		Userinfo userInfo = findUserinfo(assist);
		String communityKey = userInfo.getCommunityKey().toString();
		String toAddress = null;//邮箱地址
		
		User queryUser = new User();
		queryUser.setCommunityKey(communityKey);
		List<User> userList = userDao.findList(queryUser);
		if(null == userList || userList.size() == 0) {
			//没有对应的邮箱 默认0序号邮箱
			Communityemail defaultVce = communityemailDao.getCommunityEmailByIdSerial(String.valueOf(0));
			if(null!=defaultVce){
       		 	toAddress = defaultVce.getEmail();
       	 	}
		}else {
			//默认用第一个 不为空的
			for(User user : userList) {
				String emailAddress = user.getEmail();
				if(StringUtils.isNotEmpty(emailAddress)) {
					toAddress = emailAddress;
					break;
				}
			}
		}
		
		if(StringUtils.isEmpty(toAddress)) {
			  toAddress = CommonConstants.EMAIL_DEFAULT;//邮箱地址
		}
		return toAddress;
	        
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
	 * 图片文件路径
	 */
	private String getImagePath(String idCard) throws Exception{
	    //获取附件图片
        if(null == idCard){
			return null;
		}
		String dirParam = headPhotoPath(idCard);
		if(null == dirParam){
			return null;
		}
		File dir = new File(dirParam);
		if(!dir.exists()){
			return null;
		}
	
		return dirParam;
	}
    
    private String headPhotoPath(String idCard) throws Exception{
    	StringBuilder sb = null;
    	String dirParam = getSavePath(CommonConstants.fileDir);
    	File dir = new File(dirParam);
    	if(!dir.exists()) {
    		dir.mkdir();
    	}
    	sb = new StringBuilder(dirParam);
		sb.append(File.separator);
		sb.append(idCard);
		return sb.toString();
    }

	
	//生成临时模板表
	private Map<String, Object> createTempSheet(Assist assist){
		Userinfo userInfo = findUserinfo(assist);
		List<Famliyrelationship> frs = findFr(assist);
		Integer famliyNum = frs.size();
		int sum = CommonConstants.ASSIST_NUM;//总共5个成员
		Map<String, Object> params = new HashMap<String, Object>();    
        params.put("${ProblemEmployeeNo}","");  
        if(null!=userInfo.getProblemKind()){
        	  params.put("${ProblemKind}",userInfo.getProblemKind());
        }else{
        	  params.put("${ProblemKind}","");
        }
        params.put("${Name}",userInfo.getName());
        if(null!= userInfo.getGender()){
            params.put("${Gender}",CasUtils.converGender(userInfo.getGender())); 
        }else {
            params.put("${Gender}","");
        }
        params.put("${Nation}",userInfo.getNation()); 
        params.put("${Birth}",userInfo.getBirth()); 
        if(null!=userInfo.getPoliticalLandscape()){
      	  params.put("${Politacal}",userInfo.getPoliticalLandscape());
        }else{
      	  params.put("${Politacal}","");
        }
        params.put("${IdCard}",userInfo.getIdCard());
        if(null!=userInfo.getHealthy()){
        	  params.put("${Healthy}",userInfo.getHealthy());
        }else{
        	  params.put("${Healthy}","");
        }
        if(null!=userInfo.getDisability()){
      	  params.put("${Disable}",userInfo.getDisability());
        }else{
      	  params.put("${Disable}","");
        }
        if(null!=userInfo.getOriginalWorkAddress()){
        	  params.put("${WorkPlace}",userInfo.getOriginalWorkAddress());
        }else{
        	  params.put("${WorkPlace}","");
        }
        params.put("${Indentity}",userInfo.getIdentity());
        params.put("${Telphone}",userInfo.getPhoneNumber());
        params.put("${ModelWorker}",userInfo.getModelWorker());
        params.put("${Marriage}",userInfo.getMarriage());
        params.put("${FamliyAddress}",userInfo.getFamliyAddress());
        params.put("${PostCode}",userInfo.getPostCode());
        params.put("${HousingKind}",userInfo.getHousingKind());
        if(null!=userInfo.getHousingArea()){
        	  params.put("${HousingArea}",userInfo.getHousingArea().toString());
        }else{
        	  params.put("${HousingArea}","");
        }
        if(null!=userInfo.getFamliyNum()){
      	  params.put("${FamliyPopulation}",userInfo.getFamliyNum().toString());
        }else{
      	  params.put("${FamliyPopulation}","");
        }
        params.put("${RegisterPlace}",userInfo.getRegisterPlace());
        params.put("${RegisterKind}",userInfo.getRegisterKind());
        if(null!=userInfo.getSalaryPerson()){
        	  params.put("${SalaryPerson}",userInfo.getSalaryPerson().toString());
        }else{
        	  params.put("${SalaryPerson}","");
        }
        
        if(null!=userInfo.getMedicalInsurance()){
        	  params.put("${MedicalInsurance}",CasUtils.convertSure(userInfo.getMedicalInsurance()));
        }else{
        	  params.put("${MedicalInsurance}","否");
        }
        if(null!=userInfo.getSubsistenceAllowances()){
      	  params.put("${SubsistenceAllowances}",CasUtils.convertSure(userInfo.getSubsistenceAllowances()));
        }else{
      	  params.put("${SubsistenceAllowances}","否");
        }
        
        double salayTotal = 0;
    	salayTotal = userInfo.getSalaryPerson();
		for(int i=0;i<famliyNum;i++){
			Famliyrelationship fr = frs.get(i);
			salayTotal +=fr.getSalaryPerson();
    	}	
		Double salayFamliy = salayTotal/famliyNum;
	    params.put("${SalaryFamliy}",salayFamliy.toString());
 

        if(famliyNum!=0){
        	int tempFamliyNum = famliyNum;
        	//超过5个
        	if(tempFamliyNum>sum){
        		for(int i=0;i<tempFamliyNum;i++){
            		famliyMemberData(params,i+1,frs.get(i));
            	}
        	}else{
        		//不超过5个
        		for(int i=0;i<sum;i++){
            		//最多5个成员
            		if(i<tempFamliyNum){
            			famliyMemberData(params,i+1,frs.get(i));
            		}else{
            			famliyMemberData(params,i+1);
            		}
            		
            	}
        	}
        }else{
        	for(int i=0;i<sum;i++){
        		famliyMemberData(params,i+1);
        	}
        }
        
        
        if(null != assist){
            params.put("${ApplyRemark}",assist.getApplyReason());  
            params.put("${ApplyPerson}",assist.getApplyName());  
            params.put("${ApplyDate}",CasUtils.convertDate2FullString(new Date()));  
        }
        return params;
	}
	
	/**
	 * 生成 家庭成员数据
	 */
	private void  famliyMemberData(Map<String, Object> params,int num,Famliyrelationship fr){
		StringBuffer data = new StringBuffer().append(num).append("}");
	 	params.put("${RelationName"+data.toString(),fr.getName());  
        params.put("${Relation"+data.toString(),fr.getRelationship()); 
        params.put("${RelationGender"+data.toString(),CasUtils.converGender(fr.getGender()));  
        params.put("${RelationBirth"+data.toString(),fr.getBirth()); 
        params.put("${RelationPolicatical"+data.toString(),fr.getPoliticalLandscape()); 
        params.put("${RelationIdCard"+data.toString(),fr.getIdCard()); 
        params.put("${RelationHealthy"+data.toString(),fr.getHealthy()); 
        params.put("${RelationSchool"+data.toString(),fr.getJobAddress());
	     
	}
	

	/**
	 * 生成 家庭成员数据
	 */
	private void  famliyMemberData(Map<String, Object> params,int num){
		StringBuffer data = new StringBuffer().append(num).append("}");
	 	params.put("${RelationName"+data.toString(),"");  
        params.put("${Relation"+data.toString(),""); 
        params.put("${RelationGender"+data.toString(),"");  
        params.put("${RelationBirth"+data.toString(),""); 
        params.put("${RelationPolicatical"+data.toString(),""); 
        params.put("${RelationIdCard"+data.toString(),""); 
        params.put("${RelationHealthy"+data.toString(),""); 
        params.put("${RelationSchool"+data.toString(),"");
	     
	}
	
	//查询数量
	public Integer findCount() {
		return dao.findCount();
	}
	
	//查询去重复会员
	public Integer findDistinctCount() {
		return dao.findDistinctCount();
	}
}