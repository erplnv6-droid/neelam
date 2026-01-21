package com.SCM.projection;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TemporaryRetailerProjection {

	int getId();
	
	String getTrade_name();
	
	String getTransporter_name();
	
	String getDelivery_location();
	
	String getState();
	
	String getGst_number();
	
	LocalTime getCreatedtime();
	
	String getcreateddate();
	
	String getcreatebyname();
	
	String getupdatedbyname();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();
	
}
