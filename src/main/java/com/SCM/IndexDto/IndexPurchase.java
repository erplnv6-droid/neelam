package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexPurchase {
	int getId();

	String getpurchasedate();
	
	String getstatus();

	String getRemarks();

	Float getnetamount();
	
	Float getgstvalue();

	float getGrandtotal();
	
	Float getgross_amount();
	
	String getcities();
	
	String getvouchermaster_series();

	String getCompanyname();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();

	LocalTime getCreatedtime();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();
	
	String getshippingaddress();
	
	String getcontactname();
	
	String getigst();
	
	String getsgst();
	
	String getcgst();

}
