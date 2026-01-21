package com.SCM.IndexDto;

import java.time.LocalTime;


public interface IndexBrand {

	Long getid();

	String getbrand_Name();

	String getcreateddate();

	LocalTime getCreatedtime();
	
	String getcreatebyname();
	
	String getupdatedbyname();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();

}
