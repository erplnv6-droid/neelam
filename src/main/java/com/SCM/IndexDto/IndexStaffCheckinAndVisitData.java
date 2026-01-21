package com.SCM.IndexDto;

import java.util.List;

import com.SCM.dto.VisitData;

public interface IndexStaffCheckinAndVisitData {

	int getstaffid();
	
	String getstaff_name();
	
	String getdayintime();
	
	List<VisitData> getvisitDatas();
	
}
