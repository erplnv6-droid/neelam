package com.SCM.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.AssetDto;
import com.SCM.service.AssetService;


@RestController
@RequestMapping("/api/asset")
@CrossOrigin(origins = "*")
public class AssetController {
	
	@Autowired
	private AssetService assetService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveAsset(@ModelAttribute AssetDto assetDto,@PathVariable("inscfile")MultipartFile inscfile) throws IOException{
		AssetDto save = assetService.save(assetDto,inscfile);
		return new ResponseEntity<>(save,HttpStatus.OK);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateAsset(@PathVariable long id,@ModelAttribute AssetDto assetDto,@PathVariable("inscfile")MultipartFile inscfile) throws IOException{
		String assetupdate = assetService.update1(id,assetDto,inscfile);
		return new ResponseEntity<>(assetupdate,HttpStatus.OK);
	}
	
	@GetMapping("/byid/{id}")
	public ResponseEntity<?> getById(@PathVariable long id){
		Optional<AssetDto> byId = assetService.getById(id);
		return new ResponseEntity<>(byId,HttpStatus.OK);
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<?> allAsset(){
		List<AssetDto> all = assetService.getAll();
		return new ResponseEntity<>(all,HttpStatus.OK);
	}
	
	
	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexDistributor(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search") String search) {

		if (!search.equals(" ")) {
			
			return new ResponseEntity<>(assetService.SearchAsset(pageno, pagesize, search), HttpStatus.OK);
			
		} else if ("asc".equals(sortby)) {
			
			return new ResponseEntity<>(assetService.IndexAssetAsc(pageno, pagesize, field), HttpStatus.OK);
			
		} else if ("desc".equals(sortby)) {
			
			return new ResponseEntity<>(assetService.IndexAssetDesc(pageno, pagesize, field),HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?>deleteById(@PathVariable long id){
		return new ResponseEntity<>(assetService.delete(id),HttpStatus.OK);
	}
	
	
}
