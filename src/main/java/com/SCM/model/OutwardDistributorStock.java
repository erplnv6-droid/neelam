package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class OutwardDistributorStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private java.sql.Date outdisdate;
	private int distributoroid;
	private int retailerid;
	private String refno;
	
	private String uom;
	private double grandtotal;
	
	private double grossamount;
	private float igst;
	private float cgst;
	private float sgst;
	private double roundvalue;
	
	private String retailer;
	
	private String invoiceno;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "outwarddisid")
	private List<OutwardDistributorStockItems> distributorStockItems;
	 
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
	
	private int startnumberwithprefilno;
	private String startnumberwithprefilyes;
	private String vouchermasterSeries;
	private String voucherstatus;
	@ManyToOne
	@JoinColumn(name = "vouchermaster_id")
private VoucherMaster voucherMaster;

	
}
