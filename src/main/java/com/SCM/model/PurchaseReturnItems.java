package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseReturnItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int puritemid;
	private int rate;
	private int mrp;
	private int purquantity;
	private int puraltquantity;
	private String per;
//	private int discount;
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
	private float purquantitykgs;
	private float grossamount;
	private String hsncode;
	
	private int SlNo;
    private String PrdDesc;
    private String IsServc;

    private double Qty;
    private int FreeQty;
    private String Unit;
   
    private double TotAmt;
    private double Discount;
    private double AssAmt;
	private double GstRt;
	private double IgstAmt;
	private double CgstAmt;
	private double SgstAmt;
	private double TotItemVal;
	
//	private int SlNo;
//    private String PrdDesc;
//    private String IsServc;
//
//    private double Qty;
//    private int FreeQty;
//    private String Unit;
//   
//    private double TotAmt;
//    private double Discount;
//    private double AssAmt;
//	private double GstRt;
//	private double IgstAmt;
//	private double CgstAmt;
//	private double SgstAmt;
////	private double CesRt;
////	private double CesAmt;
////	private double CesNonAdvlAmt;
////	private double StateCesRt;
////	private double StateCesAmt;
////	private double StateCesNonAdvlAmt;
////	private double OthChrg;
//	private double TotItemVal;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	public int getPuritemid() {
		return puritemid;
	}

	public void setPuritemid(int puritemid) {
		this.puritemid = puritemid;
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

	public int getPurquantity() {
		return purquantity;
	}

	public void setPurquantity(int purquantity) {
		this.purquantity = purquantity;
	}

	public int getPuraltquantity() {
		return puraltquantity;
	}

	public void setPuraltquantity(int puraltquantity) {
		this.puraltquantity = puraltquantity;
	}

	public String getPer() {
		return per;
	}

	public void setPer(String per) {
		this.per = per;
	}

//	public int getDiscount() {
//		return discount;
//	}
//
//	public void setDiscount(int discount) {
//		this.discount = discount;
//	}

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

	public float getPurquantitykgs() {
		return purquantitykgs;
	}

	public void setPurquantitykgs(float purquantitykgs) {
		this.purquantitykgs = purquantitykgs;
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

//	public int getSlNo() {
//		return SlNo;
//	}
//
//	public void setSlNo(int slNo) {
//		SlNo = slNo;
//	}
//
//	public String getPrdDesc() {
//		return PrdDesc;
//	}
//
//	public void setPrdDesc(String prdDesc) {
//		PrdDesc = prdDesc;
//	}
//
//	public String getIsServc() {
//		return IsServc;
//	}
//
//	public void setIsServc(String isServc) {
//		IsServc = isServc;
//	}
//
//	public double getQty() {
//		return Qty;
//	}
//
//	public void setQty(double qty) {
//		Qty = qty;
//	}
//
//	public int getFreeQty() {
//		return FreeQty;
//	}
//
//	public void setFreeQty(int freeQty) {
//		FreeQty = freeQty;
//	}
//
//	public String getUnit() {
//		return Unit;
//	}
//
//	public void setUnit(String unit) {
//		Unit = unit;
//	}
//
//	public double getTotAmt() {
//		return TotAmt;
//	}
//
//	public void setTotAmt(double totAmt) {
//		TotAmt = totAmt;
//	}
//
//	public double getDiscount() {
//		return Discount;
//	}
//
//	public void setDiscount(double discount) {
//		Discount = discount;
//	}
//
//	public double getAssAmt() {
//		return AssAmt;
//	}
//
//	public void setAssAmt(double assAmt) {
//		AssAmt = assAmt;
//	}
//
//	public double getGstRt() {
//		return GstRt;
//	}
//
//	public void setGstRt(double gstRt) {
//		GstRt = gstRt;
//	}
//
//	public double getIgstAmt() {
//		return IgstAmt;
//	}
//
//	public void setIgstAmt(double igstAmt) {
//		IgstAmt = igstAmt;
//	}
//
//	public double getCgstAmt() {
//		return CgstAmt;
//	}
//
//	public void setCgstAmt(double cgstAmt) {
//		CgstAmt = cgstAmt;
//	}
//
//	public double getSgstAmt() {
//		return SgstAmt;
//	}
//
//	public void setSgstAmt(double sgstAmt) {
//		SgstAmt = sgstAmt;
//	}
//
//	public double getTotItemVal() {
//		return TotItemVal;
//	}
//
//	public void setTotItemVal(double totItemVal) {
//		TotItemVal = totItemVal;
//	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getSlNo() {
		return SlNo;
	}

	public void setSlNo(int slNo) {
		SlNo = slNo;
	}

	public String getPrdDesc() {
		return PrdDesc;
	}

	public void setPrdDesc(String prdDesc) {
		PrdDesc = prdDesc;
	}

	public String getIsServc() {
		return IsServc;
	}

	public void setIsServc(String isServc) {
		IsServc = isServc;
	}

	public double getQty() {
		return Qty;
	}

	public void setQty(double qty) {
		Qty = qty;
	}

	public int getFreeQty() {
		return FreeQty;
	}

	public void setFreeQty(int freeQty) {
		FreeQty = freeQty;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public double getTotAmt() {
		return TotAmt;
	}

	public void setTotAmt(double totAmt) {
		TotAmt = totAmt;
	}

	public double getDiscount() {
		return Discount;
	}

	public void setDiscount(double discount) {
		Discount = discount;
	}

	public double getAssAmt() {
		return AssAmt;
	}

	public void setAssAmt(double assAmt) {
		AssAmt = assAmt;
	}

	public double getGstRt() {
		return GstRt;
	}

	public void setGstRt(double gstRt) {
		GstRt = gstRt;
	}

	public double getIgstAmt() {
		return IgstAmt;
	}

	public void setIgstAmt(double igstAmt) {
		IgstAmt = igstAmt;
	}

	public double getCgstAmt() {
		return CgstAmt;
	}

	public void setCgstAmt(double cgstAmt) {
		CgstAmt = cgstAmt;
	}

	public double getSgstAmt() {
		return SgstAmt;
	}

	public void setSgstAmt(double sgstAmt) {
		SgstAmt = sgstAmt;
	}

	public double getTotItemVal() {
		return TotItemVal;
	}

	public void setTotItemVal(double totItemVal) {
		TotItemVal = totItemVal;
	}

	
}
