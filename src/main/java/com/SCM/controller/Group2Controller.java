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

import com.SCM.model.Group2;
import com.SCM.service.Group2Service;

@RestController
@RequestMapping("/api/group2")
public class Group2Controller {

	@Autowired
	private Group2Service service;
	
	
	@PostMapping("/save")
	public ResponseEntity<?> saveGroup2(@RequestBody Group2 Group2){
		return new ResponseEntity<>(service.save(Group2),HttpStatus.OK);
	}
	
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<?>getById(@PathVariable long id){
		return new ResponseEntity<>(service.getById(id),HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateGroup2(@PathVariable long id,@RequestBody Group2 Group2){
		return new ResponseEntity<>(service.updateGroup2(id, Group2),HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?>delete(@PathVariable long id){
		return new  ResponseEntity<>(service.deleteGroup2(id),HttpStatus.OK);
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
				return new ResponseEntity<>(service.SearchGroup2(pageno, pagesize,search),HttpStatus.OK) ; 
			}
	    	else if("asc".equals(sortby))
			{ 
//	    		System.out.println(service.IndexGroup1Asc(pageno,pagesize,field));
				return new ResponseEntity<>(service.IndexGroup2Asc(pageno,pagesize,field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(service.IndexGroup2Desc(pageno,pagesize,field), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
	
}
