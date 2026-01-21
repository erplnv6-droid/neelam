package com.SCM.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SCM.dto.GroupDto;
import com.SCM.dto.SubGroupDto;
import com.SCM.model.Group;
import com.SCM.model.SubGroup;

@Component
public class SubGroupMapper {

	@Autowired
	private Mapper mapper;
	
	public SubGroup toEntity(SubGroupDto subGroupDto) {
		return mapper.getModelMapper().map(subGroupDto, SubGroup.class);
	}
	
	public SubGroupDto toDto(SubGroup subGroup) {
		return mapper.getModelMapper().map(subGroup, SubGroupDto.class);
	}
	
}
