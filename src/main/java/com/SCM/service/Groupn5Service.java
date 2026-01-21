package com.SCM.service;

 import java.util.List;
import java.util.Optional;

 import com.SCM.IndexDto.IndexGroupn5;
import com.SCM.dto.CustomPageResponse;
  import com.SCM.dto.Groupn5Dto;

public interface Groupn5Service {
	
	public Groupn5Dto save(Groupn5Dto dto);
	
	public Optional<Groupn5Dto> getById(long id);
	
	public Groupn5Dto updateGroupn5(Groupn5Dto dto,long id);
	
	public String deleteGroupn5(long id);
	
	
	public List<Groupn5Dto> getAll();
	
	public CustomPageResponse<IndexGroupn5> FindAllGroupByGroupn5(int pageNumber,int pageSize,String field,String direction);
	public CustomPageResponse<IndexGroupn5> searchGroupn5(int pageNumber,int pageSize,String field,String direction,String search);
	
	
	
}
