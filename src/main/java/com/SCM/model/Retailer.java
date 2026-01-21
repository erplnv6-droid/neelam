package com.SCM.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Retailer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String tradeName;
	private String billingAddress;

	private String city;
	private String country;
	private String panNumber;
	private String gstNumber;
	private String gstType;
	private String pinCode;
	private String perMobileNumber;
	private String alterMobileNumber;
	private String paymentTerms;

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
	private float schemeboxProductDiscount;
	private float schemekgProductDiscount;
	private float schemecorporateProductDiscount;
	private float schemecookerProductDiscount;
	private float schemenoshProductDisocunt;
	private boolean activestatus;
	private int aseid;
	private int asmid;
	private int rsmid;
	private int nsmid;
	private int stateid;
	private int zonesid;
	private boolean authorized;
	private String password;
	private String passwordDecrypted;
	private String aadharcard;
	private String pancard;
	private boolean convertTempret;
	private int tempretid;
	private String referredBy;
	private String retailerstatus;
	private LocalDate createddate;
	private LocalTime createdtime;
	private String colourtype;
	private String emailotp;
	private Instant time;
	private String emailLoginStatus;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "retailer_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<RetailerAddress> deliveryAddress;

	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "retailer_role", joinColumns = @JoinColumn(name = "retailer_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "retailerId")
	private List<WorkOrder> workOrders = new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.DETACH) // no error while adding and updating retailer and distributor
	@JoinColumn(name = "distributorId")
	@JsonBackReference
	private Distributor distributor;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "retialer_id")
	private List<RetailerToStaff> retailerToStaff;
	
	
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

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
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

	public boolean isActivestatus() {
		return activestatus;
	}

	public void setActivestatus(boolean activestatus) {
		this.activestatus = activestatus;
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

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
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

	public boolean isConvertTempret() {
		return convertTempret;
	}

	public void setConvertTempret(boolean convertTempret) {
		this.convertTempret = convertTempret;
	}

	public int getTempretid() {
		return tempretid;
	}

	public void setTempretid(int tempretid) {
		this.tempretid = tempretid;
	}

	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	public String getRetailerstatus() {
		return retailerstatus;
	}

	public void setRetailerstatus(String retailerstatus) {
		this.retailerstatus = retailerstatus;
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

	public List<RetailerAddress> getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(List<RetailerAddress> deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<WorkOrder> getWorkOrders() {
		return workOrders;
	}

	public void setWorkOrders(List<WorkOrder> workOrders) {
		this.workOrders = workOrders;
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public List<RetailerToStaff> getRetailerToStaff() {
		return retailerToStaff;
	}

	public void setRetailerToStaff(List<RetailerToStaff> retailerToStaff) {
		this.retailerToStaff = retailerToStaff;
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

	@Override
	public String toString() {
		return "Retailer [id=" + id + ", tradeName=" + tradeName + ", billingAddress=" + billingAddress + ", city="
				+ city + ", country=" + country + ", panNumber=" + panNumber + ", gstNumber=" + gstNumber + ", gstType="
				+ gstType + ", pinCode=" + pinCode + ", perMobileNumber=" + perMobileNumber + ", alterMobileNumber="
				+ alterMobileNumber + ", paymentTerms=" + paymentTerms + ", perEmail=" + perEmail + ", alterEmail="
				+ alterEmail + ", creditLimit=" + creditLimit + ", creditDays=" + creditDays + ", transporterName="
				+ transporterName + ", deliveryLocation=" + deliveryLocation + ", discountStructure="
				+ discountStructure + ", boxProductDiscount=" + boxProductDiscount + ", kgProductDiscount="
				+ kgProductDiscount + ", corporaetProductDiscount=" + corporaetProductDiscount
				+ ", cookerProductDiscount=" + cookerProductDiscount + ", noshProductDiscount=" + noshProductDiscount
				+ ", schemeDiscount=" + schemeDiscount + ", retailerName1=" + retailerName1 + ", retailerName2="
				+ retailerName2 + ", dob1=" + dob1 + ", dob2=" + dob2 + ", doa1=" + doa1 + ", doa2=" + doa2
				+ ", mobNo1=" + mobNo1 + ", mobNo2=" + mobNo2 + ", email1=" + email1 + ", email2=" + email2
				+ ", schemeboxProductDiscount=" + schemeboxProductDiscount + ", schemekgProductDiscount="
				+ schemekgProductDiscount + ", schemecorporateProductDiscount=" + schemecorporateProductDiscount
				+ ", schemecookerProductDiscount=" + schemecookerProductDiscount + ", schemenoshProductDisocunt="
				+ schemenoshProductDisocunt + ", activestatus=" + activestatus + ", aseid=" + aseid + ", asmid=" + asmid
				+ ", rsmid=" + rsmid + ", nsmid=" + nsmid + ", stateid=" + stateid + ", zonesid=" + zonesid
				+ ", authorized=" + authorized + ", password=" + password + ", passwordDecrypted=" + passwordDecrypted
				+ ", aadharcard=" + aadharcard + ", pancard=" + pancard + ", convertTempret=" + convertTempret
				+ ", tempretid=" + tempretid + ", referredBy=" + referredBy + ", retailerstatus=" + retailerstatus
				+ ", createddate=" + createddate + ", createdtime=" + createdtime + ", colourtype=" + colourtype
				+ ", emailotp=" + emailotp + ", time=" + time + ", emailLoginStatus=" + emailLoginStatus
				+ ", deliveryAddress=" + deliveryAddress + ", roles=" + roles + ", workOrders=" + workOrders
				+ ", distributor=" + distributor + ", retailerToStaff=" + retailerToStaff + ", createdby=" + createdby
				+ ", createbyname=" + createbyname + ", role=" + role + ", updateddate=" + updateddate
				+ ", updatedtime=" + updatedtime + ", updatedby=" + updatedby + ", updatedbyname=" + updatedbyname
				+ ", updatedrole=" + updatedrole + ", Latitude=" + Latitude + ", Longitude=" + Longitude + ", groupn1="
				+ groupn1 + ", groupn2=" + groupn2 + ", groupn3=" + groupn3 + ", groupn4=" + groupn4 + ", groupn5="
				+ groupn5 + "]";
	}
	



	
}
