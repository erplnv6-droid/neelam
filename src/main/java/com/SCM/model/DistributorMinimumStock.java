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
import javax.persistence.OneToMany;

@Entity
public class DistributorMinimumStock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int distributorid;

	private float stockqty;

	private String uom;

	private double grandtotal;

	private LocalDate dmsdate;

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

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "dms_id")
	private List<DistributorMinimumStockItems> distributorMinimumStockItems;

	public int getId() {
		return this.id;
	}

	public int getDistributorid() {
		return this.distributorid;
	}

	public float getStockqty() {
		return this.stockqty;
	}

	public LocalDate getDmsdate() {
		return this.dmsdate;
	}

	public LocalDate getCreateddate() {
		return this.createddate;
	}

	public LocalTime getCreatedtime() {
		return this.createdtime;
	}

	public long getCreatedby() {
		return this.createdby;
	}

	public String getCreatebyname() {
		return this.createbyname;
	}

	public String getRole() {
		return this.role;
	}

	public LocalDate getUpdateddate() {
		return this.updateddate;
	}

	public LocalTime getUpdatedtime() {
		return this.updatedtime;
	}

	public long getUpdatedby() {
		return this.updatedby;
	}

	public String getUpdatedbyname() {
		return this.updatedbyname;
	}

	public String getUpdatedrole() {
		return this.updatedrole;
	}

	public List<DistributorMinimumStockItems> getDistributorMinimumStockItems() {
		return this.distributorMinimumStockItems;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDistributorid(int distributorid) {
		this.distributorid = distributorid;
	}

	public void setStockqty(float stockqty) {
		this.stockqty = stockqty;
	}

	public void setDmsdate(LocalDate dmsdate) {
		this.dmsdate = dmsdate;
	}

	public void setCreateddate(LocalDate createddate) {
		this.createddate = createddate;
	}

	public void setCreatedtime(LocalTime createdtime) {
		this.createdtime = createdtime;
	}

	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}

	public void setCreatebyname(String createbyname) {
		this.createbyname = createbyname;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setUpdateddate(LocalDate updateddate) {
		this.updateddate = updateddate;
	}

	public void setUpdatedtime(LocalTime updatedtime) {
		this.updatedtime = updatedtime;
	}

	public void setUpdatedby(long updatedby) {
		this.updatedby = updatedby;
	}

	public void setUpdatedbyname(String updatedbyname) {
		this.updatedbyname = updatedbyname;
	}

	public void setUpdatedrole(String updatedrole) {
		this.updatedrole = updatedrole;
	}

	public void setDistributorMinimumStockItems(List<DistributorMinimumStockItems> distributorMinimumStockItems) {
		this.distributorMinimumStockItems = distributorMinimumStockItems;
	}

	public double getGrandtotal() {
		return grandtotal;
	}

	public void setGrandtotal(double grandtotal) {
		this.grandtotal = grandtotal;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

}
