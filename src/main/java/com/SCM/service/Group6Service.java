package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.model.Group6;

public interface Group6Service {

	public Group6 save(Group6 Group6);
	public List<Group6> getAll();
	public Optional<Group6> getById(long id);
	public String updateGroup6(long id,Group6 Group6);
	public String deleteGroup6(long id);

	
   	public Map<String, Object> IndexGroup6Asc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexGroup6Desc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchGroup6(int pageno,int pagesize,String search);
	
}
