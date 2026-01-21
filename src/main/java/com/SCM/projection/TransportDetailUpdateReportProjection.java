package com.SCM.projection;

import java.sql.Date;

public interface TransportDetailUpdateReportProjection {
	
	Date getVoucherDate();
	
	String getVoucherNumber();
	
	String getPartyName();
	
	String getDestination();
	
	double getAmount();
	
	String getTransporter();
	
	double getTransportCharges();
	
	float getFreightCost();
	
	String getLrNumber();
	
	String getLrnDate();
	
	String getEdd();
	
	String getGrnNumber();
	
	String getGrnDate();
	
	long getEarlyLateDelivery();
	
	String getTotalNoOfPKGS();
	
	

}
