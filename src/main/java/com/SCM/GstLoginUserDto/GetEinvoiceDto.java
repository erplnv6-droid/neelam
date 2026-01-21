package com.SCM.GstLoginUserDto;

import javax.validation.constraints.NotNull;

public class GetEinvoiceDto {

	private long AckNo;
	

	private String AckDt;
	

	private String Irn;
	

	private String SignedInvoice;
	

	private String SignedQRCode;
	

	private String Status;
	

	private long EwbNo;
	

	private String EwbDt;
	

	private String EwbValidTill;
	


	private int Sales_id;



	public GetEinvoiceDto(long ackNo, String ackDt, String irn, String signedInvoice, String signedQRCode,
			String status, long ewbNo, String ewbDt, String ewbValidTill, int sales_id) {
		super();
		AckNo = ackNo;
		AckDt = ackDt;
		Irn = irn;
		SignedInvoice = signedInvoice;
		SignedQRCode = signedQRCode;
		Status = status;
		EwbNo = ewbNo;
		EwbDt = ewbDt;
		EwbValidTill = ewbValidTill;
		Sales_id = sales_id;
	}



	public GetEinvoiceDto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public long getAckNo() {
		return AckNo;
	}



	public void setAckNo(long ackNo) {
		AckNo = ackNo;
	}



	public String getAckDt() {
		return AckDt;
	}



	public void setAckDt(String ackDt) {
		AckDt = ackDt;
	}



	public String getIrn() {
		return Irn;
	}



	public void setIrn(String irn) {
		Irn = irn;
	}



	public String getSignedInvoice() {
		return SignedInvoice;
	}



	public void setSignedInvoice(String signedInvoice) {
		SignedInvoice = signedInvoice;
	}



	public String getSignedQRCode() {
		return SignedQRCode;
	}



	public void setSignedQRCode(String signedQRCode) {
		SignedQRCode = signedQRCode;
	}



	public String getStatus() {
		return Status;
	}



	public void setStatus(String status) {
		Status = status;
	}



	public long getEwbNo() {
		return EwbNo;
	}



	public void setEwbNo(long ewbNo) {
		EwbNo = ewbNo;
	}



	public String getEwbDt() {
		return EwbDt;
	}



	public void setEwbDt(String ewbDt) {
		EwbDt = ewbDt;
	}



	public String getEwbValidTill() {
		return EwbValidTill;
	}



	public void setEwbValidTill(String ewbValidTill) {
		EwbValidTill = ewbValidTill;
	}



	public int getSales_id() {
		return Sales_id;
	}



	public void setSales_id(int sales_id) {
		Sales_id = sales_id;
	}
	
	
}
