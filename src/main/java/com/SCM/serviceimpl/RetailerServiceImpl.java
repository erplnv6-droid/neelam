package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.SCM.ExportDto.ExportRetailer;
import com.SCM.IndexDto.IndexGroupn;
import com.SCM.IndexDto.IndexRetailer;
import com.SCM.IndexDto.RetailerDetailsByPincode;
import com.SCM.IndexDto.RetailerToStaffDto;
import com.SCM.dto.RetailerDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.Distributor;
import com.SCM.model.ERole;
import com.SCM.model.Retailer;
import com.SCM.model.RetailerTemporary;
import com.SCM.model.RetailerToStaff;
import com.SCM.model.Role;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.DistributorRepo;
import com.SCM.repository.RetailerAddressRepository;
import com.SCM.repository.RetailerRepo;
import com.SCM.repository.RetailerTemporaryAddressRepository;
import com.SCM.repository.RetailerToStaffRepo;
import com.SCM.repository.RoleRepository;
import com.SCM.repository.TempRetailerRepo;
import com.SCM.service.RetailerService;

@Service
public class RetailerServiceImpl implements RetailerService {

	@Autowired
	private RetailerRepo retailerRepo;

	@Autowired
	private DistributorRepo distributorRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	private RoleRepository rolerepository;

	@Autowired
	private TempRetailerRepo tempRetailerRepo;

	@Autowired
	private ActivityLogRepo activityLogRepo;
	
	@Autowired
	private  RetailerToStaffRepo retailerToStaffRepo;

	@Autowired
	private RetailerAddressRepository retailerAddressRepository;

	@Autowired
	private RetailerTemporaryAddressRepository retailerTemporaryAddressRepository;

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
	public Retailer saveRetailer(RetailerDto retailerDto) {

	    if (retailerDto == null) {
	    	
	        throw new IllegalArgumentException("RetailerDto cannot be null");
	    }
		
		Set<Role> roles = new HashSet<>();
		Role r = rolerepository.findByName(ERole.ROLE_RETAILER).orElseThrow(() -> new RuntimeException("role retailer not found"));
		roles.add(r); 

		Distributor d = distributorRepo.findById(129).get();
		System.out.println(d);
		
		Retailer retailer = new Retailer();
		
//		code for multiple  staff
		
	    List<RetailerToStaff> retailerToStaffList = retailerDto.getRetailerToStaffs().stream()
	        .flatMap(staffDto -> {
	            	        	
	        	List<RetailerToStaff> staffList = new ArrayList<>();

	            // Find the maximum length of the lists
	            int maxLength = Math.max(staffDto.getRsmid().size(), 
                                Math.max(staffDto.getAsmid().size(), 
                                Math.max(staffDto.getAseid().size(),
                                         staffDto.getNsmid().size())));
	                       

	            for (int i = 0; i < maxLength; i++) {
	                
	            	RetailerToStaff retailerToStaff = new RetailerToStaff();
	            	               
	                // Set rsmid if available
	                if (i < staffDto.getRsmid().size()) {
	                    retailerToStaff.setRsmid(staffDto.getRsmid().get(i));
	                    
	                }

	                // Set asmid if available
	                if (i < staffDto.getAsmid().size()) {
	                    retailerToStaff.setAsmid(staffDto.getAsmid().get(i));
	                }

	                // Set aseid if available
	                if (i < staffDto.getAseid().size()) {
	                    retailerToStaff.setAseid(staffDto.getAseid().get(i));
	                }
	                
	                // set nsmid if available
	                if (i < staffDto.getNsmid().size()) {
	                    retailerToStaff.setNsmid(staffDto.getNsmid().get(i));
	                }

	                staffList.add(retailerToStaff);
	            }

	            return staffList.stream();
	        }).collect(Collectors.toList());

//	    ==============================================================
	    
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		retailer.setCreatebyname(uname);
		retailer.setCreatedby(uid);
		retailer.setRole(role);

		retailer.setRetailerName1(retailerDto.getRetailerName1());
		retailer.setRetailerName2(retailerDto.getRetailerName2());
		retailer.setBillingAddress(retailerDto.getBillingAddress());
		retailer.setDeliveryAddress(retailerDto.getDeliveryAddress());
		retailer.setBoxProductDiscount(retailerDto.getBoxProductDiscount());
		retailer.setAlterMobileNumber(retailerDto.getAlterMobileNumber());
		retailer.setCity(retailerDto.getCity());
		retailer.setPaymentTerms(retailerDto.getPaymentTerms());
		retailer.setTransporterName(retailerDto.getTransporterName());
		retailer.setTradeName(retailerDto.getTradeName());
		retailer.setSchemenoshProductDisocunt(retailerDto.getSchemenoshProductDisocunt());
		retailer.setSchemekgProductDiscount(retailerDto.getSchemekgProductDiscount());
		retailer.setSchemecorporateProductDiscount(retailerDto.getSchemecorporateProductDiscount());
		retailer.setCookerProductDiscount(retailerDto.getCookerProductDiscount());
		retailer.setAlterEmail(retailerDto.getAlterEmail());
		retailer.setPinCode(retailerDto.getPinCode());
		retailer.setPerMobileNumber(retailerDto.getPerMobileNumber());
		retailer.setPerEmail(retailerDto.getPerEmail());
		retailer.setPanNumber(retailerDto.getPanNumber());
		retailer.setPinCode(retailerDto.getPinCode());
		retailer.setDoa1(retailerDto.getDoa1());
		retailer.setDoa2(retailerDto.getDoa2());
		retailer.setGstType(retailerDto.getGstType());
		retailer.setCreditLimit(retailerDto.getCreditLimit());
		retailer.setCreditDays(retailerDto.getCreditDays());
		retailer.setAuthorized(retailerDto.isAuthorized());
		retailer.setGstNumber(retailerDto.getGstNumber());
		retailer.setDeliveryLocation(retailerDto.getDeliveryLocation());
		retailer.setDiscountStructure(retailerDto.getDiscountStructure());
		retailer.setDob1(retailerDto.getDob1());
		retailer.setDob2(retailerDto.getDob2());
		retailer.setEmail1(retailerDto.getEmail1());
		retailer.setEmail2(retailerDto.getEmail2());
		retailer.setCountry(retailerDto.getCountry());
		retailer.setDiscountStructure(retailerDto.getDiscountStructure());
		retailer.setKgProductDiscount(retailerDto.getKgProductDiscount());
		retailer.setMobNo1(retailerDto.getMobNo1());
		retailer.setCorporaetProductDiscount(retailerDto.getCorporaetProductDiscount());
		retailer.setMobNo2(retailerDto.getMobNo2());
		retailer.setNoshProductDiscount(retailerDto.getNoshProductDiscount());
		retailer.setSchemeboxProductDiscount(retailerDto.getSchemeboxProductDiscount());
		retailer.setSchemecookerProductDiscount(retailerDto.getSchemecookerProductDiscount());
		retailer.setActivestatus(retailerDto.isActivestatus());
		retailer.setAuthorized(retailerDto.isAuthorized());
		retailer.setNsmid(retailerDto.getNsmid());
		retailer.setRsmid(retailerDto.getRsmid());
		retailer.setAseid(retailerDto.getAseid());
		retailer.setAsmid(retailerDto.getAsmid());
		retailer.setNsmid(retailerDto.getNsmid());
		retailer.setZonesid(retailerDto.getZonesid());
		retailer.setStateid(retailerDto.getStateid());
		retailer.setAadharcard(retailerDto.getAadharcard());
		retailer.setPancard(retailerDto.getPancard());
		retailer.setReferredBy(retailerDto.getReferredBy());
		retailer.setColourtype(retailerDto.getColourtype());
		retailer.setRetailerToStaff(retailerToStaffList);
		retailer.setCreateddate(LocalDate.now());
		retailer.setCreatedtime(LocalTime.now());
		retailer.setRoles(roles);
		retailer.setRetailerstatus(retailerDto.getRetailerstatus());
		retailer.setDistributor(retailerDto.getDistributor());

		retailer.setLatitude(retailerDto.getLatitude());
		retailer.setLongitude(retailerDto.getLongitude());

		retailer.setPassword(encoder.encode(retailerDto.getPassword()));
		retailer.setPasswordDecrypted(retailerDto.getPassword());
		
		retailer.setEmailLoginStatus(retailerDto.getEmailLoginStatus());
		
		retailer.setGroupn1(retailerDto.getGroupn1());
		retailer.setGroupn2(retailerDto.getGroupn2());
		retailer.setGroupn3(retailerDto.getGroupn3());
		retailer.setGroupn4(retailerDto.getGroupn4());
		retailer.setGroupn5(retailerDto.getGroupn5());
		
		System.out.println("Retailer Created");
		System.out.println(retailer);

		if (retailerDto.getDistributor() == null) {
			retailer.setDistributor(d);
		}


		Retailer ret = retailerRepo.save(retailer);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setRetailerid((long) ret.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return ret;
	}

	@Override
	public List<Retailer> getAllWorkOrderWithRetailer() {

		return retailerRepo.getAllWorkorderWithRetailer();
	}

	@Override
	public List<Retailer> getAllRetailer() {
		return retailerRepo.findAll();
	}

	@Override
	public List<RetailerDto> getAllRetailerDto() {

		List<RetailerDto> retailerDtos = new ArrayList<>();

		for (Retailer retailer : retailerRepo.findAll()) {

			RetailerDto retailerDto = new RetailerDto();
			retailerDto.setId(retailer.getId());
			retailerDto.setTradeName(retailer.getTradeName());
			retailerDto.setBillingAddress(retailer.getBillingAddress());
			retailerDto.setDeliveryAddress(retailer.getDeliveryAddress());
			retailerDto.setCity(retailer.getCity());
			retailerDto.setCountry(retailer.getCountry());
			retailerDto.setPanNumber(retailer.getPanNumber());
			retailerDto.setGstNumber(retailer.getGstNumber());
			retailerDto.setGstType(retailer.getGstType());
			retailerDto.setPinCode(retailer.getPinCode());
			retailerDto.setAlterMobileNumber(retailer.getAlterMobileNumber());
			retailerDto.setPerMobileNumber(retailer.getPerMobileNumber());
			retailerDto.setCreditDays(retailer.getCreditDays());
			retailerDto.setAlterEmail(retailer.getAlterEmail());
			retailerDto.setPerEmail(retailer.getPerEmail());
			retailerDto.setCreditLimit(retailer.getCreditLimit());
			retailerDto.setTransporterName(retailer.getTransporterName());
			retailerDto.setDeliveryLocation(retailer.getDeliveryLocation());
			retailerDto.setDiscountStructure(retailer.getDiscountStructure());
			retailerDto.setBoxProductDiscount(retailer.getBoxProductDiscount());
			retailerDto.setCookerProductDiscount(retailer.getCookerProductDiscount());
			retailerDto.setCorporaetProductDiscount(retailer.getCorporaetProductDiscount());
			retailerDto.setKgProductDiscount(retailer.getKgProductDiscount());
			retailerDto.setNoshProductDiscount(retailer.getNoshProductDiscount());
			retailerDto.setSchemeboxProductDiscount(retailer.getSchemeboxProductDiscount());
			retailerDto.setSchemecookerProductDiscount(retailer.getSchemecookerProductDiscount());
			retailerDto.setSchemenoshProductDisocunt(retailer.getSchemenoshProductDisocunt());
			retailerDto.setSchemecorporateProductDiscount(retailer.getSchemecorporateProductDiscount());
			retailerDto.setSchemekgProductDiscount(retailer.getKgProductDiscount());
			retailerDto.setSchemeDiscount(retailer.getSchemeDiscount());
			retailerDto.setRetailerName1(retailer.getRetailerName1());
			retailerDto.setRetailerName2(retailer.getRetailerName2());
			retailerDto.setDob1(retailer.getDob1());
			retailerDto.setDob2(retailer.getDob2());
			retailerDto.setDoa1(retailer.getDoa1());
			retailerDto.setDoa2(retailer.getDoa2());
			retailerDto.setPaymentTerms(retailer.getPaymentTerms());
			retailerDto.setMobNo1(retailer.getMobNo1());
			retailerDto.setMobNo2(retailer.getMobNo2());
			retailerDto.setEmail1(retailer.getEmail1());
			retailerDto.setEmail2(retailer.getEmail2());
			retailerDto.setKgProductDiscount(retailer.getKgProductDiscount());
			retailerDto.setMobNo1(retailer.getMobNo1());
			retailerDto.setCorporaetProductDiscount(retailer.getCorporaetProductDiscount());
			retailerDto.setMobNo2(retailer.getMobNo2());
			retailerDto.setNoshProductDiscount(retailer.getNoshProductDiscount());
			retailerDto.setSchemeboxProductDiscount(retailer.getSchemeboxProductDiscount());
			retailerDto.setSchemecookerProductDiscount(retailer.getSchemecookerProductDiscount());
			retailerDto.setActivestatus(retailer.isActivestatus());

			retailerDto.setAuthorized(retailer.isAuthorized());

			retailerDto.setCountry(retailer.getCountry());
			retailerDto.setNsmid(retailer.getNsmid());
			retailerDto.setRsmid(retailer.getRsmid());
			retailerDto.setAseid(retailer.getAseid());
			retailerDto.setAsmid(retailer.getAsmid());
			retailerDto.setNsmid(retailer.getNsmid());
			retailerDto.setZonesid(retailer.getZonesid());
			retailerDto.setStateid(retailer.getStateid());
			retailerDto.setRoles(retailer.getRoles());
			retailerDto.setAadharcard(retailer.getAadharcard());
			retailerDto.setPancard(retailer.getPancard());
			retailerDto.setPassword(retailer.getPassword());
			retailerDto.setReferredBy(retailer.getReferredBy());
			retailerDto.setDistributor(retailer.getDistributor());
			retailerDto.setColourtype(retailer.getColourtype());

			retailerDtos.add(retailerDto);
		}
		return retailerDtos;
	}

	@Override
	public RetailerDto getRetailerById(int id) {

		Optional<Retailer> op = retailerRepo.findById(id);

		RetailerDto retailerDto = null;

		if (op.isPresent()) {
			Retailer r = op.get();
			retailerDto = new RetailerDto();
			retailerDto.setId(r.getId());
			retailerDto.setTradeName(r.getTradeName());
			retailerDto.setBillingAddress(r.getBillingAddress());
			retailerDto.setDeliveryAddress(r.getDeliveryAddress());
			retailerDto.setCity(r.getCity());
			retailerDto.setCountry(r.getCountry());
			retailerDto.setPanNumber(r.getPanNumber());
			retailerDto.setGstNumber(r.getGstNumber());
			retailerDto.setGstType(r.getGstType());
			retailerDto.setPinCode(r.getPinCode());
			retailerDto.setAlterMobileNumber(r.getAlterMobileNumber());
			retailerDto.setPerMobileNumber(r.getPerMobileNumber());
			retailerDto.setCreditDays(r.getCreditDays());
			retailerDto.setAlterEmail(r.getAlterEmail());
			retailerDto.setPerEmail(r.getPerEmail());
			retailerDto.setCreditLimit(r.getCreditLimit());
			retailerDto.setTransporterName(r.getTransporterName());
			retailerDto.setDeliveryLocation(r.getDeliveryLocation());
			retailerDto.setDiscountStructure(r.getDiscountStructure());
			retailerDto.setBoxProductDiscount(r.getBoxProductDiscount());
			retailerDto.setCookerProductDiscount(r.getCookerProductDiscount());
			retailerDto.setCorporaetProductDiscount(r.getCorporaetProductDiscount());
			retailerDto.setKgProductDiscount(r.getKgProductDiscount());
			retailerDto.setNoshProductDiscount(r.getNoshProductDiscount());
			retailerDto.setSchemeboxProductDiscount(r.getSchemeboxProductDiscount());
			retailerDto.setSchemecookerProductDiscount(r.getSchemecookerProductDiscount());
			retailerDto.setSchemenoshProductDisocunt(r.getSchemenoshProductDisocunt());
			retailerDto.setSchemecorporateProductDiscount(r.getSchemecorporateProductDiscount());
			retailerDto.setSchemekgProductDiscount(r.getSchemekgProductDiscount());
			retailerDto.setSchemeDiscount(r.getSchemeDiscount());
			retailerDto.setRetailerName1(r.getRetailerName1());
			retailerDto.setRetailerName2(r.getRetailerName2());
			retailerDto.setDob1(r.getDob1());
			retailerDto.setDob2(r.getDob2());
			retailerDto.setDoa1(r.getDoa1());
			retailerDto.setDoa2(r.getDoa2());
			retailerDto.setMobNo1(r.getMobNo1());
			retailerDto.setMobNo2(r.getMobNo2());
			retailerDto.setEmail1(r.getEmail1());
			retailerDto.setEmail2(r.getEmail2());
			retailerDto.setPaymentTerms(r.getPaymentTerms());
			retailerDto.setKgProductDiscount(r.getKgProductDiscount());
			retailerDto.setMobNo1(r.getMobNo1());
			retailerDto.setCorporaetProductDiscount(r.getCorporaetProductDiscount());
			retailerDto.setMobNo2(r.getMobNo2());
			retailerDto.setNoshProductDiscount(r.getNoshProductDiscount());
			retailerDto.setSchemeboxProductDiscount(r.getSchemeboxProductDiscount());
			retailerDto.setSchemecookerProductDiscount(r.getSchemecookerProductDiscount());
			retailerDto.setActivestatus(r.isActivestatus());

			retailerDto.setAuthorized(r.isAuthorized());

			retailerDto.setCountry(r.getCountry());
			retailerDto.setNsmid(r.getNsmid());
			retailerDto.setRsmid(r.getRsmid());
			retailerDto.setAseid(r.getAseid());
			retailerDto.setAsmid(r.getAsmid());
			retailerDto.setNsmid(r.getNsmid());
			retailerDto.setZonesid(r.getZonesid());
			retailerDto.setStateid(r.getStateid());
			retailerDto.setRoles(r.getRoles());
			retailerDto.setAadharcard(r.getAadharcard());
			retailerDto.setPancard(r.getPancard());
			retailerDto.setPassword(r.getPassword());
			retailerDto.setPasswordDecrypted(r.getPasswordDecrypted());
			retailerDto.setReferredBy(r.getReferredBy());
			retailerDto.setColourtype(r.getColourtype());
			retailerDto.setDistributor(r.getDistributor());

			System.out.println(retailerDto);

			retailerDto.setEmailLoginStatus(r.getEmailLoginStatus());
			retailerDto.setRetailerstatus(r.getRetailerstatus());
			retailerDto.setLongitude(r.getLongitude());
			retailerDto.setLatitude(r.getLatitude());
			System.out.println(retailerDto);

		}
		return retailerDto;

	}

	@Override
	public String deleteRetailer(int id) {
		retailerRepo.deleteById(id);
		return "Company Removed !!" + id;
	}

	
	
	@Override
	public Retailer updateRetailer(RetailerDto retailerDto, int id) {
		
		Retailer existingRetailer = retailerRepo.findById(id).orElse(null);
		
//		code for multiple  staff
		
	    List<RetailerToStaff> retailerToStaffList = retailerDto.getRetailerToStaffs().stream().flatMap(staffDto -> {
	        	
	        	List<RetailerToStaff> staffList = new ArrayList<>();

	            // Find the maximum length of the lists
	            int maxLength = Math.max(staffDto.getRsmid().size(), 
                                Math.max(staffDto.getAsmid().size(), 
                                Math.max(staffDto.getAseid().size(),
                                         staffDto.getNsmid().size())));

	            for (int i = 0; i < maxLength; i++) {
	                RetailerToStaff retailerToStaff = new RetailerToStaff();
	                
	                // Set rsmid if available
	                if (i < staffDto.getRsmid().size()) {
	                    retailerToStaff.setRsmid(staffDto.getRsmid().get(i));
	                }

	                // Set asmid if available
	                if (i < staffDto.getAsmid().size()) {
	                    retailerToStaff.setAsmid(staffDto.getAsmid().get(i));
	                }

	                // Set aseid if available
	                if (i < staffDto.getAseid().size()) {
	                    retailerToStaff.setAseid(staffDto.getAseid().get(i));
	                }
	                
	                // set nsmid if available
	                if (i < staffDto.getNsmid().size()) {
	                    retailerToStaff.setNsmid(staffDto.getNsmid().get(i));
	                }

	                staffList.add(retailerToStaff);
	            }

	            return staffList.stream();
	            
	        }).collect(Collectors.toList());

          System.out.println(retailerToStaffList + "++++++++++++++++++++++++++++");
//	    ==============================================================
	    
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();
		
		existingRetailer.setUpdatedbyname(uname);
		existingRetailer.setUpdatedby(uid);
		existingRetailer.setUpdatedrole(role);
		existingRetailer.setUpdateddate(LocalDate.now());
		existingRetailer.setUpdatedtime(LocalTime.now());

		existingRetailer.setTradeName(retailerDto.getTradeName());
		existingRetailer.setBillingAddress(retailerDto.getBillingAddress());
		existingRetailer.setDeliveryAddress(retailerDto.getDeliveryAddress());
		existingRetailer.setCity(retailerDto.getCity());
		existingRetailer.setCountry(retailerDto.getCountry());
		existingRetailer.setPanNumber(retailerDto.getPanNumber());
		existingRetailer.setPaymentTerms(retailerDto.getPaymentTerms());
		existingRetailer.setGstNumber(retailerDto.getGstNumber());
		existingRetailer.setGstType(retailerDto.getGstType());
		existingRetailer.setPinCode(retailerDto.getPinCode());
		existingRetailer.setAlterMobileNumber(retailerDto.getAlterMobileNumber());
		existingRetailer.setPerMobileNumber(retailerDto.getPerMobileNumber());
		existingRetailer.setCreditDays(retailerDto.getCreditDays());
		existingRetailer.setAlterEmail(retailerDto.getAlterEmail());
		existingRetailer.setPerEmail(retailerDto.getPerEmail());
		existingRetailer.setCreditLimit(retailerDto.getCreditLimit());
		existingRetailer.setTransporterName(retailerDto.getTransporterName());
		existingRetailer.setDeliveryLocation(retailerDto.getDeliveryLocation());
		existingRetailer.setDiscountStructure(retailerDto.getDiscountStructure());
		existingRetailer.setBoxProductDiscount(retailerDto.getBoxProductDiscount());
		existingRetailer.setCookerProductDiscount(retailerDto.getCookerProductDiscount());
		existingRetailer.setCorporaetProductDiscount(retailerDto.getCorporaetProductDiscount());
		existingRetailer.setKgProductDiscount(retailerDto.getKgProductDiscount());
		existingRetailer.setNoshProductDiscount(retailerDto.getNoshProductDiscount());
		existingRetailer.setSchemeboxProductDiscount(retailerDto.getSchemeboxProductDiscount());
		existingRetailer.setSchemecookerProductDiscount(retailerDto.getSchemecookerProductDiscount());
		existingRetailer.setSchemenoshProductDisocunt(retailerDto.getSchemenoshProductDisocunt());
		existingRetailer.setSchemecorporateProductDiscount(retailerDto.getSchemecorporateProductDiscount());
		existingRetailer.setSchemekgProductDiscount(retailerDto.getSchemekgProductDiscount());
		existingRetailer.setSchemeDiscount(retailerDto.getSchemeDiscount());
		existingRetailer.setRetailerName1(retailerDto.getRetailerName1());
		existingRetailer.setAuthorized(retailerDto.isAuthorized());
		existingRetailer.setRetailerName2(retailerDto.getRetailerName2());
		existingRetailer.setDob1(retailerDto.getDob1());
		existingRetailer.setDob2(retailerDto.getDob2());
		existingRetailer.setDoa1(retailerDto.getDoa1());
		existingRetailer.setDoa2(retailerDto.getDoa2());
		existingRetailer.setMobNo1(retailerDto.getMobNo1());
		existingRetailer.setMobNo2(retailerDto.getMobNo2());
		existingRetailer.setEmail1(retailerDto.getEmail1());
		existingRetailer.setEmail2(retailerDto.getEmail2());
		existingRetailer.setAadharcard(retailerDto.getAadharcard());
		existingRetailer.setPancard(retailerDto.getPancard());
		existingRetailer.setReferredBy(retailerDto.getReferredBy());
		existingRetailer.setDistributor(retailerDto.getDistributor());
		existingRetailer.setColourtype(retailerDto.getColourtype());
		existingRetailer.setEmailLoginStatus(retailerDto.getEmailLoginStatus());
		existingRetailer.setLatitude(retailerDto.getLatitude());
		existingRetailer.setLongitude(retailerDto.getLongitude());
		existingRetailer.setStateid(retailerDto.getStateid());
		existingRetailer.setZonesid(retailerDto.getZonesid());
		existingRetailer.setNsmid(retailerDto.getNsmid());
		existingRetailer.setRsmid(retailerDto.getRsmid());
		existingRetailer.setAsmid(retailerDto.getAsmid());
		existingRetailer.setAseid(retailerDto.getAseid());
		existingRetailer.setRetailerToStaff(retailerToStaffList);

		if (retailerDto.getPassword() != "") {
			existingRetailer.setPassword(encoder.encode(retailerDto.getPassword()));
			existingRetailer.setPasswordDecrypted(retailerDto.getPassword());
		}

		Retailer ret = retailerRepo.save(existingRetailer);
		
		retailerRepo.deleteRetailerTostaffByRetailerId();
		

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setRetailerid((long) ret.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		retailerAddressRepository.deleteByRetailerId();

		return ret;
	}

	
	@Override
	public Retailer updateRetailer1(RetailerDto retailerDto, int id) {
		
		Retailer retailer = retailerRepo.findById(id).get();
		
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();
		
		retailer.setUpdatedbyname(uname);
		retailer.setUpdatedby(uid);
		retailer.setUpdatedrole(role);
		retailer.setUpdateddate(LocalDate.now());
		retailer.setUpdatedtime(LocalTime.now());

		retailer.setTradeName(retailerDto.getTradeName());
		retailer.setBillingAddress(retailerDto.getBillingAddress());
		retailer.setDeliveryAddress(retailerDto.getDeliveryAddress());
		retailer.setCity(retailerDto.getCity());
		retailer.setCountry(retailerDto.getCountry());
		retailer.setPanNumber(retailerDto.getPanNumber());
		retailer.setGstNumber(retailerDto.getGstNumber());
		retailer.setGstType(retailerDto.getGstType());
		retailer.setPinCode(retailerDto.getPinCode());
		retailer.setPaymentTerms(retailerDto.getPaymentTerms());
		retailer.setAlterMobileNumber(retailerDto.getAlterMobileNumber());
		retailer.setPerMobileNumber(retailerDto.getPerMobileNumber());
		retailer.setCreditDays(retailerDto.getCreditDays());
		retailer.setAlterEmail(retailerDto.getAlterEmail());
		retailer.setPerEmail(retailerDto.getPerEmail());
		retailer.setCreditLimit(retailerDto.getCreditLimit());
		retailer.setTransporterName(retailerDto.getTransporterName());
		retailer.setDeliveryLocation(retailerDto.getDeliveryLocation());
		retailer.setDiscountStructure(retailerDto.getDiscountStructure());
		retailer.setBoxProductDiscount(retailerDto.getBoxProductDiscount());
		retailer.setCookerProductDiscount(retailerDto.getCookerProductDiscount());
		retailer.setCorporaetProductDiscount(retailerDto.getCorporaetProductDiscount());
		retailer.setKgProductDiscount(retailerDto.getKgProductDiscount());
		retailer.setNoshProductDiscount(retailerDto.getNoshProductDiscount());
		retailer.setSchemeboxProductDiscount(retailerDto.getSchemeboxProductDiscount());
		retailer.setSchemecookerProductDiscount(retailerDto.getSchemecookerProductDiscount());
		retailer.setSchemenoshProductDisocunt(retailerDto.getSchemenoshProductDisocunt());
		retailer.setSchemecorporateProductDiscount(retailerDto.getSchemecorporateProductDiscount());
		retailer.setSchemekgProductDiscount(retailerDto.getSchemekgProductDiscount());
		retailer.setSchemeDiscount(retailerDto.getSchemeDiscount());
		retailer.setRetailerName1(retailerDto.getRetailerName1());
		retailer.setAuthorized(retailerDto.isAuthorized());
		retailer.setRetailerName2(retailerDto.getRetailerName2());
		retailer.setDob1(retailerDto.getDob1());
		retailer.setDob2(retailerDto.getDob2());
		retailer.setDoa1(retailerDto.getDoa1());
		retailer.setDoa2(retailerDto.getDoa2());
		retailer.setMobNo1(retailerDto.getMobNo1());
		retailer.setMobNo2(retailerDto.getMobNo2());
		retailer.setEmail1(retailerDto.getEmail1());
		retailer.setEmail2(retailerDto.getEmail2());
		retailer.setAadharcard(retailerDto.getAadharcard());
		retailer.setPancard(retailerDto.getPancard());
		retailer.setReferredBy(retailerDto.getReferredBy());
		retailer.setDistributor(retailerDto.getDistributor());
		retailer.setColourtype(retailerDto.getColourtype());
		retailer.setEmailLoginStatus(retailerDto.getEmailLoginStatus());
		retailer.setLatitude(retailerDto.getLatitude());
		retailer.setLongitude(retailerDto.getLongitude());
		retailer.setNsmid(retailerDto.getNsmid());
		retailer.setRsmid(retailerDto.getRsmid());
		retailer.setAsmid(retailerDto.getAsmid());
		retailer.setAseid(retailerDto.getAseid());
		
		if (retailerDto.getPassword() != "") {
			retailer.setPassword(encoder.encode(retailerDto.getPassword()));
//			retailer.setPasswordDecrypted(retailerDto.getPassword());
		}

		Retailer ret = retailerRepo.save(retailer);
		
		
		return ret;
	}
	
	

	@Override
	public List<Retailer> getWorkOrdersbyId(int d_id, String from_date, String to_date) {

		List<Retailer> r = retailerRepo.getWorkOrderandRetailer(d_id, from_date, to_date);

		System.out.println(r);

		return retailerRepo.getWorkOrderandRetailer(d_id, from_date, to_date);
	}

	// ---------------------------Retailer asm rsm ase rsm------------------

	@Override
	public List<RetailerDto> getRetailerByASE(int aseId) {
		List<Retailer> r = retailerRepo.getRetailerByAseID(aseId);
		return mapToListDto(r);
	}

	@Override
	public List<RetailerDto> getRetailerByASM(int asmId) {
		List<Retailer> r = retailerRepo.getRetailerByAsmID(asmId);
		return mapToListDto(r);
	}

	@Override
	public List<RetailerDto> getRetailerByRSM(int rsmId) {
		List<Retailer> r = retailerRepo.getRetailerByRsmID(rsmId);
		return mapToListDto(r);
	}

	@Override
	public List<RetailerDto> getRetailerByNSM(int nsmId) {
		List<Retailer> r = retailerRepo.getRetailerByNsmID(nsmId);
		return mapToListDto(r);
	}

	@Override
	public List<RetailerDto> getRetailerByZONE(int zonesId) {
		List<Retailer> r = retailerRepo.getRetailerByZoneId(zonesId);
		return mapToListDto(r);
	}

	@Override
	public List<RetailerDto> getRetailerBySTATE(int stateId) {
		List<Retailer> r = retailerRepo.getRetailerByStateId(stateId);
		return mapToListDto(r);
	}

	@Override
	public List<RetailerDto> getRetailerByRETAILER(int retId) {
		List<Retailer> r = retailerRepo.getRetailerByRetailerId(retId);
		return mapToListDto(r);
	}

	@Override
	public List<RetailerDto> getRetailerByDISTRIBUTOR(int distId) {
		List<Retailer> r = retailerRepo.getRetailerByDistributorId(distId);
		return mapToListDto(r);
	}

	public List<RetailerDto> mapToListDto(List<Retailer> retailers) {

		List<RetailerDto> rr = new ArrayList<>();
		for (Retailer r : retailers) {

			RetailerDto retailerDto = new RetailerDto();
			retailerDto.setId(r.getId());
			retailerDto.setRetailerName1(r.getRetailerName1());
			retailerDto.setRetailerName2(r.getRetailerName2());
			retailerDto.setBillingAddress(r.getBillingAddress());
			retailerDto.setBoxProductDiscount(r.getBoxProductDiscount());
			retailerDto.setAlterMobileNumber(r.getAlterMobileNumber());
			retailerDto.setCity(r.getCity());
			retailerDto.setTransporterName(r.getTransporterName());
			retailerDto.setTradeName(r.getTradeName());
			retailerDto.setSchemenoshProductDisocunt(r.getSchemenoshProductDisocunt());
			retailerDto.setSchemekgProductDiscount(r.getSchemekgProductDiscount());
			retailerDto.setSchemecorporateProductDiscount(r.getSchemecorporateProductDiscount());
			retailerDto.setSchemekgProductDiscount(r.getSchemekgProductDiscount());
			retailerDto.setCookerProductDiscount(r.getCookerProductDiscount());
			retailerDto.setAlterEmail(r.getAlterEmail());
			retailerDto.setPinCode(r.getPinCode());
			retailerDto.setPerMobileNumber(r.getPerMobileNumber());
			retailerDto.setPerEmail(r.getPerEmail());
			retailerDto.setPanNumber(r.getPanNumber());
			retailerDto.setPinCode(r.getPinCode());
			retailerDto.setDoa1(r.getDoa1());
			retailerDto.setDoa2(r.getDoa2());
			retailerDto.setGstType(r.getCountry());
			retailerDto.setCreditLimit(r.getCreditLimit());
			retailerDto.setCreditDays(r.getCreditDays());
			retailerDto.setDeliveryAddress(r.getDeliveryAddress());
			retailerDto.setGstNumber(r.getGstNumber());
			retailerDto.setDeliveryLocation(r.getDeliveryLocation());
			retailerDto.setDiscountStructure(r.getDiscountStructure());
			retailerDto.setDob1(r.getDob1());
			retailerDto.setDob2(r.getDob2());
			retailerDto.setEmail1(r.getEmail1());
			retailerDto.setEmail2(r.getEmail2());
			retailerDto.setNsmid(r.getNsmid());
			retailerDto.setRsmid(r.getRsmid());
			retailerDto.setPaymentTerms(r.getPaymentTerms());
			retailerDto.setAseid(r.getAseid());
			retailerDto.setAsmid(r.getAsmid());
			retailerDto.setZonesid(r.getZonesid());
			retailerDto.setStateid(r.getStateid());
			retailerDto.setWorkOrders(r.getWorkOrders());
			retailerDto.setCookerProductDiscount(r.getCookerProductDiscount());
			retailerDto.setCorporaetProductDiscount(r.getCorporaetProductDiscount());
			retailerDto.setKgProductDiscount(r.getKgProductDiscount());
			retailerDto.setNoshProductDiscount(r.getNoshProductDiscount());
			retailerDto.setSchemeboxProductDiscount(r.getSchemeboxProductDiscount());
			retailerDto.setSchemecookerProductDiscount(r.getSchemecookerProductDiscount());
			retailerDto.setSchemekgProductDiscount(r.getKgProductDiscount());
			retailerDto.setSchemeDiscount(r.getSchemeDiscount());
			retailerDto.setAadharcard(r.getAadharcard());
			retailerDto.setPancard(r.getPancard());
			retailerDto.setReferredBy(r.getReferredBy());
			retailerDto.setColourtype(r.getColourtype());

			rr.add(retailerDto);
		}
		return rr;
	}

	@Override
	public Retailer registerRetailer(RetailerDto retailerDto) {

		Set<Role> roles = new HashSet<>();

		Role r = rolerepository.findByName(ERole.ROLE_RETAILER).orElseThrow(() -> new RuntimeException("role not"));
		roles.add(r);

		Retailer retailer = new Retailer();
		retailer.setTransporterName(retailerDto.getTransporterName());
		retailer.setTradeName(retailerDto.getTradeName());
		retailer.setBillingAddress(retailerDto.getBillingAddress());
		retailer.setDeliveryAddress(retailerDto.getDeliveryAddress());
		retailer.setDiscountStructure(retailerDto.getDiscountStructure());
		retailer.setGstNumber(retailerDto.getGstNumber());
		retailer.setPanNumber(retailerDto.getPanNumber());
		retailer.setCountry(retailerDto.getCountry());
		retailer.setCity(retailerDto.getCity());
		retailer.setPinCode(retailerDto.getPinCode());
		retailer.setGstType(retailerDto.getGstType());
		retailer.setAlterMobileNumber(retailerDto.getAlterMobileNumber());
		retailer.setPerMobileNumber(retailerDto.getPerMobileNumber());
		retailer.setPerEmail(retailerDto.getPerEmail());
		retailer.setAlterEmail(retailerDto.getAlterEmail());
		retailer.setCreditLimit(retailerDto.getCreditLimit());
		retailer.setCreditDays(retailerDto.getCreditDays());
		retailer.setDeliveryLocation(retailerDto.getDeliveryLocation());
		retailer.setRetailerName1(retailerDto.getRetailerName1());
		retailer.setRetailerName2(retailerDto.getRetailerName2());
		retailer.setDoa1(retailerDto.getDoa1());
		retailer.setDoa2(retailerDto.getDoa2());
		retailer.setDob1(retailerDto.getDob1());
		retailer.setDob2(retailerDto.getDob2());

		retailer.setAuthorized(retailerDto.isAuthorized());
		
		retailer.setMobNo1(retailerDto.getMobNo1());
		retailer.setMobNo2(retailerDto.getMobNo2());
		retailer.setEmail1(retailerDto.getEmail1());
		retailer.setEmail2(retailerDto.getEmail2());
		retailer.setZonesid(retailerDto.getZonesid());
		retailer.setStateid(retailerDto.getStateid());
		retailer.setReferredBy(retailerDto.getReferredBy());
		retailer.setColourtype(retailerDto.getColourtype());
		retailer.setRoles(roles);

		Retailer ret = retailerRepo.save(retailer);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setRetailerid((long) ret.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return ret;
	}

	
//	 ===== convert temperory-retailer to retailer
	
	@Override
	public Retailer ConvertToRetailer(RetailerDto retailerDto) {

		Set<Role> role = new HashSet<>();
		Role roles = rolerepository.findByName(ERole.ROLE_RETAILER).get();
		role.add(roles);

		Retailer retailer = new Retailer();
		
	    List<RetailerToStaff> retailerToStaffList = retailerDto.getRetailerToStaffs().stream()
		        .flatMap(staffDto -> {
		            	        	
		        	List<RetailerToStaff> staffList = new ArrayList<>();

		            // Find the maximum length of the lists
		            int maxLength = Math.max(staffDto.getRsmid().size(), 
	                                Math.max(staffDto.getAsmid().size(), 
	                                Math.max(staffDto.getAseid().size(),
	                                         staffDto.getNsmid().size())));
		                       

		            for (int i = 0; i < maxLength; i++) {
		                
		            	RetailerToStaff retailerToStaff = new RetailerToStaff();
		            	               
		                // Set rsmid if available
		                if (i < staffDto.getRsmid().size()) {

		                    retailerToStaff.setRsmid(staffDto.getRsmid().get(i));
		                    
		                }

		                // Set asmid if available
		                if (i < staffDto.getAsmid().size()) {
		                    retailerToStaff.setAsmid(staffDto.getAsmid().get(i));
		                }

		                // Set aseid if available
		                if (i < staffDto.getAseid().size()) {
		                    retailerToStaff.setAseid(staffDto.getAseid().get(i));
		                }
		                
		                // set nsmid if available
		                if (i < staffDto.getNsmid().size()) {
		                    retailerToStaff.setNsmid(staffDto.getNsmid().get(i));
		                }

		                staffList.add(retailerToStaff);
		            }

		            return staffList.stream();
		        }).collect(Collectors.toList());
		

		Long uid = getUserId();
		String uname = getUserName();
		String role1 = getRolename();

		retailer.setCreatebyname(uname);
		retailer.setCreatedby(uid);
		retailer.setRole(role1);
		retailer.setRetailerName1(retailerDto.getRetailerName1());
		retailer.setRetailerName2(retailerDto.getRetailerName2());
		retailer.setBillingAddress(retailerDto.getBillingAddress());
		retailer.setBoxProductDiscount(retailerDto.getBoxProductDiscount());
		retailer.setAlterMobileNumber(retailerDto.getAlterMobileNumber());
		retailer.setCity(retailerDto.getCity());
		retailer.setTransporterName(retailerDto.getTransporterName());
		retailer.setTradeName(retailerDto.getTradeName());
		retailer.setSchemenoshProductDisocunt(retailerDto.getSchemenoshProductDisocunt());
		retailer.setSchemekgProductDiscount(retailerDto.getSchemekgProductDiscount());
		retailer.setSchemecorporateProductDiscount(retailerDto.getSchemecorporateProductDiscount());
		retailer.setSchemekgProductDiscount(retailerDto.getSchemekgProductDiscount());
		retailer.setCookerProductDiscount(retailerDto.getCookerProductDiscount());
		retailer.setAlterEmail(retailerDto.getAlterEmail());
		retailer.setPinCode(retailerDto.getPinCode());
		retailer.setPerMobileNumber(retailerDto.getPerMobileNumber());
		retailer.setPerEmail(retailerDto.getPerEmail());
		retailer.setPaymentTerms(retailerDto.getPaymentTerms());
		retailer.setPanNumber(retailerDto.getPanNumber());
		retailer.setPinCode(retailerDto.getPinCode());
		retailer.setDoa1(retailerDto.getDoa1());
		retailer.setDoa2(retailerDto.getDoa2());
		retailer.setGstType(retailerDto.getGstType());
		retailer.setCreditLimit(retailerDto.getCreditLimit());
		retailer.setCreditDays(retailerDto.getCreditDays());
		retailer.setDeliveryAddress(retailerDto.getDeliveryAddress());
		retailer.setGstNumber(retailerDto.getGstNumber());
		retailer.setDeliveryLocation(retailerDto.getDeliveryLocation());
		retailer.setDiscountStructure(retailerDto.getDiscountStructure());
		retailer.setDob1(retailerDto.getDob1());
		retailer.setDob2(retailerDto.getDob2());
		retailer.setEmail1(retailerDto.getEmail1());
		retailer.setEmail2(retailerDto.getEmail2());
		retailer.setCountry(retailerDto.getCountry());
		retailer.setDiscountStructure(retailerDto.getDiscountStructure());
		retailer.setKgProductDiscount(retailerDto.getKgProductDiscount());
		retailer.setMobNo1(retailerDto.getMobNo1());
		retailer.setCorporaetProductDiscount(retailerDto.getCorporaetProductDiscount());
		retailer.setMobNo2(retailerDto.getMobNo2());
		retailer.setNoshProductDiscount(retailerDto.getNoshProductDiscount());
		retailer.setSchemeboxProductDiscount(retailerDto.getSchemeboxProductDiscount());
		retailer.setSchemecookerProductDiscount(retailerDto.getSchemecookerProductDiscount());
		retailer.setAuthorized(false);
		retailer.setNsmid(retailerDto.getNsmid());
		retailer.setRsmid(retailerDto.getRsmid());
		retailer.setAseid(retailerDto.getAseid());
		retailer.setAsmid(retailerDto.getAsmid());
		retailer.setNsmid(retailerDto.getNsmid());
		retailer.setZonesid(retailerDto.getZonesid());
		retailer.setStateid(retailerDto.getStateid());
		retailer.setAadharcard(retailerDto.getAadharcard());
		retailer.setPancard(retailerDto.getPancard());
		retailer.setDistributor(retailerDto.getDistributor());
		retailer.setPassword(encoder.encode(retailerDto.getPassword()));
		retailer.setReferredBy(retailerDto.getReferredBy());
		retailer.setColourtype(retailerDto.getColourtype());
		retailer.setRoles(role);
		retailer.setLatitude(retailerDto.getLatitude());
		retailer.setLongitude(retailerDto.getLongitude());
		retailer.setRetailerToStaff(retailerToStaffList);
		Retailer rt = retailerRepo.save(retailer);

		RetailerTemporary retTemp = tempRetailerRepo.findById(retailerDto.getTempretid()).get();

		System.out.println(retTemp);

		retTemp.getRoles().clear();
		retTemp.getRoles().remove(roles);
		retTemp.getDeliveryAddress().remove(retailerDto.getDeliveryAddress());
		retTemp.setActivestatus(false);

		RetailerTemporary savedrtp = tempRetailerRepo.save(retTemp);

		if (retailerDto.getDeliveryAddress() != null) {
			retailerTemporaryAddressRepository.deleteTempAddressBytempId(retTemp.getId());
			System.out.println(retTemp.getId() + "++++++++++++");
		}

		if (savedrtp != null) {

			tempRetailerRepo.deleteTempByRetailer(retTemp.getId());
		}

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setRetailerid((long) rt.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return rt;
	}

	@Override
	public List<Retailer> getAllRet() {

		return retailerRepo.findAll();
	}

	// ----- Index Retailer

	@Override
	public Map<String, Object> IndexRetailerAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int riid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				long countpages = retailerRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexRetailer> ipo = retailerRepo.indexRetailer(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
	      
			else if (s.equals("ROLE_RSM")) {
				
				long countpages = retailerRepo.indexRetailerByRsmId(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexRetailer> ipo = retailerRepo.indexRetailerByRsmId(riid, p);
				System.out.println(riid);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_ASM")) {
				long countpages = retailerRepo.indexRetailerByAsmId(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				
				List<IndexRetailer> ipo = retailerRepo.indexRetailerByAsmId(riid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_ASE"))

			{
				long countpages = retailerRepo.indexRetailerByAseId(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexRetailer> ipo = retailerRepo.indexRetailerByAseId(riid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {
				long countpages = retailerRepo.indexRetailerByDistId(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexRetailer> ipo = retailerRepo.indexRetailerByDistId(riid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> IndexRetailerDesc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int riid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				long countpages = retailerRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexRetailer> ipo = retailerRepo.indexRetailer(p);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);

				return response;
			}
		
			else if (s.equals("ROLE_RSM")) {
				long countpages = retailerRepo.indexRetailerByRsmId(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexRetailer> ipo = retailerRepo.indexRetailerByRsmId(riid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			} else if (s.equals("ROLE_ASM"))

			{
				long countpages = retailerRepo.indexRetailerByAsmId(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexRetailer> ipo = retailerRepo.indexRetailerByAsmId(riid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_ASE"))

			{
				long countpages = retailerRepo.indexRetailerByAseId(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexRetailer> ipo = retailerRepo.indexRetailerByAseId(riid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {
				long countpages = retailerRepo.indexRetailerByDistId(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexRetailer> ipo = retailerRepo.indexRetailerByDistId(riid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

		}

		return null;

	}

	@Override
	public Map<String, Object> SearchRetailer(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		
		Pageable p = PageRequest.of(pageno, pagesize);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int riid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				long countpages = retailerRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexRetailer> retailers = retailerRepo.SearchByRetailer(search, p);

				long searchcount = retailers.size();

				response.put("data", retailers);
				response.put("SearchCount", searchcount);

				return response;
			}
//			else if (s.equals("ROLE_NSM")) {
//
//				long countpages = retailerRepo.count();
//				long pages = countpages / pagesize;
//
//				long rem = countpages % pagesize;
//				if (rem > 0) {
//					pages++;
//				}
//
//				List<IndexRetailer> retailers = retailerRepo.SearchRetailerByNsmId(riid, search, p);
//
//				long searchcount = retailers.size();
//
//				response.put("data", retailers);
//				response.put("SearchCount", searchcount);
//
//				return response;
//			}
			else if (s.equals("ROLE_RSM")) {

				long countpages = retailerRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexRetailer> retailers = retailerRepo.SearchRetailerByRsmId(riid, search, p);

				long searchcount = retailers.size();

				response.put("data", retailers);
				response.put("SearchCount", searchcount);

				return response;
			}
			else if (s.equals("ROLE_ASM")) {

				long countpages = retailerRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexRetailer> retailers = retailerRepo.SearchRetailerByAsmId(riid, search, p);

				long searchcount = retailers.size();

				response.put("data", retailers);
				response.put("SearchCount", searchcount);

				return response;
			} else if (s.equals("ROLE_ASE")) {

				long countpages = retailerRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
    
				List<IndexRetailer> retailers = retailerRepo.SearchRetailerByAseId(riid,search, p);

				long searchcount = retailers.size();

				response.put("data", retailers);
				response.put("SearchCount", searchcount);

				return response;
			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {

				long countpages = retailerRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexRetailer> retailers = retailerRepo.SearchRetailerByDistId(riid, search, p);

				long searchcount = retailers.size();

				response.put("data", retailers);
				response.put("SearchCount", searchcount);

				return response;
			}
		}
		return null;
	}

	
	@Override
	public List<ExportRetailer> exportRetailer() {

		List<ExportRetailer> excelexportfromretailer = retailerRepo.Excelexportfromretailer();

		System.out.println(excelexportfromretailer);

		return excelexportfromretailer;
	}

	@Override
	public List<Retailer> getAllRetail() {

		return retailerRepo.getAllRet();
	}

	@Override
	public Map<String, Object> IndexAllRetailerAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = retailerRepo.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexRetailer> ipo = retailerRepo.indexRetailer(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexAllRetailerDsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = retailerRepo.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexRetailer> ipo = retailerRepo.indexRetailer(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchAllRetailer(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		long countpages = retailerRepo.count();
		long pages = countpages / pagesize;

		List<IndexRetailer> retailers = retailerRepo.SearchByRetailer(search, p);

		long searchcount = retailers.size();

		response.put("data", retailers);
		response.put("SearchCount", searchcount);

		return response;
	}

	@Override
	public List<RetailerDetailsByPincode> retailerbypincode(String pincode) {

		List<RetailerDetailsByPincode> list = new ArrayList<RetailerDetailsByPincode>();

		List<Retailer> byPinCode = retailerRepo.findByPinCode(pincode);
		
		byPinCode.forEach(r -> {
			RetailerDetailsByPincode pincode2 = new RetailerDetailsByPincode();

			pincode2.setBillingAddress(r.getBillingAddress());
			pincode2.setCity(r.getCity());
			pincode2.setLattitude(r.getLatitude());
			pincode2.setLongitude(r.getLongitude());
			pincode2.setName(r.getTradeName());
			pincode2.setPincode(r.getPinCode());
			
			list.add(pincode2);

			System.out.println(pincode2 +"+++++++ pincode2");
			System.out.println(list +"+++++++ list");
			
		});
		
		return list;
	}

	
	
	@Override
	public List<RetailerDto> getRetailerByRSM1(int rsmId) {
		List<Retailer> r = retailerRepo.getRetailerByRsmID1(rsmId);
		return mapToListDto(r);
	}

	@Override
	public List<com.SCM.IndexDto.RetailerWithRetailerStaffDto> getretailertostaffbyId(int ret_id) {
		
		List<com.SCM.IndexDto.RetailerWithRetailerStaffDto> fetchRetailerWithStaffByRetailerId = retailerRepo.fetchRetailerWithStaffByRetailerId(ret_id);
		
		return fetchRetailerWithStaffByRetailerId;
	}

	@Override
	public List<RetailerToStaffDto> getRetailerTostaff(int id) {
		 List<RetailerToStaffDto> retailerTostaffByRetailer = retailerToStaffRepo.getRetailerTostaffByRetailer(id);
		return retailerTostaffByRetailer;
	}

	
	
	
	@Override
	public  Map<String,Object> findRetailerWhereGroupnIsAsc(int pagno,int pagesize,String field,String search1
			,String search2,String search3,String search4,String search5,int count) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = 0 ;
		
		String neelam="neelam";
		List<IndexRetailer> ipo=null;
		
//		search with group5
		
		if (search1.equals(neelam) && search2.equals(neelam)&&search3.equals(neelam)&&search4.equals(neelam)&&!search5.equals(neelam)) {	
 			ipo = retailerRepo.getAllRetailerWithGroupn5Is(p,search5);
 			countpages=retailerRepo.getAllRetailerWithGroupn5Is(p,search5).size();
		}
		
//		search with group4
		if (search1.equals(neelam) && search2.equals(neelam)&&search3.equals(neelam)&&!search4.equals(neelam)&&search5.equals(neelam)) {
			
 			ipo = retailerRepo.getAllRetailerWithGroupn4Is(p,search4);
 			countpages=retailerRepo.getAllRetailerWithGroupn4Is(p,search4).size();
		}

//		search with group3		
 		if (search1.equals(neelam) && search2.equals(neelam) && !search3.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
			ipo = retailerRepo.getAllRetailerWithGroupn3Is(p,search3);
			countpages = retailerRepo.getAllRetailerWithGroupn3Is(p,search3).size();
		}
// 		search with group2
		if (search1.equals(neelam) && !search2.equals(neelam)&&search3.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
			ipo = retailerRepo.getAllRetailerWithGroupn2Is(p,search2);
			countpages = retailerRepo.getAllRetailerWithGroupn2Is(p,search2).size();
		}
//		search with group1
		if (!search1.equals(neelam) && search2.equals(neelam)&&search3.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
			ipo = retailerRepo.getAllRetailerWithGroupn1Is(p,search1);
			countpages=retailerRepo.getAllRetailerWithGroupn1Is(p,search1).size();
		}
		
//		for multi search
		
		if (count!=0 && count==2) {
 			ipo = retailerRepo.getAllRetailerWithGroupn1AndGroupn2Is(p, search1, search2);
 			countpages=retailerRepo.getAllRetailerWithGroupn1AndGroupn2Is(p, search1, search2).size();
		}
		if (count!=0 && count==3) {
 			ipo = retailerRepo.getAllRetailerWithGroupn1AndGroupn2AndGroup3Is(p, search1, search2, search3);
 			countpages= retailerRepo.getAllRetailerWithGroupn1AndGroupn2AndGroup3Is(p, search1, search2, search3).size();
		}
		if (count!=0 && count==4) {
 			ipo = retailerRepo.getAllRetailerWithGroupn1AndGroupn2AndGroup3AndGroupn4Is(p, search1, search2, search3, search4);
 			countpages=retailerRepo.getAllRetailerWithGroupn1AndGroupn2AndGroup3AndGroupn4Is(p, search1, search2, search3, search4).size();
		}
		if (count!=0 && count==5) {
 			ipo = retailerRepo.getAllRetailerWithGroupn1AndGroupn2AndGroup3AndGroupn4AndGroupn5Is
 					(p, search1, search2, search3, search4, search5);
 			countpages = retailerRepo.getAllRetailerWithGroupn1AndGroupn2AndGroup3AndGroupn4AndGroupn5Is
				(p, search1, search2, search3, search4, search5).size();
		}
		
		
//		if (  search1.equals(neelam) &&search2.equals(neelam) && search3.equals(neelam) && search4.equals(neelam) && search5.equals(neelam)) {
//			System.out.println("g1g2g3g4g5 neelam ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			System.out.println("g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//			System.out.println("g1g2g3g4g5 neelam ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			System.out.println("g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//			System.out.println("g1g2g3g4g5 neelam ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			System.out.println("g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//			System.out.println("g1g2g3g4g5 neelam ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			System.out.println("g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//			System.out.println("g1g2g3g4g5 neelam ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			System.out.println("g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//
//			ipo=retailerRepo.getAllRetailerWithGroupn1AndGroupn2AndGroup3AndGroupn4AndGroupn5Is(p,search1,search2, search3,search4,search5);
//			countpages=retailerRepo.getAllRetailerWithGroupn2AndGroupn3AndGroupn4AndGroup5Is(p, search2, search3,search4,search5).size();
//		}
		if (  !search1.equals(neelam) &&!search2.equals(neelam) && !search3.equals(neelam) && !search4.equals(neelam) && !search5.equals(neelam)) {
			System.out.println("g1g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			System.out.println("g1g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			System.out.println("g1g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			System.out.println("g1g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			System.out.println("g1g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 

			ipo=retailerRepo.getAllRetailerWithGroupn1AndGroupn2AndGroup3AndGroupn4AndGroupn5Is(p,search1,search2, search3,search4,search5);
			countpages=retailerRepo.getAllRetailerWithGroupn2AndGroupn3AndGroupn4AndGroup5Is(p, search2, search3,search4,search5).size();
		}
		if (  search1.equals(neelam) &&!search2.equals(neelam) && !search3.equals(neelam) && !search4.equals(neelam) && !search5.equals(neelam)) {
			System.out.println("g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			System.out.println("g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");		 
			System.out.println("g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");		 
			System.out.println("g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");	 
			System.out.println("g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");	 

			ipo=retailerRepo.getAllRetailerWithGroupn2AndGroupn3AndGroupn4AndGroup5Is(p, search2, search3,search4,search5);
			countpages=retailerRepo.getAllRetailerWithGroupn2AndGroupn3AndGroupn4AndGroup5Is(p, search2, search3,search4,search5).size();
		}
		if (search1.equals(neelam)&&search5.equals(neelam)&&!search2.equals(neelam) && !search3.equals(neelam) && !search4.equals(neelam)) {
			System.out.println("g2g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			System.out.println("g2g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			System.out.println("g2g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			System.out.println("g2g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			System.out.println("g2g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 

			ipo=retailerRepo.getAllRetailerWithGroupn2AndGroupn3AndGroup4Is(p, search2, search3,search4);
			countpages=retailerRepo.getAllRetailerWithGroupn2AndGroupn3AndGroup4Is(p, search2, search3,search4).size();
		}
		if (!search2.equals(neelam) && !search3.equals(neelam) &&search1.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
			System.out.println("g2g3 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");		 
			System.out.println("g2g3 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			System.out.println("g2g3 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			System.out.println("g2g3 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			System.out.println("g2g3 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");		 

			ipo=retailerRepo.getAllRetailerWithGroupn2AndGroupn3Is(p, search2, search3);
			countpages=retailerRepo.getAllRetailerWithGroupn2AndGroupn3Is(p, search2, search3).size();
		}
		if (search1.equals(neelam)&&search2.equals(neelam)&&!search3.equals(neelam) && !search4.equals(neelam) && !search5.equals(neelam)) {
			System.out.println("g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");		 
			System.out.println("g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			System.out.println("g3g4g5 ke ytytyyttytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytytyt");
			System.out.println("g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			System.out.println("g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			ipo=retailerRepo.getAllRetailerWithGroupn3AndGroupn4AndGroupn5Is(p, search3, search4,search5);
			countpages=retailerRepo.getAllRetailerWithGroupn3AndGroupn4AndGroupn5Is(p, search3, search4,search5).size();
		}
		if (!search3.equals(neelam) && !search4.equals(neelam) && search1.equals(neelam) && search2.equals(neelam)&& search5.equals(neelam)) {
			System.out.println("g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			System.out.println("g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			System.out.println("g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			System.out.println("g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			System.out.println("g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			System.out.println("g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

			ipo=retailerRepo.getAllRetailerWithGroupn3AndGroupn4Is(p, search3, search4);
			countpages=retailerRepo.getAllRetailerWithGroupn3AndGroupn4Is(p, search3, search4).size();
		}
		if (!search4.equals(neelam) && !search5.equals(neelam)  && search1.equals(neelam) && search2.equals(neelam) && search3.equals(neelam)) {
			System.out.println("g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			System.out.println("g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			System.out.println("g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			System.out.println("g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			System.out.println("g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

			ipo=retailerRepo.getAllRetailerWithGroupn4AndGroupn5Is(p, search4, search5);
			countpages=retailerRepo.getAllRetailerWithGroupn4AndGroupn5Is(p, search4, search5).size();
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
	public  Map<String,Object> findRetailerWhereGroupnIsDesc(int pagno,int pagesize,String field,String search1,String search2,
			String search3,String search4,String search5,int count) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = retailerRepo.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}
		
		List<IndexRetailer> ipo=null;
		if (search1.equals("neelam")&& search2.equals("neelam") && search3.equals("neelam") && search4.equals("neelam") && !search5.equals("neelam")) {	
 			ipo = retailerRepo.getAllRetailerWithGroupn5Is(p,search5);
		}
		if (search1.equals("neelam")&& search2.equals("neelam") && search3.equals("neelam") && !search4.equals("neelam")) {
			ipo = retailerRepo.getAllRetailerWithGroupn3Is(p,search4);
		}
		if (search1.equals("neelam")&& search2.equals("neelam") && !search3.equals("neelam")) {
			ipo = retailerRepo.getAllRetailerWithGroupn3Is(p,search3);
		}
		if (search1.equals("neelam")&& !search2.equals("neelam")) {
			ipo = retailerRepo.getAllRetailerWithGroupn2Is(p,search2);
		}
		if (!search1.equals("neelam")) {
			ipo = retailerRepo.getAllRetailerWithGroupn1Is(p,search1);
		}

		
		
		
		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;

	}

	@Override
	public Map<String, Object> findRetailerAndDistributorWhereGroupnIsAsc(int pagno, int pagesize, String field,
			String search1, String search2, String search3, String search4, String search5, int count) {

		
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = 0 ;
		
		String neelam="neelam";
		List<IndexGroupn> ipo=null;
		
//		search with group1
		
		if (!search1.equals(neelam) && search2.equals(neelam)&&search3.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
			System.out.println("777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777");
 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn1Is(search1);
 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn1Is(search1).size();
		}
		
		if (search1.equals(neelam) && !search2.equals(neelam)&&search3.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
			System.out.println("777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777");
 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn2Is(search2);
 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn2Is(search2).size();
		}
		
		if (search1.equals(neelam) && search2.equals(neelam) && !search3.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
			System.out.println("777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777");
 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn3Is(search3);
 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn3Is(search3).size();
		}
		if (search1.equals(neelam) && search2.equals(neelam) && search3.equals(neelam) && !search4.equals(neelam)&&search5.equals(neelam)) {
			System.out.println("777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777");
 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn4Is(search4);
 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn4Is(search4).size();
		}
		if (search1.equals(neelam) && search2.equals(neelam) && search3.equals(neelam) && search4.equals(neelam) && !search5.equals(neelam)) {
			System.out.println("777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777");
 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn5Is(search5);
 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn5Is(search5).size();
		}
		
//		for multi search
		
		if (count!=0 && count==2) {
 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2Is( search1, search2);
 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2Is(  search1, search2).size();
		}
		if (count!=0 && count==3) {
 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3Is(  search1, search2,search3);
 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3Is(  search1, search2,search3).size();
		}
		if (count!=0 && count==4) {
 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4Is(  search1, search2,search3,search4);
 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4Is( search1, search2,search3,search4).size();
		}
		if (count!=0 && count==5) {
 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5Is(  search1, search2,search3,search4,search5);
 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5Is(  search1, search2,search3,search4,search5).size();
		}
		
		if (  !search1.equals(neelam) &&!search2.equals(neelam) && !search3.equals(neelam) && !search4.equals(neelam) && !search5.equals(neelam)) {
			System.out.println("g1g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			ipo=retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5Is( search1,search2, search3,search4,search5);
			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5Is( search1,search2, search3,search4,search5).size();
		}
		
		if (  search1.equals(neelam) &&!search2.equals(neelam) && !search3.equals(neelam) && !search4.equals(neelam) && !search5.equals(neelam)) {
//			System.out.println("g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
//			System.out.println("g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");		 
			ipo=retailerRepo.getAllRetailerAndDistributorWithGroupn2AndGroupn3AndGroupn4AndGroupn5Is(  search2, search3,search4,search5);
			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn2AndGroupn3AndGroupn4AndGroupn5Is(  search2, search3,search4,search5).size();
		}
		if (search1.equals(neelam)&&search5.equals(neelam)&&!search2.equals(neelam) && !search3.equals(neelam) && !search4.equals(neelam)) {
//			System.out.println("g2g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
			ipo=retailerRepo.getAllRetailerAndDistributorWithGroupn2AndGroupn3andGroupn4Is(  search2, search3,search4);
			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn2AndGroupn3andGroupn4Is(  search2, search3,search4).size();
		}
		if (!search2.equals(neelam) && !search3.equals(neelam) &&search1.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
			System.out.println("g2g3 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");		 
			ipo=retailerRepo.getAllRetailerAndDistributorWithGroupn2AndGroupn3Is( search2, search3);
			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn2AndGroupn3Is(  search2, search3).size();
		}
		if (search1.equals(neelam)&&search2.equals(neelam)&&!search3.equals(neelam) && !search4.equals(neelam) && !search5.equals(neelam)) {
			System.out.println("g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			ipo=retailerRepo. getAllRetailerAndDistributorWithGroupn3AndGroupn4AndGroupn5Is(  search3, search4,search5);
			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn3AndGroupn4AndGroupn5Is(  search3, search4,search5).size();
		}
		if (!search3.equals(neelam) && !search4.equals(neelam) && search1.equals(neelam) && search2.equals(neelam)&& search5.equals(neelam)) {
		System.out.println("g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			ipo=retailerRepo.getAllRetailerAndDistributorWithGroupn3AndGroupn4Is(  search3, search4);
			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn3AndGroupn4Is(  search3, search4).size();
		}
		if (!search4.equals(neelam) && !search5.equals(neelam)  && search1.equals(neelam) && search2.equals(neelam) && search3.equals(neelam)) {
			System.out.println("g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			ipo=retailerRepo.getAllRetailerAndDistributorWithGroupn4AndGroupn5Is( search4, search5);
			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn4AndGroupn5Is(  search4, search5).size();
		}
		
		
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}
		response.put("Index", ipo);
		return response;
	}

	@Override
	public Map<String, Object> findRetailerAndDistributorWhereGroupnIsDesc(int pagno, int pagesize, String field,
			String search1, String search2, String search3, String search4, String search5, int count) {
		return null;
//		
//		Map<String, Object> response = new HashMap<>();
//		Sort sort = Sort.by(Sort.Direction.DESC, field);
//		Pageable p = PageRequest.of(pagno, pagesize, sort);
//
//		long countpages = 0 ;
//		
//		String neelam="neelam";
//		List<IndexGroupn> ipo=null;
//		
////		search with group1
//		
//		if (!search1.equals(neelam) && search2.equals(neelam)&&search3.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
//			System.out.println("777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777");
// 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn1Is(p,search1);
// 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn1Is(p,search1).size();
//		}
//		
//		if (search1.equals(neelam) && !search2.equals(neelam)&&search3.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
//			System.out.println("777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777");
// 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn2Is(p,search2);
// 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn2Is(p,search2).size();
//		}
//		
//		if (search1.equals(neelam) && search2.equals(neelam) && !search3.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
//			System.out.println("777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777");
// 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn3Is(p,search3);
// 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn3Is(p,search3).size();
//		}
//		if (search1.equals(neelam) && search2.equals(neelam) && search3.equals(neelam) && !search4.equals(neelam)&&search5.equals(neelam)) {
//			System.out.println("777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777");
// 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn4Is(p,search4);
// 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn4Is(p,search4).size();
//		}
//		if (search1.equals(neelam) && search2.equals(neelam) && search3.equals(neelam) && search4.equals(neelam) && !search5.equals(neelam)) {
//			System.out.println("777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777");
// 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn5Is(p,search5);
// 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn5Is(p,search5).size();
//		}
//		
////		for multi search
//		
//		if (count!=0 && count==2) {
// 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2Is(p, search1, search2);
// 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2Is(p, search1, search2).size();
//		}
//		if (count!=0 && count==3) {
// 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3Is(p, search1, search2,search3);
// 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3Is(p, search1, search2,search3).size();
//		}
//		if (count!=0 && count==4) {
// 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4Is(p, search1, search2,search3,search4);
// 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4Is(p, search1, search2,search3,search4).size();
//		}
//		if (count!=0 && count==5) {
// 			ipo = retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5Is(p, search1, search2,search3,search4,search5);
// 			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5Is(p, search1, search2,search3,search4,search5).size();
//		}
//		
//		if (  !search1.equals(neelam) &&!search2.equals(neelam) && !search3.equals(neelam) && !search4.equals(neelam) && !search5.equals(neelam)) {
//			System.out.println("g1g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
//			ipo=retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5Is(p,search1,search2, search3,search4,search5);
//			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5Is(p,search1,search2, search3,search4,search5).size();
//		}
//		
//		if (  search1.equals(neelam) &&!search2.equals(neelam) && !search3.equals(neelam) && !search4.equals(neelam) && !search5.equals(neelam)) {
////			System.out.println("g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
////			System.out.println("g2g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");		 
//			ipo=retailerRepo.getAllRetailerAndDistributorWithGroupn2AndGroupn3AndGroupn4AndGroupn5Is(p, search2, search3,search4,search5);
//			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn2AndGroupn3AndGroupn4AndGroupn5Is(p, search2, search3,search4,search5).size();
//		}
//		if (search1.equals(neelam)&&search5.equals(neelam)&&!search2.equals(neelam) && !search3.equals(neelam) && !search4.equals(neelam)) {
////			System.out.println("g2g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");			 
//			ipo=retailerRepo.getAllRetailerAndDistributorWithGroupn2AndGroupn3andGroupn4Is(p, search2, search3,search4);
//			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn2AndGroupn3andGroupn4Is(p, search2, search3,search4).size();
//		}
//		if (!search2.equals(neelam) && !search3.equals(neelam) &&search1.equals(neelam)&&search4.equals(neelam)&&search5.equals(neelam)) {
//			System.out.println("g2g3 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");		 
//			ipo=retailerRepo.getAllRetailerAndDistributorWithGroupn2AndGroupn3Is(p, search2, search3);
//			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn2AndGroupn3Is(p, search2, search3).size();
//		}
//		if (search1.equals(neelam)&&search2.equals(neelam)&&!search3.equals(neelam) && !search4.equals(neelam) && !search5.equals(neelam)) {
//			System.out.println("g3g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//			ipo=retailerRepo. getAllRetailerAndDistributorWithGroupn3AndGroupn4AndGroupn5Is(p, search3, search4,search5);
//			countpages=retailerRepo.getAllRetailerWithGroupn3AndGroupn4AndGroupn5Is(p, search3, search4,search5).size();
//		}
//		if (!search3.equals(neelam) && !search4.equals(neelam) && search1.equals(neelam) && search2.equals(neelam)&& search5.equals(neelam)) {
//		System.out.println("g3g4 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//			ipo=retailerRepo.getAllRetailerAndDistributorWithGroupn3AndGroupn4Is(p, search3, search4);
//			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn3AndGroupn4Is(p, search3, search4).size();
//		}
//		if (!search4.equals(neelam) && !search5.equals(neelam)  && search1.equals(neelam) && search2.equals(neelam) && search3.equals(neelam)) {
//			System.out.println("g4g5 ke liyeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//			ipo=retailerRepo.getAllRetailerAndDistributorWithGroupn4AndGroupn5Is(p, search4, search5);
//			countpages=retailerRepo.getAllRetailerAndDistributorWithGroupn4AndGroupn5Is(p, search4, search5).size();
//		}
//		
//		
//		long pages = countpages / pagesize;
//
//		long rem = countpages % pagesize;
//		if (rem > 0) {
//			pages++;
//		}
//		response.put("Index", ipo);
//		response.put("Enteries", countpages);
//		response.put("Pages", pages);
//
//		return response;
//	
//	
			}


	
}
