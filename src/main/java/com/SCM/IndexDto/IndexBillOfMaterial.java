package com.SCM.IndexDto;

import java.time.LocalTime;


public interface IndexBillOfMaterial {

	Integer getId();

	String getBom_No();

	Float getRate();

	Float getAmount();

	String getDescription();

	Long getProduct_Id();

	String getProductname();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();
	
	LocalTime getcreatedtime();
}
