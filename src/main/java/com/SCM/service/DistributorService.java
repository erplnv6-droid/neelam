package com.SCM.service;

import com.SCM.IndexDto.DistDto;
import com.SCM.IndexDto.DistributorDetailsByPincode;
import com.SCM.IndexDto.DistributorToStaffDto;
import com.SCM.IndexDto.RetailerToStaffDto;
import com.SCM.dto.DistributorDto;
import com.SCM.model.Distributor;
import java.util.List;
import java.util.Map;

public interface DistributorService {
	
    public Distributor saveDistributor(DistributorDto distributorDto);
    
    public List<DistributorDto> getAllDistributor();
    
    public List<Distributor> getAllDist();
    
    public List<Distributor> getAllDistByJdbcTemp();
    
    public Distributor getDistributorById(int id);
    
    public String deleteDistributor(int id);
    
    public Distributor updateDistributor(Distributor distributor, int id);
    
    public Distributor updateDistributor(DistributorDto distributorDto, int id); // update distributor 
    
    public Distributor updateDistributor1(DistributorDto distributorDto, int id); // update distributor except (staff zone state) for mobile app
    
    public List<Distributor> getprimaryorderbyid(int d_id, String from_date, String to_date);


    // ase asm rsm nsm id

    public List<DistributorDto> getDistributorByASE(int aseId);

    public List<DistributorDto> getDistributorByASM(int asmId);

    public List<DistributorDto> getDistributorByRSM(int rsmId);

    public List<DistributorDto> getDistributorByNSM(int nsmId);

    public List<DistributorDto> getDistributorByZONE(int zonesId);

    public List<DistributorDto> getDistributorBySTATE(int stateId);
    
    public List<DistributorDto> getDistributorBySingleDist(int id);
    
    
    //  index Distributor
    
    
    public Map<String, Object> IndexDistributorAsc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexDistributorDesc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchDistributor(int pageno,int pagesize,String search);

    
    public Map<String, Object> IndexAllDistributorAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> IndexAllDistributorDesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> SearchAllDistributor(int pageno,int pagesize,String search);
    
    
    //------- export excel from distributor
    
    public  List<com.SCM.ExportDto.ExportDistributor> ExportDistributor();
    
    //only distributor can change the location of retailer
    
    public String updateRetailerLocation(int id,Double Lattitude,Double Longitude );
    
    //only staff can change the location of distributor
    
    public String updateDistributorLocation(int id,Double Lattitude,Double Longitude);
    
    public List<DistributorDetailsByPincode> findByPincode(String pincode);
    
    
//     fetch distributor for retailer
    
    public List<DistDto> FetchDistributor();
    
    public List<DistributorToStaffDto> getDistributorTostaff(int id);
    
    public Map<String,Object> findDistributorWhereGroupnIsAsc(int pagno,int pagesize,String field,String search1,String search2,String search3,String search4,String search5,int count);
    
    public Map<String,Object> findDistributorWhereGroupnIsDesc(int pagno,int pagesize,String field,String search1,String search2,String search3,String search4,String search5,int count);

}
