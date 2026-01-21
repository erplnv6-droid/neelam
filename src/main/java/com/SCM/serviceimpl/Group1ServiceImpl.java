package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexBranch;
import com.SCM.IndexDto.IndexGroup1;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.Group1;
import com.SCM.repository.Group1Repository;
import com.SCM.service.Group1Service;
import com.google.common.base.Optional;

@Service
public class Group1ServiceImpl implements Group1Service{
	
	@Autowired
	private Group1Repository group1Repository;
	
	@Autowired
	private GetCurrentUserRoleName auth;
	
	@Override
	public Group1 save(Group1 group1) {
		// TODO Auto-generated method stub
		Long uid = auth.getUserId();
		String uname=auth.getUserName();

		String role=auth.getRolename();
		group1.setCreatebyname(uname);
		group1.setCreatedby(uid);
		group1.setRole(role);
		group1.setCreateddate(LocalDate.now());
		group1.setCreatedtime(LocalTime.now());
		
		Group1 save = group1Repository.save(group1);
		return save;
		
	}

	@Override
	public List<Group1> getAll() {
		// TODO Auto-generated method stub
		List<Group1> findAll = group1Repository.findAll();
		return findAll;
	}

	@Override
	public java.util.Optional<Group1> getById(long id) {
		// TODO Auto-generated method stub
		java.util.Optional<Group1> findById = group1Repository.findById(id);
		return findById;
	}

	@Override
	public String updateGroup1(long id, Group1 group1) {
		// TODO Auto-generated method stub
		Long uid = auth.getUserId();
		String uname=auth.getUserName();

		String role=auth.getRolename();
		if (group1Repository.existsById(id)) {
			Group1 group = group1Repository.findById(id).get();
			Group1 group2=group;

			group.setTitle(group1.getTitle());
			group.setUpdatedby(uid);
			group.setUpdatedbyname(uname);
			group.setUpdateddate(LocalDate.now());
			group.setUpdatedtime(LocalTime.now());
			group.setUpdatedrole(role);

			group1Repository.save(group2);
			return "Succesfully updated the group with id "+id;
			
		}
		return "No group is present with id "+id;
	}

	@Override
	public String deleteGroup1(long id) {
		if (group1Repository.existsById(id)) {
		
			group1Repository.deleteById(id);
			return "Succesfully deleted the group with id "+id;
			
		}
		return "No group is present with id "+id;
	}

	@Override
	public Map<String, Object> IndexGroup1Asc(int pagno, int pagesize,String field) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group1Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup1> groups = group1Repository.indexGroup1(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexGroup1Desc(int pagno, int pagesize,String field) {
		// TODO Auto-generated method stub
				Map<String, Object> response = new HashMap<>();
				Sort sort = Sort.by(Sort.Direction.DESC, field);
				Pageable p = PageRequest.of(pagno, pagesize, sort);

				long countpages = group1Repository.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}

				List<IndexGroup1> groups = group1Repository.indexGroup1(p);

				response.put("Index", groups);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
	}

	@Override
	public Map<String, Object> SearchGroup1(int pageno, int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexGroup1> group1 = group1Repository.indexGroup1(search, p);
		
		long searchcount = group1.size();
		
		response.put("data", group1);
		response.put("SearchCount", searchcount);
		
		return response;
	}

	
	
}
