package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexSupplierDelivery {

	int getId();

	String getSdndate();
	
	String getstatus();

	String getCompanyname();

	float getGrossamount();
	
	String getcities();
	
	String getvouchermaster_series();
	
	float getgstvalue();

	float getGrandtotal();

	String getRemarks();

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
