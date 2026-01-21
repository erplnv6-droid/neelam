package com.SCM.IndexDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IndexAssetReqForm {

	Long getid();

	Long getstaffid();

	String getstaffname();

	LocalDate getdate();

	String getremarks();

	String getstatus();

	String getrole();
	
	LocalTime getcreatedtime();

	String getcreatebyname();

	String getcreateddate();

	String getupdatedbyname();

	String getupdateddate();

	LocalTime getupdatedtime(); 
	

//	private long id;
//	private long staffid;
//	private String staffname;
//	private LocalDate date;
//	private String remarks;
//	private String status;
//	private String role;

}
