package com.SCM.dtoReports;

public interface ContractMgmtReport {

	int getId();

	String getcompanyname();

	String getcontracttype();
	
	String getapprovaldate();
	
	String getgstno();
	
	String getfromdate();
	
	String gettodate();
	
	float gettaxableamount();
	
	float gettaxamount();
	
	float gettotalamount();
	
	String getremarks();
	
	int getremaningdays();
}
