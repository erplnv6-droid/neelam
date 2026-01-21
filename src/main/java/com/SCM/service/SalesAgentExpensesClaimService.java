package com.SCM.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.SalesAgentExpenseClaimDto;

public interface SalesAgentExpensesClaimService {

	public SalesAgentExpenseClaimDto saveAgentExpenseClaimDto(SalesAgentExpenseClaimDto agentExpenseClaimDto,MultipartFile file) throws IOException;

	public Optional<SalesAgentExpenseClaimDto> getById(long id);

	public List<SalesAgentExpenseClaimDto> getAll();

	public String updateSalesAgentExpensesClaimDto(long id, SalesAgentExpenseClaimDto salesAgentExpenseClaimDto) throws IOException;

	public String deleteSalesAgentExpensesClaimDto(long id);

	public String updateStatus(long id, String status);

	public Map<String, Object> ascSalesAgentExpenseClaims(long staffid, int pageno, int pagesize, String field,String sort);

	public Map<String, Object> searchSalesAgentExpenseClaims(long staffid, int pageno, int pagesize, String search);

	public Map<String, Object> ascSalesAgentExpense(int pageno, int pagesize, String field, String sort);

	public Map<String, Object> searchSalesAgentExpense(int pageno, int pagesize, String search);
	
	
	
	public Map<String, Object> ascSalesAgenetExpenseByStaffId(long id,String startDate,String endDate,int pageno,int pagesize,
			String field);
	
	public Map<String, Object> descSalesAgenetExpenseByStaffId(long id,String startDate,String endDate,int pageno,int pagesize,
			String field);
	
	public Map<String, Object> searchSalesAgenetExpenseByStaffId(long id,String startDate,String endDate,int pageno,int pagesize,
			String search);
}
