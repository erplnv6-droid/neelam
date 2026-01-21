package com.SCM.IndexDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IndexMasterCarton {

	String getpname();

	String getbname();

	Long getId();

	Long getProductid();

	Long getBrandid();

	Long getQty();

	Long getminweight();

	Long getMaxweight();

	Long getHeight();

	Long getWidth();

	String getEancode();

	Long getPcs();

	String getcreateddate();

	LocalTime getCreatedtime();

	String getcreatebyname();

	String getupdatedbyname();

	String getupdateddate();

	LocalTime getupdatedtime();

}
