package com.SCM.dtoReports;


public interface PendingPoVsGodownReports {
	
	Integer getwarehouseid();
	
	String getProduct_Name();

	String getEan_Code();

	float getprimaryorderpcsqty();

	float getprimaryorderkgqty();
	
	float getrate();
	
	float getpcsamount();
	
	float getkgamount();
	
	float getclosingpcsqty();

	float getclosingkgqty();

	float gettotalpcsclosingstock();

	float gettotalkgclosingstock();

	float gettotalpcsstock();

	float gettotalkgstock();
	
	String getwarehousename();
	

}
