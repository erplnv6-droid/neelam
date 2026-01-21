package com.SCM.dto;


import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.SCM.model.Branch;
import com.SCM.model.Company;
import com.SCM.model.PurchaseItems;
import com.SCM.model.Staff;
import com.SCM.model.Supplier;
import com.SCM.model.SupplierSubContacts;
import com.SCM.model.VoucherMaster;
import com.SCM.model.Warehouse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseDto {

	private int id;
	private String type;
	private String status;
	private String taxtype;
	private String invoiceno;
	private String ewaybillno;
	private String paymentTerms;
	private Date purchasedate;
	private String otherreference;
	private String supplierinvoiceno;
	private Date supplierinvoicedate;
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
	private Warehouse warehouse;
	private List<Staff> owners;
	private Company company;
	private Supplier supplier;
	private Branch branch;
	private LocalDate ewaybilldate;
	private SupplierSubContacts supplierSubContacts;
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
	private String voucherseries;
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
	private VoucherMaster voucherMaster;
	

}
