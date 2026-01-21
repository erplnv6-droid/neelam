package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.model.Group1;

public interface Group1Service {

	public Group1 save(Group1 group1);
	public List<Group1> getAll();
	public Optional<Group1> getById(long id);
	public String updateGroup1(long id,Group1 group1);
	public String deleteGroup1(long id);
	
	 public Map<String, Object> IndexGroup1Asc(int pagno,int pagesize,String field);
	    
	    public Map<String, Object> IndexGroup1Desc(int pagno,int pagesize,String field);
	    
	    public Map<String, Object> SearchGroup1(int pageno,int pagesize,String search);
	
}
