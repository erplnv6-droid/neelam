package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.model.Group5;

public interface Group5Service {

	

	public Group5 save(Group5 Group5);
	public List<Group5> getAll();
	public Optional<Group5> getById(long id);
	public String updateGroup5(long id,Group5 group5);
	public String deleteGroup5(long id);
	
	
	   	public Map<String, Object> IndexGroup5Asc(int pagno,int pagesize,String field);
	    
	    public Map<String, Object> IndexGroup5Desc(int pagno,int pagesize,String field);
	    
	    public Map<String, Object> SearchGroup5(int pageno,int pagesize,String search);
	    
	
}
