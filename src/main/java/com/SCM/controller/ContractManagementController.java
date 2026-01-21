package com.SCM.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.ContractManagementDto;
import com.SCM.service.ContractManagementService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cm")
public class ContractManagementController {

	@Autowired
	private ContractManagementService contractManagementService;

	private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;
	
	@PostMapping("/")
	public ResponseEntity<?> save(@ModelAttribute ContractManagementDto contractManagementDto,
			                      @RequestParam("pdf") MultipartFile pdffile) throws IOException {
		
		if(pdffile.getSize() > MAX_FILE_SIZE)
		{
			return new ResponseEntity<>("The file size 2MB execced to limit",HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(contractManagementService.saveCM(contractManagementDto, pdffile),HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<?> show() {

		return new ResponseEntity<>(contractManagementService.getAllCM(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable int id) {

		return new ResponseEntity<>(contractManagementService.getCMById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @ModelAttribute ContractManagementDto contractManagementDto,
			                                              @RequestPart("pdf") MultipartFile pdffile) throws IOException {

		return new ResponseEntity<>(contractManagementService.updateCM(contractManagementDto, pdffile, id),HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCompany(@PathVariable int id) {

		return new ResponseEntity<>(contractManagementService.deleteCM(id), HttpStatus.OK);

	}

	@GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexDistributorMinimumStock(@PathVariable("pageno") int pageno,
			                                              @PathVariable("pagesize") int pagesize, 
			                                              @PathVariable("sortby") String sortby,
			                                              @PathVariable("field") String field,
			                                              @PathVariable("search") String search) {
		if (!search.equals(" ")) {

			return new ResponseEntity<>(contractManagementService.SearchContractManagement(pageno, pagesize, search),HttpStatus.OK);

		} else if ("asc".equals(sortby)) {

			return new ResponseEntity<>(contractManagementService.IndexContractManagementASC(pageno, pagesize, field),HttpStatus.OK);

		} else if ("desc".equals(sortby)) {

			return new ResponseEntity<>(contractManagementService.IndexContractManagementDESC(pageno, pagesize, field),HttpStatus.OK);

		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

	
	@GetMapping("/report")
	public ResponseEntity<?> Report() {

		return new ResponseEntity<>(contractManagementService.ContractMgmtReport(), HttpStatus.OK);
	}
	
	
	
	
	@GetMapping("/report/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> ReportContractMgmt(@PathVariable("pageno") int pageno,
			                                    @PathVariable("pagesize") int pagesize, 
			                                    @PathVariable("sortby") String sortby,
			                                    @PathVariable("field") String field,
			                                    @PathVariable("search") String search) {
		if (!search.equals(" ")) {

			return new ResponseEntity<>(contractManagementService.searchReportByContractMgmt(pageno, pagesize, search),HttpStatus.OK);

		} else if ("asc".equals(sortby)) {

			return new ResponseEntity<>(contractManagementService.reportContractMgmtAsc(pageno, pagesize, field),HttpStatus.OK);

		} else if ("desc".equals(sortby)) {

			return new ResponseEntity<>(contractManagementService.reportContractmgmtDesc(pageno, pagesize, field),HttpStatus.OK);

		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
