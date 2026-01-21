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

import com.SCM.dto.GroupDto;
import com.SCM.service.GroupService;

@RestController
@RequestMapping("/api/group")
public class GroupController {

	
	@Autowired
	private GroupService groupService;
	
	@PostMapping("/save")
	public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto){
		return new ResponseEntity<GroupDto>(groupService.createGroup(groupDto),HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAllGroup(){
		return new ResponseEntity<List<GroupDto>>(groupService.getAllGroup(),HttpStatus.OK);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable long id){
		return new ResponseEntity<Optional<GroupDto>>(groupService.getById(id),HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateGroup(@RequestBody GroupDto groupDto,@PathVariable long id){
		return new ResponseEntity<String>(groupService.updateGroup(groupDto, id),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable long id){
		return new ResponseEntity<String>(groupService.deleteGroup(id),HttpStatus.OK);
	}
	
	
	
	
}
