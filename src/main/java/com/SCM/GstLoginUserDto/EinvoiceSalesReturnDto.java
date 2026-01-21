package com.SCM.GstLoginUserDto;

public class EinvoiceSalesReturnDto {

	private long AckNo;

	private String AckDt;

	private String Irn;

    private String SignedInvoice;

	private String SignedQRCode;

	private String Status;

	private long EwbNo;

	private String EwbDt;

	private String EwbValidTill;

	private String decodedInvoice;

	private String decodedQrCode;

	private int Sales_return_id;

	public EinvoiceSalesReturnDto(long ackNo, String ackDt, String irn, String signedInvoice, String signedQRCode,
			String status, long ewbNo, String ewbDt, String ewbValidTill, String decodedInvoice, String decodedQrCode,
			int sales_return_id) {
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
		this.decodedInvoice = decodedInvoice;
		this.decodedQrCode = decodedQrCode;
		Sales_return_id = sales_return_id;
	}

	public EinvoiceSalesReturnDto() {
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

	public String getDecodedInvoice() {
		return decodedInvoice;
	}

	public void setDecodedInvoice(String decodedInvoice) {
		this.decodedInvoice = decodedInvoice;
	}

	public String getDecodedQrCode() {
		return decodedQrCode;
	}

	public void setDecodedQrCode(String decodedQrCode) {
		this.decodedQrCode = decodedQrCode;
	}

	public int getSales_return_id() {
		return Sales_return_id;
	}

	public void setSales_return_id(int sales_return_id) {
		Sales_return_id = sales_return_id;
	}
	
	
}
