package com.SCM.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.SalesExpenseDTO;
import com.SCM.service.SalesExpenseService;

@RestController
@RequestMapping("/api/salesexp")
public class SalesExpenseController {

	@Autowired
	private SalesExpenseService salesExpenseService;

	
	@PostMapping("/")
	public ResponseEntity<?> save(@ModelAttribute SalesExpenseDTO salesExpenseDTO,
			                      @RequestParam("salesexpenseitems") String salesexpenseitems,
			                      @RequestParam("file") MultipartFile[] file) throws IOException {

		return new ResponseEntity<>(salesExpenseService.save1(salesExpenseDTO,salesexpenseitems,file), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id")int id,
			                        @ModelAttribute SalesExpenseDTO salesExpenseDTO,
			                        @RequestParam("salesexpenseitems") String salesexpenseitems,
			                        @RequestParam("file") MultipartFile[] file) throws IOException {

		return new ResponseEntity<>(salesExpenseService.update1(id,salesExpenseDTO,salesexpenseitems,file), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable("id")int id){
		
		return new ResponseEntity<>(salesExpenseService.get(id),HttpStatus.OK);
	}
	
	@GetMapping("/item/{id}")
	public ResponseEntity<?> getimagebyitemid(@PathVariable("id")int id){
		
		return new ResponseEntity<>(salesExpenseService.expenseImagesbyitemid(id),HttpStatus.OK);
	}
	

	@GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexSalesExpense(@PathVariable("pageno") int pageno,
			                                   @PathVariable("pagesize") int pagesize,
			                                   @PathVariable("sortby") String sortby,
			                                   @PathVariable("field") String field,
			                                   @PathVariable("search") String search) {

		if (!search.equals(" ")) {

			return new ResponseEntity<>(salesExpenseService.SearchSalesExp(pageno, pagesize, search), HttpStatus.OK);

		} else if ("asc".equals(sortby)) {

			return new ResponseEntity<>(salesExpenseService.IndexSalesExpAsc(pageno, pagesize, field), HttpStatus.OK);

		} else if ("desc".equals(sortby)) {

			return new ResponseEntity<>(salesExpenseService.SearchSalesExp(pageno, pagesize, field), HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

	
//	-=============================
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> del(@PathVariable("id")int id) throws IOException{
		
		salesExpenseService.deletesalesexp(id);
		return new ResponseEntity<>("delete ",HttpStatus.OK);
	}
	
	
//	=================================
	
	@GetMapping("/report/{sid}/{fromdate}/{todate}")
	public ResponseEntity<?> salesexpensereport(@PathVariable("sid")int sid,
			                                    @PathVariable("fromdate")String fromdate,
			                                    @PathVariable("todate")String todate){
		
		return new ResponseEntity<>(salesExpenseService.salesexpreport(sid,fromdate,todate),HttpStatus.OK);
	}
	
}
