package com.SCM.projection;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public interface DeliveryChallan {

	Integer getId();

	String getContactname();

	String getDcdate();
	
	String getdeliverynotno();

	Float getgrandtotal();

	Float getgross_amount();

	String getdistributorname();

	Float getTaxvalue();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();

	LocalTime getCreatedtime();

	String getupdateddate();
	
	LocalTime getupdatedtime();
	
    String getstate();
	
	Float getigst();
	
	Float getcgst();
	
	Float getsgst();
	
	String getrsmname();
	
	String getasename();
	
	String getstatus();
	
	String getvouchermaster_series();
}
