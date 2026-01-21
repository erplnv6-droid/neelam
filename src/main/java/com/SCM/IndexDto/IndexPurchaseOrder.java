package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexPurchaseOrder {

	int getId();

	String getPodate();
	
	String getstatus();

	String getRemarks();

	float getNet_Amount();
	
	Float getgstvalue();

	float getGrandtotal();
	
	String getcities();
	
	String getvouchermaster_series();

	String getCompanyname();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();

	LocalTime getCreatedtime();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();
	
	float getigst();
	
	float getsgst();
	
	float getcgst();
	
	

}
