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

import com.SCM.IndexDto.IndexGroup1;
import com.SCM.IndexDto.IndexGroup2;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.Group2;
import com.SCM.repository.Group2Repository;
import com.SCM.service.Group2Service;

@Service
public class Group2ServiceImpl implements Group2Service{

	@Autowired
	private Group2Repository group2Repository;

	@Autowired
	private GetCurrentUserRoleName auth;
	
	@Override
	public Group2 save(Group2 Group2) {
		// TODO Auto-generated method stub
		Long uid = auth.getUserId();
		String uname=auth.getUserName();

		String role=auth.getRolename();
		Group2.setCreatedby(uid);
		Group2.setCreatebyname(uname);
		Group2.setRole(role);
		Group2.setCreatedtime(LocalTime.now());
		Group2.setCreateddate(LocalDate.now());
		com.SCM.model.Group2 save = group2Repository.save(Group2);
		return save;
	}

	@Override
	public List<Group2> getAll() {
		// TODO Auto-generated method stub
		List<Group2> findAll = group2Repository.findAll();
		return findAll;
	}

	@Override
	public Optional<Group2> getById(long id) {
		// TODO Auto-generated method stub
		Optional<Group2> findById = group2Repository.findById(id);
		return findById;
	}

	@Override
	public String updateGroup2(long id, Group2 group2) {
		// TODO Auto-generated method stub
		
		if (group2Repository.existsById(id)) {
			Long uid = auth.getUserId();
			String uname=auth.getUserName();

			String role=auth.getRolename();
			
			Group2 group = group2Repository.findById(id).get();
			Group2 group3=group;

			group.setTitle(group2.getTitle());
			group.setUpdatedby(uid);
			group.setUpdatedbyname(uname);
			group.setUpdateddate(LocalDate.now());
			group.setUpdatedtime(LocalTime.now());
			group.setUpdatedrole(role);

			group2Repository.save(group3);
			return "succesfully updated the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public String deleteGroup2(long id) {
		if (group2Repository.existsById(id)) {
			group2Repository.deleteById(id);
			return "succesfully deleted the group with id "+id;
			
		}
		return "no group is present with id "+id;
	}

	@Override
	public Map<String, Object> IndexGroup2Asc(int pagno, int pagesize, String field) {
		// TODO Auto-generated method stub
				Map<String, Object> response = new HashMap<>();

				Sort sort = Sort.by(Sort.Direction.ASC, field);
				Pageable p = PageRequest.of(pagno, pagesize, sort);

				long countpages = group2Repository.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}

				List<IndexGroup2> groups = group2Repository.indexGroup2(p);

				response.put("Index", groups);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
	}

	@Override
	public Map<String, Object> IndexGroup2Desc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = group2Repository.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGroup2> groups = group2Repository.indexGroup2(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchGroup2(int pageno, int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexGroup2> group1 = group2Repository.indexGroup2(search, p);
		
		long searchcount = group1.size();
		
		response.put("data", group1);
		response.put("SearchCount", searchcount);
		
		return response;
	}
}
