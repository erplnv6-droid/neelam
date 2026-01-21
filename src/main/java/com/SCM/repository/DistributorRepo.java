package com.SCM.repository;

import com.SCM.ExportDto.ExportDistributor;
import com.SCM.IndexDto.DistDto;
import com.SCM.IndexDto.IndexDistributor;
import com.SCM.model.Distributor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Repository
public interface DistributorRepo extends JpaRepository<Distributor, Integer> {

	
	List<Distributor> findByPinCode(String pincode);
	
	// ase asm rsm nsm zone id

	@Query(value = "select * from distributor d where d.aseid = ?1", nativeQuery = true)
	public List<Distributor> getDistributorByAseID(int aseId);

	@Query(value = "select * from distributor d where d.asmid = ?1", nativeQuery = true)
	public List<Distributor> getDistributorByAsmID(int asmId);

	@Query(value = "select * from distributor d where d.rsmid = ?1", nativeQuery = true)
	public List<Distributor> getDistributorByRsmID(int rsmId);

	@Query(value = "select * from distributor d where d.nsmid = ?1", nativeQuery = true)
	public List<Distributor> getDistributorByNsmID(int nsmId);

	@Query(value = "select * from distributor d where d.zonesid = ?1", nativeQuery = true)
	public List<Distributor> getDistributorByZoneId(int zoneId);

	@Query(value = "select * from distributor d where d.stateid = ?1", nativeQuery = true)
	public List<Distributor> getDistributorBystateId(int stateId);

	@Query(value = "select * from distributor d where d.id = ?1", nativeQuery = true)
	public List<Distributor> getSingleDistributorById(int id);

	Optional<Distributor> findByPerEmail(String email);

	Boolean existsByPerEmail(String email);

	
	// -------- Distributor Index
	
//	@Query(value = "SELECT d.id,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude,sz.state_name,z.zone_name,GROUP_CONCAT(s.staff_name) as asestaffname"
//			+ " from distributor d "
//			+ " left join state_zone sz on d.stateid = sz.id"
//			+ " left join zone z on d.zonesid = z.id"
//			+ " LEFT join staff s on d.aseid = s.id "
//			+ " ",nativeQuery = true)
//	List<IndexDistributor> indexDistributor(Pageable p);
	
	
	@Query(value = "SELECT d.id, \r\n"
			+ "       d.city, \r\n"
			+ "       d.gst_number, \r\n"
			+ "       DATE_FORMAT(d.createddate, '%d-%m-%Y') AS createddate, \r\n"
			+ "       d.createbyname, \r\n"
			+ "       d.updatedbyname, \r\n"
			+ "       d.createdtime, \r\n"
			+ "       d.trade_name, \r\n"
			+ "       d.transporter_name, \r\n"
			+ "       DATE_FORMAT(d.updateddate, '%d-%m-%Y') AS updateddate, \r\n"
			+ "       d.updatedtime, \r\n"
			+ "       d.latitude, \r\n"
			+ "       d.longitude, \r\n"
			+ "       sz.state_name, \r\n"
			+ "       z.zone_name, \r\n"
			+ "       GROUP_CONCAT(s.staff_name) AS asestaffnames\r\n"
			+ " FROM distributor d\r\n"
			+ "LEFT JOIN state_zone sz ON d.stateid = sz.id\r\n"
			+ "LEFT JOIN zone z ON d.zonesid = z.id\r\n"
			+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
			+ "LEFT JOIN staff s ON ds.aseid = s.id \r\n"
			+ "GROUP BY d.id, \r\n"
			+ "         d.city, \r\n"
			+ "         d.gst_number, \r\n"
			+ "         d.createddate, \r\n"
			+ "         d.createbyname, \r\n"
			+ "         d.updatedbyname, \r\n"
			+ "         d.createdtime, \r\n"
			+ "         d.trade_name, \r\n"
			+ "         d.transporter_name, \r\n"
			+ "         d.updateddate, \r\n"
			+ "         d.updatedtime, \r\n"
			+ "         d.latitude, \r\n"
			+ "         d.longitude, \r\n"
			+ "         sz.state_name, \r\n"
			+ "         z.zone_name",nativeQuery = true)
	List<IndexDistributor> indexDistributor(Pageable p);
    
		
//	@Query(value = "SELECT d.id,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude,sz.state_name,z.zone_name,s.staff_name as asestaffname"
//			+ "    FROM distributor d"
//			+ "    left join state_zone sz on d.stateid = sz.id "
//			+ "    left join zone z on d.zonesid = z.id "
//			+ "	   LEFT join staff s on d.aseid = s.id "
//			+ " where REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
//			+ "     or d.transporter_name Like CONCAT('%', :search, '%')" 
//			+ "     or sz.state_name Like CONCAT('%', :search, '%')" 
//			+ "     or z.zone_name Like CONCAT('%', :search, '%')" 
//			+ "     or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))" 
//			+ "     or d.id Like CONCAT('%', :search, '%')"
//			+ "     or d.gst_number Like CONCAT('%', :search, '%')"
//			+ "     or d.createbyname Like CONCAT('%', :search, '%')"
//			+ "     or d.updatedbyname Like CONCAT('%', :search, '%')", nativeQuery = true)
//	List<IndexDistributor> SearchByDistributor(String search, Pageable p);
//	
	
	@Query(value = "SELECT d.id, \r\n"
            + "       d.city, \r\n"
            + "       d.gst_number, \r\n"
            + "       DATE_FORMAT(d.createddate, '%d-%m-%Y') AS createddate, \r\n"
            + "       d.createbyname, \r\n"
            + "       d.updatedbyname, \r\n"
            + "       d.createdtime, \r\n"
            + "       d.trade_name, \r\n"
            + "       d.transporter_name, \r\n"
            + "       DATE_FORMAT(d.updateddate, '%d-%m-%Y') AS updateddate, \r\n"
            + "       d.updatedtime, \r\n"
            + "       d.latitude, \r\n"
            + "       d.longitude, \r\n"
            + "       sz.state_name, \r\n"
            + "       z.zone_name, \r\n"
            + "       GROUP_CONCAT(s.staff_name) AS asestaffnames\r\n"
            + " FROM distributor d\r\n"
            + "LEFT JOIN state_zone sz ON d.stateid = sz.id\r\n"
            + "LEFT JOIN zone z ON d.zonesid = z.id\r\n"
            + "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
            + "LEFT JOIN staff s ON ds.aseid = s.id \r\n"
            + "WHERE REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))\r\n"
            + "  OR d.id LIKE CONCAT('%', :search, '%') \r\n"
            + "     OR d.transporter_name LIKE CONCAT('%', :search, '%') \r\n"
            + "     OR sz.state_name LIKE CONCAT('%', :search, '%') \r\n"
            + "     OR z.zone_name LIKE CONCAT('%', :search, '%') \r\n"
            + "     OR REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')) \r\n"
            + "     OR d.id LIKE CONCAT('%', :search, '%')\r\n"
            + "     OR d.gst_number LIKE CONCAT('%', :search, '%')\r\n"
            + "     OR d.createbyname LIKE CONCAT('%', :search, '%')\r\n"
            + "     OR d.updatedbyname LIKE CONCAT('%', :search, '%')\r\n"
            + " or CONCAT(s.staff_name) Like CONCAT('%', :search, '%')\r\n"
            + "GROUP BY d.id, \r\n"
            + "         d.city, \r\n"
            + "         d.gst_number, \r\n"
            + "         d.createddate, \r\n"
            + "         d.createbyname, \r\n"
            + "         d.updatedbyname, \r\n"
            + "         d.createdtime, \r\n"
            + "         d.trade_name, \r\n"
            + "         d.transporter_name, \r\n"
            + "         d.updateddate, \r\n"
            + "         d.updatedtime, \r\n"
            + "         d.latitude, \r\n"
            + "         d.longitude, \r\n"
            + "         sz.state_name, \r\n"
            + "         z.zone_name", 
            nativeQuery = true)
List<IndexDistributor> SearchByDistributor(String search, Pageable p);
	
	
	@Query(value = "SELECT d.id, \r\n"
			+ "       d.city, \r\n"
			+ "       d.gst_number, \r\n"
			+ "       DATE_FORMAT(d.createddate, '%d-%m-%Y') AS createddate, \r\n"
			+ "       d.createbyname, \r\n"
			+ "       d.updatedbyname, \r\n"
			+ "       d.createdtime, \r\n"
			+ "       d.trade_name, \r\n"
			+ "       d.transporter_name, \r\n"
			+ "       DATE_FORMAT(d.updateddate, '%d-%m-%Y') AS updateddate, \r\n"
			+ "       d.updatedtime, \r\n"
			+ "       d.latitude, \r\n"
			+ "       d.longitude, \r\n"
			+ "       sz.state_name, \r\n"
			+ "       z.zone_name, \r\n"
			+ "       GROUP_CONCAT(s.staff_name) AS asestaffnames\r\n"
			+ " FROM distributor d\r\n"
			+ "LEFT JOIN state_zone sz ON d.stateid = sz.id\r\n"
			+ "LEFT JOIN zone z ON d.zonesid = z.id\r\n"
			+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
			+ "LEFT JOIN staff s ON ds.aseid = s.id \r\n"
			+ "WHERE d.id=:distid\r\n"
			+ "GROUP BY d.id, \r\n"
			+ "         d.city, \r\n"
			+ "         d.gst_number, \r\n"
			+ "         d.createddate, \r\n"
			+ "         d.createbyname, \r\n"
			+ "         d.updatedbyname, \r\n"
			+ "         d.createdtime, \r\n"
			+ "         d.trade_name, \r\n"
			+ "         d.transporter_name, \r\n"
			+ "         d.updateddate, \r\n"
			+ "         d.updatedtime, \r\n"
			+ "         d.latitude, \r\n"
			+ "         d.longitude, \r\n"
			+ "         sz.state_name, \r\n"
			+ "         z.zone_name",nativeQuery = true)
	List<IndexDistributor> indexDistributorDistributor(int distid,Pageable p);
	
	
	@Query(value = "SELECT d.id, \r\n"
            + "       d.city, \r\n"
            + "       d.gst_number, \r\n"
            + "       DATE_FORMAT(d.createddate, '%d-%m-%Y') AS createddate, \r\n"
            + "       d.createbyname, \r\n"
            + "       d.updatedbyname, \r\n"
            + "       d.createdtime, \r\n"
            + "       d.trade_name, \r\n"
            + "       d.transporter_name, \r\n"
            + "       DATE_FORMAT(d.updateddate, '%d-%m-%Y') AS updateddate, \r\n"
            + "       d.updatedtime, \r\n"
            + "       d.latitude, \r\n"
            + "       d.longitude, \r\n"
            + "       sz.state_name, \r\n"
            + "       z.zone_name, \r\n"
            + "       GROUP_CONCAT(s.staff_name) AS asestaffnames\r\n"
            + " FROM distributor d\r\n"
            + "LEFT JOIN state_zone sz ON d.stateid = sz.id\r\n"
            + "LEFT JOIN zone z ON d.zonesid = z.id\r\n"
            + "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
            + "LEFT JOIN staff s ON ds.aseid = s.id \r\n"
            + "WHERE d.id=:distid and REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))\r\n"
            + "  OR d.id LIKE CONCAT('%', :search, '%') \r\n"
            + "     OR d.transporter_name LIKE CONCAT('%', :search, '%') \r\n"
            + "     OR sz.state_name LIKE CONCAT('%', :search, '%') \r\n"
            + "     OR z.zone_name LIKE CONCAT('%', :search, '%') \r\n"
            + "     OR REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')) \r\n"
            + "     OR d.id LIKE CONCAT('%', :search, '%')\r\n"
            + "     OR d.gst_number LIKE CONCAT('%', :search, '%')\r\n"
            + "     OR d.createbyname LIKE CONCAT('%', :search, '%')\r\n"
            + "     OR d.updatedbyname LIKE CONCAT('%', :search, '%')\r\n"
            + " or CONCAT(s.staff_name) Like CONCAT('%', :search, '%')\r\n"
            + "GROUP BY d.id, \r\n"
            + "         d.city, \r\n"
            + "         d.gst_number, \r\n"
            + "         d.createddate, \r\n"
            + "         d.createbyname, \r\n"
            + "         d.updatedbyname, \r\n"
            + "         d.createdtime, \r\n"
            + "         d.trade_name, \r\n"
            + "         d.transporter_name, \r\n"
            + "         d.updateddate, \r\n"
            + "         d.updatedtime, \r\n"
            + "         d.latitude, \r\n"
            + "         d.longitude, \r\n"
            + "         sz.state_name, \r\n"
            + "         z.zone_name", 
            nativeQuery = true)
List<IndexDistributor> SearchByDistributorId(int distid, String search, Pageable p);
    

	
	
//	get distributor by rsm id
	
	@Query(value = "SELECT d.id,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude,sz.state_name,z.zone_name, GROUP_CONCAT(s.staff_name) AS asestaffnames"
			+ " from distributor d "
			+ " left join state_zone sz on d.stateid = sz.id"
			+ " left join zone z on d.zonesid = z.id"
		+ " LEFT JOIN distributor_to_staff dts on d.id = dts.distributor_id"
		+ " LEFT JOIN staff s ON dts.aseid = s.id"
			+ " where dts.rsmid=:rsmid or d.rsmid=:rsmid"
			+ " GROUP BY d.id",nativeQuery = true)
	List<IndexDistributor> indexDistributorByrsmId(int rsmid,Pageable p);


	@Query(value = "SELECT d.id,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude,sz.state_name,z.zone_name, GROUP_CONCAT(s.staff_name) AS asestaffnames"
			+ " from distributor d "
			+ " left join state_zone sz on d.stateid = sz.id"
			+ " left join zone z on d.zonesid = z.id"
	        + " LEFT JOIN distributor_to_staff dts on d.id = dts.distributor_id"
			+ " LEFT JOIN staff s ON dts.aseid = s.id"
			+ " where dts.rsmid=:rsmid or d.rsmid=:rsmid"
			+ " GROUP BY d.id",nativeQuery = true)
	List<IndexDistributor> indexDistributorByrsmId(int rsmid);
	
	
	@Query(value = "select d.id,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude,sz.state_name,z.zone_name,GROUP_CONCAT(s.staff_name) AS asestaffnames"
			+ "    FROM distributor d"
			+ "    left join state_zone sz on d.stateid = sz.id "
			+ "    left join zone z on d.zonesid = z.id "
			+ "	   LEFT join staff s on d.aseid = s.id "
			+ "    LEFT JOIN distributor_to_staff dts on d.id = dts.distributor_id"
			+ "     where dts.rsmid=:rsmid or d.rsmid=:rsmid and"
			+" LOWER(REGEXP_REPLACE(COALESCE(d.trade_name), '[^a-z0-9]', ''))\r\n" 
			+" LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ "     or d.transporter_name Like CONCAT('%', :search, '%')" 
			+ "     or sz.state_name Like CONCAT('%', :search, '%')" 
			+ "     or z.zone_name Like CONCAT('%', :search, '%')" 
			+ "     or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))" 
			+ "     or d.id Like CONCAT('%', :search, '%')"
			+ "     or d.gst_number Like CONCAT('%', :search, '%')"
			+ " GROUP BY d.id", nativeQuery = true)
	List<IndexDistributor> SearchDistributorByRsmId(int rsmid,String search, Pageable p);
	
	
//	get distributor by rsm id end
	
	
	
//	get distributor by asmid 

	@Query(value = "SELECT d.id,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude,sz.state_name,z.zone_name,s.staff_name as asestaffname"
			+ " from distributor d "
			+ " left join state_zone sz on d.stateid = sz.id"
			+ " left join zone z on d.zonesid = z.id"
			+ " LEFT join staff s on d.aseid = s.id "
			+ " LEFT JOIN distributor_to_staff dts on d.id = dts.distributor_id"
			+ " where dts.asmid=:asmid or d.asmid=:asmid",nativeQuery = true)
	List<IndexDistributor> indexDistributorByasmId(int asmid,Pageable p);
	
	
	@Query(value = "SELECT d.id,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude,sz.state_name,z.zone_name,s.staff_name as asestaffname"
			+ " from distributor d "
			+ " left join state_zone sz on d.stateid = sz.id"
			+ " left join zone z on d.zonesid = z.id"
			+ " LEFT join staff s on d.aseid = s.id "
			+ "  LEFT JOIN distributor_to_staff dts on d.id = dts.distributor_id"
			+ " where dts.asmid=:asmid or d.asmid=:asmid",nativeQuery = true)
	List<IndexDistributor> indexDistributorByasmId(int asmid);
	
	
	@Query(value = "select d.id,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude,sz.state_name,z.zone_name,s.staff_name as asestaffname"
			+ "    FROM distributor d"
			+ "    left join state_zone sz on d.stateid = sz.id"
			+ "    left join zone z on d.zonesid = z.id "
			+ "	   LEFT join staff s on d.aseid = s.id "
			+ "    LEFT JOIN distributor_to_staff dts on d.id = dts.distributor_id"
			+ "    where dts.asmid=:asmid or d.asmid=:asmid and"
			+ "     REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "     or d.transporter_name Like CONCAT('%', :search, '%')" 
			+ "     or sz.state_name Like CONCAT('%', :search, '%')" 
			+ "     or z.zone_name Like CONCAT('%', :search, '%')" 
			+ "     or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "     or d.id Like CONCAT('%', :search, '%')"
			+ "     or d.gst_number Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<IndexDistributor> SearchDistributorByAsmId(int asmid,String search, Pageable p);

//	get distributor by asmid  end
	
	
	
//	get distributor by aseid 

	@Query(value = "SELECT d.id,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude,sz.state_name,z.zone_name,s.staff_name as asestaffname"
			+ " from distributor d "
			+ " left join state_zone sz on d.stateid = sz.id"
			+ " left join zone z on d.zonesid = z.id"
			+ " LEFT join staff s on d.aseid = s.id "
			+ " LEFT JOIN distributor_to_staff dts on d.id = dts.distributor_id"
			+ " where dts.aseid=:aseid or d.aseid=:aseid",nativeQuery = true)
	List<IndexDistributor> indexDistributorByaseId(int aseid,Pageable p);

	
	@Query(value = "SELECT d.id,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude,sz.state_name,z.zone_name,s.staff_name as asestaffname"
			+ " from distributor d "
			+ " left join state_zone sz on d.stateid = sz.id"
			+ " left join zone z on d.zonesid = z.id"
			+ " LEFT join staff s on d.aseid = s.id "
			+ " LEFT JOIN distributor_to_staff dts on d.id = dts.distributor_id"
			+ " where dts.aseid=:aseid or d.aseid=:aseid",nativeQuery = true)
	List<IndexDistributor> indexDistributorByaseId(int aseid);
	
	
	@Query(value = "select d.id,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude,sz.state_name,z.zone_name,s.staff_name as asestaffname"
			+ "    FROM distributor d"
			+ "    left join state_zone sz on d.stateid = sz.id "
			+ "    left join zone z on d.zonesid = z.id "
			+ "	   LEFT join staff s on d.aseid = s.id "
			+ "    LEFT JOIN distributor_to_staff dts on d.id = dts.distributor_id"
			+ "     where dts.aseid=:aseid or d.aseid=:aseid and"
			+ "     REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "     or d.transporter_name Like CONCAT('%', :search, '%')" 
			+ "     or sz.state_name Like CONCAT('%', :search, '%')" 
			+ "     or z.zone_name Like CONCAT('%', :search, '%')" 
			+ "     or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "     or d.id Like CONCAT('%', :search, '%')"
			+ "     or d.gst_number Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<IndexDistributor> SearchDistributorByAseId(int aseid,String search, Pageable p);
	
	
	@Query(value = "CALL DistributorGetAll()",nativeQuery = true)
	List<Distributor> getAllDist();
	
	
	@Query(value = "CALL DistributorExport()",nativeQuery = true)
	List<ExportDistributor> Excelexportfromdistributor();
	
	
	@Query(value = "CALL DistributorGetById(?)",nativeQuery = true)
	Distributor getDistributorById(int did);
	
	
	@Query(value = "select d.id,d.trade_name,d.stateid from distributor d",nativeQuery = true)
	public List<DistDto> fetchDsitributor();
	
	
	@Transactional
	@Modifying
	@Query(value = "delete from distributor_to_staff dts where dts.distributor_id is null",nativeQuery = true)
	void deletedistributorstaffbydistributorId();

	
//	get distributor by asmid  end
// groupn1 to n5 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	
	

//	with groupn5
	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where d.groupn5 Like CONCAT('%',:search,'%')"
			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn5Is(Pageable p,String search);
	
//	with groupn4
	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where d.groupn4 Like CONCAT('%',:search,'%')"
			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn4Is(Pageable p,String search);
	
//	with groupn3
	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where d.groupn3 Like CONCAT('%',:search,'%')"
			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn3Is(Pageable p,String search);
	
//	with groupn2
	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where d.groupn2 Like CONCAT('%',:search,'%')"
			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn2Is(Pageable p,String search);
	
//	with groupn1
	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where d.groupn1 Like CONCAT('%',:search,'%')"
			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn1Is(Pageable p,String search);
	
//	when count is 2
	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where "
			+ " d.groupn1 Like CONCAT('%',:search1,'%') and"
			+ " d.groupn2 Like CONCAT('%',:search2,'%')"

			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn1AndGroupn2Is(Pageable p,String search1,String search2);
	
//	when count is 3
	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where "
			+ " d.groupn1 Like CONCAT('%',:search1,'%') and"
			+ " d.groupn2 Like CONCAT('%',:search2,'%') and"
			+ " d.groupn3 Like CONCAT('%',:search3,'%')"

			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn1AndGroupn2AndGroupn3Is(Pageable p,String search1,String search2,String search3);
	
//	when count is 4
	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where "
			+ " d.groupn1 Like CONCAT('%',:search1,'%') and"
			+ " d.groupn2 Like CONCAT('%',:search2,'%') and"
			+ " d.groupn3 Like CONCAT('%',:search3,'%') and"
			+ " d.groupn4 Like CONCAT('%',:search4,'%')"

			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4Is(Pageable p,String search1,String search2,String search3,String search4);
	
	
//	when count is 5
	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where "
			+ " d.groupn1 Like CONCAT('%',:search1,'%') and"
			+ " d.groupn2 Like CONCAT('%',:search2,'%') and"
			+ " d.groupn3 Like CONCAT('%',:search3,'%') and"
			+ " d.groupn4 Like CONCAT('%',:search4,'%') and"
			+ " d.groupn5 Like CONCAT('%',:search5,'%')"
			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5Is(Pageable p,String search1,String search2,String search3,String search4,String search5);
	
	
//	when search with g2 and g3
	
	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where "
			+ " d.groupn2 Like CONCAT('%',:search2,'%') and"
			+ " d.groupn3 Like CONCAT('%',:search3,'%')  "
			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn2AndGroupn3Is(Pageable p, String search2,String search3 );
	
//	when search with g2 and g3 and g4

	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where "
			+ " d.groupn2 Like CONCAT('%',:search2,'%') and"
			+ " d.groupn3 Like CONCAT('%',:search3,'%') and"
			+ " d.groupn4 Like CONCAT('%',:search4,'%')  "

			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn2AndGroupn3AndGroupn4Is(Pageable p, String search2,String search3,String search4 );
	
	
//	when search with g2 and g3 and g4 and g5

	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where "
			+ " d.groupn2 Like CONCAT('%',:search2,'%') and"
			+ " d.groupn3 Like CONCAT('%',:search3,'%') and"
			+ " d.groupn4 Like CONCAT('%',:search4,'%') and"
			+ " d.groupn5 Like CONCAT('%',:search5,'%')  "
			
			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn2AndGroupn3AndGroupn4AndGroupn5Is(Pageable p, String search2,String search3,String search4,String search5);
	
	
	
//	when search with  g3 and g4

	
	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where "
			+ " d.groupn3 Like CONCAT('%',:search3,'%') and"
			+ " d.groupn4 Like CONCAT('%',:search4,'%') "
			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn3AndGroupn4Is(Pageable p,String search3,String search4);
	
//	when search with  g3 and g4 and g5
	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where "
			+ " d.groupn3 Like CONCAT('%',:search3,'%') and"
			+ " d.groupn4 Like CONCAT('%',:search4,'%') and"
			+ " d.groupn5 Like CONCAT('%',:search5,'%')  "
			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn3AndGroupn4AndGroupn5Is(Pageable p,String search3,String search4,String search5);
	

//	when search with  g4 and g5
	@Query(value = "select d.id,'ROLE_DISTRIBUTOR' AS rolename ,d.city,d.gst_number,DATE_FORMAT(d.createddate,'%d-%m-%Y') AS createddate,d.createbyname,d.updatedbyname,d.createdtime,d.trade_name,d.transporter_name,DATE_FORMAT(d.updateddate,'%d-%m-%Y') AS updateddate,d.updatedtime,d.latitude,d.longitude"
			+ "    FROM distributor d"
			+ " where "
			+ " d.groupn4 Like CONCAT('%',:search4,'%') and"
			+ " d.groupn5 Like CONCAT('%',:search5,'%')  "
			+"  ",nativeQuery = true)
	List<IndexDistributor> getAllDistributorWithGroupn4AndGroupn5Is(Pageable p,String search4,String search5);
	
	
	
// groupn1 to n5 end %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%	

	
	
}
