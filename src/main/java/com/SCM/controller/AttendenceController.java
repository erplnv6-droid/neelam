package com.SCM.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.AttendanceReportDto;
import com.SCM.dto.AttendenceDto;
import com.SCM.dtoReports.AttendanceReport;
import com.SCM.model.Attendence;

import com.SCM.projection.AttendanceReportProjection;
import com.SCM.projection.DateProjection;
import com.SCM.model.Staff;

import com.SCM.repository.AttendenceRepo;
import com.SCM.service.AttendenceService;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendenceController {

	@Autowired
	private AttendenceService attendenceService;

	@Autowired
	private AttendenceRepo attendenceRepo;

	@PostMapping("/save")
	public ResponseEntity<?> saveAttendance(@RequestBody AttendenceDto attendenceDto) {
		return new ResponseEntity<>(attendenceService.saveAttendence(attendenceDto), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public String updateAttedance(@PathVariable("id") int id, @RequestBody AttendenceDto attendenceDto) {
		attendenceService.updateAttendece(id, attendenceDto);

		return "upadte attendance";
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getAttendanceById(@PathVariable("id") int id) {
		return new ResponseEntity<>(attendenceService.getAttendenceById(id), HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<?> getAttendance() {
		return new ResponseEntity<>(attendenceService.getAtendence(), HttpStatus.OK);
	}

	@GetMapping("/staff/{staffid}")
	public ResponseEntity<?> getAttendanceByStaffId(@PathVariable("staffid") int staffid) {
		return new ResponseEntity<>(attendenceService.getAttendanceByStaffId(staffid), HttpStatus.OK);
	}

	@GetMapping("/lastrow")
	public ResponseEntity<?> getAttendanceLastRow() {
		return new ResponseEntity<>(attendenceService.getLastRowOfAttendance(), HttpStatus.OK);
	}

	@GetMapping("/previouslogin/{id}")
	public Optional<Attendence> getAttendence(@PathVariable int id) {
		Optional<Attendence> attendenceByStaffId = attendenceService.getByStaffId(id);
		return attendenceByStaffId;
	}


	

			

	@GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexAttendance(@PathVariable("pageno") int pageno, @PathVariable("pagesize") int pagesize,
			@PathVariable("sortby") String sortby, @PathVariable("field") String field,
			@PathVariable("search") String search) {

		if (!search.equals(" ")) {

			return new ResponseEntity<>(attendenceService.SearchAttendance(pageno, pagesize, search), HttpStatus.OK);

		} else if ("asc".equals(sortby)) {

			return new ResponseEntity<>(attendenceService.IndexAttendanceAsc(pageno, pagesize, field), HttpStatus.OK);

		} else if ("desc".equals(sortby)) {

			return new ResponseEntity<>(attendenceService.IndexAttendanceDesc(pageno, pagesize, field), HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

//	@GetMapping("/report/{loginyear}/{loginmonth}")
//	public ResponseEntity<?> LoginReports(@PathVariable("loginyear") String loginyear,
//			@PathVariable("loginmonth") String loginmonth) {
//		Map<Object, Object> response = new HashMap<>();
//
//		List<Map<Object, Object>> arraylist = new ArrayList<>();
//
//		List<com.SCM.dtoReports.AttendanceReport> attendance = attendenceService.AttendanceReport(loginyear,
//				loginmonth);
//
//		Map<Object, Object> attendanceobj = new HashMap<>();
//
//		System.out.println(attendance.size() + "size");
//
//		int days = 31;
//
//		for (com.SCM.dtoReports.AttendanceReport attendanceReport : attendance) {
//			
//			String s = attendanceReport.getstaffname();
//			int d = attendanceReport.getDays();
//
//			System.out.println(d - days);
//
//			for (int i = 1; i <= s.length(); i++) {
//				
//				attendanceobj.put("staffname", attendanceReport.getstaffname());
//
//				for (int j = 1; j <= days; j++) {
//					
//					attendanceobj.put("logindate", attendanceReport.getlogin());
//					attendanceobj.put("days", attendanceReport.getDays());
//					attendanceobj.put("Days", days);
//					
//				}
//			}
//
//			arraylist.add(attendanceobj);
//
//			response.put(attendanceReport.getstaffname(), arraylist);
//		}
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}



	@GetMapping("/report/{loginyear}/{loginmonth}/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> LoginReports(@PathVariable("loginyear") String loginyear,
			@PathVariable("loginmonth") String loginmonth, @PathVariable("pageno") int pageno,
			@PathVariable("pagesize") int pagesize, @PathVariable("sortby") String sortby,
			@PathVariable("field") String field, @PathVariable("search") String search) {

		if (!search.equals(" ")) {

			return new ResponseEntity<>(null, HttpStatus.OK);

		} else if ("asc".equals(sortby)) {

			return new ResponseEntity<>(
					attendenceService.IndexStaffAsc1(loginyear, loginmonth, pageno, pagesize, field), HttpStatus.OK);

		} else if ("desc".equals(sortby)) {

			return null;
//			return new ResponseEntity<>(attendenceService.IndexStaffDSC1(loginyear,loginmonth,pageno,pagesize,field), HttpStatus.OK);

		} else {


			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}

	}

//	@GetMapping("/report/{loginyear}/{loginmonth}")
//	public ResponseEntity<?> LoginReports1(@PathVariable("loginyear") String loginyear,
//			                              @PathVariable("loginmonth") String loginmont) {
//
//	
//			
//
//			return new ResponseEntity<>(response,HttpStatus.OK);	
//		}

	@GetMapping("/attendancereport/{date}/{pageNo}/{pageSize}/{field}")
	 public ResponseEntity<Map<String,Object>> attendanceReport(@PathVariable("date") String date,
			                                                    @PathVariable("pageNo") int pageNo, 
			                                                    @PathVariable("pageSize") int pageSize,
			                                                    @PathVariable("field") String field)
	{
		 Map<String,Object> report =  attendenceService.attendanceReport(pageNo,pageSize,field, date);
		 
		 return new ResponseEntity<>(report,HttpStatus.OK);
	}
	
	
	 @GetMapping("/dates/{date}")
	 public ResponseEntity<List<DateProjection>> getDates(@PathVariable("date") String date){
		 
		 return new ResponseEntity<>(attendenceRepo.getDatesByMonth(date),HttpStatus.OK);
	 }

//	 return new ResponseEntity<>(attendenceService.AttendanceReport(loginyear,loginmont), HttpStatus.OK);
//			
//	}



	@GetMapping("/attendancereport/{date}/{pageNo}/{pageSize}/{sortby}/{field}/{search}")
	public ResponseEntity<Map<String, Object>> attendanceReport(@PathVariable("date") String date,
			                                                    @PathVariable("pageNo") int pageNo,
			                                                    @PathVariable("pageSize") int pageSize,
			                                                    @PathVariable("field") String field,
			                                                    @PathVariable("sortby") String sortby,
			                                                    @PathVariable("search") String search) {

		Map<String, Object> report = attendenceService.attendanceReport(pageNo, pageSize, field, date, sortby, search);
		return new ResponseEntity<>(report, HttpStatus.OK);
	}



	@GetMapping("/attendancereport1/{date}")
	public ResponseEntity<?> attendanceReport1(@PathVariable("date") String date) {

		List<AttendanceReportDto> attendanceReportwithoutpaginnation = attendenceService.attendanceReportwithoutpaginnation(date);
		
		return new ResponseEntity<>(attendanceReportwithoutpaginnation, HttpStatus.OK);
	}



	@PostMapping("/savebulk")
	public ResponseEntity<List<Attendence>> saveBulkAttendance(@RequestBody List<AttendenceDto> attendanceDto) {
		System.out.println(attendanceDto);
		return new ResponseEntity<>(attendenceService.saveBulkAttendance(attendanceDto), HttpStatus.OK);
//		 return null;
	}

	@PutMapping("/updatebulk")
	public ResponseEntity<List<Attendence>> updateBulkAttendance(@RequestBody List<AttendenceDto> attendanceDto) {
		System.out.println(attendanceDto);
		return new ResponseEntity<>(attendenceService.updateBulkAttendance(attendanceDto), HttpStatus.OK);
//		 return null;
	}

	@GetMapping("/index/{fromDate}/{toDate}/{pageNo}/{pageSize}/{sortby}/{field}/{search}")
	public ResponseEntity<Map<String, Object>> getAttendanceReportByDate(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, @PathVariable("pageNo") int pageNo,
			@PathVariable("pageSize") int pageSize, @PathVariable("sortby") String sortby,
			@PathVariable("field") String field, @PathVariable("search") String search) {
		return new ResponseEntity<>(
				attendenceService.getAttendanceByDate(fromDate, toDate, pageNo, pageSize, sortby, field, search),
				HttpStatus.OK);
	}

	@GetMapping("/attendencereportlist/{loginyear}/{loginmonth}")
	public ResponseEntity<List<AttendanceReportDto>> getAttendenceReport(@PathVariable String loginyear,
			@PathVariable String loginmonth) {

		String date = "01";
		List<AttendanceReportDto> attendanceReportlist = attendenceService.attendanceReportlist(date, loginyear,
				loginmonth);

		return new ResponseEntity<List<AttendanceReportDto>>(attendanceReportlist, HttpStatus.OK);
	}

	@GetMapping("/allattendancereport/{date}")
	public ResponseEntity<List<AttendanceReportProjection>> getAllAttendanceReportProject(@PathVariable String date) {

		List<AttendanceReportProjection> allAttendanceReport = attendenceService.getAllAttendanceReport(date);
		return new ResponseEntity<List<AttendanceReportProjection>>(allAttendanceReport, HttpStatus.OK);
	}

	
	@GetMapping("/stf/{currentdate}/{staffid}")
	public ResponseEntity<?> getAttendanceOnlyStaffidandName(@PathVariable("currentdate")String currentdate,
			                                                 @PathVariable("staffid")int staffid) {
		return new ResponseEntity<>(attendenceService.getAttendenceandstaffnameandid(currentdate,staffid), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable int id)
	{
		attendenceService.deleteAttendece(id);
	}


	
	@GetMapping("/currentStaffId/{staffId}/{dayin}")
	public ResponseEntity<?> getCurrentAttendanceofStaffId(@PathVariable int staffId,@PathVariable String dayin){
		Attendence attendance = attendenceService.getCurrentAttendaceByStaffId(staffId,dayin);
		return new ResponseEntity<>(attendance,HttpStatus.OK);
	}
}
