package com.SCM.service;

import java.util.List;
import java.util.Map;

import com.SCM.dto.PurchaseOrderDto;
import com.SCM.model.PurchaseOrder;

public interface POService {
	
	public PurchaseOrder savePO(PurchaseOrderDto purchaseOrderDto);
	
	public List<PurchaseOrder> getAllPO();

	public PurchaseOrder getPOById(int id);

	public PurchaseOrderDto updatePO(PurchaseOrderDto purchaseDto, int id);

	void deletePO(int id);
	
	
  //  index PO
    
    
    public Map<String, Object> IndexPOAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> IndexPODesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> SearchPO(int pageno,int pagesize,String search);
}
