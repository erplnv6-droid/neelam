package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexContractManagement {

	int getId();
	
	String getfromdate();
	
	String getcontracttype();
	
	String gettodate();
	
	String getremarks();
	
	float gettaxableamount();
	
	float gettaxamount();
	
	float gettotalamount();
	
	String getcompanyname();
	
	String getcreateddate();
	
	LocalTime getcreatedtime();
	
	String getcreatebyname();
	
	String getupdatedbyname();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();
}
