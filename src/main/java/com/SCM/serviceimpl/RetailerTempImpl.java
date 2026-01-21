package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndesSalesReturn;
import com.SCM.IndexDto.IndexTemporaryRetailer;
import com.SCM.dto.RetailerTempDto;
import com.SCM.model.ERole;
import com.SCM.model.RetailerTemporary;
import com.SCM.model.Role;
import com.SCM.projection.SalesReturnProjection;
import com.SCM.projection.TemporaryRetailerProjection;
import com.SCM.repository.RetailerTemporaryAddressRepository;
import com.SCM.repository.RoleRepository;
import com.SCM.repository.TempRetailerRepo;
import com.SCM.service.RetailerTempService;

@Service
public class RetailerTempImpl implements RetailerTempService {

	@Autowired
	private TempRetailerRepo tempRetailerRepo;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RetailerTemporaryAddressRepository retailerTemporaryAddressRepository;
	    
	
	@Override
	public RetailerTemporary save(RetailerTempDto retailerTempDto) {
		
		RetailerTemporary retailerTemporary = new RetailerTemporary();
		retailerTemporary.setAadharcard(retailerTempDto.getAadharcard());
		retailerTemporary.setActivestatus(retailerTempDto.isActivestatus());
		retailerTemporary.setAlterEmail(retailerTempDto.getAlterEmail());
		retailerTemporary.setAlterMobileNumber(retailerTempDto.getAlterMobileNumber());
		retailerTemporary.setAuthorized(false);
		retailerTemporary.setBillingAddress(retailerTempDto.getBillingAddress());
		retailerTemporary.setCity(retailerTempDto.getCity());
		retailerTemporary.setCountry(retailerTempDto.getCountry());
		retailerTemporary.setZonesid(retailerTempDto.getZonesid());
		retailerTemporary.setTransporterName(retailerTempDto.getTransporterName());
		retailerTemporary.setTradeName(retailerTempDto.getTradeName());
		retailerTemporary.setStateid(retailerTempDto.getStateid());
		retailerTemporary.setStatecode(retailerTempDto.getStatecode());
		retailerTemporary.setState(retailerTempDto.getState());
		retailerTemporary.setRetailerName2(retailerTempDto.getRetailerName2());
		retailerTemporary.setRetailerName1(retailerTempDto.getRetailerName1());
		retailerTemporary.setPinCode(retailerTempDto.getPinCode());
		retailerTemporary.setPerMobileNumber(retailerTempDto.getPerMobileNumber());
		retailerTemporary.setPerEmail(retailerTempDto.getPerEmail());
		retailerTemporary.setPanNumber(retailerTempDto.getPanNumber());
		retailerTemporary.setPancard(retailerTempDto.getPancard());
		retailerTemporary.setMobNo1(retailerTempDto.getMobNo1());
		retailerTemporary.setMobNo2(retailerTempDto.getMobNo2());
		retailerTemporary.setGstType(retailerTempDto.getGstType());
		retailerTemporary.setGstNumber(retailerTempDto.getGstNumber());
		retailerTemporary.setEmail2(retailerTempDto.getEmail2());
		retailerTemporary.setEmail1(retailerTempDto.getEmail1());
		retailerTemporary.setDob2(retailerTempDto.getDob2());
		retailerTemporary.setDob1(retailerTempDto.getDob1());
		retailerTemporary.setDoa2(retailerTempDto.getDoa2());
		retailerTemporary.setDoa1(retailerTempDto.getDoa1());
		retailerTemporary.setDiscountStructure(retailerTempDto.getDiscountStructure());
		retailerTemporary.setDeliveryLocation(retailerTempDto.getDeliveryLocation());
		retailerTemporary.setReferredBy(retailerTempDto.getReferredBy());
		retailerTemporary.setDistributorId(retailerTempDto.getDistributorId());
		retailerTemporary.setCreateddate(LocalDate.now());
		retailerTemporary.setCreatedtime(LocalTime.now());
		retailerTemporary.setLongitude(retailerTempDto.getLongitude());
		retailerTemporary.setLatitude(retailerTempDto.getLatitude());
		
	    Set<Role> role = new HashSet<>();
	    Optional<Role> roleoptional = roleRepository.findById(retailerTempDto.getRoleId());
	    Role rol = roleoptional.get();
	    
	    role.add(rol);
	    retailerTemporary.setRoles(role);
		
	    RetailerTemporary rt = tempRetailerRepo.save(retailerTemporary);
	    
//	    List<Object> obj = retailerTempDto.getRetailerIds();
//	    
//	
//	    	 for(Object l : obj)
//	 	    {
//	 	    	Optional<Retailer> retailer = retailerRepo.findById((Integer)l);
//	 	    	Retailer r = retailer.get();
//	 	    
//	 	    	
//	 	    	r.setConvertTempret(true);
//	 	    	r.setTempretid(rt.getId());
//	 	    	
//	 	    	retailerRepo.save(r);
//	 	    }
	    
	      
		return rt;
	}

	
	
	@Override
	public List<RetailerTemporary> getAllTempRetailer() {
		
		List<RetailerTemporary> rt = tempRetailerRepo.findAll();
		
		return rt;
	}

	@Override
	public RetailerTemporary getTempRetailerById(int id) {
		
		Optional<RetailerTemporary> optionalrt = tempRetailerRepo.findById(id);
		
		RetailerTemporary retailerTemporary = optionalrt.get();
		return retailerTemporary;
	}

	
	@Override
	public String deleteTempRetailer(int id) {
		tempRetailerRepo.deleteById(id);
		return "delete temporory retailer";
	}



	@Override
	public RetailerTemporary save2(RetailerTempDto retailerDto) {
		
//		Long uid = getUserId();
//		String uname=getUserName();
//		String role1=getRolename();
		
		RetailerTemporary retailerTemporary = new RetailerTemporary();
		retailerTemporary.setTransporterName(retailerDto.getTransporterName());
		retailerTemporary.setTradeName(retailerDto.getTradeName());
		retailerTemporary.setBillingAddress(retailerDto.getBillingAddress());
		retailerTemporary.setDeliveryAddress(retailerDto.getDeliveryAddress());
		retailerTemporary.setDiscountStructure(retailerDto.getDiscountStructure());
		retailerTemporary.setGstNumber(retailerDto.getGstNumber());
		retailerTemporary.setPanNumber(retailerDto.getPanNumber());
		retailerTemporary.setCountry(retailerDto.getCountry());
		retailerTemporary.setCity(retailerDto.getCity());
		retailerTemporary.setPinCode(retailerDto.getPinCode());
		retailerTemporary.setGstType(retailerDto.getGstType());
		retailerTemporary.setAlterMobileNumber(retailerDto.getAlterMobileNumber());
		retailerTemporary.setPerMobileNumber(retailerDto.getPerMobileNumber());
		retailerTemporary.setPerEmail(retailerDto.getPerEmail());
		retailerTemporary.setAlterEmail(retailerDto.getAlterEmail());
		retailerTemporary.setCreditLimit(retailerDto.getCreditLimit());
		retailerTemporary.setCreditDays(retailerDto.getCreditDays());
		retailerTemporary.setDeliveryLocation(retailerDto.getDeliveryLocation());
		retailerTemporary.setRetailerName1(retailerDto.getRetailerName1());
		retailerTemporary.setRetailerName2(retailerDto.getRetailerName2());
		retailerTemporary.setDoa1(retailerDto.getDoa1());
		retailerTemporary.setDoa2(retailerDto.getDoa2());
		retailerTemporary.setDob1(retailerDto.getDob1());
		retailerTemporary.setDob2(retailerDto.getDob2());
		retailerTemporary.setMobNo1(retailerDto.getMobNo1());
		retailerTemporary.setMobNo2(retailerDto.getMobNo2());
		retailerTemporary.setEmail1(retailerDto.getEmail1());
		retailerTemporary.setEmail2(retailerDto.getEmail2());
		retailerTemporary.setZonesid(retailerDto.getZonesid());
		retailerTemporary.setStateid(retailerDto.getStateid());
		retailerTemporary.setReferredBy(retailerDto.getReferredBy());
		retailerTemporary.setDistributorId(retailerDto.getDistributorId());
//		retailerTemporary.setCreatebyname(uname);
//		retailerTemporary.setCreatedby(uid);
//		retailerTemporary.setRole(role1);
		retailerTemporary.setCreateddate(LocalDate.now());
		retailerTemporary.setCreatedtime(LocalTime.now());
		retailerTemporary.setLatitude(retailerDto.getLatitude());
		retailerTemporary.setLongitude(retailerDto.getLongitude());
	    Set<Role> role = new HashSet<>();
	    Optional<Role> roleoptional = roleRepository.findByName(ERole.ROLE_RETAILER);
	    Role rol = roleoptional.get();
	    
	    role.add(rol);
	    retailerTemporary.setRoles(role);
		
	    RetailerTemporary rt = tempRetailerRepo.save(retailerTemporary);
	    
		return rt;
	}



	@Override
	public Map<String, Object> IndexTemporaryRetailerAsc(int pageno, int pagesize, String field) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = tempRetailerRepo.count();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<TemporaryRetailerProjection> ipo =tempRetailerRepo.indexTemporaryRetailer(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);
		

		return response;
		
	}



	@Override
	public Map<String, Object> IndexTemporaryRetailerDesc(int pageno, int pagesize, String field) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = tempRetailerRepo.count();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<TemporaryRetailerProjection> ipo = tempRetailerRepo.indexTemporaryRetailer(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);
		
		return response;
	
	}



//	@Override
//	public List<TemporaryRetailerProjection> IndexTemporaryRetailerSearch(int pageno, int pagesize, String search) {
//		// TODO Auto-generated method stub
//		Pageable p=PageRequest.of(pageno, pagesize);
//		ModelMapper modelMapper=new ModelMapper();
//		List<TemporaryRetailerProjection> salesReturn=tempRetailerRepo.SearchByTemporaryRetailer(search, p)
//				.stream().map(temporaryRetailerProjection -> modelMapper.map(temporaryRetailerProjection, TemporaryRetailerProjection.class))
//				.collect(Collectors.toList());
//	
//		
//		return salesReturn;
//	
//	}
	

	
	@Override
	public Map<String, Object> SearchTemporaryRetailer(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<TemporaryRetailerProjection> tempRetailer =tempRetailerRepo.SearchByTemporaryRetailer(search, p);

		long searchcount = tempRetailer.size();
		
		response.put("data", tempRetailer);
		response.put("SearchCount", searchcount);

		return response;
	}

}
