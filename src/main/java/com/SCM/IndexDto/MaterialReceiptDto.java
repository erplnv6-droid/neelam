package com.SCM.IndexDto;

import java.sql.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.SCM.model.Supplier;

public class MaterialReceiptDto {
	
	private int id;
	
	private Date mrndate;
	
	private String suppliername;
	
	private float grossamount;
	
	private float igst;
	
	private float grandtotal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getMrndate() {
		return mrndate;
	}

	public void setMrndate(Date mrndate) {
		this.mrndate = mrndate;
	}

	

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
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

	

	public MaterialReceiptDto(int id, Date mrndate, String suppliername, float grossamount, float igst,
			float grandtotal) {
		super();
		this.id = id;
		this.mrndate = mrndate;
		this.suppliername = suppliername;
		this.grossamount = grossamount;
		this.igst = igst;
		this.grandtotal = grandtotal;
	}

	public MaterialReceiptDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
