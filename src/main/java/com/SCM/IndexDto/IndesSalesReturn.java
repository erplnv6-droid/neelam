package com.SCM.IndexDto;

import java.util.Date;

public class IndesSalesReturn {

	private int id;
	
	private String contactname;
	
	private Date invoicedate;
	
	private String type;
	
	private int grandtotal;

	public IndesSalesReturn(int id, String contactname, Date invoicedate, String type, int grandtotal) {
		super();
		this.id = id;
		this.contactname = contactname;
		this.invoicedate = invoicedate;
		this.type = type;
		this.grandtotal = grandtotal;
	}

	public IndesSalesReturn() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public Date getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getGrandtotal() {
		return grandtotal;
	}

	public void setGrandtotal(int grandtotal) {
		this.grandtotal = grandtotal;
	}
	
	
}
