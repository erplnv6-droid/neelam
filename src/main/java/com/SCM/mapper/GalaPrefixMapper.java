package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SCM.dto.GalaPrefixDto;
import com.SCM.model.GalaPrefix;

@Component
public class GalaPrefixMapper {

	@Autowired
	private Mapper mapper;
	public GalaPrefix toEntity(GalaPrefixDto dto) {
		return mapper.getModelMapper().map(dto, GalaPrefix.class);
	}
	
	public GalaPrefixDto toDto(GalaPrefix gala) {
		return mapper.getModelMapper().map(gala, GalaPrefixDto.class);
	}
}
