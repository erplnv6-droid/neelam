package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class RetailerTemporary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String tradeName;
	private String billingAddress;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "temp_retailer_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<RetailerTemporaryAddress> deliveryAddress;

	private String state;
	private String statecode;
	private String city;
	private String country;
	private String panNumber;
	private String gstNumber;
	private String gstType;
	private String pinCode;
	private String perMobileNumber;
	private String alterMobileNumber;
	private String perEmail;
	private String alterEmail;
	private String creditLimit;
	private String creditDays;
	private String transporterName;
	private String deliveryLocation;
	private String discountStructure;
	private String retailerName1;
	private String retailerName2;
	private Date dob1;
	private Date dob2;
	private Date doa1;
	private Date doa2;
	private long mobNo1;
	private long mobNo2;
	private String email1;
	private String email2;
	private boolean activestatus;
	private int stateid;
	private int zonesid;
	private boolean authorized;
	private String aadharcard;
	private String pancard;
	private Integer distributorId;
	private String referredBy;

	private LocalDate createddate;
	private LocalTime createdtime;
	private long createdby;
	private String createbyname;
	private String role;

	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	// @Cascade(org.hibernate.annotations.CascadeType.DELETE)
	@JoinTable(name = "retailertemp_role", joinColumns = @JoinColumn(name = "tempretailer_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	private String Latitude;
	private String Longitude;
	
	
	
	
	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public List<RetailerTemporaryAddress> getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(List<RetailerTemporaryAddress> deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatecode() {
		return statecode;
	}

	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getGstType() {
		return gstType;
	}

	public void setGstType(String gstType) {
		this.gstType = gstType;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getPerMobileNumber() {
		return perMobileNumber;
	}

	public void setPerMobileNumber(String perMobileNumber) {
		this.perMobileNumber = perMobileNumber;
	}

	public String getAlterMobileNumber() {
		return alterMobileNumber;
	}

	public void setAlterMobileNumber(String alterMobileNumber) {
		this.alterMobileNumber = alterMobileNumber;
	}

	public String getPerEmail() {
		return perEmail;
	}

	public void setPerEmail(String perEmail) {
		this.perEmail = perEmail;
	}

	public String getAlterEmail() {
		return alterEmail;
	}

	public void setAlterEmail(String alterEmail) {
		this.alterEmail = alterEmail;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getCreditDays() {
		return creditDays;
	}

	public void setCreditDays(String creditDays) {
		this.creditDays = creditDays;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public String getDeliveryLocation() {
		return deliveryLocation;
	}

	public void setDeliveryLocation(String deliveryLocation) {
		this.deliveryLocation = deliveryLocation;
	}

	public String getDiscountStructure() {
		return discountStructure;
	}

	public void setDiscountStructure(String discountStructure) {
		this.discountStructure = discountStructure;
	}

	public String getRetailerName1() {
		return retailerName1;
	}

	public void setRetailerName1(String retailerName1) {
		this.retailerName1 = retailerName1;
	}

	public String getRetailerName2() {
		return retailerName2;
	}

	public void setRetailerName2(String retailerName2) {
		this.retailerName2 = retailerName2;
	}

	public Date getDob1() {
		return dob1;
	}

	public void setDob1(Date dob1) {
		this.dob1 = dob1;
	}

	public Date getDob2() {
		return dob2;
	}

	public void setDob2(Date dob2) {
		this.dob2 = dob2;
	}

	public Date getDoa1() {
		return doa1;
	}

	public void setDoa1(Date doa1) {
		this.doa1 = doa1;
	}

	public Date getDoa2() {
		return doa2;
	}

	public void setDoa2(Date doa2) {
		this.doa2 = doa2;
	}

	public long getMobNo1() {
		return mobNo1;
	}

	public void setMobNo1(long mobNo1) {
		this.mobNo1 = mobNo1;
	}

	public long getMobNo2() {
		return mobNo2;
	}

	public void setMobNo2(long mobNo2) {
		this.mobNo2 = mobNo2;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public boolean isActivestatus() {
		return activestatus;
	}

	public void setActivestatus(boolean activestatus) {
		this.activestatus = activestatus;
	}

	public int getStateid() {
		return stateid;
	}

	public void setStateid(int stateid) {
		this.stateid = stateid;
	}

	public int getZonesid() {
		return zonesid;
	}

	public void setZonesid(int zonesid) {
		this.zonesid = zonesid;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public String getAadharcard() {
		return aadharcard;
	}

	public void setAadharcard(String aadharcard) {
		this.aadharcard = aadharcard;
	}

	public String getPancard() {
		return pancard;
	}

	public void setPancard(String pancard) {
		this.pancard = pancard;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	public Integer getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(Integer distributorId) {
		this.distributorId = distributorId;
	}

	public LocalDate getCreateddate() {
		return createddate;
	}

	public void setCreateddate(LocalDate createddate) {
		this.createddate = createddate;
	}

	public LocalTime getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(LocalTime createdtime) {
		this.createdtime = createdtime;
	}

	public long getCreatedby() {
		return createdby;
	}

	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}

	public String getCreatebyname() {
		return createbyname;
	}

	public void setCreatebyname(String createbyname) {
		this.createbyname = createbyname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDate getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(LocalDate updateddate) {
		this.updateddate = updateddate;
	}

	public LocalTime getUpdatedtime() {
		return updatedtime;
	}

	public void setUpdatedtime(LocalTime updatedtime) {
		this.updatedtime = updatedtime;
	}

	public long getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(long updatedby) {
		this.updatedby = updatedby;
	}

	public String getUpdatedbyname() {
		return updatedbyname;
	}

	public void setUpdatedbyname(String updatedbyname) {
		this.updatedbyname = updatedbyname;
	}

	public String getUpdatedrole() {
		return updatedrole;
	}

	public void setUpdatedrole(String updatedrole) {
		this.updatedrole = updatedrole;
	}

	@Override
	public String toString() {
		return "RetailerTemporary [id=" + id + ", tradeName=" + tradeName + ", billingAddress=" + billingAddress
				+ ", deliveryAddress=" + deliveryAddress + ", state=" + state + ", statecode=" + statecode + ", city="
				+ city + ", country=" + country + ", panNumber=" + panNumber + ", gstNumber=" + gstNumber + ", gstType="
				+ gstType + ", pinCode=" + pinCode + ", perMobileNumber=" + perMobileNumber + ", alterMobileNumber="
				+ alterMobileNumber + ", perEmail=" + perEmail + ", alterEmail=" + alterEmail + ", creditLimit="
				+ creditLimit + ", creditDays=" + creditDays + ", transporterName=" + transporterName
				+ ", deliveryLocation=" + deliveryLocation + ", discountStructure=" + discountStructure
				+ ", retailerName1=" + retailerName1 + ", retailerName2=" + retailerName2 + ", dob1=" + dob1 + ", dob2="
				+ dob2 + ", doa1=" + doa1 + ", doa2=" + doa2 + ", mobNo1=" + mobNo1 + ", mobNo2=" + mobNo2 + ", email1="
				+ email1 + ", email2=" + email2 + ", activestatus=" + activestatus + ", stateid=" + stateid
				+ ", zonesid=" + zonesid + ", authorized=" + authorized + ", aadharcard=" + aadharcard + ", pancard="
				+ pancard + ", distributorId=" + distributorId + ", referredBy=" + referredBy + ", roles=" + roles
				+ "]";
	}

}
