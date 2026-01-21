package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.SCM.model.Country;
import com.SCM.model.States;

import lombok.Data;

@Data
public class BranchDto {

	private int id;
	private String branchname;

	@NotNull
	@Pattern(regexp = "^\\d{10}$", message = "Phone number must be between 10 and 11 characters")
	@Column(unique = true)
	private String phoneno;
	
	@Column(unique = true)
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email is not valid", regexp = "^[a-z-0-9_!#$%&’*+/=?`{|}~^.-]+@[a-z-0-9.-]+$")
	private String email;
	
	private String gstno;
	private String address;
	private String zipcode;
	private String bankname;
	private long accountno;
	private String branch;
	private String ifsc;
	private Country country;
	private States states;
	private String cities;
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



}
