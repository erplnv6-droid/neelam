package com.SCM.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.projection.DistributorMonthReportProjection;
import com.SCM.projection.RetailerMonthReportProjection;
import com.SCM.repository.DistributorMonthWiseRepository;
import com.SCM.repository.RetailerMonthWiseRepository;
import com.SCM.service.DistributorMonthReportService;
@RestController
@RequestMapping("/api/dist/report")
public class DistributorMonthWiseController {
	@Autowired
	private DistributorMonthWiseRepository distributorMonthWiseRepository;
	
	@Autowired
	private DistributorMonthReportService distributorMonthReportService;
	
	@GetMapping("/get/{distributor_id}/{pageno}/{pagesize}/{sort}/{field}")
	public ResponseEntity<?> findByDistributorMonthAndDistributorId(@PathVariable ("distributor_id") long distributor_id,
			@PathVariable("pageno")int pageno,
            @PathVariable("pagesize") int pagesize,
            @PathVariable("sort")String sort,
            @PathVariable("field")String field)
	{
		
         
		  if("asc".equals(sort)){
				
				return new ResponseEntity<>(distributorMonthReportService.IndexDistributorReportAsc(distributor_id, pageno, pagesize, field),HttpStatus.OK);
			}
			else if("desc".equals(sort)) {
				
				return new ResponseEntity<>(distributorMonthReportService.IndexDistributorReportDSC(distributor_id, pageno, pagesize, field),HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("Invalid Route",HttpStatus.BAD_REQUEST);
			}	

            
            
    
		
	}
}
