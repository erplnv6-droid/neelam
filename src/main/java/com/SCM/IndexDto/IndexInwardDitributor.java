package com.SCM.IndexDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IndexInwardDitributor {

	int getId();

	LocalDate getindate();

	String getdistributorname();

	String getproduct_name();

	Long getinwardqty();

	Double getAmount();

	Double getRate();
	
	String getcreateddate();

	LocalTime getcreatedtime();

	String getcreatebyname();

	String getupdatedbyname();

	String getupdateddate();

	LocalTime getupdatedtime();
	
String getinvoiceno();
	
	String getstate();
	
	Float getgrandtotal();
	
	Float getgrossamount();
	
	Float getigst();
	
	Float getsgst();
	
	Float getcgst();
	
	String getrsmname();
	
	String getasename();
	
	String getvouchermaster_series();
	
	
	
}
