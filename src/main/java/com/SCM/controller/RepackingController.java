package com.SCM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.RepackingDto;
import com.SCM.model.Repacking;
import com.SCM.service.RepackingService;

@RestController
@RequestMapping("/api/repacking")
public class RepackingController {

	@Autowired
	private RepackingService repackingService;

	@PostMapping("/")
	public ResponseEntity<?> save(@RequestBody RepackingDto repackingDto) {

		Repacking save = repackingService.save(repackingDto);
		
		return new ResponseEntity<>(save, HttpStatus.CREATED);
	}
	
	
	
	@GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> PaginationRepacking(@PathVariable("pageno") int pageno,
			                                        @PathVariable("pagesize") int pagesize,
			                                        @PathVariable("sortby") String sortby,
			                                        @PathVariable("field") String field,
			                                        @PathVariable("search") String search) {
		if (!search.equals(" ")) {
			
			return new ResponseEntity<>(repackingService.SearchRepacking(pageno, pagesize, search),HttpStatus.OK);
			
		} else if ("asc".equals(sortby)) {
			
			return new ResponseEntity<>(repackingService.IndexRepackingAsc(pageno, pagesize, field),HttpStatus.OK);
			
		} else if ("desc".equals(sortby)) {
			
			return new ResponseEntity<>(repackingService.IndexRepackingDesc(pageno, pagesize, field),HttpStatus.OK);
			
		} else {
			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
}
