package com.SCM.GstDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EwayItem {

	private String productName;
	
	private String productDesc;
	
	private double hsnCode;
	
	private double quantity;
	
	private String qtyUnit;
	
	private double cgstRate;
	
	private double sgstRate;
	
	private double igstRate;
	
	private double cessRate;
	
	private double cessNonadvol;
	
	private double taxableAmount;
	
	
}
