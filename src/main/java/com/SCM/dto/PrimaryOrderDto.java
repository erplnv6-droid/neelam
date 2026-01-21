package com.SCM.dto;

import com.SCM.model.Distributor;
import com.SCM.model.PrimaryOrderItems;
import com.SCM.model.WorkOrderItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import javax.persistence.Column;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrimaryOrderDto {

	private int id;
	@Column(unique = true)
	private String workOrderId;
	private Date workOrderDate;
	private String status;
	private int retailerPinCode;
	private boolean orderStatus;
	private String remarks;
	private int saleAgentId;
	private Date from_date;
	private Date to_Date;
	private LocalDate dateCreated;
	private int aseid;
	private int asmid;
	private int rsmid;
	private int nsmid;
	private int stateid;
	private int zonesid;
	private Distributor distributor;
	private long deliveryAddress;
	private List<WorkOrderItem> items;
	int sid;
	private List<Object> secondaryIds;
	private float grossTotal;
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
	private List<PrimaryOrderItems> primaryOrderItems = new ArrayList<>();
	private int salesorderId;
	private String distributorAddress;

	private LocalDate createddate;
	private LocalTime createdtime;

	private long createdby;
	private String createbyname;
	private String role;
	
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	private Date edd;


}
