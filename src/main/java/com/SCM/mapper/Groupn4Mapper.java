package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

  import com.SCM.dto.Groupn4Dto;
 import com.SCM.model.Groupn4;

@Configuration
public class Groupn4Mapper {

	

	@Autowired
	private Mapper mapper;
	
	public Groupn4 toEntity(Groupn4Dto dto) {
		return mapper.getModelMapper().map(dto, Groupn4.class);
	}
	
	public Groupn4Dto toDto(Groupn4 groupn4) {
		return mapper.getModelMapper().map(groupn4, Groupn4Dto.class);
	}
}
