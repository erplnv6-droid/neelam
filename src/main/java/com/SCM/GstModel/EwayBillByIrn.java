package com.SCM.GstModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class EwayBillByIrn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
    @NotNull
	private long ewbNo;
	
    @NotNull
	private String ewbDt;

	@NotNull
	private String ewbValidTill;
	
	@NotNull
	private int sales_id;
	
	@NotNull
	private String status;

	public EwayBillByIrn(long id, @NotNull long ewbNo, @NotNull String ewbDt, @NotNull String ewbValidTill,
			@NotNull int sales_id, @NotNull String status) {
		super();
		this.id = id;
		this.ewbNo = ewbNo;
		this.ewbDt = ewbDt;
		this.ewbValidTill = ewbValidTill;
		this.sales_id = sales_id;
		this.status = status;
	}

	public EwayBillByIrn() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEwbNo() {
		return ewbNo;
	}

	public void setEwbNo(long ewbNo) {
		this.ewbNo = ewbNo;
	}

	public String getEwbDt() {
		return ewbDt;
	}

	public void setEwbDt(String ewbDt) {
		this.ewbDt = ewbDt;
	}

	public String getEwbValidTill() {
		return ewbValidTill;
	}

	public void setEwbValidTill(String ewbValidTill) {
		this.ewbValidTill = ewbValidTill;
	}

	public int getSales_id() {
		return sales_id;
	}

	public void setSales_id(int sales_id) {
		this.sales_id = sales_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	
	
	
}
