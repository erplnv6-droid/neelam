package com.SCM.service;

import java.util.List;
import java.util.Optional;

import com.SCM.IndexDto.IndexGroup1;
import com.SCM.IndexDto.IndexGroup2;
import com.SCM.IndexDto.IndexGroupn2;
import com.SCM.dto.CustomPageResponse;
import com.SCM.dto.Groupn2Dto;

public interface Groupn2Service {
	public Groupn2Dto save(Groupn2Dto dto);

	List<Groupn2Dto> getAll();
	
	public Optional<Groupn2Dto> getById(long id);
	
	public Groupn2Dto updateGroupn2(Groupn2Dto dto,long id);
	
	public String deleteGroupn2(long id);
	
	
	
	
	public CustomPageResponse<IndexGroupn2> FindAllGroupByGroupn2(int pageNumber,int pageSize,String field,String direction);
	public CustomPageResponse<IndexGroupn2> searchGroupn2(int pageNumber,int pageSize,String field,String direction,String search);
}
