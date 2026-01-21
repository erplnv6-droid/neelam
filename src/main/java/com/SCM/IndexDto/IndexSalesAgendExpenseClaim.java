package com.SCM.IndexDto;

import java.sql.Date;
import java.time.LocalTime;

import javax.persistence.Column;

public interface IndexSalesAgendExpenseClaim {


	Long getId();
	Date getCreated_On_Date();
	LocalTime getCreated_Time();
	LocalTime getTime();
	Date getDate();
	Long getSales_Staff_Id();
	String getRemark();
	String getStatus();
	String getFilepath();
	String getImagename();
	String getStaff_Name();

	
	
}
