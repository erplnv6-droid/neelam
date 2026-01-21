package com.SCM.GstModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class CancelEwayResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	
	private long ewayBillNo;
	
	@NotNull
	private String cancelDate;
	
	@NotNull
	private int jo_id;
	
	@NotNull
	private String status;

	public CancelEwayResponse(long id, @NotNull long ewayBillNo, @NotNull String cancelDate, @NotNull int jo_id,
			@NotNull String status) {
		super();
		this.id = id;
		this.ewayBillNo = ewayBillNo;
		this.cancelDate = cancelDate;
		this.jo_id = jo_id;
		this.status = status;
	}

	public CancelEwayResponse() {
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
