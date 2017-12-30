package com.thinkgem.jeesite.wx.view;

import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;

/**视图转换对象**/
public class ViewUserInfo {

	/**唯一标识**/
	private Long id;
	
	/**序号**/
	private Integer serialNumber;
	
	/**名字**/
	private String name;
	
	/**性别**/
	private Boolean gender;
	
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
	private Integer famliyNum;
	
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
	 * 所在工会组织
	 */
	private String organization;
	
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
	 * 困难类别
	 */
	private String problemKind;
	
	/**
	 * 所在社区
	 */
	private String community;
	
	/**
	 * 保险情况
	 */
	private String insurance;
	
	/**
	 * 收入来源
	 */
	private String incomeSource;
	
	
	/**
	 * 身份
	 */
	private String identity;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	
	/**
	 * 模板对象标识。
	 */
	private Boolean staticFlag = true;
	
	/**
	 * 标志码
	 */
	private int code = 0;
	
	private Integer version;
	
	private Integer communityKey;
	private Integer insuranceKey;
	private Integer incomeSourceKey;
	private Integer nationKey;
	
	public ViewUserInfo(){
		
	}

	


	/**
	 * 补充数据转换
	 */
	public void convertToSupplyDbModel(Userinfo userInfo){
		userInfo.setIdCard(idCard);
		userInfo.setName(name);
		userInfo.setIncomeSourceKey(incomeSourceKey);
		userInfo.setCommunityKey(communityKey);
		userInfo.setInsuranceKey(insuranceKey);
		userInfo.setNationKey(nationKey);
		userInfo.setHealthy(healthy);
		userInfo.setGender(gender);
		userInfo.setNation(nation);
		userInfo.setDisability(disability);
		userInfo.setOriginalWorkAddress(originalWorkAddress);
		userInfo.setBirth(birth);
		userInfo.setPhoneNumber(phoneNumber);
		userInfo.setModelWorker(modelWorker);
		userInfo.setMarriage(marriage);
		userInfo.setFamliyAddress(famliyAddress);
		userInfo.setPostCode(postCode);
		userInfo.setHousingArea(housingArea);
		userInfo.setHousingKind(housingKind);
		userInfo.setFamliyNum(famliyNum);
		userInfo.setRegisterKind(registerKind);
		userInfo.setRegisterPlace(registerPlace);
		userInfo.setSalaryFamliy(salaryFamliy);
		userInfo.setSalaryPerson(salaryPerson);
		userInfo.setMedicalInsurance(medicalInsurance);
		userInfo.setSubsistenceAllowances(subsistenceAllowances);
		userInfo.setProblemKind(problemKind);
		userInfo.setCommunity(community);
		userInfo.setInsurance(insurance);
		userInfo.setIncomeSource(incomeSource);
		userInfo.setIdentity(identity);
		userInfo.setDegreeOfEducation(degreeOfEducation);
		userInfo.setPartInJobTime(partInJobTime);
		userInfo.setMembershipTime(membershipTime);
		userInfo.setOrganization(organization);
		userInfo.setPoliticalLandscape(politicalLandscape);
		userInfo.setFarmersAndHerdsmen(farmersAndHerdsmen);
		userInfo.setRemarks(remarks);
		userInfo.setStaticFlag(true);
		userInfo.setVersion(version);
	}

	/**
	 * 数据转换
	 * @return
	 */
	public void convertToVoModel(Userinfo userInfo){
		this.id = Long.valueOf(userInfo.getId());
		this.serialNumber = userInfo.getSerialNumber();
		this.name = userInfo.getName();
		this.gender = userInfo.getGender();
		this.nation = userInfo.getNation();
		this.birth = userInfo.getBirth();
		this.degreeOfEducation = userInfo.getDegreeOfEducation();
		this.politicalLandscape = userInfo.getPoliticalLandscape();
		this.partInJobTime = userInfo.getPartInJobTime();
		this.membershipTime = userInfo.getMembershipTime();
		this.idCard = userInfo.getIdCard();
		this.phoneNumber = userInfo.getPhoneNumber();
		this.farmersAndHerdsmen = userInfo.getFarmersAndHerdsmen();
		this.staticFlag = userInfo.getStaticFlag();
		this.healthy=userInfo.getHealthy();		
		this.disability=userInfo.getDisability();	
		this.originalWorkAddress=userInfo.getOriginalWorkAddress();		
		this.modelWorker=userInfo.getModelWorker();
		this.marriage=userInfo.getMarriage();
		this.famliyAddress=userInfo.getFamliyAddress();
		this.postCode=userInfo.getPostCode();
		this.housingKind=userInfo.getHousingKind();
		this.housingArea=userInfo.getHousingArea();
		this.famliyNum=userInfo.getFamliyNum();
		this.registerPlace=userInfo.getRegisterPlace();
		this.registerKind=userInfo.getRegisterKind();
		this.salaryPerson=userInfo.getSalaryPerson();
		this.salaryFamliy=userInfo.getSalaryFamliy();
		this.medicalInsurance=userInfo.getMedicalInsurance();
		this.subsistenceAllowances=userInfo.getSubsistenceAllowances();
		this.communityKey = userInfo.getCommunityKey();
		this.insuranceKey = userInfo.getInsuranceKey();
		this.nationKey = userInfo.getNationKey();
		this.incomeSourceKey = userInfo.getIncomeSourceKey();
		this.community = userInfo.getCommunity();
		this.insurance = userInfo.getInsurance();
		this.incomeSource = userInfo.getIncomeSource();
		this.identity = userInfo.getIdentity();
		this.problemKind = userInfo.getProblemKind();
		this.organization = userInfo.getOrganization();
		this.remarks = userInfo.getRemarks();
		this.version = userInfo.getVersion();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getDegreeOfEducation() {
		return degreeOfEducation;
	}

	public void setDegreeOfEducation(String degreeOfEducation) {
		this.degreeOfEducation = degreeOfEducation;
	}

	public String getPoliticalLandscape() {
		return politicalLandscape;
	}

	public void setPoliticalLandscape(String politicalLandscape) {
		this.politicalLandscape = politicalLandscape;
	}

	public String getPartInJobTime() {
		return partInJobTime;
	}

	public void setPartInJobTime(String partInJobTime) {
		this.partInJobTime = partInJobTime;
	}

	public String getMembershipTime() {
		return membershipTime;
	}

	public void setMembershipTime(String membershipTime) {
		this.membershipTime = membershipTime;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Boolean getFarmersAndHerdsmen() {
		return farmersAndHerdsmen;
	}

	public void setFarmersAndHerdsmen(Boolean farmersAndHerdsmen) {
		this.farmersAndHerdsmen = farmersAndHerdsmen;
	}

	public Boolean getStaticFlag() {
		return staticFlag;
	}

	public void setStaticFlag(Boolean staticFlag) {
		this.staticFlag = staticFlag;
	}

	

	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}

	
	
	public String getOrganization() {
		return organization;
	}




	public void setOrganization(String organization) {
		this.organization = organization;
	}




	public String getHealthy() {
		return healthy;
	}


	public void setHealthy(String healthy) {
		this.healthy = healthy;
	}


	public String getDisability() {
		return disability;
	}


	public void setDisability(String disability) {
		this.disability = disability;
	}


	public String getOriginalWorkAddress() {
		return originalWorkAddress;
	}


	public void setOriginalWorkAddress(String originalWorkAddress) {
		this.originalWorkAddress = originalWorkAddress;
	}


	public String getModelWorker() {
		return modelWorker;
	}


	public void setModelWorker(String modelWorker) {
		this.modelWorker = modelWorker;
	}


	public String getMarriage() {
		return marriage;
	}


	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}


	public String getFamliyAddress() {
		return famliyAddress;
	}


	public void setFamliyAddress(String famliyAddress) {
		this.famliyAddress = famliyAddress;
	}


	public String getPostCode() {
		return postCode;
	}


	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}


	public String getHousingKind() {
		return housingKind;
	}


	public void setHousingKind(String housingKind) {
		this.housingKind = housingKind;
	}


	public Double getHousingArea() {
		return housingArea;
	}


	public void setHousingArea(Double housingArea) {
		this.housingArea = housingArea;
	}


	public Integer getFamliyNum() {
		return famliyNum;
	}


	public void setFamliyNum(Integer famliyNum) {
		this.famliyNum = famliyNum;
	}


	public String getRegisterPlace() {
		return registerPlace;
	}


	public void setRegisterPlace(String registerPlace) {
		this.registerPlace = registerPlace;
	}


	public String getRegisterKind() {
		return registerKind;
	}


	public void setRegisterKind(String registerKind) {
		this.registerKind = registerKind;
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


	public Boolean getMedicalInsurance() {
		return medicalInsurance;
	}


	public void setMedicalInsurance(Boolean medicalInsurance) {
		this.medicalInsurance = medicalInsurance;
	}


	public Boolean getSubsistenceAllowances() {
		return subsistenceAllowances;
	}


	public void setSubsistenceAllowances(Boolean subsistenceAllowances) {
		this.subsistenceAllowances = subsistenceAllowances;
	}




	public String getProblemKind() {
		return problemKind;
	}




	public void setProblemKind(String problemKind) {
		this.problemKind = problemKind;
	}




	public String getCommunity() {
		return community;
	}




	public void setCommunity(String community) {
		this.community = community;
	}




	public String getInsurance() {
		return insurance;
	}




	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}




	public String getIncomeSource() {
		return incomeSource;
	}


	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}




	public String getIdentity() {
		return identity;
	}




	public void setIdentity(String identity) {
		this.identity = identity;
	}




	public Integer getCommunityKey() {
		return communityKey;
	}




	public void setCommunityKey(Integer communityKey) {
		this.communityKey = communityKey;
	}



	
	
	public Integer getInsuranceKey() {
		return insuranceKey;
	}




	public void setInsuranceKey(Integer insuranceKey) {
		this.insuranceKey = insuranceKey;
	}


	public Integer getIncomeSourceKey() {
		return incomeSourceKey;
	}




	public void setIncomeSourceKey(Integer incomeSourceKey) {
		this.incomeSourceKey = incomeSourceKey;
	}




	public Integer getNationKey() {
		return nationKey;
	}




	public void setNationKey(Integer nationKey) {
		this.nationKey = nationKey;
	}




	public String getRemarks() {
		return remarks;
	}




	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}




	public Integer getVersion() {
		return version;
	}




	public void setVersion(Integer version) {
		this.version = version;
	}




	@Override
	public String toString() {
		return "ViewUserInfo [id=" + id + ", serialNumber=" + serialNumber + ", name=" + name + ", gender=" + gender
				+ ", nation=" + nation + ", birth=" + birth + ", degreeOfEducation=" + degreeOfEducation
				+ ", politicalLandscape=" + politicalLandscape + ", partInJobTime=" + partInJobTime
				+ ", membershipTime=" + membershipTime + ", idCard=" + idCard + ", phoneNumber=" + phoneNumber
				+ ", farmersAndHerdsmen=" + farmersAndHerdsmen + ", healthy=" + healthy + ", disability=" + disability
				+ ", originalWorkAddress=" + originalWorkAddress + ", modelWorker=" + modelWorker + ", marriage="
				+ marriage + ", famliyAddress=" + famliyAddress + ", postCode=" + postCode + ", housingKind="
				+ housingKind + ", housingArea=" + housingArea + ", famliyNum=" + famliyNum + ", registerPlace="
				+ registerPlace + ", registerKind=" + registerKind + ", salaryPerson=" + salaryPerson
				+ ", salaryFamliy=" + salaryFamliy + ", medicalInsurance=" + medicalInsurance
				+ ", subsistenceAllowances=" + subsistenceAllowances + ", problemKind=" + problemKind + ", community="
				+ community + ", insurance=" + insurance + ", incomeSource=" + incomeSource + ", identity=" + identity
				+ ", staticFlag=" + staticFlag + ", code=" + code + ", communityKey=" + communityKey + ", insuranceKey="
				+ insuranceKey + ", incomeSourceKey=" + incomeSourceKey + ", nationKey=" + nationKey + "]";
	}

	
}
