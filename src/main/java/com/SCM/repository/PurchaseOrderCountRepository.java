package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SCM.model.PurchaseOrder;
import com.SCM.projection.PurchaseOrderCountProjection;


public interface PurchaseOrderCountRepository extends JpaRepository<PurchaseOrder, Integer>{

	
	@Query(value="select count(case when purchase_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "	count(case when purchase_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "	count(*) as total_count,\r\n"
			+ "	round(ifnull(sum(purchase_order.grandtotal),0),2) as total_amount\r\n"
			+ "	from purchase_order where year(podate)=year(CURDATE()) and monthname(podate)=monthname(CURDATE())",nativeQuery = true)
	List<PurchaseOrderCountProjection> getMonthCountPurchaseOrder();
	
	
	@Query(value="select count(case when purchase_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "	count(case when purchase_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "	count(*) as total_count,\r\n"
			+ "	round(ifnull(sum(purchase_order.grandtotal),0),2) as total_amount\r\n"
			+ "	from purchase_order where date(podate)=CAST(CURRENT_TIMESTAMP() as date)",nativeQuery = true)
	List<PurchaseOrderCountProjection> getDateCountPurchaseOrder();
	
	
	@Query(value="select count(case when purchase_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "	count(case when purchase_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "	count(*) as total_count,\r\n"
			+ "	round(ifnull(sum(purchase_order.grandtotal),0),2) as total_amount\r\n"
			+ "	from purchase_order where year(podate)=year(CAST(CURRENT_TIMESTAMP() as date))",nativeQuery = true)
	List<PurchaseOrderCountProjection> getYearCountPurchaseOrder();
	
	
	@Query(value="select count(case when purchase_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "	count(case when purchase_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "	count(*) as total_count,\r\n"
			+ "	round(ifnull(sum(purchase_order.grandtotal),0),2) as total_amount\r\n"
			+ "	from purchase_order where year(podate)=year(CURDATE()) and monthname(podate)=monthname(CURDATE()) and purchase_order.supplier_id=? ",nativeQuery = true)
	List<PurchaseOrderCountProjection> getMonthCountPurchaseOrderBySupplierId(@Param ("supplier_id") int supplier_id);
	
	@Query(value="select count(case when purchase_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "	count(case when purchase_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "	count(*) as total_count,\r\n"
			+ "	round(ifnull(sum(purchase_order.grandtotal),0),2) as total_amount\r\n"
			+ "	from purchase_order where date(podate)=CAST(CURRENT_TIMESTAMP() as date) and purchase_order.supplier_id=? ",nativeQuery = true)
	List<PurchaseOrderCountProjection> getDateCountPurchaseOrderBySupplierId(@Param ("supplier_id") int supplier_id);
	
	
	@Query(value="select count(case when purchase_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "	count(case when purchase_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "	count(*) as total_count,\r\n"
			+ "	round(ifnull(sum(purchase_order.grandtotal),0),2) as total_amount\r\n"
			+ "	from purchase_order where year(podate)=year(CAST(CURRENT_TIMESTAMP() as date)) and purchase_order.supplier_id=? ",nativeQuery = true)
	List<PurchaseOrderCountProjection> getYearCountPurchaseOrderBySupplierId(@Param ("supplier_id") int supplier_id);
	
	
	
	
}
