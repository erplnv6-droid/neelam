package com.SCM.model;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String companyname;
	private String phonenumber;
	private String suppliername;
	private String gstno;
	private String email;
	private String password;
	private String passwordDecrypted;
	private String address;
	private String zipcode;
	private String statecode;
	private long accountno;
	private String colourtype;
	private String bankname;
	private String branch;
	private String ifsc;
	private String openingbal;
	private String openingbaltype;
	private Date openingbaldate;
	private String cities;
	private String emailotp;
	private Instant time;
	private String emailLoginStatus;
	private String termsofpayment;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "supplier_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<SupplierAddress> supplierAddresses;

	@ManyToOne
	@JoinColumn(name = "statezoneId")
	private State_Zone state_Zone;

	@ManyToOne
	@JoinColumn(name = "countryId")
	private Country country;

	@ManyToOne
	@JoinColumn(name = "statesId")
	private States states;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "supplier_roles", joinColumns = @JoinColumn(name = "supplier_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "supplier_id")
	private List<SupplierSubContacts> supplierSubContacts;

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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getGstno() {
		return gstno;
	}
	public void setGstno(String gstno) {
		this.gstno = gstno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getStatecode() {
		return statecode;
	}
	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}
	public long getAccountno() {
		return accountno;
	}
	public void setAccountno(long accountno) {
		this.accountno = accountno;
	}
	public String getColourtype() {
		return colourtype;
	}
	public void setColourtype(String colourtype) {
		this.colourtype = colourtype;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getIfsc() {
		return ifsc;
	}
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	public String getOpeningbal() {
		return openingbal;
	}
	public void setOpeningbal(String openingbal) {
		this.openingbal = openingbal;
	}
	public String getOpeningbaltype() {
		return openingbaltype;
	}
	public void setOpeningbaltype(String openingbaltype) {
		this.openingbaltype = openingbaltype;
	}
	public Date getOpeningbaldate() {
		return openingbaldate;
	}
	public void setOpeningbaldate(Date openingbaldate) {
		this.openingbaldate = openingbaldate;
	}
	public String getCities() {
		return cities;
	}
	public void setCities(String cities) {
		this.cities = cities;
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
	public String getTermsofpayment() {
		return termsofpayment;
	}
	public void setTermsofpayment(String termsofpayment) {
		this.termsofpayment = termsofpayment;
	}
	public List<SupplierAddress> getSupplierAddresses() {
		return supplierAddresses;
	}
	public void setSupplierAddresses(List<SupplierAddress> supplierAddresses) {
		this.supplierAddresses = supplierAddresses;
	}
	public State_Zone getState_Zone() {
		return state_Zone;
	}
	public void setState_Zone(State_Zone state_Zone) {
		this.state_Zone = state_Zone;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public States getStates() {
		return states;
	}
	public void setStates(States states) {
		this.states = states;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public List<SupplierSubContacts> getSupplierSubContacts() {
		return supplierSubContacts;
	}
	public void setSupplierSubContacts(List<SupplierSubContacts> supplierSubContacts) {
		this.supplierSubContacts = supplierSubContacts;
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
	

}
