package com.SCM.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesOrderItems {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int salesorderitemId;
  
  private float soqty;
  
  private int altsoqty;
  
  private String per;
  
  private float grossamount;
  
  private float discount1;
  
  private int someasurement;
  
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
  
  private float soquantity_placed_kg;
  
  private float soquantity_placed;
  
  private float dlp;
  
  private String standardQtyPerBox;
  
  private float netAmount;
  
  private float gstvalue;
  
  private int dcitemspending;
  
  private int dcitemsplaced;
  
  private String dcstatus;
  
  private int soid;
  
  private int cancelpcsqty;
  
  private float cancelkgqty;
  
  private int addcancelpcsqty;
  
  private float addcancelkgqty;
  
  private int pendingcancelpcsqty;
  
  private float pendingcancelkgqty;
  
  private int dist_cancelpcsqty;
  
  private float dist_cancelkgqty;
  
  private int dist_addcancelpcsqty;
  
  private float dist_addcancelkgqty;
  
  private int dist_pendingcancelpcsqty;
  
  private float dist_pendingcancelkgqty;
  
  private String cancelstatus;
  
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
  
  @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
  @JoinColumn(name = "salesorderid")
  @JsonBackReference
  private SalesOrder salesorder;
  
  public int getSalesorderitemId() {
    return this.salesorderitemId;
  }
  
  public void setSalesorderitemId(int salesorderitemId) {
    this.salesorderitemId = salesorderitemId;
  }
  
  public float getSoqty() {
    return this.soqty;
  }
  
  public void setSoqty(float soqty) {
    this.soqty = soqty;
  }
  
  public int getAltsoqty() {
    return this.altsoqty;
  }
  
  public void setAltsoqty(int altsoqty) {
    this.altsoqty = altsoqty;
  }
  
  public String getPer() {
    return this.per;
  }
  
  public void setPer(String per) {
    this.per = per;
  }
  
  public float getGrossamount() {
    return this.grossamount;
  }
  
  public void setGrossamount(float grossamount) {
    this.grossamount = grossamount;
  }
  
  public float getDiscount1() {
    return this.discount1;
  }
  
  public void setDiscount1(float discount1) {
    this.discount1 = discount1;
  }
  
  public int getSomeasurement() {
    return this.someasurement;
  }
  
  public void setSomeasurement(int someasurement) {
    this.someasurement = someasurement;
  }
  
  public float getRate() {
    return this.rate;
  }
  
  public void setRate(float rate) {
    this.rate = rate;
  }
  
  public float getMrp() {
    return this.mrp;
  }
  
  public void setMrp(float mrp) {
    this.mrp = mrp;
  }
  
  public float getDiscount() {
    return this.discount;
  }
  
  public void setDiscount(float discount) {
    this.discount = discount;
  }
  
  public float getDiscountamount() {
    return this.discountamount;
  }
  
  public void setDiscountamount(float discountamount) {
    this.discountamount = discountamount;
  }
  
  public float getDiscountamount1() {
    return this.discountamount1;
  }
  
  public void setDiscountamount1(float discountamount1) {
    this.discountamount1 = discountamount1;
  }
  
  public float getTotal() {
    return this.total;
  }
  
  public void setTotal(float total) {
    this.total = total;
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
  
  public float getGst() {
    return this.gst;
  }
  
  public void setGst(float gst) {
    this.gst = gst;
  }
  
  public float getAmount() {
    return this.amount;
  }
  
  public void setAmount(float amount) {
    this.amount = amount;
  }
  
  public Product getProduct() {
    return this.product;
  }
  
  public void setProduct(Product product) {
    this.product = product;
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
  
  public float getTradediscount() {
    return this.tradediscount;
  }
  
  public void setTradediscount(float tradediscount) {
    this.tradediscount = tradediscount;
  }
  
  public float getSchemeDiscount() {
    return this.schemeDiscount;
  }
  
  public void setSchemeDiscount(float schemeDiscount) {
    this.schemeDiscount = schemeDiscount;
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
  
  public String getUnitofmeasurement() {
    return this.unitofmeasurement;
  }
  
  public void setUnitofmeasurement(String unitofmeasurement) {
    this.unitofmeasurement = unitofmeasurement;
  }
  
  public float getSoquantity_placed_kg() {
    return this.soquantity_placed_kg;
  }
  
  public void setSoquantity_placed_kg(float soquantity_placed_kg) {
    this.soquantity_placed_kg = soquantity_placed_kg;
  }
  
  public float getSoquantity_placed() {
    return this.soquantity_placed;
  }
  
  public void setSoquantity_placed(float soquantity_placed) {
    this.soquantity_placed = soquantity_placed;
  }
  
  public float getDlp() {
    return this.dlp;
  }
  
  public void setDlp(float dlp) {
    this.dlp = dlp;
  }
  
  public String getStandardQtyPerBox() {
    return this.standardQtyPerBox;
  }
  
  public void setStandardQtyPerBox(String standardQtyPerBox) {
    this.standardQtyPerBox = standardQtyPerBox;
  }
  
  public float getNetAmount() {
    return this.netAmount;
  }
  
  public void setNetAmount(float netAmount) {
    this.netAmount = netAmount;
  }
  
  public int getDcitemspending() {
    return this.dcitemspending;
  }
  
  public void setDcitemspending(int dcitemspending) {
    this.dcitemspending = dcitemspending;
  }
  
  public int getDcitemsplaced() {
    return this.dcitemsplaced;
  }
  
  public void setDcitemsplaced(int dcitemsplaced) {
    this.dcitemsplaced = dcitemsplaced;
  }
  
  public String getDcstatus() {
    return this.dcstatus;
  }
  
  public void setDcstatus(String dcstatus) {
    this.dcstatus = dcstatus;
  }
  
  public float getGstvalue() {
    return this.gstvalue;
  }
  
  public void setGstvalue(float gstvalue) {
    this.gstvalue = gstvalue;
  }
  
  public int getSoid() {
    return this.soid;
  }
  
  public void setSoid(int soid) {
    this.soid = soid;
  }
  
  public SalesOrder getSalesorder() {
    return this.salesorder;
  }
  
  public void setSalesorder(SalesOrder salesorder) {
    this.salesorder = salesorder;
  }
  
  public int getCancelpcsqty() {
    return this.cancelpcsqty;
  }
  
  public void setCancelpcsqty(int cancelpcsqty) {
    this.cancelpcsqty = cancelpcsqty;
  }
  
  public float getCancelkgqty() {
    return this.cancelkgqty;
  }
  
  public void setCancelkgqty(float cancelkgqty) {
    this.cancelkgqty = cancelkgqty;
  }
  
  public int getPendingcancelpcsqty() {
    return this.pendingcancelpcsqty;
  }
  
  public void setPendingcancelpcsqty(int pendingcancelpcsqty) {
    this.pendingcancelpcsqty = pendingcancelpcsqty;
  }
  
  public float getPendingcancelkgqty() {
    return this.pendingcancelkgqty;
  }
  
  public void setPendingcancelkgqty(float pendingcancelkgqty) {
    this.pendingcancelkgqty = pendingcancelkgqty;
  }
  
  public String getCancelstatus() {
    return this.cancelstatus;
  }
  
  public void setCancelstatus(String cancelstatus) {
    this.cancelstatus = cancelstatus;
  }
  
  public int getAddcancelpcsqty() {
    return this.addcancelpcsqty;
  }
  
  public void setAddcancelpcsqty(int addcancelpcsqty) {
    this.addcancelpcsqty = addcancelpcsqty;
  }
  
  public float getAddcancelkgqty() {
    return this.addcancelkgqty;
  }
  
  public void setAddcancelkgqty(float addcancelkgqty) {
    this.addcancelkgqty = addcancelkgqty;
  }
  
  public int getDist_cancelpcsqty() {
    return this.dist_cancelpcsqty;
  }
  
  public void setDist_cancelpcsqty(int dist_cancelpcsqty) {
    this.dist_cancelpcsqty = dist_cancelpcsqty;
  }
  
  public float getDist_cancelkgqty() {
    return this.dist_cancelkgqty;
  }
  
  public void setDist_cancelkgqty(float dist_cancelkgqty) {
    this.dist_cancelkgqty = dist_cancelkgqty;
  }
  
  public int getDist_addcancelpcsqty() {
    return this.dist_addcancelpcsqty;
  }
  
  public void setDist_addcancelpcsqty(int dist_addcancelpcsqty) {
    this.dist_addcancelpcsqty = dist_addcancelpcsqty;
  }
  
  public float getDist_addcancelkgqty() {
    return this.dist_addcancelkgqty;
  }
  
  public void setDist_addcancelkgqty(float dist_addcancelkgqty) {
    this.dist_addcancelkgqty = dist_addcancelkgqty;
  }
  
  public int getDist_pendingcancelpcsqty() {
    return this.dist_pendingcancelpcsqty;
  }
  
  public void setDist_pendingcancelpcsqty(int dist_pendingcancelpcsqty) {
    this.dist_pendingcancelpcsqty = dist_pendingcancelpcsqty;
  }
  
  public float getDist_pendingcancelkgqty() {
    return this.dist_pendingcancelkgqty;
  }
  
  public void setDist_pendingcancelkgqty(float dist_pendingcancelkgqty) {
    this.dist_pendingcancelkgqty = dist_pendingcancelkgqty;
  }
}
