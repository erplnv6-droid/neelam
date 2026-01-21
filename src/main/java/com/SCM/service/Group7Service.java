package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.model.Group7;

public interface Group7Service {

	

	public Group7 save(Group7 Group7);
	public List<Group7> getAll();
	public Optional<Group7> getById(long id);
	public String updateGroup7(long id,Group7 Group7);
	public String deleteGroup7(long id);
	
   	public Map<String, Object> IndexGroup7Asc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexGroup7Desc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchGroup7(int pageno,int pagesize,String search);
}
