package com.SCM.GstLoginUserDto;

public class CancelEwayBillPartB {

	
	private long ewbNo;
	
	private int cancelRsnCode;
	
	private String cancelRmrk;

	public CancelEwayBillPartB(long ewbNo, int cancelRsnCode, String cancelRmrk) {
		super();
		this.ewbNo = ewbNo;
		this.cancelRsnCode = cancelRsnCode;
		this.cancelRmrk = cancelRmrk;
	}

	public CancelEwayBillPartB() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getEwbNo() {
		return ewbNo;
	}

	public void setEwbNo(long ewbNo) {
		this.ewbNo = ewbNo;
	}

	public int getCancelRsnCode() {
		return cancelRsnCode;
	}

	public void setCancelRsnCode(int cancelRsnCode) {
		this.cancelRsnCode = cancelRsnCode;
	}

	public String getCancelRmrk() {
		return cancelRmrk;
	}

	public void setCancelRmrk(String cancelRmrk) {
		this.cancelRmrk = cancelRmrk;
	}
	
	
}
