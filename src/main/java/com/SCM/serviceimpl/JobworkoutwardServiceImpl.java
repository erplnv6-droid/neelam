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
import com.SCM.dto.JobworkOutwardDto;
import com.SCM.model.Jobworkoutward;
import com.SCM.repository.JobworkoutwardRepo;
import com.SCM.service.JobworkoutwardService;


@Service
public class JobworkoutwardServiceImpl implements JobworkoutwardService {

	@Autowired
	private JobworkoutwardRepo jobworkoutwardRepo;
	
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
	public Jobworkoutward saveJobWorkOutward(JobworkOutwardDto jobworkOutwardDto) {
		
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();
		
		Jobworkoutward jobworkoutward = new Jobworkoutward();
		
		Jobworkoutward lastid = jobworkoutwardRepo.findTopByOrderByIdDesc();
		String name = "JOBOUT-";
		
		if(lastid == null)
		{
			int count = 1;
		    String s = name + count;
		    jobworkoutward.setJobsheetno(s);
		    jobworkoutward.setDocNo(s);
		   
		    
		}else {
			int count = lastid.getId() + 1;
			String s1 = name + count;
			 System.out.println(s1);
			jobworkoutward.setJobsheetno(s1);
			jobworkoutward.setDocNo(s1);
		}
		
		jobworkoutward.setJobsheetdate(jobworkOutwardDto.getJobsheetdate());
		jobworkoutward.setJobtype(jobworkOutwardDto.getJobtype());
		jobworkoutward.setGrandtotal(jobworkOutwardDto.getGrandtotal());
		jobworkoutward.setRemarks(jobworkOutwardDto.getRemarks());
		jobworkoutward.setSupplier(jobworkOutwardDto.getSupplier());
		jobworkoutward.setWarehouse(jobworkOutwardDto.getWarehouse());
		jobworkoutward.setJobworkoutwarditems(jobworkOutwardDto.getJobworkoutwarditems());
		jobworkoutward.setCreatebyname(uname);
		jobworkoutward.setCreatedby(uid);
		jobworkoutward.setRole(role);
		jobworkoutward.setCreateddate(LocalDate.now());
		jobworkoutward.setCreatedtime(LocalTime.now());
		
		jobworkoutward.setSupplyType("O");
		jobworkoutward.setSubSupplyType("4");
		jobworkoutward.setDocType("CHL");
//		jobworkoutward.setDocNo(jobworkOutwardDto.getJobsheetno());
		jobworkoutward.setDocDate(jobworkOutwardDto.getJobsheetdate());
		jobworkoutward.setFromGstin(jobworkOutwardDto.getFromGstin());
		jobworkoutward.setFromTrdName(jobworkOutwardDto.getFromTrdName());
		jobworkoutward.setFromAddr1(jobworkOutwardDto.getFromAddr1());
		jobworkoutward.setFromAddr2(jobworkOutwardDto.getFromAddr2());
		jobworkoutward.setFromPlace(jobworkOutwardDto.getFromPlace());
		jobworkoutward.setFromPincode(jobworkOutwardDto.getFromPincode());
		jobworkoutward.setActFromStateCode(jobworkOutwardDto.getFromStateCode());
		jobworkoutward.setFromStateCode(jobworkOutwardDto.getFromStateCode());
		jobworkoutward.setToGstin(jobworkOutwardDto.getToGstin());
		jobworkoutward.setToTrdName(jobworkOutwardDto.getToTrdName());
		jobworkoutward.setToAddr1(jobworkOutwardDto.getToAddr1());
		jobworkoutward.setToAddr2(jobworkOutwardDto.getToAddr2());
		jobworkoutward.setToPlace(jobworkOutwardDto.getToPlace());
		jobworkoutward.setToPincode(jobworkOutwardDto.getToPincode());
		jobworkoutward.setActToStateCode(jobworkOutwardDto.getToStateCode());
		jobworkoutward.setToStateCode(jobworkOutwardDto.getToStateCode());
		jobworkoutward.setTransactionType(jobworkOutwardDto.getTransactionType());
		jobworkoutward.setTransporters(jobworkOutwardDto.getTransporters());
		jobworkoutward.setTransporterName(jobworkOutwardDto.getTransporterName());
		jobworkoutward.setTransDocNo(jobworkOutwardDto.getTransDocNo());
		jobworkoutward.setTransMode(jobworkOutwardDto.getTransMode());
		jobworkoutward.setTransDistance(jobworkOutwardDto.getTransDistance());
		jobworkoutward.setTransDocDate(jobworkOutwardDto.getTransDocDate());
		jobworkoutward.setVehicleNo(jobworkOutwardDto.getVehicleNo());
		jobworkoutward.setVehicleType(jobworkOutwardDto.getVehicleType());
		jobworkoutward.setEway_status("Not Generated");
		
		Jobworkoutward save = jobworkoutwardRepo.save(jobworkoutward);
		
		return save;
	}

	@Override
	public List<Jobworkoutward> getAllJobWorkOutward() {
		
		List<Jobworkoutward> all = jobworkoutwardRepo.findAll();
		
		return all;
	}

	
	@Override
	public Jobworkoutward getJobWorkOutwardById(int id) {
		
		Optional<Jobworkoutward> byId = jobworkoutwardRepo.findById(id);
		Jobworkoutward jobworkoutward = byId.get();
		
		return jobworkoutward;
	}

	
	@Override
	public String deleteJobWorkOutward(int id) {
		
		jobworkoutwardRepo.deleteById(id);
		
		return "delete success";
	}

	
	@Override
	public Jobworkoutward updateJobWorkOutward(JobworkOutwardDto jobworkOutwardDto, int id) {
		
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();
		
		Jobworkoutward jobworkoutward = jobworkoutwardRepo.findById(id).get();
		jobworkoutward.setJobsheetno(jobworkOutwardDto.getJobsheetno());
		jobworkoutward.setJobsheetdate(jobworkOutwardDto.getJobsheetdate());
		jobworkoutward.setJobtype(jobworkOutwardDto.getJobtype());
		jobworkoutward.setGrandtotal(jobworkOutwardDto.getGrandtotal());
		jobworkoutward.setRemarks(jobworkOutwardDto.getRemarks());
		jobworkoutward.setSupplier(jobworkOutwardDto.getSupplier());
		jobworkoutward.setWarehouse(jobworkOutwardDto.getWarehouse());
		jobworkoutward.setJobworkoutwarditems(jobworkOutwardDto.getJobworkoutwarditems());
		jobworkoutward.setUpdatedbyname(uname);
		jobworkoutward.setUpdatedby(uid);
		jobworkoutward.setUpdatedrole(role);
		jobworkoutward.setUpdateddate(LocalDate.now());
		jobworkoutward.setUpdatedtime(LocalTime.now());
		
		jobworkoutward.setSupplyType("O");
		jobworkoutward.setSubSupplyType("4");
		jobworkoutward.setDocType("CHL");
		jobworkoutward.setDocNo(jobworkOutwardDto.getJobsheetno());
		jobworkoutward.setDocDate(jobworkOutwardDto.getJobsheetdate());
		jobworkoutward.setFromGstin(jobworkOutwardDto.getFromGstin());
		jobworkoutward.setFromTrdName(jobworkOutwardDto.getFromTrdName());
		jobworkoutward.setFromAddr1(jobworkOutwardDto.getFromAddr1());
		jobworkoutward.setFromAddr2(jobworkOutwardDto.getFromAddr2());
		jobworkoutward.setFromPlace(jobworkOutwardDto.getFromPlace());
		jobworkoutward.setFromPincode(jobworkOutwardDto.getFromPincode());
		jobworkoutward.setActFromStateCode(jobworkOutwardDto.getFromStateCode());
		jobworkoutward.setFromStateCode(jobworkOutwardDto.getFromStateCode());
		jobworkoutward.setToGstin(jobworkOutwardDto.getToGstin());
		jobworkoutward.setToTrdName(jobworkOutwardDto.getToTrdName());
		jobworkoutward.setToAddr1(jobworkOutwardDto.getToAddr1());
		jobworkoutward.setToAddr2(jobworkOutwardDto.getToAddr2());
		jobworkoutward.setToPlace(jobworkOutwardDto.getToPlace());
		jobworkoutward.setToPincode(jobworkOutwardDto.getToPincode());
		jobworkoutward.setActToStateCode(jobworkOutwardDto.getToStateCode());
		jobworkoutward.setToStateCode(jobworkOutwardDto.getToStateCode());
		jobworkoutward.setTransactionType(jobworkOutwardDto.getTransactionType());

		jobworkoutward.setTransporters(jobworkOutwardDto.getTransporters());
		jobworkoutward.setTransporterName(jobworkOutwardDto.getTransporterName());
		jobworkoutward.setTransDocNo(jobworkOutwardDto.getTransDocNo());
		jobworkoutward.setTransMode(jobworkOutwardDto.getTransMode());
		jobworkoutward.setTransDistance(jobworkOutwardDto.getTransDistance());
		jobworkoutward.setTransDocDate(jobworkOutwardDto.getTransDocDate());
		jobworkoutward.setVehicleNo(jobworkOutwardDto.getVehicleNo());
		jobworkoutward.setVehicleType(jobworkOutwardDto.getVehicleType());
		jobworkoutward.setEway_status("Not Generated");
		
		
		Jobworkoutward save = jobworkoutwardRepo.save(jobworkoutward);
		
		return save;
	}

	@Override
	public Map<String, Object> IndexJobWorkOutwardAsc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		
	    long countpages = jobworkoutwardRepo.count();
	    long pages = countpages / pagesize;
	    
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}
		
		List<IndexJobworkInward> indexJobWorkOutward = jobworkoutwardRepo.IndexJobWorkOutward(p);
		
		response.put("Index", indexJobWorkOutward);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexJobWorkOutwardDesc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		
	    long countpages = jobworkoutwardRepo.count();
	    long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}
	    
		List<IndexJobworkInward> indexJobWorkOutward = jobworkoutwardRepo.IndexJobWorkOutward(p);
		
		response.put("Index", indexJobWorkOutward);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchByJobWorkOutward(int pageno, int pagesize, String search) {
		
		Pageable p = PageRequest.of(pageno, pagesize);
		Map<String, Object> response = new HashMap<>();		

		 List<IndexJobworkInward> searchByJobWorkOutward = jobworkoutwardRepo.SearchByJobWorkOutward(search, p);
		long searchcount = searchByJobWorkOutward.size();
		
		response.put("data", searchByJobWorkOutward);
		response.put("SearchCount", searchcount);
		
		return response;
	}

}
