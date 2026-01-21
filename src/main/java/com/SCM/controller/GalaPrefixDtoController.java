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
import org.springframework.web.context.support.ServletContextLiveBeansView;

import com.SCM.dto.GalaPrefixDto;
import com.SCM.model.GalaPrefix;
import com.SCM.serviceimpl.GalaPrefixDtoServiceImpl;

@RestController
@RequestMapping("/api/galaprefix")
public class GalaPrefixDtoController {

	@Autowired
	private GalaPrefixDtoServiceImpl service;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveGala(@RequestBody GalaPrefixDto dto){
		return new ResponseEntity<>(service.saveGalaPrefix(dto),HttpStatus.OK);
	}
	
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<?> getById(@PathVariable long id){
		return new ResponseEntity<>(service.getById(id),HttpStatus.OK);
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(service.getAllPrefix(),HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable long id,@RequestBody GalaPrefix dto){
		return new ResponseEntity<>(service.updateGala(dto, id),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletebyid(@PathVariable long id){
		return new ResponseEntity<>(service.deleteGala(id),HttpStatus.OK);
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
				return new ResponseEntity<>(service.SearchGalaPrefix(pageno, pagesize,search),HttpStatus.OK) ; 
			}
	    	else if("asc".equals(sortby))
			{ 
//	    		System.out.println(service.IndexGroup1Asc(pageno,pagesize,field));
				return new ResponseEntity<>(service.IndexGalaPrefix(pageno,pagesize,field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(service.IndexGalaPrefixDesc(pageno,pagesize,field), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}

}
