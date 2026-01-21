package com.SCM.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.model.Zone;
import com.SCM.repository.ZoneRepo;
import com.SCM.service.ZoneService;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/zone")
public class ZoneController {

	@Autowired
	ZoneRepo zoneRepo;
	
	@Autowired
	ZoneService zoneService;

//	@GetMapping("/getAll")
//	public List<?> showZone(ZoneDto zoneDto) {
//		
//		List<ZoneDto> zoneDtos = new ArrayList<>();
//		List<Zone> z = zoneRepo.findAll();
//
//		for (Zone zo : z) {
//			ZoneDto zoneDto1 = new ZoneDto();
//			zoneDto1.setId(zo.getId());
//			zoneDto1.setZoneName(zo.getZoneName());
//			zoneDto1.setState_zone(zo.getState_zone());
//
//			System.out.println(zo);
//			zoneDtos.add(zoneDto1);
//		}
//		return zoneDtos;
//	}

	
	@GetMapping("/getAll")
	public ResponseEntity<List<Zone>> getAll(){
		
//		List<Zone> all = zoneRepo.getAll();
		
		List<Zone> zones=zoneRepo.findAll();
		
//		System.out.println(all);
//		
//		System.out.println(all);
		
		return new ResponseEntity<List<Zone>>(zones , HttpStatus.OK);
	}
	
    
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<?> showZonebyId(@PathVariable("id") Integer id) {

		return new ResponseEntity<>(zoneRepo.findById(id), HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/getStaffByZoneId/{id}")
	public ResponseEntity<?> getStaffByZoneId(@PathVariable("id") Long id) {

		return new ResponseEntity<>(zoneService.getZoneByStaffId(id), HttpStatus.ACCEPTED);
	}

}
