package com.SCM.serviceimpl;

import com.SCM.IndexDto.IndexBrand;
import com.SCM.model.ActivityLog;
import com.SCM.model.Brand;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.BrandRepo;
import com.SCM.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandRepo brandRepo;

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
	public Brand saveBrand(Brand brand) {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		brand.setCreatebyname(uname);
		brand.setCreatedby(uid);
		brand.setRole(role);
		brand.setCreateddate(LocalDate.now());
		brand.setCreatedtime(LocalTime.now());

		Brand b = brandRepo.save(brand);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setBrandId(b.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return b;

	}

	@Override
	public List<Brand> getAllBrands() {

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String loggeduser = userDetailsImpl.getEmail();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentname = (String) authentication.getName();

		System.out.println(loggeduser);
		System.out.println(currentname);

		return brandRepo.findAll();
	}

	@Override
	public Brand getBrandById(long id) {
		return brandRepo.findById(id).get();
	}

	@Override
	public String deleteBrand(long id) {
		brandRepo.deleteById(id);
		return "Company Removed !!" + id;
	}

	@Override
	public Brand updateBrand(Brand brand, long id) {

		Brand existingBrand = brandRepo.findById(id).orElse(null);

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		existingBrand.setUpdatedbyname(uname);
		existingBrand.setUpdatedby(uid);
		existingBrand.setUpdatedrole(role);
		existingBrand.setUpdateddate(LocalDate.now());
		existingBrand.setUpdatedtime(LocalTime.now());

		existingBrand.setBrandName(brand.getBrandName());

		Brand b = brandRepo.save(existingBrand);

		System.out.println(b);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setBrandId(b.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return b;
	}

//	-----------------------------  sahil -------------------------------------//

	@Override
	public Map<String, Object> IndexBrandAsc(int pagno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = brandRepo.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexBrand> ipo = brandRepo.indexBrand(p);

		System.out.println(ipo);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexBrandDesc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = brandRepo.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexBrand> ipo = brandRepo.indexBrand(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchBrand(int pageno, int pagesize, String search) {

		Pageable p = PageRequest.of(pageno, pagesize);
		Map<String, Object> response = new HashMap<>();

		List<IndexBrand> brands = brandRepo.SearchByBrand(search, p);

		long searchcount = brands.size();

		response.put("data", brands);
		response.put("SearchCount", searchcount);

		return response;
	}

}
