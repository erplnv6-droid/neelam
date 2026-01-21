package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SCM.model.PrimaryOrder;
import com.SCM.projection.PrimaryOrderCountProjection;


public interface PrimaryOrderCountRepository extends JpaRepository<PrimaryOrder, Integer> {

	@Query(value="select count(case when primary_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when primary_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(primary_order.gross_total) as total_amount\r\n"
			+ "from primary_order where year(date_created)=year(CURDATE()) and monthname(date_created)=monthname(CURDATE())",nativeQuery = true)
	List<PrimaryOrderCountProjection> getCountWorkOrder();
	
	
	@Query(value="select count(case when primary_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when primary_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(primary_order.gross_total) as total_amount\r\n"
			+ "from primary_order where date(date_created)=CAST(CURRENT_TIMESTAMP() as date)",nativeQuery = true)
	List<PrimaryOrderCountProjection> getDateCountWorkOrder();
	
	
	@Query(value="select count(case when primary_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when primary_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(primary_order.gross_total) as total_amount\r\n"
			+ "from primary_order where year(date_created)=year(CAST(CURRENT_TIMESTAMP() as date))",nativeQuery = true)
	List<PrimaryOrderCountProjection> getYearCountWorkOrder();
	

	@Query(value="select count(case when primary_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when primary_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(primary_order.gross_total) as total_amount\r\n"

			+ "from primary_order where year(date_created)=year(CURDATE()) and monthname(date_created)=monthname(CURDATE()) and distrubator_id=?",nativeQuery = true)



	List<PrimaryOrderCountProjection> getCountWorkOrderByDistributorIdMonth(@Param ("distrubator_id") int distrubator_id);
	
	
	
	@Query(value="select count(case when primary_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when primary_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(primary_order.gross_total) as total_amount\r\n"

			+ "from primary_order where date(date_created)=CAST(CURRENT_TIMESTAMP() as date) and distrubator_id=?",nativeQuery = true)



	List<PrimaryOrderCountProjection> getDateCountWorkOrderDistributorIdDate(@Param ("distrubator_id") int distrubator_id);
	
	
	@Query(value="select count(case when primary_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when primary_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(primary_order.gross_total) as total_amount\r\n"

			+ "from primary_order where year(date_created)=year(CAST(CURRENT_TIMESTAMP() as date)) and distrubator_id=?",nativeQuery = true)
	List<PrimaryOrderCountProjection> getYearCountWorkOrderDistributorIdYear(@Param ("distrubator_id") int distrubator_id);
	

	@Query(value="select count(case when primary_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when primary_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(primary_order.gross_total) as total_amount\r\n"
			+ "from primary_order left join staff on staff.id=primary_order.nsmid or staff.id=primary_order.rsmid\r\n"
			+ "or staff.id=primary_order.asmid or staff.id=primary_order.aseid where year(date_created)=year(CURDATE()) and monthname(date_created)=monthname(CURDATE()) and staff.id=?",nativeQuery = true)
	List<PrimaryOrderCountProjection> getCountWorkOrderByStaffMonth(@Param ("id") int id);
	
	
	@Query(value="select count(case when primary_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when primary_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(primary_order.gross_total) as total_amount\r\n"
			+ "from primary_order left join staff on staff.id=primary_order.nsmid or staff.id=primary_order.rsmid\r\n"
			+ "or staff.id=primary_order.asmid or staff.id=primary_order.aseid where date(date_created)=CAST(CURRENT_TIMESTAMP() as date)  and staff.id=?",nativeQuery = true)
	List<PrimaryOrderCountProjection> getDateCountWorkOrderSatffDate(@Param ("id") int id);
	
	
	@Query(value="select count(case when primary_order.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when primary_order.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "sum(primary_order.gross_total) as total_amount\r\n"
			+ "from primary_order left join staff on staff.id=primary_order.nsmid or staff.id=primary_order.rsmid\r\n"
			+ "or staff.id=primary_order.asmid or staff.id=primary_order.aseid where year(date_created)=year(CAST(CURRENT_TIMESTAMP() as date)) and staff.id=?",nativeQuery = true)
	List<PrimaryOrderCountProjection> getYearCountWorkOrderSatffYear(@Param ("id") int id);
	

}
