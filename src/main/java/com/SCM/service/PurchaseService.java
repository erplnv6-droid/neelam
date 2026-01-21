package com.SCM.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.SCM.IndexDto.PurchasePageDtoProjection;
import com.SCM.dto.PurchaseDto;
import com.SCM.model.Purchase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface PurchaseService {

	public Purchase savePurchase(PurchaseDto purchaseDto);
	
	//--- convert po to purchase ----  
	
	public Purchase ConvertPotoPurchase(PurchaseDto purchaseDto,int poId);
	
	//--- convert MaterialReceiptNote to purchase ----  
	
	public Purchase ConvertMRNtoPurchase(PurchaseDto purchaseDto,int mrnId);

	public List<Purchase> getAllPurchase();

	public Purchase getPurchaseById(int id);

	public PurchaseDto updatePurchase(PurchaseDto purchaseDto, int id);

	void deletePurchase(int id);
	
	String deletePurchaseConvert(int id);

	
	
	
    public Map<String, Object> indexPurchaseAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> indexPurchaseDesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> searchByPurchase(int pageno,int pagesize,String search); 
    
    

}
