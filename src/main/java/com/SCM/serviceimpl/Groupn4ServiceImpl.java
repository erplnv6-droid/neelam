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

import com.SCM.IndexDto.IndexGroupn3;
import com.SCM.IndexDto.IndexGroupn4;
import com.SCM.dto.CustomPageResponse;
import com.SCM.dto.Groupn4Dto;
 import com.SCM.mapper.Groupn4Mapper;
 import com.SCM.model.Groupn4;
 import com.SCM.repository.Groupn4Repository;
 import com.SCM.service.Groupn4Service;

@Service
public class Groupn4ServiceImpl implements Groupn4Service{

	@Autowired
	private Groupn4Repository groupn4Repository;
	
	@Autowired
	private Groupn4Mapper mapper;

	@Override
	public Groupn4Dto save(Groupn4Dto dto) {
		Groupn4 entity = mapper.toEntity(dto);
		Groupn4 save = groupn4Repository.save(entity);
		return mapper.toDto(save);
	}

	@Override
	public List<Groupn4Dto> getAll() {
		List<Groupn4> findAll = groupn4Repository.findAll();
		List<Groupn4Dto> list = findAll.stream().map(f->mapper.toDto(f)).collect(Collectors.toList());
		return list;
	}

	@Override
	public CustomPageResponse<IndexGroupn4> FindAllGroupByGroupn4(int pageNumber, int pageSize, String field,
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
		
		Page<IndexGroupn4> repoPage=groupn4Repository.getAllGroupn4(pageable);
		List<IndexGroupn4> content=repoPage.getContent();
		
		CustomPageResponse<IndexGroupn4> response=new CustomPageResponse<>();
		response.setContent(content);
	    response.setPageNumber(repoPage.getNumber());
	    response.setPageSize(repoPage.getSize());
	    response.setLast(repoPage.isLast());
	    response.setTotalElements(repoPage.getTotalElements());
	    response.setTotalPages(repoPage.getTotalPages());

		return response;
	}

	@Override
	public CustomPageResponse<IndexGroupn4> searchGroupn4(int pageNumber, int pageSize, String field, String direction,
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
		Page<IndexGroupn4> repoPage=groupn4Repository.searchGroupn4(pageable, search);
		List<IndexGroupn4> content=repoPage.getContent();
		
		CustomPageResponse<IndexGroupn4> response=new CustomPageResponse<>();
		response.setContent(content);
	    response.setPageNumber(repoPage.getNumber());
	    response.setPageSize(repoPage.getSize());
	    response.setLast(repoPage.isLast());
	    response.setTotalElements(repoPage.getTotalElements());
	    response.setTotalPages(repoPage.getTotalPages());
		
	    return response;
	}

	@Override
	public Optional<Groupn4Dto> getById(long id) {
		groupn4Repository.findById(id)
	    .orElseThrow(() -> new RuntimeException("No groupn4 is present with id " + id));

	Optional<Groupn4> g4 = groupn4Repository.findById(id);
	Optional<Groupn4Dto> map = g4.map(g -> mapper.toDto(g));
	return map;
 	}

	@Override
	public Groupn4Dto updateGroupn4(Groupn4Dto dto, long id) {
		groupn4Repository.findById(id)
	    .orElseThrow(() -> new RuntimeException("No groupn4 is present with id " + id));

	Groupn4 entity = mapper.toEntity(dto);
	entity.setId(id);
	Groupn4 save = groupn4Repository.save(entity);

	return mapper.toDto(save);

	}

	@Override
	public String deleteGroupn4(long id) {
groupn4Repository.findById(id).orElseThrow(()-> new RuntimeException("no groupn4 is present with id "+id));
		
		groupn4Repository.deleteById(id);
		
		return "succesfully deleted the groupn4 with id "+id;
	}
}
