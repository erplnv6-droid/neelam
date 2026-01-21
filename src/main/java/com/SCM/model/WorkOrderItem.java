package com.SCM.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class WorkOrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int wItemId;
	private int productId;
	private float mrp;
	private float qty;
	private float total;
	private float gstvalue;
	private float amount;
	private float discount;
	private String text;
	private String igst;
	private float cgstLedger;
	private float sgstLedger;
	private float igstLedger;
	private String unitofmeasurement;
	private int measurement;
	private float dlp;
	private float discount1;
	private float grossamount;
	private String productName;
	private int estimatedDays;
	public String productType;
	private String standardQtyPerBox;
	private String uomSecondary;

	private float staffCancelQty;
	private float retailerCancelQty;
	private float disributorCancelQty;
	
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JsonIgnore
//	private WorkOrder workorder;


	public float getStaffCancelQty() {
		return staffCancelQty;
	}

	public void setStaffCancelQty(float staffCancelQty) {
		this.staffCancelQty = staffCancelQty;
	}

	public float getRetailerCancelQty() {
		return retailerCancelQty;
	}

	public void setRetailerCancelQty(float retailerCancelQty) {
		this.retailerCancelQty = retailerCancelQty;
	}

	public float getDisributorCancelQty() {
		return disributorCancelQty;
	}

	public void setDisributorCancelQty(float disributorCancelQty) {
		this.disributorCancelQty = disributorCancelQty;
	}

	public WorkOrderItem() {
	}

	public int getEstimatedDays() {
		return estimatedDays;
	}

	public void setEstimatedDays(int estimatedDays) {
		this.estimatedDays = estimatedDays;
	}

	public int getMeasurement() {
		return measurement;
	}

	public void setMeasurement(int measurement) {
		this.measurement = measurement;
	}

	public int getwItemId() {
		return wItemId;
	}

	public void setwItemId(int wItemId) {
		this.wItemId = wItemId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public float getMrp() {
		return mrp;
	}

	public void setMrp(float mrp) {
		this.mrp = mrp;
	}

	public float getQty() {
		return qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getGstvalue() {
		return gstvalue;
	}

	public void setGstvalue(float gstvalue) {
		this.gstvalue = gstvalue;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getIgstLedger() {
		return igstLedger;
	}

	public void setIgstLedger(float igstLedger) {
		this.igstLedger = igstLedger;
	}

	public float getCgstLedger() {
		return cgstLedger;
	}

	public void setCgstLedger(float cgstLedger) {
		this.cgstLedger = cgstLedger;
	}

	public float getSgstLedger() {
		return sgstLedger;
	}

	public void setSgstLedger(float sgstLedger) {
		this.sgstLedger = sgstLedger;
	}

	public float getDlp() {
		return dlp;
	}

	public void setDlp(float dlp) {
		this.dlp = dlp;
	}

	public float getDiscount1() {
		return discount1;
	}

	public void setDiscount1(float discount1) {
		this.discount1 = discount1;
	}

	public String getStandardQtyPerBox() {
		return standardQtyPerBox;
	}

	public String getIgst() {
		return igst;
	}

	public void setIgst(String igst) {
		this.igst = igst;
	}

	public void setStandardQtyPerBox(String standardQtyPerBox) {
		this.standardQtyPerBox = standardQtyPerBox;
	}

	// public WorkOrder getWorkorder() {
//        return workorder;
//    }
//
//    public void setWorkorder(WorkOrder workorder) {
//        this.workorder = workorder;
//    }

	public float getGrossamount() {
		return grossamount;
	}

	public void setGrossamount(float grossamount) {
		this.grossamount = grossamount;
	}

	public String getUnitofmeasurement() {
		return unitofmeasurement;
	}

	public void setUnitofmeasurement(String unitofmeasurement) {
		this.unitofmeasurement = unitofmeasurement;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUomSecondary() {
		return uomSecondary;
	}

	public void setUomSecondary(String uomSecondary) {
		this.uomSecondary = uomSecondary;
	}

	
	@Override
	public String toString() {
		return "WorkOrderItem{" + "wItemId=" + wItemId + ", productId=" + productId + ", mrp='" + mrp + '\'' + ", qty='"
				+ qty + '\'' + ", total='" + total + '\'' + ", gstvalue='" + gstvalue + '\'' + ", amount='" + amount
				+ '\'' + ", discount='" + discount + '\'' + ", text='" + text + '\'' + ", igstLedger=" + igstLedger
				+ ", cgstLedger=" + cgstLedger + ", sgstLedger=" + sgstLedger + ", unitofmeasurement='"
				+ unitofmeasurement + '\'' + ", dlp='" + dlp + '\'' + ", discount1='" + discount1 + '\''
				+ ", grossamount='" + grossamount + '\'' + ", productType='" + productType + '\''
				+ ", standardQtyPerBox='" + standardQtyPerBox + '\'' +
//                ", workorder=" + workorder +
				'}';
	}

}
