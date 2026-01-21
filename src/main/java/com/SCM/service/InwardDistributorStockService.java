package com.SCM.service;

import java.util.List;
import java.util.Map;

import com.SCM.dto.InwardDistributorStockDto;
import com.SCM.model.InwardDistributorStock;

public interface InwardDistributorStockService {

	public InwardDistributorStock save(InwardDistributorStockDto inwardDistributorStockDto);
	
	public List<InwardDistributorStock> getAll();

	public InwardDistributorStock getById(int id);

	public InwardDistributorStock update(InwardDistributorStockDto inwardDistributorStockDto, int id);

	void deleteInwardDistributor(int id);
	
	
	
    public Map<String, Object> indexInwardDistributorStockAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> indexInwardDistributorStockDesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> searchByInwardDistributorStock(int pageno,int pagesize,String search); 
}
