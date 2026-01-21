package com.SCM.ReportDto;

public interface DistributorStockReport {

	String getProduct_Name();

//	Integer getdcquantity();
	
//	Integer getoutwarddistributorstockqty();

	Float getinwardqty();

	Float getoutwardqty();

	float getpreviousclosing();

	Integer getclosingstock();

	Integer getamount();
	
	
}
