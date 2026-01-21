package com.SCM.IndexDto;

import java.util.Date;

public class PurchaseReturnPageDto {
	
	private int id;
	
	private Date sdndate;
	
	private String suppliername;
	
	private float netAmount;
	
	public PurchaseReturnPageDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PurchaseReturnPageDto(int id, Date sdndate, String suppliername, float netAmount, int igst, float grandtotal,
			String remarks) {
		super();
		this.id = id;
		this.sdndate = sdndate;
		this.suppliername = suppliername;
		this.netAmount = netAmount;
		this.igst = igst;
		this.grandtotal = grandtotal;
		this.remarks = remarks;
	}

	private int igst;
	
	private float grandtotal;
	
	private String remarks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getSdndate() {
		return sdndate;
	}

	public void setSdndate(Date sdndate) {
		this.sdndate = sdndate;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public float getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(float netAmount) {
		this.netAmount = netAmount;
	}

	public int getIgst() {
		return igst;
	}

	public void setIgst(int igst) {
		this.igst = igst;
	}

	public float getGrandtotal() {
		return grandtotal;
	}

	public void setGrandtotal(float grandtotal) {
		this.grandtotal = grandtotal;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
