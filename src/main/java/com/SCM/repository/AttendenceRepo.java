package com.SCM.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexAttendance;
import com.SCM.dtoReports.AttendanceReport;
import com.SCM.model.Attendence;
import com.SCM.projection.AttendanceReportProjection;
import com.SCM.projection.AttendanceSummaryProjection;
import com.SCM.projection.DateProjection;
import com.SCM.projection.TotalWorkingHoursProjection;


@Repository
public interface AttendenceRepo extends JpaRepository<Attendence, Integer> {

	
	@Query(value = "select * from attendence where staffuserid = ?1",nativeQuery = true)
	public List<Attendence> getAttendaceByStaffId(int staffid);
	
	@Query(value = "select * from attendence where sid=:sid and DATE(dayin)=:dayin order by dayout desc limit 1",nativeQuery = true)
	public Attendence getCurrentAttendaceByStaffId(int sid,String dayin);
	
	@Query(value =  "select * FROM attendence order by id desc limit 1",nativeQuery = true)
	public List<Attendence> getLastRowOfAttendance();
	
	
	@Query(value = "SELECT a.id,a.dayin,a.dayintime,a.dayout,a.dayouttime,a.staffuserid,s.staff_name"
			+ " from"
			+ " attendence a"
			+ " left join staff s on a.staffuserid = s.id",nativeQuery = true)
	List<IndexAttendance> indexAttendance(Pageable p);
	
	
	@Query(value = "select a.id,a.dayin,a.dayintime,a.dayout,a.dayouttime,a.staffuserid,a.sid,s.staff_name FROM attendence a"
			+ "   left join staff s on a.staffuserid = s.id "
			+ "   where a.dayin Like CONCAT('%', :search, '%')"
			+ "   or a.id Like CONCAT('%', :search, '%')"
			+ "   or a.dayin Like CONCAT('%', :search, '%')"
			+ "   or a.dayout Like CONCAT('%', :search, '%')"
			+ "   or a.dayintime Like CONCAT('%', :search, '%')"
			+ "   or a.dayouttime Like CONCAT('%', :search, '%')"
			+ "   or s.staff_name Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<IndexAttendance> SearchByAttendance(String search, Pageable p);
	
	
	@Query(value="select * from attendence where attendence.staffuserid= ?1\r\n"
			+ "order by  dayin desc ,dayintime desc\r\n"
			+ "limit 1",nativeQuery =  true)
	public Optional<Attendence> getPreviousLoginOfUser(int staffid);
	
	

	
//	@Query(value = "SELECT  d.d AS days,t.aid,t.staffid,"
//			+ "       COALESCE(t.staffname,'no name') AS staffname,"
//			+ "       COALESCE (t.dayin,'absent') as login\r\n"
//			+ "FROM days AS d\r\n"
//			+ "LEFT JOIN (SELECT s.id as staffid,a.id as aid,a.dayin as dayin,a.dayout,s.staff_name AS staffname,DATE(a.dayin) AS attendance_date,s.id\r\n"
//			+ "FROM attendence a\r\n"
//			+ "LEFT JOIN staff s ON a.sid = s.id\r\n"
//			+ "WHERE YEAR(a.dayin) = 2024 AND MONTH(a.dayin) = 4) AS t ON d.d = DAY(t.attendance_date)",nativeQuery = true)
//	List<AttendanceReport> fetchAttendanceReport(String loginyear,String loginmonth);

	
//	@Query(value = "SELECT  d.d AS days,t.aid,t.staffid,"
//			+ " COALESCE(t.staffname,'no name') AS staffname,"
//			+ " COALESCE (t.dayin,'absent') as login"
//			+ " FROM days AS d"
//			+ " LEFT JOIN (SELECT s.id as staffid,a.id as aid,a.dayin as dayin,a.dayout,s.staff_name AS staffname,DATE(a.dayin) AS attendance_date,s.id"
//			+ " FROM attendence a LEFT JOIN staff s ON a.sid = s.id"
//			+ " WHERE YEAR(a.dayin) = ?1 AND MONTH(a.dayin) = ?2) AS t ON d.d = DAY(t.attendance_date)",nativeQuery = true)
//	List<AttendanceReportProjection> fetchAttendanceReport1(String loginyear,String loginmonth);
//	
	

	
	@Query(value = "SELECT  d.d AS days,t.aid,t.staffid,"
			+ " COALESCE(t.staffname,'no name') AS staffname,"
			+ " COALESCE (t.dayin,'absent') as login"
			+ " FROM days AS d"
			+ " LEFT JOIN (SELECT s.id as staffid,a.id as aid,a.dayin as dayin,a.dayout,s.staff_name AS staffname,DATE(a.dayin) AS attendance_date,s.id"
			+ " FROM attendence a LEFT JOIN staff s ON a.sid = s.id"
			+ " WHERE YEAR(a.dayin) = ?1 AND MONTH(a.dayin) = ?2) AS t ON d.d = DAY(t.attendance_date)",nativeQuery = true)
	List<AttendanceReportProjection> fetchAttendanceReport1(String loginyear,String loginmonth);
	
	
	@Query(value = "select distinct (a.sid) from attendence a;",nativeQuery = true)
	List<Integer> fetchstaff();
	
	@Query(value = "SELECT dates.date,Date(att.dayin) as dayin, Date(att.dayout) as dayout, att.staffuserid as staff, att.dayintime, att.dayouttime, timediff(att.dayouttime, att.dayintime) as duration, att.status FROM\r\n"
			+ "(\r\n"
			+ "select a.Date as date \r\n"
			+ "from (\r\n"
			+ "    select last_day(?1) - INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as Date\r\n"
			+ "    from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a\r\n"
			+ "    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b\r\n"
			+ "    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c\r\n"
			+ ") a \r\n"
			+ "where a.Date between ?1 and last_day(?1) order by a.Date) as dates\r\n"
			+ "left join attendence as att on dates.date = Date(att.dayin)\r\n"
			+ "where staffuserid = ?2\r\n"
			+ "\r\n"
			+ "group by dates.date, att.dayin, att.dayout, att.staffuserid, att.dayintime, att.dayouttime, att.status\r\n"
			+ "\r\n"
			+ "order by dates.date asc", nativeQuery = true)
//	List<AttendanceReportProjection> getAttendanceReport(String date, int staffId);
	List<AttendanceReportProjection> getAttendanceReport(String date, long staffId);
	
	
	@Query(value = "SELECT dates.date,Date(att.dayin) as dayin, Date(att.dayout) as dayout, att.staffuserid as staff, att.dayintime, att.dayouttime, timediff(att.dayouttime, att.dayintime) as duration FROM\r\n"
			+ "(\r\n"
			+ "select a.Date as date \r\n"
			+ "from (\r\n"
			+ "    select last_day(?1) - INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as Date\r\n"
			+ "    from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a\r\n"
			+ "    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b\r\n"
			+ "    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c\r\n"
			+ ") a \r\n"
			+ "where a.Date between ?1 and last_day(?1) order by a.Date) as dates\r\n"
			+ "left join attendence as att on dates.date = Date(att.dayin)\r\n"
			+ "where staffuserid = ?2\r\n"
			+ "\r\n"
			+ "group by dates.date, att.dayin, att.dayout, att.staffuserid, att.dayintime, att.dayouttime\r\n"
			+ "\r\n"
			+ "order by dates.date asc", nativeQuery = true)
//	List<AttendanceReportProjection> getAttendanceReport(String date, int staffId);
	AttendanceReportProjection getSingleAttendanceReport(String date, long staffId);
	
//	sahil=================================
	
	@Query(value = "SELECT case when  isnull(att.status) then \"A\" else att.status end  as status ,dates.date,Date(att.dayin) as dayin, Date(att.dayout) as dayout, att.staffuserid as staff, att.dayintime, att.dayouttime, timediff(att.dayouttime, att.dayintime) as duration FROM		\r\n"
			+ "			(select a.Date as date \r\n"
			+ "			from (\r\n"
			+ "			select last_day(\"2024-04-01\") - INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as Date\r\n"
			+ "			from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a\r\n"
			+ "			cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b\r\n"
			+ "			cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c\r\n"
			+ "			) a \r\n"
			+ "			where a.Date between ?1 and last_day(?1) order by a.Date) as dates\r\n"
			+ "			left join attendence as att on dates.date = Date(att.dayin)\r\n"
			+ "			group by att.status,dates.date, att.dayin, att.dayout, att.staffuserid, att.dayintime, att.dayouttime\r\n"
			+ "			\r\n"
			+ "			order by dates.date asc", nativeQuery = true)
//	List<AttendanceReportProjection> getAttendanceReport(String date, int staffId);
	List<AttendanceReportProjection> getAllAttendanceReport(String date);
	
//	sahil==================================
	
	
	
	@Query(value = "select a.Date, coalesce(h.date,null) as date, case when coalesce(h.date,\"normal\") != \"normal\" then \"H\" else \"no\" end as holiday\r\n"
			+ "			from (\r\n"
			+ "			select last_day(?1) - INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as Date\r\n"
			+ "			from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a\r\n"
			+ "			cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b\r\n"
			+ "			cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c\r\n"
			+ "			) a left join holiday h on h.date = a.Date\r\n"
			+ "			where a.Date between ?1 and last_day(?1) order by a.Date", nativeQuery = true)
	List<DateProjection> getDatesByMonth(String date);

	@Query(value = "SELECT  d.d AS days,t.aid,t.staffid,"
			+ " COALESCE(t.staffname,'no name') AS staffname,"
			+ " COALESCE (t.dayin,'absent') as login"
			+ " FROM days AS d"
			+ " LEFT JOIN (SELECT s.id as staffid,a.id as aid,a.dayin as dayin,a.dayout,s.staff_name AS staffname,DATE(a.dayin) AS attendance_date,s.id"
			+ " FROM attendence a LEFT JOIN staff s ON a.sid = s.id"
			+ " WHERE YEAR(a.dayin) = ?1 AND MONTH(a.dayin) = ?2) AS t ON d.d = DAY(t.attendance_date) and t.staffid= ?3",nativeQuery = true)
	List<AttendanceReport> fetchAttendanceReport(String loginyear,String loginmonth,long staffId);
	
	
	@Query(value = "select count(status) statuscount, case when status is null then \"NO\" else status end as status from attendence "
			+ "where sid = ?2 and (dayin between ?1 and "
			+ "last_day(?1)) group by status",nativeQuery = true)
	List<AttendanceSummaryProjection> getAttendanceSummary(String date, Long id);
	
	
	@Query(value = "select case when sum(timediff(dayouttime, dayintime)) is null "
			+ "then 0 else sec_to_time(sum(time_to_sec(timediff(dayouttime, dayintime)))) end as workinghours "
			+ "from attendence a where sid = ?2 and (status = \"P\" or status = \"HP\" or status = \"WOP\") "
			+ "and dayin between ?1 and last_day(?1)", nativeQuery = true)
	TotalWorkingHoursProjection getTotalWorkingHours(String date, Long id);
	
	
//	@Query(value = "SELECT CASE WHEN sum(TIMESTAMPDIFF(SECOND, dayintime, dayouttime)) < 0 THEN '00:00:00' ELSE SEC_TO_TIME(sum(TIMESTAMPDIFF(SECOND, dayintime, dayouttime)))\r\n"
//			+ "END AS workinghours \r\n"
//			+ "FROM attendence a WHERE sid = ?1 AND (status = 'P' OR status = 'HP' OR status = 'WOP') \r\n"
//			+ "AND dayin BETWEEN ?1 AND LAST_DAY(?1)", nativeQuery = true)
//	TotalWorkingHoursProjection getTotalWorkingHours(String date, Long id);
	
	
	@Query(value = "select * from attendence where dayin between ?1 and ?2", nativeQuery = true)
	List<Attendence> getAttendancebyDate(String fromDate, String toDate, Pageable p);
	
	
	@Query(value = "select * from attendence where sid=?", nativeQuery = true)
	List<Attendence> findbyid(long id);
	
	@Query(value = "SELECT a.id,a.checkin_location_latitude,a.checkin_location_longitude,a.checkout_location_latitude,a.checkout_location_longitude,a.dayin,a.dayintime,a.dayout,a.dayouttime,a.sid,a.staffuserid,s.id as staffid,s.staff_name\r\n"
			+ "FROM attendence a\r\n"
			+ "LEFT JOIN staff s ON a.staffuserid = s.id "
			+ "WHERE a.dayin = ?1 and a.sid = ?2 ",nativeQuery = true)
	List<IndexAttendance> getAttendanceOnlystaffidandname(String currentdate,int staffid);
}
