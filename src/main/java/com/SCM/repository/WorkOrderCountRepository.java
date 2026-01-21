package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SCM.model.WorkOrder;
import com.SCM.projection.WorkOrderCountProjection;

public interface WorkOrderCountRepository extends JpaRepository<WorkOrder, Integer> {

	@Query(value="select count(case when work_order.converted_to_po='0' THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when work_order.converted_to_po='1' THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(work_order.gross_total) as total_amount\r\n"
			+ "from work_order where year(work_order_date)=year(CURDATE()) and monthname(work_order_date)=monthname(CURDATE())",nativeQuery = true)
	List<WorkOrderCountProjection> getCountWorkOrder();
	
	
	@Query(value="select count(case when work_order.converted_to_po='0' THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when work_order.converted_to_po='1' THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(work_order.gross_total) as total_amount\r\n"
			+ "from work_order where work_order.work_order_date=current_date()",nativeQuery = true)
	List<WorkOrderCountProjection> getDateCountWorkOrder();
	
	
	@Query(value="select count(case when work_order.converted_to_po='0' THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when work_order.converted_to_po='1' THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(work_order.gross_total) as total_amount\r\n"
			+ "from work_order where year(work_order_date)=year(CAST(CURRENT_TIMESTAMP() as date))",nativeQuery = true)
	List<WorkOrderCountProjection> getYearCountWorkOrder();
	

	@Query(value="select count(case when work_order.converted_to_po='0' THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when work_order.converted_to_po='1' THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(work_order.gross_total) as total_amount\r\n"
			+ "from work_order left join retailer on retailer.id=work_order.retailer_id\r\n"
			+ "left join distributor\r\n"
			+ "on distributor.id=retailer.distributor_id\r\n"

			+ " where year(work_order_date)=year(CURDATE()) and monthname(work_order_date)=monthname(CURDATE()) and retailer.distributor_id=?",nativeQuery = true)



	List<WorkOrderCountProjection> getCountWorkOrderByDistributorIdMonth(@Param ("distributor_id") int distributor_id);
	
	
	@Query(value="select count(case when work_order.converted_to_po='0' THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when work_order.converted_to_po='1' THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(work_order.gross_total) as total_amount\r\n"
			+ "from work_order left join retailer on retailer.id=work_order.retailer_id\r\n"
			+ "left join distributor\r\n"
			+ "on distributor.id=retailer.distributor_id\r\n"

			+ " where work_order.work_order_date=current_date() and retailer.distributor_id=?",nativeQuery = true)



	List<WorkOrderCountProjection> getDateCountWorkOrderByDistributorIdDate(@Param ("distributor_id") int distributor_id);
	
	
	
	@Query(value="select count(case when work_order.converted_to_po='0' THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when work_order.converted_to_po='1' THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(work_order.gross_total) as total_amount\r\n"
			+ "from work_order left join retailer on retailer.id=work_order.retailer_id\r\n"
			+ "left join distributor\r\n"
			+ "on distributor.id=retailer.distributor_id\r\n"

			+ " where year(work_order_date)=year(CAST(CURRENT_TIMESTAMP() as date)) and retailer.distributor_id=?",nativeQuery = true)
	List<WorkOrderCountProjection> getYearCountWorkOrderByDistributorIdYear(@Param ("distributor_id") int distributor_id);


	@Query(value="select count(case when work_order.converted_to_po='0' THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when work_order.converted_to_po='1' THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(work_order.gross_total) as total_amount\r\n"
			+ "from work_order where year(work_order_date)=year(CURDATE()) and monthname(work_order_date)=monthname(CURDATE()) and work_order.retailer_id=?",nativeQuery = true)
	List<WorkOrderCountProjection> getCountWorkOrderByRetailerMonth(@Param ("retailer_id") int retailer_id);
	
	
	@Query(value="select count(case when work_order.converted_to_po='0' THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when work_order.converted_to_po='1' THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(work_order.gross_total) as total_amount\r\n"
			+ "from work_order where work_order.work_order_date=current_date() and work_order.retailer_id=?",nativeQuery = true)
	List<WorkOrderCountProjection> getDateCountWorkOrderByRetailerDate(@Param ("retailer_id") int retailer_id);
	
	
	@Query(value="select count(case when work_order.converted_to_po='0' THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when work_order.converted_to_po='1' THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(work_order.gross_total) as total_amount\r\n"
			+ "from work_order where year(work_order_date)=year(CAST(CURRENT_TIMESTAMP() as date)) and work_order.retailer_id=?",nativeQuery = true)
	List<WorkOrderCountProjection> getYearCountWorkOrderByRetailerYear(@Param ("retailer_id") int retailer_id);

	
	
	@Query(value="select count(case when work_order.converted_to_po='0' THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when work_order.converted_to_po='1' THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(work_order.gross_total) as total_amount\r\n"
			+ "from work_order left join staff on staff.id=work_order.nsmid or staff.id=work_order.rsmid\r\n"
			+ "or staff.id=work_order.asmid or staff.id=work_order.aseid \r\n"
			+ " where year(work_order_date)=year(CURDATE()) and monthname(work_order_date)=monthname(CURDATE()) and staff.id=?",nativeQuery = true)
	List<WorkOrderCountProjection> getYearCountWorkOrderBySatffMonth(@Param ("id") int id);
	
	
	@Query(value="select count(case when work_order.converted_to_po='0' THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when work_order.converted_to_po='1' THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(work_order.gross_total) as total_amount\r\n"
			+ "from work_order left join staff on staff.id=work_order.nsmid or staff.id=work_order.rsmid\r\n"
			+ "or staff.id=work_order.asmid or staff.id=work_order.aseid \r\n"
			+ "where work_order.work_order_date=current_date() and staff.id=?",nativeQuery = true)
	List<WorkOrderCountProjection> getYearCountWorkOrderBySatffDate(@Param ("id") int id);
	
	
	@Query(value="select count(case when work_order.converted_to_po='0' THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when work_order.converted_to_po='1' THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(work_order.gross_total) as total_amount\r\n"
			+ "from work_order left join staff on staff.id=work_order.nsmid or staff.id=work_order.rsmid\r\n"
			+ "or staff.id=work_order.asmid or staff.id=work_order.aseid \r\n"
			+ "where year(work_order_date)=year(CAST(CURRENT_TIMESTAMP() as date)) and staff.id=?",nativeQuery = true)
	List<WorkOrderCountProjection> getYearCountWorkOrderBySatffYear(@Param ("id") int id);

}


