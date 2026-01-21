package com.SCM.dtoReports;

public class RetailerCountReport {
	private int total_inactive_retailer;
	
	private int total_active_retailer;
	
	private int total_retailer;
	


	public RetailerCountReport(int total_inactive_retailer, int total_active_retailer, int total_retailer
		) {
		super();
		this.total_inactive_retailer = total_inactive_retailer;
		this.total_active_retailer = total_active_retailer;
		this.total_retailer = total_retailer;

	}

	public RetailerCountReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getTotal_inactive_retailer() {
		return total_inactive_retailer;
	}

	public void setTotal_inactive_retailer(int total_inactive_retailer) {
		this.total_inactive_retailer = total_inactive_retailer;
	}

	public int getTotal_active_retailer() {
		return total_active_retailer;
	}

	public void setTotal_active_retailer(int total_active_retailer) {
		this.total_active_retailer = total_active_retailer;
	}

	public int getTotal_retailer() {
		return total_retailer;
	}

	public void setTotal_retailer(int total_retailer) {
		this.total_retailer = total_retailer;
	}




}
