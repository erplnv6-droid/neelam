package com.SCM.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SCM.model.Sales;
import com.SCM.model.SalesOrder;
import com.SCM.model.VoucherMaster;
import com.SCM.projection.SalesProjection;
import com.SCM.projection.TransportDetailUpdateReportProjection;
import com.SCM.projectionDto.ClosingStockReport;
import com.SCM.projectionDto.SalesReportDto;

@Repository
public interface SalesRepo extends JpaRepository<Sales, Integer> {

	
	List<Sales> findByVoucherid(String voucherid);
	
	@Query(value = "select * from sales order by id desc limit 1",nativeQuery = true)
	Sales findlastsale();
	
	@Query(value = "select * from sales where sales.voucherseries like  CONCAT('%',:search,'%') order by id desc limit 1 ",nativeQuery = true)
	Sales searchByVoucher(String search);
//	for voucher end
	
	
	@Query(value = "   SELECT \r\n"
			+ "  s.id,\r\n"
			+ "  MAX(s.grandtotal) as grandtotal,\r\n"
			+ "  MAX(cu.contactname) as contactname,\r\n"
			+ "  MAX(s.gstno) as gstno,\r\n"
			+ "  DATE_FORMAT(MAX(s.invoicedate), '%d-%m-%Y') AS invoicedate,\r\n"
			+ "  MAX(s.status) as status,\r\n"
			+ "  MAX(COALESCE(r.trade_name, d.trade_name)) as distributorname,\r\n"
			+ "  MAX(s.grossamount) as net_amount,\r\n"
			+ "  MAX(s.invoiceno) as invoiceno,\r\n"
			+ "  MAX(st.name) as state,\r\n"
			+ "  MAX(s.igst) as igst,\r\n"
			+ "  MAX(s.cgst) as cgst,\r\n"
			+ "  MAX(s.sgst) as sgst,\r\n"
			+ "  MAX(s.vouchermaster_series) as vouchermaster_series,\r\n"
			+ "  GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,\r\n"
			+ "  GROUP_CONCAT(DISTINCT s2.staff_name) as asename,\r\n"
			+ "  MAX(s.createbyname) as createbyname,\r\n"
			+ "  MAX(s.updatedbyname) as updatedbyname,\r\n"
			+ "  MAX(s.createdtime) as createdtime,\r\n"
			+ "  DATE_FORMAT(MAX(s.createddate), '%d-%m-%Y') AS createddate,\r\n"
			+ "  DATE_FORMAT(MAX(s.updateddate), '%d-%m-%Y') AS updateddate,\r\n"
			+ "  MAX(s.updatedtime) as updatedtime\r\n"
			+ "FROM sales s\r\n"
			+ "LEFT JOIN customer_sub_contacts cu ON cu.id = s.sub_conatacts_id\r\n"
			+ "LEFT JOIN retailer r ON s.retailer_id = r.id\r\n"
			+ "LEFT JOIN distributor d ON d.id = s.distributor_id\r\n"
			+ "LEFT JOIN states st ON d.stateid = st.id or r.stateid=st.id\r\n"
			+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
			+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesProjection> indexSales(Pageable p);
	
	
	@Query(value = "SELECT s.id, " +
	        "MAX(cu.contactname) AS contactname, " +
	        "MAX(s.gstno) AS gstno, " +
	        "DATE_FORMAT(MAX(s.invoicedate), '%d-%m-%Y') AS invoicedate, " +
	        "MAX(s.status) AS status, " +
	        "MAX(s.grossamount) AS net_amount, " +
	        "MAX(COALESCE(r.trade_name, d.trade_name)) AS distributorname, " +
	        "MAX(s.invoiceno) AS invoiceno, " +
	        "MAX(st.name) AS state, " +
	        "MAX(s.igst) AS igst, " +
	        "MAX(s.cgst) AS cgst, " +
	        "MAX(s.sgst) AS sgst, " +
	    	"MAX(s.vouchermaster_series) as vouchermaster_series, "+
	        "GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname, " +
	        "GROUP_CONCAT(DISTINCT s2.staff_name) AS asename, " +
	        "MAX(s.createbyname) AS createbyname, " +
	        "MAX(s.updatedbyname) AS updatedbyname, " +
	        "MAX(s.createdtime) AS createdtime, " +
	        "DATE_FORMAT(MAX(s.createddate), '%d-%m-%Y') AS createddate, " +
	        "DATE_FORMAT(MAX(s.updateddate), '%d-%m-%Y') AS updateddate, " +
	        "MAX(s.updatedtime) AS updatedtime " +
	        "FROM sales s " +
	        "LEFT JOIN customer_sub_contacts cu ON cu.id = s.sub_conatacts_id " +
	        "LEFT JOIN distributor d ON d.id = s.distributor_id " +
	        "LEFT JOIN retailer r ON s.retailer_id = r.id " +
	        "LEFT JOIN states st ON d.stateid = st.id or r.stateid=st.id " +
	        "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id " +
	         "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"+
	     	 "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n" +
	  		 "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n" +
	        "WHERE s.id LIKE CONCAT('%', :search, '%') " +
	        "OR cu.contactname LIKE CONCAT('%', :search, '%') " +
	        "OR s.gstno LIKE CONCAT('%', :search, '%') " +
	        "OR s.invoicedate LIKE CONCAT('%', :search, '%') " +
	        "OR s.status LIKE CONCAT('%', :search, '%') " +
	         "OR LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) "+
	         "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%')) "+
	        "OR s.grossamount LIKE CONCAT('%', :search, '%') " +
	        "GROUP BY s.id",
	        countQuery = "SELECT COUNT(DISTINCT s.id) FROM sales s " +
	                "LEFT JOIN customer_sub_contacts cu ON cu.id = s.sub_conatacts_id " +
	                "LEFT JOIN distributor d ON d.id = s.distributor_id " +
	                "LEFT JOIN retailer r ON s.retailer_id = r.id " +
	                "LEFT JOIN states st ON d.stateid = st.id or r.stateid=st.id " +
	                "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id " +
	                "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"+
	                "LEFT JOIN staff s1 ON ds.rsmid = s1.id or rs.rsmid=s1.id " +
	                "LEFT JOIN staff s2 ON ds.aseid = s2.id or rs.aseid=s1.id " +
	                "WHERE s.id LIKE CONCAT('%', :search, '%') " +
	                "OR cu.contactname LIKE CONCAT('%', :search, '%') " +
	                "OR s.gstno LIKE CONCAT('%', :search, '%') " +
	                "OR s.invoicedate LIKE CONCAT('%', :search, '%') " +
	                "OR s.status LIKE CONCAT('%', :search, '%') " +
	                 " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n" +
	    	         "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%')))\r\n" +
	                "OR s.netvalue LIKE CONCAT('%', :search, '%')",
	        nativeQuery = true)
	List<SalesProjection> SearchBySales(@Param("search") String search, Pageable pageable);


	
	
	@Query(value = "SELECT s.id,s.grandtotal,cu.contactname,s.gstno,s.invoicedate,s.status,s.grossamount as net_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,s.invoiceno,st.name as state,s.igst,s.cgst,s.sgst, GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname, GROUP_CONCAT(DISTINCT s2.staff_name) as asename,s.createddate,s.createdtime,s.vouchermaster_series from sales s"
             + " left join customer_sub_contacts cu on cu.id=s.sub_conatacts_id"
			+ " left join retailer r on s.retailer_id=r.id"
			+ " left join distributor d on d.id = s.distributor_id"
			+ "             left join states st on d.stateid=st.id or r.stateid=st.id\r\n"
			+ "        left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+  " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ " where s.distributor_id= :id "
			+ "GROUP BY s.id" ,nativeQuery = true)
	List<SalesProjection> indexSales(long id);
	
	
	@Query(value = "SELECT s.id,s.grandtotal,cu.contactname,s.gstno,s.invoicedate,s.status,s.grossamount as net_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,s.invoiceno,st.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,s.createddate,s.createdtime,s.vouchermaster_series from sales s"
+ " left join customer_sub_contacts cu on cu.id=s.sub_conatacts_id"
			+ " left join retailer r on s.retailer_id=r.id"
			+ " left join distributor d on d.id = s.distributor_id "
			+ "             left join states st on d.stateid=st.id or r.stateid=st.id\r\n"
			+ "        left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+  " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ "where s.distributor_id= :id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesProjection> indexSales(Pageable p,long id);
	
	
	@Query(value = "SELECT s.id,s.grandtotal,cu.contactname,s.gstno,s.invoicedate,s.status,s.grossamount as net_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,s.invoiceno,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,s.createddate,s.createdtime,s.vouchermaster_series from sales s\r\n"
		+ "			 left join customer_sub_contacts cu on cu.id=s.sub_conatacts_id\r\n"
			+ "			 left join distributor d on d.id = s.distributor_id \r\n"
			+ "             left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " left join retailer r on s.retailer_id=r.id\r\n"
			+ "             left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
          + "             where ds.rsmid=:id or rs.rsmid=:id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesProjection> indexSalesRsm(Pageable p,long id);
	
	@Query(value = "SELECT s.id,s.grandtotal,cu.contactname,s.gstno,s.invoicedate,s.status,s.grossamount as net_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,s.invoiceno,sta.name as state,s.igst,s.cgst,s.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,s.createddate,s.createdtime,s.vouchermaster_series from sales s\r\n"
	+ "			 left join customer_sub_contacts cu on cu.id=s.sub_conatacts_id\r\n"
			+ "			 left join distributor d on d.id = s.distributor_id \r\n"
			+ "             left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " left join retailer r on s.retailer_id=r.id\r\n"
			+ "             left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
            + " where ds.rsmid=:id or rs.rsmid=:id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesProjection> indexSalesRsm(long id);
	
	
	@Query(value = "SELECT s.id,s.grandtotal,cu.contactname,s.gstno,s.invoicedate,s.status,s.grossamount as net_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,s.invoiceno,st.name as state,s.igst,s.cgst,s.sgst, GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname, GROUP_CONCAT(DISTINCT s2.staff_name) as asename,s.createddate,s.createdtime,s.vouchermaster_series from sales s\r\n"
		+ "			 left join customer_sub_contacts cu on cu.id=s.sub_conatacts_id\r\n"
			+ "			 left join distributor d on d.id = s.distributor_id \r\n"
			+ "             left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " left join retailer r on s.retailer_id=r.id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "             left join staff st on ds.asmid=st.id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
            + "             left join states st on d.stateid=st.id or r.stateid=st.id\r\n"
			+ "             where ds.asmid=:id or rs.asmid=:id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesProjection> indexSalesAsm(Pageable p,long id);
	
	
	@Query(value = "SELECT s.id,s.grandtotal,cu.contactname,s.gstno,s.invoicedate,s.status,s.grossamount as net_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,s.invoiceno,st.name as state,s.igst,s.cgst,s.sgst, GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname, GROUP_CONCAT(DISTINCT s2.staff_name) as asename,s.createddate,s.createdtime,s.vouchermaster_series from sales s\r\n"
			+ "	left join sales_items si on s.id=si.sales_id\r\n"
			+ "			 left join customer_sub_contacts cu on cu.id=s.sub_conatacts_id\r\n"
			+ "			 left join distributor d on d.id = s.distributor_id \r\n"
			+ "             left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " left join retailer r on s.retailer_id=r.id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "             left join staff st on ds.asmid=st.id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
            + " left join states st on d.stateid=sta.id or r.stateid=sta.id\r\n"
			+ "             where ds.asmid=:id or rs.asmid=:id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesProjection> indexSalesAsm(long id);
	
	
	@Query(value = "SELECT s.id,s.grandtotal,cu.contactname,s.gstno,s.invoicedate,s.status,s.grossamount as net_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,s.invoiceno,sta.name as state,s.igst,s.cgst,s.sgst, GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname, GROUP_CONCAT(DISTINCT s2.staff_name) as asename,s.createddate,s.createdtime,s.vouchermaster_series from sales s\r\n"
			+ "	left join sales_items si on s.id=si.sales_id\r\n"
			+ "			 left join customer_sub_contacts cu on cu.id=s.sub_conatacts_id\r\n"
			+ "			 left join distributor d on d.id = s.distributor_id \r\n"
			+ "             left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " left join retailer r on s.retailer_id=r.id\r\n"
			+ " left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
            + " where ds.aseid=:id or rs.aseid=:id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesProjection> indexSalesAse(Pageable p,long id);
	
	
	
	@Query(value = "SELECT s.id,s.grandtotal,cu.contactname,s.gstno,s.invoicedate,s.status,s.grossamount as net_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,s.invoiceno,sta.name as state,s.igst,s.cgst,s.sgst, GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname, GROUP_CONCAT(DISTINCT s2.staff_name) as asename,s.createddate,s.createdtime,s.vouchermaster_series from sales s\r\n"
			+ "	left join sales_items si on s.id=si.sales_id\r\n"
			+ "			 left join customer_sub_contacts cu on cu.id=s.sub_conatacts_id\r\n"
			+ "			 left join distributor d on d.id = s.distributor_id \r\n"
			+ "             left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " left join retailer r on s.retailer_id=r.id\r\n"
			+ " left join states sta on d.stateid=sta.id or r.stateid=sta.id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
            + " where ds.aseid=:id or rs.aseid=:id "
			+ "GROUP BY s.id",nativeQuery = true)
	List<SalesProjection> indexSalesAse(long id);
    
    
	
	@Query(value = "select s.id,s.grandtotal,cu.contactname,s.gstno,s.invoicedate,s.status,si.net_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,s.invoiceno,sta.name as state,s.igst,s.cgst,s.sgst, GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname, GROUP_CONCAT(DISTINCT s2.staff_name) as asename,s.createddate,s.createdtime,s.vouchermaster_series FROM sales s"
			+ "	left join sales_items si on s.id=si.sales_id\r\n"
			+ " left join customer_sub_contacts cu on cu.id=s.sub_conatacts_id"
			+ " left join retailer r on s.retailer_id=r.id"
			+ " left join distributor d on d.id = s.distributor_id "
			+ "             left join states sta on d.stateid=sta.id or r.stateid=sta.id "
			+ "        left join distributor_to_staff ds on d.id=ds.distributor_id"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ " where  s.distributor_id= :id and (s.id Like CONCAT('%',:search, '%')\r\n"
			+ "	or cu.contactname Like CONCAT('%',:search, '%')\r\n"
			+ " or s.gstno Like CONCAT('%',:search, '%')\r\n"
			+ "	or s.invoicedate Like CONCAT('%',:search, '%')\r\n"
			+ "	or s.status Like CONCAT('%',:search, '%')\r\n"
			+ "	or s.grossamount Like CONCAT('%',:search, '%')"
	        + " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%')))\r\n"
			, nativeQuery = true)
	List<SalesProjection> SearchBySales(long id,String search, Pageable p);

	
	@Query(value = "select s.id,s.grandtotal,cu.contactname,s.gstno,s.invoicedate,s.status,si.net_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,s.invoiceno,sta.name as state,s.igst,s.cgst,s.sgst, GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname, GROUP_CONCAT(DISTINCT s2.staff_name) as asename,s.createddate,s.createdtime,s.vouchermaster_series FROM sales s"
			+ "	left join sales_items si on s.id=si.sales_id\r\n"
			+ " left join customer_sub_contacts cu on cu.id=s.sub_conatacts_id"
			+ " left join retailer r on s.retailer_id=r.id"
			+ " left join distributor d on d.id = s.distributor_id "
			+ " left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ " left join states sta on d.stateid=sta.id or r.stateid=sta.id "
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
	        + " where (ds.rsmid=:id or rs.rsmid=:id)  and (s.id Like CONCAT('%',:search, '%')\r\n"
			+ "	or cu.contactname Like CONCAT('%',:search, '%')\r\n"
			+ " or s.gstno Like CONCAT('%',:search, '%')\r\n"
			+ "	or s.invoicedate Like CONCAT('%',:search, '%')\r\n"
			+ "	or s.status Like CONCAT('%',:search, '%')\r\n"
			+ "	or s.grossamount Like CONCAT('%',:search, '%')"
	        + " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%')))\r\n"
	        , nativeQuery = true)
	List<SalesProjection> SearchBySalesRsm(long id,String search, Pageable p);
	


	
	
	@Query(value = "select s.id,s.grandtotal,cu.contactname,s.gstno,s.invoicedate,s.status,si.net_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,s.invoiceno,sta.name as state,s.igst,s.cgst,s.sgst, GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname, GROUP_CONCAT(DISTINCT s2.staff_name) as asename,s.createddate,s.createdtime,s.vouchermaster_series FROM sales s"
			+ "	left join sales_items si on s.id=si.sales_id\r\n"
			+ " left join customer_sub_contacts cu on cu.id=s.sub_conatacts_id"
			+ " left join retailer r on s.retailer_id=r.id"
			+ " left join distributor d on d.id = s.distributor_id "
			+ " left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ " left join staff st on ds.asmid=st.id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ " left join states sta on d.stateid=sta.id or r.stateid=sta.id "
			+ " where (ds.asmid=:id or rs.asmid=:id) and (s.id Like CONCAT('%',:search, '%')\r\n"
			+ "	or cu.contactname Like CONCAT('%',:search, '%')\r\n"
			+ " or s.gstno Like CONCAT('%',:search, '%')\r\n"
			+ "	or s.invoicedate Like CONCAT('%',:search, '%')\r\n"
			+ "	or s.status Like CONCAT('%',:search, '%')\r\n"
			+ "	or s.grossamount Like CONCAT('%',:search, '%')"
	        + " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%')))\r\n"
			, nativeQuery = true)
	List<SalesProjection> SearchBySalesAsm(long id,String search, Pageable p);
	
	@Query(value = "select s.id,s.grandtotal,cu.contactname,s.gstno,s.invoicedate,s.status,si.net_amount,COALESCE(r.trade_name, d.trade_name) as distributorname,s.invoiceno,sta.name as state,s.igst,s.cgst,s.sgst, GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname, GROUP_CONCAT(DISTINCT s2.staff_name) as asename,s.createddate,s.createdtime,s.vouchermaster_series FROM sales s"
			+ "	left join sales_items si on s.id=si.sales_id\r\n"
			+ " left join customer_sub_contacts cu on cu.id=s.sub_conatacts_id"
			+ " left join retailer r on s.retailer_id=r.id"
			+ " left join distributor d on d.id = s.distributor_id "
			+ " left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "LEFT JOIN staff s1 ON ((s.retailer_id is null and ds.rsmid = s1.id) or (s.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((s.retailer_id is null and ds.aseid = s2.id) or (s.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ " left join states sta on d.stateid=sta.id or r.stateid=sta.id "
			+ " where (ds.aseid=:id or rs.aseid=:id) and (s.id Like CONCAT('%',:search, '%')\r\n"
			+ "	or cu.contactname Like CONCAT('%',:search, '%')\r\n"
			+ " or s.gstno Like CONCAT('%',:search, '%')\r\n"
			+ "	or s.invoicedate Like CONCAT('%',:search, '%')\r\n"
			+ "	or s.status Like CONCAT('%',:search, '%')\r\n"
			+ "	or s.grossamount Like CONCAT('%',:search, '%')"
	        + " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%')))\r\n"
			, nativeQuery = true)
	List<SalesProjection> SearchBySalesAse(long id,String search, Pageable p);
	
	
	// Transport Details Update Report
	
	@Query(value = "select s.invoicedate as voucherDate,s.invoiceno as voucherNumber,d.trade_name as partyName,s.destination as destination,ifnull(s.grossamount,0) as amount,s.dispatchedthrough as transporter,\r\n"
			+ "ifnull(s.shippingcharge,0) as transportCharges ,ifnull(((s.shippingcharge / s.grossamount) * 100),0) as freightCost,s.lrno as lrNumber,\r\n"
			+ "s.lrndate as lrnDate, s.edd as edd , s.grnno as grnNumber , s.grndate as grnDate,\r\n"
			+ "ifnull(datediff(s.edd,s.grndate),0) as earlyLateDelivery,ifnull(s.totalnopkg,0) as totalNoOfPKGS\r\n"
			+ "from sales s \r\n"
			+ "left join distributor d on s.distributor_id = d.id\r\n"
			+ "where s.invoicedate between :start_date and :end_date",nativeQuery = true)
	public List<TransportDetailUpdateReportProjection> transportReportAdmin(@Param("start_date") String start_date,@Param("end_date") String end_date,Pageable p);
	
	
	
	
	
	


	@Query(value = "select s.invoicedate as voucherDate,s.invoiceno as voucherNumber,d.trade_name as partyName,s.destination as destination,ifnull(s.grossamount,0) as amount,s.dispatchedthrough as transporter,\r\n"
			+ "ifnull(s.shippingcharge,0) as transportCharges ,ifnull(((s.shippingcharge / s.grossamount) * 100),0) as freightCost,s.lrno as lrNumber,\r\n"
			+ "s.lrndate as lrnDate, s.edd as edd , s.grnno as grnNumber , s.grndate as grnDate,\r\n"
			+ "ifnull(datediff(s.edd,s.grndate),0) as earlyLateDelivery,ifnull(s.totalnopkg,0) as totalNoOfPKGS\r\n"
			+ "from sales s \r\n"
			+ "left join distributor d on s.distributor_id = d.id\r\n"
			+ "where s.invoicedate between :start_date and :end_date and s.distributor_id=:did",nativeQuery = true)
	public List<TransportDetailUpdateReportProjection> transportReport(@Param("start_date") String start_date,@Param("end_date") String end_date,Pageable p,int did);
	
	@Query(value = "select s.invoicedate as voucherDate,s.invoiceno as voucherNumber,d.trade_name as partyName,s.destination as destination,ifnull(s.grossamount,0) as amount,s.dispatchedthrough as transporter,\r\n"
			+ "ifnull(s.shippingcharge,0) as transportCharges ,ifnull(((s.shippingcharge / s.grossamount) * 100),0) as freightCost,s.lrno as lrNumber,\r\n"
			+ "s.lrndate as lrnDate, s.edd as edd , s.grnno as grnNumber , s.grndate as grnDate,\r\n"
			+ "ifnull(datediff(s.edd,s.grndate),0) as earlyLateDelivery,ifnull(s.totalnopkg,0) as totalNoOfPKGS\r\n"
			+ "from sales s \r\n"
			+ "left join distributor d on s.distributor_id = d.id\r\n"
			+ "where s.invoicedate between :start_date and :end_date and s.distributor_id=:did",nativeQuery = true)
	public List<TransportDetailUpdateReportProjection> transportReport(@Param("start_date") String start_date,@Param("end_date") String end_date,int did);
	
	
	@Query(value = "select s.invoicedate as voucherDate,s.invoiceno as voucherNumber,d.trade_name as partyName,s.destination as destination,ifnull(s.grossamount,0) as amount,s.dispatchedthrough as transporter,\r\n"
			+ "			ifnull(s.shippingcharge,0) as transportCharges ,ifnull(((s.shippingcharge / s.grossamount) * 100),0) as freightCost,s.lrno as lrNumber,\r\n"
			+ "			s.lrndate as lrnDate, s.edd as edd , s.grnno as grnNumber , s.grndate as grnDate,\r\n"
			+ "			ifnull(datediff(s.edd,s.grndate),0) as earlyLateDelivery,ifnull(s.totalnopkg,0) as totalNoOfPKGS\r\n"
			+ "			from sales s \r\n"
			+ "			left join distributor d on s.distributor_id = d.id\r\n"
			+ "                     left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "            left join staff st on ds.rsmid=st.id\r\n"
			+ "        where s.invoicedate between :start_date and :end_date and ds.rsmid=:did",nativeQuery = true)
	public List<TransportDetailUpdateReportProjection> transportReportRsm(@Param("start_date") String start_date,@Param("end_date") String end_date,Pageable p,int did);
	
	
	@Query(value = "select s.invoicedate as voucherDate,s.invoiceno as voucherNumber,d.trade_name as partyName,s.destination as destination,ifnull(s.grossamount,0) as amount,s.dispatchedthrough as transporter,\r\n"
			+ "			ifnull(s.shippingcharge,0) as transportCharges ,ifnull(((s.shippingcharge / s.grossamount) * 100),0) as freightCost,s.lrno as lrNumber,\r\n"
			+ "			s.lrndate as lrnDate, s.edd as edd , s.grnno as grnNumber , s.grndate as grnDate,\r\n"
			+ "			ifnull(datediff(s.edd,s.grndate),0) as earlyLateDelivery,ifnull(s.totalnopkg,0) as totalNoOfPKGS\r\n"
			+ "			from sales s \r\n"
			+ "			left join distributor d on s.distributor_id = d.id\r\n"
			+ "           left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "		          left join staff st on ds.rsmid=st.id\r\n"
			+ "        where s.invoicedate between :start_date and :end_date and ds.rsmid=:did",nativeQuery = true)
	public List<TransportDetailUpdateReportProjection> transportReportRsm(@Param("start_date") String start_date,@Param("end_date") String end_date,int did);
	
	
	@Query(value = "select s.invoicedate as voucherDate,s.invoiceno as voucherNumber,d.trade_name as partyName,s.destination as destination,ifnull(s.grossamount,0) as amount,s.dispatchedthrough as transporter,\r\n"
			+ "			ifnull(s.shippingcharge,0) as transportCharges ,ifnull(((s.shippingcharge / s.grossamount) * 100),0) as freightCost,s.lrno as lrNumber,\r\n"
			+ "			s.lrndate as lrnDate, s.edd as edd , s.grnno as grnNumber , s.grndate as grnDate,\r\n"
			+ "			ifnull(datediff(s.edd,s.grndate),0) as earlyLateDelivery,ifnull(s.totalnopkg,0) as totalNoOfPKGS\r\n"
			+ "			from sales s \r\n"
			+ "			left join distributor d on s.distributor_id = d.id\r\n"
			+ "           left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "				          left join staff st on ds.asmid=st.id\r\n"
			+ "        where s.invoicedate between :start_date and :end_date and ds.asmid=:did",nativeQuery = true)
	public List<TransportDetailUpdateReportProjection> transportReportAsm(@Param("start_date") String start_date,@Param("end_date") String end_date,Pageable p,int did);
	
	
	@Query(value = "select s.invoicedate as voucherDate,s.invoiceno as voucherNumber,d.trade_name as partyName,s.destination as destination,ifnull(s.grossamount,0) as amount,s.dispatchedthrough as transporter,\r\n"
			+ "			ifnull(s.shippingcharge,0) as transportCharges ,ifnull(((s.shippingcharge / s.grossamount) * 100),0) as freightCost,s.lrno as lrNumber,\r\n"
			+ "			s.lrndate as lrnDate, s.edd as edd , s.grnno as grnNumber , s.grndate as grnDate,\r\n"
			+ "			ifnull(datediff(s.edd,s.grndate),0) as earlyLateDelivery,ifnull(s.totalnopkg,0) as totalNoOfPKGS\r\n"
			+ "			from sales s \r\n"
			+ "			left join distributor d on s.distributor_id = d.id\r\n"
			+ "           left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "				          left join staff st on ds.asmid=st.id\r\n"
			+ "        where s.invoicedate between :start_date and :end_date and ds.asmid=:did",nativeQuery = true)
	public List<TransportDetailUpdateReportProjection> transportReportAsm(@Param("start_date") String start_date,@Param("end_date") String end_date,int did);
	
	
	@Query(value = "select s.invoicedate as voucherDate,s.invoiceno as voucherNumber,d.trade_name as partyName,s.destination as destination,ifnull(s.grossamount,0) as amount,s.dispatchedthrough as transporter,\r\n"
			+ "			ifnull(s.shippingcharge,0) as transportCharges ,ifnull(((s.shippingcharge / s.grossamount) * 100),0) as freightCost,s.lrno as lrNumber,\r\n"
			+ "			s.lrndate as lrnDate, s.edd as edd , s.grnno as grnNumber , s.grndate as grnDate,\r\n"
			+ "			ifnull(datediff(s.edd,s.grndate),0) as earlyLateDelivery,ifnull(s.totalnopkg,0) as totalNoOfPKGS\r\n"
			+ "			from sales s \r\n"
			+ "			left join distributor d on s.distributor_id = d.id\r\n"
			+ "           left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "			  left join staff st on ds.aseid=st.id\r\n"
			+ "        where s.invoicedate between :start_date and :end_date and ds.aseid=:did",nativeQuery = true)
	public List<TransportDetailUpdateReportProjection> transportReportAse(@Param("start_date") String start_date,@Param("end_date") String end_date,Pageable p,int did);
	
	
	@Query(value = "select s.invoicedate as voucherDate,s.invoiceno as voucherNumber,d.trade_name as partyName,s.destination as destination,ifnull(s.grossamount,0) as amount,s.dispatchedthrough as transporter,\r\n"
			+ "			ifnull(s.shippingcharge,0) as transportCharges ,ifnull(((s.shippingcharge / s.grossamount) * 100),0) as freightCost,s.lrno as lrNumber,\r\n"
			+ "			s.lrndate as lrnDate, s.edd as edd , s.grnno as grnNumber , s.grndate as grnDate,\r\n"
			+ "			ifnull(datediff(s.edd,s.grndate),0) as earlyLateDelivery,ifnull(s.totalnopkg,0) as totalNoOfPKGS\r\n"
			+ "			from sales s \r\n"
			+ "			left join distributor d on s.distributor_id = d.id\r\n"
			+ "          left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "		 left join staff st on ds.aseid=st.id\r\n"
			+ "        where s.invoicedate between :start_date and :end_date and d.aseid=:did",nativeQuery = true)
	public List<TransportDetailUpdateReportProjection> transportReportAse(@Param("start_date") String start_date,@Param("end_date") String end_date,int did);
	
	

	@Transactional
	@Modifying
	@Query(value="update sales set status='ACT' where id=?",nativeQuery = true)
	public void changeStatus(int id);
	

	@Query(value="SELECT \r\n"
			+ "    si.product_id as productid, \r\n"
			+ "    p.product_name as productname,\r\n"
			+ "    SUM(si.salesquantity) AS spcsqty,\r\n"
			+ "    SUM(si.salesquantitykgs) AS skgqty\r\n"
			+ "FROM \r\n"
			+ "    sales s\r\n"
			+ "LEFT JOIN \r\n"
			+ "    sales_items si ON s.id = si.sales_id\r\n"
			+ "left join \r\n"
			+ "	product p on p.id=si.product_id\r\n"
			+ "WHERE \r\n"
			+ "    (s.retailer_id IN (:rid) \r\n"
			+ "    or s.distributor_id IN (:did))\r\n"
			+ "    AND (s.invoicedate BETWEEN :fromdate AND :todate)\r\n"
			+ "GROUP BY \r\n"
			+ "    si.product_id",nativeQuery = true)
	List<SalesReportDto> findAllSalesWithRandDandwId( @Param("rid") List<Long> rid, @Param("did") List<Long> did,String fromdate,
			String todate);
	  
	  
	  @Query(value = "SELECT \r\n"
	  		+ "    p.id AS productid,\r\n"
	  		+ "    p.product_name AS product,\r\n"
	  		+ "    CASE \r\n"
	  		+ "        WHEN dcc.status IN ('pending', 'converted') THEN COALESCE(SUM(mrni.pcsqty), 0) + COALESCE(SUM(msc.stdqty), 0) + COALESCE(SUM(jii.pcsqty), 0) + COALESCE(SUM(sri.pcsqty), 0) - (COALESCE(SUM(dci.pcsqty), 0) + COALESCE(SUM(cbi.pcsqty), 0) + COALESCE(SUM(joi.pcsqty), 0) + COALESCE(SUM(pri.pcsqty), 0))\r\n"
	  		+ "        WHEN ss.status = 'directsales' THEN COALESCE(SUM(mrni.pcsqty), 0) + COALESCE(SUM(msc.stdqty), 0) + COALESCE(SUM(jii.pcsqty), 0) + COALESCE(SUM(sri.pcsqty), 0) - (COALESCE(SUM(cbi.pcsqty), 0) + COALESCE(SUM(joi.pcsqty), 0) + COALESCE(SUM(si.pcsqty), 0) + COALESCE(SUM(pri.pcsqty), 0))\r\n"
	  		+ "        ELSE 0\r\n"
	  		+ "    END AS closingpcsqty,\r\n"
	  		+ "    CASE\r\n"
	  		+ "        WHEN dcc.status IN ('pending', 'converted') THEN COALESCE(SUM(mrni.kgqty), 0) + COALESCE(SUM(jii.kgqty), 0) + COALESCE(SUM(sri.kgqty), 0) - (COALESCE(SUM(dci.kgqty), 0) + COALESCE(SUM(joi.kgqty), 0) + COALESCE(SUM(pri.kgqty), 0))\r\n"
	  		+ "        WHEN ss.status = 'directsales' THEN  COALESCE(SUM(mrni.kgqty), 0) + COALESCE(SUM(jii.kgqty), 0) + COALESCE(SUM(sri.kgqty), 0) - (COALESCE(SUM(joi.kgqty), 0) + COALESCE(SUM(si.kgqty), 0) + COALESCE(SUM(pri.kgqty), 0))\r\n"
	  		+ "        ELSE 0\r\n"
	  		+ "    END AS closingkgqty\r\n"
	  		+ "FROM \r\n"
	  		+ "    product p\r\n"
	  		+ "LEFT JOIN \r\n"
	  		+ "    delivery_charge_items dcii ON p.id = dcii.product_id\r\n"
	  		+ "LEFT JOIN \r\n"
	  		+ "    delivery_charge dcc ON dcii.dc_id = dcc.id\r\n"
	  		+ "LEFT JOIN \r\n"
	  		+ "    sales_items sii ON p.id = sii.product_id\r\n"
	  		+ "LEFT JOIN \r\n"
	  		+ "    sales ss ON sii.sales_id = ss.id\r\n"
	  		+ "LEFT JOIN \r\n"
	  		+ "    opening_stock os ON p.id = os.product_id\r\n"
	  		+ "    \r\n"
	  		+ "-- inward\r\n"
	  		+ "LEFT JOIN (SELECT mrni.product_id, SUM(mrni.dlp) AS rate, SUM(mrni.mrnquantity) AS pcsqty, SUM(mrni.mrnquantitykgs) AS kgqty FROM material_receipt_note_items mrni\r\n"
	  		+ "JOIN material_recepit_note mrn ON mrni.mrn_id = mrn.id \r\n"
	  		+ "WHERE mrn.warehouse_id IN(:wid) AND mrn.mrndate BETWEEN :fromdate AND :todate AND mrni.product_id IN(:pid) GROUP BY mrni.product_id) AS mrni ON p.id = mrni.product_id\r\n"
	  		+ "\r\n"
	  		+ "LEFT JOIN (SELECT sri.product_id, SUM(sri.dlp) AS rate, SUM(sri.srquantity) AS pcsqty, SUM(sri.srquantitykgs) AS kgqty FROM sales_return_items sri\r\n"
	  		+ "JOIN sales_return sr ON sri.sales_returnid = sr.id WHERE sr.warehouse_id IN(:wid) AND sr.salesreturndate BETWEEN :fromdate AND :todate AND sri.product_id IN(:pid)\r\n"
	  		+ "GROUP BY sri.product_id) AS sri ON p.id = sri.product_id\r\n"
	  		+ "\r\n"
	  		+ "LEFT JOIN (SELECT msc.productid, SUM(msc.stdqty) AS stdqty FROM mastercartoon msc JOIN product p ON msc.productid = p.id\r\n"
	  		+ "WHERE msc.createddate BETWEEN :fromdate AND :todate AND msc.productid IN (:pid)\r\n"
	  		+ "GROUP BY msc.productid) AS msc ON msc.productid = p.id\r\n"
	  		+ "\r\n"
	  		+ "LEFT JOIN (SELECT jii.product_id, SUM(jii.dlp) AS rate, SUM(jii.jobsheet_qty) AS pcsqty, SUM(jii.jobsheet_qty_kg) AS kgqty FROM jobwork_inward_items jii\r\n"
	  		+ "JOIN jobwork_inward ji ON jii.jobsheet_id = ji.id WHERE ji.warehouse_id IN(:wid) AND ji.jobsheetdate BETWEEN :fromdate AND :todate AND jii.product_id IN(:pid)\r\n"
	  		+ "GROUP BY jii.product_id) AS jii ON p.id = jii.product_id\r\n"
	  		+ "\r\n"
	  		+ "-- outward\r\n"
	  		+ "LEFT JOIN (SELECT si.product_id, SUM(si.dlp) AS rate, SUM(si.salesquantity) AS pcsqty, SUM(si.salesquantitykgs) AS kgqty FROM sales_items si\r\n"
	  		+ "JOIN sales s ON si.sales_id = s.id WHERE s.warehouse_id IN(:wid) AND s.invoicedate BETWEEN :fromdate AND :todate AND si.product_id IN(:pid)\r\n"
	  		+ "GROUP BY si.product_id) AS si ON p.id = si.product_id\r\n"
	  		+ " \r\n"
	  		+ "LEFT JOIN (SELECT dci.product_id, SUM(dci.dlp) AS rate, SUM(dci.dcquantity_placed) AS pcsqty, SUM(dci.dcquantity_placed_kg) AS kgqty \r\n"
	  		+ "FROM delivery_charge_items dci JOIN delivery_charge dc ON dci.dc_id = dc.id\r\n"
	  		+ "WHERE dc.warehouse_id IN(:wid) AND dc.dcdate BETWEEN :fromdate AND :todate AND dci.product_id IN(:pid) GROUP BY dci.product_id) AS dci ON p.id = dci.product_id\r\n"
	  		+ "\r\n"
	  		+ "LEFT JOIN (SELECT pri.product_id, SUM(pri.dlp) AS rate, SUM(pri.purquantity) AS pcsqty, SUM(pri.purquantitykgs) AS kgqty FROM purchase_return_items pri\r\n"
	  		+ "JOIN purchase_return pr ON pri.purchasereturn_id = pr.id WHERE pr.warehouse_id IN(:wid) AND pr.purchasereturndate BETWEEN :fromdate AND :todate AND pri.product_id IN(:pid)\r\n"
	  		+ "GROUP BY pri.product_id) AS pri ON p.id = pri.product_id\r\n"
	  		+ "\r\n"
	  		+ "LEFT JOIN (SELECT joi.product_id, SUM(joi.dlp) AS rate, SUM(joi.jobsheet_qty) AS pcsqty, SUM(joi.jobsheet_qty_kg) AS kgqty FROM jobworkoutwarditems joi\r\n"
	  		+ "JOIN jobworkoutward jo ON joi.jobsheetoutward_id = jo.id WHERE jo.warehouse_id IN(:wid) AND jo.jobsheetdate BETWEEN :fromdate AND :todate AND joi.product_id IN(:pid)\r\n"
	  		+ "GROUP BY joi.product_id) AS joi ON p.id = joi.product_id\r\n"
	  		+ "\r\n"
	  		+ "LEFT JOIN (SELECT cbi.product_id, SUM(cbi.qty) AS pcsqty FROM cartonbarcodeitems cbi \r\n"
	  		+ "JOIN cartonbarcode cb ON cbi.cartonbarcodeid = cb.id WHERE cb.createddate BETWEEN :fromdate AND :todate AND cbi.product_id IN(:pid)\r\n"
	  		+ "GROUP BY cbi.product_id) AS cbi ON p.id = cbi.product_id\r\n"
	  		+ "\r\n"
	  		+ "WHERE mrni.pcsqty IS NOT NULL OR dci.pcsqty IS NOT NULL OR sri.pcsqty IS NOT NULL OR msc.stdqty IS NOT NULL \r\n"
	  		+ "    OR jii.pcsqty IS NOT NULL OR dci.pcsqty IS NOT NULL OR pri.pcsqty IS NOT NULL OR si.pcsqty IS NOT NULL \r\n"
	  		+ "    OR joi.pcsqty IS NOT NULL OR cbi.pcsqty IS NOT NULL\r\n"
	  		+ "    \r\n"
	  		+ "GROUP BY p.id, p.product_name, dcc.status, ss.status"
	  		+ "", nativeQuery = true)
	  List<ClosingStockReport> findAllClosingStockOfProduct(String fromdate, String todate, String wid, Long pid);

	  
	  
	  @Query(value = "SELECT * FROM sales s order by id desc limit 1",nativeQuery = true)
	    Sales lastrowstatus();
	  
	  Sales findTopByVoucherMasterOrderByIdDesc(VoucherMaster voucher);
	//
	//
	    Sales findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(VoucherMaster voucher);
		
	    Sales findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(VoucherMaster voucher);
	  
	  
	  
}
