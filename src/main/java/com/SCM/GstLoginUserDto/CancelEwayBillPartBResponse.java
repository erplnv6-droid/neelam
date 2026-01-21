package com.SCM.GstLoginUserDto;

import javax.validation.constraints.NotNull;

public class CancelEwayBillPartBResponse {

	private long ewayBillNo;
	

	private String cancelDate;
	

	private int jo_id;

	private String status;

	public CancelEwayBillPartBResponse(long ewayBillNo, String cancelDate, int jo_id, String status) {
		super();
		this.ewayBillNo = ewayBillNo;
		this.cancelDate = cancelDate;
		this.jo_id = jo_id;
		this.status = status;
	}

	public CancelEwayBillPartBResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getEwayBillNo() {
		return ewayBillNo;
	}

	public void setEwayBillNo(long ewayBillNo) {
		this.ewayBillNo = ewayBillNo;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
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
