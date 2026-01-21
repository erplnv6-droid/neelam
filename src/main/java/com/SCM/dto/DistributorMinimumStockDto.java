package com.SCM.dto;

import com.SCM.model.DistributorMinimumStockItems;
import com.SCM.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistributorMinimumStockDto {
	private int id;

	private int distributorid;
	private double grandtotal;
	private float stockqty;
	private String uom;

	private LocalDate createddate;

	private LocalTime createdtime;

	private Product product;

	private LocalDate dmsdate;

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

	public LocalDate getCreateddate() {
		return this.createddate;
	}

	public LocalTime getCreatedtime() {
		return this.createdtime;
	}

	public Product getProduct() {
		return this.product;
	}

	public LocalDate getDmsdate() {
		return this.dmsdate;
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

	public void setCreateddate(LocalDate createddate) {
		this.createddate = createddate;
	}

	public void setCreatedtime(LocalTime createdtime) {
		this.createdtime = createdtime;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setDmsdate(LocalDate dmsdate) {
		this.dmsdate = dmsdate;
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
