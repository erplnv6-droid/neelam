package com.SCM.service;

import java.util.Map;

import com.SCM.dto.RepackingDto;
import com.SCM.model.Repacking;

public interface RepackingService {

	public Repacking save(RepackingDto repackingDto);
	
	
//	index
	
    public Map<String, Object> IndexRepackingAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> IndexRepackingDesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> SearchRepacking(int pageno,int pagesize,String search);
}
