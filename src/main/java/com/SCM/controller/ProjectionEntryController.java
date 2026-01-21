package com.SCM.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.IndexDto.IndexProjectionEntry;
import com.SCM.dto.AppConstants;
import com.SCM.dto.CustomPageResponse;
import com.SCM.dto.ProjectionEntryDto;
import com.SCM.repository.ProjectionEntryRepository;
import com.SCM.service.ProjectionEntryService;

@RestController
@RequestMapping("/api/v1/projectionentry")
@CrossOrigin(origins = "*")
public class ProjectionEntryController {

	@Autowired
	private ProjectionEntryService service;

//	@Autowired
//	private ProjectionEntryRepository repository;

	@GetMapping("/getProjectionReport")
	public ResponseEntity<?> IndexProjectionReportWithGroupAndAll(@RequestParam("pageno") int pageno,
			@RequestParam("pagesize") int pagesize, @RequestParam("sortby") String sortby,
			@RequestParam("field") String field, @RequestParam("wid") long wid,
			@RequestParam(value = "g1", required = false) String g1,
			@RequestParam(value = "g2", required = false) String g2,
			@RequestParam(value = "g3", required = false) String g3,
			@RequestParam(value = "g4", required = false) String g4,
			@RequestParam(value = "g5", required = false) String g5, @RequestParam("fromdate") String fromdate,
			@RequestParam("todate") String todate, @RequestParam(value = "rid", defaultValue = "0") List<Long> rid,
			@RequestParam(value = "did", defaultValue = "0") List<Long> did,
			@RequestParam(value = "count", defaultValue = "0") Long count

	)

	{

		if ("asc".equals(sortby)) {
			return new ResponseEntity<>(service.indexProjectionReportByRetailerlistAndDistributorlistGroupAsc(pageno,
					pagesize, field, wid, g1, g2, g3, g4, g5, fromdate, todate, rid, did, count), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}

	}

//	
//	@GetMapping("/getreport")
//	public ResponseEntity<?> getAllProjectionEntry(
//			@PathVariable long wid,
//			@PathVariable long g1,
//			@PathVariable long g2,
//			@PathVariable long g3,
//			@PathVariable long g4,
//			@PathVariable long g5,
//			@PathVariable String fromdate,
//			@PathVariable String todate,
//			@PathVariable long rid,
//			@PathVariable long did){
//		return new ResponseEntity<>(repository.getAllNewProjectionEntryWithGroupn1(wid, g1,fromdate, todate, rid, did),HttpStatus.OK);
//	}

//	@GetMapping("/getProjectionReport/{wid}/")

	@PostMapping
	public ResponseEntity<?> saveProjectionEtnry(@RequestBody ProjectionEntryDto dto) {
		return new ResponseEntity<>(service.saveProjectionEntry(dto), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProjectionEntry(@PathVariable long id) {
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProjectionEntry(@PathVariable long id, @RequestBody ProjectionEntryDto dto) {
		return new ResponseEntity<>(service.updateProjectionEntryDto(dto, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProjectionEntry(@PathVariable long id) {
		return new ResponseEntity<>(service.deleteProjectionEntry(id), HttpStatus.OK);
	}

	@GetMapping("/page")
	public ResponseEntity<?> findAllProductByPagination(
			@RequestParam(value = "pageNumber", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
			@RequestParam(value = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "field", required = false) String field,
			@RequestParam(value = "direction") String direction, @RequestParam(value = "search") String search) {

		CustomPageResponse<IndexProjectionEntry> response = null;

		if (search.trim().isEmpty()) {
			response = service.FindAllIndexProjection(pageNumber, pageSize, field, direction);
			return new ResponseEntity<>(response, HttpStatus.OK);

		}

		if (!search.trim().isEmpty()) {

			response = service.searchIndexProjection(pageNumber, pageSize, field, direction, search);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		return null;
	}

	@GetMapping("/projection/report")
	public ResponseEntity<?> IndexProjectionReportWithGroupAndAll(

			@RequestParam(value = "g1", required = false, defaultValue = "neelam") String g1,
			@RequestParam(value = "g2", required = false, defaultValue = "neelam") String g2,
			@RequestParam(value = "g3", required = false, defaultValue = "neelam") String g3,
			@RequestParam(value = "g4", required = false, defaultValue = "neelam") String g4,
			@RequestParam(value = "g5", required = false, defaultValue = "neelam") String g5,
			@RequestParam("fromdate") String fromdate, @RequestParam("todate") String todate,
			@RequestParam(value = "count", defaultValue = "0") Long count)

	{

		return new ResponseEntity<>(service.indexProjectionReportAsc(g1, g2, g3, g4, g5, fromdate, todate, count),
				HttpStatus.OK);

	}

}
