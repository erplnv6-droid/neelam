package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.SCM.model.Customer;
import com.SCM.model.Distributor;
import com.SCM.model.Groupn1;
import com.SCM.model.Groupn2;
import com.SCM.model.Groupn3;
import com.SCM.model.Groupn4;
import com.SCM.model.Groupn5;
import com.SCM.model.RetailerAddress;
import com.SCM.model.Role;
import com.SCM.model.WorkOrder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetailerDto {

	private int id;
	private String tradeName;
	private String billingAddress;
	private List<RetailerAddress> deliveryAddress;
	private String state;
	private String statecode;
	private String city;
	private String country;
	private String panNumber;
	private String gstNumber;
	private String gstType;
	private String pinCode;
	private String paymentTerms;
//	private List<RetailerToStaff> retailerToStaff;
	private List<RetailerToStaffDto> retailerToStaffs;

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
	private long distrubatorid;
	private float schemeboxProductDiscount;
	private float schemekgProductDiscount;
	private float schemecorporateProductDiscount;
	private float schemecookerProductDiscount;
	private float schemenoshProductDisocunt;
	private int aseid;
	private int asmid;
	private int rsmid;
	private int nsmid;
	private int stateid;
	private int zonesid;
	private String aadharcard;
	private String pancard;
	private boolean activestatus;
	private boolean authorized;
	private String password;
	private String passwordDecrypted;
	private Set<Role> roles = new HashSet<>();
	private List<WorkOrder> workOrders = new ArrayList<>();
	private Distributor distributor;
	private int TempretailerIds;
	private int tempretid;
	private List<Object> retailerTempIds;
	private String referredBy;
	private String colourtype;
	private String emailLoginStatus;
	private String retailerstatus;
	private Customer customer;

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

	private int staffid;

}
