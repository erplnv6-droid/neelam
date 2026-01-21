package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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

@Entity
public class DeliveryCharge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String type;
	private String status;
	private String taxtype;
	private String buyerorderno;
	private Date buyerorderdate;
	private String totalnopkg;
	private String termsofdelivery;
	private String deliverynotno;
	private java.sql.Date dcdate;
	private String dispatchedthrough;
	private String destination;
	private String irnno;
	private String ackno;
	private Date ackdate;
	private String msmeno;
	private String remarks;
	private float grossAmount;
	private float igst;
	private float cgst;
	private float sgst;
	private float roundofvalue;
	private String paymentTerms;
	private float tcs;
	private String udyamno;
	private String gstno;
	private float taxvalue;
	private float grandtotal;
	private int salesorderId;
	private String salesorderids;
	private LocalDate createddate;
	private LocalTime createdtime;
	private long deliveryAddress;
	private String retailerstatus;
	private String igstoutput;
	private String branchname;
	private String bankname;
	private String ifsccode;
	private float accountno;
	private long createdby;
	private String createbyname;
	private String role;
	private String voucherid;
	private long vouchernumber;
	//@Column(unique = false)
//	private String voucherseries;

	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	@ManyToOne
	@JoinColumn(name = "branchId")
	private Branch branch;

	@ManyToOne
	@JoinColumn(name = "distributorId")
	private Distributor distributor;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne
	@JoinColumn(name = "customersubcontacts_id")
	private CustomerSubContacts customerSubContacts;

	@ManyToOne
	@JoinColumn(name = "retailer_id")
	private Retailer retailer;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "dc_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DeliveryChargeItems> dcItems = new ArrayList<>();
	
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
	@ManyToOne
	@JoinColumn(name = "vouchermaster_id")
private VoucherMaster voucherMaster;

	
	public long getCreatedby() {
		return createdby;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBuyerorderno() {
		return buyerorderno;
	}

	public void setBuyerorderno(String buyerorderno) {
		this.buyerorderno = buyerorderno;
	}

	public Date getBuyerorderdate() {
		return buyerorderdate;
	}

	public void setBuyerorderdate(Date buyerorderdate) {
		this.buyerorderdate = buyerorderdate;
	}

	public String getTotalnopkg() {
		return totalnopkg;
	}

	public void setTotalnopkg(String totalnopkg) {
		this.totalnopkg = totalnopkg;
	}

	public String getTermsofdelivery() {
		return termsofdelivery;
	}

	public void setTermsofdelivery(String termsofdelivery) {
		this.termsofdelivery = termsofdelivery;
	}

	public String getDeliverynotno() {
		return deliverynotno;
	}

	public void setDeliverynotno(String deliverynotno) {
		this.deliverynotno = deliverynotno;
	}

	public java.sql.Date getDcdate() {
		return dcdate;
	}

	public void setDcdate(java.sql.Date dcdate) {
		this.dcdate = dcdate;
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

	public String getIrnno() {
		return irnno;
	}

	public void setIrnno(String irnno) {
		this.irnno = irnno;
	}

	public String getAckno() {
		return ackno;
	}

	public void setAckno(String ackno) {
		this.ackno = ackno;
	}

	public Date getAckdate() {
		return ackdate;
	}

	public void setAckdate(Date ackdate) {
		this.ackdate = ackdate;
	}

	public String getMsmeno() {
		return msmeno;
	}

	public void setMsmeno(String msmeno) {
		this.msmeno = msmeno;
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

	public float getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(float grossAmount) {
		this.grossAmount = grossAmount;
	}

	public float getRoundofvalue() {
		return roundofvalue;
	}

	public void setRoundofvalue(float roundofvalue) {
		this.roundofvalue = roundofvalue;
	}

	public float getTcs() {
		return tcs;
	}

	public void setTcs(float tcs) {
		this.tcs = tcs;
	}

	public float getTaxvalue() {
		return taxvalue;
	}

	public void setTaxvalue(float taxvalue) {
		this.taxvalue = taxvalue;
	}

	public float getGrandtotal() {
		return grandtotal;
	}

	public void setGrandtotal(float grandtotal) {
		this.grandtotal = grandtotal;
	}

	public int getSalesorderId() {
		return salesorderId;
	}

	public void setSalesorderId(int salesorderId) {
		this.salesorderId = salesorderId;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CustomerSubContacts getCustomerSubContacts() {
		return customerSubContacts;
	}

	public void setCustomerSubContacts(CustomerSubContacts customerSubContacts) {
		this.customerSubContacts = customerSubContacts;
	}

	public String getUdyamno() {
		return udyamno;
	}

	public void setUdyamno(String udyamno) {
		this.udyamno = udyamno;
	}

	public String getGstno() {
		return gstno;
	}

	public void setGstno(String gstno) {
		this.gstno = gstno;
	}

	public List<DeliveryChargeItems> getDcItems() {
		return dcItems;
	}

	public void setDcItems(List<DeliveryChargeItems> dcItems) {
		this.dcItems = dcItems;
	}

	public String getSalesorderids() {
		return salesorderids;
	}

	public void setSalesorderids(String salesorderids) {
		this.salesorderids = salesorderids;
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

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(long deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
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

	public String getIgstoutput() {
		return igstoutput;
	}

	public void setIgstoutput(String igstoutput) {
		this.igstoutput = igstoutput;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getIfsccode() {
		return ifsccode;
	}

	public void setIfsccode(String ifsccode) {
		this.ifsccode = ifsccode;
	}

	public float getAccountno() {
		return accountno;
	}

	public void setAccountno(float accountno) {
		this.accountno = accountno;
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

//	public String getVoucherseries() {
//		return voucherseries;
//	}
//
//	public void setVoucherseries(String voucherseries) {
//		this.voucherseries = voucherseries;
//	}
	
	

}
