package com.SCM.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="distributor_sales_return")
public class DistributorSalesReturn {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String salesreturnnumber;
	
	private Date salesreturndate;
	
	private String originalinvoiceno;
	
	private Date originalinvoicedate;
	
	private String termsofpayment;
	
	private String otherreference;
	
	private String dispatchdocumentnumber;
	
	private String dispatchthrough;
	
	private String destination;
	
	private String termsofdelivery;
	
@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "distributorsrid")
	private List<DistributorSalesReturnItems> distributorSalesReturnItems = new ArrayList<>();

public DistributorSalesReturn(long id, String salesreturnnumber, Date salesreturndate, String originalinvoiceno,
		Date originalinvoicedate, String termsofpayment, String otherreference, String dispatchdocumentnumber,
		String dispatchthrough, String destination, String termsofdelivery,
		List<DistributorSalesReturnItems> distributorSalesReturnItems) {
	super();
	this.id = id;
	this.salesreturnnumber = salesreturnnumber;
	this.salesreturndate = salesreturndate;
	this.originalinvoiceno = originalinvoiceno;
	this.originalinvoicedate = originalinvoicedate;
	this.termsofpayment = termsofpayment;
	this.otherreference = otherreference;
	this.dispatchdocumentnumber = dispatchdocumentnumber;
	this.dispatchthrough = dispatchthrough;
	this.destination = destination;
	this.termsofdelivery = termsofdelivery;
	this.distributorSalesReturnItems = distributorSalesReturnItems;
}

public DistributorSalesReturn() {
	super();
	// TODO Auto-generated constructor stub
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getSalesreturnnumber() {
	return salesreturnnumber;
}

public void setSalesreturnnumber(String salesreturnnumber) {
	this.salesreturnnumber = salesreturnnumber;
}

public Date getSalesreturndate() {
	return salesreturndate;
}

public void setSalesreturndate(Date salesreturndate) {
	this.salesreturndate = salesreturndate;
}

public String getOriginalinvoiceno() {
	return originalinvoiceno;
}

public void setOriginalinvoiceno(String originalinvoiceno) {
	this.originalinvoiceno = originalinvoiceno;
}

public Date getOriginalinvoicedate() {
	return originalinvoicedate;
}

public void setOriginalinvoicedate(Date originalinvoicedate) {
	this.originalinvoicedate = originalinvoicedate;
}

public String getTermsofpayment() {
	return termsofpayment;
}

public void setTermsofpayment(String termsofpayment) {
	this.termsofpayment = termsofpayment;
}

public String getOtherreference() {
	return otherreference;
}

public void setOtherreference(String otherreference) {
	this.otherreference = otherreference;
}

public String getDispatchdocumentnumber() {
	return dispatchdocumentnumber;
}

public void setDispatchdocumentnumber(String dispatchdocumentnumber) {
	this.dispatchdocumentnumber = dispatchdocumentnumber;
}

public String getDispatchthrough() {
	return dispatchthrough;
}

public void setDispatchthrough(String dispatchthrough) {
	this.dispatchthrough = dispatchthrough;
}

public String getDestination() {
	return destination;
}

public void setDestination(String destination) {
	this.destination = destination;
}

public String getTermsofdelivery() {
	return termsofdelivery;
}

public void setTermsofdelivery(String termsofdelivery) {
	this.termsofdelivery = termsofdelivery;
}

public List<DistributorSalesReturnItems> getDistributorSalesReturnItems() {
	return distributorSalesReturnItems;
}

public void setDistributorSalesReturnItems(List<DistributorSalesReturnItems> distributorSalesReturnItems) {
	this.distributorSalesReturnItems = distributorSalesReturnItems;
}
	
	
	
	
	
	
	
	
	

}
