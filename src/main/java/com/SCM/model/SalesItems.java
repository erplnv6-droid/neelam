package com.SCM.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SalesItems {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int salesitemid;
  
  private int salesquantity;
  
  private int salesaltquantity;
  
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
  
  private float schemeDiscount;
  
  private float netAmount;
  
  private String unitofmeasurement;
  
  private String calunitofmeasurement;
  
  private float salesquantitykgs;
  
  private float grossamount;
  
  private String hsncode;
  
  private String cancelsalestatus;
  
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
  
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
  
  public SalesItems(int salesitemid, int salesquantity, int salesaltquantity, float total, float gst, float igst, float cgst, float sgst, float amount, float gstvalue, float dlp, String productName, String productType, String standardQtyPerBox, float uomPrimary, float uomSecondary, float tradeDiscount, float schemeDiscount, float netAmount, String unitofmeasurement, String calunitofmeasurement, float salesquantitykgs, float grossamount, String hsncode, int slNo, String prdDesc, String isServc, double qty, int freeQty, String unit, double totAmt, double discount, double assAmt, double gstRt, double igstAmt, double cgstAmt, double sgstAmt, double cesRt, double cesAmt, double cesNonAdvlAmt, double stateCesRt, double stateCesAmt, double stateCesNonAdvlAmt, double othChrg, double totItemVal, Product product) {
    this.salesitemid = salesitemid;
    this.salesquantity = salesquantity;
    this.salesaltquantity = salesaltquantity;
    this.total = total;
    this.gst = gst;
    this.igst = igst;
    this.cgst = cgst;
    this.sgst = sgst;
    this.amount = amount;
    this.gstvalue = gstvalue;
    this.dlp = dlp;
    this.productName = productName;
    this.productType = productType;
    this.standardQtyPerBox = standardQtyPerBox;
    this.uomPrimary = uomPrimary;
    this.uomSecondary = uomSecondary;
    this.tradeDiscount = tradeDiscount;
    this.schemeDiscount = schemeDiscount;
    this.netAmount = netAmount;
    this.unitofmeasurement = unitofmeasurement;
    this.calunitofmeasurement = calunitofmeasurement;
    this.salesquantitykgs = salesquantitykgs;
    this.grossamount = grossamount;
    this.hsncode = hsncode;
    this.SlNo = slNo;
    this.PrdDesc = prdDesc;
    this.IsServc = isServc;
    this.Qty = qty;
    this.FreeQty = freeQty;
    this.Unit = unit;
    this.TotAmt = totAmt;
    this.Discount = discount;
    this.AssAmt = assAmt;
    this.GstRt = gstRt;
    this.IgstAmt = igstAmt;
    this.CgstAmt = cgstAmt;
    this.SgstAmt = sgstAmt;
    this.TotItemVal = totItemVal;
    this.product = product;
  }
  
  public SalesItems() {}
  
  public int getSalesitemid() {
    return this.salesitemid;
  }
  
  public void setSalesitemid(int salesitemid) {
    this.salesitemid = salesitemid;
  }
  
  public int getSalesquantity() {
    return this.salesquantity;
  }
  
  public void setSalesquantity(int salesquantity) {
    this.salesquantity = salesquantity;
  }
  
  public int getSalesaltquantity() {
    return this.salesaltquantity;
  }
  
  public void setSalesaltquantity(int salesaltquantity) {
    this.salesaltquantity = salesaltquantity;
  }
  
  public float getTotal() {
    return this.total;
  }
  
  public void setTotal(float total) {
    this.total = total;
  }
  
  public float getGst() {
    return this.gst;
  }
  
  public void setGst(float gst) {
    this.gst = gst;
  }
  
  public float getIgst() {
    return this.igst;
  }
  
  public void setIgst(float igst) {
    this.igst = igst;
  }
  
  public float getCgst() {
    return this.cgst;
  }
  
  public void setCgst(float cgst) {
    this.cgst = cgst;
  }
  
  public float getSgst() {
    return this.sgst;
  }
  
  public void setSgst(float sgst) {
    this.sgst = sgst;
  }
  
  public float getAmount() {
    return this.amount;
  }
  
  public void setAmount(float amount) {
    this.amount = amount;
  }
  
  public float getGstvalue() {
    return this.gstvalue;
  }
  
  public void setGstvalue(float gstvalue) {
    this.gstvalue = gstvalue;
  }
  
  public float getDlp() {
    return this.dlp;
  }
  
  public void setDlp(float dlp) {
    this.dlp = dlp;
  }
  
  public String getProductName() {
    return this.productName;
  }
  
  public void setProductName(String productName) {
    this.productName = productName;
  }
  
  public String getProductType() {
    return this.productType;
  }
  
  public void setProductType(String productType) {
    this.productType = productType;
  }
  
  public String getStandardQtyPerBox() {
    return this.standardQtyPerBox;
  }
  
  public void setStandardQtyPerBox(String standardQtyPerBox) {
    this.standardQtyPerBox = standardQtyPerBox;
  }
  
  public float getUomPrimary() {
    return this.uomPrimary;
  }
  
  public void setUomPrimary(float uomPrimary) {
    this.uomPrimary = uomPrimary;
  }
  
  public float getUomSecondary() {
    return this.uomSecondary;
  }
  
  public void setUomSecondary(float uomSecondary) {
    this.uomSecondary = uomSecondary;
  }
  
  public float getTradeDiscount() {
    return this.tradeDiscount;
  }
  
  public void setTradeDiscount(float tradeDiscount) {
    this.tradeDiscount = tradeDiscount;
  }
  
  public float getSchemeDiscount() {
    return this.schemeDiscount;
  }
  
  public void setSchemeDiscount(float schemeDiscount) {
    this.schemeDiscount = schemeDiscount;
  }
  
  public float getNetAmount() {
    return this.netAmount;
  }
  
  public void setNetAmount(float netAmount) {
    this.netAmount = netAmount;
  }
  
  public String getUnitofmeasurement() {
    return this.unitofmeasurement;
  }
  
  public void setUnitofmeasurement(String unitofmeasurement) {
    this.unitofmeasurement = unitofmeasurement;
  }
  
  public String getCalunitofmeasurement() {
    return this.calunitofmeasurement;
  }
  
  public void setCalunitofmeasurement(String calunitofmeasurement) {
    this.calunitofmeasurement = calunitofmeasurement;
  }
  
  public float getSalesquantitykgs() {
    return this.salesquantitykgs;
  }
  
  public void setSalesquantitykgs(float salesquantitykgs) {
    this.salesquantitykgs = salesquantitykgs;
  }
  
  public float getGrossamount() {
    return this.grossamount;
  }
  
  public void setGrossamount(float grossamount) {
    this.grossamount = grossamount;
  }
  
  public String getHsncode() {
    return this.hsncode;
  }
  
  public void setHsncode(String hsncode) {
    this.hsncode = hsncode;
  }
  
  public int getSlNo() {
    return this.SlNo;
  }
  
  public void setSlNo(int slNo) {
    this.SlNo = slNo;
  }
  
  public String getPrdDesc() {
    return this.PrdDesc;
  }
  
  public void setPrdDesc(String prdDesc) {
    this.PrdDesc = prdDesc;
  }
  
  public String getIsServc() {
    return this.IsServc;
  }
  
  public void setIsServc(String isServc) {
    this.IsServc = isServc;
  }
  
  public double getQty() {
    return this.Qty;
  }
  
  public void setQty(double qty) {
    this.Qty = qty;
  }
  
  public int getFreeQty() {
    return this.FreeQty;
  }
  
  public void setFreeQty(int freeQty) {
    this.FreeQty = freeQty;
  }
  
  public String getUnit() {
    return this.Unit;
  }
  
  public void setUnit(String unit) {
    this.Unit = unit;
  }
  
  public double getTotAmt() {
    return this.TotAmt;
  }
  
  public void setTotAmt(double totAmt) {
    this.TotAmt = totAmt;
  }
  
  public double getDiscount() {
    return this.Discount;
  }
  
  public void setDiscount(double discount) {
    this.Discount = discount;
  }
  
  public double getAssAmt() {
    return this.AssAmt;
  }
  
  public void setAssAmt(double assAmt) {
    this.AssAmt = assAmt;
  }
  
  public double getGstRt() {
    return this.GstRt;
  }
  
  public void setGstRt(double gstRt) {
    this.GstRt = gstRt;
  }
  
  public double getIgstAmt() {
    return this.IgstAmt;
  }
  
  public void setIgstAmt(double igstAmt) {
    this.IgstAmt = igstAmt;
  }
  
  public double getCgstAmt() {
    return this.CgstAmt;
  }
  
  public void setCgstAmt(double cgstAmt) {
    this.CgstAmt = cgstAmt;
  }
  
  public double getSgstAmt() {
    return this.SgstAmt;
  }
  
  public void setSgstAmt(double sgstAmt) {
    this.SgstAmt = sgstAmt;
  }
  
  public double getTotItemVal() {
    return this.TotItemVal;
  }
  
  public void setTotItemVal(double totItemVal) {
    this.TotItemVal = totItemVal;
  }
  
  public Product getProduct() {
    return this.product;
  }
  
  public void setProduct(Product product) {
    this.product = product;
  }
  
  public String getCancelsalestatus() {
    return this.cancelsalestatus;
  }
  
  public void setCancelsalestatus(String cancelsalestatus) {
    this.cancelsalestatus = cancelsalestatus;
  }
}
