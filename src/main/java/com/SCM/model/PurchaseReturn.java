package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseReturn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String type;
	private String taxtype;
	private java.sql.Date purchasereturndate;
	private Date sdndate;
	private String debitnoteno;

	private String originalinvoiceno;
	private Date originalinvoicenodate;
	private String otherrefernce;
	private float grossAmount;
	private String paymentTerms;
	private String remarks;
	private int igst;
	private int cgst;
	private int sgst;
	private float taxAmount;
	private float netamount;
	private float roundvalue;
	private float grandtotal;
	private float shippingcharge;
	private String shippingaddress;
	private String suppliercreditno;
	private java.sql.Date suppliercreaditdate;
	private long ewaybillno;
	private String ewbValidTill;
	
	private String voucherid;
	private long vouchernumber;
	@Column(unique = true)
	private String voucherseries;

	private String ewbDt;

	@Lob
	private String signedInevoice;

	@Lob
	private String signedQrCode;

	private String irnno;
	private long ackno;
	private String ackdate;

	private String version;

	private String taxSch;

	private String supTyp;

	private String ecmGstin;

	private String typ;

	private String no;

	private String dt;

	private String gstin;

	private String lglNm;

	private String addr1;

	private String loc;

	private int pin;

	private String stcd;

	private String ph;

	private String em;

	private String bgstin;

	private String blglNm;

	private String pos;

	private String baddr1;

	private String bloc;

	private int bpin;

	private String bstcd;

	private String bph;

	private String bem;

//	@Lob

	private float assVal;

//	@Lob
	private float cgstVal;

//	@Lob
	private float sgstVal;

//	@Lob
	private float igstVal;

//	@Lob
	private float cesVal;

//	@Lob
	private float stCesVal;

//	@Lob
	private float vdiscount;

//	@Lob
	private float vothChrg;

//	@Lob
	private float totInvVal;

	@Lob
	private String invoice_status;

	private long createdby;
	private String createbyname;
	private String role;
	private LocalDate createddate;
	private LocalTime createdtime;

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
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;

	@ManyToOne
	@JoinColumn(name = "supplierSubConatacts_id")
	private SupplierSubContacts supplierSubContacts;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "purchasereturn_id")
	private List<PurchaseReturnItems> purchaseReturnItems;
	
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
	
	@ManyToOne
	@JoinColumn(name = "vouchermaster_id")
private VoucherMaster voucherMaster;





}
