/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.userinfo.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.supcan.annotation.treelist.cols.SupCol;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * userInfoEntity
 * @author wzy
 * @version 2017-09-20
 */
public class Userinfo extends DataEntity<Userinfo> {
	
	private static final long serialVersionUID = 1L;
	/**序号**/
	private Integer serialNumber;
	
	/**
	 * 困难类别
	 */
	private String problemKind;
	
	/**名字**/
	private String name;
	
	/**性别**/
	private Boolean gender;
	
	/**民族前段的key**/
	private Integer nationKey;
	
	/**民族**/
	private String nation;
	
	/**出生年月**/
	private String birth;
	
	/**文化程度**/
	private String degreeOfEducation;
	
	/**政治面貌**/
	private String politicalLandscape;
	
	/**参加工作时间**/
	private String partInJobTime;
	
	/**入会时间**/
	private String membershipTime;
	
	/**身份证**/
	private String idCard;
	
	/**手机号码**/
	private String phoneNumber;
	
	/**是否农牧民**/
	private Boolean farmersAndHerdsmen;
	
	/**
	 * 所在工会组织
	 */
	private String organization;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	/**
	 * 健康状况
	 */
	private String healthy;
	
	/**
	 * 残疾类别
	 */
	private String disability;
	
	/**
	 * 工作单位（或原工作单位）
	 */
	private String originalWorkAddress;
	
	/**
	 * 身份
	 */
	private String identity;
	
	/**
	 * 劳模类型
	 */
	private String modelWorker;
	
	/**
	 * 婚姻状况
	 */
	private String marriage;
	
	
	/**
	 * 家庭住址
	 */
	private String famliyAddress;
	
	/**
	 * 邮政编码
	 */
	private String postCode;
	
	/**
	 * 住房类型
	 */
	private String housingKind;
	
	/**
	 * 住房面积
	 */
	private Double housingArea;
	
	/**
	 * 家庭人口
	 */
	private Integer famliyNum = 1;
	
	/**
	 * 户口所在地行政区别
	 */
	private String registerPlace;
	
	/**
	 * 户口类型
	 */
	private String registerKind;
	
	/**
	 * 本人月工资
	 */
	private Double salaryPerson;
	
	/**
	 * 家庭月人均收入
	 */
	private Double salaryFamliy;
	
	/**
	 * 是否参加医保
	 */
	private Boolean medicalInsurance;
	
	/**
	 * 是否享受低保
	 */
	private Boolean subsistenceAllowances;
	
	/**
	 * 所在社区key
	 */
	private Integer communityKey;
	
	/**
	 * 所在社区
	 */
	private String community;
	
	/**
	 * 保险情况Key
	 */
	private Integer insuranceKey;
	
	/**
	 * 保险情况
	 */
	private String insurance;
	
	/**
	 * 收入来源Key
	 */
	private Integer incomeSourceKey;
	
	/**
	 * 收入来源
	 */
	private String incomeSource;
	
	/**
	 * 模板对象标识。
	 */
	private Boolean staticFlag;

	
	private Integer version;

	private Long assistId;		// assist_id
	
	public Userinfo() {
		super();
	}

	public Userinfo(String id){
		super(id);
	}

	@Length(min=0, max=255, message="birth长度必须介于 0 和 255 之间")
	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	@Length(min=0, max=255, message="community长度必须介于 0 和 255 之间")
	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}
	

	
	
	
	
	
	
	

	public Integer getCommunityKey() {
		return communityKey;
	}

	public String getDegreeOfEducation() {
		return degreeOfEducation;
	}

	public void setDegreeOfEducation(String degreeOfEducation) {
		this.degreeOfEducation = degreeOfEducation;
	}

	public void setCommunityKey(Integer communityKey) {
		this.communityKey = communityKey;
	}

	
	@Length(min=0, max=255, message="disability长度必须介于 0 和 255 之间")
	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}
	
	
	
	

	@Length(min=0, max=255, message="famliyaddress长度必须介于 0 和 255 之间")
	public String getFamliyAddress() {
		return famliyAddress;
	}
	
	public Integer getFamliyNum() {
		return famliyNum;
	}

	public void setFamliyNum(Integer famliyNum) {
		this.famliyNum = famliyNum;
	}

	public void setFamliyAddress(String famliyAddress) {
		this.famliyAddress = famliyAddress;
	}

	
	
	
	
	public Boolean getFarmersAndHerdsmen() {
		return farmersAndHerdsmen;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public void setFarmersAndHerdsmen(Boolean farmersAndHerdsmen) {
		this.farmersAndHerdsmen = farmersAndHerdsmen;
	}

	
	
	

	@Length(min=0, max=255, message="healthy长度必须介于 0 和 255 之间")
	public String getHealthy() {
		return healthy;
	}

	public void setHealthy(String healthy) {
		this.healthy = healthy;
	}

	
	public Double getHousingArea() {
		return housingArea;
	}

	public void setHousingArea(Double housingArea) {
		this.housingArea = housingArea;
	}

	
	

	@Length(min=0, max=255, message="housingkind长度必须介于 0 和 255 之间")
	public String getHousingKind() {
		return housingKind;
	}

	@NotNull(message="身份证号不能为空")
	@SupCol(isUnique="true")
	@Length(min=1, max=255, message="idCard长度必须介于 1 和 255 之间")
	@ExcelField(title="身份证号", align=2, sort=80)
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public void setHousingKind(String housingKind) {
		this.housingKind = housingKind;
	}

	@Length(min=0, max=255, message="identity长度必须介于 0 和 255 之间")
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	

	@Length(min=0, max=255, message="incomesource长度必须介于 0 和 255 之间")
	public String getIncomeSource() {
		return incomeSource;
	}

	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}

	
	
	public Integer getIncomeSourceKey() {
		return incomeSourceKey;
	}

	public void setIncomeSourceKey(Integer incomeSourceKey) {
		this.incomeSourceKey = incomeSourceKey;
	}

	@Length(min=0, max=255, message="insurance长度必须介于 0 和 255 之间")
	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	
	
	
	public Integer getInsuranceKey() {
		return insuranceKey;
	}

	public void setInsuranceKey(Integer insuranceKey) {
		this.insuranceKey = insuranceKey;
	}

	@Length(min=0, max=255, message="marriage长度必须介于 0 和 255 之间")
	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	
	
	
	public Boolean getMedicalInsurance() {
		return medicalInsurance;
	}

	public void setMedicalInsurance(Boolean medicalInsurance) {
		this.medicalInsurance = medicalInsurance;
	}

	
	@Length(min=0, max=255, message="membershiptime长度必须介于 0 和 255 之间")
	public String getMembershipTime() {
		return membershipTime;
	}

	public void setMembershipTime(String membershipTime) {
		this.membershipTime = membershipTime;
	}

	@Length(min=0, max=255, message="modelworker长度必须介于 0 和 255 之间")
	public String getModelWorker() {
		return modelWorker;
	}

	public void setModelWorker(String modelWorker) {
		this.modelWorker = modelWorker;
	}

	@NotNull(message="名字不能为空")
	@Length(min=0, max=255, message="name长度必须介于 0 和 255 之间")
	@ExcelField(title="名字", align=2, sort=20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="nation长度必须介于 0 和 255 之间")
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
	
	public Integer getNationKey() {
		return nationKey;
	}

	public void setNationKey(Integer nationKey) {
		this.nationKey = nationKey;
	}

	@Length(min=0, max=255, message="organization长度必须介于 0 和 255 之间")
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	
	
	public String getOriginalWorkAddress() {
		return originalWorkAddress;
	}

	public void setOriginalWorkAddress(String originalWorkAddress) {
		this.originalWorkAddress = originalWorkAddress;
	}

	
	@Length(min=0, max=255, message="partinjobtime长度必须介于 0 和 255 之间")
	public String getPartInJobTime() {
		return partInJobTime;
	}

	public void setPartInJobTime(String partInJobTime) {
		this.partInJobTime = partInJobTime;
	}

	@Length(min=0, max=255, message="phonenumber长度必须介于 0 和 255 之间")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Length(min=0, max=255, message="politicallandscape长度必须介于 0 和 255 之间")
	public String getPoliticalLandscape() {
		return politicalLandscape;
	}

	public void setPoliticalLandscape(String politicalLandscape) {
		this.politicalLandscape = politicalLandscape;
	}

	
	@Length(min=0, max=255, message="postcode长度必须介于 0 和 255 之间")
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	
	@Length(min=0, max=255, message="problemkind长度必须介于 0 和 255 之间")
	public String getProblemKind() {
		return problemKind;
	}

	public void setProblemKind(String problemKind) {
		this.problemKind = problemKind;
	}

	
	@Length(min=0, max=255, message="registerkind长度必须介于 0 和 255 之间")
	public String getRegisterKind() {
		return registerKind;
	}

	public void setRegisterKind(String registerKind) {
		this.registerKind = registerKind;
	}

	
	@Length(min=0, max=255, message="registerplace长度必须介于 0 和 255 之间")
	public String getRegisterPlace() {
		return registerPlace;
	}

	public void setRegisterPlace(String registerPlace) {
		this.registerPlace = registerPlace;
	}

	
	public Double getSalaryPerson() {
		return salaryPerson;
	}

	public void setSalaryPerson(Double salaryPerson) {
		this.salaryPerson = salaryPerson;
	}

	public Double getSalaryFamliy() {
		return salaryFamliy;
	}

	public void setSalaryFamliy(Double salaryFamliy) {
		this.salaryFamliy = salaryFamliy;
	}

	
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@ExcelField(title="序号", align=2, sort=1)
	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	
	
	public Boolean getStaticFlag() {
		return staticFlag;
	}

	public void setStaticFlag(Boolean staticFlag) {
		this.staticFlag = staticFlag;
	}

	
	public Boolean getSubsistenceAllowances() {
		return subsistenceAllowances;
	}

	public void setSubsistenceAllowances(Boolean subsistenceAllowances) {
		this.subsistenceAllowances = subsistenceAllowances;
	}

	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getAssistId() {
		return assistId;
	}

	public void setAssistId(Long assistId) {
		this.assistId = assistId;
	}
	
}