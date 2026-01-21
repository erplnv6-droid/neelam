package com.SCM.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.SCM.model.RetailerTemporaryAddress;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetailerTempDto {

	private int id;
	private String tradeName;
	private String billingAddress;
	private List<RetailerTemporaryAddress> deliveryAddress;

	private String state;
	private String statecode;
	private String city;
	private String country;
	private String panNumber;
	private String gstNumber;
	private String gstType;
	private String pinCode;
	
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
//	private int boxProductDiscount;
//	private int kgProductDiscount;
//	private int corporaetProductDiscount;
//	private int cookerProductDiscount;
//	private int noshProductDiscount;
//	private int schemeDiscount;
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
//	private int schemeboxProductDiscount;
//	private int schemekgProductDiscount;
//	private int schemecorporateProductDiscount;
//	private int schemecookerProductDiscount;
//	private int schemenoshProductDisocunt;
	private boolean activestatus;
//	private int aseid;
//	private int asmid;
//	private int rsmid;
//	private int nsmid;
	private int stateid;
	private int zonesid;
	private boolean authorized;
	private String password;
	private String aadharcard;
	private String pancard;
	private int roleId;
	private String referredBy;
	private Integer distributorId;
	private int TempretailerIds;
	private String Latitude;
	private String Longitude;

}
