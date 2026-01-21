package com.SCM.serviceimpl;

import java.util.HashMap;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.SCM.projection.RetailerMonthReportProjection;
import com.SCM.repository.RetailerMonthWiseRepository;
import com.SCM.service.RetailerMonthReportService;


@Service
public class RetailerMonthReportServiceImpl implements RetailerMonthReportService{
	
	@Autowired
	private RetailerMonthWiseRepository monthWiseRepository;

	@Override
	public Map<String, Object> IndexRetailerReportAsc(long retailer_id, int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = monthWiseRepository.count();
		long pages = countpages / pagesize;

		List<RetailerMonthReportProjection> Listofproduct = monthWiseRepository.findByRetailerMonthAndRetailerId(retailer_id, p);

		response.put("Index", Listofproduct);
		response.put("Pages", pages);
		response.put("Enteries", countpages);

		return response;
	}

	
	
	@Override
	public Map<String, Object> IndexRetailerReportDSC(long retailer_id, int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = monthWiseRepository.count();
		long pages = countpages / pagesize;

		List<RetailerMonthReportProjection> Listofproduct = monthWiseRepository.findByRetailerMonthAndRetailerId(retailer_id, p);

		response.put("Index", Listofproduct);
		response.put("Pages", pages);
		response.put("Enteries", countpages);

		return response;
	}

	
	

}
