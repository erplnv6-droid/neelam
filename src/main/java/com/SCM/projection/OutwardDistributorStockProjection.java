package com.SCM.projection;

import java.time.LocalDate;
import java.time.LocalTime;

public interface OutwardDistributorStockProjection {

	Long getId();

	String getOutwardDate();
	
	String getinvoiceno();

	String getDistributor();

	String getProduct();

	Long getQuantity();

	Double getAmount();

	Double getRate();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();

	LocalTime getCreatedtime();
	
	String getupdateddate();
	
	LocalTime getupdatedtime();
	
	Float getgrandtotal();
	
	Float getgrossamount();
	
	Float getigst();
	
	Float getsgst();
	
	Float getcgst();
	
	String getrsmname();
	
	String getasename();
	
	String getvouchermaster_series();
	
	

}
