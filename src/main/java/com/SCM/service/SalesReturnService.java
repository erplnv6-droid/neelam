package com.SCM.service;

import java.util.List;
import java.util.Map;

import com.SCM.dto.SalesReturnDto;
import com.SCM.model.SalesReturn;
import com.SCM.projection.SalesProjection;
import com.SCM.projection.SalesReturnProjection;

public interface SalesReturnService {

	public SalesReturn saveSalesReturn(SalesReturnDto salesReturnDto);

	public List<SalesReturn> getAllSalesReturn();

	public SalesReturn getSalesReturnById(int id);

	void deleteSalesReturn(int id);

	public SalesReturnDto updateSalesReturn(SalesReturnDto salesReturnDto, int id);
	
    public Map<String,Object> IndexSalesReturnAsc(int pageno,int pagesize,String field);
	
	public Map<String,Object> IndexSalesReturnDesc(int pageno,int pagesize,String field);
	
	 public Map<String, Object> SearchSalesReturn(int pageno,int pagesize,String search);
}
