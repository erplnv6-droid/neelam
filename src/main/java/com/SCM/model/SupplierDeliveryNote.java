package com.SCM.model;

import java.sql.Date;
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

@Entity
public class SupplierDeliveryNote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date sdndate;
	private String type;
	private String taxtype;
	private String status;
	private String shippingaddress;
	private String remarks;
	private String paymentTerms;
	private float grossamount;
	private float shippingcharge;
	private float igst;
	private float cgst;
	private float sgst;
	private float roundofvalue;
	private float grandtotal;
	private String invoiceno;
	private int poId;

	private LocalDate createddate;
	private LocalTime createdtime;

	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	@ManyToOne
	@JoinColumn(name = "branchId")
	private Branch branch;

	@ManyToOne
	@JoinColumn(name = "suppliersubcontact_id")
	private SupplierSubContacts supplierSubContacts;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "supplierdeliverynote_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<SupplierDeliverNoteItems> supplierDeliverNoteItems;

	private long createdby;

	private String createbyname;

	private String role;

	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;

//	private String voucherid;
//	private long vouchernumber;
//	@Column(unique = true)
//	private String voucherseries;

//	---- new voucher
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
	@ManyToOne
	@JoinColumn(name = "vouchermaster_id")
	private VoucherMaster voucherMaster;

//	public String getVoucherid() {
//		return voucherid;
//	}
//
//	public void setVoucherid(String voucherid) {
//		this.voucherid = voucherid;
//	}
//
//	public long getVouchernumber() {
//		return vouchernumber;
//	}
//
//	public void setVouchernumber(long vouchernumber) {
//		this.vouchernumber = vouchernumber;
//	}
//
//	public String getVoucherseries() {
//		return voucherseries;
//	}
//
//	public void setVoucherseries(String voucherseries) {
//		this.voucherseries = voucherseries;
//	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getSdndate() {
		return sdndate;
	}

	public void setSdndate(Date sdndate) {
		this.sdndate = sdndate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShippingaddress() {
		return shippingaddress;
	}

	public void setShippingaddress(String shippingaddress) {
		this.shippingaddress = shippingaddress;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public float getGrossamount() {
		return grossamount;
	}

	public void setGrossamount(float grossamount) {
		this.grossamount = grossamount;
	}

	public float getShippingcharge() {
		return shippingcharge;
	}

	public void setShippingcharge(float shippingcharge) {
		this.shippingcharge = shippingcharge;
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

	public float getRoundofvalue() {
		return roundofvalue;
	}

	public void setRoundofvalue(float roundofvalue) {
		this.roundofvalue = roundofvalue;
	}

	public float getGrandtotal() {
		return grandtotal;
	}

	public void setGrandtotal(float grandtotal) {
		this.grandtotal = grandtotal;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public SupplierSubContacts getSupplierSubContacts() {
		return supplierSubContacts;
	}

	public void setSupplierSubContacts(SupplierSubContacts supplierSubContacts) {
		this.supplierSubContacts = supplierSubContacts;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public List<SupplierDeliverNoteItems> getSupplierDeliverNoteItems() {
		return supplierDeliverNoteItems;
	}

	public void setSupplierDeliverNoteItems(List<SupplierDeliverNoteItems> supplierDeliverNoteItems) {
		this.supplierDeliverNoteItems = supplierDeliverNoteItems;
	}

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
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
