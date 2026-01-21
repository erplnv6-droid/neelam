package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.SalesReturnDto;
import com.SCM.service.SalesReturnService;


@RestController
@RequestMapping("/api/sr")
@CrossOrigin(origins = "*")
public class SalesReturnController {

	@Autowired
	private SalesReturnService salesReturnService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> saveSalesReturn(@RequestBody com.SCM.dto.SalesReturnDto salesReturnDto)
	{
		return new ResponseEntity<>(salesReturnService.saveSalesReturn(salesReturnDto),HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> showSalesReturn()
	{
		return new ResponseEntity<>(salesReturnService.getAllSalesReturn(),HttpStatus.OK);	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showSalesReturnById(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(salesReturnService.getSalesReturnById(id),HttpStatus.OK);	
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateSalesReturn(@PathVariable("id") int id,@RequestBody SalesReturnDto salesReturnDto)
	{
	     return new ResponseEntity<>(salesReturnService.updateSalesReturn(salesReturnDto,id),HttpStatus.ACCEPTED);	
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSalesReturn(@PathVariable("id") int id)
	{
		salesReturnService.deleteSalesReturn(id);
		return new ResponseEntity<>("deleted Sales return success",HttpStatus.OK);	
	}	
	
	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexDistributor(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search")String search) 
	{
    	
    	if(!search.equals(" "))
		{
			return new ResponseEntity<>(salesReturnService.SearchSalesReturn(pageno, pagesize,search),HttpStatus.OK) ; 
		}
    	else if("asc".equals(sortby))
		{ 
			return new ResponseEntity<>(salesReturnService.IndexSalesReturnAsc(pageno,pagesize,field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(salesReturnService.IndexSalesReturnDesc(pageno, pagesize, field), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

}
