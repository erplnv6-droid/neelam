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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




@Getter
@Setter
@ToString
@Entity
public class Sales {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String irnno;
	private long ackno;
	private String ackdate;
	private String udyamno;
	private String msmeno;
	private String panno;
	private String gstno;
	private String deliverynoteno;
	private String packaginglistno;
	private String lrno;
	private String lrndate;
	private String edd;
	private String totalnopkg;
	private String buyerorderno;
	private String grnno;
	private String grndate;
	private String invoiceno;
	private long ewaybillno;
	private String ewbValidTill;

	private String ewbDt;
	private String paymentTerms;
	private String destination;
	private String termsofdelivery;
	private java.sql.Date invoicedate;
	private String type;

	@Column(length = 100)
	private String status;
	private String taxtype;
	private double grossamount;
	private double shippingcharge;
	private long taxamount;
	private float netAmount;
	private float netValue;
	private float roundingofvalue;
	private float igst;
	private float cgst;
	private float sgst;
	private float tcs;
	private float shippingtax;
	private String dispatchedthrough;
	private int dcId;
	private float grandtotal;

	private LocalDate createddate;
	private LocalTime createdtime;
	private String retailerstatus;


	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	
	@Lob
	private String signedInevoice;
	
	@Lob
	private String signedQrCode;

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	@ManyToOne
	@JoinColumn(name = "branchId")
	private Branch branch;

	@ManyToOne
	@JoinColumn(name = "distributorId")
	private Distributor distributor;

	private long deliveryAddress;

	@ManyToOne
	@JoinColumn(name = "subConatacts_id")
	private CustomerSubContacts customersubContacts;

	@ManyToOne
	@JoinColumn(name = "retailer_id")
	private Retailer retailer;

	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "sales_id")
	private List<SalesItems> salesItems;
	
	private String voucherid;
	private long vouchernumber;


 

	@Column(unique = true)

	private String voucherseries;
	private long vid;

	private long createdby;
	private String createbyname;
	private String role;
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

////	@Lob
//	private double vdiscount;

//	@Lob
	private float vothChrg;

//	@Lob
	private float totInvVal;

//	@Lob
	private int distance;

	@Lob
	private String transMode;


	
	@ManyToOne
	@JoinColumn(name="transporters_id")
	private Transporters transporters; 

	@Lob
	private String transName;

	@Lob
	private String transDocDt;

	@Lob
	private String transDocNo;

	@Lob
	private String vehNo;

	@Lob
	private String vehType;

	@Lob
	private String imgname;
	
	@Lob
	private String pdfname;
	
	@Lob
	private String imglocation;
	
	@Lob
	private String pdflocation;

	@Lob
private String invoice_status;
	
	@Lob 
	private String eway_status;
	
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
	@ManyToOne
	@JoinColumn(name = "vouchermaster_id")
private VoucherMaster voucherMaster;
	
	
	

}
