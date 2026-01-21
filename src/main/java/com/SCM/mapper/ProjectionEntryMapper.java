package com.SCM.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.SCM.dto.ProjectionEntryDto;
import com.SCM.model.ProjectionEntry;

@Configuration
public class ProjectionEntryMapper {
	
	@Autowired
	private ModelMapper mapper;
	
	public ProjectionEntry toEntity(ProjectionEntryDto dto) {
		return mapper.map(dto, ProjectionEntry.class);
	}
	
	public ProjectionEntryDto toDto(ProjectionEntry entity) {
		return mapper.map(entity, ProjectionEntryDto.class);
	}	
}
