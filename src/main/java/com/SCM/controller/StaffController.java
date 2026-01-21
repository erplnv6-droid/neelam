package com.SCM.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.ExportDto.ExportStaff;
import com.SCM.IndexDto.IndexStaffByStates;
import com.SCM.IndexDto.IndexStaffDto;
import com.SCM.IndexDto.IndexStatesByZones;
import com.SCM.IndexDto.MultipleToStaffDto;
import com.SCM.config.UserId;
import com.SCM.dto.StaffDto;
import com.SCM.model.Staff;
import com.SCM.payload.StaffRequest;
import com.SCM.repository.StaffRepo;
import com.SCM.service.StaffService;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StaffController {

	@Autowired
	private StaffService staffService;

	@Autowired
	private StaffRepo staffRepo;

	@Autowired
	private UserId user;

	
	@GetMapping("/")
	public ResponseEntity<?> showStaff() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

			return new ResponseEntity<>(staffService.getAllStaff(), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_NSM"))) {

			return new ResponseEntity<>(staffService.getAllStaff(), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RSM"))) {

			return new ResponseEntity<>(staffService.getStaffByRsmId(user.getId().intValue()), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASM"))) {

			return new ResponseEntity<>(staffService.getStaffByAsmId(user.getId().intValue()), HttpStatus.OK);

		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASE"))) {

			return new ResponseEntity<>(staffService.getStaffByAseId(user.getId().intValue()), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>("Staff Not Found", HttpStatus.NOT_FOUND);
		}
	}

	
	@GetMapping("ase/{aseId}")
	public ResponseEntity<List<StaffDto>> getStaffByAseId(@PathVariable("aseId") int aseId) {
		List<StaffDto> dto = staffService.getStaffByAseId(aseId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	
	@GetMapping("asm/{asmId}")
	public ResponseEntity<List<StaffDto>> getStaffByAsmId(@PathVariable("asmId") int asmId) {
		List<StaffDto> dto = staffService.getStaffByAsmId(asmId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("asm/asm/{id}")
	public ResponseEntity<List<StaffDto>> getAsmByAsmId(@PathVariable("id") int id) {
		List<StaffDto> dto = staffService.getASMByAsmId(id);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("ase/ase/{id}")
	public ResponseEntity<List<StaffDto>> getAseByAseId(@PathVariable("id") int id) {
		List<StaffDto> dto = staffService.getASEByAseId(id);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("rsm/{rsmId}")
	public ResponseEntity<List<StaffDto>> getStaffByRsmId(@PathVariable("rsmId") int rsmId) {
		List<StaffDto> dto = staffService.getStaffByRsmId(rsmId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("nsm/{nsmId}")
	public ResponseEntity<List<StaffDto>> getStaffByNsmId(@PathVariable("nsmId") int nsmId) {
		List<StaffDto> dto = staffService.getStaffByNsmId(nsmId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("zone/{zoneId}")
	public ResponseEntity<List<StaffDto>> getStaffByZoneId(@PathVariable("zoneId") int zoneId) {
		
		List<StaffDto> dto = staffService.getStaffByZoneId(zoneId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	
	@GetMapping("state/{stateId}")
	public ResponseEntity<?> getStaffByStateId(@PathVariable("stateId") int stateId) {
 
		List<IndexStaffDto> staffByStateId = staffService.getStaffByStateId(stateId);
		
		return new ResponseEntity<>(staffByStateId, HttpStatus.OK);
	}

	@GetMapping("rsm/zone/{zoneId}")
	public ResponseEntity<List<StaffDto>> getRsmByZoneId(@PathVariable("zoneId") int zoneId) {
		List<StaffDto> dto = staffService.getRsmZoneId(zoneId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("asm/zone/{zoneId}")
	public ResponseEntity<List<StaffDto>> getAsmByZoneId(@PathVariable("zoneId") int zoneId) {

		List<StaffDto> dto = staffService.getAsmByZoneId(zoneId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("ase/zone/{zoneId}")
	public ResponseEntity<List<StaffDto>> getAseByZoneId(@PathVariable("zoneId") int zoneId) {

		List<StaffDto> dto = staffService.getAseByZoneId(zoneId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("asm/rsm/{rsmId}")
	public ResponseEntity<List<StaffDto>> getAsmByRoleId(@PathVariable("rsmId") int rsmId) {

		List<StaffDto> dto = staffService.getStaffAsmByRsmId(rsmId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("ase/rsm/{rsmId}")
	public ResponseEntity<List<StaffDto>> getAseByRsmId(@PathVariable("rsmId") int rsmId) {

		List<StaffDto> dto = staffService.getStaffAseByRsmId(rsmId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("ase/asm/{asmId}")
	public ResponseEntity<List<StaffDto>> getAseByAsmId(@PathVariable("asmId") int asmId) {

		List<StaffDto> dto = staffService.getStaffAseByASMId(asmId);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/nsm")
	public ResponseEntity<List<StaffDto>> getNsm() {

		List<StaffDto> dto = staffService.getStaffByNsm();

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<Staff> get(@PathVariable Integer id) {
		try {
			Staff staff = staffService.getStaffById(id);

			return new ResponseEntity<>(staff, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/update/{id}")
	public Staff updateStaff(@PathVariable("id") long id, @RequestBody StaffRequest staffRequest) {
	
		return staffService.updateStaff(staffRequest, id);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {

		return new ResponseEntity<String>(staffService.deleteStaff(id), HttpStatus.OK);
	}

	// --location update ----------------------------------

	@PutMapping("/update/{id}/{location}")
	public void updateStaff(@PathVariable("id") Long id, @PathVariable("location") String location) {

		staffRepo.updatestafflocation(id, location);

	}

	// ----------- index stafff

	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexStaff(@PathVariable("pageno") int pageno,
			                            @PathVariable("pagesize") int pagesize,
			                            @PathVariable("sortby") String sortby, 
			                            @PathVariable("field") String field,
			                            @PathVariable("search") String search) {

		if (!search.equals(" ")) {
			
			return new ResponseEntity<>(staffService.SearchStaff(pageno, pagesize, search), HttpStatus.OK);
			
		} else if ("asc".equals(sortby)) {
			
			return new ResponseEntity<>(staffService.IndexStaffAsc(pageno, pagesize, field), HttpStatus.OK);
			
		} else if ("desc".equals(sortby)) {
			
			return new ResponseEntity<>(staffService.IndexStaffDSC(pageno, pagesize, field), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/export")
	public ResponseEntity<?> exportStaff() {
		
		List<ExportStaff> dto = staffService.exportStaff();
		
		System.out.println(dto);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	
//	@PostMapping("/states/zone")
//	public ResponseEntity<?> getStatesByZoneId(@RequestBody ZoneRequest zoneRequest) {
//		
//         List<IndexStatesByZones> fetchStatesByZones = staffService.fetchStatesByZones(zoneRequest);
//
//		return new ResponseEntity<>(fetchStatesByZones, HttpStatus.OK);
//	}
	
	@GetMapping("/states/zone/{zoneid}")
	public ResponseEntity<?> getStatesByZoneId(@PathVariable("zoneid") List<Integer> zoneid) {
		
         List<IndexStatesByZones> fetchStatesByZones = staffService.fetchStatesByZones(zoneid);

		return new ResponseEntity<>(fetchStatesByZones, HttpStatus.OK);
	}

	
	@GetMapping("/states/zone/ase/{stateid}")
	public ResponseEntity<?> getASEByZoneId(@PathVariable("stateid") List<Integer> stateid) {
		
          List<IndexStatesByZones> aseByZones = staffService.getASEByMultipleZones(stateid);

		return new ResponseEntity<>(aseByZones, HttpStatus.OK);
	}
	
	
	@GetMapping("/states/zone/asm/{stateid}")
	public ResponseEntity<?> getASMByZoneId(@PathVariable("stateid") List<Integer> stateid) {
		
          List<IndexStatesByZones> aseByZones = staffService.getASMByMultipleZones(stateid);

		return new ResponseEntity<>(aseByZones, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/states/zone/rsm/{stateid}")
	public ResponseEntity<?> getRSMByZoneId(@PathVariable("stateid") List<Integer> stateid) {
		
          List<IndexStatesByZones> aseByZones = staffService.getRSMByMultipleZones(stateid);

		 return new ResponseEntity<>(aseByZones, HttpStatus.OK);
	}
	
	
	@GetMapping("/multiple/ase/rsm/{rsmid}")
	public ResponseEntity<?> getASEByRSMId(@PathVariable("rsmid") List<Integer> rsmid) {
		
          List<IndexStatesByZones> aseByMultipleRSMId = staffService.getASEByMultipleRSMId(rsmid);

		 return new ResponseEntity<>(aseByMultipleRSMId, HttpStatus.OK);
	}
	
	
	@GetMapping("/multiple/asm/rsm/{rsmid}")
	public ResponseEntity<?> getASMByRSMId(@PathVariable("rsmid") List<Integer> rsmid) {
		
          List<IndexStatesByZones> asmByMultipleRSMId = staffService.getASMByMultipleRSMId(rsmid);

		  return new ResponseEntity<>(asmByMultipleRSMId, HttpStatus.OK);
	}
	
	
	@GetMapping("/multiple/ase/asm/{asmid}")
	public ResponseEntity<?> getASEByASMId(@PathVariable("asmid") List<Integer> asmid) {
		
          List<IndexStatesByZones> aseByMultipleASMId = staffService.getASEByMultipleASMId(asmid);

		  return new ResponseEntity<>(aseByMultipleASMId, HttpStatus.OK);
	}
	
	
	@GetMapping("/multiple/{sid}")
	public ResponseEntity<?> getMultipleStaffByStaffId(@PathVariable("sid") int sid) {
		
         List<MultipleToStaffDto> multipleToStaff = staffService.multipleToStaff(sid);

		  return new ResponseEntity<>(multipleToStaff, HttpStatus.OK);
	}
	
	
	@GetMapping("/staffs/{stateid}")
	public ResponseEntity<?> getMulStaffByStateId(@PathVariable("stateid") int stateid) {
		
         List<IndexStaffByStates> staffByMultipleStates = staffService.getStaffByMultipleStates(stateid);

		  return new ResponseEntity<>(staffByMultipleStates, HttpStatus.OK);
	}
}
