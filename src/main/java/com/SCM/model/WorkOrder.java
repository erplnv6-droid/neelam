package com.SCM.model;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity

public class WorkOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true)
	private String workOrderId;

	private java.sql.Date workOrderDate;
	private int retailerPinCode;
	private boolean orderStatus;
	private String remarks;
	private int saleAgentId;

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

//	added later for calculations and reports 20-09-23
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
	private String retailerAddress;
	private String status;
	
	private LocalDate createddate;
	private LocalTime createdtime;
	private long createdBy;
	private String createbyname;
	private String role;

	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	private float total;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "wid")
	private List<WorkOrderItem> workOderItems = new ArrayList<>();

	public LocalDate getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(LocalDate updateddate) {
		this.updateddate = updateddate;
	}

	public LocalTime getUpdatedtime() {
		return updatedtime;
	}

	public void setUpdatedtime(LocalTime updatedtime) {
		this.updatedtime = updatedtime;
	}

	public long getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(long updatedby) {
		this.updatedby = updatedby;
	}

	public String getUpdatedbyname() {
		return updatedbyname;
	}

	public void setUpdatedbyname(String updatedbyname) {
		this.updatedbyname = updatedbyname;
	}

	public String getUpdatedrole() {
		return updatedrole;
	}

	public void setUpdatedrole(String updatedrole) {
		this.updatedrole = updatedrole;
	}

	public String getCreatebyname() {
		return createbyname;
	}

	public void setCreatebyname(String createbyname) {
		this.createbyname = createbyname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public WorkOrder() {
	}

	public LocalDate getCreateddate() {
		return createddate;
	}

	public void setCreateddate(LocalDate createddate) {
		this.createddate = createddate;
	}

	public LocalTime getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(LocalTime createdtime) {
		this.createdtime = createdtime;
	}

	public float getKgProductTotalQtyKg() {
		return kgProductTotalQtyKg;
	}

	public void setKgProductTotalQtyKg(float kgProductTotalQtyKg) {
		this.kgProductTotalQtyKg = kgProductTotalQtyKg;
	}

	public float getKgProductTotalQtyPcs() {
		return kgProductTotalQtyPcs;
	}

	public void setKgProductTotalQtyPcs(float kgProductTotalQtyPcs) {
		this.kgProductTotalQtyPcs = kgProductTotalQtyPcs;
	}

	public float getBoxProductTotalQtyPcs() {
		return boxProductTotalQtyPcs;
	}

	public void setBoxProductTotalQtyPcs(float boxProductTotalQtyPcs) {
		this.boxProductTotalQtyPcs = boxProductTotalQtyPcs;
	}

	public float getCorporateProductTotalQtyPcs() {
		return corporateProductTotalQtyPcs;
	}

	public void setCorporateProductTotalQtyPcs(float corporateProductTotalQtyPcs) {
		this.corporateProductTotalQtyPcs = corporateProductTotalQtyPcs;
	}

	public float getCookerProductTotalQtyPcs() {
		return cookerProductTotalQtyPcs;
	}

	public void setCookerProductTotalQtyPcs(float cookerProductTotalQtyPcs) {
		this.cookerProductTotalQtyPcs = cookerProductTotalQtyPcs;
	}

	public float getNoshProductTotalQtyPcs() {
		return noshProductTotalQtyPcs;
	}

	public void setNoshProductTotalQtyPcs(float noshProductTotalQtyPcs) {
		this.noshProductTotalQtyPcs = noshProductTotalQtyPcs;
	}

	public float getTotalQtyKg() {
		return totalQtyKg;
	}

	public void setTotalQtyKg(float totalQtyKg) {
		this.totalQtyKg = totalQtyKg;
	}

	public float getTotalQtyPcs() {
		return totalQtyPcs;
	}

	public void setTotalQtyPcs(float totalQtyPcs) {
		this.totalQtyPcs = totalQtyPcs;
	}

	public float getKgProductTotalprice() {
		return kgProductTotalprice;
	}

	public void setKgProductTotalprice(float kgProductTotalprice) {
		this.kgProductTotalprice = kgProductTotalprice;
	}

	public float getBoxProductTotalprice() {
		return boxProductTotalprice;
	}

	public void setBoxProductTotalprice(float boxProductTotalprice) {
		this.boxProductTotalprice = boxProductTotalprice;
	}

	public float getCorporateProductTotalprice() {
		return corporateProductTotalprice;
	}

	public void setCorporateProductTotalprice(float corporateProductTotalprice) {
		this.corporateProductTotalprice = corporateProductTotalprice;
	}

	public float getCookerProductTotalprice() {
		return cookerProductTotalprice;
	}

	public void setCookerProductTotalprice(float cookerProductTotalprice) {
		this.cookerProductTotalprice = cookerProductTotalprice;
	}

	public float getNoshProductTotalprice() {
		return noshProductTotalprice;
	}

	public void setNoshProductTotalprice(float noshProductTotalprice) {
		this.noshProductTotalprice = noshProductTotalprice;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public float getGrossTotal() {
		return grossTotal;
	}

	public void setGrossTotal(float grossTotal) {
		this.grossTotal = grossTotal;
	}

	public float getNetValue() {
		return netValue;
	}

	public void setNetValue(float netValue) {
		this.netValue = netValue;
	}

	public float getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(float taxAmount) {
		this.taxAmount = taxAmount;
	}

	public int getPrimaryorderId() {
		return primaryorderId;
	}

	public void setPrimaryorderId(int primaryorderId) {
		this.primaryorderId = primaryorderId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<WorkOrderItem> getWorkOderItems() {
		return workOderItems;
	}

	public void setWorkOderItems(List<WorkOrderItem> workOderItems) {
		this.workOderItems = workOderItems;
	}

	public String getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}

	public java.sql.Date getWorkOrderDate() {
		return workOrderDate;
	}

	public void setWorkOrderDate(java.sql.Date workOrderDate) {
		this.workOrderDate = workOrderDate;
	}

	public boolean isOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(boolean orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getRetailerPinCode() {
		return retailerPinCode;
	}

	public boolean isConvertedToPo() {
		return convertedToPo;
	}

	public void setConvertedToPo(boolean convertedToPo) {
		this.convertedToPo = convertedToPo;
	}

	public void setRetailerPinCode(int retailerPinCode) {
		this.retailerPinCode = retailerPinCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getSaleAgentId() {
		return saleAgentId;
	}

	public void setSaleAgentId(int saleAgentId) {
		this.saleAgentId = saleAgentId;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDateAndTime() {
		return createdDateAndTime;
	}

	public void setCreatedDateAndTime(String createdDateAndTime) {
		this.createdDateAndTime = createdDateAndTime;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getDist_id() {
		return dist_id;
	}

	public void setDist_id(int dist_id) {
		this.dist_id = dist_id;
	}

	public int getRet_id() {
		return ret_id;
	}

	public void setRet_id(int ret_id) {
		this.ret_id = ret_id;
	}

	public int getAseid() {
		return aseid;
	}

	public void setAseid(int aseid) {
		this.aseid = aseid;
	}

	public int getAsmid() {
		return asmid;
	}

	public void setAsmid(int asmid) {
		this.asmid = asmid;
	}

	public int getRsmid() {
		return rsmid;
	}

	public void setRsmid(int rsmid) {
		this.rsmid = rsmid;
	}

	public int getNsmid() {
		return nsmid;
	}

	public void setNsmid(int nsmid) {
		this.nsmid = nsmid;
	}

	public int getStateid() {
		return stateid;
	}

	public void setStateid(int stateid) {
		this.stateid = stateid;
	}

	public int getZonesid() {
		return zonesid;
	}

	public void setZonesid(int zonesid) {
		this.zonesid = zonesid;
	}

	public String getRetailerAddress() {
		return retailerAddress;
	}

	public void setRetailerAddress(String retailerAddress) {
		this.retailerAddress = retailerAddress;
	}

	public long getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(long deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
	
	

}
