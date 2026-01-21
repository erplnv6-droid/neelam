package com.SCM.service;

import java.util.List;
import java.util.Map;

import com.SCM.ExportDto.ExportRetailer;
import com.SCM.IndexDto.RetailerDetailsByPincode;
import com.SCM.IndexDto.RetailerToStaffDto;
import com.SCM.dto.RetailerDto;
import com.SCM.model.Retailer;

public interface RetailerService {

    public Retailer saveRetailer(RetailerDto retailerDto);
    
    public Retailer registerRetailer(RetailerDto retailerDto);
    
    public Retailer ConvertToRetailer(RetailerDto retailerDto);

    public List<Retailer> getAllWorkOrderWithRetailer();
    
    public List<Retailer> getAllRetailer();
    
    public List<Retailer> getAllRet();
    
    public List<Retailer> getAllRetail();

    public List<RetailerDto> getAllRetailerDto();

    public RetailerDto getRetailerById(int id);

    public String deleteRetailer(int id);

//    public Retailer updateRetailer(Retailer retailer, int id);
    
    public Retailer updateRetailer(RetailerDto retailerDto, int id); // new update

    public Retailer updateRetailer1(RetailerDto retailerDto, int id); // update for mobile app except zone,state,staff
    
    public List<Retailer> getWorkOrdersbyId(int d_id, String from_date, String to_date);


    // ase asm rsm nsm id in Retailer

    public List<RetailerDto> getRetailerByASE(int aseId);

    public List<RetailerDto> getRetailerByASM(int asmId);

    public List<RetailerDto> getRetailerByRSM(int rsmId);
    
    public List<RetailerDto> getRetailerByRSM1(int rsmId);

    public List<RetailerDto> getRetailerByNSM(int nsmId);

    public List<RetailerDto> getRetailerByZONE(int zonesId);

    public List<RetailerDto> getRetailerBySTATE(int stateId);
    
    public List<RetailerDto> getRetailerByRETAILER(int retId);
    
    public List<RetailerDto> getRetailerByDISTRIBUTOR(int distId);
    
  
    //  index Retailer
    
    public Map<String, Object> IndexRetailerAsc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexRetailerDesc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchRetailer(int pageno,int pagesize,String search);
    
    public Map<String, Object> IndexAllRetailerAsc(int pageno,int pagesize,String field);
   
    public Map<String, Object> IndexAllRetailerDsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> SearchAllRetailer(int pageno,int pagesize,String search);
    
    // export retailer
    
    public List<ExportRetailer> exportRetailer();
    
    

    
    public List<RetailerDetailsByPincode> retailerbypincode(String pincode);
    
    public List<com.SCM.IndexDto.RetailerWithRetailerStaffDto> getretailertostaffbyId(int ret_id);
    
    public List<RetailerToStaffDto> getRetailerTostaff(int id);
    
//    ------ projection 
    
    public Map<String,Object> findRetailerWhereGroupnIsAsc(int pagno,int pagesize,String field,String search1,String search2,String search3,String search4,String search5,int count);
    
    public Map<String,Object> findRetailerWhereGroupnIsDesc(int pagno,int pagesize,String field,String search1,String search2,String search3,String search4,String search5,int count);
    
    public Map<String,Object> findRetailerAndDistributorWhereGroupnIsAsc(int pagno,int pagesize,String field,String search1,String search2,String search3,String search4,String search5,int count);
    
    public Map<String,Object> findRetailerAndDistributorWhereGroupnIsDesc(int pagno,int pagesize,String field,String search1,String search2,String search3,String search4,String search5,int count);

    
}
