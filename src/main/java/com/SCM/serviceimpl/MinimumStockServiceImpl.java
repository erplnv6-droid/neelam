package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.SCM.IndexDto.IndexMinimumStock;
import com.SCM.dto.MinimumStockDto;
import com.SCM.dto.MinimumStockReportDto;
import com.SCM.model.Branch;
import com.SCM.model.MinimumStock;
import com.SCM.projection.MinimumStockReportProjection;
import com.SCM.repository.MinimumStockRepository;
import com.SCM.repository.PagingAndSortingRepo.MinimumStockReportRepo;
import com.SCM.service.MinimumStockService;


@Service
public class MinimumStockServiceImpl implements MinimumStockService {

	@Autowired
	private MinimumStockRepository minimumStockRepository;
	
	@Autowired 
	private MinimumStockReportRepo minimumStockReportRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
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
	public MinimumStock savedminimumStock(MinimumStockDto minimumStockDto) {
	
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		Branch b = new Branch();
		b.setCreatebyname(uname);
		b.setCreatedby(uid);
		b.setRole(role);
		
		MinimumStock ms = new MinimumStock();
		ms.setStockqty(minimumStockDto.getStockqty());
		ms.setWid(minimumStockDto.getWid());
		ms.setStockdate(LocalDate.now());
		ms.setCreateddate(LocalDate.now());
		ms.setCreatedtime(LocalTime.now());
		ms.setProduct(minimumStockDto.getProduct());

		return minimumStockRepository.save(ms);
	}

	@Override
	public List<MinimumStock> showminimumStocks() {
		return minimumStockRepository.findAll();
	}

	
	@Override
	public MinimumStock showminimumStocksId(int id) {
		MinimumStock ms = minimumStockRepository.findById(id).get();
		return ms;
	}

	
	@Override
	public MinimumStock updateMinimumStocks(MinimumStock minimumStock, int id) {
		
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		
		MinimumStock ms = minimumStockRepository.findById(id).get();
		ms.setStockqty(minimumStock.getStockqty());
		ms.setWid(minimumStock.getWid());
		ms.setStockdate(LocalDate.now());
		ms.setProduct(minimumStock.getProduct());
		ms.setUpdateddate(LocalDate.now());
		ms.setUpdatedtime(LocalTime.now());
		ms.setUpdatedbyname(uname);
		ms.setUpdatedby(uid);
		ms.setUpdatedrole(role);

		return minimumStockRepository.save(ms);
	}
	
	

	@Override
	public void deleteMinimumStock(int id) {
		minimumStockRepository.deleteById(id);

	}

	@Override
	public List<MinimumStockReportProjection> MinimumStockReports(int wid) {
			
		List<MinimumStockReportProjection> data = minimumStockReportRepo.getMinimumStockReport(wid);
		
		List<MinimumStockReportDto> stockrReportDtoList = new ArrayList<>();
		
//		return minimumStockRepository.minimumstockreports();
		return data;
	}

	@Override
	public Map<String, Object> ascMinimumStockReport(int wid,int pageno, int pagesize, String field,String sort) {
		
		Map<String, Object> report = new HashMap<>();
		Sort sorted;
		if(sort.equals("desc")) {
			sorted = Sort.by(Sort.Direction.DESC, field);
		}else {
			sorted =  Sort.by(Sort.Direction.ASC, field);
		}
		
		Pageable pageable = PageRequest.of(pageno, pagesize, sorted);
		long totalItems = minimumStockRepository.getMinimumStockReportForCount(wid).size();
		long pagesCount = (long) Math.abs(totalItems / pagesize);
		
		long remainder = totalItems % pagesize;
		
		if(remainder> 0) {
			pagesCount ++;
		}
		List<MinimumStockReportProjection> data = minimumStockRepository.getMinimumStockReport(wid, pageable);
		report.put("Index", data);
		report.put("Pages", pagesCount);
		report.put("Enteries", totalItems);

		return report;
	}


	
	
	@Override
	public Map<String, Object> IndexMinimumStockAsc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = minimumStockRepository.count();
		long pages = countpages / pagesize;

		List<IndexMinimumStock> ipo = minimumStockRepository.indexMinimumStock(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);
		

		return response;
	}

	@Override
	public Map<String, Object> IndexMinimumStockDesc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = minimumStockRepository.count();
		long pages = countpages / pagesize;

		List<IndexMinimumStock> ipo = minimumStockRepository.indexMinimumStock(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);
		

		return response;
	}

	@Override
	public Map<String, Object> SearchMinimumStock(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();		
		Pageable p = PageRequest.of(pageno, pagesize);

		List<IndexMinimumStock> mns = minimumStockRepository.SearchByMinimumStock(search, p);
		
		long searchcount = mns.size();
		
		response.put("data", mns);
		response.put("SearchCount", searchcount);

		return response;
	}
	
	

}
