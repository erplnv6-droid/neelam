package com.SCM.IndexDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public interface PurchasePageDtoProjection {

	int getId();

	String getcompanyname();
	
	String getcities();
	
	String getPurchasedate();
	
	Float getgstvalue();

	String getShippingaddress();

	String getInvoiceno();

	String getEwaybillno();

	float getNetAmount();

	float getIgst();

	float getGrandtotal();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();

	LocalTime getCreatedtime();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();
	
	String getStatus();
	
	String getContactname();

}
