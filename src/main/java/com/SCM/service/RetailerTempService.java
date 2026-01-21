package com.SCM.service;

import java.util.List;
import java.util.Map;

import com.SCM.dto.RetailerTempDto;
import com.SCM.model.RetailerTemporary;

import com.SCM.projection.TemporaryRetailerProjection;

public interface RetailerTempService {

	public RetailerTemporary save(RetailerTempDto retailerTempDto);

	public RetailerTemporary save2(RetailerTempDto retailerTempDto);

	public List<RetailerTemporary> getAllTempRetailer();

	public RetailerTemporary getTempRetailerById(int id);

	public String deleteTempRetailer(int id);

	

public Map<String,Object> IndexTemporaryRetailerAsc(int pageno,int pagesize,String field);
	

	
	 public Map<String, Object> SearchTemporaryRetailer(int pageno,int pagesize,String search);
		



	public Map<String, Object> IndexTemporaryRetailerDesc(int pageno, int pagesize, String field);




}
