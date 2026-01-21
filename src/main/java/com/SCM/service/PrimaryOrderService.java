package com.SCM.service;

import com.SCM.ExportDto.ExportPrimaryWorkOrder;
import com.SCM.dto.PrimaryOrderDto;
import com.SCM.model.PrimaryOrder;
import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.List;
import java.util.Map;


public interface PrimaryOrderService {

    public PrimaryOrder savePrimaryWorkOder(PrimaryOrderDto primaryOrderDto) throws FirebaseMessagingException;
         
    public List<PrimaryOrder> getAllPo();
    
    public List<PrimaryOrder> getAllPo1();
    
    public PrimaryOrderDto getPriamryWorkOderById(int id);
    
    public PrimaryOrderDto updatePrimaryOrder(PrimaryOrderDto primaryOrderDto, int id);
    
    public String deletePrimaryWorkOder(int id);

    
    // ase asm rsm in Primary workorder

    public List<PrimaryOrderDto> getPrimaryOrderByASE(int aseId);

    public List<PrimaryOrderDto> getPrimaryOrderByASM(int asmId);

    public List<PrimaryOrderDto> getPrimaryOrderByRSM(int rsmId);

    public List<PrimaryOrderDto> getPrimaryOrderByNSM(int nsmId);

    public List<PrimaryOrderDto> getPrimaryOrderByZONE(int zonesId);

    public List<PrimaryOrderDto> getPrimaryOrderBySTATE(int stateId);

    public List<PrimaryOrderDto> getPrimaryOrderByDistributor(int distId);

    
    //------ index primary order
    
    public Map<String, Object> IndexPrimaryOrderAsc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexPrimaryOrderDesc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchPrimaryOrder(int pageno,int pagesize,String search);
    
    //--------- export priamry order
    
    public List<ExportPrimaryWorkOrder> exportPo();
    
    
    public Map<String, Object> IndexPrimaryOrderItemsAsc(String startdate,String enddate,int distid,int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexPrimaryOrderItemsDesc(String startdate,String enddate,int distid,int pagno,int pagesize,String field);
    public Map<String, Object> SearchPrimaryOrderItemsDesc(String startdate,String enddate,int distid,int pagno,int pagesize,String search);

}
