package com.SCM.dto;

import java.sql.Date;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SCM.model.Branch;
import com.SCM.model.Company;
import com.SCM.model.MaterialReceiptNoteItems;
import com.SCM.model.Supplier;
import com.SCM.model.SupplierSubContacts;
import com.SCM.model.VoucherMaster;
import com.SCM.model.Warehouse;

import lombok.Data;

@Data
public class MaterialRecepitNoteDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Date mrndate;
	private String type;
	private String taxtype;
	private String status;
	private String receiptnoteno;
	private String refernceno;
	private String paymentTerms;
	private String receiptdate;
	private String otherreference;
	private float grossamount;
	private float igst;
	private float cgst;
	private float sgst;
	private float roundofvalue;
	private float grandtotal;
	private String shippingAddress;
	private float shippingcharge;
	private int supplierdeliverynote_id;
	private Company company;
	private Warehouse warehouse;
	private Supplier supplier;
	private Branch branch;
	private SupplierSubContacts supplierSubContacts;
	private List<MaterialReceiptNoteItems> materialReceiptNoteItems;
	private String remarks;
	private String ewaybillno;
	private String einvoiceno;
	
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
	private String voucherseries;

	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
	private VoucherMaster voucherMaster;


}
