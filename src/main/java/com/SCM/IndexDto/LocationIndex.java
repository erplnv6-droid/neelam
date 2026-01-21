package com.SCM.IndexDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface LocationIndex {

	int getid();
	
	String getcheckin_location();
	
	String getcheckin_location_date();
	
	String getcheckin_location_latitude();
	
	String getcheckin_location_longitude();
	
	LocalTime getcheckin_location_time();
	
	String getcheckout_location();
	
	String getcheckout_location_date();
	
	String getcheckout_location_latitude();
	
	String getcheckout_location_longitude();
	
	LocalTime getcheckout_location_time();
	
	LocalDate getcreateddate();
	
	LocalTime getcreatedtime();
	
	String getrname();
	
	String getdname();
}
