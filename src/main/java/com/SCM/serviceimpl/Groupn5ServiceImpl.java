package com.SCM.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexGroupn4;
import com.SCM.IndexDto.IndexGroupn5;
import com.SCM.dto.CustomPageResponse;
import com.SCM.dto.Group5Dto;
import com.SCM.dto.Groupn5Dto;
 import com.SCM.mapper.Groupn5Mapper;
 import com.SCM.model.Groupn5;
 import com.SCM.repository.Groupn5Repository;
import com.SCM.service.Groupn5Service;

@Service
public class Groupn5ServiceImpl implements Groupn5Service{

	
	
	
	@Autowired
	private Groupn5Repository groupn5Repository;
	
	@Autowired
	private Groupn5Mapper mapper;

	@Override
	public Groupn5Dto save(Groupn5Dto dto) {
		Groupn5 entity = mapper.toEntity(dto);
		Groupn5 save = groupn5Repository.save(entity);
		return mapper.toDto(save);
	}

	@Override
	public List<Groupn5Dto> getAll() {
		List<Groupn5> findAll = groupn5Repository.findAll();
		List<Groupn5Dto> list = findAll.stream().map(f->mapper.toDto(f)).collect(Collectors.toList());
	
		return list;
	}

	@Override
	public CustomPageResponse<IndexGroupn5> FindAllGroupByGroupn5(int pageNumber, int pageSize, String field,
			String direction) {
		
		Sort sort = Sort.by(field);
	    if ("asc".equalsIgnoreCase(direction)) {
	        sort = sort.ascending();
	    } else if ("desc".equalsIgnoreCase(direction)) {
	        sort = sort.descending();
	    } else {
	        sort = sort.ascending(); // Default sorting
	    }
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<IndexGroupn5> repoPage=groupn5Repository.getAllGroupn5(pageable);
		List<IndexGroupn5> content=repoPage.getContent();
		
		CustomPageResponse<IndexGroupn5> response=new CustomPageResponse<>();
		response.setContent(content);
	    response.setPageNumber(repoPage.getNumber());
	    response.setPageSize(repoPage.getSize());
	    response.setLast(repoPage.isLast());
	    response.setTotalElements(repoPage.getTotalElements());
	    response.setTotalPages(repoPage.getTotalPages());

		return response;
	}

	@Override
	public CustomPageResponse<IndexGroupn5> searchGroupn5(int pageNumber, int pageSize, String field, String direction,
			String search) {
		
		Sort sort = Sort.by(field);
	    if ("asc".equalsIgnoreCase(direction)) {
	        sort = sort.ascending();
	    } else if ("desc".equalsIgnoreCase(direction)) {
	        sort = sort.descending();
	    } else {
	        sort = sort.ascending(); // Default sorting
	    }
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<IndexGroupn5> repoPage=groupn5Repository.searchGroupn5(pageable, search);
		List<IndexGroupn5> content=repoPage.getContent();
		
		CustomPageResponse<IndexGroupn5> response=new CustomPageResponse<>();
		response.setContent(content);
	    response.setPageNumber(repoPage.getNumber());
	    response.setPageSize(repoPage.getSize());
	    response.setLast(repoPage.isLast());
	    response.setTotalElements(repoPage.getTotalElements());
	    response.setTotalPages(repoPage.getTotalPages());
		
	    return response;
	}

	@Override
	public Optional<Groupn5Dto> getById(long id) {
		groupn5Repository.findById(id)
	    .orElseThrow(() -> new RuntimeException("No groupn5 is present with id " + id));

	Optional<Groupn5> g5 = groupn5Repository.findById(id);
	Optional<Groupn5Dto> map = g5.map(g -> mapper.toDto(g));
	return map;
 	}

	@Override
	public Groupn5Dto updateGroupn5(Groupn5Dto dto, long id) {
		groupn5Repository.findById(id)
	    .orElseThrow(() -> new RuntimeException("No groupn5 is present with id " + id));

	Groupn5 entity = mapper.toEntity(dto);
	entity.setId(id);
	Groupn5 save = groupn5Repository.save(entity);

	return mapper.toDto(save);

	}

	@Override
	public String deleteGroupn5(long id) {
		
		groupn5Repository.findById(id).orElseThrow(()-> new RuntimeException("no groupn2 is present with id "+id));
		
		groupn5Repository.deleteById(id);
		
		return "succesfully deleted the groupn2 with id "+id;
	}
}
