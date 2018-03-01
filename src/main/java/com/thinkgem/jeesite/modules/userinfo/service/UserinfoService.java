package com.thinkgem.jeesite.modules.userinfo.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;
import com.thinkgem.jeesite.modules.views.ViewUserInfoExcel;
import com.thinkgem.jeesite.modules.assist.dao.AssistDao;
import com.thinkgem.jeesite.modules.assist.entity.Assist;
import com.thinkgem.jeesite.modules.famliyship.dao.FamliyrelationshipDao;
import com.thinkgem.jeesite.modules.famliyship.entity.Famliyrelationship;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * userInfoService
 * @author wzy
 * @version 2017-09-20
 */
@Service
@Transactional(readOnly = true)
public class UserinfoService extends BaseUserinfoService {

	@Autowired
	private FamliyrelationshipDao frDao;
	
	@Autowired
	private AssistDao assistDao;
	
	public Userinfo get(String id) {
		Userinfo userinfo = super.get(id);
		return userinfo;
	}
	
	public Userinfo getByIdCard(String idcard) {
		return dao.getByIdCard(idcard);
	}
	
	
	public List<String> findAllIdCards(){
		return dao.findListIdCards();
	}
	
	public List<Userinfo> findList(Userinfo userinfo) {
		return super.findList(userinfo);
	}
	
	public Page<Userinfo> findPage(Page<Userinfo> page, Userinfo userinfo) {
		page.setOrderBy("update_date desc");
		return super.findPage(page, userinfo);
	}
	
	//查询家庭成员
	public List<Famliyrelationship> findFr(Userinfo userinfo){
		List<Famliyrelationship> frss = null;
		Long userinfoId = Long.valueOf(userinfo.getId());
		if(null!=userinfoId) {
			frss = frDao.findByUserId(Long.valueOf(userinfoId));
		}
		return frss;
	}
	
	@Transactional(readOnly = false)
	public void save(Userinfo userinfo) {
		
		setAddDefault(userinfo);//补充默认数据
		dictData(userinfo);//补充字典数据
		
		super.save(userinfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(Userinfo userinfo) {
		
		//删除之前需要删除关联对象 依次顺序是 家庭成员  帮扶状态 会员
		
		
		String userinfoId = userinfo.getId();
		if(null == userinfoId) {
			return;
		}
		
		Long assistId = userinfo.getAssistId();
		if(null == assistId) {
			return;
		}
		
		//查找成员和帮扶
		List<Famliyrelationship> frss = frDao.findByUserId(Long.valueOf(userinfoId));
		List<Assist> assists = assistDao.getByUserInfoId(Long.valueOf(userinfoId));
		
		//删除成员关系
		if(null!=frss) {
			for(Famliyrelationship frs:frss) {
				frDao.delete(frs);
			}
		}
		
		//先设置外键为空
		if(null!=assists) {
			for(Assist assist:assists) {
				assist.setUserinfoId(null);
				assistDao.update(assist);

			}
		}
		//先设置外键为空
		userinfo.setAssistId(null);
		super.save(userinfo);
		
		//删除帮扶状态
		if(null!=assists) {
			for(Assist assist:assists) {
				assistDao.delete(assist);

			}
		}
		

		//删除会员
		super.delete(userinfo);
	}
	
	/**
	 * 查询会员数量，依据性别
	 */
	public Integer findGenderCount(Userinfo userInfo) {
		return dao.findGenderCount(userInfo);
	}
	
	/**，依据性别
	 */
	public Integer findCount() {
		return dao.findCount();
	}
	
	/**
	 * 表格数据处理
	 */
	@Transactional(readOnly = false)
	public String processExcel(List<ViewUserInfoExcel> data) {
		
		StringBuilder failureMsg = new StringBuilder();
		
		//空数据检查
		if(null == data || data.size() == 0) {
			failureMsg.append("数据为空");
			return failureMsg.toString();
		}
		
		boolean isError = false;//是否有错误
		//验证身份证
		for(ViewUserInfoExcel forEntity : data) {
			String idCard = forEntity.getIdCard();
			logger.info("身份证是:"+idCard);
			if(null == idCard) {
				failureMsg.append("<br/>姓名: "+forEntity.getName()+"身份证为空; ");
				isError = true;
			}else if(!CasUtils.idCardNumber(idCard)) {
				failureMsg.append("<br/>身份证号 "+idCard+" 格式错误; ");
				isError = true;
			}
		}
		
		//有错误
		if(isError) {
			return failureMsg.toString();
		}
		
		//数据处理
		for(ViewUserInfoExcel forEntity : data) {
			String idCard = forEntity.getIdCard();
			Userinfo userinfo = getByIdCard(idCard);
			if(null == userinfo) {
				userinfo = new Userinfo();
			}
			convertExcelData(forEntity,userinfo);
			save(userinfo);
		}
		return null;
	}
	
	//数据转换 Excel和数据库
	private void  convertExcelData(ViewUserInfoExcel vie,Userinfo userinfo) {
		userinfo.setName(vie.getName());
		userinfo.setDegreeOfEducation(vie.getEdu());
		userinfo.setMembershipTime(vie.getMemberDate());
		userinfo.setCertificate(vie.getCertificate());
		userinfo.setIdCard(vie.getIdCard());
		userinfo.setPhoneNumber(vie.getPhone());
		String yes = DictUtils.getDictLabel("1", "yes_no", "是");
		
		//劳模先进
		String advanced = vie.getAdvanced();
		if(yes.equals(advanced)) {
			userinfo.setAdvanced(DictUtils.getDictValue(yes, "yes_no", "1"));
		}
		
		//五一劳动
		String labor = vie.getLabor();
		if(yes.equals(labor)) {
			userinfo.setLabor(DictUtils.getDictValue(yes, "yes_no", "1"));
		}
		
		//是否农牧民
		String farmersAndHerdsmen = vie.getFarmersAndHerdsmen();
		boolean isFah = false;
		if(null!=farmersAndHerdsmen) {
			if(farmersAndHerdsmen.equals("是")) {
				isFah = true;
			}
		}
		userinfo.setFarmersAndHerdsmen(isFah);
		
		userinfo.setRemarks(vie.getRemark());
	}
	
	/**
	 * 读取表格
	 */
	public List<ViewUserInfoExcel> excel(MultipartFile multipartFile) throws InvalidFormatException, IOException{
		String fileName = multipartFile.getOriginalFilename();
		InputStream is = multipartFile.getInputStream();
		Workbook wb = null;
		if (StringUtils.isBlank(fileName)){
			throw new RuntimeException("导入文档为空!");
		}else if(fileName.toLowerCase().endsWith("xls")){    
			wb = new HSSFWorkbook(is);    
        }else if(fileName.toLowerCase().endsWith("xlsx")){  
        	wb = new XSSFWorkbook(is);
        }else{  
        	throw new RuntimeException("文档格式不正确!");
        }  
		if (wb.getNumberOfSheets() < 0){
			throw new RuntimeException("文档中没有工作表!");
		}
		logger.info("文件读入成功，文件名:"+fileName);
		Sheet sheet = wb.getSheetAt(0);//只去第一个表格
		int dataStartNum = 4;//数据行号
		int dataEndNum = sheet.getLastRowNum();//数据行号
		logger.info("数据行数:"+dataEndNum);
		List<ViewUserInfoExcel> ret = new ArrayList<ViewUserInfoExcel>();
		for (int i = dataStartNum; i < dataEndNum; i++) {
			int column = 0;
			Row row = sheet.getRow(i);
			ViewUserInfoExcel entity = new ViewUserInfoExcel();
			for(int j=0;j<row.getLastCellNum();j++) {
				Object val = this.getCellValue(row, column++);
				logger.info("行数:"+i+" 列数:"+j+" 值:"+val);
				if(j == 0) {//名字
					entity.setName(val.toString());
				}
				if(j == 1) {//性别
					entity.setGender(val.toString());
				}
				if(j == 2) {//政治面貌
					entity.setPoliticalLandscape(val.toString());
				}
				if(j == 3) {//学历
					entity.setEdu(val.toString());
				}
				if(j == 4) {//入会时间
					entity.setMemberDate(val.toString());
				}
				if(j == 5) {//证件类型
					entity.setCertificate(val.toString());
				}
				if(j == 6) {//证件号码
					entity.setIdCard(val.toString());
				}
				if(j == 7) {//手机号码
					entity.setPhone(val.toString());
				}
				if(j == 8) {//劳模先进
					entity.setAdvanced(val.toString());
				}
				if(j == 9) {//五一劳动
					entity.setLabor(val.toString());
				}
				if(j == 10) {//是否农牧民
					entity.setFarmersAndHerdsmen(val.toString());
				}
				if(j == 11) {//是否农牧民
					entity.setRemark(val.toString());
				}
			}
			ret.add(entity);
		}
		return ret;
	}
	
	/**
	 * 获取单元格值
	 * @param row 获取的行
	 * @param column 获取单元格列号
	 * @return 单元格值
	 */
	private Object getCellValue(Row row, int column){
		Object val = "";
		try{
			Cell cell = row.getCell(column);
			if (cell != null){
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
					 if(HSSFDateUtil.isCellDateFormatted(cell)) {  
						 //判断日期类型
						 val = CasUtils.convertDate2YMDString(cell.getDateCellValue());
		             } else {  //否
		            	 val = new DecimalFormat("#.######").format(cell.getNumericCellValue()); 
		             } 
				}else if (cell.getCellType() == Cell.CELL_TYPE_STRING){
					val = cell.getStringCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA){
					val = cell.getCellFormula();
				}else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
					val = cell.getBooleanCellValue();
				}else if (cell.getCellType() == Cell.CELL_TYPE_ERROR){
					val = cell.getErrorCellValue();
				}
			}
		}catch (Exception e) {
			return val;
		}
		return val;
	}
}