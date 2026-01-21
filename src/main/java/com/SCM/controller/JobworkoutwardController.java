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

import com.SCM.dto.JobworkOutwardDto;
import com.SCM.model.Jobworkoutward;
import com.SCM.service.JobworkoutwardService;

@RestController
@RequestMapping("/api/jobworkoutward")
public class JobworkoutwardController {

	@Autowired
	private JobworkoutwardService jobworkoutwardService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> save(@RequestBody JobworkOutwardDto jobworkOutwardDto) {
		
		 return new ResponseEntity<>(jobworkoutwardService.saveJobWorkOutward(jobworkOutwardDto), HttpStatus.CREATED);
	}

	
	@GetMapping("/")
	public ResponseEntity<?> show() {
		
		 List<Jobworkoutward> allJobWorkOutward = jobworkoutwardService.getAllJobWorkOutward();
		
		 return ResponseEntity.status(HttpStatus.OK).body(allJobWorkOutward);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showById(@PathVariable("id")int id) {
		
		   Jobworkoutward jobWorkOutwardById = jobworkoutwardService.getJobWorkOutwardById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(jobWorkOutwardById);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id")int id) {
		
		jobworkoutwardService.deleteJobWorkOutward(id);
		
		return ResponseEntity.status(HttpStatus.OK).body("deleted");
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateById(@RequestBody JobworkOutwardDto jobworkOutwardDto,@PathVariable("id")int id) {
		
		Jobworkoutward updateJobWorkOutward = jobworkoutwardService.updateJobWorkOutward(jobworkOutwardDto,id);
		
		return ResponseEntity.status(HttpStatus.OK).body(updateJobWorkOutward);
	}
	
	
	
	 @GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	 public ResponseEntity<?> IndexJobWorkOutward(@PathVariable("pageno") int pageno,
					                                  @PathVariable("pagesize") int pagesize,
					                                  @PathVariable("sortby") String sortby,
					                                  @PathVariable("field") String field,
					                                  @PathVariable("search")String search) 
			{  	
		    	if(!search.equals(" "))
				{
					return new ResponseEntity<>(jobworkoutwardService.SearchByJobWorkOutward(pageno, pagesize,search),HttpStatus.OK) ; 
				}
		    	else if("asc".equals(sortby))
				{
					return new ResponseEntity<>(jobworkoutwardService.IndexJobWorkOutwardAsc(pageno,pagesize,field), HttpStatus.OK);
				}
				else if("desc".equals(sortby))
				{
					return new ResponseEntity<>(jobworkoutwardService.IndexJobWorkOutwardDesc(pageno,pagesize,field), HttpStatus.OK);
				}
				else {			
					return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
				}
			}
}
