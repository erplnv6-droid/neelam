package com.SCM.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.ProfilePictureDto;
import com.SCM.service.ProfilePicService;

@RestController
@RequestMapping("/api/profilepic")
@CrossOrigin(origins = "*")
public class ProfilePicController {

	@Autowired
	private ProfilePicService profilePicService;
	
	private static final long MAX_FILE_SIZE = 3 * 1024 * 1024;
	
	@PostMapping("/upload")
	public ResponseEntity<?> save(@RequestParam(value = "staffid",required = false,defaultValue = "0") int staffid,
			                      @RequestParam(value =  "retailerid",required = false,defaultValue = "0") int retailerid,
			                      @RequestParam(value =  "distributorid",required = false,defaultValue = "0")int distributorid,
			                      @RequestParam(value =  "supplierid",required = false,defaultValue = "0")int supplierid,
			                      @RequestParam("files") MultipartFile files) throws IOException
	{
		
		if(files.getSize() > MAX_FILE_SIZE)
		{
			return new ResponseEntity<>("The file size 3MB execced to limit",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(profilePicService.upload(staffid,retailerid,distributorid,supplierid,files), HttpStatus.CREATED);
	}

	
	@GetMapping("/staffid/{staffid}")
	public ResponseEntity<?> get(@PathVariable("staffid") int staffid) throws IOException
	{
		return new ResponseEntity<>(profilePicService.getProfileByStaffId(staffid),HttpStatus.OK);
	}
	
	@GetMapping("/retailer/{retailerid}")
	public ResponseEntity<?> getretailer(@PathVariable("retailerid") int retailerid) throws IOException
	{
		return new ResponseEntity<>(profilePicService.getProfileByRetailerId(retailerid),HttpStatus.OK);
	}
	
	@GetMapping("/distributor/{distributor}")
	public ResponseEntity<?> getdistributor(@PathVariable("distributor") int distributor) throws IOException
	{
		return new ResponseEntity<>(profilePicService.getProfileByDistributorId(distributor),HttpStatus.OK);
	}
	
	@GetMapping("/supplier/{supplier}")
	public ResponseEntity<?> getsupplier(@PathVariable("supplier") int supplier) throws IOException
	{
		return new ResponseEntity<>(profilePicService.getProfileBySupplierId(supplier),HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id")int id,
			                        @ModelAttribute ProfilePictureDto profilePictureDto,
			                        @RequestParam(value = "staffid",required = false,defaultValue = "0") int staffid,
			                        @RequestParam(value =  "retailerid",required = false,defaultValue = "0") int retailerid,
			                        @RequestParam(value =  "distributorid",required = false,defaultValue = "0")int distributorid,
			                        @RequestParam(value =  "supplierid",required = false,defaultValue = "0")int supplierid,
			                        @RequestParam("files") MultipartFile files) throws IOException
	{
		return new ResponseEntity<>(profilePicService.update(id,profilePictureDto,staffid,retailerid,distributorid,supplierid,files), HttpStatus.CREATED);
	}
}
