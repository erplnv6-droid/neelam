package com.SCM.IndexDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public interface IndexPrimaryOrder {

	int getId();

	String getWork_Order_Id();

	String getRemarks();

	String getWork_Order_Date();

	float getGross_Total();

	float getNet_Value();

	float getTax_Amount();

	String getTrade_Name();

	String getState_Name();

	String getRsmstaffname();

	String getAsestaffname();

	Date getDate_Created();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();

	LocalTime getCreatedtime();

	String getStatus();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();

}
