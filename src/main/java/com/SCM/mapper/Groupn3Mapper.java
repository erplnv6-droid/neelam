package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

 import com.SCM.dto.Groupn3Dto;
 import com.SCM.model.Groupn3;

@Configuration
public class Groupn3Mapper {


	@Autowired
	private Mapper mapper;
	
	public Groupn3 toEntity(Groupn3Dto dto) {
		return mapper.getModelMapper().map(dto, Groupn3.class);
	}
	
	public Groupn3Dto toDto(Groupn3 groupn3) {
		return mapper.getModelMapper().map(groupn3, Groupn3Dto.class);
	}
}
