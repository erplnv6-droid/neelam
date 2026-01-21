package com.SCM.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.IndexDto.SalesExpenseReportDTO;
import com.SCM.dto.SalesExpenseDTO;
import com.SCM.model.SalesExpense;
import com.SCM.model.SalesExpenseImages;

public interface SalesExpenseService {
	

	public SalesExpense save1(SalesExpenseDTO salesExpenseDTO,String salesexpenseitems,MultipartFile[] salesexpfile) throws IOException;
	
//	public SalesExpense update(int id,SalesExpenseDTO salesExpenseDTO,String salesexpenseitems,MultipartFile[] salesexpfile) throws JsonMappingException, JsonProcessingException, IOException;
	
//	public SalesExpense update1(int id,SalesExpenseDTO salesExpenseDTO,String salesexpenseitems,MultipartFile[] salesexpfile) throws IOException;
	
	public ResponseEntity<Object> update1(int id,SalesExpenseDTO salesExpenseDTO,String salesexpenseitems,MultipartFile[] salesexpfile) throws IOException;
	
	public List<SalesExpenseImages> expenseImagesbyitemid(int itemid);
	
//	void deletesalesexp(int id) throws IOException;
	
	ResponseEntity<Object> deletesalesexp(int id) throws IOException;
	
	public SalesExpense get(int id);
	
	public Map<String, Object> IndexSalesExpAsc(int pageno,int pagesize,String field);
	
	public Map<String, Object> IndexSalesExpDesc(int pageno,int pagesize,String field);
	
	public Map<String, Object> SearchSalesExp(int pageno,int pagesize,String search);
	
	
//	--------------------
	

	List<SalesExpenseReportDTO> salesexpreport(int sid,String fromdate,String todate);
	
}
