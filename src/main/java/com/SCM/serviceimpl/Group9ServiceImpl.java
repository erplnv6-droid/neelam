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

import com.SCM.IndexDto.IndexGroup8;
import com.SCM.IndexDto.IndexGroup9;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.Group8;
import com.SCM.model.Group9;
import com.SCM.repository.Group9Repository;
import com.SCM.service.Group9Service;
import com.google.cloud.storage.Acl.Group;

@Service
public class Group9ServiceImpl implements Group9Service{


	@Autowired
	private Group9Repository group9Repository;

	@Autowired
	private GetCurrentUserRoleName auth;
	@Override
	public Group9 save(Group9 Group9) {
		// TODO Auto-generated method stub
		
		Long uid = auth.getUserId();
		String uname=auth.getUserName();

		String role=auth.getRolename();
		Group9.setCreatedby(uid);
		Group9.setCreatebyname(uname);
		Group9.setRole(role);
		Group9.setCreatedtime(LocalTime.now());
		Group9.setCreateddate(LocalDate.now());
		com.SCM.model.Group9 save = group9Repository.save(Group9);
		return save;
	}

	@Override
	public List<Group9> getAll() {
		// TODO Auto-generated method stub
		List<Group9> findAll = group9Repository.findAll();
		return findAll;
	}

	@Override
	public Optional<Group9> getById(long id) {
		// TODO Auto-generated method stub
		Optional<Group9> findById = group9Repository.findById(id);
		return findById;
	}

	@Override
	public String updateGroup9(long id, Group9 Group9) {
		// TODO Auto-generated method stub
		
		if (group9Repository.existsById(id)) {
			Long uid = auth.getUserId();
			String uname=auth.getUserName();

			String role=auth.getRolename();
			
			com.SCM.model.Group9 group = group9Repository.findById(id).get();
			Group9 groups=group;
			group.setTitle(Group9.getTitle());
			group.setUpdatedby(uid);
			group.setUpdatedbyname(uname);
			group.setUpdateddate(LocalDate.now());
			group.setUpdatedrole(role);
			group.setUpdatedtime(LocalTime.now());
			group9Repository.save(groups);
			return "succesfully updated the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public String deleteGroup9(long id) {
		if (group9Repository.existsById(id)) {
			group9Repository.deleteById(id);
			return "succesfully deleted the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public Map<String, Object> IndexGroup9Asc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group9Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup9> groups = group9Repository.indexGroup9(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexGroup9Desc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group9Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup9> groups = group9Repository.indexGroup9(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchGroup9(int pageno, int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexGroup9> group1 = group9Repository.indexGroup9(search, p);
		
		long searchcount = group1.size();
		
		response.put("data", group1);
		response.put("SearchCount", searchcount);
		
		return response;
	}
	
	
	
	
	
}
