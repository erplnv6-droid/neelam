package com.SCM.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.dto.SubGroupDto;
import com.SCM.mapper.SubGroupMapper;
import com.SCM.model.Group;
import com.SCM.model.SubGroup;
import com.SCM.repository.SubGroupRepo;
import com.SCM.service.SubGroupService;

@Service
public class SubGroupServiceImpl implements SubGroupService{

	@Autowired
	private SubGroupRepo subRepo;
	@Autowired
	private SubGroupMapper mapper;
	
	
	@Override
	public SubGroupDto createSubGroup(SubGroupDto subGroupDto) {
		// TODO Auto-generated method stub
		SubGroup entity = mapper.toEntity(subGroupDto);
		SubGroup save = subRepo.save(entity);
		SubGroupDto dto = mapper.toDto(save);
		return dto;
	}
	@Override
	public List<SubGroupDto> getAllSubGroup() {
		// TODO Auto-generated method stub
		List<SubGroup> allSub = subRepo.findAll();
		List<SubGroupDto> allSubDto = allSub.stream().map((sub)->mapper.toDto(sub)).collect(Collectors.toList());
		
		return allSubDto;
	}
	@Override
	public Optional<SubGroupDto> getById(long id) {
		// TODO Auto-generated method stub
		Optional<SubGroup> subById = subRepo.findById(id);
		Optional<SubGroupDto> sub = subById.map((sub1)->mapper.toDto(sub1));
		return sub;
	}
	
	
	@Override
	public String updateSubGroup(SubGroupDto subGroupDto, long id) {
		if (subRepo.findById(id).isPresent()) {
			SubGroup entity = mapper.toEntity(subGroupDto);
			entity.setId(id);
			subRepo.save(entity);
			return "succesfully updated the Subgroup with id "+id;
		}
		return "subgroup with id  "+id+"  is not present";
	}
	
	
	@Override
	public String deleteSubGroup(long id) {
		if (subRepo.findById(id).isPresent()) {
			subRepo.deleteById(id);
			return "succesfully deleted the Subgroup with id "+id;
		}
		return "group with id  "+id+"  is not present";
	}
	
	
}
