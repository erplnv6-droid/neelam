package com.SCM.service;

import java.util.List;
import java.util.Map;

import com.SCM.dto.BillOfMaterialDto;
import com.SCM.model.BillOfMaterial;

public interface BillOfMaterialsService {
	
	public BillOfMaterialDto saveBom(BillOfMaterialDto bomDto);

	public BillOfMaterial getById1(int id);

	public BillOfMaterialDto getById(int id);
	
	public BillOfMaterial updateBom(BillOfMaterialDto bomDto,int id);

	public String deleteById(int id);

	public List<BillOfMaterialDto> getBom(int pageno, int pagesize, String field);

	public Map<String, Object> IndexBillOfMaterialAsc(int pageno, int pagesize, String field);

	public Map<String, Object> IndexBillOfMaterialDesc(int pageno, int pagesize, String field);

	public Map<String, Object> SearchBillOfMaterial(int pageno, int pagesize, String search);

}
