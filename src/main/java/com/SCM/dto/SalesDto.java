package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.SCM.GstDto.BuyerDtls;
import com.SCM.GstDto.DocDtls;
import com.SCM.GstDto.EwbDtls;
import com.SCM.GstDto.ItemList;
import com.SCM.GstDto.SellerDtls;
import com.SCM.GstDto.TranDtls;
import com.SCM.GstDto.ValDtls;
import com.SCM.model.Branch;
import com.SCM.model.CustomerSubContacts;
import com.SCM.model.Distributor;
import com.SCM.model.Retailer;
import com.SCM.model.SalesItems;
import com.SCM.model.Transporters;
import com.SCM.model.VoucherMaster;
import com.SCM.model.Warehouse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class SalesDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String irnno;

	private long ackno;
	private String ackdate;
	
	private String udyamno;
	private String msmeno;
	private String paymentTerms;
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
    private float grandtotal;
	private long ewaybillno;
	private String ewbValidTill;
	private String ewbDt;

	

	private String destination;
	private String termsofdelivery;
	private java.sql.Date invoicedate;
	private String type;
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
	private float igst_val;
	private float cgst_val;
	private float sgst_val;
	private float tcs;
	private float shippingtax;
	private String dispatchedthrough;
	private int dcId;
	private Warehouse warehouse;
	private Distributor distributor;
	private long deliveryAddress;
	private Branch branch;
	private Retailer retailer;
	private CustomerSubContacts customersubContacts;
	private List<SalesItems> salesItems;

	private String signedInevoice;
	private String signedQrCode;

	private String version;
	private TranDtls tranDtls;
	private DocDtls docDtls;
	private SellerDtls sellerDtls;
	private BuyerDtls buyerDtls;
	private List<ItemList> itemList;
	private ValDtls valDtls;
	private EwbDtls ewbDtls;

	private String voucherid;
	private long vouchernumber;

	private String imgname;
	private String pdfname;
	private String imglocation;
	private String pdflocation;

	@Column(unique = true)
	private String voucherseries;
	private long vid;
	private String invoice_status;
	private String eway_status;

	private long createdby;
	private String createbyname;
	private String role;
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	private Transporters transporters;
	
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
    private VoucherMaster voucherMaster;

}
