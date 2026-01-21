package com.SCM.GstLoginUserDto;

import javax.validation.constraints.NotNull;

public class EwayBillGenerateResDto {



	private long ewayBillNo;

	private String ewayBillDate;
	
	private String validUpto;
	
	private String alert;

	
	private int jo_id;
	
	private String status;

	public EwayBillGenerateResDto(long ewayBillNo, String ewayBillDate, String validUpto, String alert, int jo_id,
			String status) {
		super();


	

		this.ewayBillNo = ewayBillNo;
		this.ewayBillDate = ewayBillDate;
		this.validUpto = validUpto;
		this.alert = alert;

		this.jo_id = jo_id;
		this.status = status;

	}

	public EwayBillGenerateResDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public long getEwayBillNo() {
		return ewayBillNo;
	}

	public void setEwayBillNo(long ewayBillNo) {
		this.ewayBillNo = ewayBillNo;
	}

	public String getEwayBillDate() {
		return ewayBillDate;
	}

	public void setEwayBillDate(String ewayBillDate) {
		this.ewayBillDate = ewayBillDate;
	}

	public String getValidUpto() {
		return validUpto;
	}

	public void setValidUpto(String validUpto) {
		this.validUpto = validUpto;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}


	public int getJo_id() {
		return jo_id;
	}

	public void setJo_id(int jo_id) {
		this.jo_id = jo_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	


	
	
}
