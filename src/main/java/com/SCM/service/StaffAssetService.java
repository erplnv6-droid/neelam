package com.SCM.service;

import java.util.List;
import java.util.Optional;

import com.SCM.dto.StaffAssetDto;

public interface StaffAssetService {

	public StaffAssetDto save(StaffAssetDto staffAssetDto);
	public String updateStaffAsset(long id,StaffAssetDto assetDto);
	public Optional<StaffAssetDto> getById(long id);
	public List<StaffAssetDto> getAll();
	public String deleteStaffAsset(long id);
	
}
