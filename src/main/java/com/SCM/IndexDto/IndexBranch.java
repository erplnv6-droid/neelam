package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexBranch {

	int getId();

	String getBranchname();

	String getEmail();

	String getgstno();

	String getAddress();

	String getState_name();

	String getCreateddate();

	LocalTime getCreatedtime();

	String getcreatebyname();

	String getupdatedbyname();

	String getupdateddate();

	LocalTime getupdatedtime();
}
