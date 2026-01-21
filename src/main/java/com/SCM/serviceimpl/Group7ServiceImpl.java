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
import com.SCM.IndexDto.IndexGroup7;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.Group6;
import com.SCM.model.Group7;
import com.SCM.repository.Group7Repository;
import com.SCM.service.Group7Service;

@Service
public class Group7ServiceImpl implements Group7Service{


	@Autowired
	private Group7Repository group7Repository;

	
	@Autowired
	private GetCurrentUserRoleName auth;
	
	@Override
	public Group7 save(Group7 Group7) {
		
		Long uid = auth.getUserId();
		String uname=auth.getUserName();

		String role=auth.getRolename();
		Group7.setCreatedby(uid);
		Group7.setCreatebyname(uname);
		Group7.setRole(role);
		Group7.setCreatedtime(LocalTime.now());
		Group7.setCreateddate(LocalDate.now());
		
		
		
		com.SCM.model.Group7 save = group7Repository.save(Group7);
		return save;
	}

	@Override
	public List<Group7> getAll() {
		// TODO Auto-generated method stub
		List<Group7> findAll = group7Repository.findAll();
		return findAll;
	}

	@Override
	public Optional<Group7> getById(long id) {
		// TODO Auto-generated method stub
		Optional<Group7> findById = group7Repository.findById(id);
		return findById;
	}

	@Override
	public String updateGroup7(long id, Group7 Group7) {
		// TODO Auto-generated method stub
		
		if (group7Repository.existsById(id)) {
			Long uid = auth.getUserId();
			String uname=auth.getUserName();

			String role=auth.getRolename();
			
			com.SCM.model.Group7 group = group7Repository.findById(id).get();
			Group7 groups=group;
			group.setTitle(Group7.getTitle());
			group.setUpdatedby(uid);
			group.setUpdatedbyname(uname);
			group.setUpdateddate(LocalDate.now());
			group.setUpdatedrole(role);
			group.setUpdatedtime(LocalTime.now());
			
			group7Repository.save(groups);
			return "succesfully updated the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public String deleteGroup7(long id) {
		if (group7Repository.existsById(id)) {
			group7Repository.deleteById(id);
			return "succesfully deleted the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public Map<String, Object> IndexGroup7Asc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group7Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup7> groups = group7Repository.indexGroup7(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexGroup7Desc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group7Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup7> groups = group7Repository.indexGroup7(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchGroup7(int pageno, int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexGroup7> group1 = group7Repository.indexGroup7(search, p);
		
		long searchcount = group1.size();
		
		response.put("data", group1);
		response.put("SearchCount", searchcount);
		
		return response;
	}
	
	
	
}
