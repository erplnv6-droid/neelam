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

import com.SCM.IndexDto.IndexGroupn2;
import com.SCM.IndexDto.IndexGroupn3;
import com.SCM.dto.CustomPageResponse;
import com.SCM.dto.Groupn3Dto;
 import com.SCM.mapper.Groupn3Mapper;
 import com.SCM.model.Groupn3;
 import com.SCM.repository.Groupn3Repository;
import com.SCM.service.Groupn3Service;

@Service
public class Groupn3ServiceImpl implements Groupn3Service{

	
	
	@Autowired
	private Groupn3Repository groupn3Repository;
	
	@Autowired
	private Groupn3Mapper mapper;

	@Override
	public Groupn3Dto save(Groupn3Dto dto) {
		Groupn3 entity = mapper.toEntity(dto);
		Groupn3 save = groupn3Repository.save(entity);
		return mapper.toDto(save);
	}

	@Override
	public List<Groupn3Dto> getAll() {
		List<Groupn3> findAll = groupn3Repository.findAll();
		List<Groupn3Dto> list = findAll.stream().map(f->mapper.toDto(f)).collect(Collectors.toList());
		return list;
	}

	@Override
	public CustomPageResponse<IndexGroupn3> FindAllGroupByGroupn3(int pageNumber, int pageSize, String field,
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
		
		Page<IndexGroupn3> repoPage=groupn3Repository.getAllGroupn3(pageable);
		List<IndexGroupn3> content=repoPage.getContent();
		
		CustomPageResponse<IndexGroupn3> response=new CustomPageResponse<>();
		response.setContent(content);
	    response.setPageNumber(repoPage.getNumber());
	    response.setPageSize(repoPage.getSize());
	    response.setLast(repoPage.isLast());
	    response.setTotalElements(repoPage.getTotalElements());
	    response.setTotalPages(repoPage.getTotalPages());

		return response;

	}

	@Override
	public CustomPageResponse<IndexGroupn3> searchGroupn3(int pageNumber, int pageSize, String field, String direction,
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
		Page<IndexGroupn3> repoPage=groupn3Repository.searchGroupn3(pageable, search);
		List<IndexGroupn3> content=repoPage.getContent();
		
		CustomPageResponse<IndexGroupn3> response=new CustomPageResponse<>();
		response.setContent(content);
	    response.setPageNumber(repoPage.getNumber());
	    response.setPageSize(repoPage.getSize());
	    response.setLast(repoPage.isLast());
	    response.setTotalElements(repoPage.getTotalElements());
	    response.setTotalPages(repoPage.getTotalPages());
		
	    return response;
	}

	@Override
	public Optional<Groupn3Dto> getById(long id) {
		groupn3Repository.findById(id)
	    .orElseThrow(() -> new RuntimeException("No groupn3 is present with id " + id));

	Optional<Groupn3> g3 = groupn3Repository.findById(id);
	Optional<Groupn3Dto> map = g3.map(g -> mapper.toDto(g));
	return map;
	}

	@Override
	public Groupn3Dto updateGroupn3(Groupn3Dto dto, long id) {
		groupn3Repository.findById(id)
	    .orElseThrow(() -> new RuntimeException("No groupn3 is present with id " + id));

	Groupn3 entity = mapper.toEntity(dto);
	entity.setId(id);
	Groupn3 save = groupn3Repository.save(entity);

	return mapper.toDto(save);

	}

	@Override
	public String deleteGroupn3(long id) {
groupn3Repository.findById(id).orElseThrow(()-> new RuntimeException("no groupn3 is present with id "+id));
		
		groupn3Repository.deleteById(id);
		
		return "succesfully deleted the groupn3 with id "+id;
	}
}
