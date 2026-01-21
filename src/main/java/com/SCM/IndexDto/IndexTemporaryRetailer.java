package com.SCM.IndexDto;

public class IndexTemporaryRetailer {

	private int id;
	
	private String trade_name;
	
	private String transporter_name;
	
	private String delivery_location;
	
	private String state;
	
	private String gst_number;

	public IndexTemporaryRetailer(int id, String trade_name, String transporter_name, String delivery_location,
			String state, String gst_number) {
		super();
		this.id = id;
		this.trade_name = trade_name;
		this.transporter_name = transporter_name;
		this.delivery_location = delivery_location;
		this.state = state;
		this.gst_number = gst_number;
	}

	public IndexTemporaryRetailer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTrade_name() {
		return trade_name;
	}

	public void setTrade_name(String trade_name) {
		this.trade_name = trade_name;
	}

	public String getTransporter_name() {
		return transporter_name;
	}

	public void setTransporter_name(String transporter_name) {
		this.transporter_name = transporter_name;
	}

	public String getDelivery_location() {
		return delivery_location;
	}

	public void setDelivery_location(String delivery_location) {
		this.delivery_location = delivery_location;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGst_number() {
		return gst_number;
	}

	public void setGst_number(String gst_number) {
		this.gst_number = gst_number;
	}
	
	
}
