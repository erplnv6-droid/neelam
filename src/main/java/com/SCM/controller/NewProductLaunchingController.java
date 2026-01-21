package com.SCM.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.NewProductLaunchingDto;
import com.SCM.service.NewProductLaunchingService;
import com.google.api.client.util.Value;

@RestController
@RequestMapping("/api/launching")
@CrossOrigin(origins = "*")
public class NewProductLaunchingController {
	
	@Autowired
	public NewProductLaunchingService launchingService;
	
	private final String path = "C:\\Users\\LNV114\\eclipse-workspace\\Neelam\\Neelam\\Imag/";
	
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadImage(@RequestParam("file")MultipartFile file,NewProductLaunchingDto launchingDto) throws IOException
	{
		
		return new ResponseEntity<>(launchingService.uploadimage(file,launchingDto),HttpStatus.CREATED);	
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> UpdateuploadImage(@RequestParam("file")MultipartFile file,@PathVariable("id")int id) throws IOException
	{
		
		return new ResponseEntity<>(launchingService.updateuploadimage(file,id),HttpStatus.CREATED);	
	}
	
	
	
	@GetMapping("/{filename}")
	public void downloadimage(@PathVariable String filename,HttpServletResponse response) throws IOException
	{
	    InputStream inputStream =   launchingService.getResourceFile(path, filename);
	    response.setContentType(MediaType.IMAGE_PNG_VALUE);
	    StreamUtils.copy(inputStream, response.getOutputStream());
			
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> fetcEntity() throws IOException
	{
		System.out.println("nihal ");
		return new ResponseEntity<>(launchingService.fetchAll(),HttpStatus.OK);	
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id")int id) throws IOException
	{
		launchingService.deleteproductlaunching(id);
		
		return new ResponseEntity<>("delete success",HttpStatus.OK);	
	}
}
