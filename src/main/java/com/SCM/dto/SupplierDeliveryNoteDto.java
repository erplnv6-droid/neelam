package com.SCM.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.SCM.model.Branch;
import com.SCM.model.Supplier;
import com.SCM.model.SupplierDeliverNoteItems;
import com.SCM.model.SupplierSubContacts;
import com.SCM.model.VoucherMaster;
import com.SCM.model.Warehouse;

import lombok.Data;

@Data
public class SupplierDeliveryNoteDto {

	private int id;
	private Date sdndate;
	private String type;
	private String taxtype;
	private String status;
	private String shippingaddress;
	private String remarks;
	private float grossamount;
	private float shippingcharge;
	private String paymentTerms;
	private float igst;
	private float cgst;
	private float sgst;
	private float roundofvalue;
	private float grandtotal;
	private String invoiceno;
	private Supplier supplier;
	private Branch branch;
	private Warehouse warehouse;
	private SupplierSubContacts supplierSubContacts;
	private List<SupplierDeliverNoteItems> supplierDeliverNoteItems;

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
