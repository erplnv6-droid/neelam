package com.SCM.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.MinutesOfMeetingDto;
import com.SCM.service.MinutesOfMeetingService;

@RestController
@RequestMapping("/api/meeting")
public class MinutesOfMeetingController {

	@Autowired
	private MinutesOfMeetingService minutesOfMeetingService;

	@PostMapping("/")
	public ResponseEntity<?> save(@ModelAttribute MinutesOfMeetingDto minutesOfMeetingDto,
			                      @RequestParam("st")String staff,
			                      @RequestParam("ret")String retailer,
			                      @RequestParam("dist")String distributor,
			                      @RequestParam("sup")String supplier,
			                      @RequestPart("doc")MultipartFile docfile) throws IOException {

		return new ResponseEntity<>(minutesOfMeetingService.save(minutesOfMeetingDto,staff,retailer,distributor,supplier,docfile),HttpStatus.CREATED) ;
	}

	
	@GetMapping("/")
	public  ResponseEntity<?>  getAll() {

		return new ResponseEntity<>(minutesOfMeetingService.getAll(),HttpStatus.OK) ;
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<?> getbyId(@PathVariable("id") int id) {

		return new ResponseEntity<>(minutesOfMeetingService.getById(id),HttpStatus.OK) ;
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @ModelAttribute MinutesOfMeetingDto minutesOfMeetingDto,
			                                              @RequestParam("st")String staff,
			                                              @RequestParam("ret")String retailer,
			            			                      @RequestParam("dist")String distributor,
			            			                      @RequestParam("sup")String supplier,
			                                              @RequestPart("doc")MultipartFile docfile) throws IOException {

		return new ResponseEntity<>(minutesOfMeetingService.update(minutesOfMeetingDto,docfile,staff,retailer,distributor,supplier,id),HttpStatus.OK) ;
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {

		return new ResponseEntity<>(minutesOfMeetingService.delete(id),HttpStatus.OK) ;

	}

	
	 @GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
		public ResponseEntity<?> IndexDistributorMinimumStock(@PathVariable("pageno") int pageno,
				                                  @PathVariable("pagesize") int pagesize,
				                                  @PathVariable("sortby") String sortby,
				                                  @PathVariable("field") String field,
				                                  @PathVariable("search")String search) 
		{   	
	    	if(!search.equals(" "))
			{
				return new ResponseEntity<>(minutesOfMeetingService.SearchMOM(pageno, pagesize,search),HttpStatus.OK) ; 
			}
	    	else if("asc".equals(sortby))
			{ 
				return new ResponseEntity<>(minutesOfMeetingService.IndexMOMAsc(pageno,pagesize,field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(minutesOfMeetingService.IndexMOMDesc(pageno,pagesize,field), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
	
	 
	 
//		@GetMapping("/image/{filename}")
//		public ResponseEntity<?> downloadImagesInFiles(@PathVariable("filename") String filename) throws IOException {
//
//			byte[] imgdata = minutesOfMeetingService.downloadImages(filename);
//
//			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imgdata);
//		}
		
		
		@GetMapping("/image/{id}")
		public ResponseEntity<?> imgGetById(@PathVariable("id") int id) throws IOException {

			byte[] imgdata = minutesOfMeetingService.getImagebyId(id);
			
			System.out.println(imgdata + "+++++++++++++++");

			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imgdata);
		}
}
