package com.SCM.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexStaffCheckinAndVisitData;
import com.SCM.IndexDto.IndexVisitData;
import com.SCM.IndexDto.LocationIndex;
import com.SCM.IndexDto.LocationWithAttendanceIndex;
import com.SCM.dto.LocationDto1;
import com.SCM.dto.VisitData;
import com.SCM.dto.VisitDto;
import com.SCM.model.Attendence;
import com.SCM.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

	@Modifying
	@Transactional
	@Query(value = "update Location l set l.checkoutLocation = :checkoutLocation,l.checkoutLocationDate = :checkoutLocationDate,"
			  + " l.checkoutLocationLatitude = :checkoutLocationLatitude,l.checkoutLocationLongitude = :checkoutLocationLongitude  where l.id = :id")
	public void updatelocation(int id, String checkoutLocation, String checkoutLocationDate,String checkoutLocationLatitude, String checkoutLocationLongitude);

	@Query(value = "select * from location where staff_id = ?1", nativeQuery = true)
	public List<Location> getLocationByStaffId(int staffid);

	@Modifying
	@Transactional
	@Query(value = "delete from location where staff_id = ?1", nativeQuery = true)
	public void deleteByStaffId(int staffid);

//	@Query(value = "SELECT * FROM location WHERE staff_id = ?1 AND STR_TO_DATE(checkin_location_date, '%d-%m-%Y') = ?2", nativeQuery = true)
//	List<Location> Locationdatabystaffanddate(int staffid, String currentdate);

	
	@Query(value = "SELECT l.id,l.checkin_location_date as checkinLocationDate,l.checkin_location as checkinLocation,l.checkin_location_latitude as \r\n"
			+ "checkinLocationLatitude,l.checkin_location_longitude as checkinLocationLongitude,l.checkout_location_date as checkoutLocationDate,\r\n"
			+ "l.checkout_location as checkoutLocation,l.checkout_location_latitude as checkoutLocationLatitude,\r\n"
			+ "l.checkout_location_longitude as checkoutLocationLongitude,s.staff_name as staff,d.trade_name as distributor,\r\n"
			+ "r.trade_name as retailer\r\n"
			+ "FROM location l\r\n"
			+ "left join staff s on s.id=l.staff_id\r\n"
			+ "left join distributor d on d.id=l.distributor_id\r\n"
			+ "left join retailer r on r.id=l.retailer_id"
			+ " WHERE staff_id = ?1 AND STR_TO_DATE(checkin_location_date, '%d-%m-%Y') = ?2", nativeQuery = true)
	List<LocationDto1> Locationdatabystaffanddate(int staffid, String currentdate);

	
	
	
//	@Query(value = 
//		    "SELECT " +
//		    " l.id AS id, " +
//		    " l.checkin_location AS checkin_location, " +
//		    " l.checkin_location_date AS checkin_location_date, " +
//		    " l.checkin_location_latitude AS checkin_location_latitude, " +
//		    " l.checkin_location_longitude AS checkin_location_longitude, " +
//		    " l.checkin_location_time AS checkin_location_time, " +
//		    " l.checkout_location AS checkout_location, " +
//		    " l.checkout_location_date AS checkout_location_date, " +
//		    " l.checkout_location_latitude AS checkout_location_latitude, " +
//		    " l.checkout_location_longitude AS checkout_location_longitude, " +
//		    " l.checkout_location_time AS checkout_location_time, " +
//		    " l.createddate AS createddate, " +
//		    " l.createdtime AS createdtime, " +
//		    " r.trade_name AS rname, " +
//		    " d.trade_name AS dname " +
//		    "FROM location l " +
//		    "LEFT JOIN retailer r ON l.retailer_id = r.id " +
//		    "LEFT JOIN distributor d ON l.distributor_id = d.id " +
//		    "WHERE l.staff_id = :staffid " +
//		    "AND STR_TO_DATE(l.checkin_location_date, '%d-%m-%Y %H:%i:%s') " +
//		    "    = STR_TO_DATE(:currentdate, '%d-%m-%Y')",
//		    nativeQuery = true)
//		List<LocationIndex> Locationspecificbystaffanddate(
//		        @Param("staffid") int staffid,
//		        @Param("currentdate") String currentdate
//		);
	
	@Query(value =
	        "SELECT " +
	        " l.id AS id, " +
	        " l.checkin_location AS checkin_location, " +
	        " l.checkin_location_date AS checkin_location_date, " +
	        " l.checkin_location_latitude AS checkin_location_latitude, " +
	        " l.checkin_location_longitude AS checkin_location_longitude, " +
	        " l.checkin_location_time AS checkin_location_time, " +
	        " l.checkout_location AS checkout_location, " +
	        " l.checkout_location_date AS checkout_location_date, " +
	        " l.checkout_location_latitude AS checkout_location_latitude, " +
	        " l.checkout_location_longitude AS checkout_location_longitude, " +
	        " l.checkout_location_time AS checkout_location_time, " +
	        " l.createddate AS createddate, " +
	        " l.createdtime AS createdtime, " +
	        " r.trade_name AS rname, " +
	        " d.trade_name AS dname " +
	        "FROM location l " +
	        "LEFT JOIN retailer r ON l.retailer_id = r.id " +
	        "LEFT JOIN distributor d ON l.distributor_id = d.id " +
	        "WHERE l.staff_id = ?1 " +
	        "AND STR_TO_DATE(l.checkin_location_date, '%d-%m-%Y') = STR_TO_DATE(?2, '%Y-%m-%d')",
	        nativeQuery = true)
		List<LocationIndex> Locationspecificbystaffanddate(
		        @Param("staffid") int staffid,
		        @Param("currentdate") String currentdate
		);



	
	@Query(value = "SELECT s.id AS staffid,s.staff_name,a.dayintime,a.dayouttime,l.checkin_location_date,date(str_to_date(l.checkin_location_date,'%d-%m-%Y')) as locationdate,date(a.dayin) as dayin\r\n"
			+ "FROM staff s\r\n"
			+ "LEFT JOIN attendence a ON s.id = a.staffuserid\r\n"
			+ "LEFT JOIN location l ON s.id = l.staff_id\r\n"
			+ "WHERE s.id = :staffid AND date(a.dayin) BETWEEN :fromdate AND :todate \r\n"
			+ "AND  date(str_to_date(l.checkin_location_date,'%d-%m-%Y')) BETWEEN :fromdate AND :todate", nativeQuery = true)
	List<IndexStaffCheckinAndVisitData> getStaffCheckinAndVisitData(int staffid,String fromdate,String todate);

	
	@Query(value = "select l.checkin_location_date,l.checkout_location_date\r\n"
			+ "FROM staff s\r\n"
			+ "LEFT JOIN location l ON s.id = l.staff_id WHERE s.id = :staffid AND\r\n"
			+ "date(str_to_date(l.checkin_location_date,'%d-%m-%Y')) BETWEEN :fromdate AND :todate", nativeQuery = true)
	List<IndexVisitData> getStaffAndVisitById(int staffid,String fromdate,String todate);
	
//	------
//	select * from location where sid=:sid and dayin=:dayin order by dayout desc limit 1
	@Query(value = "select * from location where staff_id=:sid and checkin_location_date=:dayin order by checkin_location_date desc limit 1",nativeQuery = true)
	public Location getCurrentLocationByStaffId(int sid,String dayin);
	
//	------------
//	@Query(value = "select * from location where staff_id=:sid and checkin_location_date=:dayin order by checkin_location_date desc limit 1",nativeQuery = true)
//	public Location getCurrentLocationByStaffId(int sid,String dayin);
//	
	@Query(value = "SELECT a.dayintime,a.dayouttime,a.checkin_location_latitude,a.checkin_location_longitude,a.checkout_location_latitude,a.checkout_location_longitude,s.id\r\n"
			+ "FROM attendence a\r\n"
			+ "LEFT JOIN staff s ON a.staffuserid = s.id\r\n"
			+ "WHERE a.staffuserid =:staffid AND a.dayin =:currentdate", nativeQuery = true)
	List<LocationWithAttendanceIndex> Locationdatabystaffanddate1(int staffid, String currentdate);
	
//	+ "   or a.mrp Like CONCAT('%', :search, '%')" 
//
	@Query(value = "SELECT * FROM location " +
            "WHERE staff_id = :staffid " +
            "AND checkin_location_date LIKE CONCAT(:currentdate, '%') " +
            "ORDER BY checkin_location_date DESC LIMIT 1",
    nativeQuery = true)
Optional<Location> findTopLocationByStaffAndDate(@Param("staffid") int staffid,
                                              @Param("currentdate") String currentdate);
	
	
}
