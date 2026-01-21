package com.SCM.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.SCM.model.Distributor;
import com.SCM.model.RetailerAddress;
import com.SCM.model.RetailerToStaff;
import com.SCM.model.Role;
import com.SCM.model.WorkOrder;

import lombok.Data;

@Data
public class RetailerWithRetailerStaffDto {

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
	private List<RetailerAddress> deliveryAddress;
	private Set<Role> roles = new HashSet<>();
	private List<WorkOrder> workOrders = new ArrayList<>();
	private Distributor distributor;
	private List<RetailerToStaff> retailerToStaff;	
	private long createdby;
	private String createbyname;
	private String role;
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	private Float Latitude;
	private Float Longitude;
	
}
