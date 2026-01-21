package com.SCM.service;

import java.util.List;
import java.util.Optional;

import com.SCM.IndexDto.IndexGroup1;
import com.SCM.dto.CustomPageResponse;
import com.SCM.dto.Groupn1Dto;

public interface Groupn1Service {

	public Groupn1Dto save(Groupn1Dto dto);
	
	public List<Groupn1Dto> getAll();
	
	public Optional<Groupn1Dto> getById(long id);

	public Groupn1Dto update(Groupn1Dto dto,Long id);
	
	public  String deleteGroupn1(long id);

	
	
	
	
}
