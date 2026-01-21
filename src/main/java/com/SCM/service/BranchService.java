package com.SCM.service;

import java.util.List;
import java.util.Map;

import com.SCM.dto.BranchDto;
import com.SCM.model.Branch;

public interface BranchService {

	public Branch savebranch(BranchDto branchDto);
	
	public List<Branch> showbranch();
	
	public Branch branchbyId(int id);
	
	public Branch updatebranch(Branch branch,int id);
	
	void deletebranch(int id);
	
	
    //  index Branch
    
    
    public Map<String, Object> IndexBranchAsc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexBranchDesc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchBranch(int pageno,int pagesize,String search);
	
}
