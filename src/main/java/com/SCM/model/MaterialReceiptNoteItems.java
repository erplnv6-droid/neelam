package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialReceiptNoteItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int mrnitemid;
	private int rate;
	private int mrp;
	private int mrnquantity;
	private int mrnaltquantity;
	private String per;
	private int discount;
	private float total;
	private float gst;
	private float igst;
	private float cgst;
	private float sgst;
	private float amount;
	private float gstvalue;
	private float dlp;
	private String productName;
	private String productType;
	private String standardQtyPerBox;
	private float uomPrimary;
	private float uomSecondary;
	private float tradeDiscount;
	private String unitofmeasurement;
	private String calunitofmeasurement;
	private float mrnquantitykgs;
	private float grossamount;
	private String hsncode;


	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public int getMrnitemid() {
		return mrnitemid;
	}

	public void setMrnitemid(int mrnitemid) {
		this.mrnitemid = mrnitemid;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getMrp() {
		return mrp;
	}

	public void setMrp(int mrp) {
		this.mrp = mrp;
	}

	public int getMrnquantity() {
		return mrnquantity;
	}

	public void setMrnquantity(int mrnquantity) {
		this.mrnquantity = mrnquantity;
	}

	public int getMrnaltquantity() {
		return mrnaltquantity;
	}

	public void setMrnaltquantity(int mrnaltquantity) {
		this.mrnaltquantity = mrnaltquantity;
	}

	public String getPer() {
		return per;
	}

	public void setPer(String per) {
		this.per = per;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getGst() {
		return gst;
	}

	public void setGst(float gst) {
		this.gst = gst;
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

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getGstvalue() {
		return gstvalue;
	}

	public void setGstvalue(float gstvalue) {
		this.gstvalue = gstvalue;
	}

	public float getDlp() {
		return dlp;
	}

	public void setDlp(float dlp) {
		this.dlp = dlp;
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

	public String getStandardQtyPerBox() {
		return standardQtyPerBox;
	}

	public void setStandardQtyPerBox(String standardQtyPerBox) {
		this.standardQtyPerBox = standardQtyPerBox;
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

	public float getTradeDiscount() {
		return tradeDiscount;
	}

	public void setTradeDiscount(float tradeDiscount) {
		this.tradeDiscount = tradeDiscount;
	}

	public String getUnitofmeasurement() {
		return unitofmeasurement;
	}

	public void setUnitofmeasurement(String unitofmeasurement) {
		this.unitofmeasurement = unitofmeasurement;
	}

	public String getCalunitofmeasurement() {
		return calunitofmeasurement;
	}

	public void setCalunitofmeasurement(String calunitofmeasurement) {
		this.calunitofmeasurement = calunitofmeasurement;
	}

	public float getMrnquantitykgs() {
		return mrnquantitykgs;
	}

	public void setMrnquantitykgs(float mrnquantitykgs) {
		this.mrnquantitykgs = mrnquantitykgs;
	}

	public float getGrossamount() {
		return grossamount;
	}

	public void setGrossamount(float grossamount) {
		this.grossamount = grossamount;
	}

	public String getHsncode() {
		return hsncode;
	}

	public void setHsncode(String hsncode) {
		this.hsncode = hsncode;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
