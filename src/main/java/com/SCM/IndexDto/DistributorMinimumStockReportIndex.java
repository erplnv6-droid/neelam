package com.SCM.IndexDto;

public interface DistributorMinimumStockReportIndex {

	Integer getpid();

	String getProduct_Name();

	Float getoutwarddistributorstockqty();

	Float getminimumstockqty();

	Float getinwardpcsqty();

	Float getinwardkgqty();
	
	Float getoutwardpcsqty();

	Float getoutwardkgqty();
	
	Integer getclosingstock();

	Integer getamount();

	String getclosingpcsstock();
	
	String getclosingkgstock();

}
