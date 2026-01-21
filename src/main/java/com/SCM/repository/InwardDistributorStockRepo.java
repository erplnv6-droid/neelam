package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexInwardDitributor;
import com.SCM.model.InwardDistributorStock;
import com.SCM.model.PurchaseOrder;
import com.SCM.model.SalesReturn;
import com.SCM.model.VoucherMaster;

@Repository
public interface InwardDistributorStockRepo extends JpaRepository<InwardDistributorStock, Integer> {

	// index ---------------------------------------------
	
	
	@Query(value = "SELECT ids.id,ids.indate,ids.invoiceno,DATE_FORMAT(ids.createddate,'%d-%m-%Y') AS createddate,ids.createbyname,ids.updatedbyname,ids.createdtime,d.trade_name as distributorname,DATE_FORMAT(ids.updateddate,'%d-%m-%Y') as updateddate,ids.updatedtime,ids.vouchermaster_series\r\n"
			+ ",ids.grossamount,ids.igst,ids.sgst,ids.cgst,ids.grandtotal,sta.name as state,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename\r\n"
			+ "			FROM inward_distributor_stock ids\r\n"
			+ "            LEFT JOIN distributor d on ids.distributorid = d.id\r\n"
			+ "            left join states sta on d.stateid=sta.id\r\n"
			+ "			left join distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
			+ "            left join staff s1 on ds.rsmid=s1.id\r\n"
			+ "			left join staff s2 on ds.aseid=s2.id "
			+ "GROUP BY ids.id",nativeQuery = true)
	public List<IndexInwardDitributor> indexInwardDistributor(Pageable p);
	
	
	@Query(value = "SELECT ids.id,ids.indate,ids.invoiceno,DATE_FORMAT(ids.createddate,'%d-%m-%Y') AS createddate,ids.createbyname,ids.updatedbyname,ids.createdtime,d.trade_name as distributorname,DATE_FORMAT(ids.updateddate,'%d-%m-%Y') as updateddate,ids.updatedtime,ids.vouchermaster_series\r\n"
			+ ",ids.grossamount,ids.igst,ids.sgst,ids.cgst,ids.grandtotal,sta.name as state,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename\r\n"
			+ "			FROM inward_distributor_stock ids\r\n"
			+ "            LEFT JOIN distributor d on ids.distributorid = d.id\r\n"
			+ "            left join states sta on d.stateid=sta.id\r\n"
			+ "			left join distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
			+ "            left join staff s1 on ds.rsmid=s1.id\r\n"
			+ "			left join staff s2 on ds.aseid=s2.id "
			+ " WHERE ids.id Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ids.id , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or ids.indate Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ids.indate , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or d.trade_name Like CONCAT('%',:search,'%')"
			+ " or REPLACE(d.trade_name , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or ids.grossamount Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ids.grossamount , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or sta.name Like CONCAT('%',:search,'%')"
			+ " or REPLACE(sta.name , ' ' ,'') Like CONCAT('%',:search,'%') "
			+ "GROUP BY ids.id"  , nativeQuery = true)
	public List<IndexInwardDitributor> indexInwardDistribtorSearch(Pageable p,String search);
	
    //	
	
	@Query(value = "SELECT ids.id,ids.indate,ids.invoiceno,DATE_FORMAT(ids.createddate,'%d-%m-%Y') AS createddate,ids.createbyname,ids.updatedbyname,ids.createdtime,d.trade_name as distributorname,DATE_FORMAT(ids.updateddate,'%d-%m-%Y') as updateddate,ids.updatedtime,ids.vouchermaster_series\r\n"
			+ ",ids.grossamount,ids.igst,ids.sgst,ids.cgst,ids.grandtotal,sta.name as state,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename\r\n"
			+ "FROM inward_distributor_stock ids\r\n"
	       + "LEFT JOIN distributor d on ids.distributorid = d.id\r\n"
	   	+ " left join states sta on d.stateid=sta.id"
		+ "			left join distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
		+ "            left join staff s1 on ds.rsmid=s1.id\r\n"
		+ "			left join staff s2 on ds.aseid=s2.id "
          + "WHERE ids.distributorid = :id "
          + "GROUP BY ids.id", nativeQuery = true)
	public List<IndexInwardDitributor> indexInwardDistributor(int id);
	
	
	
	
	
	// index by distributor id --------------------------------------------- 
	
	@Query(value = "SELECT ids.id,ids.indate,ids.invoiceno,DATE_FORMAT(ids.createddate,'%d-%m-%Y') AS createddate,ids.createbyname,ids.updatedbyname,ids.createdtime,d.trade_name as distributorname,DATE_FORMAT(ids.updateddate,'%d-%m-%Y') as updateddate,ids.updatedtime,ids.vouchermaster_series\r\n"
			+ ",ids.grossamount,ids.igst,ids.sgst,ids.cgst,ids.grandtotal,sta.name as state,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename\r\n"
			+ "FROM inward_distributor_stock ids\r\n"
	       + "LEFT JOIN distributor d on ids.distributorid = d.id\r\n"
	   	+ " left join states sta on d.stateid=sta.id"
		+ "			left join distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
		+ "            left join staff s1 on ds.rsmid=s1.id\r\n"
		+ "			left join staff s2 on ds.aseid=s2.id "
          + "WHERE ids.distributorid = :id "
          + "GROUP BY ids.id", nativeQuery = true)
	public List<IndexInwardDitributor> indexInwardDistributor(Pageable p,int id);
	
	
	@Query(value="SELECT ids.id,ids.indate,ids.invoiceno,DATE_FORMAT(ids.createddate,'%d-%m-%Y') AS createddate,ids.createbyname,ids.updatedbyname,ids.createdtime,d.trade_name as distributorname,DATE_FORMAT(ids.updateddate,'%d-%m-%Y') as updateddate,ids.updatedtime,ids.vouchermaster_series\r\n"
			+ ",ids.grossamount,ids.igst,ids.sgst,ids.cgst,ids.grandtotal,sta.name as state,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename\r\n"
			+ "FROM inward_distributor_stock ids\r\n"
	       + "LEFT JOIN distributor d on ids.distributorid = d.id\r\n"
	   	+ " left join states sta on d.stateid=sta.id"
		+ "			left join distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
		+ "            left join staff s1 on ds.rsmid=s1.id\r\n"
		+ "			left join staff s2 on ds.aseid=s2.id "
          + "WHERE ds.rsmid = :id "
          + "GROUP BY ids.id",nativeQuery = true)
	public List<IndexInwardDitributor> indexInwardDistributorRsm(Pageable p,int id);
	
	
	
	@Query(value="SELECT ids.id,ids.indate,ids.invoiceno,DATE_FORMAT(ids.createddate,'%d-%m-%Y') AS createddate,ids.createbyname,ids.updatedbyname,ids.createdtime,d.trade_name as distributorname,DATE_FORMAT(ids.updateddate,'%d-%m-%Y') as updateddate,ids.updatedtime,ids.vouchermaster_series\r\n"
			+ ",ids.grossamount,ids.igst,ids.sgst,ids.cgst,ids.grandtotal,sta.name as state,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename\r\n"
			+ "FROM inward_distributor_stock ids\r\n"
	       + "LEFT JOIN distributor d on ids.distributorid = d.id\r\n"
	   	+ " left join states sta on d.stateid=sta.id"
		+ "			left join distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
		+ "            left join staff s1 on ds.rsmid=s1.id\r\n"
		+ "			left join staff s2 on ds.aseid=s2.id"
		+ "			left join staff s on ds.asmid=s.id "
          + "WHERE ds.asmid = :id "
          + "GROUP BY ids.id",nativeQuery = true)
	public List<IndexInwardDitributor> indexInwardDistributorAsm(Pageable p,int id);
	
	
	
	@Query(value="SELECT ids.id,ids.indate,ids.invoiceno,DATE_FORMAT(ids.createddate,'%d-%m-%Y') AS createddate,ids.createbyname,ids.updatedbyname,ids.createdtime,d.trade_name as distributorname,DATE_FORMAT(ids.updateddate,'%d-%m-%Y') as updateddate,ids.updatedtime,ids.vouchermaster_series\r\n"
			+ ",ids.grossamount,ids.igst,ids.sgst,ids.cgst,ids.grandtotal,sta.name as state,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename\r\n"
			+ "FROM inward_distributor_stock ids\r\n"
	       + "LEFT JOIN distributor d on ids.distributorid = d.id\r\n"
	   	+ " left join states sta on d.stateid=sta.id"
		+ "			left join distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
		+ "            left join staff s1 on ds.rsmid=s1.id\r\n"
		+ "			left join staff s2 on ds.aseid=s2.id"
        + "WHERE ds.aseid = :id "
        + "GROUP BY ids.id",nativeQuery = true)
	public List<IndexInwardDitributor> indexInwardDistributorAse(Pageable p,int id);
	
	
	
	
	@Query(value = "SELECT ids.id,ids.indate,ids.invoiceno,DATE_FORMAT(ids.createddate,'%d-%m-%Y') AS createddate,ids.createbyname,ids.updatedbyname,ids.createdtime,d.trade_name as distributorname,DATE_FORMAT(ids.updateddate,'%d-%m-%Y') as updateddate,ids.updatedtime,ids.vouchermaster_series\r\n"
			+ ",ids.grossamount,ids.igst,ids.sgst,ids.cgst,ids.grandtotal,sta.name as state,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename\r\n"
			+ "			FROM inward_distributor_stock ids\r\n"
			+ "            LEFT JOIN distributor d on ids.distributorid = d.id\r\n"
			+ "            left join states sta on d.stateid=sta.id\r\n"
			+ "			left join distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
			+ "            left join staff s1 on ds.rsmid=s1.id\r\n"
			+ "			left join staff s2 on ds.aseid=s2.id "
			+ " WHERE "
			+ " (ids.id Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ids.id , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or ids.indate Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ids.indate , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or d.trade_name Like CONCAT('%',:search,'%')"
			+ " or REPLACE(d.trade_name , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or ids.grossamount Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ids.grossamount , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or sta.name Like CONCAT('%',:search,'%')"
			+ " or REPLACE(sta.name , ' ' ,'') Like CONCAT('%',:search,'%'))"
		    + "AND ids.distributorid = :id "
		    + "GROUP BY ids.id", nativeQuery = true)
	public List<IndexInwardDitributor> indexInwardDistribtorSearch(Pageable p,String search,int id);
	
	
	@Query(value = "SELECT ids.id,ids.indate,ids.invoiceno,DATE_FORMAT(ids.createddate,'%d-%m-%Y') AS createddate,ids.createbyname,ids.updatedbyname,ids.createdtime,d.trade_name as distributorname,DATE_FORMAT(ids.updateddate,'%d-%m-%Y') as updateddate,ids.updatedtime,ids.vouchermaster_series\r\n"
			+ ",ids.grossamount,ids.igst,ids.sgst,ids.cgst,ids.grandtotal,sta.name as state,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename\r\n"
			+ "			FROM inward_distributor_stock ids\r\n"
			+ "            LEFT JOIN distributor d on ids.distributorid = d.id\r\n"
			+ "            left join states sta on d.stateid=sta.id\r\n"
			+ "			left join distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
			+ "            left join staff s1 on ds.rsmid=s1.id\r\n"
			+ "			left join staff s2 on ds.aseid=s2.id "
			+ " WHERE "
			+ " (ids.id Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ids.id , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or ids.indate Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ids.indate , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or d.trade_name Like CONCAT('%',:search,'%')"
			+ " or REPLACE(d.trade_name , ' ' ,'') Like CONCAT('%',:search,'%')"
            + " or ids.grossamount Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ids.grossamount , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or sta.name Like CONCAT('%',:search,'%')"
			+ " or REPLACE(sta.name , ' ' ,'') Like CONCAT('%',:search,'%')) "
			+ "AND ds.rsmid = :id "
			+ "GROUP BY ids.id", nativeQuery = true)
	public List<IndexInwardDitributor> indexInwardDistribtorSearchRsm(Pageable p,String search,int id);
	
	@Query(value = "SELECT ids.id,ids.indate,ids.invoiceno,DATE_FORMAT(ids.createddate,'%d-%m-%Y') AS createddate,ids.createbyname,ids.updatedbyname,ids.createdtime,d.trade_name as distributorname,DATE_FORMAT(ids.updateddate,'%d-%m-%Y') as updateddate,ids.updatedtime,ids.vouchermaster_series\r\n"
			+ ",ids.grossamount,ids.igst,ids.sgst,ids.cgst,ids.grandtotal,sta.name as state,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename\r\n"
			+ "			FROM inward_distributor_stock ids\r\n"
			+ "            LEFT JOIN distributor d on ids.distributorid = d.id\r\n"
			+ "            left join states sta on d.stateid=sta.id\r\n"
			+ "			left join distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
			+ "            left join staff s1 on ds.rsmid=s1.id\r\n"
			+ "			left join staff s2 on ds.aseid=s2.id "
			+ " LEFT JOIN staff s on ds.asmid=s.id"
			+ " WHERE "
			+ " (ids.id Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ids.id , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or ids.indate Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ids.indate , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or d.trade_name Like CONCAT('%',:search,'%')"
			+ " or REPLACE(d.trade_name , ' ' ,'') Like CONCAT('%',:search,'%')"
			 + " or ids.grossamount Like CONCAT('%',:search,'%')"
				+ " or REPLACE(ids.grossamount , ' ' ,'') Like CONCAT('%',:search,'%')"
				+ " or sta.name Like CONCAT('%',:search,'%')"
				+ " or REPLACE(sta.name , ' ' ,'') Like CONCAT('%',:search,'%'))"
			+ "AND  ds.asmid = :id "
			+ "GROUP BY ids.id", nativeQuery = true)
	public List<IndexInwardDitributor> indexInwardDistribtorSearchAsm(Pageable p,String search,int id);
	
	
	@Query(value = "SELECT ids.id,ids.indate,ids.invoiceno,DATE_FORMAT(ids.createddate,'%d-%m-%Y') AS createddate,ids.createbyname,ids.updatedbyname,ids.createdtime,d.trade_name as distributorname,DATE_FORMAT(ids.updateddate,'%d-%m-%Y') as updateddate,ids.updatedtime,ids.vouchermaster_series\r\n"
			+ ",ids.grossamount,ids.igst,ids.sgst,ids.cgst,ids.grandtotal,sta.name as state,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename\r\n"
			+ "			FROM inward_distributor_stock ids\r\n"
			+ "            LEFT JOIN distributor d on ids.distributorid = d.id\r\n"
			+ "            left join states sta on d.stateid=sta.id\r\n"
			+ "			left join distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
			+ "            left join staff s1 on ds.rsmid=s1.id\r\n"
			+ "			left join staff s2 on ds.aseid=s2.id "
			+ " WHERE "
			+ " (ids.id Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ids.id , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or ids.indate Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ids.indate , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or d.trade_name Like CONCAT('%',:search,'%')"
			+ " or REPLACE(d.trade_name , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or p.product_name Like CONCAT('%',:search,'%')"
			+ " or REPLACE(p.product_name , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or idsi.inwardqty Like CONCAT('%',:search,'%')"
			+ " or REPLACE(idsi.inwardqty , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or idsi.amount Like CONCAT('%',:search,'%')"
			+ " or REPLACE(idsi.amount , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or idsi.rate Like CONCAT('%',:search,'%')"
			+ " or REPLACE(idsi.rate , ' ' ,'') Like CONCAT('%',:search,'%')) "
			+ "AND ds.aseid = :id "
			+ "GROUP BY ids.id", nativeQuery = true)
	public List<IndexInwardDitributor> indexInwardDistribtorSearchAse(Pageable p,String search,int id);
	
	
	
	   @Query(value = "SELECT * FROM inward_distributor_stock di order by id desc limit 1",nativeQuery = true)
	   InwardDistributorStock lastrowstatus();
	   
	   InwardDistributorStock findTopByVoucherMasterOrderByIdDesc(VoucherMaster voucher);
	    
	   InwardDistributorStock findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(VoucherMaster voucher);
		
	   InwardDistributorStock findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(VoucherMaster voucher);
	   
	
}
