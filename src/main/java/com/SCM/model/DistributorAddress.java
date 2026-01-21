package com.SCM.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="distributor_address")
public class DistributorAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column( length = 600)
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

	public DistributorAddress() {
		super();
		// TODO Auto-generated constructor stub
	}




public DistributorAddress(long id, String delivery_address, String state, long pincode) {
		super();
		this.id = id;
		this.delivery_address = delivery_address;
		this.state = state;
		this.pincode = pincode;
	}

@Override
public String toString() {
	return "DistributorAddress [id=" + id + ", delivery_address=" + delivery_address + ", state=" + state + ", pincode="
			+ pincode + "]";
}




	


}
