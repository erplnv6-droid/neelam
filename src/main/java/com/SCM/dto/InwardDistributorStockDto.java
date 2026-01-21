package com.SCM.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SCM.model.InwardDistributorStockItems;
import com.SCM.model.VoucherMaster;

import lombok.Data;

@Data
public class InwardDistributorStockDto {

	private int id;
	private LocalDate indate;
	private int distributorid;
	private String invoiceno;
	
	private double grossamount;
	private float igst;
	private float cgst;
	private float sgst;
	private double roundvalue;
	private double grandtotal;
	
	private LocalDate createddate;
	private LocalTime createdtime;
	private long createdby;
	private String createbyname;
	private String role;
	
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	private String updatedrole;

	private List<InwardDistributorStockItems> inwardDistributorStockItems;
	
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;

private VoucherMaster voucherMaster;
}
