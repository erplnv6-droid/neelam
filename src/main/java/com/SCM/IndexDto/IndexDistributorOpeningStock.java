package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexDistributorOpeningStock {

	int getid();

	String getDistname();

	Integer getQuantity();

	String getpname();

	String getPeancode();
	
	String getcreateddate();
	
	LocalTime getcreatedtime();
	
	String getcreatebyname();
	
	String getupdatedbyname();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();
	
	String getuser_date();
	
	
}
//http://192.168.1.35:8081/api/distributor/openingstock/page/0/2/asc/quantity/mumbai