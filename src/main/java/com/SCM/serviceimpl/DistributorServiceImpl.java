package com.SCM.serviceimpl;

import com.SCM.IndexDto.DistDto;
import com.SCM.IndexDto.DistributorDetailsByPincode;
import com.SCM.IndexDto.DistributorToStaffDto;
import com.SCM.IndexDto.IndexDistributor;
import com.SCM.dto.*;
import com.SCM.model.*;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.DistributorAddressRepository;
import com.SCM.repository.DistributorRepo;
import com.SCM.repository.DistributorToStaffRepo;
import com.SCM.repository.RetailerRepo;
import com.SCM.repository.RoleRepository;

import com.SCM.service.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import java.util.HashSet;
import java.util.Set;

import java.util.stream.Collectors;

@Service
public class DistributorServiceImpl implements DistributorService {

	@Autowired
	private DistributorRepo distributorRepo;
	
	@Autowired
	private DistributorToStaffRepo distributorToStaffRepo;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private ActivityLogRepo activityLogRepo;
	
	@Autowired
	private DistributorAddressRepository distributorAddressRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

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
	public Distributor saveDistributor(DistributorDto distributorDto) {

		Set<Role> r = new HashSet<>();
		Role roleid = roleRepository.findById(8).get();
		r.add(roleid);
		
		Distributor newDistributor = new Distributor();
		
		List<DistributorToStaff> diststafflist  = distributorDto.getDistributorToStaffs().stream().flatMap(dts ->{
			
			 int maxLength = Math.max(dts.getRsmid().size(), 
                             Math.max(dts.getAsmid().size(), 
                             Math.max(dts.getAseid().size(),
                                      dts.getNsmid().size())));
	         
	         List<DistributorToStaff> list =  new ArrayList<DistributorToStaff>();
	         
	         for(int i=0;i<maxLength;i++)
	         {
	        	 DistributorToStaff distributorToStaff = new DistributorToStaff();
	        	 
	        	 if(i < dts.getRsmid().size())
	        	 {
	        		 distributorToStaff.setRsmid(dts.getRsmid().get(i));
	        	 }
	        	 if(i < dts.getAsmid().size())
	        	 {
	        		 distributorToStaff.setAsmid(dts.getAsmid().get(i));
	        	 }
	        	 if(i < dts.getAseid().size())
	        	 {
	        		 distributorToStaff.setAseid(dts.getAseid().get(i));
	        	 }
	        	 if(i < dts.getNsmid().size())
	        	 {
	        		 distributorToStaff.setNsmid(dts.getNsmid().get(i));
	        	 }
	        	list.add(distributorToStaff);
	        }
			
			return list.stream();
			
		}).collect(Collectors.toList());
		
		Long uid = getUserId();
		String uname=getUserName();
		String role=getRolename();
		
		newDistributor.setDistributorName2(distributorDto.getDistributorName2());
		newDistributor.setDistributorName1(distributorDto.getDistributorName1());
		newDistributor.setBillingAddress(distributorDto.getBillingAddress());
		newDistributor.setDeliveryAddress(distributorDto.getDeliveryAddress());
		newDistributor.setBoxProductDiscount(distributorDto.getBoxProductDiscount());
		newDistributor.setAlterMobileNumber(distributorDto.getAlterMobileNumber());
		newDistributor.setCity(distributorDto.getCity());
		newDistributor.setPaymentTerms(distributorDto.getPaymentTerms());
		newDistributor.setTransporterName(distributorDto.getTransporterName());
		newDistributor.setTradeName(distributorDto.getTradeName());
		newDistributor.setSchemenoshProductDisocunt(distributorDto.getSchemenoshProductDisocunt());
		newDistributor.setSchemekgProductDiscount(distributorDto.getSchemekgProductDiscount());
		newDistributor.setSchemecorporateProductDiscount(distributorDto.getSchemecorporateProductDiscount());
		newDistributor.setCookerProductDiscount(distributorDto.getCookerProductDiscount());
		newDistributor.setAlterEmail(distributorDto.getAlterEmail());
		newDistributor.setPinCode(distributorDto.getPinCode());
		newDistributor.setPerMobileNumber(distributorDto.getPerMobileNumber());
		newDistributor.setPerEmail(distributorDto.getPerEmail());
		newDistributor.setPanNumber(distributorDto.getPanNumber());
		newDistributor.setPinCode(distributorDto.getPinCode());
		newDistributor.setDoa1(distributorDto.getDoa1());
		newDistributor.setDoa2(distributorDto.getDoa2());
		newDistributor.setGstType(distributorDto.getGstType());
		newDistributor.setCreditLimit(distributorDto.getCreditLimit());
		newDistributor.setCreditDays(distributorDto.getCreditDays());
		newDistributor.setCountry(distributorDto.getCountry());
		newDistributor.setState(distributorDto.getState());
		newDistributor.setCity(distributorDto.getCity());
		newDistributor.setStatecode(distributorDto.getStatecode());
		newDistributor.setGstNumber(distributorDto.getGstNumber());
		newDistributor.setDeliveryLocation(distributorDto.getDeliveryLocation());
		newDistributor.setDiscountStructure(distributorDto.getDiscountStructure());
		newDistributor.setDob1(distributorDto.getDob1());
		newDistributor.setDob2(distributorDto.getDob2());
		newDistributor.setEmail1(distributorDto.getEmail1());
		newDistributor.setEmail2(distributorDto.getEmail2());
		newDistributor.setMobNo1(distributorDto.getMobNo1());
		newDistributor.setMobNo2(distributorDto.getMobNo2());
		newDistributor.setCorporaetProductDiscount(distributorDto.getCorporaetProductDiscount());
		newDistributor.setKgProductDiscount(distributorDto.getKgProductDiscount());
		newDistributor.setNoshProductDiscount(distributorDto.getNoshProductDiscount());
		newDistributor.setSchemeboxProductDiscount(distributorDto.getSchemeboxProductDiscount());
		newDistributor.setSchemecookerProductDiscount(distributorDto.getSchemecookerProductDiscount());
		newDistributor.setNsmid(distributorDto.getNsmid());
		newDistributor.setRsmid(distributorDto.getRsmid());
		newDistributor.setAseid(distributorDto.getAseid());
		newDistributor.setAsmid(distributorDto.getAsmid());
		newDistributor.setZonesid(distributorDto.getZonesid());
		newDistributor.setStateid(distributorDto.getStateid());
		newDistributor.setRoles(distributorDto.getRoles());
		newDistributor.setSchemeDiscount(distributorDto.getSchemeDiscount());
		newDistributor.setAadharcard(distributorDto.getAadharcard());
		newDistributor.setPancard(distributorDto.getPancard());
		newDistributor.setPassword(encoder.encode(distributorDto.getPassword()));
		newDistributor.setPasswordDecrypted(distributorDto.getPassword());
		
		newDistributor.setCreateddate(LocalDate.now());
		newDistributor.setCreatedtime(LocalTime.now());
		newDistributor.setColourtype(distributorDto.getColourtype());
		newDistributor.setEmailLoginStatus(distributorDto.getEmailLoginStatus());
		newDistributor.setRoles(r);
		newDistributor.setCreatedby(uid);
		newDistributor.setCreatebyname(uname);
		newDistributor.setRole(role);
		newDistributor.setLatitude(distributorDto.getLatitude());
		newDistributor.setLongitude(distributorDto.getLongitude());
		newDistributor.setDistributorToStaffs(diststafflist);
		newDistributor.setGroupn1(distributorDto.getGroupn1());
		newDistributor.setGroupn2(distributorDto.getGroupn2());
		newDistributor.setGroupn3(distributorDto.getGroupn3());
		newDistributor.setGroupn4(distributorDto.getGroupn4());
		newDistributor.setGroupn5(distributorDto.getGroupn5());
		
		Distributor d = distributorRepo.save(newDistributor);
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setDistributorid((long) d.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return d;
	}

	
	@Override
	public List<DistributorDto> getAllDistributor() {

		return mapToListDto(distributorRepo.findAll());
	}

	
	@Override
	public Distributor getDistributorById(int id) {

		return distributorRepo.findById(id).orElse(null);
	}

	
	@Override
	public String deleteDistributor(int id) {

		distributorRepo.deleteById(id);

		return "Customer Removed !!" + id;
	}

	
	@Override
	public Distributor updateDistributor(Distributor distributor, int id) {

		Distributor existingDistributor = distributorRepo.findById(id).orElse(null);

		Long uid = getUserId();
		String uname=getUserName();
		String role=getRolename();
		
		existingDistributor.setUpdatedby(uid);
		existingDistributor.setUpdatedbyname(uname);
		existingDistributor.setUpdatedrole(role);
		existingDistributor.setUpdatedtime(LocalTime.now());
		existingDistributor.setUpdateddate(LocalDate.now());
		existingDistributor.setTradeName(distributor.getTradeName());
		existingDistributor.setBillingAddress(distributor.getBillingAddress());
		existingDistributor.setDeliveryAddress(distributor.getDeliveryAddress());
		existingDistributor.setState(distributor.getState());
		existingDistributor.setStatecode(distributor.getStatecode());
		existingDistributor.setCity(distributor.getCity());
		existingDistributor.setCountry(distributor.getCountry());
		existingDistributor.setPanNumber(distributor.getPanNumber());
		existingDistributor.setPaymentTerms(distributor.getPaymentTerms());
		existingDistributor.setGstNumber(distributor.getGstNumber());
		existingDistributor.setGstType(distributor.getGstType());
		existingDistributor.setPinCode(distributor.getPinCode());
		existingDistributor.setAlterMobileNumber(distributor.getAlterMobileNumber());
		existingDistributor.setPerMobileNumber(distributor.getPerMobileNumber());
		existingDistributor.setAlterEmail(distributor.getAlterEmail());
		existingDistributor.setPerEmail(distributor.getPerEmail());
		existingDistributor.setCreditDays(distributor.getCreditDays());
		existingDistributor.setCreditLimit(distributor.getCreditLimit());
		existingDistributor.setTransporterName(distributor.getTransporterName());
		existingDistributor.setDeliveryLocation(distributor.getDeliveryLocation());
		existingDistributor.setDiscountStructure(distributor.getDiscountStructure());
		existingDistributor.setBoxProductDiscount(distributor.getBoxProductDiscount());
		existingDistributor.setCookerProductDiscount(distributor.getCookerProductDiscount());
		existingDistributor.setCorporaetProductDiscount(distributor.getCorporaetProductDiscount());
		existingDistributor.setKgProductDiscount(distributor.getKgProductDiscount());
		existingDistributor.setNoshProductDiscount(distributor.getNoshProductDiscount());
		existingDistributor.setSchemeboxProductDiscount(distributor.getSchemeboxProductDiscount());
		existingDistributor.setSchemecookerProductDiscount(distributor.getSchemecookerProductDiscount());
		existingDistributor.setSchemecorporateProductDiscount(distributor.getSchemecorporateProductDiscount());
		existingDistributor.setSchemekgProductDiscount(distributor.getSchemekgProductDiscount());
		existingDistributor.setSchemenoshProductDisocunt(distributor.getSchemenoshProductDisocunt());
		existingDistributor.setSchemeDiscount(distributor.getSchemeDiscount());
		existingDistributor.setDistributorName1(distributor.getDistributorName1());
		existingDistributor.setDistributorName2(distributor.getDistributorName2());
		existingDistributor.setDob1(distributor.getDob1());
		existingDistributor.setDob2(distributor.getDob2());
		existingDistributor.setDoa1(distributor.getDoa1());
		existingDistributor.setDoa2(distributor.getDoa2());
		existingDistributor.setMobNo1(distributor.getMobNo1());
		existingDistributor.setMobNo2(distributor.getMobNo2());
		existingDistributor.setEmail1(distributor.getEmail1());
		existingDistributor.setEmail2(distributor.getEmail2());
		existingDistributor.setAseid(distributor.getAseid());
		existingDistributor.setAsmid(distributor.getAsmid());
		existingDistributor.setRsmid(distributor.getRsmid());
		existingDistributor.setNsmid(distributor.getNsmid());
		existingDistributor.setState(distributor.getState());
		existingDistributor.setStateid(distributor.getStateid());
		existingDistributor.setRoles(distributor.getRoles());
		existingDistributor.setAadharcard(distributor.getAadharcard());
		existingDistributor.setPancard(distributor.getPancard());
		existingDistributor.setRetailer(distributor.getRetailer());
		existingDistributor.setColourtype(distributor.getColourtype());
		existingDistributor.setEmailLoginStatus(distributor.getEmailLoginStatus());
		existingDistributor.setLatitude(distributor.getLatitude());
		existingDistributor.setLongitude(distributor.getLongitude());
		
		if (distributor.getPassword() != "") {
			existingDistributor.setPassword(encoder.encode(distributor.getPassword()));
		}
	
		Distributor d =   distributorRepo.save(existingDistributor);
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setDistributorid((long) d.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);
		
		distributorAddressRepository.deleteBydistributorId();
		
		return d;
	}

	
	@Override
	public List<Distributor> getprimaryorderbyid(int d_id, String from_date, String to_date) {
		return null;
	}
	
	
	@Override
	public Distributor updateDistributor(DistributorDto distributorDto, int id) {
		
		Set<Role> r = new HashSet<>();
		Role roleid = roleRepository.findById(8).get();
		r.add(roleid);
		
		Distributor existingDistributor = distributorRepo.findById(id).orElse(null);

		List<DistributorToStaff> diststafflist  = distributorDto.getDistributorToStaffs().stream().flatMap(dts ->{
			
			 int maxLength = Math.max(dts.getRsmid().size(), 
                             Math.max(dts.getAsmid().size(), 
                             Math.max(dts.getAseid().size(),
                                      dts.getNsmid().size())));
	         
	         List<DistributorToStaff> list =  new ArrayList<DistributorToStaff>();
	         
	         for(int i=0;i<maxLength;i++)
	         {
	        	 DistributorToStaff distributorToStaff = new DistributorToStaff();
	        	 
	        	 if(i < dts.getRsmid().size())
	        	 {
	        		 distributorToStaff.setRsmid(dts.getRsmid().get(i));
	        	 }
	        	 if(i < dts.getAsmid().size())
	        	 {
	        		 distributorToStaff.setAsmid(dts.getAsmid().get(i));
	        	 }
	        	 if(i < dts.getAseid().size())
	        	 {
	        		 distributorToStaff.setAseid(dts.getAseid().get(i));
	        	 }
	        	 if(i < dts.getNsmid().size())
	        	 {
	        		 distributorToStaff.setNsmid(dts.getNsmid().get(i));
	        	 }
	        	list.add(distributorToStaff);
	        }
			
			return list.stream();
			
		}).collect(Collectors.toList());
		
		
		
		Long uid = getUserId();
		String uname=getUserName();
		String role=getRolename();
		
		existingDistributor.setUpdatedby(uid);
		existingDistributor.setUpdatedbyname(uname);
		existingDistributor.setUpdatedrole(role);
		existingDistributor.setUpdatedtime(LocalTime.now());
		existingDistributor.setUpdateddate(LocalDate.now());
		existingDistributor.setTradeName(distributorDto.getTradeName());
		existingDistributor.setBillingAddress(distributorDto.getBillingAddress());
		existingDistributor.setDeliveryAddress(distributorDto.getDeliveryAddress());
		existingDistributor.setState(distributorDto.getState());
		existingDistributor.setStatecode(distributorDto.getStatecode());
		existingDistributor.setCity(distributorDto.getCity());
		existingDistributor.setCountry(distributorDto.getCountry());
		existingDistributor.setPaymentTerms(distributorDto.getPaymentTerms());
		existingDistributor.setPanNumber(distributorDto.getPanNumber());
		existingDistributor.setGstNumber(distributorDto.getGstNumber());
		existingDistributor.setGstType(distributorDto.getGstType());
		existingDistributor.setPinCode(distributorDto.getPinCode());
		existingDistributor.setAlterMobileNumber(distributorDto.getAlterMobileNumber());
		existingDistributor.setPerMobileNumber(distributorDto.getPerMobileNumber());
		existingDistributor.setAlterEmail(distributorDto.getAlterEmail());
		existingDistributor.setPerEmail(distributorDto.getPerEmail());
		existingDistributor.setCreditDays(distributorDto.getCreditDays());
		existingDistributor.setCreditLimit(distributorDto.getCreditLimit());
		existingDistributor.setTransporterName(distributorDto.getTransporterName());
		existingDistributor.setDeliveryLocation(distributorDto.getDeliveryLocation());
		existingDistributor.setDiscountStructure(distributorDto.getDiscountStructure());
		existingDistributor.setBoxProductDiscount(distributorDto.getBoxProductDiscount());
		existingDistributor.setCookerProductDiscount(distributorDto.getCookerProductDiscount());
		existingDistributor.setCorporaetProductDiscount(distributorDto.getCorporaetProductDiscount());
		existingDistributor.setKgProductDiscount(distributorDto.getKgProductDiscount());
		existingDistributor.setNoshProductDiscount(distributorDto.getNoshProductDiscount());
		existingDistributor.setSchemeboxProductDiscount(distributorDto.getSchemeboxProductDiscount());
		existingDistributor.setSchemecookerProductDiscount(distributorDto.getSchemecookerProductDiscount());
		existingDistributor.setSchemecorporateProductDiscount(distributorDto.getSchemecorporateProductDiscount());
		existingDistributor.setSchemekgProductDiscount(distributorDto.getSchemekgProductDiscount());
		existingDistributor.setSchemenoshProductDisocunt(distributorDto.getSchemenoshProductDisocunt());
		existingDistributor.setSchemeDiscount(distributorDto.getSchemeDiscount());
		existingDistributor.setDistributorName1(distributorDto.getDistributorName1());
		existingDistributor.setDistributorName2(distributorDto.getDistributorName2());
		existingDistributor.setDob1(distributorDto.getDob1());
		existingDistributor.setDob2(distributorDto.getDob2());
		existingDistributor.setDoa1(distributorDto.getDoa1());
		existingDistributor.setDoa2(distributorDto.getDoa2());
		existingDistributor.setMobNo1(distributorDto.getMobNo1());
		existingDistributor.setMobNo2(distributorDto.getMobNo2());
		existingDistributor.setEmail1(distributorDto.getEmail1());
		existingDistributor.setEmail2(distributorDto.getEmail2());
		existingDistributor.setAseid(distributorDto.getAseid());
		existingDistributor.setAsmid(distributorDto.getAsmid());
		existingDistributor.setRsmid(distributorDto.getRsmid());
		existingDistributor.setNsmid(distributorDto.getNsmid());
		existingDistributor.setState(distributorDto.getState());
		existingDistributor.setStateid(distributorDto.getStateid());
		existingDistributor.setZonesid(distributorDto.getZonesid());
		existingDistributor.setRoles(r);
		existingDistributor.setAadharcard(distributorDto.getAadharcard());
		existingDistributor.setPancard(distributorDto.getPancard());
		existingDistributor.setRetailer(distributorDto.getRetailer());
		existingDistributor.setColourtype(distributorDto.getColourtype());
		existingDistributor.setEmailLoginStatus(distributorDto.getEmailLoginStatus());
		existingDistributor.setLatitude(distributorDto.getLatitude());
		existingDistributor.setLongitude(distributorDto.getLongitude());
		existingDistributor.setDistributorToStaffs(diststafflist);
		
		if (distributorDto.getPassword() != "") {
			existingDistributor.setPassword(encoder.encode(distributorDto.getPassword()));
			existingDistributor.setPasswordDecrypted(distributorDto.getPassword());
		}
		
//		  User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//	        
//	        // Check if the current user has the admin role
//	        boolean isAdmin = ((Authentication) currentUser).getAuthorities().stream()
//	                                      .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
//	        
//	        if(isAdmin)
//	        {
//	        	existingDistributor.setPasswordDecrypted(distributorDto.getPassword());
//	        }
		
		
	
		Distributor d =   distributorRepo.save(existingDistributor);
		
		distributorRepo.deletedistributorstaffbydistributorId();
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setDistributorid((long) d.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);
		
		distributorAddressRepository.deleteBydistributorId();
		
		return d;
	}
	
	
	@Override
	public Distributor updateDistributor1(DistributorDto distributorDto, int id) {

		Set<Role> r = new HashSet<>();
		Role roleid = roleRepository.findById(8).get();
		r.add(roleid);
		
		Distributor distributor = distributorRepo.findById(id).get();
		
		Long uid = getUserId();
		String uname=getUserName();
		String role=getRolename();
		
		distributor.setUpdatedby(uid);
		distributor.setUpdatedbyname(uname);
		distributor.setUpdatedrole(role);
		distributor.setUpdatedtime(LocalTime.now());
		distributor.setUpdateddate(LocalDate.now());
		distributor.setTradeName(distributorDto.getTradeName());
		distributor.setBillingAddress(distributorDto.getBillingAddress());
		distributor.setDeliveryAddress(distributorDto.getDeliveryAddress());
		distributor.setState(distributorDto.getState());
		distributor.setStatecode(distributorDto.getStatecode());
		distributor.setCity(distributorDto.getCity());
		distributor.setCountry(distributorDto.getCountry());
		distributor.setPanNumber(distributorDto.getPanNumber());
		distributor.setGstNumber(distributorDto.getGstNumber());
		distributor.setGstType(distributorDto.getGstType());
		distributor.setPinCode(distributorDto.getPinCode());
		distributor.setPaymentTerms(distributorDto.getPaymentTerms());
		distributor.setAlterMobileNumber(distributorDto.getAlterMobileNumber());
		distributor.setPerMobileNumber(distributorDto.getPerMobileNumber());
		distributor.setAlterEmail(distributorDto.getAlterEmail());
		distributor.setPerEmail(distributorDto.getPerEmail());
		distributor.setCreditDays(distributorDto.getCreditDays());
		distributor.setCreditLimit(distributorDto.getCreditLimit());
		distributor.setTransporterName(distributorDto.getTransporterName());
		distributor.setDeliveryLocation(distributorDto.getDeliveryLocation());
		distributor.setDiscountStructure(distributorDto.getDiscountStructure());
		distributor.setBoxProductDiscount(distributorDto.getBoxProductDiscount());
		distributor.setCookerProductDiscount(distributorDto.getCookerProductDiscount());
		distributor.setCorporaetProductDiscount(distributorDto.getCorporaetProductDiscount());
		distributor.setKgProductDiscount(distributorDto.getKgProductDiscount());
		distributor.setNoshProductDiscount(distributorDto.getNoshProductDiscount());
		distributor.setSchemeboxProductDiscount(distributorDto.getSchemeboxProductDiscount());
		distributor.setSchemecookerProductDiscount(distributorDto.getSchemecookerProductDiscount());
		distributor.setSchemecorporateProductDiscount(distributorDto.getSchemecorporateProductDiscount());
		distributor.setSchemekgProductDiscount(distributorDto.getSchemekgProductDiscount());
		distributor.setSchemenoshProductDisocunt(distributorDto.getSchemenoshProductDisocunt());
		distributor.setSchemeDiscount(distributorDto.getSchemeDiscount());
		distributor.setDistributorName1(distributorDto.getDistributorName1());
		distributor.setDistributorName2(distributorDto.getDistributorName2());
		distributor.setDob1(distributorDto.getDob1());
		distributor.setDob2(distributorDto.getDob2());
		distributor.setDoa1(distributorDto.getDoa1());
		distributor.setDoa2(distributorDto.getDoa2());
		distributor.setMobNo1(distributorDto.getMobNo1());
		distributor.setMobNo2(distributorDto.getMobNo2());
		distributor.setEmail1(distributorDto.getEmail1());
		distributor.setEmail2(distributorDto.getEmail2());
		distributor.setAseid(distributorDto.getAseid());
		distributor.setAsmid(distributorDto.getAsmid());
		distributor.setRsmid(distributorDto.getRsmid());
		distributor.setNsmid(distributorDto.getNsmid());
		distributor.setRoles(r);
		distributor.setAadharcard(distributorDto.getAadharcard());
		distributor.setPancard(distributorDto.getPancard());
		distributor.setRetailer(distributorDto.getRetailer());
		distributor.setColourtype(distributorDto.getColourtype());
		distributor.setEmailLoginStatus(distributorDto.getEmailLoginStatus());
		distributor.setLatitude(distributorDto.getLatitude());
		distributor.setLongitude(distributorDto.getLongitude());
		
		if (distributorDto.getPassword() != "") {
			distributor.setPassword(encoder.encode(distributorDto.getPassword()));
		}
	
		Distributor update =   distributorRepo.save(distributor);
		
		return update;
	}


	
//--------------------------------------------------------------------------------------------------

	@Override
	public List<DistributorDto> getDistributorByASE(int aseId) {
		List<Distributor> d = distributorRepo.getDistributorByAseID(aseId);
		return mapToListDto(d);
	}

	@Override
	public List<DistributorDto> getDistributorByASM(int asmId) {
		List<Distributor> d = distributorRepo.getDistributorByAsmID(asmId);
		return mapToListDto(d);
	}

	@Override
	public List<DistributorDto> getDistributorByRSM(int rsmId) {
		List<Distributor> d = distributorRepo.getDistributorByRsmID(rsmId);
		return mapToListDto(d);
	}

	@Override
	public List<DistributorDto> getDistributorByNSM(int nsmId) {
		List<Distributor> d = distributorRepo.getDistributorByNsmID(nsmId);
		return mapToListDto(d);
	}

	@Override
	public List<DistributorDto> getDistributorByZONE(int zonesId) {
		List<Distributor> d = distributorRepo.getDistributorByZoneId(zonesId);
		return mapToListDto(d);
	}

	@Override
	public List<DistributorDto> getDistributorBySTATE(int stateId) {
		List<Distributor> d = distributorRepo.getDistributorBystateId(stateId);
		return mapToListDto(d);
	}
	
	
	@Override
	public List<DistributorDto> getDistributorBySingleDist(int id) {

		List<Distributor> d = distributorRepo.getSingleDistributorById(id);
		return mapToListDto(d);
	}
	

	public List<DistributorDto> mapToListDto(List<Distributor> distributors) {

		List<DistributorDto> dd = new ArrayList<>();

		for (Distributor d : distributors) {

			DistributorDto distributorDto = new DistributorDto();
			distributorDto.setId(d.getId());
			distributorDto.setDistributorName2(d.getDistributorName2());
			distributorDto.setDistributorName1(d.getDistributorName1());
			distributorDto.setBillingAddress(d.getBillingAddress());
			distributorDto.setBoxProductDiscount(d.getBoxProductDiscount());
			distributorDto.setAlterMobileNumber(d.getAlterMobileNumber());
			distributorDto.setCity(d.getCity());
			distributorDto.setTransporterName(d.getTransporterName());
			distributorDto.setTradeName(d.getTradeName());
			distributorDto.setSchemenoshProductDisocunt(d.getSchemenoshProductDisocunt());
			distributorDto.setGstType(d.getGstType());
			distributorDto.setSchemekgProductDiscount(d.getSchemekgProductDiscount());
			distributorDto.setSchemecorporateProductDiscount(d.getSchemecorporateProductDiscount());
			distributorDto.setSchemekgProductDiscount(d.getSchemekgProductDiscount());
			distributorDto.setCookerProductDiscount(d.getCookerProductDiscount());
			distributorDto.setAlterEmail(d.getAlterEmail());
			distributorDto.setPinCode(d.getPinCode());
			distributorDto.setPerMobileNumber(d.getPerMobileNumber());
			distributorDto.setPerEmail(d.getPerEmail());
			distributorDto.setPanNumber(d.getPanNumber());
			distributorDto.setPinCode(d.getPinCode());
			distributorDto.setCountry(d.getCountry());
			distributorDto.setCity(d.getCity());
			distributorDto.setDoa1(d.getDoa1());
			distributorDto.setDoa2(d.getDoa2());
			distributorDto.setGstType(d.getGstType());
			distributorDto.setCreditLimit(d.getCreditLimit());
			distributorDto.setCreditDays(d.getCreditDays());
			distributorDto.setDeliveryAddress(d.getDeliveryAddress());
			distributorDto.setState(d.getState());
			distributorDto.setPaymentTerms(d.getPaymentTerms());
			distributorDto.setStatecode(d.getStatecode());
			distributorDto.setGstNumber(d.getGstNumber());
			distributorDto.setDeliveryLocation(d.getDeliveryLocation());
			distributorDto.setDiscountStructure(d.getDiscountStructure());
			distributorDto.setDob1(d.getDob1());
			distributorDto.setDob2(d.getDob2());
			distributorDto.setEmail1(d.getEmail1());
			distributorDto.setEmail2(d.getEmail2());
			distributorDto.setMobNo1(d.getMobNo1());
			distributorDto.setMobNo2(d.getMobNo2());
			distributorDto.setSchemecookerProductDiscount(d.getSchemecookerProductDiscount());
			distributorDto.setSchemeboxProductDiscount(d.getSchemeboxProductDiscount());
			distributorDto.setSchemeDiscount(d.getSchemeDiscount());
			distributorDto.setNoshProductDiscount(d.getNoshProductDiscount());
			distributorDto.setCorporaetProductDiscount(d.getCorporaetProductDiscount());
			distributorDto.setKgProductDiscount(d.getKgProductDiscount());
			distributorDto.setDiscountStructure(d.getDiscountStructure());
			distributorDto.setPassword(d.getPassword());
			distributorDto.setNsmid(d.getNsmid());
			distributorDto.setRsmid(d.getRsmid());
			distributorDto.setAseid(d.getAseid());
			distributorDto.setAsmid(d.getAsmid());
			distributorDto.setZonesid(d.getZonesid());
			distributorDto.setStateid(d.getStateid());
			distributorDto.setAadharcard(d.getAadharcard());
			distributorDto.setPancard(d.getPancard());
			distributorDto.setRoles(d.getRoles());
			distributorDto.setColourtype(d.getColourtype());
		
			dd.add(distributorDto);
		}
		return dd;
	}



	@Override
	public Map<String, Object> IndexDistributorAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		Long rid=userDetails.getId();
		int riid = (int) (long) rid;

		
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		
		
		
		for(String s:list) {
			
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				
				long countpages = distributorRepo.count();
				long pages = countpages / pagesize;
				
				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}

				List<IndexDistributor> ipo = distributorRepo.indexDistributor(p);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);
				
				return response;
			}
			
	if (s.equals("ROLE_DISTRIBUTOR")) {
				
				long countpages = distributorRepo.count();
				long pages = countpages / pagesize;
				
				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}

				List<IndexDistributor> ipo = distributorRepo.indexDistributorDistributor(riid,p);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);
				
				return response;
			}
			
			else if((s.equals("ROLE_RSM")))  {
				
				long countpages = distributorRepo.indexDistributorByrsmId(riid).size();
				long pages = countpages / pagesize;
				
				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}

				List<IndexDistributor> ipo = distributorRepo.indexDistributorByrsmId(riid,p);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);
				

				return response;				
			}
			else if(s.equals("ROLE_ASM")) {
				long countpages = distributorRepo.indexDistributorByasmId(riid).size();
				long pages = countpages / pagesize;
				
				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}

				List<IndexDistributor> ipo = distributorRepo.indexDistributorByasmId(riid,p);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);
				

				return response;
				
				
			}
			else if(s.equals("ROLE_ASE")) {
				long countpages = distributorRepo.indexDistributorByaseId(riid).size();
				long pages = countpages / pagesize;
				
				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}

				List<IndexDistributor> ipo = distributorRepo.indexDistributorByaseId(riid,p);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
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
	public Map<String, Object> IndexDistributorDesc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		Long rid=userDetails.getId();
		int riid = (int) (long) rid;

		
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		
		
		
		for(String s:list) {
			
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				
				long countpages = distributorRepo.count();
				long pages = countpages / pagesize;
				
				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}

				List<IndexDistributor> ipo = distributorRepo.indexDistributor(p);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);
				
 
				return response;
			}
			
if (s.equals("ROLE_DISTRIBUTOR")) {
				
				long countpages = distributorRepo.count();
				long pages = countpages / pagesize;
				
				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}

				List<IndexDistributor> ipo = distributorRepo.indexDistributorDistributor(riid,p);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);
				
				return response;
			}
			
			else if(s.equals("ROLE_RSM")) {
				long countpages = distributorRepo.indexDistributorByrsmId(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}
				
				List<IndexDistributor> ipo = distributorRepo.indexDistributorByrsmId(riid,p);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);
				

				return response;
				
				
			}
			else if(s.equals("ROLE_ASM")) {
				long countpages = distributorRepo.indexDistributorByasmId(riid).size();
				long pages = countpages / pagesize;
				
				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}

				List<IndexDistributor> ipo = distributorRepo.indexDistributorByasmId(riid,p);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);
				

				return response;
			
			}
			else if(s.equals("ROLE_ASE")) {
				long countpages = distributorRepo.indexDistributorByaseId(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}
				
				List<IndexDistributor> ipo = distributorRepo.indexDistributorByaseId(riid,p);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);
				

				return response;
				
				
			}
			else {
				return null;
			}
		}
		return null;
	}

	
	// search distributor
	
	@Override
	public Map<String, Object> SearchDistributor(int pageno, int pagesize, String search) {
		
		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		Long rid=userDetails.getId();
		int riid = (int) (long) rid;
		
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		
		for(String s:list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				List<IndexDistributor> ipo = distributorRepo.SearchByDistributor(search,p);
				
				 long searchcount = ipo.size();

				   response.put("data", ipo);
				   response.put("SearchCount", searchcount);

				   return response;
			}
			
if (s.equals("ROLE_DISTRIBUTOR")) {
				
				long countpages = distributorRepo.count();
				long pages = countpages / pagesize;
				
				long rem = countpages % pagesize;
				if(rem > 0)
				{
					pages++;
				}

				List<IndexDistributor> ipo = distributorRepo.SearchByDistributorId(riid,search,p);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);
				
				return response;
			}
			
			else if(s.equals("ROLE_RSM") ) {
				List<IndexDistributor> ipo = distributorRepo.SearchDistributorByRsmId(riid,search,p);
				
				 long searchcount = ipo.size();

				   response.put("data", ipo);
				   response.put("SearchCount", searchcount);

				   return response;
			}
			else if(s.equals("ROLE_ASM") ) {
				List<IndexDistributor> ipo = distributorRepo.SearchDistributorByAsmId(riid,search,p);
				
				 long searchcount = ipo.size();

				   response.put("data", ipo);
				   response.put("SearchCount", searchcount);

				   return response;
			}
			
			else if(s.equals("ROLE_ASE") ) {
				List<IndexDistributor> ipo = distributorRepo.SearchDistributorByAseId(riid,search,p);
				
				 long searchcount = ipo.size();

				   response.put("data", ipo);
				   response.put("SearchCount", searchcount);

				   return response;
			}
		}
		List<IndexDistributor> distributors = distributorRepo.SearchByDistributor(search, p);
		
		long searchcount = distributors.size();
		
		response.put("data", distributors);
		response.put("SearchCount", searchcount);

		return response;
	}

	
	
	// export excel 
	
	@Override
	public List<com.SCM.ExportDto.ExportDistributor> ExportDistributor() {

		List<com.SCM.ExportDto.ExportDistributor> dist = distributorRepo.Excelexportfromdistributor();

		System.out.println(dist);

		return dist;
	}


	@Override
	public List<Distributor> getAllDist() {
		
		return distributorRepo.getAllDist();
	}
	



	@Override
	public List<Distributor> getAllDistByJdbcTemp() {
		
		String sql = "CALL DistributorGetAll()";
		
		List<Distributor> d = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Distributor.class));
		
		return d;
	}


	@Override
	public Map<String, Object> IndexAllDistributorAsc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = distributorRepo.count();
		long pages = countpages / pagesize;

	    List<IndexDistributor> ipo = distributorRepo.indexDistributor(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}


	@Override
	public Map<String, Object> IndexAllDistributorDesc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = distributorRepo.count();
		long pages = countpages / pagesize;

	    List<IndexDistributor> ipo = distributorRepo.indexDistributor(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}


	@Override
	public Map<String, Object> SearchAllDistributor(int pageno, int pagesize, String search) {
		
		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		long countpages = distributorRepo.count();
		long pages = countpages / pagesize;

		 List<IndexDistributor> distributor = distributorRepo.SearchByDistributor(search, p);

		long searchcount = distributor.size();

		response.put("data", distributor);
		response.put("SearchCount", searchcount);

		return response;
	}
	
	@Autowired
	private RetailerRepo retailerRepo;
	
	@Override
	public String updateRetailerLocation(int id, Double Lattitude, Double Longitude) {
		// TODO Auto-generated method stub
		
		if (!retailerRepo.existsById(id)) {
			throw new RuntimeException("no retailer found with id "+id);
		}
		
		Retailer retailer = retailerRepo.findById(id).get();
		
		Retailer retailer2 = retailer;
//		retailer.setId(retailer.getId());
		retailer.setLatitude(Lattitude);
		retailer.setLongitude(Longitude);
		Retailer save = retailerRepo.save(retailer2);
		
		
		return "updated succesfully ";
	}

	@Override
	public String updateDistributorLocation(int id, Double Lattitude, Double Longitude) {

		if (!distributorRepo.existsById(id)) {
			throw new RuntimeException("no retailer found with id "+id);
		}
		
//		double value = 19.1828526;
//      DecimalFormat df = new DecimalFormat("#.#######");
        
        
		Distributor distributor=distributorRepo.findById(id).get();
		Distributor distributor2=distributor;
	
		distributor.setLatitude(Lattitude);
		distributor.setLongitude(Longitude);
		
		distributorRepo.save(distributor2);
		return "updated succesfully";
		
	}

	@Override
	public List<DistributorDetailsByPincode> findByPincode(String pincode) {
		
		
	List<DistributorDetailsByPincode> list=new ArrayList<DistributorDetailsByPincode>();
	
		List<Distributor> dst = distributorRepo.findByPinCode(pincode);
	
		dst.forEach(ds->  {
			
			DistributorDetailsByPincode dist=new DistributorDetailsByPincode();
			dist.setBillingAddress(ds.getBillingAddress());
			dist.setCity(ds.getCity());
			dist.setLattitude(ds.getLatitude());
			dist.setLongitude(ds.getLongitude());
			dist.setName(ds.getTradeName());
			dist.setPincode(ds.getPinCode());
			
			list.add(dist);
		});
		
		return list;
	}

	@Override
	public List<DistDto> FetchDistributor() {
		List<DistDto> allDistributor = distributorRepo.fetchDsitributor();
		return allDistributor;
	}

	
	
	@Override
	public List<DistributorToStaffDto> getDistributorTostaff(int id) {
	
		List<DistributorToStaffDto> distributorTostaffByDistributor = distributorToStaffRepo.getDistributorTostaffByDistributor(id);
		
		return distributorTostaffByDistributor;
	}
	
	@Override
	public Map<String, Object> findDistributorWhereGroupnIsAsc(int pagno, int pagesize, String field, String search1,
			String search2, String search3, String search4, String search5, int count) {
		
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = 0 ;
		
		String neelam="neelam";
		List<IndexDistributor> ipo=null;
//		search with group5
		if (search1.equals(neelam) && search2.equals(neelam)&&search3.equals(neelam)&&search4.equals(neelam)&&!search5.equals(neelam)) {	
			ipo = distributorRepo.getAllDistributorWithGroupn5Is(p, search5);
			countpages=distributorRepo.getAllDistributorWithGroupn5Is(p,search5).size();
		}
		
//		search with group4		
		if (search1.equals(neelam) && search2.equals(neelam)&&search3.equals(neelam)&&!search4.equals(neelam)&&search5.equals(neelam)) {
			ipo = distributorRepo.getAllDistributorWithGroupn4Is(p, search4);
			countpages=distributorRepo.getAllDistributorWithGroupn4Is(p,search4).size();
		}
		
//		search with group3		
 		if (search1.equals(neelam) && search2.equals(neelam) && !search3.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
 			ipo = distributorRepo.getAllDistributorWithGroupn3Is(p, search3);
			countpages=distributorRepo.getAllDistributorWithGroupn3Is(p,search3).size();
		}
 		
// 		search with group2
		if (search1.equals(neelam) && !search2.equals(neelam)&&search3.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
			ipo = distributorRepo.getAllDistributorWithGroupn2Is(p, search2);
			countpages=distributorRepo.getAllDistributorWithGroupn2Is(p,search2).size();
		}
 		
//		search with group1
		if (!search1.equals(neelam) && search2.equals(neelam)&&search3.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
			ipo = distributorRepo.getAllDistributorWithGroupn1Is(p, search1);
			countpages=distributorRepo.getAllDistributorWithGroupn1Is(p,search1).size();
		}
		
		
//		for multiple search with count
		
		if (count!=0 && count==2) {
			ipo = distributorRepo.getAllDistributorWithGroupn1AndGroupn2Is(p, search1, search2);
			countpages=distributorRepo.getAllDistributorWithGroupn1AndGroupn2Is(p, search1, search2).size();
		}
		if (count!=0 && count==3) {
			ipo = distributorRepo.getAllDistributorWithGroupn1AndGroupn2AndGroupn3Is(p, search1, search2, search3);
			countpages=distributorRepo.getAllDistributorWithGroupn1AndGroupn2AndGroupn3Is(p, search1, search2, search3).size();
		}
		if (count!=0 && count==4) {
			ipo = distributorRepo.getAllDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4Is(p, search1, search2, search3, search4);
			countpages=distributorRepo.getAllDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4Is(p, search1, search2, search3, search4).size();
		}
		if (count!=0 && count==5) {
			ipo = distributorRepo.getAllDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5Is(p, search1, search2, search3, search4, search5);
			countpages=distributorRepo.getAllDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5Is(p, search1, search2, search3, search4, search5).size();
		}
		
		
//		search with 2 and 3 and 4 and 5
		
		if (  search1.equals(neelam) &&!search2.equals(neelam) && !search3.equals(neelam) && !search4.equals(neelam) && !search5.equals(neelam)) {
			ipo = distributorRepo.getAllDistributorWithGroupn2AndGroupn3AndGroupn4AndGroupn5Is(p, search2, search3, search4, search5);
			countpages=distributorRepo.getAllDistributorWithGroupn2AndGroupn3AndGroupn4AndGroupn5Is(p, search2, search3, search4, search5).size();
		}
//		search with 2 and 3 and 4
		if (search1.equals(neelam)&&search5.equals(neelam)&&!search2.equals(neelam) && !search3.equals(neelam) && !search4.equals(neelam)) {
			ipo = distributorRepo.getAllDistributorWithGroupn2AndGroupn3AndGroupn4Is(p, search2, search3, search4);
			countpages=distributorRepo.getAllDistributorWithGroupn2AndGroupn3AndGroupn4Is(p, search2, search3, search4).size();
		}
//		search with 2 and 3 

		if (!search2.equals(neelam) && !search3.equals(neelam) &&search1.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
		
			ipo = distributorRepo.getAllDistributorWithGroupn2AndGroupn3Is(p, search2, search3);
			countpages=distributorRepo.getAllDistributorWithGroupn2AndGroupn3Is(p, search2, search3).size();
		}
		
//		with g3 and g4
		if (!search3.equals(neelam) && !search4.equals(neelam) && search1.equals(neelam) && search2.equals(neelam)&& search5.equals(neelam)) {
			ipo = distributorRepo.getAllDistributorWithGroupn3AndGroupn4Is(p, search3, search4);
			countpages=distributorRepo.getAllDistributorWithGroupn3AndGroupn4Is(p, search3, search4).size();
		}
		
// 		with g3 and g4 and g5		
		if (search1.equals(neelam)&&search2.equals(neelam)&&!search3.equals(neelam) && !search4.equals(neelam) && !search5.equals(neelam)) {
			ipo = distributorRepo.getAllDistributorWithGroupn3AndGroupn4AndGroupn5Is(p, search3, search4,search5);
			countpages=distributorRepo.getAllDistributorWithGroupn3AndGroupn4AndGroupn5Is(p, search3, search4,search5).size();
		
		}
// 		with g4 and g5		
		if (!search4.equals(neelam) && !search5.equals(neelam)  && search1.equals(neelam) && search2.equals(neelam) && search3.equals(neelam)) {
			ipo = distributorRepo.getAllDistributorWithGroupn4AndGroupn5Is(p, search4,search5);
			countpages=distributorRepo.getAllDistributorWithGroupn4AndGroupn5Is(p, search4,search5).size();
		}
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}
		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);
		
		return response;
	}

	
	@Override
	public Map<String, Object> findDistributorWhereGroupnIsDesc(int pagno, int pagesize, String field, String search1,
			String search2, String search3, String search4, String search5, int count) {
		// TODO Auto-generated method stub
		return null;
	}

}
