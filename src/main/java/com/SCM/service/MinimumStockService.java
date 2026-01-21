package com.SCM.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.SCM.dto.MinimumStockDto;
import com.SCM.dto.MinimumStockReportDto;
import com.SCM.dtoReports.MinimumStockReport;
import com.SCM.model.MinimumStock;
import com.SCM.projection.MinimumStockReportProjection;

public interface MinimumStockService {

	public MinimumStock savedminimumStock(MinimumStockDto minimumStock);
	
	public List<MinimumStock> showminimumStocks();
	
	public MinimumStock showminimumStocksId(int id);

	public MinimumStock updateMinimumStocks(MinimumStock minimumStock, int id);

	void deleteMinimumStock(int id);
	
	//-------- minimum stock service
	
    public Map<String, Object> IndexMinimumStockAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> IndexMinimumStockDesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> SearchMinimumStock(int pageno,int pagesize,String search);
	
	public List<MinimumStockReportProjection> MinimumStockReports(int wid);
	
	public Map<String, Object> ascMinimumStockReport(int wid,int pageno,int pagesize,String field, String sort);
	
	

		
}
