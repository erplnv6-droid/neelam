package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String status;
	private String taxtype;
	private String invoiceno;
	private String ewaybillno;
	private Date purchasedate;
	private String otherreference;
	private String supplierinvoiceno;
	private Date supplierinvoicedate;
	private String paymentTerms;
	private float grossAmount;
	private float igst;
	private float cgst;
	private float sgst;
	private String remarks;
	private float roundofvalue;
	private float grandtotal;
	private float shippingcharge;
	private String shippingaddress;
	private float netamount;
	private float taxAmount;
	private int poId;
	private int mrnId;
	private LocalDate ewaybilldate;

	private LocalDate createddate;
	private LocalTime createdtime;

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "purchase_users", joinColumns = @JoinColumn(name = "Purchase_id"), inverseJoinColumns = @JoinColumn(name = "users_id"))
	private List<Staff> owners;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;

	@ManyToOne
	@JoinColumn(name = "supplier_sub_conatacts_id")
	private SupplierSubContacts supplierSubContacts;

	@ManyToOne
	@JoinColumn(name = "branchId")
	private Branch branch;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "purchase_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<PurchaseItems> purchaseItems = new ArrayList<>();

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaxtype() {
		return taxtype;
	}

	public void setTaxtype(String taxtype) {
		this.taxtype = taxtype;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public String getEwaybillno() {
		return ewaybillno;
	}

	public void setEwaybillno(String ewaybillno) {
		this.ewaybillno = ewaybillno;
	}

	public Date getPurchasedate() {
		return purchasedate;
	}

	public void setPurchasedate(Date purchasedate) {
		this.purchasedate = purchasedate;
	}

	public String getOtherreference() {
		return otherreference;
	}

	public void setOtherreference(String otherreference) {
		this.otherreference = otherreference;
	}

	public String getSupplierinvoiceno() {
		return supplierinvoiceno;
	}

	public void setSupplierinvoiceno(String supplierinvoiceno) {
		this.supplierinvoiceno = supplierinvoiceno;
	}

	public Date getSupplierinvoicedate() {
		return supplierinvoicedate;
	}

	public void setSupplierinvoicedate(Date supplierinvoicedate) {
		this.supplierinvoicedate = supplierinvoicedate;
	}

	public float getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(float grossAmount) {
		this.grossAmount = grossAmount;
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

	public float getNetamount() {
		return netamount;
	}

	public void setNetamount(float netamount) {
		this.netamount = netamount;
	}

	public float getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(float taxAmount) {
		this.taxAmount = taxAmount;
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

	public float getShippingcharge() {
		return shippingcharge;
	}

	public void setShippingcharge(float shippingcharge) {
		this.shippingcharge = shippingcharge;
	}

	public String getShippingaddress() {
		return shippingaddress;
	}

	public void setShippingaddress(String shippingaddress) {
		this.shippingaddress = shippingaddress;
	}

	public int getPoId() {
		return poId;
	}

	public void setPoId(int poId) {
		this.poId = poId;
	}

	public int getMrnId() {
		return mrnId;
	}

	public void setMrnId(int mrnId) {
		this.mrnId = mrnId;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public List<Staff> getOwners() {
		return owners;
	}

	public void setOwners(List<Staff> owners) {
		this.owners = owners;
	}

	public Company getCompany() {
		return company;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
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

	public List<PurchaseItems> getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(List<PurchaseItems> purchaseItems) {
		this.purchaseItems = purchaseItems;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDate getEwaybilldate() {
		return ewaybilldate;
	}

	public void setEwaybilldate(LocalDate ewaybilldate) {
		this.ewaybilldate = ewaybilldate;
	}
	
	public VoucherMaster getVoucherMaster() {
		return voucherMaster;
	}

	public void setVoucherMaster(VoucherMaster voucherMaster) {
		this.voucherMaster = voucherMaster;
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

}