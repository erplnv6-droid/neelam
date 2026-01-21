package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.ToString;

@ToString
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryChargeItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deliverychallanitemId;
	private int dcquantity;
	private int dcaltqty;
	private String per;
	private float grossamount;
	private float discount1;
	private int dcmeasurement;
	private float rate;
	private float mrp;
	private float discount;
	private float discountamount;
	private float discountamount1;
	private float total;
	private float igst;
	private float cgst;
	private float sgst;
	private float gst;
	private float amount;
	private float uomPrimary;
	private float uomSecondary;
	private float tradediscount;
	private float schemeDiscount;
	private String productName;
	private String productType;
	private String unitofmeasurement;
	private float dcquantity_placed_kg;
	private String calunitofmeasurement;
	private float dcquantity_placed;
	private float dlp;
	private String standardQtyPerBox;
	private float netAmount;
	private float gstvalue;
	private int salesOrderItemsId;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public int getDeliverychallanitemId() {
		return deliverychallanitemId;
	}

	public void setDeliverychallanitemId(int deliverychallanitemId) {
		this.deliverychallanitemId = deliverychallanitemId;
	}

	public int getDcquantity() {
		return dcquantity;
	}

	public void setDcquantity(int dcquantity) {
		this.dcquantity = dcquantity;
	}

	public int getDcaltqty() {
		return dcaltqty;
	}

	public void setDcaltqty(int dcaltqty) {
		this.dcaltqty = dcaltqty;
	}

	public String getPer() {
		return per;
	}

	public void setPer(String per) {
		this.per = per;
	}

	public float getGrossamount() {
		return grossamount;
	}

	public void setGrossamount(float grossamount) {
		this.grossamount = grossamount;
	}

	public float getDiscount1() {
		return discount1;
	}

	public void setDiscount1(float discount1) {
		this.discount1 = discount1;
	}

	public int getDcmeasurement() {
		return dcmeasurement;
	}

	public void setDcmeasurement(int dcmeasurement) {
		this.dcmeasurement = dcmeasurement;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getMrp() {
		return mrp;
	}

	public void setMrp(float mrp) {
		this.mrp = mrp;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getDiscountamount() {
		return discountamount;
	}

	public void setDiscountamount(float discountamount) {
		this.discountamount = discountamount;
	}

	public float getDiscountamount1() {
		return discountamount1;
	}

	public void setDiscountamount1(float discountamount1) {
		this.discountamount1 = discountamount1;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getIgst() {
		return igst;
	}

	public void setIgst(float igst) {
		this.igst = igst;
	}

	public float getCgst() {
		return cgst;
	}

	public void setCgst(float cgst) {
		this.cgst = cgst;
	}

	public float getSgst() {
		return sgst;
	}

	public void setSgst(float sgst) {
		this.sgst = sgst;
	}

	public float getGst() {
		return gst;
	}

	public void setGst(float gst) {
		this.gst = gst;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getUomPrimary() {
		return uomPrimary;
	}

	public void setUomPrimary(float uomPrimary) {
		this.uomPrimary = uomPrimary;
	}

	public float getUomSecondary() {
		return uomSecondary;
	}

	public void setUomSecondary(float uomSecondary) {
		this.uomSecondary = uomSecondary;
	}

	public float getTradediscount() {
		return tradediscount;
	}

	public void setTradediscount(float tradediscount) {
		this.tradediscount = tradediscount;
	}

	public float getSchemeDiscount() {
		return schemeDiscount;
	}

	public void setSchemeDiscount(float schemeDiscount) {
		this.schemeDiscount = schemeDiscount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getUnitofmeasurement() {
		return unitofmeasurement;
	}

	public void setUnitofmeasurement(String unitofmeasurement) {
		this.unitofmeasurement = unitofmeasurement;
	}

	public float getDcquantity_placed_kg() {
		return dcquantity_placed_kg;
	}

	public void setDcquantity_placed_kg(float dcquantity_placed_kg) {
		this.dcquantity_placed_kg = dcquantity_placed_kg;
	}

	public float getDcquantity_placed() {
		return dcquantity_placed;
	}

	public void setDcquantity_placed(float dcquantity_placed) {
		this.dcquantity_placed = dcquantity_placed;
	}

	public float getDlp() {
		return dlp;
	}

	public void setDlp(float dlp) {
		this.dlp = dlp;
	}

	public String getStandardQtyPerBox() {
		return standardQtyPerBox;
	}

	public void setStandardQtyPerBox(String standardQtyPerBox) {
		this.standardQtyPerBox = standardQtyPerBox;
	}

	public float getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(float netAmount) {
		this.netAmount = netAmount;
	}

	public float getGstvalue() {
		return gstvalue;
	}

	public void setGstvalue(float gstvalue) {
		this.gstvalue = gstvalue;
	}

	public int getSalesOrderItemsId() {
		return salesOrderItemsId;
	}

	public void setSalesOrderItemsId(int salesOrderItemsId) {
		this.salesOrderItemsId = salesOrderItemsId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getCalunitofmeasurement() {
		return calunitofmeasurement;
	}

	public void setCalunitofmeasurement(String calunitofmeasurement) {
		this.calunitofmeasurement = calunitofmeasurement;
	}

}
