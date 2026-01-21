package com.SCM.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.dto.Groupn1Dto;
import com.SCM.mapper.Groupn1Mapper;
import com.SCM.model.Groupn1;
import com.SCM.repository.Groupn1Repository;
import com.SCM.service.Groupn1Service;

@Service
public class Groupn1ServiceImpl implements Groupn1Service{

	@Autowired
	private Groupn1Repository groupn1Repository;
	
	@Autowired
	private Groupn1Mapper mapper;

	@Override
	public Groupn1Dto save(Groupn1Dto dto) {
		List<Groupn1> findAll = groupn1Repository.findAll();
		int size = findAll.size();
		System.out.println(size+"sizeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		if (size>=1) {
			throw new RuntimeException("Sorry only one groupn1 is allowed first delete it then try to create again ");
		}
		Groupn1 entity = mapper.toEntity(dto);
		Groupn1 save = groupn1Repository.save(entity);
		return mapper.toDto(save);
	}

	@Override
	public List<Groupn1Dto> getAll() {
		List<Groupn1> findAll = groupn1Repository.findAll();
		List<Groupn1Dto> collect = findAll.stream().map(f->mapper.toDto(f)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public Optional<Groupn1Dto> getById(long id) {
		groupn1Repository.findById(id).orElseThrow(()-> new RuntimeException("groupn1 is not present with id "+id));
		Optional<Groupn1> g1 = groupn1Repository.findById(id);
		Optional<Groupn1Dto> map = g1.map(g->mapper.toDto(g));
		return map;
	}

	@Override
	public Groupn1Dto update(Groupn1Dto dto,Long id) {
		groupn1Repository.findById(id).orElseThrow(()-> new RuntimeException("groupn1 is not present with id "+id));
		Groupn1 entity = mapper.toEntity(dto);
		entity.setId(id);
		Groupn1 save = groupn1Repository.save(entity);
		return mapper.toDto(save);
	}

	@Override
	public String deleteGroupn1(long id) {
		groupn1Repository.findById(id).orElseThrow(()-> new RuntimeException("groupn1 is not present with id "+id));
		groupn1Repository.deleteById(id);
		return "groupn1 is succesfully deleted with id "+id;
	}
	
	
	
	
}
