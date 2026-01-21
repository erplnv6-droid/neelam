package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.InwardDistributorStockDto;
import com.SCM.service.InwardDistributorStockService;

@RestController
@RequestMapping("/api/indiststock")
public class InwardDistributorStockController {

	@Autowired
	private InwardDistributorStockService distributorStockService;
	
	@PostMapping("/")
	private ResponseEntity<?> save(@RequestBody InwardDistributorStockDto inwardDistributorStockDto)
	{
		return new ResponseEntity<>(distributorStockService.save(inwardDistributorStockDto),HttpStatus.CREATED);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<?> showIDS() {
		
		return new ResponseEntity<>(distributorStockService.getAll(), HttpStatus.OK);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<?> showODSById(@PathVariable("id") int id) {
		
		return new ResponseEntity<>(distributorStockService.getById(id), HttpStatus.OK);
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateODS(@RequestBody InwardDistributorStockDto inwardDistributorStockDto, @PathVariable("id") int id) {
		
		return new ResponseEntity<>(distributorStockService.update(inwardDistributorStockDto,id), HttpStatus.CREATED);
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteODS(@PathVariable("id") int id) {
		
		distributorStockService.deleteInwardDistributor(id);
		
		return new ResponseEntity<>("deleted Inward success", HttpStatus.OK);
	}
	
	
	
	// Inward Distributor Stock Index
	
	
		 @GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
		 public ResponseEntity<?> indexInwardDistribtor(@PathVariable("pageno") int pageno,
					                                     @PathVariable("pagesize") int pagesize,
					                                     @PathVariable("sortby") String sortby,
					                                     @PathVariable("field") String field,
					                                     @PathVariable("search") String search) 
			{   	
			    if(!search.equals(" "))
			    {
				    return new ResponseEntity<>(distributorStockService.searchByInwardDistributorStock(pageno, pagesize,search),HttpStatus.OK) ; 
			    }
	    	    else if("asc".equals(sortby))
				{ 
					return new ResponseEntity<>(distributorStockService.indexInwardDistributorStockAsc(pageno,pagesize,field), HttpStatus.OK);
				}
				else if("desc".equals(sortby))
				{
					return new ResponseEntity<>(distributorStockService.indexInwardDistributorStockDesc(pageno,pagesize,field), HttpStatus.OK);
				}
				else {			
					return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
				}
			}
		 
	
}
