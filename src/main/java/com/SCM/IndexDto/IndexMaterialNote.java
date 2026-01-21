package com.SCM.IndexDto;

import java.time.LocalTime;
import java.util.Date;

public interface IndexMaterialNote {

	int getId();

	String getmrndate();
	
	String getstatus();

	String getRemarks();

	Float getNet_Amount();
	
	Float getgrossamount();
	
	Float getgstvalue();

	Float getGrandtotal();
	
	String getcities();
	
	String getvouchermaster_series();

	String getCompanyname();

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
