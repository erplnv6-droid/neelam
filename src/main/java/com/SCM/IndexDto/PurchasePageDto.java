package com.SCM.IndexDto;

import java.util.Date;

public class PurchasePageDto {
	
	private int id;
	
	private String contactname;	
	
	private Date purchasedate;
	
	private String shippingaddress;
	
	private String invoiceno;
	
	private String ewaybillno;
	
	private float netAmount;
	
	private float igst;
	
	private float grandtotal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public Date getPurchasedate() {
		return purchasedate;
	}

	public void setPurchasedate(Date purchasedate) {
		this.purchasedate = purchasedate;
	}

	public String getShippingaddress() {
		return shippingaddress;
	}

	public void setShippingaddress(String shippingaddress) {
		this.shippingaddress = shippingaddress;
	}
	
	
	

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public String getEwaybillno() {
		return ewaybillno;
	}

	public void setEwaybillno(String ewaybillno) {
		this.ewaybillno = ewaybillno;
	}

	public float getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(float netAmount) {
		this.netAmount = netAmount;
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

	

	public PurchasePageDto(int id, String contactname, Date purchasedate, String shippingaddress, String invoiceno,
			String ewaybillno, float netAmount, float igst, float grandtotal) {
		super();
		this.id = id;
		this.contactname = contactname;
		this.purchasedate = purchasedate;
		this.shippingaddress = shippingaddress;
		this.invoiceno = invoiceno;
		this.ewaybillno = ewaybillno;
		this.netAmount = netAmount;
		this.igst = igst;
		this.grandtotal = grandtotal;
	}

	public PurchasePageDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
