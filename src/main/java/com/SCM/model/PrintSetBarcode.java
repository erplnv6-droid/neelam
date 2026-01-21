package com.SCM.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PrintSetBarcode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Brand brand;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Product product;
	
	private int noprint;
	
	private LocalDate createddate;
	private LocalTime createdtime;
	private long createdby;
	private String createbyname;
	private LocalDate updateddate;
	private LocalTime updatedtime;
	private long updatedby;
	private String updatedbyname;
	public PrintSetBarcode(long id, Brand brand, Product product, int noprint, LocalDate createddate,
			LocalTime createdtime, long createdby, String createbyname, LocalDate updateddate, LocalTime updatedtime,
			long updatedby, String updatedbyname) {
		super();
		this.id = id;
		this.brand = brand;
		this.product = product;
		this.noprint = noprint;
		this.createddate = createddate;
		this.createdtime = createdtime;
		this.createdby = createdby;
		this.createbyname = createbyname;
		this.updateddate = updateddate;
		this.updatedtime = updatedtime;
		this.updatedby = updatedby;
		this.updatedbyname = updatedbyname;
	}
	public PrintSetBarcode() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNoprint() {
		return noprint;
	}
	public void setNoprint(int noprint) {
		this.noprint = noprint;
	}
	public LocalDate getCreateddate() {
		return createddate;
	}
	public void setCreateddate(LocalDate createddate) {
		this.createddate = createddate;
	}
	public LocalTime getCreatedtime() {
		return createdtime;
	}
	public void setCreatedtime(LocalTime createdtime) {
		this.createdtime = createdtime;
	}
	public long getCreatedby() {
		return createdby;
	}
	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}
	public String getCreatebyname() {
		return createbyname;
	}
	public void setCreatebyname(String createbyname) {
		this.createbyname = createbyname;
	}
	public LocalDate getUpdateddate() {
		return updateddate;
	}
	public void setUpdateddate(LocalDate updateddate) {
		this.updateddate = updateddate;
	}
	public LocalTime getUpdatedtime() {
		return updatedtime;
	}
	public void setUpdatedtime(LocalTime updatedtime) {
		this.updatedtime = updatedtime;
	}
	public long getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(long updatedby) {
		this.updatedby = updatedby;
	}
	public String getUpdatedbyname() {
		return updatedbyname;
	}
	public void setUpdatedbyname(String updatedbyname) {
		this.updatedbyname = updatedbyname;
	}
	
	
	
	



}
