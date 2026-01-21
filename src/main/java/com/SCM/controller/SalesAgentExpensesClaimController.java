package com.SCM.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.IndexDto.IndexSalesAgendExpenseClaim;
import com.SCM.dto.SalesAgentExpenseClaimDto;
import com.SCM.dto.Status;
import com.SCM.repository.SalesAgentExpensesClaimRepository;
import com.SCM.service.SalesAgentExpensesClaimService;

@RestController
@RequestMapping("/api/salesAgent")
public class SalesAgentExpensesClaimController {

	@Autowired
	private SalesAgentExpensesClaimService salesExpensesClaimService;

	@Autowired
	private SalesAgentExpensesClaimRepository claimRepository;

	@PostMapping("/save")
	public ResponseEntity<?> saveSalesExpensesClaimService(
			@ModelAttribute SalesAgentExpenseClaimDto agentExpenseClaimDto, @RequestParam("files") MultipartFile files)
			throws IOException {

		return new ResponseEntity<SalesAgentExpenseClaimDto>(
				salesExpensesClaimService.saveAgentExpenseClaimDto(agentExpenseClaimDto, files), HttpStatus.OK);
	}

	@GetMapping("/getSalesAgent/{id}")
	public ResponseEntity<?> getSalesExpensesClaimsService(@PathVariable long id) {
		return new ResponseEntity<>(salesExpensesClaimService.getById(id), HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(salesExpensesClaimService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateSalesExpensesClaimService(@PathVariable long id,
			@RequestBody SalesAgentExpenseClaimDto agentExpenseClaimDto) throws IOException {
		return new ResponseEntity<String>(
				salesExpensesClaimService.updateSalesAgentExpensesClaimDto(id, agentExpenseClaimDto), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable long id) {
		return new ResponseEntity<>(salesExpensesClaimService.deleteSalesAgentExpensesClaimDto(id), HttpStatus.OK);
	}

	@PutMapping("/updatestatus/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable long id, @RequestBody Status status) {
		return new ResponseEntity<>(salesExpensesClaimService.updateStatus(id, status.getStatus()), HttpStatus.OK);
	}

	@GetMapping("/staffclaim/{id}")
	public List<IndexSalesAgendExpenseClaim> getByStaffId(@PathVariable long id) {
		List<IndexSalesAgendExpenseClaim> allSalesAgentClaim = claimRepository.findAllSalesAgentClaim(id);
		return allSalesAgentClaim;
	}

	@GetMapping("/salesagentclaim/pages/{staffid}/{pageno}/{pagesize}/{sort}/{field}/{search}")
	public ResponseEntity<?> getSalesAgentClaim(@PathVariable("pageno") int pageno,
			@PathVariable("pagesize") int pagesize, @PathVariable("sort") String sort,
			@PathVariable("field") String field, @PathVariable("search") String search,
			@PathVariable("staffid") int staffid

	) {
		if (!search.equals(" ")) {
			System.out.println("1");
			return new ResponseEntity<>(
					salesExpensesClaimService.searchSalesAgentExpenseClaims(staffid, pageno, pagesize, search),
					HttpStatus.OK);
		} else if ("asc".equals(sort)) {
			System.out.println("2");

			return new ResponseEntity<>(
					salesExpensesClaimService.ascSalesAgentExpenseClaims(staffid, pageno, pagesize, field, sort),
					HttpStatus.OK);
		} else if ("desc".equals(sort)) {
			System.out.println("3");

			return new ResponseEntity<>(
					salesExpensesClaimService.ascSalesAgentExpenseClaims(staffid, pageno, pagesize, field, sort),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}

	}

	

	@GetMapping("/pages/{pageno}/{pagesize}/{sort}/{field}/{search}")
	public ResponseEntity<?> indexReport(@PathVariable("pageno") int pageno, @PathVariable("pagesize") int pagesize,
			                             @PathVariable("sort") String sort, @PathVariable("field") String field,
			                             @PathVariable("search") String search) {
		if (!search.equals(" ")) {
			return new ResponseEntity<>(salesExpensesClaimService.searchSalesAgentExpense(pageno, pagesize, search),
					HttpStatus.OK);

		} else if ("asc".equals(sort)) {

			return new ResponseEntity<>(salesExpensesClaimService.ascSalesAgentExpense(pageno, pagesize, field, sort),
					HttpStatus.OK);
		} else if ("desc".equals(sort)) {

			return new ResponseEntity<>(salesExpensesClaimService.ascSalesAgentExpense(pageno, pagesize, field, sort),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/salesexpense/pages/{id}/{startdate}/{enddate}/{pageno}/{pagesize}/{sort}/{field}")
	public ResponseEntity<?> indexSalesExpenseReportByStaffId(@PathVariable("id") long id,
			                             @PathVariable("startdate") String stardate, @PathVariable("enddate") String enddate,
		                               	 @PathVariable("pageno") int pageno, @PathVariable("pagesize") int pagesize,
			                             @PathVariable("sort") String sort, @PathVariable("field") String field,
                                         @PathVariable("search")String search)
	{
//		if (!search.equals(" ")) {
//			return new ResponseEntity<>(salesExpensesClaimService.searchSalesAgenetExpenseByStaffId
//					(id, stardate, enddate, pageno, pagesize, search),HttpStatus.OK); 
//		}
		if ("asc".equals(sort)) {

			return new ResponseEntity<>(salesExpensesClaimService.ascSalesAgenetExpenseByStaffId(id, stardate, enddate,
					pageno, pagesize, field), HttpStatus.OK);
		} else if ("desc".equals(sort)) {

			return new ResponseEntity<>(salesExpensesClaimService.descSalesAgenetExpenseByStaffId(id, stardate, enddate,
					pageno, pagesize, field), HttpStatus.OK);

		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.OK);
		}

	}

}
