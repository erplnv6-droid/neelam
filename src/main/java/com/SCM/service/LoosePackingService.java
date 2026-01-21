package com.SCM.service;

import java.util.List;
import java.util.Map;

import com.SCM.IndexDto.IndexLoosePacking;
import com.SCM.dto.LoosepackingDto;
import com.SCM.model.LoosePacking;

public interface LoosePackingService {

	public LoosePacking save(LoosepackingDto loosepackingDto);
	
	public LoosePacking getById(int id);
	
	public List<IndexLoosePacking> FetchProductByBrand(int bid);
	
//	index
	
    public Map<String, Object> IndexLoocsePackingAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> IndexLoosePackingDesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> SearchLoosePacking(int pageno,int pagesize,String search);

}
