package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexJobworkInward;
import com.SCM.dto.JobworkInwardDto;
import com.SCM.model.JobworkInward;
import com.SCM.repository.JobWorkInwardRepo;
import com.SCM.service.JobWorkInwardService;


@Service
public class JobWorkInwardServiceImpl implements JobWorkInwardService {

	
	@Autowired
	private JobWorkInwardRepo jobWorkInwardRepo;
	
	public long getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long uid = userDetails.getId();

		return uid;
	}

	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String username = userDetails.getUsername();
		return username;
	}

	public String getRolename() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		String role = list.get(0);
		return role;
	}

	
	@Override
	public JobworkInward saveJobWorkInward(JobworkInwardDto jobworkInwardDto) {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();
		
		JobworkInward jobworkInward = new JobworkInward();
		
		JobworkInward lastid = jobWorkInwardRepo.findTopByOrderByIdDesc();
		String name = "JOBIN-";
		int count = 1;
		
		
		if(lastid == null)
		{
			String s = name + count;
			jobworkInward.setJobsheetno(s);
		}
		else {
			System.out.println("++++++");
			int count1 = lastid.getId() + 1;
			String s1 = name + count1;
			
			System.out.println(s1 + "+++++++++++++++");
			
			jobworkInward.setJobsheetno(s1);
		}
		
		jobworkInward.setJobsheetdate(jobworkInwardDto.getJobsheetdate());
		jobworkInward.setJobtype(jobworkInwardDto.getJobtype());
		jobworkInward.setRemarks(jobworkInwardDto.getRemarks());
		jobworkInward.setGrandtotal(jobworkInwardDto.getGrandtotal());
		jobworkInward.setWarehouse(jobworkInwardDto.getWarehouse());
		jobworkInward.setSupplier(jobworkInwardDto.getSupplier());
		jobworkInward.setJobsheetItems(jobworkInwardDto.getJobsheetItems());
		jobworkInward.setCreatebyname(uname);
		jobworkInward.setCreatedby(uid);
		jobworkInward.setRole(role);
		jobworkInward.setCreateddate(LocalDate.now());
		jobworkInward.setCreatedtime(LocalTime.now());

		JobworkInward save = jobWorkInwardRepo.save(jobworkInward);

		return save;
	}

	
	@Override
	public List<JobworkInward> getAllJobWorkInward() {

		return jobWorkInwardRepo.findAll();
	}

	@Override
	public JobworkInward getJobWorkInwardById(int id) {

		JobworkInward byId = jobWorkInwardRepo.findById(id).get();
		return byId;
	}

	
	@Override
	public String deleteJobWorkInward(int id) {

		jobWorkInwardRepo.deleteById(id);
		return "delete sucess";
	}

	
	@Override
	public JobworkInward updateJobWorkInward(JobworkInwardDto jobworkInwardDto, int id) {

		Optional<JobworkInward> byId = jobWorkInwardRepo.findById(id);
		
		if (!byId.isPresent()) {
			
			throw new RuntimeException("jobwork id not found");
		}

		
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();
		
		JobworkInward jobworkInward = byId.get();
		jobworkInward.setJobsheetno(jobworkInwardDto.getJobsheetno());
		jobworkInward.setJobsheetdate(jobworkInwardDto.getJobsheetdate());
		jobworkInward.setJobtype(jobworkInwardDto.getJobtype());
		jobworkInward.setRemarks(jobworkInwardDto.getRemarks());
		jobworkInward.setGrandtotal(jobworkInwardDto.getGrandtotal());
		jobworkInward.setWarehouse(jobworkInwardDto.getWarehouse());
		jobworkInward.setSupplier(jobworkInwardDto.getSupplier());
		jobworkInward.setJobsheetItems(jobworkInwardDto.getJobsheetItems());
		jobworkInward.setUpdatedbyname(uname);
		jobworkInward.setUpdatedby(uid);
		jobworkInward.setUpdatedrole(role);
		jobworkInward.setUpdateddate(LocalDate.now());
		jobworkInward.setUpdatedtime(LocalTime.now());

		JobworkInward save = jobWorkInwardRepo.save(jobworkInward);

		return save;
	}


	@Override
	public Map<String, Object> IndexJobWorkInwardAsc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		
	    long countpages = jobWorkInwardRepo.count();
	    long pages = countpages / pagesize;
	    
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}
		
		List<IndexJobworkInward> indexJobWorkInward = jobWorkInwardRepo.IndexJobWorkInward(p);
		
		response.put("Index", indexJobWorkInward);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}


	@Override
	public Map<String, Object> IndexJobWorkInwardDesc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		
	    long countpages = jobWorkInwardRepo.count();
	    long pages = countpages / pagesize;
	    
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}
		
		List<IndexJobworkInward> indexJobWorkInward = jobWorkInwardRepo.IndexJobWorkInward(p);
		
		response.put("Index", indexJobWorkInward);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}


	@Override
	public Map<String, Object> SearchByJobWorkInward(int pageno, int pagesize, String search) {
		
		Pageable p = PageRequest.of(pageno, pagesize);
		Map<String, Object> response = new HashMap<>();		

		 List<IndexJobworkInward> searchByJobWorkInward = jobWorkInwardRepo.SearchByJobWorkInward(search, p);
		long searchcount = searchByJobWorkInward.size();
		
		response.put("data", searchByJobWorkInward);
		response.put("SearchCount", searchcount);
		
		return response;
	}

}
