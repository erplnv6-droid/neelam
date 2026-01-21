package com.SCM.dto;

public class MinimumStockReportDto {
	private int product_id;
	private String product_name;
	private String date;
	private int totalosqty;
	private int msqty;
	private int totalmrniqty;
	private int totalsriqty;
	private int totaldciqty;
	private int totalpriqty;
	private int inwardqty;
	private int outwardqty;
	private int closingstock;
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTotalosqty() {
		return totalosqty;
	}
	public void setTotalosqty(int totalosqty) {
		this.totalosqty = totalosqty;
	}
	public int getMsqty() {
		return msqty;
	}
	public void setMsqty(int msqty) {
		this.msqty = msqty;
	}
	public int getTotalmrniqty() {
		return totalmrniqty;
	}
	public void setTotalmrniqty(int totalmrniqty) {
		this.totalmrniqty = totalmrniqty;
	}
	public int getTotalsriqty() {
		return totalsriqty;
	}
	public void setTotalsriqty(int totalsriqty) {
		this.totalsriqty = totalsriqty;
	}
	public int getTotaldciqty() {
		return totaldciqty;
	}
	public void setTotaldciqty(int totaldciqty) {
		this.totaldciqty = totaldciqty;
	}
	public int getTotalpriqty() {
		return totalpriqty;
	}
	public void setTotalpriqty(int totalpriqty) {
		this.totalpriqty = totalpriqty;
	}
	public int getInwardqty() {
		return inwardqty;
	}
	public void setInwardqty(int inwardqty) {
		this.inwardqty = inwardqty;
	}
	public int getOutwardqty() {
		return outwardqty;
	}
	public void setOutwardqty(int outwardqty) {
		this.outwardqty = outwardqty;
	}
	public int getClosingstock() {
		return closingstock;
	}
	public void setClosingstock(int closingstock) {
		this.closingstock = closingstock;
	}
	@Override
	public String toString() {
		return "MinimumStockReportDto [product_id=" + product_id + ", product_name=" + product_name + ", date=" + date
				+ ", totalosqty=" + totalosqty + ", msqty=" + msqty + ", totalmrniqty=" + totalmrniqty
				+ ", totalsriqty=" + totalsriqty + ", totaldciqty=" + totaldciqty + ", totalpriqty=" + totalpriqty
				+ ", inwardqty=" + inwardqty + ", outwardqty=" + outwardqty + ", closingstock=" + closingstock + "]";
	}
	
}
