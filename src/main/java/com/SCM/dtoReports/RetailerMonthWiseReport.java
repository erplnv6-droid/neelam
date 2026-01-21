package com.SCM.dtoReports;

public class RetailerMonthWiseReport {
	
	private String month;
	
	private String year;

	private double order_qty_kg;
	
	private double order_qty_kg_pcs;
	
	private double rate;
	
	private double order_value;
	
	private double order_qty_pcs_set;
	
	private double rate_set;
	
	private double order_value_set;

	public RetailerMonthWiseReport(String month, String year, double order_qty_kg, double order_qty_kg_pcs, double rate,
			double order_value, double order_qty_pcs_set, double rate_set, double order_value_set) {
		super();
		this.month = month;
		this.year = year;
		this.order_qty_kg = order_qty_kg;
		this.order_qty_kg_pcs = order_qty_kg_pcs;
		this.rate = rate;
		this.order_value = order_value;
		this.order_qty_pcs_set = order_qty_pcs_set;
		this.rate_set = rate_set;
		this.order_value_set = order_value_set;
	}

	public RetailerMonthWiseReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public double getOrder_qty_kg() {
		return order_qty_kg;
	}

	public void setOrder_qty_kg(double order_qty_kg) {
		this.order_qty_kg = order_qty_kg;
	}

	public double getOrder_qty_kg_pcs() {
		return order_qty_kg_pcs;
	}

	public void setOrder_qty_kg_pcs(double order_qty_kg_pcs) {
		this.order_qty_kg_pcs = order_qty_kg_pcs;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getOrder_value() {
		return order_value;
	}

	public void setOrder_value(double order_value) {
		this.order_value = order_value;
	}

	public double getOrder_qty_pcs_set() {
		return order_qty_pcs_set;
	}

	public void setOrder_qty_pcs_set(double order_qty_pcs_set) {
		this.order_qty_pcs_set = order_qty_pcs_set;
	}

	public double getRate_set() {
		return rate_set;
	}

	public void setRate_set(double rate_set) {
		this.rate_set = rate_set;
	}

	public double getOrder_value_set() {
		return order_value_set;
	}

	public void setOrder_value_set(double order_value_set) {
		this.order_value_set = order_value_set;
	}

	

	
	
}
