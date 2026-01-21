package com.SCM.GstLoginUserDto;

import javax.validation.constraints.NotNull;

public class CancelEinvoiceSalesReturnDto {

	private String irn;
	

	private String cancelDate;
	
	
	private String status;
	
	
	private int sales_return_id;


	public CancelEinvoiceSalesReturnDto(String irn, String cancelDate, String status, int sales_return_id) {
		super();
		this.irn = irn;
		this.cancelDate = cancelDate;
		this.status = status;
		this.sales_return_id = sales_return_id;
	}


	public CancelEinvoiceSalesReturnDto() {
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


	public int getSales_return_id() {
		return sales_return_id;
	}


	public void setSales_return_id(int sales_return_id) {
		this.sales_return_id = sales_return_id;
	}
	
	
}
