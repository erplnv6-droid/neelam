package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.model.Sales;
import com.SCM.model.SalesReturn;
import com.SCM.model.VoucherMaster;
import com.SCM.projection.SalesReturnProjection;

@Repository
public interface SalesReturnRepo extends JpaRepository<SalesReturn, Integer> {

	
	@Query(value = "select * from sales_return sr where sr.voucherseries like  CONCAT('%', :search , '%') order by id desc limit 1",nativeQuery = true)
	SalesReturn searchByVoucher(String search);
	
	
	@Query(value = "SELECT \r\n"
			+ "  s.id,\r\n"
			+ "  MAX(cu.contactname) AS contactname,\r\n"
			+ "  DATE_FORMAT(MAX(s.invoicedate), '%d-%m-%Y') AS invoicedate,\r\n"
			+ "  MAX(s.invoiceno) AS invoiceno,\r\n"
			+ "  MAX(s.type) AS type,\r\n"
			+ "  MAX(s.grandtotal) AS grandtotal,\r\n"
			+ "  MAX(s.gross_amount) AS gross_amount,\r\n"
			+ "  MAX(COALESCE(r.trade_name, d.trade_name)) AS distributorname,\r\n"
			+ "  MAX(sta.name) AS state,\r\n"
			+ "  MAX(s.igst) AS igst,\r\n"
			+ "  MAX(s.cgst) AS cgst,\r\n"
			+ "  MAX(s.sgst) AS sgst,\r\n"
			+ "  MAX(s.vouchermaster_series) AS vouchermaster_series,\r\n"
			+ "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n"
			+ "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n"
			+ "  DATE_FORMAT(MAX(s.createddate), '%d-%m-%Y') AS createddate,\r\n"
			+ "  MAX(s.createdtime) AS createdtime,\r\n"
			+ "  MAX(s.createbyname) AS createbyname,\r\n"
			+ "  MAX(s.updatedbyname) AS updatedbyname,\r\n"
			+ "  DATE_FORMAT(MAX(s.updateddate), '%d-%m-%Y') AS updateddate,\r\n"
			+ "  MAX(s.updatedtime) AS updatedtime\r\n"
			+ "FROM sales_return s\r\n"
			+ "LEFT JOIN customer_sub_contacts cu ON cu.id = s.customersubcontacts_id\r\n"
			+ "LEFT JOIN retailer r ON s.retailer_id = r.id\r\n"
			+ "LEFT JOIN distributor d ON d.id = s.distributor_id\r\n"
			+ "LEFT JOIN states sta ON d.stateid = sta.id or r.stateid = sta.id\r\n"	
			+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
			+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesReturnProjection> indexSalesReturn(Pageable p);
    
    
	@Query(value = "SELECT s.id,cu.contactname,DATE_FORMAT(s.invoicedate,'%d-%m-%Y') AS invoicedate,s.type,s.invoiceno,s.grandtotal,s.gross_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime,s.vouchermaster_series"
			+ "	 FROM sales_return s"
			+ "	 LEFT join customer_sub_contacts cu on cu.id=s.customersubcontacts_id "
            + "  LEFT JOIN retailer r on s.retailer_id=r.id"
			+ "	 left join distributor d on d.id = s.distributor_id"
			+ "  left join states sta on d.stateid=sta.id or r.stateid=sta.id \r\n"
			+ "                 left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ " where s.id Like CONCAT('%',:search, '%')\r\n"
			+ "	or cu.contactname Like CONCAT('%',:search, '%')\r\n"
			+ " or s.type Like CONCAT('%',:search, '%')\r\n"
			+ "	  or s.invoicedate Like CONCAT('%',:search, '%')\r\n"
			+ "	  or s.grandtotal Like CONCAT('%',:search, '%')\r\n"
		    + " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ "GROUP BY s.id"
			, nativeQuery = true)
	List<SalesReturnProjection> SearchBySalesReturn(String search, Pageable p);
	
	
//	salesreturn by distributor id
	@Query(value = "SELECT s.id,cu.contactname,DATE_FORMAT(s.invoicedate,'%d-%m-%Y') AS invoicedate,s.invoiceno,s.type,s.grandtotal,s.gross_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime,s.vouchermaster_series"
			+ "	FROM sales_return s"
			+ "	LEFT join customer_sub_contacts cu on cu.id=s.customersubcontacts_id "
			+ " LEFT JOIN retailer r on s.retailer_id=r.id"
            + "	left join distributor d on d.id = s.distributor_id"
			+ "  left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
			+ "                 left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ " where s.distributor_id=:id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesReturnProjection> indexSalesReturn(long id);
	
	@Query(value = "SELECT s.id,cu.contactname,DATE_FORMAT(s.invoicedate,'%d-%m-%Y') AS invoicedate,s.invoiceno,s.type,s.grandtotal,s.gross_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(s1.staff_name) as rsmname,GROUP_CONCAT(s2.staff_name) as asename,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime,s.vouchermaster_series"
			+ "	FROM sales_return s"
			+ "	LEFT join customer_sub_contacts cu on cu.id=s.customersubcontacts_id "
			+ " LEFT JOIN retailer r on s.retailer_id=r.id"
			+ "	LEFT JOIN sales_return_items sri ON s.id = sri.sales_returnid"
			+ "	left join distributor d on d.id = s.distributor_id"
			+ "  left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
			+ "                 left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ " where s.distributor_id=:id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesReturnProjection> indexSalesReturn(long id,Pageable p);
	
	
	@Query(value = "  SELECT s.id,cu.contactname,DATE_FORMAT(s.invoicedate,'%d-%m-%Y') AS invoicedate,s.invoiceno,s.type,s.grandtotal,s.gross_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime,s.vouchermaster_series\r\n"
			+ "			FROM sales_return s\r\n"
			+ "				LEFT join customer_sub_contacts cu on cu.id=s.customersubcontacts_id \r\n"
			+ " LEFT JOIN retailer r on s.retailer_id=r.id\r\n"
            + "				left join distributor d on d.id = s.distributor_id\r\n"
			+ "                left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
            + "  left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
            + "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ "			 where ds.rsmid=:id or rs.rsmid =:id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesReturnProjection> indexSalesReturnRsm(long id,Pageable p);
	
	
	@Query(value = "  SELECT s.id,cu.contactname,DATE_FORMAT(s.invoicedate,'%d-%m-%Y') AS invoicedate,s.invoiceno,s.type,s.grandtotal,s.gross_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime,s.vouchermaster_series\r\n"
			+ "			FROM sales_return s\r\n"
			+ "				LEFT join customer_sub_contacts cu on cu.id=s.customersubcontacts_id \r\n"
			+ " LEFT JOIN retailer r on s.retailer_id=r.id\r\n"
		    + "				left join distributor d on d.id = s.distributor_id\r\n"
			+ "                left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
            + "  left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
            + "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ "			 where ds.rsmid=:id or rs.rsmid =:id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesReturnProjection> indexSalesReturnRsm(long id);
	
	
	
	@Query(value = "  SELECT s.id,cu.contactname,DATE_FORMAT(s.invoicedate,'%d-%m-%Y') AS invoicedate,s.invoiceno,s.type,s.grandtotal,s.gross_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime,s.vouchermaster_series\r\n"
			+ "			FROM sales_return s\r\n"
			+ "				LEFT join customer_sub_contacts cu on cu.id=s.customersubcontacts_id \r\n"
			+ " LEFT JOIN retailer r on s.retailer_id=r.id\r\n"
	         + "				left join distributor d on d.id = s.distributor_id\r\n"
			+ "                left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "                left join staff st on ds.asmid=st.id\r\n"
			+ "  left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ "			 where ds.asmid=:id or rs.asmid=:id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesReturnProjection> indexSalesReturnAsm(long id,Pageable p);
	
	
	
	@Query(value = "  SELECT s.id,cu.contactname,DATE_FORMAT(s.invoicedate,'%d-%m-%Y') AS invoicedate,s.invoiceno,s.type,s.grandtotal,s.gross_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime,s.vouchermaster_series\r\n"
			+ "			FROM sales_return s\r\n"
			+ "				LEFT join customer_sub_contacts cu on cu.id=s.customersubcontacts_id \r\n"
			+ " LEFT JOIN retailer r on s.retailer_id=r.id\r\n"
            + "				left join distributor d on d.id = s.distributor_id\r\n"
			+ "                left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "                left join staff st on ds.asmid=st.id\r\n"
			+ "  left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ "			 where ds.asmid=:id or rs.asmid=:id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesReturnProjection> indexSalesReturnAsm(long id);
	
	@Query(value = "  SELECT s.id,cu.contactname,DATE_FORMAT(s.invoicedate,'%d-%m-%Y') AS invoicedate,s.invoiceno,s.type,s.grandtotal,s.gross_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime,s.vouchermaster_series\r\n"
			+ "			FROM sales_return s\r\n"
			+ "				LEFT join customer_sub_contacts cu on cu.id=s.customersubcontacts_id \r\n"
			+ " LEFT JOIN retailer r on s.retailer_id=r.id\r\n"
			+ "				left join distributor d on d.id = s.distributor_id\r\n"
			+ "                left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "  left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
		    + "			 where ds.aseid=:id or rs.aseid=:id "
		    + "GROUP BY s.id",nativeQuery = true)
	List<SalesReturnProjection> indexSalesReturnAse(long id,Pageable p);
	
	
	@Query(value = "  SELECT s.id,cu.contactname,DATE_FORMAT(s.invoicedate,'%d-%m-%Y') AS invoicedate,s.invoiceno,s.type,s.grandtotal,s.gross_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime,s.vouchermaster_series\r\n"
			+ "			FROM sales_return s\r\n"
			+ "				LEFT join customer_sub_contacts cu on cu.id=s.customersubcontacts_id \r\n"
			+ " LEFT JOIN retailer r on s.retailer_id=r.id\r\n"
			+ "				LEFT JOIN sales_return_items sri ON s.id = sri.sales_returnid\r\n"
			+ "				left join distributor d on d.id = s.distributor_id\r\n"
			+ "                left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
        + "  left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
    	+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ "			 where ds.aseid=:id or rs.aseid=:id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesReturnProjection> indexSalesReturnAse(long id);
	
	
	
	
	@Query(value = "SELECT s.id,cu.contactname,DATE_FORMAT(s.invoicedate,'%d-%m-%Y') AS invoicedate,s.invoiceno,s.type,s.grandtotal,s.gross_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime,s.vouchermaster_series"
			+ "	FROM sales_return s"
			+ "	LEFT join customer_sub_contacts cu on cu.id=s.customersubcontacts_id"
			+ " LEFT JOIN retailer r on s.retailer_id=r.id"
            + "	left join distributor d on d.id = s.distributor_id"
			+ "            left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
			+ "                 left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ "	where s.distributor_id=:id"
			+ " and( s.id Like CONCAT('%',:search, '%')\r\n"
			+ "	or cu.contactname Like CONCAT('%',:search, '%')\r\n"
			+ " or s.type Like CONCAT('%',:search, '%')\r\n"
			+ "	  or s.invoicedate Like CONCAT('%',:search, '%')\r\n"
			+ "	  or s.grandtotal Like CONCAT('%',:search, '%')"
	        + " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%')))\r\n"
	    	+ "GROUP BY s.id"
			, nativeQuery = true)
	List<SalesReturnProjection> SearchBySalesReturn(long id,String search, Pageable p);
	
	
	@Query(value = "SELECT s.id,cu.contactname,DATE_FORMAT(s.invoicedate,'%d-%m-%Y') AS invoicedate,s.invoiceno,s.type,s.grandtotal,s.gross_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime,s.vouchermaster_series"
			+ "	FROM sales_return s"
			+ "	LEFT join customer_sub_contacts cu on cu.id=s.customersubcontacts_id"
			+ " LEFT JOIN retailer r on s.retailer_id=r.id"
            + "	left join distributor d on d.id = s.distributor_id"
			+ " left join distributor_to_staff ds on d.id=ds.distributor_id"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "            left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ "	where (ds.rsmid=:id or rs.rsmid=:id) "
			+ " and( s.id Like CONCAT('%',:search, '%')\r\n"
			+ "	or cu.contactname Like CONCAT('%',:search, '%')\r\n"
			+ " or s.type Like CONCAT('%',:search, '%')\r\n"
			+ "	  or s.invoicedate Like CONCAT('%',:search, '%')\r\n"
			+ "	  or s.grandtotal Like CONCAT('%',:search, '%')"
	        + " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%')))\r\n"
	    	+ "GROUP BY s.id"
			, nativeQuery = true)
	List<SalesReturnProjection> SearchBySalesReturnRsm(long id,String search, Pageable p);
	
	@Query(value = "SELECT s.id,cu.contactname,DATE_FORMAT(s.invoicedate,'%d-%m-%Y') AS invoicedate,s.invoiceno,s.type,s.grandtotal,s.gross_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime,s.vouchermaster_series"
			+ "	FROM sales_return s"
			+ "	LEFT join customer_sub_contacts cu on cu.id=s.customersubcontacts_id"
			+ " LEFT JOIN retailer r on s.retailer_id=r.id"
	        + "	left join distributor d on d.id = s.distributor_id"
			+ " left join distributor_to_staff ds on d.id=ds.distributor_id"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ " left join staff st on ds.asmid=st.id"
			+ "            left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ "	where (ds.asmid=:id or rs.asmid=:id) "
			+ " and( s.id Like CONCAT('%',:search, '%')\r\n"
			+ "	or cu.contactname Like CONCAT('%',:search, '%')\r\n"
			+ " or s.type Like CONCAT('%',:search, '%')\r\n"
			+ "	  or s.invoicedate Like CONCAT('%',:search, '%')\r\n"
			+ "	  or s.grandtotal Like CONCAT('%',:search, '%')"
	        + " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%')))\r\n"
	    	+ "GROUP BY s.id"
			, nativeQuery = true)
	List<SalesReturnProjection> SearchBySalesReturnAsm(long id,String search, Pageable p);
	
	
	@Query(value = "SELECT s.id,cu.contactname,DATE_FORMAT(s.invoicedate,'%d-%m-%Y') AS invoicedate,s.invoiceno,s.type,s.grandtotal,s.gross_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createdtime,s.createbyname,s.updatedbyname,DATE_FORMAT(s.updateddate,'%d-%m-%Y') as updateddate,s.updatedtime,s.vouchermaster_series"
			+ "	FROM sales_return s"
			+ "	LEFT join customer_sub_contacts cu on cu.id=s.customersubcontacts_id"
			+ " LEFT JOIN retailer r on s.retailer_id=r.id"
            + "	left join distributor d on d.id = s.distributor_id"
			+ " left join distributor_to_staff ds on d.id=ds.distributor_id"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
           + "  left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
           + "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	       + "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
	  		+ "	where (ds.aseid=:id or rs.aseid=:id) "
			+ " and( s.id Like CONCAT('%',:search, '%')\r\n"
			+ "	or cu.contactname Like CONCAT('%',:search, '%')\r\n"
			+ " or s.type Like CONCAT('%',:search, '%')\r\n"
			+ "	  or s.invoicedate Like CONCAT('%',:search, '%')\r\n"
			+ "	  or s.grandtotal Like CONCAT('%',:search, '%')"
	        + " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%')))\r\n"
	    	+ "GROUP BY s.id"
			, nativeQuery = true)
	List<SalesReturnProjection> SearchBySalesReturnAse(long id,String search, Pageable p);
	
	
	  @Query(value = "SELECT * FROM sales_return sr order by id desc limit 1",nativeQuery = true)
	  SalesReturn lastrowstatus();
	  
	  SalesReturn findTopByVoucherMasterOrderByIdDesc(VoucherMaster voucher);
	
	  SalesReturn findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(VoucherMaster voucher);
		
	  SalesReturn findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(VoucherMaster voucher);
	  
	
}
