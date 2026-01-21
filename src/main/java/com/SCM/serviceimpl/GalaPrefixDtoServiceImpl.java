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

import com.SCM.IndexDto.IndexGalaPrefix;
import com.SCM.dto.GalaPrefixDto;
import com.SCM.mapper.GalaPrefixMapper;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.GalaPrefix;
import com.SCM.repository.GalaPrefixRepo;
import com.SCM.service.GalaPrefixService;

@Service
public class GalaPrefixDtoServiceImpl implements GalaPrefixService{

	@Autowired
	private GalaPrefixRepo galaRepo;
	
	@Autowired
	private GalaPrefixMapper mapper;

	@Autowired
	private GetCurrentUserRoleName auth;
	
	@Override
	public GalaPrefixDto saveGalaPrefix(GalaPrefixDto dto) {

		Long uid = auth.getUserId();
		String uname=auth.getUserName();
		String role=auth.getRolename();
		
		GalaPrefix entity = mapper.toEntity(dto);
		entity.setCreatebyname(uname);
		entity.setCreatedby(uid);
		entity.setCreateddate(LocalDate.now());
		entity.setCreatedtime(LocalTime.now());
		entity.setRole(role);
		GalaPrefix save = galaRepo.save(entity);
		return mapper.toDto(save);
	}

	@Override
	public List<GalaPrefix> getAllPrefix() {
		// TODO Auto-generated method stub

		System.out.println("allllllllllllllll galaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		List<GalaPrefix> galas = galaRepo.findAll();
//		List<GalaPrefixDto> collect = galas.stream().map((g)-> mapper.toDto(g)).collect(Collectors.toList());
		return galas;
	}

	@Override
	public Optional<GalaPrefixDto> getById(long id) {
		// TODO Auto-generated method stub
		Optional<GalaPrefix> gala = galaRepo.findById(id);
		Optional<GalaPrefixDto> gala1 = gala.map((g)->mapper.toDto(g));
		return gala1;
	}

	@Override
	public String updateGala(GalaPrefix dto, long id) {
		// TODO Auto-generated method stub
		if (galaRepo.findById(id).isPresent()) {
			Long uid = auth.getUserId();
			String uname=auth.getUserName();
			String role=auth.getRolename();
			
			GalaPrefix entity = galaRepo.findById(id).get();
			GalaPrefix dto1=entity;
//			entity.setId(id);
			entity.setStaff(dto.getStaff());
			entity.setGname(dto.getGname());
			entity.setUpdatedbyname(uname);
			entity.setUpdatedby(uid);
			entity.setUpdatedrole(role);
			entity.setUpdateddate(LocalDate.now());
			entity.setUpdatedtime(LocalTime.now());
			galaRepo.save(dto1);
			return "gala prefix updated succesfully with id"+id;
			
		}
		return "no gala is present with id "+id;
	}

	@Override
	public String deleteGala(long id) {
		if (galaRepo.findById(id).isPresent()) {
			
			galaRepo.deleteById(id);
			return "gala prefix deleted succesfully with id"+id;
			
		}
		return "no gala is present with id "+id;

	}


	


	@Override
	public Map<String, Object> IndexGalaPrefix(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = galaRepo.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGalaPrefix> groups = galaRepo.indexGala(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexGalaPrefixDesc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = galaRepo.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<IndexGalaPrefix> groups = galaRepo.indexGala(p);

		response.put("Index", groups);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchGalaPrefix(int pageno, int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexGalaPrefix> group1 = galaRepo.indexGala(search, p);
		
		long searchcount = group1.size();
		
		response.put("data", group1);
		response.put("SearchCount", searchcount);
		
		return response;
	}

}
