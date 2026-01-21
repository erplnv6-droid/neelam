package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.DistributorMinimumStockIndex;
import com.SCM.IndexDto.DistributorMinimumStockReportIndex;
import com.SCM.IndexDto.SalesOrderPageDtoProjection;
import com.SCM.dto.DistributorMinimumStockDto;
import com.SCM.model.DistributorMinimumStock;
import com.SCM.repository.DistributorMinimumStockRepo;
import com.SCM.service.DistributorMinimumStockService;

@Service
public class DistributorMinimumStockServiceImpl implements DistributorMinimumStockService {
	@Autowired
	private DistributorMinimumStockRepo distributorMinimumStockRepo;

	public long getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long uid = userDetails.getId();
		return uid.longValue();
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
		List<String> list = (List<String>) userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		String role = list.get(0);
		return role;
	}

	public DistributorMinimumStock saveDistributorMinimumStock(DistributorMinimumStockDto distributorMinimumStockDto) {
		
		Long uid = Long.valueOf(getUserId());
		String uname = getUserName();
		String role = getRolename();
		
		DistributorMinimumStock dms = new DistributorMinimumStock();
		dms.setStockqty(distributorMinimumStockDto.getStockqty());
		dms.setGrandtotal(distributorMinimumStockDto.getGrandtotal());
		dms.setUom(distributorMinimumStockDto.getUom());
		dms.setDistributorid(distributorMinimumStockDto.getDistributorid());
		dms.setDmsdate(distributorMinimumStockDto.getDmsdate());
		dms.setDistributorMinimumStockItems(distributorMinimumStockDto.getDistributorMinimumStockItems());
		dms.setCreateddate(LocalDate.now());
		dms.setCreatedtime(LocalTime.now());
		dms.setCreatebyname(uname);
		dms.setCreatedby(uid.longValue());
		dms.setRole(role);
		return (DistributorMinimumStock) this.distributorMinimumStockRepo.save(dms);
	}

	public List<DistributorMinimumStock> getAllDistributorMinimumStock() {
		return this.distributorMinimumStockRepo.findAll();
	}

	public DistributorMinimumStock getDistributorMinimumStockById(int id) {
		DistributorMinimumStock distributorMinimumStock = this.distributorMinimumStockRepo.findById(Integer.valueOf(id))
				.get();
		return distributorMinimumStock;
	}

	public String deleteDistributorMinimumStock(int id) {
		this.distributorMinimumStockRepo.deleteById(Integer.valueOf(id));
		return "delete success";
	}

	public DistributorMinimumStock updateDistributorMinimumStock(DistributorMinimumStockDto distributorMinimumStockDto,
			int id) {
		Long uid = Long.valueOf(getUserId());
		String uname = getUserName();
		String role = getRolename();
		DistributorMinimumStock distributorMinimumStock = this.distributorMinimumStockRepo.findById(Integer.valueOf(id))
				.get();
		distributorMinimumStock.setStockqty(distributorMinimumStockDto.getStockqty());
		distributorMinimumStock
				.setDistributorMinimumStockItems(distributorMinimumStockDto.getDistributorMinimumStockItems());
		distributorMinimumStock.setDistributorid(distributorMinimumStockDto.getDistributorid());
		distributorMinimumStock.setDmsdate(distributorMinimumStockDto.getDmsdate());
		distributorMinimumStock.setGrandtotal(distributorMinimumStockDto.getGrandtotal());
		distributorMinimumStock.setUom(distributorMinimumStockDto.getUom());
		distributorMinimumStock.setUpdateddate(LocalDate.now());
		distributorMinimumStock.setUpdatedtime(LocalTime.now());
		distributorMinimumStock.setUpdatedbyname(uname);
		distributorMinimumStock.setUpdatedby(uid.longValue());
		distributorMinimumStock.setUpdatedrole(role);
		return (DistributorMinimumStock) this.distributorMinimumStockRepo.save(distributorMinimumStock);
	}

	public Map<String, Object> IndexDMSAsc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pagno, pagesize, sort);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		long id = userDetails.getId().longValue();
		int uid = (int) id;
		List<String> list = (List<String>) userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		for (String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				long countpages = this.distributorMinimumStockRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<DistributorMinimumStockIndex> ipo = this.distributorMinimumStockRepo.IndexDistributorMinimumStock(pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_DISTRIBUTOR")) {
				long countpages = this.distributorMinimumStockRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<DistributorMinimumStockIndex> ipo = this.distributorMinimumStockRepo.IndexDistributorMinimumStockDistributor(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_RSM")) {
				long countpages = this.distributorMinimumStockRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<DistributorMinimumStockIndex> ipo = this.distributorMinimumStockRepo.IndexDistributorMinimumStockRsm(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_ASM")) {
				long countpages = this.distributorMinimumStockRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<DistributorMinimumStockIndex> ipo = this.distributorMinimumStockRepo.IndexDistributorMinimumStockAsm(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_ASE")) {
				long countpages = this.distributorMinimumStockRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<DistributorMinimumStockIndex> ipo = this.distributorMinimumStockRepo.IndexDistributorMinimumStockAse(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			
		}
		return null;
	}

	public Map<String, Object> IndexDMSDesc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pagno, pagesize, sort);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		long id = userDetails.getId().longValue();
		int uid = (int) id;
		List<String> list = (List<String>) userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		for (String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				long countpages = this.distributorMinimumStockRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<DistributorMinimumStockIndex> ipo = this.distributorMinimumStockRepo.IndexDistributorMinimumStock(pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_DISTRIBUTOR")) {
				long countpages = this.distributorMinimumStockRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<DistributorMinimumStockIndex> ipo = this.distributorMinimumStockRepo.IndexDistributorMinimumStockDistributor(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_RSM")) {
				long countpages = this.distributorMinimumStockRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<DistributorMinimumStockIndex> ipo = this.distributorMinimumStockRepo.IndexDistributorMinimumStockRsm(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_ASM")) {
				long countpages = this.distributorMinimumStockRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<DistributorMinimumStockIndex> ipo = this.distributorMinimumStockRepo.IndexDistributorMinimumStockAsm(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_ASE")) {
				long countpages = this.distributorMinimumStockRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<DistributorMinimumStockIndex> ipo = this.distributorMinimumStockRepo.IndexDistributorMinimumStockAse(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			
		}
		return null;

	}

	public Map<String, Object> SearchDMS(int pageno, int pagesize, String search) {
		
		PageRequest pageRequest = PageRequest.of(pageno, pagesize);
		Map<String, Object> response = new HashMap<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		Long id = userDetail.getId();
		int uid = (int) id.longValue();
		List<String> list = (List<String>) userDetail.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		for (String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				List<DistributorMinimumStockIndex> receipt = this.distributorMinimumStockRepo.SearchByDistributorMinimumStock(search,
						(Pageable) pageRequest);
				int size = receipt.size();
				response.put("data", receipt);
				response.put("SearchCount", Integer.valueOf(size));
				return response;
			}
			if (s.equals("ROLE_DISTRIBUTOR")) {
				List<DistributorMinimumStockIndex> receipt = this.distributorMinimumStockRepo.SearchByDistributorMinimumStockDistributor(search,
						pageRequest,uid);
				int size = receipt.size();
				response.put("data", receipt);
				response.put("SearchCount", Integer.valueOf(size));
				return response;
			}
			
			if (s.equals("ROLE_RSM")) {
				List<DistributorMinimumStockIndex> receipt = this.distributorMinimumStockRepo.SearchByDistributorMinimumStockRsm(search,
						pageRequest,uid);
				int size = receipt.size();
				response.put("data", receipt);
				response.put("SearchCount", Integer.valueOf(size));
				return response;
			}
			
			if (s.equals("ROLE_ASM")) {
				List<DistributorMinimumStockIndex> receipt = this.distributorMinimumStockRepo.SearchByDistributorMinimumStockAsm(search,
						pageRequest,uid);
				int size = receipt.size();
				response.put("data", receipt);
				response.put("SearchCount", Integer.valueOf(size));
				return response;
			}
			
			if (s.equals("ROLE_ASE")) {
				List<DistributorMinimumStockIndex> receipt = this.distributorMinimumStockRepo.SearchByDistributorMinimumStockAse(search,
						pageRequest,uid);
				int size = receipt.size();
				response.put("data", receipt);
				response.put("SearchCount", Integer.valueOf(size));
				return response;
			}
			
		}
		return null;
	}

	public Map<String, Object> IndexDMSReportAsc(int pageno, int pagesize, String field, int distid) {
		
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, new String[] { field });
		
		PageRequest pageRequest = PageRequest.of(pageno, pagesize, sort);
		long countpages = this.distributorMinimumStockRepo.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0L)
			pages++;
		List<DistributorMinimumStockReportIndex> ipo = this.distributorMinimumStockRepo.MinimumStockForDistributor1(distid,pageRequest);
		response.put("Index", ipo);
		response.put("Enteries", Long.valueOf(countpages));
		response.put("Pages", Long.valueOf(pages));
		return response;
	}

	public Map<String, Object> IndexDMSReportDesc(int pageno, int pagesize, String field, int distid) {
		
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pageno, pagesize, sort);
		long countpages = this.distributorMinimumStockRepo.count();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0L)
			pages++;
		List<DistributorMinimumStockReportIndex> ipo = this.distributorMinimumStockRepo.MinimumStockForDistributor1(distid,pageRequest);
		response.put("Index", ipo);
		response.put("Enteries", Long.valueOf(countpages));
		response.put("Pages", Long.valueOf(pages));
		return response;
	}

	public List<DistributorMinimumStockReportIndex> minimumstockfordistributor(int distid) {
		List<DistributorMinimumStockReportIndex> minimumStockForDistributor = this.distributorMinimumStockRepo
				.MinimumStockForDistributor(distid);
		return minimumStockForDistributor;
	}
}
