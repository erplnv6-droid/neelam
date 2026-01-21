package com.SCM.service;

import com.SCM.IndexDto.IndexOpeningStock;
import com.SCM.IndexDto.IndexOpeningStockDto;
import com.SCM.dto.OpeningStockDto;
import com.SCM.model.Distributor;
import com.SCM.model.OpeningStock;

import java.util.List;
import java.util.Map;

public interface OpeningStockService {

    public OpeningStock saveOpeningStock(OpeningStock openingStock);

    public List<OpeningStockDto> getAllOpeningStock();

    public OpeningStock getOpeningStockById(int id);

    public String deleteOpeningStock(int id);

    public OpeningStock updateOpeningStock(OpeningStock openingStock, int id);

    
    
  //  index os
    
    
    public Map<String, Object> IndexOpeningStockAsc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexOpeningStockDesc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchOpeningStock(int pageno,int pagesize,String search);
}
