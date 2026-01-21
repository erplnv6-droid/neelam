package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexAsset {

	Long getid();

	String getassetsname();
	
	String getstatus();

	Long getmrp();

	String getsrno();

	String getremarks();

	LocalTime getcreatedtime();

	String getcreatedbyname();

	String getcreateddate();

	String getupdatedbyname();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();

}
