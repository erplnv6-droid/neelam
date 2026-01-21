package com.SCM.dtoReports;

import java.util.Date;

public class PurchaseProductWiseReports {

	private Date purchasedate;
	private String companyname;
	private String productName;
	private int purquantity;
	private int rate;
	private int total;

	public Date getPurchasedate() {
		return purchasedate;
	}

	public void setPurchasedate(Date purchasedate) {
		this.purchasedate = purchasedate;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPurquantity() {
		return purquantity;
	}

	public void setPurquantity(int purquantity) {
		this.purquantity = purquantity;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
