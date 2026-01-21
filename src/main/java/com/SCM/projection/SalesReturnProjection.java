package com.SCM.projection;

import java.time.LocalTime;

public interface SalesReturnProjection {

	int getId();

	float getgross_Amount();
	
	String getContactname();

	String getInvoicedate();
	
	String getinvoiceno();

	String getType();

	int getGrandtotal();

	String getdistributorname();

	String getCreateddate();

	LocalTime getCreatedtime();

	String getcreatebyname();

	String getupdatedbyname();

	String getupdateddate();
	
	LocalTime getupdatedtime();
	
	String getstate();
	
	Float getigst();
	
	Float getcgst();
	
	Float getsgst();
	
	String getrsmname();
	
	String getasename();
	
	String getvouchermaster_series();
	
}
