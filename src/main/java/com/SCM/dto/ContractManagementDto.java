package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.SCM.model.Supplier;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractManagementDto {

	private int id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fromdate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate todate;
	private String remarks;
	private float taxableamount;
	private float taxamount;
	private float totalamount;	
	private String docname;
	private String doclocation;
	private String contracttype;
	private Supplier supplier; 
	private String cmStringImage;
	private long createdby;
	private String createbyname;
	private String role;
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
}
