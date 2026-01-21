package com.SCM.service;

import com.SCM.model.Brand;
import com.SCM.model.Distributor;

import java.util.List;
import java.util.Map;

public interface BrandService {

    public Brand saveBrand(Brand brand);

    public List<Brand> getAllBrands();

    public Brand getBrandById(long id);

    public String deleteBrand(long id);

    public Brand updateBrand(Brand brand, long id);
  
    
  //  index Brand
    
    
    public Map<String, Object> IndexBrandAsc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexBrandDesc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchBrand(int pageno,int pagesize,String search);
}
