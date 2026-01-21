package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.SCM.dto.AssetDto;
import com.SCM.model.Asset;

@Configuration
public class AssetMapper {

	@Autowired
	private Mapper mapper;
	
	
	public Asset toEntiy(AssetDto assetDto) {
		return mapper.getModelMapper().map(assetDto, Asset.class);
	}
	
	public AssetDto toDto(Asset asset) {
		return mapper.getModelMapper().map(asset, AssetDto.class);
	}
	
	
	
}
