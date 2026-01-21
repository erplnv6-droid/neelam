package com.SCM.projection;

import java.time.LocalTime;

public interface SalesProjection {

	Integer getId();

	String getContactname();
	
	String getgrandtotal();

	String getGstno();

	String getInvoicedate();

	String getStatus();

	String getnet_amount();

	String getdistributorname();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();

	LocalTime getCreatedtime();

	String getupdateddate();

	LocalTime getupdatedtime();
	
	String getinvoiceno();
	
	String getstate();
	
	String getigst();
	
	String getcgst();
	
	String getsgst();
	
	String getrsmname();
	
	String getasename();
	
	String getvouchermaster_series();
	
	
}
