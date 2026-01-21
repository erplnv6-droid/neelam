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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesReturn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private java.sql.Date salesreturndate;
	private String type;
	private String creditnoteno;
	private String termsofpayment;
	private String invoiceno;
	private Date invoicedate;
	private String otherrefernce;
	private String buyerorderno;
	private Date dated;
	private String dispatcheddocno;
	private String dispatchedthrough;
	private String destination;
	private String termsofdelivery;
	private String declaration;
	private float grossAmount;
	private String paymentTerms;
	private float roundingoff;
	private int igst;
	private int cgst;
	private int sgst;
	private float roundvalue;
	private float shippingcharge;
	private float grandtotal;
	private String customercreditno;
	private java.sql.Date customercreaditdate;
	private LocalDate createddate;
	private LocalTime createdtime;
	private String retailerstatus;
	
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
	@ManyToOne
	@JoinColumn(name = "vouchermaster_id")
private VoucherMaster voucherMaster;
	

	@ManyToOne
	@JoinColumn(name = "distributor_id")
	private Distributor distributor;

	@ManyToOne
	@JoinColumn(name = "retailer_id")
	private Retailer retailer;

	@ManyToOne
	@JoinColumn(name = "branchId")
	private Branch branch;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne
	@JoinColumn(name = "customersubcontacts_id")
	private CustomerSubContacts customerSubContacts;

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "sales_returnid")
	private List<SalesReturnItems> salesReturnItems = new ArrayList<>();
	

	
//	@Lob
//	private String signedInevoice;
//	
//	@Lob
//	private String signedQrCode;
//	

	
	
//	private String version;
//
//	private String taxSch;
//
//	private String supTyp;
//
//   private String ecmGstin;
//
//	private String typ;
//
//	private String no;
//
//	private String dt;
//
//	private String gstin;
//
//	private String lglNm;
//
//	private String addr1;
//
//	private String loc;
//
//	private int pin;
//
//	private String stcd;
//	
//	private String ph;
//	
//	private String em;
//
//    private String bgstin;
//
//	private String blglNm;
//
//    private String pos;
//
//	private String baddr1;
//
//	private String bloc;
//
//	private int bpin;
//
//	private String bstcd;
//	
//	private String bph;
//	
//	private String bem;
//
//	
//
////	@Lob
//	@Column(length = 10)
//	private double assVal;
//
////	@Lob
//	private double cgstVal;
//
////	@Lob
//	private double sgstVal;
//
////	@Lob
//	private double igstVal;
//
////	@Lob
//	private double cesVal;
//
////	@Lob
//	private double stCesVal;
//
////	@Lob
//	private double vdiscount;
//
////	@Lob
//	private double vothChrg;
//
////	@Lob
//	private double totInvVal;
//	
//	@Lob
//private String invoice_status;
	
	private long ewaybillno;
	private String ewbValidTill;

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
	@Column(length = 10)
	private double assVal;

//	@Lob
	private double cgstVal;

//	@Lob
	private double sgstVal;

//	@Lob
	private double igstVal;

//	@Lob
	private double cesVal;

//	@Lob
	private double stCesVal;

//	@Lob
	private double vdiscount;

//	@Lob
	private double vothChrg;

//	@Lob
	private double totInvVal;
	
	@Lob
private String invoice_status;
	
	
	

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
	

	
	
	
}
