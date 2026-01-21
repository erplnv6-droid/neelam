package com.SCM.IndexDto;

import java.time.LocalDate;
import java.time.LocalTime;


public interface IndexMinimumStock {

	int getId();

	String getProduct_Name();

	String getStockdate();

	String getwarehousename();
	
	int getstockqty();
	
	String getCreateddate();
	
	LocalTime getCreatedtime();
	
	String getcreatebyname();
	
	String getupdatedbyname();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();
}
