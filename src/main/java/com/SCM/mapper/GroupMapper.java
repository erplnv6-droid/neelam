package com.SCM.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.SCM.dto.GroupDto;
import com.SCM.model.Group;

@Component
public class GroupMapper {

	@Autowired
	private Mapper mapper;
	
	public Group toEntity(GroupDto groupDto) {
		return mapper.getModelMapper().map(groupDto, Group.class);
	}
	
	public GroupDto toDto(Group group) {
		return mapper.getModelMapper().map(group, GroupDto.class);
	}
	
}
