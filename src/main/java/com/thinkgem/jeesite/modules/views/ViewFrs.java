package com.thinkgem.jeesite.modules.views;

import com.thinkgem.jeesite.modules.famliyship.entity.Famliyrelationship;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;

public class ViewFrs {
	
	private String id;

	/**名字**/
	private String name;
	
	
	/**关系Key**/
	private String relationshipKey;
	
	/**关系**/
	private String relationship;
	
	/**性别**/
	private String gender;
	
	/**出生年月**/
	private String birth;	
	
	/**政治面貌Key**/
	private String politicalLandscapeKey;
	
	/**政治面貌**/
	private String politicalLandscape;
	
	/**
	 * 年龄
	 */
	private String age;
	
	
	/**
	 * 本人月工资
	 */
	private String salaryPerson;
	
	/**
	 * 邮政编码
	 */
	private String postCode;

	
	/**
	 * 家庭住址
	 */
	private String famliyAddress;
	
	/**
	 * 家庭人口
	 */
	private String famliyNum;
	
	/**身份证**/
	private String idCard;
	
	/**健康状况Key**/
	private String healthyKey;
	
	
	
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
	private String insuranceKey;
	
	
	/**
	 * 保险情况
	 */
	private String insurance;
	
	/**
	 * 模板对象标识。
	 */
	private String staticFlag;
	
	private String version;
	private String userinfoId;// userinfo_id
	
	private Userinfo userInfo;//会员信息
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRelationshipKey() {
		return relationshipKey;
	}
	public void setRelationshipKey(String relationshipKey) {
		this.relationshipKey = relationshipKey;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getPoliticalLandscapeKey() {
		return politicalLandscapeKey;
	}
	public void setPoliticalLandscapeKey(String politicalLandscapeKey) {
		this.politicalLandscapeKey = politicalLandscapeKey;
	}
	public String getPoliticalLandscape() {
		return politicalLandscape;
	}
	public void setPoliticalLandscape(String politicalLandscape) {
		this.politicalLandscape = politicalLandscape;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSalaryPerson() {
		return salaryPerson;
	}
	public void setSalaryPerson(String salaryPerson) {
		this.salaryPerson = salaryPerson;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getFamliyAddress() {
		return famliyAddress;
	}
	public void setFamliyAddress(String famliyAddress) {
		this.famliyAddress = famliyAddress;
	}
	public String getFamliyNum() {
		return famliyNum;
	}
	public void setFamliyNum(String famliyNum) {
		this.famliyNum = famliyNum;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getHealthyKey() {
		return healthyKey;
	}
	public void setHealthyKey(String healthyKey) {
		this.healthyKey = healthyKey;
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
	public String getInsuranceKey() {
		return insuranceKey;
	}
	public void setInsuranceKey(String insuranceKey) {
		this.insuranceKey = insuranceKey;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public String getStaticFlag() {
		return staticFlag;
	}
	public void setStaticFlag(String staticFlag) {
		this.staticFlag = staticFlag;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUserinfoId() {
		return userinfoId;
	}
	public void setUserinfoId(String userinfoId) {
		this.userinfoId = userinfoId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public Userinfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(Userinfo userInfo) {
		this.userInfo = userInfo;
	}
	//转换到视图数据
	public void convertToViewData(Famliyrelationship frs) {
		this.id = frs.getId();
		this.name = frs.getName();
		this.relationshipKey = String.valueOf(frs.getRelationshipKey());
		this.relationship = frs.getRelationship();
		this.gender = String.valueOf(frs.getGender());
		this.birth = frs.getBirth();
		this.politicalLandscapeKey = String.valueOf(this.getPoliticalLandscapeKey());
		this.politicalLandscape = frs.getPoliticalLandscape();
		this.age = String.valueOf(frs.getAge());
		if(null == frs.getAge()) {
			this.age = "";
		}
		this.salaryPerson = String.valueOf(frs.getSalaryPerson());
		if(null == frs.getSalaryPerson()) {
			this.salaryPerson = "";
		}
		this.postCode = frs.getPostCode();
		this.famliyAddress = frs.getFamliyAddress();
		this.famliyNum = String.valueOf(frs.getFamliyNum());
		this.idCard = frs.getIdCard();
		this.healthyKey = String.valueOf(frs.getHealthyKey());
		this.healthy = frs.getHealthy();
		this.jobAddress = frs.getJobAddress();
		this.insuranceKey = String.valueOf(frs.getInsuranceKey());
		this.insurance = frs.getInsurance();
		this.userinfoId = String.valueOf(frs.getUserinfoId());
		this.staticFlag = String.valueOf(frs.getStaticFlag());
		this.version = String.valueOf(frs.getVersion());
		this.userInfo = frs.getUserInfo();
	}
	
	
}
