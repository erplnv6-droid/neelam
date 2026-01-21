package com.SCM.GstModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class CancelPurchaseReturn {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String irn;
	
	@NotNull
	private String cancelDate;
	
	@NotNull
	private String status;
	
	@NotNull
	private int purchase_return_id;

	public CancelPurchaseReturn(long id, @NotNull String irn, @NotNull String cancelDate, @NotNull String status,
			@NotNull int purchase_return_id) {
		super();
		this.id = id;
		this.irn = irn;
		this.cancelDate = cancelDate;
		this.status = status;
		this.purchase_return_id = purchase_return_id;
	}

	public CancelPurchaseReturn() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
