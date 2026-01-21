package com.SCM.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Distributor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String tradeName;
	private String billingAddress;
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

	@Column(unique = true)
	private String perEmail;
	private String alterEmail;
	private String creditLimit;
	private String creditDays;
	private String transporterName;
	private String deliveryLocation;
	private String discountStructure;
	private float boxProductDiscount;
	private float kgProductDiscount;
	private float corporaetProductDiscount;
	private float cookerProductDiscount;
	private float noshProductDiscount;
	private float schemeDiscount;
	private float schemeboxProductDiscount;
	private float schemekgProductDiscount;
	private float schemecorporateProductDiscount;
	private float schemecookerProductDiscount;
	private float schemenoshProductDisocunt;
	private String distributorName1;
	private String distributorName2;
	private String paymentTerms;
	private java.sql.Date dob1;
	private java.sql.Date dob2;
	private java.sql.Date doa1;
	private java.sql.Date doa2;
	private long mobNo1;
	private long mobNo2;
	private String email1;
	private String email2;
	private int aseid;
	private int asmid;
	private int rsmid;
	private int nsmid;
	private int stateid;
	private int zonesid;
	private String password;
	private String passwordDecrypted;
	
	private String aadharcard;
	private String pancard;
	private LocalDate createddate;
	private LocalTime createdtime;
	private String colourtype;
	private String emailotp;
	private Instant time;
	private String emailLoginStatus;
	
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "distributor_id")
	private List<DistributorAddress> deliveryAddress;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "distributor_roles", joinColumns = @JoinColumn(name = "distributor_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "distributor")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonManagedReference
	private List<Retailer> retailer = new ArrayList<>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "distributor_id")
	private List<DistributorToStaff> distributorToStaffs; 
	
	@ManyToOne
	@JoinColumn(name="groupn1")
	private Groupn1 groupn1;
	
	@ManyToOne
	@JoinColumn(name="groupn2")
	private Groupn2 groupn2;
	
	@ManyToOne
	@JoinColumn(name="groupn3")
	private Groupn3 groupn3;
	
	@ManyToOne
	@JoinColumn(name="groupn4")
	private Groupn4 groupn4;
	
	@ManyToOne
	@JoinColumn(name="groupn5")
	private Groupn5 groupn5;
	
	

	private long createdby;
	private String createbyname;
	private String role;

	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	
	@Column(nullable = true)
	private Double Latitude;
	@Column(nullable=true)
	private Double Longitude;
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
	public float getBoxProductDiscount() {
		return boxProductDiscount;
	}
	public void setBoxProductDiscount(float boxProductDiscount) {
		this.boxProductDiscount = boxProductDiscount;
	}
	public float getKgProductDiscount() {
		return kgProductDiscount;
	}
	public void setKgProductDiscount(float kgProductDiscount) {
		this.kgProductDiscount = kgProductDiscount;
	}
	public float getCorporaetProductDiscount() {
		return corporaetProductDiscount;
	}
	public void setCorporaetProductDiscount(float corporaetProductDiscount) {
		this.corporaetProductDiscount = corporaetProductDiscount;
	}
	public float getCookerProductDiscount() {
		return cookerProductDiscount;
	}
	public void setCookerProductDiscount(float cookerProductDiscount) {
		this.cookerProductDiscount = cookerProductDiscount;
	}
	public float getNoshProductDiscount() {
		return noshProductDiscount;
	}
	public void setNoshProductDiscount(float noshProductDiscount) {
		this.noshProductDiscount = noshProductDiscount;
	}
	public float getSchemeDiscount() {
		return schemeDiscount;
	}
	public void setSchemeDiscount(float schemeDiscount) {
		this.schemeDiscount = schemeDiscount;
	}
	public float getSchemeboxProductDiscount() {
		return schemeboxProductDiscount;
	}
	public void setSchemeboxProductDiscount(float schemeboxProductDiscount) {
		this.schemeboxProductDiscount = schemeboxProductDiscount;
	}
	public float getSchemekgProductDiscount() {
		return schemekgProductDiscount;
	}
	public void setSchemekgProductDiscount(float schemekgProductDiscount) {
		this.schemekgProductDiscount = schemekgProductDiscount;
	}
	public float getSchemecorporateProductDiscount() {
		return schemecorporateProductDiscount;
	}
	public void setSchemecorporateProductDiscount(float schemecorporateProductDiscount) {
		this.schemecorporateProductDiscount = schemecorporateProductDiscount;
	}
	public float getSchemecookerProductDiscount() {
		return schemecookerProductDiscount;
	}
	public void setSchemecookerProductDiscount(float schemecookerProductDiscount) {
		this.schemecookerProductDiscount = schemecookerProductDiscount;
	}
	public float getSchemenoshProductDisocunt() {
		return schemenoshProductDisocunt;
	}
	public void setSchemenoshProductDisocunt(float schemenoshProductDisocunt) {
		this.schemenoshProductDisocunt = schemenoshProductDisocunt;
	}
	public String getDistributorName1() {
		return distributorName1;
	}
	public void setDistributorName1(String distributorName1) {
		this.distributorName1 = distributorName1;
	}
	public String getDistributorName2() {
		return distributorName2;
	}
	public void setDistributorName2(String distributorName2) {
		this.distributorName2 = distributorName2;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public java.sql.Date getDob1() {
		return dob1;
	}
	public void setDob1(java.sql.Date dob1) {
		this.dob1 = dob1;
	}
	public java.sql.Date getDob2() {
		return dob2;
	}
	public void setDob2(java.sql.Date dob2) {
		this.dob2 = dob2;
	}
	public java.sql.Date getDoa1() {
		return doa1;
	}
	public void setDoa1(java.sql.Date doa1) {
		this.doa1 = doa1;
	}
	public java.sql.Date getDoa2() {
		return doa2;
	}
	public void setDoa2(java.sql.Date doa2) {
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
	public int getAseid() {
		return aseid;
	}
	public void setAseid(int aseid) {
		this.aseid = aseid;
	}
	public int getAsmid() {
		return asmid;
	}
	public void setAsmid(int asmid) {
		this.asmid = asmid;
	}
	public int getRsmid() {
		return rsmid;
	}
	public void setRsmid(int rsmid) {
		this.rsmid = rsmid;
	}
	public int getNsmid() {
		return nsmid;
	}
	public void setNsmid(int nsmid) {
		this.nsmid = nsmid;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordDecrypted() {
		return passwordDecrypted;
	}
	public void setPasswordDecrypted(String passwordDecrypted) {
		this.passwordDecrypted = passwordDecrypted;
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
	public String getColourtype() {
		return colourtype;
	}
	public void setColourtype(String colourtype) {
		this.colourtype = colourtype;
	}
	public String getEmailotp() {
		return emailotp;
	}
	public void setEmailotp(String emailotp) {
		this.emailotp = emailotp;
	}
	public Instant getTime() {
		return time;
	}
	public void setTime(Instant time) {
		this.time = time;
	}
	public String getEmailLoginStatus() {
		return emailLoginStatus;
	}
	public void setEmailLoginStatus(String emailLoginStatus) {
		this.emailLoginStatus = emailLoginStatus;
	}
	public List<DistributorAddress> getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(List<DistributorAddress> deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public List<Retailer> getRetailer() {
		return retailer;
	}
	public void setRetailer(List<Retailer> retailer) {
		this.retailer = retailer;
	}
	public List<DistributorToStaff> getDistributorToStaffs() {
		return distributorToStaffs;
	}
	public void setDistributorToStaffs(List<DistributorToStaff> distributorToStaffs) {
		this.distributorToStaffs = distributorToStaffs;
	}
	public Groupn1 getGroupn1() {
		return groupn1;
	}
	public void setGroupn1(Groupn1 groupn1) {
		this.groupn1 = groupn1;
	}
	public Groupn2 getGroupn2() {
		return groupn2;
	}
	public void setGroupn2(Groupn2 groupn2) {
		this.groupn2 = groupn2;
	}
	public Groupn3 getGroupn3() {
		return groupn3;
	}
	public void setGroupn3(Groupn3 groupn3) {
		this.groupn3 = groupn3;
	}
	public Groupn4 getGroupn4() {
		return groupn4;
	}
	public void setGroupn4(Groupn4 groupn4) {
		this.groupn4 = groupn4;
	}
	public Groupn5 getGroupn5() {
		return groupn5;
	}
	public void setGroupn5(Groupn5 groupn5) {
		this.groupn5 = groupn5;
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
	public Double getLatitude() {
		return Latitude;
	}
	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}
	public Double getLongitude() {
		return Longitude;
	}
	public void setLongitude(Double longitude) {
		Longitude = longitude;
	}
	
	
	

	


	
	

}
