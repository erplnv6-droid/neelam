package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexMinutesOfMeeting {

	int getid();

	String getmeetingtitle();

	String getmeetingdate();

	String getstaff_name();

	String getcreateddate();

	LocalTime getcreatedtime();

	String getcreatebyname();

	String getupdatedbyname();

	String getupdateddate();

	LocalTime getupdatedtime();
}
