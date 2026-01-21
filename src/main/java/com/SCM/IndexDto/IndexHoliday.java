package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexHoliday {

	Integer getid();

	java.sql.Date getdate();

	String getOccasion();
	
	String getcreateddate();

	LocalTime getCreatedtime();

	String getcreatebyname();

	String getupdatedbyname();

	String getupdateddate();

	LocalTime getupdatedtime();

}
