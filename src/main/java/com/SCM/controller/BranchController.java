package com.SCM.controller;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.SCM.dto.BranchDto;
import com.SCM.model.Branch;
import com.SCM.repository.BranchRepo;
import com.SCM.service.BranchService;

@RestController
@RequestMapping("/api/branch")
@CrossOrigin(origins = "*")
public class BranchController {

	@Autowired
	public BranchService branchService;
	
	@Autowired
	public BranchRepo branchRepo;
	
	Logger logger = LoggerFactory.getLogger(BranchController.class);

	
	@PostMapping("/")
	public ResponseEntity<?> save(@RequestBody @Valid BranchDto branchDto) {
		
		return new ResponseEntity<>(branchService.savebranch(branchDto), HttpStatus.CREATED);
	}

	
	@GetMapping("/getAll")
	public ResponseEntity<?> show() {
		
		List<Branch> b =    branchService.showbranch();
		
		if(b.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.OK).body("Branch List is Completely empty, we have nothing ");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(branchService.showbranch());
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<?> showbyId(@PathVariable("id") int id) {

		Branch b = branchService.branchbyId(id);

		if (b != null) {
			
			return ResponseEntity.status(HttpStatus.OK).body(b);
			
		} else {
			
			logger.error("branch id " +id+ " is not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("branch id " +id+ " is not found ");
			
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Branch branch, @PathVariable("id") int id) {
		
	    Branch branch2 = branchService.updatebranch(branch, id);
	    
	    if(branch2 != null)
	    {
	    	return ResponseEntity.status(HttpStatus.CREATED).body(branch2);
	    }
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("branch id " +id+ " is not found ");
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletebyId(@PathVariable("id") int id) {
		
		branchService.deletebranch(id);
		
		return ResponseEntity.status(HttpStatus.OK).body("delete success");
	}
	
	
    //-------------------   index  BRANCH
    
    
    @GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexBranch(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search")String search) 
	{
    	
    	if(!search.equals(" "))
		{ 
			return new ResponseEntity<>(branchService.SearchBranch(pageno, pagesize,search),HttpStatus.OK) ; 
		}
    	else if("asc".equals(sortby))
		{ 
    		System.out.println(branchService.IndexBranchAsc(pageno,pagesize,field));
			return new ResponseEntity<>(branchService.IndexBranchAsc(pageno,pagesize,field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(branchService.IndexBranchDesc(pageno,pagesize,field), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

}
