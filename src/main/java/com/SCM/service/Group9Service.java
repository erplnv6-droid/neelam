package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.model.Group9;

public interface Group9Service {

	public Group9 save(Group9 Group9);
	public List<Group9> getAll();
	public Optional<Group9> getById(long id);
	public String updateGroup9(long id,Group9 Group9);
	public String deleteGroup9(long id);
	
   	public Map<String, Object> IndexGroup9Asc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexGroup9Desc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchGroup9(int pageno,int pagesize,String search);
}
