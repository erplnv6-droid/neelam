package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SCM.ExportDto.ExportStaff;
import com.SCM.IndexDto.IndexStaff;
import com.SCM.IndexDto.IndexStaffByStates;
import com.SCM.IndexDto.IndexStaffDto;
import com.SCM.IndexDto.IndexStatesByZones;
import com.SCM.IndexDto.MultipleToStaffDto;
import com.SCM.dto.StaffDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.MultipleStaff;
import com.SCM.model.Staff;
import com.SCM.payload.StaffRequest;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.MultipleToStaffRepo;
import com.SCM.repository.StaffRepo;
import com.SCM.service.StaffService;

@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffRepo staffRepo;
	
	@Autowired
	private MultipleToStaffRepo multipleToStaffRepo;
	
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private ActivityLogRepo activityLogRepo;

	public long getUserId() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		 Long uid = userDetails.getId();

		 return uid;
	}
	
	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		String username = userDetails.getUsername();
		return username;
	}

	public String getRolename() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		String role=list.get(0);
		return role;
	}
	

	@Override
	public List<StaffDto> getAllStaff() {
		List<Staff> staff = staffRepo.findAll();
		return mapToListDto(staff);
	}

	@Override
	public List<StaffDto> getStaffByAseId(int aseId) {
		List<Staff> s = staffRepo.getStaffByAseId(aseId);
		return mapToListDto(s);
	}

	@Override
	public List<StaffDto> getStaffByAsmId(int asmId) {
		List<Staff> s = staffRepo.getStaffByAsmId(asmId);
		return mapToListDto(s);
	}

	@Override
	public List<StaffDto> getStaffByRsmId(int rsmId) {
		List<Staff> s = staffRepo.getStaffByRsmId(rsmId);
		return mapToListDto(s);
	}

	@Override
	public List<StaffDto> getStaffByNsmId(int nsmId) {
		List<Staff> s = staffRepo.getStaffByNsmId(nsmId);
		return mapToListDto(s);
	}

	@Override
	public List<StaffDto> getRsmZoneId(int zoneId) {
		List<Staff> s = staffRepo.getRsmByZoneId(zoneId);
		return mapToListDto(s);
	}

	@Override
	public List<StaffDto> getAsmByZoneId(int zoneId) {
		List<Staff> s = staffRepo.getAsmByZoneId(zoneId);
		return mapToListDto(s);
	}

	@Override
	public List<StaffDto> getAseByZoneId(int zoneId) {
		List<Staff> s = staffRepo.getAseByZoneId(zoneId);
		return mapToListDto(s);
	}

	@Override
	public List<StaffDto> getStaffByZoneId(int zoneId) {
		List<Staff> s = staffRepo.getStaffByZoneId(zoneId);
		return mapToListDto(s);
	}

//	@Override
//	public List<IndexStaffDto> getStaffByStateId(int stateId) {
//		List<IndexStaffDto> s = staffRepo.getStaffBystateId(stateId);
//		return mapToListDto(s);
//	}

	@Override
	public List<StaffDto> getStaffAsmByRsmId(int rsmId) {
		List<Staff> s = staffRepo.getStaffASMBYRSMId(rsmId);
		return mapToListDto(s);
	}

	@Override
	public List<StaffDto> getStaffAseByRsmId(int rsmId) {
		List<Staff> s = staffRepo.getStaffASEBYRSMId(rsmId);
		return mapToListDto(s);
	}

	@Override
	public List<StaffDto> getStaffAseByASMId(int asmId) {
		List<Staff> s = staffRepo.getStaffASEBYASMId(asmId);
		return mapToListDto(s);
	}

	@Override
	public List<StaffDto> getStaffByNsm() {

		List<Staff> s = staffRepo.getStaffNsm();
		return mapToListDto(s);
	}

	@Override
	public List<StaffDto> getASMByAsmId(int id) {

		List<Staff> s = staffRepo.getASMByAsmId(id);
		return mapToListDto(s);
	}

	@Override
	public List<StaffDto> getASEByAseId(int id) {
		List<Staff> s = staffRepo.getASEByAseId(id);
		return mapToListDto(s);
	}

	public List<StaffDto> mapToListDto(List<Staff> staff) {

		List<StaffDto> sd = new ArrayList<>();

		for (Staff s : staff) {

			Long uid = getUserId();
			String uname=getUserName();

			String role=getRolename();
			
			StaffDto staffDto = new StaffDto();
			staffDto.setCreatebyname(uname);
			staffDto.setCreatedby(uid);
			staffDto.setRole(role);

			staffDto.setId(s.getId());
			staffDto.setAadharNumber(s.getAadharNumber());
			staffDto.setAccountNumber(s.getAccountNumber());
			staffDto.setAddress(s.getAddress());
			staffDto.setStaffName(s.getStaffName());
			staffDto.setBankDetail(s.getBankDetail());
			staffDto.setBankName(s.getBankName());
			staffDto.setBankDetail(s.getBankDetail());
			staffDto.setSpouseName(s.getSpouseName());
			staffDto.setSalary(s.getSalary());
			staffDto.setPassword(s.getPassword());
			staffDto.setPanNumber(s.getPanNumber());
			staffDto.setMobileNumber(s.getMobileNumber());
			staffDto.setIfscCode(s.getIfscCode());
			staffDto.setGender(s.getGender());
			staffDto.setFatherName(s.getFatherName());
			staffDto.setEmail(s.getEmail());
			staffDto.setDoj(s.getDoj());
			staffDto.setDateOfBirth(s.getDateOfBirth());
			staffDto.setDateOfAnniversary(s.getDateOfAnniversary());
			staffDto.setBranchName(s.getBranchName());
			staffDto.setBloodGroup(s.getBloodGroup());
			staffDto.setArea(s.getArea());
			staffDto.setNsmId(s.getNsmId());
			staffDto.setRsmId(s.getRsmId());
			staffDto.setAsmId(s.getAsmId());
			staffDto.setAseId(s.getAseId());
			staffDto.setRoles(s.getRoles());
			staffDto.getZoneId();
			staffDto.setRoles(s.getRoles());
			staffDto.setBranch(s.getBranch());
			staffDto.setStateZoneId(s.getStateZoneId());
			staffDto.setEmail(s.getEmailLoginStatus());

			sd.add(staffDto);
		}
		return sd;
	}

	
	
	@Override
	public Staff updateStaff(Staff staff, long id) {

		Optional<Staff> existStaff = staffRepo.findById(id);
			
		Long uid = getUserId();
		String uname=getUserName();
		String role=getRolename();
		
		Staff s = existStaff.get();
		s.setAadharNumber(staff.getAadharNumber());
		s.setAccountNumber(staff.getAccountNumber());
		s.setAddress(s.getAddress());
		s.setStaffName(staff.getStaffName());
		s.setBankDetail(staff.getBankDetail());
		s.setBankName(staff.getBankName());
		s.setBankDetail(staff.getBankDetail());
		s.setSpouseName(staff.getSpouseName());
		s.setSalary(staff.getSalary());
		s.setPanNumber(staff.getPanNumber());
		s.setMobileNumber(staff.getMobileNumber());
		s.setIfscCode(staff.getIfscCode());
		s.setGender(staff.getGender());
		s.setFatherName(staff.getFatherName());
		s.setEmail(staff.getEmail());
		s.setDoj(staff.getDoj());
		s.setDateOfBirth(staff.getDateOfBirth());
		s.setDateOfAnniversary(staff.getDateOfAnniversary());
		s.setBranchName(staff.getBranchName());
		s.setBloodGroup(staff.getBloodGroup());
		s.setArea(staff.getArea());
		s.setNsmId(staff.getNsmId());
		s.setRsmId(staff.getRsmId());
		s.setAsmId(staff.getAsmId());
		s.setAseId(staff.getAseId());
		s.setStateZoneId(staff.getStateZoneId());
		s.setRoles(staff.getRoles());
		s.setEmailLoginStatus(staff.getEmailLoginStatus());
		s.setUpdatedbyname(uname);
		s.setUpdatedby(uid);
		s.setUpdatedrole(role);
		s.setUpdateddate(LocalDate.now());
		s.setUpdatedtime(LocalTime.now());

		if (!staff.getPassword().isEmpty()) {
			System.out.println("password change:" + staff.getPassword());
	
//			s.setPassword(encoder.encode(staff.getPassword()));
			
			s.setPassword(encoder.encode(staff.getPasswordDecrypted()));
			
			s.setPasswordDecrypted(staff.getPasswordDecrypted());
			
		}
		
	
//		if(staffRepo.findById(uid).get().getPasswordDecrypted().equals(s.getPasswordDecrypted()))
//		{
//			s.setPasswordDecrypted(staff.getPassword());
//		}
//	
//		else
//		{
//			s.setPasswordDecrypted(staff.getPassword());
//			
//			s.setPassword(encoder.encode(staff.getPassword()));
//		}
//		
		
		Staff st = staffRepo.save(s);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setStaffid((long) st.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return st;
	}

	
	@Override
	public Staff updateStaff(StaffRequest staffRequest, long id) {
		
		Optional<Staff> existStaff = staffRepo.findById(id);
		
//		code for multiple  staff
		
	    List<MultipleStaff> MultipleStaffList = staffRequest.getMultipleStaffDtos().stream()
	        .flatMap(staffDto -> {
	            	        	
	        	List<MultipleStaff> staffList = new ArrayList<>();

	            // Find the maximum length of the lists
	            int maxLength = Math.max(staffDto.getRsmid().size(), 
                                Math.max(staffDto.getAsmid().size(), 
                                Math.max(staffDto.getAseid().size(),
                                         staffDto.getNsmid().size())));
	                       

	            for (int i = 0; i < maxLength; i++) {
	                
	            	MultipleStaff MultipleStaff = new MultipleStaff();
	            	               
	                // Set rsmid if available
	                if (i < staffDto.getRsmid().size()) {
	                	MultipleStaff.setRsmid(staffDto.getRsmid().get(i));
	                    
	                }

	                // Set asmid if available
	                if (i < staffDto.getAsmid().size()) {
	                	MultipleStaff.setAsmid(staffDto.getAsmid().get(i));
	                }

	                // Set aseid if available
	                if (i < staffDto.getAseid().size()) {
	                	MultipleStaff.setAseid(staffDto.getAseid().get(i));
	                }
	                
	                // set nsmid if available
	                if (i < staffDto.getNsmid().size()) {
	                	MultipleStaff.setNsmid(staffDto.getNsmid().get(i));
	                }

	                staffList.add(MultipleStaff);
	            }

	            return staffList.stream();
	        }).collect(Collectors.toList());

//	    ==============================================================

		
		Long uid = getUserId();
		String uname=getUserName();
		String role=getRolename();
		
		Staff s = existStaff.get();
		s.setAadharNumber(staffRequest.getAadharNumber());
		s.setAccountNumber(staffRequest.getAccountNumber());
		s.setAddress(staffRequest.getAddress());
		s.setStaffName(staffRequest.getStaffName());
		s.setBankDetail(staffRequest.getBankDetail());
		s.setBankName(staffRequest.getBankName());
		s.setBankDetail(staffRequest.getBankDetail());
		s.setSpouseName(staffRequest.getSpouseName());
		s.setSalary(staffRequest.getSalary());
		s.setPanNumber(staffRequest.getPanNumber());
		s.setMobileNumber(staffRequest.getMobileNumber());
		s.setIfscCode(staffRequest.getIfscCode());
		s.setGender(staffRequest.getGender());
		s.setFatherName(staffRequest.getFatherName());
		s.setEmail(staffRequest.getEmail());
		s.setDoj(staffRequest.getDoj());
		s.setDateOfBirth(staffRequest.getDateOfBirth());
		s.setDateOfAnniversary(staffRequest.getDateOfAnniversary());
		s.setBranchName(staffRequest.getBranchName());
		s.setBloodGroup(staffRequest.getBloodGroup());
		s.setArea(staffRequest.getArea());
		s.setNsmId(staffRequest.getNsmId());
		s.setRsmId(staffRequest.getRsmId());
		s.setAsmId(staffRequest.getAsmId());
		s.setAseId(staffRequest.getAseId());
		s.setStateZoneId(staffRequest.getStateZoneId());
		s.setRoles(staffRequest.getRoles());
		s.setEmailLoginStatus(staffRequest.getEmailLoginStatus());
		s.setMultipleStaffe(MultipleStaffList);
		s.setZones(staffRequest.getZones());
		s.setStatezones(staffRequest.getStatezones());
		s.setSalesexpwithincity(staffRequest.getSalesexpwithincity());
		s.setSalesexpoutsidecity(staffRequest.getSalesexpoutsidecity());
		s.setSalesexpdaytrip(staffRequest.getSalesexpdaytrip());
		s.setUpdatedbyname(uname);
		s.setUpdatedby(uid);
		s.setUpdatedrole(role);
		s.setUpdateddate(LocalDate.now());
		s.setUpdatedtime(LocalTime.now());

		if (!staffRequest.getPassword().isEmpty()) {
			System.out.println("password change:" + staffRequest.getPassword());

			s.setPassword(encoder.encode(staffRequest.getPassword()));
			s.setPasswordDecrypted(staffRequest.getPassword());
		}

		Staff st = staffRepo.save(s);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setStaffid((long) st.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return st;
	}
	
	
	
	@Override
	public String deleteStaff(long id) {

		staffRepo.deleteById(id);
		return "delete successfully";
	}

	@Override
	public Staff getStaffById(long id) {

		Optional<Staff> s = staffRepo.findById(id);
		Staff staff = s.get();
		return staff;
	}

	@Override
	public Map<String, Object> IndexStaffAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		Long rid=userDetails.getId();
		int riid = (int) (long) rid;
		
		
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

		
		for(String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				
				long countpages = staffRepo.count();
				long pages = countpages / pagesize;
				List<IndexStaff> is=staffRepo.indexstaff(p);

				    response.put("Index", is);
				    response.put("Enteries", countpages);
				    response.put("Pages", pages);
				    return response;
			}
			
	         else if(s.equals("ROLE_RSM")) {
	        	 long countpages = staffRepo.indexstaffByRsmId(riid).size();
					long pages = countpages / pagesize;
					List<IndexStaff> is=staffRepo.indexstaffByRsmId(riid,p);
					 response.put("Index", is);
					    response.put("Enteries", countpages);
					    response.put("Pages", pages);
					    return response;
	        	 
	         }
			
	         else if(s.equals("ROLE_ASM")) {
	        	 long countpages = staffRepo.indexstaffByAsmId(riid).size();
					long pages = countpages / pagesize;
					List<IndexStaff> is=staffRepo.indexstaffByAsmId(riid,p);
					 response.put("Index", is);
					    response.put("Enteries", countpages);
					    response.put("Pages", pages);
					    return response;
	        	 
	         }
	         else {
	        	 return null;
	         }

		}
		return null;

	}

	@Override
	public Map<String, Object> IndexStaffDSC(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		Long rid=userDetails.getId();
		int riid = (int) (long) rid;
		
		
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

		
		
		for(String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				
				long countpages = staffRepo.count();
				long pages = countpages / pagesize;
				
				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}
				
				List<IndexStaff> is=staffRepo.indexstaff(p);
				
				
				 response.put("Index", is);
				    response.put("Enteries", countpages);
				    response.put("Pages", pages);
				    return response;
			}
			
	         else if(s.equals("ROLE_RSM")) {
	        	 long countpages = staffRepo.indexstaffByRsmId(riid).size();
					long pages = countpages / pagesize;
					
					long rem = countpages % pagesize;
					if(rem > 0)
					{
						pages++;
					}
					
					List<IndexStaff> is=staffRepo.indexstaffByRsmId(riid,p);
					 response.put("Index", is);
					    response.put("Enteries", countpages);
					    response.put("Pages", pages);
					    return response;
	        	 
	         }
			
	         else if(s.equals("ROLE_ASM")) {
	        	 
	        	    long countpages = staffRepo.indexstaffByAsmId(riid).size();
					long pages = countpages / pagesize;
					long rem = countpages % pagesize;
					
					if(rem > 0)
					{
						pages++;
					}
					
					List<IndexStaff> is=staffRepo.indexstaffByAsmId(riid,p);
					 response.put("Index", is);
					    response.put("Enteries", countpages);
					    response.put("Pages", pages);
					    return response;
	        	 
	         }
	         else {
	        	 return null;
	         }
		}
		return null;
	}

	
	@Override
	public Map<String, Object> SearchStaff(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();	
		Pageable p = PageRequest.of(pageno, pagesize);
		

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		Long rid=userDetails.getId();
		int riid = (int) (long) rid;
		
		
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

		for(String s:list) {
			
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				
				
				   List<IndexStaff> is = staffRepo.SearchByStaff(search, p);

				   long searchcount = is.size();

				   response.put("data", is);
				   response.put("SearchCount", searchcount);

				   return response;
			}
			
			else if(s.equals("ROLE_RSM")) {
				List<IndexStaff> is = staffRepo.SearchByStaffRsmId(riid,search, p);

				   long searchcount = is.size();

				   response.put("data", is);
				   response.put("SearchCount", searchcount);

				   return response;
			}
			
			else if(s.equals("ROLE_ASM")) {
				List<IndexStaff> is = staffRepo.SearchByStaffAsmId(riid,search, p);

				   long searchcount = is.size();

				   response.put("data", is);
				   response.put("SearchCount", searchcount);

				   return response;
			}
		}
		return null;
	}

	
	@Override
	public List<ExportStaff> exportStaff() {

		return staffRepo.ExportStaff();
	}

	@Override
	public List<IndexStaffDto> getStaffByStateId(int stateId) {
		
		List<IndexStaffDto> staffBystateId = staffRepo.getStaffBystateId(stateId);
		
		return staffBystateId;
	}
	
//	@Override
//	public List<IndexStatesByZones> fetchStatesByZones(ZoneRequest zoneRequest) {
//		
//		List<Integer> zoneid = zoneRequest.getZoneid();
//
//		List<IndexStatesByZones> fetchStatesByZoneId = staffRepo.FetchStatesByZoneId(zoneid);
//		
//		return fetchStatesByZoneId;
//	}

	@Override
	public List<IndexStatesByZones> fetchStatesByZones(List<Integer> zoneid) {
		
		List<IndexStatesByZones> fetchStatesByZoneId = staffRepo.FetchStatesByZoneId(zoneid);
		
		return fetchStatesByZoneId;
	}

	@Override
	public List<IndexStatesByZones> getASEByMultipleZones(List<Integer> stateid) {
		List<IndexStatesByZones> aseByMultipleStates = staffRepo.getASEByMultipleStates(stateid);
		return aseByMultipleStates;
	}

	@Override
	public List<IndexStatesByZones> getASMByMultipleZones(List<Integer> stateid) {
		List<IndexStatesByZones> asmByMultipleStates = staffRepo.getASMByMultipleStates(stateid);
		return asmByMultipleStates;
	}

	@Override
	public List<IndexStatesByZones> getRSMByMultipleZones(List<Integer> stateid) {
		List<IndexStatesByZones> rsmByMultipleStates = staffRepo.getRSMByMultipleStates(stateid);
		return rsmByMultipleStates;
	}

	@Override
	public List<IndexStatesByZones> getASEByMultipleRSMId(List<Integer> rsmid) {
		List<IndexStatesByZones> aseByMultipleRSMId = staffRepo.getASEByMultipleRSMId(rsmid);
		return aseByMultipleRSMId;
	}

	@Override
	public List<IndexStatesByZones> getASMByMultipleRSMId(List<Integer> rsmid) {
		List<IndexStatesByZones> asmByMultipleRSMId = staffRepo.getASMByMultipleRSMId(rsmid);
		return asmByMultipleRSMId;
	}

	@Override
	public List<IndexStatesByZones> getASEByMultipleASMId(List<Integer> asmid) {
		List<IndexStatesByZones> aseByMultipleASMId = staffRepo.getASEByMultipleASMId(asmid);
		return aseByMultipleASMId;
	}

	@Override
	public List<MultipleToStaffDto> multipleToStaff(int sid) {
		List<MultipleToStaffDto> multipleToStaffDtos = multipleToStaffRepo.multipleToStaffDtos(sid);
		return multipleToStaffDtos;
	}

	@Override
	public List<IndexStaffByStates> getStaffByMultipleStates(int stateid) {
		List<IndexStaffByStates> staffByMultipleStates = staffRepo.getStaffByMultipleStates(stateid);
		return staffByMultipleStates;
	}
}
