package com.SCM.IndexDto;

import java.time.LocalTime;

public interface IndexAssetAssignToStaff {

	Long getid();

	String getstaff_name();
	
    String getAssetsname();
    
    String getexpiry_date();
    
    String getinsuranceprovidername();
    
    String getsuppliername();

	String getremarks();
	
	String getcompanyname();

//	Long getasset();

	LocalTime getcreatedtime();

	String getcreatebyname();

	String getcreateddate();

	String getupdatedbyname();

	String getupdateddate();

	LocalTime getupdatedtime();

}
