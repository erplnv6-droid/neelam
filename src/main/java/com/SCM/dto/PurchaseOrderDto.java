package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.SCM.model.Branch;
import com.SCM.model.Company;
import com.SCM.model.PurchaseOrderItems;
import com.SCM.model.Staff;
import com.SCM.model.Supplier;
import com.SCM.model.SupplierSubContacts;
import com.SCM.model.VoucherMaster;
import com.SCM.model.Warehouse;

import lombok.Data;

@Data
public class PurchaseOrderDto {

	private int id;
	private String voucherno;
	private Date podate;
	private Date podueon;
	private String type;
	private String taxtype;
	private String status;
	private String termsofpayment;
	private String referenceno;
	private Date referencedate;
	private String otherrefernce;
	private String dispatchedthrough;
	

	private String destination;
	
	
	private String termsofdelivery;
	private String remarks;
	private String declaration;
	private String paymentTerms;
	private float netAmount;
	private float igst;
	private float cgst;
	private float sgst;
	private float grandtotal;
	private String shippingcharge;
	private float roundingofvalue;
	private List<Staff> owners;
	private String shippingAddress;

	private Warehouse warehouse;
	

	private Supplier supplier;
	
	private SupplierSubContacts supplierSubContacts;
	private Company company;
	private Branch branch;
	private List<PurchaseOrderItems> purchaseOrderItems;

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
