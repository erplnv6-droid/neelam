package com.SCM.service;

import java.util.List;
import java.util.Optional;

import com.SCM.dto.SubGroupDto;

public interface SubGroupService {

	public SubGroupDto createSubGroup(SubGroupDto subGroupDto);
	public List<SubGroupDto> getAllSubGroup();
	public Optional<SubGroupDto> getById(long id);
	public String updateSubGroup(SubGroupDto subGroupDto,long id);
	public String deleteSubGroup(long id);
}
