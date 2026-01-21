package com.SCM.IndexDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IndexSupplier {

	Integer getId();

	String getSuppliername();

	String getCompanyname();

	String getCities();

	String getGstno();

	String getState_name();

	String getCreateddate();

	LocalTime getCreatedtime();

	String getcreatebyname();

	String getupdatedbyname();
	

	String getupdateddate();
	
	LocalTime getupdatedtime();
}
