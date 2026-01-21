package com.SCM.IndexDto;

import java.time.LocalTime;

public interface PurchaseReturnPageDtoProjection {

	int getId();

	String getcities();
	
	String getCompanyname();
	
	String getvoucherseries();
	
	float getgstvalue();
	
	String getpurchasereturndate();

	float getNetAmount();

	int getIgst();

	float getGrandtotal();

	String getRemarks();

	String getShippingaddress();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();

	LocalTime getCreatedtime();

	String getupdateddate();

	LocalTime getupdatedtime();
}
