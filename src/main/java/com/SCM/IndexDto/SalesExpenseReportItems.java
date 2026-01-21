package com.SCM.IndexDto;

public interface SalesExpenseReportItems {

	String gettravelfrom();

	String gettravelto();

	String getmodeoftravel();

	double gettravelfare();

	double getotherexpamount();
	
	double getapprovedexpensebyadmin();
	
	double getapprovedexpensebyrsm();
	
	double getsumapprovedexpensebyadmin();
	
	double getsumapprovedexpensebyrsm();

	String getotherexp();
}
