package com.SCM.dto;

import java.util.List;

import com.SCM.model.Jobworkoutwarditems;
import com.SCM.model.Supplier;
import com.SCM.model.Transporters;
import com.SCM.model.Warehouse;

import lombok.Data;

@Data
public class JobworkOutwardDto {

	private int id;
	private String jobsheetno;
//	private Date jobsheetdate;
	private String jobsheetdate;
	private String jobtype;
	private String remarks;
	private float grandtotal;
	private Warehouse warehouse;
	private Supplier supplier;
	private Transporters transporters;
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
	private List<Jobworkoutwarditems> jobworkoutwarditems;
}
