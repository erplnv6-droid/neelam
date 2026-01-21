package com.SCM.repository;

import com.SCM.IndexDto.IndexPrimaryOrderByDistributorId;
import com.SCM.model.PrimaryOrderItems;
import com.SCM.projection.PrimaryOrderIndexReport;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PrimaryOrderItemsRepository extends JpaRepository<PrimaryOrderItems,Integer> {
	
	@Modifying
	@Query(value = "update primary_order_items pi set pi.estimated_days = ?1 where pi.poid =?2", nativeQuery = true)
	@Transactional
	public void updatePrimaryOrderEstimatedDaysById(Date days, int id);
	
	
	@Query(value="select pi.poid as id,pi.net_amount as netamount ,pi.cgst_ledger as cgstledger,pi.discount as discount,\r\n"
			+ "pi.discount1 as discount1,pi.dlp as dlp ,pi.estimated_days as estimateddays,pi.grossamount as grossamount,\r\n"
			+ "pi.gstvalue as gstvalue,pi.igst as igst,pi.igst_ledger as igstledger,pi.measurement as measurement,\r\n"
			+ "pi.mrp as mrp ,pi.product_id as productid,pi.product_type as producttype,pi.qty as qty,pi.quantity_placed as \r\n"
			+ "qtyplaced,pi.quantity_placed_kg as qtyplacedkg,pi.sgst_ledger as sgstledger,pi.standard_qty_per_box as standardqtyperbox,pi.text as text\r\n"
			+ ",pi.total as total,pi.unitofmeasurement as unittomeasurement,pi.uom_primary as uomprimary,pi.primaryorder_id\r\n"
			+ "as pid,pi.product_name as productname,\r\n"
			+ "pi.rate as rate from primary_order_items as pi  left join\r\n"
			+ "primary_order as p on pi.primaryorder_id=p.id\r\n"
			+ "where p.date_created between '2023-08-04'and'2023-08-05' and p.distrubator_id=4"
			,nativeQuery = true)
	List<IndexPrimaryOrderByDistributorId> getAllPoByDistId();
	
	
//	@Query(value="select p.work_order_id as workorderid, pi.poid as id,pi.net_amount as netamount ,pi.cgst_ledger as cgstledger,pi.discount as discount,\r\n"
//			+ "pi.discount1 as discount1,pi.dlp as dlp ,pi.estimated_days as estimateddays,pi.grossamount as grossamount,\r\n"
//			+ "pi.gstvalue as gstvalue,pi.igst as igst,pi.igst_ledger as igstledger,pi.measurement as measurement,\r\n"
//			+ "pi.mrp as mrp ,pi.product_id as productid,pi.product_type as producttype,pi.qty as qty,pi.quantity_placed as \r\n"
//			+ "qtyplaced,pi.quantity_placed_kg as qtyplacedkg,pi.sgst_ledger as sgstledger,pi.standard_qty_per_box as standardqtyperbox,pi.text as text\r\n"
//			+ ",pi.total as total,pi.unitofmeasurement as unittomeasurement,pi.uom_primary as uomprimary,pi.primaryorder_id\r\n"
//			+ "as pid,pi.product_name as productname\r\n"
//			+ "from primary_order_items as pi  left join\r\n"
//			+ "primary_order as p on pi.primaryorder_id=p.id\r\n"
//			+ "where p.date_created between ?1 and ?2 and p.distrubator_id=?3"
//			,nativeQuery = true)
//	List<IndexPrimaryOrderByDistributorId> getPoitemsByDistributorId(String startdate,String enddate,int distid);
	
	
	
//	@Query(value="select p.work_order_id as workorderid, pi.poid as id,pi.net_amount as netamount ,pi.cgst_ledger as cgstledger,pi.discount as discount,\r\n"
//			+ "pi.discount1 as discount1,pi.dlp as dlp ,pi.estimated_days as estimateddays,pi.grossamount as grossamount,\r\n"
//			+ "pi.gstvalue as gstvalue,pi.igst as igst,pi.igst_ledger as igstledger,pi.measurement as measurement,\r\n"
//			+ "pi.mrp as mrp ,pi.product_id as productid,pi.product_type as producttype,pi.qty as qty,pi.quantity_placed as \r\n"
//			+ "qtyplaced,pi.quantity_placed_kg as qtyplacedkg,pi.sgst_ledger as sgstledger,pi.standard_qty_per_box as standardqtyperbox,pi.text as text\r\n"
//			+ ",pi.total as total,pi.unitofmeasurement as unittomeasurement,pi.uom_primary as uomprimary,pi.primaryorder_id\r\n"
//			+ "as pid,pi.product_name as productname\r\n"
//			+ "from primary_order_items as pi  left join\r\n"
//			+ "primary_order as p on pi.primaryorder_id=p.id\r\n"
//			+ "where p.date_created between ?1 and ?2 and p.distrubator_id=?3"
//			,nativeQuery = true)
//	List<IndexPrimaryOrderByDistributorId> getPoitemsByDistributorIdPagination(String startdate,String enddate,int distid,Pageable p);
	
	
//	@Query(value="select p.id as orderid,pi.net_amount as taxablevalue ,pi.grossamount as grossamount,\r\n"
//			+ "			pi.gstvalue as taxvalue,pi.total as totalvalue,p.createddate as ordercreateddate,p.date_created as orderdate,d.trade_name as tradename,d.city as city,st.name as state,z.zone_name as zone,group_concat(s.staff_name) as rsmname,\r\n"
//			+ "            group_concat(s1.staff_name) as asename,pt.ean_code as eancode,pt.product_group as productgroup,pi.quantity_placed as qtyprm,pi.quantity_placed_kg as qtyalt,pi.dlp as rate,\r\n"
//			+ "            pi.discount as 1stdis,pi.discount1 as 2nddis,p.createbyname as createdby,p.createdtime as createdtime\r\n"
//			+ "			,pi.product_name as productname\r\n"
//			+ "			from primary_order_items as pi left join\r\n"
//			+ "			primary_order as p on pi.primaryorder_id=p.id\r\n"
//			+ "            left join distributor as d on p.distrubator_id=d.id\r\n"
//			+ "            left join zone as z on d.zonesid=z.id\r\n"
//			+ "            left join states as st on d.stateid=st.id\r\n"
//			+ "            left join product as pt on pi.product_id=pt.id\r\n"
//			+ "            left join distributor_to_staff as ds on d.id=ds.distributor_id\r\n"
//			+ "			left join staff as s on ds.rsmid=s.id\r\n"
//			+ "            left join staff as s1 on ds.aseid=s1.id\r\n"
//			+ "            where p.date_created between ?1 and ?2 and p.distrubator_id=?3 and COALESCE(createddate, date_created) IS NOT NULL group by p.id ,pi.net_amount,pi.grossamount,\r\n"
//			+ "			pi.gstvalue,pi.total,p.createddate,p.date_created,d.trade_name,d.city,st.name,z.zone_name,\r\n"
//			+ "pt.ean_code,pt.product_group,pi.quantity_placed,pi.quantity_placed_kg,pi.dlp,\r\n"
//			+ "            pi.discount,pi.discount1,p.createbyname,p.createdtime\r\n"
//			+ "			,pi.product_name"
//			,nativeQuery = true)
//	List<IndexPrimaryOrderByDistributorId> getPoitemsByDistributorId(String startdate,String enddate,int distid);
	
	
//	@Query(value = "SELECT p.id AS orderid, pi.net_amount AS taxablevalue, pi.grossamount AS grossamount, "
//            + "pi.gstvalue AS taxvalue, pi.total AS totalvalue, p.createddate AS ordercreateddate, p.date_created AS orderdate, "
//            + "d.trade_name AS tradename, d.city AS city, st.name AS state, z.zone_name AS zone, "
//            + "GROUP_CONCAT(s.staff_name) AS rsmname, GROUP_CONCAT(s1.staff_name) AS asename, "
//            + "pt.ean_code AS eancode, pt.product_group AS productgroup, pi.quantity_placed AS qtyprm, "
//            + "pi.quantity_placed_kg AS qtyalt, pi.dlp AS rate, pi.discount AS first_dis, pi.discount1 AS second_dis, "
//            + "p.createbyname AS createdby, p.createdtime AS createdtime, pi.product_name AS productname "
//            + "FROM scm.primary_order_items AS pi "
//            + "LEFT JOIN scm.primary_order AS p ON pi.primaryorder_id = p.id "
//            + "LEFT JOIN scm.distributor AS d ON p.distrubator_id = d.id "
//            + "LEFT JOIN scm.zone AS z ON d.zonesid = z.id "
//            + "LEFT JOIN scm.states AS st ON d.stateid = st.id "
//            + "LEFT JOIN scm.product AS pt ON pi.product_id = pt.id "
//            + "LEFT JOIN scm.distributor_to_staff AS ds ON d.id = ds.distributor_id "
//            + "LEFT JOIN scm.staff AS s ON ds.rsmid = s.id "
//            + "LEFT JOIN scm.staff AS s1 ON ds.aseid = s1.id "
//            + "WHERE p.distrubator_id = :distid "
//            + "AND COALESCE(p.createddate, p.date_created) IS NOT NULL AND COALESCE(p.createddate, p.date_created) BETWEEN :startdate AND :enddate "
//            + "GROUP BY p.id, pi.net_amount, pi.grossamount, pi.gstvalue, pi.total, p.createddate, p.date_created, "
//            + "d.trade_name, d.city, st.name, z.zone_name, pt.ean_code, pt.product_group, pi.quantity_placed, "
//            + "pi.quantity_placed_kg, pi.dlp, pi.discount, pi.discount1, p.createbyname, p.createdtime, pi.product_name", 
// nativeQuery = true)
//List<IndexPrimaryOrderByDistributorId> getPoitemsByDistributorId(String startdate,String enddate,int distid);
	
	@Query(value = "SELECT " +
		    "p.id AS orderid, " +
		    "pi.net_amount AS taxablevalue, " +
		    "pi.grossamount AS grossamount, " +
		    "pi.gstvalue AS taxvalue, " +
		    "pi.total AS totalvalue, " +
		    "p.createddate AS ordercreateddate, " +
		    "p.date_created AS orderdate, " +
		    "d.trade_name AS tradename, " +
		    "d.city AS city, " +
		    "st.name AS state, " +
		    "z.zone_name AS zone, " +
		    "GROUP_CONCAT(s.staff_name) AS rsmname, " +
		    "GROUP_CONCAT(s1.staff_name) AS asename, " +
		    "pt.ean_code AS eancode, " +
		    "pt.product_group AS productgroup, " +
		    "pi.quantity_placed AS qtyprm, " +
		    "pi.quantity_placed_kg AS qtyalt, " +
		    "pi.dlp AS rate, " +
		    "pi.discount AS first_dis, " +
		    "pi.discount1 AS second_dis, " +
		    "p.createbyname AS createdby, " +
		    "p.createdtime AS createdtime, " +
		    "pi.product_name AS productname " +
		"FROM scm.primary_order_items AS pi " +
		"LEFT JOIN scm.primary_order AS p ON pi.primaryorder_id = p.id " +
		"LEFT JOIN scm.distributor AS d ON p.distrubator_id = d.id " +
		"LEFT JOIN scm.zone AS z ON d.zonesid = z.id " +
		"LEFT JOIN scm.states AS st ON d.stateid = st.id " +
		"LEFT JOIN scm.product AS pt ON pi.product_id = pt.id " +
		"LEFT JOIN scm.distributor_to_staff AS ds ON d.id = ds.distributor_id " +
		"LEFT JOIN scm.staff AS s ON ds.rsmid = s.id " +
		"LEFT JOIN scm.staff AS s1 ON ds.aseid = s1.id " +
		"WHERE p.distrubator_id = :distid " +
		"AND (p.createddate IS NOT NULL OR p.date_created IS NOT NULL) " +
		"AND COALESCE(p.createddate, p.date_created) BETWEEN :startdate AND :enddate " +
		"GROUP BY " +
		"p.id, " +
		"pi.net_amount, " +
		"pi.grossamount, " +
		"pi.gstvalue, " +
		"pi.total, " +
		"p.createddate, " +
		"p.date_created, " +
		"d.trade_name, " +
		"d.city, " +
		"st.name, " +
		"z.zone_name, " +
		"pt.ean_code, " +
		"pt.product_group, " +
		"pi.quantity_placed, " +
		"pi.quantity_placed_kg, " +
		"pi.dlp, " +
		"pi.discount, " +
		"pi.discount1, " +
		"p.createbyname, " +
		"p.createdtime, " +
		"pi.product_name " +
		"ORDER BY p.id ASC", 
		nativeQuery = true)
		List<IndexPrimaryOrderByDistributorId> getPoitemsByDistributorId(String startdate, 
		                                                                 String enddate, 
		                                                                 int distid);



	
	
//	@Query(value="select p.id as orderid,pi.net_amount as taxablevalue ,pi.grossamount as grossamount,\r\n"
//			+ "			pi.gstvalue as taxvalue,pi.total as totalvalue,p.createddate as ordercreateddate,p.date_created as orderdate,d.trade_name as tradename,d.city as city,st.name as state,z.zone_name as zone,group_concat(s.staff_name) as rsmname,\r\n"
//			+ "            group_concat(s1.staff_name) as asename,pt.ean_code as eancode,pt.product_group as productgroup,pi.quantity_placed as qtyprm,pi.quantity_placed_kg as qtyalt,pi.dlp as rate,\r\n"
//			+ "            pi.discount as 1stdis,pi.discount1 as 2nddis,p.createbyname as createdby,p.createdtime as createdtime\r\n"
//			+ "			,pi.product_name as productname\r\n"
//			+ "			from primary_order_items as pi left join\r\n"
//			+ "			primary_order as p on pi.primaryorder_id=p.id\r\n"
//			+ "            left join distributor as d on p.distrubator_id=d.id\r\n"
//			+ "            left join zone as z on d.zonesid=z.id\r\n"
//			+ "            left join states as st on d.stateid=st.id\r\n"
//			+ "            left join product as pt on pi.product_id=pt.id\r\n"
//			+ "            left join distributor_to_staff as ds on d.id=ds.distributor_id\r\n"
//			+ "			left join staff as s on ds.rsmid=s.id\r\n"
//			+ "            left join staff as s1 on ds.aseid=s1.id\r\n"
//			+ "            where p.date_created between ?1 and ?2 and p.distrubator_id=?3 group by p.id ,pi.net_amount,pi.grossamount,\r\n"
//			+ "			pi.gstvalue,pi.total,p.createddate,p.date_created,d.trade_name,d.city,st.name,z.zone_name,\r\n"
//			+ "pt.ean_code,pt.product_group,pi.quantity_placed,pi.quantity_placed_kg,pi.dlp,\r\n"
//			+ "            pi.discount,pi.discount1,p.createbyname,p.createdtime\r\n"
//			+ "			,pi.product_name"
//			,nativeQuery = true)
//	List<IndexPrimaryOrderByDistributorId> getPoitemsByDistributorIdPagination(String startdate,String enddate,int distid,Pageable p);
	

	@Query(value = "SELECT p.id AS orderid, " +
	        "pi.net_amount AS taxablevalue, " +
	        "pi.grossamount AS grossamount, " +
	        "pi.gstvalue AS taxvalue, " +
	        "pi.total AS totalvalue, " +
	        "p.createddate AS ordercreateddate, " +
	        "p.date_created AS orderdate, " +
	        "d.trade_name AS tradename, " +
	        "d.city AS city, " +
	        "st.name AS state, " +
	        "z.zone_name AS zone, " +
	        "GROUP_CONCAT(s.staff_name) AS rsmname, " +
	        "GROUP_CONCAT(s1.staff_name) AS asename, " +
	        "pt.ean_code AS eancode, " +
	        "pt.product_group AS productgroup, " +
	        "pi.quantity_placed AS qtyprm, " +
	        "pi.quantity_placed_kg AS qtyalt, " +
	        "pi.dlp AS rate, " +
	        "pi.discount AS first_dis, " +
	        "pi.discount1 AS second_dis, " +
	        "p.createbyname AS createdby, " +
	        "p.createdtime AS createdtime, " +
	        "pi.product_name AS productname " +
	    "FROM primary_order_items AS pi " +
	    "LEFT JOIN primary_order AS p ON pi.primaryorder_id = p.id " +
	    "LEFT JOIN distributor AS d ON p.distrubator_id = d.id " +
	    "LEFT JOIN zone AS z ON d.zonesid = z.id " +
	    "LEFT JOIN states AS st ON d.stateid = st.id " +
	    "LEFT JOIN product AS pt ON pi.product_id = pt.id " +
	    "LEFT JOIN distributor_to_staff AS ds ON d.id = ds.distributor_id " +
	    "LEFT JOIN staff AS s ON ds.rsmid = s.id " +
	    "LEFT JOIN staff AS s1 ON ds.aseid = s1.id " +
	    "WHERE p.distrubator_id = :distid " +
	    "AND COALESCE(p.createddate, p.date_created) IS NOT NULL " +
	    "AND COALESCE(p.createddate, p.date_created) BETWEEN :startdate AND :enddate " +
	    "GROUP BY p.id, pi.net_amount, pi.grossamount, pi.gstvalue, pi.total, p.createddate, p.date_created, " +
	    "d.trade_name, d.city, st.name, z.zone_name, pt.ean_code, pt.product_group, pi.quantity_placed, " +
	    "pi.quantity_placed_kg, pi.dlp, pi.discount, pi.discount1, p.createbyname, p.createdtime, pi.product_name ORDER BY p.id ASC",
	    nativeQuery = true)
	List<IndexPrimaryOrderByDistributorId> getPoitemsByDistributorIdPagination(String startdate, String enddate, int distid, Pageable p);

	
	
	@Query(value = "SELECT p.id AS orderid, " +
	        "pi.net_amount AS taxablevalue, " +
	        "pi.grossamount AS grossamount, " +
	        "pi.gstvalue AS taxvalue, " +
	        "pi.total AS totalvalue, " +
	        "p.createddate AS ordercreateddate, " +
	        "p.date_created AS orderdate, " +
	        "d.trade_name AS tradename, " +
	        "d.city AS city, " +
	        "st.name AS state, " +
	        "z.zone_name AS zone, " +
	        "GROUP_CONCAT(s.staff_name) AS rsmname, " +
	        "GROUP_CONCAT(s1.staff_name) AS asename, " +
	        "pt.ean_code AS eancode, " +
	        "pt.product_group AS productgroup, " +
	        "pi.quantity_placed AS qtyprm, " +
	        "pi.quantity_placed_kg AS qtyalt, " +
	        "pi.dlp AS rate, " +
	        "pi.discount AS first_dis, " +
	        "pi.discount1 AS second_dis, " +
	        "p.createbyname AS createdby, " +
	        "p.createdtime AS createdtime, " +
	        "pi.product_name AS productname " +
	    "FROM primary_order_items AS pi " +
	    "LEFT JOIN primary_order AS p ON pi.primaryorder_id = p.id " +
	    "LEFT JOIN distributor AS d ON p.distrubator_id = d.id " +
	    "LEFT JOIN zone AS z ON d.zonesid = z.id " +
	    "LEFT JOIN states AS st ON d.stateid = st.id " +
	    "LEFT JOIN product AS pt ON pi.product_id = pt.id " +
	    "LEFT JOIN distributor_to_staff AS ds ON d.id = ds.distributor_id " +
	    "LEFT JOIN staff AS s ON ds.rsmid = s.id " +
	    "LEFT JOIN staff AS s1 ON ds.aseid = s1.id " +
	    "WHERE p.distrubator_id = :distid " +
	    "AND COALESCE(p.createddate, p.date_created) IS NOT NULL " +
	    "AND COALESCE(p.createddate, p.date_created) BETWEEN :startdate AND :enddate " +
	    "GROUP BY p.id, pi.net_amount, pi.grossamount, pi.gstvalue, pi.total, p.createddate, p.date_created, " +
	    "d.trade_name, d.city, st.name, z.zone_name, pt.ean_code, pt.product_group, pi.quantity_placed, " +
	    "pi.quantity_placed_kg, pi.dlp, pi.discount, pi.discount1, p.createbyname, p.createdtime, pi.product_name",
	    nativeQuery = true)
	List<IndexPrimaryOrderByDistributorId> getPoitemsByDistributorIdPaginationRsm(
	        String startdate,
	        String enddate,
	        int distid,
	        Pageable p
	);

	
	
	@Query(value = "SELECT p.id AS orderid, " +
	        "pi.net_amount AS taxablevalue, " +
	        "pi.grossamount AS grossamount, " +
	        "pi.gstvalue AS taxvalue, " +
	        "pi.total AS totalvalue, " +
	        "p.createddate AS ordercreateddate, " +
	        "p.date_created AS orderdate, " +
	        "d.trade_name AS tradename, " +
	        "d.city AS city, " +
	        "st.name AS state, " +
	        "z.zone_name AS zone, " +
	        "GROUP_CONCAT(s.staff_name) AS rsmname, " +
	        "GROUP_CONCAT(s1.staff_name) AS asename, " +
	        "pt.ean_code AS eancode, " +
	        "pt.product_group AS productgroup, " +
	        "pi.quantity_placed AS qtyprm, " +
	        "pi.quantity_placed_kg AS qtyalt, " +
	        "pi.dlp AS rate, " +
	        "pi.discount AS first_dis, " +
	        "pi.discount1 AS second_dis, " +
	        "p.createbyname AS createdby, " +
	        "p.createdtime AS createdtime, " +
	        "pi.product_name AS productname " +
	    "FROM primary_order_items AS pi " +
	    "LEFT JOIN primary_order AS p ON pi.primaryorder_id = p.id " +
	    "LEFT JOIN distributor AS d ON p.distrubator_id = d.id " +
	    "LEFT JOIN zone AS z ON d.zonesid = z.id " +
	    "LEFT JOIN states AS st ON d.stateid = st.id " +
	    "LEFT JOIN product AS pt ON pi.product_id = pt.id " +
	    "LEFT JOIN distributor_to_staff AS ds ON d.id = ds.distributor_id " +
	    "LEFT JOIN staff AS s ON ds.rsmid = s.id " +
	    "LEFT JOIN staff AS s1 ON ds.aseid = s1.id " +
	    "LEFT JOIN staff AS s2 ON ds.asmid = s2.id " +
	    "WHERE p.distrubator_id = :distid " +
	    "AND COALESCE(p.createddate, p.date_created) IS NOT NULL " +
	    "AND COALESCE(p.createddate, p.date_created) BETWEEN :startdate AND :enddate " +
	    "GROUP BY p.id, pi.net_amount, pi.grossamount, pi.gstvalue, pi.total, p.createddate, p.date_created, " +
	    "d.trade_name, d.city, st.name, z.zone_name, pt.ean_code, pt.product_group, pi.quantity_placed, " +
	    "pi.quantity_placed_kg, pi.dlp, pi.discount, pi.discount1, p.createbyname, p.createdtime, pi.product_name",
	    nativeQuery = true)
	List<IndexPrimaryOrderByDistributorId> getPoitemsByDistributorIdPaginationAsm(String startdate, String enddate, int distid, Pageable p);
	
	
	
	@Query(value = "SELECT p.id AS orderid, " +
	        "pi.net_amount AS taxablevalue, " +
	        "pi.grossamount AS grossamount, " +
	        "pi.gstvalue AS taxvalue, " +
	        "pi.total AS totalvalue, " +
	        "p.createddate AS ordercreateddate, " +
	        "p.date_created AS orderdate, " +
	        "d.trade_name AS tradename, " +
	        "d.city AS city, " +
	        "st.name AS state, " +
	        "z.zone_name AS zone, " +
	        "GROUP_CONCAT(s.staff_name) AS rsmname, " +
	        "GROUP_CONCAT(s1.staff_name) AS asename, " +
	        "pt.ean_code AS eancode, " +
	        "pt.product_group AS productgroup, " +
	        "pi.quantity_placed AS qtyprm, " +
	        "pi.quantity_placed_kg AS qtyalt, " +
	        "pi.dlp AS rate, " +
	        "pi.discount AS first_dis, " +
	        "pi.discount1 AS second_dis, " +
	        "p.createbyname AS createdby, " +
	        "p.createdtime AS createdtime, " +
	        "pi.product_name AS productname " +
	    "FROM primary_order_items AS pi " +
	    "LEFT JOIN primary_order AS p ON pi.primaryorder_id = p.id " +
	    "LEFT JOIN distributor AS d ON p.distrubator_id = d.id " +
	    "LEFT JOIN zone AS z ON d.zonesid = z.id " +
	    "LEFT JOIN states AS st ON d.stateid = st.id " +
	    "LEFT JOIN product AS pt ON pi.product_id = pt.id " +
	    "LEFT JOIN distributor_to_staff AS ds ON d.id = ds.distributor_id " +
	    "LEFT JOIN staff AS s ON ds.rsmid = s.id " +
	    "LEFT JOIN staff AS s1 ON ds.aseid = s1.id " +
        "WHERE p.distrubator_id = :distid " +
	    "AND COALESCE(p.createddate, p.date_created) IS NOT NULL " +
	    "AND COALESCE(p.createddate, p.date_created) BETWEEN :startdate AND :enddate " +
	    "GROUP BY p.id, pi.net_amount, pi.grossamount, pi.gstvalue, pi.total, p.createddate, p.date_created, " +
	    "d.trade_name, d.city, st.name, z.zone_name, pt.ean_code, pt.product_group, pi.quantity_placed, " +
	    "pi.quantity_placed_kg, pi.dlp, pi.discount, pi.discount1, p.createbyname, p.createdtime, pi.product_name",
	    nativeQuery = true)
	List<IndexPrimaryOrderByDistributorId> getPoitemsByDistributorIdPaginationAse(String startdate, String enddate, int distid, Pageable p);
	
//	@Query(value = "SELECT p.id AS orderid, pi.net_amount AS taxablevalue, pi.grossamount AS grossamount, " +
//            "pi.gstvalue AS taxvalue, pi.total AS totalvalue, p.createddate AS ordercreateddate, " +
//            "p.date_created AS orderdate, d.trade_name AS tradename, d.city AS city, st.name AS state, " +
//            "z.zone_name AS zone, GROUP_CONCAT(s.staff_name) AS rsmname, GROUP_CONCAT(s1.staff_name) AS asename, " +
//            "pt.ean_code AS eancode, pt.product_group AS productgroup, pi.quantity_placed AS qtyprm, " +
//            "pi.quantity_placed_kg AS qtyalt, pi.dlp AS rate, pi.discount AS 1stdis, pi.discount1 AS 2nddis, " +
//            "p.createbyname AS createdby, p.createdtime AS createdtime, pi.product_name AS productname " +
//            "FROM primary_order_items AS pi " +
//            "LEFT JOIN primary_order AS p ON pi.primaryorder_id = p.id " +
//            "LEFT JOIN distributor AS d ON p.distrubator_id = d.id " +
//            "LEFT JOIN zone AS z ON d.zonesid = z.id " +
//            "LEFT JOIN states AS st ON d.stateid = st.id " +
//            "LEFT JOIN product AS pt ON pi.product_id = pt.id " +
//            "LEFT JOIN distributor_to_staff AS ds ON d.id = ds.distributor_id " +
//            "LEFT JOIN staff AS s ON ds.rsmid = s.id " +
//            "LEFT JOIN staff AS s1 ON ds.aseid = s1.id " +
//            "WHERE p.distrubator_id = :distid " +
//    	    "AND COALESCE(p.createddate, p.date_created) IS NOT NULL " +
//    	    "AND COALESCE(p.createddate, p.date_created) BETWEEN :startdate AND :enddate " +
//            "AND (" +
//            "p.id LIKE CONCAT('%', ?4, '%') " +
//            "OR pi.net_amount LIKE CONCAT('%', ?4, '%') " +
//            "OR pt.product_group LIKE CONCAT('%', ?4, '%') " +
//            "OR pt.product_name LIKE CONCAT('%', ?4, '%') " +
//            "OR d.trade_name LIKE CONCAT('%', ?4, '%') " +
//            "OR s.staff_name LIKE CONCAT('%', ?4, '%') " +
//            "OR s1.staff_name LIKE CONCAT('%', ?4, '%')) " +
//            "GROUP BY p.id, pi.net_amount, pi.grossamount, pi.gstvalue, pi.total, p.createddate, p.date_created, " +
//            "d.trade_name, d.city, st.name, z.zone_name, pt.ean_code, pt.product_group, pi.quantity_placed, " +
//            "pi.quantity_placed_kg, pi.dlp, pi.discount, pi.discount1, p.createbyname, p.createdtime, pi.product_name", 
//            nativeQuery = true)
//List<IndexPrimaryOrderByDistributorId> searchPoitemsByDistributorIdPagination(String startdate, String enddate, int distid, String search, Pageable p);

	
	
	@Query(value = "SELECT p.id AS orderid, pi.net_amount AS taxablevalue, pi.grossamount AS grossamount, " +
	        "pi.gstvalue AS taxvalue, pi.total AS totalvalue, p.createddate AS ordercreateddate, " +
	        "p.date_created AS orderdate, d.trade_name AS tradename, d.city AS city, st.name AS state, " +
	        "z.zone_name AS zone, GROUP_CONCAT(s.staff_name) AS rsmname, GROUP_CONCAT(s1.staff_name) AS asename, " +
	        "pt.ean_code AS eancode, pt.product_group AS productgroup, pi.quantity_placed AS qtyprm, " +
	        "pi.quantity_placed_kg AS qtyalt, pi.dlp AS rate, pi.discount AS 1stdis, pi.discount1 AS 2nddis, " +
	        "p.createbyname AS createdby, p.createdtime AS createdtime, pi.product_name AS productname " +
	        "FROM primary_order_items AS pi " +
	        "LEFT JOIN primary_order AS p ON pi.primaryorder_id = p.id " +
	        "LEFT JOIN distributor AS d ON p.distrubator_id = d.id " +
	        "LEFT JOIN zone AS z ON d.zonesid = z.id " +
	        "LEFT JOIN states AS st ON d.stateid = st.id " +
	        "LEFT JOIN product AS pt ON pi.product_id = pt.id " +
	        "LEFT JOIN distributor_to_staff AS ds ON d.id = ds.distributor_id " +
	        "LEFT JOIN staff AS s ON ds.rsmid = s.id " +
	        "LEFT JOIN staff AS s1 ON ds.aseid = s1.id " +
	        "WHERE p.distrubator_id = :distid " +
	        "AND COALESCE(p.createddate, p.date_created) IS NOT NULL " +
	        "AND COALESCE(p.createddate, p.date_created) BETWEEN :startdate AND :enddate " +
	        "AND (" +
	        "p.id LIKE CONCAT('%', :search, '%') " +
	        "OR pi.net_amount LIKE CONCAT('%', :search, '%') " +
	        "OR pt.product_group LIKE CONCAT('%', :search, '%') " +
	        "OR pt.product_name LIKE CONCAT('%', :search, '%') " +
	        "OR d.trade_name LIKE CONCAT('%', :search, '%') " +
	        "OR s.staff_name LIKE CONCAT('%', :search, '%') " +
	        "OR s1.staff_name LIKE CONCAT('%', :search, '%')) " +
	        "GROUP BY p.id, pi.net_amount, pi.grossamount, pi.gstvalue, pi.total, p.createddate, p.date_created, " +
	        "d.trade_name, d.city, st.name, z.zone_name, pt.ean_code, pt.product_group, pi.quantity_placed, " +
	        "pi.quantity_placed_kg, pi.dlp, pi.discount, pi.discount1, p.createbyname, p.createdtime, pi.product_name", 
	        nativeQuery = true)
	List<IndexPrimaryOrderByDistributorId> searchPoitemsByDistributorIdPagination(String startdate, String enddate, int distid, String search, Pageable p);


	@Query(value = "SELECT p.id AS orderid, pi.net_amount AS taxablevalue, pi.grossamount AS grossamount, " +
	        "pi.gstvalue AS taxvalue, pi.total AS totalvalue, p.createddate AS ordercreateddate, " +
	        "p.date_created AS orderdate, d.trade_name AS tradename, d.city AS city, st.name AS state, " +
	        "z.zone_name AS zone, GROUP_CONCAT(s.staff_name) AS rsmname, GROUP_CONCAT(s1.staff_name) AS asename, " +
	        "pt.ean_code AS eancode, pt.product_group AS productgroup, pi.quantity_placed AS qtyprm, " +
	        "pi.quantity_placed_kg AS qtyalt, pi.dlp AS rate, pi.discount AS 1stdis, pi.discount1 AS 2nddis, " +
	        "p.createbyname AS createdby, p.createdtime AS createdtime, pi.product_name AS productname " +
	        "FROM primary_order_items AS pi " +
	        "LEFT JOIN primary_order AS p ON pi.primaryorder_id = p.id " +
	        "LEFT JOIN distributor AS d ON p.distrubator_id = d.id " +
	        "LEFT JOIN zone AS z ON d.zonesid = z.id " +
	        "LEFT JOIN states AS st ON d.stateid = st.id " +
	        "LEFT JOIN product AS pt ON pi.product_id = pt.id " +
	        "LEFT JOIN distributor_to_staff AS ds ON d.id = ds.distributor_id " +
	        "LEFT JOIN staff AS s ON ds.rsmid = s.id " +
	        "LEFT JOIN staff AS s1 ON ds.aseid = s1.id " +
	        "WHERE p.distrubator_id = :distid " +
	        "AND COALESCE(p.createddate, p.date_created) IS NOT NULL " +
	        "AND COALESCE(p.createddate, p.date_created) BETWEEN :startdate AND :enddate " +
	        "AND (" +
	        "p.id LIKE CONCAT('%', :search, '%') " +
	        "OR pi.net_amount LIKE CONCAT('%', :search, '%') " +
	        "OR pt.product_group LIKE CONCAT('%', :search, '%') " +
	        "OR pt.product_name LIKE CONCAT('%', :search, '%') " +
	        "OR d.trade_name LIKE CONCAT('%', :search, '%') " +
	        "OR s.staff_name LIKE CONCAT('%', :search, '%') " +
	        "OR s1.staff_name LIKE CONCAT('%', :search, '%')) " +
	        "GROUP BY p.id, pi.net_amount, pi.grossamount, pi.gstvalue, pi.total, p.createddate, p.date_created, " +
	        "d.trade_name, d.city, st.name, z.zone_name, pt.ean_code, pt.product_group, pi.quantity_placed, " +
	        "pi.quantity_placed_kg, pi.dlp, pi.discount, pi.discount1, p.createbyname, p.createdtime, pi.product_name", 
	        nativeQuery = true)
	List<IndexPrimaryOrderByDistributorId> searchPoitemsByDistributorIdPaginationRsm(String startdate, String enddate, int distid, String search, Pageable p);

	@Query(value = "SELECT p.id AS orderid, pi.net_amount AS taxablevalue, pi.grossamount AS grossamount, " +
	        "pi.gstvalue AS taxvalue, pi.total AS totalvalue, p.createddate AS ordercreateddate, " +
	        "p.date_created AS orderdate, d.trade_name AS tradename, d.city AS city, st.name AS state, " +
	        "z.zone_name AS zone, GROUP_CONCAT(s.staff_name) AS rsmname, GROUP_CONCAT(s1.staff_name) AS asename, " +
	        "pt.ean_code AS eancode, pt.product_group AS productgroup, pi.quantity_placed AS qtyprm, " +
	        "pi.quantity_placed_kg AS qtyalt, pi.dlp AS rate, pi.discount AS 1stdis, pi.discount1 AS 2nddis, " +
	        "p.createbyname AS createdby, p.createdtime AS createdtime, pi.product_name AS productname " +
	        "FROM primary_order_items AS pi " +
	        "LEFT JOIN primary_order AS p ON pi.primaryorder_id = p.id " +
	        "LEFT JOIN distributor AS d ON p.distrubator_id = d.id " +
	        "LEFT JOIN zone AS z ON d.zonesid = z.id " +
	        "LEFT JOIN states AS st ON d.stateid = st.id " +
	        "LEFT JOIN product AS pt ON pi.product_id = pt.id " +
	        "LEFT JOIN distributor_to_staff AS ds ON d.id = ds.distributor_id " +
	        "LEFT JOIN staff AS s ON ds.rsmid = s.id " +
	        "LEFT JOIN staff AS s2 ON ds.asmid = s.id " +
	        "LEFT JOIN staff AS s1 ON ds.aseid = s1.id " +
	        "WHERE p.distrubator_id = :distid " +
	        "AND COALESCE(p.createddate, p.date_created) IS NOT NULL " +
	        "AND COALESCE(p.createddate, p.date_created) BETWEEN :startdate AND :enddate " +
	        "AND (" +
	        "p.id LIKE CONCAT('%', :search, '%') " +
	        "OR pi.net_amount LIKE CONCAT('%', :search, '%') " +
	        "OR pt.product_group LIKE CONCAT('%', :search, '%') " +
	        "OR pt.product_name LIKE CONCAT('%', :search, '%') " +
	        "OR d.trade_name LIKE CONCAT('%', :search, '%') " +
	        "OR s.staff_name LIKE CONCAT('%', :search, '%') " +
	        "OR s1.staff_name LIKE CONCAT('%', :search, '%')) " +
	        "GROUP BY p.id, pi.net_amount, pi.grossamount, pi.gstvalue, pi.total, p.createddate, p.date_created, " +
	        "d.trade_name, d.city, st.name, z.zone_name, pt.ean_code, pt.product_group, pi.quantity_placed, " +
	        "pi.quantity_placed_kg, pi.dlp, pi.discount, pi.discount1, p.createbyname, p.createdtime, pi.product_name", 
	        nativeQuery = true)
	List<IndexPrimaryOrderByDistributorId> searchPoitemsByDistributorIdPaginationAsm(String startdate, String enddate, int distid, String search, Pageable p);
	
	@Query(value = "SELECT p.id AS orderid, pi.net_amount AS taxablevalue, pi.grossamount AS grossamount, " +
	        "pi.gstvalue AS taxvalue, pi.total AS totalvalue, p.createddate AS ordercreateddate, " +
	        "p.date_created AS orderdate, d.trade_name AS tradename, d.city AS city, st.name AS state, " +
	        "z.zone_name AS zone, GROUP_CONCAT(s.staff_name) AS rsmname, GROUP_CONCAT(s1.staff_name) AS asename, " +
	        "pt.ean_code AS eancode, pt.product_group AS productgroup, pi.quantity_placed AS qtyprm, " +
	        "pi.quantity_placed_kg AS qtyalt, pi.dlp AS rate, pi.discount AS 1stdis, pi.discount1 AS 2nddis, " +
	        "p.createbyname AS createdby, p.createdtime AS createdtime, pi.product_name AS productname " +
	        "FROM primary_order_items AS pi " +
	        "LEFT JOIN primary_order AS p ON pi.primaryorder_id = p.id " +
	        "LEFT JOIN distributor AS d ON p.distrubator_id = d.id " +
	        "LEFT JOIN zone AS z ON d.zonesid = z.id " +
	        "LEFT JOIN states AS st ON d.stateid = st.id " +
	        "LEFT JOIN product AS pt ON pi.product_id = pt.id " +
	        "LEFT JOIN distributor_to_staff AS ds ON d.id = ds.distributor_id " +
	        "LEFT JOIN staff AS s ON ds.rsmid = s.id " +
	        "LEFT JOIN staff AS s1 ON ds.aseid = s1.id " +
	        "WHERE p.distrubator_id = :distid " +
	        "AND COALESCE(p.createddate, p.date_created) IS NOT NULL " +
	        "AND COALESCE(p.createddate, p.date_created) BETWEEN :startdate AND :enddate " +
	        "AND (" +
	        "p.id LIKE CONCAT('%', :search, '%') " +
	        "OR pi.net_amount LIKE CONCAT('%', :search, '%') " +
	        "OR pt.product_group LIKE CONCAT('%', :search, '%') " +
	        "OR pt.product_name LIKE CONCAT('%', :search, '%') " +
	        "OR d.trade_name LIKE CONCAT('%', :search, '%') " +
	        "OR s.staff_name LIKE CONCAT('%', :search, '%') " +
	        "OR s1.staff_name LIKE CONCAT('%', :search, '%')) " +
	        "GROUP BY p.id, pi.net_amount, pi.grossamount, pi.gstvalue, pi.total, p.createddate, p.date_created, " +
	        "d.trade_name, d.city, st.name, z.zone_name, pt.ean_code, pt.product_group, pi.quantity_placed, " +
	        "pi.quantity_placed_kg, pi.dlp, pi.discount, pi.discount1, p.createbyname, p.createdtime, pi.product_name", 
	        nativeQuery = true)
	List<IndexPrimaryOrderByDistributorId> searchPoitemsByDistributorIdPaginationAse(String startdate, String enddate, int distid, String search, Pageable p);
}
