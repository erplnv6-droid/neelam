package com.SCM.service;

import java.util.List;
import java.util.Optional;

import com.SCM.IndexDto.IndexGroupn2;
import com.SCM.IndexDto.IndexGroupn3;
import com.SCM.dto.CustomPageResponse;
 import com.SCM.dto.Groupn3Dto;

public interface Groupn3Service {

	public Groupn3Dto save(Groupn3Dto dto);
	List<Groupn3Dto> getAll();
	
	public Optional<Groupn3Dto> getById(long id);
	
	public Groupn3Dto updateGroupn3(Groupn3Dto dto,long id);
	
	public String deleteGroupn3(long id);
	
	
	
	public CustomPageResponse<IndexGroupn3> FindAllGroupByGroupn3(int pageNumber,int pageSize,String field,String direction);
	public CustomPageResponse<IndexGroupn3> searchGroupn3(int pageNumber,int pageSize,String field,String direction,String search);
}
