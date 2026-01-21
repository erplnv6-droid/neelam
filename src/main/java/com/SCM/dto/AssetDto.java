package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.SCM.model.Supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetDto {
	
	private long id;
	private String assetsname;
	private long mrp;
	private String srno;
	private String remarks;
	private LocalDate dateofpurchase;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expiryDate;
	private String status;
	
	private String invoiceno;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate invoicedate;
	
	private String insuranceprovidername;
	private String insurancepolicyno;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate ipnstartdate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate ipnenddate;
	
	private String insurancefilename;
	private String insurancefilelocation;
	private Supplier supplier;
	
	
	private LocalDate createddate;
	private LocalTime createdtime;
	private long createdby;
	private String createdbyname;
	private String role;
	
	
	
	private long updatedby;
	private String updatedbyname;
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private String updatedrole;
	
}
