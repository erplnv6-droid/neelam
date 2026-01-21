package com.SCM.service;

 import java.util.List;
import java.util.Optional;

import com.SCM.IndexDto.IndexGroupn3;
import com.SCM.IndexDto.IndexGroupn4;
import com.SCM.dto.CustomPageResponse;
import com.SCM.dto.Groupn4Dto;

public interface Groupn4Service {

	
	public Groupn4Dto save(Groupn4Dto dto);
	
	public Optional<Groupn4Dto> getById(long id);
	
	public Groupn4Dto updateGroupn4(Groupn4Dto dto,long id);
	
	public String deleteGroupn4(long id);
	
	
	public List<Groupn4Dto> getAll();
	
	public CustomPageResponse<IndexGroupn4> FindAllGroupByGroupn4(int pageNumber,int pageSize,String field,String direction);
	public CustomPageResponse<IndexGroupn4> searchGroupn4(int pageNumber,int pageSize,String field,String direction,String search);
	
	
}
