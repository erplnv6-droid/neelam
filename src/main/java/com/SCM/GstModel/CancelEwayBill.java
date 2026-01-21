package com.SCM.GstModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class CancelEwayBill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
    private long EwayBillNo;
	
    @NotNull
	private String CancelDate;
	
	@NotNull
	private String status;
	
	@NotNull
	private int sales_id;

	public CancelEwayBill(long id, @NotNull long ewayBillNo, @NotNull String cancelDate, @NotNull String status,
			@NotNull int sales_id) {
		super();
		this.id = id;
		EwayBillNo = ewayBillNo;
		CancelDate = cancelDate;
		this.status = status;
		this.sales_id = sales_id;
	}

	public CancelEwayBill() {
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
		return EwayBillNo;
	}

	public void setEwayBillNo(long ewayBillNo) {
		EwayBillNo = ewayBillNo;
	}

	public String getCancelDate() {
		return CancelDate;
	}

	public void setCancelDate(String cancelDate) {
		CancelDate = cancelDate;
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
