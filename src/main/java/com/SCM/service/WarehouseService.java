package com.SCM.service;

import com.SCM.dto.WarehouseDto;
import com.SCM.model.Distributor;
import com.SCM.model.Warehouse;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;

public interface WarehouseService {

	public Warehouse saveWarehouse(WarehouseDto warehouseDto);

	public List<Warehouse> getAllWarehouses();
	
	//------- get all Product by warehouse id

	public Warehouse getWarehouseById(int id);

	public String deleteWarehouse(int id);

	public Warehouse updateWarehouse(Warehouse warehouse, int id);

	
 //  index warehouse
    
    
    public Map<String, Object> IndexWarehouseAsc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexWarehouseDesc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchWarehouse(int pageno,int pagesize,String search);

}
