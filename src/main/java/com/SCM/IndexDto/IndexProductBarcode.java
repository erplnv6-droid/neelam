package com.SCM.IndexDto;

import java.sql.Date;
import java.time.LocalTime;

public interface IndexProductBarcode {

	Long getId();

	String getProductname();

	String getSize();

	String getQty();

	String getMrp();

	String getCapacity();

	String getBrandname();

	String getEancode();

	Date getPacked_On();

	String getcreateddate();

	LocalTime getCreatedtime();

	String getcreatebyname();

	String getupdatedbyname();

	String getupdateddate();

	LocalTime getupdatedtime();

}
