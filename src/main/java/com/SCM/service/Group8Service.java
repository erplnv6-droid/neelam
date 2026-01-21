package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.model.Group8;

public interface Group8Service {

	

	public Group8 save(Group8 Group8);
	public List<Group8> getAll();
	public Optional<Group8> getById(long id);
	public String updateGroup8(long id,Group8 Group8);
	public String deleteGroup8(long id);
	
	
   	public Map<String, Object> IndexGroup8Asc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexGroup8Desc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchGroup8(int pageno,int pagesize,String search);
}
