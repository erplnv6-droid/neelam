package com.SCM.dto;

import com.SCM.model.Branch;
import com.SCM.model.Role;

import lombok.Data;


import java.time.LocalDate;
import java.time.LocalTime;

import java.util.HashSet;
import java.util.Set;

@Data
public class StaffDto {

	private Long id;
	private String staffName;
	private String doj;
	private String address;
	private String mobileNumber;
	private String email;
	private String gender;
	private String designation;
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
	private String password;
	private int aseId;
	private int asmId;
	private int rsmId;
	private int nsmId;
	private int zoneId;
	private int stateZoneId;
	private Long roleId;
	private Branch branch;

	private Set<Role> roles = new HashSet<>();


	

	private long createdby;
	private String createbyname;
	private String role;
	


	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;


}
