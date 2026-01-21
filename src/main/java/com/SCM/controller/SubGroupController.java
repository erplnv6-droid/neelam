package com.SCM.controller;

import java.util.List;
import java.util.Optional;

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

import com.SCM.dto.SubGroupDto;
import com.SCM.service.SubGroupService;

@RestController
@RequestMapping("/api/subgroup")
public class SubGroupController {

	
	@Autowired
	private SubGroupService subService;
	
	
	@PostMapping("/save")
	public ResponseEntity<?> createSubGroup(@RequestBody SubGroupDto subGroupDto){
		return new ResponseEntity<SubGroupDto>(subService.createSubGroup(subGroupDto),HttpStatus.OK);
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAllSubGroup(){
		return new ResponseEntity<List<SubGroupDto>>(subService.getAllSubGroup(),HttpStatus.OK);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable long id){
		return new ResponseEntity<Optional<SubGroupDto>>(subService.getById(id),HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateSubGroup(@RequestBody SubGroupDto subGroupDto,@PathVariable long id){
		return new ResponseEntity<String>(subService.updateSubGroup(subGroupDto, id),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSubGroup(@PathVariable long id){
		return new ResponseEntity<String>(subService.deleteSubGroup(id),HttpStatus.OK);
	}
	
}
