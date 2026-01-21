package com.SCM.IndexDto;

import lombok.Data;

@Data
public class RetailerDetailsByPincode {

	private Double longitude;
	private Double lattitude;
	private String name;
	private String city;
	private String pincode;
	private String billingAddress;
	
}
