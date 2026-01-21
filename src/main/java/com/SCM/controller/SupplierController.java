package com.SCM.controller;

import com.SCM.dto.SupplierDto;
import com.SCM.model.Supplier;
import com.SCM.payload.MessageResponse;
import com.SCM.repository.SupplierRepository;
import com.SCM.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private SupplierRepository supplierRepository;

	@PostMapping("/add")
	public ResponseEntity<?> SaveSupplier(@RequestBody @Valid SupplierDto supplierDto) {
		
		if(supplierRepository.existsByEmail(supplierDto.getEmail()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		return new ResponseEntity<>(supplierService.saveSupplier(supplierDto),HttpStatus.CREATED);
	}

	@GetMapping("/getAll")
	public List<Supplier> getAllSuppliers() {

		return supplierService.getAllSuppliers();
	}
	
	@GetMapping("/getAll1")
	public List<SupplierDto> getAllSupplierswithDto() {

		return supplierService.getAllSupplierIndex();
	}

	@GetMapping("/getById/{id}")
	public Supplier findSupplierById(@PathVariable int id) {

		return supplierService.getSupplierById(id);
	}

	@PutMapping("/update/{id}")
	public Supplier updateSupplier(@PathVariable int id, @RequestBody SupplierDto supplierDto) {

		return supplierService.updateSupplier(supplierDto, id);
	}

	
	@DeleteMapping("/delete/{id}")
	public String deleteSupplier(@PathVariable int id) {

		return supplierService.deleteSupplier(id);
	}

	
	
	  @GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
		public ResponseEntity<?> IndexBranch(@PathVariable("pageno") int pageno,
				                                  @PathVariable("pagesize") int pagesize,
				                                  @PathVariable("sortby") String sortby,
				                                  @PathVariable("field") String field,
				                                  @PathVariable("search")String search) 
		{
	    	
	    	if(!search.equals(" "))
			{ 
				return new ResponseEntity<>(supplierService.SearchSupplier(pageno, pagesize,search),HttpStatus.OK) ; 
			}
	    	else if("asc".equals(sortby))
			{ 
				return new ResponseEntity<>(supplierService.IndexSupplierAsc(pageno,pagesize,field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(supplierService.IndexSupplierDesc(pageno,pagesize,field), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
}
