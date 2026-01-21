package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.SCM.dto.Groupn1Dto;
import com.SCM.model.Groupn1;

@Configuration
public class Groupn1Mapper {

	
	@Autowired
	private Mapper mapper;
	
	public Groupn1 toEntity(Groupn1Dto dto) {
		return mapper.getModelMapper().map(dto, Groupn1.class);
	}
	
	public Groupn1Dto toDto(Groupn1 groupn1) {
		return mapper.getModelMapper().map(groupn1, Groupn1Dto.class);
	}
	
	
	
	
}
