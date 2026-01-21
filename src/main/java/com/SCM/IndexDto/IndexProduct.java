package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexProduct {

	Integer getId();

	String getProduct_Name();
	
	String getproduct_kind();
	
	Double getcostprice();

	String getean_Code();

	String getStandard_Qty_Per_Box();

	String getMrp();

	String getProduct_Group();
	
	String getCapacity();
	
	String getDiameter();
	
	Float  getDlp();
	
	String getShort_Name();
	
	String getCreateddate();
	
	LocalTime getCreatedtime();
	
	String getuom_secondary();
	
	String getuom_primary();
	
	String getproduct_type();

}
