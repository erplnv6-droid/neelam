package com.SCM.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexGroup1;
import com.SCM.IndexDto.IndexGroupn2;
import com.SCM.dto.CustomPageResponse;
import com.SCM.dto.Groupn2Dto;
 import com.SCM.mapper.Groupn2Mapper;
 import com.SCM.model.Groupn2;
 import com.SCM.repository.Groupn2Repository;
import com.SCM.service.Groupn2Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class Groupn2ServiceImpl implements Groupn2Service {

	
	
	@Autowired
	private Groupn2Repository groupn2Repository;
	
	@Autowired
	private Groupn2Mapper mapper;

	@Override
	public Groupn2Dto save(Groupn2Dto dto) {
		Groupn2 entity = mapper.toEntity(dto);
		Groupn2 save = groupn2Repository.save(entity);
		return mapper.toDto(save);
	}

	@Override
	public List<Groupn2Dto> getAll() {
		List<Groupn2> findAll = groupn2Repository.findAll();
		List<Groupn2Dto> list = findAll.stream().map(f->mapper.toDto(f)).collect(Collectors.toList());
		return list;
	}
	
	@Override
	public Optional<Groupn2Dto> getById(long id) {
		
		groupn2Repository.findById(id).orElseThrow(()-> new RuntimeException("no groupn2 is present with id "+id));
		Optional<Groupn2> g2 = groupn2Repository.findById(id);
		Optional<Groupn2Dto> map = g2.map(g->mapper.toDto(g));
		return map;
	}

	@Override
	public Groupn2Dto updateGroupn2(Groupn2Dto dto, long id) {
		groupn2Repository.findById(id).orElseThrow(()-> new RuntimeException("no groupn2 is present with id "+id));
		Groupn2 entity = mapper.toEntity(dto);
		entity.setId(id);
		Groupn2 save = groupn2Repository.save(entity);
		
		return mapper.toDto(save);
	}

	@Override
	public String deleteGroupn2(long id) {
		groupn2Repository.findById(id).orElseThrow(()-> new RuntimeException("no groupn2 is present with id "+id));
		
		groupn2Repository.deleteById(id);
		
		return "succesfully deleted the groupn2 with id "+id;
	}

	

	@Override
	public CustomPageResponse<IndexGroupn2> FindAllGroupByGroupn2(int pageNumber, int pageSize, String field,
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
		
		Page<IndexGroupn2> repoPage=groupn2Repository.getAllGroupn2(pageable);
		List<IndexGroupn2> content=repoPage.getContent();
		
		CustomPageResponse<IndexGroupn2> response=new CustomPageResponse<>();
		response.setContent(content);
	    response.setPageNumber(repoPage.getNumber());
	    response.setPageSize(repoPage.getSize());
	    response.setLast(repoPage.isLast());
	    response.setTotalElements(repoPage.getTotalElements());
	    response.setTotalPages(repoPage.getTotalPages());

		return response;
	}
	
	@Override
	public CustomPageResponse<IndexGroupn2> searchGroupn2(int pageNumber, int pageSize, String field, String direction,
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
		Page<IndexGroupn2> repoPage=groupn2Repository.searchGroupn2(pageable, search);
		List<IndexGroupn2> content=repoPage.getContent();
		
		CustomPageResponse<IndexGroupn2> response=new CustomPageResponse<>();
		response.setContent(content);
	    response.setPageNumber(repoPage.getNumber());
	    response.setPageSize(repoPage.getSize());
	    response.setLast(repoPage.isLast());
	    response.setTotalElements(repoPage.getTotalElements());
	    response.setTotalPages(repoPage.getTotalPages());
		
	    return response;
	}


	
	
}
