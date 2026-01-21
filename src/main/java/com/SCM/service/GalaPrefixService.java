package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.dto.GalaPrefixDto;
import com.SCM.model.GalaPrefix;

public interface GalaPrefixService {

	public GalaPrefixDto saveGalaPrefix(GalaPrefixDto dto);
	public List<GalaPrefix> getAllPrefix();
	public Optional<GalaPrefixDto> getById(long id);
	public String updateGala(GalaPrefix dto,long id);
	public String deleteGala(long id);
	

   	public Map<String, Object> IndexGalaPrefix(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexGalaPrefixDesc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchGalaPrefix(int pageno,int pagesize,String search);
    
}
