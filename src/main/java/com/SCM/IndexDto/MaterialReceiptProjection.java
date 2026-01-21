package com.SCM.IndexDto;

import java.time.LocalTime;

public interface MaterialReceiptProjection {

	int getId();

	String getMrndate();
	
	String getstatus();

	String getcities();
	
	String getCompanyname();
	
	float getgstvalue();

	float getGrossamount();

	float getIgst();

	float getGrandtotal();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();

	LocalTime getCreatedtime();

	String getupdateddate();
	
	LocalTime getupdatedtime();
}
