package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.model.Group10;

public interface Group10Service {

	public Group10 save(Group10 Group10);
	public List<Group10> getAll();
	public Optional<Group10> getById(long id);
	public String updateGroup10(long id,Group10 Group10);
	public String deleteGroup10(long id);
	
	
   	public Map<String, Object> IndexGroup10Asc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexGroup10Desc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchGroup10(int pageno,int pagesize,String search);
}
