package com.SCM.dto;


public class OpeningStockResponse {

	private int id;
	private int qty;
	private String date;
	private String name;
	private String wname;

//    private String date;

	public OpeningStockResponse(int id, int qty, String date, String name, String wname) {
		this.id = id;
		this.qty = qty;
		this.date = date;
		this.name = name;
		this.wname = wname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWname() {
		return wname;
	}

	public void setWname(String wname) {
		this.wname = wname;
	}

	@Override
	public String toString() {
		return "OpeningStockResponse{" + "id=" + id + ", qty='" + qty + '\'' + ", date='" + date + '\'' + ", name='"
				+ name + '\'' + ", wname='" + wname + '\'' + '}';
	}
}
