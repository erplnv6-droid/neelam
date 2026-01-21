package com.SCM.IndexDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IndexDistributor {

	int getId();

	String getTrade_Name();

	String getGst_Number();

	String getTransporter_Name();

	String getState_Name();

	String getZone_name();

	String getCity();

	String getasestaffnames();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();

	LocalTime getCreatedtime();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();
	
	String getlatitude();
	
	String getlongitude();

}
