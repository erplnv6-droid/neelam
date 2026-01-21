package com.SCM.dto;

import org.springframework.stereotype.Component;

@Component
public class RetailerTemporaryAddressDto {
	
	
	private long id;
	
	private String deliveryAddress;
	
    private String state;
	
	private long pincode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
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

	

	public RetailerTemporaryAddressDto(long id, String deliveryAddress, String state, long pincode) {
		super();
		this.id = id;
		this.deliveryAddress = deliveryAddress;
		this.state = state;
		this.pincode = pincode;
	}

	public RetailerTemporaryAddressDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
