package com.SCM.dto;

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

import com.SCM.model.DistributorAddress;
import com.SCM.model.Groupn1;
import com.SCM.model.Groupn2;
import com.SCM.model.Groupn3;
import com.SCM.model.Groupn4;
import com.SCM.model.Groupn5;
import com.SCM.model.Retailer;
import com.SCM.model.Role;

import lombok.Data;


@Data
public class DistributorDto {
	
	private int id;
	private String tradeName;
	private String billingAddress;
	private List<DistributorAddress> deliveryAddress;
	private String state;
	private String paymentTerms;
	private String statecode;
	private String city;
	private String country;
	private String panNumber;
	private String gstNumber;
	private String gstType;
	private String pinCode;
	private List<DistributorToStaffDto> distributorToStaffs;
	private List<Retailer> retailer;
	
	@NotNull
	@Pattern(regexp = "^\\d{10}$", message = "Phone number must be between 10 and 11 characters")
	@Column(unique = true)
	private String perMobileNumber;
	
	private String alterMobileNumber;
	
	@Column(unique = true)
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email is not valid", regexp = "^[a-z-0-9_!#$%&’*+/=?`{|}~^.-]+@[a-z-0-9.-]+$")	
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
	private String aadharcard;
	private String pancard;
	private String password;
	private Set<Role> roles = new HashSet<>();
	private String state_name;
	private String colourtype;
	private String emailLoginStatus;

	private Groupn1 groupn1;
  	private Groupn2 groupn2;
 	private Groupn3 groupn3;
 	private Groupn4 groupn4;
 	private Groupn5 groupn5;
	
	private long createdby;
	private String createbyname;
	private String role;
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	
	private Double Latitude;
	private Double Longitude;

	public DistributorDto() {}



}
