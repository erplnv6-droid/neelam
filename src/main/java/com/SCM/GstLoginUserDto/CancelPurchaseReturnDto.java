package com.SCM.GstLoginUserDto;


public class CancelPurchaseReturnDto {

	
	private String irn;
	
	
	private String cancelDate;
	
	
	private String status;
	
	
	private int purchase_return_id;


	public CancelPurchaseReturnDto(String irn, String cancelDate, String status, int purchase_return_id) {
		super();
		this.irn = irn;
		this.cancelDate = cancelDate;
		this.status = status;
		this.purchase_return_id = purchase_return_id;
	}


	public CancelPurchaseReturnDto() {
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


	public int getPurchase_return_id() {
		return purchase_return_id;
	}


	public void setPurchase_return_id(int purchase_return_id) {
		this.purchase_return_id = purchase_return_id;
	}
	
	
}
