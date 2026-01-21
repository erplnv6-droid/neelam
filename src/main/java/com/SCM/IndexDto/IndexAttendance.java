package com.SCM.IndexDto;

import java.time.LocalDate;
import java.util.Date;


public interface IndexAttendance {

	int getid();

	Date getDayin();

	
	String getcheckin_location_latitude();
	
	String getcheckin_location_longitude();
	
	String getcheckout_location_latitude();
	
	String getcheckout_location_longitude();


	LocalDate getDayintime();

	Date getDayout();

	LocalDate getDayouttime();

	String getStaff_name();
	
	int getSid();
}
