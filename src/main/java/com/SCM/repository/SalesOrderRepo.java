package com.SCM.repository;

import com.SCM.IndexDto.IndexRendingSalesOrderByDistributor;
import com.SCM.IndexDto.IndexRendingSalesOrderByRetailer;
import com.SCM.IndexDto.SalesOrderPageDtoProjection;
import com.SCM.dto.SalesOrderAndDistributor;
import com.SCM.dto.SalesOrderByRetailerAndDistibutorGroup;
import com.SCM.dto.SalesOrderItemsResponse;
import com.SCM.dtoReports.PendingSalesOrderByRetailerWithoutDistributor;
import com.SCM.dtoReports.SalesOrderReportIndex;
import com.SCM.model.PurchaseOrder;
import com.SCM.model.SalesOrder;
import com.SCM.model.VoucherMaster;
import com.SCM.projectionDto.SalesOrderReportDto;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepo extends JpaRepository<SalesOrder, Integer> {
	
	@Query(value = "SELECT new com.SCM.dto.SalesOrderItemsResponse (soi.salesorderitemId,soi.soqty,soi.altsoqty,soi.per,soi.grossamount,soi.discount1,soi.someasurement,soi.rate,soi.mrp,soi.discount,soi.discountamount,soi.discountamount1,soi.total,soi.igst,soi.cgst,soi.sgst,soi.gst,soi.amount,soi.uomPrimary,soi.uomSecondary,soi.tradediscount,soi.schemeDiscount,soi.soquantity_placed_kg,soi.soquantity_placed,soi.dlp,soi.netAmount,soi.gstvalue,p.productName,p.productType,p.standardQtyPerBox,soi.unitofmeasurement,soi.dcitemspending,soi.dcitemsplaced,soi.dcstatus,so.distributor.tradeName,so.buyerorder) FROM SalesOrderItems soi "
			+ " JOIN SalesOrder so ON soi.salesorder.id = so.id JOIN  Product  p on soi.product.id = p.id "
			+ " WHERE so.distributor.id = ?1")
	public List<SalesOrderItemsResponse> fetchsalesOrders1(@Param("distid") int distid);
  
	
	@Query(value = "select * from sales_order so where so.voucherseries like  CONCAT('%', :search , '%') order by id desc limit 1", nativeQuery = true)
	SalesOrder searchByVoucher(String search);
  
  @Query(value = "SELECT so.id, so.buyerorder, so.destination, so.termsofdelivery FROM sales_order so LEFT JOIN distributor d ON so.distributor_id = d.id WHERE so.status = 'pending' AND d.id = :distid AND so.sodate BETWEEN :startdate AND :enddate", nativeQuery = true)
  List<SalesOrderReportIndex> fetchSalesOrderByDistributorId(long distid, String startdate, String enddate);
  
  @Query(value = "SELECT \r\n"
  		+ "  MAX(COALESCE(r.trade_name, d.trade_name)) AS Trade_Name,\r\n"
  		+ "  so.id,\r\n"
  		+ "  DATE_FORMAT(MAX(so.sodate), '%d-%m-%Y') AS sodate,\r\n"
  		+ "  MAX(csc.contactname) AS contactname,\r\n"
  		+ "  MAX(so.grossamount) AS grossamount,\r\n"
  		+ "  MAX(sta.name) AS state,\r\n"
  		+ "  MAX(so.igst) AS igst,\r\n"
  		+ "  MAX(so.sgst) AS sgst,\r\n"
  		+ "  MAX(so.cgst) AS cgst,\r\n"
  		+ "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n"
  		+ "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n"
  		+ "  MAX(so.grandtotal) AS grandtotal,\r\n"
  		+ "  MAX(so.remarks) AS remarks,\r\n"
  		+ "  MAX(so.vouchermaster_series) AS vouchermaster_series,\r\n"
  		+ "  DATE_FORMAT(MAX(so.createddate), '%d-%m-%Y') AS createddate,\r\n"
  		+ "  MAX(so.createdtime) AS createdtime,\r\n"
  		+ "  MAX(so.createbyname) AS createbyname,\r\n"
  		+ "  MAX(so.updatedbyname) AS updatedbyname,\r\n"
  		+ "  MAX(so.retailerstatus) AS retailerstatus,\r\n"
  		+ "  DATE_FORMAT(MAX(so.updateddate), '%d-%m-%Y') AS updateddate,\r\n"
  		+ "  MAX(so.updatedtime) AS updatedtime,\r\n"
  		+ "  MAX(so.status) AS status\r\n"
  		+ "FROM sales_order so\r\n"
  		+ "LEFT JOIN customer_sub_contacts csc ON csc.id=so.customersubcontacts_id \r\n"
  		+ "LEFT JOIN retailer r ON so.retailer_id = r.id\r\n"
  		+ "LEFT JOIN distributor d ON d.id=so.distributor_id\r\n"
  		+ "LEFT JOIN states sta ON d.stateid = sta.id or r.stateid=sta.id\r\n"
  		+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
  		+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
  		+ "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null and rs.rsmid=s1.id))\r\n"
  		+ "LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null and rs.aseid=s2.id))\r\n"
        + "GROUP BY so.id", nativeQuery = true)
  List<SalesOrderPageDtoProjection> indexSalesOrder(Pageable p);
  
  @Query(value = "select COALESCE(r.trade_name, d.trade_name) AS trade_name,so.id,DATE_FORMAT(so.sodate,'%d-%m-%Y') AS sodate,sta.name as state,so.igst,so.sgst,so.cgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,csc.contactname,so.grossamount,so.grandtotal,so.remarks,DATE_FORMAT(so.createddate,'%d-%m-%Y') AS createddate,so.createdtime,so.createbyname,so.updatedbyname,so.retailerstatus,DATE_FORMAT(so.updateddate,'%d-%m-%Y') as updateddate,so.updatedtime,so.status,so.vouchermaster_series from sales_order so "
  		+ "left join customer_sub_contacts csc on so.customersubcontacts_id=csc.id left join distributor d on so.distributor_id = d.id "
  		+ "LEFT JOIN retailer r ON so.retailer_id = r.id\r\n"
       + "LEFT JOIN states sta ON d.stateid = sta.id or r.stateid=sta.id\r\n"
  		+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
  		+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
  		+ "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null and rs.rsmid=s1.id))\r\n"
  		+ "LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null and rs.aseid=s2.id))\r\n"
  	   + "where so.id Like CONCAT('%', :search, '%')or so.sodate Like CONCAT('%', :search, '%') or csc.contactname Like CONCAT('%', :search, '%') "
  	 + " OR LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) "
     + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%')) "
  		+ "or so.grossamount Like CONCAT('%', :search, '%')or so.igst Like CONCAT('%', :search, '%') or so.status Like CONCAT('%', :search, '%') or so.grandtotal Like CONCAT('%', :search, '%') or so.remarks Like CONCAT('%', :search, '%')or so.createbyname Like CONCAT('%', :search, '%')or so.updatedbyname Like CONCAT('%', :search, '%') "
  		+ "GROUP BY so.id", nativeQuery = true)
  List<SalesOrderPageDtoProjection> searchBySalesOrder(String search, Pageable p);
  
  @Query(value = "select COALESCE(r.trade_name, d.trade_name) AS trade_name,so.id,so.sodate,csc.contactname,so.grossamount,sta.name as state,so.igst,so.sgst,so.cgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,so.grandtotal,so.remarks,so.createddate,so.createdtime,so.status,so.vouchermaster_series from sales_order so  "
  		+ "left join customer_sub_contacts csc on so.customersubcontacts_id=csc.id "
  		+ "LEFT JOIN retailer r ON so.retailer_id = r.id\r\n"
  		+ "LEFT JOIN distributor d ON d.id=so.distributor_id\r\n"
  		+ "LEFT JOIN states sta ON d.stateid = sta.id or r.stateid=sta.id\r\n"
  		+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
  		+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
  		+ "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null and rs.rsmid=s1.id))\r\n"
  		+ "LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null and rs.aseid=s2.id))\r\n"
  		+"  where so.distributor_id= :id "
  		+ "GROUP BY so.id", nativeQuery = true)
  List<SalesOrderPageDtoProjection> indexSalesOrder(long id);
  
  @Query(value = "select COALESCE(r.trade_name, d.trade_name) AS trade_name,so.id,so.sodate,csc.contactname,so.grossamount,sta.name as state,so.igst,so.sgst,so.cgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,so.grandtotal,so.remarks,so.createddate,so.createdtime,so.status,so.vouchermaster_series from sales_order so "
  		+ " left join customer_sub_contacts csc on so.customersubcontacts_id=csc.id "
  		+ "LEFT JOIN retailer r ON so.retailer_id = r.id\r\n"
  		+ "LEFT JOIN distributor d ON d.id=so.distributor_id\r\n"
  		+ "LEFT JOIN states sta ON d.stateid = sta.id or r.stateid=sta.id\r\n"
  		+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
  		+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
  		+ "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null and rs.rsmid=s1.id))\r\n"
  		+ "LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null and rs.aseid=s2.id))\r\n"
  		+" where so.distributor_id= :id "
  		+ "GROUP BY so.id", nativeQuery = true)
  List<SalesOrderPageDtoProjection> indexSalesOrder(long id, Pageable p);
  
  @Query(value="select COALESCE(r.trade_name, d.trade_name) AS trade_name,so.id,DATE_FORMAT(so.sodate,'%d-%m-%Y') AS sodate,csc.contactname,so.grossamount,sta.name as state,so.igst,so.sgst,so.cgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,so.grandtotal,so.remarks,DATE_FORMAT(so.createddate,'%d-%m-%Y') AS createddate,so.createdtime,so.createbyname,so.updatedbyname,so.retailerstatus,DATE_FORMAT(so.updateddate,'%d-%m-%Y') as updateddate,so.updatedtime,so.status,so.vouchermaster_series from sales_order so "
  		+ "left join customer_sub_contacts csc on so.customersubcontacts_id=csc.id "
  		+ "LEFT JOIN retailer r ON so.retailer_id = r.id\r\n"
  		+ "LEFT JOIN distributor d ON d.id=so.distributor_id\r\n"
  		+ "LEFT JOIN states sta ON d.stateid = sta.id or r.stateid=sta.id\r\n"
  		+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
  		+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
  		+ "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null and rs.rsmid=s1.id))\r\n"
  		+ "LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null and rs.aseid=s2.id))\r\n"
  		+ " where ds.rsmid=:id and rs.rsmid=:id\r\n"
  		+ "GROUP BY so.id",nativeQuery = true)
  List<SalesOrderPageDtoProjection> indexSalesOrderRsm(long id, Pageable p);
  
  
  @Query(value="select COALESCE(r.trade_name, d.trade_name) AS trade_name,so.id,DATE_FORMAT(so.sodate,'%d-%m-%Y') AS sodate,csc.contactname,so.grossamount,sta.name as state,so.igst,so.sgst,so.cgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,so.grandtotal,so.remarks,DATE_FORMAT(so.createddate,'%d-%m-%Y') AS createddate,so.createdtime,so.createbyname,so.updatedbyname,so.retailerstatus,DATE_FORMAT(so.updateddate,'%d-%m-%Y') as updateddate,so.updatedtime,so.status,so.vouchermaster_series from sales_order so left join customer_sub_contacts csc on so.customersubcontacts_id=csc.id  "
		  + "LEFT JOIN retailer r ON so.retailer_id = r.id\r\n"
	  		+ "LEFT JOIN distributor d ON d.id=so.distributor_id\r\n"
	  		+ "LEFT JOIN states sta ON d.stateid = sta.id or r.stateid=sta.id\r\n"
	  		+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
	  		+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
	  		+ "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null and rs.aseid=s2.id))\r\n"
	  		+ "where ds.rsmid=:id and rs.rsmid=:id "
	  		+ "GROUP BY so.id",nativeQuery = true)
	  List<SalesOrderPageDtoProjection> indexSalesOrderRsm(long id);
  
  @Query(value="select COALESCE(r.trade_name, d.trade_name) AS trade_name,so.id,DATE_FORMAT(so.sodate,'%d-%m-%Y') AS sodate,csc.contactname,so.grossamount,sta.name as state,so.igst,so.sgst,so.cgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,so.grandtotal,so.remarks,DATE_FORMAT(so.createddate,'%d-%m-%Y') AS createddate,so.createdtime,so.createbyname,so.updatedbyname,so.retailerstatus,DATE_FORMAT(so.updateddate,'%d-%m-%Y') as updateddate,so.updatedtime,so.status,so.vouchermaster_series from sales_order so left join customer_sub_contacts csc on so.customersubcontacts_id=csc.id  "
		  + "LEFT JOIN retailer r ON so.retailer_id = r.id\r\n"
	  		+ "LEFT JOIN distributor d ON d.id=so.distributor_id\r\n"
	  		+ "LEFT JOIN states sta ON d.stateid = sta.id or r.stateid=sta.id\r\n"
	  		+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
	  		+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
	  		+ "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null and rs.aseid=s2.id))\r\n"
	  		+ " where ds.asmid=:id and rs.asmid=:id\r\n"
	  		+ "GROUP BY so.id",nativeQuery = true)
	  List<SalesOrderPageDtoProjection> indexSalesOrderAsm(long id, Pageable p);
  
  @Query(value="select COALESCE(r.trade_name, d.trade_name) AS trade_name,so.id,DATE_FORMAT(so.sodate,'%d-%m-%Y') AS sodate,csc.contactname,so.grossamount,sta.name as state,so.igst,so.sgst,so.cgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,so.grandtotal,so.remarks,DATE_FORMAT(so.createddate,'%d-%m-%Y') AS createddate,so.createdtime,so.createbyname,so.updatedbyname,so.retailerstatus,DATE_FORMAT(so.updateddate,'%d-%m-%Y') as updateddate,so.updatedtime,so.status,so.vouchermaster_series from sales_order so left join customer_sub_contacts csc on so.customersubcontacts_id=csc.id  "
		  + "LEFT JOIN retailer r ON so.retailer_id = r.id\r\n"
	  		+ "LEFT JOIN distributor d ON d.id=so.distributor_id\r\n"
	  		+ "LEFT JOIN states sta ON d.stateid = sta.id or r.stateid=sta.id\r\n"
	  		+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
	  		+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
	  		+ "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null and rs.aseid=s2.id))\r\n"
	  		+ " where ds.asmid=:id and rs.asmid=:id\r\n"
	  		+ "GROUP BY so.id",nativeQuery = true)
	  List<SalesOrderPageDtoProjection> indexSalesOrderAsm(long id);
  
  
  @Query(value="select COALESCE(r.trade_name, d.trade_name) AS trade_name,so.id,DATE_FORMAT(so.sodate,'%d-%m-%Y') AS sodate,csc.contactname,so.grossamount,sta.name as state,so.igst,so.sgst,so.cgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,so.grandtotal,so.remarks,DATE_FORMAT(so.createddate,'%d-%m-%Y') AS createddate,so.createdtime,so.createbyname,so.updatedbyname,so.retailerstatus,DATE_FORMAT(so.updateddate,'%d-%m-%Y') as updateddate,so.updatedtime,so.status,so.vouchermaster_series from sales_order so "
  		+ " left join customer_sub_contacts csc on so.customersubcontacts_id=csc.id "
  	  + "LEFT JOIN retailer r ON so.retailer_id = r.id\r\n"
		+ "LEFT JOIN distributor d ON d.id=so.distributor_id\r\n"
		+ "LEFT JOIN states sta ON d.stateid = sta.id or r.stateid=sta.id\r\n"
		+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
		+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
		+ "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null and rs.rsmid=s1.id))\r\n"
  		+ "LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null and rs.aseid=s2.id))\r\n"
	  		+ " where ds.aseid=:id and rs.aseid=:id\r\n"
	  		+ "GROUP BY so.id",nativeQuery = true)
	  List<SalesOrderPageDtoProjection> indexSalesOrderAse(long id, Pageable p);
  
  
  @Query(value="select COALESCE(r.trade_name, d.trade_name) AS trade_name,so.id,DATE_FORMAT(so.sodate,'%d-%m-%Y') AS sodate,csc.contactname,so.grossamount,sta.name as state,so.igst,so.sgst,so.cgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,so.grandtotal,so.remarks,DATE_FORMAT(so.createddate,'%d-%m-%Y') AS createddate,so.createdtime,so.createbyname,so.updatedbyname,so.retailerstatus,DATE_FORMAT(so.updateddate,'%d-%m-%Y') as updateddate,so.updatedtime,so.status,so.vouchermaster_series from sales_order so "
  		+ " left join customer_sub_contacts csc on so.customersubcontacts_id=csc.id "
  	  + "LEFT JOIN retailer r ON so.retailer_id = r.id\r\n"
		+ "LEFT JOIN distributor d ON d.id=so.distributor_id\r\n"
		+ "LEFT JOIN states sta ON d.stateid = sta.id or r.stateid=sta.id\r\n"
		+ "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id\r\n"
		+ "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
		+ "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null  and rs.rsmid=s1.id))\r\n"
  		+ "LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null and rs.aseid=s2.id))\r\n"
	  		+ " where ds.aseid=:id and rs.aseid=:id\r\n"
	  		+ "GROUP BY so.id",nativeQuery = true)
	  List<SalesOrderPageDtoProjection> indexSalesOrderAse(long id);
  
  
//  @Query(value = "select COALESCE(r.trade_name, d.trade_name) AS trade_name,so.id,so.sodate,csc.contactname,so.grossamount,so.igst,so.grandtotal,so.remarks,so.createddate,so.createdtime,so.status from sales_order so  left join customer_sub_contacts csc on so.customersubcontacts_id=csc.id where so.distributor_id= :id and (so.id Like CONCAT('%', :search, '%')or so.sodate Like CONCAT('%', :search, '%') or csc.contactname Like CONCAT('%', :search, '%')or so.grossamount Like CONCAT('%', :search, '%') or REGEXP_LIKE(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name),'[^a-zA-Z0-9]','') or so.igst Like CONCAT('%', :search, '%') or so.status Like CONCAT('%', :search, '%') or so.grandtotal Like CONCAT('%', :search, '%') or so.remarks Like CONCAT('%', :search, '%')) ", nativeQuery = true)
//  List<SalesOrderPageDtoProjection> searchBySalesOrder(long id, String search, Pageable p);
  
  @Query(value = "SELECT COALESCE(r.trade_name, d.trade_name) AS trade_name, so.id, so.sodate, csc.contactname, so.grossamount,sta.name as state,so.igst,so.sgst,so.cgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename, so.grandtotal, so.remarks, so.createddate, so.createdtime, so.status,so.vouchermaster_series " +
	        "FROM sales_order so " +
	        "LEFT JOIN customer_sub_contacts csc ON so.customersubcontacts_id = csc.id " +
	        "LEFT JOIN distributor d ON so.distributor_id = d.id " +
	        "LEFT JOIN retailer r ON so.retailer_id = r.id " +
	    	"LEFT JOIN distributor_to_staff ds on d.id=ds.distributor_id "+
	    	"LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n" +
	    	"LEFT JOIN states sta on d.stateid=sta.id or r.stateid=sta.id "+
	    	 "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null  and rs.rsmid=s1.id))"+
	  		"LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null  and rs.aseid=s2.id))"+
	        "WHERE so.distributor_id = :id AND (" +
	        "CAST(so.id AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "CAST(so.sodate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "csc.contactname LIKE CONCAT('%', :search, '%') OR " +
	        "CAST(so.grossamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	   	 " LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) "+
	     "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%')) OR "+
	        "CAST(so.grandtotal AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "so.status LIKE CONCAT('%', :search, '%') OR " +
	        "so.igst LIKE CONCAT('%', :search, '%') OR " +
	        "so.remarks LIKE CONCAT('%', :search, '%')) "+
	    	"GROUP BY so.id",
	       nativeQuery = true)
	List<SalesOrderPageDtoProjection> searchBySalesOrder(@Param("id") long id, @Param("search") String search, Pageable p);

  @Query(value = "SELECT COALESCE(r.trade_name, d.trade_name) AS trade_name, " +
	        "so.id, so.sodate, csc.contactname, so.grossamount, sta.name AS state, " +
	        "so.igst, so.sgst, so.cgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename, " +
	        "so.grandtotal, so.remarks, so.createddate, so.createdtime, so.status, so.vouchermaster_series " +
	        "FROM sales_order so " +
	        "LEFT JOIN customer_sub_contacts csc ON so.customersubcontacts_id = csc.id " +
	        "LEFT JOIN distributor d ON so.distributor_id = d.id " +
	        "LEFT JOIN retailer r ON so.retailer_id = r.id " +
	        "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id " +
	        "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id " +
	        "LEFT JOIN states sta ON (d.stateid = sta.id OR r.stateid = sta.id) " +  // Avoid OR in JOIN
	        "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null and rs.rsmid=s1.id))"+
	  		"LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null and rs.aseid=s2.id))"+
	        "WHERE (ds.rsmid = :id and rs.rsmid = :id) AND (" +
	        "CAST(so.id AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "CAST(so.sodate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "csc.contactname LIKE CONCAT('%', :search, '%') OR " +
	        "LOWER(COALESCE(r.trade_name, d.trade_name, '')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "CAST(so.grossamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "CAST(so.grandtotal AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "so.status LIKE CONCAT('%', :search, '%') OR " +
	        "CAST(so.igst AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "so.remarks LIKE CONCAT('%', :search, '%')) " +
	        "GROUP BY so.id",
	        nativeQuery = true)
	List<SalesOrderPageDtoProjection> searchBySalesOrderRsm(long id, String search, Pageable p);



  @Query(value = "SELECT COALESCE(r.trade_name, d.trade_name) AS trade_name, " +
	        "so.id, so.sodate, csc.contactname, so.grossamount, sta.name AS state, " +
	        "so.igst, so.sgst, so.cgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename, " +
	        "so.grandtotal, so.remarks, so.createddate, so.createdtime, so.status, so.vouchermaster_series " +
	        "FROM sales_order so " +
	        "LEFT JOIN customer_sub_contacts csc ON so.customersubcontacts_id = csc.id " +
	        "LEFT JOIN distributor d ON so.distributor_id = d.id " +
	        "LEFT JOIN retailer r ON so.retailer_id = r.id " +
	        "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id " +
	        "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id " +
	        "LEFT JOIN states sta ON (d.stateid = sta.id OR r.stateid = sta.id) " +  // Avoid OR in JOIN
	        "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null and rs.rsmid=s1.id))"+
	  		"LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null and rs.aseid=s2.id))"+
	        "WHERE (ds.asmid = :id and rs.asmid = :id) AND (" +
	        "CAST(so.id AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "CAST(so.sodate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "csc.contactname LIKE CONCAT('%', :search, '%') OR " +
	        "LOWER(COALESCE(r.trade_name, d.trade_name, '')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "CAST(so.grossamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "CAST(so.grandtotal AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "so.status LIKE CONCAT('%', :search, '%') OR " +
	        "CAST(so.igst AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "so.remarks LIKE CONCAT('%', :search, '%')) " +
	        "GROUP BY so.id",
	    nativeQuery = true)
	List<SalesOrderPageDtoProjection> searchBySalesOrderAsm(long id, String search, Pageable p);




  
  @Query(value = "SELECT COALESCE(r.trade_name, d.trade_name) AS trade_name, " +
	        "so.id, so.sodate, csc.contactname, so.grossamount, sta.name AS state, " +
	        "so.igst, so.sgst, so.cgst, GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename, " +
	        "so.grandtotal, so.remarks, so.createddate, so.createdtime, so.status, so.vouchermaster_series " +
	        "FROM sales_order so " +
	        "LEFT JOIN customer_sub_contacts csc ON so.customersubcontacts_id = csc.id " +
	        "LEFT JOIN distributor d ON so.distributor_id = d.id " +
	        "LEFT JOIN retailer r ON so.retailer_id = r.id " +
	        "LEFT JOIN distributor_to_staff ds ON d.id = ds.distributor_id " +
	        "LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id " +
	        "LEFT JOIN states sta ON (d.stateid = sta.id OR r.stateid = sta.id) " +  // Avoid OR in JOIN
	        "LEFT JOIN staff s1 ON ((so.retailer_id is null and ds.rsmid = s1.id) or (so.retailer_id is not null and rs.rsmid=s1.id))"+
	  		"LEFT JOIN staff s2 ON ((so.retailer_id is null and ds.aseid = s2.id) or (so.retailer_id is not null and rs.aseid=s2.id))"+
	        "WHERE (ds.aseid = :id and rs.aseid = :id) AND (" +
	        "CAST(so.id AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "CAST(so.sodate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "csc.contactname LIKE CONCAT('%', :search, '%') OR " +
	        "LOWER(COALESCE(r.trade_name, d.trade_name, '')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "CAST(so.grossamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "CAST(so.grandtotal AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "so.status LIKE CONCAT('%', :search, '%') OR " +
	        "CAST(so.igst AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
	        "so.remarks LIKE CONCAT('%', :search, '%')) " +
	        "GROUP BY so.id", 
	        nativeQuery = true)
	List<SalesOrderPageDtoProjection> searchBySalesOrderAse(long id, String search, Pageable p);

  
  
  
  @Query(value = "select so.*,sz.state_name,d.id as did,d.aadharcard,d.alter_email,d.alter_mobile_number,d.billing_address,d.box_product_discount,d.city,d.cooker_product_discount,d.corporaet_product_discount,d.country,d.credit_days,d.credit_limit,d.delivery_location,d.discount_structure,d.distributor_name1,d.distributor_name2,d.doa1,d.doa2,d.dob1,d.dob2,d.email1,d.email2,d.gst_number,d.gst_type,d.kg_product_discount,d.mob_no1,d.mob_no2,d.nosh_product_discount,d.pan_number,d.pancard,d.per_email,d.per_mobile_number,d.pin_code,d.scheme_discount,d.schemebox_product_discount,d.schemecooker_product_discount,d.schemecorporate_product_discount,d.schemekg_product_discount,d.schemenosh_product_disocunt,d.trade_name,d.transporter_name from sales_order so    left join distributor d on so.distributor_id = d.id    left join state_zone sz on d.stateid = sz.id where so.id = ?1", nativeQuery = true)
  List<SalesOrderAndDistributor> getSoById(int soid);
  
  
  
  @Query(value = "SELECT * FROM   "
  		+ " (SELECT so.id,so.buyerorder,so.destination,so.termsofdelivery,so.status,woi.measurement,"
  		+ " (woi.measurement - soi.soquantity_placed) as www,"
  		+ "  CASE\t\tWHEN (woi.measurement - soi.soqty) > 0 THEN 'pending'"
  		+ " ELSE 'converted'"
  		+ "END AS qtystatus    "
  		+ " FROM sales_order_items soi "
  		+ " LEFT JOIN sales_order so ON soi.salesorderid = so.id"
  		+ " LEFT JOIN retailer r ON so.retailer_id = r.id "
  		+ " LEFT JOIN work_order wo ON r.id = wo.ret_id  "
  		+ " LEFT JOIN work_order_item woi ON wo.id = woi.wid   "
  		+ " WHERE so.retailer_id = ?1 and so.sodate between ?2 and ?3 and so.retailerstatus ='retailer') AS subquery WHERE qtystatus = 'pending'", nativeQuery = true)
  List<PendingSalesOrderByRetailerWithoutDistributor> pendingsobyretailerwithoutdistributor(int retid, String fromdate, String todate);
  
  @Query(value = "SELECT * FROM   (SELECT so.id,so.buyerorder,so.destination,so.termsofdelivery,so.status,woi.measurement,\t (woi.measurement - soi.soquantity_placed) as www,\t CASE\t\tWHEN (woi.measurement - soi.soqty) > 0 THEN 'pending'\t\tELSE 'converted'\t END AS qtystatus  FROM sales_order_items soi    LEFT JOIN sales_order so ON soi.salesorderid = so.id    LEFT JOIN retailer r ON so.retailer_id = r.id    LEFT JOIN work_order wo ON r.id = wo.ret_id    LEFT JOIN work_order_item woi ON wo.id = woi.wid    WHERE so.retailer_id = ?1 and so.sodate between ?2 and ?3 and so.retailerstatus ='customer') AS subquery    WHERE qtystatus = 'pending'", nativeQuery = true)
  List<PendingSalesOrderByRetailerWithoutDistributor> pendingsobyretailercustomer(int retid, String fromdate, String todate);
  
//  ----------------------------------------
  @Query(value ="SELECT soi.salesorderitem_id,p.product_name as productname,p.ean_code,p.id AS productid,p.uom_primary,p.uom_secondary,p.product_type,soi.someasurement AS sopcsqty,soi.soqty AS sokgqty,\r\n"
  		+ "    COALESCE(si.salesquantity, 0) AS sipcsqty,COALESCE(si.salesquantitykgs, 0) AS sikgqty,soi.cancelpcsqty,soi.cancelkgqty,si.cancelsalestatus,\r\n"
  		+ "    so.id AS salesorderid,COALESCE(si.salesitemid, 0) AS salesitemid,soi.cancelstatus,soi.addcancelpcsqty,soi.addcancelkgqty,\r\n"
  		+ "    CASE\r\n"
  		+ "        WHEN (soi.cancelpcsqty = 0) AND si.cancelsalestatus IS NULL THEN (COALESCE(soi.pendingcancelpcsqty, 0) - COALESCE(si.salesquantity, 0))\r\n"
  		+ "        ELSE soi.pendingcancelpcsqty\r\n"
  		+ "    END AS pendingcancelpcsqty,\r\n"
  		+ "    CASE\r\n"
  		+ "        WHEN (soi.cancelpcsqty = 0) AND si.cancelsalestatus IS NULL THEN ((COALESCE(soi.pendingcancelpcsqty, 0) - COALESCE(si.salesquantity, 0)) * p.uom_secondary)\r\n"
  		+ "        ELSE soi.pendingcancelkgqty\r\n"
  		+ "    END AS pendingcancelkgqty\r\n"
  		+ "FROM sales_order so\r\n"
  		+ "LEFT JOIN retailer r ON so.retailer_id = r.id\r\n"
  		+ "LEFT JOIN sales_order_items soi ON so.id = soi.salesorderid\r\n"
  		+ "LEFT JOIN product p ON soi.product_id = p.id\r\n"
  		+ "LEFT JOIN sales_items si ON p.id = si.product_id\r\n"
  		+ "WHERE so.status = 'pending' AND r.id = ?1 AND so.sodate BETWEEN ?2 AND ?3 AND soi.cancelstatus != 'Cancelled'" , nativeQuery = true)
  List<IndexRendingSalesOrderByRetailer> PendingSalesOrderByRetailerReport(int rid, String fromdate, String todate);
  
  @Query(value = "SELECT \r\n"
  		+ "    soi.salesorderitem_id,p.product_name as productname,p.ean_code,p.id AS productid,p.uom_primary,p.uom_secondary,p.product_type,soi.someasurement AS sopcsqty,soi.soqty AS sokgqty,\r\n"
  		+ "    COALESCE(si.salesquantity, 0) AS sipcsqty,COALESCE(si.salesquantitykgs, 0) AS sikgqty, soi.dist_cancelpcsqty,soi.dist_cancelkgqty,si.cancelsalestatus,\r\n"
  		+ "    so.id AS salesorderid,COALESCE(si.salesitemid, 0) AS salesitemid,soi.cancelstatus,soi.dist_addcancelpcsqty,soi.dist_addcancelkgqty,\r\n"
  		+ "    CASE\r\n"
  		+ "        WHEN (soi.cancelpcsqty = 0) AND si.cancelsalestatus IS NULL THEN (COALESCE(soi.dist_pendingcancelpcsqty, 0) - COALESCE(si.salesquantity, 0))\r\n"
  		+ "        ELSE soi.dist_pendingcancelpcsqty\r\n"
  		+ "    END AS dist_pendingcancelpcsqty,\r\n"
  		+ "    CASE\r\n"
  		+ "        WHEN (soi.cancelpcsqty = 0) AND si.cancelsalestatus IS NULL THEN ((COALESCE(soi.dist_pendingcancelpcsqty, 0) - COALESCE(si.salesquantity, 0)) * p.uom_secondary)\r\n"
  		+ "        ELSE soi.dist_pendingcancelkgqty\r\n"
  		+ "    END AS dist_pendingcancelkgqty\r\n"
  		+ "FROM sales_order so\r\n"
  		+ "LEFT JOIN distributor d ON so.distributor_id = d.id\r\n"
  		+ "LEFT JOIN sales_order_items soi ON so.id = soi.salesorderid\r\n"
  		+ "LEFT JOIN product p ON soi.product_id = p.id\r\n"
  		+ "LEFT JOIN sales_items si ON p.id = si.product_id\r\n"
  		+ "WHERE so.status = 'pending' AND d.id = ?1 AND so.sodate BETWEEN ?2 AND ?3 AND soi.cancelstatus != 'Cancelled'\r\n"
  		+ "group by soi.salesorderitem_id", nativeQuery = true)
  List<IndexRendingSalesOrderByDistributor> PendingSalesOrderByDistributorReport(int did, String fromdate, String todate);
  
  
  @Modifying
  @Transactional
  @Query(value = "UPDATE sales_order_items soi"
  		+ " LEFT JOIN product p ON soi.product_id = p.id"
  		+ " LEFT JOIN sales_items si ON p.id = si.product_id"
  		+ " SET soi.pendingcancelpcsqty = ?1,soi.pendingcancelkgqty = ?2"
  		+ " WHERE soi.salesorderitem_id = ?3", nativeQuery = true)
  void UpdateSoPendingPcs(int pcsqty, int soitemid);
  
  @Modifying
  @Transactional
  @Query(value = "UPDATE sales_order_items soi "
  		+ " SET soi.pendingcancelpcsqty = soi.someasurement"
  		+ " WHERE soi.salesorderitem_id = ?1", nativeQuery = true)
  void UpdatePendIntoPcs(int pcsqty, int soitemid);
  
  
  @Query(value = "SELECT soi.salesorderitem_id,p.product_name,p.ean_code,p.id AS productid,p.uom_primary,p.uom_secondary,p.product_type,soi.someasurement AS sopcsqty,soi.soqty AS sokgqty,\r\n"
	  		+ "    COALESCE(si.salesquantity, 0) AS sipcsqty,COALESCE(si.salesquantitykgs, 0) AS sikgqty,soi.cancelpcsqty,soi.cancelkgqty,si.cancelsalestatus,\r\n"
	  		+ "    so.id AS salesorderid,COALESCE(si.salesitemid, 0) AS salesitemid,soi.cancelstatus,soi.addcancelpcsqty,soi.addcancelkgqty,\r\n"
	  		+ "    CASE\r\n"
	  		+ "        WHEN (soi.cancelpcsqty = 0) AND si.cancelsalestatus IS NULL THEN (COALESCE(soi.pendingcancelpcsqty, 0) - COALESCE(si.salesquantity, 0))\r\n"
	  		+ "        ELSE soi.pendingcancelpcsqty\r\n"
	  		+ "    END AS pendingcancelpcsqty,\r\n"
	  		+ "    CASE\r\n"
	  		+ "        WHEN (soi.cancelpcsqty = 0) AND si.cancelsalestatus IS NULL THEN ((COALESCE(soi.pendingcancelpcsqty, 0) - COALESCE(si.salesquantity, 0)) * p.uom_secondary)\r\n"
	  		+ "        ELSE soi.pendingcancelkgqty\r\n"
	  		+ "    END AS pendingcancelkgqty\r\n"
	  		+ "FROM sales_order so\r\n"
	  		+ "LEFT JOIN retailer r ON so.retailer_id = r.id\r\n"
	  		+ "LEFT JOIN sales_order_items soi ON so.id = soi.salesorderid\r\n"
	  		+ "LEFT JOIN product p ON soi.product_id = p.id\r\n"
	  		+ "LEFT JOIN sales_items si ON p.id = si.product_id\r\n"
	  		+ "WHERE soi.salesorderitem_id = ?1", nativeQuery = true)
  List<IndexRendingSalesOrderByRetailer> PendingSalesOrderID(int soitemid);
  
  
  @Query(value = "SELECT \r\n"
	  		+ "    soi.salesorderitem_id,p.product_name,p.ean_code,p.id AS productid,p.uom_primary,p.uom_secondary,p.product_type,soi.someasurement AS sopcsqty,soi.soqty AS sokgqty,\r\n"
	  		+ "    COALESCE(si.salesquantity, 0) AS sipcsqty,COALESCE(si.salesquantitykgs, 0) AS sikgqty, soi.dist_cancelpcsqty,soi.dist_cancelkgqty,si.cancelsalestatus,\r\n"
	  		+ "    so.id AS salesorderid,COALESCE(si.salesitemid, 0) AS salesitemid,soi.cancelstatus,soi.dist_addcancelpcsqty,soi.dist_addcancelkgqty,\r\n"
	  		+ "    CASE\r\n"
	  		+ "        WHEN (soi.cancelpcsqty = 0) AND si.cancelsalestatus IS NULL THEN (COALESCE(soi.dist_pendingcancelpcsqty, 0) - COALESCE(si.salesquantity, 0))\r\n"
	  		+ "        ELSE soi.dist_pendingcancelpcsqty\r\n"
	  		+ "    END AS dist_pendingcancelpcsqty,\r\n"
	  		+ "    CASE\r\n"
	  		+ "        WHEN (soi.cancelpcsqty = 0) AND si.cancelsalestatus IS NULL THEN ((COALESCE(soi.dist_pendingcancelpcsqty, 0) - COALESCE(si.salesquantity, 0)) * p.uom_secondary)\r\n"
	  		+ "        ELSE soi.dist_pendingcancelkgqty\r\n"
	  		+ "    END AS dist_pendingcancelkgqty\r\n"
	  		+ "FROM sales_order so\r\n"
	  		+ "LEFT JOIN distributor d ON so.distributor_id = d.id\r\n"
	  		+ "LEFT JOIN sales_order_items soi ON so.id = soi.salesorderid\r\n"
	  		+ "LEFT JOIN product p ON soi.product_id = p.id\r\n"
	  		+ "LEFT JOIN sales_items si ON p.id = si.product_id\r\n"
	  		+ "WHERE soi.salesorderitem_id = ?1", nativeQuery = true)
  List<IndexRendingSalesOrderByDistributor> PendingSalesOrderID1(int soitemid);
  
  
  @Query(value = "select so.id,\r\n"
	        + "	 so.retailer_id as retailerid,\r\n"
	        + "    so.distributor_id as dis,soi.altsoqty,soi.amount,soi.dcitemspending,\r\n"
	        + "	soi.dcitemsplaced,soi.dlp,soi.grossamount,soi.gstvalue,soi.mrp,soi.net_amount as netamount,"
	        + " soi.product_name as productname,soi.product_type as producttype\r\n"
	        + "	,soi.scheme_discount as schemediscount,soi.soquantity_placed as soquantityplaced,"
	        + " soi.total,soi.tradediscount,soi.unitofmeasurement,soi.uom_primary as uomprimary,\r\n"
	        + "	soi.uom_secondary as uomsecondary,soi.product_id as productid,\r\n"
	        + "	'ROLE_RETAILER' AS rolename,\r\n"
	        + "	soi.salesorderitem_id as saleitemid,soi.dcstatus from sales_order so\r\n"
	        + "	left join sales_order_items soi\r\n"
	        + "	on so.id=soi.salesorderid\r\n"
	        + " where so.retailer_id=:rid2 ", nativeQuery = true)
	List<SalesOrderByRetailerAndDistibutorGroup> findSalesOrderByRetailerGroup(Pageable p, long rid2);
	
	
	@Query(value = "select so.id,\r\n"
	        + "	soi.altsoqty,soi.amount,soi.dcitemspending,\r\n"
	        + "	soi.dcitemsplaced,soi.dlp,soi.grossamount,soi.gstvalue,soi.mrp,soi.net_amount as netamount,"
	        + " soi.product_name as productname,soi.product_type as producttype\r\n"
	        + "	,soi.scheme_discount as schemediscount,soi.soquantity_placed as soquantityplaced,"
	        + " soi.total,soi.tradediscount,soi.unitofmeasurement,soi.uom_primary as uomprimary,\r\n"
	        + "	soi.uom_secondary as uomsecondary,soi.product_id as productid,\r\n"
	        + "	'ROLE_RETAILER' AS rolename,\r\n"
	        + "	soi.salesorderitem_id as saleitemid,soi.dcstatus from sales_order so\r\n"
	        + "	left join sales_order_items soi\r\n"
	        + "	on so.id=soi.salesorderid\r\n"
	        + " where so.distributor_id=:did ",nativeQuery = true)
	List<SalesOrderByRetailerAndDistibutorGroup> findSalesOrderByDistributorGroup(Pageable p,long did);
	
	
	
	@Query(value = "select so.id, so.createddate,so.retailer_id as retailerid,so.distributor_id as distributorid, \r\n"
	        + "	soi.altsoqty,soi.amount,soi.dcitemspending,\r\n"
	        + "	soi.dcitemsplaced,soi.dlp,soi.grossamount,soi.gstvalue,soi.mrp,soi.net_amount as netamount,"
	        + " soi.product_name as productname,soi.product_type as producttype\r\n"
	        + "	,soi.scheme_discount as schemediscount,soi.soquantity_placed as soquantityplaced,"
	        + " soi.total,soi.tradediscount,soi.unitofmeasurement,soi.uom_primary as uomprimary,\r\n"
	        + "	soi.uom_secondary as uomsecondary,soi.product_id as productid,\r\n"
	        + "	'ROLE_RETAILER' AS rolename,\r\n"
	        + "	soi.salesorderitem_id as saleitemid,soi.dcstatus from sales_order so\r\n"
	        + "	left join sales_order_items soi\r\n"
	        + "	on so.id=soi.salesorderid\r\n"
	        + " where so.retailer_id=:rid2 or so.distributor_id=:did", nativeQuery = true)
	List<SalesOrderByRetailerAndDistibutorGroup> findSalesOrderByRetailerAndDistributorGroup(Pageable p, long rid2,long did);

	
	
	
	
	@Query(value = "select "
			+ "so.id,"
			+ " r.trade_name as retailername,\r\n"
			+ " d.trade_name as distributorname"
			+ ",so.createddate, so.retailer_id as retailerid, so.distributor_id as distributorid, \r\n"
	        + "	soi.altsoqty, soi.amount, soi.dcitemspending, \r\n"
	        + "	soi.dcitemsplaced, soi.dlp, soi.grossamount, soi.gstvalue, soi.mrp, soi.net_amount as netamount, \r\n"
	        + "	soi.product_name as productname, soi.product_type as producttype, \r\n"
	        + "	soi.scheme_discount as schemediscount, soi.soquantity_placed as soquantityplaced, \r\n"
	        + "	soi.total, soi.tradediscount, soi.unitofmeasurement, soi.uom_primary as uomprimary, \r\n"
	        + "	soi.uom_secondary as uomsecondary, soi.product_id as productid, \r\n"
	        + "	'ROLE_RETAILER' AS rolename, \r\n"
	        + "	soi.salesorderitem_id as saleitemid, soi.dcstatus from sales_order so \r\n"
	        + "	left join sales_order_items soi \r\n"
	        + "	on so.id = soi.salesorderid \r\n"
	        + " LEFT JOIN retailer r\r\n"
	        + "    on r.id=so.retailer_id\r\n"
	        + " LEFT JOIN distributor d\r\n"
	        + "	on d.id=so.distributor_id\r\n"
	        + " where so.retailer_id IN (:rid2) or so.distributor_id IN (:did)", nativeQuery = true)
	List<SalesOrderByRetailerAndDistibutorGroup> findSalesOrderByRetailerlistAndDistributorlistGroup(Pageable p, @Param("rid2") List<Long> rid2, @Param("did") List<Long> did);

	@Query(value = "select "
			+ "so.id,"
			+ " r.trade_name as retailername,\r\n"
			+ " d.trade_name as distributorname"
			+ ",so.createddate, so.retailer_id as retailerid, so.distributor_id as distributorid, \r\n"
	        + "	soi.altsoqty, soi.amount, soi.dcitemspending, \r\n"
	        + "	soi.dcitemsplaced, soi.dlp, soi.grossamount, soi.gstvalue, soi.mrp, soi.net_amount as netamount, \r\n"
	        + "	soi.product_name as productname, soi.product_type as producttype, \r\n"
	        + "	soi.scheme_discount as schemediscount, soi.soquantity_placed as soquantityplaced, \r\n"
	        + "	soi.total, soi.tradediscount, soi.unitofmeasurement, soi.uom_primary as uomprimary, \r\n"
	        + "	soi.uom_secondary as uomsecondary, soi.product_id as productid, \r\n"
	        + "	'ROLE_RETAILER' AS rolename, \r\n"
	        + "	soi.salesorderitem_id as saleitemid, soi.dcstatus from sales_order so \r\n"
	        + "	left join sales_order_items soi \r\n"
	        + "	on so.id = soi.salesorderid \r\n"
	        + " LEFT JOIN retailer r\r\n"
	        + "    on r.id=so.retailer_id\r\n"
	        + " LEFT JOIN distributor d\r\n"
	        + "	on d.id=so.distributor_id\r\n"
	        
	        + " where so.retailer_id IN (:rid2) or so.distributor_id IN (:did)", nativeQuery = true)
	List<SalesOrderByRetailerAndDistibutorGroup> findSalesOrderByRetailerlistAndDistributorlistGroup( @Param("rid2") List<Long> rid2, @Param("did") List<Long> did);


	@Query(value = "select "
			+ " so.id,"
			+ " r.trade_name as retailername,\r\n"
			+ " d.trade_name as distributorname"
			+ ",so.createddate, so.retailer_id as retailerid, so.distributor_id as distributorid, \r\n"
	        + "	soi.altsoqty, soi.amount, soi.dcitemspending, \r\n"
	        + "	soi.dcitemsplaced, soi.dlp, soi.grossamount, soi.gstvalue, soi.mrp, soi.net_amount as netamount, \r\n"
	        + "	soi.product_name as productname, soi.product_type as producttype, \r\n"
	        + "	soi.scheme_discount as schemediscount, soi.soquantity_placed as soquantityplaced, \r\n"
	        + "	soi.total, soi.tradediscount, soi.unitofmeasurement, soi.uom_primary as uomprimary, \r\n"
	        + "	soi.uom_secondary as uomsecondary, soi.product_id as productid, \r\n"
	        + "	'ROLE_RETAILER' AS rolename, \r\n"
	        + "	soi.salesorderitem_id as saleitemid, soi.dcstatus from sales_order so \r\n"
	        + "	left join sales_order_items soi \r\n"
	        + "	on so.id = soi.salesorderid \r\n"
	        + " LEFT JOIN retailer r\r\n"
	        + "    on r.id=so.retailer_id\r\n"
	        + " LEFT JOIN distributor d\r\n"
	        + "	on d.id=so.distributor_id\r\n"  
	        +"WHERE (so.retailer_id IN (:rid2) OR so.distributor_id IN (:did)) " +
	        "AND (soi.product_name LIKE CONCAT('%', :search, '%'))", 
	        nativeQuery = true)
	List<SalesOrderByRetailerAndDistibutorGroup> findSalesOrderByRetailerlistAndDistributorlistGroupForSearch(Pageable p, @Param("rid2") List<Long> rid2, @Param("did") List<Long> did, @Param("search") String search);
	
	
	
	
	
	
	
//	@Query(value = "select so.id,so.sodate,so.retailer_id as retailer,so.distributor_id as distributor,"
//			+ "soi.product_id as product,soi.soquantity_placed as pcs,soi.soquantity_placed_kg as kg from sales_order so\r\n"
//			+ "left join sales_order_items soi on so.id=soi.salesorderid \r\n"
//			+ "where (so.retailer_id IN (:rid) or so.distributor_id IN (:did))\r\n"
//			+ "and (so.sodate between :fromdate and :todate)", 
//	        nativeQuery = true)
//	List<SalesOrderReportDto> findAllSalesOrderByDistListAndRetList( @Param("rid") List<Long> rid, @Param("did") List<Long> did,@Param("fromdate") String fromdate,@Param("todate") String todate);
//

//	@Query(value = "SELECT \r\n"
//			+ "    soi.product_id AS productid,\r\n"
//			+ "    soi.addcancelkgqty AS cancelkgqty,\r\n"
//			+ "    soi.addcancelpcsqty AS cancelpcsqty,\r\n"
//			+ "    soi.dist_addcancelkgqty AS distcancelkgqty,\r\n"
//			+ "    soi.dist_addcancelpcsqty AS distcancelpcsqty,\r\n"
//			+ "    p.product_name as productname,\r\n"
//			+ "    SUM(soi.someasurement) AS sopcsqty,\r\n"
//			+ "    SUM(soi.soqty) AS sokgqty\r\n"
//			+ "FROM \r\n"
//			+ "    sales_order so\r\n"
//			+ "LEFT JOIN \r\n"
//			+ "    sales_order_items soi ON so.id = soi.salesorderid\r\n"
//			+ "left join \r\n"
//			+ "product p on p.id=soi.product_id\r\n"
//			+ "WHERE \r\n"
//			+ "    (so.retailer_id IN (:rid) OR so.distributor_id IN (:did))\r\n"
//			+ "    AND (so.sodate BETWEEN :fromdate AND :todate)\r\n"
//			+ "GROUP BY \r\n"
//			+ "    soi.product_id",			
//	        nativeQuery = true)
//	List<SalesOrderReportDto> findAllSalesOrderByDistListAndRetList( @Param("rid") List<Long> rid, @Param("did") List<Long> did,@Param("fromdate") String fromdate,@Param("todate") String todate);
	  
 
	@Query(value = "SELECT soi.product_id AS productid,\r\n"
			+ "(coalesce(soi.addcancelkgqty,0) + coalesce(soi.dist_addcancelkgqty,0) ) AS cancelkgqty,\r\n"
			+ "(coalesce(soi.addcancelpcsqty,0) + coalesce(soi.dist_addcancelpcsqty,0) ) AS cancelpcsqty,\r\n"
			+ "p.product_name as productname,\r\n"
			+ "SUM(soi.someasurement) AS sopcsqty,\r\n"
			+ "SUM(soi.soqty) AS sokgqty\r\n"
			+ " FROM sales_order so\r\n"
			+ " LEFT JOIN sales_order_items soi ON so.id = soi.salesorderid\r\n"
			+ " left join product p on p.id=soi.product_id\r\n"
			+ " WHERE (so.retailer_id IN (:rid) OR so.distributor_id IN (:did)) AND (so.sodate BETWEEN :fromdate AND :todate) \r\n"
			+ " GROUP BY soi.product_id,addcancelkgqty,dist_addcancelkgqty,addcancelpcsqty,dist_addcancelpcsqty",			
	        nativeQuery = true)
	List<SalesOrderReportDto> findAllSalesOrderByDistListAndRetList( @Param("rid") List<Long> rid, @Param("did") List<Long> did,@Param("fromdate") String fromdate,@Param("todate") String todate);
	  
	
    @Query(value = "SELECT * FROM sales_order so order by id desc limit 1",nativeQuery = true)
    SalesOrder lastrowstatus();
    
    SalesOrder findTopByVoucherMasterOrderByIdDesc(VoucherMaster voucher);
//
//
    SalesOrder findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(VoucherMaster voucher);
	
    SalesOrder findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(VoucherMaster voucher);  
	
	
}
