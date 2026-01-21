package com.SCM.IndexDto;

import lombok.Data;

@Data
public class DistributorDetailsByPincode {

	private Double longitude;
	private Double lattitude;
	private String name;
	private String city;
	private String pincode;
	private String billingAddress;
	
}
