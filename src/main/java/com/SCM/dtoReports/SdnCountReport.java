package com.SCM.dtoReports;

public class SdnCountReport {

private int total_pending_count;
	
	private int total_converted_count;
	
	private int total_count;
	
	private double total_amount;

	public SdnCountReport(int total_pending_count, int total_converted_count, int total_count, double total_amount) {
		super();
		this.total_pending_count = total_pending_count;
		this.total_converted_count = total_converted_count;
		this.total_count = total_count;
		this.total_amount = total_amount;
	}

	public SdnCountReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getTotal_pending_count() {
		return total_pending_count;
	}

	public void setTotal_pending_count(int total_pending_count) {
		this.total_pending_count = total_pending_count;
	}

	public int getTotal_converted_count() {
		return total_converted_count;
	}

	public void setTotal_converted_count(int total_converted_count) {
		this.total_converted_count = total_converted_count;
	}

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}


	
}
