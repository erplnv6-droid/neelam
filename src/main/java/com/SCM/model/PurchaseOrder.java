package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;
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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.ToString;

@Entity
@ToString
public class PurchaseOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String voucherno;
	private Date podate;
	private Date podueon;
	private String type;
	private String taxtype;
	private String status;
	private String paymentTerms;
	private String referenceno;
	private Date referencedate;
	private String otherrefernce;
	private String dispatchedthrough;
	private String destination;
	private String termsofdelivery;
	private String remarks;
	private String declaration;
	private float netAmount;
	private float igst;
	private float cgst;
	private float sgst;
	private float grandtotal;
	private String shippingcharge;
	private String shippingAddress;
	private float roundingofvalue;
	private String billingaddress;

	private LocalDate createddate;
	private LocalTime createdtime;

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;

	@ManyToOne
	@JoinColumn(name = "suppliersubContacts_id")
	private SupplierSubContacts supplierSubContacts;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne
	@JoinColumn(name = "branchId")
	private Branch branch;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "po_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<PurchaseOrderItems> purchaseOrderItems;

	private long createdby;
	private String createbyname;
	private String role;

	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;

	private String voucherid;
	private long vouchernumber;
	@Column(unique = true)
	private String voucherseries;

//	---- new voucher
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
	@ManyToOne
	@JoinColumn(name = "vouchermaster_id")
private VoucherMaster voucherMaster;

	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVoucherno() {
		return voucherno;
	}

	public void setVoucherno(String voucherno) {
		this.voucherno = voucherno;
	}

	public Date getPodate() {
		return podate;
	}

	public void setPodate(Date podate) {
		this.podate = podate;
	}

	public Date getPodueon() {
		return podueon;
	}

	public void setPodueon(Date podueon) {
		this.podueon = podueon;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTaxtype() {
		return taxtype;
	}

	public void setTaxtype(String taxtype) {
		this.taxtype = taxtype;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getReferenceno() {
		return referenceno;
	}

	public void setReferenceno(String referenceno) {
		this.referenceno = referenceno;
	}

	public Date getReferencedate() {
		return referencedate;
	}

	public void setReferencedate(Date referencedate) {
		this.referencedate = referencedate;
	}

	public String getOtherrefernce() {
		return otherrefernce;
	}

	public void setOtherrefernce(String otherrefernce) {
		this.otherrefernce = otherrefernce;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
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

	public float getGrandtotal() {
		return grandtotal;
	}

	public void setGrandtotal(float grandtotal) {
		this.grandtotal = grandtotal;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public SupplierSubContacts getSupplierSubContacts() {
		return supplierSubContacts;
	}

	public void setSupplierSubContacts(SupplierSubContacts supplierSubContacts) {
		this.supplierSubContacts = supplierSubContacts;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public float getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(float netAmount) {
		this.netAmount = netAmount;
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

	public List<PurchaseOrderItems> getPurchaseOrderItems() {
		return purchaseOrderItems;
	}

	public void setPurchaseOrderItems(List<PurchaseOrderItems> purchaseOrderItems) {
		this.purchaseOrderItems = purchaseOrderItems;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
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

	public String getBillingaddress() {
		return billingaddress;
	}

	public void setBillingaddress(String billingaddress) {
		this.billingaddress = billingaddress;
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

	public String getVouchermasterSeries() {
		return vouchermasterSeries;
	}

	public void setVouchermasterSeries(String vouchermasterSeries) {
		this.vouchermasterSeries = vouchermasterSeries;
	}

}
