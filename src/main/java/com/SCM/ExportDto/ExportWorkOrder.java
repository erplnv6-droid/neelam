package com.SCM.ExportDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public interface ExportWorkOrder {

	int getId();

	String getWork_Order_Id();
	
	Date getWork_Order_Date();
	
	String gettrade_name();
	
	String getdistname();
	
	String getState_Name();
	
	String getRsmstaffname();
	
	String getAsestaffname();

	java.util.Date getDate_Created();

	float getGross_Total();

	float getNet_Value();

	float getTax_Amount();
	
	String getRemarks();
	
	LocalDate getCreateddate();
	
	LocalTime getCreatedtime();
	
	LocalDate getupdateddate();
	
	LocalTime getupdatedtime();
	
	String getcreatebyname();

	String getupdatedbyname();

}
