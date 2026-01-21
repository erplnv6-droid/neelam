package com.SCM.GstLoginUserDto;

import javax.validation.constraints.NotNull;

public class CancelEinvoiceDto {


	private String irn;
	

	private String cancelDate;
	

	private String status;
	

	private int sales_id;


	public CancelEinvoiceDto(String irn, String cancelDate, String status, int sales_id) {
		super();
		this.irn = irn;
		this.cancelDate = cancelDate;
		this.status = status;
		this.sales_id = sales_id;
	}


	public CancelEinvoiceDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getIrn() {
		return irn;
	}


	public void setIrn(String irn) {
		this.irn = irn;
	}


	public String getCancelDate() {
		return cancelDate;
	}


	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public int getSales_id() {
		return sales_id;
	}


	public void setSales_id(int sales_id) {
		this.sales_id = sales_id;
	}
	
	
}
