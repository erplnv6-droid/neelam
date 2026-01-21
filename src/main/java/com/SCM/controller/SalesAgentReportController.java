package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.service.SalesAgentExpensesClaimService;

@RestController
@RequestMapping("/api/salesexpense/report")
public class SalesAgentReportController {
	
	@Autowired
	private SalesAgentExpensesClaimService salesExpensesClaimService;


	
	@GetMapping("/pages/{id}/{startdate}/{enddate}/{pageno}/{pagesize}/{sort}/{field}")
	public ResponseEntity<?> indexSalesExpenseReportByStaffId(@PathVariable("id") long id,
			                                                  @PathVariable("startdate") String stardate,
			                                                  @PathVariable("enddate") String enddate,
			                                                  @PathVariable("pageno") int pageno, 
			                                                  @PathVariable("pagesize") int pagesize,
			                                                  @PathVariable("sort") String sort,
			                                                  @PathVariable("field") String field) {
		if ("asc".equals(sort)) {

			return new ResponseEntity<>(salesExpensesClaimService.ascSalesAgenetExpenseByStaffId(id, stardate, enddate,pageno, pagesize, field), HttpStatus.OK);
		
		} else if ("desc".equals(sort)) {

			return new ResponseEntity<>(salesExpensesClaimService.descSalesAgenetExpenseByStaffId(id, stardate, enddate,pageno, pagesize, field), HttpStatus.OK);

		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.OK);
		}

	}
}
