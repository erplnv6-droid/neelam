package com.SCM.IndexDto;

import java.util.Date;

import javax.persistence.ManyToOne;


import com.SCM.model.CustomerSubContacts;

public class IndexSales {

	private int id;
	

	private String contactname;;
	
	private String gstno;
	
	private Date invoicedate;
	
	private String status;
	
	private String netvalue;

	public IndexSales(int id, String contactname, String gstno, Date invoicedate, String status, String netvalue) {
		super();
		this.id = id;
		this.contactname = contactname;
		this.gstno = gstno;
		this.invoicedate = invoicedate;
		this.status = status;
		this.netvalue = netvalue;
	}

	public IndexSales() {
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

	public String getGstno() {
		return gstno;
	}

	public void setGstno(String gstno) {
		this.gstno = gstno;
	}

	public Date getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNetvalue() {
		return netvalue;
	}

	public void setNetvalue(String netvalue) {
		this.netvalue = netvalue;
	}

	
	
	
	
	
}
