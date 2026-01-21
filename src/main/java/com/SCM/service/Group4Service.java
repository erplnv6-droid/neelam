package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.model.Group4;

public interface Group4Service {

	public Group4 save(Group4 Group4);
	public List<Group4> getAll();
	public Optional<Group4> getById(long id);
	public String updateGroup4(long id,Group4 Group4);
	public String deleteGroup4(long id);
	
	 public Map<String, Object> IndexGroup4Asc(int pagno,int pagesize,String field);
	    
	    public Map<String, Object> IndexGroup4Desc(int pagno,int pagesize,String field);
	    
	    public Map<String, Object> SearchGroup4(int pageno,int pagesize,String search);
}
