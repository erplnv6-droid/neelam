package com.SCM.IndexDto;

import java.sql.Date;
import java.time.LocalTime;

public interface IndexJobworkInward {

	int getId();

	String getJobsheetno();

	String getJobsheetdate();

	String getJobtype();

	String getRemarks();

	float getGrandtotal();

	String getSuppliername();

	String getName();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();

	LocalTime getcreatedtime();

	
	String getupdateddate();
	
	LocalTime getupdatedtime();
}
