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

import com.SCM.IndexDto.IndexGroup3;
import com.SCM.IndexDto.IndexGroup4;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.Group3;
import com.SCM.model.Group4;
import com.SCM.repository.Group4Repository;
import com.SCM.service.Group4Service;

@Service
public class Group4ServiceImpl implements Group4Service{


	
	@Autowired
	private Group4Repository group4Repository;

	@Autowired
	private GetCurrentUserRoleName auth;
	
	@Override
	public Group4 save(Group4 Group4) {
		// TODO Auto-generated method stub
		Long uid = auth.getUserId();
		String uname=auth.getUserName();

		String role=auth.getRolename();
		Group4.setCreatedby(uid);
		Group4.setCreatebyname(uname);
		Group4.setRole(role);
		Group4.setCreatedtime(LocalTime.now());
		Group4.setCreateddate(LocalDate.now());
		
		com.SCM.model.Group4 save = group4Repository.save(Group4);
		return save;
	}

	@Override
	public List<Group4> getAll() {
		// TODO Auto-generated method stub
		List<Group4> findAll = group4Repository.findAll();
		return findAll;
	}

	@Override
	public Optional<Group4> getById(long id) {
		// TODO Auto-generated method stub
		Optional<Group4> findById = group4Repository.findById(id);
		return findById;
	}

	@Override
	public String updateGroup4(long id, Group4 Group4) {
		// TODO Auto-generated method stub
		
		if (group4Repository.existsById(id)) {
			Long uid = auth.getUserId();
			String uname=auth.getUserName();

			String role=auth.getRolename();
			
			com.SCM.model.Group4 group = group4Repository.findById(id).get();
			Group4 groups=group;
			group.setTitle(Group4.getTitle());
			group.setUpdatedby(uid);
			group.setUpdatedbyname(uname);
			group.setUpdateddate(LocalDate.now());
			group.setUpdatedrole(role);
			group.setUpdatedtime(LocalTime.now());
			
			group4Repository.save(groups);
			
			return "succesfully updated the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public String deleteGroup4(long id) {
		if (group4Repository.existsById(id)) {
			group4Repository.deleteById(id);
			return "succesfully deleted the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public Map<String, Object> IndexGroup4Asc(int pagno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group4Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup4> groups = group4Repository.indexGroup4(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexGroup4Desc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group4Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup4> groups = group4Repository.indexGroup4(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchGroup4(int pageno, int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexGroup4> group1 = group4Repository.indexGroup4(search, p);
		
		long searchcount = group1.size();
		
		response.put("data", group1);
		response.put("SearchCount", searchcount);
		
		return response;
	}
	
	
	
}
