package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SCM.model.WorkOrder;
import com.SCM.projection.RetailerMonthReportProjection;

public interface RetailerMonthWiseRepository extends JpaRepository<WorkOrder, Integer> {

	


//@Query(value="select monthname(work_order_date) as month,year(work_order_date) as year ,round(sum((ifnull(work_order.net_value,0))+(ifnull(work_order.tax_amount,0))),2) as order_value,round((((sum(ifnull(work_order.net_value,0))+sum(ifnull(work_order.tax_amount,0)))/sum(ifnull(work_order.kg_product_total_qty_kg,0)))),2) as rate,\r\n"
//		+ "round(sum(((ifnull(work_order.box_product_total_qty_pcs,0))+(ifnull(work_order.cooker_product_total_qty_pcs,0)))+(ifnull(work_order.corporate_product_total_qty_pcs,0))),2) as order_qty_pcs_set,\r\n"
//		+ "round(sum((ifnull(work_order.net_value,0)+(ifnull(work_order.tax_amount,0)))),2) as order_value_set,\r\n"
//		+ "round(((sum(ifnull(work_order.net_value,0))+sum(ifnull(work_order.tax_amount,0)))/((sum(ifnull(work_order.box_product_total_qty_pcs,0))+sum(ifnull(work_order.cooker_product_total_qty_pcs,0)))+sum(ifnull(work_order.corporate_product_total_qty_pcs,0)))),2) as rate_set,\r\n"
//		+ "round(sum(ifnull(work_order.kg_product_total_qty_kg,0)),2) as order_qty_kg,round(sum(ifnull(work_order.kg_product_total_qty_pcs,0)),2) as \r\n"
//		+ "order_qty_kg_pcs\r\n"
//        + "from work_order where work_order.retailer_id=? group by monthname(work_order_date),year(work_order_date)",nativeQuery = true)
//List<RetailerMonthReportProjection> findByRetailerMonthAndRetailerId(@Param ("retailer_id") long retailer_id,Pageable p);



	@Query(value = "select monthname(createddate) as month,year(createddate) as year ,round(sum((ifnull(work_order.net_value,0))+(ifnull(work_order.tax_amount,0))),2) as order_value,round((((sum(ifnull(work_order.net_value,0))+sum(ifnull(work_order.tax_amount,0)))/sum(ifnull(work_order.kg_product_total_qty_kg,0)))),2) as rate,\r\n"
			+ "round(sum(((ifnull(work_order.box_product_total_qty_pcs,0))+(ifnull(work_order.cooker_product_total_qty_pcs,0)))+(ifnull(work_order.corporate_product_total_qty_pcs,0))),2) as order_qty_pcs_set,\r\n"
			+ "round(sum((ifnull(work_order.net_value,0)+(ifnull(work_order.tax_amount,0)))),2) as order_value_set,\r\n"
			+ "round(((sum(ifnull(work_order.net_value,0))+sum(ifnull(work_order.tax_amount,0)))/((sum(ifnull(work_order.box_product_total_qty_pcs,0))+sum(ifnull(work_order.cooker_product_total_qty_pcs,0)))+sum(ifnull(work_order.corporate_product_total_qty_pcs,0)))),2) as rate_set,\r\n"
			+ "round(sum(ifnull(work_order.kg_product_total_qty_kg,0)),2) as order_qty_kg,round(sum(ifnull(work_order.kg_product_total_qty_pcs,0)),2) as \r\n"
			+ "order_qty_kg_pcs\r\n"
			+ "from work_order where work_order.retailer_id=? group by monthname(createddate),year(createddate)", nativeQuery = true)
	List<RetailerMonthReportProjection> findByRetailerMonthAndRetailerId(@Param("retailer_id") long retailer_id, Pageable p);

}
