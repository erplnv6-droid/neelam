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

import com.SCM.dto.TransportersDto;
import com.SCM.service.TransportersService;

@RestController
@RequestMapping("/api/transporter")
public class TransportersController {

	
	@Autowired
	private TransportersService transportersService;
	
	
	@PostMapping("/")
	
	public ResponseEntity<?> saveTransporter(@RequestBody TransportersDto transportersDto)
	{
		return new ResponseEntity<>(transportersService.saveTransporters(transportersDto),HttpStatus.OK);
	}
	
	@GetMapping("/")
	
	public ResponseEntity<?> findAll()
	{
		return new ResponseEntity<>(transportersService.getAllTransporters(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	
	public ResponseEntity<?> getById(@PathVariable long id)
	{
		return new ResponseEntity<>(transportersService.getById(id),HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	
	public ResponseEntity<?> updateByTransporters(@RequestBody TransportersDto transportersDto,@PathVariable long id)
	{
		return new ResponseEntity<>(transportersService.updateTransporters(transportersDto, id),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	
	public void deleteTransporters(@PathVariable long id)
	{
		transportersService.deleteById(id);
	}
	
	@GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexTransporters(@PathVariable("pageno") int pageno,
			@PathVariable("pagesize") int pagesize,
			@PathVariable("sortby") String sortby,
			@PathVariable("field") String field, 
			@PathVariable("search") String search) {
		if (!search.equals(" ")) {

			return new ResponseEntity<>(transportersService.searchByTransporetes(pageno, pagesize, search), HttpStatus.OK);

		} else if ("asc".equals(sortby)) {

			return new ResponseEntity<>(transportersService.indexTransporterAsc(pageno, pagesize, field), HttpStatus.OK);

		} else if ("desc".equals(sortby)) {

			return new ResponseEntity<>(transportersService.indexTransporterDesc(pageno, pagesize, field), HttpStatus.OK);

		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
}
