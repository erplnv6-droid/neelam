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

import com.SCM.model.Group7;
import com.SCM.service.Group7Service;

@RestController
@RequestMapping("/api/group7")
@CrossOrigin(origins = "*")
public class Group7Controller {

	

	@Autowired
	private Group7Service service;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveGroup7(@RequestBody Group7 Group7){
		return new ResponseEntity<>(service.save(Group7),HttpStatus.OK);
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<?> getById(@PathVariable long id){
		return new ResponseEntity<>(service.getById(id),HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable long id,@RequestBody Group7 Group7){
		return new ResponseEntity<>(service.updateGroup7(id, Group7),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable long id){
		return new ResponseEntity<>(service.deleteGroup7(id),HttpStatus.OK);
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
				return new ResponseEntity<>(service.SearchGroup7(pageno, pagesize,search),HttpStatus.OK) ; 
			}
	    	else if("asc".equals(sortby))
			{ 
//	    		System.out.println(service.IndexGroup1Asc(pageno,pagesize,field));
				return new ResponseEntity<>(service.IndexGroup7Asc(pageno,pagesize,field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(service.IndexGroup7Desc(pageno,pagesize,field), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
	
	
}
