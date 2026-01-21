package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SCM.model.OutwardDistributorStockItems;
import com.SCM.model.VoucherMaster;

import lombok.Data;

@Data
public class OutwardDistributorStockDto {

	private int id;
	private java.sql.Date outdisdate;
	private int distributoroid;
	private int retailerid;
	private String refno;
	private double grandtotal;
	private double grossamount;
	private float igst;
	private float cgst;
	private float sgst;
	private double roundvalue;
	private String uom;
	private String retailer;
	private String invoiceno;
	private List<OutwardDistributorStockItems> distributorStockItems;

	private long createdby;
	private String createbyname;
	private String role;
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;
	
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
    private VoucherMaster voucherMaster;

}
