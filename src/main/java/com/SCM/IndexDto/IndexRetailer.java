package com.SCM.IndexDto;


import java.time.LocalTime;


public interface IndexRetailer {

	int getid();

	String getretailername();
    
	String getCity();

	String getState_name();

	String getGst_Number();
	
	String getdistributorname();
	
	String getasestaffnames();
	
	Float getBoxProductDiscount();
	
	Float getCookerProductDiscount();
	
	Float getCorporaetProductDiscount();
	
	Float getKgProductDiscount();
	
	Float getNoshProductDiscount();
	
	Float getSchemeDiscount();
	
	Float getSchemeBoxProductDiscount();
	
	Float getSchemeCookerProductDiscount();
	
	Float getSchemeCorporateProductDiscount();
	
	Float getSchemeKgProductDiscount();
	
	Float getSchemeNoshProductDisocunt();
	
	String getPincode();

	String getRetailerstatus();

	String getcreatebyname();
	
	String getupdatedbyname();
	
	String getcreateddate();
	
	LocalTime getcreatedtime();

	String getupdateddate();
	
	LocalTime getupdatedtime();
	
	String getlatitude();
	
	String getlongitude();
}
