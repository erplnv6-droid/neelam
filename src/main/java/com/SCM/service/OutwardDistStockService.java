package com.SCM.service;

import java.util.List;
import java.util.Map;

import com.SCM.IndexDto.OutwardDistributorIndexDto;
import com.SCM.dto.OutwardDistributorStockDto;
import com.SCM.model.OutwardDistributorStock;

public interface OutwardDistStockService {

	public OutwardDistributorStock save(OutwardDistributorStockDto distributorStockDto);
	
	public List<OutwardDistributorStock> getAllOutwardDistributorStock();
	
	public List<OutwardDistributorIndexDto> getAllRetailer(int id);

	public OutwardDistributorStock getOutwardDistributorStockById(int id);

	public OutwardDistributorStock updateOutwardDistributorStock(OutwardDistributorStockDto distributorStockDto, int id);

	void deleteOutwardDistributorStock(int id);
	
	
	
    public Map<String, Object> indexOutwardDistributorStockAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> indexOutwardDistributorStockDesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> searchByOutwardDistributorStock(int pageno,int pagesize,String search); 
    
    
    
    public Map<String, Object> reportOutwardDistributorStockAsc(long did ,int pageno,int pagesize,String field);
    
    public Map<String, Object> reportOutwardDistributorStockDesc(long did ,int pageno,int pagesize,String field);
    
    public Map<String, Object> searchReportByOutwardDistributorStock(long did ,int pageno,int pagesize,String search); 
	
	
//	public Map<String, Object> DistributorStockReport(int pageno, int pagesize, String field,String fromdate,String todate,int distid);
	
}
