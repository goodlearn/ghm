package com.thinkgem.jeesite.modules.views;

import com.thinkgem.jeesite.modules.userinfo.entity.Userinfo;

/**
 * userInfoEntity
 * @author wzy
 * @version 2017-09-20
 */
public class ViewUserinfo {
	
	private static final long serialVersionUID = 1L;
	private String birth;		// birth
	private String community;		// community
	private String communitykey;		// communitykey
	private String degreeofeducation;		// degreeofeducation
	private String disability;		// disability
	private String famliyaddress;		// famliyaddress
	private String famliynum;		// famliynum
	private String farmersandherdsmen;		// farmersandherdsmen
	private String gender;		// gender
	private String healthy;		// healthy
	private String housingarea;		// housingarea
	private String housingkind;		// housingkind
	private String idcard;		// idcard
	private String identity;		// identity
	private String incomesource;		// incomesource
	private String incomesourcekey;		// incomesourcekey
	private String insurance;		// insurance
	private String insurancekey;		// insurancekey
	private String marriage;		// marriage
	private String medicalinsurance;		// medicalinsurance
	private String membershiptime;		// membershiptime
	private String modelworker;		// modelworker
	private String name;		// name
	private String nation;		// nation
	private String nationkey;		// nationkey
	private String organization;		// organization
	private String originalworkaddress;		// originalworkaddress
	private String partinjobtime;		// partinjobtime
	private String phonenumber;		// phonenumber
	private String politicallandscape;		// politicallandscape
	private String postcode;		// postcode
	private String problemkind;		// problemkind
	private String registerkind;		// registerkind
	private String registerplace;		// registerplace
	private String salaryfamliy;		// salaryfamliy
	private String salaryperson;		// salaryperson
	private String serialnumber;		// serialnumber
	private String staticflag;		// staticflag
	private String subsistenceallowances;		// subsistenceallowances
	private String version;		// version
	private Long assistId;		// assist_id
	
	public void convertToViewData(Userinfo userinfo) {
		this.birth = userinfo.getBirth();
		this.community = userinfo.getCommunity();
		if(null != userinfo.getCommunityKey()) {
			this.communitykey = userinfo.getCommunityKey().toString();
		}
		this.degreeofeducation = userinfo.getDegreeOfEducation();
		this.disability = userinfo.getDisability();
		this.famliyaddress = userinfo.getFamliyAddress();
		this.farmersandherdsmen = userinfo.getFamliyAddress();
		if(null!=userinfo.getGender()) {
			this.gender = userinfo.getGender().toString();
		}
		this.healthy = userinfo.getHealthy();
		if(null!=userinfo.getHousingArea()) {
			this.housingarea = userinfo.getHousingArea().toString();
		}
		this.housingkind = userinfo.getHousingKind();
		this.idcard = userinfo.getIdCard();
		this.identity = userinfo.getIdentity();
		this.incomesource = userinfo.getIncomeSource();
		if(null!=userinfo.getIncomeSourceKey()) {
			this.incomesourcekey = userinfo.getIncomeSourceKey().toString();
		}
		this.insurance = userinfo.getInsurance();
		if(null!=userinfo.getInsuranceKey()) {
			this.insurancekey = userinfo.getInsuranceKey().toString();
		}
		this.marriage = userinfo.getMarriage();
		if(null!=userinfo.getMedicalInsurance()) {
			this.medicalinsurance = userinfo.getMedicalInsurance().toString();
		}
		this.membershiptime = userinfo.getMembershipTime();
		this.modelworker = userinfo.getModelWorker();
		this.name = userinfo.getName();
		this.nation = userinfo.getNation();
		if(null!=userinfo.getNation()) {
			this.nationkey = userinfo.getNationKey().toString();
		}
		this.organization = userinfo.getOrganization();
		this.originalworkaddress = userinfo.getOriginalWorkAddress();
		this.partinjobtime = userinfo.getPartInJobTime();
		this.phonenumber = userinfo.getPhoneNumber();
		this.politicallandscape = userinfo.getPoliticalLandscape();
		this.postcode = userinfo.getPostCode();
		this.problemkind = userinfo.getProblemKind();
		this.registerkind = userinfo.getRegisterKind();
		this.registerplace = userinfo.getRegisterPlace();
		if(null!=userinfo.getSalaryFamliy()) {
			this.salaryfamliy = userinfo.getSalaryFamliy().toString();
		}
		if(null!=userinfo.getSalaryPerson()) {
			this.salaryperson = userinfo.getSalaryPerson().toString();
		}
		if(null!=userinfo.getSerialNumber()) {
			this.serialnumber = userinfo.getSerialNumber().toString();
		}
		if(null!=userinfo.getSubsistenceAllowances()) {
			this.subsistenceallowances = userinfo.getSubsistenceAllowances().toString();
		}
		if(null!=userinfo.getSerialNumber()) {
			this.serialnumber = userinfo.getSerialNumber().toString();
		}
		this.assistId = userinfo.getAssistId();
	}
	
	public ViewUserinfo() {
	}


	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}
	
	public String getCommunitykey() {
		return communitykey;
	}

	public void setCommunitykey(String communitykey) {
		this.communitykey = communitykey;
	}
	
	public String getDegreeofeducation() {
		return degreeofeducation;
	}

	public void setDegreeofeducation(String degreeofeducation) {
		this.degreeofeducation = degreeofeducation;
	}
	
	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}
	
	public String getFamliyaddress() {
		return famliyaddress;
	}

	public void setFamliyaddress(String famliyaddress) {
		this.famliyaddress = famliyaddress;
	}
	
	public String getFamliynum() {
		return famliynum;
	}

	public void setFamliynum(String famliynum) {
		this.famliynum = famliynum;
	}
	
	public String getFarmersandherdsmen() {
		return farmersandherdsmen;
	}

	public void setFarmersandherdsmen(String farmersandherdsmen) {
		this.farmersandherdsmen = farmersandherdsmen;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getHealthy() {
		return healthy;
	}

	public void setHealthy(String healthy) {
		this.healthy = healthy;
	}
	
	public String getHousingarea() {
		return housingarea;
	}

	public void setHousingarea(String housingarea) {
		this.housingarea = housingarea;
	}
	
	public String getHousingkind() {
		return housingkind;
	}

	public void setHousingkind(String housingkind) {
		this.housingkind = housingkind;
	}
	
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public String getIncomesource() {
		return incomesource;
	}

	public void setIncomesource(String incomesource) {
		this.incomesource = incomesource;
	}
	
	public String getIncomesourcekey() {
		return incomesourcekey;
	}

	public void setIncomesourcekey(String incomesourcekey) {
		this.incomesourcekey = incomesourcekey;
	}
	
	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	
	public String getInsurancekey() {
		return insurancekey;
	}

	public void setInsurancekey(String insurancekey) {
		this.insurancekey = insurancekey;
	}
	
	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	
	public String getMedicalinsurance() {
		return medicalinsurance;
	}

	public void setMedicalinsurance(String medicalinsurance) {
		this.medicalinsurance = medicalinsurance;
	}
	
	public String getMembershiptime() {
		return membershiptime;
	}

	public void setMembershiptime(String membershiptime) {
		this.membershiptime = membershiptime;
	}
	
	public String getModelworker() {
		return modelworker;
	}

	public void setModelworker(String modelworker) {
		this.modelworker = modelworker;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	
	public String getNationkey() {
		return nationkey;
	}

	public void setNationkey(String nationkey) {
		this.nationkey = nationkey;
	}
	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public String getOriginalworkaddress() {
		return originalworkaddress;
	}

	public void setOriginalworkaddress(String originalworkaddress) {
		this.originalworkaddress = originalworkaddress;
	}
	
	public String getPartinjobtime() {
		return partinjobtime;
	}

	public void setPartinjobtime(String partinjobtime) {
		this.partinjobtime = partinjobtime;
	}
	
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	public String getPoliticallandscape() {
		return politicallandscape;
	}

	public void setPoliticallandscape(String politicallandscape) {
		this.politicallandscape = politicallandscape;
	}
	
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public String getProblemkind() {
		return problemkind;
	}

	public void setProblemkind(String problemkind) {
		this.problemkind = problemkind;
	}
	
	public String getRegisterkind() {
		return registerkind;
	}

	public void setRegisterkind(String registerkind) {
		this.registerkind = registerkind;
	}
	
	public String getRegisterplace() {
		return registerplace;
	}

	public void setRegisterplace(String registerplace) {
		this.registerplace = registerplace;
	}
	
	public String getSalaryfamliy() {
		return salaryfamliy;
	}

	public void setSalaryfamliy(String salaryfamliy) {
		this.salaryfamliy = salaryfamliy;
	}
	
	public String getSalaryperson() {
		return salaryperson;
	}

	public void setSalaryperson(String salaryperson) {
		this.salaryperson = salaryperson;
	}
	
	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	
	public String getStaticflag() {
		return staticflag;
	}

	public void setStaticflag(String staticflag) {
		this.staticflag = staticflag;
	}
	
	public String getSubsistenceallowances() {
		return subsistenceallowances;
	}

	public void setSubsistenceallowances(String subsistenceallowances) {
		this.subsistenceallowances = subsistenceallowances;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public Long getAssistId() {
		return assistId;
	}

	public void setAssistId(Long assistId) {
		this.assistId = assistId;
	}
	
}