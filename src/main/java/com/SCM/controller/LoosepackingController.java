package com.SCM.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.IndexDto.IndexLoosePacking;
import com.SCM.dto.LoosepackingDto;
import com.SCM.model.LoosePacking;
import com.SCM.service.LoosePackingService;

@RestController
@RequestMapping("/api/loosepacking")
public class LoosepackingController {

	@Autowired
	private LoosePackingService loosePackingService;

	@PostMapping("/")
	public ResponseEntity<?> save(@RequestBody LoosepackingDto loosepackingDto) {

		LoosePacking save = loosePackingService.save(loosepackingDto);

		return new ResponseEntity<>(save, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getMethodName(@PathVariable("id") int id) {

		LoosePacking byId = loosePackingService.getById(id);
		return new ResponseEntity<>(byId, HttpStatus.OK);
	}

	@GetMapping("/brand/{bid}")
	public ResponseEntity<?> ProductByBrand(@PathVariable("bid") int bid) {

		List<IndexLoosePacking> fetchProductByBrand = loosePackingService.FetchProductByBrand(bid);

		return new ResponseEntity<>(fetchProductByBrand, HttpStatus.OK);
	}

	
	
	@GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> PaginationLoosePacking(@PathVariable("pageno") int pageno,
			                                        @PathVariable("pagesize") int pagesize,
			                                        @PathVariable("sortby") String sortby,
			                                        @PathVariable("field") String field,
			                                        @PathVariable("search") String search) {
		if (!search.equals(" ")) {
			
			return new ResponseEntity<>(loosePackingService.SearchLoosePacking(pageno, pagesize, search),HttpStatus.OK);
			
		} else if ("asc".equals(sortby)) {
			
			return new ResponseEntity<>(loosePackingService.IndexLoocsePackingAsc(pageno, pagesize, field),HttpStatus.OK);
			
		} else if ("desc".equals(sortby)) {
			
			return new ResponseEntity<>(loosePackingService.IndexLoosePackingDesc(pageno, pagesize, field),HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

}
