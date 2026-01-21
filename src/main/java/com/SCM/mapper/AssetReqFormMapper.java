package com.SCM.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;

import com.SCM.dto.AssetReqFormDto;
import com.SCM.model.AssetRequestForm;

@Configuration
public class AssetReqFormMapper {

	@Autowired
	private Mapper mapper;
	
	public AssetRequestForm toEntity(AssetReqFormDto assetReqFormDto) {
		return mapper.getModelMapper().map(assetReqFormDto, AssetRequestForm.class);
	}
	
	public AssetReqFormDto toDto(AssetRequestForm assetRequestForm) {
		return mapper.getModelMapper().map(assetRequestForm, AssetReqFormDto.class);
	}
	
}
