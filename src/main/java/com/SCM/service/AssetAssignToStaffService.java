package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.dto.AssetAssignToStaffDto;

public interface AssetAssignToStaffService {

	public AssetAssignToStaffDto save(AssetAssignToStaffDto assetAssignToStaffDto);
	public List<AssetAssignToStaffDto> all();
	public Optional<AssetAssignToStaffDto> getById(long id);
	public String updateAsset(AssetAssignToStaffDto assetAssignToStaffDto,long id);
	public  String deleteAsset(long id);
	
	
    
    public Map<String, Object> IndexAssetAssignToStaffAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> IndexAssetAssignToStaffDesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> SearchAssetAssignToStaff(int pageno,int pagesize,String search);
}
