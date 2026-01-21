package com.SCM.dto;

import java.sql.Date;

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

import com.SCM.model.Country;
import com.SCM.model.Role;
import com.SCM.model.State_Zone;
import com.SCM.model.States;
import com.SCM.model.SupplierAddress;
import com.SCM.model.SupplierSubContacts;
import com.SCM.model.Zone;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierDto {

	private int id;
	private String companyname;
	
	@NotNull
	@Pattern(regexp = "^\\d{10}$", message = "Phone number must be between 10 and 11 characters")
	@Column(unique = true)
	private String phonenumber;
	
	private String suppliername;
	private String gstno;
	
	@Column(unique = true)
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email is not valid", regexp = "^[a-z-0-9_!#$%&’*+/=?`{|}~^.-]+@[a-z-0-9.-]+$")	
	private String email;
	
	private String password;
	private String address;
	private String zipcode;
	private String statecode;
	private long accountno;
	private String bankname;
	private String branch;
	private String ifsc;
	private String openingbal;
	private String openingbaltype;
	private Date openingbaldate;
	private String cities;
	private State_Zone state_Zone;
	private Zone zone;
	private Country country;
	private States states;
	private List<SupplierSubContacts> supplierSubContacts;
	private Set<Role> roles = new HashSet<>();
	private String colourtype;
	private String emailLoginStatus;
	private String termsofpayment;

	
	private long createdby;
	private String createbyname;
	private String role;
	
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	

	private List<SupplierAddress> supplierAddresses;

}
