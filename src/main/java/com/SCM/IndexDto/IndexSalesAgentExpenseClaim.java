package com.SCM.IndexDto;

import java.sql.Date;
import java.time.LocalTime;

public interface IndexSalesAgentExpenseClaim {

	Date getCreatedon();
	String getRemarks();
	String getStatus();
	LocalTime getCreatedtime();
	Long getStaffid();
	String getStaffname();
}
