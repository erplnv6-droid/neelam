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

import com.SCM.IndexDto.IndexGroup7;
import com.SCM.IndexDto.IndexGroup8;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.Group7;
import com.SCM.model.Group8;

import com.SCM.repository.Group8Repository;
import com.SCM.service.Group8Service;

@Service
public class Group8ServiceImpl implements Group8Service{


	@Autowired
	private Group8Repository group8Repository;

	@Autowired
	private GetCurrentUserRoleName auth;
	@Override
	public Group8 save(Group8 Group8) {
		// TODO Auto-generated method stub
		
		Long uid = auth.getUserId();
		String uname=auth.getUserName();

		String role=auth.getRolename();
		Group8.setCreatedby(uid);
		Group8.setCreatebyname(uname);
		Group8.setRole(role);
		Group8.setCreatedtime(LocalTime.now());
		Group8.setCreateddate(LocalDate.now());
		com.SCM.model.Group8 save = group8Repository.save(Group8);
		return save;
	}

	@Override
	public List<Group8> getAll() {
		// TODO Auto-generated method stub
		List<Group8> findAll = group8Repository.findAll();
		return findAll;
	}

	@Override
	public Optional<Group8> getById(long id) {
		// TODO Auto-generated method stub
		Optional<Group8> findById = group8Repository.findById(id);
		return findById;
	}

	@Override
	public String updateGroup8(long id, Group8 Group8) {
		// TODO Auto-generated method stub
		
		if (group8Repository.existsById(id)) {
			Long uid = auth.getUserId();
			String uname=auth.getUserName();

			String role=auth.getRolename();
			
			com.SCM.model.Group8 group = group8Repository.findById(id).get();
			Group8 groups=group;
			group.setTitle(Group8.getTitle());
			group.setUpdatedby(uid);
			group.setUpdatedbyname(uname);
			group.setUpdateddate(LocalDate.now());
			group.setUpdatedrole(role);
			group.setUpdatedtime(LocalTime.now());
			group8Repository.save(groups);

			return "succesfully updated the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public String deleteGroup8(long id) {
		if (group8Repository.existsById(id)) {
			group8Repository.deleteById(id);
			return "succesfully deleted the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public Map<String, Object> IndexGroup8Asc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group8Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup8> groups = group8Repository.indexGroup8(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexGroup8Desc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group8Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup8> groups = group8Repository.indexGroup8(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchGroup8(int pageno, int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexGroup8> group1 = group8Repository.indexGroup8(search, p);
		
		long searchcount = group1.size();
		
		response.put("data", group1);
		response.put("SearchCount", searchcount);
		
		return response;
	}
	
	
	
}
