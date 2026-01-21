package com.SCM.controller;

import java.util.List;
import java.util.Optional;

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

import com.SCM.dto.StaffAssetDto;
import com.SCM.service.StaffAssetService;

@RestController
@RequestMapping("/api/staffasset")
public class StaffAssetController {
	
	@Autowired
	private StaffAssetService staffAssetService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveStaffAsset(@RequestBody StaffAssetDto staffAssetDto){
		StaffAssetDto save = staffAssetService.save(staffAssetDto);
		return new ResponseEntity<>(save,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateStaffAsset(@PathVariable long id,@RequestBody StaffAssetDto assetDto){
		String updateStaffAsset = staffAssetService.updateStaffAsset(id, assetDto);
		return new ResponseEntity<>(updateStaffAsset,HttpStatus.OK);
	}
	
	
	@GetMapping("/getall")
	public ResponseEntity<?> allstaffasset(){
		List<StaffAssetDto> all = staffAssetService.getAll();
		return new ResponseEntity<>(all,HttpStatus.OK);
	}
	
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getById(@PathVariable long id){
		Optional<StaffAssetDto> byId = staffAssetService.getById(id);
		return new ResponseEntity<>(byId,HttpStatus.OK);
	}
	

	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable long id){
		String deleteStaffAsset = staffAssetService.deleteStaffAsset(id);
		return new ResponseEntity<>(deleteStaffAsset,HttpStatus.OK);
	}
	
}
