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

import com.SCM.IndexDto.IndexGroup2;
import com.SCM.IndexDto.IndexGroup3;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.Group3;
import com.SCM.repository.Group3Repository;
import com.SCM.service.Group3Service;

@Service
public class Group3ServiceImpl implements Group3Service{

	
	@Autowired
	private Group3Repository group3Repository;
	@Autowired
	private GetCurrentUserRoleName auth;

	@Override
	public Group3 save(Group3 Group3) {
		// TODO Auto-generated method stub
		Long uid = auth.getUserId();
		String uname=auth.getUserName();

		String role=auth.getRolename();
		Group3.setCreateddate(LocalDate.now());
		Group3.setCreatebyname(uname);
		Group3.setCreatedtime(LocalTime.now());
		Group3.setCreatedby(uid);
		Group3.setRole(role);
		
		com.SCM.model.Group3 save = group3Repository.save(Group3);
		return save;
	}

	@Override
	public List<Group3> getAll() {
		// TODO Auto-generated method stub
		List<Group3> findAll = group3Repository.findAll();
		return findAll;
	}

	@Override
	public Optional<Group3> getById(long id) {
		// TODO Auto-generated method stub
		Optional<Group3> findById = group3Repository.findById(id);
		return findById;
	}

	@Override
	public String updateGroup3(long id, Group3 Group3) {
		// TODO Auto-generated method stub
		
		if (group3Repository.existsById(id)) {
			Long uid = auth.getUserId();
			String uname=auth.getUserName();

			String role=auth.getRolename();
			
			com.SCM.model.Group3 group = group3Repository.findById(id).get();
			Group3 groups=group;
			group.setTitle(Group3.getTitle());
			group.setUpdatedby(uid);
			group.setUpdatedbyname(uname);
			group.setUpdateddate(LocalDate.now());
			group.setUpdatedrole(role);
			group.setUpdatedtime(LocalTime.now());
			
			group3Repository.save(groups);
			
			return "succesfully updated the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public String deleteGroup3(long id) {
		if (group3Repository.existsById(id)) {
			group3Repository.deleteById(id);
			return "succesfully deleted the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public Map<String, Object> IndexGroup3Asc(int pagno, int pagesize, String field) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group3Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup3> groups = group3Repository.indexGroup3(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexGroup3Desc(int pagno, int pagesize, String field) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group3Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup3> groups = group3Repository.indexGroup3(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchGroup3(int pageno, int pagesize, String search) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexGroup3> group1 = group3Repository.indexGroup3(search, p);
		
		long searchcount = group1.size();
		
		response.put("data", group1);
		response.put("SearchCount", searchcount);
		
		return response;
	}
	
}
