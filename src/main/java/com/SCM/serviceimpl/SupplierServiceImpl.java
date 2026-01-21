package com.SCM.serviceimpl;

import com.SCM.IndexDto.IndexSupplier;
import com.SCM.dto.SupplierDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.ERole;
import com.SCM.model.Role;
import com.SCM.model.Supplier;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.RoleRepository;
import com.SCM.repository.SupplierRepository;
import com.SCM.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

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
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		String role = list.get(0);
		return role;
	}

	
	@Override
	public Supplier saveSupplier(SupplierDto supplierDto) {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		Set<Role> roles = new HashSet<>();
		Role r = roleRepository.findByName(ERole.ROLE_SUPPLIER).get();
		roles.add(r);

		Supplier supplier = new Supplier();
		
		supplier.setCreatebyname(uname);
		supplier.setCreatedby(uid);
		supplier.setRole(role);
		supplier.setAccountno(supplierDto.getAccountno());
		supplier.setAddress(supplierDto.getAddress());
		supplier.setBankname(supplierDto.getBankname());
		supplier.setBranch(supplierDto.getBranch());
		supplier.setCompanyname(supplierDto.getCompanyname());
		supplier.setGstno(supplierDto.getGstno());
		supplier.setIfsc(supplierDto.getIfsc());
		supplier.setOpeningbal(supplierDto.getOpeningbal());
		supplier.setOpeningbaldate(supplierDto.getOpeningbaldate());
		supplier.setOpeningbaltype(supplierDto.getOpeningbaltype());
		supplier.setPhonenumber(supplierDto.getPhonenumber());
		supplier.setStatecode(supplierDto.getStatecode());
		supplier.setSuppliername(supplierDto.getSuppliername());
		supplier.setZipcode(supplierDto.getZipcode());
		supplier.setCountry(supplierDto.getCountry());
		supplier.setCities(supplierDto.getCities());
		supplier.setEmail(supplierDto.getEmail());
		supplier.setStates(supplierDto.getStates());
		supplier.setPassword(encoder.encode(supplierDto.getPassword()));
		supplier.setPasswordDecrypted(supplierDto.getPassword());
		supplier.setState_Zone(supplierDto.getState_Zone());
		supplier.setSupplierSubContacts(supplierDto.getSupplierSubContacts());
		supplier.setRoles(roles);
		supplier.setCreateddate(LocalDate.now());
		supplier.setCreatedtime(LocalTime.now());
		supplier.setColourtype(supplierDto.getColourtype());
		supplier.setTermsofpayment(supplierDto.getTermsofpayment());
		supplier.setEmailLoginStatus(supplierDto.getEmailLoginStatus());
		supplier.setSupplierAddresses(supplierDto.getSupplierAddresses());
		
		Supplier sp = supplierRepository.save(supplier);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setSupplierid((long) sp.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return sp;
	}

	@Override
	public List<Supplier> getAllSuppliers() {
		return supplierRepository.findAllSupplier();
	}

	@Override
	public List<SupplierDto> getAllSupplierIndex() {

		List<SupplierDto> supplierDtos = new ArrayList<>();

		for (Supplier supplier : supplierRepository.findAll()) {
			SupplierDto supplierDto = new SupplierDto();
			supplierDto.setId(supplier.getId());
			supplierDto.setAccountno(supplier.getAccountno());
			supplierDto.setAddress(supplier.getAddress());
			supplierDto.setBankname(supplier.getBankname());
			supplierDto.setBranch(supplier.getBranch());
			supplierDto.setCompanyname(supplier.getCompanyname());
			supplierDto.setGstno(supplier.getGstno());
			supplierDto.setIfsc(supplier.getIfsc());
			supplierDto.setOpeningbal(supplier.getOpeningbal());
			supplierDto.setOpeningbaldate(supplier.getOpeningbaldate());
			supplierDto.setOpeningbaltype(supplier.getOpeningbaltype());
			supplierDto.setPhonenumber(supplier.getPhonenumber());
			supplierDto.setStatecode(supplier.getStatecode());
			supplierDto.setSuppliername(supplier.getSuppliername());
			supplierDto.setZipcode(supplier.getZipcode());
			supplierDto.setCountry(supplier.getCountry());
			supplierDto.setCities(supplier.getCities());
			supplierDto.setEmail(supplier.getEmail());
			supplierDto.setState_Zone(supplier.getState_Zone());
			supplierDto.setStates(supplier.getStates());
			supplierDto.setSupplierSubContacts(supplier.getSupplierSubContacts());
			supplierDto.setRoles(supplier.getRoles());
			supplierDto.setSupplierAddresses(supplier.getSupplierAddresses());
			supplierDto.setColourtype(supplier.getColourtype());

			supplierDtos.add(supplierDto);
		}

		return supplierDtos;
	}

	@Override
	public Supplier getSupplierById(int id) {
		return supplierRepository.findById(id).orElse(null);
	}

	@Override
	public String deleteSupplier(int id) {
		supplierRepository.deleteById(id);
		return "Supplier Removed !!" + id;
	}

	@Override
	public Supplier updateSupplier(SupplierDto supplierDto, int id) {

		Supplier supplier = supplierRepository.findById(id).orElse(null);

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		supplier.setAccountno(supplierDto.getAccountno());
		supplier.setAddress(supplierDto.getAddress());
		supplier.setBankname(supplierDto.getBankname());
		supplier.setBranch(supplierDto.getBranch());
		supplier.setCompanyname(supplierDto.getCompanyname());
		supplier.setGstno(supplierDto.getGstno());
		supplier.setIfsc(supplierDto.getIfsc());
		supplier.setOpeningbal(supplierDto.getOpeningbal());
		supplier.setOpeningbaldate(supplierDto.getOpeningbaldate());
		supplier.setOpeningbaltype(supplierDto.getOpeningbaltype());
		supplier.setPhonenumber(supplierDto.getPhonenumber());
		supplier.setStatecode(supplierDto.getStatecode());
		supplier.setSuppliername(supplierDto.getSuppliername());
		supplier.setZipcode(supplierDto.getZipcode());
		supplier.setCountry(supplierDto.getCountry());
		supplier.setCities(supplierDto.getCities());
		supplier.setEmail(supplierDto.getEmail());
		supplier.setStates(supplierDto.getStates());
		supplier.setState_Zone(supplierDto.getState_Zone());
		supplier.setColourtype(supplierDto.getColourtype());
		supplier.setEmailLoginStatus(supplierDto.getEmailLoginStatus());
		supplier.setSupplierAddresses(supplierDto.getSupplierAddresses());
		supplier.setTermsofpayment(supplierDto.getTermsofpayment());
		supplier.setUpdatedbyname(uname);
		supplier.setUpdatedby(uid);
		supplier.setUpdatedrole(role);
		supplier.setUpdateddate(LocalDate.now());
		supplier.setUpdatedtime(LocalTime.now());

		if (supplierDto.getPassword() != "") {
			System.out.println("Password Changes " + supplierDto.getPassword());
			supplier.setPassword(encoder.encode(supplierDto.getPassword()));
			supplier.setPasswordDecrypted(supplierDto.getPassword());
		}

		Supplier sp = supplierRepository.save(supplier);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setSupplierid((long) sp.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return sp;
	}

	@Override
	public Map<String, Object> IndexSupplierAsc(int pagno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = supplierRepository.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}
		List<IndexSupplier> supplier = supplierRepository.indexSupplier(p);

		System.out.println(supplier);

		response.put("Index", supplier);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexSupplierDesc(int pagno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = supplierRepository.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}
		List<IndexSupplier> supplier = supplierRepository.indexSupplier(p);

		System.out.println(supplier);

		response.put("Index", supplier);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchSupplier(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexSupplier> suppliers = supplierRepository.SearchBySupplier(search, p);

		long searchcount = suppliers.size();

		response.put("data", suppliers);
		response.put("SearchCount", searchcount);

		return response;
	}
}
