package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.SCM.dto.MasterCartoonDto;
import com.SCM.model.MasterCartoon;

@Configuration
public class MasterCartoonMapper {

	@Autowired
	private Mapper mapper;
	
	public MasterCartoon toEntity(MasterCartoonDto dto) {
		return mapper.getModelMapper().map(dto, MasterCartoon.class);
	}
	
	public MasterCartoonDto toDto(MasterCartoon cartoon) {
		return mapper.getModelMapper().map(cartoon, MasterCartoonDto.class);
	}
	
	
}
