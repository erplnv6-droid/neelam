package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexPurchaseReutrn {
	int getId();

	String getpurchasereturndate();
	
	String getstatus();

	String getRemarks();

	float getnetamount();
	
	float getgstvalue();

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
	
	float getigst();
	
	float getsgst();
	
	float getcgst();
	
	

}
