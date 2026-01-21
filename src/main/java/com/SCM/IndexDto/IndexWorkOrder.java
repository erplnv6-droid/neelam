package com.SCM.IndexDto;

import java.sql.Date;
import java.time.LocalTime;

public interface IndexWorkOrder {

	int getId();

	String getWork_Order_Id();

	String getWork_Order_Date();

	String getTrade_name();

	String getdistname();

	String getState_Name();

	String getRsmstaffname();

	String getAsestaffname();

	java.util.Date getDate_Created();

	float getGross_Total();

	float getNet_Value();

	float getTax_Amount();

	String getRemarks();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();

	LocalTime getCreatedtime();

	String getOrder_status();

	String getConverted_to_po();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();

}
