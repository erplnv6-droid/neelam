package com.SCM.dto;

import com.SCM.model.Retailer;
import com.SCM.model.WorkOrderItem;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
public class WorkOrderDto {

	private int id;
	private String workOrderId;
	private java.sql.Date workOrderDate;
	private int retailerPinCode;
	private boolean orderStatus;
	private String remarks;
	private int saleAgentId;
	private String createdBy;
	private Date dateCreated;
	private String createdDateAndTime;
	private String paymentTerms;
	private List<WorkOrderItem> workOderItems;
	private Retailer retailer;
	private int ret_id;
	private long deliveryAddress;
	private int dist_id;
	private float grossTotal; // total inclusive of taxes
	private float netValue; // taxable value: gross - tax amount
	private float taxAmount;// gross - net
	private float kgProductTotalQtyKg; // kg products' total quantity in KG
	private float kgProductTotalQtyPcs; // kg products' total quantity in PCS
	private float boxProductTotalQtyPcs; // box products' total quantity in PCS
	private float corporateProductTotalQtyPcs; // corporate products' total quantity in PCS
	private float cookerProductTotalQtyPcs; // cooker products' total quantity in PCS
	private float noshProductTotalQtyPcs; // nosh products' total quantity in PCS
	private float totalQtyKg; // All kg products' total quantity
	private float totalQtyPcs; // All pcs products' total quantity
	private float kgProductTotalprice;
	private float boxProductTotalprice;
	private float corporateProductTotalprice;
	private float cookerProductTotalprice;
	private float noshProductTotalprice;
	private int aseid;
	private int asmid;
	private int rsmid;
	private int nsmid;
	private int stateid;
	private int zonesid;
	private boolean convertedToPo;
	private int primaryorderId;
	private String retailerAddress;
	private LocalDate createddate;
	private LocalTime createdtime;

	private long createduserid;
	private String createbyname;
	private String role;
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	private float total;

}
