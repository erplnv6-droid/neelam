package com.SCM.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.ContractManagementDto;
import com.SCM.model.ContractManagement;

public interface ContractManagementService {

	public ContractManagement saveCM(ContractManagementDto contractManagementDto,MultipartFile pdffile) throws IOException;

    public List<ContractManagement> getAllCM();

    public ContractManagement getCMById(int id);

    public String deleteCM(int id);

    public ContractManagement updateCM(ContractManagementDto contractManagementDto,MultipartFile pdffile,int id) throws IOException;
    
    
    
    public Map<String, Object> IndexContractManagementASC(int pageno,int pagesize,String field);
    
    public Map<String, Object> IndexContractManagementDESC(int pageno,int pagesize,String field);
    
    public Map<String, Object> SearchContractManagement(int pageno,int pagesize,String field);
    
    
    public Map<String, Object> reportContractMgmtAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> reportContractmgmtDesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> searchReportByContractMgmt(int pageno,int pagesize,String search); 
    
    
    public List<com.SCM.dtoReports.ContractMgmtReport> ContractMgmtReport();
}
