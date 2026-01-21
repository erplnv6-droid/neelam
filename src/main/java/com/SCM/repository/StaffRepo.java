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

import com.SCM.ExportDto.ExportStaff;
import com.SCM.IndexDto.IndexStaff;
import com.SCM.IndexDto.IndexStaffByStates;
import com.SCM.IndexDto.IndexStaffDto;
import com.SCM.IndexDto.IndexStatesByZones;
import com.SCM.model.Staff;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long> {

	Boolean existsByEmail(String email);

	Optional<Staff> findByEmail(String email);

	@Query(value = "SELECT * FROM staff s where s.ase_id = ?1", nativeQuery = true)
	List<Staff> getStaffByAseId(int aseId);

	@Query(value = "SELECT * FROM staff s where s.asm_id = ?1", nativeQuery = true)
	List<Staff> getStaffByAsmId(int asmId);

	@Query(value = "SELECT * FROM staff s where s.id = ?1", nativeQuery = true)
	List<Staff> getASMByAsmId(int id);

	@Query(value = "SELECT * FROM staff s where s.id = ?1", nativeQuery = true)
	List<Staff> getASEByAseId(int id);

	@Query(value = "SELECT * FROM staff s where s.rsm_id = ?1", nativeQuery = true)
	List<Staff> getStaffByRsmId(int rsmId);

	@Query(value = "SELECT * FROM staff s where s.nsm_id = ?1", nativeQuery = true)
	List<Staff> getStaffByNsmId(int nsmId);

	@Query(value = "select * from staff s where s.zonesid = ?1", nativeQuery = true)
	public List<Staff> getStaffByZoneId(int zoneId);

	@Query(value = "select s.id,s.staff_name,r.name as rolename\r\n" 
	         + "FROM staff s\r\n"
			+ "LEFT JOIN staff_roles sr ON s.id = sr.staff_id\r\n"
	        + "LEFT JOIN roles r ON sr.role_id = r.id\r\n"
			+ "WHERE s.state_zone_id = ?1", nativeQuery = true)
	public List<IndexStaffDto> getStaffBystateId(int stateId);

	@Modifying
	@Transactional
	@Query(value = "update Staff s set s.location = :location where s.id = :id")
	public void updatestafflocation(long id, String location);

	@Query(value = "SELECT * FROM staff s " 
	        + "JOIN zone z on z.id = s.zone_id "
			+ "JOIN staff_roles sr on sr.staff_id = s.id " 
			+ "where z.id = ?1 AND sr.role_id = 6", nativeQuery = true)
	List<Staff> getRsmByZoneId(int zoneId);

	@Query(value = "SELECT * FROM staff s " 
	        + "JOIN zone z on z.id = s.zone_id "
			+ "JOIN staff_roles sr on sr.staff_id = s.id " 
			+ "where z.id = ?1 AND sr.role_id = 5", nativeQuery = true)
	List<Staff> getAsmByZoneId(int zoneId);

	@Query(value = "SELECT * FROM staff s " 
	        + "JOIN zone z on z.id = s.zone_id "
			+ "JOIN staff_roles sr on sr.staff_id = s.id " 
			+ "where z.id = ?1 AND sr.role_id = 4", nativeQuery = true)
	List<Staff> getAseByZoneId(int zoneId);

	@Query(value = "select * from staff s " 
	        + "JOIN staff_roles sr on sr.staff_id = s.id "
			+ "where s.rsm_id = ?1 and sr.role_id = 5", nativeQuery = true)
	public List<Staff> getStaffASMBYRSMId(int rsmId);

	@Query(value = "select * from staff s " 
	        + "JOIN staff_roles sr on sr.staff_id = s.id "
			+ "where s.rsm_id = ?1 and sr.role_id = 4", nativeQuery = true) // will rectify later
	public List<Staff> getStaffASEBYRSMId(int rsmId);

	@Query(value = "select * from staff s " 
	        + "JOIN staff_roles sr on sr.staff_id = s.id "
			+ "where s.asm_id = ?1 and sr.role_id = 4", nativeQuery = true)
	public List<Staff> getStaffASEBYASMId(int asmId);

	@Query(value = "select * from staff s " 
	        + "JOIN staff_roles sr on sr.staff_id = s.id "
			+ "where sr.role_id = 7", nativeQuery = true)
	public List<Staff> getStaffNsm();

	@Query(value = "SELECT * FROM staff s where s.id = ?1", nativeQuery = true)
	Staff getAllStaffData(int staffid);

	// ---------- index staff

	@Query(value = " SELECT s.id,s.staff_name,s.mobile_number,s.email,s.area,r.name as rolename,DATE_FORMAT(s.doj,'%d-%m-%Y') AS doj,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime"
			+ "    FROM staff s" 
			+ "    JOIN staff_roles sr ON s.id = sr.staff_id"
			+ "    JOIN roles r ON sr.role_id = r.id", nativeQuery = true)
	public List<IndexStaff> indexstaff(Pageable p);

//        find all staff for rsm 
	
	@Query(value = "SELECT s.id,s.staff_name,s.mobile_number,s.email,s.area,r.name as rolename,s.doj,s.createddate,s.createdtime\r\n"
			+ "FROM staff s\r\n"
			+ "LEFT JOIN staff_roles sr ON s.id = sr.staff_id\r\n"
			+ "LEFT JOIN roles r ON sr.role_id = r.id \r\n"
			+ "LEFT JOIN multiple_staff mts ON s.id = mts.staff_id\r\n"
			+ "WHERE s.rsm_id=:rsmid OR mts.rsmid =:rsmid", nativeQuery = true)
	public List<IndexStaff> indexstaffByRsmId(int rsmid, Pageable p);

	
	@Query(value = "SELECT s.id,s.staff_name,s.mobile_number,s.email,s.area,r.name as rolename,s.doj,s.createddate,s.createdtime\r\n"
			+ "FROM staff s\r\n"
			+ "LEFT JOIN staff_roles sr ON s.id = sr.staff_id\r\n"
			+ "LEFT JOIN roles r ON sr.role_id = r.id \r\n"
			+ "LEFT JOIN multiple_staff mts ON s.id = mts.staff_id\r\n"
			+ "WHERE s.rsm_id=:rsmid OR mts.rsmid =:rsmid", nativeQuery = true)
	public List<IndexStaff> indexstaffByRsmId(int rsmid);

//       end find all staff for rsm 

//        search all staff 

	@Query(value = "select s.id,s.staff_name,s.mobile_number,s.email,s.area,r.name as rolename,s.doj,s.createddate,s.createdtime"
			+ "   FROM staff s" 
			+ "  left JOIN staff_roles sr ON s.id = sr.staff_id"
			+ "  left JOIN roles r ON sr.role_id = r.id " 
			+ "   where s.rsm_id=:rsmid and "
			+ "   REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))" 
			+ "   or s.area Like CONCAT('%', :search, '%')"
			+ "   or s.email Like CONCAT('%', :search, '%')" 
			+ "   or s.id Like CONCAT('%', :search, '%')"
			+ "   or s.mobile_number Like CONCAT('%', :search,'%')"
			+ "   or r.name Like CONCAT('%', :search,'%')", nativeQuery = true)
	List<IndexStaff> SearchByStaffRsmId(int rsmid, String search, Pageable p);

//        end search all staff

//     ======================

//      find all staff for asm 
	
	@Query(value = " SELECT s.id,s.staff_name,s.mobile_number,s.email,s.area,r.name as rolename,s.doj,s.createddate,s.createdtime"
			+ "  FROM staff s" 
			+ " LEFT JOIN staff_roles sr ON s.id = sr.staff_id"
			+ " LEFT JOIN roles r ON sr.role_id = r.id"
			+ " LEFT JOIN multiple_staff mts ON s.id = mts.staff_id" 
			+ "	where s.asm_id=:asmid OR mts.asmid =:asmid", nativeQuery = true)
	public List<IndexStaff> indexstaffByAsmId(int asmid, Pageable p);

	
	@Query(value = " SELECT s.id,s.staff_name,s.mobile_number,s.email,s.area,r.name as rolename,s.doj,s.createddate,s.createdtime"
			+ "  FROM staff s" 
			+ " LEFT JOIN staff_roles sr ON s.id = sr.staff_id"
			+ " LEFT JOIN roles r ON sr.role_id = r.id"
			+ " LEFT JOIN multiple_staff mts ON s.id = mts.staff_id" 
			+ "	where s.asm_id=:asmid OR mts.asmid =:asmid", nativeQuery = true)
	public List<IndexStaff> indexstaffByAsmId(int asmid);

//     end find all staff for rsm 

//      search all staff 

	@Query(value = "select s.id,s.staff_name,s.mobile_number,s.email,s.area,r.name as rolename,s.doj,s.createddate,s.createdtime"
			+ "   FROM staff s" 
			+ "  left JOIN staff_roles sr ON s.id = sr.staff_id"
			+ "  left JOIN roles r ON sr.role_id = r.id " 
			+ "   where s.asm_id=:asmid and "
			+ "   REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))" 
			+ "   or s.area Like CONCAT('%', :search, '%')"
			+ "   or s.email Like CONCAT('%', :search, '%')" 
			+ "   or s.id Like CONCAT('%', :search, '%')"
			+ "   or s.mobile_number Like CONCAT('%', :search,'%')"
			+ "   or r.name Like CONCAT('%', :search,'%')", nativeQuery = true)
	List<IndexStaff> SearchByStaffAsmId(int asmid, String search, Pageable p);

//      end search all staff
//        ====================

//@Query(value = "SELECT s.id,s.staff_name,s.mobile_number,s.email,s.area ,r.name FROM staff s join s.roles r",nativeQuery = true)
//public List<IndexStaff> indexstaff(Pageable p);

	@Query(value = "select s.id,s.staff_name,s.mobile_number,s.email,s.area,r.name as rolename,s.doj,s.createddate,s.createdtime"
			+ "   FROM staff s" 
			+ "  left JOIN staff_roles sr ON s.id = sr.staff_id"
			+ "  left JOIN roles r ON sr.role_id = r.id " 
			+ "   where REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "   or s.area Like CONCAT('%', :search, '%')" 
			+ "   or s.email Like CONCAT('%', :search, '%')"
			+ "   or s.id Like CONCAT('%', :search, '%')" 
			+ "   or s.mobile_number Like CONCAT('%', :search,'%')"
			+ "   or r.name Like CONCAT('%', :search,'%')", nativeQuery = true)
	List<IndexStaff> SearchByStaff(String search, Pageable p);

	
	@Query(value = "SELECT s.id, s.doj,s.address,s.mobile_number,s.email,s.gender,s.salary,s.area,s.date_of_birth,s.blood_group,s.father_name, s.spouse_name, s.bank_detail, s.account_number, s.ifsc_code, s.bank_name, s.branch_name, s.pan_number, s.aadhar_number, s.date_of_anniversary, s.location, s.password, s.staff_name, group_concat(distinct s1.staff_name) AS ASMstaffname, group_concat(distinct s2.staff_name) AS RSMstaffname, group_concat(distinct s3.staff_name) AS NSMstaffname, sz.state_name,s.createddate,s.createdtime,s.createbyname,s.updatedbyname,s.updateddate,s.updatedtime\r\n"
			+ "			FROM staff s\r\n"
			+ "			LEFT JOIN state_zone sz ON s.state_zone_id = sz.id\r\n"
			+ "            left join multiple_staff ms on s.id=ms.staff_id\r\n"
			+ "            LEFT JOIN staff s_ase ON ms.aseid = s_ase.id\r\n"
			+ "			LEFT JOIN staff s1 ON ms.asmid = s1.id\r\n"
			+ "			LEFT JOIN staff s2 ON ms.rsmid = s2.id\r\n"
			+ "			LEFT JOIN staff s3 ON ms.nsmid = s3.id\r\n"
			+ "            group by s.id", nativeQuery = true)
	List<ExportStaff> ExportStaff();

	
	@Query(value = "select * from staff s", nativeQuery = true)
	List<Staff> findallcount();

	
	@Query(value = "select * from staff s", nativeQuery = true)
	List<Staff> findall(Pageable p);

	
	List<Staff> findAllByIdIn(List<Integer> list);
	
	
//	=========================================================================
	
	
	@Query(value = "SELECT sz.id, sz.state_name as statename,z.zone_name as zonename\r\n"
			+ "FROM state_zone sz\r\n"
			+ "LEFT JOIN zone z ON sz.zone_id= z.id\r\n"
			+ "WHERE sz.zone_id IN (?1)",nativeQuery = true)
	List<IndexStatesByZones> FetchStatesByZoneId(List<Integer> zoneid);
	
	
//	 =================== get staff ASE By Multiple States AND ASM,RSM
	
	@Query(value = "SELECT s.id as staffid,stz.id,stz.state_name as statename,s.staff_name as staffname \r\n"
			+ "FROM staff s\r\n"
			+ "LEFT JOIN staff_state ss ON s.id = ss.staff_id\r\n"
			+ "LEFT JOIN state_zone stz ON ss.statezone_id = stz.id\r\n"
			+ "LEFT JOIN staff_roles sr on sr.staff_id = s.id\r\n"
			+ "WHERE (ss.statezone_id IN (:stateid) OR s.state_zone_id IN (:stateid)) AND sr.role_id = 4",nativeQuery = true)
	List<IndexStatesByZones> getASEByMultipleStates(List<Integer> stateid);
	
	
	@Query(value = "SELECT s.id as staffid,s.staff_name as staffname\r\n"
			+ "FROM staff s\r\n"
			+ "LEFT JOIN staff_roles sr on sr.staff_id = s.id\r\n"
			+ "LEFT JOIN multiple_staff mts ON s.id = mts.staff_id\r\n"
			+ "WHERE (mts.rsmid IN (:rsmid) OR s.rsm_id IN (:rsmid) OR s.id = 166) AND sr.role_id = 4",nativeQuery = true)
	List<IndexStatesByZones> getASEByMultipleRSMId(List<Integer> rsmid);
	
	
	@Query(value = "SELECT s.id as staffid,s.staff_name as staffname\r\n"
			+ "FROM staff s\r\n"
			+ "LEFT JOIN staff_roles sr ON sr.staff_id = s.id\r\n"
			+ "LEFT JOIN multiple_staff mts ON s.id = mts.staff_id\r\n"
			+ "WHERE (mts.asmid IN (:asmid) OR s.asm_id IN (:asmid) OR s.id = 166) AND sr.role_id = 4",nativeQuery = true)
	List<IndexStatesByZones> getASEByMultipleASMId(List<Integer> asmid);
	

//	 =================== get staff ASM By Multiple States BY RSM
	
	@Query(value = "SELECT s.id as staffid,stz.id,stz.state_name as statename,s.staff_name as staffname\r\n"
			+ "FROM staff s\r\n"
			+ "LEFT JOIN staff_state ss ON s.id = ss.staff_id\r\n"
			+ "LEFT JOIN state_zone stz ON ss.statezone_id = stz.id OR s.state_zone_id = stz.id\r\n"
			+ "LEFT JOIN staff_roles sr on sr.staff_id = s.id\r\n"
			+ "WHERE (ss.statezone_id IN (:stateid) OR s.state_zone_id IN (:stateid)) AND sr.role_id = 5",nativeQuery = true)
	List<IndexStatesByZones> getASMByMultipleStates(List<Integer> stateid);
	
	
	@Query(value = "SELECT s.id as staffid,s.staff_name as staffname\r\n"
			+ "FROM staff s\r\n"
			+ "LEFT JOIN staff_roles sr on sr.staff_id = s.id\r\n"
			+ "LEFT JOIN multiple_staff mts ON s.id = mts.staff_id\r\n"
			+ "WHERE (mts.rsmid IN (:rsmid) OR s.rsm_id IN (:rsmid) OR s.id = 165) AND sr.role_id = 5",nativeQuery = true)
	List<IndexStatesByZones> getASMByMultipleRSMId(List<Integer> rsmid);
	

//	 =================== get staff RSM By Multiple States
	
	@Query(value = "SELECT s.id as staffid,stz.id,stz.state_name as statename,s.staff_name as staffname\r\n"
			+ "FROM staff s\r\n"
			+ "LEFT JOIN staff_state ss ON s.id = ss.staff_id\r\n"
			+ "LEFT JOIN state_zone stz ON ss.statezone_id = stz.id OR s.state_zone_id = stz.id\r\n"
			+ "LEFT JOIN staff_roles sr on sr.staff_id = s.id\r\n"
			+ "WHERE (ss.statezone_id IN (:stateid) OR s.state_zone_id IN (:stateid)) AND sr.role_id = 6",nativeQuery = true)
	List<IndexStatesByZones> getRSMByMultipleStates(List<Integer> stateid);
	
	
	
//	======================== get Staff By Multiple States
	
	@Query(value = "SELECT s.id,s.staff_name,r.name as rolename\r\n"
			+ " FROM staff s\r\n"
			+ " LEFT JOIN staff_state ss ON s.id = ss.staff_id\r\n"
			+ " LEFT JOIN state_zone stz ON ss.statezone_id = stz.id\r\n"
			+ " LEFT JOIN staff_roles sr on sr.staff_id = s.id\r\n"
			+ " LEFT JOIN roles r ON sr.role_id = r.id"
			+ " WHERE ss.statezone_id IN (:stateid) OR s.state_zone_id IN (:stateid)",nativeQuery = true)
	List<IndexStaffByStates> getStaffByMultipleStates(int stateid);
	
	
	
	
	

	
}
