/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.famliyship.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;

/**
 * famliyshipEntity
 * @author wzy
 * @version 2017-09-20
 */
public class Famliyrelationship extends DataEntity<Famliyrelationship> {
	
	private static final long serialVersionUID = 1L;
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
	
	/**
	 * 年龄
	 */
	private Integer age;
	
	
	/**
	 * 本人月工资
	 */
	private Double salaryPerson;
	
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
	private Integer famliyNum;
	
	/**身份证**/
	private String idCard;
	
	/**健康状况Key**/
	private Integer healthyKey;
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
	 * 模板对象标识。
	 */
	private Boolean staticFlag;
	
	private Integer version;

	
	private Long userinfoId;// userinfo_id
	private Userinfo userInfo;//会员信息
	
	public Famliyrelationship() {
		super();
	}

	public Famliyrelationship(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="birth长度必须介于 0 和 255 之间")
	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	@Length(min=0, max=255, message="famliyaddress长度必须介于 0 和 255 之间")
	public String getFamliyAddress() {
		return famliyAddress;
	}

	public void setFamliyAddress(String famliyAddress) {
		this.famliyAddress = famliyAddress;
	}
	
	
	public String getHealthy() {
		return healthy;
	}

	public void setHealthy(String healthy) {
		this.healthy = healthy;
	}
	
	@Length(min=0, max=255, message="idCard长度必须介于 0 和 255 之间")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	@Length(min=0, max=255, message="insurance长度必须介于 0 和 255 之间")
	public String getInsurance() {
		return insurance;
	}


	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	
	
	@Length(min=0, max=255, message="jobaddress长度必须介于 0 和 255 之间")
	public String getJobAddress() {
		return jobAddress;
	}

	public void setJobAddress(String jobAddress) {
		this.jobAddress = jobAddress;
	}
	
	@Length(min=0, max=255, message="name长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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
	
	
	@Length(min=0, max=255, message="relationship长度必须介于 0 和 255 之间")
	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	
	
	public Integer getRelationshipKey() {
		return relationshipKey;
	}

	public void setRelationshipKey(Integer relationshipKey) {
		this.relationshipKey = relationshipKey;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public Integer getPoliticalLandscapeKey() {
		return politicalLandscapeKey;
	}

	public void setPoliticalLandscapeKey(Integer politicalLandscapeKey) {
		this.politicalLandscapeKey = politicalLandscapeKey;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Double getSalaryPerson() {
		return salaryPerson;
	}

	public void setSalaryPerson(Double salaryPerson) {
		this.salaryPerson = salaryPerson;
	}

	public Integer getFamliyNum() {
		return famliyNum;
	}

	public void setFamliyNum(Integer famliyNum) {
		this.famliyNum = famliyNum;
	}

	public Integer getInsuranceKey() {
		return insuranceKey;
	}

	public void setInsuranceKey(Integer insuranceKey) {
		this.insuranceKey = insuranceKey;
	}

	public Boolean getStaticFlag() {
		return staticFlag;
	}

	public void setStaticFlag(Boolean staticFlag) {
		this.staticFlag = staticFlag;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getUserinfoId() {
		return userinfoId;
	}

	public void setUserinfoId(Long userinfoId) {
		this.userinfoId = userinfoId;
	}

	public Userinfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Userinfo userInfo) {
		this.userInfo = userInfo;
	}

	public Integer getHealthyKey() {
		return healthyKey;
	}

	public void setHealthyKey(Integer healthyKey) {
		this.healthyKey = healthyKey;
	}
	
	
}