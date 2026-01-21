package com.SCM.IndexDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IndexCartonBarcode {
	String getpname();

	String getbname();

	Long getId();

	Long getProductid();

	Long getBrandid();

	String getEancode();

	Long getPcs();

	String getGalaseriesname();

	Long getStdqty();

	Long getLength();

	Long getWidth();

	Long getHeight();

	String getcreateddate();

	LocalTime getCreatedtime();

	String getcreatebyname();

	String getupdatedbyname();

	String getupdateddate();

	LocalTime getupdatedtime();

}
