package com.SCM.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.SCM.IndexDto.PreIndex;
import com.SCM.model.WarehouseUpload;

public interface WarehouseUploadService {

	public void savePrimary(WarehouseUpload warehouseUpload,MultipartFile file) throws IOException;
	
	public void savePrimary1(WarehouseUpload warehouseUpload,MultipartFile file) throws IOException;
	
    public Map<String, Object> IndexWarehouseUploadAsc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexWarehouseUploadDesc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchWarehouseUpload(int pageno,int pagesize,String search);
    
    public List<WarehouseUpload> showWarehouseUpload();
    
    //============ By Warehouse
     
//	public List<PreIndex> getWarehouseUploadById(int wid,String wdate);
//	
//	public Map<String, Object> getWarehouseUploadByIdWithPaginationASC(int pagno,int pagesize,String field);
//    
//	public Map<String, Object> getWarehouseUploadByIdWithPaginationDESC(int pagno,int pagesize,String field);
	
	//================ By Warehouse
	
    public Map<String, Object> IndexWarehouseUploadAscByWarehouseId(int pagno,int pagesize,String field,int wid);
    
    public Map<String, Object> IndexWarehouseUploadDescByWarehouseId(int pagno,int pagesize,String field,int wid);
    
    public Map<String, Object> SearchWarehouseUploadDescByWarehouseId(int pageno,int pagesize,String search,int wid);
}
