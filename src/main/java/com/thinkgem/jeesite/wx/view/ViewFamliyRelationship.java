package com.thinkgem.jeesite.wx.view;

import java.util.Date;
import java.util.Random;

import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.modules.famliyship.entity.Famliyrelationship;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;

public class ViewFamliyRelationship {
	
	/**ID**/
	private String famliyId;
	
	/**名字**/
	private String name;
	
	/**关系Key**/
	private Integer relationshipKey;
	
	/**关系**/
	private String relationship;
	
	/**性别**/
	private Boolean gender;
	
	/**出生年月**/
	private String birth;	
	
	/**政治面貌Key**/
	private Integer politicalLandscapeKey;
	
	/**政治面貌**/
	private String politicalLandscape;
	
	/**身份证**/
	private String idCard;
	
	/**
	 * 所属会员
	 */
	private Userinfo userInfo;

	
	/**
	 * 健康状况
	 */
	private String healthy;
	
	/**
	 * 所在单位或学校
	 */
	private String jobAddress;
	
	/**
	 * 保险情况Key
	 */
	private Integer insuranceKey;
	
	/**
	 * 保险情况
	 */
	private String insurance;
	
	
	/**
	 * 邮政编码
	 */
	private String postCode;
	
	/**
	 * 本人月工资
	 */
	private Double salaryPerson;
	
	/**
	 * 家庭住址
	 */
	private String famliyAddress;
	
	/**
	 * 年龄
	 */
	private Integer age;
	
	/**
	 * 家庭人口
	 */
	private Integer famliyNum;

	/**
	 * 数据转换
	 * @return
	 */
	public Famliyrelationship convertToModel(Famliyrelationship frs){
		this.name = frs.getName();
		this.relationship = frs.getRelationship();
		this.birth = frs.getBirth();
		this.gender = frs.getGender();
		this.politicalLandscape = frs.getPoliticalLandscape();
		this.idCard = frs.getIdCard();
		this.userInfo = frs.getUserInfo();
		this.healthy = frs.getHealthy();
		this.jobAddress = frs.getJobAddress();
		this.insurance = frs.getInsurance();
		this.famliyAddress = frs.getFamliyAddress();
		this.famliyNum = frs.getFamliyNum();
		this.postCode = frs.getPostCode();
		this.age = frs.getAge();
		this.salaryPerson = frs.getSalaryPerson();
		this.insuranceKey = frs.getInsuranceKey();
		this.politicalLandscapeKey = frs.getPoliticalLandscapeKey();
		this.relationshipKey = frs.getRelationshipKey();
		return frs;
	}
	
	/**
	 * 数据转换
	 * @return
	 */
	public Famliyrelationship convertToDbModel(Famliyrelationship frs){
		frs.setStaticFlag(true);
		frs.setName(name);
		frs.setRelationship(relationship);
		frs.setBirth(birth);
		frs.setGender(gender);
		frs.setPoliticalLandscape(politicalLandscape);
		frs.setIdCard(idCard);
		frs.setUserInfo(userInfo);
		frs.setHealthy(healthy);
		frs.setJobAddress(jobAddress);
		frs.setInsurance(insurance);
		frs.setFamliyAddress(famliyAddress);
		frs.setFamliyNum(famliyNum);
		frs.setPostCode(postCode);
		frs.setAge(age);
		frs.setSalaryPerson(salaryPerson);
		frs.setInsuranceKey(insuranceKey);
		frs.setPoliticalLandscapeKey(politicalLandscapeKey);
		frs.setRelationshipKey(relationshipKey);
		return frs;
	}
	
	
	/**
	 * 数据转换
	 * @return
	 */
	public Famliyrelationship convertToDbModel(Userinfo userInfo,Famliyrelationship frs){
		frs.setStaticFlag(true);
		frs.setInsuranceKey(insuranceKey);
		frs.setPoliticalLandscapeKey(politicalLandscapeKey);
		frs.setRelationshipKey(relationshipKey);
		frs.setName(name);
		frs.setRelationship(relationship);
		frs.setBirth(birth);
		frs.setGender(gender);
		frs.setPoliticalLandscape(politicalLandscape);
		frs.setIdCard(idCard);
		frs.setUserInfo(userInfo);
		frs.setHealthy(healthy);
		frs.setJobAddress(jobAddress);
		frs.setInsurance(insurance);
		frs.setFamliyAddress(famliyAddress);
		frs.setFamliyNum(famliyNum);
		frs.setPostCode(postCode);
		frs.setAge(age);
		frs.setSalaryPerson(salaryPerson);
		return frs;
	}
	
	/**
	 * 生成当前家庭信息
	 * @return
	 */
	public void createCurrentUserInfo(Userinfo userInfo){
		this.name = userInfo.getName();
		this.birth = userInfo.getBirth();
		this.gender = userInfo.getGender();
		this.politicalLandscape = userInfo.getPoliticalLandscape();
		this.idCard = userInfo.getIdCard();
		this.healthy = userInfo.getHealthy();
		this.jobAddress = userInfo.getOriginalWorkAddress();
		this.insurance = userInfo.getInsurance();
		this.famliyAddress = userInfo.getFamliyAddress();
		this.famliyNum = userInfo.getFamliyNum();
		this.postCode = userInfo.getPostCode();
		this.salaryPerson = userInfo.getSalaryPerson();
	}
	/**
	 * 数据转换
	 * @return
	 */
	public Famliyrelationship convertToDbModel(Userinfo userInfo){
		Famliyrelationship ret = new Famliyrelationship();
		ret.setId(String.valueOf((new Date()).getTime()));
		ret.setStaticFlag(true);
		ret.setName(name);
		ret.setRelationship(relationship);
		ret.setRelationshipKey(relationshipKey);
		ret.setBirth(birth);
		ret.setGender(gender);
		ret.setPoliticalLandscape(politicalLandscape);
		ret.setIdCard(idCard);
		ret.setUserInfo(userInfo);
		ret.setHealthy(healthy);
		String healthyKey = DictUtils.getDictValue(healthy, "healthy", "0");
		ret.setHealthyKey(Integer.valueOf(healthyKey));
		ret.setJobAddress(jobAddress);
		ret.setInsurance(insurance);
		ret.setFamliyAddress(famliyAddress);
		ret.setFamliyNum(famliyNum);
		ret.setPostCode(postCode);
		ret.setAge(age);
		ret.setSalaryPerson(salaryPerson);
		ret.setInsuranceKey(insuranceKey);
		ret.setPoliticalLandscapeKey(politicalLandscapeKey);
		ret.setUserInfo(userInfo);
		ret.setUserinfoId(Long.valueOf(userInfo.getId()));
		return ret;
	}
	
	

	public String getInsurance() {
		return insurance;
	}



	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}



	public String getPostCode() {
		return postCode;
	}



	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}


	public Integer getRelationshipKey() {
		return relationshipKey;
	}

	public void setRelationshipKey(Integer relationshipKey) {
		this.relationshipKey = relationshipKey;
	}

	public Integer getPoliticalLandscapeKey() {
		return politicalLandscapeKey;
	}

	public void setPoliticalLandscapeKey(Integer politicalLandscapeKey) {
		this.politicalLandscapeKey = politicalLandscapeKey;
	}

	public Integer getInsuranceKey() {
		return insuranceKey;
	}

	public void setInsuranceKey(Integer insuranceKey) {
		this.insuranceKey = insuranceKey;
	}

	public String getFamliyAddress() {
		return famliyAddress;
	}



	public void setFamliyAddress(String famliyAddress) {
		this.famliyAddress = famliyAddress;
	}



	public Integer getAge() {
		return age;
	}



	public void setAge(Integer age) {
		this.age = age;
	}



	public Integer getFamliyNum() {
		return famliyNum;
	}



	public void setFamliyNum(Integer famliyNum) {
		this.famliyNum = famliyNum;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getPoliticalLandscape() {
		return politicalLandscape;
	}

	public void setPoliticalLandscape(String politicalLandscape) {
		this.politicalLandscape = politicalLandscape;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Userinfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Userinfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getHealthy() {
		return healthy;
	}

	public void setHealthy(String healthy) {
		this.healthy = healthy;
	}

	public String getJobAddress() {
		return jobAddress;
	}

	public void setJobAddress(String jobAddress) {
		this.jobAddress = jobAddress;
	}



	public Double getSalaryPerson() {
		return salaryPerson;
	}


	public void setSalaryPerson(Double salaryPerson) {
		this.salaryPerson = salaryPerson;
	}
	public String getFamliyId() {
		return famliyId;
	}
	public void setFamliyId(String famliyId) {
		this.famliyId = famliyId;
	}
	
	
	
}
