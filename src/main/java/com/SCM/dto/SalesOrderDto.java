package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SCM.model.Branch;
import com.SCM.model.CustomerSubContacts;
import com.SCM.model.Distributor;
import com.SCM.model.Retailer;
import com.SCM.model.SalesOrderItems;
import com.SCM.model.Staff;
import com.SCM.model.VoucherMaster;
import com.SCM.model.Warehouse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesOrderDto {

	private int id;
	private String buyerorder;
	private String voucherno;
	private String dispatchedthrough;
	private String destination;
	private String termsofdelivery;
	private String paymentTerms;
	private float igst;
	private float cgst;
	private float sgst;
	private float grossamount;
	private float grandtotal;
	private String shippingcharge;
	private float roundingofvalue;
	private java.sql.Date sodate;
	private boolean quotationstatus;
	private String taxtype;
	private String remarks;
	private int primaryorderId;
	private String status;
	private List<Staff> owners;
	private Warehouse warehouse;
	private Distributor distributor;
	private long deliveryAddress;
	private Branch branch;
	private Retailer retailer;
	private CustomerSubContacts customerSubContacts;
	private List<SalesOrderItems> salesOrderItems;
	private long createdby;
	private String createbyname;
	private String role;
	private LocalDate buyerorderdate;
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	
	private String voucherid;
	private long vouchernumber;
	@Column(unique = true)
	private String voucherseries;
	
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
    private VoucherMaster voucherMaster;

}
