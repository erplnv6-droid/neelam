package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.SCM.GstDto.BuyerDtls;
import com.SCM.GstDto.DocDtls;
import com.SCM.GstDto.EwbDtls;
import com.SCM.GstDto.ItemList;
import com.SCM.GstDto.SellerDtls;
import com.SCM.GstDto.TranDtls;
import com.SCM.GstDto.ValDtls;
import com.SCM.model.Branch;
import com.SCM.model.Company;
import com.SCM.model.PurchaseReturnItems;
import com.SCM.model.Supplier;
import com.SCM.model.SupplierSubContacts;
import com.SCM.model.VoucherMaster;
import com.SCM.model.Warehouse;

import lombok.Data;

@Data
public class PurchaseReturnDto {

	private int id;
	private String type;
	private String taxtype;
	private java.sql.Date purchasereturndate;
	private String paymentTerms;
	private Date sdndate;
	private String debitnoteno;

	private String originalinvoiceno;
	private Date originalinvoicenodate;
	private String otherrefernce;
	private float grossAmount;
	private String remarks;
	private int igst;
	private int cgst;
	private int sgst;
	private float taxAmount;
	private float netamount;
	private float roundvalue;
	private float grandtotal;
	private float shippingcharge;
	private String suppliercreditno;
	private java.sql.Date suppliercreaditdate;
	private Warehouse warehouse;
	private Supplier supplier;
	private Branch branch;
	private String shippingaddress;
	private SupplierSubContacts supplierSubContacts;
	private Company company;
	private List<PurchaseReturnItems> purchaseReturnItems;
	private String voucherid;
	private long vouchernumber;
	@Column(unique = true)
	private String voucherseries;

	private long createdby;
	private String createbyname;
	private String role;
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	private String irnno;
	private long ackno;
	private String ackdate;
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
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
	private VoucherMaster voucherMaster;

}
