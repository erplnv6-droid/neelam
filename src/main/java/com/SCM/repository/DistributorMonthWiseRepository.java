package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SCM.model.PrimaryOrder;
import com.SCM.projection.DistributorMonthReportProjection;

public interface DistributorMonthWiseRepository extends JpaRepository<PrimaryOrder, Integer>{




	@Query(value="select monthname(date_created) as month,year(date_created) as year,\r\n"
			+ "round(sum((ifnull(primary_order.net_value,0))+(ifnull(primary_order.tax_amount,0))),2) as order_value,round((((sum(ifnull(primary_order.net_value,0))+sum(ifnull(primary_order.tax_amount,0)))/sum(ifnull(primary_order.kg_product_total_qty_kg,0)))),2) as rate,\r\n"
			+ "round(sum(((ifnull(primary_order.box_product_total_qty_pcs,0))+(ifnull(primary_order.cooker_product_total_qty_pcs,0)))+(ifnull(primary_order.corporate_product_total_qty_pcs,0))),2) as order_qty_pcs_set,\r\n"
			+ "round(sum(ifnull(primary_order.kg_product_total_qty_kg,0)),2) as order_qty_kg,round(sum(ifnull(primary_order.kg_product_total_qty_pcs,0)),2) as \r\n"
			+ "order_qty_kg_pcs,round(sum(ifnull(primary_order.net_value,0)+(ifnull(primary_order.tax_amount,0))),2) as order_value_set,\r\n"
			+ "round(((sum(ifnull(primary_order.net_value,0))+sum(ifnull(primary_order.tax_amount,0)))/((sum(ifnull(primary_order.box_product_total_qty_pcs,0))+sum(ifnull(primary_order.cooker_product_total_qty_pcs,0)))+sum(ifnull(primary_order.corporate_product_total_qty_pcs,0)))),2) as rate_set,\r\n"
			+ "round(sum(ifnull(sales_items.salesquantitykgs,0)),2) as dispatch_qty_kgs,\r\n"
			+ "round(sum(ifnull(sales_items.salesaltquantity,0)),2) as dispatch_qty_kgs_pcs,\r\n"
			+ "round(sum((ifnull(primary_order.net_value,0))+(ifnull(primary_order.tax_amount,0))),2) as dispatch_value,\r\n"
			+ "round(((sum(ifnull(primary_order.net_value,0))+sum(ifnull(primary_order.tax_amount,0)))/sum(ifnull(sales_items.salesquantitykgs,0))),2) as rate_dispatch,\r\n"
			+ "round(sum(((ifnull(primary_order.box_product_total_qty_pcs,0))+(ifnull(primary_order.cooker_product_total_qty_pcs,0)))+(ifnull(primary_order.corporate_product_total_qty_pcs,0))),2) as dispatch_qty_pcs_set,\r\n"
			+ "round(sum((ifnull(primary_order.net_value,0))+(ifnull(primary_order.tax_amount,0))),2) as dispatch_value_set,\r\n"
			+ "round(((sum(ifnull(primary_order.net_value,0))+sum(ifnull(primary_order.tax_amount,0)))/((sum(ifnull(primary_order.box_product_total_qty_pcs,0))+sum(ifnull(primary_order.cooker_product_total_qty_pcs,0)))+sum(ifnull(primary_order.corporate_product_total_qty_pcs,0)))),2) as rate_dispatch_set,\r\n"
			+ "round(((sum(ifnull(sales_items.salesquantitykgs,0))/sum(ifnull(primary_order.kg_product_total_qty_kg,0)))*100),2) as achievmnet_qty_kgs,\r\n"
			+ "round(((sum(ifnull(sales_items.salesaltquantity,0))/sum(ifnull(primary_order.kg_product_total_qty_pcs,0)))*100),2) as achievmnet_qty_kgs_pcs,\r\n"
			+ "round(((((sum(ifnull(primary_order.net_value,0))+sum(ifnull(primary_order.tax_amount,0)))/sum(ifnull(sales_items.salesquantitykgs,0)))/((sum(ifnull(primary_order.net_value,0))+sum(ifnull(primary_order.tax_amount,0)))/sum(ifnull(primary_order.kg_product_total_qty_kg,0))))*100),2) as achievment_kg_value,\r\n"
			+ "round(((sum(ifnull(primary_order.net_value,0))+sum(ifnull(primary_order.tax_amount,0)))/(sum(ifnull(primary_order.net_value,0))+sum(ifnull(primary_order.tax_amount,0)))*100),2) as achievment_qty_pcs_set,\r\n"
			+ "round((((sum(ifnull(primary_order.box_product_total_qty_pcs,0))+sum(ifnull(primary_order.cooker_product_total_qty_pcs,0))+sum(ifnull(primary_order.corporate_product_total_qty_pcs,0)))/(sum(ifnull(primary_order.box_product_total_qty_pcs,0))+sum(ifnull(primary_order.cooker_product_total_qty_pcs,0))+sum(ifnull(primary_order.corporate_product_total_qty_pcs,0))))*100),2) as achievment_set_value\r\n"
			+ "from  primary_order left join sales on primary_order.distrubator_id=sales.distributor_id left join\r\n"
			+ "sales_items on sales.id=sales_items.sales_id\r\n"
			+ "where primary_order.distrubator_id=? group by monthname(date_created),year(date_created)",nativeQuery=true)

List<DistributorMonthReportProjection> findByDistributorIdAndMonth(@Param ("distributor_id") long distributor_id,Pageable p);
}
