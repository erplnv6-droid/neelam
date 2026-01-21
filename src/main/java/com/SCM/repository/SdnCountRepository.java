package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SCM.model.SupplierDeliveryNote;

import com.SCM.projection.SdnCountProjection;

public interface SdnCountRepository extends JpaRepository<SupplierDeliveryNote, Integer> {


	@Query(value="select count(case when supplier_delivery_note.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when supplier_delivery_note.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "round(ifnull(sum(supplier_delivery_note.grandtotal),0),2) as total_amount\r\n"
			+ "from supplier_delivery_note where year(sdndate)=year(CURDATE()) and monthname(sdndate)=monthname(CURDATE())",nativeQuery = true)
	List<SdnCountProjection> getMonthCountSdn();
	
	
	
	@Query(value="select count(case when supplier_delivery_note.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when supplier_delivery_note.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "round(ifnull(sum(supplier_delivery_note.grandtotal),0),2) as total_amount\r\n"
			+ "from supplier_delivery_note where date(sdndate)=CAST(CURRENT_TIMESTAMP() as date)",nativeQuery = true)
	List<SdnCountProjection> getDateCountSdn();
	
	
	@Query(value="select count(case when supplier_delivery_note.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when supplier_delivery_note.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "round(ifnull(sum(supplier_delivery_note.grandtotal),0),2) as total_amount\r\n"
			+ "from supplier_delivery_note where year(sdndate)=year(CAST(CURRENT_TIMESTAMP() as date))",nativeQuery = true)
	List<SdnCountProjection> getYearCountSdn();
	
	
	@Query(value="select count(case when supplier_delivery_note.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when supplier_delivery_note.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "round(ifnull(sum(supplier_delivery_note.grandtotal),0),2) as total_amount\r\n"
			+ "from supplier_delivery_note where year(sdndate)=year(CURDATE()) and monthname(sdndate)=monthname(CURDATE()) and \r\n"
			+ "supplier_delivery_note.supplier_id=?",nativeQuery = true)
	List<SdnCountProjection> getMonthCountSdnBySupplierId(@Param ("supplier_id") int supplier_id);
	
	
	@Query(value="select count(case when supplier_delivery_note.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when supplier_delivery_note.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "round(ifnull(sum(supplier_delivery_note.grandtotal),0),2) as total_amount\r\n"
			+ "from supplier_delivery_note where date(sdndate)=CAST(CURRENT_TIMESTAMP() as date) and \r\n"
			+ "supplier_delivery_note.supplier_id=?",nativeQuery = true)
	List<SdnCountProjection> getDateCountSdnBySupplierId(@Param ("supplier_id") int supplier_id);
	
	
	@Query(value="select count(case when supplier_delivery_note.status=\"pending\" THEN 1 ELSE NULL END) as total_pending_count,\r\n"
			+ "count(case when supplier_delivery_note.status=\"converted\" THEN 1 ELSE NULL END) as total_converted_count,\r\n"
			+ "count(*) as total_count,\r\n"
			+ "round(ifnull(sum(supplier_delivery_note.grandtotal),0),2) as total_amount\r\n"
			+ "from supplier_delivery_note where year(sdndate)=year(CAST(CURRENT_TIMESTAMP() as date)) and \r\n"
			+ "supplier_delivery_note.supplier_id=?",nativeQuery = true)
	List<SdnCountProjection> getYearCountSdnBySupplierId(@Param ("supplier_id") int supplier_id);
	
	
	
	
	
}
