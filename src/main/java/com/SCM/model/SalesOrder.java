package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.ToString;

@Entity
@ToString
public class SalesOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String buyerorder;
	private LocalDate buyerorderdate;
	private String voucherno;
	private String dispatchedthrough;
	private String destination;
	private String termsofdelivery;
	private float igst;
	private float cgst;
	private float sgst;
	private float grossamount;
	private float grandtotal;
	private String shippingcharge;
	private String paymentTerms;
	private float roundingofvalue;
	private java.sql.Date sodate;
	private boolean quotationstatus;
	private String taxtype;
	private String remarks;
	private int primaryorderId;
	private int workorderId;
	private int dcid;
	private String status;
	private long deliveryAddress;

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

	private String retailerstatus;

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	@ManyToOne
	@JoinColumn(name = "customersubcontacts_id")
	private CustomerSubContacts customerSubContacts;

	@ManyToOne
	@JoinColumn(name = "branchId")
	private Branch branch;

	@ManyToOne
	@JoinColumn(name = "distributorId")
	private Distributor distributor;

	@ManyToOne
	@JoinColumn(name = "retailer_id")
	private Retailer retailer;

	@OneToMany(mappedBy = "salesorder", cascade = CascadeType.ALL)
	@JsonManagedReference
private List<SalesOrderItems> salesOrderItems;

	private String voucherid;
	private long vouchernumber;
	@Column(unique = true)
	private String voucherseries;
	
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
	@ManyToOne
	@JoinColumn(name = "vouchermaster_id")
private VoucherMaster voucherMaster;
	

	public SalesOrder() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBuyerorder() {
		return buyerorder;
	}

	public void setBuyerorder(String buyerorder) {
		this.buyerorder = buyerorder;
	}

	public String getVoucherno() {
		return voucherno;
	}

	public void setVoucherno(String voucherno) {
		this.voucherno = voucherno;
	}

	public String getDispatchedthrough() {
		return dispatchedthrough;
	}

	public void setDispatchedthrough(String dispatchedthrough) {
		this.dispatchedthrough = dispatchedthrough;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getTermsofdelivery() {
		return termsofdelivery;
	}

	public void setTermsofdelivery(String termsofdelivery) {
		this.termsofdelivery = termsofdelivery;
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

	public float getGrossamount() {
		return grossamount;
	}

	public void setGrossamount(float grossamount) {
		this.grossamount = grossamount;
	}

	public float getGrandtotal() {
		return grandtotal;
	}

	public void setGrandtotal(float grandtotal) {
		this.grandtotal = grandtotal;
	}

	public String getShippingcharge() {
		return shippingcharge;
	}

	public void setShippingcharge(String shippingcharge) {
		this.shippingcharge = shippingcharge;
	}

	public float getRoundingofvalue() {
		return roundingofvalue;
	}

	public void setRoundingofvalue(float roundingofvalue) {
		this.roundingofvalue = roundingofvalue;
	}

	public boolean isQuotationstatus() {
		return quotationstatus;
	}

	public void setQuotationstatus(boolean quotationstatus) {
		this.quotationstatus = quotationstatus;
	}

	public String getTaxtype() {
		return taxtype;
	}

	public void setTaxtype(String taxtype) {
		this.taxtype = taxtype;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getPrimaryorderId() {
		return primaryorderId;
	}

	public void setPrimaryorderId(int primaryorderId) {
		this.primaryorderId = primaryorderId;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public CustomerSubContacts getCustomerSubContacts() {
		return customerSubContacts;
	}

	public void setCustomerSubContacts(CustomerSubContacts customerSubContacts) {
		this.customerSubContacts = customerSubContacts;
	}

	public List<SalesOrderItems> getSalesOrderItems() {
		return salesOrderItems;
	}

	public int getDcid() {
		return dcid;
	}

	public void setDcid(int dcid) {
		this.dcid = dcid;
	}

	public void setSalesOrderItems(List<SalesOrderItems> salesOrderItems) {

		this.salesOrderItems = salesOrderItems;

		for (SalesOrderItems soi : salesOrderItems) {
			soi.setSalesorder(this);
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public long getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(long deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public java.sql.Date getSodate() {
		return sodate;
	}

	public void setSodate(java.sql.Date sodate) {
		this.sodate = sodate;
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

	public int getWorkorderId() {
		return workorderId;
	}

	public void setWorkorderId(int workorderId) {
		this.workorderId = workorderId;
	}

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	public String getRetailerstatus() {
		return retailerstatus;
	}

	public void setRetailerstatus(String retailerstatus) {
		this.retailerstatus = retailerstatus;
	}

	public long getCreatedby() {
		return createdby;
	}

	public void setCreatedby(long createdby) {
		this.createdby = createdby;
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

	public String getVoucherid() {
		return voucherid;
	}

	public void setVoucherid(String voucherid) {
		this.voucherid = voucherid;
	}

	public long getVouchernumber() {
		return vouchernumber;
	}

	public void setVouchernumber(long vouchernumber) {
		this.vouchernumber = vouchernumber;
	}

	public String getVoucherseries() {
		return voucherseries;
	}

	public void setVoucherseries(String voucherseries) {
		this.voucherseries = voucherseries;
	}

	public LocalDate getBuyerorderdate() {
		return buyerorderdate;
	}

	public void setBuyerorderdate(LocalDate buyerorderdate) {
		this.buyerorderdate = buyerorderdate;
	}

	public int getStartnumberwithprefilno() {
		return startnumberwithprefilno;
	}

	public void setStartnumberwithprefilno(int startnumberwithprefilno) {
		this.startnumberwithprefilno = startnumberwithprefilno;
	}

	public String getStartnumberwithprefilyes() {
		return startnumberwithprefilyes;
	}

	public void setStartnumberwithprefilyes(String startnumberwithprefilyes) {
		this.startnumberwithprefilyes = startnumberwithprefilyes;
	}

	public String getVouchermasterSeries() {
		return vouchermasterSeries;
	}

	public void setVouchermasterSeries(String vouchermasterSeries) {
		this.vouchermasterSeries = vouchermasterSeries;
	}

	public String getVoucherstatus() {
		return voucherstatus;
	}

	public void setVoucherstatus(String voucherstatus) {
		this.voucherstatus = voucherstatus;
	}

	public VoucherMaster getVoucherMaster() {
		return voucherMaster;
	}

	public void setVoucherMaster(VoucherMaster voucherMaster) {
		this.voucherMaster = voucherMaster;
	}

}
