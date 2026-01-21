package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.SCM.dto.AssetAssignToStaffDto;
import com.SCM.model.AssetAssignToStaff;

@Configuration
public class AssetAssignToStaffMapper {
	@Autowired
	private Mapper mapper;
	
	public AssetAssignToStaff toEntity(AssetAssignToStaffDto assetAssignToStaffDto) {
		return mapper.getModelMapper().map(assetAssignToStaffDto, AssetAssignToStaff.class);
	}
	
	public AssetAssignToStaffDto toDto(AssetAssignToStaff assetAssignToStaff) {
		return mapper.getModelMapper().map(assetAssignToStaff, AssetAssignToStaffDto.class);
	}
	
}
