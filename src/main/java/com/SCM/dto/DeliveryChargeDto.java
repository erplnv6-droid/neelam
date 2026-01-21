package com.SCM.dto;


import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import com.SCM.model.Branch;
import com.SCM.model.Company;
import com.SCM.model.CustomerSubContacts;
import com.SCM.model.DeliveryChargeItems;
import com.SCM.model.Distributor;
import com.SCM.model.Retailer;
import com.SCM.model.SalesOrderItems;
import com.SCM.model.VoucherMaster;
import com.SCM.model.Warehouse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryChargeDto {

	private int id;
	private String type;
	private String status;
	private String taxtype;
	private String buyerorderno;
	private Date buyerorderdate;
	private String totalnopkg;
	private String termsofdelivery;
	private String deliverynotno;
	private java.sql.Date dcdate;
	private String dispatchedthrough;
	private String destination;
	private String irnno;
	private String ackno;
	private Date ackdate;
	private String msmeno;
	private String remarks;
	private float grossAmount;
	private float igst;
	private float cgst;
	private float sgst;
	private String paymentTerms;
	private float roundofvalue;
	private float tcs;
	private String udyamno;
	private String gstno;
	private float taxvalue;
	private float grandtotal;
	private int salesorderId;
	private List<Object> deliverychallanid;
	private List<Object> sorderid;
	private List<Integer> solistid;
	private List<Integer> soitemslistid;
	private List<SalesOrderItems> salesOrderItems;
	private Map<String, Map<String, Object>> salesOrderItems1;
	private List<String> solistids;
	private int salesorderitemsid;
	private Warehouse warehouse;
	private Company company;
	private Branch branch;
	private String salesorderids;
	private Distributor distributor;
	private long deliveryAddress;
	private Retailer retailer;
	private CustomerSubContacts customerSubContacts;
	private List<DeliveryChargeItems> dcItems = new ArrayList<>();
	private String retailerstatus;

	private float accountno;
	private String igstoutput;
	private String branchname;
	private String bankname;
	private String ifsccode;
	
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