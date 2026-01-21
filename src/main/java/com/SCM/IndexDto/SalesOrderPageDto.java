package com.SCM.IndexDto;

import java.util.Date;

public class SalesOrderPageDto {
	
	
	private int id;
	
	private Date sodate;
	
	private String contactname;
	
	private float grossamount;
	
	private float igst;
	
	private float grandtotal;
	
	private String remarks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getSodate() {
		return sodate;
	}

	public void setSodate(Date sodate) {
		this.sodate = sodate;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public float getGrossamount() {
		return grossamount;
	}

	public void setGrossamount(float grossamount) {
		this.grossamount = grossamount;
	}

	public float getIgst() {
		return igst;
	}

	public void setIgst(float igst) {
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

	public SalesOrderPageDto(int id, Date sodate, String contactname, float grossamount, float igst, float grandtotal,
			String remarks) {
		super();
		this.id = id;
		this.sodate = sodate;
		this.contactname = contactname;
		this.grossamount = grossamount;
		this.igst = igst;
		this.grandtotal = grandtotal;
		this.remarks = remarks;
	}

	public SalesOrderPageDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
