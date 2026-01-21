package com.SCM.payload;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.SCM.dto.MultipleStaffDto;
import com.SCM.model.Branch;
import com.SCM.model.Role;
import com.SCM.model.StaffLocation;
import com.SCM.model.State_Zone;
import com.SCM.model.Zone;

public class StaffRequest {

	@NotBlank
	@Size(min = 3, max = 60)
	private String staffName;
	private String doj;
	private String address;

	@NotNull
	@Pattern(regexp = "^\\d{10}$", message = "Phone number must be between 10 and 11 characters")
	@Column(unique = true)
	private String mobileNumber;

	@Column(unique = true)
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email is not valid", regexp = "^[a-z-0-9_!#$%&’*+/=?`{|}~^.-]+@[a-z-0-9.-]+$")
	private String email;

	private List<MultipleStaffDto> multipleStaffDtos;

	private String gender;
	private String salary;
	private String area;
	private String dateOfBirth;
	private String bloodGroup;
	private String fatherName;
	private String spouseName;
	private String bankDetail;
	private String accountNumber;
	private String ifscCode;
	private String bankName;
	private String branchName;
	private String panNumber;
	private String aadharNumber;
	private String dateOfAnniversary;
	private String location;

	private Set<Role> roles = new HashSet<>();
	private Branch branch;
	private String password;
	private String passwordDecrypted;
	private int zoneId;
	private int aseId;
	private int asmId;
	private int rsmId;
	private int nsmId;
	private int stateZoneId;
	private Long roleId;
	private String emailLoginStatus;
	private List<State_Zone> statezones;
	private List<Zone> zones;

	public StaffLocation getStafflocation() {
		return stafflocation;
	}

	public void setStafflocation(StaffLocation stafflocation) {
		this.stafflocation = stafflocation;
	}

	private LocalDate createddate;
	private LocalTime createdtime;

	private long salesexpwithincity;
	private long salesexpoutsidecity;
	private long salesexpdaytrip;
	
	
	
    private StaffLocation stafflocation;

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	public String getEmailLoginStatus() {
		return emailLoginStatus;
	}

	public void setEmailLoginStatus(String emailLoginStatus) {
		this.emailLoginStatus = emailLoginStatus;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public int getAseId() {
		return aseId;
	}

	public void setAseId(int aseId) {
		this.aseId = aseId;
	}

	public int getAsmId() {
		return asmId;
	}

	public void setAsmId(int asmId) {
		this.asmId = asmId;
	}

	public int getRsmId() {
		return rsmId;
	}

	public void setRsmId(int rsmId) {
		this.rsmId = rsmId;
	}

	public int getNsmId() {
		return nsmId;
	}

	public void setNsmId(int nsmId) {
		this.nsmId = nsmId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getBankDetail() {
		return bankDetail;
	}

	public void setBankDetail(String bankDetail) {
		this.bankDetail = bankDetail;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getDateOfAnniversary() {
		return dateOfAnniversary;
	}

	public void setDateOfAnniversary(String dateOfAnniversary) {
		this.dateOfAnniversary = dateOfAnniversary;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordDecrypted()
	{
		return passwordDecrypted;
	}
	
	public void setPasswordDecrypted(String passwordDecrypted) {
		this.passwordDecrypted = passwordDecrypted;
	}

	public int getStateZoneId() {
		return stateZoneId;
	}

	public void setStateZoneId(int stateZoneId) {
		this.stateZoneId = stateZoneId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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

	public List<Zone> getZones() {
		return zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

	public List<State_Zone> getStatezones() {
		return statezones;
	}

	public void setStatezones(List<State_Zone> statezones) {
		this.statezones = statezones;
	}

	public List<MultipleStaffDto> getMultipleStaffDtos() {
		return multipleStaffDtos;
	}

	public void setMultipleStaffDtos(List<MultipleStaffDto> multipleStaffDtos) {
		this.multipleStaffDtos = multipleStaffDtos;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public long getSalesexpwithincity() {
		return salesexpwithincity;
	}

	public void setSalesexpwithincity(long salesexpwithincity) {
		this.salesexpwithincity = salesexpwithincity;
	}

	public long getSalesexpoutsidecity() {
		return salesexpoutsidecity;
	}

	public void setSalesexpoutsidecity(long salesexpoutsidecity) {
		this.salesexpoutsidecity = salesexpoutsidecity;
	}

	public long getSalesexpdaytrip() {
		return salesexpdaytrip;
	}

	public void setSalesexpdaytrip(long salesexpdaytrip) {
		this.salesexpdaytrip = salesexpdaytrip;
	}

}
