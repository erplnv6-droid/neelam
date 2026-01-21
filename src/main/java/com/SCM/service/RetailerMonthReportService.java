package com.SCM.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.SCM.projection.RetailerMonthReportProjection;

public interface RetailerMonthReportService {


	public Map<String, Object> IndexRetailerReportAsc(@Param ("retailer_id") long retailer_id,int pageno,int pagesize,String field);
	
	public Map<String, Object> IndexRetailerReportDSC(@Param ("retailer_id") long retailer_id,int pageno,int pagesize,String field);
}

