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

import com.SCM.ExportDto.ExportRetailer;
import com.SCM.IndexDto.IndexGroupn;
import com.SCM.IndexDto.IndexRetailer;
import com.SCM.IndexDto.RetailerWithRetailerStaffDto;
import com.SCM.model.Retailer;

@Repository
public interface RetailerRepo extends JpaRepository<Retailer, Integer> {

	public List<Retailer> findByPinCode(String pincode);
	
	@Query(value = "SELECT * from work_order wo " + "join retailer r on r.id = wo.retailer_id "
			+ "where wo.dist_id = ?1 AND (wo.work_order_date BETWEEN ?2 AND ?3)", nativeQuery = true)
	public List<Retailer> getWorkOrderandRetailer(@Param("d_id") Integer d_id, @Param("from_date") String from_date,@Param("to_date") String to_date);

	
	@Query(value = "CALL FetchAllWorkOrder()", nativeQuery = true)
	public List<Retailer> getAllWorkorderWithRetailer();

	// ase asm rsm nsm zone id in Retailer

	@Query(value = "select * from retailer r where r.aseid = ?1", nativeQuery = true)
	public List<Retailer> getRetailerByAseID(int aseId);

	@Query(value = "select * from retailer r where r.asmid = ?1", nativeQuery = true)
	public List<Retailer> getRetailerByAsmID(int asmId);

	@Query(value = "select * from retailer r where r.rsmid = ?1", nativeQuery = true)
	public List<Retailer> getRetailerByRsmID(int rsmId);

	@Query(value = "select * from retailer r where r.nsmid = ?1", nativeQuery = true)
	public List<Retailer> getRetailerByNsmID(int nsmId);

	@Query(value = "select * from retailer r where r.zonesid = ?1", nativeQuery = true)
	public List<Retailer> getRetailerByZoneId(int zoneId);

	@Query(value = "select * from retailer r where r.stateid = ?1", nativeQuery = true)
	public List<Retailer> getRetailerByStateId(int stateId);

	@Query(value = "select * from retailer r where r.distributor_id = ?1", nativeQuery = true)
	public List<Retailer> getRetailerByDistributorId(int distId);

	@Query(value = "select * from retailer r where r.id = ?1", nativeQuery = true)
	public List<Retailer> getRetailerByRetailerId(int retId);

	Optional<Retailer> findByEmail1(String email);

	Optional<Retailer> findByPerEmail(String email);

	Boolean existsByPerEmail(String email);

	@Query(value = "select * from retailer r where r.per_email = :email", nativeQuery = true)
	Optional<Retailer> getRetailerByPerEmail(String email);


//
//    @Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,d.trade_name as distributorname,s.staff_name as asestaffname,"
//    		+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
//    		+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
//    		+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
//    		+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
//    		+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
//    		+ " r.pin_code as pincode,r.createddate,r.createdtime "
//    		+ "   from retailer r"
//    		+ "   LEFT join state_zone sz on r.stateid = sz.id"
//    		+ "   LEFT join distributor d on r.distributor_id = d.id"
//    		+ "   LEFT join staff s on r.aseid = s.id ",nativeQuery = true)
//  	List<IndexRetailer> indexRetailer(Pageable p);
    



//	@Query(value = "SELECT r.id,r.trade_name as retailername,r.retailerstatus,r.city,sz.state_name,r.gst_number,d.trade_name as distributorname,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,s.staff_name as asestaffname,"
//			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
//			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
//			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
//			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
//			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
//			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime " + " from retailer r"
//			+ " LEFT join state_zone sz on r.stateid = sz.id" + " LEFT join distributor d on r.distributor_id = d.id"
//			+ " LEFT join staff s on r.aseid = s.id", nativeQuery = true)
//	List<IndexRetailer> indexRetailer(Pageable p);
	

	@Query(value = "SELECT r.id,r.trade_name as retailername,r.retailerstatus,r.city,sz.state_name,r.gst_number,d.trade_name as distributorname,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,GROUP_CONCAT(s.staff_name) AS asestaffnames,\r\n"
			+ "			 r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,\r\n"
			+ "			 r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,\r\n"
			+ "			 r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,\r\n"
			+ "			 r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,\r\n"
			+ "			 r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,\r\n"
			+ "			 r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime from retailer r\r\n"
			+ "			 LEFT join state_zone sz on r.stateid = sz.id LEFT join distributor d on r.distributor_id = d.id\r\n"
			+ "		     left join retailer_to_staff rs on r.id=rs.retialer_id\r\n"
			+ "			LEFT join staff s on rs.aseid = s.id\r\n"
			+ "             group by r.id,r.trade_name,r.retailerstatus,r.city,sz.state_name,r.gst_number,d.trade_name,r.updatedtime,r.latitude,r.longitude", nativeQuery = true)
	List<IndexRetailer> indexRetailer(Pageable p);
	
	
	


	
	
	@Query(value = "SELECT r.id,r.trade_name as retailername,r.retailerstatus,r.city,sz.state_name,r.gst_number,d.trade_name as distributorname,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,GROUP_CONCAT(s.staff_name) AS asestaffnames,\r\n"
			+ "	r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,\r\n"
			+ "	r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,\r\n"
			+ "	r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,\r\n"
			+ "	r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,\r\n"
			+ "	r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,\r\n"
			+ "	r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime from retailer r\r\n"
			+ "	LEFT join state_zone sz on r.stateid = sz.id LEFT join distributor d on r.distributor_id = d.id\r\n"
			+ "	left join retailer_to_staff rs on r.id=rs.retialer_id\r\n"
			+ "	LEFT join staff s on rs.aseid = s.id"
			+ " where sz.state_name Like CONCAT('%',:search, '%')\r\n"
			  + " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name), '[^a-z0-9]', '')) \r\n"
		        + " LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')) "
			+" or LOWER(REGEXP_REPLACE(COALESCE(d.trade_name), '[^a-z0-9]', ''))\r\n" 
			+" LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
            + " or r.box_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.gst_number Like CONCAT('%', :search, '%')"
			+ " or r.cooker_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.corporaet_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.kg_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.nosh_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.scheme_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemebox_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemecooker_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemecorporate_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemekg_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemenosh_product_disocunt Like CONCAT('%', :search, '%')"
			+ " or r.pin_code Like CONCAT('%', :search, '%')"
			+ " or r.id Like CONCAT('%', :search, '%')"
			+ " or CONCAT(s.staff_name) Like CONCAT('%', :search, '%')"
			+ "group by r.id,r.trade_name,r.retailerstatus,r.city,sz.state_name,r.gst_number,d.trade_name,r.updatedtime,r.latitude,r.longitude", nativeQuery = true)
	List<IndexRetailer> SearchByRetailer(String search, Pageable p);
	
	
// find retailer by nsmid
	
	
	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames,\r\n"
			+ "r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,\r\n"
			+ "r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,\r\n"
			+ "r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,\r\n"
			+ "r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,\r\n"
			+ "r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,\r\n"
			+ "r.pin_code as pinCode,r.createddate,r.createdtime\r\n"
			+ "from retailer r\r\n"
			+ "LEFT join state_zone sz on r.stateid = sz.id\r\n"
			+ "LEFT join distributor d on r.distributor_id = d.id\r\n"
			+ "LEFT join staff s on r.aseid = s.id\r\n"
			+ "LEFT JOIN retailer_to_staff rts ON r.id = rts.retialer_id\r\n"
			+ "where  rts.nsmid= :nsmid\r\n"
			+ "group by r.id", nativeQuery = true)
	List<IndexRetailer> indexRetailerByNsmId(int nsmid, Pageable p);
	
	
	
	

	
	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames,\r\n"
			+ "r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,\r\n"
			+ "r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,\r\n"
			+ "r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,\r\n"
			+ "r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,\r\n"
			+ "r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,\r\n"
			+ "r.pin_code as pinCode,r.createddate,r.createdtime\r\n"
			+ "from retailer r\r\n"
			+ "LEFT join state_zone sz on r.stateid = sz.id\r\n"
			+ "LEFT join distributor d on r.distributor_id = d.id\r\n"
			+ "LEFT join staff s on r.aseid = s.id\r\n"
			+ "LEFT JOIN retailer_to_staff rts ON r.id = rts.retialer_id\r\n"
			+ "where  rts.nsmid= :nsmid\r\n"
			+ "group by r.id", nativeQuery = true)
	List<IndexRetailer> indexRetailerByNsmId(int nsmid);
	
	
	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames ,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode," 
			+ " r.createddate,r.createdtime" 
			+ " from retailer r"
			+ " LEFT join state_zone sz on r.stateid = sz.id\r\n"
			+ " LEFT join distributor d on r.distributor_id = d.id\r\n" 
			+ " LEFT join staff s on r.aseid = s.id \r\n"
			+ " LEFT JOIN retailer_to_staff rts ON r.id = rts.retialer_id"
			+ " where (rts.nsmid= :nsmid or r.nsmid=:nsmid) and"
			+ " (sz.state_name Like CONCAT('%',:search, '%')\r\n"
			  + " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name), '[^a-z0-9]', '')) \r\n"
		        + " LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')) "
			+" or LOWER(REGEXP_REPLACE(COALESCE(d.trade_name), '[^a-z0-9]', ''))\r\n" 
			+" LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ " or r.box_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.gst_number Like CONCAT('%', :search, '%')"
			+ " or r.cooker_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.corporaet_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.kg_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.nosh_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.scheme_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemebox_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemecooker_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemecorporate_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemekg_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemenosh_product_disocunt Like CONCAT('%', :search, '%')"
			+ " or r.pin_code Like CONCAT('%', :search, '%'))"
			+ " group by r.id", nativeQuery = true)
	List<IndexRetailer> SearchRetailerByNsmId(int nsmid, String search, Pageable p);
	
	
	
//	find retailer by rsmid

	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames,\r\n"
			+ "r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,\r\n"
			+ "r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,\r\n"
			+ "r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,\r\n"
			+ "r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,\r\n"
			+ "r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,\r\n"
			+ "r.pin_code as pinCode,r.createddate,r.createdtime\r\n"
			+ "from retailer r\r\n"
			+ "LEFT join state_zone sz on r.stateid = sz.id\r\n"
			+ "LEFT join distributor d on r.distributor_id = d.id\r\n"
            + "LEFT JOIN retailer_to_staff rts ON r.id = rts.retialer_id\r\n"
			+ "LEFT join staff s on rts.aseid = s.id\r\n"
			+ "where rts.rsmid= :rsmid\r\n"
			+ "group by r.id", nativeQuery = true)
	List<IndexRetailer> indexRetailerByRsmId(int rsmid, Pageable p);

	
	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames,\r\n"
			+ "r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,\r\n"
			+ "r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,\r\n"
			+ "r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,\r\n"
			+ "r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,\r\n"
			+ "r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,\r\n"
			+ "r.pin_code as pinCode,r.createddate,r.createdtime\r\n"
			+ "from retailer r\r\n"
			+ "LEFT join state_zone sz on r.stateid = sz.id\r\n"
			+ "LEFT join distributor d on r.distributor_id = d.id\r\n"
          + "LEFT JOIN retailer_to_staff rts ON r.id = rts.retialer_id\r\n"
  		+ "LEFT join staff s on rts.aseid = s.id\r\n"
			+ "where rts.rsmid= :rsmid\r\n"
			+ "group by r.id", nativeQuery = true)
	List<IndexRetailer> indexRetailerByRsmId(int rsmid);
	
	
	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode," 
			+ " r.createddate,r.createdtime" 
			+ " from retailer r"
			+ " LEFT join state_zone sz on r.stateid = sz.id\r\n"
			+ " LEFT join distributor d on r.distributor_id = d.id\r\n" 
	        + " LEFT JOIN retailer_to_staff rts ON r.id = rts.retialer_id"
		    + " LEFT join staff s on rts.aseid = s.id\r\n"
			+ " where rts.rsmid= :rsmid and "
			+ " (sz.state_name Like CONCAT('%',:search, '%')\r\n"
			+" or LOWER(REGEXP_REPLACE(COALESCE(d.trade_name), '[^a-z0-9]', ''))\r\n" 
			+" LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')) "
			+" or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name), '[^a-z0-9]', ''))\r\n" 
			+" LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ " or r.box_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.gst_number Like CONCAT('%', :search, '%')"
			+ " or r.cooker_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.corporaet_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.kg_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.nosh_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.scheme_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemebox_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemecooker_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemecorporate_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemekg_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemenosh_product_disocunt Like CONCAT('%', :search, '%')"
			+ " or r.pin_code Like CONCAT('%', :search, '%'))"
			+ "group by r.id", nativeQuery = true)
	List<IndexRetailer> SearchRetailerByRsmId(int rsmid, String search, Pageable p);
	
	
// find retailer by rsmid end

//		===================================================
//		find retailer by amsid

	
	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames,\r\n"
			+ "r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,\r\n"
			+ "r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,\r\n"
			+ "r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,\r\n"
			+ "r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,\r\n"
			+ "r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,\r\n"
			+ "r.pin_code as pinCode,r.createddate,r.createdtime\r\n"
			+ "from retailer r\r\n"
			+ "LEFT join state_zone sz on r.stateid = sz.id\r\n"
			+ "LEFT join distributor d on r.distributor_id = d.id\r\n"
	        + "LEFT JOIN retailer_to_staff rts ON r.id = rts.retialer_id\r\n"
			+ "LEFT join staff s on rts.aseid = s.id\r\n"
			+ "where  rts.asmid= :asmid\r\n"
			+ "group by r.id", nativeQuery = true)
	List<IndexRetailer> indexRetailerByAsmId(int asmid, Pageable p);

	
	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames,\r\n"
			+ "r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,\r\n"
			+ "r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,\r\n"
			+ "r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,\r\n"
			+ "r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,\r\n"
			+ "r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,\r\n"
			+ "r.pin_code as pinCode,r.createddate,r.createdtime\r\n"
			+ "from retailer r\r\n"
			+ "LEFT join state_zone sz on r.stateid = sz.id\r\n"
			+ "LEFT join distributor d on r.distributor_id = d.id\r\n"
	      + " LEFT JOIN retailer_to_staff rts ON r.id = rts.retialer_id\r\n"
			+ "LEFT join staff s on rts.aseid = s.id\r\n"
			+ "where  rts.asmid= :asmid\r\n"
			+ "group by r.id", nativeQuery = true)
	List<IndexRetailer> indexRetailerByAsmId(int asmid);
	
	
	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode," 
			+ " r.createddate,r.createdtime" 
			+ " from retailer r "
			+ " LEFT join state_zone sz on r.stateid = sz.id\r\n"
			+ " LEFT join distributor d on r.distributor_id = d.id\r\n" 
			+ " LEFT JOIN retailer_to_staff rts ON r.id = rts.retialer_id"
			+ " LEFT join staff s on rts.aseid = s.id\r\n"
			+ " where (rts.asmid= :asmid or r.asmid=:asmid) and"
			+ " (sz.state_name Like CONCAT('%',:search, '%')\r\n"
			+ " or LOWER(REGEXP_REPLACE(COALESCE(d.trade_name), '[^a-z0-9]', ''))\r\n" 
			+" LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')) "
			+" or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name), '[^a-z0-9]', ''))\r\n" 
			+" LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ " or r.box_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.gst_number Like CONCAT('%', :search, '%')"
			+ " or r.cooker_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.corporaet_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.kg_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.nosh_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.scheme_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemebox_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemecooker_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemecorporate_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemekg_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemenosh_product_disocunt Like CONCAT('%', :search, '%')"
			+ " or r.pin_code Like CONCAT('%', :search, '%'))"
			+ " group by r.id", nativeQuery = true)
	List<IndexRetailer> SearchRetailerByAsmId(int asmid, String search, Pageable p);
	
	
	// =-------------------------------  find retailer by amsid end

//			====================================

//			find retailer by aseid

	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames,\r\n"
			+ "r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,\r\n"
			+ "r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,\r\n"
			+ "r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,\r\n"
			+ "r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,\r\n"
			+ "r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,\r\n"
			+ "r.pin_code as pinCode,r.createddate,r.createdtime\r\n"
			+ "from retailer r\r\n"
			+ "LEFT join state_zone sz on r.stateid = sz.id\r\n"
			+ "LEFT join distributor d on r.distributor_id = d.id\r\n"
+ "LEFT JOIN retailer_to_staff rts ON r.id = rts.retialer_id\r\n"
+ "LEFT join staff s on rts.aseid = s.id\r\n"
			+ "where  rts.aseid= :aseid\r\n"
			+ "group by r.id", nativeQuery = true)
	List<IndexRetailer> indexRetailerByAseId(int aseid, Pageable p);


	
	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames,\r\n"
			+ "r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,\r\n"
			+ "r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,\r\n"
			+ "r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,\r\n"
			+ "r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,\r\n"
			+ "r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,\r\n"
			+ "r.pin_code as pinCode,r.createddate,r.createdtime\r\n"
			+ "from retailer r\r\n"
			+ "LEFT join state_zone sz on r.stateid = sz.id\r\n"
			+ "LEFT join distributor d on r.distributor_id = d.id\r\n"
	       + "LEFT JOIN retailer_to_staff rts ON r.id = rts.retialer_id\r\n"
			+ "LEFT join staff s on rts.aseid = s.id\r\n"
			+ "where  rts.aseid= :aseid\r\n"
			+ "group by r.id", nativeQuery = true)
	List<IndexRetailer> indexRetailerByAseId(int aseid);
	
	
	
	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime from retailer r "
			+ " LEFT join state_zone sz on r.stateid = sz.id\r\n"
			+ " LEFT join distributor d on r.distributor_id = d.id\r\n"
           + " LEFT JOIN retailer_to_staff rts ON r.id = rts.retialer_id"
			+ " LEFT join staff s on rts.aseid = s.id\r\n"
			+ " where (rts.aseid= :aseid or r.aseid=:aseid) and"
			+ " (r.id Like CONCAT('%', :search, '%')"
			+ " or rts.retialer_id Like CONCAT('%', :search, '%')"
			+" or LOWER(REGEXP_REPLACE(COALESCE(d.trade_name), '[^a-z0-9]', ''))\r\n" 
			+" LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')) "
			+" or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name), '[^a-z0-9]', ''))\r\n" 
			+" LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ " or r.box_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.gst_number Like CONCAT('%', :search, '%')"
			+ " or r.cooker_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.corporaet_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.kg_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.nosh_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.scheme_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemebox_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemecooker_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemecorporate_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemekg_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemenosh_product_disocunt Like CONCAT('%', :search, '%')"
			+ " or r.pin_code Like CONCAT('%', :search, '%'))"
			+ " group by r.id", nativeQuery = true)
	List<IndexRetailer> SearchRetailerByAseId(int aseid,String search,Pageable p);

	
	// find retailer by aseid end

	
	
	// ------------------------ retailer by distributor id

	
	@Query(value = "SELECT r.id, r.trade_name AS retailername, r.city, sz.state_name, r.gst_number, r.latitude, r.longitude, d.trade_name AS distributorname, " +
            "GROUP_CONCAT(s.staff_name) AS asestaffnames, " +
            "r.box_product_discount AS boxProductDiscount, r.cooker_product_discount AS cookerProductDiscount, r.corporaet_product_discount AS corporaetProductDiscount, " +
            "r.kg_product_discount AS kgProductDiscount, r.nosh_product_discount AS noshProductDiscount, " +
            "r.scheme_discount AS schemeDiscount, r.schemebox_product_discount AS schemeBoxProductDiscount, " +
            "r.schemecooker_product_discount AS schemeCookerProductDiscount, r.schemecorporate_product_discount AS schemeCorporateProductDiscount, " +
            "r.schemekg_product_discount AS schemeKgProductDiscount, r.schemenosh_product_disocunt AS schemeNoshProductDisocunt, " +
            "r.pin_code AS pinCode, r.createddate, r.createdtime " +
            "FROM retailer r " +
            "LEFT JOIN state_zone sz ON r.stateid = sz.id " +
            "LEFT JOIN distributor d ON r.distributor_id = d.id " +
            "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id " +
            "LEFT JOIN staff s ON rs.aseid = s.id " +
            "WHERE r.distributor_id = :distid " +
            "GROUP BY r.id",
    nativeQuery = true)
List<IndexRetailer> indexRetailerByDistId(@Param("distid") int distid, Pageable p);


	
	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode," + " r.createddate,r.createdtime" + "   from retailer r"
			+ "   LEFT join state_zone sz on r.stateid = sz.id"
			+ "   LEFT join distributor d on r.distributor_id = d.id" 
			+ "		     LEFT join retailer_to_staff rs on r.id=rs.retialer_id"
			+ "			LEFT join staff s on rs.aseid = s.id"
			+ "	  where r.distributor_id=:distid "
			+ "             group by r.id", nativeQuery = true)
	List<IndexRetailer> indexRetailerByDistId(int distid);
	
	
	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,r.latitude,r.longitude,d.trade_name as distributorname,GROUP_CONCAT(s.staff_name) as asestaffnames,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode," + " r.createddate,r.createdtime" + "   from retailer r"
			+ "   LEFT join state_zone sz on r.stateid = sz.id"
			+ "   LEFT join distributor d on r.distributor_id = d.id" 
			+ "		     LEFT join retailer_to_staff rs on r.id=rs.retialer_id"
			+ "			LEFT join staff s on rs.aseid = s.id"
			+ "	  where  r.distributor_id = :distid and "
			+ " (r.id Like CONCAT('%', :search, '%')"
			+ " or sz.state_name Like CONCAT('%',:search, '%')\r\n"
			+" or LOWER(REGEXP_REPLACE(COALESCE(d.trade_name), '[^a-z0-9]', ''))\r\n" 
			+" LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')) "
			+" or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name), '[^a-z0-9]', ''))\r\n" 
			+" LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ " or r.box_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.gst_number Like CONCAT('%', :search, '%')"
			+ " or r.cooker_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.corporaet_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.kg_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.nosh_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.scheme_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemebox_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemecooker_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemecorporate_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemekg_product_discount Like CONCAT('%', :search, '%')"
			+ " or r.schemenosh_product_disocunt Like CONCAT('%', :search, '%')"
			+ " or r.pin_code Like CONCAT('%', :search, '%'))"
			+ " group by r.id"
		, nativeQuery = true)
	List<IndexRetailer> SearchRetailerByDistId(int distid, String search, Pageable p);
	
	
	
//	@Query(value = "SELECT r.id,r.trade_name as retailername,r.city,sz.state_name,r.gst_number,d.trade_name as distributorname,s.staff_name as asestaffname,"
//			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
//			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
//			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
//			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
//			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
//			+ " r.pin_code as pinCode," + " r.createddate,r.createdtime" + "   from retailer r"
//			+ "   LEFT join state_zone sz on r.stateid = sz.id"
//			+ "   LEFT join distributor d on r.distributor_id = d.id" + "   LEFT join staff s on r.aseid = s.id "
//			+ "	  where  r.aseid=:aseid and " + "  (sz.state_name Like CONCAT('%',:search, '%')\r\n"
//			+ " or d.trade_name Like CONCAT('%', :search, '%')\r\n" + " or s.staff_name Like CONCAT('%', :search, '%')"
//			+ " or r.trade_name Like CONCAT('%', :search, '%')"
//			+ " or r.box_product_discount Like CONCAT('%', :search, '%')"
//			+ " or r.cooker_product_discount Like CONCAT('%', :search, '%')"
//			+ " or r.corporaet_product_discount Like CONCAT('%', :search, '%')"
//			+ " or r.kg_product_discount Like CONCAT('%', :search, '%')"
//			+ " or r.nosh_product_discount Like CONCAT('%', :search, '%')"
//			+ " or r.scheme_discount Like CONCAT('%', :search, '%')"
//			+ " or r.schemebox_product_discount Like CONCAT('%', :search, '%')"
//			+ " or r.schemecooker_product_discount Like CONCAT('%', :search, '%')"
//			+ " or r.schemecorporate_product_discount Like CONCAT('%', :search, '%')"
//			+ " or r.schemekg_product_discount Like CONCAT('%', :search, '%')"
//			+ " or r.schemenosh_product_disocunt Like CONCAT('%', :search, '%')"
//			+ " or r.pin_code Like CONCAT('%', :search, '%'))", nativeQuery = true)
//	List<IndexRetailer> SearchRetailerByDistIdSearch(int distid, String search, Pageable p);

//	@Query(value = "SELECT r.*,sz.state_name,z.zone_name,s.staff_name as AseStaff,s1.staff_name As AsmStaff,s2.staff_name As RsmStaff ,s3.staff_name As NsmStaff,d.trade_name As distributorName FROM retailer r"
//			+ " LEFT JOIN state_zone sz ON r.stateid = sz.id "
//			+ " LEFT JOIN distributor d on r.distributor_id = d.id "
//			+ " LEFT JOIN zone  z  on r.zonesid =  z.id "
//			+ " LEFT JOIN staff s  on r.aseid  =  s.id "
//			+ " LEFT JOIN staff s1 ON r.asmid   = s1.id"
//			+ " LEFT JOIN staff s2 ON r.rsmid   = s2.id"
//			+ " LEFT JOIN staff s3 ON r.nsmid  =  s3.id",nativeQuery = true)
//	List<ExportRetailer> Excelexportfromretailer();


	@Query(value = "SELECT r.*,sz.state_name,z.zone_name,GROUP_CONCAT(distinct s.staff_name) as AseStaff,GROUP_CONCAT(distinct s1.staff_name) As AsmStaff,GROUP_CONCAT(distinct s2.staff_name) As RsmStaff ,GROUP_CONCAT(distinct s3.staff_name) As NsmStaff,d.trade_name As distributorName FROM retailer r\r\n"
			+ "    LEFT JOIN state_zone sz ON r.stateid = sz.id \r\n"
			+ "    LEFT JOIN distributor d on r.distributor_id = d.id \r\n"
			+ "    LEFT JOIN zone  z  on r.zonesid =  z.id \r\n"
			+ "    left join retailer_to_staff rs on r.id=rs.retialer_id\r\n"
			+ "LEFT JOIN staff s  on rs.aseid  =  s.id \r\n"
			+ "LEFT JOIN staff s1 ON rs.asmid   = s1.id\r\n"
			+ "LEFT JOIN staff s2 ON rs.rsmid   = s2.id\r\n"
			+ "LEFT JOIN staff s3 ON rs.nsmid  =  s3.id\r\n"
			+ "GROUP BY r.id", nativeQuery = true)
	List<ExportRetailer> Excelexportfromretailer();

	@Query(value = "CALL RetailerGetAll()", nativeQuery = true)
	List<Retailer> getAllRet();

	@Query(value = "CALL RetailerById(?)", nativeQuery = true)
	Retailer getRetailerById(int retid);
	
	
//	 multiple staff from retailer
	
	@Query(value = "SELECT * FROM retailer r\r\n"
			+ "LEFT JOIN retailer_to_staff rts ON r.id = rts.retialer_id\r\n"
			+ "WHERE rts.rsmid = ?1", nativeQuery = true)
	public List<Retailer> getRetailerByRsmID1(int rsmId);
	
	
	@Transactional
	@Modifying
	@Query(value = "delete from retailer_to_staff where retialer_id is null",nativeQuery = true)
	void deleteRetailerTostaffByRetailerId();
	
	
    @Query(value = "select * from retailer r\r\n"
    		+ "left join retailer_to_staff rts on r.id = rts.retialer_id\r\n"
    		+ "where r.id = 5959",nativeQuery = true)
     public List<Retailer> getRetailerWithRetailerToStaff(@Param("ret_id")Integer ret_id);
    
   
    @Query(value = "select r.*,rts.aseid,rts.asmid,rts.rsmid,s.staff_name AS asestaffname,s1.staff_name AS asmstaffname,s2.staff_name AS rsmstaffname from retailer r\r\n"
    		+ "LEFT join retailer_to_staff rts on r.id = rts.retialer_id\r\n"
    		+ "LEFT join staff s on rts.aseid = s.id\r\n"
    		+ "left join staff s1 on rts.asmid = s1.id\r\n"
    		+ "left join staff s2 on rts.rsmid = s2.id\r\n"
    		+ "WHERE rts.retialer_id = ?1",nativeQuery = true)
    public List<RetailerWithRetailerStaffDto> fetchRetailerWithStaffByRetailerId(int retid);
    
    
    
    @Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn1,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where r.groupn1 Like CONCAT('%',:search,'%')"
			+"  ",nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn1Is(Pageable p,String search);
	
// 3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333
//	for g1
	@Query(value = "SELECT * FROM (\r\n"
			+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
			+ "    FROM retailer r\r\n"
			+ "    WHERE r.groupn1 Like CONCAT('%',:search,'%')\r\n"
			+ "    UNION ALL\r\n"
			+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
			+ "    FROM distributor d\r\n"
			+ "    WHERE d.groupn1 Like CONCAT('%',:search,'%')\r\n"
			+ " ) AS combinedResults\r\n"
			+ " ORDER BY id", nativeQuery = true)
List<IndexGroupn> getAllRetailerAndDistributorWithGroupn1Is(String search);

// 3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333

	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn2,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where r.groupn2 Like CONCAT('%',:search,'%')"
			+"  ",nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn2Is(Pageable p,String search);
	
//	33333333333333333333333333333333333333333333333333333
 // for g2
	@Query(value = "SELECT * FROM (\r\n"
			+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
			+ "    FROM retailer r\r\n"
			+ "    WHERE r.groupn2 Like CONCAT('%',:search,'%')\r\n"
			+ "    UNION ALL\r\n"
			+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
			+ "    FROM distributor d\r\n"
			+ "    WHERE d.groupn2 Like CONCAT('%',:search,'%')\r\n"
			+ " ) AS combinedResults\r\n"
			+ " ORDER BY id", nativeQuery = true)
List<IndexGroupn> getAllRetailerAndDistributorWithGroupn2Is( String search);
	
//	333333333333333333333333333333333333333333333333333333
	
	
	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn3,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where r.groupn3 Like CONCAT('%',:search,'%')"
			+"  ",nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn3Is(Pageable p,String search);
	
//	33333333333333333333333333333333333333333333333333333
 // for g3
	@Query(value = "SELECT * FROM (\r\n"
			+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
			+ "    FROM retailer r\r\n"
			+ "    WHERE r.groupn3 Like CONCAT('%',:search,'%')\r\n"
			+ "    UNION ALL\r\n"
			+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
			+ "    FROM distributor d\r\n"
			+ "    WHERE d.groupn3 Like CONCAT('%',:search,'%')\r\n"
			+ " ) AS combinedResults\r\n"
			+ " ORDER BY id", nativeQuery = true)
List<IndexGroupn> getAllRetailerAndDistributorWithGroupn3Is(   String search);
	
//	333333333333333333333333333333333333333333333333333333
	
	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn4,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where r.groupn4 Like CONCAT('%',:search,'%')"
			,nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn4Is(Pageable p,String search);
//	33333333333333333333333333333333333333333333333333333
 // for g4
	@Query(value = "SELECT * FROM (\r\n"
			+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
			+ "    FROM retailer r\r\n"
			+ "    WHERE r.groupn4 Like CONCAT('%',:search,'%')\r\n"
			+ "    UNION ALL\r\n"
			+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
			+ "    FROM distributor d\r\n"
			+ "    WHERE d.groupn4 Like CONCAT('%',:search,'%')\r\n"
			+ " ) AS combinedResults\r\n"
			+ " ORDER BY id", nativeQuery = true)
List<IndexGroupn> getAllRetailerAndDistributorWithGroupn4Is( String search);
	
//	333333333333333333333333333333333333333333333333333333
	
	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn5,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where r.groupn5 Like CONCAT('%',:search,'%')"
			,nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn5Is(Pageable p,String search);
	
//	33333333333333333333333333333333333333333333333333333
 // for g5
	@Query(value = "SELECT * FROM (\r\n"
			+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
			+ "    FROM retailer r\r\n"
			+ "    WHERE r.groupn5 Like CONCAT('%',:search,'%')\r\n"
			+ "    UNION ALL\r\n"
			+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
			+ "    FROM distributor d\r\n"
			+ "    WHERE d.groupn5 Like CONCAT('%',:search,'%')\r\n"
			+ " ) AS combinedResults\r\n"
			+ " ORDER BY id", nativeQuery = true)
List<IndexGroupn> getAllRetailerAndDistributorWithGroupn5Is( String search);
	
//	333333333333333333333333333333333333333333333333333333
	
	
//	when count is 2
	
	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn1,r.groupn2,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where"
			+ " r.groupn1 Like CONCAT('%',:search1,'%') AND"
			+ " r.groupn2 Like CONCAT('%',:search2,'%')"

			+"  ",nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn1AndGroupn2Is(Pageable p,String search1,String search2);
	
//	33333333333333333333333333333333333333333333333333333
 // for g1 and g2 when count is 2
	@Query(value = "SELECT * FROM (\r\n"
			+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
			+ "    FROM retailer r\r\n"
			+ "    WHERE r.groupn1 Like CONCAT('%',:search1,'%') and\r\n"
			+ " 		 r.groupn2 Like CONCAT('%',:search2,'%')"
			+ "    UNION ALL\r\n"
			+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
			+ "    FROM distributor d\r\n"
			+ "    WHERE d.groupn1 Like CONCAT('%',:search1,'%') and\r\n"
			+ "			 d.groupn2 Like CONCAT('%',:search2,'%')"
			+ " ) AS combinedResults\r\n"
			+ " ORDER BY id", nativeQuery = true)
List<IndexGroupn> getAllRetailerAndDistributorWithGroupn1AndGroupn2Is( String search1,String search2);
	
//	333333333333333333333333333333333333333333333333333333

	
//	when count is 3
	
	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn1,r.groupn2,r.groupn3,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where"
			+ " r.groupn1 Like CONCAT('%',:search1,'%') AND"
			+ " r.groupn2 Like CONCAT('%',:search2,'%') AND"
			+ " r.groupn3 Like CONCAT('%',:search3,'%')"
			+"  ",nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn1AndGroupn2AndGroup3Is(Pageable p,String search1,String search2,String search3);
	
//	33333333333333333333333333333333333333333333333333333
 // for g1 and g2 and g3 when count is 3
	@Query(value = "SELECT * FROM (\r\n"
			+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
			+ "    FROM retailer r\r\n"
			+ "    WHERE r.groupn1 Like CONCAT('%',:search1,'%') and\r\n"
			+ " 		 r.groupn2 Like CONCAT('%',:search2,'%') and"
			+ " 		 r.groupn3 Like CONCAT('%',:search3,'%')"
			+ "    UNION ALL\r\n"
			+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
			+ "    FROM distributor d\r\n"
			+ "    WHERE d.groupn1 Like CONCAT('%',:search1,'%') and\r\n"
			+ "			 d.groupn2 Like CONCAT('%',:search2,'%') and"
			+ "			 d.groupn3 Like CONCAT('%',:search3,'%')"
			+ " ) AS combinedResults\r\n"
			+ " ORDER BY id", nativeQuery = true)
List<IndexGroupn> getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3Is( String search1,String search2,String search3);
	
//	333333333333333333333333333333333333333333333333333333
//	when count is 4
	
	
	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn1,r.groupn2,r.groupn3,r.groupn4,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where"
			+ " r.groupn1 Like CONCAT('%',:search1,'%') AND"
			+ " r.groupn2 Like CONCAT('%',:search2,'%') AND"
			+ " r.groupn3 Like CONCAT('%',:search3,'%') AND"
			+ " r.groupn4 Like CONCAT('%',:search4,'%')"
			+"  ",nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn1AndGroupn2AndGroup3AndGroupn4Is(Pageable p,String search1,String search2,String search3,String search4);
	
	
//	33333333333333333333333333333333333333333333333333333
 // for g1 and g2 and g3 and g4 when count is 4
	@Query(value = "SELECT * FROM (\r\n"
			+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
			+ "    FROM retailer r\r\n"
			+ "    WHERE r.groupn1 Like CONCAT('%',:search1,'%') and\r\n"
			+ " 		 r.groupn2 Like CONCAT('%',:search2,'%') and"
			+ " 		 r.groupn3 Like CONCAT('%',:search3,'%') and"
			+ " 		 r.groupn4 Like CONCAT('%',:search4,'%')"
			+ "    UNION ALL\r\n"
			+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
			+ "    FROM distributor d\r\n"
			+ "    WHERE d.groupn1 Like CONCAT('%',:search1,'%') and\r\n"
			+ "			 d.groupn2 Like CONCAT('%',:search2,'%') and"
			+ "			 d.groupn3 Like CONCAT('%',:search3,'%') and"
			+ "			 d.groupn4 Like CONCAT('%',:search4,'%')"
			+ " ) AS combinedResults\r\n"
			+ " ORDER BY id", nativeQuery = true)
List<IndexGroupn> getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4Is( String search1,String search2,String search3,String search4);
	
//	333333333333333333333333333333333333333333333333333333
//	when count is 5
	
	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn1,r.groupn2,r.groupn3,r.groupn4,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where"
			+ " r.groupn1 Like CONCAT('%',:search1,'%') AND"
			+ " r.groupn2 Like CONCAT('%',:search2,'%') AND"
			+ " r.groupn3 Like CONCAT('%',:search3,'%') AND"
			+ " r.groupn4 Like CONCAT('%',:search4,'%') AND"
			+ " r.groupn5 Like CONCAT('%',:search5,'%')"
			+"  ",nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn1AndGroupn2AndGroup3AndGroupn4AndGroupn5Is(Pageable p,String search1,String search2,String search3,String search4,String search5);
	
//	33333333333333333333333333333333333333333333333333333
 // for g1 and g2 and g3 and g4 and g5 when count is 0
	@Query(value = "SELECT * FROM (\r\n"
			+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
			+ "    FROM retailer r\r\n"
			+ "    WHERE r.groupn1 Like CONCAT('%',:search1,'%') and\r\n"
			+ " 		 r.groupn2 Like CONCAT('%',:search2,'%') and"
			+ " 		 r.groupn3 Like CONCAT('%',:search3,'%') and"
			+ " 		 r.groupn4 Like CONCAT('%',:search4,'%') and"
			+ " 		 r.groupn5 Like CONCAT('%',:search5,'%')"
			+ "    UNION ALL\r\n"
			+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
			+ "    FROM distributor d\r\n"
			+ "    WHERE d.groupn1 Like CONCAT('%',:search1,'%') and\r\n"
			+ "			 d.groupn2 Like CONCAT('%',:search2,'%') and"
			+ "			 d.groupn3 Like CONCAT('%',:search3,'%') and"
			+ "			 d.groupn4 Like CONCAT('%',:search4,'%') and"
			+ "			 d.groupn5 Like CONCAT('%',:search5,'%')"
			+ " ) AS combinedResults\r\n"
			+ " ORDER BY id", nativeQuery = true)
List<IndexGroupn> getAllRetailerAndDistributorWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5Is( String search1,String search2,String search3,String search4,String search5);
	
//	333333333333333333333333333333333333333333333333333333
	
//	when search2 and search3 is there
	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn1,r.groupn2,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where"
			+ " r.groupn2 Like CONCAT('%',:search2,'%') AND"
			+ " r.groupn3 Like CONCAT('%',:search3,'%')"
			+"  ",nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn2AndGroupn3Is(Pageable p,String search2,String search3);
//	&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
//	33333333333333333333333333333333333333333333333333333
 // for g2 and g3
	@Query(value = "SELECT * FROM (\r\n"
			+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
			+ "    FROM retailer r\r\n"
			+ "    WHERE r.groupn2 Like CONCAT('%',:search2,'%') and\r\n"
			+ " 		 r.groupn3 Like CONCAT('%',:search3,'%') "
			+ "    UNION ALL\r\n"
			+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
			+ "    FROM distributor d\r\n"
			+ "    WHERE d.groupn2 Like CONCAT('%',:search2,'%') and\r\n"
			+ "			 d.groupn3 Like CONCAT('%',:search3,'%')"
			+ " ) AS combinedResults\r\n"
			+ " ORDER BY id", nativeQuery = true)
List<IndexGroupn> getAllRetailerAndDistributorWithGroupn2AndGroupn3Is( String search2,String search3);
	
 // for  g2 and g3 and g4
	@Query(value = "SELECT * FROM (\r\n"
			+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
			+ "    FROM retailer r\r\n"
			+ "    WHERE r.groupn2 Like CONCAT('%',:search2,'%') and\r\n"
			+ " 		 r.groupn3 Like CONCAT('%',:search3,'%') and "
			+ " 		 r.groupn4 Like CONCAT('%',:search4,'%') "
			+ "    UNION ALL\r\n"
			+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
			+ "    FROM distributor d\r\n"
			+ "    WHERE d.groupn2 Like CONCAT('%',:search2,'%') and\r\n"
			+ "			 d.groupn3 Like CONCAT('%',:search3,'%') and"
			+ "			 d.groupn4 Like CONCAT('%',:search4,'%')"
			+ " ) AS combinedResults\r\n"
			+ " ORDER BY id", nativeQuery = true)
List<IndexGroupn> getAllRetailerAndDistributorWithGroupn2AndGroupn3andGroupn4Is( String search2,String search3,String search4);

	
	 // for  g2 and g3 and g4 and g5
		@Query(value = "SELECT * FROM (\r\n"
				+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
				+ "    FROM retailer r\r\n"
				+ "    WHERE r.groupn2 Like CONCAT('%',:search2,'%') and\r\n"
				+ " 		 r.groupn3 Like CONCAT('%',:search3,'%') and "
				+ " 		 r.groupn4 Like CONCAT('%',:search4,'%') and"
				+ " 		 r.groupn5 Like CONCAT('%',:search5,'%') "
				+ "    UNION ALL\r\n"
				+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
				+ "    FROM distributor d\r\n"
				+ "    WHERE d.groupn2 Like CONCAT('%',:search2,'%') and\r\n"
				+ "			 d.groupn3 Like CONCAT('%',:search3,'%') and"
				+ "			 d.groupn4 Like CONCAT('%',:search4,'%') and"
				+ "			 d.groupn5 Like CONCAT('%',:search5,'%')"
				+ " ) AS combinedResults\r\n"
				+ " ORDER BY id", nativeQuery = true)
	List<IndexGroupn> getAllRetailerAndDistributorWithGroupn2AndGroupn3AndGroupn4AndGroupn5Is( String search2,String search3,String search4,String search5);
		
//	333333333333333333333333333333333333333333333333333333
		
		
//		for g3 and g4 
		@Query(value = "SELECT * FROM (\r\n"
				+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
				+ "    FROM retailer r\r\n"
				+ "    WHERE r.groupn3 Like CONCAT('%',:search3,'%') and\r\n"
				+ " 		 r.groupn4 Like CONCAT('%',:search4,'%') "
				+ "    UNION ALL\r\n"
				+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
				+ "    FROM distributor d\r\n"
				+ "    WHERE d.groupn3 Like CONCAT('%',:search3,'%') and\r\n"
				+ "			 d.groupn4 Like CONCAT('%',:search4,'%')"
				+ " ) AS combinedResults\r\n"
				+ " ORDER BY id", nativeQuery = true)
	List<IndexGroupn> getAllRetailerAndDistributorWithGroupn3AndGroupn4Is( String search3,String search4);
//		for g3 and g4 and g5
		@Query(value = "SELECT * FROM (\r\n"
				+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
				+ "    FROM retailer r\r\n"
				+ "    WHERE r.groupn3 Like CONCAT('%',:search3,'%') and\r\n"
				+ " 		 r.groupn4 Like CONCAT('%',:search4,'%') and"
				+ " 		 r.groupn5 Like CONCAT('%',:search5,'%') "
				+ "    UNION ALL\r\n"
				+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
				+ "    FROM distributor d\r\n"
				+ "    WHERE d.groupn3 Like CONCAT('%',:search3,'%') and\r\n"
				+ "			 d.groupn4 Like CONCAT('%',:search4,'%') and"
				+ "			 d.groupn5 Like CONCAT('%',:search5,'%')"
				+ " ) AS combinedResults\r\n"
				+ " ORDER BY id", nativeQuery = true)
	List<IndexGroupn> getAllRetailerAndDistributorWithGroupn3AndGroupn4AndGroupn5Is( String search3,String search4,String search5);
		
//		for g4 and g5
		@Query(value = "SELECT * FROM (\r\n"
				+ "    SELECT r.id, 'ROLE_RETAILER' AS rolename\r\n"
				+ "    FROM retailer r\r\n"
				+ "    WHERE r.groupn4 Like CONCAT('%',:search4,'%') and\r\n"
				+ " 		 r.groupn5 Like CONCAT('%',:search5,'%') "
				+ "    UNION ALL\r\n"
				+ "    SELECT d.id, 'ROLE_DISTRIBUTOR' AS rolename\r\n"
				+ "    FROM distributor d\r\n"
				+ "    WHERE d.groupn4 Like CONCAT('%',:search4,'%') and\r\n"
				+ "			 d.groupn5 Like CONCAT('%',:search5,'%') "
				+ " ) AS combinedResults\r\n"
				+ " ORDER BY id", nativeQuery = true)
	List<IndexGroupn> getAllRetailerAndDistributorWithGroupn4AndGroupn5Is( String search4,String search5);
 //	&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

//	when search2 and search3 and search4 is there
	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn1,r.groupn2,r.groupn3,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where"
			+ " r.groupn2 Like CONCAT('%',:search2,'%') AND"
			+ " r.groupn3 Like CONCAT('%',:search3,'%') AND"
			+ " r.groupn4 Like CONCAT('%',:search4,'%')"
			+"  ",nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn2AndGroupn3AndGroup4Is(Pageable p,String search2,String search3,String search4);
//	when search2 and search3 and search4 and search5 is there
	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn1,r.groupn2,r.groupn3,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where"
			+ " r.groupn2 Like CONCAT('%',:search2,'%') AND"
			+ " r.groupn3 Like CONCAT('%',:search3,'%') AND"
			+ " r.groupn4 Like CONCAT('%',:search4,'%') AND"
			+ " r.groupn5 Like CONCAT('%',:search5,'%')"

			+"  ",nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn2AndGroupn3AndGroupn4AndGroup5Is(Pageable p,String search2,String search3,String search4,String search5);
	
	
	
	
	
//	when search3 and search4  is there
	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn1,r.groupn2,r.groupn3,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where"
 			+ " r.groupn3 Like CONCAT('%',:search3,'%') AND"
			+ " r.groupn4 Like CONCAT('%',:search4,'%')"
			+"  ",nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn3AndGroupn4Is(Pageable p,String search3,String search4);
	
//	when search3 and search4  and search5 is there
	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn1,r.groupn2,r.groupn3,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where"
 			+ " r.groupn3 Like CONCAT('%',:search3,'%') AND"
			+ " r.groupn4 Like CONCAT('%',:search4,'%') AND"
			+ " r.groupn5 Like CONCAT('%',:search5,'%')"
			+"  ",nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn3AndGroupn4AndGroupn5Is(Pageable p,String search3,String search4,String search5);
	
	
//	when search4 and search5  is there
	@Query(value = "SELECT r.id,'ROLE_RETAILER' AS rolename ,r.groupn1,r.groupn2,r.groupn3,r.trade_name as retailername,r.retailerstatus,r.city,r.gst_number,DATE_FORMAT(r.updateddate,'%d-%m-%Y') AS updateddate,r.updatedtime,r.latitude,r.longitude,"
			+ " r.box_product_discount as boxProductDiscount,r.cooker_product_discount as cookerProductDiscount ,r.corporaet_product_discount as corporaetProductDiscount,"
			+ " r.kg_product_discount as kgProductDiscount ,r.nosh_product_discount as noshProductDiscount ,"
			+ " r.scheme_discount as schemeDiscount,r.schemebox_product_discount as schemeBoxProductDiscount,"
			+ " r.schemecooker_product_discount as schemeCookerProductDiscount ,r.schemecorporate_product_discount as schemeCorporateProductDiscount,"
			+ " r.schemekg_product_discount as schemeKgProductDiscount ,r.schemenosh_product_disocunt as schemeNoshProductDisocunt,"
			+ " r.pin_code as pinCode,DATE_FORMAT(r.createddate,'%d-%m-%Y') AS createddate,r.createbyname,r.updatedbyname,r.createdtime "
			+ " from retailer r where"
 			+ " r.groupn4 Like CONCAT('%',:search4,'%') AND"
			+ " r.groupn5 Like CONCAT('%',:search5,'%')"
			+"  ",nativeQuery = true)
List<IndexRetailer> getAllRetailerWithGroupn4AndGroupn5Is(Pageable p,String search4,String search5);
}
