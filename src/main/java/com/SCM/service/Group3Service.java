package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.model.Group3;

public interface Group3Service {

	public Group3 save(Group3 Group3);
	public List<Group3> getAll();
	public Optional<Group3> getById(long id);
	public String updateGroup3(long id,Group3 group3);
	public String deleteGroup3(long id);
	

	 public Map<String, Object> IndexGroup3Asc(int pagno,int pagesize,String field);
	    
	    public Map<String, Object> IndexGroup3Desc(int pagno,int pagesize,String field);
	    
	    public Map<String, Object> SearchGroup3(int pageno,int pagesize,String search);
}
