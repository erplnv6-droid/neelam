package com.SCM.serviceimpl;

import java.sql.Date;
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

import com.SCM.IndexDto.IndexDistributorOpeningStock;
import com.SCM.dto.DistributorOpeningStockDto;
import com.SCM.mapper.DistributorOpeningStockMapper;
import com.SCM.model.DistributorOpeningStock;
import com.SCM.repository.DistributorOpeningRepository;
import com.SCM.service.DistributorOpeningService;

@Service
public class DistributorOpeningServiceImpl implements DistributorOpeningService {
	@Autowired
	private DistributorOpeningRepository distributorOpeningRepository;

	@Autowired
	private DistributorOpeningStockMapper distributorOpeningStockMapper;

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

	public DistributorOpeningStockDto createOpeningStock(DistributorOpeningStockDto distributorOpeningStockDto) {
		
		Long uid = Long.valueOf(getUserId());
		String uname = getUserName();
		String role = getRolename();
		
		DistributorOpeningStock distributorOpeningStock = this.distributorOpeningStockMapper.mapToDistributorOpeningStock(distributorOpeningStockDto);
		distributorOpeningStock.setDate(new Date(System.currentTimeMillis()));
		distributorOpeningStock.setGrandtotal(distributorOpeningStockDto.getGrandtotal());
		distributorOpeningStock.setUom(distributorOpeningStockDto.getUom());
		distributorOpeningStock.setLastUpdate(new java.util.Date());
		distributorOpeningStock.setCreateddate(LocalDate.now());
		distributorOpeningStock.setCreatedtime(LocalTime.now());
		distributorOpeningStock.setCreatebyname(uname);
		distributorOpeningStock.setCreatedby(uid.longValue());
		distributorOpeningStock.setRole(role);
		distributorOpeningStock.setDistributor(distributorOpeningStockDto.getDistributor());
		DistributorOpeningStock distributorOpeningStock2 = distributorOpeningRepository
				.save(distributorOpeningStock);
		DistributorOpeningStockDto distributorOpeningStockDto2 = this.distributorOpeningStockMapper				.mapToDistributorOpeningStockDto(distributorOpeningStock2);
		distributorOpeningStock.setDistributorOpeningStockItems(distributorOpeningStockDto.getDistributorOpeningStockItems());
		return distributorOpeningStockDto2;
	}

	public List<DistributorOpeningStockDto> findAllOpeningStock() {
		List<DistributorOpeningStock> distributorOpeningStockDtos = this.distributorOpeningRepository.findAll();
		return (List<DistributorOpeningStockDto>) distributorOpeningStockDtos.stream()
				.map(this.distributorOpeningStockMapper::mapToDistributorOpeningStockDto).collect(Collectors.toList());
	}

	public Optional<DistributorOpeningStockDto> findByOpeningStockId(long id) {
		Optional<DistributorOpeningStock> optional = this.distributorOpeningRepository.findById(Long.valueOf(id));
		DistributorOpeningStock findby = optional.get();
		return Optional.of(this.distributorOpeningStockMapper.mapToDistributorOpeningStockDto(findby));
	}

	public DistributorOpeningStockDto updateByOpeningStock(DistributorOpeningStockDto distributorOpeningStockDto) {

		Long uid = Long.valueOf(getUserId());
		String uname = getUserName();
		String role = getRolename();

		DistributorOpeningStock distributorOpeningStock = this.distributorOpeningStockMapper
				.mapToDistributorOpeningStock(distributorOpeningStockDto);
		distributorOpeningStock.setLastUpdate(new java.util.Date());
		distributorOpeningStock.setGrandtotal(distributorOpeningStockDto.getGrandtotal());
		distributorOpeningStock.setUpdatedbyname(uname);
		distributorOpeningStock.setUpdatedby(uid.longValue());
		distributorOpeningStock.setUpdatedrole(role);
		distributorOpeningStock.setUpdatedtime(LocalTime.now());
		distributorOpeningStock.setUpdateddate(LocalDate.now());
		DistributorOpeningStock distributorOpeningStock2 = (DistributorOpeningStock) this.distributorOpeningRepository
				.save(distributorOpeningStock);
		DistributorOpeningStockDto distributorOpeningStockDto2 = this.distributorOpeningStockMapper
				.mapToDistributorOpeningStockDto(distributorOpeningStock2);
		return distributorOpeningStockDto2;
	}

	public String deleteByOpeningStockId(long id) {
		this.distributorOpeningRepository.deleteById(Long.valueOf(id));
		return "Data Deleted Successfully";
	}

	public String deleteAllOpeningStock() {
		this.distributorOpeningRepository.deleteAll();
		return "All Data Deleted Successfully";
	}

	public Map<String, Object> indexDistributorOpeningStockDesc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pagno, pagesize, sort);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long distid = userDetails.getId();
		List<String> list = (List<String>) userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		for (String s : list) {
			if (s.equals("ROLE_ADMIN") ||  s.equals("ROLE_NSM")) {
				long countpages = this.distributorOpeningRepository.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexDistributorOpeningStock> ipo = this.distributorOpeningRepository
						.indexDistributorOpeningStocks((Pageable) pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			if (s.equals("ROLE_DISTRIBUTOR")) {
				long countpages = this.distributorOpeningRepository.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexDistributorOpeningStock> ipo = this.distributorOpeningRepository
						.indexDistributorOpeningStocksByDistributorId((Pageable) pageRequest, distid.longValue());
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_RSM")) {
				long countpages = this.distributorOpeningRepository.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexDistributorOpeningStock> ipo = this.distributorOpeningRepository
						.indexDistributorOpeningStocksByRsmId(pageRequest, distid);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_ASM")) {
				long countpages = this.distributorOpeningRepository.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexDistributorOpeningStock> ipo = this.distributorOpeningRepository
						.indexDistributorOpeningStocksByAsmId(pageRequest, distid);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			
			if (s.equals("ROLE_ASE")) {
				long countpages = this.distributorOpeningRepository.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexDistributorOpeningStock> ipo = this.distributorOpeningRepository
						.indexDistributorOpeningStocksByAseId(pageRequest, distid);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			
		}
		return null;
	}

	public Map<String, Object> searchByDistributorOpeningStock(int pageno, int pagesize, String search) {
		PageRequest pageRequest = PageRequest.of(pageno, pagesize);
		Map<String, Object> response = new HashMap<>();
		List<IndexDistributorOpeningStock> dos = this.distributorOpeningRepository
				.searchByIndexDistributorOpeningStock(search, (Pageable) pageRequest);
		long searchcount = dos.size();
		response.put("data", dos);
		response.put("SearchCount", Long.valueOf(searchcount));
		return response;
	}

	public Map<String, Object> indexDistributorOpeningStockAsc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pagno, pagesize, sort);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long distid = userDetails.getId();
		List<String> list = (List<String>) userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		for (String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				long countpages = this.distributorOpeningRepository.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexDistributorOpeningStock> ipo = this.distributorOpeningRepository
						.indexDistributorOpeningStocks((Pageable) pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			if (s.equals("ROLE_DISTRIBUTOR")) {
				long countpages = this.distributorOpeningRepository.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexDistributorOpeningStock> ipo = this.distributorOpeningRepository
						.indexDistributorOpeningStocksByDistributorId((Pageable) pageRequest, distid.longValue());
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_RSM")) {
				long countpages = this.distributorOpeningRepository.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexDistributorOpeningStock> ipo = this.distributorOpeningRepository
						.indexDistributorOpeningStocksByRsmId(pageRequest, distid);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_ASM")) {
				long countpages = this.distributorOpeningRepository.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexDistributorOpeningStock> ipo = this.distributorOpeningRepository
						.indexDistributorOpeningStocksByAsmId(pageRequest, distid);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			
			if (s.equals("ROLE_ASE")) {
				long countpages = this.distributorOpeningRepository.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexDistributorOpeningStock> ipo = this.distributorOpeningRepository
						.indexDistributorOpeningStocksByAseId(pageRequest, distid);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
		}
		return null;
	}

	public DistributorOpeningStock update(DistributorOpeningStockDto distributorOpeningStockDto, long id) {

		Long uid = Long.valueOf(getUserId());
		String uname = getUserName();
		String role = getRolename();

		DistributorOpeningStock distributorOpeningStock = this.distributorOpeningRepository.findById(Long.valueOf(id))
				.get();
		distributorOpeningStock
				.setDistributorOpeningStockItems(distributorOpeningStockDto.getDistributorOpeningStockItems());
		distributorOpeningStock.setUser_date(distributorOpeningStockDto.getUser_date());
		distributorOpeningStock.setQuantity(distributorOpeningStockDto.getQuantity());
		distributorOpeningStock.setDate(distributorOpeningStockDto.getDate());
		distributorOpeningStock.setDistributor(distributorOpeningStockDto.getDistributor());
		distributorOpeningStock.setLastUpdate(distributorOpeningStockDto.getLastUpdate());
		distributorOpeningStock.setUpdatedbyname(uname);
		distributorOpeningStock.setUpdatedby(uid.longValue());
		distributorOpeningStock.setUpdatedrole(role);
		distributorOpeningStock.setUpdatedtime(LocalTime.now());
		distributorOpeningStock.setUpdateddate(LocalDate.now());
		DistributorOpeningStock update = (DistributorOpeningStock) this.distributorOpeningRepository
				.save(distributorOpeningStock);
		return update;
	}

	public DistributorOpeningStock findByOpeningStockId1(long id) {
		DistributorOpeningStock distributorOpeningStock = this.distributorOpeningRepository.findById(Long.valueOf(id))
				.get();
		return distributorOpeningStock;
	}
}
