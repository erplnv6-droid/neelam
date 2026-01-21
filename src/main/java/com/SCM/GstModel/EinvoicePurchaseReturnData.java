package com.SCM.GstModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Entity
public class EinvoicePurchaseReturnData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@NotNull
	private long AckNo;
	
	@NotNull
	private String AckDt;
	
	@NotNull
	private String Irn;
	
	
	@Column(name = "signed_invoice", length =10000)

@Lob
@NotNull
private String SignedInvoice;

	@Column(name = "signed_qr_code", length =10000)

@Lob
@NotNull
private String SignedQRCode;
	
	@NotNull
	private String Status;
	

	private long EwbNo;
	

	private String EwbDt;
	

	private String EwbValidTill;
	
	@NotNull
	private int Purchase_return_id;

	public EinvoicePurchaseReturnData(long id, @NotNull long ackNo, @NotNull String ackDt, @NotNull String irn,
			@NotNull String signedInvoice, @NotNull String signedQRCode, @NotNull String status, long ewbNo,
			String ewbDt, String ewbValidTill, @NotNull int purchase_return_id) {
		super();
		this.id = id;
		AckNo = ackNo;
		AckDt = ackDt;
		Irn = irn;
		SignedInvoice = signedInvoice;
		SignedQRCode = signedQRCode;
		Status = status;
		EwbNo = ewbNo;
		EwbDt = ewbDt;
		EwbValidTill = ewbValidTill;
		Purchase_return_id = purchase_return_id;
	}

	public EinvoicePurchaseReturnData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getPurchase_return_id() {
		return Purchase_return_id;
	}

	public void setPurchase_return_id(int purchase_return_id) {
		Purchase_return_id = purchase_return_id;
	}
	
	
	
	
	
}
