package com.SCM.dtoReports;

import java.util.Date;

public class StockCheckingReports {

	private Date purchasedate;
	private String partno;
	private String product_name;
	private int openingstock_qty;
	private int inward_stock;
	private int outward_stock;
	private int closing_stock;

	public Date getPurchasedate() {
		return purchasedate;
	}

	public void setPurchasedate(Date purchasedate) {
		this.purchasedate = purchasedate;
	}

	public String getPartno() {
		return partno;
	}

	public void setPartno(String partno) {
		this.partno = partno;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getOpeningstock_qty() {
		return openingstock_qty;
	}

	public void setOpeningstock_qty(int openingstock_qty) {
		this.openingstock_qty = openingstock_qty;
	}

	public int getInward_stock() {
		return inward_stock;
	}

	public void setInward_stock(int inward_stock) {
		this.inward_stock = inward_stock;
	}

	public int getOutward_stock() {
		return outward_stock;
	}

	public void setOutward_stock(int outward_stock) {
		this.outward_stock = outward_stock;
	}

	public int getClosing_stock() {
		return closing_stock;
	}

	public void setClosing_stock(int closing_stock) {
		this.closing_stock = closing_stock;
	}

}
