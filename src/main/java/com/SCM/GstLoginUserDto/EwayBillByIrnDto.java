package com.SCM.GstLoginUserDto;

public class EwayBillByIrnDto {
	
	private long ewbNo;
	
	private String ewbDt;
	
	private String ewbValidTill;
	
	private int sales_id;
	
	private String status;

	public EwayBillByIrnDto(long ewbNo, String ewbDt, String ewbValidTill, int sales_id, String status) {
		super();
		this.ewbNo = ewbNo;
		this.ewbDt = ewbDt;
		this.ewbValidTill = ewbValidTill;
		this.sales_id = sales_id;
		this.status = status;
	}

	public EwayBillByIrnDto() {
		super();
		// TODO Auto-generated constructor stub
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

	@Override
	public String toString() {
		return "EwayBillByIrnDto [ewbNo=" + ewbNo + ", ewbDt=" + ewbDt + ", ewbValidTill=" + ewbValidTill
				+ ", sales_id=" + sales_id + ", status=" + status + "]";
	}

	
	
	

}
