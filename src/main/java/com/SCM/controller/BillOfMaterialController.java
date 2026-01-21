package com.SCM.controller;

import java.util.List;

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

import com.SCM.dto.BillOfMaterialDto;
import com.SCM.service.BillOfMaterialsService;

@RestController
@RequestMapping("/api/bom")
@CrossOrigin(origins = "*")
public class BillOfMaterialController {
	
	@Autowired
	public BillOfMaterialsService bomService;
	
	
	@PostMapping("/")
	public ResponseEntity<BillOfMaterialDto> saveBom(@RequestBody BillOfMaterialDto bomDto){
		
		return new ResponseEntity<>(bomService.saveBom(bomDto), HttpStatus.OK);
	}
	
	@GetMapping("/{pageNo}/{pageSize}/{field}")
	public ResponseEntity<List<BillOfMaterialDto>> getBom(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize,@PathVariable("field") String field){
		
		return new ResponseEntity<>(bomService.getBom(pageNo, pageSize, field), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id){
		return new ResponseEntity<>(bomService.deleteById(id),HttpStatus.OK);
	}
	
	
	
	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexAllDistributor(@PathVariable("pageno") int pageno,
			                                     @PathVariable("pagesize") int pagesize,
			                                     @PathVariable("sortby") String sortby,
			                                     @PathVariable("field") String field,
			                                     @PathVariable("search") String search) {
		if (!search.equals(" ")) {
			
			return new ResponseEntity<>(bomService.SearchBillOfMaterial(pageno, pagesize, search), HttpStatus.OK);
			
		} else if ("asc".equals(sortby)) {
			
			return new ResponseEntity<>(bomService.IndexBillOfMaterialAsc(pageno, pagesize, field), HttpStatus.OK);
			
		} else if ("desc".equals(sortby)) {
			
			return new ResponseEntity<>(bomService.IndexBillOfMaterialDesc(pageno, pagesize, field),HttpStatus.OK);
			
		} else {
			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") int id){
		
		return new ResponseEntity<>(bomService.getById1(id), HttpStatus.OK);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBom(@RequestBody BillOfMaterialDto bomDto,@PathVariable("id")int id){
		
		return new ResponseEntity<>(bomService.updateBom(bomDto,id), HttpStatus.OK);
	}
}
