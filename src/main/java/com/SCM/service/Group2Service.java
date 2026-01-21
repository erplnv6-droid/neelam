package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.model.Group2;



public interface Group2Service {

	public Group2 save(Group2 Group2);
	public List<Group2> getAll();
	public Optional<Group2> getById(long id);
	public String updateGroup2(long id,Group2 group2);
	public String deleteGroup2(long id);
	
	 public Map<String, Object> IndexGroup2Asc(int pagno,int pagesize,String field);
	    
	    public Map<String, Object> IndexGroup2Desc(int pagno,int pagesize,String field);
	    
	    public Map<String, Object> SearchGroup2(int pageno,int pagesize,String search);
}
