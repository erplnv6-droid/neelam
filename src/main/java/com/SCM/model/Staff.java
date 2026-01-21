package com.SCM.model;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 60)
	private String staffName;
	private String doj;
	private String address;
	private String mobileNumber;

	@Column(unique = true)
	private String email;
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
	private String password;
	private String passwordDecrypted;
	private int aseId;
	private int asmId;
	private int rsmId;
	private int nsmId;
	private int stateZoneId;
	private String emailotp;
	private Instant time;
	private String emailLoginStatus;
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

	private long salesexpwithincity;
	private long salesexpoutsidecity;
	private long salesexpdaytrip;

	@ManyToOne
	@JoinColumn(name = "branchId")
	private Branch branch;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "staff_roles", joinColumns = @JoinColumn(name = "staff_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "staff_zone", joinColumns = @JoinColumn(name = "staff_id"), inverseJoinColumns = @JoinColumn(name = "zone_id"))
	private List<Zone> zones;

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "staff_state", joinColumns = @JoinColumn(name = "staff_id"), inverseJoinColumns = @JoinColumn(name = "statezone_id"))
	private List<State_Zone> statezones;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "staff_id")
	private List<MultipleStaff> multipleStaffe;

	
	
    @OneToOne(mappedBy = "staff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private StaffLocation stafflocation;
    
    

    
	public StaffLocation getStafflocation() {
		return stafflocation;
	}
	
//	public void setStafflocation(StaffLocation stafflocation) {
//	    this.stafflocation = stafflocation;
//	}

	public void setStafflocation(StaffLocation stafflocation) {
	    this.stafflocation = stafflocation;
	    if (stafflocation != null) {
	        stafflocation.setStaff(this); // owning side ko bhi set kar diya
	    }
	}

	public Staff() {
	}

	public Staff(Long id, String staffName, String doj, String address, String mobileNumber, String email,
			String gender, String salary, String area, String dateOfBirth, String bloodGroup, String fatherName,
			String spouseName, String bankDetail, String accountNumber, String ifscCode, String bankName,
			String branchName, String panNumber, String aadharNumber, String dateOfAnniversary, String location,
			String password, String passwordDecrypted, int aseId, int asmId, int rsmId, int nsmId, int stateZoneId,
			String emailotp, Instant time, String emailLoginStatus, LocalDate createddate, LocalTime createdtime,
			long createdby, String createbyname, String role, LocalDate updateddate, LocalTime updatedtime,
			long updatedby, String updatedbyname, String updatedrole, long salesexpwithincity, long salesexpoutsidecity,
			long salesexpdaytrip, Branch branch, Set<Role> roles, List<Zone> zones, List<State_Zone> statezones,
			List<MultipleStaff> multipleStaffe,StaffLocation staffLocation) {
		super();
		this.id = id;
		this.stafflocation=staffLocation;
		this.staffName = staffName;
		this.doj = doj;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.gender = gender;
		this.salary = salary;
		this.area = area;
		this.dateOfBirth = dateOfBirth;
		this.bloodGroup = bloodGroup;
		this.fatherName = fatherName;
		this.spouseName = spouseName;
		this.bankDetail = bankDetail;
		this.accountNumber = accountNumber;
		this.ifscCode = ifscCode;
		this.bankName = bankName;
		this.branchName = branchName;
		this.panNumber = panNumber;
		this.aadharNumber = aadharNumber;
		this.dateOfAnniversary = dateOfAnniversary;
		this.location = location;
		this.password = password;
		this.passwordDecrypted = passwordDecrypted;
		this.aseId = aseId;
		this.asmId = asmId;
		this.rsmId = rsmId;
		this.nsmId = nsmId;
		this.stateZoneId = stateZoneId;
		this.emailotp = emailotp;
		this.time = time;
		this.emailLoginStatus = emailLoginStatus;
		this.createddate = createddate;
		this.createdtime = createdtime;
		this.createdby = createdby;
		this.createbyname = createbyname;
		this.role = role;
		this.updateddate = updateddate;
		this.updatedtime = updatedtime;
		this.updatedby = updatedby;
		this.updatedbyname = updatedbyname;
		this.updatedrole = updatedrole;
		this.salesexpwithincity = salesexpwithincity;
		this.salesexpoutsidecity = salesexpoutsidecity;
		this.salesexpdaytrip = salesexpdaytrip;
		this.branch = branch;
		this.roles = roles;
		this.zones = zones;
		this.statezones = statezones;
		this.multipleStaffe = multipleStaffe;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public int getStateZoneId() {
		return stateZoneId;
	}

	public void setStateZoneId(int stateZoneId) {
		this.stateZoneId = stateZoneId;
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

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	public List<MultipleStaff> getMultipleStaffe() {
		return multipleStaffe;
	}

	public void setMultipleStaffe(List<MultipleStaff> multipleStaffe) {
		this.multipleStaffe = multipleStaffe;
	}

	@Override
	public String toString() {
		return "Staff [id=" + id + ", staffName=" + staffName + ", doj=" + doj + ", address=" + address
				+ ", mobileNumber=" + mobileNumber + ", email=" + email + ", gender=" + gender + ", salary=" + salary
				+ ", area=" + area + ", dateOfBirth=" + dateOfBirth + ", bloodGroup=" + bloodGroup + ", fatherName="
				+ fatherName + ", spouseName=" + spouseName + ", bankDetail=" + bankDetail + ", accountNumber="
				+ accountNumber + ", ifscCode=" + ifscCode + ", bankName=" + bankName + ", branchName=" + branchName
				+ ", panNumber=" + panNumber + ", aadharNumber=" + aadharNumber + ", dateOfAnniversary="
				+ dateOfAnniversary + ", location=" + location + ", password=" + password + ", passwordDecrypted="
				+ passwordDecrypted + ", aseId=" + aseId + ", asmId=" + asmId + ", rsmId=" + rsmId + ", nsmId=" + nsmId
				+ ", stateZoneId=" + stateZoneId + ", emailotp=" + emailotp + ", time=" + time + ", emailLoginStatus="
				+ emailLoginStatus + ", createddate=" + createddate + ", createdtime=" + createdtime + ", createdby="
				+ createdby + ", createbyname=" + createbyname + ", role=" + role + ", updateddate=" + updateddate
				+ ", updatedtime=" + updatedtime + ", updatedby=" + updatedby + ", updatedbyname=" + updatedbyname
				+ ", updatedrole=" + updatedrole + ", salesexpwithincity=" + salesexpwithincity
				+ ", salesexpoutsidecity=" + salesexpoutsidecity + ", salesexpdaytrip=" + salesexpdaytrip + ", branch="
				+ branch + ", roles=" + roles + ", zones=" + zones + ", statezones=" + statezones + ", multipleStaffe="
				+ multipleStaffe + "]";
	}
	
	

//	public Staff(String staffName, String doj, String address, String mobileNumber, String email, String gender,
//			String salary, String area, String dateOfBirth, String bloodGroup, String fatherName, String spouseName,
//			String bankDetail, String accountNumber, String ifscCode, String bankName, String branchName,
//			String panNumber, String aadharNumber, String location, String dateOfAnniversary, String password,
//			int aseId, int asmId, int rsmId, int nsmId, int stateZoneId, Branch branch) {
//
//		this.staffName = staffName;
//		this.doj = doj;
//		this.address = address;
//		this.mobileNumber = mobileNumber;
//		this.email = email;
//		this.gender = gender;
//		this.location = location;
//		this.salary = salary;
//		this.area = area;
//		this.dateOfBirth = dateOfBirth;
//		this.bloodGroup = bloodGroup;
//		this.fatherName = fatherName;
//		this.spouseName = spouseName;
//		this.bankDetail = bankDetail;
//		this.accountNumber = accountNumber;
//		this.ifscCode = ifscCode;
//		this.bankName = bankName;
//		this.branchName = branchName;
//		this.panNumber = panNumber;
//		this.aadharNumber = aadharNumber;
//		this.dateOfAnniversary = dateOfAnniversary;
//		this.password = password;
//		this.aseId = aseId;
//		this.asmId = asmId;
//		this.rsmId = rsmId;
//		this.nsmId = nsmId;
//		this.stateZoneId = stateZoneId;
//		this.branch = branch;
//	}
//
//	public LocalDate getUpdateddate() {
//		return updateddate;
//	}
//
//	public void setUpdateddate(LocalDate updateddate) {
//		this.updateddate = updateddate;
//	}
//
//	public LocalTime getUpdatedtime() {
//		return updatedtime;
//	}
//
//	public void setUpdatedtime(LocalTime updatedtime) {
//		this.updatedtime = updatedtime;
//	}
//
//	public long getUpdatedby() {
//		return updatedby;
//	}
//
//	public void setUpdatedby(long updatedby) {
//		this.updatedby = updatedby;
//	}
//
//	public String getUpdatedbyname() {
//		return updatedbyname;
//	}
//
//	public void setUpdatedbyname(String updatedbyname) {
//		this.updatedbyname = updatedbyname;
//	}
//
//	public String getUpdatedrole() {
//		return updatedrole;
//	}
//
//	public void setUpdatedrole(String updatedrole) {
//		this.updatedrole = updatedrole;
//	}
//
//	public long getCreatedby() {
//		return createdby;
//	}
//
//	public void setCreatedby(long createdby) {
//		this.createdby = createdby;
//	}
//
//	public String getCreatebyname() {
//		return createbyname;
//	}
//
//	public void setCreatebyname(String createbyname) {
//		this.createbyname = createbyname;
//	}
//
//	public String getRole() {
//		return role;
//	}
//
//	public void setRole(String role) {
//		this.role = role;
//	}
//
//	public String getEmailLoginStatus() {
//		return emailLoginStatus;
//	}
//
//	public void setEmailLoginStatus(String emailLoginStatus) {
//		this.emailLoginStatus = emailLoginStatus;
//	}
//
//	public Instant getTime() {
//		return time;
//	}
//
//	public void setTime(Instant time) {
//		this.time = time;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public Set<Role> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(Set<Role> roles) {
//		this.roles = roles;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getStaffName() {
//		return staffName;
//	}
//
//	public void setStaffName(String staffName) {
//		this.staffName = staffName;
//	}
//
//	public String getDoj() {
//		return doj;
//	}
//
//	public void setDoj(String doj) {
//		this.doj = doj;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//	public String getMobileNumber() {
//		return mobileNumber;
//	}
//
//	public void setMobileNumber(String mobileNumber) {
//		this.mobileNumber = mobileNumber;
//	}
//
//	public String getGender() {
//		return gender;
//	}
//
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//
//	public String getSalary() {
//		return salary;
//	}
//
//	public void setSalary(String salary) {
//		this.salary = salary;
//	}
//
//	public String getArea() {
//		return area;
//	}
//
//	public void setArea(String area) {
//		this.area = area;
//	}
//
//	public String getDateOfBirth() {
//		return dateOfBirth;
//	}
//
//	public void setDateOfBirth(String dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}
//
//	public String getBloodGroup() {
//		return bloodGroup;
//	}
//
//	public void setBloodGroup(String bloodGroup) {
//		this.bloodGroup = bloodGroup;
//	}
//
//	public String getFatherName() {
//		return fatherName;
//	}
//
//	public void setFatherName(String fatherName) {
//		this.fatherName = fatherName;
//	}
//
//	public String getSpouseName() {
//		return spouseName;
//	}
//
//	public void setSpouseName(String spouseName) {
//		this.spouseName = spouseName;
//	}
//
//	public String getBankDetail() {
//		return bankDetail;
//	}
//
//	public void setBankDetail(String bankDetail) {
//		this.bankDetail = bankDetail;
//	}
//
//	public String getAccountNumber() {
//		return accountNumber;
//	}
//
//	public void setAccountNumber(String accountNumber) {
//		this.accountNumber = accountNumber;
//	}
//
//	public String getIfscCode() {
//		return ifscCode;
//	}
//
//	public void setIfscCode(String ifscCode) {
//		this.ifscCode = ifscCode;
//	}
//
//	public String getBankName() {
//		return bankName;
//	}
//
//	public void setBankName(String bankName) {
//		this.bankName = bankName;
//	}
//
//	public String getBranchName() {
//		return branchName;
//	}
//
//	public void setBranchName(String branchName) {
//		this.branchName = branchName;
//	}
//
//	public String getPanNumber() {
//		return panNumber;
//	}
//
//	public void setPanNumber(String panNumber) {
//		this.panNumber = panNumber;
//	}
//
//	public String getAadharNumber() {
//		return aadharNumber;
//	}
//
//	public void setAadharNumber(String aadharNumber) {
//		this.aadharNumber = aadharNumber;
//	}
//
//	public String getDateOfAnniversary() {
//		return dateOfAnniversary;
//	}
//
//	public void setDateOfAnniversary(String dateOfAnniversary) {
//		this.dateOfAnniversary = dateOfAnniversary;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public int getAseId() {
//		return aseId;
//	}
//
//	public void setAseId(int aseId) {
//		this.aseId = aseId;
//	}
//
//	public int getAsmId() {
//		return asmId;
//	}
//
//	public void setAsmId(int asmId) {
//		this.asmId = asmId;
//	}
//
//	public int getRsmId() {
//		return rsmId;
//	}
//
//	public void setRsmId(int rsmId) {
//		this.rsmId = rsmId;
//	}
//
//	public int getNsmId() {
//		return nsmId;
//	}
//
//	public void setNsmId(int nsmId) {
//		this.nsmId = nsmId;
//	}
//
//	public int getStateZoneId() {
//		return stateZoneId;
//	}
//
//	public void setStateZoneId(int stateZoneId) {
//		this.stateZoneId = stateZoneId;
//	}
//
//	public String getLocation() {
//		return location;
//	}
//
//	public void setLocation(String location) {
//		this.location = location;
//	}
//
//	public Branch getBranch() {
//		return branch;
//	}
//
//	public void setBranch(Branch branch) {
//		this.branch = branch;
//	}
//
//	public String getEmailotp() {
//		return emailotp;
//	}
//
//	public void setEmailotp(String emailotp) {
//		this.emailotp = emailotp;
//	}
//
//	public LocalDate getCreateddate() {
//		return createddate;
//	}
//
//	public void setCreateddate(LocalDate createddate) {
//		this.createddate = createddate;
//	}
//
//	public LocalTime getCreatedtime() {
//		return createdtime;
//	}
//
//	public void setCreatedtime(LocalTime createdtime) {
//		this.createdtime = createdtime;
//	}
//
//	public List<Zone> getZones() {
//		return zones;
//	}
//
//	public void setZones(List<Zone> zones) {
//		this.zones = zones;
//	}
//
//	public List<State_Zone> getStatezones() {
//		return statezones;
//	}
//
//	public void setStatezones(List<State_Zone> statezones) {
//		this.statezones = statezones;
//	}
//
//	public List<MultipleStaff> getMultipleStaffe() {
//		return multipleStaffe;
//	}
//
//	public void setMultipleStaffe(List<MultipleStaff> multipleStaffe) {
//		this.multipleStaffe = multipleStaffe;
//	}
//
//	public long getSalesexpwithincity() {
//		return salesexpwithincity;
//	}
//
//	public void setSalesexpwithincity(long salesexpwithincity) {
//		this.salesexpwithincity = salesexpwithincity;
//	}
//
//	public long getSalesexpoutsidecity() {
//		return salesexpoutsidecity;
//	}
//
//	public void setSalesexpoutsidecity(long salesexpoutsidecity) {
//		this.salesexpoutsidecity = salesexpoutsidecity;
//	}
//
//	public long getSalesexpdaytrip() {
//		return salesexpdaytrip;
//	}
//
//	public void setSalesexpdaytrip(long salesexpdaytrip) {
//		this.salesexpdaytrip = salesexpdaytrip;
//	}
//
//	@Override
//	public String toString() {
//		return "Staff{" + "staffName='" + staffName + '\'' + ", doj='" + doj + '\'' + ", address='" + address + '\''
//				+ ", mobileNumber='" + mobileNumber + '\'' + ", email='" + email + '\'' + ", gender='" + gender + '\''
//				+ ",  , salary='" + salary + '\'' + ", area='" + area + '\'' + ", dateOfBirth='" + dateOfBirth + '\''
//				+ ", bloodGroup='" + bloodGroup + '\'' + ", fatherName='" + fatherName + '\'' + ", spouseName='"
//				+ spouseName + '\'' + ", bankDetail='" + bankDetail + '\'' + ", accountNumber='" + accountNumber + '\''
//				+ ", ifscCode='" + ifscCode + '\'' + ", bankName='" + bankName + '\'' + ", branchName='" + branchName
//				+ '\'' + ", panNumber='" + panNumber + '\'' + ", aadharNumber='" + aadharNumber + '\''
//				+ ", dateOfAnniversary='" + dateOfAnniversary + '\'' + ", password='" + password + '\'' + '}';
//	}
	
	
	
	
	
	
}
