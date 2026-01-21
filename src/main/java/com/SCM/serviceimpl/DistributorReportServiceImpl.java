package com.SCM.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.SCM.projection.DistributorMonthReportProjection;
import com.SCM.repository.DistributorMonthWiseRepository;
import com.SCM.service.DistributorMonthReportService;


@Service
public class DistributorReportServiceImpl implements DistributorMonthReportService{
	
	@Autowired
	private DistributorMonthWiseRepository distributorMonthWiseRepository;

	@Override
	public Map<String, Object> IndexDistributorReportAsc(long distributor_id, int pageno, int pagesize,
			String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = distributorMonthWiseRepository.count();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<DistributorMonthReportProjection> Listofproduct = distributorMonthWiseRepository.findByDistributorIdAndMonth(distributor_id, p);

		response.put("Index", Listofproduct);
		response.put("Pages", pages);
		response.put("Enteries", countpages);

		return response;
	}

	@Override
	public Map<String, Object> IndexDistributorReportDSC(long distributor_id, int pageno, int pagesize,
			String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = distributorMonthWiseRepository.count();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if(rem > 0)
		{
			pages++;
		}

		List<DistributorMonthReportProjection> Listofproduct = distributorMonthWiseRepository.findByDistributorIdAndMonth(distributor_id, p);

		response.put("Index", Listofproduct);
		response.put("Pages", pages);
		response.put("Enteries", countpages);

		return response;
	}

}
