package com.SCM.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Jobworkoutward {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String jobsheetno;
	private String jobsheetdate;
	private String jobtype;
	private String remarks;
	private float grandtotal;
	private LocalDate createddate;
	private LocalTime createdtime;
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long createdby;
	private String createbyname;
	private String role;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	
	private String supplyType;
	
	   private String subSupplyType;
		
	   private String docType;
		
		private String docNo;
		
		private String docDate;
		
		private String fromGstin;
		
		private String fromTrdName;
		
		private String fromAddr1;
		
		private String fromAddr2;
		
		private String fromPlace;
		
		private int fromPincode;
		
		private int actFromStateCode;
		
		private int fromStateCode;
		
		private String toGstin;
		
		private String toTrdName;
		
		private String toAddr1;
		
		private String toAddr2;
		
		private String toPlace;
		
		private int toPincode;
		
		private int actToStateCode;
		
		private int toStateCode;
		
		private int transactionType;
		
	  
		
		private String transporterName;
		
		private String transDocNo;
		
		private String transMode;
		
		private String transDistance;
		
		private String transDocDate;
		
		private String vehicleNo;
		
		private String vehicleType;
		
		private long ewayBillNo;
		
		private String ewayBillDate;
		
		private String validUpto;
		
		private String alert;
		
		private String eway_status;
	
	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;
	
	@ManyToOne
    @JoinColumn(name = "supplier_id")
	private Supplier supplier;
	
	@ManyToOne
	@JoinColumn(name="transporters_id")
	private Transporters transporters;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "jobsheetoutward_id")
	private List<Jobworkoutwarditems> jobworkoutwarditems;
}
