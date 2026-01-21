package com.SCM.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.SCM.IndexDto.IndexMinutesOfMeeting;
import com.SCM.model.MinutesOfMeeting;

@Repository
public interface MinutesOfMeetingRepo extends JpaRepository<MinutesOfMeeting, Integer> {

	
	@Query(value = "SELECT DATE_FORMAT(mom.createddate,'%d-%m-%Y') AS createddate,mom.createdtime,mom.id,mom.meetingtitle,DATE_FORMAT(mom.meetingdate,'%d-%m-%Y') AS meetingdate,mom.createbyname,mom.updatedbyname,DATE_FORMAT(mom.updateddate,'%d-%m-%Y') AS updateddate,mom.updatedtime\r\n"
			+ "FROM minutes_of_meeting mom\r\n", nativeQuery = true)
	List<IndexMinutesOfMeeting> IndexMiutesOfMeeting(Pageable p);
	
	
	@Query(value = "SELECT DATE_FORMAT(mom.createddate,'%d-%m-%Y') AS createddate,mom.createdtime,mom.id,mom.meetingtitle,DATE_FORMAT(mom.meetingdate,'%d-%m-%Y') AS meetingdate,mom.createbyname,mom.updatedbyname,DATE_FORMAT(mom.updateddate,'%d-%m-%Y') AS updateddate,mom.updatedtime\r\n"
			+ "FROM minutes_of_meeting mom\r\n"
			+ "LEFT JOIN staff s on mom.id = s.minutesofmeeting_id\r\n"
			+ "LEFT JOIN staff_roles sr ON s.id = sr.staff_id\r\n"
			+ "LEFT JOIN roles r ON sr.role_id = r.id\r\n"
			+ "WHERE r.name = 'ROLE_RSM' AND s.id = ?1", nativeQuery = true)
	List<IndexMinutesOfMeeting> IndexMiutesOfMeetingByStaffRSM(Pageable p,int rsmid);
	
	
	@Query(value = "SELECT DATE_FORMAT(mom.createddate,'%d-%m-%Y') AS createddate,mom.createdtime,mom.id,mom.meetingtitle,DATE_FORMAT(mom.meetingdate,'%d-%m-%Y') AS meetingdate,mom.createbyname,mom.updatedbyname,DATE_FORMAT(mom.updateddate,'%d-%m-%Y') AS updateddate,mom.updatedtime\r\n"
			+ "FROM minutes_of_meeting mom\r\n"
			+ "LEFT JOIN staff s on mom.id = s.minutesofmeeting_id\r\n"
			+ "LEFT JOIN staff_roles sr ON s.id = sr.staff_id\r\n"
			+ "LEFT JOIN roles r ON sr.role_id = r.id\r\n"
			+ "WHERE r.name = 'ROLE_ASM' AND s.id = ?1", nativeQuery = true)
	List<IndexMinutesOfMeeting> IndexMiutesOfMeetingByStaffASM(Pageable p,int asmid);
	
	
	@Query(value = "SELECT DATE_FORMAT(mom.createddate,'%d-%m-%Y') AS createddate,mom.createdtime,mom.id,mom.meetingtitle,DATE_FORMAT(mom.meetingdate,'%d-%m-%Y') AS meetingdate,mom.createbyname,mom.updatedbyname,DATE_FORMAT(mom.updateddate,'%d-%m-%Y') AS updateddate,mom.updatedtime\r\n"
			+ "FROM minutes_of_meeting mom\r\n"
			+ "LEFT JOIN staff s on mom.id = s.minutesofmeeting_id\r\n"
			+ "LEFT JOIN staff_roles sr ON s.id = sr.staff_id\r\n"
			+ "LEFT JOIN roles r ON sr.role_id = r.id\r\n"
			+ "WHERE r.name = 'ROLE_ASE' AND s.id = ?1", nativeQuery = true)
	List<IndexMinutesOfMeeting> IndexMiutesOfMeetingByStaffASE(Pageable p,int aseid);
	
	
	@Query(value = "SELECT DATE_FORMAT(mom.createddate,'%d-%m-%Y') AS createddate,mom.createdtime,mom.id,mom.meetingtitle,DATE_FORMAT(mom.meetingdate,'%d-%m-%Y') AS meetingdate,mom.createbyname,mom.updatedbyname,DATE_FORMAT(mom.updateddate,'%d-%m-%Y') AS updateddate,mom.updatedtime\r\n"
			+ "FROM minutes_of_meeting mom\r\n"
			+ "LEFT JOIN retailer r on mom.id = r.minutesofmeeting_id\r\n"
			+ "LEFT JOIN retailer_role rr ON r.id = rr.retailer_id\r\n"
			+ "LEFT JOIN roles ro ON rr.role_id = ro.id\r\n"
			+ "WHERE ro.name = 'ROLE_RETAILER' AND r.id = ?1", nativeQuery = true)
	List<IndexMinutesOfMeeting> IndexMiutesOfMeetingByRetailer(Pageable p,int rid);

	
	@Query(value = "SELECT DATE_FORMAT(mom.createddate,'%d-%m-%Y') AS createddate,mom.createdtime,mom.id,mom.meetingtitle,DATE_FORMAT(mom.meetingdate,'%d-%m-%Y') AS meetingdate,mom.createbyname,mom.updatedbyname,DATE_FORMAT(mom.updateddate,'%d-%m-%Y') AS updateddate,mom.updatedtime\r\n"
			+ "FROM minutes_of_meeting mom\r\n"
			+ "LEFT JOIN distributor d on mom.id = d.minutesofmeeting_id\r\n"
			+ "LEFT JOIN distributor_roles dr ON d.id = dr.distributor_id\r\n"
			+ "LEFT JOIN roles r ON dr.role_id = r.id\r\n"
			+ "WHERE r.name = 'ROLE_DISTRIBUTOR' AND d.id = ?1",nativeQuery = true)
	List<IndexMinutesOfMeeting> IndexMinutesOfMeetingByDistributor(Pageable p,int did);
	
	
	@Query(value = "SELECT DATE_FORMAT(mom.createddate,'%d-%m-%Y') AS createddate,mom.createdtime,mom.id,mom.meetingtitle,DATE_FORMAT(mom.meetingdate,'%d-%m-%Y') AS meetingdate,mom.createbyname,mom.updatedbyname,DATE_FORMAT(mom.updateddate,'%d-%m-%Y') AS updateddate,mom.updatedtime\r\n"
			+ "FROM minutes_of_meeting mom\r\n"
			+ "LEFT JOIN supplier s on mom.id = s.minutesofmeeting_id\r\n"
			+ "LEFT JOIN supplier_roles sr ON s.id = sr.supplier_id\r\n"
			+ "LEFT JOIN roles r ON sr.role_id = r.id\r\n"
			+ "WHERE r.name = 'ROLE_SUPPLIER' AND s.id = ?1",nativeQuery = true)
	List<IndexMinutesOfMeeting> IndexMinutesOfMeetingBySupplier(Pageable p,int sid);
	
	
	@Query(value = "SELECT DATE_FORMAT(mom.createddate,'%d-%m-%Y') AS createddate,mom.createdtime,mom.id,mom.meetingtitle,DATE_FORMAT(mom.meetingdate,'%d-%m-%Y') AS meetingdate,mom.createbyname,mom.updatedbyname,DATE_FORMAT(mom.updateddate,'%d-%m-%Y') AS updateddate,mom.updatedtime\r\n"
			+ "FROM minutes_of_meeting mom\r\n"
			+ "LEFT JOIN supplier s on mom.id = s.minutesofmeeting_id\r\n"
			+ "LEFT JOIN supplier_roles sr ON s.id = sr.supplier_id\r\n"
			+ "LEFT JOIN roles r ON sr.role_id = r.id\r\n"
			+ "WHERE r.name = 'ROLE_SUPPLIER' AND s.id = ?1",nativeQuery = true)
	List<IndexMinutesOfMeeting> IndexMinutesOfMeetingBySupplier(int sid);
	
	
	
	@Query(value="SELECT DATE_FORMAT(mom.createddate,'%d-%m-%Y') AS createddate,mom.createdtime,mom.id,mom.meetingtitle,DATE_FORMAT(mom.meetingdate,'%d-%m-%Y') AS meetingdate,mom.createbyname,mom.updatedbyname,DATE_FORMAT(mom.updateddate,'%d-%m-%Y') AS updateddate,mom.updatedtime\r\n"
			+ "			FROM minutes_of_meeting mom\r\n"
			+ "			LEFT JOIN distributor d on mom.id = d.minutesofmeeting_id\r\n"
			+ "			LEFT JOIN distributor_roles dr ON d.id = dr.distributor_id\r\n"
			+ "			LEFT JOIN roles r ON dr.role_id = r.id\r\n"
			+ "            left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "            left join staff s on ds.rsmid=s.id\r\n"
			+ "			WHERE ds.rsmid=:rsmid",nativeQuery = true)
	List<IndexMinutesOfMeeting> IndexMinutesOfMeetingByRsm(Pageable p,int rsmid);
	
	
	
	
	
	@Query(value = "SELECT DATE_FORMAT(mom.createddate,'%d-%m-%Y') AS createddate,mom.createdtime,mom.id,mom.meetingtitle,DATE_FORMAT(mom.meetingdate,'%d-%m-%Y') AS meetingdate,mom.createbyname,mom.updatedbyname,DATE_FORMAT(mom.updateddate,'%d-%m-%Y') AS updateddate,mom.updatedtime" 
	        + "	FROM minutes_of_meeting mom"
	        + " WHERE mom.id Like CONCAT('%', :search, '%')"
			+ " OR mom.id Like CONCAT('%', :search, '%')" 
	        + " OR mom.meetingdate Like CONCAT('%', :search, '%')"
			+ " OR mom.meetingtitle Like CONCAT('%', :search, '%')"
			+ " OR mom.createddate Like CONCAT('%', :search, '%')"
			+ " OR mom.createbyname Like CONCAT('%', :search, '%')"
			+ " OR mom.updatedbyname Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<IndexMinutesOfMeeting> SearchMinutesOfMeeting(String search, Pageable p);
	
	
	
	
	
	
	
	@Query(value = "SELECT DATE_FORMAT(mom.createddate,'%d-%m-%Y') AS createddate,mom.createdtime,mom.id,mom.meetingtitle,DATE_FORMAT(mom.meetingdate,'%d-%m-%Y') AS meetingdate,mom.createbyname,mom.updatedbyname,DATE_FORMAT(mom.updateddate,'%d-%m-%Y') AS updateddate,mom.updatedtime" 
	        + "	FROM minutes_of_meeting mom"
	    	+ "LEFT JOIN supplier s on mom.id = s.minutesofmeeting_id\r\n"
	        + " WHERE (mom.id Like CONCAT('%', :search, '%')"
			+ " OR mom.id Like CONCAT('%', :search, '%')" 
	        + " OR mom.meetingdate Like CONCAT('%', :search, '%')"
			+ " OR mom.meetingtitle Like CONCAT('%', :search, '%')"
			+ " OR mom.createddate Like CONCAT('%', :search, '%')"
			+ " OR mom.createbyname Like CONCAT('%', :search, '%')"
			+ " OR mom.updatedbyname Like CONCAT('%', :search, '%')) " 
			+"and s.id=:sid ", nativeQuery = true)
	List<IndexMinutesOfMeeting> SearchMinutesOfMeeting(String search, Pageable p,int sid);

	
	Optional<MinutesOfMeeting> findByDocname(String filename);


	@Query(value = "select mom.doclocation from minutes_of_meeting mom where id = ?1", nativeQuery = true)
	String getImage(int id);

}
