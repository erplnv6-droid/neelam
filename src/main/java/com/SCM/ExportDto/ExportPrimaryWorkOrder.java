package com.SCM.ExportDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public interface ExportPrimaryWorkOrder {

	int getId();

	String getWork_Order_Id();

	String getRemarks();

	Date getWork_Order_Date();

	float getGross_Total();

	float getNet_Value();

	float getTax_Amount();

	String getTrade_Name();
	
	String getState_Name();
	
	String getRsmstaffname();
	
	String getAsestaffname();
	
	Date getDate_Created();
	
	LocalDate getCreateddate();
	
	LocalTime getCreatedtime();
	
	LocalDate getupdateddate();
	
	LocalTime getupdatedtime();
	
	String getcreatebyname();

	String getupdatedbyname();
}
