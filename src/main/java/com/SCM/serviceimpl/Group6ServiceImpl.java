package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexGroup5;
import com.SCM.IndexDto.IndexGroup6;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.Group5;
import com.SCM.model.Group6;
import com.SCM.repository.Group6Repo;
import com.SCM.service.Group6Service;

@Service
public class Group6ServiceImpl implements Group6Service{

	@Autowired
	private Group6Repo group6Repository;

	@Autowired
	private GetCurrentUserRoleName auth;
	
	@Override
	public Group6 save(Group6 Group6) {
		// TODO Auto-generated method stub
		Long uid = auth.getUserId();
		String uname=auth.getUserName();

		String role=auth.getRolename();
		Group6.setCreatedby(uid);
		Group6.setCreatebyname(uname);
		Group6.setRole(role);
		Group6.setCreatedtime(LocalTime.now());
		Group6.setCreateddate(LocalDate.now());
	
		
		com.SCM.model.Group6 save = group6Repository.save(Group6);
		return save;
	}

	@Override
	public List<Group6> getAll() {
		// TODO Auto-generated method stub
		List<Group6> findAll = group6Repository.findAll();
		return findAll;
	}

	@Override
	public Optional<Group6> getById(long id) {
		// TODO Auto-generated method stub
		Optional<Group6> findById = group6Repository.findById(id);
		return findById;
	}

	@Override
	public String updateGroup6(long id, Group6 Group6) {
		// TODO Auto-generated method stub
		
		if (group6Repository.existsById(id)) {
			Long uid = auth.getUserId();
			String uname=auth.getUserName();

			String role=auth.getRolename();
			
			com.SCM.model.Group6 group = group6Repository.findById(id).get();
			Group6 groups=group;
			group.setTitle(Group6.getTitle());
			group.setUpdatedby(uid);
			group.setUpdatedbyname(uname);
			group.setUpdateddate(LocalDate.now());
			group.setUpdatedrole(role);
			group.setUpdatedtime(LocalTime.now());
			
			group6Repository.save(groups);
			return "succesfully updated the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public String deleteGroup6(long id) {
		if (group6Repository.existsById(id)) {
			group6Repository.deleteById(id);
			return "succesfully deleted the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public Map<String, Object> IndexGroup6Asc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group6Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup6> groups = group6Repository.indexGroup6(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexGroup6Desc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();
		
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group6Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}
		List<IndexGroup6> groups = group6Repository.indexGroup6(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchGroup6(int pageno, int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexGroup6> group1 = group6Repository.indexGroup6(search, p);
		
		long searchcount = group1.size();
		
		response.put("data", group1);
		response.put("SearchCount", searchcount);
		
		return response;
	}
	
	
}
