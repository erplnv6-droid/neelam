package com.SCM.service;

import java.util.List;
import java.util.Optional;

import com.SCM.dto.GroupDto;
import com.SCM.model.Group;

public interface GroupService {

	public GroupDto createGroup(GroupDto groupDto);
	public List< GroupDto> getAllGroup();
	public Optional<GroupDto> getById(long id);
	public String updateGroup(GroupDto dto,long id);
	public String deleteGroup(long id);
	
}
