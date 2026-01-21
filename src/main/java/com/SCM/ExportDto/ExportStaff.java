package com.SCM.ExportDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ExportStaff {

	long getId();

	String getDoj();

	String getAddress();

	String getMobile_Number();

	String getEmail();

	String getGender();

	String getSalary();

	String getArea();

	String getDate_Of_Birth();

	String getBlood_Group();

	String getFather_Name();

	String getSpouse_Name();

	String getBank_Detail();

	String getAccount_Number();

	String getIfsc_Code();

	String getBank_Name();

	String getBranch_Name();

	String getPan_Number();

	String getAadhar_Number();

	String getDate_Of_Anniversary();

	String getLocation();

	String getPassword();

	String getState_name();

	String getZone_name();

	String getStaff_name();

	String getASMstaffname();

	String getRSMstaffname();

	String getNSMstaffname();

	LocalDate getCreateddate();
	
	LocalTime getCreatedtime();
	
	LocalDate getupdateddate();
	
	LocalTime getupdatedtime();
	
	String getcreatebyname();

	String getupdatedbyname();
}
