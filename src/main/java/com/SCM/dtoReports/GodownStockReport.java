package com.SCM.dtoReports;

public interface GodownStockReport {

	String getProduct_Name();
	
	float getpreviousclosingpcsqty();
	
	float getpreviousclosingkgqty();
	
	float getpreviousclosingrate();
	
	float getpreviousclosingamount();
	
	float getinwardpcsqty();
	
	float getinwardkgsqty();
	
	float getinwardrate();
	
	float getinwardamount();
	
	float getoutwardpcsqty();
	
	float getoutwardkgqty();
	
	float getoutwardrate();
	
	float getoutwardamount();
		
	float getclosingpcsqty();
	
	float getclosingkgqty();
	
	float closingrate();
	
	float getclosingamount();
	
}
