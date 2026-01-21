package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexInvoiceUpload {

	int getid();

	float getamount();

	String gettrade_name();

	String getvoucherno();

	String getvoucherdate();
	
	String getimglocation();
	
	String getpdflocation();
	
	String getcreateddate();

	LocalTime getCreatedtime();
	
	String getcreatebyname();
	
	String getupdatedbyname();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();
}
