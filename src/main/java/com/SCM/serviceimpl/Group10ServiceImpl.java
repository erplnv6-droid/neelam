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

import com.SCM.IndexDto.IndexGroup10;
import com.SCM.IndexDto.IndexGroup9;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.Group10;
import com.SCM.model.Group9;
import com.SCM.repository.Group10Repository;
import com.SCM.service.Group10Service;

@Service
public class Group10ServiceImpl implements Group10Service{


	@Autowired
	private Group10Repository group10Repository;
	@Autowired
	private GetCurrentUserRoleName auth;
	
	@Override
	public Group10 save(Group10 Group10) {
		// TODO Auto-generated method stub
		
		Long uid = auth.getUserId();
		String uname=auth.getUserName();

		String role=auth.getRolename();
		Group10.setCreatebyname(uname);
		Group10.setCreatedby(uid);
		Group10.setRole(role);
		Group10.setCreateddate(LocalDate.now());
		Group10.setCreatedtime(LocalTime.now());
	
		
		com.SCM.model.Group10 save = group10Repository.save(Group10);
		return save;
	}

	@Override
	public List<Group10> getAll() {
		// TODO Auto-generated method stub
		List<Group10> findAll = group10Repository.findAll();
		return findAll;
	}

	@Override
	public Optional<Group10> getById(long id) {
		// TODO Auto-generated method stub
		Optional<Group10> findById = group10Repository.findById(id);
		return findById;
	}
	
	@Override
	public String updateGroup10(long id, Group10 Group10) {
		// TODO Auto-generated method stub
		
		if (group10Repository.existsById(id)) {
			Long uid = auth.getUserId();
			String uname=auth.getUserName();

			String role=auth.getRolename();
			
			com.SCM.model.Group10 group = group10Repository.findById(id).get();
			Group10 groups=group;
			group.setTitle(Group10.getTitle());
			group.setUpdatedby(uid);
			group.setUpdatedbyname(uname);
			group.setUpdateddate(LocalDate.now());
			group.setUpdatedrole(role);
			group.setUpdatedtime(LocalTime.now());
			group10Repository.save(groups);
			return "succesfully updated the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public String deleteGroup10(long id) {
		if (group10Repository.existsById(id)) {
			group10Repository.deleteById(id);
			return "succesfully deleted the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public Map<String, Object> IndexGroup10Asc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group10Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup10> groups = group10Repository.indexGroup10(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexGroup10Desc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group10Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup10> groups = group10Repository.indexGroup10(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
		
	}

	@Override
	public Map<String, Object> SearchGroup10(int pageno, int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexGroup10> group1 = group10Repository.indexGroup10(search, p);
		
		long searchcount = group1.size();
		
		response.put("data", group1);
		response.put("SearchCount", searchcount);
		
		return response;
	}
	
	
	
	
	
}
