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

import com.SCM.dto.HolidayDto;
import com.SCM.model.Holiday;
import com.SCM.service.HolidayService;
import com.google.common.base.Optional;

@RestController
@RequestMapping("/api/holiday")
public class HolidayController {
	@Autowired
	HolidayService holidayService;
	
	@PostMapping("/")
	public ResponseEntity<HolidayDto> create(@RequestBody HolidayDto holidayDto){
		return new ResponseEntity<>( holidayService.create(holidayDto), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<HolidayDto> update(@RequestBody HolidayDto holidayDto, @PathVariable int id){
		return new ResponseEntity<>(holidayService.update(holidayDto, id), HttpStatus.OK);
	}
	
	
	@GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexDistributor(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search") String search) {

		if (!search.equals(" ")) {
			
			return new ResponseEntity<>(holidayService.SearchAllHoliday(pageno, pagesize, search), HttpStatus.OK);
			
		} else if ("asc".equals(sortby)) {
			
			return new ResponseEntity<>(holidayService.IndexAllHolidayAsc(pageno, pagesize, field), HttpStatus.OK);
			
		} else if ("desc".equals(sortby)) {
			
			return new ResponseEntity<>(holidayService.IndexAllHolidayDesc(pageno, pagesize, field),HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/byid/{id}")
	public ResponseEntity<?> getByid(@PathVariable int id){
		
		java.util.Optional<Holiday> findById = holidayService.findById(id);
		
		return new ResponseEntity<>(findById,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id){
		
		String deleteById = holidayService.deleteById(id);
		
		return new ResponseEntity<>(deleteById,HttpStatus.OK);
	}
	
	
}
