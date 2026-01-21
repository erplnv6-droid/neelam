package com.SCM.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.dto.GroupDto;
import com.SCM.mapper.GroupMapper;
import com.SCM.model.Group;
import com.SCM.repository.GroupRepository;
import com.SCM.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService{

	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private GroupMapper mapper;
	
	
	
	@Override
	public GroupDto createGroup(GroupDto groupDto) {
		// TODO Auto-generated method stub
		Group entity = mapper.toEntity(groupDto);
		Group save = groupRepository.save(entity);
		GroupDto dto = mapper.toDto(save);		
		return dto;
	}

	@Override
	public List<GroupDto> getAllGroup() {
		// TODO Auto-generated method stub
		List<Group> group = groupRepository.findAll();
		List<GroupDto> groupDto = group.stream().map((g)->mapper.toDto(g)).collect(Collectors.toList());
		return groupDto;
	}

	@Override
	public Optional<GroupDto> getById(long id) {
		// TODO Auto-generated method stub
		Optional<Group> group=groupRepository.findById(id);
		Optional<GroupDto> groupDto = group.map((g)->mapper.toDto(g));
		return groupDto ;
	}

	@Override
	public String updateGroup(GroupDto dto, long id) {
		if (groupRepository.findById(id).isPresent()) {
			Group entity = mapper.toEntity(dto);
			entity.setId(id);
			groupRepository.save(entity);
			return "succesfully updated the group with id "+id;
		}
		return "group with id  "+id+"  is not present";
	}

	@Override
	public String deleteGroup(long id) {
		if (groupRepository.findById(id).isPresent()) {
			groupRepository.deleteById(id);
			return "succesfully updated the group with id "+id;
		}
		return "group with id  "+id+"  is not present";
	}

}
