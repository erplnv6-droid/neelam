package com.SCM.IndexDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface DistributorMinimumStockIndex {

	int getid();

	String getcreatebyname();

	String getupdatedbyname();

	String getCreateddate();

	LocalTime getcreatedtime();

	Float getstockqty();

	String getdmsdate();

	String getproduct_name();

	String gettrade_name();

	String getupdateddate();

	LocalTime getupdatedtime();
}
