package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.SCM.dto.StaffAssetDto;
import com.SCM.model.StaffAsset;

@Configuration
public class StaffAssetMapper {

	
	@Autowired
	private Mapper mapper;
	
	public StaffAsset toEntity(StaffAssetDto staffAssetDto) {
		return	mapper.getModelMapper().map(staffAssetDto, StaffAsset.class);	
	}
	
	public StaffAssetDto toDto(StaffAsset staffAsset) {
		return mapper.getModelMapper().map(staffAsset, StaffAssetDto.class);
	}
	
	
}
