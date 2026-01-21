package com.SCM.dto;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SCM.GstDto.BuyerDtls;
import com.SCM.GstDto.DocDtls;
import com.SCM.GstDto.EwbDtls;
import com.SCM.GstDto.ItemList;
import com.SCM.GstDto.SellerDtls;
import com.SCM.GstDto.TranDtls;
import com.SCM.GstDto.ValDtls;
import com.SCM.model.Branch;
import com.SCM.model.Company;
import com.SCM.model.CustomerSubContacts;
import com.SCM.model.Distributor;
import com.SCM.model.Retailer;
import com.SCM.model.SalesReturnItems;
import com.SCM.model.VoucherMaster;
import com.SCM.model.Warehouse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesReturnDto {

	private int id;
	private String irnno;

	private long ackno;
	private String ackdate;
	
	private java.sql.Date salesreturndate;
	private String type;
	private String creditnoteno;
	private String termsofpayment;
	private String invoiceno;
	private Date invoicedate;
	private String paymentTerms;
	private String otherrefernce;
	private String buyerorderno;
	private Date dated;
	private String dispatcheddocno;
	private String dispatchedthrough;
	private String destination;
	private String termsofdelivery;
	private String declaration;
	private float grossAmount;
	private float roundingoff;
	private int igst;
	private int cgst;
	private int sgst;
	private float roundvalue;
	private float grandtotal;
	private float shippingcharge;
	private String customercreditno;
	private java.sql.Date customercreaditdate;
	private Distributor distributor;
	private Company company;
	private Branch branch;
	private CustomerSubContacts customerSubContacts;
	private Warehouse warehouse;
	private Retailer retailer;
	private List<SalesReturnItems> salesReturnItems = new ArrayList<>();

	
	
	private long createdby;
	private String createbyname;
	private String role;
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	
	private long ewaybillno;
	private String ewbValidTill;

	private String ewbDt;
	
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
	
	private String invoice_status;
	
	
	private String voucherid;
	private long vouchernumber;
	private String voucherseries;
	
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;

private VoucherMaster voucherMaster;
	
	

}
