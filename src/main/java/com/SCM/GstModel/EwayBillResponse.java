package com.SCM.GstModel;


import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.NotNull;


@Entity
public class EwayBillResponse {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	

	@NotNull
	private long ewayBillNo;
	@NotNull
	private String ewayBillDate;
	@NotNull
	private String validUpto;
	@NotNull
	private String alert;
	@NotNull
	private int jo_id;
	@NotNull
	private String status;
	
	public EwayBillResponse(long id, @NotNull long ewayBillNo, @NotNull String ewayBillDate, @NotNull String validUpto,
			@NotNull String alert, @NotNull int jo_id, @NotNull String status) {

		super();
		this.id = id;
		this.ewayBillNo = ewayBillNo;
		this.ewayBillDate = ewayBillDate;
		this.validUpto = validUpto;
		this.alert = alert;
        this.jo_id = jo_id;
		this.status = status;
	}

	

	public EwayBillResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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

