package com.SCM.dto;

import org.springframework.stereotype.Component;

import com.SCM.model.Distributor;

@Component
public class DistributorAddressDto {

	private long id;

	private String delivery_address;
	
    private String state;
	
	private long pincode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDelivery_address() {
		return delivery_address;
	}

	public void setDelivery_address(String delivery_address) {
		this.delivery_address = delivery_address;
	}

	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getPincode() {
		return pincode;
	}

	public void setPincode(long pincode) {
		this.pincode = pincode;
	}

	
	public DistributorAddressDto(long id, String delivery_address, String state, long pincode) {
		super();
		this.id = id;
		this.delivery_address = delivery_address;
		this.state = state;
		this.pincode = pincode;
	}

	public DistributorAddressDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
