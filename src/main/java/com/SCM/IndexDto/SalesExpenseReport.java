package com.SCM.IndexDto;

import java.time.LocalDate;

public interface SalesExpenseReport {

	int getId();
	
	String getstaff_name();

	String gethometown();

	String getexpdate();
	
	String getdesignation();
	
	double getdailyallownces();
	
	double gettotalexp();
	
	String getapprovalbyadmin();
	
	String getapprovedexpensebyadmin();
	
	String getapprovedexpensebyrsm();

	Double getcountvisit();
	
	Double getsecondaryordervalue();
	
	String getotherexpamounttotal();
	
	String getStatus();
	
	Double gettotalcountvisit();
	
	Double gettotalsecondaryvalue();
	
	String getdaytotalexpense();
	

	
}
