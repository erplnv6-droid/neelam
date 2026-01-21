package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexSetBarcode {

	Long getId();

	String getEancode();

	String getQuantity();

	Long getBrandid();

	Long getProductid();

	String getcreateddate();

	LocalTime getCreatedtime();

	String getcreatebyname();

	String getupdatedbyname();

	String getupdateddate();

	LocalTime getupdatedtime();
}
