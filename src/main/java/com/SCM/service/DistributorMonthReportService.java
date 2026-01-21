package com.SCM.service;

import java.util.Map;

import org.springframework.data.repository.query.Param;

public interface DistributorMonthReportService {

public Map<String, Object> IndexDistributorReportAsc(@Param ("distributor_id") long distributor_id,int pageno,int pagesize,String field);
	
	public Map<String, Object> IndexDistributorReportDSC(@Param ("distributor_id") long distributor_id,int pageno,int pagesize,String field);
}
