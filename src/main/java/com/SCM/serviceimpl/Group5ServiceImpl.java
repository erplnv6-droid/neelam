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

import com.SCM.IndexDto.IndexGroup4;
import com.SCM.IndexDto.IndexGroup5;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.Group4;
import com.SCM.model.Group5;
import com.SCM.model.Group5;
import com.SCM.repository.Group5Repository;
import com.SCM.service.Group5Service;

@Service
public class Group5ServiceImpl implements Group5Service{

	@Autowired
	private Group5Repository group5Repository;

	@Autowired
	private GetCurrentUserRoleName auth;
	
	@Override
	public Group5 save(Group5 Group5) {
		// TODO Auto-generated method stub
		Long uid = auth.getUserId();
		String uname=auth.getUserName();

		String role=auth.getRolename();
		Group5.setCreatedby(uid);
		Group5.setCreatebyname(uname);
		Group5.setRole(role);
		Group5.setCreatedtime(LocalTime.now());
		Group5.setCreateddate(LocalDate.now());
		
		com.SCM.model.Group5 save = group5Repository.save(Group5);
		return save;
	}

	@Override
	public List<Group5> getAll() {
		// TODO Auto-generated method stub
		List<Group5> findAll = group5Repository.findAll();
		return findAll;
	}

	@Override
	public Optional<Group5> getById(long id) {
		// TODO Auto-generated method stub
		Optional<Group5> findById = group5Repository.findById(id);
		return findById;
	}

	@Override
	public String updateGroup5(long id, Group5 Group5) {
		// TODO Auto-generated method stub
		
		if (group5Repository.existsById(id)) {
			Long uid = auth.getUserId();
			String uname=auth.getUserName();

			String role=auth.getRolename();
			
			com.SCM.model.Group5 group = group5Repository.findById(id).get();
			Group5 groups=group;
			group.setTitle(Group5.getTitle());
			group.setUpdatedby(uid);
			group.setUpdatedbyname(uname);
			group.setUpdateddate(LocalDate.now());
			group.setUpdatedrole(role);
			group.setUpdatedtime(LocalTime.now());
			
			group5Repository.save(groups);
			return "succesfully updated the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public String deleteGroup5(long id) {
		if (group5Repository.existsById(id)) {
			group5Repository.deleteById(id);
			return "succesfully deleted the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public Map<String, Object> IndexGroup5Asc(int pagno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group5Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup5> groups = group5Repository.indexGroup5(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexGroup5Desc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group5Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup5> groups = group5Repository.indexGroup5(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchGroup5(int pageno, int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexGroup5> group1 = group5Repository.indexGroup5(search, p);
		
		long searchcount = group1.size();
		
		response.put("data", group1);
		response.put("SearchCount", searchcount);
		
		return response;
	}
	
}
