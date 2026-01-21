package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
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

import com.SCM.IndexDto.IndexBranch;
import com.SCM.dto.BranchDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.Branch;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.BranchRepo;
import com.SCM.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService {

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private ActivityLogRepo activityLogRepo;

	
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
	public Branch savebranch(BranchDto branchDto) {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		Branch b = new Branch();
		b.setCreatebyname(uname);
		b.setCreatedby(uid);
		b.setRole(role);

		b.setAccountno(branchDto.getAccountno());
		b.setAddress(branchDto.getAddress());
		b.setBankname(branchDto.getBankname());
		b.setBranch(branchDto.getBranch());
		b.setBranchname(branchDto.getBranchname());
		b.setEmail(branchDto.getEmail());
		b.setGstno(branchDto.getGstno());
		b.setIfsc(branchDto.getIfsc());
		b.setPhoneno(branchDto.getPhoneno());
		b.setZipcode(branchDto.getZipcode());
		b.setCountry(branchDto.getCountry());
		b.setStates(branchDto.getStates());
		b.setCities(branchDto.getCities());
		b.setCreateddate(LocalDate.now());
		b.setCreatedtime(LocalTime.now());

		Branch br = branchRepo.save(b);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setBranchid((long) br.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return br;
	}

	@Override
	public List<Branch> showbranch() {

		List<Branch> b = branchRepo.findAll();

		if (b.isEmpty()) {
			throw new RuntimeException("Branch List is Completely empty, we have nothing");
		}

		return b;
	}

	@Override
	public Branch branchbyId(int id) {

		Optional<Branch> b = branchRepo.findById(id);

		if (b.isPresent()) {
			Branch branch = b.get();

			return branch;
		}
		return null;

	}

	@Override
	public Branch updatebranch(Branch branch, int id) {

		Optional<Branch> branchid = branchRepo.findById(id);

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		if (branchid.isPresent()) {

			Branch b = branchid.get();

			b.setUpdatedbyname(uname);
			b.setUpdatedby(uid);
			b.setUpdatedrole(role);
			
			b.setUpdateddate(LocalDate.now());
			b.setUpdatedtime(LocalTime.now());
			b.setAccountno(branch.getAccountno());
			b.setAddress(branch.getAddress());
			b.setBankname(branch.getBankname());
			b.setBranch(branch.getBranch());
			b.setBranchname(branch.getBranchname());
			b.setEmail(branch.getEmail());
			b.setGstno(branch.getGstno());
			b.setIfsc(branch.getIfsc());
			b.setPhoneno(branch.getPhoneno());
			b.setZipcode(branch.getZipcode());
			b.setCountry(branch.getCountry());
			b.setStates(branch.getStates());
			b.setCities(branch.getCities());

			Branch br = branchRepo.save(b);

			UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Long loggeduser = userDetailsImpl.getId();
			ActivityLog activityLog = new ActivityLog();

			activityLog.setUpdatedate(new Date());
			activityLog.setUpdatedtime(LocalTime.now());
			activityLog.setBranchid((long) b.getId());
			activityLog.setLoggeduser(loggeduser);

			activityLogRepo.save(activityLog);

			return br;
		}
		return null;
	}

	@Override
	public void deletebranch(int id) {

		branchRepo.deleteById(id);


	}

	@Override
	public Map<String, Object> IndexBranchAsc(int pagno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = branchRepo.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexBranch> branches = branchRepo.indexBranch(p);

		System.out.println(branches);

		response.put("Index", branches);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexBranchDesc(int pagno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = branchRepo.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexBranch> branches = branchRepo.indexBranch(p);

		System.out.println(branches);

		response.put("Index", branches);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchBranch(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexBranch> branch = branchRepo.SearchByBranch(search, p);

		long searchcount = branch.size();

		response.put("data", branch);
		response.put("SearchCount", searchcount);

		return response;
	}

}
