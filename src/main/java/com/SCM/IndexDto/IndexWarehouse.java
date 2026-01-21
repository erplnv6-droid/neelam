package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexWarehouse {

	int getId();

	String getName();

	String getLocation();

	String getType();
	
	String getCreateddate();
	
	LocalTime getCreatedtime();
	
	String getcreatebyname();
	
	String getupdatedbyname();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();
	
}
