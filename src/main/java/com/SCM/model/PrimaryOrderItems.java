package com.SCM.model;





import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PrimaryOrderItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int poid;
	private String productName;
	private int productId;
	private float mrp;
	private float qty;
	private float total;
	private float gstvalue;
	private float NetAmount;
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
	private int quantity_placed;
	private float quantity_placed_kg;
	private Date estimatedDays;
	private String standardQtyPerBox;
	private String uomPrimary;
	private String uomSecondary;
	private String productType;

	
	public int getPoid() {
		return poid;
	}

	public void setPoid(int poid) {
		this.poid = poid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public float getNetAmount() {
		return NetAmount;
	}

	public void setNetAmount(float netAmount) {
		NetAmount = netAmount;
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

	public String getIgst() {
		return igst;
	}

	public void setIgst(String igst) {
		this.igst = igst;
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

	public float getIgstLedger() {
		return igstLedger;
	}

	public void setIgstLedger(float igstLedger) {
		this.igstLedger = igstLedger;
	}

	public String getUnitofmeasurement() {
		return unitofmeasurement;
	}

	public void setUnitofmeasurement(String unitofmeasurement) {
		this.unitofmeasurement = unitofmeasurement;
	}

	public int getMeasurement() {
		return measurement;
	}

	public void setMeasurement(int measurement) {
		this.measurement = measurement;
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

	public float getGrossamount() {
		return grossamount;
	}

	public void setGrossamount(float grossamount) {
		this.grossamount = grossamount;
	}

	public int getQuantity_placed() {
		return quantity_placed;
	}

	public void setQuantity_placed(int quantity_placed) {
		this.quantity_placed = quantity_placed;
	}

	public float getQuantity_placed_kg() {
		return quantity_placed_kg;
	}

	public void setQuantity_placed_kg(float quantity_placed_kg) {
		this.quantity_placed_kg = quantity_placed_kg;
	}

	public Date getEstimatedDays() {
		return estimatedDays;
	}

	public void setEstimatedDays(Date estimatedDays) {
		this.estimatedDays = estimatedDays;
	}

	public String getStandardQtyPerBox() {
		return standardQtyPerBox;
	}

	public void setStandardQtyPerBox(String standardQtyPerBox) {
		this.standardQtyPerBox = standardQtyPerBox;
	}

	public String getUomPrimary() {
		return uomPrimary;
	}

	public void setUomPrimary(String uomPrimary) {
		this.uomPrimary = uomPrimary;
	}

	public String getUomSecondary() {
		return uomSecondary;
	}

	public void setUomSecondary(String uomSecondary) {
		this.uomSecondary = uomSecondary;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

}
