package com.SCM.ExportDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import lombok.Data;

@Data
public class ExportWorkOrder1 {

	private int id;
	private String workOrderId;
	private java.sql.Date workOrderDate;
	private int retailerPinCode;
	private boolean orderStatus;
	private String remarks;
	private int saleAgentId;
	private String createdBy;
	private String createdDateAndTime;
	private Date dateCreated;
	private int ret_id;
	private long deliveryAddress;
	private int dist_id;
	private int aseid;
	private int asmid;
	private int rsmid;
	private int nsmid;
	private int stateid;
	private int zonesid;
	private String paymentTerms;
	private boolean convertedToPo;
	private int primaryorderId;
	private float grossTotal; 
	private float netValue; 
	private float taxAmount;
	private float kgProductTotalQtyKg;
	private float kgProductTotalQtyPcs; 
	private float boxProductTotalQtyPcs; 
	private float corporateProductTotalQtyPcs; 
	private float cookerProductTotalQtyPcs; 
	private float noshProductTotalQtyPcs; 
	private float totalQtyKg; 
	private float totalQtyPcs; 
	private float kgProductTotalprice;
	private float boxProductTotalprice;
	private float corporateProductTotalprice;
	private float cookerProductTotalprice;
	private float noshProductTotalprice;
	private String retailerAddress;
	private LocalDate createddate;
	private LocalTime createdtime;
	private String state_name;
	private String zone_name;
	private String ASEstaffname;
	private String ASMstaffname;
	private String RSMstaffname;
	private String NSMstaffname;
	private String retailername;
}
