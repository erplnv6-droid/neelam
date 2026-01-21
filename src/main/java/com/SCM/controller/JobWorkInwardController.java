package com.SCM.controller;

import java.util.List;
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
import com.SCM.dto.JobworkInwardDto;
import com.SCM.model.JobworkInward;
import com.SCM.service.JobWorkInwardService;

@RestController
@RequestMapping("/api/jobworkinward")
public class JobWorkInwardController {

	@Autowired
	private JobWorkInwardService jobWorkInwardService;
	
	@PostMapping("/")
	public ResponseEntity<?> save(@RequestBody JobworkInwardDto jobworkInwardDto) {
		
		return new ResponseEntity<>(jobWorkInwardService.saveJobWorkInward(jobworkInwardDto), HttpStatus.CREATED);
	}

	
	@GetMapping("/")
	public ResponseEntity<?> show() {
		
		   List<JobworkInward> allJobWorkInward = jobWorkInwardService.getAllJobWorkInward();
		
		return ResponseEntity.status(HttpStatus.OK).body(allJobWorkInward);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showById(@PathVariable("id")int id) {
		
		   JobworkInward jobWorkInwardById = jobWorkInwardService.getJobWorkInwardById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(jobWorkInwardById);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id")int id) {
		
		   jobWorkInwardService.deleteJobWorkInward(id);
		
		return ResponseEntity.status(HttpStatus.OK).body("deleted");
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateById(@RequestBody JobworkInwardDto jobworkInwardDto,@PathVariable("id")int id) {
		
		   JobworkInward updateJobWorkInward = jobWorkInwardService.updateJobWorkInward(jobworkInwardDto,id);
		
		return ResponseEntity.status(HttpStatus.OK).body(updateJobWorkInward);
	}
	
	
	
	
    @GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexJobWorkInward(@PathVariable("pageno") int pageno,
				                                  @PathVariable("pagesize") int pagesize,
				                                  @PathVariable("sortby") String sortby,
				                                  @PathVariable("field") String field,
				                                  @PathVariable("search")String search) 
		{  	
	    	if(!search.equals(" "))
			{
				return new ResponseEntity<>(jobWorkInwardService.SearchByJobWorkInward(pageno, pagesize,search),HttpStatus.OK) ; 
			}
	    	else if("asc".equals(sortby))
			{
				return new ResponseEntity<>(jobWorkInwardService.IndexJobWorkInwardAsc(pageno,pagesize,field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(jobWorkInwardService.IndexJobWorkInwardDesc(pageno,pagesize,field), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
}
