package com.SCM.service;

import com.SCM.dto.SupplierDto;
import com.SCM.model.Supplier;

import java.util.List;
import java.util.Map;

public interface SupplierService {

	public Supplier saveSupplier(SupplierDto supplierDto);

	public List<Supplier> getAllSuppliers();
	
	public List<SupplierDto> getAllSupplierIndex();

	public Supplier getSupplierById(int id);

	public String deleteSupplier(int id);

	public Supplier updateSupplier(SupplierDto supplierDto, int id);
	
	
  //  index Supplier
    
    
    public Map<String, Object> IndexSupplierAsc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexSupplierDesc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchSupplier(int pageno,int pagesize,String search);

}
