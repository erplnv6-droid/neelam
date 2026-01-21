package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.model.DeliveryCharge;
import com.SCM.model.SalesOrder;
import com.SCM.model.VoucherMaster;
import com.SCM.projection.DeliveryChallan;


@Repository
public interface DeliveryChargeRepo extends JpaRepository<DeliveryCharge, Integer> {

	
    @Query(value = "select * from scm.delivery_charge dc where dc.salesorder_id = ?1",nativeQuery = true)
    public List<DeliveryCharge> getDeliveryChargeBySalesOrderId(String soid);
    
    
	@Query(value = "SELECT del.id,cu.contactname,DATE_FORMAT(del.dcdate,'%d-%m-%Y') AS dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,DATE_FORMAT(del.createddate,'%d-%m-%Y') AS createddate,del.createbyname,del.updatedbyname,del.createdtime,del.taxvalue,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,\r\n"
			+ "     DATE_FORMAT(del.updateddate,'%d-%m-%Y') as updateddate,del.updatedtime,del.vouchermaster_series\r\n"
			+ "			  from delivery_charge del\r\n"
			+ "			left join customer_sub_contacts cu on cu.id=del.customersubcontacts_id \r\n"
			+ "             left join retailer r on del.retailer_id=r.id \r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "			  left join distributor d on del.distributor_id = d.id\r\n"
			+ "              left join states sta on r.stateid=sta.id or d.stateid=sta.id\r\n"
			+ "                left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ " GROUP BY del.id",nativeQuery = true)
	List<DeliveryChallan> indexDeliveryChallans(Pageable p);
    
	

	@Query(value = "SELECT del.id,cu.contactname,del.dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,DATE_FORMAT(del.createddate,'%d-%m-%Y') AS createddate,del.createbyname,del.updatedbyname,del.createdtime,del.taxvalue,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(del.updateddate,'%d-%m-%Y') as updateddate,del.updatedtime,del.vouchermaster_series"
			+ "  from delivery_charge del"
			+ "  left join customer_sub_contacts cu on cu.id=del.customersubcontacts_id "
		      + " left join retailer r on del.retailer_id=r.id "
		      + " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "  left join distributor d on del.distributor_id = d.id "
			+ "              left join states sta on d.stateid=sta.id\r\n"
			+ "                left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ " where del.distributor_id= :id "
			+ "GROUP BY del.id",nativeQuery = true)
	List<DeliveryChallan> indexDeliveryChallans(long id);
	
	
	@Query(value = "SELECT del.id,cu.contactname,del.dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,DATE_FORMAT(del.createddate,'%d-%m-%Y') AS createddate,del.createbyname,del.updatedbyname,del.createdtime,del.taxvalue,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(del.updateddate,'%d-%m-%Y') as updateddate,del.updatedtime,del.vouchermaster_series"
			+ "  from delivery_charge del"
			+ "  left join customer_sub_contacts cu on cu.id=del.customersubcontacts_id "
			+ " left join retailer r on del.retailer_id=r.id "
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
		    + "  left join distributor d on del.distributor_id = d.id "
		    + "              left join states sta on d.stateid=sta.id\r\n"
			+ "                left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n"
			+ " where del.distributor_id= :id "
			+ "GROUP BY del.id",nativeQuery = true)
	List<DeliveryChallan> indexDeliveryChallans(Pageable p,long id);
	
	
	@Query(value = "SELECT del.id,cu.contactname,del.dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,DATE_FORMAT(del.createddate,'%d-%m-%Y') AS createddate,del.createbyname,del.updatedbyname,del.createdtime,del.taxvalue,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(del.updateddate,'%d-%m-%Y') as updateddate,del.updatedtime,del.vouchermaster_series\r\n"
			+ "			  from delivery_charge del\r\n"
			+ "			  left join customer_sub_contacts cu on cu.id=del.customersubcontacts_id \r\n"
			+ "			  left join distributor d on del.distributor_id = d.id\r\n"
			+ "              left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " left join retailer r on del.retailer_id=r.id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
		+ "              left join states sta on d.stateid=sta.id\r\n"
		+ "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n" 
			+ "              where ds.rsmid=:id "
			+ "GROUP BY del.id",nativeQuery = true)
	List<DeliveryChallan> indexDeliveryChallansRsm(Pageable p,long id);
	
	@Query(value = "SELECT del.id,cu.contactname,del.dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,DATE_FORMAT(del.createddate,'%d-%m-%Y') AS createddate,del.createbyname,del.updatedbyname,del.createdtime,del.taxvalue,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(del.updateddate,'%d-%m-%Y') as updateddate,del.updatedtime,del.vouchermaster_series\r\n"
			+ "			  from delivery_charge del\r\n"
			+ "			  left join customer_sub_contacts cu on cu.id=del.customersubcontacts_id \r\n"
			+ "			  left join distributor d on del.distributor_id = d.id\r\n"
			+ "              left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " left join retailer r on del.retailer_id=r.id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
		+ "              left join states sta on d.stateid=sta.id\r\n"
		+ "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n" 
	+ " where ds.rsmid=:id "
			+ "GROUP BY del.id",nativeQuery = true)
	List<DeliveryChallan> indexDeliveryChallansRsm(long id);
	
	@Query(value = "SELECT del.id,cu.contactname,del.dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,DATE_FORMAT(del.createddate,'%d-%m-%Y') AS createddate,del.createbyname,del.updatedbyname,del.createdtime,del.taxvalue,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(del.updateddate,'%d-%m-%Y') as updateddate,del.updatedtime,del.vouchermaster_series\r\n"
			+ "			  from delivery_charge del\r\n"
			+ "			  left join customer_sub_contacts cu on cu.id=del.customersubcontacts_id \r\n"
			+ "			  left join distributor d on del.distributor_id = d.id\r\n"
			+ "              left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " left join retailer r on del.retailer_id=r.id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "              left join staff s on ds.asmid=s.id\r\n"
			+ "              left join states sta on d.stateid=sta.id\r\n"
			+ "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n" 
			+ "              where ds.asmid=:id "
			+ "GROUP BY del.id",nativeQuery = true)
	List<DeliveryChallan> indexDeliveryChallansAsm(Pageable p,long id);
	
	@Query(value = "SELECT del.id,cu.contactname,del.dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,DATE_FORMAT(del.createddate,'%d-%m-%Y') AS createddate,del.createbyname,del.updatedbyname,del.createdtime,del.taxvalue,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(del.updateddate,'%d-%m-%Y') as updateddate,del.updatedtime,del.vouchermaster_series\r\n"
			+ "			  from delivery_charge del\r\n"
			+ "			  left join customer_sub_contacts cu on cu.id=del.customersubcontacts_id \r\n"
			+ "			  left join distributor d on del.distributor_id = d.id\r\n"
			+ "              left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " left join retailer r on del.retailer_id=r.id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "              left join staff s on ds.asmid=s.id\r\n"
			+ "              left join states sta on d.stateid=sta.id\r\n"
			+ "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n" 
	        + "              where ds.asmid=:id "
			+ "GROUP BY del.id",nativeQuery = true)
	List<DeliveryChallan> indexDeliveryChallansAsm(long id);
	
	
	@Query(value = "SELECT del.id,cu.contactname,del.dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,DATE_FORMAT(del.createddate,'%d-%m-%Y') AS createddate,del.createbyname,del.updatedbyname,del.createdtime,del.taxvalue,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(del.updateddate,'%d-%m-%Y') as updateddate,del.updatedtime,del.vouchermaster_series\r\n"
			+ "			  from delivery_charge del\r\n"
			+ "			  left join customer_sub_contacts cu on cu.id=del.customersubcontacts_id \r\n"
			+ "			  left join distributor d on del.distributor_id = d.id\r\n"
			+ "              left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " left join retailer r on del.retailer_id=r.id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
            + "   left join states sta on d.stateid=sta.id\r\n"
            + "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n" 
            + " where ds.aseid=:id "
			+ "GROUP BY del.id",nativeQuery = true)
	List<DeliveryChallan> indexDeliveryChallansAse(Pageable p,long id);
	
	@Query(value = "SELECT del.id,cu.contactname,del.dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,DATE_FORMAT(del.createddate,'%d-%m-%Y') AS createddate,del.createbyname,del.updatedbyname,del.createdtime,del.taxvalue,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(del.updateddate,'%d-%m-%Y') as updateddate,del.updatedtime,del.vouchermaster_series\r\n"
			+ "			  from delivery_charge del\r\n"
			+ "			  left join customer_sub_contacts cu on cu.id=del.customersubcontacts_id \r\n"
			+ "			  left join distributor d on del.distributor_id = d.id\r\n"
			+ "              left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ " left join retailer r on del.retailer_id=r.id\r\n"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "              left join states sta on d.stateid=sta.id\r\n"
			 + "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
		  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n" 
	         + "        where ds.aseid=:id "
			+ "GROUP BY del.id",nativeQuery = true)
	List<DeliveryChallan> indexDeliveryChallansAse(long id);
	
	
	
	
	@Query(value = "select del.id,cu.contactname,del.dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,DATE_FORMAT(del.createddate,'%d-%m-%Y') AS createddate,del.createbyname,del.updatedbyname,del.createdtime,del.taxvalue,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename,DATE_FORMAT(del.updateddate,'%d-%m-%Y') as updateddate,del.updatedtime,del.vouchermaster_series"
			+ " FROM delivery_charge del"
			+ "  left join customer_sub_contacts cu on cu.id = del.customersubcontacts_id"
			+ " left join retailer r on del.retailer_id=r.id"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "  left join distributor d on del.distributor_id = d.id "
			+ "  left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "  left join states sta on d.stateid=sta.id\r\n"
			 + "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
		  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n" 
			+ " where del.distributor_id= :id"
			+ "  and(del.id Like CONCAT('%',:search, '%')\r\n"
			+ "	   or cu.contactname Like CONCAT('%',:search, '%')\r\n"
	        + " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
			+ "	   or del.buyerorderdate Like CONCAT('%',:search, '%')\r\n"
			+ "	   or del.taxtype Like CONCAT('%',:search, '%')) "
			+ "GROUP BY del.id", nativeQuery = true)
	List<DeliveryChallan> SearchByDeliveryCharge(long id,String search, Pageable p);
	
	
//	end distributor 
	
	@Query(value = "select del.id,cu.contactname,DATE_FORMAT(del.dcdate,'%d-%m-%Y') AS dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,del.createddate,del.createdtime,del.taxvalue,del.vouchermaster_series,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename FROM delivery_charge del"
			+ "  left join customer_sub_contacts cu on cu.id = del.customersubcontacts_id"
			+ " left join retailer r on del.retailer_id=r.id"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "  left join distributor d on del.distributor_id = d.id"
			+ "  left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "  left join states sta on d.stateid=sta.id\r\n"
			 + "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
		  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n" 
			+ " where del.id Like CONCAT('%',:search, '%')\r\n"
			+ "	   or cu.contactname Like CONCAT('%',:search, '%')\r\n"
			+ "	   or del.buyerorderdate Like CONCAT('%',:search, '%')\r\n"
			+ "	   or del.taxtype Like CONCAT('%',:search, '%')"
	        + " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
	        + "GROUP BY del.id", nativeQuery = true)
	List<DeliveryChallan> SearchByDeliveryCharge(String search, Pageable p);
	
	
	@Query(value = "select del.id,cu.contactname,DATE_FORMAT(del.dcdate,'%d-%m-%Y') AS dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,del.createddate,del.createdtime,del.taxvalue,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,del.vouchermaster_series,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename FROM delivery_charge del"
			+ "  left join customer_sub_contacts cu on cu.id = del.customersubcontacts_id"
			+ " left join retailer r on del.retailer_id=r.id"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "  left join distributor d on del.distributor_id = d.id"
			+ "  left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
            + "  left join states sta on d.stateid=sta.id\r\n"
            + "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n" 
			+ " where ds.rsmid=:id Like CONCAT('%',:search, '%')\r\n"
			+ "	   or cu.contactname Like CONCAT('%',:search, '%')\r\n"
			+ "	   or del.buyerorderdate Like CONCAT('%',:search, '%')\r\n"
			+ "	   or del.taxtype Like CONCAT('%',:search, '%')"
			+ " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
	        + "GROUP BY del.id", nativeQuery = true)
	List<DeliveryChallan> SearchByDeliveryChargeRsm(long id,String search, Pageable p);
	
	@Query(value = "select del.id,cu.contactname,DATE_FORMAT(del.dcdate,'%d-%m-%Y') AS dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,del.createddate,del.createdtime,del.taxvalue,del.vouchermaster_series,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename FROM delivery_charge del"
			+ "  left join customer_sub_contacts cu on cu.id = del.customersubcontacts_id"
			+ " left join retailer r on del.retailer_id=r.id"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "  left join distributor d on del.distributor_id = d.id"
			+ "  left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "  left join staff s on ds.asmid=s.id\r\n"
			+ "  left join states sta on d.stateid=sta.id\r\n"
			   + "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
		  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n" 
			+ " where ds.asmid=:id Like CONCAT('%',:search, '%')\r\n"
			+ "	   or cu.contactname Like CONCAT('%',:search, '%')\r\n"
			+ "	   or del.buyerorderdate Like CONCAT('%',:search, '%')\r\n"
			+ "	   or del.taxtype Like CONCAT('%',:search, '%')"
			+ " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
	        + "GROUP BY del.id", nativeQuery = true)
	List<DeliveryChallan> SearchByDeliveryChargeAsm(long id,String search, Pageable p);
	
	
	@Query(value = "select del.id,cu.contactname,DATE_FORMAT(del.dcdate,'%d-%m-%Y') AS dcdate,del.deliverynotno,del.grandtotal,del.gross_amount,del.remarks,del.status,del.createddate,del.createdtime,del.taxvalue,del.vouchermaster_series,COALESCE(r.trade_name, d.trade_name) as distributorname,sta.name as state,del.igst,del.cgst,del.sgst,GROUP_CONCAT(DISTINCT s1.staff_name) as rsmname,GROUP_CONCAT(DISTINCT s2.staff_name) as asename FROM delivery_charge del"
			+ "  left join customer_sub_contacts cu on cu.id = del.customersubcontacts_id"
			+ " left join retailer r on del.retailer_id=r.id"
			+ " LEFT JOIN retailer_to_staff rs ON r.id = rs.retialer_id\r\n"
			+ "  left join distributor d on del.distributor_id = d.id"
			+ "  left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
	        + "  left join states sta on d.stateid=sta.id\r\n"
	        + "LEFT JOIN staff s1 ON ((del.retailer_id is null and ds.rsmid = s1.id) or (del.retailer_id is not null and rs.rsmid=s1.id))\r\n"
	  		+ "LEFT JOIN staff s2 ON ((del.retailer_id is null and ds.aseid = s2.id) or (del.retailer_id is not null and rs.aseid=s2.id))\r\n" 
			+ " where ds.aseid=:id Like CONCAT('%',:search, '%')\r\n"
			+ "	   or cu.contactname Like CONCAT('%',:search, '%')\r\n"
			+ "	   or del.buyerorderdate Like CONCAT('%',:search, '%')\r\n"
			+ "	   or del.taxtype Like CONCAT('%',:search, '%')"
			+ " or LOWER(REGEXP_REPLACE(COALESCE(r.trade_name, d.trade_name), '[^a-z0-9]', '')) \r\n"
	        + "LIKE LOWER(CONCAT('%', REGEXP_REPLACE(:search, '[^a-z0-9]', ''), '%'))\r\n"
	        + "GROUP BY del.id", nativeQuery = true)
	List<DeliveryChallan> SearchByDeliveryChargeAse(long id,String search, Pageable p);
	
	
    public List<DeliveryCharge> findBySalesorderids(int soid);
    
    
    @Query(value = "select * from delivery_charge dc where dc.voucherseries like  CONCAT('%', :search , '%') order by id desc limit 1", nativeQuery = true)
	DeliveryCharge searchByVoucher(String search);

    
    @Query(value = "SELECT * FROM delivery_charge dc order by id desc limit 1",nativeQuery = true)
    DeliveryCharge lastrowstatus();
    
    
    DeliveryCharge findTopByVoucherMasterOrderByIdDesc(VoucherMaster voucher);
    
    DeliveryCharge findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(VoucherMaster voucher);
	
    DeliveryCharge findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(VoucherMaster voucher);  
	
    
}
