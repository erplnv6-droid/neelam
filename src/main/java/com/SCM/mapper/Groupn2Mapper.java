package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

 import com.SCM.dto.Groupn2Dto;
 import com.SCM.model.Groupn2;

@Configuration
public class Groupn2Mapper {

	@Autowired
	private Mapper mapper;
	
	public Groupn2 toEntity(Groupn2Dto dto) {
		return mapper.getModelMapper().map(dto, Groupn2.class);
	}
	
	public Groupn2Dto toDto(Groupn2 groupn2) {
		return mapper.getModelMapper().map(groupn2, Groupn2Dto.class);
	}
}
