package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

 import com.SCM.dto.Groupn5Dto;
 import com.SCM.model.Groupn5;

@Configuration
public class Groupn5Mapper {

	@Autowired
	private Mapper mapper;
	
	public Groupn5 toEntity(Groupn5Dto dto) {
		return mapper.getModelMapper().map(dto, Groupn5.class);
	}
	
	public Groupn5Dto toDto(Groupn5 groupn5) {
		return mapper.getModelMapper().map(groupn5, Groupn5Dto.class);
	}
}
