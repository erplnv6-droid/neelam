package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.SCM.IndexDto.IndexAttendance;
import com.SCM.dto.AttendanceReportDto;
import com.SCM.dto.AttendenceDto;
import com.SCM.dtoReports.AttendanceReport;
import com.SCM.model.Attendence;
import com.SCM.projection.AttendanceReportProjection;


public interface AttendenceService {

	public Attendence saveAttendence(AttendenceDto attendenceDto);

	public void updateAttendece(int id,AttendenceDto attendenceDto);
	
	public Attendence getAttendenceById(int id);
	
	public List<Attendence> getAtendence();
	
	public void deleteAttendece(int id);
	

	public List<Attendence> getAttendanceByStaffId(int staffid);
	
	public List<Attendence> getLastRowOfAttendance();
	
	public List<AttendanceReport> getAllAttendenceReportByLoginYearAndDate(String loginyear,String loginmonth);


//  index Attendance
    
    
    public Map<String, Object> IndexAttendanceAsc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexAttendanceDesc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchAttendance(int pageno,int pagesize,String search);
    
	public Optional<Attendence> getByStaffId(int id);

	
// report attendance	


	public Map<String, Object> attendanceReport(int pageNo, int pageSize, String field, String date, String sortby, String search);
	
	public Map<String, Object> attendanceReport(int pageNo, int pageSize, String field, String date);
	
	public List<AttendanceReportDto> attendanceReportwithoutpaginnation( String date);
	
	public List<AttendanceReportDto> attendanceReportlist(String date,String Loginyear,String Loginmonth);



	
// report attendance
	
	public Map<String,Object> AttendanceReport(String loginyear,String loginmonth);

	public Map<String,Object> AttendanceReportAsc(String loginyear,String loginmonth);
	
	public Map<String,Object> AttendanceReportDesc(String loginyear,String loginmonth);
	
	
	
	
    public Map<Long, Object> IndexStaffAsc1(String loginyear,String loginmonth,int pageno,int pagesize,String field);
    
    public Map<String, Object> IndexStaffDSC1(String loginyear, String loginmonth,int pageno,int pagesize,String field);
        
    public Map<String, Object> SearchStaff1(String loginyear, String loginmonth,int pageno,int pagesize,String search);


	public List<Attendence> saveBulkAttendance(List<AttendenceDto> attendanceDtoList);

	public List<Attendence> updateBulkAttendance(List<AttendenceDto> attendanceDtoList);
	
	public  Map<String, Object> getAttendanceByDate(String fromDate, String toDate,int pageNo, int pageSize,String sortby, String field, String search);
	
	public List<AttendanceReportProjection> getAllAttendanceReport(String date);
	
	
	public List<IndexAttendance> getAttendenceandstaffnameandid(String currentdate,int staffid);
	
	
	public Attendence getCurrentAttendaceByStaffId(int sid,String dayin);


}
