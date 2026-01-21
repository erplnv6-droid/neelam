package com.SCM.serviceimpl;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;

import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexAttendance;
import com.SCM.IndexDto.IndexStaff;
import com.SCM.dto.AttendanceReportDto;
import com.SCM.dto.AttendanceSingleDto;
import com.SCM.dto.AttendanceSummaryReportDto;
import com.SCM.dto.AttendenceDto;
import com.SCM.model.Attendence;
import com.SCM.model.Holiday;
import com.SCM.projection.AttendanceReportProjection;
import com.SCM.projection.AttendanceSummaryProjection;
import com.SCM.projection.DateProjection;
import com.SCM.projection.TotalWorkingHoursProjection;
import com.SCM.model.Staff;

import com.SCM.repository.AttendenceRepo;
import com.SCM.repository.HolidayRepo;
import com.SCM.repository.StaffRepo;
import com.SCM.service.AttendenceService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AttendenceServiceImpl implements AttendenceService {

	@Autowired
	private AttendenceRepo attendenceRepo;

	@Autowired
	private StaffRepo staffRepo;

	@Autowired
	private HolidayRepo holidayRepo;

	@Autowired
	ModelMapper modelMapper;

	private static int k = 0;

	@Override
	public Attendence saveAttendence(AttendenceDto attendenceDto) {

		LocalDate date = LocalDate.now();
		java.sql.Date date2 = java.sql.Date.valueOf(date);

		Attendence attendence = new Attendence();
		attendence.setDayin(date2);
		attendence.setDayintime(LocalTime.now());
		attendence.setCheckinLocationLatitude(attendenceDto.getCheckinLocationLatitude());
		attendence.setCheckinLocationLongitude(attendenceDto.getCheckinLocationLongitude());
		attendence.setStaff(attendenceDto.getStaff());
		attendence.setSid(attendenceDto.getSid());
		
		List<Date> holidayDatesList = new ArrayList<>();
		List<Holiday> holidayLists = holidayRepo.getAllHolidaysByDate(date.toString());
		holidayLists.forEach(day->{
			
			holidayDatesList.add(day.getDate());
		});
		
		if(holidayDatesList.contains(date2)) {
			
			attendence.setStatus("HP");	
		}else {
			attendence.setStatus("P");			
		}
		Attendence a = attendenceRepo.save(attendence);
		return a;
	}


	@Override
	public void updateAttendece(int id, AttendenceDto attendenceDto) {

		LocalDate date = LocalDate.now();
		java.sql.Date date2 = java.sql.Date.valueOf(date);
		
		LocalTime currentTime = LocalTime.now();
		Attendence attendence = attendenceRepo.findById(id).get();
		LocalTime dayintime = attendence.getDayintime();

		attendence.setDayout(date2);
		attendence.setDayouttime(LocalTime.now());
		
		LocalTime localTime=LocalTime.now();

		LocalTime localTime2=LocalTime.of(23, 59);


		if(localTime.equals(localTime2))
		{
			attendence.setDayouttime(localTime2);
		}
		
		
		attendence.setCheckoutLocationLatitude(attendenceDto.getCheckoutLocationLatitude());
		attendence.setCheckoutLocationLongitude(attendenceDto.getCheckoutLocationLongitude());
		attendence.setStaff(attendenceDto.getStaff());
		attendence.setStatus(attendenceDto.getStatus());
		attendence.setSid(attendenceDto.getSid());
		
		if(dayintime.until(currentTime,ChronoUnit.MINUTES) < 240) {			
		  attendence.setStatus("HD");
		}else {
		  attendence.setStatus("P");
		}
		
		attendenceRepo.save(attendence);
	}

	
	@Override
	public Attendence getAttendenceById(int id) {

		Attendence attendence = attendenceRepo.findById(id).get();

		return attendence;
	}

	@Override
	public List<Attendence> getAtendence() {

		return attendenceRepo.findAll();
	}

	@Override
	public void deleteAttendece(int id) {

		attendenceRepo.deleteById(id);
	}

	@Override
	public List<Attendence> getAttendanceByStaffId(int staffid) {

		List<Attendence> a = attendenceRepo.getAttendaceByStaffId(staffid);

		return a;
	}

	@Override
	public List<Attendence> getLastRowOfAttendance() {

		return attendenceRepo.getLastRowOfAttendance();
	}

	@Override
	public Map<String, Object> IndexAttendanceAsc(int pagno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = attendenceRepo.count();
		long pages = countpages / pagesize;

		List<IndexAttendance> ipo = attendenceRepo.indexAttendance(p);

		response.put("Index", ipo);
		response.put("CountPages", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexAttendanceDesc(int pagno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = attendenceRepo.count();
		long pages = countpages / pagesize;

		List<IndexAttendance> ipo = attendenceRepo.indexAttendance(p);

		response.put("Index", ipo);
		response.put("CountPages", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchAttendance(int pageno, int pagesize, String search) {

		Pageable p = PageRequest.of(pageno, pagesize);
		Map<String, Object> response = new HashMap<>();

		List<IndexAttendance> attendance = attendenceRepo.SearchByAttendance(search, p);
		long searchcount = attendance.size();

		response.put("data", attendance);
		response.put("SearchCount", searchcount);

		return response;
	}

	@Override
	public Optional<Attendence> getByStaffId(int id) {
		Optional<Attendence> attendenceByStaffId = attendenceRepo.getPreviousLoginOfUser(id);
		return attendenceByStaffId;
	}

	@Override
	public Map<String, Object> AttendanceReportAsc(String loginyear, String loginmonth) {

//		Map<String, Object> response = new HashMap<>();
//		Sort sort = Sort.by(Sort.Direction.ASC,field);
//		
//		Pageable p = PageRequest.of(pageno, pagesize, sort);
//		
//		List<Staff> staffList = new ArrayList<Staff>();
//		staffList = staffRepo.findall(p);
//			
//
//  	      long countpages = attendenceRepo.fetchAttendanceReport1(loginyear,loginmonth).size();
//		  long pages = countpages / pagesize;
//		  
//		
//         staffList.forEach(staff->{
//
//			List<com.SCM.dtoReports.AttendanceReport> ipo = attendenceRepo.fetchAttendanceReport(loginyear,loginmonth,staff.getId());
//	
//			response.put(staff.getStaffName()+" "+staff.getId(),ipo);
//			
//			  response.put("Index", ipo);
//			  response.put("Enteries", countpages);
//			  response.put("Pages", pages);
//			  
//		});

		return null;
	}

	@Override
	public Map<String, Object> attendanceReport(int pageNo, int pageSize, String field, String date, String sortby, String search) {
		// for pagination
		Map<String, Object> report = new HashMap<>();
		
		// for pagination

		List<AttendanceReportDto> attendanceReportListDto = new ArrayList<>(); // Main DTO
		List<DateProjection> dates = attendenceRepo.getDatesByMonth(date); // to get all dates
		List<Holiday> holidays = holidayRepo.getAllHolidaysByDate(date);
		int totalAllHolidays = holidays.size();
		List<AttendanceSingleDto> attendanceSingleDtoList = new ArrayList<>(); //
//		List<AttendanceReportProjection> attendanceReport = attendenceRepo.getAttendanceReport("2024-04-01", 103); // attendance
																													// report
																													// by
		Sort sort;
		if(sortby.equals("desc")) {
			sort = Sort.by(Sort.Direction.DESC, field);
		}else {
			sort = Sort.by(Sort.Direction.ASC, field);			
		}
		Pageable p = PageRequest.of(pageNo, pageSize, sort);
		List<IndexStaff> listStaff;
		if(!search.equals(" ")) {
			listStaff = staffRepo.SearchByStaff(search, p);
		}else {
			listStaff = staffRepo.indexstaff(p); // list of all staff
		}
		long totalItems = staffRepo.findallcount().size();
		long pagesCount = (long) Math.abs(totalItems / pageSize);
		long remainder = totalItems % pageSize;
		if (remainder > 0) {
			pagesCount++;
		}
//		List<StaffDto> staffList = staffRepo.indexstaff();
//		List<Long> listStaffIds = new ArrayList<>();
		// NEW LOGIC
		List<Integer> countList = new ArrayList<Integer>();
		countList.add(0);
//		Map<String, List<AttendanceReportProjection>> mapAttendance = new HashMap<>();
		// -------------------------------------------

		listStaff.forEach(staff -> {
			AttendanceReportDto tempattendanceReportDto = new AttendanceReportDto();
			List<AttendanceSummaryProjection> attendanceSummary = attendenceRepo.getAttendanceSummary(date,
					staff.getid());
			TotalWorkingHoursProjection totalWorkinghours = attendenceRepo.getTotalWorkingHours(date, staff.getid());
//			List<AttendanceSummaryReportDto> attendanceSummaryDto = new ArrayList<>();
			List<AttendanceReportProjection> tempattendanceReport = attendenceRepo.getAttendanceReport(date,
					staff.getid());
//			Map<String, List<AttendanceSingleDto>> mapSingleDto = new HashMap<>();
//			
			List<Date> allDatesList = new ArrayList<>();
			List<Date> allpresentDates = new ArrayList<>();
			int[] totalAbsentList = {0};
			dates.forEach(day->{
				allDatesList.add(day.getDate());
			});
			//
			tempattendanceReportDto.setId(staff.getid());
			tempattendanceReportDto.setStaffName(staff.getStaff_Name());
			tempattendanceReportDto.setAttendanceData(attendanceSingleDtoList);
			tempattendanceReportDto.setTotalWorkingHours(totalWorkinghours.getWorkinghours());
			
			if (attendanceSummary.size() != 0) {
			attendanceSummary.forEach(summary -> {
//				AttendanceSummaryReportDto newDto = this.projectionToDto(summary);
				ObjectMapper newObjMapper = new ObjectMapper();
				StringWriter newStrWriter = new StringWriter();
				try {
					newObjMapper.writeValue(newStrWriter, summary);
				} catch (StreamWriteException e) {
					
					e.printStackTrace();
				} catch (DatabindException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
					if (summary.getStatus().equals("P")) {
						
						if(summary.getStatuscount() != null) {
						tempattendanceReportDto.setTotalPresent(summary.getStatuscount().intValue());
						}
					}
					if (summary.getStatus().equals("A")) {
						
						if(summary.getStatuscount() != null) {
							totalAbsentList[0] = totalAbsentList[0]+ summary.getStatuscount().intValue();
//						tempattendanceReportDto.setTotalAbsent(summary.getStatuscount().intValue());
						}
					}
			    if(summary.getStatus().equals("H")) {
			    	tempattendanceReportDto.setTotalHoliday(summary.getStatuscount() + totalAllHolidays);
			    }
			    if(summary.getStatus().equals("HP")) {
			    	tempattendanceReportDto.setTotalHolidayPresent(summary.getStatuscount());
			    }
			    if(summary.getStatus().equals("L")) {
			    	tempattendanceReportDto.setTotalLeave(summary.getStatuscount());
			    }
			    if(summary.getStatus().equals("WO")) {
			    	tempattendanceReportDto.setTotalWorkOff(summary.getStatuscount());
			    }
			    if(summary.getStatus().equals("WOP")) {
			    	tempattendanceReportDto.setTotalWorkOffPresent(summary.getStatuscount());
			    }
			    if(summary.getStatus().equals("HD")) {
			    	tempattendanceReportDto.setTotalHalfDays(summary.getStatuscount());
			    }
			    
			});
			}

			List<AttendanceSingleDto> newListtempAttendanceSingleDto = new ArrayList();
			tempattendanceReport.forEach(attendance -> {
				AttendanceSingleDto newtempAttendanceSingleDto = new AttendanceSingleDto();
				newtempAttendanceSingleDto = modelMapper.map(attendance, AttendanceSingleDto.class);
				newListtempAttendanceSingleDto.add(newtempAttendanceSingleDto);
				allpresentDates.add(attendance.getDate());
				tempattendanceReportDto.setAttendanceData(newListtempAttendanceSingleDto);
			});
			
			allDatesList.forEach(day->{
				if(!allpresentDates.contains(day)) {
					AttendanceSingleDto newtempAttendanceSingleDto = new AttendanceSingleDto();
					newtempAttendanceSingleDto.setDate(day);
					newtempAttendanceSingleDto.setStatus("A");
					newListtempAttendanceSingleDto.add(newtempAttendanceSingleDto);
					tempattendanceReportDto.setAttendanceData(newListtempAttendanceSingleDto);
					totalAbsentList[0] = totalAbsentList[0]+ 1;
				}
			});
			tempattendanceReportDto.setTotalAbsent(totalAbsentList[0]);
			attendanceReportListDto.add(tempattendanceReportDto);
		});
//		for pagination
		report.put("Index", attendanceReportListDto);
		report.put("Pages", pagesCount);
		report.put("Entries", totalItems);
//		for pagination

		return report;
	}

	

	@Override
	public List<AttendanceReportDto> attendanceReportlist(
			String date,String Loginyear,String Loginmonth) {
		// TODO Auto-generated method stub
		List<AttendanceReportDto> attendanceReportListDto = new ArrayList<>(); // Main DTO
		
		
		
		List<DateProjection> dates = attendenceRepo.getDatesByMonth(date); // to get all dates
		List<Holiday> holidays = holidayRepo.getAllHolidaysByDate(date);
		int totalAllHolidays = holidays.size();
		List<AttendanceSingleDto> attendanceSingleDtoList = new ArrayList<>(); //

		List<Staff> listStaff;
		listStaff=staffRepo.findAll();
		
		listStaff.forEach(staff -> {
			AttendanceReportDto tempattendanceReportDto = new AttendanceReportDto();
			List<AttendanceSummaryProjection> attendanceSummary = attendenceRepo.getAttendanceSummary(date,
					staff.getId());
			TotalWorkingHoursProjection totalWorkinghours = attendenceRepo.getTotalWorkingHours(date, staff.getId());
//			List<AttendanceSummaryReportDto> attendanceSummaryDto = new ArrayList<>();
//			List<AttendanceReportProjection> tempattendanceReport = attendenceRepo.getAttendanceReport(date,staff.getId());
			List<AttendanceReportProjection> tempattendanceReport = attendenceRepo.fetchAttendanceReport1(Loginyear, Loginmonth);

//			Map<String, List<AttendanceSingleDto>> mapSingleDto = new HashMap<>();
//			
			List<Date> allDatesList = new ArrayList<>();
			List<Date> allpresentDates = new ArrayList<>();
			int[] totalAbsentList = {0};
			dates.forEach(day->{
				allDatesList.add(day.getDate());
			});
			//
			tempattendanceReportDto.setId(staff.getId());
			tempattendanceReportDto.setStaffName(staff.getStaffName());
			tempattendanceReportDto.setAttendanceData(attendanceSingleDtoList);
			tempattendanceReportDto.setTotalWorkingHours(totalWorkinghours.getWorkinghours());
			
			if (attendanceSummary.size() != 0) {
			attendanceSummary.forEach(summary -> {
//				AttendanceSummaryReportDto newDto = this.projectionToDto(summary);
				ObjectMapper newObjMapper = new ObjectMapper();
				StringWriter newStrWriter = new StringWriter();
				try {
					newObjMapper.writeValue(newStrWriter, summary);
				} catch (StreamWriteException e) {
					
					e.printStackTrace();
				} catch (DatabindException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
					if (summary.getStatus().equals("P")) {
						
						if(summary.getStatuscount() != null) {
						tempattendanceReportDto.setTotalPresent(summary.getStatuscount().intValue());
						}
					}
					if (summary.getStatus().equals("A")) {
						
						if(summary.getStatuscount() != null) {
							totalAbsentList[0] = totalAbsentList[0]+ summary.getStatuscount().intValue();
//						tempattendanceReportDto.setTotalAbsent(summary.getStatuscount().intValue());
						}
					}
			    if(summary.getStatus().equals("H")) {
			    	tempattendanceReportDto.setTotalHoliday(summary.getStatuscount() + totalAllHolidays);
			    }
			    if(summary.getStatus().equals("HP")) {
			    	tempattendanceReportDto.setTotalHolidayPresent(summary.getStatuscount());
			    }
			    if(summary.getStatus().equals("L")) {
			    	tempattendanceReportDto.setTotalLeave(summary.getStatuscount());
			    }
			    if(summary.getStatus().equals("WO")) {
			    	tempattendanceReportDto.setTotalWorkOff(summary.getStatuscount());
			    }
			    if(summary.getStatus().equals("WOP")) {
			    	tempattendanceReportDto.setTotalWorkOffPresent(summary.getStatuscount());
			    }
			    if(summary.getStatus().equals("HD")) {
			    	tempattendanceReportDto.setTotalHalfDays(summary.getStatuscount());
			    }
			    
			});
			}

			List<AttendanceSingleDto> newListtempAttendanceSingleDto = new ArrayList();
			tempattendanceReport.forEach(attendance -> {
				AttendanceSingleDto newtempAttendanceSingleDto = new AttendanceSingleDto();
				newtempAttendanceSingleDto = modelMapper.map(attendance, AttendanceSingleDto.class);
				newListtempAttendanceSingleDto.add(newtempAttendanceSingleDto);
				allpresentDates.add(attendance.getDate());
				tempattendanceReportDto.setAttendanceData(newListtempAttendanceSingleDto);
			});
			
			allDatesList.forEach(day->{
				if(!allpresentDates.contains(day)) {
					AttendanceSingleDto newtempAttendanceSingleDto = new AttendanceSingleDto();
					newtempAttendanceSingleDto.setDate(day);
					newtempAttendanceSingleDto.setStatus("A");
					newListtempAttendanceSingleDto.add(newtempAttendanceSingleDto);
					tempattendanceReportDto.setAttendanceData(newListtempAttendanceSingleDto);
					totalAbsentList[0] = totalAbsentList[0]+ 1;
				}
			});
			tempattendanceReportDto.setTotalAbsent(totalAbsentList[0]);
			 attendanceReportListDto.add(tempattendanceReportDto);
		});
		
		
		
		
		
		
		
		return attendanceReportListDto;
	}
//	@Override
//	public List<com.SCM.dtoReports.AttendanceReport> AttendanceReport(String loginyear, String loginmonth) {

//		return null;
//	}

	public Map<String, Object> AttendanceReportDesc(String loginyear, String loginmonth) {

		
		
		
		
		
		
//		Map<String, Object> response = new HashMap<>();
//        Sort sort = Sort.by(Sort.Direction.DESC, field);
//		
//		Pageable p = PageRequest.of(pageno, pagesize, sort);
//				
//		List<Staff> staffList = new ArrayList<Staff>();
//		staffList = staffRepo.findAll();
//			  	
//		staffList.forEach(staff->{
//			
//			long countpages = attendenceRepo.fetchAttendanceReport1(loginyear, loginmonth).size();
//	     	long pages = countpages / pagesize;
//			
//			List<com.SCM.dtoReports.AttendanceReport> ipo = attendenceRepo.fetchAttendanceReport(loginyear,loginmonth,staff.getId(),p);
//			response.put(staff.getStaffName()+" "+staff.getId(), ipo);
//			 
//			    response.put("Index", ipo);
//			    response.put("Enteries", countpages);
//			    response.put("Pages", pages);
//		});

		return null;
	}

	@Override
	public Map<String, Object> AttendanceReport(String loginyear, String loginmonth) {
		List<Staff> staffList = new ArrayList<Staff>();
		staffList = staffRepo.findAll();

		Map<String, Object> response = new HashMap<String, Object>();

		staffList.forEach(staff -> {

//			 response.put(staff.getStaffName()+" "+staff.getId(), attendenceRepo.fetchAttendanceReport1(loginyear,loginmonth,staff.getId()));
		});

		return response;
	}

	@Override
	public Map<Long, Object> IndexStaffAsc1(String loginyear, String loginmonth, int pageno, int pagesize,
			String field) {

		Map<Long, Object> response = new HashMap<>();

		int year = Integer.parseInt(loginyear);
		int month = Integer.parseInt(loginmonth);

		int lastdayofmonth = YearMonth.of(year, month).lengthOfMonth();
		LocalDate lastdate = LocalDate.of(year, month, lastdayofmonth);

		System.out.println(lastdayofmonth);
		System.out.println(lastdate);

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = staffRepo.findallcount().size();
		long pages = countpages / pagesize;

		List<Staff> staffList = new ArrayList<Staff>();
		staffList = staffRepo.findall(p);

//		  response.put("Enteries", countpages);
//		  response.put("Pages", pages);

		staffList.forEach(staff -> {

			List<com.SCM.dtoReports.AttendanceReport> ipo = attendenceRepo.fetchAttendanceReport(loginyear, loginmonth,
					staff.getId());

			response.put(staff.getId(), ipo);

//					response.put("Index", ipo);

		});

		return response;
	}

	@Override
	public Map<String, Object> IndexStaffDSC1(String loginyear, String loginmonth, int pageno, int pagesize,
			String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = staffRepo.findallcount().size();
		long pages = countpages / pagesize;

		List<Staff> staffList = new ArrayList<Staff>();
		staffList = staffRepo.findall(p);

		staffList.forEach(staff -> {

//			List<com.SCM.dtoReports.AttendanceReport> ipo = attendenceRepo.fetchAttendanceReport(loginyear,loginmonth,staff.getId());

//			response.put(staff.getStaffName()+ " " +staff.getId(),ipo);

//				  response.put("Index", ipo);

		});

		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchStaff1(String loginyear, String loginmonth, int pageno, int pagesize,
			String search) {

		return null;
	}

	@Override
	public List<Attendence> saveBulkAttendance(List<AttendenceDto> attendanceDtoList) {
		List<Attendence> attendanceList = new ArrayList<>();
		attendanceDtoList.forEach(attendance -> {
			System.out.println(attendance);
			Attendence newAttendance = attendenceRepo.save(this.attendanceDtoToAttendance(attendance));
			attendanceList.add(newAttendance);
		});
		return attendanceList;
//		return null;
	}

	@Override
	public List<Attendence> updateBulkAttendance(List<AttendenceDto> attendanceDtoList) {
		List<Attendence> attendanceList = new ArrayList<>();
		attendanceDtoList.forEach(attendance -> {

			Attendence tempAttendence = attendenceRepo.findById(attendance.getId()).get();

			Attendence newAttendance = attendenceRepo
					.save(this.attendanceDtoToAttendance(this.attendanceToAttendanceDto(tempAttendence)));
			attendanceList.add(newAttendance);
		});
		return attendanceList;
//		return null;
	}

	public Attendence attendanceDtoToAttendance(AttendenceDto attendanceDto) {
		Date dayin = attendanceDto.getDayin();
		return modelMapper.map(attendanceDto, Attendence.class);
//		return null;
	}

	public AttendenceDto attendanceToAttendanceDto(Attendence atetndance) {
		return modelMapper.map(atetndance, AttendenceDto.class);
	}

	public AttendanceSummaryReportDto projectionToDto(AttendanceSummaryProjection projection) {
		return modelMapper.map(projection, AttendanceSummaryReportDto.class);
	}

	@Override
	public Map<String, Object> getAttendanceByDate(String fromDate, String toDate,int pageNo, int pageSize,String sortby, String field, String search) {
		Map<String, Object> report = new HashMap<>();
		Sort sort;
		if(sortby.equals("desc")) {
			sort = Sort.by(Sort.Direction.DESC, field);
		}else {
			sort = Sort.by(Sort.Direction.ASC, field);			
		}
		Pageable p = PageRequest.of(pageNo, pageSize, sort);
		
		List<IndexStaff> listStaff;
		if(!search.equals(" ")) {
			listStaff = staffRepo.SearchByStaff(search, p);
		}else {
			listStaff = staffRepo.indexstaff(p); // list of all staff
		}
		
		long totalItems = attendenceRepo.findAll().size();
		long pagesCount = (long) Math.abs(totalItems / pageSize);
		long remainder = totalItems % pageSize;
		if (remainder > 0) {
			pagesCount++;
		}
		report.put("Index",attendenceRepo.getAttendancebyDate(fromDate, toDate, p));
		report.put("Pages", pagesCount);
		report.put("Entries", totalItems);
		
		
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		Long id = userDetailsImpl.getId();
		
		List<Attendence> findbyid = attendenceRepo.findbyid(id);
		
		for(Attendence attendence:findbyid)
		{
		
		LocalTime localTime=LocalTime.now();

		LocalTime localTime2=LocalTime.of(16, 15);


		if(localTime.equals(localTime2))
		{
			attendence.setDayouttime(localTime2);
		}
		}
		
		
		return report;
	}

	
	@Override
	public List<com.SCM.dtoReports.AttendanceReport> getAllAttendenceReportByLoginYearAndDate(String loginyear,
			String loginmonth) {
		// TODO Auto-generated method stub
//		
//		List<com.SCM.dtoReports.AttendanceReport> fetchAttendanceReport1 = attendenceRepo.fetchAttendanceReport1(loginyear, loginmonth);
//		return fetchAttendanceReport1;
		
		return null;
	}

	@Override
	public List<AttendanceReportProjection> getAllAttendanceReport(String date) {
		// TODO Auto-generated method stub
		
		List<AttendanceReportProjection> allAttendanceReport = attendenceRepo.getAllAttendanceReport(date);
		
		return allAttendanceReport;
	}

	
	
	@Override
	public List<AttendanceReportDto> attendanceReportwithoutpaginnation(String date) {
	
	 Map<String, Object> report = new HashMap<>();
		
		// for pagination

		List<AttendanceReportDto> attendanceReportListDto = new ArrayList<>(); // Main DTO
		List<DateProjection> dates = attendenceRepo.getDatesByMonth(date); // to get all dates
		List<Holiday> holidays = holidayRepo.getAllHolidaysByDate(date);
		int totalAllHolidays = holidays.size();
		List<AttendanceSingleDto> attendanceSingleDtoList = new ArrayList<>(); //
//		List<AttendanceReportProjection> attendanceReport = attendenceRepo.getAttendanceReport("2024-04-01", 103); // attendance
																													// report																									// by
		List<Staff> listStaff;
		listStaff = staffRepo.findAll();
	
//		List<StaffDto> staffList = staffRepo.indexstaff();
//		List<Long> listStaffIds = new ArrayList<>();
		// NEW LOGIC
		List<Integer> countList = new ArrayList<Integer>();
		countList.add(0);
//		Map<String, List<AttendanceReportProjection>> mapAttendance = new HashMap<>();
		
		// -------------------------------------------

		listStaff.forEach(staff -> {
			
			AttendanceReportDto tempattendanceReportDto = new AttendanceReportDto();
			List<AttendanceSummaryProjection> attendanceSummary = attendenceRepo.getAttendanceSummary(date,staff.getId());
			TotalWorkingHoursProjection totalWorkinghours = attendenceRepo.getTotalWorkingHours(date, staff.getId());
			
//			List<AttendanceSummaryReportDto> attendanceSummaryDto = new ArrayList<>();
			List<AttendanceReportProjection> tempattendanceReport = attendenceRepo.getAttendanceReport(date,staff.getId());
			
//			Map<String, List<AttendanceSingleDto>> mapSingleDto = new HashMap<>();
//			
			List<Date> allDatesList = new ArrayList<>();
			List<Date> allpresentDates = new ArrayList<>();
			int[] totalAbsentList = {0};
			
			dates.forEach(day->{
				allDatesList.add(day.getDate());
			});
			//
			tempattendanceReportDto.setId(staff.getId());
			tempattendanceReportDto.setStaffName(staff.getStaffName());
			tempattendanceReportDto.setAttendanceData(attendanceSingleDtoList);
			tempattendanceReportDto.setTotalWorkingHours(totalWorkinghours.getWorkinghours());
			
			if (attendanceSummary.size() != 0) {
			attendanceSummary.forEach(summary -> {
//				AttendanceSummaryReportDto newDto = this.projectionToDto(summary);
				ObjectMapper newObjMapper = new ObjectMapper();
				StringWriter newStrWriter = new StringWriter();
				try {
					newObjMapper.writeValue(newStrWriter, summary);
				} catch (StreamWriteException e) {
					
					e.printStackTrace();
				} catch (DatabindException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
					if (summary.getStatus().equals("P")) {
						
						if(summary.getStatuscount() != null) {
						tempattendanceReportDto.setTotalPresent(summary.getStatuscount().intValue());
						}
					}
					if (summary.getStatus().equals("A")) {
						
						if(summary.getStatuscount() != null) {
							totalAbsentList[0] = totalAbsentList[0]+ summary.getStatuscount().intValue();
//						tempattendanceReportDto.setTotalAbsent(summary.getStatuscount().intValue());
						}
					}
			    if(summary.getStatus().equals("H")) {
			    	tempattendanceReportDto.setTotalHoliday(summary.getStatuscount() + totalAllHolidays);
			    }
			    if(summary.getStatus().equals("HP")) {
			    	tempattendanceReportDto.setTotalHolidayPresent(summary.getStatuscount());
			    }
			    if(summary.getStatus().equals("L")) {
			    	tempattendanceReportDto.setTotalLeave(summary.getStatuscount());
			    }
			    if(summary.getStatus().equals("WO")) {
			    	tempattendanceReportDto.setTotalWorkOff(summary.getStatuscount());
			    }
			    if(summary.getStatus().equals("WOP")) {
			    	tempattendanceReportDto.setTotalWorkOffPresent(summary.getStatuscount());
			    }
			    if(summary.getStatus().equals("HD")) {
			    	tempattendanceReportDto.setTotalHalfDays(summary.getStatuscount());
			    }
			    
			});
			}

			List<AttendanceSingleDto> newListtempAttendanceSingleDto = new ArrayList();
			tempattendanceReport.forEach(attendance -> {
				AttendanceSingleDto newtempAttendanceSingleDto = new AttendanceSingleDto();
				newtempAttendanceSingleDto = modelMapper.map(attendance, AttendanceSingleDto.class);
				newListtempAttendanceSingleDto.add(newtempAttendanceSingleDto);
				allpresentDates.add(attendance.getDate());
				tempattendanceReportDto.setAttendanceData(newListtempAttendanceSingleDto);
			});
			
			allDatesList.forEach(day->{
				if(!allpresentDates.contains(day)) {
					AttendanceSingleDto newtempAttendanceSingleDto = new AttendanceSingleDto();
					newtempAttendanceSingleDto.setDate(day);
					newtempAttendanceSingleDto.setStatus("A");
					newListtempAttendanceSingleDto.add(newtempAttendanceSingleDto);
					tempattendanceReportDto.setAttendanceData(newListtempAttendanceSingleDto);
					totalAbsentList[0] = totalAbsentList[0]+ 1;
				}
			});
			tempattendanceReportDto.setTotalAbsent(totalAbsentList[0]);
			attendanceReportListDto.add(tempattendanceReportDto);
		});

		return attendanceReportListDto;
	}

	
	
	@Override
	public Map<String, Object> attendanceReport(int pageNo, int pageSize, String field, String date) {
		// TODO Auto-generated method stub
		return null;
	}


	
	@Override
	public List<IndexAttendance> getAttendenceandstaffnameandid(String currentdate,int staffid) {
		
		List<IndexAttendance> attendanceOnlystaffidandname = attendenceRepo.getAttendanceOnlystaffidandname(currentdate,staffid);
		
		return attendanceOnlystaffidandname;
	}	

	
	@Override
	public Attendence getCurrentAttendaceByStaffId(int sid,String dayin) {

		Attendence attendance = attendenceRepo.getCurrentAttendaceByStaffId(sid,dayin);
		return attendance;
	}	
}
