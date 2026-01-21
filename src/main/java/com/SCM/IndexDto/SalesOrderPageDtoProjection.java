package com.SCM.IndexDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public interface SalesOrderPageDtoProjection {

	int getId();
	
	String getSodate();
	
	String getContactname();
	
	float getGrossamount();

	float getGrandtotal();
	
	String getRemarks();
	
	String getTrade_Name();
	
	String getCreateddate();
	
	LocalTime getCreatedtime();
	
	String getRetailerstatus();
	
	String getcreatebyname();
	
	String getupdatedbyname();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();
	
	String getstate();
	
	String getstatus();
	
	Float getIgst();
	
	Float getcgst();
	
	Float getsgst();
	
	String getrsmname();
	
	String getasename();
	
	String getvouchermaster_series();
	
	
	
}
